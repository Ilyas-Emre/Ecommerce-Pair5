package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.OrderDetail;
import org.turkcell.ecommercepair5.entity.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {



}
