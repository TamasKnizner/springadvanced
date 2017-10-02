package com.epam.tamasknizner.springadvancedtraining.transformer;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.domain.request.TicketSaveRequest;
import com.epam.tamasknizner.springadvancedtraining.domain.request.TicketView;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tamas_Knizner
 */
@Component
public class TicketViewTransformer {

    public Set<Ticket> transform(final TicketSaveRequest ticketSaveRequest) {
        return ticketSaveRequest.getTickets().stream().map(this::transform).collect(Collectors.toSet());
    }

    private Ticket transform(final TicketView ticketView) {
        User user = new User();
        user.setId(ticketView.getUserId());
        Event event = new Event();
        event.setId(ticketView.getEventId());
        return new Ticket(user, event, ticketView.getDateTime(), ticketView.getSeat());
    }

}
