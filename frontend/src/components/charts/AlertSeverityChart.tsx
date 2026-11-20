import React from 'react';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  Cell,
} from 'recharts';
import { MOCK_ALERT_DISTRIBUTION } from '../../data/mockData';

export const AlertSeverityChart: React.FC = () => {
  const CustomTooltip = ({ active, payload }: any) => {
    if (active && payload && payload.length) {
      const data = payload[0];
      return (
        <div className="bg-slate-900 border border-slate-700/80 p-2.5 rounded-lg shadow-xl text-xs font-mono">
          <div className="flex items-center gap-1.5">
            <span
              className="w-2.5 h-2.5 rounded-full"
              style={{ backgroundColor: data.payload.color }}
            />
            <span className="font-semibold text-white">{data.payload.severity} Severity</span>
          </div>
          <div className="mt-1 text-slate-300">
            Active Incidents: <span className="font-bold text-white">{data.value}</span>
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="glass-panel rounded-xl p-5 border-slate-800/80 h-full flex flex-col justify-between">
      <div className="flex items-center justify-between mb-2">
        <div>
          <h3 className="text-sm font-semibold text-white tracking-tight">
            Alert Severity Breakdown
          </h3>
          <p className="text-xs text-slate-400">Current active anomaly alerts</p>
        </div>
        <span className="text-xs font-mono text-slate-400">14 Total Active</span>
      </div>

      <div className="h-[200px] w-full">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart
            data={MOCK_ALERT_DISTRIBUTION}
            margin={{ top: 15, right: 10, left: -25, bottom: 0 }}
          >
            <XAxis
              dataKey="severity"
              stroke="#64748B"
              fontSize={11}
              tickLine={false}
              axisLine={{ stroke: '#334155' }}
            />
            <YAxis
              stroke="#64748B"
              fontSize={11}
              allowDecimals={false}
              tickLine={false}
              axisLine={{ stroke: '#334155' }}
            />
            <Tooltip content={<CustomTooltip />} />
            <Bar dataKey="count" radius={[4, 4, 0, 0]}>
              {MOCK_ALERT_DISTRIBUTION.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={entry.color} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>

      <div className="grid grid-cols-4 gap-1.5 pt-3 border-t border-slate-800/80 text-center">
        {MOCK_ALERT_DISTRIBUTION.map((item) => (
          <div key={item.severity} className="p-1.5 rounded bg-slate-900/60 border border-slate-800">
            <span className="block text-[10px] font-mono uppercase text-slate-400">
              {item.severity}
            </span>
            <span
              className="text-base font-bold font-mono"
              style={{ color: item.color }}
            >
              {item.count}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
};
