package com.tks.grocerymono.repository;

import com.tks.grocerymono.entity.StripeCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StripeCustomerRepository extends JpaRepository<StripeCustomer, String> {

    Optional<StripeCustomer> findByUserId(String userId);
}
