package io.github.theisson.ecommerce.models.types;

import java.io.Serializable;
import io.github.theisson.ecommerce.models.entities.*;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class OrderItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
