package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemInfoRepository extends JpaRepository<OrderItemInfo, Integer> {

    @Query("SELECT oi FROM OrderItemInfo oi WHERE oi.orderInfo.customerId = :userId")
    List<OrderItemInfo> findOrderItemsByUserId(@Param("userId") Integer userId);

}
