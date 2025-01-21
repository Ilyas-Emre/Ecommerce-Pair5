package org.turkcell.ecommercepair5.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListingDto {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private Integer stock;
    private Integer categoryId;
    private Integer subcategoryId;
    private String imageUrl;

}
