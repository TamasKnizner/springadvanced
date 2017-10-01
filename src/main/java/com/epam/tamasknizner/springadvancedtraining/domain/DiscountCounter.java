package com.epam.tamasknizner.springadvancedtraining.domain;

public class DiscountCounter extends DomainObject {
    private long discountStrategyId;
    private Long userId;
    private int counter;

    public DiscountCounter(long discountStrategyId, Long userId) {
        this.discountStrategyId = discountStrategyId;
        this.userId = userId;
    }

    public long getDiscountStrategyId() {
        return discountStrategyId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
