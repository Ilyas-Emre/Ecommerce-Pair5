package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.cart.CreateCartDto;
import org.turkcell.ecommercepair5.entity.Cart;
import org.turkcell.ecommercepair5.entity.Category;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.repository.CartRepository;
import org.turkcell.ecommercepair5.repository.ProductRepository;
import org.turkcell.ecommercepair5.repository.UserRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;


    @Override
    public void CreateCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setIsActive(true);
        cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    @Override
    public Optional<Cart> findByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void save(Cart userCart) {

        cartRepository.save(userCart);

    }

    @Override
    public void deleteCartForAUser(Integer id) {
        Cart cartToDelete = cartRepository.findByUserId(id)
                .orElseThrow(() -> new BusinessException("Cart not found with user id: " + id)); // Use a custom exception or handle gracefully

        cartToDelete.setIsActive(false); // Mark the cart as inactive
        cartRepository.save(cartToDelete); // Save the updated cart
    }
}
