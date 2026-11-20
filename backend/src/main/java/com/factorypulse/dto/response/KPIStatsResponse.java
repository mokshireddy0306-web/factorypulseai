package com.factorypulse.dto.response;

public class KPIStatsResponse {
    private Integer totalMachines;
    private Integer activeMachines;
    private Integer alertsCount;
    private Integer criticalIncidents;
    private Double factoryHealthScore;
    private Double oeeScore;
    private Double egressComplianceRate;
    private Double totalDataProcessedMb;
    private Double metadataTransmittedKb;

    public KPIStatsResponse() {}

    public KPIStatsResponse(Integer totalMachines, Integer activeMachines, Integer alertsCount,
                            Integer criticalIncidents, Double factoryHealthScore, Double oeeScore,
                            Double egressComplianceRate, Double totalDataProcessedMb, Double metadataTransmittedKb) {
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
        private Integer totalMachines;
        private Integer activeMachines;
        private Integer alertsCount;
        private Integer criticalIncidents;
        private Double factoryHealthScore;
        private Double oeeScore;
        private Double egressComplianceRate;
        private Double totalDataProcessedMb;
        private Double metadataTransmittedKb;

        public Builder totalMachines(Integer totalMachines) { this.totalMachines = totalMachines; return this; }
        public Builder activeMachines(Integer activeMachines) { this.activeMachines = activeMachines; return this; }
        public Builder alertsCount(Integer alertsCount) { this.alertsCount = alertsCount; return this; }
        public Builder criticalIncidents(Integer criticalIncidents) { this.criticalIncidents = criticalIncidents; return this; }
        public Builder factoryHealthScore(Double factoryHealthScore) { this.factoryHealthScore = factoryHealthScore; return this; }
        public Builder oeeScore(Double oeeScore) { this.oeeScore = oeeScore; return this; }
        public Builder egressComplianceRate(Double egressComplianceRate) { this.egressComplianceRate = egressComplianceRate; return this; }
        public Builder totalDataProcessedMb(Double totalDataProcessedMb) { this.totalDataProcessedMb = totalDataProcessedMb; return this; }
        public Builder metadataTransmittedKb(Double metadataTransmittedKb) { this.metadataTransmittedKb = metadataTransmittedKb; return this; }

        public KPIStatsResponse build() {
            return new KPIStatsResponse(totalMachines, activeMachines, alertsCount, criticalIncidents,
                    factoryHealthScore, oeeScore, egressComplianceRate, totalDataProcessedMb, metadataTransmittedKb);
        }
    }

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
}
