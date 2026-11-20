import React from 'react';
import {
  Cpu,
  Activity,
  AlertTriangle,
  Flame,
  ShieldCheck,
  TrendingUp,
  Clock,
  Zap,
  ArrowRight,
  Database,
  Lock,
} from 'lucide-react';
import { useApp } from '../context/AppContext';
import { MetricCard } from '../components/common/MetricCard';
import { HealthDistributionChart } from '../components/charts/HealthDistributionChart';
import { TelemetryTrendChart } from '../components/charts/TelemetryTrendChart';
import { AlertSeverityChart } from '../components/charts/AlertSeverityChart';
import { HealthBadge, SeverityBadge } from '../components/common/Badge';
import { MOCK_ACTIVITY_LOG } from '../data/mockData';
import { cn } from '../lib/utils';

export const DashboardPage: React.FC = () => {
  const { kpiStats, machines, alerts, setActivePage, setSelectedMachine } = useApp();

  const criticalAlerts = alerts.filter((a) => a.severity === 'CRITICAL' && a.status === 'ACTIVE');
  const recentAlerts = alerts.slice(0, 4);

  // Highest temp and vibration machines for real-time monitoring cards
  const topCriticalMachines = [...machines]
    .sort((a, b) => b.temperature - a.temperature)
    .slice(0, 3);

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Title & Overview Banner */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>Plant Overview & AIOps Telemetry</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/30">
              Live Edge Sync
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Real-time machine telemetry aggregation with zero sensitive on-premise data leakage.
          </p>
        </div>

        {/* Quick action banners */}
        <div className="flex items-center gap-3">
          <button
            onClick={() => setActivePage('egress')}
            className="flex items-center gap-2 px-3.5 py-2 rounded-lg bg-blue-600 hover:bg-blue-500 text-white text-xs font-semibold shadow-lg shadow-blue-600/25 transition-all"
          >
            <ShieldCheck className="w-4 h-4" />
            <span>Verify Egress Proof</span>
          </button>
          <button
            onClick={() => setActivePage('aiops')}
            className="flex items-center gap-2 px-3.5 py-2 rounded-lg bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-semibold border border-slate-700 transition-all"
          >
            <span>AIOps Insights</span>
            <ArrowRight className="w-4 h-4 text-blue-400" />
          </button>
        </div>
      </div>

      {/* 5 Primary KPI Cards Required */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        <MetricCard
          title="Total Machines"
          value={kpiStats.totalMachines}
          icon={Cpu}
          unit="units"
          subtitle="4 Facilities active"
          change="+4 this quarter"
          isPositive={true}
          variant="default"
        />

        <MetricCard
          title="Active Machines"
          value={kpiStats.activeMachines}
          icon={Activity}
          unit="online"
          subtitle="95.2% operational rate"
          change="+1.8%"
          isPositive={true}
          variant="success"
        />

        <MetricCard
          title="Alerts Count"
          value={kpiStats.alertsCount}
          icon={AlertTriangle}
          unit="active"
          subtitle="12 acknowledged"
          change="-3 from yesterday"
          isPositive={true}
          variant="warning"
        />

        <MetricCard
          title="Critical Incidents"
          value={kpiStats.criticalIncidents}
          icon={Flame}
          unit="urgent"
          subtitle="Requires immediate review"
          badge="ACTION REQ"
          variant="critical"
        />

        <MetricCard
          title="Factory Health Score"
          value={`${kpiStats.factoryHealthScore}%`}
          icon={TrendingUp}
          subtitle="Target: >92.0%"
          change="+2.4% vs baseline"
          isPositive={true}
          variant="primary"
        />
      </div>

      {/* Real-time Monitoring Cards (Highlights of highest monitored units) */}
      <div className="space-y-3">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-2">
            <h2 className="text-base font-semibold text-white tracking-tight">
              Real-Time Machine Highlights
            </h2>
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
          </div>
          <button
            onClick={() => setActivePage('machines')}
            className="text-xs text-blue-400 hover:text-blue-300 flex items-center gap-1 font-medium"
          >
            View all 248 machines <ArrowRight className="w-3.5 h-3.5" />
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          {topCriticalMachines.map((machine) => (
            <div
              key={machine.id}
              onClick={() => {
                setSelectedMachine(machine);
                setActivePage('machines');
              }}
              className="glass-panel glass-card-hover rounded-xl p-4 border-slate-800 cursor-pointer group"
            >
              <div className="flex items-start justify-between">
                <div>
                  <div className="flex items-center gap-2">
                    <span className="font-mono text-xs text-blue-400 font-bold">
                      {machine.id}
                    </span>
                    <span className="text-xs text-slate-400">• {machine.line}</span>
                  </div>
                  <h3 className="font-bold text-white text-sm mt-0.5 group-hover:text-blue-300 transition-colors">
                    {machine.name}
                  </h3>
                </div>
                <HealthBadge state={machine.health} size="sm" />
              </div>

              {/* 3 Telemetry Gauges */}
              <div className="grid grid-cols-3 gap-2 mt-4 pt-3 border-t border-slate-800/80 text-center font-mono text-xs">
                <div className="bg-slate-900/60 p-2 rounded-lg border border-slate-800/60">
                  <span className="text-[10px] text-slate-500 block uppercase">Temp</span>
                  <span
                    className={cn(
                      'font-bold',
                      machine.temperature > machine.tempMax ? 'text-red-400' : 'text-slate-200'
                    )}
                  >
                    {machine.temperature}°C
                  </span>
                </div>

                <div className="bg-slate-900/60 p-2 rounded-lg border border-slate-800/60">
                  <span className="text-[10px] text-slate-500 block uppercase">Power</span>
                  <span
                    className={cn(
                      'font-bold',
                      machine.powerUsage > machine.powerMax ? 'text-amber-400' : 'text-slate-200'
                    )}
                  >
                    {machine.powerUsage} kW
                  </span>
                </div>

                <div className="bg-slate-900/60 p-2 rounded-lg border border-slate-800/60">
                  <span className="text-[10px] text-slate-500 block uppercase">Vibration</span>
                  <span
                    className={cn(
                      'font-bold',
                      machine.vibration > machine.vibrationMax ? 'text-red-400' : 'text-slate-200'
                    )}
                  >
                    {machine.vibration} mm/s
                  </span>
                </div>
              </div>

              <div className="mt-3 flex items-center justify-between text-[11px] text-slate-400">
                <span className="font-mono">Uptime: {machine.uptime}%</span>
                <span className="text-blue-400 group-hover:translate-x-0.5 transition-transform flex items-center gap-0.5">
                  Inspect <ArrowRight className="w-3 h-3" />
                </span>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Main Charts Row */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2">
          <TelemetryTrendChart />
        </div>
        <div className="lg:col-span-1">
          <HealthDistributionChart />
        </div>
      </div>

      {/* Secondary Metrics & Incident Breakdown Row */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-1">
          <AlertSeverityChart />
        </div>

        {/* Factory Performance Metrics & Security Sovereignty */}
        <div className="lg:col-span-2 glass-panel rounded-xl p-5 border-slate-800 flex flex-col justify-between">
          <div>
            <div className="flex items-center justify-between mb-3">
              <div>
                <h3 className="text-sm font-semibold text-white tracking-tight">
                  Factory Operations & Data Sovereignty Metrics
                </h3>
                <p className="text-xs text-slate-400">
                  Local edge reduction performance & zero-leakage compliance
                </p>
              </div>
              <span className="px-2.5 py-1 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 text-xs font-mono">
                IEC-62443 / ISO-27001
              </span>
            </div>

            <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 mt-4">
              <div className="p-3.5 rounded-xl bg-slate-900/60 border border-slate-800">
                <div className="flex items-center gap-1.5 text-xs text-slate-400 mb-1">
                  <Activity className="w-3.5 h-3.5 text-blue-400" />
                  <span>Factory OEE</span>
                </div>
                <div className="text-xl font-bold font-mono text-white">88.6%</div>
                <span className="text-[11px] text-emerald-400">Top decile manufacturing</span>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-900/60 border border-slate-800">
                <div className="flex items-center gap-1.5 text-xs text-slate-400 mb-1">
                  <Database className="w-3.5 h-3.5 text-purple-400" />
                  <span>Raw Data Processed</span>
                </div>
                <div className="text-xl font-bold font-mono text-white">4.12 GB</div>
                <span className="text-[11px] text-slate-400">Stays 100% on-premises</span>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-900/60 border border-slate-800">
                <div className="flex items-center gap-1.5 text-xs text-slate-400 mb-1">
                  <Zap className="w-3.5 h-3.5 text-emerald-400" />
                  <span>Metadata Sent</span>
                </div>
                <div className="text-xl font-bold font-mono text-white">142 KB</div>
                <span className="text-[11px] text-emerald-400">99.96% Bandwidth Saved</span>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-900/60 border border-slate-800">
                <div className="flex items-center gap-1.5 text-xs text-slate-400 mb-1">
                  <Lock className="w-3.5 h-3.5 text-emerald-400" />
                  <span>Egress Compliance</span>
                </div>
                <div className="text-xl font-bold font-mono text-emerald-400">100.0%</div>
                <span className="text-[11px] text-emerald-400">0 Data Leaks Logged</span>
              </div>
            </div>
          </div>

          <div className="mt-4 p-3 rounded-lg bg-blue-950/20 border border-blue-500/20 flex items-center justify-between text-xs">
            <span className="text-blue-300">
              💡 <strong>AIOps Prediction Active:</strong> Potential motor bearing seizure detected on Welding Robot A.
            </span>
            <button
              onClick={() => setActivePage('aiops')}
              className="text-blue-400 hover:text-blue-200 underline whitespace-nowrap ml-3"
            >
              Review AI Diagnostics →
            </button>
          </div>
        </div>
      </div>

      {/* Recent Incidents and System Activity Feed */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Incidents */}
        <div className="glass-panel rounded-xl p-5 border-slate-800">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="text-sm font-semibold text-white tracking-tight">
                Recent Incidents & Anomaly Triggers
              </h3>
              <p className="text-xs text-slate-400">Active machine alerts requiring technician triage</p>
            </div>
            <button
              onClick={() => setActivePage('alerts')}
              className="text-xs text-blue-400 hover:underline"
            >
              View Alert Center
            </button>
          </div>

          <div className="space-y-3">
            {recentAlerts.map((alert) => (
              <div
                key={alert.id}
                className="p-3.5 rounded-lg bg-slate-900/70 border border-slate-800/80 hover:border-slate-700 transition-all space-y-1.5"
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <SeverityBadge severity={alert.severity} size="sm" />
                    <span className="font-semibold text-xs text-white">{alert.title}</span>
                  </div>
                  <span className="text-[11px] font-mono text-slate-500">{alert.timestamp}</span>
                </div>
                <div className="flex items-center justify-between text-xs text-slate-400">
                  <span>{alert.machineName} ({alert.machineId})</span>
                  <span className="font-mono text-slate-300">{alert.triggerValue}</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* System Activity Feed */}
        <div className="glass-panel rounded-xl p-5 border-slate-800">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="text-sm font-semibold text-white tracking-tight">
                Real-Time Edge & SaaS Activity Feed
              </h3>
              <p className="text-xs text-slate-400">
                Continuous log of edge ingestion, filtering, and cloud metadata dispatch
              </p>
            </div>
            <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
          </div>

          <div className="space-y-3">
            {MOCK_ACTIVITY_LOG.slice(0, 4).map((event) => (
              <div
                key={event.id}
                className="p-3 rounded-lg bg-slate-900/50 border border-slate-800/60 flex items-start gap-3"
              >
                <div className="p-2 rounded bg-slate-800 text-blue-400 shrink-0 mt-0.5">
                  <Clock className="w-3.5 h-3.5" />
                </div>
                <div className="flex-1 min-w-0">
                  <div className="flex items-center justify-between">
                    <span className="text-xs font-semibold text-slate-200 truncate">
                      {event.title}
                    </span>
                    <span className="text-[10px] font-mono text-slate-500 ml-2">
                      {event.timestamp}
                    </span>
                  </div>
                  <p className="text-[11px] text-slate-400 mt-0.5 leading-relaxed">
                    {event.description}
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};
