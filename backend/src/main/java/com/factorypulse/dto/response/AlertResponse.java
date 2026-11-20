package com.factorypulse.dto.response;

import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.AlertStatus;

public class AlertResponse {
    private String id;
    private String title;
    private String description;
    private AlertSeverity severity;
    private AlertStatus status;
    private String machineId;
    private String machineName;
    private String line;
    private String timestamp;
    private String triggerSignal;
    private String triggerValue;
    private String thresholdValue;
    private String recommendedAction;
    private String acknowledgedBy;
    private String resolvedAt;

    public AlertResponse() {}

    public AlertResponse(String id, String title, String description, AlertSeverity severity,
                         AlertStatus status, String machineId, String machineName, String line,
                         String timestamp, String triggerSignal, String triggerValue,
                         String thresholdValue, String recommendedAction, String acknowledgedBy, String resolvedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.machineId = machineId;
        this.machineName = machineName;
        this.line = line;
        this.timestamp = timestamp;
        this.triggerSignal = triggerSignal;
        this.triggerValue = triggerValue;
        this.thresholdValue = thresholdValue;
        this.recommendedAction = recommendedAction;
        this.acknowledgedBy = acknowledgedBy;
        this.resolvedAt = resolvedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private AlertSeverity severity;
        private AlertStatus status;
        private String machineId;
        private String machineName;
        private String line;
        private String timestamp;
        private String triggerSignal;
        private String triggerValue;
        private String thresholdValue;
        private String recommendedAction;
        private String acknowledgedBy;
        private String resolvedAt;

        public Builder id(String id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder severity(AlertSeverity severity) { this.severity = severity; return this; }
        public Builder status(AlertStatus status) { this.status = status; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder machineName(String machineName) { this.machineName = machineName; return this; }
        public Builder line(String line) { this.line = line; return this; }
        public Builder timestamp(String timestamp) { this.timestamp = timestamp; return this; }
        public Builder triggerSignal(String triggerSignal) { this.triggerSignal = triggerSignal; return this; }
        public Builder triggerValue(String triggerValue) { this.triggerValue = triggerValue; return this; }
        public Builder thresholdValue(String thresholdValue) { this.thresholdValue = thresholdValue; return this; }
        public Builder recommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; return this; }
        public Builder acknowledgedBy(String acknowledgedBy) { this.acknowledgedBy = acknowledgedBy; return this; }
        public Builder resolvedAt(String resolvedAt) { this.resolvedAt = resolvedAt; return this; }

        public AlertResponse build() {
            return new AlertResponse(id, title, description, severity, status, machineId, machineName,
                    line, timestamp, triggerSignal, triggerValue, thresholdValue, recommendedAction,
                    acknowledgedBy, resolvedAt);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public AlertSeverity getSeverity() { return severity; }
    public void setSeverity(AlertSeverity severity) { this.severity = severity; }
    public AlertStatus getStatus() { return status; }
    public void setStatus(AlertStatus status) { this.status = status; }
    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }
    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getTriggerSignal() { return triggerSignal; }
    public void setTriggerSignal(String triggerSignal) { this.triggerSignal = triggerSignal; }
    public String getTriggerValue() { return triggerValue; }
    public void setTriggerValue(String triggerValue) { this.triggerValue = triggerValue; }
    public String getThresholdValue() { return thresholdValue; }
    public void setThresholdValue(String thresholdValue) { this.thresholdValue = thresholdValue; }
    public String getRecommendedAction() { return recommendedAction; }
    public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }
    public String getAcknowledgedBy() { return acknowledgedBy; }
    public void setAcknowledgedBy(String acknowledgedBy) { this.acknowledgedBy = acknowledgedBy; }
    public String getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(String resolvedAt) { this.resolvedAt = resolvedAt; }
}
