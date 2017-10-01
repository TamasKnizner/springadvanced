package com.epam.tamasknizner.springadvancedtraining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.repository.EventRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Service
public class EventServiceImpl extends RepositoryBasedDomainObjectService<Event> implements EventService {

    /**
     * A clock reference that could be easily injected for testing purposes.
     */
    private Clock clock;

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        return repository.findSingleOrDefault(e -> name.equals(e.getName()));
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        LocalDateTime lowerBound = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime upperBound = LocalDateTime.of(to, LocalTime.MAX);
        return repository.find(event -> !event.getAirDates()
                .subSet(lowerBound, true, upperBound, true)
                .isEmpty());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        LocalDateTime lowerBound = LocalDateTime.now(clock);
        if (to.isBefore(lowerBound)) {
            throw new IllegalArgumentException("to");
        }
        return repository.find(event -> !event.getAirDates()
                .subSet(lowerBound, true, to, true)
                .isEmpty());
    }

    public Clock getClock() {
        return clock;
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
