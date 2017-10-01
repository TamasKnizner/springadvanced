package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.repository.EventRepository;

import java.util.Collection;

public class EventRepositoryImpl extends MapBasedIdentityRepository<Event> implements EventRepository {

    public EventRepositoryImpl() {
    }

    public EventRepositoryImpl(Collection<Event> events) {
        super(events);
    }

}
