package com.example.PixelCartP4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PixelCartP4.model.Order;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.model.enums.OrderStatus;

public interface OrderRepo extends JpaRepository<Order, Long> {
     // Fetch all orders for a user, sorted by most recent first
    List<Order> findByUserOrderByOrderAtDesc(User user);

    // Optional: Fetch all orders for a user filtered by status
    List<Order> findByUserAndStatus(User user, OrderStatus status);
}
