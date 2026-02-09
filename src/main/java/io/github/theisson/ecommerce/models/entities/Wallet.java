package io.github.theisson.ecommerce.models.entities;

import io.github.theisson.ecommerce.models.*;
import jakarta.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Money balance;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, unique = true)
    private Customer customer;

    protected Wallet() {}

    public Wallet(Customer customer) {
        this.customer = customer;
        this.balance = Money.zero();
    }

    public void deposit(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }

        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }

        if (!this.balance.isGreaterThanOrEqual(amount)) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        this.balance = this.balance.subtract(amount);
    }

    public Long getId() {
        return id;
    }

    public long getBalance() {
        return balance.getAmount();
    }

    public Customer getCustomer() {
        return customer;
    }
}
