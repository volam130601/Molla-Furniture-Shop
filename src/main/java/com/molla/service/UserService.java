package com.molla.service;

import com.molla.entity.User;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    void deleteById(Long userId);
}
