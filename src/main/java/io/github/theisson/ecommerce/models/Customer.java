package io.github.theisson.ecommerce.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;

    @Embedded
    private Cpf cpf;

    @Embedded
    private PhoneNumber phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ElementCollection
    @CollectionTable(name = "customer_addresses", joinColumns = @JoinColumn(name = "customer_id"))
    private Set<Address> addresses = new HashSet<>();
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Wallet wallet;

    protected Customer() {}

    public Customer(User user, Cpf cpf, PhoneNumber phoneNumber, LocalDate birthDate) {
        this.user = user;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.wallet = new Wallet(this);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCpf() {
        return cpf.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("O endereço não pode ser nulo.");
        }
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }
}
