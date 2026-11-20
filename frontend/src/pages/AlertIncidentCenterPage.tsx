import React, { useState } from 'react';
import {
  AlertTriangle,
  ChevronDown,
  ChevronUp,
  CheckCircle,
  Filter,
  Search,
  Clock,
  ShieldAlert,
  ArrowRight,
  Flame,
  CheckCheck,
} from 'lucide-react';
import { useApp } from '../context/AppContext';
import { Alert, AlertSeverity, AlertStatus } from '../types';
import { SeverityBadge } from '../components/common/Badge';
import { cn } from '../lib/utils';

export const AlertIncidentCenterPage: React.FC = () => {
  const { alerts, acknowledgeAlert, resolveAlert, setActivePage, setSelectedMachine, machines } = useApp();

  const [expandedAlertId, setExpandedAlertId] = useState<string | null>('ALT-1092');
  const [selectedSeverity, setSelectedSeverity] = useState<AlertSeverity | 'ALL'>('ALL');
  const [selectedStatus, setSelectedStatus] = useState<AlertStatus | 'ALL'>('ALL');
  const [searchFilter, setSearchFilter] = useState('');

  const toggleExpand = (id: string) => {
    setExpandedAlertId(expandedAlertId === id ? null : id);
  };

  const filteredAlerts = alerts.filter((alert) => {
    const matchesSeverity = selectedSeverity === 'ALL' || alert.severity === selectedSeverity;
    const matchesStatus = selectedStatus === 'ALL' || alert.status === selectedStatus;
    const matchesSearch =
      alert.title.toLowerCase().includes(searchFilter.toLowerCase()) ||
      alert.machineName.toLowerCase().includes(searchFilter.toLowerCase()) ||
      alert.machineId.toLowerCase().includes(searchFilter.toLowerCase()) ||
      alert.id.toLowerCase().includes(searchFilter.toLowerCase());
    return matchesSeverity && matchesStatus && matchesSearch;
  });

  const criticalCount = alerts.filter((a) => a.severity === 'CRITICAL' && a.status === 'ACTIVE').length;
  const highCount = alerts.filter((a) => a.severity === 'HIGH' && a.status === 'ACTIVE').length;
  const mediumCount = alerts.filter((a) => a.severity === 'MEDIUM' && a.status === 'ACTIVE').length;
  const lowCount = alerts.filter((a) => a.severity === 'LOW' && a.status === 'ACTIVE').length;

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>Alert & Incident Center</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-red-500/10 text-red-400 border border-red-500/30">
              {criticalCount} Critical Active
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Real-time anomaly triggers detected by AIOps engine from edge-aggregated metadata.
          </p>
        </div>

        {/* Severity Quick Counts */}
        <div className="flex items-center gap-2">
          <div className="px-3 py-1.5 rounded-lg bg-red-500/10 border border-red-500/30 text-xs font-mono text-red-400">
            <span className="font-bold">{criticalCount}</span> Critical
          </div>
          <div className="px-3 py-1.5 rounded-lg bg-orange-500/10 border border-orange-500/30 text-xs font-mono text-orange-400">
            <span className="font-bold">{highCount}</span> High
          </div>
          <div className="px-3 py-1.5 rounded-lg bg-amber-500/10 border border-amber-500/30 text-xs font-mono text-amber-400">
            <span className="font-bold">{mediumCount}</span> Medium
          </div>
          <div className="px-3 py-1.5 rounded-lg bg-blue-500/10 border border-blue-500/30 text-xs font-mono text-blue-400">
            <span className="font-bold">{lowCount}</span> Low
          </div>
        </div>
      </div>

      {/* Filter and Search Bar */}
      <div className="glass-panel rounded-xl p-4 border-slate-800 flex flex-col md:flex-row gap-4 items-stretch md:items-center justify-between">
        {/* Search */}
        <div className="relative flex-1 max-w-sm">
          <Search className="w-4 h-4 text-slate-500 absolute left-3 top-1/2 -translate-y-1/2" />
          <input
            type="text"
            value={searchFilter}
            onChange={(e) => setSearchFilter(e.target.value)}
            placeholder="Search alerts by title, machine, or alert ID..."
            className="w-full bg-slate-900/80 border border-slate-800 rounded-lg pl-9 pr-3 py-1.5 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-blue-500 font-mono"
          />
        </div>

        {/* Severity Filter buttons */}
        <div className="flex items-center gap-2 flex-wrap text-xs">
          <div className="flex items-center gap-1 bg-slate-900/80 border border-slate-800 rounded-lg p-1">
            <span className="text-slate-500 px-2 font-mono text-[11px]">Severity:</span>
            {(['ALL', 'CRITICAL', 'HIGH', 'MEDIUM', 'LOW'] as const).map((sev) => (
              <button
                key={sev}
                onClick={() => setSelectedSeverity(sev)}
                className={cn(
                  'px-2 py-1 rounded text-xs font-mono transition-all',
                  selectedSeverity === sev
                    ? 'bg-blue-600 text-white font-bold'
                    : 'text-slate-400 hover:text-white'
                )}
              >
                {sev}
              </button>
            ))}
          </div>

          {/* Status Filter */}
          <div className="flex items-center gap-1 bg-slate-900/80 border border-slate-800 rounded-lg p-1">
            <span className="text-slate-500 px-2 font-mono text-[11px]">Status:</span>
            {(['ALL', 'ACTIVE', 'ACKNOWLEDGED', 'RESOLVED'] as const).map((st) => (
              <button
                key={st}
                onClick={() => setSelectedStatus(st)}
                className={cn(
                  'px-2 py-1 rounded text-xs font-mono transition-all',
                  selectedStatus === st
                    ? 'bg-slate-800 text-white font-bold'
                    : 'text-slate-400 hover:text-white'
                )}
              >
                {st}
              </button>
            ))}
          </div>
        </div>
      </div>

      {/* Alerts List */}
      <div className="space-y-4">
        {filteredAlerts.length === 0 ? (
          <div className="glass-panel rounded-xl p-12 text-center border-slate-800">
            <CheckCircle className="w-10 h-10 text-emerald-400 mx-auto mb-3" />
            <h3 className="text-base font-semibold text-white">No Incidents Found</h3>
            <p className="text-xs text-slate-400 mt-1">
              All machines matching this criteria are within baseline operating parameters.
            </p>
          </div>
        ) : (
          filteredAlerts.map((alert) => {
            const isExpanded = expandedAlertId === alert.id;
            const machineObj = machines.find((m) => m.id === alert.machineId);

            return (
              <div
                key={alert.id}
                className={cn(
                  'glass-panel rounded-xl border transition-all duration-200 overflow-hidden',
                  alert.severity === 'CRITICAL' && alert.status === 'ACTIVE'
                    ? 'border-red-500/40 bg-slate-900/90 shadow-[0_0_20px_rgba(239,68,68,0.12)]'
                    : alert.severity === 'HIGH' && alert.status === 'ACTIVE'
                    ? 'border-orange-500/30 bg-slate-900/80'
                    : 'border-slate-800 bg-slate-900/60'
                )}
              >
                {/* Header Row (Clickable to Expand) */}
                <div
                  onClick={() => toggleExpand(alert.id)}
                  className="p-5 flex flex-col md:flex-row md:items-center justify-between gap-4 cursor-pointer select-none hover:bg-slate-800/30 transition-colors"
                >
                  <div className="flex items-start gap-4">
                    <div className="pt-0.5">
                      <SeverityBadge severity={alert.severity} size="md" />
                    </div>

                    <div>
                      <div className="flex items-center gap-2 flex-wrap">
                        <span className="font-mono text-xs font-bold text-blue-400">
                          {alert.id}
                        </span>
                        <span className="text-slate-500 text-xs">•</span>
                        <span className="font-bold text-white text-base">
                          {alert.title}
                        </span>
                        {alert.status === 'ACKNOWLEDGED' && (
                          <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-blue-500/10 text-blue-300 border border-blue-500/20">
                            ACKNOWLEDGED
                          </span>
                        )}
                        {alert.status === 'RESOLVED' && (
                          <span className="text-[10px] font-mono px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-300 border border-emerald-500/20">
                            RESOLVED
                          </span>
                        )}
                      </div>

                      <div className="flex items-center gap-4 text-xs text-slate-400 mt-1 font-mono">
                        <span className="text-white font-medium">
                          Machine: {alert.machineName} ({alert.machineId})
                        </span>
                        <span>•</span>
                        <span>Line: {alert.line}</span>
                        <span>•</span>
                        <span className="flex items-center gap-1 text-slate-400">
                          <Clock className="w-3.5 h-3.5" />
                          {alert.timestamp}
                        </span>
                      </div>
                    </div>
                  </div>

                  <div className="flex items-center gap-3 self-end md:self-center">
                    <div className="text-right hidden sm:block">
                      <span className="text-[10px] text-slate-500 font-mono block">Trigger</span>
                      <span className="text-xs font-mono font-bold text-red-400">
                        {alert.triggerValue}
                      </span>
                    </div>

                    <div className="p-1 rounded bg-slate-800 text-slate-400">
                      {isExpanded ? (
                        <ChevronUp className="w-4 h-4" />
                      ) : (
                        <ChevronDown className="w-4 h-4" />
                      )}
                    </div>
                  </div>
                </div>

                {/* EXPANDABLE DETAILS */}
                {isExpanded && (
                  <div className="px-5 pb-5 pt-2 border-t border-slate-800/80 bg-slate-950/40 space-y-4 animate-in fade-in duration-150">
                    <p className="text-xs text-slate-300 leading-relaxed font-sans">
                      {alert.description}
                    </p>

                    {/* Sensor Trigger vs Threshold comparison */}
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-3 font-mono text-xs">
                      <div className="p-3 rounded-lg bg-slate-900 border border-slate-800">
                        <span className="text-[10px] text-slate-500 block uppercase">Triggering Signal</span>
                        <span className="text-white font-semibold mt-0.5 block">
                          {alert.triggerSignal}
                        </span>
                      </div>

                      <div className="p-3 rounded-lg bg-slate-900 border border-slate-800">
                        <span className="text-[10px] text-slate-500 block uppercase">Trigger Value</span>
                        <span className="text-red-400 font-bold mt-0.5 block">
                          {alert.triggerValue}
                        </span>
                      </div>

                      <div className="p-3 rounded-lg bg-slate-900 border border-slate-800">
                        <span className="text-[10px] text-slate-500 block uppercase">Operational Threshold</span>
                        <span className="text-slate-300 font-semibold mt-0.5 block">
                          {alert.thresholdValue}
                        </span>
                      </div>
                    </div>

                    {/* Recommended Action Box */}
                    <div className="p-3.5 rounded-lg bg-blue-950/20 border border-blue-500/20 space-y-1">
                      <span className="text-[11px] font-mono uppercase font-bold text-blue-400 flex items-center gap-1.5">
                        <ShieldAlert className="w-4 h-4" />
                        Recommended Containment Protocol
                      </span>
                      <p className="text-xs text-slate-200 leading-relaxed font-sans">
                        {alert.recommendedAction}
                      </p>
                    </div>

                    {/* Actions and Workflow buttons */}
                    <div className="flex items-center justify-between pt-2">
                      <div className="text-xs font-mono text-slate-500">
                        {alert.acknowledgedBy && (
                          <span>Acknowledged by: <strong className="text-slate-300">{alert.acknowledgedBy}</strong></span>
                        )}
                        {alert.resolvedAt && (
                          <span className="ml-3 text-emerald-400">Resolved at {alert.resolvedAt}</span>
                        )}
                      </div>

                      <div className="flex items-center gap-2">
                        {alert.status === 'ACTIVE' && (
                          <button
                            onClick={() => acknowledgeAlert(alert.id)}
                            className="px-3 py-1.5 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-medium border border-slate-700 transition-colors"
                          >
                            Acknowledge Alert
                          </button>
                        )}

                        {alert.status !== 'RESOLVED' && (
                          <button
                            onClick={() => resolveAlert(alert.id)}
                            className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-emerald-600 hover:bg-emerald-500 text-white text-xs font-semibold shadow-md shadow-emerald-600/20 transition-colors"
                          >
                            <CheckCheck className="w-3.5 h-3.5" />
                            <span>Mark Resolved</span>
                          </button>
                        )}

                        {machineObj && (
                          <button
                            onClick={() => {
                              setSelectedMachine(machineObj);
                              setActivePage('machines');
                            }}
                            className="flex items-center gap-1 px-3 py-1.5 rounded-lg bg-blue-600/20 hover:bg-blue-600/30 text-blue-300 text-xs font-medium border border-blue-500/30 transition-colors"
                          >
                            <span>Inspect Machine</span>
                            <ArrowRight className="w-3 h-3" />
                          </button>
                        )}
                      </div>
                    </div>
                  </div>
                )}
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};
