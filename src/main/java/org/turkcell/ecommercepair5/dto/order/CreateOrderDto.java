package org.turkcell.ecommercepair5.dto.order;

import jakarta.validation.constraints.NotNull;
import org.turkcell.ecommercepair5.dto.cartdetail.ViewCartDto;
import org.turkcell.ecommercepair5.dto.orderdetail.ViewOrderDetailDto;

import java.util.List;

public class CreateOrderDto {

    //TODO: Carddan alması gerekiyor
    @NotNull(message = "UserID cannot be null!")
    private Integer userId;

    @NotNull(message = "Order details cannot be null!")
//    private List<ViewOrderDetailDto> orderDetails;
    private Integer cartId;
//    private List <ViewCartDto> cartDetails;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

//    public List<ViewCartDto> getCartDetails() {
//        return cartDetails;
//    }
//
//    public void setCartDetails(List<ViewCartDto> cartDetails) {
//        this.cartDetails = cartDetails;
//    }

    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId = userId;}
//    public List<ViewOrderDetailDto> getOrderDetails() {return orderDetails; }
//    public void setOrderDetails(List<ViewOrderDetailDto> orderDetails) {this.orderDetails = orderDetails;}
}