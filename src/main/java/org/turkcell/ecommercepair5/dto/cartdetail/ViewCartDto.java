package org.turkcell.ecommercepair5.dto.cartdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCartDto {

    private BigDecimal cartTotal; // Total price for the entire cart
    private List<CartDetailDto> cartDetails; // List of cart details

}
