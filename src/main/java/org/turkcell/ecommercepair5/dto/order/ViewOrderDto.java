package org.turkcell.ecommercepair5.dto.order;

import org.turkcell.ecommercepair5.dto.orderdetail.ViewOrderDetailDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ViewOrderDto {
    private Integer orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private String status;
    private List<ViewOrderDetailDto> orderDetails;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ViewOrderDetailDto> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<ViewOrderDetailDto> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
