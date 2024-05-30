package com.njoshi.repository;

import com.njoshi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByOrderId(String orderId);
}
