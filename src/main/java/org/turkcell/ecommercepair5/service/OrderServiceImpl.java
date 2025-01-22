package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.order.CreateOrderDto;
import org.turkcell.ecommercepair5.dto.order.DeleteOrderDto;
import org.turkcell.ecommercepair5.dto.order.UpdateOrderStatusDto;
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

    @Override
    public void deleteOrderById(DeleteOrderDto deleteOrderDto) {

        Order order = orderRepository.findById(deleteOrderDto.getOrderId()).orElseThrow(() ->
                new BusinessException("No order found with id: " + deleteOrderDto.getOrderId())
        );
        List<OrderDetail> orderDetailsToDelete = orderDetailService.findByOrderId(deleteOrderDto.getOrderId());


        if (orderDetailsToDelete.isEmpty()) {
            throw new BusinessException("No order details found for user with id: " + deleteOrderDto.getOrderId());
        }
        orderDetailsToDelete.forEach(orderDetail -> orderDetail.setIsActive(false));
        orderDetailService.saveAll(orderDetailsToDelete);
        order.setIsActive(false);
        order.setStatus("Silindi");
        orderRepository.save(order);
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
    public void updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto)
    {
        Order order = orderRepository.findById(updateOrderStatusDto.getOrderId())
                .orElseThrow(() -> new BusinessException("Order not found with id: " + updateOrderStatusDto.getOrderId()));
        order.setStatus(updateOrderStatusDto.getStatus());
        orderRepository.save(order);
    }

    @Override
    public void deleteOrdersForAUser(Integer id) {

        List<Order> ordersToDelete = orderRepository.findByUserId(id);
        List<OrderDetail> orderDetailsToDelete = new ArrayList<>();
        for (Order order : ordersToDelete) {
            List<OrderDetail> details = orderDetailService.findByOrderId(order.getId());
            orderDetailsToDelete.addAll(details);
        }
        if (ordersToDelete.isEmpty()) {
            throw new BusinessException("No orders found for user with id: " + id);
        }
        if (orderDetailsToDelete.isEmpty()) {
            throw new BusinessException("No order details found for user with id: " + id);
        }
        orderDetailsToDelete.forEach(orderDetail -> orderDetail.setIsActive(false));
        orderDetailService.saveAll(orderDetailsToDelete);

        ordersToDelete.forEach(order -> order.setIsActive(false));
        ordersToDelete.forEach(order -> order.setStatus("Silindi"));
        orderRepository.saveAll(ordersToDelete);
    }

    @Override
    public Order createOrderFromCart(CreateOrderDto createOrderDto) {
        Integer userId = createOrderDto.getUserId();
        Integer cartId = createOrderDto.getCartId();

        // Step 1: Fetch the Cart associated with the UserID
        Cart cart = cartService.findByUserId(userId).orElseThrow(() -> new BusinessException("Cart not found with user id: " + userId));
        cartDetailService.calculateTotal(cart);
        // Step 2: Fetch cart details using the CartID (and the UserID if needed)
        List<CartDetail> cartDetails = cartDetailService.findByCartId(cart.getId());
        if (cartDetails.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Step 3: Check if all cart details are inactive or if product is missing
        boolean allInactive = true; // Flag to check if all cart items are inactive
        for (CartDetail detail : cartDetails) {
            if (detail.getIsActive()) {
                allInactive = false;
                Product product = productService.findById(detail.getProductId()).orElseThrow(() -> new BusinessException("Product not found with id: " + detail.getProductId()));

                // Check if there's enough stock
                if (product.getStock() < detail.getQuantity()) {
                    throw new BusinessException("Not enough stock for product: " + product.getName());
                }
            }
        }

        // If all cart details are inactive, throw an exception
        if (allInactive) {
            throw new BusinessException("All cart details are inactive, cannot create order.");
        }

        // Step 4: Create the order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("Hazırlanıyor"); // Assuming "Preparing" status
        order.setDate(LocalDateTime.now());
        order.setTotalPrice(cart.getTotalPrice());
        order.setIsActive(true);

        // Save the order
        orderRepository.save(order);

        // Step 5: Create OrderDetails from active CartDetails
        for (CartDetail cartDetail : cartDetails) {
            if (!cartDetail.getIsActive()) {
                continue; // Skip inactive cart details
            }

            // Fetch the product for the current cart detail
            Product product = productService.findById(cartDetail.getProductId())
                    .orElseThrow(() -> new BusinessException("Product not found with id: " + cartDetail.getProductId()));

            // Create and populate OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId()); // Link OrderDetail to Order
            orderDetail.setProductId(product.getId()); // Link OrderDetail to Product
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setUnitPrice(product.getUnitPrice());
            orderDetail.setIsActive(true);

            // Save the OrderDetail to the database
            orderDetailService.saveOrderDetail(orderDetail);

            // Deduct product stock
            product.setStock(product.getStock() - cartDetail.getQuantity());
            productRepository.save(product);

            // Mark CartDetail as inactive (because the product is now ordered)
            cartDetail.setIsActive(false);
            cartDetailService.saveCartDetail(cartDetail);
        }

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
