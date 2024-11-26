package com.tks.grocerymono.repository;

import com.tks.grocerymono.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
