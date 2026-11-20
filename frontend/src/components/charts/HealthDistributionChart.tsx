import React from 'react';
import {
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
  Tooltip,
} from 'recharts';
import { MOCK_HEALTH_DISTRIBUTION } from '../../data/mockData';
import { useApp } from '../../context/AppContext';

export const HealthDistributionChart: React.FC = () => {
  const { kpiStats } = useApp();

  const CustomTooltip = ({ active, payload }: any) => {
    if (active && payload && payload.length) {
      const data = payload[0];
      return (
        <div className="bg-slate-900 border border-slate-700/80 p-3 rounded-lg shadow-xl text-xs font-mono">
          <div className="flex items-center gap-2">
            <span
              className="w-2.5 h-2.5 rounded-full"
              style={{ backgroundColor: data.payload.color }}
            />
            <span className="font-semibold text-white">{data.name}</span>
          </div>
          <div className="mt-1 text-slate-300">
            Count: <span className="text-white font-bold">{data.value}</span> machines
          </div>
          <div className="text-slate-400 text-[11px]">
            {((data.value / kpiStats.totalMachines) * 100).toFixed(1)}% of fleet
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="glass-panel rounded-xl p-5 border-slate-800/80 flex flex-col justify-between h-full">
      <div className="flex items-center justify-between mb-2">
        <div>
          <h3 className="text-sm font-semibold text-white tracking-tight">
            Machine Health Distribution
          </h3>
          <p className="text-xs text-slate-400">Fleet status across all production lines</p>
        </div>
        <span className="px-2.5 py-1 rounded-full bg-emerald-500/10 text-emerald-400 border border-emerald-500/20 text-xs font-mono font-medium">
          {kpiStats.factoryHealthScore}% Optimal
        </span>
      </div>

      <div className="relative h-[220px] w-full flex items-center justify-center">
        <ResponsiveContainer width="100%" height="100%">
          <PieChart>
            <Pie
              data={MOCK_HEALTH_DISTRIBUTION}
              cx="50%"
              cy="50%"
              innerRadius={58}
              outerRadius={84}
              paddingAngle={4}
              dataKey="value"
            >
              {MOCK_HEALTH_DISTRIBUTION.map((entry, index) => (
                <Cell
                  key={`cell-${index}`}
                  fill={entry.color}
                  stroke="#0B1220"
                  strokeWidth={2}
                />
              ))}
            </Pie>
            <Tooltip content={<CustomTooltip />} />
          </PieChart>
        </ResponsiveContainer>

        {/* Center score */}
        <div className="absolute inset-0 flex flex-col items-center justify-center pointer-events-none">
          <span className="text-2xl font-bold font-mono text-white tracking-tight">
            {kpiStats.totalMachines}
          </span>
          <span className="text-[11px] text-slate-400 uppercase tracking-wider">
            Total Units
          </span>
        </div>
      </div>

      {/* Legend */}
      <div className="grid grid-cols-2 gap-2 pt-3 border-t border-slate-800/80">
        {MOCK_HEALTH_DISTRIBUTION.map((item) => (
          <div key={item.name} className="flex items-center justify-between text-xs px-2 py-1 rounded bg-slate-900/40">
            <div className="flex items-center gap-1.5">
              <span
                className="w-2 h-2 rounded-full"
                style={{ backgroundColor: item.color }}
              />
              <span className="text-slate-300 font-medium">{item.name}</span>
            </div>
            <span className="font-mono text-slate-400">{item.value}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
