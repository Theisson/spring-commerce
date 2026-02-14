package io.github.theisson.ecommerce.models.types;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Username {
    
    @Column(name = "username", nullable = false, length = 20)
    private final String username;

    protected Username() {
        this.username = null;
    }

    public Username(String username) {
        validate(username);
        this.username = username;
    }

    private void validate(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("O nome de usuário não pode estar vazio.");
        }

        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("O nome de usuário deve ter entre 3 e 20 caracteres.");
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("O nome de usuário não deve conter caracteres especiais.");
        }
    }

    public String getValue() {
        return username;
    }
}
