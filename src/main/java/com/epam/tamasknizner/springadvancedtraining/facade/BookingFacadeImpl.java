package com.epam.tamasknizner.springadvancedtraining.facade;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.service.BookingService;
import com.epam.tamasknizner.springadvancedtraining.service.EventService;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookingFacadeImpl implements BookingFacade {

    private final BookingService bookingService;
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public BookingFacadeImpl(final BookingService bookingService, final EventService eventService, final UserService userService) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public double getTicketsPrice(final Long eventId, final Long userId, final String dateTime, final String seats) {
        return 0;
    }

    @Override
    public void bookTickets(final Set<Long> ticketIds) {

    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(final Long eventId, final String dateTime) {
        return null;
    }
}
