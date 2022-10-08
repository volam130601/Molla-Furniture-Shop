package com.molla.controller.web.api;

import com.molla.dto.EmailDetails;
import com.molla.dto.ResponseBody;
import com.molla.dto.UserDto;
import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.service.EmailService;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/registration", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<User>> registration(@RequestBody UserDto userDto) {
        if(userService.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity.ok().body(
                    new ResponseBody<>("Email is already exists!", ResponseBody.StatusCode.BAD_REQUEST)
            );
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.findByName("ROLE_USER"));
            User user = User.builder()
                    .email(userDto.getEmail())
                    .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                    .roles(roles)
                    .build();
            user = userService.save(user);
            if(user != null) {
                return ResponseEntity.ok(
                        new ResponseBody<>(user,"Registration is successful.", ResponseBody.StatusCode.SUCCESS)
                );
            }
            return ResponseEntity.badRequest().body(
                    new ResponseBody<>("Registration is failed.",ResponseBody.StatusCode.FAIL)
            );
        }
    }

    //Forgot Password
    @PostMapping(value = "/find-email", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<UserDto>> findEmail(@RequestBody UserDto userDto) {
        if(userService.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity.ok(
                    new ResponseBody<>(userDto , "Email is exist.", ResponseBody.StatusCode.SUCCESS)
            );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Cannot found this email address." , ResponseBody.StatusCode.FAIL)
        );
    }

    @PostMapping(value = "/forgot-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<UserDto>> handleForgotPassword(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        if(user != null) {
            //Thêm: Cho nó chạy một luồng riêng.
            Map<String, Object> properties = new HashMap<>();
            properties.put("email", userDto.getEmail());
            EmailDetails emailDetails = EmailDetails.builder()
                    .to(user.getEmail())
                    .subject("Molla Store - Reset your password")
                    .template("web/email-template.html")
                    .properties(properties)
                    .build();
            emailService.sendHtmlMessage(emailDetails);
            return ResponseEntity.ok(
                    new ResponseBody<>(userDto , "Email is exist.", ResponseBody.StatusCode.SUCCESS)
            );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Cannot found this email address." , ResponseBody.StatusCode.FAIL)
        );
    }

    @PostMapping(value = "/reset-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<User>> handleResetPassword(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        if(user != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            userService.save(user);
            return ResponseEntity.ok(
                    new ResponseBody<>("Reset your password is successful.", ResponseBody.StatusCode.SUCCESS)
            );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Reset your password is fail.", ResponseBody.StatusCode.FAIL)
        );
    }

}
