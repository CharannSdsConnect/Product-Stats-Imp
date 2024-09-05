package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WeightUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double weight;

    @Column
    private String unit;

}
