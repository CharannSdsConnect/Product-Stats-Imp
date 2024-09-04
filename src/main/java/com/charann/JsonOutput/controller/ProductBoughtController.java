package com.charann.JsonOutput.controller;

import com.charann.JsonOutput.entity.BoughtPieChart;
import com.charann.JsonOutput.entity.ProductBought;
import com.charann.JsonOutput.repository.ProductBoughtRepo;
import com.charann.JsonOutput.service.ProductBoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductBoughtController {

    @Autowired
    ProductBoughtRepo productBoughtRepo;

    @Autowired
    ProductBoughtService productBoughtService;

    public String weight_convert(String unit) {

        if(unit.equals("g")) {
            return "kg";
        } else if (unit.equals("ml")) {
            return "L";
        }
        else return "";
    }

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

    @GetMapping("/get-pie-details")
    public ResponseEntity<String> pieDetails() {

        return new ResponseEntity<String>(
                productBoughtService.getPieChartData(),
                HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-product/{id}")
    public ProductBought updateProduct(@PathVariable long id, @RequestBody ProductBought productBought) {
        ProductBought productBought1 = productBoughtRepo.findById(id).get();
        productBought1.setName(productBought.getName());
        productBought1.setPrice(productBought.getPrice());
        productBought1.setMonth(productBought.getMonth());
        productBought1.setCategory(productBought.getCategory());
        productBought1.setSku(productBought.getSku());
        productBought1.setExpirydate(productBought.getExpirydate());
        if(productBought.getWeight()>=1000) {
            productBought1.setWeight(productBought.getWeight()/1000);
            productBought1.setUnit(weight_convert(productBought.getUnit()));
        } else {
            productBought1.setWeight(productBought.getWeight());
            productBought1.setUnit(productBought.getUnit());
        }

        return productBoughtRepo.save(productBought1);
    }

    @DeleteMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable long id) {
        ProductBought productBought = productBoughtRepo.findById(id).get();
        productBoughtRepo.delete(productBought);

        return "Deleted successfully";
    }
}
