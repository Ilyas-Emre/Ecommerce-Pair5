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
@IdClass(CartDetailId.class)
@Table(name = "cart_details")
public class CartDetail {

    @Id
    @Column(name = "cart_id", insertable = false, updatable = false)
    private Integer cartId;

    @Id
    @Column(name = "product_id", insertable = false, updatable = false)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

}
