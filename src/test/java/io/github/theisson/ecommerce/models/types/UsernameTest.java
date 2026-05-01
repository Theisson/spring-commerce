package io.github.theisson.ecommerce.models.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    @DisplayName("Deve criar Username válido com exatamente 3 caracteres")
    void shouldCreateUsernameWithMinLength() {
        Username username = new Username("abc");
        assertEquals("abc", username.getValue());
    }

    @Test
    @DisplayName("Deve criar Username válido com exatamente 20 caracteres")
    void shouldCreateUsernameWithMaxLength() {
        String value = "a".repeat(20);
        Username username = new Username(value);
        assertEquals(value, username.getValue());
    }

    @Test
    @DisplayName("Deve criar Username alfanumérico com letras e números")
    void shouldCreateAlphanumericUsername() {
        Username username = new Username("user123");
        assertEquals("user123", username.getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção para Username nulo")
    void shouldThrowForNullUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Username(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username vazio")
    void shouldThrowForEmptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Username(""));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username com 2 caracteres (abaixo do mínimo)")
    void shouldThrowForTooShortUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Username("ab"));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username com 21 caracteres (acima do máximo)")
    void shouldThrowForTooLongUsername() {
        assertThrows(IllegalArgumentException.class, () -> new Username("a".repeat(21)));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username com caracteres especiais")
    void shouldThrowForUsernameWithSpecialCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new Username("user!@#"));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username com espaços")
    void shouldThrowForUsernameWithSpaces() {
        assertThrows(IllegalArgumentException.class, () -> new Username("user name"));
    }

    @Test
    @DisplayName("Deve lançar exceção para Username com underscore")
    void shouldThrowForUsernameWithUnderscore() {
        assertThrows(IllegalArgumentException.class, () -> new Username("user_name"));
    }
}
