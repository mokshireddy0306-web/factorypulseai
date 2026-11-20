import React, { useState } from 'react';
import {
  LayoutGrid,
  Table as TableIcon,
  Search,
  Filter,
  SlidersHorizontal,
  ArrowUpDown,
  ExternalLink,
  ShieldAlert,
  Flame,
  Zap,
  Activity,
  CheckCircle2,
  AlertTriangle,
  Clock,
  Wrench,
} from 'lucide-react';
import { useApp } from '../context/AppContext';
import { Machine, HealthState } from '../types';
import { HealthBadge, ConnectivityBadge } from '../components/common/Badge';
import { Modal } from '../components/common/Modal';
import { cn } from '../lib/utils';

export const MachineMonitoringPage: React.FC = () => {
  const { machines, selectedMachine, setSelectedMachine, searchQuery, setSearchQuery } = useApp();

  const [viewMode, setViewMode] = useState<'grid' | 'table'>('grid');
  const [selectedHealth, setSelectedHealth] = useState<HealthState | 'ALL'>('ALL');
  const [selectedLine, setSelectedLine] = useState<string>('ALL');
  const [sortBy, setSortBy] = useState<'id' | 'health' | 'temp' | 'vibration'>('health');
  const [inspectModalOpen, setInspectModalOpen] = useState(false);

  // Extract unique lines
  const lines = ['ALL', ...Array.from(new Set(machines.map((m) => m.line)))];

  // Filtering & searching
  const filteredMachines = machines
    .filter((machine) => {
      const matchesSearch =
        machine.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        machine.id.toLowerCase().includes(searchQuery.toLowerCase()) ||
        machine.line.toLowerCase().includes(searchQuery.toLowerCase());
      const matchesHealth = selectedHealth === 'ALL' || machine.health === selectedHealth;
      const matchesLine = selectedLine === 'ALL' || machine.line === selectedLine;
      return matchesSearch && matchesHealth && matchesLine;
    })
    .sort((a, b) => {
      if (sortBy === 'health') {
        const order = { POOR: 0, FAIR: 1, GOOD: 2, EXCELLENT: 3 };
        return order[a.health] - order[b.health];
      }
      if (sortBy === 'temp') return b.temperature - a.temperature;
      if (sortBy === 'vibration') return b.vibration - a.vibration;
      return a.id.localeCompare(b.id);
    });

  const handleInspect = (machine: Machine) => {
    setSelectedMachine(machine);
    setInspectModalOpen(true);
  };

  return (
    <div className="space-y-6 animate-in fade-in duration-300">
      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold text-white tracking-tight flex items-center gap-2.5">
            <span>Machine Fleet Telemetry</span>
            <span className="text-xs font-mono px-2.5 py-0.5 rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/30">
              {filteredMachines.length} of {machines.length} Units
            </span>
          </h1>
          <p className="text-sm text-slate-400 mt-1">
            Real-time machine status, vibration analysis, temperature, and local edge collector health.
          </p>
        </div>

        {/* View Toggle (Grid / Table) */}
        <div className="flex items-center gap-3">
          <div className="flex bg-slate-900 border border-slate-800 rounded-lg p-1">
            <button
              onClick={() => setViewMode('grid')}
              className={cn(
                'flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all',
                viewMode === 'grid'
                  ? 'bg-blue-600 text-white shadow-sm'
                  : 'text-slate-400 hover:text-white'
              )}
            >
              <LayoutGrid className="w-4 h-4" />
              <span>Grid View</span>
            </button>
            <button
              onClick={() => setViewMode('table')}
              className={cn(
                'flex items-center gap-1.5 px-3 py-1.5 rounded-md text-xs font-medium transition-all',
                viewMode === 'table'
                  ? 'bg-blue-600 text-white shadow-sm'
                  : 'text-slate-400 hover:text-white'
              )}
            >
              <TableIcon className="w-4 h-4" />
              <span>Table View</span>
            </button>
          </div>
        </div>
      </div>

      {/* Filter and Control Bar */}
      <div className="glass-panel rounded-xl p-4 border-slate-800 flex flex-col md:flex-row gap-4 items-stretch md:items-center justify-between">
        {/* Search */}
        <div className="relative flex-1 max-w-sm">
          <Search className="w-4 h-4 text-slate-500 absolute left-3 top-1/2 -translate-y-1/2" />
          <input
            type="text"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            placeholder="Search by Machine ID, Name, or Line..."
            className="w-full bg-slate-900/80 border border-slate-800 rounded-lg pl-9 pr-3 py-1.5 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-blue-500 font-mono"
          />
        </div>

        {/* Filters */}
        <div className="flex items-center gap-3 flex-wrap text-xs">
          {/* Health Filter */}
          <div className="flex items-center gap-1 bg-slate-900/80 border border-slate-800 rounded-lg p-1">
            <span className="text-slate-500 px-2 font-mono text-[11px]">Health:</span>
            {(['ALL', 'EXCELLENT', 'GOOD', 'FAIR', 'POOR'] as const).map((h) => (
              <button
                key={h}
                onClick={() => setSelectedHealth(h)}
                className={cn(
                  'px-2 py-1 rounded text-xs font-mono transition-all',
                  selectedHealth === h
                    ? 'bg-blue-600 text-white font-bold'
                    : 'text-slate-400 hover:text-white'
                )}
              >
                {h}
              </button>
            ))}
          </div>

          {/* Line Filter */}
          <div className="flex items-center gap-1 bg-slate-900/80 border border-slate-800 rounded-lg px-2 py-1">
            <Filter className="w-3.5 h-3.5 text-slate-500" />
            <select
              value={selectedLine}
              onChange={(e) => setSelectedLine(e.target.value)}
              className="bg-transparent text-slate-300 text-xs focus:outline-none font-mono cursor-pointer"
            >
              {lines.map((l) => (
                <option key={l} value={l} className="bg-slate-900 text-white">
                  Line: {l}
                </option>
              ))}
            </select>
          </div>

          {/* Sort By */}
          <div className="flex items-center gap-1 bg-slate-900/80 border border-slate-800 rounded-lg px-2 py-1">
            <ArrowUpDown className="w-3.5 h-3.5 text-slate-500" />
            <select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value as any)}
              className="bg-transparent text-slate-300 text-xs focus:outline-none font-mono cursor-pointer"
            >
              <option value="health" className="bg-slate-900 text-white">Sort: Critical First</option>
              <option value="temp" className="bg-slate-900 text-white">Sort: Highest Temp</option>
              <option value="vibration" className="bg-slate-900 text-white">Sort: Highest Vibration</option>
              <option value="id" className="bg-slate-900 text-white">Sort: Machine ID</option>
            </select>
          </div>
        </div>
      </div>

      {/* GRID VIEW */}
      {viewMode === 'grid' && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {filteredMachines.map((machine) => (
            <div
              key={machine.id}
              className={cn(
                'glass-panel rounded-xl p-5 border-slate-800 glass-card-hover relative overflow-hidden flex flex-col justify-between group',
                machine.health === 'POOR' && 'border-red-500/30 shadow-[0_0_20px_rgba(239,68,68,0.1)]',
                machine.health === 'FAIR' && 'border-amber-500/30'
              )}
            >
              {/* Header */}
              <div>
                <div className="flex items-start justify-between">
                  <div className="flex items-center gap-2">
                    <span className="font-mono text-sm font-bold text-blue-400 bg-blue-500/10 px-2 py-0.5 rounded border border-blue-500/20">
                      {machine.id}
                    </span>
                    <ConnectivityBadge status={machine.connectivity} />
                  </div>
                  <HealthBadge state={machine.health} />
                </div>

                <div className="mt-3">
                  <h3 className="font-bold text-base text-white group-hover:text-blue-300 transition-colors">
                    {machine.name}
                  </h3>
                  <p className="text-xs text-slate-400 font-mono mt-0.5">
                    {machine.model} • {machine.line}
                  </p>
                </div>

                {/* 3 Main Telemetry Gauges */}
                <div className="grid grid-cols-3 gap-2 mt-4 pt-3 border-t border-slate-800/80 text-center font-mono">
                  {/* Temp */}
                  <div className="p-2.5 rounded-lg bg-slate-900/80 border border-slate-800">
                    <span className="text-[10px] text-slate-500 uppercase block">Temp</span>
                    <span
                      className={cn(
                        'text-sm font-bold',
                        machine.temperature > machine.tempMax
                          ? 'text-red-400'
                          : 'text-slate-200'
                      )}
                    >
                      {machine.temperature}°C
                    </span>
                    <span className="text-[9px] text-slate-500 block">max {machine.tempMax}°C</span>
                  </div>

                  {/* Power */}
                  <div className="p-2.5 rounded-lg bg-slate-900/80 border border-slate-800">
                    <span className="text-[10px] text-slate-500 uppercase block">Power</span>
                    <span
                      className={cn(
                        'text-sm font-bold',
                        machine.powerUsage > machine.powerMax
                          ? 'text-amber-400'
                          : 'text-slate-200'
                      )}
                    >
                      {machine.powerUsage} kW
                    </span>
                    <span className="text-[9px] text-slate-500 block">max {machine.powerMax}kW</span>
                  </div>

                  {/* Vibration */}
                  <div className="p-2.5 rounded-lg bg-slate-900/80 border border-slate-800">
                    <span className="text-[10px] text-slate-500 uppercase block">Vibration</span>
                    <span
                      className={cn(
                        'text-sm font-bold',
                        machine.vibration > machine.vibrationMax
                          ? 'text-red-400'
                          : 'text-slate-200'
                      )}
                    >
                      {machine.vibration} mm/s
                    </span>
                    <span className="text-[9px] text-slate-500 block">max {machine.vibrationMax}mm/s</span>
                  </div>
                </div>

                {/* Mini progress bar of health score */}
                <div className="mt-4 space-y-1">
                  <div className="flex justify-between text-[11px] font-mono">
                    <span className="text-slate-400">Health Index</span>
                    <span
                      className={cn(
                        'font-bold',
                        machine.healthScore < 50
                          ? 'text-red-400'
                          : machine.healthScore < 75
                          ? 'text-amber-400'
                          : 'text-emerald-400'
                      )}
                    >
                      {machine.healthScore} / 100
                    </span>
                  </div>
                  <div className="w-full bg-slate-800 h-1.5 rounded-full overflow-hidden">
                    <div
                      className={cn(
                        'h-full rounded-full transition-all duration-500',
                        machine.healthScore < 50
                          ? 'bg-red-500'
                          : machine.healthScore < 75
                          ? 'bg-amber-500'
                          : 'bg-emerald-500'
                      )}
                      style={{ width: `${machine.healthScore}%` }}
                    />
                  </div>
                </div>
              </div>

              {/* Bottom Actions */}
              <div className="mt-5 pt-3 border-t border-slate-800/80 flex items-center justify-between text-xs">
                <span className="text-slate-400 font-mono text-[11px]">
                  Hours: {machine.operatingHours}h
                </span>
                <button
                  onClick={() => handleInspect(machine)}
                  className="flex items-center gap-1.5 px-3 py-1.5 rounded-lg bg-slate-800 hover:bg-blue-600 text-slate-200 hover:text-white transition-all font-medium border border-slate-700/60"
                >
                  <span>Inspect</span>
                  <ExternalLink className="w-3.5 h-3.5" />
                </button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* TABLE VIEW */}
      {viewMode === 'table' && (
        <div className="glass-panel rounded-xl border-slate-800 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs font-mono">
              <thead className="bg-slate-950/80 text-slate-400 uppercase text-[10px] tracking-wider border-b border-slate-800">
                <tr>
                  <th className="py-3 px-4">Machine ID</th>
                  <th className="py-3 px-4">Machine Name</th>
                  <th className="py-3 px-4">Production Line</th>
                  <th className="py-3 px-4">Health State</th>
                  <th className="py-3 px-4">Temperature</th>
                  <th className="py-3 px-4">Power Usage</th>
                  <th className="py-3 px-4">Vibration</th>
                  <th className="py-3 px-4">Status</th>
                  <th className="py-3 px-4 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-800/60 text-slate-300">
                {filteredMachines.map((machine) => (
                  <tr
                    key={machine.id}
                    className="hover:bg-slate-800/40 transition-colors group cursor-pointer"
                    onClick={() => handleInspect(machine)}
                  >
                    <td className="py-3.5 px-4 font-bold text-blue-400">
                      {machine.id}
                    </td>
                    <td className="py-3.5 px-4 font-sans font-semibold text-white group-hover:text-blue-300">
                      {machine.name}
                    </td>
                    <td className="py-3.5 px-4 text-slate-400">
                      {machine.line}
                    </td>
                    <td className="py-3.5 px-4">
                      <HealthBadge state={machine.health} size="sm" />
                    </td>
                    <td className="py-3.5 px-4">
                      <span
                        className={cn(
                          'font-bold',
                          machine.temperature > machine.tempMax
                            ? 'text-red-400'
                            : 'text-slate-300'
                        )}
                      >
                        {machine.temperature}°C
                      </span>
                    </td>
                    <td className="py-3.5 px-4">
                      <span
                        className={cn(
                          'font-bold',
                          machine.powerUsage > machine.powerMax
                            ? 'text-amber-400'
                            : 'text-slate-300'
                        )}
                      >
                        {machine.powerUsage} kW
                      </span>
                    </td>
                    <td className="py-3.5 px-4">
                      <span
                        className={cn(
                          'font-bold',
                          machine.vibration > machine.vibrationMax
                            ? 'text-red-400'
                            : 'text-slate-300'
                        )}
                      >
                        {machine.vibration} mm/s
                      </span>
                    </td>
                    <td className="py-3.5 px-4">
                      <ConnectivityBadge status={machine.connectivity} />
                    </td>
                    <td className="py-3.5 px-4 text-right">
                      <button
                        onClick={(e) => {
                          e.stopPropagation();
                          handleInspect(machine);
                        }}
                        className="p-1.5 rounded hover:bg-slate-700 text-slate-400 hover:text-white"
                      >
                        <ExternalLink className="w-4 h-4" />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}

      {/* Detailed Machine Inspection Modal */}
      {selectedMachine && (
        <Modal
          isOpen={inspectModalOpen}
          onClose={() => setInspectModalOpen(false)}
          title={`Telemetry Inspection: ${selectedMachine.name}`}
          subtitle={`Machine ID: ${selectedMachine.id} • Model: ${selectedMachine.model} • ${selectedMachine.line}`}
          size="xl"
        >
          <div className="space-y-6">
            {/* Top Status & Health row */}
            <div className="grid grid-cols-1 md:grid-cols-4 gap-3">
              <div className="p-3.5 rounded-xl bg-slate-950/60 border border-slate-800">
                <span className="text-[11px] text-slate-400 font-mono block">Health State</span>
                <div className="mt-1">
                  <HealthBadge state={selectedMachine.health} />
                </div>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-950/60 border border-slate-800">
                <span className="text-[11px] text-slate-400 font-mono block">Uptime Score</span>
                <span className="text-lg font-bold font-mono text-white mt-1 block">
                  {selectedMachine.uptime}%
                </span>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-950/60 border border-slate-800">
                <span className="text-[11px] text-slate-400 font-mono block">Operating Hours</span>
                <span className="text-lg font-bold font-mono text-white mt-1 block">
                  {selectedMachine.operatingHours} hrs
                </span>
              </div>

              <div className="p-3.5 rounded-xl bg-slate-950/60 border border-slate-800">
                <span className="text-[11px] text-slate-400 font-mono block">Next Maintenance</span>
                <span className="text-xs font-mono text-blue-400 mt-1 block">
                  {selectedMachine.nextScheduledMaintenance}
                </span>
              </div>
            </div>

            {/* Live Sensors Breakdown */}
            <div className="p-4 rounded-xl bg-slate-950/80 border border-slate-800 space-y-3">
              <h4 className="text-xs font-semibold uppercase tracking-wider text-slate-300 font-mono">
                Real-Time Telemetry Sensor Array
              </h4>

              <div className="grid grid-cols-1 md:grid-cols-3 gap-3 font-mono text-xs">
                <div className="p-3 rounded-lg bg-slate-900 border border-slate-800 space-y-1">
                  <span className="text-slate-400">Core Temperature</span>
                  <div className="text-xl font-bold text-white">
                    {selectedMachine.temperature}°C
                  </div>
                  <span className="text-[10px] text-slate-500">
                    Threshold: {selectedMachine.tempMax}°C max
                  </span>
                </div>

                <div className="p-3 rounded-lg bg-slate-900 border border-slate-800 space-y-1">
                  <span className="text-slate-400">Power Consumption</span>
                  <div className="text-xl font-bold text-white">
                    {selectedMachine.powerUsage} kW
                  </div>
                  <span className="text-[10px] text-slate-500">
                    Threshold: {selectedMachine.powerMax} kW max
                  </span>
                </div>

                <div className="p-3 rounded-lg bg-slate-900 border border-slate-800 space-y-1">
                  <span className="text-slate-400">Harmonic Vibration</span>
                  <div className="text-xl font-bold text-white">
                    {selectedMachine.vibration} mm/s
                  </div>
                  <span className="text-[10px] text-slate-500">
                    Threshold: {selectedMachine.vibrationMax} mm/s max
                  </span>
                </div>
              </div>
            </div>

            {/* Local Collector Redaction & Privacy Enclave for this unit */}
            <div className="p-4 rounded-xl bg-slate-950/80 border border-slate-800 space-y-2">
              <div className="flex items-center justify-between">
                <h4 className="text-xs font-semibold uppercase tracking-wider text-emerald-400 font-mono flex items-center gap-1.5">
                  <CheckCircle2 className="w-4 h-4" />
                  Local Collector Data Redaction Status
                </h4>
                <span className="px-2 py-0.5 rounded bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 text-[10px] font-mono">
                  ZERO CLOUD LEAKAGE
                </span>
              </div>

              <p className="text-xs text-slate-400 leading-relaxed">
                The following sensitive attributes are processed exclusively within the factory edge hardware enclave and are scrubbed prior to metadata egress:
              </p>

              <div className="grid grid-cols-1 md:grid-cols-3 gap-2 pt-2 text-xs font-mono">
                <div className="p-2.5 rounded bg-red-950/20 border border-red-500/30 text-red-300">
                  <span className="text-[10px] text-red-400 block font-bold">OPERATOR PII</span>
                  <span className="line-through">{selectedMachine.operatorId}</span>
                </div>
                <div className="p-2.5 rounded bg-red-950/20 border border-red-500/30 text-red-300">
                  <span className="text-[10px] text-red-400 block font-bold">PROPRIETARY CODE</span>
                  <span className="line-through">{selectedMachine.proprietaryCode}</span>
                </div>
                <div className="p-2.5 rounded bg-red-950/20 border border-red-500/30 text-red-300">
                  <span className="text-[10px] text-red-400 block font-bold">INTERNAL IP</span>
                  <span className="line-through">{selectedMachine.internalIp}</span>
                </div>
              </div>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
};
