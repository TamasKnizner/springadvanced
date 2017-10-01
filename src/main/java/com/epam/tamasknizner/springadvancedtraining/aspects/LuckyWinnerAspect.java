package com.epam.tamasknizner.springadvancedtraining.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.domain.UserLuckyEventInfo;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;

import java.time.LocalDateTime;

//@Aspect
//@Component
public class LuckyWinnerAspect {

    //private final LuckyWinnerProbabilityChecker probabilityChecker;

    private final UserService userService;

    public LuckyWinnerAspect(
            //LuckyWinnerProbabilityChecker probabilityChecker,
            UserService userService
    ) {
        //this.probabilityChecker = probabilityChecker;
        this.userService = userService;
    }

    @Around("execution(* com.epam.tamasknizner.springadvancedtraining.service.BookingService.getTicketsPrice(..))")
    public Object handleGetPurchasingPrice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        User user = (User) args[2];

        if (user == null /*|| !probabilityChecker.isTrue()*/) {
            return joinPoint.proceed();
        }

        Event event = (Event) args[0];
        LocalDateTime airDate = (LocalDateTime) args[1];

        user.addLuckyEvent(new UserLuckyEventInfo(user.getId(), event.getId(), airDate));
        userService.save(user);

        return 0.0;
    }
}
