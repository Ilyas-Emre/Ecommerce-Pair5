package org.turkcell.ecommercepair5.dto.order;

import jakarta.validation.constraints.NotNull;

public class UpdateOrderStatusDto {

    @NotNull(message = "Order ID null olamaz")
    private Integer orderId;
    @NotNull(message = "Order ID null olamaz")
    private String status;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
