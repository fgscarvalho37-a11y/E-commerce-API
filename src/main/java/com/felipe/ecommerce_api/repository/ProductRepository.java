package com.felipe.ecommerce_api.repository;

import com.felipe.ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStockQuantityGreaterThan(Integer quantity);
}