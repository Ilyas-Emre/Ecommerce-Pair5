package org.turkcell.ecommercepair5.dto.cartdetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.turkcell.ecommercepair5.entity.CartDetailId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartDetailDto {

    private CartDetailId cartDetailId;
    private Integer quantity;

}
