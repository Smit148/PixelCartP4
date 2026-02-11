package com.example.PixelCartP4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.repository.CartRepo;
import com.example.PixelCartP4.repository.ProductRepo;
import com.example.PixelCartP4.repository.UserRepo;

@Controller
@RequestMapping("/admin/cart")
public class CartController {

    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public CartController(CartRepo cartRepo, UserRepo userRepo, ProductRepo productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @GetMapping
    public String listCart(Model model) {
        model.addAttribute("cartItems", cartRepo.findAll());
        return "/admin/cart/view_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cart", new Cart());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("products", productRepo.findAll());
        return "/admin/cart/add_list";
    }

    @PostMapping("/add")
    public String addCart(@ModelAttribute Cart cart) {
        cartRepo.save(cart);
        return "redirect:/admin/cart";
    }

    @GetMapping("/delete/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        cartRepo.deleteById(id);
        return "redirect:/admin/cart";
    }
}