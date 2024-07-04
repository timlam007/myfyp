package com.ujjaval.ecommerce.commondataservice.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO implements Serializable {
    private int id;
    private int sellerId;
    private String name;
    private Date publicationDate;
    private double price;
    private int availableQuantity;
    private int deliveryTime;
    private float ratings;
    private boolean verificationStatus;
    private String imageLocalPath;
    private String imageURL;
}