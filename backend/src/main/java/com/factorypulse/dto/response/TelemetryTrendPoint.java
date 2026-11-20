package com.factorypulse.dto.response;

public class TelemetryTrendPoint {
    private String time;
    private Double avgTemp;
    private Double totalPowerKw;
    private Double avgVibration;
    private Integer anomalyEvents;

    public TelemetryTrendPoint() {}

    public TelemetryTrendPoint(String time, Double avgTemp, Double totalPowerKw, Double avgVibration, Integer anomalyEvents) {
        this.time = time;
        this.avgTemp = avgTemp;
        this.totalPowerKw = totalPowerKw;
        this.avgVibration = avgVibration;
        this.anomalyEvents = anomalyEvents;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String time;
        private Double avgTemp;
        private Double totalPowerKw;
        private Double avgVibration;
        private Integer anomalyEvents;

        public Builder time(String time) { this.time = time; return this; }
        public Builder avgTemp(Double avgTemp) { this.avgTemp = avgTemp; return this; }
        public Builder totalPowerKw(Double totalPowerKw) { this.totalPowerKw = totalPowerKw; return this; }
        public Builder avgVibration(Double avgVibration) { this.avgVibration = avgVibration; return this; }
        public Builder anomalyEvents(Integer anomalyEvents) { this.anomalyEvents = anomalyEvents; return this; }

        public TelemetryTrendPoint build() {
            return new TelemetryTrendPoint(time, avgTemp, totalPowerKw, avgVibration, anomalyEvents);
        }
    }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public Double getAvgTemp() { return avgTemp; }
    public void setAvgTemp(Double avgTemp) { this.avgTemp = avgTemp; }
    public Double getTotalPowerKw() { return totalPowerKw; }
    public void setTotalPowerKw(Double totalPowerKw) { this.totalPowerKw = totalPowerKw; }
    public Double getAvgVibration() { return vibration(); }
    public Double getVibration() { return avgVibration; }
    public void setAvgVibration(Double avgVibration) { this.avgVibration = avgVibration; }
    public Integer getAnomalyEvents() { return anomalyEvents; }
    public void setAnomalyEvents(Integer anomalyEvents) { this.anomalyEvents = anomalyEvents; }

    // Helper alias
    private Double vibration() { return avgVibration; }
}
