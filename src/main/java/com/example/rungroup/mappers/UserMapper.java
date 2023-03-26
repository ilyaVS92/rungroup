package com.example.rungroup.mappers;

import com.example.rungroup.dto.RegistrationDto;
import com.example.rungroup.entities.UserEntity;

public class UserMapper {
    public static UserEntity mapToUserEntity(RegistrationDto regDto){
        return UserEntity.builder()
        .userName(regDto.getUserName())
        .email(regDto.getEmail())
        .password(regDto.getPassword())
        .build();
    }

    public static RegistrationDto mapToRegDto(UserEntity user){
        return RegistrationDto.builder()
        .userName(user.getUserName())
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
    }
}
