-- ==============================================================================
-- FACTORYPULSE AI — REALISTIC SEED DATA
-- Matches Frontend Mock Data exactly for 1:1 seamless integration
-- ==============================================================================

-- -----------------------------------------------------------------------------
-- 1. SEED MACHINES
-- -----------------------------------------------------------------------------
INSERT INTO machines (
    id, name, line, model, temperature, temp_max, power_usage, power_max,
    vibration, vibration_max, connectivity, health, health_score, uptime,
    operating_hours, last_maintenance, next_scheduled_maintenance,
    operator_id, proprietary_code, internal_ip
) VALUES
('M01', 'Welding Robot A', 'Line A - Structural Framing', 'Kuka Titan KR-1000', 95.4, 85.0, 48.2, 42.0, 7.8, 4.5, 'ONLINE', 'POOR', 42, 94.1, 4210, '2026-10-14', '2026-11-28', 'EMP-7842-JONES', 'FORMULA-TITAN-ARC-V9', '192.168.10.45'),
('M02', '5-Axis CNC Milling Center', 'Line B - Precision Machining', 'DMG Mori DMU 50', 68.2, 75.0, 31.4, 38.0, 2.3, 3.5, 'ONLINE', 'EXCELLENT', 98, 99.4, 1850, '2026-11-02', '2027-01-15', 'EMP-1190-CHEN', 'AERO-TURBINE-GCODE-REV4', '192.168.10.46'),
('M03', 'Hydraulic Stamping Press 3', 'Line C - Heavy Stamping', 'Schuler Servo Press 800T', 84.1, 80.0, 112.5, 105.0, 5.4, 4.8, 'ONLINE', 'FAIR', 68, 96.2, 6120, '2026-09-20', '2026-11-24', 'EMP-3401-KOVACS', 'AUTO-HOOD-STAMP-SPEC-3', '192.168.10.47'),
('M04', 'Fiber Laser Cutting Cell 01', 'Line B - Sheet Processing', 'Trumpf TruLaser 5030', 54.6, 65.0, 22.8, 28.0, 1.4, 2.5, 'ONLINE', 'EXCELLENT', 96, 99.1, 2400, '2026-10-29', '2026-12-20', 'EMP-9032-MUELLER', 'CHASSIS-CONTOUR-PATS', '192.168.10.48'),
('M05', 'Automated Guided Vehicle Fleet Hub', 'Logistics Corridor 2', 'KION AGV Carrier Hub', 42.0, 50.0, 14.2, 18.0, 1.9, 3.0, 'DEGRADED', 'GOOD', 84, 97.8, 3200, '2026-10-05', '2026-12-05', 'EMP-5512-SANCHEZ', 'CORRIDOR-ROUTE-MAPPING-SECRET', '192.168.10.49'),
('M06', 'Continuous Polymer Curing Oven', 'Line D - Surface Treatment', 'ThermalDynamics TC-400', 184.2, 175.0, 74.0, 65.0, 3.2, 3.0, 'ONLINE', 'POOR', 48, 91.5, 7800, '2026-08-11', '2026-11-22', 'EMP-2210-RODRIGUEZ', 'EPOXY-POLYMER-CURING-FORMULA-9', '192.168.10.50'),
('M07', 'SMT High-Speed Pick & Place', 'Line E - Electronics Sub-assembly', 'Yamaha YSM20R Modular', 46.1, 55.0, 18.6, 24.0, 1.1, 2.0, 'ONLINE', 'GOOD', 91, 98.7, 1940, '2026-11-10', '2027-01-20', 'EMP-7711-TANAKA', 'PCB-ECU-REV7-PLACEMENT-MATRIX', '192.168.10.51'),
('M08', 'Heavy Rotary Lathe 07', 'Line B - Precision Machining', 'Mazak Quick Turn 350', 71.5, 72.0, 36.2, 35.0, 4.1, 3.8, 'ONLINE', 'FAIR', 71, 95.3, 5200, '2026-10-01', '2026-12-01', 'EMP-4490-WAGNER', 'CAMSHAFT-TURN-GCODE-PROP', '192.168.10.52'),
('M09', 'Industrial Rotary Air Compressor', 'Central Utilities Facility', 'Atlas Copco GA 160 VSD+', 63.8, 70.0, 88.0, 95.0, 2.6, 3.5, 'ONLINE', 'GOOD', 89, 99.8, 11400, '2026-09-15', '2026-12-15', 'EMP-0012-GOMEZ', 'PLANT-PRESSURE-LOOP-CTRL-CONF', '192.168.10.53'),
('M10', 'Ultrasonic Parts Washing Station', 'Line C - Finishing Cell', 'Branson OMNI-CLEAN Pro', 58.0, 60.0, 16.4, 20.0, 1.2, 2.2, 'ONLINE', 'EXCELLENT', 97, 99.6, 980, '2026-11-12', '2027-02-12', 'EMP-6029-LECLERC', 'DEGREASING-SOLVENT-MIX-RATIO', '192.168.10.54')
ON CONFLICT (id) DO UPDATE SET
    temperature = EXCLUDED.temperature,
    power_usage = EXCLUDED.power_usage,
    vibration = EXCLUDED.vibration,
    health = EXCLUDED.health,
    health_score = EXCLUDED.health_score;

-- -----------------------------------------------------------------------------
-- 2. SEED TELEMETRY HISTORY
-- -----------------------------------------------------------------------------
INSERT INTO telemetry_records (machine_id, time_label, temperature, power, vibration) VALUES
('M01', '14:00', 72.0, 34.0, 3.1),
('M01', '15:00', 75.0, 36.0, 3.4),
('M01', '16:00', 81.0, 39.0, 4.2),
('M01', '17:00', 88.0, 44.0, 5.9),
('M01', '18:00', 93.0, 47.0, 7.1),
('M01', '18:50', 95.4, 48.2, 7.8),

('M02', '14:00', 65.0, 29.0, 2.1),
('M02', '15:00', 66.0, 30.0, 2.2),
('M02', '16:00', 67.0, 31.0, 2.1),
('M02', '17:00', 68.0, 31.0, 2.3),
('M02', '18:00', 68.0, 32.0, 2.2),
('M02', '18:50', 68.2, 31.4, 2.3),

('M03', '14:00', 76.0, 98.0, 4.2),
('M03', '15:00', 78.0, 101.0, 4.5),
('M03', '16:00', 80.0, 104.0, 4.9),
('M03', '17:00', 82.0, 108.0, 5.1),
('M03', '18:00', 83.0, 110.0, 5.3),
('M03', '18:50', 84.1, 112.5, 5.4),

('M06', '14:00', 165.0, 62.0, 2.4),
('M06', '15:00', 170.0, 65.0, 2.6),
('M06', '16:00', 174.0, 68.0, 2.9),
('M06', '17:00', 179.0, 71.0, 3.0),
('M06', '18:00', 182.0, 73.0, 3.1),
('M06', '18:50', 184.2, 74.0, 3.2);

-- -----------------------------------------------------------------------------
-- 3. SEED ALERTS
-- -----------------------------------------------------------------------------
INSERT INTO alerts (
    id, title, description, severity, status, machine_id, machine_name,
    line, timestamp, trigger_signal, trigger_value, threshold_value,
    recommended_action, acknowledged_by, resolved_at
) VALUES
('ALT-1092', 'Impending Motor Bearing Seizure Risk', 'High harmonic vibration spike and secondary winding thermal runaway detected on Primary Arm Axis 3.', 'CRITICAL', 'ACTIVE', 'M01', 'Welding Robot A', 'Line A - Structural Framing', '18:42:15 (16m ago)', 'Vibration RMS & Bearing Temp', '7.8 mm/s @ 95.4°C', '4.5 mm/s @ 85.0°C', 'Inspect cooling duct, check lubrication level, prepare backup robotic cell substitution.', NULL, NULL),
('ALT-1093', 'Heating Element Thermal Override', 'Curing temperature has exceeded thermal tolerance window by +9.2°C for 22 consecutive minutes.', 'CRITICAL', 'ACTIVE', 'M06', 'Continuous Polymer Curing Oven', 'Line D - Surface Treatment', '18:31:02 (27m ago)', 'Chamber Pyrometer 2', '184.2°C', '175.0°C', 'Throttle thyristor power output, verify secondary ventilation intake damper status.', NULL, NULL),
('ALT-1088', 'Hydraulic Stamping Pressure Surge', 'Pressure spike detected at valve block B during downward stroke cycle.', 'HIGH', 'ACTIVE', 'M03', 'Hydraulic Stamping Press 3', 'Line C - Heavy Stamping', '17:55:40 (1h ago)', 'Manifold Pressure & Power Draw', '112.5 kW / 340 bar', '105.0 kW / 310 bar', 'Check proportional relief valve calibration and fluid particulate contamination sensor.', NULL, NULL),
('ALT-1085', 'Spindle Harmonic Resonance Warning', 'Radial runout vibration exceeding operational tolerance band at 12,000 RPM.', 'HIGH', 'ACTIVE', 'M08', 'Heavy Rotary Lathe 07', 'Line B - Precision Machining', '17:10:18 (1.5h ago)', 'Spindle Radial Vibration', '4.1 mm/s', '3.8 mm/s', 'Verify tool clamping force and inspect collet taper for metal burrs or misalignment.', NULL, NULL),
('ALT-1074', 'AGV Mesh Packet Latency Jitter', 'WiFi telemetry ping packet drop rate elevated to 3.8% in Logistics Corridor 2.', 'MEDIUM', 'ACTIVE', 'M05', 'Automated Guided Vehicle Fleet Hub', 'Logistics Corridor 2', '16:22:11 (2.5h ago)', 'Network Connectivity', 'DEGRADED (180ms jitter)', 'NORMAL (< 40ms)', 'Reboot local edge antenna access point WAP-04 and inspect RF interference.', NULL, NULL),
('ALT-1062', 'Air Compressor Intercooler Temp Drift', 'Stage 2 discharge temperature trending higher than historical rolling average by +3.4°C.', 'LOW', 'ACKNOWLEDGED', 'M09', 'Industrial Rotary Air Compressor', 'Central Utilities Facility', '14:45:00 (4h ago)', 'Intercooler Delta T', '63.8°C', '65.0°C warn', 'Clean intake air filters during upcoming weekend shift changeover.', 'Lead Tech M. Davis', NULL),
('ALT-1051', 'Coolant Flow Rate Micro-fluctuation', 'Periodic coolant line flow decrease during rapid high-feed machining cycles.', 'LOW', 'RESOLVED', 'M02', '5-Axis CNC Milling Center', 'Line B - Precision Machining', '11:15:22 (7h ago)', 'Coolant Line Sensor', '4.2 L/min', '4.0 L/min min', 'Filter mesh replaced, backpressure normalized.', NULL, '12:05:10')
ON CONFLICT (id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 4. SEED AIOPS RECOMMENDATIONS
-- -----------------------------------------------------------------------------
INSERT INTO aiops_recommendations (
    id, title, machine_id, machine_name, line, severity, confidence_score,
    root_cause, recommended_action, estimated_time_to_failure,
    financial_impact_prevented, downtime_prevented_hours, urgency,
    model_algorithm, dispatched
) VALUES
('REC-001', 'Potential Motor Bearing Catastrophic Failure', 'M01', 'Welding Robot A', 'Line A - Structural Framing', 'CRITICAL', 92,
 'Degradation of inner bearing cage ball race causing mechanical micro-galling and friction-induced thermal runaway.',
 'Inspect cooling duct immediately, lubricate outer bearing race, and dispatch maintenance team to execute predictive bearing replacement before next cycle.',
 '14 to 18 Operating Hours', '$42,500 in unplanned line stoppage & scrapped chassis', 5.5, 'IMMEDIATE',
 'Ensemble Isolation Forest + LSTM Spectral Telemetry Predictor', FALSE),

('REC-002', 'Thermal Element Degradation & Thermal Runaway', 'M06', 'Continuous Polymer Curing Oven', 'Line D - Surface Treatment', 'CRITICAL', 95,
 'Thyristor solid-state relay SSR-3 latching in high-conduction mode coupled with reduced exhaust damper throughput.',
 'Switch to auxiliary bypass PID controller, clean ventilation pre-filter, and replace SSR-3 during shift break.',
 '6 to 10 Operating Hours', '$28,000 in ruined composite polymer batches', 4.0, 'IMMEDIATE',
 'Multi-Variate Autoencoder + Drift Detector', FALSE),

('REC-003', 'Hydraulic Proportional Valve Seal Micro-leakage', 'M03', 'Hydraulic Stamping Press 3', 'Line C - Heavy Stamping', 'HIGH', 88,
 'Dynamic elastomer seal wear on 4-way spool valve allowing high-pressure fluid internal bypass back to sump.',
 'Schedule elastomer seal pack replacement at next maintenance window. Top up fluid level and inspect filter delta-P.',
 '36 to 48 Operating Hours', '$19,200 hydraulic pump rebuild avoidance', 3.0, 'NEXT_SHIFT',
 'Hidden Markov Model Pressure Analysis', FALSE),

('REC-004', 'Lathe Spindle Harmonic Collet Imbalance', 'M08', 'Heavy Rotary Lathe 07', 'Line B - Precision Machining', 'HIGH', 84,
 'Micro-fretting corrosion on collet chuck clamping faces causing sub-millimeter workpiece eccentricity.',
 'Perform ultrasonic collet bath cleaning, re-torque chuck jaws to 140 Nm, and re-zero touch probe.',
 '72 Operating Hours', '$14,500 in rejected aero-spec turned shafts', 2.0, 'NEXT_SHIFT',
 'Wavelet Transform Vibration Classifier', FALSE)
ON CONFLICT (id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 5. SEED CONTRIBUTING SIGNALS
-- -----------------------------------------------------------------------------
INSERT INTO aiops_contributing_signals (recommendation_id, name, value, deviation, severity) VALUES
('REC-001', 'High Joint Temperature', '95.4°C', '+23.4°C over baseline', 'critical'),
('REC-001', 'High Harmonic Vibration', '7.8 mm/s', '+73% above safe threshold', 'critical'),
('REC-001', 'Increased Power Consumption', '48.2 kW', '+15.2% parasitic friction drag', 'warning'),
('REC-001', 'Acoustic Kurtosis Signature', '5.2 kHz spike', 'Characteristic outer-race spall', 'critical'),

('REC-002', 'Zone 2 Pyrometer Drift', '184.2°C', '+9.2°C above recipe target', 'critical'),
('REC-002', 'Heater Current Imbalance', '74.0 kW', 'Phase C carrying 42% excess load', 'warning'),
('REC-002', 'Convection Blower RPM', '2,940 RPM', '-120 RPM airflow deficit', 'warning'),

('REC-003', 'Pump Power Ramp Duration', '112.5 kW', '+18% longer dwell cycle', 'warning'),
('REC-003', 'Pressure Decay Curve', '18 bar/s decay', '2.5x normal internal bypass rate', 'warning'),
('REC-003', 'Hydraulic Reservoir Temp', '84.1°C', '+4.1°C above optimal thermal band', 'warning'),

('REC-004', 'Radial Vibration Amplitude', '4.1 mm/s', '+8% over ISO 10816 threshold', 'warning'),
('REC-004', 'Taper Runout Micro-strain', '12.4 μm', 'Double the 6 μm precision limit', 'warning'),
('REC-004', 'Drive Motor Current Ripple', '±3.8 A', 'Periodic torque oscillation', 'normal');

-- -----------------------------------------------------------------------------
-- 6. SEED EGRESS AUDIT RECORDS
-- -----------------------------------------------------------------------------
INSERT INTO egress_records (
    id, timestamp, machine_id, machine_name, status, sha256_hash,
    redacted_fields_count, redacted_keys, raw_payload, metadata_payload
) VALUES
('EGR-PAYLOAD-99410', '2026-11-20T18:58:34.120Z', 'M01', 'Welding Robot A', 'FILTERED_AND_SEALED',
 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', 5,
 '["operator", "productionFormula", "internalIP", "serialBarcode", "customerContractId"]'::jsonb,
 '{
   "machine": "Welding Robot A",
   "operator": "EMP-7842-JONES",
   "productionFormula": "SECRET-TITAN-ARC-V9-PROPRIETARY",
   "internalIP": "192.168.10.45",
   "serialBarcode": "CHASSIS-EV-99482-CLASSIFIED",
   "customerContractId": "DEFENSE-DEPT-PO-99120",
   "temperature": 95.4,
   "powerUsageKw": 48.2,
   "vibrationRms": 7.8,
   "cycleCount": 14205,
   "rawSensorStreamSamples": [7.81, 7.85, 7.79, 7.82, 7.88, 7.84, 7.91, 7.80]
 }'::jsonb,
 '{
   "machineId": "M01",
   "payloadId": "FP-EGR-99410",
   "timestamp": "2026-11-20T18:58:34.120Z",
   "temperature": 95.4,
   "vibration": 7.8,
   "powerKw": 48.2,
   "health": "POOR",
   "severity": "CRITICAL",
   "dataSovereigntyVerified": true,
   "sha256Seal": "e3b0c442...991b7852b855",
   "privacyStandard": "ISO-27001 / IEC-62443 COMPLIANT"
 }'::jsonb),

('EGR-PAYLOAD-99411', '2026-11-20T18:58:22.045Z', 'M02', '5-Axis CNC Milling Center', 'APPROVED',
 '9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08', 4,
 '["operator", "cadModelChecksum", "internalIP", "proprietaryGCodeSubroutine"]'::jsonb,
 '{
   "machine": "5-Axis CNC Milling Center",
   "operator": "EMP-1190-CHEN",
   "cadModelChecksum": "MD5_AERO_HYPERSONIC_ROTOR_BLADE",
   "proprietaryGCodeSubroutine": "G01 X142.1 Y99.4 Z-12.8 F450 M08 S12000 (SECRET_PASS)",
   "internalIP": "192.168.10.46",
   "macAddress": "00:1A:2B:3C:4D:5E",
   "temperature": 68.2,
   "powerUsageKw": 31.4,
   "vibrationRms": 2.3
 }'::jsonb,
 '{
   "machineId": "M02",
   "payloadId": "FP-EGR-99411",
   "timestamp": "2026-11-20T18:58:22.045Z",
   "temperature": 68.2,
   "vibration": 2.3,
   "powerKw": 31.4,
   "health": "EXCELLENT",
   "severity": "LOW",
   "dataSovereigntyVerified": true,
   "sha256Seal": "9f86d081...15b0f00a08",
   "privacyStandard": "ISO-27001 / IEC-62443 COMPLIANT"
 }'::jsonb),

('EGR-PAYLOAD-99412', '2026-11-20T18:57:48.810Z', 'M06', 'Continuous Polymer Curing Oven', 'FILTERED_AND_SEALED',
 '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 5,
 '["operator", "chemicalMixFormula", "internalIP", "furnaceCatalystBatch", "securityBadgeHash"]'::jsonb,
 '{
   "machine": "Continuous Polymer Curing Oven",
   "operator": "EMP-2210-RODRIGUEZ",
   "chemicalMixFormula": "RESIN_EPOXY_BOND_CHEM_X49_PATENT_PENDING",
   "furnaceCatalystBatch": "CAT-BATCH-990-CONFIDENTIAL",
   "internalIP": "192.168.10.50",
   "securityBadgeHash": "9882a1b94d8f",
   "temperature": 184.2,
   "powerUsageKw": 74.0,
   "vibrationRms": 3.2
 }'::jsonb,
 '{
   "machineId": "M06",
   "payloadId": "FP-EGR-99412",
   "timestamp": "2026-11-20T18:57:48.810Z",
   "temperature": 184.2,
   "vibration": 3.2,
   "powerKw": 74.0,
   "health": "POOR",
   "severity": "CRITICAL",
   "dataSovereigntyVerified": true,
   "sha256Seal": "5e884898...721d1542d8",
   "privacyStandard": "ISO-27001 / IEC-62443 COMPLIANT"
 }'::jsonb)
ON CONFLICT (id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 7. SEED ACTIVITY EVENTS
-- -----------------------------------------------------------------------------
INSERT INTO activity_events (id, timestamp, type, title, description, machine_id, badge) VALUES
('ACT-01', '18:58:34', 'EGRESS_APPROVED', 'Egress Proof Sealed & Metadata Dispatched', 'Local Collector stripped 5 confidential fields from M01 telemetry. Transmitted 284 bytes metadata to SaaS.', 'M01', '100% Zero Leakage'),
('ACT-02', '18:42:15', 'ANOMALY_DETECTED', 'AIOps Engine Triggered Critical Recommendation', 'Potential Motor Failure predicted on Welding Robot A with 92% confidence score.', 'M01', 'AIOps Alert'),
('ACT-03', '18:31:02', 'DATA_FILTERED', 'Proprietary Recipe Scrubbed from Sensor Packet', 'Chemical recipe "RESIN_EPOXY_BOND" blocked from outbound transit on M06.', 'M06', 'Redacted'),
('ACT-04', '17:55:40', 'ANOMALY_DETECTED', 'High Pressure Spike Logged', 'Schuler Servo Press 800T pressure curve exceeded standard deviation threshold.', 'M03', 'High Severity'),
('ACT-05', '17:15:00', 'INCIDENT_ACK', 'Maintenance Team Dispatched', 'Operator M. Davis acknowledged intercooler inspection on Compressor GA-160.', 'M09', 'Acknowledged'),
('ACT-06', '16:00:22', 'MAINTENANCE_LOG', 'Routine Edge Health Heartbeat Passed', 'All 8 Local Collector edge agents operating in isolated hardware enclaves with 0.8ms latency.', NULL, 'Heartbeat OK')
ON CONFLICT (id) DO NOTHING;

-- -----------------------------------------------------------------------------
-- 8. SEED SYSTEM KPIS
-- -----------------------------------------------------------------------------
INSERT INTO system_kpis (
    id, total_machines, active_machines, alerts_count, critical_incidents,
    factory_health_score, oee_score, egress_compliance_rate,
    total_data_processed_mb, metadata_transmitted_kb
) VALUES (
    1, 248, 236, 14, 2, 94.2, 88.6, 100.0, 4128.5, 142.3
)
ON CONFLICT (id) DO UPDATE SET
    total_machines = EXCLUDED.total_machines,
    active_machines = EXCLUDED.active_machines,
    alerts_count = EXCLUDED.alerts_count,
    critical_incidents = EXCLUDED.critical_incidents,
    factory_health_score = EXCLUDED.factory_health_score,
    oee_score = EXCLUDED.oee_score;
