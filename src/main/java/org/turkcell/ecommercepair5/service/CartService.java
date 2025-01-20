package org.turkcell.ecommercepair5.service;

import org.turkcell.ecommercepair5.dto.cart.CreateCartDto;
import org.turkcell.ecommercepair5.entity.Cart;
import org.turkcell.ecommercepair5.entity.User;

import java.util.List;
import java.util.Optional;

public interface CartService {

    void CreateCart(User user);

    Optional<Cart> findById(Integer id);
    
    Optional<Cart> findByUserId(Integer id);

    void save(Cart userCart);
}
