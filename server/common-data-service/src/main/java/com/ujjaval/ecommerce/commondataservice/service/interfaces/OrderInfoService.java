package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.ProductInfo;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.OrderInfoRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.OrderItemInfoRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.ProductInfoRepository;

@Service
public class OrderInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderItemInfoRepository orderItemInfoRepository;

    public OrderInfo saveOrder(OrderInfo orderInfo) {
        return orderInfoRepository.save(orderInfo);
    }

    public List<OrderItemInfo> getUserOrderItems(String userId) {
        Integer userIdAsInteger;
        try {
            userIdAsInteger = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId format");
        }

        List<OrderItemInfo> orderItems = orderItemInfoRepository.findOrderItemsByUserId(userIdAsInteger);

        List<Integer> productIds = new ArrayList<>();
        for (OrderItemInfo orderItem : orderItems) {
            productIds.add(orderItem.getProductId());
        }

        List<ProductInfo> products = productInfoRepository.getProductsById(productIds.stream()
                                                                                     .map(String::valueOf)
                                                                                     .toArray(String[]::new));

        HashMap<Integer, ProductInfo> productMap = new HashMap<>();
        for (ProductInfo product : products) {
            productMap.put(product.getId(), product);
        }

        for (OrderItemInfo orderItem : orderItems) {
            ProductInfo productInfo = productMap.get(orderItem.getProductId());
            if (productInfo != null) {
                orderItem.setProductInfo(productInfo);
            } else {
                throw new RuntimeException("Product not found with id: " + orderItem.getProductId());
            }
        }

        return orderItems;
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
