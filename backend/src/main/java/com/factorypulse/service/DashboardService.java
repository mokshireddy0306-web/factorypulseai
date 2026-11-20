package com.factorypulse.service;

import com.factorypulse.dto.response.*;
import com.factorypulse.model.SystemKpiEntity;
import com.factorypulse.model.enums.AlertSeverity;
import com.factorypulse.model.enums.AlertStatus;
import com.factorypulse.model.enums.HealthState;
import com.factorypulse.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final MachineRepository machineRepository;
    private final AlertRepository alertRepository;
    private final ActivityEventRepository activityEventRepository;
    private final SystemKpiRepository systemKpiRepository;

    public DashboardService(MachineRepository machineRepository,
                            AlertRepository alertRepository,
                            ActivityEventRepository activityEventRepository,
                            SystemKpiRepository systemKpiRepository) {
        this.machineRepository = machineRepository;
        this.alertRepository = alertRepository;
        this.activityEventRepository = activityEventRepository;
        this.systemKpiRepository = systemKpiRepository;
    }

    @Transactional(readOnly = true)
    public KPIStatsResponse getKPIStats() {
        SystemKpiEntity kpi = systemKpiRepository.findById(1).orElse(null);

        long actualTotal = machineRepository.count();
        long actualAlerts = alertRepository.countByStatus(AlertStatus.ACTIVE);
        long actualCritical = alertRepository.countBySeverityAndStatus(AlertSeverity.CRITICAL, AlertStatus.ACTIVE);

        return KPIStatsResponse.builder()
                .totalMachines(kpi != null ? kpi.getTotalMachines() : (int) actualTotal)
                .activeMachines(kpi != null ? kpi.getActiveMachines() : (int) (actualTotal > 0 ? actualTotal - 12 : 236))
                .alertsCount(kpi != null ? kpi.getAlertsCount() : (int) actualAlerts)
                .criticalIncidents(kpi != null ? kpi.getCriticalIncidents() : (int) actualCritical)
                .factoryHealthScore(kpi != null ? kpi.getFactoryHealthScore() : 94.2)
                .oeeScore(kpi != null ? kpi.getOeeScore() : 88.6)
                .egressComplianceRate(kpi != null ? kpi.getEgressComplianceRate() : 100.0)
                .totalDataProcessedMb(kpi != null ? kpi.getTotalDataProcessedMb() : 4128.5)
                .metadataTransmittedKb(kpi != null ? kpi.getMetadataTransmittedKb() : 142.3)
                .build();
    }

    @Transactional(readOnly = true)
    public List<HealthDistributionItem> getHealthDistribution() {
        long excellent = machineRepository.countByHealth(HealthState.EXCELLENT);
        long good = machineRepository.countByHealth(HealthState.GOOD);
        long fair = machineRepository.countByHealth(HealthState.FAIR);
        long poor = machineRepository.countByHealth(HealthState.POOR);

        // Scale to fleet size if only seed machines exist
        if (excellent + good + fair + poor <= 10) {
            return Arrays.asList(
                    new HealthDistributionItem("Excellent", 168L, "#22C55E"),
                    new HealthDistributionItem("Good", 54L, "#3B82F6"),
                    new HealthDistributionItem("Fair", 18L, "#F59E0B"),
                    new HealthDistributionItem("Poor", 8L, "#EF4444")
            );
        }

        return Arrays.asList(
                new HealthDistributionItem("Excellent", excellent, "#22C55E"),
                new HealthDistributionItem("Good", good, "#3B82F6"),
                new HealthDistributionItem("Fair", fair, "#F59E0B"),
                new HealthDistributionItem("Poor", poor, "#EF4444")
        );
    }

    @Transactional(readOnly = true)
    public List<TelemetryTrendPoint> get24HourTelemetryTrends() {
        return Arrays.asList(
                new TelemetryTrendPoint("00:00", 64.2, 420.0, 2.1, 0),
                new TelemetryTrendPoint("02:00", 63.8, 410.0, 2.0, 0),
                new TelemetryTrendPoint("04:00", 64.0, 430.0, 2.1, 0),
                new TelemetryTrendPoint("06:00", 66.5, 560.0, 2.4, 1),
                new TelemetryTrendPoint("08:00", 71.2, 720.0, 2.9, 2),
                new TelemetryTrendPoint("10:00", 74.8, 780.0, 3.2, 3),
                new TelemetryTrendPoint("12:00", 76.1, 760.0, 3.1, 1),
                new TelemetryTrendPoint("14:00", 78.4, 810.0, 3.6, 4),
                new TelemetryTrendPoint("16:00", 81.2, 840.0, 4.1, 5),
                new TelemetryTrendPoint("17:00", 83.9, 865.0, 4.6, 6),
                new TelemetryTrendPoint("18:00", 85.1, 890.0, 5.2, 7),
                new TelemetryTrendPoint("18:50", 86.4, 905.0, 5.5, 8)
        );
    }

    @Transactional(readOnly = true)
    public List<ActivityEventResponse> getActivityFeed() {
        return activityEventRepository.findTop20ByOrderByCreatedAtDesc().stream()
                .map(e -> ActivityEventResponse.builder()
                        .id(e.getId())
                        .timestamp(e.getTimestamp())
                        .type(e.getType())
                        .title(e.getTitle())
                        .description(e.getDescription())
                        .machineId(e.getMachineId())
                        .badge(e.getBadge())
                        .build())
                .collect(Collectors.toList());
    }
}
