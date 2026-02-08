package io.github.theisson.ecommerce.converters;

import java.math.BigDecimal;
import io.github.theisson.ecommerce.models.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return BigDecimal.valueOf(money.getAmount(), 2);
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbData) {
        return new Money(dbData.movePointRight(2).longValueExact());
    }
}
