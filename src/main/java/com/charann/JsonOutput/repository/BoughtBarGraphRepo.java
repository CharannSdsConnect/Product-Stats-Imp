package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.BoughtBarGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughtBarGraphRepo extends JpaRepository<BoughtBarGraph, Long> {

    Boolean existsByMonth(String month);
    BoughtBarGraph findByMonth(String month);


}
