package com.factorypulse.dto.request;

public class AcknowledgeAlertRequest {
    private String operatorName;

    public AcknowledgeAlertRequest() {}

    public AcknowledgeAlertRequest(String operatorName) {
        this.operatorName = operatorName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String operatorName;
        public Builder operatorName(String operatorName) { this.operatorName = operatorName; return this; }
        public AcknowledgeAlertRequest build() { return new AcknowledgeAlertRequest(operatorName); }
    }

    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }
}
