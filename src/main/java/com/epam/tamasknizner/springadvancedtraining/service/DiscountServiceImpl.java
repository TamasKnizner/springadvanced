package com.epam.tamasknizner.springadvancedtraining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.DiscountStrategyRepository;
import com.epam.tamasknizner.springadvancedtraining.service.settings.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountStrategyRepository discountStrategyRepository;
    private Set<DiscountStrategy> discountStrategies = null;

    @Autowired
    public DiscountServiceImpl(DiscountStrategyRepository discountStrategyRepository) {
        this.discountStrategyRepository = discountStrategyRepository;
    }

    private synchronized Set<DiscountStrategy> getDiscountStrategies() {
        if (discountStrategies == null) {
            this.discountStrategies = new HashSet<>(discountStrategyRepository.getAll());
        }
        return discountStrategies;
    }

    @Override
    public Map<Long, ApplicableDiscountInfo> getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, @Nonnull LinkedHashSet<Long> seats) {
        if (seats.isEmpty()) {
            throw new IllegalArgumentException("seats");
        }
        Set<DiscountStrategy> discountStrategies = getDiscountStrategies();
        if (discountStrategies.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, ApplicableDiscountInfo> discountMap = new HashMap<>();
        final AtomicInteger seatNaturalOrderNumber = new AtomicInteger(1);
        for (Long seat : seats) {
            Optional<ApplicableDiscountInfo> maxDiscount = discountStrategies.stream()
                    .map(strategy -> new ApplicableDiscountInfo(strategy,
                            strategy.getDiscount(user, event, airDateTime,
                            seats.size(), seatNaturalOrderNumber.get())))
                    .max((i1, i2) -> Float.compare(i1.getDiscountPercent(), i2.getDiscountPercent()));

            if (maxDiscount.isPresent() && maxDiscount.get().getDiscountPercent() > 0.0f) {
                discountMap.put(seat, maxDiscount.get());
            }
            seatNaturalOrderNumber.incrementAndGet();
        }

        return discountMap;
    }
}
