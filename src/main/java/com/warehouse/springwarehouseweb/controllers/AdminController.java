package com.warehouse.springwarehouseweb.controllers;

import com.warehouse.springwarehouseweb.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {
    private final UserServiceImpl userService;
}
