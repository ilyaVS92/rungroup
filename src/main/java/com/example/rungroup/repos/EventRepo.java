package com.example.rungroup.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.rungroup.entities.EventEntity;
import java.util.List;


@Repository
public interface EventRepo extends JpaRepository<EventEntity, Long>{
    public List<EventEntity> findAllByClubId(Long id);
}
