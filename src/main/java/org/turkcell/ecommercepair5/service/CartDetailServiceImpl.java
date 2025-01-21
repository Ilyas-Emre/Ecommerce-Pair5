package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.cartdetail.AddProductToCartDto;
import org.turkcell.ecommercepair5.dto.cartdetail.UpdateCartDetailDto;
import org.turkcell.ecommercepair5.entity.Cart;
import org.turkcell.ecommercepair5.entity.CartDetail;
import org.turkcell.ecommercepair5.entity.CartDetailId;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.repository.CartDetailRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

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
        if (existingCartDetail != null) {
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
        } else {
            // Eğer ürün sepette yoksa, yeni bir satır ekle
            // Stok kontrolü
            if (product.getStock() < requestedQuantity) {
                throw new BusinessException("Not enough stock available. Only " + product.getStock() + " items are in stock.");
            }
        }

    }
}