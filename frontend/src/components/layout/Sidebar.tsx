import React from 'react';
import {
  LayoutDashboard,
  Cpu,
  AlertTriangle,
  Sparkles,
  ShieldCheck,
  GitBranch,
  Info,
  Activity,
  Layers,
} from 'lucide-react';
import { useApp, PageId } from '../../context/AppContext';
import { cn } from '../../lib/utils';

export const Sidebar: React.FC = () => {
  const { activePage, setActivePage, alerts, kpiStats } = useApp();

  const criticalAlertsCount = alerts.filter((a) => a.severity === 'CRITICAL' && a.status === 'ACTIVE').length;

  const navItems: {
    id: PageId;
    label: string;
    icon: React.ComponentType<{ className?: string }>;
    badge?: string | number;
    badgeVariant?: 'primary' | 'critical' | 'success' | 'warning';
  }[] = [
    {
      id: 'dashboard',
      label: 'Dashboard Overview',
      icon: LayoutDashboard,
    },
    {
      id: 'machines',
      label: 'Machine Monitoring',
      icon: Cpu,
      badge: kpiStats.totalMachines,
      badgeVariant: 'primary',
    },
    {
      id: 'alerts',
      label: 'Alert & Incident Center',
      icon: AlertTriangle,
      badge: criticalAlertsCount > 0 ? `${criticalAlertsCount} CRIT` : undefined,
      badgeVariant: 'critical',
    },
    {
      id: 'aiops',
      label: 'AIOps Recommendations',
      icon: Sparkles,
      badge: '92% AI',
      badgeVariant: 'success',
    },
    {
      id: 'egress',
      label: 'Egress Proof Panel',
      icon: ShieldCheck,
      badge: 'VERIFIED',
      badgeVariant: 'success',
    },
    {
      id: 'architecture',
      label: 'Architecture Flow',
      icon: GitBranch,
    },
    {
      id: 'about',
      label: 'About Project',
      icon: Info,
    },
  ];

  return (
    <aside className="w-64 bg-[#070D18] border-r border-slate-800/80 flex flex-col justify-between shrink-0 select-none z-30 min-h-screen">
      <div>
        {/* Brand Header */}
        <div className="h-16 flex items-center px-5 border-b border-slate-800/80 gap-3">
          <div className="h-9 w-9 rounded-lg bg-blue-600/20 border border-blue-500/40 flex items-center justify-center text-blue-400 shadow-[0_0_15px_rgba(59,130,246,0.3)]">
            <Activity className="w-5 h-5 animate-pulse" />
          </div>
          <div>
            <div className="flex items-center gap-1.5">
              <span className="font-extrabold text-white text-base tracking-tight">
                FactoryPulse
              </span>
              <span className="text-[11px] font-mono font-bold px-1.5 py-0.2 rounded bg-blue-500/20 text-blue-400 border border-blue-500/30">
                AI
              </span>
            </div>
            <p className="text-[10px] text-slate-400 uppercase tracking-widest font-mono">
              Secure AIOps Observability
            </p>
          </div>
        </div>

        {/* Navigation Section */}
        <div className="px-3 py-4">
          <p className="px-3 text-[11px] font-semibold text-slate-500 uppercase tracking-wider mb-2 font-mono">
            Navigation
          </p>
          <nav className="space-y-1">
            {navItems.map((item) => {
              const Icon = item.icon;
              const isActive = activePage === item.id;
              return (
                <button
                  key={item.id}
                  onClick={() => setActivePage(item.id)}
                  className={cn(
                    'w-full flex items-center justify-between px-3 py-2.5 rounded-lg text-sm font-medium transition-all group',
                    isActive
                      ? 'bg-blue-600/15 text-blue-400 border border-blue-500/30 shadow-[0_0_15px_rgba(59,130,246,0.15)] font-semibold'
                      : 'text-slate-400 hover:text-slate-200 hover:bg-slate-800/50'
                  )}
                >
                  <div className="flex items-center gap-3">
                    <Icon
                      className={cn(
                        'w-4 h-4 transition-colors',
                        isActive
                          ? 'text-blue-400'
                          : 'text-slate-400 group-hover:text-slate-300'
                      )}
                    />
                    <span>{item.label}</span>
                  </div>

                  {item.badge && (
                    <span
                      className={cn(
                        'text-[10px] font-mono px-2 py-0.5 rounded-full font-bold uppercase',
                        item.badgeVariant === 'critical'
                          ? 'bg-red-500/20 text-red-400 border border-red-500/30 animate-pulse'
                          : item.badgeVariant === 'success'
                          ? 'bg-emerald-500/20 text-emerald-400 border border-emerald-500/30'
                          : 'bg-slate-800 text-slate-300 border border-slate-700/60'
                      )}
                    >
                      {item.badge}
                    </span>
                  )}
                </button>
              );
            })}
          </nav>
        </div>
      </div>

      {/* Bottom Local Collector Status Card */}
      <div className="p-4 border-t border-slate-800/80">
        <div className="p-3.5 rounded-xl bg-slate-900/80 border border-slate-800 space-y-2">
          <div className="flex items-center justify-between">
            <span className="text-[11px] font-medium text-slate-400 flex items-center gap-1.5">
              <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse" />
              Local Collector v2.4
            </span>
            <span className="text-[10px] font-mono text-emerald-400 font-bold bg-emerald-500/10 px-1.5 py-0.5 rounded border border-emerald-500/20">
              ENCLAVE ACTIVE
            </span>
          </div>

          <div className="space-y-1 text-[11px] font-mono text-slate-400">
            <div className="flex justify-between">
              <span>Egress Policy:</span>
              <span className="text-white">Strict Metadata</span>
            </div>
            <div className="flex justify-between">
              <span>Sampling:</span>
              <span className="text-white">100 Hz On-Prem</span>
            </div>
            <div className="flex justify-between">
              <span>Data Sovereignty:</span>
              <span className="text-emerald-400 font-semibold">100% Retained</span>
            </div>
          </div>
        </div>
      </div>
    </aside>
  );
};
