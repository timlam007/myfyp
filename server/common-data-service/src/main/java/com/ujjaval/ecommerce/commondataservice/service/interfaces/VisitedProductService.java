package com.ujjaval.ecommerce.commondataservice.service.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ujjaval.ecommerce.commondataservice.entity.sql.info.VisitedProduct;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.VisitedProductRepository;
import com.ujjaval.ecommerce.commondataservice.dao.sql.info.ProductInfoRepository;

import java.util.List;

@Service
public class VisitedProductService {
    @Autowired
    private VisitedProductRepository visitedProductRepository;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    public VisitedProduct saveVisitedProduct(VisitedProduct visitedProduct) {
        if(visitedProduct.getUserId() == 0){
            return visitedProduct;
        }
        return visitedProductRepository.save(visitedProduct);
    }

    public List<Integer> getProductIdsByUserId(Integer userId) {
        return visitedProductRepository.findProductIdsByUserId(userId);
    }

    public boolean visitedProductExists(Integer userId, Integer productId) {
        VisitedProduct existingProduct = visitedProductRepository.findByUserIdAndProductId(userId, productId);
        return existingProduct != null;
    }

    public boolean anyProductExists() {
        Boolean existingProduct = productInfoRepository.anyProductExists();
        return existingProduct;
    }

}
