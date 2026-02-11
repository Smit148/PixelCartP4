package com.example.PixelCartP4.service;

import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.repository.CartRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Override
    public Cart addToCart(User user, Product product, Integer quantity) {
        // Check if product already in cart
        Optional<Cart> existingCart = cartRepo.findByUserAndProduct(user, product);

        if (existingCart.isPresent()) {
            // Update quantity
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepo.save(cart);
        } else {
            // Create new cart item
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            return cartRepo.save(cart);
        }
    }

    @Override
    public void removeFromCart(Long cartId) {
        cartRepo.deleteById(cartId);
    }

    @Override
    public Cart updateQuantity(Long cartId, Integer quantity) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cart.setQuantity(quantity);
        return cartRepo.save(cart);
    }

    @Override
    public List<Cart> getCartByUser(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public void clearCart(User user) {
        List<Cart> cartItems = cartRepo.findByUser(user);
        cartRepo.deleteAll(cartItems);
    }

    @Override
    public Integer getCartTotal(User user) {
        List<Cart> cartItems = cartRepo.findByUser(user);
        return cartItems.stream()
                .mapToInt(cart -> cart.getProduct().getDiscountedPrice() * cart.getQuantity())
                .sum();
    }

    @Override
    public Integer getCartItemCount(User user) {
        List<Cart> cartItems = cartRepo.findByUser(user);
        return cartItems.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }
}
