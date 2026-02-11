package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.enums.Category;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    // CRUD Operations
    Product saveProduct(Product product);

    Optional<Product> getProductById(Long id);

    List<Product> getAllProducts();

    void deleteProduct(Long id);

    // Category Operations
    List<Product> getProductsByCategory(Category category);

    // Image Operations
    byte[] getProductImage(Long id);
}
