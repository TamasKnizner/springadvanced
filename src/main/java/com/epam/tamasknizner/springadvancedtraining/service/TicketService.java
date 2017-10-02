package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;

/**
 * @author Tamas_Knizner
 */
public interface TicketService {

    Ticket getTicketById(Long id);

}
