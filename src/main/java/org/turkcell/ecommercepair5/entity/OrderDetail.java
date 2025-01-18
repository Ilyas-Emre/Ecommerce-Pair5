package org.turkcell.ecommercepair5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(OrderDetailId.class)
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @Column(name = "order_id", insertable = false, updatable = false)
    private Integer orderId;

    @Id
    @Column(name = "product_id", insertable = false, updatable = false)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
}
