package com.factorypulse.dto.response;

import java.util.List;

public class EgressVerificationResponse {
    private String payloadId;
    private String machineId;
    private Boolean verified;
    private String computedSha256;
    private String recordedSha256;
    private Integer redactedFieldsCount;
    private List<String> verifiedBlockedKeys;
    private String complianceCertification;
    private String message;

    public EgressVerificationResponse() {}

    public EgressVerificationResponse(String payloadId, String machineId, Boolean verified,
                                      String computedSha256, String recordedSha256, Integer redactedFieldsCount,
                                      List<String> verifiedBlockedKeys, String complianceCertification, String message) {
        this.payloadId = payloadId;
        this.machineId = machineId;
        this.verified = verified;
        this.computedSha256 = computedSha256;
        this.recordedSha256 = recordedSha256;
        this.redactedFieldsCount = redactedFieldsCount;
        this.verifiedBlockedKeys = verifiedBlockedKeys;
        this.complianceCertification = complianceCertification;
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String payloadId;
        private String machineId;
        private Boolean verified;
        private String computedSha256;
        private String recordedSha256;
        private Integer redactedFieldsCount;
        private List<String> verifiedBlockedKeys;
        private String complianceCertification;
        private String message;

        public Builder payloadId(String payloadId) { this.payloadId = payloadId; return this; }
        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder verified(Boolean verified) { this.verified = verified; return this; }
        public Builder computedSha256(String computedSha256) { this.computedSha256 = computedSha256; return this; }
        public Builder recordedSha256(String recordedSha256) { this.recordedSha256 = recordedSha256; return this; }
        public Builder redactedFieldsCount(Integer redactedFieldsCount) { this.redactedFieldsCount = redactedFieldsCount; return this; }
        public Builder verifiedBlockedKeys(List<String> verifiedBlockedKeys) { this.verifiedBlockedKeys = verifiedBlockedKeys; return this; }
        public Builder complianceCertification(String complianceCertification) { this.complianceCertification = complianceCertification; return this; }
        public Builder message(String message) { this.message = message; return this; }

        public EgressVerificationResponse build() {
            return new EgressVerificationResponse(payloadId, machineId, verified, computedSha256,
                    recordedSha256, redactedFieldsCount, verifiedBlockedKeys, complianceCertification, message);
        }
    }

    public String getPayloadId() { return payloadId; }
    public void setPayloadId(String payloadId) { this.payloadId = payloadId; }
    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }
    public String getComputedSha256() { return computedSha256; }
    public void setComputedSha256(String computedSha256) { this.computedSha256 = computedSha256; }
    public String getRecordedSha256() { return recordedSha256; }
    public void setRecordedSha256(String recordedSha256) { this.recordedSha256 = recordedSha256; }
    public Integer getRedactedFieldsCount() { return redactedFieldsCount; }
    public void setRedactedFieldsCount(Integer redactedFieldsCount) { this.redactedFieldsCount = redactedFieldsCount; }
    public List<String> getVerifiedBlockedKeys() { return verifiedBlockedKeys; }
    public void setVerifiedBlockedKeys(List<String> verifiedBlockedKeys) { this.verifiedBlockedKeys = verifiedBlockedKeys; }
    public String getComplianceCertification() { return complianceCertification; }
    public void setComplianceCertification(String complianceCertification) { this.complianceCertification = complianceCertification; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
