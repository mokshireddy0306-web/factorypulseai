package com.factorypulse.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class ProcessEgressRequest {

    @NotBlank(message = "Machine ID is required")
    private String machineId;

    @NotNull(message = "Raw telemetry payload is required")
    private Map<String, Object> rawPayload;

    public ProcessEgressRequest() {}

    public ProcessEgressRequest(String machineId, Map<String, Object> rawPayload) {
        this.machineId = machineId;
        this.rawPayload = rawPayload;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String machineId;
        private Map<String, Object> rawPayload;

        public Builder machineId(String machineId) { this.machineId = machineId; return this; }
        public Builder rawPayload(Map<String, Object> rawPayload) { this.rawPayload = rawPayload; return this; }

        public ProcessEgressRequest build() {
            return new ProcessEgressRequest(machineId, rawPayload);
        }
    }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }
    public Map<String, Object> getRawPayload() { return rawPayload; }
    public void setRawPayload(Map<String, Object> rawPayload) { this.rawPayload = rawPayload; }
}
