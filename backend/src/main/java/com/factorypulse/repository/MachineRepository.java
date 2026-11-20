package com.factorypulse.repository;

import com.factorypulse.model.MachineEntity;
import com.factorypulse.model.enums.HealthState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<MachineEntity, String> {

    List<MachineEntity> findByHealth(HealthState health);

    List<MachineEntity> findByLine(String line);

    @Query("SELECT m FROM MachineEntity m WHERE " +
           "(:health IS NULL OR m.health = :health) AND " +
           "(:line IS NULL OR :line = 'ALL' OR m.line = :line) AND " +
           "(:search IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           " OR LOWER(m.id) LIKE LOWER(CONCAT('%', :search, '%')) " +
           " OR LOWER(m.line) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<MachineEntity> searchMachines(
            @Param("health") HealthState health,
            @Param("line") String line,
            @Param("search") String search);

    long countByHealth(HealthState health);
}
