package com.mycompany.repository;


import com.mycompany.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT DISTINCT c.district FROM customer c", nativeQuery = true)
    List<String> getUniqueDistricts();

    @Query(value = "SELECT c.surname, c.sale FROM customer c WHERE district =:district", nativeQuery = true)
    List<Object[]> findNameAndSaleByDistrict(@Param("district") String district);

}
