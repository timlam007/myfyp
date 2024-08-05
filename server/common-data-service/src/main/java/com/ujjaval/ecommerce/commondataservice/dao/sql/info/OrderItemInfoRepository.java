package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemInfoRepository extends JpaRepository<OrderItemInfo, Integer> {
    
}
