package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProducts;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.VisitedProductsRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.images.ApparelImagesRepository;

@Service
public class VisitedProductsService {

    private final VisitedProductsRepository visitedProductsRepository;

    @Autowired
    public VisitedProductsService(VisitedProductsRepository visitedProductsRepository) {
        this.visitedProductsRepository = visitedProductsRepository;
    }

    public String saveVisitedProducts(String visitedProducts) {
        System.out.println("%%%%%%%%%%%%%% is mein agaya ye save krne service mein ^^^^^^^^^^");
        // return visitedProductsRepository.save(visitedProducts);
        return "1223";
    }

    public List<int> findByUserId(int userId) {
        int[] product_ids = VisitedProductsRepository.getProductIdsByUserId(userId);

        return product_ids;
    }

    // Add more service methods as needed: update, delete, findById, findAll, etc.
}
