package com.epam.tamasknizner.springadvancedtraining.domain.request;

import com.epam.tamasknizner.springadvancedtraining.domain.request.deserializer.LocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

/**
 * @author Tamas_Knizner
 */
public class TicketView {

    private Long eventId;
    private Long userId;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
    private Long seat;

    public TicketView() {
    }

    public TicketView(Long eventId, Long userId, LocalDateTime dateTime, Long seat) {
        this.eventId = eventId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getSeat() {
        return seat;
    }

    public void setSeat(Long seat) {
        this.seat = seat;
    }
}
