package com.mycompany.repository;


import com.mycompany.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "SELECT DISTINCT b.name FROM book b", nativeQuery = true)
    List<String> findUniqueNames();

    @Query(value = "SELECT DISTINCT b.price FROM book b", nativeQuery = true)
    List<Double> findUniquePrices();

    @Query(value = "SELECT b.name, b.price FROM book b " +
            "WHERE b.price > 20000 OR b.name LIKE '%Windows%' " +
            "ORDER BY b.name, b.price DESC",nativeQuery = true)
    List<Object[]> findSortedNamesAndPrices();

}