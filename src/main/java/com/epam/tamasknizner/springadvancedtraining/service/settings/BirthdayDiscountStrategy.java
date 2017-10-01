package com.epam.tamasknizner.springadvancedtraining.service.settings;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BirthdayDiscountStrategy extends AbstractDiscountStrategy implements DiscountStrategy {
    private final int daysDeviation;
    private final float discountPercent;

    public BirthdayDiscountStrategy(int daysDeviation, float discountPercent) {
        if (daysDeviation < 0 || daysDeviation > 364) {
            throw new IllegalArgumentException("daysDeviation");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("discountPercent");
        }
        this.daysDeviation = daysDeviation;
        this.discountPercent = discountPercent;
    }

    @Override
    public float getDiscount(
            @Nullable User user,
            @Nonnull Event event,
            @Nonnull LocalDateTime airDateTime,
            int numberOfTickets,
            int ticketNumberInOrder) {

        if (user == null || user.getBirthday() == null) {
            return 0;
        }

        LocalDate upperBound = airDateTime.plusDays(daysDeviation + 1).toLocalDate();
        LocalDate lowerBound = airDateTime.plusDays(-daysDeviation).toLocalDate();

        LocalDate birthday = LocalDate.of(airDateTime.getYear(),
                user.getBirthday().getMonth(), user.getBirthday().getDayOfMonth());

        return birthday.isBefore(upperBound) &&
                (birthday.isAfter(lowerBound) || birthday.isEqual(lowerBound))
                ? discountPercent
                : 0;
    }

    public int getDaysDeviation() {
        return daysDeviation;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthdayDiscountStrategy that = (BirthdayDiscountStrategy) o;
        return daysDeviation == that.daysDeviation &&
                Float.compare(that.discountPercent, discountPercent) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(daysDeviation, discountPercent);
    }
}
