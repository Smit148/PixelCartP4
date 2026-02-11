package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.repository.AddressRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    private final AddressRepo addressRepo;

    public PaymentController(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @PostMapping("/payment")
    public String paymentPage(@RequestParam("selectedAddress") Long addressId,
                              @RequestParam("userId") Long userId,
                              Model model) {

        Address address = addressRepo.findById(addressId).orElseThrow();
        model.addAttribute("selectedAddress", address);
        model.addAttribute("userId", userId); // pass userId to payment page

        return "payment";
    }

    
}