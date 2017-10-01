package com.epam.tamasknizner.springadvancedtraining.repository.mapBased;

import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketRepositoryImpl extends MapBasedIdentityRepository<Ticket> implements TicketRepository {

}
