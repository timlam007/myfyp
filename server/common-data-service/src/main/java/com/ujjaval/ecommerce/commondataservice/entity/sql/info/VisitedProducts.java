package com.ujjaval.ecommerce.commondataservice.entity.sql.info;

import com.fasterxml.jackson.annotation.*;
import com.ujjaval.ecommerce.commondataservice.entity.sql.categories.ApparelCategory;
import com.ujjaval.ecommerce.commondataservice.entity.sql.categories.GenderCategory;
import com.ujjaval.ecommerce.commondataservice.entity.sql.categories.PriceRangeCategory;
import com.ujjaval.ecommerce.commondataservice.entity.sql.categories.ProductBrandCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "visited_products")
public class VisitedProducts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int userId;
    public int productId;

    public VisitedProducts(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
