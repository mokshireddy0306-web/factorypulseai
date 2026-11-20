package com.factorypulse.repository;

import com.factorypulse.model.EgressRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EgressRecordRepository extends JpaRepository<EgressRecordEntity, String> {
    List<EgressRecordEntity> findByMachineId(String machineId);
    List<EgressRecordEntity> findAllByOrderByCreatedAtDesc();
}
