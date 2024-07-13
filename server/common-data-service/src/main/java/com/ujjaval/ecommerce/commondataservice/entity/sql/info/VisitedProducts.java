package com.ujjaval.ecommerce.commondataservice.entity.sql.info;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visited_products")  // Specify the table name if different from class name
public class VisitedProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int product_id;
    private int user_id;

}
