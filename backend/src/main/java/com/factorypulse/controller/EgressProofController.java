package com.factorypulse.controller;

import com.factorypulse.dto.request.ProcessEgressRequest;
import com.factorypulse.dto.response.EgressComparisonResponse;
import com.factorypulse.dto.response.EgressVerificationResponse;
import com.factorypulse.service.EgressProofService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/egress")
@Tag(name = "Egress Proof & Privacy Gateway", description = "Core hackathon module: Local Collector data redaction, zero-knowledge verification, and cryptographic audit proofs")
public class EgressProofController {

    private final EgressProofService egressProofService;

    public EgressProofController(EgressProofService egressProofService) {
        this.egressProofService = egressProofService;
    }

    @GetMapping("/proofs")
    @Operation(summary = "List egress comparison proofs", description = "Returns side-by-side audit records of on-premise raw telemetry vs approved cloud metadata")
    public ResponseEntity<List<EgressComparisonResponse>> getAllEgressRecords() {
        return ResponseEntity.ok(egressProofService.getAllEgressRecords());
    }

    @GetMapping("/proofs/{id}")
    @Operation(summary = "Get egress proof record by ID", description = "Returns single comparison record including raw payload with blocked keys and sanitized metadata")
    public ResponseEntity<EgressComparisonResponse> getEgressRecordById(@PathVariable String id) {
        return ResponseEntity.ok(egressProofService.getEgressRecordById(id));
    }

    @PostMapping("/process")
    @Operation(summary = "Process raw telemetry at local edge", description = "Simulates on-prem Local Collector: scrubs operator PII, formulas, and internal IPs, then calculates SHA-256 seal for cloud egress")
    public ResponseEntity<EgressComparisonResponse> processRawTelemetry(@Valid @RequestBody ProcessEgressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(egressProofService.processRawTelemetry(request));
    }

    @PostMapping("/verify/{id}")
    @Operation(summary = "Verify cryptographic zero-leakage proof", description = "Validates the SHA-256 hash against the hardware enclave signature and certifies zero PII or trade secret leakage")
    public ResponseEntity<EgressVerificationResponse> verifyCryptographicProof(@PathVariable String id) {
        return ResponseEntity.ok(egressProofService.verifyCryptographicProof(id));
    }
}
