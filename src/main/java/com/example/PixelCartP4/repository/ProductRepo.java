package com.example.PixelCartP4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.enums.Category;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
