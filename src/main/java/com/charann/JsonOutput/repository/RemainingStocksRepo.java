package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.RemainingStocks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemainingStocksRepo extends JpaRepository<RemainingStocks, Long> {

    RemainingStocks findByName(String name);
}
