package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.cartdetail.*;
import org.turkcell.ecommercepair5.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    void addProductToCart(AddProductToCartDto addProductToCartDto);
    void updateCartDetails(UpdateCartDetailDto updateCartDetailDto);

    void calculateTotal(Cart cart);

    void removeProductFromCart(RemoveProductFromCartDto removeProductFromCartDto);

    void reduceProductQuantity(ReduceProductQuantityDto reduceProductQuantityDto);

    List<CartDetailDto> viewCartDetails(Integer cartId);

    ViewCartDto getCartDetailsWithTotal(Integer cartId);
}
