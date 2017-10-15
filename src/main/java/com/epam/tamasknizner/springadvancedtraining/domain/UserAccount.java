package com.epam.tamasknizner.springadvancedtraining.domain;

/**
 *  An entity that should store the amount of prepaid money user has in the system, which should be used during booking procedure.
 */
public class UserAccount extends DomainObject {

    private double prepaidAmount;

    public UserAccount() {
    }

    public UserAccount(final double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        return Double.compare(that.prepaidAmount, prepaidAmount) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(prepaidAmount);
        return (int) (temp ^ (temp >>> 32));
    }

    public double getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(final double prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }
}
