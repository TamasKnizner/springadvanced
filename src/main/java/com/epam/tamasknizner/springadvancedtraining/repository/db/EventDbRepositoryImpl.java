package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;
import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.EventRating;
import com.epam.tamasknizner.springadvancedtraining.repository.AuditoriumRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.EventRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventDbRepositoryImpl
        extends DomainObjectDbRepository<Event>
        implements EventRepository {

    private AuditoriumRepository auditoriumRepository;

    protected EventDbRepositoryImpl() {
        super("Event", Arrays.asList("name", "basePrice", "rating"));
    }

    @Override
    protected Event convertToEntityInternal(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setName(resultSet.getString("name"));
        event.setBasePrice(resultSet.getDouble("basePrice"));
        event.setRating(Enum.valueOf(EventRating.class, resultSet.getString("rating")));
        return event;
    }

    @Override
    protected void fillParamValues(MapSqlParameterSource paramValues, Event event) {
        paramValues.addValue("name", event.getName());
        paramValues.addValue("basePrice", event.getBasePrice());
        paramValues.addValue("rating", event.getRating().toString());
    }

    @Override
    protected Collection<Event> fetchReferencedEntities(Collection<Event> events) {
        Map<Long, Event> eventsMap = mapById(events);

        jdbcTemplate.query("SELECT eventId, auditoriumName, airDate FROM EventAuditorium", rs -> {
            long eventId = rs.getLong("eventId");
            Event event = eventsMap.get(eventId);
            if (event == null) {
                return;
            }
            String auditoriumName = rs.getString("auditoriumName");
            Auditorium auditorium = auditoriumRepository.findById(auditoriumName);
            LocalDateTime airDate = deserializeDateTime(rs.getString("airDate"));
            event.addAirDateTime(airDate, auditorium);
        });

        return super.fetchReferencedEntities(events);
    }

    @Override
    protected void addReferencedEntities(Event event) {
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("eventId", event.getId());
        for (Map.Entry<LocalDateTime, Auditorium> entry : event.getAuditoriums().entrySet()) {
            paramValues.put("auditoriumName", entry.getValue().getName());
            paramValues.put("airDate", serializeDateTime(entry.getKey()));
            int rows = namedParameterJdbcTemplate.update(
                    "INSERT INTO EventAuditorium(eventId, auditoriumName, airDate) VALUES (:eventId, :auditoriumName, :airDate)",
                    paramValues);
            if (rows != 1) {
                throw new IllegalStateException("rows");
            }
        }

        super.addReferencedEntities(event);
    }

    @Override
    protected void removeReferencedEntities(Event event) {
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("eventId", event.getId());

        namedParameterJdbcTemplate.update("DELETE FROM EventAuditorium WHERE eventId = :eventId", paramValues);
        namedParameterJdbcTemplate.update("DELETE FROM EventCounter WHERE eventId = :eventId", paramValues);

        super.removeReferencedEntities(event);
    }

    @Autowired
    public void setAuditoriumRepository(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }
}
