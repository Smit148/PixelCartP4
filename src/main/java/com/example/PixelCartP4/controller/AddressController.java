package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.repository.AddressRepo;
import com.example.PixelCartP4.repository.UserRepo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/addresses")
public class AddressController {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressController(AddressRepo addressRepo, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String viewAddresses(Model model) {
        model.addAttribute("addresses", addressRepo.findAll()); 
        return "admin/address/view_address";  
    }

    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());  
        model.addAttribute("users", userRepo.findAll());  
        return "admin/address/add_address";  
    }

    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address) {
        addressRepo.save(address);  
        return "redirect:/admin/addresses";  
    }

    @GetMapping("/update/{id}")
    public String updateAddressForm(@PathVariable Long id, Model model) {
        Address address = addressRepo.findById(id).orElse(new Address());
        
        model.addAttribute("address", address);
        model.addAttribute("users", userRepo.findAll());  
        return "admin/address/update_address";  
    }

    @PostMapping("/update")
    public String updateAddress(@ModelAttribute Address address) {
        addressRepo.save(address);  
        return "redirect:/admin/addresses";  
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepo.deleteById(id);  
        return "redirect:/admin/addresses";  
    }
}