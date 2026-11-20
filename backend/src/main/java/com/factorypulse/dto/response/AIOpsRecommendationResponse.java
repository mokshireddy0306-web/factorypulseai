package com.factorypulse.dto.response;

import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.UrgencyLevel;

import java.util.List;

public class AIOpsRecommendationResponse {
    private String id;
    private String title;
    private String machineId;
    private String machineName;
    private String line;
    private AlertSeverity severity;
    private Integer confidenceScore;
    private List<ContributingSignalResponse> contributingSignals;
    private String rootCause;
    private String recommendedAction;
    private String estimatedTimeToFailure;
    private String financialImpactPrevented;
    private Double downtimePreventedHours;
    private UrgencyLevel urgency;
    private String modelAlgorithm;
    private Boolean dispatched;

    public AIOpsRecommendationResponse() {}

    public AIOpsRecommendationResponse(String id, String title, String machineId, String machineName,
                                      String line, AlertSeverity severity, Integer confidenceScore,
                                      List<ContributingSignalResponse> contributingSignals, String rootCause,
                                      String recommendedAction, String estimatedTimeToFailure,
                                      String financialImpactPrevented, Double downtimePreventedHours,
                                      UrgencyLevel urgency, String modelAlgorithm, Boolean dispatched) {
        this.id = id;
        this.title = title;
        this.machineId = machineId;
        this.machineName = machineName;
        this.line = line;
        this.severity = severity;
        this.confidenceScore = confidenceScore;
        this.contributingSignals = contributingSignals;
        this.rootCause = rootCause;
        this.recommendedAction = recommendedAction;
        this.estimatedTimeToFailure = estimatedTimeToFailure;
        this.financialImpactPrevented = financialImpactPrevented;
        this.downtimePreventedHours = downtimePreventedHours;
        this.urgency = urgency;
        this.modelAlgorithm = modelAlgorithm;
        this.dispatched = dispatched;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private String machineId;
        private String machineName;
        private String line;
        private AlertSeverity severity;
        private Integer confidenceScore;
        private List<ContributingSignalResponse> contributingSignals;
        private String rootCause;
        private String recommendedAction;
        private String estimatedTimeToFailure;
        private String financialImpactPrevented;
        private Double downtimePreventedHours;
        private UrgencyLevel urgency;
        private String modelAlgorithm;
        private Boolean dispatched;

        public Builder id(String id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder machineName(String machineName) { this.machineName = machineName; return this; }
        public Builder line(String line) { this.line = line; return this; }
        public Builder severity(AlertSeverity severity) { this.severity = severity; return this; }
        public Builder confidenceScore(Integer confidenceScore) { this.confidenceScore = confidenceScore; return this; }
        public Builder contributingSignals(List<ContributingSignalResponse> contributingSignals) { this.contributingSignals = contributingSignals; return this; }
        public Builder rootCause(String rootCause) { this.rootCause = rootCause; return this; }
        public Builder recommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; return this; }
        public Builder estimatedTimeToFailure(String estimatedTimeToFailure) { this.estimatedTimeToFailure = estimatedTimeToFailure; return this; }
        public Builder financialImpactPrevented(String financialImpactPrevented) { this.financialImpactPrevented = financialImpactPrevented; return this; }
        public Builder downtimePreventedHours(Double downtimePreventedHours) { this.downtimePreventedHours = downtimePreventedHours; return this; }
        public Builder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
        public Builder modelAlgorithm(String modelAlgorithm) { this.modelAlgorithm = modelAlgorithm; return this; }
        public Builder dispatched(Boolean dispatched) { this.dispatched = dispatched; return this; }

        public AIOpsRecommendationResponse build() {
            return new AIOpsRecommendationResponse(id, title, machineId, machineName, line, severity,
                    confidenceScore, contributingSignals, rootCause, recommendedAction,
                    estimatedTimeToFailure, financialImpactPrevented, downtimePreventedHours, urgency,
                    modelAlgorithm, dispatched);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }
    public String getLine() { return line; }
    public void setLine(String line) { this.line = line; }
    public AlertSeverity getSeverity() { return severity; }
    public void setSeverity(AlertSeverity severity) { this.severity = severity; }
    public Integer getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Integer confidenceScore) { this.confidenceScore = confidenceScore; }
    public List<ContributingSignalResponse> getContributingSignals() { return contributingSignals; }
    public void setContributingSignals(List<ContributingSignalResponse> contributingSignals) { this.contributingSignals = contributingSignals; }
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }
    public String getRecommendedAction() { return recommendedAction; }
    public void setRecommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; }
    public String getEstimatedTimeToFailure() { return estimatedTimeToFailure; }
    public void setEstimatedTimeToFailure(String estimatedTimeToFailure) { this.estimatedTimeToFailure = estimatedTimeToFailure; }
    public String getFinancialImpactPrevented() { return financialImpactPrevented; }
    public void setFinancialImpactPrevented(String financialImpactPrevented) { this.financialImpactPrevented = financialImpactPrevented; }
    public Double getDowntimePreventedHours() { return downtimePreventedHours; }
    public void setDowntimePreventedHours(Double downtimePreventedHours) { this.downtimePreventedHours = downtimePreventedHours; }
    public UrgencyLevel getUrgency() { return urgency; }
    public void setUrgency(UrgencyLevel urgency) { this.urgency = urgency; }
    public String getModelAlgorithm() { return modelAlgorithm; }
    public void setModelAlgorithm(String modelAlgorithm) { this.modelAlgorithm = modelAlgorithm; }
    public Boolean getDispatched() { return dispatched; }
    public void setDispatched(Boolean dispatched) { this.dispatched = dispatched; }
}
