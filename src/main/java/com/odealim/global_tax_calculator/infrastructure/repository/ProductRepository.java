package com.odealim.global_tax_calculator.infrastructure.repository;

import com.odealim.global_tax_calculator.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}