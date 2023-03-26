package com.example.rungroup.repos;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rungroup.entities.RoleEntity;
import com.example.rungroup.entities.UserEntity;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName (String name);
}
