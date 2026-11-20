import React from 'react';
import {
  Search,
  Bell,
  Play,
  Pause,
  ShieldCheck,
  Building2,
  ChevronDown,
  Menu,
} from 'lucide-react';
import { useApp } from '../../context/AppContext';
import { cn } from '../../lib/utils';

interface TopNavbarProps {
  onToggleMobileMenu?: () => void;
}

export const TopNavbar: React.FC<TopNavbarProps> = ({ onToggleMobileMenu }) => {
  const {
    isStreaming,
    toggleStreaming,
    searchQuery,
    setSearchQuery,
    isNotificationOpen,
    setIsNotificationOpen,
    activeFacility,
    setActiveFacility,
    alerts,
  } = useApp();

  const activeAlerts = alerts.filter((a) => a.status === 'ACTIVE').length;

  const facilities = [
    'Plant Alpha - Detroit Gigafactory',
    'Facility B - Stuttgart Precision Fab',
    'Plant C - Austin Micro-Electronics',
    'Corridor 4 - Tokyo Robotics Hub',
  ];

  return (
    <header className="h-16 bg-[#0B1220]/90 backdrop-blur-md border-b border-slate-800/80 px-4 sm:px-6 flex items-center justify-between gap-4 sticky top-0 z-20">
      {/* Left: Mobile trigger & Facility selector */}
      <div className="flex items-center gap-3">
        <button
          onClick={onToggleMobileMenu}
          className="lg:hidden p-2 rounded-lg text-slate-400 hover:text-white hover:bg-slate-800"
          title="Toggle Navigation Menu"
        >
          <Menu className="w-5 h-5" />
        </button>

        {/* Facility Selector */}
        <div className="relative group">
          <button className="flex items-center gap-2 px-3 py-1.5 rounded-lg bg-slate-900/80 hover:bg-slate-800 text-xs font-medium text-slate-200 border border-slate-700/60 transition-all">
            <Building2 className="w-3.5 h-3.5 text-blue-400" />
            <span className="hidden sm:inline font-mono">{activeFacility}</span>
            <span className="sm:hidden font-mono">Plant Alpha</span>
            <ChevronDown className="w-3.5 h-3.5 text-slate-400" />
          </button>

          {/* Dropdown Menu */}
          <div className="absolute left-0 top-full mt-1.5 w-64 bg-slate-900 border border-slate-800 rounded-xl shadow-2xl py-1.5 hidden group-hover:block z-50 animate-in fade-in zoom-in-95 duration-150">
            <div className="px-3 py-1.5 text-[10px] uppercase font-mono text-slate-500 font-semibold">
              Select Production Facility
            </div>
            {facilities.map((fac) => (
              <button
                key={fac}
                onClick={() => setActiveFacility(fac)}
                className={cn(
                  'w-full text-left px-3 py-2 text-xs font-mono transition-colors flex items-center justify-between',
                  activeFacility === fac
                    ? 'bg-blue-600/20 text-blue-300 font-semibold'
                    : 'text-slate-300 hover:bg-slate-800 hover:text-white'
                )}
              >
                <span>{fac}</span>
                {activeFacility === fac && (
                  <span className="w-1.5 h-1.5 rounded-full bg-blue-400" />
                )}
              </button>
            ))}
          </div>
        </div>

        {/* Egress Security Certification Pill */}
        <div className="hidden xl:flex items-center gap-1.5 px-2.5 py-1 rounded-full bg-emerald-500/10 border border-emerald-500/20 text-emerald-400 text-xs font-mono">
          <ShieldCheck className="w-3.5 h-3.5" />
          <span>ZERO DATA LEAKAGE CERTIFIED</span>
        </div>
      </div>

      {/* Middle: Search Bar */}
      <div className="flex-1 max-w-md relative hidden md:block">
        <Search className="w-4 h-4 text-slate-500 absolute left-3 top-1/2 -translate-y-1/2" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Search machines, sensor telemetry, alerts, formulas..."
          className="w-full bg-slate-900/80 border border-slate-800 rounded-lg pl-9 pr-4 py-1.5 text-xs text-slate-200 placeholder-slate-500 focus:outline-none focus:border-blue-500/60 focus:ring-1 focus:ring-blue-500/30 transition-all font-mono"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            className="absolute right-2.5 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-300 text-xs"
          >
            Clear
          </button>
        )}
      </div>

      {/* Right: Stream toggle & Notifications */}
      <div className="flex items-center gap-2.5">
        {/* Live Stream Controller */}
        <button
          onClick={toggleStreaming}
          className={cn(
            'flex items-center gap-2 px-3 py-1.5 rounded-lg border text-xs font-mono font-medium transition-all',
            isStreaming
              ? 'bg-emerald-500/10 border-emerald-500/30 text-emerald-300 hover:bg-emerald-500/20 shadow-[0_0_12px_rgba(34,197,94,0.15)]'
              : 'bg-amber-500/10 border-amber-500/30 text-amber-300 hover:bg-amber-500/20'
          )}
          title={isStreaming ? 'Click to Pause Telemetry' : 'Click to Resume Telemetry'}
        >
          {isStreaming ? (
            <>
              <span className="relative flex h-2 w-2">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-emerald-400 opacity-75" />
                <span className="relative inline-flex rounded-full h-2 w-2 bg-emerald-500" />
              </span>
              <span className="hidden sm:inline">LIVE 100Hz</span>
              <Pause className="w-3.5 h-3.5 opacity-70" />
            </>
          ) : (
            <>
              <span className="h-2 w-2 rounded-full bg-amber-400" />
              <span className="hidden sm:inline">PAUSED</span>
              <Play className="w-3.5 h-3.5 opacity-70" />
            </>
          )}
        </button>

        {/* Notifications Trigger */}
        <button
          onClick={() => setIsNotificationOpen(!isNotificationOpen)}
          className="relative p-2 rounded-lg bg-slate-900 border border-slate-800 text-slate-300 hover:text-white hover:bg-slate-800 transition-colors"
          title="Notifications & Incident Log"
        >
          <Bell className="w-4 h-4" />
          {activeAlerts > 0 && (
            <span className="absolute -top-1 -right-1 w-4 h-4 rounded-full bg-red-500 text-[10px] font-bold text-white flex items-center justify-center font-mono">
              {activeAlerts}
            </span>
          )}
        </button>

        {/* User / Org badge */}
        <div className="hidden sm:flex items-center gap-2 pl-2 border-l border-slate-800">
          <div className="w-8 h-8 rounded-lg bg-blue-600/30 border border-blue-500/30 text-blue-300 flex items-center justify-center font-bold text-xs font-mono">
            FP
          </div>
          <div className="hidden lg:block text-left text-xs">
            <span className="block font-semibold text-white">Chief Engineer</span>
            <span className="block text-[10px] text-slate-400 font-mono">SecOps Enclave</span>
          </div>
        </div>
      </div>
    </header>
  );
};
