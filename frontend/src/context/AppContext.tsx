import React, { createContext, useContext, useState, useEffect } from 'react';
import { Machine, Alert, KPIStats } from '../types';
import { MOCK_MACHINES, MOCK_ALERTS, MOCK_KPI_STATS } from '../data/mockData';

export type PageId =
  | 'dashboard'
  | 'machines'
  | 'alerts'
  | 'aiops'
  | 'egress'
  | 'architecture'
  | 'about';

interface ToastNotification {
  id: string;
  type: 'success' | 'warning' | 'info' | 'error';
  title: string;
  message: string;
}

interface AppContextType {
  activePage: PageId;
  setActivePage: (page: PageId) => void;
  machines: Machine[];
  alerts: Alert[];
  kpiStats: KPIStats;
  selectedMachine: Machine | null;
  setSelectedMachine: (machine: Machine | null) => void;
  isStreaming: boolean;
  toggleStreaming: () => void;
  searchQuery: string;
  setSearchQuery: (query: string) => void;
  isNotificationOpen: boolean;
  setIsNotificationOpen: (open: boolean) => void;
  acknowledgeAlert: (alertId: string) => void;
  resolveAlert: (alertId: string) => void;
  activeFacility: string;
  setActiveFacility: (facility: string) => void;
  toasts: ToastNotification[];
  removeToast: (id: string) => void;
  addToast: (toast: Omit<ToastNotification, 'id'>) => void;
}

const AppContext = createContext<AppContextType | undefined>(undefined);

export const AppProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [activePage, setActivePage] = useState<PageId>('dashboard');
  const [machines, setMachines] = useState<Machine[]>(MOCK_MACHINES);
  const [alerts, setAlerts] = useState<Alert[]>(MOCK_ALERTS);
  const [kpiStats, setKpiStats] = useState<KPIStats>(MOCK_KPI_STATS);
  const [selectedMachine, setSelectedMachine] = useState<Machine | null>(null);
  const [isStreaming, setIsStreaming] = useState<boolean>(true);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [isNotificationOpen, setIsNotificationOpen] = useState<boolean>(false);
  const [activeFacility, setActiveFacility] = useState<string>('Plant Alpha - Detroit Gigafactory');
  const [toasts, setToasts] = useState<ToastNotification[]>([]);

  const addToast = (toast: Omit<ToastNotification, 'id'>) => {
    const id = Math.random().toString(36).substring(2, 9);
    setToasts((prev) => [...prev, { ...toast, id }]);
    setTimeout(() => {
      removeToast(id);
    }, 4500);
  };

  const removeToast = (id: string) => {
    setToasts((prev) => prev.filter((t) => t.id !== id));
  };

  const toggleStreaming = () => {
    setIsStreaming((prev) => {
      const next = !prev;
      addToast({
        type: next ? 'success' : 'warning',
        title: next ? 'Real-Time Telemetry Resumed' : 'Telemetry Stream Paused',
        message: next
          ? 'Live 100Hz local collector metadata pipeline is actively pulsing.'
          : 'Local buffer holding frames; cloud metadata transmission paused.',
      });
      return next;
    });
  };

  const acknowledgeAlert = (alertId: string) => {
    setAlerts((prev) =>
      prev.map((alert) =>
        alert.id === alertId
          ? { ...alert, status: 'ACKNOWLEDGED', acknowledgedBy: 'Current Operator (You)' }
          : alert
      )
    );
    addToast({
      type: 'info',
      title: 'Incident Acknowledged',
      message: `Alert ${alertId} assigned to your shift log. Local collector logging event.`,
    });
  };

  const resolveAlert = (alertId: string) => {
    setAlerts((prev) =>
      prev.map((alert) =>
        alert.id === alertId
          ? {
              ...alert,
              status: 'RESOLVED',
              resolvedAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            }
          : alert
      )
    );
    setKpiStats((prev) => ({
      ...prev,
      criticalIncidents: Math.max(0, prev.criticalIncidents - 1),
      alertsCount: Math.max(0, prev.alertsCount - 1),
      factoryHealthScore: Math.min(99.4, prev.factoryHealthScore + 1.2),
    }));
    addToast({
      type: 'success',
      title: 'Incident Resolved & Closed',
      message: `Alert ${alertId} marked resolved. Factory health index updated.`,
    });
  };

  // Simulated live telemetry fluctuation when streaming is on
  useEffect(() => {
    if (!isStreaming) return;
    const interval = setInterval(() => {
      setMachines((prev) =>
        prev.map((machine) => {
          // Slight realistic jitter
          const tempJitter = (Math.random() - 0.48) * 0.4;
          const powerJitter = (Math.random() - 0.48) * 0.3;
          const vibJitter = (Math.random() - 0.48) * 0.08;
          return {
            ...machine,
            temperature: Number((machine.temperature + tempJitter).toFixed(1)),
            powerUsage: Number((machine.powerUsage + powerJitter).toFixed(1)),
            vibration: Number(Math.max(0.8, machine.vibration + vibJitter).toFixed(2)),
          };
        })
      );
    }, 2500);

    return () => clearInterval(interval);
  }, [isStreaming]);

  return (
    <AppContext.Provider
      value={{
        activePage,
        setActivePage,
        machines,
        alerts,
        kpiStats,
        selectedMachine,
        setSelectedMachine,
        isStreaming,
        toggleStreaming,
        searchQuery,
        setSearchQuery,
        isNotificationOpen,
        setIsNotificationOpen,
        acknowledgeAlert,
        resolveAlert,
        activeFacility,
        setActiveFacility,
        toasts,
        removeToast,
        addToast,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};

export const useApp = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useApp must be used within an AppProvider');
  }
  return context;
};
