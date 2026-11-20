import React from 'react';
import { HealthState, AlertSeverity, ConnectivityStatus } from '../../types';
import { getHealthColor, getSeverityColor, getConnectivityColor, cn } from '../../lib/utils';

interface HealthBadgeProps {
  state: HealthState;
  showDot?: boolean;
  className?: string;
  size?: 'sm' | 'md' | 'lg';
}

export const HealthBadge: React.FC<HealthBadgeProps> = ({
  state,
  showDot = true,
  className,
  size = 'md',
}) => {
  const colors = getHealthColor(state);
  const sizeClasses = {
    sm: 'text-xs px-2 py-0.5',
    md: 'text-xs px-2.5 py-1 font-medium',
    lg: 'text-sm px-3 py-1.5 font-semibold',
  };

  return (
    <span
      className={cn(
        'inline-flex items-center gap-1.5 rounded-full border tracking-wide uppercase',
        colors.bg,
        colors.text,
        colors.border,
        sizeClasses[size],
        className
      )}
    >
      {showDot && (
        <span className={cn('h-1.5 w-1.5 rounded-full animate-pulse', colors.dot)} />
      )}
      {state}
    </span>
  );
};

interface SeverityBadgeProps {
  severity: AlertSeverity;
  className?: string;
  size?: 'sm' | 'md';
}

export const SeverityBadge: React.FC<SeverityBadgeProps> = ({
  severity,
  className,
  size = 'md',
}) => {
  const colors = getSeverityColor(severity);
  const sizeClasses = {
    sm: 'text-[10px] px-1.5 py-0.5 font-bold',
    md: 'text-xs px-2.5 py-0.5 font-bold tracking-wider',
  };

  return (
    <span
      className={cn(
        'inline-flex items-center rounded border uppercase font-mono',
        colors.bg,
        colors.text,
        colors.border,
        sizeClasses[size],
        className
      )}
    >
      {severity}
    </span>
  );
};

interface ConnectivityBadgeProps {
  status: ConnectivityStatus;
  className?: string;
}

export const ConnectivityBadge: React.FC<ConnectivityBadgeProps> = ({
  status,
  className,
}) => {
  const colors = getConnectivityColor(status);
  return (
    <span
      className={cn(
        'inline-flex items-center gap-1.5 text-xs px-2 py-0.5 rounded-full border border-slate-700/60 font-mono',
        colors.bg,
        colors.text,
        className
      )}
    >
      <span className={cn('h-1.5 w-1.5 rounded-full', colors.dot)} />
      {status}
    </span>
  );
};
