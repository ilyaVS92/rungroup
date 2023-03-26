package com.example.rungroup.services.implementations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.rungroup.dto.RegistrationDto;
import com.example.rungroup.entities.RoleEntity;
import com.example.rungroup.entities.UserEntity;
import com.example.rungroup.mappers.UserMapper;
import com.example.rungroup.repos.UserRepo;
import com.example.rungroup.services.RoleService;
import com.example.rungroup.services.UserService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;
    private RoleService roleSrv;
    private BCryptPasswordEncoder passwEncoder;

    @Override
    public UserEntity findById(Long userId) throws EntityNotFoundException{
        // try {
        //     return userRepo.findById(userId).get();
        // } catch (EntityNotFoundException e){
        //     System.out.println(e.getMessage()+" <- findByID() in UserServiceImpl");
        // }
        // // return user;
        // System.out.println("something went wrong<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return userRepo.findById(userId).orElseThrow();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userRepo.findByUserName(userName).orElseThrow();
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public void save(RegistrationDto regDto) {
        UserEntity user = UserMapper.mapToUserEntity(regDto);
        user.setPassword(passwEncoder.encode(regDto.getPassword()));     
        user.setRoles(Arrays.asList(roleSrv.findByName("USR")));
        userRepo.save(user);
    }

    @Override
    public boolean userRecordExists (String username, String email){
        if (userRepo.findByUserName(username).isPresent() || userRepo.findByEmail(email).isPresent()){
            return true;
        }
        return false;
    }
    public boolean userFieldsOk (String username, String email){

        return false;
    }
}
