package com.epam.tamasknizner.springadvancedtraining.service.settings;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Objects;

public class NthTicketDiscountStrategy extends AbstractDiscountStrategy implements DiscountStrategy {

    private final int n;
    private final float discountPercent;
    private final TicketRepository ticketRepository;

    public NthTicketDiscountStrategy(int n, float discountPercent, @Nonnull TicketRepository ticketRepository) {
        this.n = n;
        this.discountPercent = discountPercent;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public float getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, int numberOfTickets, int ticketNumberInOrder) {
        int totalTicketNumber = ticketNumberInOrder;
        if (user != null) {
            totalTicketNumber += getPurchasedTicketsCountByUser(user);
        }
        return totalTicketNumber % n == 0 ? discountPercent : 0.0f;
    }

    /**
     * Returns number of tickets purchased by user.
     */
    private int getPurchasedTicketsCountByUser(@Nonnull User user) {
        return ticketRepository.find(t -> user.equals(t.getUser()) ||
                user.getId() != null && t.getUser() != null && user.getId().equals(t.getUser().getId()))
                .size();
    }

    public int getN() {
        return n;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthTicketDiscountStrategy that = (NthTicketDiscountStrategy) o;
        return n == that.n &&
                Float.compare(that.discountPercent, discountPercent) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n, discountPercent);
    }
}
