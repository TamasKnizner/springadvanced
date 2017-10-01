package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.EventCounter;
import com.epam.tamasknizner.springadvancedtraining.repository.EventCounterRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EventCounterDbRepositoryImpl extends DbRepository<EventCounter, Long> implements EventCounterRepository {

    public EventCounterDbRepositoryImpl() {
        super("EventCounter", "eventId",
                EventCounter::getEventId, Arrays.asList("eventId", "pricesQueried", "booked"));
    }

    @Override
    protected MapSqlParameterSource getParamValues(EventCounter eventCounter, boolean forInsert) {
        MapSqlParameterSource paramValues = new MapSqlParameterSource();
        paramValues.addValue("eventId", eventCounter.getEventId());
        paramValues.addValue("pricesQueried", eventCounter.getPricesQueried());
        paramValues.addValue("booked", eventCounter.getBooked());
        return paramValues;
    }

    @Override
    protected EventCounter convertToEntity(ResultSet resultSet) throws SQLException {
        EventCounter eventCounter = new EventCounter(resultSet.getLong("eventId"));
        eventCounter.setPricesQueried(resultSet.getInt("pricesQueried"));
        eventCounter.setBooked(resultSet.getInt("booked"));
        return eventCounter;
    }
}
