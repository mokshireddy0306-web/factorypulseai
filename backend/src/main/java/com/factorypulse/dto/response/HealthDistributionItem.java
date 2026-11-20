package com.factorypulse.dto.response;

public class HealthDistributionItem {
    private String name;
    private Long value;
    private String color;

    public HealthDistributionItem() {}

    public HealthDistributionItem(String name, Long value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Long value;
        private String color;

        public Builder name(String name) { this.name = name; return this; }
        public Builder value(Long value) { this.value = value; return this; }
        public Builder color(String color) { this.color = color; return this; }

        public HealthDistributionItem build() {
            return new HealthDistributionItem(name, value, color);
        }
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getValue() { return value; }
    public void setValue(Long value) { this.value = value; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
