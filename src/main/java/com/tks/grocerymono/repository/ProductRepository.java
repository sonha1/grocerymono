package com.tks.grocerymono.repository;


import com.tks.grocerymono.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT p.* " +
            "FROM product p " +
            "JOIN (SELECT category_id, MIN(id) as min_id " +
            "      FROM product " +
            "      GROUP BY category_id " +
            "      LIMIT ?1) grouped " +
            "ON p.id = grouped.min_id", nativeQuery = true)
    List<Product> findTopDistinctByCategory(int size);

    @Query(value = "SELECT p.* " +
            "FROM product p " +
            "WHERE p.category_id = ?1 AND p.id != ?2 " +
            "ORDER BY RAND() " +
            "LIMIT ?3", nativeQuery = true)
    List<Product> findTopRandomByCategoryIdAndIdNot(int categoryId, int id, int size);
}
