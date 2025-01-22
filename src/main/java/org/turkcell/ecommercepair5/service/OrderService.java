package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.order.CreateOrderDto;
import org.turkcell.ecommercepair5.dto.order.DeleteOrderDto;
import org.turkcell.ecommercepair5.dto.order.UpdateOrderStatusDto;
import org.turkcell.ecommercepair5.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {


    Optional<Order> findById(Integer id);

    List<Order> findByUserId(Integer userId);

    //void saveAll(List<Order> userOrders);

    void deleteOrderById(DeleteOrderDto deleteOrderDto);

    void updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto);

    void deleteOrdersForAUser(Integer id);

    Order createOrderFromCart(CreateOrderDto createOrder);

}
