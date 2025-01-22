package org.turkcell.ecommercepair5.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.turkcell.ecommercepair5.entity.OrderDetail;
import org.turkcell.ecommercepair5.repository.OrderDetailRepository;

@Service
@AllArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;


    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

}
