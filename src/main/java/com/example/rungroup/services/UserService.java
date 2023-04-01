package com.example.rungroup.services;
import com.example.rungroup.dto.*;
import com.example.rungroup.entities.*;
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
    boolean userIsAdmin();
    // <T extends EventContainer> boolean currentUserIsAuthor(T event, Long id);
    // boolean currentUserIsAuthor (EventDto entity, Long id);
    // boolean currentUserIsAuthor (EventEntity entity);
    // boolean currentUserIsAuthor (ClubEntity entity);
    // boolean currentUserIsAuthor (ClubDto entity, Long id);
    // UserEntity mapToUserEntity(RegistrationDto regDto);
    // RegistrationDto mapToRegDto(UserEntity user);

}
