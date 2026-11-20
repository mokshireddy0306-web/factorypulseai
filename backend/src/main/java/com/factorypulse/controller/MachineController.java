package com.factorypulse.controller;

import com.factorypulse.dto.request.CreateMachineRequest;
import com.factorypulse.dto.request.IngestTelemetryRequest;
import com.factorypulse.dto.response.MachineResponse;
import com.factorypulse.model.enums.HealthState;
import com.factorypulse.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/machines")
@Tag(name = "Machine Fleet", description = "Endpoints for industrial machine monitoring, telemetry ingestion, and health state inspection")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping
    @Operation(summary = "List all machines", description = "Query machines with optional filtering by health state, factory line, and text search")
    public ResponseEntity<List<MachineResponse>> getAllMachines(
            @RequestParam(required = false) HealthState health,
            @RequestParam(required = false, defaultValue = "ALL") String line,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(machineService.getAllMachines(health, line, search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get machine details by ID", description = "Returns full machine telemetry gauges, operating parameters, and rolling history")
    public ResponseEntity<MachineResponse> getMachineById(@PathVariable String id) {
        return ResponseEntity.ok(machineService.getMachineById(id));
    }

    @PostMapping
    @Operation(summary = "Register new machine", description = "Creates a new machine record with specified telemetry boundaries")
    public ResponseEntity<MachineResponse> createMachine(@Valid @RequestBody CreateMachineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.createMachine(request));
    }

    @PostMapping("/{id}/telemetry")
    @Operation(summary = "Ingest machine telemetry", description = "Ingests a live sensor reading, updating machine temperature, power, and vibration with dynamic health re-scoring")
    public ResponseEntity<MachineResponse> ingestTelemetry(
            @PathVariable String id,
            @Valid @RequestBody IngestTelemetryRequest request) {
        return ResponseEntity.ok(machineService.ingestTelemetry(id, request));
    }
}
