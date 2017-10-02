package com.epam.tamasknizner.springadvancedtraining.facade;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;

import java.time.LocalDateTime;
import java.util.Set;

public interface BookingFacade {

    double getTicketsPrice(Long eventId, Long userId, LocalDateTime dateTime, String seats);

    void bookTickets(Set<Ticket> tickets);

    Set<Ticket> getPurchasedTicketsForEvent(Long eventId, LocalDateTime dateTime);
}
