package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderInfo;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.OrderItemInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {

}
