package com.epam.tamasknizner.springadvancedtraining.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.DiscountCounter;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.DiscountCounterRepository;
import com.epam.tamasknizner.springadvancedtraining.service.DiscountService;
import com.epam.tamasknizner.springadvancedtraining.service.settings.AbstractDiscountStrategy;
import com.epam.tamasknizner.springadvancedtraining.service.settings.DiscountStrategy;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Aspect
@Component
public class DiscountAspect {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ConcurrentHashMap<DiscountStrategy, Integer> total = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<DiscountUserKey, Integer> perUser = new ConcurrentHashMap<>();

    @Autowired
    private DiscountCounterRepository discountCounterRepository;

    @AfterReturning(value = "execution(* com.epam.tamasknizner.springadvancedtraining.service.DiscountService.getDiscount(..))", returning = "returnValue")
    public void handleGetDiscount(JoinPoint joinPoint, Map<Long, DiscountService.ApplicableDiscountInfo> returnValue) {
        List<AbstractDiscountStrategy> appliedStrategies = returnValue.values().stream()
                .filter(i -> i.getDiscountPercent() > 0.0f)
                .map(DiscountService.ApplicableDiscountInfo::getDiscountStrategy)
                .map(ds -> (AbstractDiscountStrategy) ds)
                .collect(Collectors.toList());

        if (appliedStrategies.isEmpty()) {
            return;
        }

        for (AbstractDiscountStrategy discountStrategy : appliedStrategies) {
            User user = (User) joinPoint.getArgs()[0];
            incrementCounter(discountStrategy, total);
            DiscountUserKey discountUserKey = new DiscountUserKey(discountStrategy, user);
            incrementCounter(discountUserKey, perUser);
            submitUpdateTask(discountUserKey);
        }
    }

    private void submitUpdateTask(DiscountUserKey discountUserKey) {
        executorService.submit(() -> {
            Set<DiscountCounter> discountCounters = discountCounterRepository.find(dc ->
                    dc.getDiscountStrategyId() == discountUserKey.getDiscountStrategy().getId() &&
                            (dc.getUserId() == null && discountUserKey.getUser() == null ||
                            discountUserKey.getUser() != null && dc.getUserId() == discountUserKey.getUser().getId()));

            if (discountCounters.size() > 1) {
                throw new IllegalStateException();
            }
            DiscountCounter discountCounter;
            if (discountCounters.isEmpty()) {
                discountCounter = new DiscountCounter(discountUserKey.getDiscountStrategy().getId(),
                        discountUserKey.getUser() == null ? null : discountUserKey.getUser().getId());
            } else {
                discountCounter = discountCounters.iterator().next();
            }
            discountCounter.setCounter(perUser.getOrDefault(discountUserKey, 0));
            discountCounterRepository.saveOrUpdate(discountCounter);
        });
    }

    private <TKey> void incrementCounter(TKey key, ConcurrentHashMap<TKey, Integer> map) {
        map.compute(key, (k, v) -> v == null ? 1 : v + 1);
    }

    public void clear() {
        total.clear();
        perUser.clear();
    }

    public int getTotal(DiscountStrategy key) {
        return total.getOrDefault(key, 0);
    }

    public int getPerUser(DiscountUserKey key) {
        return perUser.getOrDefault(key, 0);
    }

    public static class DiscountUserKey {
        private final AbstractDiscountStrategy discountStrategy;
        private final User user;

        public DiscountUserKey(AbstractDiscountStrategy discountStrategy, User user) {
            this.discountStrategy = discountStrategy;
            this.user = user;
        }

        public AbstractDiscountStrategy getDiscountStrategy() {
            return discountStrategy;
        }

        public User getUser() {
            return user;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DiscountUserKey that = (DiscountUserKey) o;
            return Objects.equals(discountStrategy, that.discountStrategy) &&
                    Objects.equals(user, that.user);
        }

        @Override
        public int hashCode() {
            return Objects.hash(discountStrategy, user);
        }
    }

}
