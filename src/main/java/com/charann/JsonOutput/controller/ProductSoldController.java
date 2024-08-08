package com.charann.JsonOutput.controller;

import com.charann.JsonOutput.entity.MonthlySales;
import com.charann.JsonOutput.entity.ProductSold;
import com.charann.JsonOutput.repository.ProductSoldRepo;
import com.charann.JsonOutput.service.ProductSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductSoldController {

    @Autowired
    ProductSoldService productSoldService;

    @PostMapping("/buy-products")
    public ResponseEntity<String> buyProduct(@RequestBody ProductSold productSold) {
        return new ResponseEntity<String>(
                productSoldService.addSoldProduct(productSold),
                HttpStatus.CREATED);
    }

    @GetMapping("/monthly-sales-report")
    public ResponseEntity<String> monthlyReport() {
        return new ResponseEntity<>(
                productSoldService.monthlyReport(),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/most-sold-product")
    public ResponseEntity<String> mostSoldProduct() {
        return new ResponseEntity<>(
                productSoldService.mostSold(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/yearly-sales-report")
    public ResponseEntity<String> yearlyReport() {
        return new ResponseEntity<>(
            productSoldService.yearlyReport(),
                HttpStatus.ACCEPTED
        );
    }
}
