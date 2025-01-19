package org.turkcell.ecommercepair5.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @NotNull(message = "User cannot be null!")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
