package com.factorypulse.dto.response;

public class TelemetryHistoryPoint {
    private String time;
    private Double temperature;
    private Double power;
    private Double vibration;

    public TelemetryHistoryPoint() {}

    public TelemetryHistoryPoint(String time, Double temperature, Double power, Double vibration) {
        this.time = time;
        this.temperature = temperature;
        this.power = power;
        this.vibration = vibration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String time;
        private Double temperature;
        private Double power;
        private Double vibration;

        public Builder time(String time) { this.time = time; return this; }
        public Builder temperature(Double temperature) { this.temperature = temperature; return this; }
        public Builder power(Double power) { this.power = power; return this; }
        public Builder vibration(Double vibration) { this.vibration = vibration; return this; }

        public TelemetryHistoryPoint build() {
            return new TelemetryHistoryPoint(time, temperature, power, vibration);
        }
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public Double getPower() { return power; }
    public void setPower(Double power) { this.power = power; }
    public Double getVibration() { return vibration; }
    public void setVibration(Double vibration) { this.vibration = vibration; }
}
