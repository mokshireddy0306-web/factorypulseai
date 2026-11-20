package com.factorypulse.model;

import com.factorypulse.model.enums.ActivityEventType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "activity_events")
public class ActivityEventEntity {

    @Id
    @Column(length = 50)
    private String id; // e.g. "ACT-01"

    @Column(nullable = false, length = 50)
    private String timestamp; // e.g. "18:58:34"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ActivityEventType type;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "machine_id", length = 50)
    private String machineId;

    @Column(length = 100)
    private String badge;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public ActivityEventEntity() {}

    public ActivityEventEntity(String id, String timestamp, ActivityEventType type, String title,
                               String description, String machineId, String badge) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.title = title;
        this.description = description;
        this.machineId = machineId;
        this.badge = badge;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String timestamp;
        private ActivityEventType type;
        private String title;
        private String description;
        private String machineId;
        private String badge;

        public Builder id(String id) { this.id = id; return this; }
        public Builder timestamp(String timestamp) { this.timestamp = timestamp; return this; }
        public Builder type(ActivityEventType type) { this.type = type; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder badge(String badge) { this.badge = badge; return this; }

        public ActivityEventEntity build() {
            return new ActivityEventEntity(id, timestamp, type, title, description, machineId, badge);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public ActivityEventType getType() { return type; }
    public void setType(ActivityEventType type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
