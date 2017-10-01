package com.epam.tamasknizner.springadvancedtraining.domain;

public class EventCounter {
    private long eventId;
    private int pricesQueried;
    private int booked;

    public EventCounter(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getPricesQueried() {
        return pricesQueried;
    }

    public void setPricesQueried(int pricesQueried) {
        this.pricesQueried = pricesQueried;
    }

    public int getBooked() {
        return booked;
    }

    public void setBooked(int booked) {
        this.booked = booked;
    }

}
