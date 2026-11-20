package com.factorypulse.model;

import com.factorypulse.model.enums.EgressStatus;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "egress_records")
public class EgressRecordEntity {

    @Id
    @Column(length = 100)
    private String id;

    @Column(nullable = false)
    private String timestamp;

    @Column(name = "machine_id", nullable = false, length = 50)
    private String machineId;

    @Column(name = "machine_name", nullable = false)
    private String machineName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EgressStatus status;

    @Column(name = "sha256_hash", nullable = false, length = 128)
    private String sha256Hash;

    @Column(name = "redacted_fields_count", nullable = false)
    private Integer redactedFieldsCount;

    @Column(name = "redacted_keys", columnDefinition = "TEXT", nullable = false)
    private String redactedKeysJson;

    @Column(name = "raw_payload", columnDefinition = "TEXT", nullable = false)
    private String rawPayloadJson;

    @Column(name = "metadata_payload", columnDefinition = "TEXT", nullable = false)
    private String metadataPayloadJson;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    public EgressRecordEntity() {}

    public EgressRecordEntity(String id, String timestamp, String machineId, String machineName, EgressStatus status,
                              String sha256Hash, Integer redactedFieldsCount, String redactedKeysJson,
                              String rawPayloadJson, String metadataPayloadJson) {
        this.id = id;
        this.timestamp = timestamp;
        this.machineId = machineId;
        this.machineName = machineName;
        this.status = status;
        this.sha256Hash = sha256Hash;
        this.redactedFieldsCount = redactedFieldsCount;
        this.redactedKeysJson = redactedKeysJson;
        this.rawPayloadJson = rawPayloadJson;
        this.metadataPayloadJson = metadataPayloadJson;
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
        private String timestamp;
        private String machineId;
        private String machineName;
        private EgressStatus status = EgressStatus.FILTERED_AND_SEALED;
        private String sha256Hash;
        private Integer redactedFieldsCount = 0;
        private String redactedKeysJson = "[]";
        private String rawPayloadJson = "{}";
        private String metadataPayloadJson = "{}";

        public Builder id(String id) { this.id = id; return this; }
        public Builder timestamp(String timestamp) { this.timestamp = timestamp; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder machineName(String machineName) { this.machineName = machineName; return this; }
        public Builder status(EgressStatus status) { this.status = status; return this; }
        public Builder sha256Hash(String sha256Hash) { this.sha256Hash = sha256Hash; return this; }
        public Builder redactedFieldsCount(Integer redactedFieldsCount) { this.redactedFieldsCount = redactedFieldsCount; return this; }
        public Builder redactedKeysJson(String redactedKeysJson) { this.redactedKeysJson = redactedKeysJson; return this; }
        public Builder rawPayloadJson(String rawPayloadJson) { this.rawPayloadJson = rawPayloadJson; return this; }
        public Builder metadataPayloadJson(String metadataPayloadJson) { this.metadataPayloadJson = metadataPayloadJson; return this; }

        public EgressRecordEntity build() {
            return new EgressRecordEntity(id, timestamp, machineId, machineName, status, sha256Hash,
                    redactedFieldsCount, redactedKeysJson, rawPayloadJson, metadataPayloadJson);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public String getMachineName() { return machineName; }
    public void setMachineName(String machineName) { this.machineName = machineName; }
    public EgressStatus getStatus() { return status; }
    public void setStatus(EgressStatus status) { this.status = status; }
    public String getSha256Hash() { return sha256Hash; }
    public void setSha256Hash(String sha256Hash) { this.sha256Hash = sha256Hash; }
    public Integer getRedactedFieldsCount() { return redactedFieldsCount; }
    public void setRedactedFieldsCount(Integer redactedFieldsCount) { this.redactedFieldsCount = redactedFieldsCount; }
    public String getRedactedKeysJson() { return redactedKeysJson; }
    public void setRedactedKeysJson(String redactedKeysJson) { this.redactedKeysJson = redactedKeysJson; }
    public String getRawPayloadJson() { return rawPayloadJson; }
    public void setRawPayloadJson(String rawPayloadJson) { this.rawPayloadJson = rawPayloadJson; }
    public String getMetadataPayloadJson() { return metadataPayloadJson; }
    public void setMetadataPayloadJson(String metadataPayloadJson) { this.metadataPayloadJson = metadataPayloadJson; }
    public Instant getCreatedAt() { return createdAt; }
}
