package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="total_stock")
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalBought {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private double total_quantity;    // no.of products sold

    @Column
    private double total_price;   // price of one product

    @Column
    private String name;

}
