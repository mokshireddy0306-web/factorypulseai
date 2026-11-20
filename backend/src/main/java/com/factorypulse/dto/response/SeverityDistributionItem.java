package com.factorypulse.dto.response;

public class SeverityDistributionItem {
    private String severity;
    private Long count;
    private String color;

    public SeverityDistributionItem() {}

    public SeverityDistributionItem(String severity, Long count, String color) {
        this.severity = severity;
        this.count = count;
        this.color = color;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String severity;
        private Long count;
        private String color;

        public Builder severity(String severity) { this.severity = severity; return this; }
        public Builder count(Long count) { this.count = count; return this; }
        public Builder color(String color) { this.color = color; return this; }

        public SeverityDistributionItem build() {
            return new SeverityDistributionItem(severity, count, color);
        }
    }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
