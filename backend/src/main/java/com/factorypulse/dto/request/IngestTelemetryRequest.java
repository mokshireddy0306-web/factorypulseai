package com.factorypulse.dto.request;

import jakarta.validation.constraints.NotNull;

public class IngestTelemetryRequest {

    private String timeLabel;

    @NotNull(message = "Temperature reading is required")
    private Double temperature;

    @NotNull(message = "Power usage reading is required")
    private Double power;

    @NotNull(message = "Vibration reading is required")
    private Double vibration;

    public IngestTelemetryRequest() {}

    public IngestTelemetryRequest(String timeLabel, Double temperature, Double power, Double vibration) {
        this.timeLabel = timeLabel;
        this.temperature = temperature;
        this.power = power;
        this.vibration = vibration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String timeLabel;
        private Double temperature;
        private Double power;
        private Double vibration;

        public Builder timeLabel(String timeLabel) { this.timeLabel = timeLabel; return this; }
        public Builder temperature(Double temperature) { this.temperature = temperature; return this; }
        public Builder power(Double power) { this.power = power; return this; }
        public Builder vibration(Double vibration) { this.vibration = vibration; return this; }

        public IngestTelemetryRequest build() {
            return new IngestTelemetryRequest(timeLabel, temperature, power, vibration);
        }
    }

    public String getTimeLabel() { return timeLabel; }
    public void setTimeLabel(String timeLabel) { this.timeLabel = timeLabel; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getPower() { return power; }
    public void setPower(Double power) { this.power = power; }
    public Double getVibration() { return vibration; }
    public void setVibration(Double vibration) { this.vibration = vibration; }
}
