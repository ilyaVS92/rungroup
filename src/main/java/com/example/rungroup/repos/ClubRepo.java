package com.example.rungroup.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.rungroup.entities.*;

@Repository
public interface ClubRepo extends JpaRepository<ClubEntity,Long>{
    List<ClubEntity> findAll();

    @Query(value ="SELECT * from club_entity WHERE club_entity.title LIKE CONCAT('%', :query, '%')", nativeQuery=true)
    List<ClubEntity> searchClub(String query);
}
