package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.Order;
import org.turkcell.ecommercepair5.entity.OrderDetail;
import org.turkcell.ecommercepair5.entity.OrderDetailId;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {

    List<OrderDetail> findByOrderId(Integer orderId);

    Optional<OrderDetail> findByOrderAndProductId(Order order, Integer productId);
}
