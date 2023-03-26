package com.example.rungroup.services.implementations;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.rungroup.entities.RoleEntity;
import com.example.rungroup.repos.RoleRepo;
import com.example.rungroup.services.RoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepo roleRepo;

    @Override
    public void save(RoleEntity role) {
        roleRepo.save(role);
    }

    @Override
    public RoleEntity findById(Long id) {
        return roleRepo.findById(id).orElseThrow();
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleRepo.findByName(name).orElseThrow();
    }
    
}
