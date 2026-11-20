import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';
import { HealthState, AlertSeverity, ConnectivityStatus } from '../types';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function formatNumber(num: number, digits = 1): string {
  return new Intl.NumberFormat('en-US', {
    maximumFractionDigits: digits,
  }).format(num);
}

export function formatCurrency(amount: number): string {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    maximumFractionDigits: 0,
  }).format(amount);
}

export function getHealthColor(state: HealthState): {
  bg: string;
  text: string;
  border: string;
  glow: string;
  dot: string;
} {
  switch (state) {
    case 'EXCELLENT':
      return {
        bg: 'bg-emerald-500/10',
        text: 'text-emerald-400',
        border: 'border-emerald-500/30',
        glow: 'shadow-[0_0_12px_rgba(34,197,94,0.3)]',
        dot: 'bg-emerald-400',
      };
    case 'GOOD':
      return {
        bg: 'bg-blue-500/10',
        text: 'text-blue-400',
        border: 'border-blue-500/30',
        glow: 'shadow-[0_0_12px_rgba(59,130,246,0.3)]',
        dot: 'bg-blue-400',
      };
    case 'FAIR':
      return {
        bg: 'bg-amber-500/10',
        text: 'text-amber-400',
        border: 'border-amber-500/30',
        glow: 'shadow-[0_0_12px_rgba(245,158,11,0.3)]',
        dot: 'bg-amber-400',
      };
    case 'POOR':
      return {
        bg: 'bg-red-500/10',
        text: 'text-red-400',
        border: 'border-red-500/30',
        glow: 'shadow-[0_0_12px_rgba(239,68,68,0.35)]',
        dot: 'bg-red-400',
      };
  }
}

export function getSeverityColor(severity: AlertSeverity): {
  bg: string;
  text: string;
  border: string;
  badge: string;
} {
  switch (severity) {
    case 'CRITICAL':
      return {
        bg: 'bg-red-500/15',
        text: 'text-red-400',
        border: 'border-red-500/40',
        badge: 'bg-red-500 text-white',
      };
    case 'HIGH':
      return {
        bg: 'bg-orange-500/15',
        text: 'text-orange-400',
        border: 'border-orange-500/40',
        badge: 'bg-orange-500 text-white',
      };
    case 'MEDIUM':
      return {
        bg: 'bg-amber-500/15',
        text: 'text-amber-400',
        border: 'border-amber-500/40',
        badge: 'bg-amber-500 text-slate-900',
      };
    case 'LOW':
      return {
        bg: 'bg-blue-500/15',
        text: 'text-blue-400',
        border: 'border-blue-500/40',
        badge: 'bg-blue-500 text-white',
      };
  }
}

export function getConnectivityColor(status: ConnectivityStatus): {
  text: string;
  bg: string;
  dot: string;
} {
  switch (status) {
    case 'ONLINE':
      return { text: 'text-emerald-400', bg: 'bg-emerald-500/10', dot: 'bg-emerald-400' };
    case 'DEGRADED':
      return { text: 'text-amber-400', bg: 'bg-amber-500/10', dot: 'bg-amber-400' };
    case 'OFFLINE':
      return { text: 'text-red-400', bg: 'bg-red-500/10', dot: 'bg-red-400' };
  }
}
