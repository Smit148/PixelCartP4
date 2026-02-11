package com.example.PixelCartP4.controller;

import com.example.PixelCartP4.model.Product;
import com.example.PixelCartP4.model.enums.Category;
import com.example.PixelCartP4.service.ProductService;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product/view_product";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", Category.values());
        return "admin/product/add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            product.setProductImage(imageFile.getBytes());
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id).orElse(new Product());
        model.addAttribute("product", product);
        model.addAttribute("categories", Category.values());
        return "admin/product/update_product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            product.setProductImage(imageFile.getBytes());
        }

        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/image/{id}")
    @ResponseBody // returned raw object directly to the HTTP response body not the template.
    public byte[] getImage(@PathVariable Long id) {
        return productService.getProductImage(id);
    }
}