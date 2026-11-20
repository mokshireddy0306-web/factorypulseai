import React from 'react';
import { cn } from '../../lib/utils';
import { LucideIcon } from 'lucide-react';

interface MetricCardProps {
  title: string;
  value: string | number;
  unit?: string;
  change?: string;
  isPositive?: boolean;
  subtitle?: string;
  icon: LucideIcon;
  variant?: 'default' | 'primary' | 'success' | 'warning' | 'critical';
  badge?: string;
  className?: string;
}

export const MetricCard: React.FC<MetricCardProps> = ({
  title,
  value,
  unit,
  change,
  isPositive,
  subtitle,
  icon: Icon,
  variant = 'default',
  badge,
  className,
}) => {
  const variantStyles = {
    default: {
      border: 'border-slate-800/80 hover:border-slate-700',
      iconBg: 'bg-slate-800/80 text-slate-300',
      glow: '',
    },
    primary: {
      border: 'border-blue-500/20 hover:border-blue-500/40',
      iconBg: 'bg-blue-500/10 text-blue-400',
      glow: 'hover:shadow-[0_0_20px_rgba(59,130,246,0.15)]',
    },
    success: {
      border: 'border-emerald-500/20 hover:border-emerald-500/40',
      iconBg: 'bg-emerald-500/10 text-emerald-400',
      glow: 'hover:shadow-[0_0_20px_rgba(34,197,94,0.15)]',
    },
    warning: {
      border: 'border-amber-500/20 hover:border-amber-500/40',
      iconBg: 'bg-amber-500/10 text-amber-400',
      glow: 'hover:shadow-[0_0_20px_rgba(245,158,11,0.15)]',
    },
    critical: {
      border: 'border-red-500/20 hover:border-red-500/40',
      iconBg: 'bg-red-500/10 text-red-400',
      glow: 'hover:shadow-[0_0_20px_rgba(239,68,68,0.2)]',
    },
  };

  const style = variantStyles[variant];

  return (
    <div
      className={cn(
        'glass-panel rounded-xl p-5 transition-all duration-300 relative overflow-hidden group',
        style.border,
        style.glow,
        className
      )}
    >
      {/* Subtle background ambient light */}
      <div className="absolute -right-6 -top-6 w-24 h-24 bg-blue-500/5 rounded-full blur-2xl pointer-events-none group-hover:bg-blue-500/10 transition-all duration-500" />

      <div className="flex items-start justify-between">
        <div>
          <p className="text-xs font-medium text-slate-400 uppercase tracking-wider">
            {title}
          </p>
          <div className="flex items-baseline gap-1.5 mt-2">
            <span className="text-3xl font-bold tracking-tight text-white font-mono">
              {value}
            </span>
            {unit && <span className="text-sm font-medium text-slate-400">{unit}</span>}
          </div>
        </div>

        <div className={cn('p-2.5 rounded-lg border border-slate-700/40', style.iconBg)}>
          <Icon className="w-5 h-5" />
        </div>
      </div>

      <div className="mt-3.5 flex items-center justify-between text-xs">
        {change && (
          <span
            className={cn(
              'inline-flex items-center gap-1 font-medium',
              isPositive ? 'text-emerald-400' : 'text-red-400'
            )}
          >
            {isPositive ? '↑' : '↓'} {change}
          </span>
        )}
        {subtitle && <span className="text-slate-400 ml-auto">{subtitle}</span>}
        {badge && (
          <span className="ml-auto px-2 py-0.5 rounded bg-blue-500/10 text-blue-400 border border-blue-500/20 text-[11px] font-mono">
            {badge}
          </span>
        )}
      </div>
    </div>
  );
};
