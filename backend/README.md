# FactoryPulse AI — Spring Boot Backend

> **Enterprise AIOps Observability Backend for Manufacturing**  
> Built with Java 21, Spring Boot 3, Spring Data JPA, PostgreSQL, Spring Validation, Lombok, and Swagger/OpenAPI.

---

## 🚀 Quick Start

### 1. Requirements
- **Java 21+** (`C:\Program Files\Java\jdk-27` or any modern JDK 21+)
- **Maven 3.9+** (Installed in `$env:LOCALAPPDATA\Programs\maven\bin` or system PATH)
- **PostgreSQL 14+** (Optional: defaults to in-memory PostgreSQL-compatible mode for instant local development)

### 2. Run the Application

```bash
cd backend
mvn spring-boot:run
```

The server starts at [http://localhost:8080](http://localhost:8080).

### 3. Interactive Swagger UI & OpenAPI Docs
Once running, navigate to:
👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**  
Raw OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🔌 API Endpoints Reference

All endpoints are prefixed with `/api/v1` and strictly match the frontend contracts.

### 1. Dashboard & Fleet Analytics
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/dashboard/kpis` | Total machines, active count, alerts, OEE score, and data sovereignty ratios |
| `GET` | `/api/v1/dashboard/health-distribution` | Breakdown for Excellent, Good, Fair, and Poor machines |
| `GET` | `/api/v1/dashboard/trends?range=24h` | 24-hour hourly temperature, power, vibration, and anomaly events |
| `GET` | `/api/v1/dashboard/severity-breakdown` | Active incident counts for Critical, High, Medium, Low |
| `GET` | `/api/v1/dashboard/activity-feed` | Real-time event stream of edge redactions and AI alerts |

### 2. Machine Monitoring
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/machines` | List machines with filters (`health`, `line`, `search`) |
| `GET` | `/api/v1/machines/{id}` | Get machine details with telemetry history and on-prem privacy status |
| `POST` | `/api/v1/machines` | Register a new machine |
| `POST` | `/api/v1/machines/{id}/telemetry` | Ingest live sensor point (triggers dynamic health score recalibration) |

### 3. Alerts & Incident Management
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/alerts` | List alerts with filters (`severity`, `status`, `search`) |
| `GET` | `/api/v1/alerts/{id}` | Alert details with trigger signal vs threshold comparison |
| `POST` | `/api/v1/alerts/{id}/acknowledge` | Acknowledge incident (sets `ACKNOWLEDGED` and updates operator log) |
| `POST` | `/api/v1/alerts/{id}/resolve` | Resolve incident (sets `RESOLVED`, updates timestamp and health index) |

### 4. AIOps Recommendations
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/aiops/recommendations` | List predictive recommendations with confidence scores & root causes |
| `GET` | `/api/v1/aiops/recommendations/{id}` | Get specific recommendation details |
| `POST` | `/api/v1/aiops/recommendations/{id}/dispatch` | Dispatch predictive maintenance work order to technicians |

### 5. Egress Proof Panel (Zero-Leakage Gateway)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/egress/proofs` | List side-by-side on-prem raw data vs approved cloud metadata |
| `GET` | `/api/v1/egress/proofs/{id}` | Get specific egress proof record |
| `POST` | `/api/v1/egress/process` | Simulate Local Collector: scrubs operator PII, formulas, IPs & calculates SHA-256 seal |
| `POST` | `/api/v1/egress/verify/{id}` | Validates SHA-256 hash integrity and certifies 100% zero sensitive data leakage |

### 6. System & Architecture
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v1/system/status` | Heartbeat, edge enclave latency, sampling rate, and certifications |
| `GET` | `/api/v1/system/pipeline-stages` | 8-Stage industrial data pipeline metadata |

---

## 🗄️ PostgreSQL Configuration

To run against your external PostgreSQL instance:

```bash
export SPRING_PROFILES_ACTIVE=postgres
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=factorypulse
export DB_USER=postgres
export DB_PASSWORD=postgres
mvn spring-boot:run
```
