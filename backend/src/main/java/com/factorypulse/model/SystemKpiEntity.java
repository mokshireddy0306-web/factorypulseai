package com.factorypulse.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "system_kpis")
public class SystemKpiEntity {

    @Id
    private Integer id = 1;

    @Column(name = "total_machines", nullable = false)
    private Integer totalMachines;

    @Column(name = "active_machines", nullable = false)
    private Integer activeMachines;

    @Column(name = "alerts_count", nullable = false)
    private Integer alertsCount;

    @Column(name = "critical_incidents", nullable = false)
    private Integer criticalIncidents;

    @Column(name = "factory_health_score", nullable = false)
    private Double factoryHealthScore;

    @Column(name = "oee_score", nullable = false)
    private Double oeeScore;

    @Column(name = "egress_compliance_rate", nullable = false)
    private Double egressComplianceRate;

    @Column(name = "total_data_processed_mb", nullable = false)
    private Double totalDataProcessedMb;

    @Column(name = "metadata_transmitted_kb", nullable = false)
    private Double metadataTransmittedKb;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public SystemKpiEntity() {}

    public SystemKpiEntity(Integer id, Integer totalMachines, Integer activeMachines, Integer alertsCount,
                           Integer criticalIncidents, Double factoryHealthScore, Double oeeScore,
                           Double egressComplianceRate, Double totalDataProcessedMb, Double metadataTransmittedKb) {
        this.id = id != null ? id : 1;
        this.totalMachines = totalMachines;
        this.activeMachines = activeMachines;
        this.alertsCount = alertsCount;
        this.criticalIncidents = criticalIncidents;
        this.factoryHealthScore = factoryHealthScore;
        this.oeeScore = oeeScore;
        this.egressComplianceRate = egressComplianceRate;
        this.totalDataProcessedMb = totalDataProcessedMb;
        this.metadataTransmittedKb = metadataTransmittedKb;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id = 1;
        private Integer totalMachines;
        private Integer activeMachines;
        private Integer alertsCount;
        private Integer criticalIncidents;
        private Double factoryHealthScore;
        private Double oeeScore;
        private Double egressComplianceRate;
        private Double totalDataProcessedMb;
        private Double metadataTransmittedKb;

        public Builder id(Integer id) { this.id = id; return this; }
        public Builder totalMachines(Integer totalMachines) { this.totalMachines = totalMachines; return this; }
        public Builder activeMachines(Integer activeMachines) { this.activeMachines = activeMachines; return this; }
        public Builder alertsCount(Integer alertsCount) { this.alertsCount = alertsCount; return this; }
        public Builder criticalIncidents(Integer criticalIncidents) { this.criticalIncidents = criticalIncidents; return this; }
        public Builder factoryHealthScore(Double factoryHealthScore) { this.factoryHealthScore = factoryHealthScore; return this; }
        public Builder oeeScore(Double oeeScore) { this.oeeScore = oeeScore; return this; }
        public Builder egressComplianceRate(Double egressComplianceRate) { this.egressComplianceRate = egressComplianceRate; return this; }
        public Builder totalDataProcessedMb(Double totalDataProcessedMb) { this.totalDataProcessedMb = totalDataProcessedMb; return this; }
        public Builder metadataTransmittedKb(Double metadataTransmittedKb) { this.metadataTransmittedKb = metadataTransmittedKb; return this; }

        public SystemKpiEntity build() {
            return new SystemKpiEntity(id, totalMachines, activeMachines, alertsCount, criticalIncidents,
                    factoryHealthScore, oeeScore, egressComplianceRate, totalDataProcessedMb, metadataTransmittedKb);
        }
    }

    @PrePersist
    @PreUpdate
    protected void onSave() {
        updatedAt = Instant.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getTotalMachines() { return totalMachines; }
    public void setTotalMachines(Integer totalMachines) { this.totalMachines = totalMachines; }
    public Integer getActiveMachines() { return activeMachines; }
    public void setActiveMachines(Integer activeMachines) { this.activeMachines = activeMachines; }
    public Integer getAlertsCount() { return alertsCount; }
    public void setAlertsCount(Integer alertsCount) { this.alertsCount = alertsCount; }
    public Integer getCriticalIncidents() { return criticalIncidents; }
    public void setCriticalIncidents(Integer criticalIncidents) { this.criticalIncidents = criticalIncidents; }
    public Double getFactoryHealthScore() { return factoryHealthScore; }
    public void setFactoryHealthScore(Double factoryHealthScore) { this.factoryHealthScore = factoryHealthScore; }
    public Double getOeeScore() { return oeeScore; }
    public void setOeeScore(Double oeeScore) { this.oeeScore = oeeScore; }
    public Double getEgressComplianceRate() { return egressComplianceRate; }
    public void setEgressComplianceRate(Double egressComplianceRate) { this.egressComplianceRate = egressComplianceRate; }
    public Double getTotalDataProcessedMb() { return totalDataProcessedMb; }
    public void setTotalDataProcessedMb(Double totalDataProcessedMb) { this.totalDataProcessedMb = totalDataProcessedMb; }
    public Double getMetadataTransmittedKb() { return metadataTransmittedKb; }
    public void setMetadataTransmittedKb(Double metadataTransmittedKb) { this.metadataTransmittedKb = metadataTransmittedKb; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
