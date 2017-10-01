package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.EventCounter;
import com.epam.tamasknizner.springadvancedtraining.repository.EventCounterRepository;
import org.springframework.stereotype.Component;

@Component
public class EventCounterRepositoryImpl extends MapBasedRepository<EventCounter, Long> implements EventCounterRepository{

    public EventCounterRepositoryImpl() {
        super(EventCounter::getEventId, EventCounter::setEventId);
    }
}
