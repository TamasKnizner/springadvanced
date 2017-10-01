package com.epam.tamasknizner.springadvancedtraining.repository.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import com.epam.tamasknizner.springadvancedtraining.repository.DiscountStrategyRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import com.epam.tamasknizner.springadvancedtraining.service.settings.AbstractDiscountStrategy;
import com.epam.tamasknizner.springadvancedtraining.service.settings.BirthdayDiscountStrategy;
import com.epam.tamasknizner.springadvancedtraining.service.settings.NthTicketDiscountStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DiscountStrategyDbRepositoryImpl
        extends DomainObjectDbRepository<AbstractDiscountStrategy>
        implements DiscountStrategyRepository {

    private TicketRepository ticketRepository;

    public DiscountStrategyDbRepositoryImpl() {
        super("DiscountStrategy", Arrays.asList("discriminator", "discountPercent", "daysDeviation", "n"));
    }

    @Override
    protected AbstractDiscountStrategy convertToEntityInternal(ResultSet resultSet) throws SQLException {
        String discriminator = resultSet.getString("discriminator");
        float discountPercent = resultSet.getFloat("discountPercent");
        AbstractDiscountStrategy discountStrategy;
        if ("B".equalsIgnoreCase(discriminator)) {
            int daysDeviation = resultSet.getInt("daysDeviation");
            discountStrategy = new BirthdayDiscountStrategy(daysDeviation, discountPercent);
        } else if ("N".equalsIgnoreCase(discriminator)) {
            int n = resultSet.getInt("n");
            discountStrategy = new NthTicketDiscountStrategy(n, discountPercent, ticketRepository);
        } else {
            throw new UnsupportedOperationException(discriminator);
        }
        return discountStrategy;
    }

    @Override
    protected void fillParamValues(MapSqlParameterSource paramValues, AbstractDiscountStrategy discountStrategy) {
        String discriminator;
        float discountPercent;
        if (discountStrategy instanceof BirthdayDiscountStrategy) {
            discriminator = "B";
            BirthdayDiscountStrategy birthdayDiscountStrategy = (BirthdayDiscountStrategy) discountStrategy;
            discountPercent = birthdayDiscountStrategy.getDiscountPercent();
            paramValues.addValue("daysDeviation", birthdayDiscountStrategy.getDaysDeviation());
        } else if (discountStrategy instanceof NthTicketDiscountStrategy) {
            NthTicketDiscountStrategy nthTicketDiscountStrategy = (NthTicketDiscountStrategy) discountStrategy;
            discriminator = "N";
            discountPercent = nthTicketDiscountStrategy.getDiscountPercent();
            paramValues.addValue("n", nthTicketDiscountStrategy.getN());
        } else {
            throw new UnsupportedOperationException(discountStrategy.getClass().getName());
        }

        paramValues.addValue("discriminator", discriminator);
        paramValues.addValue("discountPercent", discountPercent);
    }

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    protected void removeReferencedEntities(AbstractDiscountStrategy abstractDiscountStrategy) {
        throw new UnsupportedOperationException("discounts removal hasn't been implemented yet");
    }
}
