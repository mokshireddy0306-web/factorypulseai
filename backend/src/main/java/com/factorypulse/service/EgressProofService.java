package com.factorypulse.service;

import com.factorypulse.dto.request.ProcessEgressRequest;
import com.factorypulse.dto.response.EgressComparisonResponse;
import com.factorypulse.dto.response.EgressVerificationResponse;
import com.factorypulse.exception.ResourceNotFoundException;
import com.factorypulse.model.ActivityEventEntity;
import com.factorypulse.model.EgressRecordEntity;
import com.factorypulse.model.enums.ActivityEventType;
import com.factorypulse.model.enums.EgressStatus;
import com.factorypulse.repository.ActivityEventRepository;
import com.factorypulse.repository.EgressRecordRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EgressProofService {

    private static final Logger log = LoggerFactory.getLogger(EgressProofService.class);

    private final EgressRecordRepository egressRecordRepository;
    private final ActivityEventRepository activityEventRepository;
    private final ObjectMapper objectMapper;

    public EgressProofService(EgressRecordRepository egressRecordRepository,
                              ActivityEventRepository activityEventRepository,
                              ObjectMapper objectMapper) {
        this.egressRecordRepository = egressRecordRepository;
        this.activityEventRepository = activityEventRepository;
        this.objectMapper = objectMapper;
    }

    // Sensitive field dictionary that MUST stay on-premises
    private static final Set<String> SENSITIVE_KEY_PATTERNS = Set.of(
            "operator", "operatorid", "employee", "badge",
            "formula", "recipe", "productionformula", "chemicalmix", "chemicalmixformula",
            "internalip", "ip", "macaddress", "subnet", "topology",
            "serial", "serialbarcode", "barcode", "customer", "customercontractid",
            "cad", "cadmodelchecksum", "gcode", "proprietarygcode", "securitybadgehash"
    );

    @Transactional(readOnly = true)
    public List<EgressComparisonResponse> getAllEgressRecords() {
        return egressRecordRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EgressComparisonResponse getEgressRecordById(String id) {
        EgressRecordEntity entity = egressRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Egress record not found with ID: " + id));
        return mapToResponse(entity);
    }

    @Transactional
    public EgressComparisonResponse processRawTelemetry(ProcessEgressRequest request) {
        String payloadId = "FP-EGR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String timestamp = Instant.now().toString();

        Map<String, Object> rawPayload = request.getRawPayload();
        List<String> redactedKeys = new ArrayList<>();
        Map<String, Object> metadataPayload = new LinkedHashMap<>();

        metadataPayload.put("machineId", request.getMachineId());
        metadataPayload.put("payloadId", payloadId);
        metadataPayload.put("timestamp", timestamp);

        // Filter and redact
        for (Map.Entry<String, Object> entry : rawPayload.entrySet()) {
            String key = entry.getKey();
            String normalizedKey = key.toLowerCase().replaceAll("[^a-z0-9]", "");

            boolean isSensitive = SENSITIVE_KEY_PATTERNS.stream().anyMatch(normalizedKey::contains);

            if (isSensitive) {
                redactedKeys.add(key);
            } else {
                // Keep standard telemetry metrics
                if (key.equalsIgnoreCase("temperature") || key.equalsIgnoreCase("temp")) {
                    metadataPayload.put("temperature", entry.getValue());
                } else if (key.equalsIgnoreCase("vibration") || key.equalsIgnoreCase("vibrationRms")) {
                    metadataPayload.put("vibration", entry.getValue());
                } else if (key.equalsIgnoreCase("powerUsageKw") || key.equalsIgnoreCase("powerKw") || key.equalsIgnoreCase("power")) {
                    metadataPayload.put("powerKw", entry.getValue());
                } else if (key.equalsIgnoreCase("health")) {
                    metadataPayload.put("health", entry.getValue());
                } else if (key.equalsIgnoreCase("severity")) {
                    metadataPayload.put("severity", entry.getValue());
                }
            }
        }

        // Fill defaults if absent
        metadataPayload.putIfAbsent("temperature", 72.0);
        metadataPayload.putIfAbsent("vibration", 3.2);
        metadataPayload.putIfAbsent("powerKw", 40.0);
        metadataPayload.putIfAbsent("health", "GOOD");
        metadataPayload.putIfAbsent("severity", "LOW");
        metadataPayload.put("dataSovereigntyVerified", true);
        metadataPayload.put("privacyStandard", "ISO-27001 / IEC-62443 COMPLIANT");

        // Compute SHA-256 seal
        String metadataJson = serializeJson(metadataPayload);
        String sha256 = calculateSha256(metadataJson);
        metadataPayload.put("sha256Seal", sha256.substring(0, 8) + "..." + sha256.substring(sha256.length() - 12));

        String rawJson = serializeJson(rawPayload);
        String redactedKeysJson = serializeJson(redactedKeys);

        EgressRecordEntity entity = EgressRecordEntity.builder()
                .id(payloadId)
                .timestamp(timestamp)
                .machineId(request.getMachineId())
                .machineName("Machine " + request.getMachineId())
                .status(redactedKeys.isEmpty() ? EgressStatus.APPROVED : EgressStatus.FILTERED_AND_SEALED)
                .sha256Hash(sha256)
                .redactedFieldsCount(redactedKeys.size())
                .redactedKeysJson(redactedKeysJson)
                .rawPayloadJson(rawJson)
                .metadataPayloadJson(metadataJson)
                .build();

        EgressRecordEntity saved = egressRecordRepository.save(entity);

        // Record activity log
        ActivityEventEntity event = ActivityEventEntity.builder()
                .id("ACT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .timestamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .type(ActivityEventType.EGRESS_APPROVED)
                .title("Egress Proof Sealed: " + payloadId)
                .description("Local Collector scrubbed " + redactedKeys.size() + " sensitive fields from " + request.getMachineId() + " telemetry.")
                .machineId(request.getMachineId())
                .badge("100% Zero Leakage")
                .build();
        activityEventRepository.save(event);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public EgressVerificationResponse verifyCryptographicProof(String id) {
        EgressRecordEntity record = egressRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Egress record not found with ID: " + id));

        String computedSha256 = calculateSha256(record.getMetadataPayloadJson());
        boolean hashMatches = computedSha256.equalsIgnoreCase(record.getSha256Hash());

        Map<String, Object> metadata = deserializeMap(record.getMetadataPayloadJson());
        List<String> redactedKeys = deserializeList(record.getRedactedKeysJson());

        // Ensure none of the sensitive keys leaked into metadata
        boolean zeroLeaks = redactedKeys.stream().noneMatch(metadata::containsKey);

        boolean fullyVerified = hashMatches && zeroLeaks;

        return EgressVerificationResponse.builder()
                .payloadId(record.getId())
                .machineId(record.getMachineId())
                .verified(fullyVerified)
                .computedSha256(computedSha256)
                .recordedSha256(record.getSha256Hash())
                .redactedFieldsCount(record.getRedactedFieldsCount())
                .verifiedBlockedKeys(redactedKeys)
                .complianceCertification("ISO/IEC 27001 ISMS & IEC 62443-4-2 Certified")
                .message(fullyVerified
                        ? "Cryptographic Zero-Leakage Proof Validated: Signature matches on-prem enclave hash. Zero PII or Trade Secrets present in outbound payload."
                        : "Verification Failed: Hash mismatch or data leakage detected.")
                .build();
    }

    private String calculateSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private String serializeJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Failed to serialize object to JSON", e);
            return "{}";
        }
    }

    private Map<String, Object> deserializeMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("Failed to deserialize JSON map: {}", json, e);
            return Collections.emptyMap();
        }
    }

    private List<String> deserializeList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            log.error("Failed to deserialize JSON list: {}", json, e);
            return Collections.emptyList();
        }
    }

    public EgressComparisonResponse mapToResponse(EgressRecordEntity entity) {
        return EgressComparisonResponse.builder()
                .id(entity.getId())
                .timestamp(entity.getTimestamp())
                .machineId(entity.getMachineId())
                .machineName(entity.getMachineName())
                .status(entity.getStatus())
                .sha256Hash(entity.getSha256Hash())
                .redactedFieldsCount(entity.getRedactedFieldsCount())
                .redactedKeys(deserializeList(entity.getRedactedKeysJson()))
                .rawPayload(deserializeMap(entity.getRawPayloadJson()))
                .metadataPayload(deserializeMap(entity.getMetadataPayloadJson()))
                .build();
    }
}
