package com.molla;

import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    void saveUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("ROLE_ADMIN"));
        roles.add(roleService.findByName("ROLE_USER"));
        User user =  User.builder()
                .email("admin2@gmail.com")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .firstName("vo")
                .lastName("lam")
                .phone("0795556872")
                .roles(roles)
                .build();
        assertNotNull(userService.save(user));
    }
    @Test
    void deleteUser(){
        userService.deleteById(1L);
    }

    @Test
    void fineUSer() {
        System.out.println(userService.findByEmail("admin@gmail.com"));
    }
}
