package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.entity.Order;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.repository.OrderRepository;
import org.turkcell.ecommercepair5.repository.SubcategoryRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void saveAll(List<Order> userOrders) {

        orderRepository.saveAll(userOrders);

    }

    @Override
    public void deleteOrdersForAUser(Integer id) {

        List<Order> ordersToDelete = orderRepository.findByUserId(id);

        if (ordersToDelete.isEmpty()) {
            throw new BusinessException("No orders found for user with id: " + id);
        }

        // Set all orders as inactive
        ordersToDelete.forEach(order -> order.setIsActive(false));

        orderRepository.saveAll(ordersToDelete);


    }

}
