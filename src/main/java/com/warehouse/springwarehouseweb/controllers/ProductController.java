package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.models.Product;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.services.impl.ProductServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;

    @GetMapping(value = "/products")
    public String getAllProducts(Model model, Principal principal) {
        model.addAttribute("username", userService.getUserByPrincipal(principal));
        model.addAttribute("products", productService.findAll());
        return "products";
    }

    @GetMapping(value = "/products/create")
    public String createProduct(Model model, Principal principal) {
        model.addAttribute("categories", Category.values());
        model.addAttribute("username", userService.getUserByPrincipal(principal));
        return "createProduct";
    }

    @PostMapping(value = "/products/create")
    public String createProductPost(Product product, Principal principal) {
        productService.createProduct(product, principal);
        return "redirect:/index";
    }
}
