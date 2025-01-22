package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.entity.OrderDetail;
import org.turkcell.ecommercepair5.repository.OrderDetailRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;


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

}
