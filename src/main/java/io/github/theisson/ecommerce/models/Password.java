package io.github.theisson.ecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Password {
    
    @Column(name = "password", nullable = false, length = 128)
    private final String password;

    protected Password() {
        this.password = null;
    }

    public Password(String password) {
        validate(password);
        this.password = password;
    }

    private void validate(String password) {
        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode estar vazia.");
        }

        if(password.length() < 8) {
            throw new IllegalArgumentException("A senha deve conter pelo menos 8 caracteres");
        }

        if(password.length() > 128) {
            throw new IllegalArgumentException("Senha muito longa. Limite de 128 caracteres.");
        }
    }

    public String getValue() {
        return password;
    }
}
