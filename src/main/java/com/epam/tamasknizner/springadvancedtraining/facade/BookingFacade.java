package com.epam.tamasknizner.springadvancedtraining.facade;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;

import java.util.Set;

public interface BookingFacade {

    double getTicketsPrice(Long eventId, Long userId, String dateTime, String seats);

    void bookTickets(Set<Long> ticketIds);

    Set<Ticket> getPurchasedTicketsForEvent(Long eventId, String dateTime);
}
