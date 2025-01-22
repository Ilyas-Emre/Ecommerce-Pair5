package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.cartdetail.AddProductToCartDto;
import org.turkcell.ecommercepair5.dto.cartdetail.UpdateCartDetailDto;
import org.turkcell.ecommercepair5.dto.order.CreateOrderDto;
import org.turkcell.ecommercepair5.dto.order.DeleteOrderDto;
import org.turkcell.ecommercepair5.service.OrderService;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<String> createOrderFromCart(@RequestBody @Valid CreateOrderDto    createOrderDto) {
        orderService.createOrderFromCart(createOrderDto);
        return ResponseEntity.ok("Order created successfully.");
    }

    @PutMapping("/delete")
    public ResponseEntity<String> deleteOrderById(@RequestBody @Valid DeleteOrderDto deleteOrderDto) {
        orderService.deleteOrderById(deleteOrderDto);
        return ResponseEntity.ok("Order deleted successfully.");
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> updateCartDetails(@RequestBody @Valid UpdateCartDetailDto updateCartDetailDto) {
//        cartDetailService.updateCartDetails(updateCartDetailDto);
//        return ResponseEntity.ok("Product updated successfully.");
//    }
}
