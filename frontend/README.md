# FactoryPulse AI — Frontend

> **Secure AIOps-Powered Observability Platform for Manufacturing Industries**  
> Built for hackathon demonstration with enterprise-grade Datadog/Grafana-tier UI.

---

## 🚀 Quick Start

From the project root:

```bash
cd frontend
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

To create an optimized production build:

```bash
npm run build
```

---

## 🎨 Color Palette & Design System

- **Dark Background:** `#0B1220` (Slate/Navy 950)
- **Primary Blue:** `#3B82F6` (Active navigations, accents, KPIs)
- **Success Green:** `#22C55E` (Healthy machines, approved metadata, 100% compliance)
- **Warning Yellow:** `#F59E0B` (Fair states, medium anomalies, warnings)
- **Critical Red:** `#EF4444` (Poor health, critical incidents, blocked PII/trade secret fields)
- **Light Grey Text:** `#E5E7EB` (Clean typography using Inter & JetBrains Mono)
- **Glassmorphism:** Layered frosted glass panels (`backdrop-blur-md`, subtle glowing borders)

---

## 📑 7 Complete Production Pages Included

1. **Dashboard Overview (`/`)**
   - 5 KPI Cards: Total Machines (248), Active Machines (236), Alerts Count (14), Critical Incidents (2), Factory Health Score (94.2%).
   - Real-time telemetry monitoring cards with sparklines.
   - Interactive Recharts Donut Chart: Machine Health Distribution.
   - Recharts Area Trends: Temperature (°C), Power Draw (kW), Vibration (mm/s).
   - Recharts Bar Chart: Incident Severity Breakdown.
   - Recent Anomaly Incidents & Real-Time Event Log.
   - Data Sovereignty metrics (4.12 GB processed locally vs 142 KB sent).

2. **Machine Monitoring Page**
   - Toggle between responsive **Grid View** and high-density **Table View**.
   - Filters for Health State (`Excellent`, `Good`, `Fair`, `Poor`), Production Line, and live Search.
   - Telemetry gauges for Temperature, Power, and Vibration.
   - Click to inspect Machine Detail Modal with local collector privacy status.

3. **Alert & Incident Center**
   - Real-time active alerts categorized by severity (`Critical`, `High`, `Medium`, `Low`).
   - Expandable alert rows showing root cause hypotheses and threshold breaches.
   - Interactive **Acknowledge Alert** and **Mark Resolved** workflow actions with animated toast feedback.

4. **AIOps Recommendation Page**
   - Machine learning insights (e.g. *Potential Motor Bearing Catastrophic Failure* on Welding Robot A).
   - Radial model confidence scores (e.g. 92%, 95%).
   - Contributing telemetry signals breakdown with deviation metrics.
   - Root cause hypothesis & prescriptive maintenance actions.
   - Downtime prevention & ROI figures ($42,500 prevented).
   - Dispatch Work Order action with instant technician status updates.

5. **Egress Proof Panel (Mandatory Hackathon Feature)**
   - **Side-by-Side Comparison:**
     - **Left Side:** Raw Machine Telemetry (stays 100% on-premises; highlights operator PII, proprietary formulas, and internal IPs with red redaction tags).
     - **Right Side:** Approved Metadata Sent To SaaS (clean, sanitized payload with SHA-256 integrity seal).
   - Interactive "Verify Cryptographic Proof" action.
   - Live stream toggle demonstrating continuous zero-leakage serialization.
   - Regulatory checklist (IEC 62443, ISO 27001, ITAR Defense, GDPR).

6. **Architecture Visualization Page**
   - 8-Stage Interactive Data Pipeline:
     1. Factory Machines & Sensors
     2. Local Collector
     3. Data Filtering & Redaction
     4. Metadata Generation
     5. Egress Proof Panel
     6. Secure SaaS Platform
     7. AIOps Engine
     8. Actionable Recommendations
   - Interactive stage inspector with incoming/outgoing data diffs and protocols.
   - Clear visual boundary dividing **Factory Local Enclave** from **Cloud SaaS**.

7. **About Project Page**
   - The multi-billion dollar manufacturing dilemma explained.
   - Why sensitive data must stay local (trade secret formulas, operator PII, cyber threats).
   - The metadata-only architecture breakdown.
   - Interactive **ROI Downtime Calculator** allowing judges to test fleet scale and savings.
   - Architecture comparison matrix vs traditional APM vendors.

---

## 🛠️ Tech Stack

- **React 18** + **TypeScript**
- **Tailwind CSS v3** (Custom industrial theme)
- **Recharts** (Interactive telemetry, donut, and severity bar charts)
- **Lucide React** (Modern enterprise icons)
- **Vite 6** (Blazing-fast build tool)
