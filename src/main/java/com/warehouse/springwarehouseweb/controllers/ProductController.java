package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.models.Product;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.services.impl.ProductServiceImpl;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/product/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Product product = productService.findById(id);
        if (user.isAdmin() || user == product.getUser()) {
            model.addAttribute("categories", Category.values());
            model.addAttribute("user", user);
            model.addAttribute("product", product);
            return "product-edit";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED, "It is not allowed for you"
            );
        }
    }

    @PostMapping("/product/{id}/edit")
    public String editProductPost(Model model, Principal principal, Product updProduct, @PathVariable Long id) {
        productService.editProduct(updProduct, id);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("product", productService.findById(id));
//        return "redirect:/product/" + id; todo
        return "redirect:/products";
    }

    @PostMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(userService.getUserByPrincipal(principal), id);
        return "redirect:/products";
    }
}
