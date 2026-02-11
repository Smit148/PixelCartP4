package com.example.PixelCartP4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.User;

public interface CartRepo extends JpaRepository<Cart, Long> {
     List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProduct(User user, Product product);
}
