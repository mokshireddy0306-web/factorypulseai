export type HealthState = 'EXCELLENT' | 'GOOD' | 'FAIR' | 'POOR';
export type ConnectivityStatus = 'ONLINE' | 'DEGRADED' | 'OFFLINE';
export type AlertSeverity = 'CRITICAL' | 'HIGH' | 'MEDIUM' | 'LOW';
export type AlertStatus = 'ACTIVE' | 'ACKNOWLEDGED' | 'RESOLVED';

export interface Machine {
  id: string;
  name: string;
  line: string;
  model: string;
  temperature: number; // in Celsius
  tempMax: number;
  powerUsage: number; // in kW
  powerMax: number;
  vibration: number; // in mm/s RMS
  vibrationMax: number;
  connectivity: ConnectivityStatus;
  health: HealthState;
  healthScore: number; // 0 - 100
  uptime: number; // percentage
  operatingHours: number;
  lastMaintenance: string;
  nextScheduledMaintenance: string;
  operatorId: string;
  proprietaryCode: string;
  internalIp: string;
  telemetryHistory: {
    time: string;
    temperature: number;
    power: number;
    vibration: number;
  }[];
}

export interface Alert {
  id: string;
  title: string;
  description: string;
  severity: AlertSeverity;
  status: AlertStatus;
  machineId: string;
  machineName: string;
  line: string;
  timestamp: string;
  triggerSignal: string;
  triggerValue: string;
  thresholdValue: string;
  recommendedAction: string;
  acknowledgedBy?: string;
  resolvedAt?: string;
}

export interface AIOpsRecommendation {
  id: string;
  title: string;
  machineId: string;
  machineName: string;
  line: string;
  severity: AlertSeverity;
  confidenceScore: number; // percentage (e.g. 92)
  contributingSignals: {
    name: string;
    value: string;
    deviation: string;
    severity: 'critical' | 'warning' | 'normal';
  }[];
  rootCause: string;
  recommendedAction: string;
  estimatedTimeToFailure: string;
  financialImpactPrevented: string;
  downtimePreventedHours: number;
  urgency: 'IMMEDIATE' | 'NEXT_SHIFT' | 'SCHEDULED_MAINTENANCE';
  modelAlgorithm: string;
}

export interface EgressComparison {
  id: string;
  timestamp: string;
  machineId: string;
  machineName: string;
  status: 'FILTERED_AND_SEALED' | 'BLOCKED' | 'APPROVED';
  sha256Hash: string;
  redactedFieldsCount: number;
  rawPayload: Record<string, any>;
  metadataPayload: Record<string, any>;
  redactedKeys: string[];
}

export interface ActivityEvent {
  id: string;
  timestamp: string;
  type: 'EGRESS_APPROVED' | 'ANOMALY_DETECTED' | 'DATA_FILTERED' | 'INCIDENT_ACK' | 'MAINTENANCE_LOG';
  title: string;
  description: string;
  machineId?: string;
  badge?: string;
}

export interface KPIStats {
  totalMachines: number;
  activeMachines: number;
  alertsCount: number;
  criticalIncidents: number;
  factoryHealthScore: number;
  oeeScore: number;
  egressComplianceRate: number;
  totalDataProcessedMb: number;
  metadataTransmittedKb: number;
}
