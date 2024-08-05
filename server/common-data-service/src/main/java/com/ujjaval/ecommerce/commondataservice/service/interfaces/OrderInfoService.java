package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderInfo;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.OrderInfoRepository;

@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public OrderInfo saveOrderInfo(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }

}
