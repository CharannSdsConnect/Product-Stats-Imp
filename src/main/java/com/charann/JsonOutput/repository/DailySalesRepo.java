package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.DailySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailySalesRepo extends JpaRepository<DailySalesReport, Long> {

    Boolean existsByYear(String year);

    Boolean existsByMonth(String month);

    Boolean existsByDate(String date);

    DailySalesReport findByYearAndMonthAndDate(String year, String month, String Date);

    List<DailySalesReport> findAllByYear(String year);

    Boolean existsByYearAndMonthAndDate(String year, String month, String date);

}
