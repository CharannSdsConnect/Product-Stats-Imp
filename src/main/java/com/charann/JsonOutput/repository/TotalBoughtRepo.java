package com.charann.JsonOutput.repository;

import com.charann.JsonOutput.entity.TotalBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TotalBoughtRepo extends JpaRepository<TotalBought, Long> {

    Boolean existsByName(String name);

    TotalBought findOneByName(String name);


}
