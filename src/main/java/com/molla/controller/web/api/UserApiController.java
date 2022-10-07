package com.molla.controller.web.api;

import com.molla.dto.ResponseBody;
import com.molla.dto.UserDto;
import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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

}
