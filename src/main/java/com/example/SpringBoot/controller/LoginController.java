package com.example.SpringBoot.controller;

import com.example.SpringBoot.dto.UserDTO;
import com.example.SpringBoot.model.User;
import com.example.SpringBoot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        User existingUser = userService.findUserByEmail(userDTO.getEmail());

        if (existingUser != null) result.rejectValue("email", null, "User already registered");

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "/registration";
        }

        userService.saveUser(userDTO);
        return "redirect:/registration?success";
    }

    @GetMapping("/access-denied")
    @ResponseBody
    public String accessDenied(Model model) {
        return "Access denied";
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged out";
    }

}