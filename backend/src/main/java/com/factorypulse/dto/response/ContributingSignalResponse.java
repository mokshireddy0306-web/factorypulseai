package com.factorypulse.dto.response;

public class ContributingSignalResponse {
    private String name;
    private String value;
    private String deviation;
    private String severity; // "critical", "warning", "normal"

    public ContributingSignalResponse() {}

    public ContributingSignalResponse(String name, String value, String deviation, String severity) {
        this.name = name;
        this.value = value;
        this.deviation = deviation;
        this.severity = severity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String value;
        private String deviation;
        private String severity;

        public Builder name(String name) { this.name = name; return this; }
        public Builder value(String value) { this.value = value; return this; }
        public Builder deviation(String deviation) { this.deviation = deviation; return this; }
        public Builder severity(String severity) { this.severity = severity; return this; }

        public ContributingSignalResponse build() {
            return new ContributingSignalResponse(name, value, deviation, severity);
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDeviation() { return deviation; }
    public void setDeviation(String deviation) { this.deviation = deviation; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
}
