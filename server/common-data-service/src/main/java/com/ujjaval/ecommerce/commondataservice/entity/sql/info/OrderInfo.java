package com.ujjaval.ecommerce.commondataservice.entity.sql.info;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class OrderInfo implements Serializable  {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int customerId;

    private String timestamp;

    private String amount;

    private String currency;

    private String deliveryStatus;

    private String trackPackageLink;

    private String addressFirstLine;

    private String addressSecondLine;

    private String addressZipCode;

    private String addressState;

    private String addressCountry;

    @OneToMany(mappedBy = "orderInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItemInfo> orderItems;

    public OrderInfo(int customerId, String timestamp, String deliveryStatus, String trackPackageLink, String addressFirstLine, String addressSecondLine, String addressZipCode, String addressState, String addressCountry, String amount, String currency) {
        this.customerId = customerId;
        this.timestamp = timestamp;
        this.deliveryStatus = deliveryStatus;
        this.trackPackageLink = trackPackageLink;
        this.currency = currency;
        this.amount = amount;
        this.addressFirstLine = addressFirstLine;
        this.addressSecondLine = addressSecondLine;
        this.addressZipCode = addressZipCode;
        this.addressState = addressState;
        this.addressCountry = addressCountry;
    }
}
