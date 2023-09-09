package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.models.User;
import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.NameAlreadyBoundException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;


    @GetMapping("/signin")
    public String signIn(Principal principal, Model model) {
//        model.addAttribute("customer", userService.getUserByPrincipal(principal));
        return "signin";
    }

    @PostMapping("/signin")
    public String signInPost() {
        return "redirect:/products";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(User user) throws NameAlreadyBoundException {
        userService.createUser(user);
        return "redirect:/signin";
    }

    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }
}
