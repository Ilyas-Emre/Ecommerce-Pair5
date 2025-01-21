package org.turkcell.ecommercepair5.dto.order;

import jakarta.validation.constraints.NotNull;

public class DeleteOrderDto {

    @NotNull(message = "Order ID null olamaz")
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {this.orderId = orderId;}
}
