package org.turkcell.ecommercepair5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Name cannot be null!")
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Subcategory> subCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
