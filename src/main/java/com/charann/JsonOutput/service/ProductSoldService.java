package com.charann.JsonOutput.service;

import com.charann.JsonOutput.entity.MonthlySales;
import com.charann.JsonOutput.entity.ProductSold;
import com.charann.JsonOutput.entity.TotalSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSoldService {
    Optional<ProductSold> getProductById(Long id);

    String addSoldProduct(ProductSold productSold);

    String monthlyReport();

//    TotalSales getTotalSales();

    String mostSold();

    String yearlyReport();

}
