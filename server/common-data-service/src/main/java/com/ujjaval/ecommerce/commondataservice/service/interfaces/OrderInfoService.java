package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.OrderInfoRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.OrderItemInfoRepository;

@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    public OrderInfo saveOrder(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }

    public List<OrderInfo> getAllOrders() {
        return orderInfoRepository.findAll();
    }

    public OrderItemInfo saveOrderItem(OrderItemInfo orderItemInfo) {
        return orderItemInfoRepository.save(orderItemInfo);
    }

    public List<OrderItemInfo> getAllOrderItems() {
        return orderItemInfoRepository.findAll();
    }

    public OrderInfo getOrderById(int id) {
        return orderInfoRepository.findById(id).orElse(null);
    }

}
