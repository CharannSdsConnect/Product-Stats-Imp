package com.charann.JsonOutput.service;

import com.charann.JsonOutput.entity.BoughtPieChart;
import com.charann.JsonOutput.entity.ProductBought;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductBoughtService {

    List<ProductBought> getAllProducts();

    ProductBought createProduct(ProductBought productBought);

//    @Query(value = "SELECT name, sum(quantity) FROM PRODUCT_BOUGHT group by name", nativeQuery = true)
//    List<ProductBought> categorizeProducts();

//    @Query(value = "SELECT sum(total_price) from total_bought", nativeQuery = true)
    double sumOfAmount();

    int findByPrice();

    String getPieChartData();

//    String getMonthlyBarGraphData();
//
//    double getMonthlyBoughtAmount();

//    String getBarGraphData();
}
