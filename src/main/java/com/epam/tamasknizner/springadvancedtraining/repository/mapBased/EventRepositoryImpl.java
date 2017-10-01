package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EventRepositoryImpl
        extends MapBasedIdentityRepository<Event>
        implements EventRepository {
    public EventRepositoryImpl() {
    }

    public EventRepositoryImpl(Collection<Event> events) {
        super(events);
    }

//    public EventRepositoryImpl(ObjectLoader<Collection<Event>> loader) {
//        super(loader);
//    }
}
