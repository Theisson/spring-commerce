package io.github.theisson.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class PhoneNumber {

    @Column(name = "phone_number", nullable = false, length = 11)
    private final String phoneNumber;

    protected PhoneNumber() {
        this.phoneNumber = null;
    }

    public PhoneNumber(String phoneNumber) {
        validate(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    private void validate(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("O número de telefone não pode estar vazio.");
        }

        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("O número de telefone deve conter apenas dígitos.");
        }
 
        if (!phoneNumber.matches("^[1-9]{2}(?:[2-8]\\d{7}|9\\d{8})$")) {
            throw new IllegalArgumentException("Número de telefone inválido.");
        }
    }

    public String getValue() {
        return phoneNumber;
    }
}
