package com.epam.tamasknizner.springadvancedtraining.service;

import com.epam.tamasknizner.springadvancedtraining.domain.Event;
import com.epam.tamasknizner.springadvancedtraining.domain.User;
import com.epam.tamasknizner.springadvancedtraining.service.settings.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author Yuriy_Tkach
 */
public interface DiscountService {

    /**
     * Getting discount based on some rules for user that buys some number of
     * tickets for the specific date time of the event
     *
     * @param user        User that buys tickets. Can be <code>null</code>
     * @param event       Event that tickets are bought for
     * @param airDateTime The date and time event will be aired
     * @param seats       An ordered set of seats that the user wants to purchase.
     * @return Discounts for every seat. If a purchased seat does not deserve a discount, it is not present on the map.
     */
    Map<Long, ApplicableDiscountInfo> getDiscount(
            @Nullable User user,
            @Nonnull Event event,
            @Nonnull LocalDateTime airDateTime,
            LinkedHashSet<Long> seats
    );

    class ApplicableDiscountInfo {
        private final DiscountStrategy discountStrategy;
        private final float discountPercent;

        public ApplicableDiscountInfo(DiscountStrategy discountStrategy, float discountPercent) {
            this.discountStrategy = discountStrategy;
            this.discountPercent = discountPercent;
        }

        public DiscountStrategy getDiscountStrategy() {
            return discountStrategy;
        }

        public float getDiscountPercent() {
            return discountPercent;
        }
    }
}
