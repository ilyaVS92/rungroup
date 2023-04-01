package com.example.rungroup.services;
import java.util.List;
import com.example.rungroup.entities.*;
import com.example.rungroup.dto.*;

public interface ClubService {
    ClubDto findById(Long id);
    List<ClubDto> findAll();
    void save(ClubDto clubDto);
    void updateClub(ClubDto club, Long clubId);
    void deleteByid(Long id);
    List<ClubDto> searchClubs(String query);
    ClubEntity findEntityById(Long id);
    boolean currentUserIsAuthor(Long clubId);
    boolean userHasAuthority(Long clubId);

}
