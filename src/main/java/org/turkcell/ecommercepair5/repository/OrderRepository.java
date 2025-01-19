package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.ecommercepair5.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {



}
