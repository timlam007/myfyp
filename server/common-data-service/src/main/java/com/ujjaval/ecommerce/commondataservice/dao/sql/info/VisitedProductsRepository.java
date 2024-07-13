package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VisitedProductsRepository extends JpaRepository<VisitedProducts, Integer> {

    public List<int> getProductIdsByUserId(int user_id) {
        List<Integer> productIds = new ArrayList<>();

        TypedQuery<VisitedProducts> query = entityManager.createQuery(
                "SELECT p FROM VisitedProducts p WHERE p.user_id = (?1)", VisitedProducts.class);
        query.setParameter(1, user_id);

        for (VisitedProducts obj : query.getResultList()) {
                System.out.println("ye data hai = ");
        }

        return productIds;
    }

}