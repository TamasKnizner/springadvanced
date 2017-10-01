package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private static final String GET_TICKET_PRICE_MAPPING = "/price";
    private static final String DO_BOOKING_MAPPING = "/booking";
    private static final String GET_TICKETS_FOR_EVENT_MAPPING = "/ticketlist";

    private static final String PRICE_VIEW_NAME = "price";
    private static final String BOOKING_VIEW_NAME = "booking";
    private static final String TICKET_LIST_VIEW_NAME = "ticketlist";

    private final BookingFacade bookingFacade;

    @Autowired
    public BookingController(final BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = GET_TICKET_PRICE_MAPPING, method = RequestMethod.GET)
    public String getTicketPrice(Model model, @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId,
           @RequestParam("dateTime") String dateTime, @RequestParam("seats") String seats) {
        model.addAttribute("ticketPrice", bookingFacade.getTicketsPrice(eventId, userId, dateTime, seats));
        return PRICE_VIEW_NAME;
    }

    @RequestMapping(value = DO_BOOKING_MAPPING, method = RequestMethod.POST)
    public String bookTickets() {

        return BOOKING_VIEW_NAME;
    }

    @RequestMapping(value = GET_TICKETS_FOR_EVENT_MAPPING, method = RequestMethod.GET)
    public String getTicketsForEvent() {

        return TICKET_LIST_VIEW_NAME;
    }

}
