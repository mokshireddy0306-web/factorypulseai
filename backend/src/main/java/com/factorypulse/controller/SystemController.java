package com.factorypulse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
@Tag(name = "System & Architecture", description = "Endpoints for Edge Enclave health, cybersecurity certifications, and architecture pipeline metadata")
public class SystemController {

    @GetMapping("/status")
    @Operation(summary = "Get Local Collector & Cloud SaaS Status", description = "Returns heartbeat, latency, enclave security mode, and active certifications")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> status = new LinkedHashMap<>();
        status.put("localCollectorVersion", "v2.4.1-enclave");
        status.put("status", "HEALTHY");
        status.put("edgeEnclaveActive", true);
        status.put("samplingRate", "100 Hz On-Premises");
        status.put("edgeLatencyMs", 0.8);
        status.put("cloudTransitTls", "TLS 1.3 / mTLS");
        status.put("dataSovereigntyCompliance", "100.0%");
        status.put("certifications", Arrays.asList(
                "IEC 62443-4-2 (Industrial Cybersecurity)",
                "ISO/IEC 27001 (ISMS)",
                "NIST SP 800-82r3",
                "ITAR / Defense Data Sovereignty Safe",
                "GDPR Article 25 (Privacy by Design)"
        ));
        status.put("timestamp", Instant.now().toString());
        return ResponseEntity.ok(status);
    }

    @GetMapping("/pipeline-stages")
    @Operation(summary = "Get 8-Stage Architecture Pipeline Metadata", description = "Returns the sequence of nodes bridging Factory OT and Cloud IT")
    public ResponseEntity<List<Map<String, Object>>> getPipelineStages() {
        List<Map<String, Object>> stages = Arrays.asList(
                Map.of("step", 1, "title", "Factory Machines & Sensors", "zone", "ON_PREMISE", "protocol", "OPC-UA / Modbus / MQTT"),
                Map.of("step", 2, "title", "Local Collector", "zone", "ON_PREMISE", "protocol", "Local Socket / gRPC"),
                Map.of("step", 3, "title", "Data Filtering & Redaction", "zone", "ON_PREMISE", "protocol", "AST Redaction Pipeline"),
                Map.of("step", 4, "title", "Metadata Generation", "zone", "ON_PREMISE", "protocol", "FFT / Statistical Aggregation"),
                Map.of("step", 5, "title", "Egress Proof Panel", "zone", "ON_PREMISE", "protocol", "HMAC-SHA256 / HSM Seal"),
                Map.of("step", 6, "title", "Secure SaaS Platform", "zone", "CLOUD", "protocol", "TLS 1.3 / Kafka / REST"),
                Map.of("step", 7, "title", "AIOps Engine", "zone", "CLOUD", "protocol", "Isolation Forest / LSTM Recurrent Autoencoder"),
                Map.of("step", 8, "title", "Actionable Recommendations", "zone", "CLOUD", "protocol", "Webhook / PagerDuty / Work Orders")
        );
        return ResponseEntity.ok(stages);
    }
}
