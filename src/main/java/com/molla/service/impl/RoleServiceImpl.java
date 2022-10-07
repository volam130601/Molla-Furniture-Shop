package com.molla.service.impl;

import com.molla.entity.Role;
import com.molla.repository.RoleRepository;
import com.molla.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void save(Role role) {
        repository.save(role);
    }
}
