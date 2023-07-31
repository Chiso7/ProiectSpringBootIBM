package com.example.SpringBoot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import java.util.List;

public class BaseController {
    protected void addUserToModel(Model model, Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(String::valueOf)
                .toList();

        model.addAttribute("isAdmin", roles.contains("ROLE_ADMIN"));
    }
}
