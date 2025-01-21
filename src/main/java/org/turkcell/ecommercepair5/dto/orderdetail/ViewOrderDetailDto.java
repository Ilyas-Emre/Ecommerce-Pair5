package org.turkcell.ecommercepair5.dto.orderdetail;

import org.turkcell.ecommercepair5.entity.Order;
import org.turkcell.ecommercepair5.entity.Product;

import java.math.BigDecimal;

public class ViewOrderDetailDto {

    private Integer orderId;
    private Integer productId;
    private Order order;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Boolean isActive;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
