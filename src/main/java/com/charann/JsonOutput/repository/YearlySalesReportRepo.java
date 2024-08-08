package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.YearlySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearlySalesReportRepo extends JpaRepository<YearlySalesReport, Long> {

    Boolean existsByYear(String year);

    YearlySalesReport findByYear(String year);
}
