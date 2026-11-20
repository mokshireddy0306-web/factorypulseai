/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        background: {
          DEFAULT: '#0B1220',
          darker: '#070D18',
          card: '#0F172A',
          cardHover: '#16233B',
          elevated: '#131F37',
        },
        primary: {
          DEFAULT: '#3B82F6',
          hover: '#2563EB',
          light: '#60A5FA',
          glow: 'rgba(59, 130, 246, 0.25)',
        },
        success: {
          DEFAULT: '#22C55E',
          hover: '#16A34A',
          glow: 'rgba(34, 197, 94, 0.25)',
        },
        warning: {
          DEFAULT: '#F59E0B',
          hover: '#D97706',
          glow: 'rgba(245, 158, 11, 0.25)',
        },
        critical: {
          DEFAULT: '#EF4444',
          hover: '#DC2626',
          glow: 'rgba(239, 68, 68, 0.25)',
        },
        border: {
          subtle: 'rgba(59, 130, 246, 0.12)',
          muted: 'rgba(255, 255, 255, 0.08)',
          active: 'rgba(59, 130, 246, 0.4)',
        },
        text: {
          main: '#E5E7EB',
          muted: '#94A3B8',
          faint: '#64748B',
        }
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', '-apple-system', 'sans-serif'],
        mono: ['JetBrains Mono', 'Fira Code', 'Consolas', 'monospace'],
      },
      boxShadow: {
        glass: '0 8px 32px 0 rgba(0, 0, 0, 0.37)',
        'glow-primary': '0 0 20px -2px rgba(59, 130, 246, 0.35)',
        'glow-success': '0 0 20px -2px rgba(34, 197, 94, 0.35)',
        'glow-warning': '0 0 20px -2px rgba(245, 158, 11, 0.35)',
        'glow-critical': '0 0 20px -2px rgba(239, 68, 68, 0.4)',
      },
      animation: {
        'pulse-subtle': 'pulse 3s cubic-bezier(0.4, 0, 0.6, 1) infinite',
        'flow-right': 'flow 1.5s linear infinite',
      },
      keyframes: {
        flow: {
          '0%': { transform: 'translateX(-100%)', opacity: '0' },
          '50%': { opacity: '1' },
          '100%': { transform: 'translateX(100%)', opacity: '0' },
        }
      }
    },
  },
  plugins: [],
}
