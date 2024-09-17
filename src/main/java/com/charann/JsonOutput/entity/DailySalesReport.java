package com.charann.JsonOutput.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailySalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String date;

    @Column
    private String month;

    @Column
    private String year;

    @Column
    private double sales;
}
