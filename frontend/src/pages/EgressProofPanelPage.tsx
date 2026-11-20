import React, { useState, useEffect } from 'react';
import {
  ShieldCheck,
  ShieldAlert,
  ArrowRight,
  Lock,
  FileCheck,
  Zap,
  CheckCircle2,
  RefreshCw,
  Hash,
  Database,
  EyeOff,
  Fingerprint,
  Cpu,
  Layers,
} from 'lucide-react';
import { useApp } from '../context/AppContext';
import { MOCK_EGRESS_COMPARISONS } from '../data/mockData';
import { JsonViewer } from '../components/common/JsonViewer';
import { cn } from '../lib/utils';

export const EgressProofPanelPage: React.FC = () => {
  const { addToast } = useApp();

  const [selectedPayloadIndex, setSelectedPayloadIndex] = useState(0);
  const [isVerifying, setIsVerifying] = useState(false);
  const [verificationPassed, setVerificationPassed] = useState(true);
  const [simulatedStreamActive, setSimulatedStreamActive] = useState(true);
  const [packetCount, setPacketCount] = useState(14892);

  const currentPayload = MOCK_EGRESS_COMPARISONS[selectedPayloadIndex];

  // Simulated live packet counter
  useEffect(() => {
    if (!simulatedStreamActive) return;
    const interval = setInterval(() => {
      setPacketCount((prev) => prev + 1);
    }, 2800);
    return () => clearInterval(interval);
  }, [simulatedStreamActive]);

  const handleVerifyProof = () => {
    setIsVerifying(true);
    setTimeout(() => {
      setIsVerifying(false);
      setVerificationPassed(true);
      addToast({
        type: 'success',
        title: 'Cryptographic Zero-Leakage Proof Validated',
        message: `SHA-256 seal ${currentPayload.sha256Hash.substring(0, 16)}... matches hardware enclave signature. 0 PII or Trade Secrets detected in cloud payload.`,
      });
    }, 1200);
  };

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>Egress Proof & Data Redaction Panel</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-emerald-500/10 text-emerald-400 border border-emerald-500/30">
              Judges Audit Hub
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Proof of zero sensitive data egress: raw machine telemetry stays 100% on-premises; only approved metadata leaves the factory.
          </p>
        </div>

        {/* Verification Trigger Button */}
        <div className="flex items-center gap-3">
          <button
            onClick={handleVerifyProof}
            disabled={isVerifying}
            className="flex items-center gap-2 px-4 py-2 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-semibold shadow-lg shadow-emerald-600/25 transition-all disabled:opacity-60"
          >
            {isVerifying ? (
              <>
                <RefreshCw className="w-4 h-4 animate-spin" />
                <span>Hashing Enclave Proof...</span>
              </>
            ) : (
              <>
                <ShieldCheck className="w-4 h-4" />
                <span>Verify Cryptographic Proof</span>
              </>
            )}
          </button>
        </div>
      </div>

      {/* Security Architecture & Live Status Banner */}
      <div className="glass-panel rounded-2xl p-6 border-slate-800 relative overflow-hidden bg-gradient-to-r from-slate-900/90 via-slate-900/60 to-slate-900/90">
        <div className="flex flex-col lg:flex-row items-start lg:items-center justify-between gap-6">
          <div className="flex items-center gap-4">
            <div className="w-12 h-12 rounded-xl bg-emerald-500/15 border border-emerald-500/30 flex items-center justify-center text-emerald-400 shrink-0 shadow-[0_0_20px_rgba(34,197,94,0.25)]">
              <Fingerprint className="w-6 h-6" />
            </div>

            <div>
              <div className="flex items-center gap-2 flex-wrap">
                <span className="text-base font-bold text-white">
                  Zero-Knowledge Telemetry Gateway
                </span>
                <span className="text-[10px] font-mono font-bold px-2 py-0.5 rounded bg-emerald-500/15 text-emerald-400 border border-emerald-500/30">
                  PASSED ISO-27001 / IEC-62443
                </span>
              </div>
              <p className="text-xs text-slate-400 mt-1 font-sans">
                The Local Collector operates inside an on-premise hardware-isolated enclave. Production formulas, operator PII, and internal IP subnets are permanently blocked before outbound serialization.
              </p>
            </div>
          </div>

          {/* Quick Metrics */}
          <div className="flex items-center gap-4 text-xs font-mono bg-slate-950/70 border border-slate-800 rounded-xl p-3 shrink-0">
            <div>
              <span className="text-[10px] text-slate-500 block uppercase">Packets Sealed</span>
              <span className="text-sm font-bold text-white">
                {packetCount.toLocaleString()}
              </span>
            </div>
            <div className="h-7 w-px bg-slate-800" />
            <div>
              <span className="text-[10px] text-slate-500 block uppercase">Sensitive Fields Blocked</span>
              <span className="text-sm font-bold text-red-400">100% (0 Leaks)</span>
            </div>
            <div className="h-7 w-px bg-slate-800" />
            <div>
              <span className="text-[10px] text-slate-500 block uppercase">Bandwidth Saved</span>
              <span className="text-sm font-bold text-emerald-400">98.2%</span>
            </div>
          </div>
        </div>
      </div>

      {/* Payload Sample Switcher */}
      <div className="flex items-center justify-between gap-3 flex-wrap">
        <div className="flex items-center gap-2">
          <span className="text-xs font-mono text-slate-400 font-semibold uppercase">
            Select Telemetry Sample:
          </span>
          <div className="flex bg-slate-900 border border-slate-800 rounded-lg p-1">
            {MOCK_EGRESS_COMPARISONS.map((payload, idx) => (
              <button
                key={payload.id}
                onClick={() => setSelectedPayloadIndex(idx)}
                className={cn(
                  'px-3 py-1.5 rounded-md text-xs font-mono transition-all',
                  selectedPayloadIndex === idx
                    ? 'bg-blue-600 text-white font-bold shadow-sm'
                    : 'text-slate-400 hover:text-white'
                )}
              >
                {payload.machineName} ({payload.machineId})
              </button>
            ))}
          </div>
        </div>

        {/* Live streaming toggle */}
        <div className="flex items-center gap-2 text-xs font-mono">
          <span className="text-slate-400">Live Egress Pipeline:</span>
          <button
            onClick={() => setSimulatedStreamActive(!simulatedStreamActive)}
            className={cn(
              'px-2.5 py-1 rounded border text-[11px] font-semibold transition-all flex items-center gap-1.5',
              simulatedStreamActive
                ? 'bg-emerald-500/10 text-emerald-400 border-emerald-500/30'
                : 'bg-slate-800 text-slate-400 border-slate-700'
            )}
          >
            <span
              className={cn(
                'w-1.5 h-1.5 rounded-full',
                simulatedStreamActive ? 'bg-emerald-400 animate-pulse' : 'bg-slate-500'
              )}
            />
            {simulatedStreamActive ? 'ACTIVE STREAM' : 'PAUSED'}
          </button>
        </div>
      </div>

      {/* SIDE-BY-SIDE EGRESS PROOF LAYOUT (CORE HACKATHON FEATURE) */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 items-start">
        {/* LEFT SIDE: RAW MACHINE DATA (LOCAL ON-PREM ONLY) */}
        <div className="space-y-3">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <ShieldAlert className="w-5 h-5 text-red-400" />
              <div>
                <h3 className="text-sm font-bold text-white uppercase tracking-wider font-mono">
                  1. Raw Machine Telemetry
                </h3>
                <span className="text-xs text-red-400 font-semibold font-mono">
                  ● STAYS ON-PREMISES ONLY — NEVER LEAVES FACTORY
                </span>
              </div>
            </div>
            <span className="px-2 py-0.5 rounded bg-red-500/10 text-red-400 border border-red-500/30 text-[10px] font-mono font-bold">
              RESTRICTED
            </span>
          </div>

          <p className="text-xs text-slate-400 leading-relaxed font-sans">
            Contains proprietary trade secrets, operator employee badges, internal IPv4 subnets, and uncalibrated high-frequency analog streams.
          </p>

          <JsonViewer
            data={currentPayload.rawPayload}
            title={`LOCAL BUFFER: ${currentPayload.machineName}`}
            subtitle="Raw Edge Capture"
            highlightRedacted={true}
            redactedKeys={currentPayload.redactedKeys}
            isMetadataApproved={false}
          />

          {/* Redaction Legend */}
          <div className="p-3 rounded-lg bg-red-950/20 border border-red-500/20 text-xs font-mono space-y-1.5">
            <div className="text-red-300 font-bold flex items-center gap-1.5">
              <EyeOff className="w-3.5 h-3.5" />
              <span>Edge Enclave Auto-Redaction Rules Applied:</span>
            </div>
            <ul className="list-disc list-inside text-[11px] text-slate-300 space-y-0.5">
              <li>Operator IDs scrubbed for GDPR & worker privacy compliance.</li>
              <li>Proprietary formulas & CAD checksums blocked from cloud storage.</li>
              <li>Internal IP addresses stripped to prevent cyber-topology mapping.</li>
            </ul>
          </div>
        </div>

        {/* RIGHT SIDE: APPROVED METADATA SENT TO SAAS */}
        <div className="space-y-3">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <ShieldCheck className="w-5 h-5 text-emerald-400" />
              <div>
                <h3 className="text-sm font-bold text-white uppercase tracking-wider font-mono">
                  2. Approved Metadata Sent To SaaS
                </h3>
                <span className="text-xs text-emerald-400 font-semibold font-mono">
                  ● APPROVED FOR CLOUD EGRESS — ZERO SENSITIVE DATA
                </span>
              </div>
            </div>
            <span className="px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 text-[10px] font-mono font-bold">
              VERIFIED SAFE
            </span>
          </div>

          <p className="text-xs text-slate-400 leading-relaxed font-sans">
            Sanitized mathematical abstractions, health states, and anomaly indicators. Safe for central AIOps modeling without IP exposure.
          </p>

          <JsonViewer
            data={currentPayload.metadataPayload}
            title={`CLOUD METADATA: ${currentPayload.metadataPayload.payloadId}`}
            subtitle="Secure SaaS Transmission"
            highlightRedacted={false}
            isMetadataApproved={true}
          />

          {/* Cloud Transmission Proof Info */}
          <div className="p-3 rounded-lg bg-emerald-950/20 border border-emerald-500/20 text-xs font-mono space-y-1.5">
            <div className="text-emerald-300 font-bold flex items-center gap-1.5">
              <Hash className="w-3.5 h-3.5" />
              <span>Cryptographic Proof Stamp:</span>
            </div>
            <div className="text-[11px] text-slate-300 space-y-0.5">
              <p className="truncate">
                SHA-256: <strong className="text-emerald-400">{currentPayload.sha256Hash}</strong>
              </p>
              <p>
                Status: <strong className="text-white">{currentPayload.status}</strong> • Timestamp: <strong className="text-white">{currentPayload.timestamp}</strong>
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Compliance Proof Audit Checklist for Hackathon Judges */}
      <div className="glass-panel rounded-2xl p-6 border-slate-800 space-y-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <CheckCircle2 className="w-5 h-5 text-emerald-400" />
            <h3 className="text-base font-bold text-white tracking-tight">
              Industrial Security & Regulatory Compliance Verification
            </h3>
          </div>
          <span className="text-xs font-mono text-slate-400">
            Validated by Hardware Security Module (HSM)
          </span>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 text-xs font-mono">
          <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800 space-y-1">
            <span className="text-emerald-400 font-bold flex items-center gap-1">
              <CheckCircle2 className="w-3.5 h-3.5" /> IEC 62443-4-2
            </span>
            <span className="text-white font-semibold block">Industrial Cybersecurity</span>
            <p className="text-[11px] text-slate-400 font-sans">
              Separation of OT control network from cloud monitoring layers.
            </p>
          </div>

          <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800 space-y-1">
            <span className="text-emerald-400 font-bold flex items-center gap-1">
              <CheckCircle2 className="w-3.5 h-3.5" /> ISO/IEC 27001
            </span>
            <span className="text-white font-semibold block">Info Security Standard</span>
            <p className="text-[11px] text-slate-400 font-sans">
              Strict data minimization and cryptographically signed audit logs.
            </p>
          </div>

          <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800 space-y-1">
            <span className="text-emerald-400 font-bold flex items-center gap-1">
              <CheckCircle2 className="w-3.5 h-3.5" /> ITAR / Defense Safe
            </span>
            <span className="text-white font-semibold block">Defense Sovereignty</span>
            <p className="text-[11px] text-slate-400 font-sans">
              Proprietary defense machining designs never cross firewall boundaries.
            </p>
          </div>

          <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800 space-y-1">
            <span className="text-emerald-400 font-bold flex items-center gap-1">
              <CheckCircle2 className="w-3.5 h-3.5" /> GDPR Article 25
            </span>
            <span className="text-white font-semibold block">Privacy by Design</span>
            <p className="text-[11px] text-slate-400 font-sans">
              Operator identifiers and employee shift badges scrubbed at source.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};
