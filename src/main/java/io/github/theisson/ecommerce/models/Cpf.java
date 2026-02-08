package io.github.theisson.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Cpf {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private final String cpf;

    protected Cpf() {
        this.cpf = null;
    }

    public Cpf(String cpf) {
        validate(cpf);
        this.cpf = cpf;
    }

    private void validate(String cpf) {
        if(cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if(cpf.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido: números repetidos.");
        }

        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum1 += digit * (10 - i);
            sum2 += digit * (11 - i);
        }

        int remainder1 = sum1 % 11;
        int checkDigit1 = (remainder1 < 2) ? 0 : 11 - remainder1;

        if (checkDigit1 != Character.getNumericValue(cpf.charAt(9))) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        sum2 += checkDigit1 * 2;
        int remainder2 = sum2 % 11;
        int checkDigit2 = (remainder2 < 2) ? 0 : 11 - remainder2;

        if (checkDigit2 != Character.getNumericValue(cpf.charAt(10))) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    public String getValue() {
        return cpf;
    }
}
