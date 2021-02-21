package com.mycompany.repository;

import com.mycompany.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "SELECT DISTINCT TO_CHAR(P.orderdate, 'MONTH') as month FROM purchase as p", nativeQuery = true)
    List<String> findUniqueMonths();

    @Query(value = "SELECT p.id, c.surname, s.name " +
            "FROM purchase p, customer c, shop s " +
            "WHERE p.customer = c.id AND p.seller = s.id", nativeQuery = true)
    List<Object[]> getCustomerAndShopName();


    @Query(value = "SELECT p.id, p.orderdate, c.surname, c.sale, b.name, p.quantity " +
            "FROM purchase p, customer c, book b " +
            "WHERE p.book = b.id AND p.customer = c.id", nativeQuery = true)
    List<Object[]> getPurchaseDetails();
}
