# FactoryPulse AI — PostgreSQL Database

This directory contains the complete PostgreSQL database schema, initial seed data, and Docker configuration for the FactoryPulse AI manufacturing observability platform.

---

## 🏗️ Architecture & Entities

The database schema directly maps to the frontend requirements:

| Table | Entity | Description |
|---|---|---|
| `machines` | Machine | Industrial equipment telemetry, health status, and on-premise restricted credentials (PII, formulas, internal IPs) |
| `telemetry_records` | Telemetry History | Rolling time-series sensor points (temperature, power kW, vibration mm/s) |
| `alerts` | Incident Alert | Anomaly triggers with severity (`CRITICAL`, `HIGH`, `MEDIUM`, `LOW`) and lifecycle status (`ACTIVE`, `ACKNOWLEDGED`, `RESOLVED`) |
| `aiops_recommendations` | AIOps Prediction | Machine learning diagnostics with failure probabilities, root cause analysis, and work order dispatch flags |
| `aiops_contributing_signals` | Signal Breakdown | Sensor deviations contributing to AI anomaly detection |
| `egress_records` | Egress Audit Log | Cryptographic proof records comparing on-prem raw data vs approved cloud metadata with SHA-256 seals |
| `activity_events` | Activity Stream | Real-time audit log of local edge filtering, metadata egress, and maintenance actions |
| `system_kpis` | KPI Snapshot | Fleet-wide aggregates: total machines, active units, OEE score, and data sovereignty ratios |

---

## 🚀 Running with Docker (Recommended)

To spin up a PostgreSQL instance pre-loaded with the schema and seed data:

```bash
cd database
docker-compose up -d
```

Connection parameters:
- **Host:** `localhost`
- **Port:** `5432`
- **Database:** `factorypulse`
- **Username:** `postgres`
- **Password:** `postgres`

---

## ⚙️ Manual Execution

If running a local PostgreSQL service:

```bash
psql -U postgres -d factorypulse -f schema.sql
psql -U postgres -d factorypulse -f seed-data.sql
```
