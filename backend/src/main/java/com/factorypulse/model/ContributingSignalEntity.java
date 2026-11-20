package com.factorypulse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "aiops_contributing_signals")
public class ContributingSignalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id", nullable = false)
    @JsonIgnore
    private AIOpsRecommendationEntity recommendation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private String deviation;

    @Column(nullable = false, length = 30)
    private String severity; // "critical", "warning", "normal"

    public ContributingSignalEntity() {}

    public ContributingSignalEntity(Long id, AIOpsRecommendationEntity recommendation, String name, String value, String deviation, String severity) {
        this.id = id;
        this.recommendation = recommendation;
        this.name = name;
        this.value = value;
        this.deviation = deviation;
        this.severity = severity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private AIOpsRecommendationEntity recommendation;
        private String name;
        private String value;
        private String deviation;
        private String severity;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder recommendation(AIOpsRecommendationEntity recommendation) { this.recommendation = recommendation; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder value(String value) { this.value = value; return this; }
        public Builder deviation(String deviation) { this.deviation = deviation; return this; }
        public Builder severity(String severity) { this.severity = severity; return this; }

        public ContributingSignalEntity build() {
            return new ContributingSignalEntity(id, recommendation, name, value, deviation, severity);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AIOpsRecommendationEntity getRecommendation() { return recommendation; }
    public void setRecommendation(AIOpsRecommendationEntity recommendation) { this.recommendation = recommendation; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDeviation() { return deviation; }
    public void setDeviation(String deviation) { this.deviation = deviation; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}
