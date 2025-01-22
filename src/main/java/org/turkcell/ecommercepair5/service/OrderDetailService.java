package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.cartdetail.CartDetailDto;
import org.turkcell.ecommercepair5.dto.order.OrderDetailDto;
import org.turkcell.ecommercepair5.dto.order.ViewOrderDto;
import org.turkcell.ecommercepair5.entity.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> findByOrderId(Integer orderId);
    void deleteOrderDetail(OrderDetail orderDetail);

    void saveAll(List<OrderDetail> orderDetailsToDelete);

    List<OrderDetailDto> viewOrderDetails(Integer orderId);

    BigDecimal calculateOrderTotal(Integer orderId);

    ViewOrderDto getOrderDetailsWithTotal(Integer orderId);

    List<ViewOrderDto> getAllOrdersForUser(Integer userId);

    List<OrderDetailDto> mapOrderDetailsToDtos(List<OrderDetail> orderDetails);


    List<ViewOrderDto> getOrdersByUserId(Integer userId);
}
