package com.epam.tamasknizner.springadvancedtraining.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.epam.tamasknizner.springadvancedtraining.domain.*;
import com.epam.tamasknizner.springadvancedtraining.repository.TicketRepository;
import com.epam.tamasknizner.springadvancedtraining.repository.UserRepository;
import com.epam.tamasknizner.springadvancedtraining.service.settings.BookingSettings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull LinkedHashSet<Long> seats) {
        double sum = 0.0;
        double basePrice = event.getBasePrice();
        if (event.getRating() == EventRating.HIGH) {
            basePrice = (100.0 + bookingSettings.getHighRatedExtraChargePercent()) / 100.0 * basePrice;
        }
        Map<Long, DiscountService.ApplicableDiscountInfo> discountMap =
                discountService.getDiscount(user, event, dateTime, seats);

        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        for (long seat : seats) {
            double price = auditorium.isVipSeat(seat)
                    ? (100.0 + bookingSettings.getVipExtraChargePercent()) / 100.0 * basePrice
                    : basePrice;

            DiscountService.ApplicableDiscountInfo discount = discountMap.get(seat);
            if (discount != null) {
                price = (100.0 - discount.getDiscountPercent()) / 100.0 * price;
            }

            sum += price;
        }

        return sum;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        Set<Ticket> incorrectTickets = tickets.stream()
                // for simplicity let's not do all the possible checks against the tickets
                .filter(ticket -> !ticket.getEvent().getAirDates().contains(ticket.getDateTime()) ||
                        ticket.getSeat() > ticket.getEvent().getAuditoriums().get(ticket.getDateTime()).getNumberOfSeats())
                .collect(Collectors.toSet());

        if (!incorrectTickets.isEmpty()) {
            throw new IllegalArgumentException(String.format("Incorrect tickets: %s", incorrectTickets));
        }

        // Sure it wouldn't be that simple in a real-world application,
        // but for the purpose of correctness of a sample app a simple lock should suffice.
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
                User user = ticket.getUser();
                if (user != null) {
                    user.getTickets().add(ticket);
                    if (user.getId() != null) {
                        userRepository.update(user);
                    }
                }
                ticketRepository.add(ticket);
            }
        }
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketRepository.find(t -> event.equals(t.getEvent()) && dateTime.equals(t.getDateTime()));
    }

}
