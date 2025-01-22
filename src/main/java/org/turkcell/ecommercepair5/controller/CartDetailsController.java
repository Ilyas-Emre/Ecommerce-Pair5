package org.turkcell.ecommercepair5.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.ecommercepair5.dto.cartdetail.*;
import org.turkcell.ecommercepair5.service.CartDetailService;

import java.util.List;

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

    @GetMapping("/{cartId}/more")
    public ResponseEntity<List<CartDetailDto>> getCartDetails(@PathVariable Integer cartId) {
        List<CartDetailDto> cartDetails = cartDetailService.viewCartDetails(cartId);
        return ResponseEntity.ok(cartDetails);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ViewCartDto> getCartDetailsWithTotal(@PathVariable Integer cartId) {
        ViewCartDto viewCartDto = cartDetailService.getCartDetailsWithTotal(cartId);
        return ResponseEntity.ok(viewCartDto);
    }

    @PutMapping("/delete")
    public ResponseEntity<String> removeProductFromCart(@RequestBody @Valid RemoveProductFromCartDto removeProductFromCartDto) {
        cartDetailService.removeProductFromCart(removeProductFromCartDto);
        return ResponseEntity.ok("Product updated successfully.");
    }

    @PutMapping("/reduce")
    public ResponseEntity<String> reduceProductQuantity(@RequestBody @Valid ReduceProductQuantityDto reduceProductQuantityDto) {
        cartDetailService.reduceProductQuantity(reduceProductQuantityDto);
        return ResponseEntity.ok("Product updated successfully.");
    }

}
