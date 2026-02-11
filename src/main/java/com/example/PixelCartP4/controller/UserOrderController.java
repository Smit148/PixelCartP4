package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.Cart;
import com.example.PixelCartP4.model.Order;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.model.enums.OrderStatus;
import com.example.PixelCartP4.repository.AddressRepo;
import com.example.PixelCartP4.repository.CartRepo;
import com.example.PixelCartP4.repository.OrderRepo;
import com.example.PixelCartP4.repository.UserRepo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserOrderController {

    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;

    public UserOrderController(OrderRepo orderRepo,
            CartRepo cartRepo,
            UserRepo userRepo,
            AddressRepo addressRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
    }

    @PostMapping("/order/cod")
    public String placeCodOrder(@RequestParam("addressId") Long addressId) {
        // 1. Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Load address
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // 3. Get all cart items of the user
        List<Cart> cartItems = cartRepo.findByUser(user);

        // 4. Create order for each cart item
        for (Cart cart : cartItems) {
            Order order = new Order();
            order.setUser(user);
            order.setProduct(cart.getProduct());
            order.setQuantity(cart.getQuantity());
            order.setAddress(address);
            order.setOrderAt(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);

            orderRepo.save(order);
        }

        // 5. Clear cart after placing order
        cartRepo.deleteAll(cartItems);

        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String success(Model model) {
        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepo.findByUserOrderByOrderAtDesc(user);
        model.addAttribute("orders", orders);

        return "success";
    }

    @GetMapping("/order")
    public String order(Model model) {
        // Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepo.findByUserOrderByOrderAtDesc(user);
        model.addAttribute("orders", orders);

        return "order";
    }
}