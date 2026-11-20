package com.factorypulse.service;

import com.factorypulse.dto.response.AIOpsRecommendationResponse;
import com.factorypulse.dto.response.ContributingSignalResponse;
import com.factorypulse.exception.ResourceNotFoundException;
import com.factorypulse.model.ActivityEventEntity;
import com.factorypulse.model.AIOpsRecommendationEntity;
import com.factorypulse.model.enums.ActivityEventType;
import com.factorypulse.repository.ActivityEventRepository;
import com.factorypulse.repository.AIOpsRecommendationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AIOpsService {

    private final AIOpsRecommendationRepository recommendationRepository;
    private final ActivityEventRepository activityEventRepository;

    public AIOpsService(AIOpsRecommendationRepository recommendationRepository,
                        ActivityEventRepository activityEventRepository) {
        this.recommendationRepository = recommendationRepository;
        this.activityEventRepository = activityEventRepository;
    }

    @Transactional(readOnly = true)
    public List<AIOpsRecommendationResponse> getAllRecommendations() {
        return recommendationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AIOpsRecommendationResponse getRecommendationById(String id) {
        AIOpsRecommendationEntity entity = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AIOps recommendation not found with ID: " + id));
        return mapToResponse(entity);
    }

    @Transactional
    public AIOpsRecommendationResponse dispatchWorkOrder(String id) {
        AIOpsRecommendationEntity rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AIOps recommendation not found with ID: " + id));

        rec.setDispatched(true);
        AIOpsRecommendationEntity saved = recommendationRepository.save(rec);

        // Record activity log
        ActivityEventEntity event = ActivityEventEntity.builder()
                .id("ACT-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase())
                .timestamp(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .type(ActivityEventType.MAINTENANCE_LOG)
                .title("Predictive Maintenance Dispatched")
                .description("Work order created for " + rec.getMachineName() + ". Action: " + rec.getRecommendedAction())
                .machineId(rec.getMachineId())
                .badge("Dispatched")
                .build();
        activityEventRepository.save(event);

        return mapToResponse(saved);
    }

    public AIOpsRecommendationResponse mapToResponse(AIOpsRecommendationEntity entity) {
        List<ContributingSignalResponse> signals = entity.getContributingSignals().stream()
                .map(s -> new ContributingSignalResponse(s.getName(), s.getValue(), s.getDeviation(), s.getSeverity()))
                .collect(Collectors.toList());

        return AIOpsRecommendationResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .machineId(entity.getMachineId())
                .machineName(entity.getMachineName())
                .line(entity.getLine())
                .severity(entity.getSeverity())
                .confidenceScore(entity.getConfidenceScore())
                .contributingSignals(signals)
                .rootCause(entity.getRootCause())
                .recommendedAction(entity.getRecommendedAction())
                .estimatedTimeToFailure(entity.getEstimatedTimeToFailure())
                .financialImpactPrevented(entity.getFinancialImpactPrevented())
                .downtimePreventedHours(entity.getDowntimePreventedHours())
                .urgency(entity.getUrgency())
                .modelAlgorithm(entity.getModelAlgorithm())
                .dispatched(entity.getDispatched())
                .build();
    }
}
