package com.factorypulse.dto.response;

import com.factorypulse.model.enums.ActivityEventType;

public class ActivityEventResponse {
    private String id;
    private String timestamp;
    private ActivityEventType type;
    private String title;
    private String description;
    private String machineId;
    private String badge;

    public ActivityEventResponse() {}

    public ActivityEventResponse(String id, String timestamp, ActivityEventType type, String title,
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

        public ActivityEventResponse build() {
            return new ActivityEventResponse(id, timestamp, type, title, description, machineId, badge);
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
}
