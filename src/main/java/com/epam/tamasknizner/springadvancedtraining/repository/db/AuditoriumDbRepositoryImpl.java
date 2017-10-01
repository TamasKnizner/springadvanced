package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.epam.tamasknizner.springadvancedtraining.domain.Auditorium;
import com.epam.tamasknizner.springadvancedtraining.repository.AuditoriumRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class AuditoriumDbRepositoryImpl extends DbRepository<Auditorium, String> implements AuditoriumRepository {

    public AuditoriumDbRepositoryImpl() {
        super("Auditorium", "name",
                Auditorium::getName,
                Arrays.asList("name", "numberOfSeats", "vipSeats"));
    }

    @Override
    protected MapSqlParameterSource getParamValues(Auditorium auditorium, boolean forInsert) {
        MapSqlParameterSource paramValues = new MapSqlParameterSource();

        String vipSeatsStr = String.join(",",
                auditorium.getVipSeats().stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
        paramValues.addValue("name", auditorium.getName());
        paramValues.addValue("numberOfSeats", auditorium.getNumberOfSeats());
        paramValues.addValue("vipSeats", vipSeatsStr);

        return paramValues;
    }

    @Override
    protected Auditorium convertToEntity(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        long numberOfSeats = resultSet.getLong("numberOfSeats");
        String vipSeatsStr = resultSet.getString("vipSeats");
        return new Auditorium(name, numberOfSeats, StringUtils.isEmpty(vipSeatsStr)
                ? Collections.emptySet()
                : Arrays.stream(vipSeatsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet()));
    }
}
