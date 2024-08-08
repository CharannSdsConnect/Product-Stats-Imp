package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.ProductBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBoughtRepo extends JpaRepository<ProductBought, Long> {

    Optional<ProductBought> findById(Long id);

//    @Query(value = "SELECT name, sum(quantity) FROM product_bought group by name;", nativeQuery = true)
//    List<ProductBought> findCategorizedCount();

    Boolean existsByName(String name);

    ProductBought findFirstByName(String name);


//    List<ProductBought> findAll();

}
