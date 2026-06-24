package com.felipe.ecommerce_api.repository;

import com.felipe.ecommerce_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findByCustomerEmail(String email);
}