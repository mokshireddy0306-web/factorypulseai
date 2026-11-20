package com.factorypulse.repository;

import com.factorypulse.model.AlertEntity;
import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, String> {

    List<AlertEntity> findByStatusOrderByCreatedAtDesc(AlertStatus status);

    List<AlertEntity> findBySeverity(AlertSeverity severity);

    long countBySeverityAndStatus(AlertSeverity severity, AlertStatus status);

    long countByStatus(AlertStatus status);

    @Query("SELECT a FROM AlertEntity a WHERE " +
           "(:severity IS NULL OR a.severity = :severity) AND " +
           "(:status IS NULL OR a.status = :status) AND " +
           "(:search IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           " OR LOWER(a.machineName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           " OR LOWER(a.machineId) LIKE LOWER(CONCAT('%', :search, '%')) " +
           " OR LOWER(a.id) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY a.createdAt DESC")
    List<AlertEntity> searchAlerts(
            @Param("severity") AlertSeverity severity,
            @Param("status") AlertStatus status,
            @Param("search") String search);
}
