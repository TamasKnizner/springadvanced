package com.epam.tamasknizner.springadvancedtraining.domain.request;

import java.util.Set;

/**
 * @author Tamas_Knizner
 */
public class TicketSaveRequest {

    private Set<TicketView> tickets;

    public Set<TicketView> getTickets() {
        return tickets;
    }

    public void setTickets(Set<TicketView> tickets) {
        this.tickets = tickets;
    }

}
