package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.cartdetail.*;
import org.turkcell.ecommercepair5.entity.Cart;
import org.turkcell.ecommercepair5.entity.CartDetail;
import org.turkcell.ecommercepair5.entity.CartDetailId;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.repository.CartDetailRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepository cartDetailRepository;
    private final CartService cartService;
    private final ProductService productService;


    @Override
    public void addProductToCart(AddProductToCartDto addProductToCartDto) {

        CartDetailId cartDetailId = addProductToCartDto.getCartDetailId();

        // Check if the cart exists
        Cart cart = cartService.findById(cartDetailId.getCartId())
                .orElseThrow(() -> new BusinessException("Cart not found with ID: " + cartDetailId.getCartId()));

        // Check if the product exists
        Product product = productService.findById(cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with ID: " + cartDetailId.getProductId()));

        // Get the requested quantity
        Integer requestedQuantity = addProductToCartDto.getQuantity();

        // Check if the product already exists in the cart
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndProductId(
                        cartDetailId.getCartId(), cartDetailId.getProductId())
                .orElse(null); // Return null if not found

        if (existingCartDetail != null) {
            // If product exists, create an UpdateCartDetailDto and pass it to the service
            UpdateCartDetailDto updateCartDetailDto = new UpdateCartDetailDto();
            updateCartDetailDto.setCartDetailId(cartDetailId);
            updateCartDetailDto.setQuantity(requestedQuantity);

            // Call the service to update the cart detail
            updateCartDetails(updateCartDetailDto);
        } else {

        if (product.getStock() < requestedQuantity) {
            throw new BusinessException("Not enough stock available. Only " + product.getStock() + " items are in stock.");
        }

            // Create and save the cart detail
            CartDetail cartDetail = new CartDetail();
            cartDetail.setCartId(cartDetailId.getCartId());
            cartDetail.setProductId(cartDetailId.getProductId());
            cartDetail.setQuantity(requestedQuantity);
            cartDetail.setUnitPrice(product.getUnitPrice());
            cartDetail.setIsActive(true);

            cartDetailRepository.save(cartDetail);

            calculateTotal(cart);

                }
    }

    @Override
    public void updateCartDetails(UpdateCartDetailDto updateCartDetailDto) {

        CartDetailId cartDetailId = updateCartDetailDto.getCartDetailId();

        // Check if the cart exists
        Cart cart = cartService.findById(cartDetailId.getCartId())
                .orElseThrow(() -> new BusinessException("Cart not found with ID: " + cartDetailId.getCartId()));

        // Check if the product exists
        Product product = productService.findById(cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with ID: " + cartDetailId.getProductId()));

        // Get the requested quantity
        Integer requestedQuantity = updateCartDetailDto.getQuantity();

        // Sepetteki mevcut ürünü bul
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndProductId(
                        cartDetailId.getCartId(), cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Cart detail not found with ID: " + cartDetailId.getCartId()));

        // Eğer ürün zaten sepette varsa
        if (existingCartDetail != null && existingCartDetail.getIsActive() == false) {

            // Stok kontrolü
            if (product.getStock() < requestedQuantity) {
                throw new BusinessException("Not enough stock available. Only " + product.getStock() + " items are in stock.");
            }

            existingCartDetail.setQuantity(requestedQuantity);
            existingCartDetail.setUnitPrice(product.getUnitPrice());
            existingCartDetail.setIsActive(true);
            cartDetailRepository.save(existingCartDetail);
            calculateTotal(cart);

        } else if (existingCartDetail != null) {
            // Mevcut miktarı ve istenen miktarı topla
            Integer newQuantity = existingCartDetail.getQuantity() + requestedQuantity;

            // Stok kontrolü
            if (product.getStock() < newQuantity) {
                throw new BusinessException("Not enough stock available. Only " + product.getStock() + " items are in stock.");
            }

            // Yeni miktarı güncelle
            existingCartDetail.setQuantity(newQuantity);
            existingCartDetail.setUnitPrice(product.getUnitPrice());
            cartDetailRepository.save(existingCartDetail);
            calculateTotal(cart);
        }

    }

//    @Override
//    public List<CartDetail> findByCartId(Integer cartId) {
//        return cartDetailRepository.findByCartId(cartId);
//    }

    @Override
    public void calculateTotal(Cart cart) {

        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartDetail cartDetail : cartDetails) {
            if (cartDetail.getIsActive()) {
                totalPrice = totalPrice.add(cartDetail.getUnitPrice().multiply(new BigDecimal(cartDetail.getQuantity())));
            }
        }

        cart.setTotalPrice(totalPrice);
        cartService.save(cart);

    }

    @Override
    public void removeProductFromCart(RemoveProductFromCartDto removeProductFromCartDto) {
        CartDetailId cartDetailId = removeProductFromCartDto.getCartDetailId();

        // Check if the cart exists
        Cart cart = cartService.findById(cartDetailId.getCartId())
                .orElseThrow(() -> new BusinessException("Cart not found with ID: " + cartDetailId.getCartId()));

        // Check if the product exists
        Product product = productService.findById(cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with ID: " + cartDetailId.getProductId()));

        // Sepetteki mevcut ürünü bul
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndProductId(
                        cartDetailId.getCartId(), cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Cart detail not found with ID: " + cartDetailId.getCartId()));

        existingCartDetail.setIsActive(false);
        cartDetailRepository.save(existingCartDetail);
        calculateTotal(cart);

    }

    @Override
    public void reduceProductQuantity(ReduceProductQuantityDto reduceProductQuantityDto) {

        CartDetailId cartDetailId = reduceProductQuantityDto.getCartDetailId();

        // Check if the cart exists
        Cart cart = cartService.findById(cartDetailId.getCartId())
                .orElseThrow(() -> new BusinessException("Cart not found with ID: " + cartDetailId.getCartId()));

        // Check if the product exists
        Product product = productService.findById(cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with ID: " + cartDetailId.getProductId()));

        // Get the requested quantity
        Integer quantityToReduce = reduceProductQuantityDto.getQuantity();

        // Sepetteki mevcut ürünü bul
        CartDetail existingCartDetail = cartDetailRepository.findByCartIdAndProductId(
                        cartDetailId.getCartId(), cartDetailId.getProductId())
                .orElseThrow(() -> new BusinessException("Cart detail not found with ID: " + cartDetailId.getCartId()));

        if (existingCartDetail != null) {
            // Mevcut miktarı ve istenen miktarı topla
            Integer newQuantity = existingCartDetail.getQuantity() - quantityToReduce;
            if (newQuantity <= 0)
            {
                existingCartDetail.setQuantity(0);
                existingCartDetail.setUnitPrice(product.getUnitPrice());
                existingCartDetail.setIsActive(false);
                cartDetailRepository.save(existingCartDetail);
                calculateTotal(cart);
            }
            else {// Stok kontrolü
            if (product.getStock() < newQuantity) {
                throw new BusinessException("Not enough stock available. Only " + product.getStock() + " items are in stock.");
            }

            // Yeni miktarı güncelle
            existingCartDetail.setQuantity(newQuantity);
            existingCartDetail.setUnitPrice(product.getUnitPrice());
            cartDetailRepository.save(existingCartDetail);
            calculateTotal(cart);
            }
        }
    }

    @Override
    public List<CartDetailDto> viewCartDetails(Integer cartId) {

        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);
        List<CartDetailDto> cartDetailDtos = new ArrayList<>();
        for (CartDetail cartDetail : cartDetails) {
            if (cartDetail.getIsActive()) {
                Product product = productService.findById(cartDetail.getProductId())
                        .orElseThrow(() -> new BusinessException("Product not found with ID: " + cartDetail.getProductId()));

                BigDecimal totalPrice = cartDetail.getUnitPrice().multiply(new BigDecimal(cartDetail.getQuantity()));

                CartDetailDto cartDetailDto = new CartDetailDto(
                        product.getName(),
                        cartDetail.getUnitPrice(),
                        cartDetail.getQuantity(),
                        totalPrice
                );

                cartDetailDtos.add(cartDetailDto);
            }

        }

        return cartDetailDtos;
    }

    @Override
    public ViewCartDto getCartDetailsWithTotal(Integer cartId) {
        Cart cart = cartService.findById(cartId)
                .orElseThrow(() -> new BusinessException("Cart not found with ID: " + cartId));

        List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);

        if (cartDetails.isEmpty()) {
            throw new BusinessException("No items found in the cart for ID: " + cartId);
        }

        BigDecimal cartTotal = cart.getTotalPrice(); // Get the total price from the Cart table

        // Map CartDetail to CartDetailDto
        List<CartDetailDto> cartDetailDtos = cartDetails.stream()
                .filter((cartDetail) -> cartDetail.getIsActive())
                .map(cartDetail -> {
                    Product product = productService.findById(cartDetail.getProductId())
                            .orElseThrow(() -> new BusinessException("Product not found for ID: " + cartDetail.getProductId()));
                    BigDecimal itemTotal = cartDetail.getUnitPrice().multiply(new BigDecimal(cartDetail.getQuantity()));

                    // Return DTO for each item
                    return new CartDetailDto(
                            product.getName(),
                            cartDetail.getUnitPrice(),
                            cartDetail.getQuantity(),
                            itemTotal
                    );
                })
                .toList();

        // Return the final ViewCartDto
        return new ViewCartDto(cartTotal, cartDetailDtos);
    }

    @Override
    public List<CartDetail> findByCartId(Integer cartId) {
        return cartDetailRepository.findByCartId(cartId);
    }

    @Override
    public void saveCartDetail(CartDetail cartDetail) {
        cartDetailRepository.save(cartDetail);
    }


}