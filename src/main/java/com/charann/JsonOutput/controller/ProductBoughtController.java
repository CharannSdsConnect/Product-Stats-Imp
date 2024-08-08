package com.charann.JsonOutput.controller;

import com.charann.JsonOutput.entity.BoughtPieChart;
import com.charann.JsonOutput.entity.ProductBought;
import com.charann.JsonOutput.service.ProductBoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductBoughtController {

    @Autowired
    ProductBoughtService productBoughtService;

    @GetMapping("/products")
    public List<ProductBought> getAllProducts() {


        return productBoughtService.getAllProducts();
    }

    @PostMapping("/add-products")
    public ResponseEntity<ProductBought> addProduct(@RequestBody ProductBought productBought) {

        return new ResponseEntity<ProductBought>(
                productBoughtService.createProduct(productBought),
                HttpStatus.CREATED);

    }

//    @GetMapping("/categorize-products")
//    public List<ProductBought> categorizeProducts() {
//        return productBoughtService.categorizeProducts();
//    }

    @GetMapping("/get-pie-details")
    public ResponseEntity<String> pieDetails() {

        return new ResponseEntity<String>(
                productBoughtService.getPieChartData(),
                HttpStatus.ACCEPTED);
    }
}
