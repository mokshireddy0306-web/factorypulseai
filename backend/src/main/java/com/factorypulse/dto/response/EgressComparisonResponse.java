package com.factorypulse.dto.response;

import com.factorypulse.model.enums.EgressStatus;

import java.util.List;
import java.util.Map;

public class EgressComparisonResponse {
    private String id;
    private String timestamp;
    private String machineId;
    private String machineName;
    private EgressStatus status;
    private String sha256Hash;
    private Integer redactedFieldsCount;
    private Map<String, Object> rawPayload;
    private Map<String, Object> metadataPayload;
    private List<String> redactedKeys;

    public EgressComparisonResponse() {}

    public EgressComparisonResponse(String id, String timestamp, String machineId, String machineName,
                                    EgressStatus status, String sha256Hash, Integer redactedFieldsCount,
                                    Map<String, Object> rawPayload, Map<String, Object> metadataPayload,
                                    List<String> redactedKeys) {
        this.id = id;
        this.timestamp = timestamp;
        this.machineId = machineId;
        this.machineName = machineName;
        this.status = status;
        this.sha256Hash = sha256Hash;
        this.redactedFieldsCount = redactedFieldsCount;
        this.rawPayload = rawPayload;
        this.metadataPayload = metadataPayload;
        this.redactedKeys = redactedKeys;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String timestamp;
        private String machineId;
        private String machineName;
        private EgressStatus status;
        private String sha256Hash;
        private Integer redactedFieldsCount;
        private Map<String, Object> rawPayload;
        private Map<String, Object> metadataPayload;
        private List<String> redactedKeys;

        public Builder id(String id) { this.id = id; return this; }
        public Builder timestamp(String timestamp) { this.timestamp = timestamp; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder machineName(String machineName) { this.machineName = machineName; return this; }
        public Builder status(EgressStatus status) { this.status = status; return this; }
        public Builder sha256Hash(String sha256Hash) { this.sha256Hash = sha256Hash; return this; }
        public Builder redactedFieldsCount(Integer redactedFieldsCount) { this.redactedFieldsCount = redactedFieldsCount; return this; }
        public Builder rawPayload(Map<String, Object> rawPayload) { this.rawPayload = rawPayload; return this; }
        public Builder metadataPayload(Map<String, Object> metadataPayload) { this.metadataPayload = metadataPayload; return this; }
        public Builder redactedKeys(List<String> redactedKeys) { this.redactedKeys = redactedKeys; return this; }

        public EgressComparisonResponse build() {
            return new EgressComparisonResponse(id, timestamp, machineId, machineName, status,
                    sha256Hash, redactedFieldsCount, rawPayload, metadataPayload, redactedKeys);
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
    public Map<String, Object> getRawPayload() { return rawPayload; }
    public void setRawPayload(Map<String, Object> rawPayload) { this.rawPayload = rawPayload; }
    public Map<String, Object> getMetadataPayload() { return metadataPayload; }
    public void setMetadataPayload(Map<String, Object> metadataPayload) { this.metadataPayload = metadataPayload; }
    public List<String> getRedactedKeys() { return redactedKeys; }
    public void setRedactedKeys(List<String> redactedKeys) { this.redactedKeys = redactedKeys; }
}
