package com.charann.JsonOutput.service;

import com.charann.JsonOutput.entity.*;
import com.charann.JsonOutput.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public Optional<ProductSold> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public String addSoldProduct(ProductSold productSold) {

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
            productSold1.setPrice(productSold.getQuantity()*productBought.getPrice());
            System.out.println(totalSalesRepo.existsByName(productSold.getName()));
            if(totalSalesRepo.existsByName(productSold.getName())) {
                System.out.println("In total sales repo");
                TotalSales totalSales1 = totalSalesRepo.findByName(productSold.getName());
                System.out.println("In total sales repo");
                totalSales1.setQuantity(totalSales1.getQuantity()+productSold.getQuantity());
                totalSales1.setPrice(totalSales1.getPrice()+productSold1.getPrice());
                totalSalesRepo.save(totalSales1);
            } else {
                TotalSales totalSales = new TotalSales();
                totalSales.setName(productSold.getName());
                totalSales.setQuantity(productSold.getQuantity());
                totalSales.setPrice(productSold1.getPrice());

                totalSalesRepo.save(totalSales);
            }




//        TotalBought totalBought = totalBoughtRepo.findByName(productSold.getName());
//        totalBought.setTotal_quantity(totalBought.getTotal_quantity() - productSold.getQuantity());
//        totalBought.setTotal_price(totalBought.getTotal_price() - productSold1.getPrice());
//        totalBoughtRepo.save(totalBought);

            productSoldRepo.save(productSold1);

            return "Success";
        }

        else {
            return "Out of stocks";
        }

    }

    @Override
    public String monthlyReport() {

        List<ProductSold> productSold = productSoldRepo.findAll();

        productSold.forEach((sale)-> {
            if(monthlySalesReportRepo.existsByMonth(sale.getMonth())) {
                MonthlySales monthlySales = new MonthlySales();
                monthlySales.setMonth(sale.getMonth());
                monthlySales.setSales_amount(sale.getPrice());
                monthlySalesReportRepo.save(monthlySales);
            }
            else {
                MonthlySales monthlySales = monthlySalesReportRepo.findByMonth(sale.getMonth());
                monthlySales.setSales_amount(monthlySales.getSales_amount()+ sale.getPrice());
                monthlySalesReportRepo.save(monthlySales);
            }
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
        List<ProductSold> productSold = productSoldRepo.findAll();

        productSold.forEach((product)-> {
            if (!yearlySalesReportRepo.existsByYear(product.getYear())) {
                YearlySalesReport yearlySalesReport = new YearlySalesReport();
                yearlySalesReport.setYear(product.getYear());
                yearlySalesReport.setPrice(product.getPrice());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
            else {
                YearlySalesReport yearlySalesReport = yearlySalesReportRepo.findByYear(product.getYear());
                yearlySalesReport.setPrice(product.getPrice() + yearlySalesReport.getPrice());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
        });

        return "Yearly Report Generated";
    }
}


