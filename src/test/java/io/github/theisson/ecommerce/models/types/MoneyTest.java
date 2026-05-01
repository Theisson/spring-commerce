package io.github.theisson.ecommerce.models.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    @DisplayName("Deve criar Money a partir de long em centavos")
    void shouldCreateFromLong() {
        Money money = new Money(1050L);
        assertEquals(1050L, money.getAmount());
    }

    @Test
    @DisplayName("Deve criar Money a partir de BigDecimal")
    void shouldCreateFromBigDecimal() {
        Money money = new Money(new BigDecimal("10.50"));
        assertEquals(1050L, money.getAmount());
    }

    @Test
    @DisplayName("Deve criar Money zero via factory method")
    void shouldCreateZero() {
        Money zero = Money.zero();
        assertEquals(0L, zero.getAmount());
        assertFalse(zero.isPositive());
        assertTrue(zero.isNonNegative());
    }

    @Test
    @DisplayName("Deve somar dois valores corretamente")
    void shouldAddTwoAmounts() {
        Money a = new Money(new BigDecimal("10.00"));
        Money b = new Money(new BigDecimal("5.50"));
        assertEquals(new Money(new BigDecimal("15.50")), a.add(b));
    }

    @Test
    @DisplayName("Deve subtrair dois valores corretamente")
    void shouldSubtractTwoAmounts() {
        Money a = new Money(new BigDecimal("10.00"));
        Money b = new Money(new BigDecimal("4.00"));
        assertEquals(new Money(new BigDecimal("6.00")), a.subtract(b));
    }

    @Test
    @DisplayName("Deve permitir resultado negativo na subtração")
    void subtractShouldAllowNegativeResult() {
        Money a = new Money(new BigDecimal("5.00"));
        Money b = new Money(new BigDecimal("10.00"));
        Money result = a.subtract(b);
        assertFalse(result.isNonNegative());
        assertFalse(result.isPositive());
    }

    @Test
    @DisplayName("Deve multiplicar por inteiro corretamente")
    void shouldMultiplyByInteger() {
        Money price = new Money(new BigDecimal("25.00"));
        assertEquals(new Money(new BigDecimal("75.00")), price.multiply(3));
    }

    @Test
    @DisplayName("isPositive deve retornar false para zero")
    void isPositiveShouldReturnFalseForZero() {
        assertFalse(Money.zero().isPositive());
    }

    @Test
    @DisplayName("isPositive deve retornar true para valor positivo")
    void isPositiveShouldReturnTrueForPositiveValue() {
        assertTrue(new Money(1L).isPositive());
    }

    @Test
    @DisplayName("isNonNegative deve retornar true para zero")
    void isNonNegativeShouldReturnTrueForZero() {
        assertTrue(Money.zero().isNonNegative());
    }

    @Test
    @DisplayName("isNonNegative deve retornar false para valor negativo")
    void isNonNegativeShouldReturnFalseForNegativeValue() {
        assertFalse(new Money(-1L).isNonNegative());
    }

    @Test
    @DisplayName("isGreaterThanOrEqual deve comparar corretamente")
    void shouldCompareAmountsCorrectly() {
        Money ten = new Money(new BigDecimal("10.00"));
        Money five = new Money(new BigDecimal("5.00"));
        assertTrue(ten.isGreaterThanOrEqual(five));
        assertTrue(ten.isGreaterThanOrEqual(ten));
        assertFalse(five.isGreaterThanOrEqual(ten));
    }

    @Test
    @DisplayName("Deve converter para BigDecimal com escala correta")
    void shouldConvertToBigDecimalWithCorrectScale() {
        Money money = new Money(new BigDecimal("99.99"));
        assertEquals(new BigDecimal("99.99"), money.toBigDecimal());
    }

    @Test
    @DisplayName("Deve respeitar igualdade por valor (equals e hashCode)")
    void shouldBeEqualByValue() {
        Money a = new Money(500L);
        Money b = new Money(500L);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    @DisplayName("Deve ser diferente para valores distintos")
    void shouldNotBeEqualForDifferentValues() {
        assertNotEquals(new Money(100L), new Money(200L));
    }

    @Test
    @DisplayName("Deve lançar ArithmeticException para BigDecimal com mais de 2 casas decimais")
    void shouldThrowForBigDecimalWithTooManyDecimalPlaces() {
        assertThrows(ArithmeticException.class, () -> new Money(new BigDecimal("10.001")));
    }
}
