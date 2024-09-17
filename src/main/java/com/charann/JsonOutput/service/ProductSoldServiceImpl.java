package com.charann.JsonOutput.service;

import com.charann.JsonOutput.entity.*;
import com.charann.JsonOutput.repository.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSoldServiceImpl implements ProductSoldService {

    @Autowired
    ProductSoldRepo productSoldRepo;

    @Autowired
    TotalBoughtRepo totalBoughtRepo;

    @Autowired
    MonthlySalesReportRepo monthlySalesReportRepo;

    @Autowired
    ProductBoughtRepo productBoughtRepo;

    @Autowired
    TotalSalesRepo totalSalesRepo;

    @Autowired
    RemainingStocksRepo remainingStocksRepo;

    @Autowired
    YearlySalesReportRepo yearlySalesReportRepo;

    @Autowired
    DailySalesRepo dailySalesRepo;

    int iterationDailyReport = 1;
    int iterationMonthlyReport = 1;
    int iterationYearlyReport = 1;

    @Override
    public Optional<ProductSold> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductSold addSoldProduct(ProductSold productSold) {

        //Update stocks in bought repo
        RemainingStocks remainingStocks = remainingStocksRepo.findByName(productSold.getName());

        if(remainingStocks.getQuantity() - productSold.getQuantity() >= 0) {
            remainingStocks.setQuantity(remainingStocks.getQuantity()-productSold.getQuantity());

            ProductSold productSold1 = new ProductSold();
            ProductBought productBought = productBoughtRepo.findFirstByName(productSold.getName());
            System.out.println(productBought);

            productSold1.setName(productSold.getName());
            productSold1.setQuantity(productSold.getQuantity());
            productSold1.setDate(productSold.getDate());
            productSold1.setMonth(productSold.getMonth());
            productSold1.setYear(productSold.getYear());
            productSold1.setPrice(productSold.getPrice());
            productSold1.setSku(productSold.getSku());

            System.out.println(totalSalesRepo.existsByName(productSold.getName()));
            if(totalSalesRepo.existsByName(productSold.getName())) {
                TotalSales totalSales1 = totalSalesRepo.findByName(productSold.getName());
                totalSales1.setQuantity(totalSales1.getQuantity()+productSold.getQuantity());
                totalSales1.setPrice(totalSales1.getPrice()+productSold1.getPrice());
                totalSalesRepo.save(totalSales1);
            } else {
                TotalSales totalSales = new TotalSales();
                totalSales.setName(productSold.getName());
                totalSales.setQuantity(productSold.getQuantity());
                totalSales.setPrice(productSold1.getPrice() * productSold.getQuantity());

                totalSalesRepo.save(totalSales);
            }
//        TotalBought totalBought = totalBoughtRepo.findByName(productSold.getName());
//        totalBought.setTotal_quantity(totalBought.getTotal_quantity() - productSold.getQuantity());
//        totalBought.setTotal_price(totalBought.getTotal_price() - productSold1.getPrice());
//        totalBoughtRepo.save(totalBought);

            return productSoldRepo.save(productSold1);
        }

        else {
            return null;
        }

    }

    @Override
    public String monthlyReport() {

        if(iterationMonthlyReport>1) {
            productSoldRepo.findAll().forEach(product -> product.setPrice(0));
        }

        List<ProductSold> productSold = productSoldRepo.findAll();

        productSold.forEach((sale)-> {
            if(!monthlySalesReportRepo.existsByMonth(sale.getMonth())) {
                MonthlySales monthlySales1 = new MonthlySales();
                monthlySales1.setMonth(sale.getMonth());
                monthlySales1.setSales_amount(sale.getPrice()* sale.getQuantity());
                monthlySalesReportRepo.save(monthlySales1);
            }
            else {
                MonthlySales monthlySales = monthlySalesReportRepo.findByMonth(sale.getMonth());
                monthlySales.setSales_amount(monthlySales.getSales_amount()+ (sale.getPrice()* sale.getQuantity()));
                monthlySalesReportRepo.save(monthlySales);
            }
            iterationMonthlyReport++;
        });

        return "Successfully generated";
    }

    @Override
    public String mostSold() {
        Optional<TotalSales> totalSales = totalSalesRepo.findTopByOrderByPriceDesc();
        return totalSales.get().getName();
    }

    @Override
    public String yearlyReport() {

        if(iterationYearlyReport > 1) {
            yearlySalesReportRepo.findAll().forEach(product-> product.setPrice(0));
        }

        List<ProductSold> productSold = productSoldRepo.findAll();

        productSold.forEach((product)-> {
            if (!yearlySalesReportRepo.existsByYear(product.getYear())) {
                YearlySalesReport yearlySalesReport = new YearlySalesReport();
                yearlySalesReport.setYear(product.getYear());
                yearlySalesReport.setPrice(product.getPrice()*product.getQuantity());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
            else {
                YearlySalesReport yearlySalesReport = yearlySalesReportRepo.findByYear(product.getYear());
                yearlySalesReport.setPrice((product.getPrice()* product.getQuantity()) + yearlySalesReport.getPrice());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
        });
        iterationYearlyReport++;
        return "Yearly Report Generated";
    }

    @Override
    public String dailyReport() {
        List<ProductSold> productSold = productSoldRepo.findAll();

//        productSold.forEach(product -> product.setPrice(0));
        if (iterationDailyReport>1) {
           dailySalesRepo.findAll().forEach(product -> product.setSales(0));
        }

        productSold.forEach((product) -> {
//            if(dailySalesRepo.existsByYear(product.getYear())) {
//
//                if(dailySalesRepo.existsByMonth(product.getMonth())) {
//
//                    System.out.println(product.getMonth() +" exists");
//
//                    if(dailySalesRepo.existsByDate(product.getDate())) {
//
//                        DailySalesReport dailySalesReport = dailySalesRepo.findByYearAndDateAndMonth(
//                                product.getYear(), product.getMonth(), product.getDate()
//                        );
//
//
//                        dailySalesReport.setSales(dailySalesReport.getSales() + product.getPrice());
//                        dailySalesRepo.save(dailySalesReport);
//
//                    }
//                }
//            }
            Boolean exists = dailySalesRepo.existsByYearAndMonthAndDate(
                    product.getYear(), product.getMonth(), product.getDate()
            );
            if(exists) {
                DailySalesReport dailySalesReport = dailySalesRepo.findByYearAndMonthAndDate(
                        product.getYear(), product.getMonth(), product.getDate()
                );

                dailySalesReport.setSales((product.getPrice()* product.getQuantity()) + dailySalesReport.getSales());
                dailySalesRepo.save(dailySalesReport);
            }

            else {
                DailySalesReport dailySalesReport = new DailySalesReport();
                dailySalesReport.setYear(product.getYear());
                dailySalesReport.setMonth(product.getMonth());
                dailySalesReport.setDate(product.getDate());
                dailySalesReport.setSales(product.getPrice()* product.getQuantity());

                dailySalesRepo.save(dailySalesReport);
            }
        });

        iterationDailyReport++;
        return "Daily Report Generated";
    }

    @Override
    public ProductSold updateProductSold(String sku) {


        return null;
    }

    @Override
    public void generateMonthlyReport(HttpServletResponse response) throws IOException {
        List<MonthlySales> monthlySalesList = monthlySalesReportRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Month");
        row.createCell(2).setCellValue("Amount");

        int dataRowIndex = 1;
        for(MonthlySales sales:monthlySalesList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getMonth());
            dataRow.createCell(2).setCellValue(sales.getSales_amount());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateYearlyReport(HttpServletResponse response) throws IOException {
        List<YearlySalesReport> monthlySalesList = yearlySalesReportRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Year");
        row.createCell(2).setCellValue("Amount");

        int dataRowIndex = 1;
        for(YearlySalesReport sales:monthlySalesList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getYear());
            dataRow.createCell(2).setCellValue(sales.getPrice());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateDailyReport(HttpServletResponse response) throws IOException {
        List<DailySalesReport> dailySalesReports = dailySalesRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Date");
        row.createCell(2).setCellValue("Month");
        row.createCell(3).setCellValue("Year");
        row.createCell(4).setCellValue("Amount");

        int dataRowIndex = 1;
        for(DailySalesReport sales:dailySalesReports) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getDate());
            dataRow.createCell(2).setCellValue(sales.getMonth());
            dataRow.createCell(3).setCellValue(sales.getYear());
            dataRow.createCell(4).setCellValue(sales.getSales());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}


