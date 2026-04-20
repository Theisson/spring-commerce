package io.github.theisson.ecommerce.models.entities;

import io.github.theisson.ecommerce.models.types.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Username username;

    @Embedded
    private Email email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    protected User() {}

    public User(Username username, Email email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
