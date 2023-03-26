package com.example.rungroup.repos;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.rungroup.entities.*;

import com.example.rungroup.entities.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findById (Long userId);
    Optional<UserEntity> findByEmail (String email);
    Optional<UserEntity> findByUserName (String userName);
    UserEntity findFirstByUserName (String userName);

}
