package com.molla.controller;

import com.molla.oauth.OAuth2LoginSuccessHandler;
import com.molla.service.UserService;
import com.molla.util.AuthenticationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    @Autowired
    private AuthenticationUser authenticationUser;
    @GetMapping("/")
    public String home(Model model) {
        authenticationUser.filterUser(model);
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
        authenticationUser.filterUser(model);
        model.addAttribute("username", "username");

        return "web/admin";
    }

    @GetMapping("/forgot-password")
    public String viewForgotPassword() {
        return "web/forgot-password";
    }

    @GetMapping("/reset-password")
    public String viewResetPassword(@RequestParam(value = "em" ,required = false) String email,
                                    Model model) {
        model.addAttribute("email", email);
        return "web/reset-password";
    }

}
