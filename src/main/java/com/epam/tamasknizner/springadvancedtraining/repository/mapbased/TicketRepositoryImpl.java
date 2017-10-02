package com.epam.tamasknizner.springadvancedtraining.repository.mapbased;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

public class TicketRepositoryImpl extends MapBasedIdentityRepository<Ticket> implements TicketRepository {

    public TicketRepositoryImpl() {
    }

    public TicketRepositoryImpl(Collection<Ticket> tickets) {
        super(tickets);
    }

}
