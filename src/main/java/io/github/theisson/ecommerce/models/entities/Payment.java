package io.github.theisson.ecommerce.models.entities;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant moment;

    @OneToOne
    @MapsId
    private Order order;

    protected Payment() {}

    public Payment(Instant moment, Order order) {
        this.moment = moment;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id != null && id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
