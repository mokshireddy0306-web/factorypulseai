package com.factorypulse.controller;

import com.factorypulse.dto.response.*;
import com.factorypulse.service.AlertService;
import com.factorypulse.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard & Analytics", description = "Endpoints for fleet KPIs, telemetry trends, health scores, and activity feeds")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AlertService alertService;

    public DashboardController(DashboardService dashboardService, AlertService alertService) {
        this.dashboardService = dashboardService;
        this.alertService = alertService;
    }

    @GetMapping("/kpis")
    @Operation(summary = "Get Factory Fleet KPIs", description = "Returns total machines, active units, alert counts, OEE, and data sovereignty compliance")
    public ResponseEntity<KPIStatsResponse> getKPIStats() {
        return ResponseEntity.ok(dashboardService.getKPIStats());
    }

    @GetMapping("/health-distribution")
    @Operation(summary = "Get Machine Health Distribution", description = "Returns count breakdown by health state: Excellent, Good, Fair, Poor")
    public ResponseEntity<List<HealthDistributionItem>> getHealthDistribution() {
        return ResponseEntity.ok(dashboardService.getHealthDistribution());
    }

    @GetMapping("/trends")
    @Operation(summary = "Get 24h Telemetry Trends", description = "Returns hourly aggregated temperature, power usage, vibration, and anomaly spikes")
    public ResponseEntity<List<TelemetryTrendPoint>> getTelemetryTrends(
            @RequestParam(defaultValue = "24h") String range) {
        return ResponseEntity.ok(dashboardService.get24HourTelemetryTrends());
    }

    @GetMapping("/severity-breakdown")
    @Operation(summary = "Get Alert Severity Distribution", description = "Returns active alert counts for Critical, High, Medium, and Low incidents")
    public ResponseEntity<List<SeverityDistributionItem>> getSeverityDistribution() {
        return ResponseEntity.ok(alertService.getSeverityDistribution());
    }

    @GetMapping("/activity-feed")
    @Operation(summary = "Get Real-Time Edge & Cloud Activity Feed", description = "Returns chronological event stream of edge ingestion, PII redactions, and AIOps alerts")
    public ResponseEntity<List<ActivityEventResponse>> getActivityFeed() {
        return ResponseEntity.ok(dashboardService.getActivityFeed());
    }
}
