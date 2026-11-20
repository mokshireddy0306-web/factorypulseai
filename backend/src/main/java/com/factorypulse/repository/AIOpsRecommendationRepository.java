package com.factorypulse.repository;

import com.factorypulse.model.AIOpsRecommendationEntity;
import com.factorypulse.model.enums.AlertSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIOpsRecommendationRepository extends JpaRepository<AIOpsRecommendationEntity, String> {
    List<AIOpsRecommendationEntity> findByMachineId(String machineId);
    List<AIOpsRecommendationEntity> findBySeverity(AlertSeverity severity);
}
