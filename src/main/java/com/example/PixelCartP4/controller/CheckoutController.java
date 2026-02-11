package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.repository.AddressRepo;
import com.example.PixelCartP4.repository.CartRepo;
import com.example.PixelCartP4.repository.UserRepo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CheckoutController {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;

    public CheckoutController(CartRepo cartRepo, UserRepo userRepo,
                              AddressRepo addressRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // 1. Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        // 2. Get cart items for user
        List<Cart> cartItems = cartRepo.findByUser(user);

        // 3. Calculate subtotal
        double total = 0;
        for (Cart item : cartItems) {
            total += item.getProduct().getDiscountedPrice() * item.getQuantity();
        }

        // 4. Get user addresses
        List<Address> addresses = addressRepo.findAllByUser(user);

        // 5. Add attributes to model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("shipping", 2000); // fixed shipping
        model.addAttribute("finalPrice", total + 2000);
        model.addAttribute("addresses", addresses);
        model.addAttribute("currentUser", user);

        return "checkout"; // Thymeleaf template: checkout.html
    }
}