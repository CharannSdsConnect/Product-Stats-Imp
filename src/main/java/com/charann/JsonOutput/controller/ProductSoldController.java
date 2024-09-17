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

    @GetMapping("/download-daily-report")
    public void generateDailyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=daily_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateDailyReport(response);
    }

    @GetMapping("/download-monthly-report")
    public void generateMonthlyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=monthly_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateMonthlyReport(response);
    }

    @GetMapping("/download-yearly-report")
    public void generateYearlyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=yearly_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateYearlyReport(response);
    }

}
