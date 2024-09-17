package com.charann.JsonOutput.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSold {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private double quantity;    // no.of products sold

//    @Column
//    private double price;   // price of one product

    @Column(name = "product_name")
    private String name;

    @Column(name = "sold_month")
    private String month;

    @Column
    private String year;

    @Column
    private String date;

    @Column(name = "total_price")
    private double price;

    @Column
    private String sku;

}
