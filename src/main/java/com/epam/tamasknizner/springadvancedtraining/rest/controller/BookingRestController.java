package com.epam.tamasknizner.springadvancedtraining.rest.controller;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.domain.request.TicketSaveRequest;
import com.epam.tamasknizner.springadvancedtraining.facade.BookingFacade;
import com.epam.tamasknizner.springadvancedtraining.transformer.TicketViewTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
public class BookingRestController {

    private static final String GET_TICKET_PRICE_MAPPING = "/api/booking/price";
    private static final String DO_BOOKING_MAPPING = "/api/booking/book";
    private static final String GET_TICKETS_FOR_EVENT_MAPPING = "/api/booking/ticketlist";

    private final BookingFacade bookingFacade;
    private final TicketViewTransformer ticketViewTransformer;

    @Autowired
    public BookingRestController(final BookingFacade bookingFacade, final TicketViewTransformer ticketViewTransformer) {
        this.bookingFacade = bookingFacade;
        this.ticketViewTransformer = ticketViewTransformer;
    }

    //Example query string: price?eventId=40&dateTime=1995-06-25T20:00Z&userId=50&seats=1
    @RequestMapping(value = GET_TICKET_PRICE_MAPPING, method = RequestMethod.GET)
    public ResponseEntity<Double> getTicketPrice(@RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime, @RequestParam("seats") String seats) {
        Double ticketsPrice = bookingFacade.getTicketsPrice(eventId, userId, dateTime, seats);
        return new ResponseEntity<>(ticketsPrice, HttpStatus.OK);
    }

    /*
    Example payload:
    {
    "tickets": [
        {
            "eventId": "40",
            "userId": "50",
            "dateTime": "1995-06-25T20:00",
            "seat": "5"
        }
        ]
    }
     */
    @RequestMapping(value = DO_BOOKING_MAPPING, method = RequestMethod.POST)
    public ResponseEntity bookTickets(@RequestBody TicketSaveRequest ticketSaveRequest) {
        bookingFacade.bookTickets(ticketViewTransformer.transform(ticketSaveRequest));
        return new ResponseEntity(HttpStatus.OK);
    }

    //Example query string: ticketlist?eventId=40&dateTime=1995-06-25T20:00Z
    //http://localhost:8080/springadvancedtraining/api/booking/ticketlist.pdf?eventId=40&dateTime=1995-06-25T20:00Z -> works with .json too!
    @RequestMapping(value = GET_TICKETS_FOR_EVENT_MAPPING, method = RequestMethod.GET, produces = {"application/json", "application/pdf"})
    @ResponseBody
    public Set<Ticket> getTicketsForEvent(@RequestParam("eventId") Long eventId,
            @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return bookingFacade.getPurchasedTicketsForEvent(eventId, dateTime);
    }

}
