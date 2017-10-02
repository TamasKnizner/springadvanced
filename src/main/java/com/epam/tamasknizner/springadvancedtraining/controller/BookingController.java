package com.epam.tamasknizner.springadvancedtraining.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.request.TicketSaveRequest;
import com.epam.tamasknizner.springadvancedtraining.facade.BookingFacade;
import com.epam.tamasknizner.springadvancedtraining.transformer.TicketViewTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class BookingController {

    private static final String GET_TICKET_PRICE_MAPPING = "/price";
    private static final String DO_BOOKING_MAPPING = "/booking";
    private static final String GET_TICKETS_FOR_EVENT_MAPPING = "/ticketlist";
    private static final String GET_TICKETS_FOR_EVENT_MAPPING_PDF = "/ticketlistpdf";

    private static final String PRICE_VIEW_NAME = "price";
    private static final String BOOKING_VIEW_NAME = "booking";
    private static final String TICKET_LIST_VIEW_NAME = "ticketlist";

    private final BookingFacade bookingFacade;
    private final TicketViewTransformer ticketViewTransformer;

    @Autowired
    public BookingController(final BookingFacade bookingFacade, final TicketViewTransformer ticketViewTransformer) {
        this.bookingFacade = bookingFacade;
        this.ticketViewTransformer = ticketViewTransformer;
    }

    //Example query string: price?eventId=1&dateTime=1995-06-25T20:00Z&userId=1&seats=1
    @RequestMapping(value = GET_TICKET_PRICE_MAPPING, method = RequestMethod.GET)
    public String getTicketPrice(Model model, @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime, @RequestParam("seats") String seats) {
        model.addAttribute("ticketPrice", bookingFacade.getTicketsPrice(eventId, userId, dateTime, seats));
        return PRICE_VIEW_NAME;
    }

    /*
    Example payload:
    {
    "tickets": [
        {
            "eventId": "1",
            "userId": "1",
            "dateTime": "1995-06-25T20:00",
            "seat": "5"
        }
        ]
    }
     */
    @RequestMapping(value = DO_BOOKING_MAPPING, method = RequestMethod.POST, headers = "Accept=application/json")
    public String bookTickets(@RequestBody TicketSaveRequest ticketSaveRequest) {
        //the generic error handler should catch every exception if the booking is unsuccessful
        bookingFacade.bookTickets(ticketViewTransformer.transform(ticketSaveRequest));
        return BOOKING_VIEW_NAME;
    }

    //Example query string: ticketlist?eventId=1&dateTime=1995-06-25T20:00Z
    @RequestMapping(value = GET_TICKETS_FOR_EVENT_MAPPING, method = RequestMethod.GET)
    public String getTicketsForEvent(Model model, @RequestParam("eventId") Long eventId,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        model.addAttribute("ticketsForEvent", bookingFacade.getPurchasedTicketsForEvent(eventId, dateTime));
        return TICKET_LIST_VIEW_NAME;
    }

    //Example query string: ticketlistpdf?eventId=1&dateTime=1995-06-25T20:00Z&userId=1&seats=1
    @RequestMapping(value = GET_TICKETS_FOR_EVENT_MAPPING_PDF, method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ModelAndView getTicketsForEventPdf(@RequestParam("eventId") Long eventId,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return new ModelAndView("pdfTicketView","ticketsForEvent",bookingFacade.getPurchasedTicketsForEvent(eventId, dateTime));
    }

}
