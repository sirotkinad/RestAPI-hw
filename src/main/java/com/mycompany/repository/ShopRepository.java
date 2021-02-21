package com.mycompany.repository;

import com.mycompany.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer>{

    @Query(value = "SELECT name FROM shop s WHERE district IN (:districts)", nativeQuery = true)
    List<String> getShopNamesByDistrict(@Param("districts") List<String> districts);

}
