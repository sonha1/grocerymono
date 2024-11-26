package com.tks.grocerymono.repository;


import com.tks.grocerymono.entity.Cart;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserIdAndProductId(String userId, Integer productId);

    List<Cart> findByUserId(String userId, Sort sort);
}
