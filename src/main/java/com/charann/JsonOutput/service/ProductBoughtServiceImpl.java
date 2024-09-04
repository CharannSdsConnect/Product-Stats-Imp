package com.charann.JsonOutput.service;

import com.charann.JsonOutput.entity.*;
import com.charann.JsonOutput.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class ProductBoughtServiceImpl implements ProductBoughtService{

    @Autowired
    BoughPieChartRepo boughPieChartRepo;

    @Autowired
    ProductBoughtRepo productBoughtRepo;

    @Autowired
    TotalBoughtRepo totalBoughtRepo;

    @Autowired
    BoughtBarGraphRepo boughtBarGraphRepo;

    @Autowired
    RemainingStocksRepo remainingStocksRepo;

    double total_price = 0;

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    public String weight_convert(String unit) {

        if(unit.equals("g")) {
            return "kg";
        } else if (unit.equals("ml")) {
            return "L";
        }
        else return "";
    }

    @Override
    public List<ProductBought> getAllProducts() {
        System.out.println(sumOfAmount());
        return productBoughtRepo.findAll();
    }

    // No.of products input to the store
    @Override
    public ProductBought createProduct(ProductBought productBought) {
        System.out.println("SKU" +productBought.getSku());

        if(productBoughtRepo.existsBySku(productBought.getSku())) {
            TotalBought totalBought = totalBoughtRepo.findOneByName(productBought.getName());
            totalBought.setTotal_price(productBought.getPrice() *
                    (productBought.getQuantity()+totalBought.getTotal_quantity()));
            totalBought.setTotal_quantity(totalBought.getTotal_quantity()+productBought.getQuantity());

            totalBoughtRepo.save(totalBought);
            System.out.println("Already exists");

            // Update Stocks
            RemainingStocks remainingStocks = remainingStocksRepo.findByName(productBought.getName());
            remainingStocks.setQuantity(remainingStocks.getQuantity()+productBought.getQuantity());
            remainingStocksRepo.save(remainingStocks);
        } else {
            TotalBought totalBought = new TotalBought();
            totalBought.setName(productBought.getName());
            totalBought.setTotal_price(productBought.getPrice()* productBought.getQuantity());
            totalBought.setTotal_quantity(productBought.getQuantity());
            totalBoughtRepo.save(totalBought);

            RemainingStocks remainingStocks = new RemainingStocks();
            remainingStocks.setName(productBought.getName());
            remainingStocks.setQuantity(productBought.getQuantity());
            remainingStocksRepo.save(remainingStocks);
        }

        if (boughtBarGraphRepo.existsByMonth(productBought.getMonth())) {
            BoughtBarGraph boughtBarGraph = boughtBarGraphRepo.findByMonth((String)productBought.getMonth());
            boughtBarGraph.setBought_month_amount((productBought.getPrice()*productBought.getQuantity())
                    +boughtBarGraph.getBought_month_amount());
            System.out.println(boughtBarGraph.getBought_month_amount());
            boughtBarGraphRepo.save(boughtBarGraph);
        } else {
            BoughtBarGraph boughtBarGraph = new BoughtBarGraph();
            boughtBarGraph.setMonth(productBought.getMonth());
            boughtBarGraph.setBought_month_amount(productBought.getPrice()*productBought.getQuantity());
            boughtBarGraphRepo.save(boughtBarGraph);
        }

        ProductBought productBought1 = new ProductBought();
        productBought1.setName(productBought.getName());
        productBought1.setQuantity(productBought.getQuantity());
        productBought1.setPrice(productBought.getPrice());
        productBought1.setMonth(productBought.getMonth());
        productBought1.setExpirydate(productBought.getExpirydate());
        productBought1.setCategory(productBought.getCategory());
        productBought1.setSku(productBought.getSku());
        if(productBought.getWeight()>=1000) {
            productBought1.setWeight(productBought.getWeight()/1000);
            productBought1.setUnit(weight_convert(productBought.getUnit()));
        } else {
            productBought1.setWeight(productBought.getWeight());
            productBought1.setUnit(productBought.getUnit());
        }
        return productBoughtRepo.save(productBought1);
    }

    @Override
    public double sumOfAmount() {
        total_price = 0;
        List<TotalBought> products = totalBoughtRepo.findAll();
        products.forEach((p)->{
            System.out.println(total_price+" "+p.getTotal_price());
            total_price+=p.getTotal_price();
        });
        return total_price;
    }

    @Override
    public int findByPrice() {
        totalBoughtRepo.findAll();
        return 0;
    }

    @Override
    public String getPieChartData() {
        double total_amt = sumOfAmount();

        List<TotalBought> total_bought_list = totalBoughtRepo.findAll();
        total_bought_list.forEach((p)->{
//            if(boughPieChartRepo.existsByName(p.getName())) {
//                BoughtPieChart boughtPieChart = boughPieChartRepo.findByName(p.getName());
//                boughtPieChart.setDegree(Double.parseDouble(decfor.format((
//                        (p.getTotal_price()+boughtPieChart.getDegree())/total_amt)*360)));
//                boughPieChartRepo.save(boughtPieChart);
//            }
//            else {
            BoughtPieChart boughtPieChart = new BoughtPieChart();
            boughtPieChart.setName(p.getName());
            boughtPieChart.setDegree(Double.parseDouble(decfor.format((p.getTotal_price() / total_amt) * 360)));
            System.out.println((p.getTotal_price() / total_amt) * 360);
            boughPieChartRepo.save(boughtPieChart);
            System.out.println("Name: " + boughtPieChart.getName() + "\nDegree: " + boughtPieChart.getDegree());
//            }
        });
        return "Success";
    }

}
