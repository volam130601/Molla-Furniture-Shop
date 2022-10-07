package com.molla.service.impl;

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
}
