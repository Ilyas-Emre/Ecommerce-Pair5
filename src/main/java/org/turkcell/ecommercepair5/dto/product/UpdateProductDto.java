package org.turkcell.ecommercepair5.dto.product;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {

    @Min(value = 1, message = "Product ID must be a positive integer!")
    private Integer id;

    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters!")
    private String description;  //opsiyonel

    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0!")
    private BigDecimal unitPrice;

    @Min(value = 0, message = "Stock quantity cannot be less than 0!")
    private Integer stock;

    private String imageUrl; //Opsiyonel

    @NotNull(message = "Category ID cannot be empty!")
    private Integer categoryId;

    @NotNull(message = "Subcategory ID cannot be empty!")
    private Integer subcategoryId;

}
