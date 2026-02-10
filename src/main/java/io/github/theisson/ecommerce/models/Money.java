package io.github.theisson.ecommerce.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public final class Money implements Serializable {
    private final long amount;

    public Money(long amount) {
        this.amount = amount;
    }

    public Money(BigDecimal amount) {
        this.amount = amount.multiply(new BigDecimal("100")).longValueExact();
    }

    public static Money zero() {
        return new Money(0);
    }

    public long getAmount() {
        return amount;
    }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        return new Money(this.amount - other.amount);
    }

    public Money multiply(int multiplier) {
        return new Money(this.amount * multiplier);
    }

    public boolean isPositive() {
        return this.amount >= 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount >= other.amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    
    @Override
    public String toString() {
        return BigDecimal.valueOf(amount, 2).toString();
    }
}
