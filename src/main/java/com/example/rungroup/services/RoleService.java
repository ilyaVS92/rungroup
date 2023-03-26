package com.example.rungroup.services;
import com.example.rungroup.entities.RoleEntity;
import java.util.Optional;
import java.util.List;

public interface RoleService {
    void save(RoleEntity role);
    RoleEntity findById(Long id);
    List<RoleEntity> findAll();
    void delete(Long id);
    RoleEntity findByName(String name);    
}
