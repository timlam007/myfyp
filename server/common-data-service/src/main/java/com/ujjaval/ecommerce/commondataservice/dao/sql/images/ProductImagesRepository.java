package com.ujjaval.ecommerce.commondataservice.dao.sql.images;

import com.ujjaval.ecommerce.commondataservice.entity.sql.images.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {

    @Query(value = "SELECT DISTINCT c FROM ApparelImages c")
    List<ProductImages> getAllData();
}
