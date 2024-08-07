package com.ujjaval.ecommerce.commondataservice.model;

import com.ujjaval.ecommerce.commondataservice.dto.ProductDTO;
import com.ujjaval.ecommerce.commondataservice.dto.ProductInfoDTO;
import com.ujjaval.ecommerce.commondataservice.dto.BrandImagesDTO;
import com.ujjaval.ecommerce.commondataservice.dto.ApparelImagesDTO;
import com.ujjaval.ecommerce.commondataservice.entity.sql.images.CarouselImages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MainScreenResponse implements Serializable {

    private List<BrandImagesDTO> brands;
    private List<ProductDTO> products;
    private List<ApparelImagesDTO> apparels;
    private List<CarouselImages> carousels;

    public MainScreenResponse(List<BrandImagesDTO> brands,
                                List<ProductDTO> products,
                                List<ApparelImagesDTO> apparels,
                                List<CarouselImages> carousels) {
        this.brands = brands;
        this.products = products;
        this.apparels = apparels;
        this.carousels = carousels;
    }
}
