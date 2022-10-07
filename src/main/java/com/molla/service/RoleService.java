package com.molla.service;

import com.molla.entity.Role;

public interface RoleService {
    Role findByName(String name);

    void save(Role role);
}
