package org.turkcell.ecommercepair5.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.turkcell.ecommercepair5.dto.cartdetail.CartDetailDto;
import org.turkcell.ecommercepair5.dto.orderdetail.ViewOrderDetailDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewOrderDto {

    private BigDecimal orderTotal; // Total price for the entire order

    private LocalDateTime orderDate;

    private String status;

    private List<OrderDetailDto> orderDetails; // List of order details

}
