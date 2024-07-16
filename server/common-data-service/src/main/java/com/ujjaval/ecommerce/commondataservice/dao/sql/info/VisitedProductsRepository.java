package com.ujjaval.ecommerce.commondataservice.dao.sql.info;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.*;

public interface VisitedProductsRepository extends JpaRepository<VisitedProducts, Integer> {

    @PersistenceContext
    EntityManager entityManager = null;

    public static List<Integer> getProductIdsByUserId(Integer user_id) {

        System.out.println("repo mein agaye... user id ye mili hai param mein");
        System.out.println(user_id);

        List<Integer> productIds = new ArrayList<>();

        // TypedQuery<VisitedProducts> query = entityManager.createQuery("SELECT vp FROM VisitedProducts vp WHERE vp.userId = :userId", VisitedProducts.class);
        // query.setParameter("userId", user_id);

        // List<VisitedProducts> prods = query.getResultList();
        // if (prods != null && !prods.isEmpty()) {
        //     // Process the list of visited products
        //     System.out.println("query chal chuki hai.. ye woh prods hein");
        //     System.out.println(prods);

        //     for (VisitedProducts obj : prods) {
        //             System.out.println("ye product mil hai ek tou...");
        //             System.out.println(obj.productId);
        //             productIds.add(obj.productId);
        //     }
        // } else {
        //     // Handle case where no products were found for the given user
        //     productIds.add(1);
        // }

        productIds.add(1);
        System.out.println("product ids ka array ban gaya");
        System.out.println(productIds);

        return productIds;
    }

}