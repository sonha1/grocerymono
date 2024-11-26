package com.tks.grocerymono.repository;

import com.tks.grocerymono.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
