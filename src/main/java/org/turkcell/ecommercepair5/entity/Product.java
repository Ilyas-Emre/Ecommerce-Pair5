package org.turkcell.ecommercepair5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Name cannot be null!")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters!")
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @NotNull(message = "Unit price cannot be null!")
    @DecimalMin(value = "0.000000000000001", message = "Unit price must be greater than 0")
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @NotNull(message = "Stock quantity cannot be null!")
    @Min(value = 0, message = "Stock quantity cannot be less than 0!")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @ManyToOne
    @NotNull(message = "Category cannot be null!")
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "image_url",nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<CartDetail> cartDetails;

    @ManyToOne
    @NotNull(message = "Subcategory cannot be null!")
    @JoinColumn(name = "subcategory_id", nullable = false)
    private Subcategory subcategory;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
