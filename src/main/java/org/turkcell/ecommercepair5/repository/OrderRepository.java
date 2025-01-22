package org.turkcell.ecommercepair5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.turkcell.ecommercepair5.entity.Order;
import org.turkcell.ecommercepair5.entity.User;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserId(Integer id);

    Order getById(Integer id);

    @Query("SELECT o FROM Order o WHERE o.user = :userId ORDER BY o.date DESC")
    List<Order> findOrdersByUserId(@Param("userId") User user);


}
