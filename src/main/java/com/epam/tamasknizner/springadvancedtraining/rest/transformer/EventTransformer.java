package com.epam.tamasknizner.springadvancedtraining.rest.transformer;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.rest.domain.EventRequest;
import org.springframework.stereotype.Component;

@Component
public class EventTransformer {

    public Event transform(final Long id, final EventRequest eventRequest) {
        Event event = new Event();
        event.setId(id);
        event.setBasePrice(eventRequest.getBasePrice());
        event.setName(eventRequest.getName());
        event.setRating(eventRequest.getRating());
        event.setTicketPrice(eventRequest.getTicketPrice());
        return event;
    }

    public Event transform(final EventRequest eventRequest) {
        Event event = new Event();
        event.setBasePrice(eventRequest.getBasePrice());
        event.setName(eventRequest.getName());
        event.setRating(eventRequest.getRating());
        event.setTicketPrice(eventRequest.getTicketPrice());
        return event;
    }

}
