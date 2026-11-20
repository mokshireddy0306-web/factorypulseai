import React, { useState } from 'react';
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from 'recharts';
import { MOCK_24H_TRENDS } from '../../data/mockData';
import { cn } from '../../lib/utils';

export const TelemetryTrendChart: React.FC = () => {
  const [selectedMetric, setSelectedMetric] = useState<'temp' | 'power' | 'vibration'>('temp');
  const [timeRange, setTimeRange] = useState<'6h' | '24h' | '7d'>('24h');

  const metricConfig = {
    temp: {
      label: 'Average Temperature',
      dataKey: 'avgTemp',
      unit: '°C',
      stroke: '#3B82F6',
      fill: 'rgba(59, 130, 246, 0.2)',
      domain: [50, 100],
    },
    power: {
      label: 'Aggregate Power Draw',
      dataKey: 'totalPowerKw',
      unit: 'kW',
      stroke: '#22C55E',
      fill: 'rgba(34, 197, 94, 0.2)',
      domain: [350, 1000],
    },
    vibration: {
      label: 'Harmonic Vibration',
      dataKey: 'avgVibration',
      unit: 'mm/s',
      stroke: '#F59E0B',
      fill: 'rgba(245, 158, 11, 0.2)',
      domain: [1.0, 7.0],
    },
  };

  const current = metricConfig[selectedMetric];

  const CustomTooltip = ({ active, payload, label }: any) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-slate-900 border border-slate-700/80 p-3 rounded-lg shadow-xl text-xs font-mono">
          <div className="text-slate-400 font-sans mb-1">Time: {label}</div>
          <div className="flex items-center gap-2">
            <span
              className="w-2.5 h-2.5 rounded-full"
              style={{ backgroundColor: current.stroke }}
            />
            <span className="text-slate-300">{current.label}:</span>
            <span className="text-white font-bold">
              {payload[0].value} {current.unit}
            </span>
          </div>
          {payload[0].payload.anomalyEvents > 0 && (
            <div className="mt-1 pt-1 border-t border-slate-800 text-amber-400 text-[11px] flex items-center gap-1">
              <span>⚠ Anomaly spikes detected: {payload[0].payload.anomalyEvents}</span>
            </div>
          )}
        </div>
      );
    }
    return null;
  };

  return (
    <div className="glass-panel rounded-xl p-5 border-slate-800/80 h-full flex flex-col justify-between">
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 mb-4">
        <div>
          <div className="flex items-center gap-2">
            <h3 className="text-sm font-semibold text-white tracking-tight">
              Factory Telemetry Trends
            </h3>
            <span className="px-2 py-0.5 rounded text-[11px] font-mono bg-blue-500/10 text-blue-400 border border-blue-500/20">
              Aggregated Local Edge
            </span>
          </div>
          <p className="text-xs text-slate-400 mt-0.5">
            Continuous real-time telemetry processed on-premise
          </p>
        </div>

        {/* Metric Switcher & Timeframe */}
        <div className="flex items-center gap-2 flex-wrap">
          <div className="flex bg-slate-900 rounded-lg p-0.5 border border-slate-800">
            {(['temp', 'power', 'vibration'] as const).map((metric) => (
              <button
                key={metric}
                onClick={() => setSelectedMetric(metric)}
                className={cn(
                  'px-2.5 py-1 text-xs rounded-md font-medium transition-all capitalize',
                  selectedMetric === metric
                    ? 'bg-blue-600 text-white shadow-sm'
                    : 'text-slate-400 hover:text-slate-200'
                )}
              >
                {metric === 'temp' ? 'Temperature' : metric === 'power' ? 'Power' : 'Vibration'}
              </button>
            ))}
          </div>

          <div className="flex bg-slate-900 rounded-lg p-0.5 border border-slate-800">
            {(['6h', '24h', '7d'] as const).map((t) => (
              <button
                key={t}
                onClick={() => setTimeRange(t)}
                className={cn(
                  'px-2 py-1 text-xs rounded-md font-mono transition-all uppercase',
                  timeRange === t
                    ? 'bg-slate-800 text-white font-bold'
                    : 'text-slate-500 hover:text-slate-300'
                )}
              >
                {t}
              </button>
            ))}
          </div>
        </div>
      </div>

      <div className="h-[250px] w-full">
        <ResponsiveContainer width="100%" height="100%">
          <AreaChart
            data={MOCK_24H_TRENDS}
            margin={{ top: 10, right: 10, left: -20, bottom: 0 }}
          >
            <defs>
              <linearGradient id={`gradient-${selectedMetric}`} x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor={current.stroke} stopOpacity={0.4} />
                <stop offset="95%" stopColor={current.stroke} stopOpacity={0.0} />
              </linearGradient>
            </defs>
            <CartesianGrid strokeDasharray="3 3" stroke="#1E293B" vertical={false} />
            <XAxis
              dataKey="time"
              stroke="#64748B"
              fontSize={11}
              tickLine={false}
              axisLine={{ stroke: '#334155' }}
            />
            <YAxis
              stroke="#64748B"
              fontSize={11}
              domain={current.domain}
              tickLine={false}
              axisLine={{ stroke: '#334155' }}
              tickFormatter={(v) => `${v}`}
            />
            <Tooltip content={<CustomTooltip />} />
            <Area
              type="monotone"
              dataKey={current.dataKey}
              stroke={current.stroke}
              strokeWidth={2}
              fillOpacity={1}
              fill={`url(#gradient-${selectedMetric})`}
            />
          </AreaChart>
        </ResponsiveContainer>
      </div>

      <div className="flex items-center justify-between text-xs text-slate-400 pt-3 border-t border-slate-800/80 mt-2 font-mono">
        <div className="flex items-center gap-4">
          <span>Min: {selectedMetric === 'temp' ? '63.8 °C' : selectedMetric === 'power' ? '410 kW' : '2.0 mm/s'}</span>
          <span>Max: {selectedMetric === 'temp' ? '86.4 °C' : selectedMetric === 'power' ? '905 kW' : '5.5 mm/s'}</span>
          <span>Sampling: 100 Hz Local Edge</span>
        </div>
        <div className="text-emerald-400 flex items-center gap-1">
          <span className="w-1.5 h-1.5 rounded-full bg-emerald-400 animate-pulse" />
          <span>Zero Telemetry Data Leakage</span>
        </div>
      </div>
    </div>
  );
};
