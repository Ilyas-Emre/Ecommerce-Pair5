package org.turkcell.ecommercepair5.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.turkcell.ecommercepair5.dto.order.OrderDetailDto;
import org.turkcell.ecommercepair5.dto.order.ViewOrderDto;
import org.turkcell.ecommercepair5.service.OrderDetailService;

import java.util.List;
@RestController
@RequestMapping("/orderdetails")
@AllArgsConstructor
public class OrderDetailsController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/{orderId}/more")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetails(@PathVariable Integer orderId) {
        List<OrderDetailDto> orderDetails = orderDetailService.viewOrderDetails(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ViewOrderDto> getOrderDetailsWithTotal(@PathVariable Integer orderId) {
        ViewOrderDto viewOrderDto = orderDetailService.getOrderDetailsWithTotal(orderId);
        return ResponseEntity.ok(viewOrderDto);
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<ViewOrderDto>> getAllOrdersForUser(@PathVariable Integer userId) {
        List<ViewOrderDto> userOrders = orderDetailService.getAllOrdersForUser(userId);
        return ResponseEntity.ok(userOrders);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ViewOrderDto>> getOrdersByUserId(@PathVariable Integer userId) {
        List<ViewOrderDto> orders = orderDetailService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

}
