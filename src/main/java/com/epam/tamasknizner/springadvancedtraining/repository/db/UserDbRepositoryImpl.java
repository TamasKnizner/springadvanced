package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.domain.UserLuckyEventInfo;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDbRepositoryImpl extends DomainObjectDbRepository<User> implements UserRepository {

    public UserDbRepositoryImpl() {
        super("User", Arrays.asList("firstName", "lastName", "email", "birthday"));
    }

    @Override
    protected User convertToEntityInternal(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(deserializeDate(resultSet.getString("birthday")));
        return user;
    }

    @Override
    protected void fillParamValues(MapSqlParameterSource paramValues, User user) {
        paramValues.addValue("firstName", user.getFirstName());
        paramValues.addValue("lastName", user.getLastName());
        paramValues.addValue("email", user.getEmail());
        paramValues.addValue("birthday", serializeDate(user.getBirthday()));
    }

    @Override
    protected Collection<User> fetchReferencedEntities(Collection<User> users) {
        Map<Long, User> usersMap = mapById(users);

        jdbcTemplate.query("SELECT userId, eventId, airDate FROM UserLuckyEventInfo", rs -> {
            long userId = rs.getLong("userId");
            long eventId = rs.getLong("eventId");
            LocalDateTime airDate = deserializeDateTime(rs.getString("airDate"));

            User user = usersMap.get(userId);
            user.addLuckyEvent(new UserLuckyEventInfo(userId, eventId, airDate));
        });

        return super.fetchReferencedEntities(users);
    }

    @Override
    protected void addReferencedEntities(User user) {

        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("userId", user.getId());
        for (UserLuckyEventInfo luckyEventInfo : user.getLuckyEvents()) {
            paramValues.put("eventId", luckyEventInfo.getEventId());
            paramValues.put("airDate", serializeDateTime(luckyEventInfo.getAirDate()));
            int rows = namedParameterJdbcTemplate.update("INSERT INTO UserLuckyEventInfo(userId, eventId, airDate) " +
                    "VALUES (:userId, :eventId, :airDate)", paramValues);
            if (rows != 1) {
                throw new IllegalStateException();
            }
        }

        super.addReferencedEntities(user);
    }

    @Override
    protected void removeReferencedEntities(User user) {
        Map<String, Object> paramValues = new HashMap<>();
        paramValues.put("userId", user.getId());
        namedParameterJdbcTemplate.update("DELETE FROM UserLuckyEventInfo WHERE userId = :userId", paramValues);

        super.removeReferencedEntities(user);
    }
}
