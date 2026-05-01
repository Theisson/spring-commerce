package io.github.theisson.ecommerce.models.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("Deve criar Email válido")
    void shouldCreateValidEmail() {
        Email email = new Email("user@example.com");
        assertEquals("user@example.com", email.getValue());
    }

    @Test
    @DisplayName("Deve aceitar email com subdomínio")
    void shouldAcceptEmailWithSubdomain() {
        Email email = new Email("user@mail.example.com");
        assertEquals("user@mail.example.com", email.getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção para email nulo")
    void shouldThrowForNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para email vazio")
    void shouldThrowForEmptyEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
    }

    @Test
    @DisplayName("Deve lançar exceção para email com menos de 6 caracteres")
    void shouldThrowForTooShortEmail() {
        assertThrows(IllegalArgumentException.class, () -> new Email("a@b.c"));
    }

    @Test
    @DisplayName("Deve lançar exceção para email com mais de 255 caracteres")
    void shouldThrowForTooLongEmail() {
        // 247 + "@test.com" (9) = 256 chars > 255
        String longLocal = "a".repeat(247);
        assertThrows(IllegalArgumentException.class, () -> new Email(longLocal + "@test.com"));
    }

    @Test
    @DisplayName("Deve lançar exceção para email sem @")
    void shouldThrowForEmailWithoutAtSign() {
        assertThrows(IllegalArgumentException.class, () -> new Email("invalidemail.com"));
    }

    @Test
    @DisplayName("Deve lançar exceção para email sem domínio após @")
    void shouldThrowForEmailWithoutDomain() {
        assertThrows(IllegalArgumentException.class, () -> new Email("user@"));
    }

    @Test
    @DisplayName("Deve lançar exceção para email sem extensão de domínio")
    void shouldThrowForEmailWithoutDomainExtension() {
        assertThrows(IllegalArgumentException.class, () -> new Email("user@domain"));
    }
}
