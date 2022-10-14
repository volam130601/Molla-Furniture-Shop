package com.molla.repository;

import com.molla.dto.AuthenticationProvider;
import com.molla.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndAuthProvider(String email , AuthenticationProvider authProvider);
}
