package com.epam.tamasknizner.springadvancedtraining.facade;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.service.BookingService;
import com.epam.tamasknizner.springadvancedtraining.service.EventService;
import com.epam.tamasknizner.springadvancedtraining.service.TicketService;
import com.epam.tamasknizner.springadvancedtraining.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public double getTicketsPrice(final Long eventId, final Long userId, final LocalDateTime dateTime, final String seats) {
        Event event = eventService.getById(eventId);
        User user = userService.getUserById(userId);
        LinkedHashSet<Long> seatNumbers = getSeatNumbers(seats);
        return bookingService.getTicketsPrice(event, dateTime, user, seatNumbers);
    }

    @Override
    public void bookTickets(final Set<Ticket> tickets) {
        bookingService.bookTickets(tickets);
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(final Long eventId, final LocalDateTime dateTime) {
        Event event = eventService.getById(eventId);
        return bookingService.getPurchasedTicketsForEvent(event, dateTime);
    }

    private LinkedHashSet<Long> getSeatNumbers(final String seats) {
        return Arrays.stream(seats.split(",")).map(Long::parseLong).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
