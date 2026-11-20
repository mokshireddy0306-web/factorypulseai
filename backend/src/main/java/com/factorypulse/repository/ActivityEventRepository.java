package com.factorypulse.repository;

import com.factorypulse.model.ActivityEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityEventRepository extends JpaRepository<ActivityEventEntity, String> {
    List<ActivityEventEntity> findTop20ByOrderByCreatedAtDesc();
}
