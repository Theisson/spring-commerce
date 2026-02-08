package io.github.theisson.ecommerce.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Email {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Column(name = "email", nullable = false, unique = true)
    private final String email;

    protected Email() {
        this.email = null;
    }

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email não pode estar vazio.");
        }

        if(email.length() < 6 || email.length() > 255) {
            throw new IllegalArgumentException("Quantidade de caracteres inválida para um email.");
        }
        
        Matcher m = PATTERN.matcher(email);

        if(!m.matches()) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    public String getValue() {
        return email;
    }
}
