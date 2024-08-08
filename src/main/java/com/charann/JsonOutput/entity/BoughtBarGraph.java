package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bought_bar_graph")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoughtBarGraph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String month;

    @Column
    private Double bought_month_amount;
}
