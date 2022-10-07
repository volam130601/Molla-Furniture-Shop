package com.molla.config;

import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //Roles
        if(roleService.findByName("ROLE_ADMIN") == null)
            roleService.save(new Role("ROLE_ADMIN"));
        if(roleService.findByName("ROLE_MEMBER") == null)
            roleService.save(new Role("ROLE_MEMBER"));

        //Admin account
        if(userService.findByEmail("admin@gmail.com") == null) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.findByName("ROLE_ADMIN"));
            roles.add(roleService.findByName("ROLE_MEMBER"));
            admin.setRoles(roles);
            userService.save(admin);
        }
        // Member account
        if (userService.findByEmail("member@gmail.com") == null) {
            User user = new User();
            user.setEmail("member@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.findByName("ROLE_MEMBER"));
            user.setRoles(roles);
            userService.save(user);
        }
    }
}
