package com.molla.service.impl;

import com.molla.dto.AuthenticationProvider;
import com.molla.entity.User;
import com.molla.repository.UserRepository;
import com.molla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
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

            repository.save(newUser);
        }
    }
    @Override
    public void updateExistUserAfterLoginSuccess(User user, String fullName, AuthenticationProvider provider) {
        user.setFullName(fullName);
        user.setAuthProvider(provider);
        repository.save(user);
    }
}
