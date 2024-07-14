package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProducts;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.VisitedProductsRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.images.ApparelImagesRepository;
import java.util.*;

@Service
public class VisitedProductsService {

    private final VisitedProductsRepository visitedProductsRepository;

    @Autowired
    public VisitedProductsService(VisitedProductsRepository visitedProductsRepository) {
        this.visitedProductsRepository = visitedProductsRepository;
    }

    public String saveVisitedProducts(String visitedProducts) {
        // return visitedProductsRepository.save(visitedProducts);
        return "1223";
    }

    public List<Integer> findByUserId(Integer userId) {
        System.out.println("service ke function mein visited product ids lene agaye");
        List<Integer> product_ids = VisitedProductsRepository.getProductIdsByUserId(userId);
        
        System.out.println("repo se service mein product ids agayi");
        System.out.println(product_ids);
        
        return product_ids;
    }

    // Add more service methods as needed: update, delete, findById, findAll, etc.
}
