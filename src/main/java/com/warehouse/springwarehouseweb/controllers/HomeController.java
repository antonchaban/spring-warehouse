package com.warehouse.springwarehouseweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/")
    public String redirectToArticles() {
        return "redirect:/products";
    }
}
