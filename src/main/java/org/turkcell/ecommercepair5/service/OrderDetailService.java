package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> findByOrderId(Integer orderId);
    void deleteOrderDetail(OrderDetail orderDetail);

    void saveAll(List<OrderDetail> orderDetailsToDelete);
}
