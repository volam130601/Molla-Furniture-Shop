package com.molla.util;

import com.molla.dto.AuthenticationProvider;
import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.oauth.CustomOAuth2User;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Optional;

@Component
public class AuthenticationUser {
    @Autowired
    private UserService userService;
    public void filterUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //FilterChain spring security
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            if(currentUserName.contains("@")) { //Login with LOCAL
                User user = userService.findByEmail(currentUserName);
                if (user != null) {
                    Optional<Role> roles = user.getRoles().stream().filter(role -> role.getName().equals("ROLE_ADMIN")).findFirst();
                    if(roles.isPresent()) {
                        model.addAttribute("role" , "ROLE_ADMIN");
                    } else {
                        model.addAttribute("role" , "ROLE_MEMBER");
                    }
                    if(user.getFullName() != null)
                        model.addAttribute("username", user.getFullName());
                    else
                        model.addAttribute("username", "Unknown Name");
                }
            } else { //Login with SOCIAL: Facebook, Google
                CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                if(oAuth2User.getClientName().equals("Facebook")) {
                    User user = userService.findByEmailAndAuthProvider(oAuth2User.getEmail() , AuthenticationProvider.FACEBOOK);
                    if(user == null)
                        userService.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName(),
                                AuthenticationProvider.FACEBOOK);
                    else userService.updateExistUserAfterLoginSuccess(user, oAuth2User.getName());
                } else if(oAuth2User.getClientName().equals("Google")) {
                    User user = userService.findByEmailAndAuthProvider(oAuth2User.getEmail(),AuthenticationProvider.GOOGLE);
                    if(user == null)
                        userService.processOAuthPostLogin(oAuth2User.getEmail(), oAuth2User.getName(),
                                AuthenticationProvider.GOOGLE);
                    else userService.updateExistUserAfterLoginSuccess(user, oAuth2User.getName());
                }
                model.addAttribute("role" , "ROLE_MEMBER");
                model.addAttribute("username", oAuth2User.getName());
            }
        }
    }

}
