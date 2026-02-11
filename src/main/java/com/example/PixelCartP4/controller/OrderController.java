package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Order;
import com.example.PixelCartP4.repository.OrderRepo;
import com.example.PixelCartP4.repository.ProductRepo;
import com.example.PixelCartP4.repository.UserRepo;
import com.example.PixelCartP4.repository.AddressRepo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public OrderController(OrderRepo orderRepo, ProductRepo productRepo, AddressRepo addressRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String viewOrders(Model model) {
        model.addAttribute("orders", orderRepo.findAll());  
        return "admin/order/view_order";  
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());  
        model.addAttribute("products", productRepo.findAll()); 
        model.addAttribute("addresses", addressRepo.findAll());
        model.addAttribute("users", userRepo.findAll());  
        return "admin/order/add_order";  
    }

    
    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order) {
        orderRepo.save(order);  // Save the new order
        return "redirect:/admin/orders"; 
    }

    @GetMapping("/update/{id}")
    public String updateOrderForm(@PathVariable Long id, Model model) {
        // Retrieve the order by ID, or create a new Order object if not found
        Order order = orderRepo.findById(id).orElse(new Order());
        
        model.addAttribute("order", order);
        model.addAttribute("products", productRepo.findAll());  // Add all available products
        model.addAttribute("addresses", addressRepo.findAll());  // Add all available addresses
        return "admin/order/update_order";  
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {
        orderRepo.save(order);  
        return "redirect:/admin/orders";  
    }

    // Delete an order
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderRepo.deleteById(id);  
        return "redirect:/admin/orders";  
    }
}