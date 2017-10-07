package com.epam.tamasknizner.springadvancedtraining.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.EventCounter;
import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.repository.EventCounterRepository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
public class CounterAspect {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ConcurrentHashMap<Event, Integer> pricesQueried = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Event, Integer> booked = new ConcurrentHashMap<>();
    private EventCounterRepository eventCounterRepository;

    @AfterReturning("execution(* com.epam.tamasknizner.springadvancedtraining.service.BookingService.bookTickets(..))")
    public void handleBookTickets(JoinPoint joinPoint) {
        Collection<Ticket> tickets = (Collection<Ticket>) joinPoint.getArgs()[0];
        for (Ticket ticket : tickets) {
            incrementCounterForEvent(ticket.getEvent(), booked);
        }
    }

    @Before("execution(* com.epam.tamasknizner.springadvancedtraining.service.BookingService.getTicketsPrice(..))")
    public void handleGetTicketsPrice(JoinPoint joinPoint) {
        Event event = (Event)joinPoint.getArgs()[0];
        if(event != null) {
            incrementCounterForEvent(event, pricesQueried);
        }
    }

    private void submitUpdateTask(Event event) {
        executorService.submit(() -> {
            EventCounter eventCounter = eventCounterRepository.findById(event.getId());
            if (eventCounter == null) {
                eventCounter = new EventCounter(event.getId());
            }
            eventCounter.setPricesQueried(pricesQueried.getOrDefault(event, 0));
            eventCounter.setBooked(booked.getOrDefault(event, 0));

            eventCounterRepository.saveOrUpdate(eventCounter);
        });
    }

    private void incrementCounterForEvent(Event event, ConcurrentHashMap<Event, Integer> map) {
        if(event != null) {
            map.compute(event, (key, value) -> value == null ? 1 : value + 1);
            submitUpdateTask(event);
        }
    }

    public int getPricesQueriedTimes(Event event) {
        return pricesQueried.getOrDefault(event, 0);
    }

    public int getBookedTimes(Event event) {
        return booked.getOrDefault(event, 0);
    }

    public void clear() {
        pricesQueried.clear();
        booked.clear();
    }

    @Autowired
    public void setEventCounterRepository(EventCounterRepository eventCounterRepository) {
        this.eventCounterRepository = eventCounterRepository;
    }
}
