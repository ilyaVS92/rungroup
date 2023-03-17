package com.example.rungroup.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.rungroup.entities.Event;
import java.util.List;


@Repository
public interface EventRepo extends JpaRepository<Event, Long>{
    public List<Event> findAllByClubId(Long id);
}
