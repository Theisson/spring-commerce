package io.github.theisson.ecommerce.models.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CpfTest {

    @Test
    @DisplayName("Deve criar CPF válido")
    void shouldCreateValidCpf() {
        Cpf cpf = new Cpf("52998224725");
        assertEquals("52998224725", cpf.getValue());
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF nulo")
    void shouldThrowForNullCpf() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com menos de 11 dígitos")
    void shouldThrowForShortCpf() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("1234567890"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com mais de 11 dígitos")
    void shouldThrowForLongCpf() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("123456789012"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com letras")
    void shouldThrowForCpfWithLetters() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("5299822472A"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com todos os dígitos iguais")
    void shouldThrowForCpfWithAllSameDigits() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("00000000000"));
        assertThrows(IllegalArgumentException.class, () -> new Cpf("11111111111"));
        assertThrows(IllegalArgumentException.class, () -> new Cpf("99999999999"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com dígito verificador inválido")
    void shouldThrowForInvalidCheckDigit() {
        // CPF válido seria 52998224725 — alteramos o último dígito
        assertThrows(IllegalArgumentException.class, () -> new Cpf("52998224726"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF formatado com pontuação")
    void shouldThrowForFormattedCpf() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("529.982.247-25"));
    }
}
