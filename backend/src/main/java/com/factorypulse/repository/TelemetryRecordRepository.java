package com.factorypulse.repository;

import com.factorypulse.model.TelemetryRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelemetryRecordRepository extends JpaRepository<TelemetryRecordEntity, Long> {
    List<TelemetryRecordEntity> findByMachineIdOrderByRecordedAtAsc(String machineId);
}
