package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.services.impl.ProductServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.SaleServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final SaleServiceImpl saleService;
    @GetMapping(value = "/")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("categories", Arrays.stream(Category.values()).toList());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("sales", saleService.findAll());
        model.addAttribute("username", userService.getUserByPrincipal(principal));
        model.addAttribute("products", productService.findAll());
        return "index";
    }
}
