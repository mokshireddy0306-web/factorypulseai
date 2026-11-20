import React, { useState } from 'react';
import {
  Cpu,
  Server,
  Filter,
  FileCode,
  ShieldCheck,
  Cloud,
  BrainCircuit,
  Lightbulb,
  ArrowRight,
  ArrowDown,
  Lock,
  Play,
  Pause,
  Info,
  CheckCircle2,
  Terminal,
  Activity,
  Zap,
} from 'lucide-react';
import { cn } from '../lib/utils';

interface ArchNode {
  id: string;
  step: number;
  title: string;
  subtitle: string;
  icon: React.ComponentType<{ className?: string }>;
  zone: 'ON_PREMISE' | 'CLOUD';
  protocol: string;
  throughput: string;
  description: string;
  securityFeature: string;
  sampleInput: string;
  sampleOutput: string;
}

const ARCH_NODES: ArchNode[] = [
  {
    id: 'node-1',
    step: 1,
    title: 'Factory Machines & Sensors',
    subtitle: 'Edge Physical Layer',
    icon: Cpu,
    zone: 'ON_PREMISE',
    protocol: 'OPC-UA / Modbus TCP / MQTT',
    throughput: '10,000 samples/sec per cell',
    description: 'CNC mills, welding robotics, hydraulic presses, vibration accelerometers, and pyrometers emitting high-frequency raw machine logs.',
    securityFeature: 'Air-gapped factory VLAN isolation',
    sampleInput: 'Analog 4-20mA sensor signals, PLC registers',
    sampleOutput: 'Raw telemetry frames with operator IDs & formulas',
  },
  {
    id: 'node-2',
    step: 2,
    title: 'Local Collector',
    subtitle: 'On-Premises Edge Appliance',
    icon: Server,
    zone: 'ON_PREMISE',
    protocol: 'Local UNIX Socket / gRPC',
    throughput: '50 MB/s ingestion rate',
    description: 'High-performance edge daemon running within the factory floor hardware enclave. Ingests all machine streams in local memory buffer.',
    securityFeature: 'Isolated Docker / Kubernetes Edge Cluster',
    sampleInput: 'Raw multi-machine TCP/IP telemetry streams',
    sampleOutput: 'In-memory uncompressed frame buffers',
  },
  {
    id: 'node-3',
    step: 3,
    title: 'Data Filtering & Redaction',
    subtitle: 'Zero-Knowledge Privacy Gate',
    icon: Filter,
    zone: 'ON_PREMISE',
    protocol: 'Regex / AST Redaction Pipeline',
    throughput: '< 1.2ms processing latency',
    description: 'Scans telemetry frames against strict industrial privacy schema. Automatically strips operator credentials, proprietary formulas, and IP subnets.',
    securityFeature: 'Hardened cryptographic redaction rules (ISO-27001)',
    sampleInput: '{"machine":"Robot A","operator":"EMP123","formula":"X7"}',
    sampleOutput: '{"machineId":"M01","temp":95,"vibration":7.8}',
  },
  {
    id: 'node-4',
    step: 4,
    title: 'Metadata Generation',
    subtitle: 'Statistical Feature Extractor',
    icon: FileCode,
    zone: 'ON_PREMISE',
    protocol: 'Fast Fourier Transform (FFT) / RMS',
    throughput: '140 KB/s compressed output',
    description: 'Computes spectral density, temperature drift slopes, vibration RMS, and health index scores from raw analog samples.',
    securityFeature: 'Mathematical summarization destroys reversible data',
    sampleInput: '10,000 raw audio/vibration audio samples',
    sampleOutput: 'Peak frequency: 5.2kHz, RMS: 7.8 mm/s, Score: 42',
  },
  {
    id: 'node-5',
    step: 5,
    title: 'Egress Proof Panel',
    subtitle: 'Hardware Enclave SHA-256 Seal',
    icon: ShieldCheck,
    zone: 'ON_PREMISE',
    protocol: 'HMAC-SHA256 / TPM 2.0 Attestation',
    throughput: '100% Zero Leakage Verification',
    description: 'Generates verifiable cryptographic zero-knowledge proof attesting that outbound packet contains strictly approved metadata only.',
    securityFeature: 'Hardware Security Module (HSM) signed proof stamp',
    sampleInput: 'Approved metadata JSON payload',
    sampleOutput: 'Signed metadata + SHA-256 seal: e3b0c442...855',
  },
  {
    id: 'node-6',
    step: 6,
    title: 'Secure SaaS Platform',
    subtitle: 'Cloud Time-Series Lakehouse',
    icon: Cloud,
    zone: 'CLOUD',
    protocol: 'TLS 1.3 / mTLS / REST / Kafka',
    throughput: 'Sub-15ms cloud ingestion',
    description: 'Multi-tenant cloud ingestion gateway receives and indexes encrypted metadata into scalable time-series database.',
    securityFeature: 'End-to-end encrypted in transit & at rest (AES-256)',
    sampleInput: 'Signed JSON metadata packets',
    sampleOutput: 'Normalized time-series hyper-tables',
  },
  {
    id: 'node-7',
    step: 7,
    title: 'AIOps Engine',
    subtitle: 'Distributed Machine Learning',
    icon: BrainCircuit,
    zone: 'CLOUD',
    protocol: 'Isolation Forest / LSTM Recurrent Autoencoder',
    throughput: 'Continuous model scoring',
    description: 'Correlates multi-sensor metadata across machines. Detects subtle micro-deviations indicating impending mechanical failures.',
    securityFeature: 'Models trained exclusively on safe metadata',
    sampleInput: 'Rolling 24h temperature, power, and vibration vectors',
    sampleOutput: 'Anomaly score: 0.94, Confidence: 92%',
  },
  {
    id: 'node-8',
    step: 8,
    title: 'Actionable Recommendations',
    subtitle: 'Closed-Loop Maintenance',
    icon: Lightbulb,
    zone: 'CLOUD',
    protocol: 'Webhook / PagerDuty / SCADA Feedback',
    throughput: 'Real-time alert dispatch',
    description: 'Generates prescriptive maintenance steps, root cause diagnosis, and downtime avoidance estimates directly to plant supervisors.',
    securityFeature: 'Role-based access control (RBAC) with audit trail',
    sampleInput: 'Predictive anomaly trigger',
    sampleOutput: 'Work order: Lubricate Robot A Axis 3 within 18 hrs',
  },
];

export const ArchitectureVisualizationPage: React.FC = () => {
  const [selectedNode, setSelectedNode] = useState<ArchNode>(ARCH_NODES[4]); // Default to Egress Proof Panel
  const [isAnimationPlaying, setIsAnimationPlaying] = useState<boolean>(true);

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>Interactive Architecture & Data Flow</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/30">
              8-Stage Industrial Pipeline
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Visualizing the secure bridge between factory floor OT networks and the cloud AIOps SaaS platform.
          </p>
        </div>

        {/* Animation Control */}
        <div className="flex items-center gap-2">
          <button
            onClick={() => setIsAnimationPlaying(!isAnimationPlaying)}
            className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-900 border border-slate-800 text-xs font-mono text-slate-200 hover:text-white transition-all"
          >
            {isAnimationPlaying ? (
              <>
                <Pause className="w-3.5 h-3.5 text-amber-400" />
                <span>Pause Flow</span>
              </>
            ) : (
              <>
                <Play className="w-3.5 h-3.5 text-emerald-400" />
                <span>Play Flow</span>
              </>
            )}
          </button>
        </div>
      </div>

      {/* Security Boundary Legend Bar */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="p-4 rounded-xl bg-slate-900/90 border border-blue-500/30 flex items-center gap-3">
          <div className="w-9 h-9 rounded-lg bg-blue-500/20 text-blue-400 flex items-center justify-center font-bold font-mono">
            OT
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="font-bold text-sm text-white">Factory Local Enclave (Stages 1 - 5)</span>
              <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-blue-500/10 text-blue-400 border border-blue-500/20">
                100% PRIVATE
              </span>
            </div>
            <p className="text-xs text-slate-400">
              Raw telemetry, operator PII, and trade secrets strictly confined within the factory perimeter.
            </p>
          </div>
        </div>

        <div className="p-4 rounded-xl bg-slate-900/90 border border-emerald-500/30 flex items-center gap-3">
          <div className="w-9 h-9 rounded-lg bg-emerald-500/20 text-emerald-400 flex items-center justify-center font-bold font-mono">
            IT
          </div>
          <div>
            <div className="flex items-center gap-2">
              <span className="font-bold text-sm text-white">Secure Cloud SaaS (Stages 6 - 8)</span>
              <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/20">
                METADATA ONLY
              </span>
            </div>
            <p className="text-xs text-slate-400">
              Encrypted time-series metadata lakehouse powering predictive AIOps models.
            </p>
          </div>
        </div>
      </div>

      {/* Interactive Visual Pipeline Flow */}
      <div className="glass-panel rounded-2xl p-6 border-slate-800 space-y-6">
        <div className="flex items-center justify-between">
          <span className="text-xs font-mono font-bold uppercase text-slate-400 tracking-wider">
            Interactive Architecture Flowchart (Click any stage to inspect)
          </span>
          <span className="text-[11px] font-mono text-emerald-400 flex items-center gap-1.5">
            <span className={cn('w-2 h-2 rounded-full bg-emerald-400', isAnimationPlaying && 'animate-ping')} />
            {isAnimationPlaying ? 'Data Packets Streaming' : 'Simulation Paused'}
          </span>
        </div>

        {/* 8 Pipeline Nodes */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {ARCH_NODES.map((node, idx) => {
            const Icon = node.icon;
            const isSelected = selectedNode.id === node.id;
            const isOnPrem = node.zone === 'ON_PREMISE';

            return (
              <div
                key={node.id}
                onClick={() => setSelectedNode(node)}
                className={cn(
                  'rounded-xl p-4 border transition-all duration-200 cursor-pointer relative group flex flex-col justify-between min-h-[145px]',
                  isSelected
                    ? isOnPrem
                      ? 'bg-blue-950/40 border-blue-500 shadow-[0_0_20px_rgba(59,130,246,0.25)]'
                      : 'bg-emerald-950/40 border-emerald-500 shadow-[0_0_20px_rgba(34,197,94,0.25)]'
                    : 'bg-slate-900/70 border-slate-800 hover:border-slate-700 hover:bg-slate-800/60'
                )}
              >
                {/* Node Step & Protocol Tag */}
                <div className="flex items-center justify-between">
                  <span
                    className={cn(
                      'text-xs font-mono font-bold px-2 py-0.5 rounded-full',
                      isOnPrem
                        ? 'bg-blue-500/20 text-blue-300'
                        : 'bg-emerald-500/20 text-emerald-300'
                    )}
                  >
                    Stage {node.step}
                  </span>

                  <span className="text-[10px] font-mono text-slate-500 truncate max-w-[100px]">
                    {node.zone === 'ON_PREMISE' ? 'Local Edge' : 'Cloud SaaS'}
                  </span>
                </div>

                {/* Node Title & Icon */}
                <div className="my-2 flex items-center gap-3">
                  <div
                    className={cn(
                      'p-2 rounded-lg shrink-0',
                      isOnPrem
                        ? 'bg-blue-500/10 text-blue-400 border border-blue-500/20'
                        : 'bg-emerald-500/10 text-emerald-400 border border-emerald-500/20'
                    )}
                  >
                    <Icon className="w-5 h-5" />
                  </div>
                  <div>
                    <h4 className="text-xs font-bold text-white group-hover:text-blue-300 transition-colors">
                      {node.title}
                    </h4>
                    <span className="text-[10px] text-slate-400 font-mono block">
                      {node.subtitle}
                    </span>
                  </div>
                </div>

                {/* Bottom Protocol Pill */}
                <div className="pt-2 border-t border-slate-800/80 flex items-center justify-between text-[10px] font-mono text-slate-400">
                  <span className="truncate">{node.protocol}</span>
                  <ArrowRight
                    className={cn(
                      'w-3.5 h-3.5 transition-transform',
                      isSelected ? 'translate-x-1 text-white' : 'text-slate-600'
                    )}
                  />
                </div>

                {/* Pulse animation dot if playing */}
                {isAnimationPlaying && (
                  <span
                    className={cn(
                      'absolute -top-1 -right-1 w-2.5 h-2.5 rounded-full',
                      isOnPrem ? 'bg-blue-400 animate-pulse' : 'bg-emerald-400 animate-pulse'
                    )}
                  />
                )}
              </div>
            );
          })}
        </div>
      </div>

      {/* Selected Node Technical Deep Dive Inspector */}
      {selectedNode && (
        <div className="glass-panel rounded-2xl p-6 border-slate-800 space-y-6">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 pb-4 border-b border-slate-800">
            <div className="flex items-center gap-3">
              <div
                className={cn(
                  'p-3 rounded-xl border',
                  selectedNode.zone === 'ON_PREMISE'
                    ? 'bg-blue-500/10 border-blue-500/30 text-blue-400'
                    : 'bg-emerald-500/10 border-emerald-500/30 text-emerald-400'
                )}
              >
                <selectedNode.icon className="w-6 h-6" />
              </div>

              <div>
                <div className="flex items-center gap-2">
                  <span className="text-lg font-bold text-white tracking-tight">
                    Stage {selectedNode.step}: {selectedNode.title}
                  </span>
                  <span
                    className={cn(
                      'text-xs font-mono font-bold px-2 py-0.5 rounded uppercase',
                      selectedNode.zone === 'ON_PREMISE'
                        ? 'bg-blue-500/20 text-blue-300 border border-blue-500/30'
                        : 'bg-emerald-500/20 text-emerald-300 border border-emerald-500/30'
                    )}
                  >
                    {selectedNode.zone === 'ON_PREMISE' ? 'Factory On-Premises' : 'Cloud SaaS'}
                  </span>
                </div>
                <p className="text-xs text-slate-400 font-mono mt-0.5">
                  Protocol: <strong className="text-white">{selectedNode.protocol}</strong> • Throughput: <strong className="text-white">{selectedNode.throughput}</strong>
                </p>
              </div>
            </div>

            <span className="px-3 py-1.5 rounded-lg bg-slate-900 border border-slate-800 text-xs font-mono text-emerald-400">
              Security: {selectedNode.securityFeature}
            </span>
          </div>

          <p className="text-sm text-slate-300 leading-relaxed font-sans">
            {selectedNode.description}
          </p>

          {/* Sample Input / Output Comparison */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-xs font-mono">
            <div className="p-4 rounded-xl bg-slate-950/80 border border-slate-800 space-y-2">
              <span className="text-[11px] text-slate-500 uppercase font-bold block">
                Incoming Stage Data
              </span>
              <div className="p-2.5 rounded bg-slate-900 text-slate-300 border border-slate-800 break-all font-mono">
                {selectedNode.sampleInput}
              </div>
            </div>

            <div className="p-4 rounded-xl bg-slate-950/80 border border-slate-800 space-y-2">
              <span className="text-[11px] text-emerald-400 uppercase font-bold block">
                Transformed Outgoing Data
              </span>
              <div className="p-2.5 rounded bg-slate-900 text-emerald-300 border border-slate-800 break-all font-mono">
                {selectedNode.sampleOutput}
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
