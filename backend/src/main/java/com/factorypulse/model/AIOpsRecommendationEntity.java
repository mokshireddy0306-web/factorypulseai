package com.factorypulse.model;

import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.UrgencyLevel;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aiops_recommendations")
public class AIOpsRecommendationEntity {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "machine_id", nullable = false, length = 50)
    private String machineId;

    @Column(name = "machine_name", nullable = false)
    private String machineName;

    @Column(nullable = false)
    private String line;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AlertSeverity severity;

    @Column(name = "confidence_score", nullable = false)
    private Integer confidenceScore;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ContributingSignalEntity> contributingSignals = new ArrayList<>();

    @Column(name = "root_cause", columnDefinition = "TEXT", nullable = false)
    private String rootCause;

    @Column(name = "recommended_action", columnDefinition = "TEXT", nullable = false)
    private String recommendedAction;

    @Column(name = "estimated_time_to_failure", nullable = false)
    private String estimatedTimeToFailure;

    @Column(name = "financial_impact_prevented", nullable = false)
    private String financialImpactPrevented;

    @Column(name = "downtime_prevented_hours", nullable = false)
    private Double downtimePreventedHours;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private UrgencyLevel urgency;

    @Column(name = "model_algorithm", nullable = false)
    private String modelAlgorithm;

    @Column(nullable = false)
    private Boolean dispatched = false;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public AIOpsRecommendationEntity() {}

    public AIOpsRecommendationEntity(String id, String title, String machineId, String machineName, String line,
                                    AlertSeverity severity, Integer confidenceScore, String rootCause,
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
        this.rootCause = rootCause;
        this.recommendedAction = recommendedAction;
        this.estimatedTimeToFailure = estimatedTimeToFailure;
        this.financialImpactPrevented = financialImpactPrevented;
        this.downtimePreventedHours = downtimePreventedHours;
        this.urgency = urgency;
        this.modelAlgorithm = modelAlgorithm;
        this.dispatched = dispatched != null ? dispatched : false;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
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
        private String rootCause;
        private String recommendedAction;
        private String estimatedTimeToFailure;
        private String financialImpactPrevented;
        private Double downtimePreventedHours;
        private UrgencyLevel urgency;
        private String modelAlgorithm;
        private Boolean dispatched = false;

        public Builder id(String id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder machineName(String machineName) { this.machineName = machineName; return this; }
        public Builder line(String line) { this.line = line; return this; }
        public Builder severity(AlertSeverity severity) { this.severity = severity; return this; }
        public Builder confidenceScore(Integer confidenceScore) { this.confidenceScore = confidenceScore; return this; }
        public Builder rootCause(String rootCause) { this.rootCause = rootCause; return this; }
        public Builder recommendedAction(String recommendedAction) { this.recommendedAction = recommendedAction; return this; }
        public Builder estimatedTimeToFailure(String estimatedTimeToFailure) { this.estimatedTimeToFailure = estimatedTimeToFailure; return this; }
        public Builder financialImpactPrevented(String financialImpactPrevented) { this.financialImpactPrevented = financialImpactPrevented; return this; }
        public Builder downtimePreventedHours(Double downtimePreventedHours) { this.downtimePreventedHours = downtimePreventedHours; return this; }
        public Builder urgency(UrgencyLevel urgency) { this.urgency = urgency; return this; }
        public Builder modelAlgorithm(String modelAlgorithm) { this.modelAlgorithm = modelAlgorithm; return this; }
        public Builder dispatched(Boolean dispatched) { this.dispatched = dispatched; return this; }

        public AIOpsRecommendationEntity build() {
            return new AIOpsRecommendationEntity(id, title, machineId, machineName, line, severity, confidenceScore,
                    rootCause, recommendedAction, estimatedTimeToFailure, financialImpactPrevented,
                    downtimePreventedHours, urgency, modelAlgorithm, dispatched);
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
    public List<ContributingSignalEntity> getContributingSignals() { return contributingSignals; }
    public void setContributingSignals(List<ContributingSignalEntity> contributingSignals) { this.contributingSignals = contributingSignals; }
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
    public Instant getCreatedAt() { return createdAt; }
}
