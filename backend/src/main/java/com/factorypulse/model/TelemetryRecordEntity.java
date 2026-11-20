package com.factorypulse.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "telemetry_records")
public class TelemetryRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private MachineEntity machine;

    @Column(name = "time_label", nullable = false, length = 30)
    private String timeLabel;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private Double power;

    @Column(nullable = false)
    private Double vibration;

    @Column(name = "recorded_at")
    private Instant recordedAt;

    public TelemetryRecordEntity() {}

    public TelemetryRecordEntity(Long id, MachineEntity machine, String timeLabel, Double temperature, Double power, Double vibration, Instant recordedAt) {
        this.id = id;
        this.machine = machine;
        this.timeLabel = timeLabel;
        this.temperature = temperature;
        this.power = power;
        this.vibration = vibration;
        this.recordedAt = recordedAt;
    }

    @PrePersist
    protected void onCreate() {
        if (recordedAt == null) {
            recordedAt = Instant.now();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private MachineEntity machine;
        private String timeLabel;
        private Double temperature;
        private Double power;
        private Double vibration;
        private Instant recordedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder machine(MachineEntity machine) { this.machine = machine; return this; }
        public Builder timeLabel(String timeLabel) { this.timeLabel = timeLabel; return this; }
        public Builder temperature(Double temperature) { this.temperature = temperature; return this; }
        public Builder power(Double power) { this.power = power; return this; }
        public Builder vibration(Double vibration) { this.vibration = vibration; return this; }
        public Builder recordedAt(Instant recordedAt) { this.recordedAt = recordedAt; return this; }

        public TelemetryRecordEntity build() {
            return new TelemetryRecordEntity(id, machine, timeLabel, temperature, power, vibration, recordedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public MachineEntity getMachine() { return machine; }
    public void setMachine(MachineEntity machine) { this.machine = machine; }
    public String getTimeLabel() { return timeLabel; }
    public void setTimeLabel(String timeLabel) { this.timeLabel = timeLabel; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getPower() { return power; }
    public void setPower(Double power) { this.power = power; }
    public Double getVibration() { return vibration; }
    public void setVibration(Double vibration) { this.vibration = vibration; }
    public Instant getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Instant recordedAt) { this.recordedAt = recordedAt; }
}
