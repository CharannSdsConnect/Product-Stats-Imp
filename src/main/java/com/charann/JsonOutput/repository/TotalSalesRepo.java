package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.TotalSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalSalesRepo extends JpaRepository<TotalSales, Long> {

    Boolean existsByName(String name);

    TotalSales findByName(String name);

    // Optional<Product> findTopByOrderByPriceDesc();
    Optional<TotalSales> findTopByOrderByPriceDesc();
}
