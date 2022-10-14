package com.molla.controller.web.api;

import com.molla.dto.*;
import com.molla.dto.ResponseBody;
import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.service.EmailService;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import com.molla.util.AesEncryption;
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
        if(userService.findByEmailAndAuthProvider(userDto.getEmail(),AuthenticationProvider.LOCAL) != null) {
            return ResponseEntity.ok().body(
                    new ResponseBody<>("Email is already exists!", StatusCode.BAD_REQUEST)
            );
        } else {
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.findByName("ROLE_USER"));
            User user = User.builder()
                    .email(userDto.getEmail())
                    .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                    .roles(roles)
                    .enabled(true)
                    .authProvider(AuthenticationProvider.LOCAL)
                    .build();
            user = userService.save(user);
            if(user != null) {
                return ResponseEntity.ok(
                        new ResponseBody<>(user,"Registration is successful.", StatusCode.SUCCESS)
                );
            }
            return ResponseEntity.badRequest().body(
                    new ResponseBody<>("Registration is failed.",StatusCode.FAIL)
            );
        }
    }

    //Forgot Password
    @PostMapping(value = "/find-email", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<UserDto>> findEmail(@RequestBody UserDto userDto) {
        if(userService.findByEmailAndAuthProvider(userDto.getEmail(), AuthenticationProvider.LOCAL) != null) {
            return ResponseEntity.ok(
                    new ResponseBody<>(userDto , "Email is exist.", StatusCode.SUCCESS)
            );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Cannot found this email address." , StatusCode.FAIL)
        );
    }
    @Autowired
    private AesEncryption aesEncryption; //Encrypt and Decrypt for String.
    @PostMapping(value = "/forgot-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<UserDto>> handleForgotPassword(@RequestBody UserDto userDto) {
        User user = userService.findByEmail(userDto.getEmail());
        if(user != null) {
            //Cho nó chạy một luồng riêng với AJAX.
            Map<String, Object> properties = new HashMap<>();
            properties.put("email", aesEncryption.encrypt(userDto.getEmail() , "EMAIL"));
            EmailDetails emailDetails = EmailDetails.builder()
                    .to(user.getEmail())
                    .subject("Molla Store - Reset your password")
                    .template("web/email-template.html")
                    .properties(properties)
                    .build();
            Map<String,String> messages = emailService.sendHtmlMessage(emailDetails);
            if(messages.get("status").equals(String.valueOf(StatusCode.SUCCESS)))
                return ResponseEntity.ok(
                        new ResponseBody<>(userDto , messages.get("message"), StatusCode.SUCCESS)
                );
            else
                return ResponseEntity.ok(
                        new ResponseBody<>(userDto , messages.get("message"), StatusCode.FAIL)
                );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Cannot found this email address." , StatusCode.FAIL)
        );
    }

    @PostMapping(value = "/reset-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseBody<User>> handleResetPassword(@RequestBody UserDto userDto) {
        User user = userService.findByEmailAndAuthProvider(aesEncryption.decrypt(userDto.getEmail().replace(" " , "+"),"EMAIL") , AuthenticationProvider.LOCAL);
        if(user != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            userService.save(user);
            return ResponseEntity.ok(
                    new ResponseBody<>("Reset your password is successful.", StatusCode.SUCCESS)
            );
        }
        return ResponseEntity.ok(
                new ResponseBody<>("Reset your password is fail.", StatusCode.FAIL)
        );
    }

}
