import React, { useState } from 'react';
import { AppProvider, useApp } from './context/AppContext';
import { Sidebar } from './components/layout/Sidebar';
import { TopNavbar } from './components/layout/TopNavbar';
import { NotificationDrawer } from './components/layout/NotificationDrawer';
import { DashboardPage } from './pages/DashboardPage';
import { MachineMonitoringPage } from './pages/MachineMonitoringPage';
import { AlertIncidentCenterPage } from './pages/AlertIncidentCenterPage';
import { AIOpsRecommendationPage } from './pages/AIOpsRecommendationPage';
import { EgressProofPanelPage } from './pages/EgressProofPanelPage';
import { ArchitectureVisualizationPage } from './pages/ArchitectureVisualizationPage';
import { AboutProjectPage } from './pages/AboutProjectPage';
import { X, CheckCircle, AlertTriangle, Info, AlertOctagon } from 'lucide-react';
import { cn } from './lib/utils';

const ToastContainer: React.FC = () => {
  const { toasts, removeToast } = useApp();

  return (
    <div className="fixed bottom-5 right-5 z-50 flex flex-col gap-2 max-w-sm pointer-events-none">
      {toasts.map((toast) => (
        <div
          key={toast.id}
          className={cn(
            'pointer-events-auto p-4 rounded-xl border shadow-2xl backdrop-blur-md flex items-start gap-3 transition-all animate-in slide-in-from-bottom-5 duration-200',
            toast.type === 'success' && 'bg-slate-900/95 border-emerald-500/40 text-emerald-300',
            toast.type === 'warning' && 'bg-slate-900/95 border-amber-500/40 text-amber-300',
            toast.type === 'error' && 'bg-slate-900/95 border-red-500/40 text-red-300',
            toast.type === 'info' && 'bg-slate-900/95 border-blue-500/40 text-blue-300'
          )}
        >
          <div className="pt-0.5 shrink-0">
            {toast.type === 'success' && <CheckCircle className="w-5 h-5 text-emerald-400" />}
            {toast.type === 'warning' && <AlertTriangle className="w-5 h-5 text-amber-400" />}
            {toast.type === 'error' && <AlertOctagon className="w-5 h-5 text-red-400" />}
            {toast.type === 'info' && <Info className="w-5 h-5 text-blue-400" />}
          </div>

          <div className="flex-1 min-w-0">
            <h4 className="text-xs font-bold text-white tracking-tight">{toast.title}</h4>
            <p className="text-[11px] text-slate-300 mt-0.5 leading-relaxed font-sans">
              {toast.message}
            </p>
          </div>

          <button
            onClick={() => removeToast(toast.id)}
            className="text-slate-400 hover:text-white shrink-0 p-1"
          >
            <X className="w-3.5 h-3.5" />
          </button>
        </div>
      ))}
    </div>
  );
};

const MainContent: React.FC = () => {
  const { activePage } = useApp();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const renderPage = () => {
    switch (activePage) {
      case 'dashboard':
        return <DashboardPage />;
      case 'machines':
        return <MachineMonitoringPage />;
      case 'alerts':
        return <AlertIncidentCenterPage />;
      case 'aiops':
        return <AIOpsRecommendationPage />;
      case 'egress':
        return <EgressProofPanelPage />;
      case 'architecture':
        return <ArchitectureVisualizationPage />;
      case 'about':
        return <AboutProjectPage />;
      default:
        return <DashboardPage />;
    }
  };

  return (
    <div className="flex h-screen w-screen overflow-hidden bg-[#0B1220]">
      {/* Desktop Sidebar */}
      <div className="hidden lg:block shrink-0">
        <Sidebar />
      </div>

      {/* Mobile Sidebar Overlay */}
      {mobileMenuOpen && (
        <div className="fixed inset-0 z-50 flex lg:hidden">
          <div
            className="fixed inset-0 bg-slate-950/80 backdrop-blur-sm"
            onClick={() => setMobileMenuOpen(false)}
          />
          <div className="relative z-10 w-64 bg-[#070D18] h-full shadow-2xl flex flex-col">
            <Sidebar />
          </div>
        </div>
      )}

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col h-screen overflow-hidden min-w-0">
        <TopNavbar onToggleMobileMenu={() => setMobileMenuOpen(true)} />

        <main className="flex-1 overflow-y-auto px-4 sm:px-6 lg:px-8 py-6">
          {renderPage()}
        </main>
      </div>

      <NotificationDrawer />
      <ToastContainer />
    </div>
  );
};

export default function App() {
  return (
    <AppProvider>
      <MainContent />
    </AppProvider>
  );
}
