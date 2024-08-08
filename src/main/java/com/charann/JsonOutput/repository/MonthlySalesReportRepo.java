package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.MonthlySales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlySalesReportRepo extends JpaRepository<MonthlySales, Long> {

    Boolean existsByMonth(String month);

    MonthlySales findByMonth(String month);
}
