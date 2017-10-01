package com.epam.tamasknizner.springadvancedtraining.service.settings;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface DiscountStrategy {
    float getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, int numberOfTickets, int ticketNumberInOrder);
}
