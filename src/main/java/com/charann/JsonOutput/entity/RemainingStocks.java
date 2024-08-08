package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class RemainingStocks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "stock_name")
    private String name;

    @Column(name = "rem_stocks")
    private double quantity;
}
