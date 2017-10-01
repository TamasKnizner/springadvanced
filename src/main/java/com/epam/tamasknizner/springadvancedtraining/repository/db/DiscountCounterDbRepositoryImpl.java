package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.domain.DiscountCounter;
import com.epam.tamasknizner.springadvancedtraining.repository.DiscountCounterRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Component
public class DiscountCounterDbRepositoryImpl
        extends DomainObjectDbRepository<DiscountCounter>
        implements DiscountCounterRepository {

    public DiscountCounterDbRepositoryImpl() {
        super("DiscountCounter", Arrays.asList("discountStrategyId", "userId", "counter"));
    }

    @Override
    protected DiscountCounter convertToEntityInternal(ResultSet resultSet) throws SQLException {
        long discountStrategyId = resultSet.getLong("discountStrategyId");
        Object userId = resultSet.getObject("userId");

        DiscountCounter discountCounter = new DiscountCounter(discountStrategyId, userId == null ? null : (long)userId);
        discountCounter.setCounter(resultSet.getInt("counter"));
        return discountCounter;
    }

    @Override
    protected void fillParamValues(MapSqlParameterSource paramValues, DiscountCounter discountCounter) {
        paramValues.addValue("discountStrategyId", discountCounter.getDiscountStrategyId());
        paramValues.addValue("userId", discountCounter.getUserId());
        paramValues.addValue("counter", discountCounter.getCounter());
    }
}
