package com.example.PixelCartP4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.enums.Category;
import com.example.PixelCartP4.service.ProductService;

@Controller
public class CoreController {

    private final ProductService productService;

    public CoreController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index() {
        return "index"; // templates/home.html
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // templates/about.html
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // templates/contact.html
    }

    @GetMapping("/pc-games")
    public String pcGames(Model model) {
        model.addAttribute("products", productService.getProductsByCategory(Category.PCGames));
        return "pc_games";
    }

    @GetMapping("/console-games")
    public String consoleGames(Model model) {
        model.addAttribute("products", productService.getProductsByCategory(Category.ConsoleGames));
        return "console_game";
    }

    @GetMapping("/gear")
    public String gear(Model model) {
        model.addAttribute("products", productService.getProductsByCategory(Category.Gear));
        return "gear";
    }

    // =========== For Image =========

    @GetMapping("/image/{id}")
    @ResponseBody // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable long id) {
        return productService.getProductImage(id);
    }

    // ==============================

    @GetMapping("/product-details/{id}")
    public String prodctDetails(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow();
        model.addAttribute("products", product);
        return "product_details"; // templates/product_details.html
    }
}