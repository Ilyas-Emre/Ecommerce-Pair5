package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.cartdetail.AddProductToCartDto;
import org.turkcell.ecommercepair5.dto.cartdetail.UpdateCartDetailDto;
import org.turkcell.ecommercepair5.dto.product.UpdateProductDto;
import org.turkcell.ecommercepair5.service.CartDetailService;

@RestController
@RequestMapping("/cartdetails")
@AllArgsConstructor
public class CartDetailsController {

    private final CartDetailService cartDetailService;

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestBody @Valid AddProductToCartDto addProductToCartDto) {
        cartDetailService.addProductToCart(addProductToCartDto);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCartDetails(@RequestBody @Valid UpdateCartDetailDto updateCartDetailDto) {
        cartDetailService.updateCartDetails(updateCartDetailDto);
        return ResponseEntity.ok("Product updated successfully.");
    }
}
