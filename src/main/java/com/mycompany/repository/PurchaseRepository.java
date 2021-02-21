package com.mycompany.repository;

import com.mycompany.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "SELECT DISTINCT TO_CHAR(P.orderdate, 'MONTH') as month FROM purchase as p", nativeQuery = true)
    List<String> findUniqueMonths();

}
