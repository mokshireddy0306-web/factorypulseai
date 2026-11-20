package com.factorypulse.service;

import com.factorypulse.dto.request.CreateMachineRequest;
import com.factorypulse.dto.request.IngestTelemetryRequest;
import com.factorypulse.dto.response.MachineResponse;
import com.factorypulse.dto.response.TelemetryHistoryPoint;
import com.factorypulse.exception.ResourceNotFoundException;
import com.factorypulse.model.MachineEntity;
import com.factorypulse.model.TelemetryRecordEntity;
import com.factorypulse.model.enums.HealthState;
import com.factorypulse.repository.MachineRepository;
import com.factorypulse.repository.TelemetryRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;

    public MachineService(MachineRepository machineRepository, TelemetryRecordRepository telemetryRecordRepository) {
        this.machineRepository = machineRepository;
        this.telemetryRecordRepository = telemetryRecordRepository;
    }

    @Transactional(readOnly = true)
    public List<MachineResponse> getAllMachines(HealthState health, String line, String search) {
        List<MachineEntity> entities;
        if (health != null || (line != null && !line.equals("ALL")) || (search != null && !search.isBlank())) {
            entities = machineRepository.searchMachines(health, line, search);
        } else {
            entities = machineRepository.findAll();
        }
        return entities.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MachineResponse getMachineById(String id) {
        MachineEntity entity = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + id));
        return mapToResponse(entity);
    }

    @Transactional
    public MachineResponse createMachine(CreateMachineRequest request) {
        MachineEntity entity = MachineEntity.builder()
                .id(request.getId())
                .name(request.getName())
                .line(request.getLine())
                .model(request.getModel())
                .temperature(request.getTemperature())
                .tempMax(request.getTempMax())
                .powerUsage(request.getPowerUsage())
                .powerMax(request.getPowerMax())
                .vibration(request.getVibration())
                .vibrationMax(request.getVibrationMax())
                .connectivity(request.getConnectivity())
                .health(request.getHealth())
                .healthScore(request.getHealthScore())
                .uptime(request.getUptime())
                .operatingHours(request.getOperatingHours())
                .lastMaintenance(request.getLastMaintenance())
                .nextScheduledMaintenance(request.getNextScheduledMaintenance())
                .operatorId(request.getOperatorId())
                .proprietaryCode(request.getProprietaryCode())
                .internalIp(request.getInternalIp())
                .build();

        MachineEntity saved = machineRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public MachineResponse ingestTelemetry(String machineId, IngestTelemetryRequest request) {
        MachineEntity machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found with ID: " + machineId));

        // Update real-time state
        machine.setTemperature(request.getTemperature());
        machine.setPowerUsage(request.getPower());
        machine.setVibration(request.getVibration());

        // Dynamic health state update
        if (request.getTemperature() > machine.getTempMax() || request.getVibration() > machine.getVibrationMax()) {
            machine.setHealth(HealthState.POOR);
            machine.setHealthScore(Math.max(30, machine.getHealthScore() - 15));
        } else if (request.getTemperature() > (machine.getTempMax() * 0.9) || request.getVibration() > (machine.getVibrationMax() * 0.85)) {
            machine.setHealth(HealthState.FAIR);
            machine.setHealthScore(Math.min(75, Math.max(55, machine.getHealthScore())));
        } else {
            machine.setHealth(HealthState.GOOD);
            machine.setHealthScore(Math.min(98, machine.getHealthScore() + 2));
        }

        String timeLabel = request.getTimeLabel() != null
                ? request.getTimeLabel()
                : LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        TelemetryRecordEntity record = TelemetryRecordEntity.builder()
                .machine(machine)
                .timeLabel(timeLabel)
                .temperature(request.getTemperature())
                .power(request.getPower())
                .vibration(request.getVibration())
                .build();

        machine.getTelemetryHistory().add(record);
        machineRepository.save(machine);

        return mapToResponse(machine);
    }

    public MachineResponse mapToResponse(MachineEntity entity) {
        List<TelemetryHistoryPoint> history = entity.getTelemetryHistory().stream()
                .map(t -> new TelemetryHistoryPoint(t.getTimeLabel(), t.getTemperature(), t.getPower(), t.getVibration()))
                .collect(Collectors.toList());

        return MachineResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .line(entity.getLine())
                .model(entity.getModel())
                .temperature(entity.getTemperature())
                .tempMax(entity.getTempMax())
                .powerUsage(entity.getPowerUsage())
                .powerMax(entity.getPowerMax())
                .vibration(entity.getVibration())
                .vibrationMax(entity.getVibrationMax())
                .connectivity(entity.getConnectivity())
                .health(entity.getHealth())
                .healthScore(entity.getHealthScore())
                .uptime(entity.getUptime())
                .operatingHours(entity.getOperatingHours())
                .lastMaintenance(entity.getLastMaintenance())
                .nextScheduledMaintenance(entity.getNextScheduledMaintenance())
                .operatorId(entity.getOperatorId())
                .proprietaryCode(entity.getProprietaryCode())
                .internalIp(entity.getInternalIp())
                .telemetryHistory(history)
                .build();
    }
}
