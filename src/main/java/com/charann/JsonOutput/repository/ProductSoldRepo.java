package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSoldRepo extends JpaRepository<ProductSold, Long> {

    Boolean existsByMonth(String month);
}
