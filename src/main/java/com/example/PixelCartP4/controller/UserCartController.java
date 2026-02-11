package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.repository.CartRepo;
import com.example.PixelCartP4.repository.ProductRepo;
import com.example.PixelCartP4.repository.UserRepo;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class UserCartController {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public UserCartController(CartRepo cartRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId) {
        // 1. Get currently logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        // 2. Get product
        Product product = productRepo.findById(productId).orElseThrow();

        // 3. Check if product is already in cart
        Cart cartItem = cartRepo.findByUserAndProduct(user, product)
                .orElse(new Cart());

        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        // 4. Save to cart
        cartRepo.save(cartItem);

        return "redirect:/cart/view"; // Redirect to cart page
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();

        model.addAttribute("cartItems", cartRepo.findByUser(user));
        return "view_cart";
    }

      @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepo.deleteById(id);
        return "redirect:/cart/view";
    }

    // Increment quantity
    @GetMapping("/addQuantity/{id}")
    public String addQuantity(@PathVariable Long id) {
        Cart cartItem = cartRepo.findById(id).orElseThrow();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartRepo.save(cartItem);
        return "redirect:/cart/view";
    }

    // Decrement quantity
    @GetMapping("/deleteQuantity/{id}")
    public String deleteQuantity(@PathVariable Long id) {
        Cart cartItem = cartRepo.findById(id).orElseThrow();

        // Decrement but not less than 1
        int newQty = cartItem.getQuantity() - 1;
        if (newQty < 1) {
            // Optionally, remove item from cart if quantity is zero
            cartRepo.delete(cartItem);
        } else {
            cartItem.setQuantity(newQty);
            cartRepo.save(cartItem);
        }

        return "redirect:/cart/view";
    }
}
