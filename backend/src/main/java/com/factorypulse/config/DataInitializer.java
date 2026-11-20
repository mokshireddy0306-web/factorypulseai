package com.factorypulse.config;

import com.factorypulse.model.*;
import com.factorypulse.model.enums.*;
import com.factorypulse.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final MachineRepository machineRepository;
    private final TelemetryRecordRepository telemetryRecordRepository;
    private final AlertRepository alertRepository;
    private final AIOpsRecommendationRepository recommendationRepository;
    private final EgressRecordRepository egressRecordRepository;
    private final ActivityEventRepository activityEventRepository;
    private final SystemKpiRepository systemKpiRepository;

    public DataInitializer(MachineRepository machineRepository,
                           TelemetryRecordRepository telemetryRecordRepository,
                           AlertRepository alertRepository,
                           AIOpsRecommendationRepository recommendationRepository,
                           EgressRecordRepository egressRecordRepository,
                           ActivityEventRepository activityEventRepository,
                           SystemKpiRepository systemKpiRepository) {
        this.machineRepository = machineRepository;
        this.telemetryRecordRepository = telemetryRecordRepository;
        this.alertRepository = alertRepository;
        this.recommendationRepository = recommendationRepository;
        this.egressRecordRepository = egressRecordRepository;
        this.activityEventRepository = activityEventRepository;
        this.systemKpiRepository = systemKpiRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (machineRepository.count() > 0) {
            log.info("Database already seeded with {} machines. Skipping seed initialization.", machineRepository.count());
            return;
        }

        log.info("Seeding FactoryPulse AI manufacturing telemetry database...");

        // 1. Seed Machines
        MachineEntity m1 = MachineEntity.builder()
                .id("M01").name("Welding Robot A").line("Line A - Structural Framing").model("Kuka Titan KR-1000")
                .temperature(95.4).tempMax(85.0).powerUsage(48.2).powerMax(42.0).vibration(7.8).vibrationMax(4.5)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.POOR).healthScore(42).uptime(94.1).operatingHours(4210)
                .lastMaintenance("2026-10-14").nextScheduledMaintenance("2026-11-28")
                .operatorId("EMP-7842-JONES").proprietaryCode("FORMULA-TITAN-ARC-V9").internalIp("192.168.10.45")
                .build();

        MachineEntity m2 = MachineEntity.builder()
                .id("M02").name("5-Axis CNC Milling Center").line("Line B - Precision Machining").model("DMG Mori DMU 50")
                .temperature(68.2).tempMax(75.0).powerUsage(31.4).powerMax(38.0).vibration(2.3).vibrationMax(3.5)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.EXCELLENT).healthScore(98).uptime(99.4).operatingHours(1850)
                .lastMaintenance("2026-11-02").nextScheduledMaintenance("2027-01-15")
                .operatorId("EMP-1190-CHEN").proprietaryCode("AERO-TURBINE-GCODE-REV4").internalIp("192.168.10.46")
                .build();

        MachineEntity m3 = MachineEntity.builder()
                .id("M03").name("Hydraulic Stamping Press 3").line("Line C - Heavy Stamping").model("Schuler Servo Press 800T")
                .temperature(84.1).tempMax(80.0).powerUsage(112.5).powerMax(105.0).vibration(5.4).vibrationMax(4.8)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.FAIR).healthScore(68).uptime(96.2).operatingHours(6120)
                .lastMaintenance("2026-09-20").nextScheduledMaintenance("2026-11-24")
                .operatorId("EMP-3401-KOVACS").proprietaryCode("AUTO-HOOD-STAMP-SPEC-3").internalIp("192.168.10.47")
                .build();

        MachineEntity m4 = MachineEntity.builder()
                .id("M04").name("Fiber Laser Cutting Cell 01").line("Line B - Sheet Processing").model("Trumpf TruLaser 5030")
                .temperature(54.6).tempMax(65.0).powerUsage(22.8).powerMax(28.0).vibration(1.4).vibrationMax(2.5)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.EXCELLENT).healthScore(96).uptime(99.1).operatingHours(2400)
                .lastMaintenance("2026-10-29").nextScheduledMaintenance("2026-12-20")
                .operatorId("EMP-9032-MUELLER").proprietaryCode("CHASSIS-CONTOUR-PATS").internalIp("192.168.10.48")
                .build();

        MachineEntity m5 = MachineEntity.builder()
                .id("M05").name("Automated Guided Vehicle Fleet Hub").line("Logistics Corridor 2").model("KION AGV Carrier Hub")
                .temperature(42.0).tempMax(50.0).powerUsage(14.2).powerMax(18.0).vibration(1.9).vibrationMax(3.0)
                .connectivity(ConnectivityStatus.DEGRADED).health(HealthState.GOOD).healthScore(84).uptime(97.8).operatingHours(3200)
                .lastMaintenance("2026-10-05").nextScheduledMaintenance("2026-12-05")
                .operatorId("EMP-5512-SANCHEZ").proprietaryCode("CORRIDOR-ROUTE-MAPPING-SECRET").internalIp("192.168.10.49")
                .build();

        MachineEntity m6 = MachineEntity.builder()
                .id("M06").name("Continuous Polymer Curing Oven").line("Line D - Surface Treatment").model("ThermalDynamics TC-400")
                .temperature(184.2).tempMax(175.0).powerUsage(74.0).powerMax(65.0).vibration(3.2).vibrationMax(3.0)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.POOR).healthScore(48).uptime(91.5).operatingHours(7800)
                .lastMaintenance("2026-08-11").nextScheduledMaintenance("2026-11-22")
                .operatorId("EMP-2210-RODRIGUEZ").proprietaryCode("EPOXY-POLYMER-CURING-FORMULA-9").internalIp("192.168.10.50")
                .build();

        MachineEntity m7 = MachineEntity.builder()
                .id("M07").name("SMT High-Speed Pick & Place").line("Line E - Electronics Sub-assembly").model("Yamaha YSM20R Modular")
                .temperature(46.1).tempMax(55.0).powerUsage(18.6).powerMax(24.0).vibration(1.1).vibrationMax(2.0)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.GOOD).healthScore(91).uptime(98.7).operatingHours(1940)
                .lastMaintenance("2026-11-10").nextScheduledMaintenance("2027-01-20")
                .operatorId("EMP-7711-TANAKA").proprietaryCode("PCB-ECU-REV7-PLACEMENT-MATRIX").internalIp("192.168.10.51")
                .build();

        MachineEntity m8 = MachineEntity.builder()
                .id("M08").name("Heavy Rotary Lathe 07").line("Line B - Precision Machining").model("Mazak Quick Turn 350")
                .temperature(71.5).tempMax(72.0).powerUsage(36.2).powerMax(35.0).vibration(4.1).vibrationMax(3.8)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.FAIR).healthScore(71).uptime(95.3).operatingHours(5200)
                .lastMaintenance("2026-10-01").nextScheduledMaintenance("2026-12-01")
                .operatorId("EMP-4490-WAGNER").proprietaryCode("CAMSHAFT-TURN-GCODE-PROP").internalIp("192.168.10.52")
                .build();

        MachineEntity m9 = MachineEntity.builder()
                .id("M09").name("Industrial Rotary Air Compressor").line("Central Utilities Facility").model("Atlas Copco GA 160 VSD+")
                .temperature(63.8).tempMax(70.0).powerUsage(88.0).powerMax(95.0).vibration(2.6).vibrationMax(3.5)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.GOOD).healthScore(89).uptime(99.8).operatingHours(11400)
                .lastMaintenance("2026-09-15").nextScheduledMaintenance("2026-12-15")
                .operatorId("EMP-0012-GOMEZ").proprietaryCode("PLANT-PRESSURE-LOOP-CTRL-CONF").internalIp("192.168.10.53")
                .build();

        MachineEntity m10 = MachineEntity.builder()
                .id("M10").name("Ultrasonic Parts Washing Station").line("Line C - Finishing Cell").model("Branson OMNI-CLEAN Pro")
                .temperature(58.0).tempMax(60.0).powerUsage(16.4).powerMax(20.0).vibration(1.2).vibrationMax(2.2)
                .connectivity(ConnectivityStatus.ONLINE).health(HealthState.EXCELLENT).healthScore(97).uptime(99.6).operatingHours(980)
                .lastMaintenance("2026-11-12").nextScheduledMaintenance("2027-02-12")
                .operatorId("EMP-6029-LECLERC").proprietaryCode("DEGREASING-SOLVENT-MIX-RATIO").internalIp("192.168.10.54")
                .build();

        List<MachineEntity> machines = machineRepository.saveAll(Arrays.asList(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10));

        // 2. Seed Telemetry History for M01, M02, M03, M06
        telemetryRecordRepository.saveAll(Arrays.asList(
                TelemetryRecordEntity.builder().machine(m1).timeLabel("14:00").temperature(72.0).power(34.0).vibration(3.1).build(),
                TelemetryRecordEntity.builder().machine(m1).timeLabel("15:00").temperature(75.0).power(36.0).vibration(3.4).build(),
                TelemetryRecordEntity.builder().machine(m1).timeLabel("16:00").temperature(81.0).power(39.0).vibration(4.2).build(),
                TelemetryRecordEntity.builder().machine(m1).timeLabel("17:00").temperature(88.0).power(44.0).vibration(5.9).build(),
                TelemetryRecordEntity.builder().machine(m1).timeLabel("18:00").temperature(93.0).power(47.0).vibration(7.1).build(),
                TelemetryRecordEntity.builder().machine(m1).timeLabel("18:50").temperature(95.4).power(48.2).vibration(7.8).build(),

                TelemetryRecordEntity.builder().machine(m2).timeLabel("14:00").temperature(65.0).power(29.0).vibration(2.1).build(),
                TelemetryRecordEntity.builder().machine(m2).timeLabel("15:00").temperature(66.0).power(30.0).vibration(2.2).build(),
                TelemetryRecordEntity.builder().machine(m2).timeLabel("16:00").temperature(67.0).power(31.0).vibration(2.1).build(),
                TelemetryRecordEntity.builder().machine(m2).timeLabel("17:00").temperature(68.0).power(31.0).vibration(2.3).build(),
                TelemetryRecordEntity.builder().machine(m2).timeLabel("18:00").temperature(68.0).power(32.0).vibration(2.2).build(),
                TelemetryRecordEntity.builder().machine(m2).timeLabel("18:50").temperature(68.2).power(31.4).vibration(2.3).build(),

                TelemetryRecordEntity.builder().machine(m3).timeLabel("14:00").temperature(76.0).power(98.0).vibration(4.2).build(),
                TelemetryRecordEntity.builder().machine(m3).timeLabel("15:00").temperature(78.0).power(101.0).vibration(4.5).build(),
                TelemetryRecordEntity.builder().machine(m3).timeLabel("16:00").temperature(80.0).power(104.0).vibration(4.9).build(),
                TelemetryRecordEntity.builder().machine(m3).timeLabel("17:00").temperature(82.0).power(108.0).vibration(5.1).build(),
                TelemetryRecordEntity.builder().machine(m3).timeLabel("18:00").temperature(83.0).power(110.0).vibration(5.3).build(),
                TelemetryRecordEntity.builder().machine(m3).timeLabel("18:50").temperature(84.1).power(112.5).vibration(5.4).build(),

                TelemetryRecordEntity.builder().machine(m6).timeLabel("14:00").temperature(165.0).power(62.0).vibration(2.4).build(),
                TelemetryRecordEntity.builder().machine(m6).timeLabel("15:00").temperature(170.0).power(65.0).vibration(2.6).build(),
                TelemetryRecordEntity.builder().machine(m6).timeLabel("16:00").temperature(174.0).power(68.0).vibration(2.9).build(),
                TelemetryRecordEntity.builder().machine(m6).timeLabel("17:00").temperature(179.0).power(71.0).vibration(3.0).build(),
                TelemetryRecordEntity.builder().machine(m6).timeLabel("18:00").temperature(182.0).power(73.0).vibration(3.1).build(),
                TelemetryRecordEntity.builder().machine(m6).timeLabel("18:50").temperature(184.2).power(74.0).vibration(3.2).build()
        ));

        // 3. Seed Alerts
        alertRepository.saveAll(Arrays.asList(
                AlertEntity.builder().id("ALT-1092").title("Impending Motor Bearing Seizure Risk")
                        .description("High harmonic vibration spike and secondary winding thermal runaway detected on Primary Arm Axis 3.")
                        .severity(AlertSeverity.CRITICAL).status(AlertStatus.ACTIVE).machineId("M01").machineName("Welding Robot A")
                        .line("Line A - Structural Framing").timestamp("18:42:15 (16m ago)")
                        .triggerSignal("Vibration RMS & Bearing Temp").triggerValue("7.8 mm/s @ 95.4°C").thresholdValue("4.5 mm/s @ 85.0°C")
                        .recommendedAction("Inspect cooling duct, check lubrication level, prepare backup robotic cell substitution.").build(),

                AlertEntity.builder().id("ALT-1093").title("Heating Element Thermal Override")
                        .description("Curing temperature has exceeded thermal tolerance window by +9.2°C for 22 consecutive minutes.")
                        .severity(AlertSeverity.CRITICAL).status(AlertStatus.ACTIVE).machineId("M06").machineName("Continuous Polymer Curing Oven")
                        .line("Line D - Surface Treatment").timestamp("18:31:02 (27m ago)")
                        .triggerSignal("Chamber Pyrometer 2").triggerValue("184.2°C").thresholdValue("175.0°C")
                        .recommendedAction("Throttle thyristor power output, verify secondary ventilation intake damper status.").build(),

                AlertEntity.builder().id("ALT-1088").title("Hydraulic Stamping Pressure Surge")
                        .description("Pressure spike detected at valve block B during downward stroke cycle.")
                        .severity(AlertSeverity.HIGH).status(AlertStatus.ACTIVE).machineId("M03").machineName("Hydraulic Stamping Press 3")
                        .line("Line C - Heavy Stamping").timestamp("17:55:40 (1h ago)")
                        .triggerSignal("Manifold Pressure & Power Draw").triggerValue("112.5 kW / 340 bar").thresholdValue("105.0 kW / 310 bar")
                        .recommendedAction("Check proportional relief valve calibration and fluid particulate contamination sensor.").build(),

                AlertEntity.builder().id("ALT-1085").title("Spindle Harmonic Resonance Warning")
                        .description("Radial runout vibration exceeding operational tolerance band at 12,000 RPM.")
                        .severity(AlertSeverity.HIGH).status(AlertStatus.ACTIVE).machineId("M08").machineName("Heavy Rotary Lathe 07")
                        .line("Line B - Precision Machining").timestamp("17:10:18 (1.5h ago)")
                        .triggerSignal("Spindle Radial Vibration").triggerValue("4.1 mm/s").thresholdValue("3.8 mm/s")
                        .recommendedAction("Verify tool clamping force and inspect collet taper for metal burrs or misalignment.").build(),

                AlertEntity.builder().id("ALT-1074").title("AGV Mesh Packet Latency Jitter")
                        .description("WiFi telemetry ping packet drop rate elevated to 3.8% in Logistics Corridor 2.")
                        .severity(AlertSeverity.MEDIUM).status(AlertStatus.ACTIVE).machineId("M05").machineName("Automated Guided Vehicle Fleet Hub")
                        .line("Logistics Corridor 2").timestamp("16:22:11 (2.5h ago)")
                        .triggerSignal("Network Connectivity").triggerValue("DEGRADED (180ms jitter)").thresholdValue("NORMAL (< 40ms)")
                        .recommendedAction("Reboot local edge antenna access point WAP-04 and inspect RF interference.").build(),

                AlertEntity.builder().id("ALT-1062").title("Air Compressor Intercooler Temp Drift")
                        .description("Stage 2 discharge temperature trending higher than historical rolling average by +3.4°C.")
                        .severity(AlertSeverity.LOW).status(AlertStatus.ACKNOWLEDGED).machineId("M09").machineName("Industrial Rotary Air Compressor")
                        .line("Central Utilities Facility").timestamp("14:45:00 (4h ago)")
                        .triggerSignal("Intercooler Delta T").triggerValue("63.8°C").thresholdValue("65.0°C warn")
                        .recommendedAction("Clean intake air filters during upcoming weekend shift changeover.").acknowledgedBy("Lead Tech M. Davis").build(),

                AlertEntity.builder().id("ALT-1051").title("Coolant Flow Rate Micro-fluctuation")
                        .description("Periodic coolant line flow decrease during rapid high-feed machining cycles.")
                        .severity(AlertSeverity.LOW).status(AlertStatus.RESOLVED).machineId("M02").machineName("5-Axis CNC Milling Center")
                        .line("Line B - Precision Machining").timestamp("11:15:22 (7h ago)")
                        .triggerSignal("Coolant Line Sensor").triggerValue("4.2 L/min").thresholdValue("4.0 L/min min")
                        .recommendedAction("Filter mesh replaced, backpressure normalized.").resolvedAt("12:05:10").build()
        ));

        // 4. Seed AIOps Recommendations
        AIOpsRecommendationEntity r1 = AIOpsRecommendationEntity.builder()
                .id("REC-001").title("Potential Motor Bearing Catastrophic Failure").machineId("M01").machineName("Welding Robot A")
                .line("Line A - Structural Framing").severity(AlertSeverity.CRITICAL).confidenceScore(92)
                .rootCause("Degradation of inner bearing cage ball race causing mechanical micro-galling and friction-induced thermal runaway.")
                .recommendedAction("Inspect cooling duct immediately, lubricate outer bearing race, and dispatch maintenance team to execute predictive bearing replacement before next cycle.")
                .estimatedTimeToFailure("14 to 18 Operating Hours").financialImpactPrevented("$42,500 in unplanned line stoppage & scrapped chassis")
                .downtimePreventedHours(5.5).urgency(UrgencyLevel.IMMEDIATE)
                .modelAlgorithm("Ensemble Isolation Forest + LSTM Spectral Telemetry Predictor").dispatched(false).build();

        r1.getContributingSignals().addAll(Arrays.asList(
                ContributingSignalEntity.builder().recommendation(r1).name("High Joint Temperature").value("95.4°C").deviation("+23.4°C over baseline").severity("critical").build(),
                ContributingSignalEntity.builder().recommendation(r1).name("High Harmonic Vibration").value("7.8 mm/s").deviation("+73% above safe threshold").severity("critical").build(),
                ContributingSignalEntity.builder().recommendation(r1).name("Increased Power Consumption").value("48.2 kW").deviation("+15.2% parasitic friction drag").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r1).name("Acoustic Kurtosis Signature").value("5.2 kHz spike").deviation("Characteristic outer-race spall").severity("critical").build()
        ));

        AIOpsRecommendationEntity r2 = AIOpsRecommendationEntity.builder()
                .id("REC-002").title("Thermal Element Degradation & Thermal Runaway").machineId("M06").machineName("Continuous Polymer Curing Oven")
                .line("Line D - Surface Treatment").severity(AlertSeverity.CRITICAL).confidenceScore(95)
                .rootCause("Thyristor solid-state relay SSR-3 latching in high-conduction mode coupled with reduced exhaust damper throughput.")
                .recommendedAction("Switch to auxiliary bypass PID controller, clean ventilation pre-filter, and replace SSR-3 during shift break.")
                .estimatedTimeToFailure("6 to 10 Operating Hours").financialImpactPrevented("$28,000 in ruined composite polymer batches")
                .downtimePreventedHours(4.0).urgency(UrgencyLevel.IMMEDIATE)
                .modelAlgorithm("Multi-Variate Autoencoder + Drift Detector").dispatched(false).build();

        r2.getContributingSignals().addAll(Arrays.asList(
                ContributingSignalEntity.builder().recommendation(r2).name("Zone 2 Pyrometer Drift").value("184.2°C").deviation("+9.2°C above recipe target").severity("critical").build(),
                ContributingSignalEntity.builder().recommendation(r2).name("Heater Current Imbalance").value("74.0 kW").deviation("Phase C carrying 42% excess load").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r2).name("Convection Blower RPM").value("2,940 RPM").deviation("-120 RPM airflow deficit").severity("warning").build()
        ));

        AIOpsRecommendationEntity r3 = AIOpsRecommendationEntity.builder()
                .id("REC-003").title("Hydraulic Proportional Valve Seal Micro-leakage").machineId("M03").machineName("Hydraulic Stamping Press 3")
                .line("Line C - Heavy Stamping").severity(AlertSeverity.HIGH).confidenceScore(88)
                .rootCause("Dynamic elastomer seal wear on 4-way spool valve allowing high-pressure fluid internal bypass back to sump.")
                .recommendedAction("Schedule elastomer seal pack replacement at next maintenance window. Top up fluid level and inspect filter delta-P.")
                .estimatedTimeToFailure("36 to 48 Operating Hours").financialImpactPrevented("$19,200 hydraulic pump rebuild avoidance")
                .downtimePreventedHours(3.0).urgency(UrgencyLevel.NEXT_SHIFT)
                .modelAlgorithm("Hidden Markov Model Pressure Analysis").dispatched(false).build();

        r3.getContributingSignals().addAll(Arrays.asList(
                ContributingSignalEntity.builder().recommendation(r3).name("Pump Power Ramp Duration").value("112.5 kW").deviation("+18% longer dwell cycle").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r3).name("Pressure Decay Curve").value("18 bar/s decay").deviation("2.5x normal internal bypass rate").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r3).name("Hydraulic Reservoir Temp").value("84.1°C").deviation("+4.1°C above optimal thermal band").severity("warning").build()
        ));

        AIOpsRecommendationEntity r4 = AIOpsRecommendationEntity.builder()
                .id("REC-004").title("Lathe Spindle Harmonic Collet Imbalance").machineId("M08").machineName("Heavy Rotary Lathe 07")
                .line("Line B - Precision Machining").severity(AlertSeverity.HIGH).confidenceScore(84)
                .rootCause("Micro-fretting corrosion on collet chuck clamping faces causing sub-millimeter workpiece eccentricity.")
                .recommendedAction("Perform ultrasonic collet bath cleaning, re-torque chuck jaws to 140 Nm, and re-zero touch probe.")
                .estimatedTimeToFailure("72 Operating Hours").financialImpactPrevented("$14,500 in rejected aero-spec turned shafts")
                .downtimePreventedHours(2.0).urgency(UrgencyLevel.NEXT_SHIFT)
                .modelAlgorithm("Wavelet Transform Vibration Classifier").dispatched(false).build();

        r4.getContributingSignals().addAll(Arrays.asList(
                ContributingSignalEntity.builder().recommendation(r4).name("Radial Vibration Amplitude").value("4.1 mm/s").deviation("+8% over ISO 10816 threshold").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r4).name("Taper Runout Micro-strain").value("12.4 μm").deviation("Double the 6 μm precision limit").severity("warning").build(),
                ContributingSignalEntity.builder().recommendation(r4).name("Drive Motor Current Ripple").value("±3.8 A").deviation("Periodic torque oscillation").severity("normal").build()
        ));

        recommendationRepository.saveAll(Arrays.asList(r1, r2, r3, r4));

        // 5. Seed Egress Audit Records
        egressRecordRepository.saveAll(Arrays.asList(
                EgressRecordEntity.builder()
                        .id("EGR-PAYLOAD-99410")
                        .timestamp("2026-11-20T18:58:34.120Z")
                        .machineId("M01")
                        .machineName("Welding Robot A")
                        .status(EgressStatus.FILTERED_AND_SEALED)
                        .sha256Hash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
                        .redactedFieldsCount(5)
                        .redactedKeysJson("[\"operator\", \"productionFormula\", \"internalIP\", \"serialBarcode\", \"customerContractId\"]")
                        .rawPayloadJson("{\"machine\": \"Welding Robot A\", \"operator\": \"EMP-7842-JONES\", \"productionFormula\": \"SECRET-TITAN-ARC-V9-PROPRIETARY\", \"internalIP\": \"192.168.10.45\", \"serialBarcode\": \"CHASSIS-EV-99482-CLASSIFIED\", \"customerContractId\": \"DEFENSE-DEPT-PO-99120\", \"temperature\": 95.4, \"powerUsageKw\": 48.2, \"vibrationRms\": 7.8, \"cycleCount\": 14205, \"rawSensorStreamSamples\": [7.81, 7.85, 7.79, 7.82, 7.88, 7.84, 7.91, 7.80]}")
                        .metadataPayloadJson("{\"machineId\": \"M01\", \"payloadId\": \"FP-EGR-99410\", \"timestamp\": \"2026-11-20T18:58:34.120Z\", \"temperature\": 95.4, \"vibration\": 7.8, \"powerKw\": 48.2, \"health\": \"POOR\", \"severity\": \"CRITICAL\", \"dataSovereigntyVerified\": true, \"sha256Seal\": \"e3b0c442...991b7852b855\", \"privacyStandard\": \"ISO-27001 / IEC-62443 COMPLIANT\"}")
                        .build(),

                EgressRecordEntity.builder()
                        .id("EGR-PAYLOAD-99411")
                        .timestamp("2026-11-20T18:58:22.045Z")
                        .machineId("M02")
                        .machineName("5-Axis CNC Milling Center")
                        .status(EgressStatus.APPROVED)
                        .sha256Hash("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08")
                        .redactedFieldsCount(4)
                        .redactedKeysJson("[\"operator\", \"cadModelChecksum\", \"internalIP\", \"proprietaryGCodeSubroutine\"]")
                        .rawPayloadJson("{\"machine\": \"5-Axis CNC Milling Center\", \"operator\": \"EMP-1190-CHEN\", \"cadModelChecksum\": \"MD5_AERO_HYPERSONIC_ROTOR_BLADE\", \"proprietaryGCodeSubroutine\": \"G01 X142.1 Y99.4 Z-12.8 F450 M08 S12000 (SECRET_PASS)\", \"internalIP\": \"192.168.10.46\", \"macAddress\": \"00:1A:2B:3C:4D:5E\", \"temperature\": 68.2, \"powerUsageKw\": 31.4, \"vibrationRms\": 2.3}")
                        .metadataPayloadJson("{\"machineId\": \"M02\", \"payloadId\": \"FP-EGR-99411\", \"timestamp\": \"2026-11-20T18:58:22.045Z\", \"temperature\": 68.2, \"vibration\": 2.3, \"powerKw\": 31.4, \"health\": \"EXCELLENT\", \"severity\": \"LOW\", \"dataSovereigntyVerified\": true, \"sha256Seal\": \"9f86d081...15b0f00a08\", \"privacyStandard\": \"ISO-27001 / IEC-62443 COMPLIANT\"}")
                        .build(),

                EgressRecordEntity.builder()
                        .id("EGR-PAYLOAD-99412")
                        .timestamp("2026-11-20T18:57:48.810Z")
                        .machineId("M06")
                        .machineName("Continuous Polymer Curing Oven")
                        .status(EgressStatus.FILTERED_AND_SEALED)
                        .sha256Hash("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")
                        .redactedFieldsCount(5)
                        .redactedKeysJson("[\"operator\", \"chemicalMixFormula\", \"internalIP\", \"furnaceCatalystBatch\", \"securityBadgeHash\"]")
                        .rawPayloadJson("{\"machine\": \"Continuous Polymer Curing Oven\", \"operator\": \"EMP-2210-RODRIGUEZ\", \"chemicalMixFormula\": \"RESIN_EPOXY_BOND_CHEM_X49_PATENT_PENDING\", \"furnaceCatalystBatch\": \"CAT-BATCH-990-CONFIDENTIAL\", \"internalIP\": \"192.168.10.50\", \"securityBadgeHash\": \"9882a1b94d8f\", \"temperature\": 184.2, \"powerUsageKw\": 74.0, \"vibrationRms\": 3.2}")
                        .metadataPayloadJson("{\"machineId\": \"M06\", \"payloadId\": \"FP-EGR-99412\", \"timestamp\": \"2026-11-20T18:57:48.810Z\", \"temperature\": 184.2, \"vibration\": 3.2, \"powerKw\": 74.0, \"health\": \"POOR\", \"severity\": \"CRITICAL\", \"dataSovereigntyVerified\": true, \"sha256Seal\": \"5e884898...721d1542d8\", \"privacyStandard\": \"ISO-27001 / IEC-62443 COMPLIANT\"}")
                        .build()
        ));

        // 6. Seed Activity Events
        activityEventRepository.saveAll(Arrays.asList(
                ActivityEventEntity.builder().id("ACT-01").timestamp("18:58:34").type(ActivityEventType.EGRESS_APPROVED)
                        .title("Egress Proof Sealed & Metadata Dispatched")
                        .description("Local Collector stripped 5 confidential fields from M01 telemetry. Transmitted 284 bytes metadata to SaaS.")
                        .machineId("M01").badge("100% Zero Leakage").build(),

                ActivityEventEntity.builder().id("ACT-02").timestamp("18:42:15").type(ActivityEventType.ANOMALY_DETECTED)
                        .title("AIOps Engine Triggered Critical Recommendation")
                        .description("Potential Motor Failure predicted on Welding Robot A with 92% confidence score.")
                        .machineId("M01").badge("AIOps Alert").build(),

                ActivityEventEntity.builder().id("ACT-03").timestamp("18:31:02").type(ActivityEventType.DATA_FILTERED)
                        .title("Proprietary Recipe Scrubbed from Sensor Packet")
                        .description("Chemical recipe \"RESIN_EPOXY_BOND\" blocked from outbound transit on M06.")
                        .machineId("M06").badge("Redacted").build(),

                ActivityEventEntity.builder().id("ACT-04").timestamp("17:55:40").type(ActivityEventType.ANOMALY_DETECTED)
                        .title("High Pressure Spike Logged")
                        .description("Schuler Servo Press 800T pressure curve exceeded standard deviation threshold.")
                        .machineId("M03").badge("High Severity").build(),

                ActivityEventEntity.builder().id("ACT-05").timestamp("17:15:00").type(ActivityEventType.INCIDENT_ACK)
                        .title("Maintenance Team Dispatched")
                        .description("Operator M. Davis acknowledged intercooler inspection on Compressor GA-160.")
                        .machineId("M09").badge("Acknowledged").build(),

                ActivityEventEntity.builder().id("ACT-06").timestamp("16:00:22").type(ActivityEventType.MAINTENANCE_LOG)
                        .title("Routine Edge Health Heartbeat Passed")
                        .description("All 8 Local Collector edge agents operating in isolated hardware enclaves with 0.8ms latency.")
                        .badge("Heartbeat OK").build()
        ));

        // 7. Seed System KPIs
        systemKpiRepository.save(SystemKpiEntity.builder()
                .id(1).totalMachines(248).activeMachines(236).alertsCount(14).criticalIncidents(2)
                .factoryHealthScore(94.2).oeeScore(88.6).egressComplianceRate(100.0)
                .totalDataProcessedMb(4128.5).metadataTransmittedKb(142.3)
                .build());

        log.info("FactoryPulse AI database seeding complete: 10 machines, 7 alerts, 4 recommendations, 3 egress proofs, 6 activity events.");
    }
}
