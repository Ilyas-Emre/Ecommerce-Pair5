package org.turkcell.ecommercepair5.dto.product;

<<<<<<< HEAD
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotNull(message = "Name cannot be empty!")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters!")
    private String description; //opsiyonel

    @NotNull(message = "Unit price cannot be empty!")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0!")
    private BigDecimal unitPrice;

    @NotNull(message = "Stock cannot be empty!")
    @Min(value = 0, message = "Stock quantity cannot be less than 0!")
    private Integer stock;

    @NotNull(message = "Category ID cannot be empty!")
    private Integer categoryId;

    private String imageUrl; // Opsiyonel

=======
public class CreateProductDto {
>>>>>>> 795569e52179b38f71c23b3864024f09d2056165
}
