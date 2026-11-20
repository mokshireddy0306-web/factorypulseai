-- ==============================================================================
-- FACTORYPULSE AI — INDUSTRIAL DATABASE SCHEMA
-- PostgreSQL 14+ Compatible
-- Description: Schema for secure manufacturing AIOps & telemetry observability
-- ==============================================================================

-- Drop tables in reverse dependency order if re-initializing
DROP TABLE IF EXISTS activity_events CASCADE;
DROP TABLE IF EXISTS egress_records CASCADE;
DROP TABLE IF EXISTS aiops_contributing_signals CASCADE;
DROP TABLE IF EXISTS aiops_recommendations CASCADE;
DROP TABLE IF EXISTS alerts CASCADE;
DROP TABLE IF EXISTS telemetry_records CASCADE;
DROP TABLE IF EXISTS machines CASCADE;
DROP TABLE IF EXISTS system_kpis CASCADE;

-- -----------------------------------------------------------------------------
-- 1. MACHINES TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE machines (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    line VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    temperature NUMERIC(6, 2) NOT NULL DEFAULT 65.0,
    temp_max NUMERIC(6, 2) NOT NULL DEFAULT 85.0,
    power_usage NUMERIC(7, 2) NOT NULL DEFAULT 35.0,
    power_max NUMERIC(7, 2) NOT NULL DEFAULT 50.0,
    vibration NUMERIC(6, 2) NOT NULL DEFAULT 2.5,
    vibration_max NUMERIC(6, 2) NOT NULL DEFAULT 4.5,
    connectivity VARCHAR(30) NOT NULL DEFAULT 'ONLINE' CHECK (connectivity IN ('ONLINE', 'DEGRADED', 'OFFLINE')),
    health VARCHAR(30) NOT NULL DEFAULT 'GOOD' CHECK (health IN ('EXCELLENT', 'GOOD', 'FAIR', 'POOR')),
    health_score INT NOT NULL DEFAULT 90 CHECK (health_score BETWEEN 0 AND 100),
    uptime NUMERIC(5, 2) NOT NULL DEFAULT 99.0,
    operating_hours INT NOT NULL DEFAULT 0,
    last_maintenance VARCHAR(50),
    next_scheduled_maintenance VARCHAR(50),
    -- Sensitive fields (Stays strictly on-premise in Local Edge Enclave)
    operator_id VARCHAR(100),
    proprietary_code VARCHAR(255),
    internal_ip VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_machines_health ON machines(health);
CREATE INDEX idx_machines_line ON machines(line);
CREATE INDEX idx_machines_connectivity ON machines(connectivity);

COMMENT ON TABLE machines IS 'Factory machines with operational telemetry and local on-premise sensitive configurations';
COMMENT ON COLUMN machines.operator_id IS 'RESTRICTED PII: Operator employee ID, never egresses to SaaS';
COMMENT ON COLUMN machines.proprietary_code IS 'RESTRICTED IP: Proprietary chemical/machining formula, never egresses to SaaS';
COMMENT ON COLUMN machines.internal_ip IS 'RESTRICTED NET: Internal OT IP address, never egresses to SaaS';

-- -----------------------------------------------------------------------------
-- 2. TELEMETRY RECORDS TABLE (High-Frequency History)
-- -----------------------------------------------------------------------------
CREATE TABLE telemetry_records (
    id BIGSERIAL PRIMARY KEY,
    machine_id VARCHAR(50) NOT NULL REFERENCES machines(id) ON DELETE CASCADE,
    time_label VARCHAR(30) NOT NULL, -- e.g. "14:00", "15:00", "18:50"
    temperature NUMERIC(6, 2) NOT NULL,
    power NUMERIC(7, 2) NOT NULL,
    vibration NUMERIC(6, 2) NOT NULL,
    recorded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_telemetry_machine_time ON telemetry_records(machine_id, recorded_at);

COMMENT ON TABLE telemetry_records IS 'Time-series rolling telemetry history for machines';

-- -----------------------------------------------------------------------------
-- 3. ALERTS TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE alerts (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    severity VARCHAR(30) NOT NULL CHECK (severity IN ('CRITICAL', 'HIGH', 'MEDIUM', 'LOW')),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'ACKNOWLEDGED', 'RESOLVED')),
    machine_id VARCHAR(50) NOT NULL REFERENCES machines(id) ON DELETE CASCADE,
    machine_name VARCHAR(255) NOT NULL,
    line VARCHAR(255) NOT NULL,
    timestamp VARCHAR(100) NOT NULL, -- e.g. "18:42:15 (16m ago)"
    trigger_signal VARCHAR(255) NOT NULL,
    trigger_value VARCHAR(100) NOT NULL,
    threshold_value VARCHAR(100) NOT NULL,
    recommended_action TEXT NOT NULL,
    acknowledged_by VARCHAR(100),
    resolved_at VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_alerts_severity ON alerts(severity);
CREATE INDEX idx_alerts_status ON alerts(status);
CREATE INDEX idx_alerts_machine ON alerts(machine_id);

COMMENT ON TABLE alerts IS 'Active and historical factory anomaly alerts';

-- -----------------------------------------------------------------------------
-- 4. AIOPS RECOMMENDATIONS TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE aiops_recommendations (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    machine_id VARCHAR(50) NOT NULL REFERENCES machines(id) ON DELETE CASCADE,
    machine_name VARCHAR(255) NOT NULL,
    line VARCHAR(255) NOT NULL,
    severity VARCHAR(30) NOT NULL CHECK (severity IN ('CRITICAL', 'HIGH', 'MEDIUM', 'LOW')),
    confidence_score INT NOT NULL CHECK (confidence_score BETWEEN 0 AND 100),
    root_cause TEXT NOT NULL,
    recommended_action TEXT NOT NULL,
    estimated_time_to_failure VARCHAR(100) NOT NULL,
    financial_impact_prevented VARCHAR(255) NOT NULL,
    downtime_prevented_hours NUMERIC(5, 2) NOT NULL,
    urgency VARCHAR(50) NOT NULL CHECK (urgency IN ('IMMEDIATE', 'NEXT_SHIFT', 'SCHEDULED_MAINTENANCE')),
    model_algorithm VARCHAR(255) NOT NULL,
    dispatched BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_aiops_urgency ON aiops_recommendations(urgency);
CREATE INDEX idx_aiops_machine ON aiops_recommendations(machine_id);

COMMENT ON TABLE aiops_recommendations IS 'Predictive machine learning diagnostics and maintenance prescriptions';

-- -----------------------------------------------------------------------------
-- 5. AIOPS CONTRIBUTING SIGNALS TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE aiops_contributing_signals (
    id BIGSERIAL PRIMARY KEY,
    recommendation_id VARCHAR(50) NOT NULL REFERENCES aiops_recommendations(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(100) NOT NULL,
    deviation VARCHAR(255) NOT NULL,
    severity VARCHAR(30) NOT NULL CHECK (severity IN ('critical', 'warning', 'normal'))
);

CREATE INDEX idx_signals_recommendation ON aiops_contributing_signals(recommendation_id);

-- -----------------------------------------------------------------------------
-- 6. EGRESS RECORDS TABLE (Audit Proof & Redaction Gateway)
-- -----------------------------------------------------------------------------
CREATE TABLE egress_records (
    id VARCHAR(100) PRIMARY KEY,
    timestamp VARCHAR(100) NOT NULL,
    machine_id VARCHAR(50) NOT NULL REFERENCES machines(id) ON DELETE CASCADE,
    machine_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'FILTERED_AND_SEALED' CHECK (status IN ('FILTERED_AND_SEALED', 'BLOCKED', 'APPROVED')),
    sha256_hash VARCHAR(128) NOT NULL,
    redacted_fields_count INT NOT NULL DEFAULT 0,
    redacted_keys TEXT NOT NULL DEFAULT '[]',
    raw_payload TEXT NOT NULL,
    metadata_payload TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_egress_machine ON egress_records(machine_id);
CREATE INDEX idx_egress_status ON egress_records(status);

COMMENT ON TABLE egress_records IS 'Cryptographic proof log comparing raw on-premise telemetry with sanitized cloud metadata';

-- -----------------------------------------------------------------------------
-- 7. ACTIVITY EVENTS TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE activity_events (
    id VARCHAR(50) PRIMARY KEY,
    timestamp VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('EGRESS_APPROVED', 'ANOMALY_DETECTED', 'DATA_FILTERED', 'INCIDENT_ACK', 'MAINTENANCE_LOG')),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    machine_id VARCHAR(50),
    badge VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_activity_created ON activity_events(created_at DESC);

COMMENT ON TABLE activity_events IS 'Real-time event and audit activity stream';

-- -----------------------------------------------------------------------------
-- 8. SYSTEM KPI METRICS SNAPSHOT TABLE
-- -----------------------------------------------------------------------------
CREATE TABLE system_kpis (
    id INT PRIMARY KEY DEFAULT 1,
    total_machines INT NOT NULL DEFAULT 248,
    active_machines INT NOT NULL DEFAULT 236,
    alerts_count INT NOT NULL DEFAULT 14,
    critical_incidents INT NOT NULL DEFAULT 2,
    factory_health_score NUMERIC(5, 2) NOT NULL DEFAULT 94.2,
    oee_score NUMERIC(5, 2) NOT NULL DEFAULT 88.6,
    egress_compliance_rate NUMERIC(5, 2) NOT NULL DEFAULT 100.0,
    total_data_processed_mb NUMERIC(10, 2) NOT NULL DEFAULT 4128.5,
    metadata_transmitted_kb NUMERIC(10, 2) NOT NULL DEFAULT 142.3,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
