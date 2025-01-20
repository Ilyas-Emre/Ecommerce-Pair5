package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {


    Optional<Order> findById(Integer id);

    List<Order> findByUserId(Integer userId);

    void saveAll(List<Order> userOrders);
}
