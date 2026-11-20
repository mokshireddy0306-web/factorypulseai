package com.factorypulse.service;

import com.factorypulse.dto.request.AcknowledgeAlertRequest;
import com.factorypulse.dto.response.AlertResponse;
import com.factorypulse.dto.response.SeverityDistributionItem;
import com.factorypulse.exception.ResourceNotFoundException;
import com.factorypulse.model.ActivityEventEntity;
import com.factorypulse.model.AlertEntity;
import com.factorypulse.model.enums.ActivityEventType;
import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.AlertStatus;
import com.factorypulse.repository.ActivityEventRepository;
import com.factorypulse.repository.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final ActivityEventRepository activityEventRepository;

    public AlertService(AlertRepository alertRepository, ActivityEventRepository activityEventRepository) {
        this.alertRepository = alertRepository;
        this.activityEventRepository = activityEventRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertResponse> getAlerts(AlertSeverity severity, AlertStatus status, String search) {
        List<AlertEntity> entities;
        if (severity != null || status != null || (search != null && !search.isBlank())) {
            entities = alertRepository.searchAlerts(severity, status, search);
        } else {
            entities = alertRepository.findAll();
        }
        return entities.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlertResponse getAlertById(String id) {
        AlertEntity entity = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with ID: " + id));
        return mapToResponse(entity);
    }

    @Transactional
    public AlertResponse acknowledgeAlert(String id, AcknowledgeAlertRequest request) {
        AlertEntity alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with ID: " + id));

        String operator = (request != null && request.getOperatorName() != null && !request.getOperatorName().isBlank())
                ? request.getOperatorName()
                : "Current Operator (Shift Engineer)";

        alert.setStatus(AlertStatus.ACKNOWLEDGED);
        alert.setAcknowledgedBy(operator);
        AlertEntity saved = alertRepository.save(alert);

        // Record activity log
        ActivityEventEntity event = ActivityEventEntity.builder()
                .id("ACT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .timestamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .type(ActivityEventType.INCIDENT_ACK)
                .title("Alert Acknowledged: " + alert.getTitle())
                .description("Operator " + operator + " acknowledged incident on " + alert.getMachineName() + ".")
                .machineId(alert.getMachineId())
                .badge("Acknowledged")
                .build();
        activityEventRepository.save(event);

        return mapToResponse(saved);
    }

    @Transactional
    public AlertResponse resolveAlert(String id) {
        AlertEntity alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with ID: " + id));

        alert.setStatus(AlertStatus.RESOLVED);
        alert.setResolvedAt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        AlertEntity saved = alertRepository.save(alert);

        // Record activity log
        ActivityEventEntity event = ActivityEventEntity.builder()
                .id("ACT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .timestamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .type(ActivityEventType.MAINTENANCE_LOG)
                .title("Incident Resolved: " + alert.getTitle())
                .description("Alert on " + alert.getMachineName() + " (" + alert.getMachineId() + ") was closed and marked resolved.")
                .machineId(alert.getMachineId())
                .badge("Resolved")
                .build();
        activityEventRepository.save(event);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SeverityDistributionItem> getSeverityDistribution() {
        long criticalCount = alertRepository.countBySeverityAndStatus(AlertSeverity.CRITICAL, AlertStatus.ACTIVE);
        long highCount = alertRepository.countBySeverityAndStatus(AlertSeverity.HIGH, AlertStatus.ACTIVE);
        long mediumCount = alertRepository.countBySeverityAndStatus(AlertSeverity.MEDIUM, AlertStatus.ACTIVE);
        long lowCount = alertRepository.countBySeverityAndStatus(AlertSeverity.LOW, AlertStatus.ACTIVE);

        return Arrays.asList(
                new SeverityDistributionItem("Critical", criticalCount, "#EF4444"),
                new SeverityDistributionItem("High", highCount, "#F97316"),
                new SeverityDistributionItem("Medium", mediumCount, "#F59E0B"),
                new SeverityDistributionItem("Low", lowCount, "#3B82F6")
        );
    }

    public AlertResponse mapToResponse(AlertEntity entity) {
        return AlertResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .severity(entity.getSeverity())
                .status(entity.getStatus())
                .machineId(entity.getMachineId())
                .machineName(entity.getMachineName())
                .line(entity.getLine())
                .timestamp(entity.getTimestamp())
                .triggerSignal(entity.getTriggerSignal())
                .triggerValue(entity.getTriggerValue())
                .thresholdValue(entity.getThresholdValue())
                .recommendedAction(entity.getRecommendedAction())
                .acknowledgedBy(entity.getAcknowledgedBy())
                .resolvedAt(entity.getResolvedAt())
                .build();
    }
}
