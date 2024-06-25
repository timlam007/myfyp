package com.ujjaval.ecommerce.commondataservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductImagesDTO implements Serializable {

    private String title;

    private String imageLocalPath;

    private String imageURL;

}


