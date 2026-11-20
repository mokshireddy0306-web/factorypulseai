package com.factorypulse.dto.response;

import com.factorypulse.model.enums.ConnectivityStatus;
import com.factorypulse.model.enums.HealthState;

import java.util.List;

public class MachineResponse {
    private String id;
    private String name;
    private String line;
    private String model;
    private Double temperature;
    private Double tempMax;
    private Double powerUsage;
    private Double powerMax;
    private Double vibration;
    private Double vibrationMax;
    private ConnectivityStatus connectivity;
    private HealthState health;
    private Integer healthScore;
    private Double uptime;
    private Integer operatingHours;
    private String lastMaintenance;
    private String nextScheduledMaintenance;
    private String operatorId;
    private String proprietaryCode;
    private String internalIp;
    private List<TelemetryHistoryPoint> telemetryHistory;

    public MachineResponse() {}

    public MachineResponse(String id, String name, String line, String model, Double temperature,
                           Double tempMax, Double powerUsage, Double powerMax, Double vibration,
                           Double vibrationMax, ConnectivityStatus connectivity, HealthState health,
                           Integer healthScore, Double uptime, Integer operatingHours, String lastMaintenance,
                           String nextScheduledMaintenance, String operatorId, String proprietaryCode,
                           String internalIp, List<TelemetryHistoryPoint> telemetryHistory) {
        this.id = id;
        this.name = name;
        this.line = line;
        this.model = model;
        this.temperature = temperature;
        this.tempMax = tempMax;
        this.powerUsage = powerUsage;
        this.powerMax = powerMax;
        this.vibration = vibration;
        this.vibrationMax = vibrationMax;
        this.connectivity = connectivity;
        this.health = health;
        this.healthScore = healthScore;
        this.uptime = uptime;
        this.operatingHours = operatingHours;
        this.lastMaintenance = lastMaintenance;
        this.nextScheduledMaintenance = nextScheduledMaintenance;
        this.operatorId = operatorId;
        this.proprietaryCode = proprietaryCode;
        this.internalIp = internalIp;
        this.telemetryHistory = telemetryHistory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String line;
        private String model;
        private Double temperature;
        private Double tempMax;
        private Double powerUsage;
        private Double powerMax;
        private Double vibration;
        private Double vibrationMax;
        private ConnectivityStatus connectivity;
        private HealthState health;
        private Integer healthScore;
        private Double uptime;
        private Integer operatingHours;
        private String lastMaintenance;
        private String nextScheduledMaintenance;
        private String operatorId;
        private String proprietaryCode;
        private String internalIp;
        private List<TelemetryHistoryPoint> telemetryHistory;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder line(String line) { this.line = line; return this; }
        public Builder model(String model) { this.model = model; return this; }
        public Builder temperature(Double temperature) { this.temperature = temperature; return this; }
        public Builder tempMax(Double tempMax) { this.tempMax = tempMax; return this; }
        public Builder powerUsage(Double powerUsage) { this.powerUsage = powerUsage; return this; }
        public Builder powerMax(Double powerMax) { this.powerMax = powerMax; return this; }
        public Builder vibration(Double vibration) { this.vibration = vibration; return this; }
        public Builder vibrationMax(Double vibrationMax) { this.vibrationMax = vibrationMax; return this; }
        public Builder connectivity(ConnectivityStatus connectivity) { this.connectivity = connectivity; return this; }
        public Builder health(HealthState health) { this.health = health; return this; }
        public Builder healthScore(Integer healthScore) { this.healthScore = healthScore; return this; }
        public Builder uptime(Double uptime) { this.uptime = uptime; return this; }
        public Builder operatingHours(Integer operatingHours) { this.operatingHours = operatingHours; return this; }
        public Builder lastMaintenance(String lastMaintenance) { this.lastMaintenance = lastMaintenance; return this; }
        public Builder nextScheduledMaintenance(String nextScheduledMaintenance) { this.nextScheduledMaintenance = nextScheduledMaintenance; return this; }
        public Builder operatorId(String operatorId) { this.operatorId = operatorId; return this; }
        public Builder proprietaryCode(String proprietaryCode) { this.proprietaryCode = proprietaryCode; return this; }
        public Builder internalIp(String internalIp) { this.internalIp = internalIp; return this; }
        public Builder telemetryHistory(List<TelemetryHistoryPoint> telemetryHistory) { this.telemetryHistory = telemetryHistory; return this; }

        public MachineResponse build() {
            return new MachineResponse(id, name, line, model, temperature, tempMax, powerUsage, powerMax,
                    vibration, vibrationMax, connectivity, health, healthScore, uptime, operatingHours,
                    lastMaintenance, nextScheduledMaintenance, operatorId, proprietaryCode, internalIp, telemetryHistory);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getTempMax() { return tempMax; }
    public void setTempMax(Double tempMax) { this.tempMax = tempMax; }
    public Double getPowerUsage() { return powerUsage; }
    public void setPowerUsage(Double powerUsage) { this.powerUsage = powerUsage; }
    public Double getPowerMax() { return powerMax; }
    public void setPowerMax(Double powerMax) { this.powerMax = powerMax; }
    public Double getVibration() { return vibration; }
    public void setVibration(Double vibration) { this.vibration = vibration; }
    public Double getVibrationMax() { return vibrationMax; }
    public void setVibrationMax(Double vibrationMax) { this.vibrationMax = vibrationMax; }
    public ConnectivityStatus getConnectivity() { return connectivity; }
    public void setConnectivity(ConnectivityStatus connectivity) { this.connectivity = connectivity; }
    public HealthState getHealth() { return health; }
    public void setHealth(HealthState health) { this.health = health; }
    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }
    public Double getUptime() { return uptime; }
    public void setUptime(Double uptime) { this.uptime = uptime; }
    public Integer getOperatingHours() { return operatingHours; }
    public void setOperatingHours(Integer operatingHours) { this.operatingHours = operatingHours; }
    public String getLastMaintenance() { return lastMaintenance; }
    public void setLastMaintenance(String lastMaintenance) { this.lastMaintenance = lastMaintenance; }
    public String getNextScheduledMaintenance() { return nextScheduledMaintenance; }
    public void setNextScheduledMaintenance(String nextScheduledMaintenance) { this.nextScheduledMaintenance = nextScheduledMaintenance; }
    public String getOperatorId() { return operatorId; }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId; }
    public String getProprietaryCode() { return proprietaryCode; }
    public void setProprietaryCode(String proprietaryCode) { this.proprietaryCode = proprietaryCode; }
    public String getInternalIp() { return internalIp; }
    public void setInternalIp(String internalIp) { this.internalIp = internalIp; }
    public List<TelemetryHistoryPoint> getTelemetryHistory() { return telemetryHistory; }
    public void setTelemetryHistory(List<TelemetryHistoryPoint> telemetryHistory) { this.telemetryHistory = telemetryHistory; }
}
