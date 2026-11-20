package com.factorypulse.controller;

import com.factorypulse.dto.request.AcknowledgeAlertRequest;
import com.factorypulse.dto.response.AlertResponse;
import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.AlertStatus;
import com.factorypulse.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
@Tag(name = "Alerts & Incidents", description = "Endpoints for triage, acknowledgment, and resolution of factory machine anomalies")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    @Operation(summary = "List alerts", description = "Query active and historical alerts with optional filters for severity, status, and search")
    public ResponseEntity<List<AlertResponse>> getAlerts(
            @RequestParam(required = false) AlertSeverity severity,
            @RequestParam(required = false) AlertStatus status,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(alertService.getAlerts(severity, status, search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert details", description = "Returns triggering sensor signals, threshold breaches, and recommended containment protocols")
    public ResponseEntity<AlertResponse> getAlertById(@PathVariable String id) {
        return ResponseEntity.ok(alertService.getAlertById(id));
    }

    @PostMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge alert", description = "Assigns incident to the technician shift log and transitions state to ACKNOWLEDGED")
    public ResponseEntity<AlertResponse> acknowledgeAlert(
            @PathVariable String id,
            @RequestBody(required = false) AcknowledgeAlertRequest request) {
        return ResponseEntity.ok(alertService.acknowledgeAlert(id, request));
    }

    @PostMapping("/{id}/resolve")
    @Operation(summary = "Resolve alert", description = "Closes incident, records resolution timestamp, and recalibrates factory health score")
    public ResponseEntity<AlertResponse> resolveAlert(@PathVariable String id) {
        return ResponseEntity.ok(alertService.resolveAlert(id));
    }
}
