package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.cartdetail.AddProductToCartDto;
import org.turkcell.ecommercepair5.dto.cartdetail.UpdateCartDetailDto;

public interface CartDetailService {
    void addProductToCart(AddProductToCartDto addProductToCartDto);
    void updateCartDetails(UpdateCartDetailDto updateCartDetailDto);
}
