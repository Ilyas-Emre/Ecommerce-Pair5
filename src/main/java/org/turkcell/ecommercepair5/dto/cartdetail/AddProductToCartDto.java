package org.turkcell.ecommercepair5.dto.cartdetail;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.turkcell.ecommercepair5.entity.CartDetailId;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartDto {

    @NotNull(message = "Cart Detail Id cannot be null!")
    private CartDetailId cartDetailId;

    @NotNull(message = "Quantity cannot be null!")
    private Integer quantity;


}
