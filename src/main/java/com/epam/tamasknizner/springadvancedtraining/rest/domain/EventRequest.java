package com.epam.tamasknizner.springadvancedtraining.rest.domain;

import com.epam.tamasknizner.springadvancedtraining.domain.EventRating;
import com.epam.tamasknizner.springadvancedtraining.domain.request.deserializer.EventRatingDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class EventRequest {
    private String name;
    private double basePrice;
    private double ticketPrice;
    @JsonDeserialize(using = EventRatingDeserializer.class)
    private EventRating rating;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(final double basePrice) {
        this.basePrice = basePrice;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(final double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(final EventRating rating) {
        this.rating = rating;
    }
}
