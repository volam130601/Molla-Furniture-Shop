package com.molla.service.impl;

import com.molla.dto.AuthenticationProvider;
import com.molla.entity.Role;
import com.molla.entity.User;
import com.molla.repository.UserRepository;
import com.molla.service.RoleService;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User findByEmailAndAuthProvider(String email, AuthenticationProvider authProvider) {
        return repository.findByEmailAndAuthProvider(email, authProvider);
    }

    @Override
    public void deleteById(Long userId) {
        repository.deleteById(userId);
    }

    @Override
    public void processOAuthPostLogin(String email , String fullName, AuthenticationProvider provider) {
        User existUser = repository.findByEmail(email);
        if (existUser == null) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword("#");
            newUser.setFullName(fullName);
            newUser.setAuthProvider(provider);
            newUser.setEnabled(true);
            List<Role> roles = new ArrayList<>();
            roles.add(roleService.findByName("ROLE_MEMBER"));
            newUser.setRoles(roles);
            repository.save(newUser);
        }
    }
    @Override
    public void updateExistUserAfterLoginSuccess(User user, String fullName) {
        user.setFullName(fullName);
        repository.save(user);
    }
}
