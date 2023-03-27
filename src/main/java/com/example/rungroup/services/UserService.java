package com.example.rungroup.services;
import com.example.rungroup.dto.RegistrationDto;
import com.example.rungroup.entities.UserEntity;
import java.util.List;
public interface UserService {
    void save(RegistrationDto regDto);
    UserEntity findById (Long userId);
    UserEntity findByEmail (String email);
    UserEntity findByUserName (String userName);
    List<UserEntity> findAll ();
    void deleteUser (Long userId);
    boolean userRecordExists(String userName, String email);
    UserEntity getCurrentUserEntity();

}
