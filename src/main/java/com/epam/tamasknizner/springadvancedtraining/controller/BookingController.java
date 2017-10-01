package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookingController {

    private static final String GET_TICKET_PRICE_MAPPING = "/price";
    private static final String DO_BOOKING_MAPPING = "/booking";
    private static final String GET_TICKETS_FOR_EVENT_MAPPING = "/tickets";

    private final BookingService bookingService;

    @Autowired
    public BookingController(final BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(value = GET_TICKET_PRICE_MAPPING, method = RequestMethod.GET)
    public String getTicketPrice() {

        return "price";
    }

    @RequestMapping(value = DO_BOOKING_MAPPING, method = RequestMethod.POST)
    public String bookTickets() {

        return "booking";
    }

    @RequestMapping(value = GET_TICKETS_FOR_EVENT_MAPPING, method = RequestMethod.GET)
    public String getTicketsForEvent() {

        return "/ticketlist";
    }

}
