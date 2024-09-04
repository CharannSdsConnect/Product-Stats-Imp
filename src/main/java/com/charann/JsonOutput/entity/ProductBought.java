package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "product_bought")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBought {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private double quantity;    // no.of products sold

    @Column
    private double price;   // price of one product

    @Column
    private String name;

    @Column
    private String month;

//    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "expiry_date")
    private LocalDate expirydate;

    @Column
    private String sku;

    @Column
    private String category;

    @Column
    private double weight;

    @Column
    private String unit;
}
