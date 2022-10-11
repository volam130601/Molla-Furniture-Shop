package com.molla.controller;

import com.molla.dto.AuthenticationProvider;
import com.molla.dto.UserDto;
import com.molla.entity.User;
import com.molla.oauth.CustomOAuth2User;
import com.molla.oauth.OAuth2LoginSuccessHandler;
import com.molla.service.UserService;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    public void authUsername(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            if(currentUserName.contains("@")) {
                User user = userService.findByEmail(currentUserName);
                if (user != null) {
                    model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
                }
            } else {
                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                User user = userService.findByEmail(oAuth2User.getEmail());
                if(user == null)
                    userService.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName(),
                            AuthenticationProvider.FACEBOOK);
                else userService.updateExistUserAfterLoginSuccess(user, oAuth2User.getFullName(),
                        AuthenticationProvider.FACEBOOK);
                model.addAttribute("username", oAuth2User.getName());
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
