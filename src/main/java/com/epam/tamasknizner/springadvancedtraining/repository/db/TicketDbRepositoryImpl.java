package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.Ticket;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.EventRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TicketDbRepositoryImpl extends DomainObjectDbRepository<Ticket> implements TicketRepository {

    private UserRepository userRepository;
    private EventRepository eventRepository;

    public TicketDbRepositoryImpl() {
        super("Ticket", Arrays.asList("userId", "eventId", "dateTime", "seat"));
    }

    @Override
    protected Ticket convertToEntityInternal(ResultSet resultSet) throws SQLException {
        Object userId = resultSet.getObject("userId");
        User user = userId == null ? null : userRepository.findById((long) userId);
        Event event = eventRepository.findById(resultSet.getLong("eventId"));
        LocalDateTime dateTime = deserializeDateTime(resultSet.getString("dateTime"));
        long seat = resultSet.getLong("seat");

        return new Ticket(user, event, dateTime, seat);
    }

    @Override
    protected void fillParamValues(MapSqlParameterSource paramValues, Ticket ticket) {
        paramValues.addValue("userId", ticket.getUser() == null ? null : ticket.getUser().getId());
        paramValues.addValue("eventId", ticket.getEvent().getId());
        paramValues.addValue("dateTime", serializeDateTime(ticket.getDateTime()));
        paramValues.addValue("seat", ticket.getSeat());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
