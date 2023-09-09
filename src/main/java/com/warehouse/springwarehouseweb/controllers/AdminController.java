package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.models.Product;
import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.models.enums.Category;
import com.warehouse.springwarehouseweb.models.enums.Role;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {
    private final UserServiceImpl userService;

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
            model.addAttribute("roles", Role.values());
            model.addAttribute("user", userService.findById(id));
            return "user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String editUserPost(User updUser, @PathVariable Long id) {
        userService.editUser(updUser, id);
        return "redirect:/users";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
