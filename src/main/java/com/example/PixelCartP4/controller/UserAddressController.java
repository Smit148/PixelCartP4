package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Address;
import com.example.PixelCartP4.model.User;
import com.example.PixelCartP4.model.enums.State;
import com.example.PixelCartP4.service.AddressService;
import com.example.PixelCartP4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class UserAddressController {

    private final AddressService addressService;
    private final UserService userService;

    public UserAddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    // Show all addresses for the logged-in user
    @GetMapping
    public String viewAddresses(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username).orElseThrow();

        List<Address> addresses = addressService.getAddressesByUser(user);
        model.addAttribute("address", addresses);
        return "address"; // Thymeleaf template for showing addresses
    }

    // Show Add Address form
    @GetMapping("/add")
    public String addAddressForm(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("states", State.values());
        return "add_address"; // Thymeleaf template for adding address
    }

    // Save new address
    @PostMapping("/add")
    public String saveAddress(@ModelAttribute Address address) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username).orElseThrow();

        address.setUser(user);
        addressService.saveAddress(address);

        return "redirect:/address";
    }

    // Delete address
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "redirect:/address";
    }
}