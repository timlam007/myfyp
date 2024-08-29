package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProduct;

import java.util.List;

public interface VisitedProductRepository extends JpaRepository<VisitedProduct, Long> {
    @Query("SELECT vp.productId FROM VisitedProduct vp WHERE vp.userId = :userId")
    List<Integer> findProductIdsByUserId(@Param("userId") Integer userId);

    @Query("SELECT vp FROM VisitedProduct vp WHERE vp.userId = :userId AND vp.productId = :productId")
    VisitedProduct findByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);
}
