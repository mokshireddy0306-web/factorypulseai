import React from 'react';
import { X, AlertTriangle, ShieldCheck, Activity, CheckCircle, ExternalLink } from 'lucide-react';
import { useApp } from '../../context/AppContext';
import { MOCK_ACTIVITY_LOG } from '../../data/mockData';
import { SeverityBadge } from '../common/Badge';
import { cn } from '../../lib/utils';

export const NotificationDrawer: React.FC = () => {
  const {
    isNotificationOpen,
    setIsNotificationOpen,
    alerts,
    acknowledgeAlert,
    resolveAlert,
    setActivePage,
    setSelectedMachine,
    machines,
  } = useApp();

  if (!isNotificationOpen) return null;

  const activeAlerts = alerts.filter((a) => a.status === 'ACTIVE');

  return (
    <div className="fixed inset-0 z-50 overflow-hidden">
      {/* Backdrop */}
      <div
        className="fixed inset-0 bg-slate-950/70 backdrop-blur-sm transition-opacity"
        onClick={() => setIsNotificationOpen(false)}
      />

      <div className="fixed inset-y-0 right-0 max-w-full flex pl-10">
        <div className="w-screen max-w-md bg-slate-900 border-l border-slate-800 shadow-2xl flex flex-col justify-between animate-in slide-in-from-right duration-200">
          {/* Header */}
          <div className="p-5 border-b border-slate-800 flex items-center justify-between bg-slate-950/40">
            <div className="flex items-center gap-2">
              <Activity className="w-5 h-5 text-blue-400" />
              <h2 className="text-base font-semibold text-white tracking-tight">
                Live Alert & Event Feed
              </h2>
            </div>
            <button
              onClick={() => setIsNotificationOpen(false)}
              className="p-1.5 rounded-lg text-slate-400 hover:text-white hover:bg-slate-800 transition-colors"
            >
              <X className="w-5 h-5" />
            </button>
          </div>

          {/* Body Content */}
          <div className="flex-1 overflow-y-auto p-5 space-y-6">
            {/* Active Alerts Section */}
            <div>
              <div className="flex items-center justify-between mb-3">
                <span className="text-xs font-mono font-bold uppercase text-slate-400 tracking-wider flex items-center gap-1.5">
                  <AlertTriangle className="w-4 h-4 text-amber-400" />
                  Active Anomaly Alerts ({activeAlerts.length})
                </span>
                <button
                  onClick={() => {
                    setActivePage('alerts');
                    setIsNotificationOpen(false);
                  }}
                  className="text-xs text-blue-400 hover:underline flex items-center gap-1"
                >
                  View All <ExternalLink className="w-3 h-3" />
                </button>
              </div>

              {activeAlerts.length === 0 ? (
                <div className="p-4 rounded-xl bg-slate-950/60 border border-slate-800 text-center text-xs text-slate-400">
                  <CheckCircle className="w-6 h-6 text-emerald-400 mx-auto mb-1.5" />
                  No unacknowledged alerts on this shift!
                </div>
              ) : (
                <div className="space-y-2.5">
                  {activeAlerts.map((alert) => (
                    <div
                      key={alert.id}
                      className="p-3.5 rounded-xl bg-slate-950/80 border border-slate-800/80 hover:border-slate-700 transition-all space-y-2"
                    >
                      <div className="flex items-start justify-between gap-2">
                        <span className="font-semibold text-xs text-white">
                          {alert.title}
                        </span>
                        <SeverityBadge severity={alert.severity} size="sm" />
                      </div>

                      <p className="text-[11px] text-slate-400 leading-relaxed">
                        {alert.description}
                      </p>

                      <div className="flex items-center justify-between text-[10px] font-mono text-slate-500 pt-1 border-t border-slate-800/60">
                        <span>{alert.machineName}</span>
                        <span>{alert.timestamp}</span>
                      </div>

                      <div className="flex items-center gap-2 pt-1">
                        <button
                          onClick={() => acknowledgeAlert(alert.id)}
                          className="flex-1 py-1 rounded bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs font-medium border border-slate-700 transition-colors"
                        >
                          Acknowledge
                        </button>
                        <button
                          onClick={() => resolveAlert(alert.id)}
                          className="flex-1 py-1 rounded bg-emerald-600/20 hover:bg-emerald-600/30 text-emerald-300 text-xs font-medium border border-emerald-500/40 transition-colors"
                        >
                          Mark Resolved
                        </button>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>

            {/* System Activity Stream */}
            <div>
              <div className="flex items-center justify-between mb-3">
                <span className="text-xs font-mono font-bold uppercase text-slate-400 tracking-wider flex items-center gap-1.5">
                  <ShieldCheck className="w-4 h-4 text-emerald-400" />
                  Egress & Redaction Stream
                </span>
                <span className="text-[10px] font-mono text-emerald-400 bg-emerald-500/10 px-2 py-0.5 rounded border border-emerald-500/20">
                  REAL-TIME AUDIT
                </span>
              </div>

              <div className="space-y-3 relative before:absolute before:left-2 before:top-2 before:bottom-2 before:w-0.5 before:bg-slate-800">
                {MOCK_ACTIVITY_LOG.map((event) => (
                  <div key={event.id} className="relative pl-6 text-xs group">
                    <span className="absolute left-0 top-1.5 w-4 h-4 rounded-full bg-slate-900 border border-slate-700 flex items-center justify-center">
                      <span className="w-1.5 h-1.5 rounded-full bg-blue-400" />
                    </span>
                    <div className="p-3 rounded-lg bg-slate-950/60 border border-slate-800/60 space-y-1">
                      <div className="flex items-center justify-between">
                        <span className="font-semibold text-slate-200">{event.title}</span>
                        <span className="text-[10px] font-mono text-slate-500">{event.timestamp}</span>
                      </div>
                      <p className="text-[11px] text-slate-400 leading-relaxed">
                        {event.description}
                      </p>
                      {event.badge && (
                        <span className="inline-block mt-1 text-[10px] font-mono px-1.5 py-0.5 rounded bg-blue-500/10 text-blue-400 border border-blue-500/20">
                          {event.badge}
                        </span>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* Footer */}
          <div className="p-4 border-t border-slate-800 bg-slate-950/60 flex items-center justify-between text-xs text-slate-400">
            <span className="font-mono">Local Collector Heartbeat</span>
            <span className="text-emerald-400 font-mono font-semibold">100% OPERATIONAL</span>
          </div>
        </div>
      </div>
    </div>
  );
};
