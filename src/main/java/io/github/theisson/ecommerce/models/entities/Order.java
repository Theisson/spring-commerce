package io.github.theisson.ecommerce.models.entities;

import java.time.Instant;
import io.github.theisson.ecommerce.models.*;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant moment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    protected Order() {}

    public Order(Instant moment, OrderStatus status, Customer customer) {
        this.moment = moment;
        this.status = status;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void confirmPayment(Payment payment) {
        this.payment = payment;
        this.status = OrderStatus.PAID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id != null && id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
