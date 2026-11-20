import React, { useState } from 'react';
import {
  Sparkles,
  TrendingDown,
  Clock,
  ShieldCheck,
  CheckCircle2,
  Wrench,
  AlertOctagon,
  ArrowRight,
  Cpu,
  DollarSign,
  FileText,
  Activity,
  Layers,
} from 'lucide-react';
import { useApp } from '../context/AppContext';
import { MOCK_RECOMMENDATIONS } from '../data/mockData';
import { AIOpsRecommendation } from '../types';
import { SeverityBadge } from '../components/common/Badge';
import { cn } from '../lib/utils';

export const AIOpsRecommendationPage: React.FC = () => {
  const { setActivePage, setSelectedMachine, machines, addToast } = useApp();

  const [dispatchedIds, setDispatchedIds] = useState<string[]>([]);

  const handleDispatch = (rec: AIOpsRecommendation) => {
    setDispatchedIds((prev) => [...prev, rec.id]);
    addToast({
      type: 'success',
      title: 'Work Order Dispatched to Field Technicians',
      message: `Work Order created for ${rec.machineName} (${rec.machineId}). Action: ${rec.recommendedAction}`,
    });
  };

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>AIOps Predictive Intelligence</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/30">
              Ensemble Machine Learning
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Predictive failure detection trained on anonymized edge telemetry metadata to prevent unplanned factory downtime.
          </p>
        </div>

        {/* Global Summary Badge */}
        <div className="flex items-center gap-3">
          <div className="px-4 py-2 rounded-xl bg-emerald-500/10 border border-emerald-500/20 text-xs font-mono text-emerald-300">
            <span className="font-bold text-white text-sm">$104,200</span> Projected Downtime Saved
          </div>
        </div>
      </div>

      {/* AI Intelligence Stats Bar */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <div className="glass-panel rounded-xl p-4 border-slate-800">
          <div className="flex items-center justify-between text-xs text-slate-400 font-mono">
            <span>ML Models Running</span>
            <Sparkles className="w-4 h-4 text-blue-400" />
          </div>
          <div className="text-2xl font-bold font-mono text-white mt-2">LSTM + Isolation</div>
          <span className="text-[11px] text-emerald-400 font-mono">100Hz Spectral Analysis</span>
        </div>

        <div className="glass-panel rounded-xl p-4 border-slate-800">
          <div className="flex items-center justify-between text-xs text-slate-400 font-mono">
            <span>Average Confidence</span>
            <Activity className="w-4 h-4 text-emerald-400" />
          </div>
          <div className="text-2xl font-bold font-mono text-white mt-2">89.8%</div>
          <span className="text-[11px] text-slate-400 font-mono">Calibrated Bayesian Score</span>
        </div>

        <div className="glass-panel rounded-xl p-4 border-slate-800">
          <div className="flex items-center justify-between text-xs text-slate-400 font-mono">
            <span>Lead Time to Failure</span>
            <Clock className="w-4 h-4 text-amber-400" />
          </div>
          <div className="text-2xl font-bold font-mono text-white mt-2">14 - 72 Hours</div>
          <span className="text-[11px] text-emerald-400 font-mono">Zero Catastrophic Breaks</span>
        </div>

        <div className="glass-panel rounded-xl p-4 border-slate-800">
          <div className="flex items-center justify-between text-xs text-slate-400 font-mono">
            <span>Egress Privacy</span>
            <ShieldCheck className="w-4 h-4 text-purple-400" />
          </div>
          <div className="text-2xl font-bold font-mono text-white mt-2">Zero Secrets</div>
          <span className="text-[11px] text-purple-400 font-mono">Trained on Metadata Only</span>
        </div>
      </div>

      {/* Recommendations Cards Grid */}
      <div className="space-y-6">
        {MOCK_RECOMMENDATIONS.map((rec) => {
          const isDispatched = dispatchedIds.includes(rec.id);
          const machineObj = machines.find((m) => m.id === rec.machineId);

          return (
            <div
              key={rec.id}
              className={cn(
                'glass-panel rounded-2xl p-6 border-slate-800 relative overflow-hidden transition-all duration-300',
                rec.severity === 'CRITICAL' && 'border-red-500/30 shadow-[0_0_25px_rgba(239,68,68,0.08)]'
              )}
            >
              {/* Header */}
              <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 pb-4 border-b border-slate-800/80">
                <div>
                  <div className="flex items-center gap-2.5 flex-wrap">
                    <span className="font-mono text-xs font-bold text-blue-400 bg-blue-500/10 px-2 py-0.5 rounded border border-blue-500/20">
                      {rec.id}
                    </span>
                    <SeverityBadge severity={rec.severity} size="md" />
                    <span
                      className={cn(
                        'text-xs font-mono px-2.5 py-0.5 rounded-full font-bold uppercase tracking-wider',
                        rec.urgency === 'IMMEDIATE'
                          ? 'bg-red-500/20 text-red-300 border border-red-500/30'
                          : 'bg-amber-500/20 text-amber-300 border border-amber-500/30'
                      )}
                    >
                      Urgency: {rec.urgency.replace('_', ' ')}
                    </span>
                  </div>

                  <h3 className="text-xl font-bold text-white mt-2 tracking-tight">
                    {rec.title}
                  </h3>

                  <p className="text-xs text-slate-400 font-mono mt-0.5">
                    Target Machine: <strong className="text-slate-200">{rec.machineName}</strong> ({rec.machineId}) • {rec.line}
                  </p>
                </div>

                {/* Confidence Score Pill */}
                <div className="flex items-center gap-4 bg-slate-900/90 border border-slate-800 rounded-xl p-3.5 self-start lg:self-center">
                  <div className="relative w-14 h-14 flex items-center justify-center">
                    <svg className="w-14 h-14 -rotate-90" viewBox="0 0 36 36">
                      <path
                        className="text-slate-800"
                        strokeWidth="3.5"
                        stroke="currentColor"
                        fill="none"
                        d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                      />
                      <path
                        className={cn(
                          rec.confidenceScore >= 90
                            ? 'text-red-500'
                            : rec.confidenceScore >= 80
                            ? 'text-amber-500'
                            : 'text-blue-500'
                        )}
                        strokeDasharray={`${rec.confidenceScore}, 100`}
                        strokeWidth="3.5"
                        strokeLinecap="round"
                        stroke="currentColor"
                        fill="none"
                        d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                      />
                    </svg>
                    <span className="absolute font-mono font-extrabold text-sm text-white">
                      {rec.confidenceScore}%
                    </span>
                  </div>

                  <div className="text-left font-mono">
                    <span className="text-[10px] text-slate-400 uppercase block font-semibold">
                      Model Confidence
                    </span>
                    <span className="text-xs font-bold text-emerald-400">
                      High Statistical Power
                    </span>
                    <span className="text-[10px] text-slate-500 block">
                      Lead Time: ~{rec.estimatedTimeToFailure}
                    </span>
                  </div>
                </div>
              </div>

              {/* Contributing Signals (As requested in user prompt) */}
              <div className="mt-5 space-y-3">
                <div className="flex items-center justify-between">
                  <h4 className="text-xs font-semibold uppercase tracking-wider text-slate-300 font-mono flex items-center gap-2">
                    <Activity className="w-4 h-4 text-blue-400" />
                    Contributing Telemetry Signals
                  </h4>
                  <span className="text-[11px] font-mono text-slate-500">
                    Algorithm: {rec.modelAlgorithm}
                  </span>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3">
                  {rec.contributingSignals.map((signal, idx) => (
                    <div
                      key={idx}
                      className={cn(
                        'p-3 rounded-xl border text-xs font-mono space-y-1',
                        signal.severity === 'critical'
                          ? 'bg-red-950/20 border-red-500/30'
                          : signal.severity === 'warning'
                          ? 'bg-amber-950/20 border-amber-500/30'
                          : 'bg-slate-900 border-slate-800'
                      )}
                    >
                      <span className="text-[10px] text-slate-400 block truncate">
                        {signal.name}
                      </span>
                      <div className="text-base font-bold text-white">{signal.value}</div>
                      <span
                        className={cn(
                          'text-[10px] block font-medium',
                          signal.severity === 'critical'
                            ? 'text-red-400'
                            : signal.severity === 'warning'
                            ? 'text-amber-400'
                            : 'text-slate-400'
                        )}
                      >
                        {signal.deviation}
                      </span>
                    </div>
                  ))}
                </div>
              </div>

              {/* Root Cause & Recommended Action */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-5">
                <div className="p-4 rounded-xl bg-slate-900/60 border border-slate-800 space-y-1.5">
                  <span className="text-[11px] font-mono uppercase text-slate-400 font-semibold flex items-center gap-1.5">
                    <AlertOctagon className="w-4 h-4 text-amber-400" />
                    AI Root Cause Hypothesis
                  </span>
                  <p className="text-xs text-slate-200 leading-relaxed font-sans">
                    {rec.rootCause}
                  </p>
                </div>

                <div className="p-4 rounded-xl bg-blue-950/20 border border-blue-500/30 space-y-1.5">
                  <span className="text-[11px] font-mono uppercase text-blue-400 font-semibold flex items-center gap-1.5">
                    <Wrench className="w-4 h-4 text-blue-400" />
                    Recommended Maintenance Action
                  </span>
                  <p className="text-xs text-blue-100 leading-relaxed font-sans font-medium">
                    {rec.recommendedAction}
                  </p>
                </div>
              </div>

              {/* Bottom Impact & Action Buttons */}
              <div className="mt-5 pt-4 border-t border-slate-800/80 flex flex-col sm:flex-row sm:items-center justify-between gap-3 text-xs">
                <div className="flex items-center gap-4 text-slate-400 font-mono">
                  <span className="text-emerald-400 font-semibold flex items-center gap-1">
                    <DollarSign className="w-4 h-4" />
                    {rec.financialImpactPrevented}
                  </span>
                  <span>•</span>
                  <span>Prevents ~{rec.downtimePreventedHours}h downtime</span>
                </div>

                <div className="flex items-center gap-2.5">
                  {machineObj && (
                    <button
                      onClick={() => {
                        setSelectedMachine(machineObj);
                        setActivePage('machines');
                      }}
                      className="px-3 py-1.5 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-medium border border-slate-700 transition-colors"
                    >
                      Machine Details
                    </button>
                  )}

                  <button
                    onClick={() => setActivePage('egress')}
                    className="px-3 py-1.5 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-medium border border-slate-700 transition-colors"
                  >
                    View Egress Diff
                  </button>

                  <button
                    onClick={() => handleDispatch(rec)}
                    disabled={isDispatched}
                    className={cn(
                      'flex items-center gap-1.5 px-4 py-2 rounded-lg text-xs font-semibold transition-all',
                      isDispatched
                        ? 'bg-emerald-600/20 text-emerald-300 border border-emerald-500/30'
                        : 'bg-blue-600 hover:bg-blue-500 text-white shadow-lg shadow-blue-600/25'
                    )}
                  >
                    {isDispatched ? (
                      <>
                        <CheckCircle2 className="w-4 h-4 text-emerald-400" />
                        <span>Work Order Dispatched</span>
                      </>
                    ) : (
                      <>
                        <Wrench className="w-4 h-4" />
                        <span>Dispatch Maintenance</span>
                      </>
                    )}
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
