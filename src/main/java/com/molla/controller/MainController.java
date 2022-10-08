package com.molla.controller;

import com.molla.dto.UserDto;
import com.molla.entity.User;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private UserService userService;
    public void authUsername(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userService.findByEmail(currentUserName);
            if(user.getFirstName() != null && user.getLastName() != null)
                model.addAttribute("username" , user.getFirstName() + " " +user.getLastName());
            else {
                model.addAttribute("username", "Username");
            }
        }
    }
    @GetMapping("/")
    public String home(Model model) {
        authUsername(model);
        return "web/index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "web/login";
    }

    @GetMapping("/404")
    public String error_404() {
        return "web/404";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String viewAdminPage(Model model) {
        authUsername(model);
        return "web/admin";
    }

    @GetMapping("/forgot-password")
    public String viewForgotPassword() {
        return "web/forgot-password";
    }

    @GetMapping("/reset-password")
    public String viewResetPassword(@RequestParam(value = "em" ,required = false) String email,
                                    Model model) {
        System.out.println(email);
        model.addAttribute("email", email);
        return "web/reset-password";
    }

}
