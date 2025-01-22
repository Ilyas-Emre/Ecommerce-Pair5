package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.order.CreateOrderDto;
import org.turkcell.ecommercepair5.entity.*;
import org.turkcell.ecommercepair5.repository.OrderRepository;
import org.turkcell.ecommercepair5.repository.ProductRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final CartDetailService cartDetailService;
    private final ProductService productService;
    private final OrderDetailService orderDetailService;

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    //db den alınacak
//    private BigDecimal calculateTotalPrice(Order order) {
//        BigDecimal totalPrice = BigDecimal.ZERO;
//        for (OrderDetail detail : order.getOrderDetails()) {
//            totalPrice = totalPrice.add(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
//        }
//        return totalPrice;
//    }

//    @Override
//    public void saveAll(List<Order> userOrders) {
//        userOrders.forEach(order -> {
//            order.setIsActive(true);
//            order.setStatus("Hazırlanıyor");
//            order.setDate(LocalDateTime.now());
//            cartDetailService.calculateTotal(cartService.findById(userOrders.get(userOrders.)));
//            //order.setTotalPrice(calculateTotalPrice(order));
//        });
//        orderRepository.saveAll(userOrders);
//    }

    @Override
    public void deleteOrdersForAUser(Integer id) {

        List<Order> ordersToDelete = orderRepository.findByUserId(id);

        if (ordersToDelete.isEmpty()) {
            throw new BusinessException("No orders found for user with id: " + id);
        }
        ordersToDelete.forEach(order -> order.setIsActive(false));
        orderRepository.saveAll(ordersToDelete);
    }

    @Override
    public Order createOrderFromCart(CreateOrderDto createOrderDto) {
        Integer userId = createOrderDto.getUserId();
        Integer cartId = createOrderDto.getCartId();
// Step 1: Fetch the CartID associated with the UserID
        Cart cart = cartService.findByUserId(userId).orElseThrow(() -> new BusinessException("Cart not found with user id: " + userId));
        ;

        // Step 2: Fetch cart details using the CartID (and the UserID if needed)
        List<CartDetail> cartDetails = cartDetailService.findByCartId(cart.getId());
        if (cartDetails.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

// Step 3: Validate stock for each product in the cart
        for (CartDetail detail : cartDetails) {
            Product product = productService.findById(detail.getProductId()).orElseThrow(() -> new BusinessException("Product not found with  id: " + detail.getProductId()));
            ;

        }

// Step 4: Create the order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("Hazırlanıyor");
        order.setDate(LocalDateTime.now());
        order.setTotalPrice(cart.getTotalPrice());
        order.setIsActive(true);
// Save the order and the associated order details
        orderRepository.save(order);
        double totalAmount = 0.0;

        for (CartDetail cartDetail : cartDetails) {
            // Fetch the product for the current cart detail
            Product product = productService.findById(cartDetail.getProductId())
                    .orElseThrow(() -> new BusinessException("Product not found with id: " + cartDetail.getProductId()));
            if (cartDetail.getIsActive()){
                // Create and populate OrderDetail
                OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrder(order); // Establish relationship with the Order
                orderDetail.setOrderId(order.getId());
//            orderDetail.setProduct(product); // Establish relationship with the Product
                orderDetail.setProductId(product.getId());
                orderDetail.setQuantity(cartDetail.getQuantity());
                orderDetail.setUnitPrice(product.getUnitPrice());
                orderDetail.setIsActive(true); // Assuming you use this field

                // Save the OrderDetail to the database
                orderDetailService.saveOrderDetail(orderDetail);

                // Update product inventory (deduct stock)
                product.setStock(product.getStock() - cartDetail.getQuantity());
                productRepository.save(product);
            }}

// Save the order and the associated order details
        orderRepository.save(order);



// Return the created order with all details
        return order;
    }

}


        //  @Override
//    public void createOrder(CreateOrderDto createOrderDTO) {
//        for (CartDetail cartDetail : createOrderDTO.getCartDetails()) {
//            calculatedTotalAmount += cartDetail.getTotalPrice();
//        }
//
//        if (calculatedTotalAmount != createOrderDTO.getTotalAmount()) {
//            throw new IllegalArgumentException("Total amount does not match cart details.");
//        }
//
//// Siparişi veritabanına kaydetme işlemi burada yapılır
//        saveOrderToDatabase(createOrderDTO);
//
//
//
//        /// ///////
//        order.setId(UUID.randomUUID().hashCode());
//        order.setStatus("Hazırlanıyor");
//        order.setDate(LocalDateTime.now());
//        order.setIsActive(true);
//
//        for(OrderDetail detail: order.getOrderDetails()){
//            Product product = productRepository.findById(detail
//                    .getProductId())
//                    .orElseThrow(() -> new BusinessException("Ürün bulunamadı. Urun ID:" + detail.getProductId()));
//        if(product.getStock()<detail.getQuantity()){
//            throw new BusinessException("Stokta yeterli ürün bulunamadı. Stok:" + product.getStock() + ", Ürün ID:" + detail.getProductId());
//        }
//
//        product.setStock(product.getStock() - detail.getQuantity());
//        productRepository.save(product);
//        }
//        orderRepository.save(order);
//    }
//}
