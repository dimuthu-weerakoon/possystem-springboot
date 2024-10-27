package com.ijse.cmjd106.posSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ijse.cmjd106.posSystem.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer>{
    Optional<Sale> findBySaleNumber(String saleNumber);
}
