package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Order;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.model.enums.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    // Order Operations
    Order createOrder(Order order);

    Optional<Order> getOrderById(Long id);

    List<Order> getOrdersByUser(User user);

    List<Order> getOrdersByUserAndStatus(User user, OrderStatus status);

    // Order Status Management
    Order updateOrderStatus(Long orderId, OrderStatus status);
}
