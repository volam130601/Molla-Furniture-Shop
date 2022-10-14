package com.molla.service;

import com.molla.dto.AuthenticationProvider;
import com.molla.entity.User;

public interface UserService {
    User save(User user);

    User findByEmail(String email);
    User findByEmailAndAuthProvider(String email , AuthenticationProvider authProvider);


    void deleteById(Long userId);

    void processOAuthPostLogin(String email , String fullName, AuthenticationProvider provider);

    void updateExistUserAfterLoginSuccess(User user, String fullName);
}
