package com.example.rungroup.services.implementations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.rungroup.dto.ClubDto;
import com.example.rungroup.dto.EventDto;
import com.example.rungroup.dto.RegistrationDto;
import com.example.rungroup.entities.RoleEntity;
import com.example.rungroup.mappers.UserMapper;
import com.example.rungroup.entities.*;
import com.example.rungroup.repos.UserRepo;
import com.example.rungroup.security.SecurityUtil;
import com.example.rungroup.services.*;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepo userRepo;
    private RoleService roleSrv;
    // private EventService eventSrv;
    // private ClubService clubSrv;
    private BCryptPasswordEncoder passwEncoder;

    @Override
    public UserEntity findById(Long userId) throws EntityNotFoundException{
        return userRepo.findById(userId).orElseThrow();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow();
    }

    @Override
    public UserEntity findByUserName(String userName) {
        System.out.println("---------------> UserServImpl.findByUsername = "+userName);
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

    @Override
    public UserEntity getCurrentUserEntity(){
        UserEntity user = SecurityUtil.getSessionUser().equals("dne") 
        ? new UserEntity() 
        : findByUserName(SecurityUtil.getSessionUser());
        // UserEntity user = SecurityUtil.getSessionUser().equals("dne") ? null : findByUserName(SecurityUtil.getSessionUser());
        System.out.println("---------------> UserServiceImpl.getCurrentUserEntity() found user with id = "+user.getId());
        return user;
        
    }

    @Override
    public boolean userIsAdmin(){
        return getCurrentUserEntity().getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName()));
    }
}
