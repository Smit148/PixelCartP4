package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.User;
import java.util.List;

public interface CartService {

    // Cart Operations
    Cart addToCart(User user, Product product, Integer quantity);

    void removeFromCart(Long cartId);

    Cart updateQuantity(Long cartId, Integer quantity);

    List<Cart> getCartByUser(User user);

    void clearCart(User user);

    // Cart Calculations
    Integer getCartTotal(User user);

    Integer getCartItemCount(User user);
}
