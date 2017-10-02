package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tamas_Knizner
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id);
    }
}
