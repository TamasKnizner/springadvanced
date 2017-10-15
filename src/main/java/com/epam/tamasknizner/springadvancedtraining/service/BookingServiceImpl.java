package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.exception.UserHasNotEnoughMoneyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.tamasknizner.springadvancedtraining.domain.*;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;
import com.epam.tamasknizner.springadvancedtraining.service.settings.BookingSettings;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingSettings bookingSettings;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final DiscountService discountService;
    private final Object bookingLocker = new Object();

    @Autowired
    public BookingServiceImpl(BookingSettings bookingSettings, TicketRepository ticketRepository, UserRepository userRepository, DiscountService discountService) {
        this.bookingSettings = bookingSettings;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.discountService = discountService;
    }

    @Transactional(readOnly = true)
    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull LinkedHashSet<Long> seats) {
        double sum = 0.0;
        double basePrice = event.getBasePrice();
        if (event.getRating() == EventRating.HIGH) {
            basePrice = getPriceWithExtraCharge(basePrice);
        }
        Map<Long, DiscountService.ApplicableDiscountInfo> discountMap = discountService.getDiscount(user, event, dateTime, seats);
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        for (long seat : seats) {
            double price = getPrice(basePrice, auditorium, seat);
            DiscountService.ApplicableDiscountInfo discount = discountMap.get(seat);
            if (discount != null) {
                price = getPriceWithDiscount(price, discount);
            }
            sum += price;
        }
        return sum;
    }

    @Transactional
    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        synchronized (bookingLocker) {
            Set<Ticket> alreadyBookedTickets = ticketRepository
                    .find(t -> tickets.stream()
                            .anyMatch(ticket -> Objects.equals(t.getEvent(), ticket.getEvent()) &&
                                    Objects.equals(t.getSeat(), ticket.getSeat()) &&
                                    Objects.equals(t.getDateTime(), ticket.getDateTime())));
            if (!alreadyBookedTickets.isEmpty()) {
                throw new RuntimeException(String.format("Tickets already booked: %s", alreadyBookedTickets));
            }
            for (Ticket ticket : tickets) {
                User user = loadUser(ticket.getUser().getId());
                if (user != null) {
                    if(userHasNotEnoughMoney(user.getUserAccount(), ticket.getEvent())) {
                        throw new UserHasNotEnoughMoneyException();
                    }
                    user.getTickets().add(ticket);
                    userRepository.update(user);
                }
                ticketRepository.add(ticket);
            }
        }
    }

    @Transactional(readOnly = true)
    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketRepository.find(t -> event.equals(t.getEvent()) && dateTime.equals(t.getDateTime()));
    }

    private User loadUser(final Long userId) {
        return userRepository.findById(userId);
    }

    private boolean userHasNotEnoughMoney(final UserAccount userAccount, final Event event) {
        return event.getBasePrice() > userAccount.getPrepaidAmount();
    }

    private double getPriceWithExtraCharge(final double basePrice) {
        return (100.0 + bookingSettings.getHighRatedExtraChargePercent()) / 100.0 * basePrice;
    }

    private double getPriceWithDiscount(final double price, final DiscountService.ApplicableDiscountInfo discount) {
        return (100.0 - discount.getDiscountPercent()) / 100.0 * price;
    }

    private double getPrice(final double basePrice, final Auditorium auditorium, final long seat) {
        return auditorium.isVipSeat(seat)
                ? (100.0 + bookingSettings.getVipExtraChargePercent()) / 100.0 * basePrice
                : basePrice;
    }
}
