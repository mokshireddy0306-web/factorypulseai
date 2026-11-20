package com.factorypulse.repository;

import com.factorypulse.model.SystemKpiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemKpiRepository extends JpaRepository<SystemKpiEntity, Integer> {
}
