package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Order;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.model.enums.OrderStatus;
import com.example.PixelCartP4.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    public OrderServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Order createOrder(Order order) {
        // Set default status if not set
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }
        return orderRepo.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepo.findById(id);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepo.findByUserOrderByOrderAtDesc(user);
    }

    @Override
    public List<Order> getOrdersByUserAndStatus(User user, OrderStatus status) {
        return orderRepo.findByUserAndStatus(user, status);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        return orderRepo.save(order);
    }
}
