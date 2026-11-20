package com.factorypulse.model;

import com.factorypulse.model.enums.ConnectivityStatus;
import com.factorypulse.model.enums.HealthState;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "machines")
public class MachineEntity {

    @Id
    @Column(length = 50)
    private String id; // e.g. "M01"

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String line;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Double temperature;

    @Column(name = "temp_max", nullable = false)
    private Double tempMax;

    @Column(name = "power_usage", nullable = false)
    private Double powerUsage;

    @Column(name = "power_max", nullable = false)
    private Double powerMax;

    @Column(nullable = false)
    private Double vibration;

    @Column(name = "vibration_max", nullable = false)
    private Double vibrationMax;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ConnectivityStatus connectivity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HealthState health;

    @Column(name = "health_score", nullable = false)
    private Integer healthScore;

    @Column(nullable = false)
    private Double uptime;

    @Column(name = "operating_hours", nullable = false)
    private Integer operatingHours;

    @Column(name = "last_maintenance")
    private String lastMaintenance;

    @Column(name = "next_scheduled_maintenance")
    private String nextScheduledMaintenance;

    // Restricted On-Premise Enclave Fields
    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "proprietary_code")
    private String proprietaryCode;

    @Column(name = "internal_ip")
    private String internalIp;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("recordedAt ASC")
    private List<TelemetryRecordEntity> telemetryHistory = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public MachineEntity() {}

    public MachineEntity(String id, String name, String line, String model, Double temperature, Double tempMax,
                         Double powerUsage, Double powerMax, Double vibration, Double vibrationMax,
                         ConnectivityStatus connectivity, HealthState health, Integer healthScore, Double uptime,
                         Integer operatingHours, String lastMaintenance, String nextScheduledMaintenance,
                         String operatorId, String proprietaryCode, String internalIp) {
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
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String line;
        private String model;
        private Double temperature = 65.0;
        private Double tempMax = 85.0;
        private Double powerUsage = 35.0;
        private Double powerMax = 50.0;
        private Double vibration = 2.5;
        private Double vibrationMax = 4.5;
        private ConnectivityStatus connectivity = ConnectivityStatus.ONLINE;
        private HealthState health = HealthState.GOOD;
        private Integer healthScore = 90;
        private Double uptime = 99.0;
        private Integer operatingHours = 0;
        private String lastMaintenance;
        private String nextScheduledMaintenance;
        private String operatorId;
        private String proprietaryCode;
        private String internalIp;

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

        public MachineEntity build() {
            return new MachineEntity(id, name, line, model, temperature, tempMax, powerUsage, powerMax,
                    vibration, vibrationMax, connectivity, health, healthScore, uptime, operatingHours,
                    lastMaintenance, nextScheduledMaintenance, operatorId, proprietaryCode, internalIp);
        }
    }

    // Getters and Setters
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
    public List<TelemetryRecordEntity> getTelemetryHistory() { return telemetryHistory; }
    public void setTelemetryHistory(List<TelemetryRecordEntity> telemetryHistory) { this.telemetryHistory = telemetryHistory; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
