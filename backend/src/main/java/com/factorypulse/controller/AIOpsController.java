package com.factorypulse.controller;

import com.factorypulse.dto.response.AIOpsRecommendationResponse;
import com.factorypulse.service.AIOpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aiops")
@Tag(name = "AIOps Intelligence", description = "Endpoints for machine learning diagnostics, predictive failure probabilities, and maintenance dispatching")
public class AIOpsController {

    private final AIOpsService aiopsService;

    public AIOpsController(AIOpsService aiopsService) {
        this.aiopsService = aiopsService;
    }

    @GetMapping("/recommendations")
    @Operation(summary = "List all AIOps recommendations", description = "Returns active predictive failure warnings, model confidence scores, contributing signals, and downtime avoidance metrics")
    public ResponseEntity<List<AIOpsRecommendationResponse>> getAllRecommendations() {
        return ResponseEntity.ok(aiopsService.getAllRecommendations());
    }

    @GetMapping("/recommendations/{id}")
    @Operation(summary = "Get recommendation by ID", description = "Returns deep diagnosis, root cause hypothesis, and prescriptive containment steps")
    public ResponseEntity<AIOpsRecommendationResponse> getRecommendationById(@PathVariable String id) {
        return ResponseEntity.ok(aiopsService.getRecommendationById(id));
    }

    @PostMapping("/recommendations/{id}/dispatch")
    @Operation(summary = "Dispatch predictive work order", description = "Dispatches maintenance order to floor technicians, marks recommendation as dispatched, and logs event")
    public ResponseEntity<AIOpsRecommendationResponse> dispatchWorkOrder(@PathVariable String id) {
        return ResponseEntity.ok(aiopsService.dispatchWorkOrder(id));
    }
}
