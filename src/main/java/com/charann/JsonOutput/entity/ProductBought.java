package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
}
