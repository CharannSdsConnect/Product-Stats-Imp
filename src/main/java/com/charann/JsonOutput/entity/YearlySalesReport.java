package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class YearlySalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String year;

    @Column(name = "sales_amount")
    private double price;
}
