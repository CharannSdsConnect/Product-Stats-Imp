package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bought_pie_chart")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoughtPieChart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private double degree;
}

