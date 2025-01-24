package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.dto.order.OrderDetailDto;
import org.turkcell.ecommercepair5.dto.order.ViewOrderDto;
import org.turkcell.ecommercepair5.entity.Order;
import org.turkcell.ecommercepair5.entity.OrderDetail;
import org.turkcell.ecommercepair5.entity.Product;
import org.turkcell.ecommercepair5.entity.User;
import org.turkcell.ecommercepair5.repository.OrderDetailRepository;
import org.turkcell.ecommercepair5.util.exception.type.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;


    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository,
                                  ProductService productService,
                                  @Lazy OrderService orderService,
                                  @Lazy UserService userService) {
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }




    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findByOrderId(Integer orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public void deleteOrderDetail(OrderDetail orderDetail) {
        orderDetail.setIsActive(false);
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void saveAll(List<OrderDetail> orderDetailsToDelete) {
        orderDetailRepository.saveAll(orderDetailsToDelete);
    }

    @Override
    public List<OrderDetailDto> viewOrderDetails(Integer orderId) {

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        BigDecimal orderTotal = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getIsActive()) {
                Product product = productService.findById(orderDetail.getProductId())
                        .orElseThrow(() -> new BusinessException("Product not found with ID: " + orderDetail.getProductId()));

                BigDecimal totalPrice = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                orderTotal = orderTotal.add(totalPrice);

                OrderDetailDto orderDetailDto = new OrderDetailDto(
                        product.getName(),
                        orderDetail.getUnitPrice(),
                        orderDetail.getQuantity(),
                        totalPrice
                );

                orderDetailDtos.add(orderDetailDto);
            }

        }

        return orderDetailDtos;
    }

    @Override
    public BigDecimal calculateOrderTotal(Integer orderId)
    {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        BigDecimal totalOrderPrice = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails) {
            if (orderDetail.getIsActive()) {
                Product product = productService.findById(orderDetail.getProductId())
                        .orElseThrow(() -> new BusinessException("Product not found with ID: " + orderDetail.getProductId()));

                BigDecimal totalPrice = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                totalOrderPrice = totalOrderPrice.add(totalPrice);
            }
        }
        return totalOrderPrice;
    }

    @Override
    public ViewOrderDto getOrderDetailsWithTotal(Integer orderId) {
        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found with ID: " + orderId));

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        if (orderDetails.isEmpty()) {
            throw new BusinessException("No items found in the order for ID: " + orderId);
        }

        BigDecimal orderTotal = calculateOrderTotal(orderId);

        LocalDateTime orderDate = order.getDate();
        String orderStatus = order.getStatus();

        // Map OrderDetail to OrderDetailDto
        List<OrderDetailDto> orderDetailDtos = orderDetails.stream()
                .filter((orderDetail) -> orderDetail.getIsActive())
                .map(orderDetail -> {
                    Product product = productService.findById(orderDetail.getProductId())
                            .orElseThrow(() -> new BusinessException("Product not found for ID: " + orderDetail.getProductId()));
                    BigDecimal itemTotal = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));

                    // Return DTO for each item
                    return new OrderDetailDto(
                            product.getName(),
                            orderDetail.getUnitPrice(),
                            orderDetail.getQuantity(),
                            itemTotal
                    );
                })
                .toList();

        // Return the final ViewCartDto
        return new ViewOrderDto(orderTotal, orderDate, orderStatus, orderDetailDtos);
    }

    @Override
    public List<ViewOrderDto> getAllOrdersForUser(Integer userId) {
        // Fetch all orders for the given user ID
        List<Order> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new BusinessException("No orders found for user with ID: " + userId);
        }

        // Map each order to a ViewOrderDto
        return orders.stream()
                .map(order -> {
                    // Fetch order details for each order
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());

                    if (orderDetails.isEmpty()) {
                        throw new BusinessException("No items found in the order for ID: " + order.getId());
                    }

                    // Calculate total for the order
                    BigDecimal orderTotal = orderDetails.stream()
                            .filter(OrderDetail::getIsActive)
                            .map(detail -> detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    // Map OrderDetails to OrderDetailDtos
                    List<OrderDetailDto> orderDetailDtos = orderDetails.stream()
                            .filter(OrderDetail::getIsActive)
                            .map(orderDetail -> {
                                Product product = productService.findById(orderDetail.getProductId())
                                        .orElseThrow(() -> new BusinessException("Product not found for ID: " + orderDetail.getProductId()));
                                BigDecimal itemTotal = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));

                                // Return DTO for each item
                                return new OrderDetailDto(
                                        product.getName(),
                                        orderDetail.getUnitPrice(),
                                        orderDetail.getQuantity(),
                                        itemTotal
                                );
                            })
                            .toList();

                    // Return the ViewOrderDto for this order
                    return new ViewOrderDto(
                            orderTotal,
                            order.getDate(),
                            order.getStatus(),
                            orderDetailDtos
                    );
                })
                .toList();
    }

    @Override
    public List<OrderDetailDto> mapOrderDetailsToDtos(List<OrderDetail> orderDetails) {
        // Map each OrderDetail to OrderDetailDto
        return orderDetails.stream()
                .map(orderDetail -> new OrderDetailDto(
                        orderDetail.getProduct().getName(),
                        orderDetail.getUnitPrice(),
                        orderDetail.getQuantity(),
                        orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()))))
                .collect(Collectors.toList());
    }


    @Override
    public List<ViewOrderDto> getOrdersByUserId(Integer userId) {
        // Find the User entity by ID
        User user = userService.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found with ID: " + userId));

        // Find the orders for the user and map to ViewOrderDto
        List<Order> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            throw new BusinessException("No orders found for user with ID: " + userId);
        }

        return orders.stream()
                .map(order -> new ViewOrderDto(
                        calculateOrderTotal(order.getId()),
                        order.getDate(),
                        order.getStatus(),
                        mapOrderDetailsToDtos(orderDetailRepository.findByOrderId(order.getId()))))
                .collect(Collectors.toList());
    }


}
