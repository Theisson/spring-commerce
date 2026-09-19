package io.github.theisson.ecommerce.models.types;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Money implements Serializable {
    
    private final long amount;

    public Money(long amount) {
        this.amount = amount;
    }

    public Money(BigDecimal amount) {
        this.amount = amount.movePointRight(2).longValueExact();
    }

    public static Money zero() {
        return new Money(0);
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
        return this.amount > 0;
    }

    public boolean isNonNegative() {
        return this.amount >= 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return this.amount >= other.amount;
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(amount, 2);
    }

    public long getAmount() { return amount; }
    
    @Override
    public String toString() {
        return BigDecimal.valueOf(amount, 2).toString();
    }
}
