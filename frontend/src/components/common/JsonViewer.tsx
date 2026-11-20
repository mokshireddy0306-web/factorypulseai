import React, { useState } from 'react';
import { Copy, Check, ShieldAlert, ShieldCheck } from 'lucide-react';
import { cn } from '../../lib/utils';

interface JsonViewerProps {
  data: Record<string, any>;
  title?: string;
  subtitle?: string;
  highlightRedacted?: boolean;
  redactedKeys?: string[];
  isMetadataApproved?: boolean;
  className?: string;
}

export const JsonViewer: React.FC<JsonViewerProps> = ({
  data,
  title,
  subtitle,
  highlightRedacted = false,
  redactedKeys = [],
  isMetadataApproved = false,
  className,
}) => {
  const [copied, setCopied] = useState(false);

  const rawString = JSON.stringify(data, null, 2);
  const byteSize = new Blob([rawString]).size;

  const handleCopy = () => {
    navigator.clipboard.writeText(rawString);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const lines = rawString.split('\n');

  return (
    <div
      className={cn(
        'rounded-xl border flex flex-col font-mono text-xs overflow-hidden transition-all',
        isMetadataApproved
          ? 'border-emerald-500/30 bg-slate-950/80 shadow-[0_0_25px_rgba(34,197,94,0.08)]'
          : 'border-red-500/30 bg-slate-950/80 shadow-[0_0_25px_rgba(239,68,68,0.08)]',
        className
      )}
    >
      {/* Header bar */}
      <div
        className={cn(
          'flex items-center justify-between px-4 py-3 border-b text-xs select-none',
          isMetadataApproved
            ? 'bg-emerald-950/20 border-emerald-500/20 text-emerald-300'
            : 'bg-red-950/20 border-red-500/20 text-red-300'
        )}
      >
        <div className="flex items-center gap-2">
          {isMetadataApproved ? (
            <ShieldCheck className="w-4 h-4 text-emerald-400" />
          ) : (
            <ShieldAlert className="w-4 h-4 text-red-400" />
          )}
          <div>
            <span className="font-semibold uppercase tracking-wider">{title}</span>
            {subtitle && (
              <span className="ml-2 text-[11px] text-slate-400 font-sans">
                {subtitle}
              </span>
            )}
          </div>
        </div>

        <div className="flex items-center gap-3">
          <span className="px-2 py-0.5 rounded bg-slate-900 border border-slate-700/60 text-slate-300 text-[10px]">
            {byteSize} Bytes
          </span>
          <button
            onClick={handleCopy}
            className="flex items-center gap-1 px-2.5 py-1 rounded bg-slate-800/80 hover:bg-slate-700 text-slate-300 hover:text-white transition-colors border border-slate-700/50"
            title="Copy JSON to clipboard"
          >
            {copied ? (
              <>
                <Check className="w-3.5 h-3.5 text-emerald-400" />
                <span className="text-emerald-400 font-sans">Copied</span>
              </>
            ) : (
              <>
                <Copy className="w-3.5 h-3.5" />
                <span className="font-sans">Copy</span>
              </>
            )}
          </button>
        </div>
      </div>

      {/* Code body */}
      <div className="p-4 overflow-x-auto max-h-[460px] overflow-y-auto space-y-1 font-mono leading-relaxed select-text">
        {lines.map((line, idx) => {
          // Check if this line contains any redacted key
          const isRedactedLine =
            highlightRedacted &&
            redactedKeys.some((key) => line.includes(`"${key}":`));

          return (
            <div
              key={idx}
              className={cn(
                'flex items-start group py-0.5 px-2 -mx-2 rounded transition-colors',
                isRedactedLine
                  ? 'bg-red-500/15 border-l-2 border-red-500'
                  : 'hover:bg-slate-900/60'
              )}
            >
              {/* Line number */}
              <span className="w-7 text-right pr-3 select-none text-slate-600 group-hover:text-slate-400 text-[11px]">
                {idx + 1}
              </span>

              {/* Line content */}
              <div className="flex-1 whitespace-pre flex items-center justify-between">
                <span>
                  {formatJsonLine(line, isRedactedLine, isMetadataApproved)}
                </span>
                {isRedactedLine && (
                  <span className="ml-3 select-none inline-flex items-center gap-1 text-[10px] font-sans px-2 py-0.5 rounded bg-red-900/60 text-red-300 border border-red-500/40 font-semibold tracking-wide uppercase">
                    Blocked On-Prem
                  </span>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

function formatJsonLine(line: string, isRedacted: boolean, isApproved: boolean) {
  // Simple regex-based syntax highlight for JSON lines
  const keyMatch = line.match(/^(\s*)("([^"]+)")(\s*:\s*)(.*)$/);
  if (!keyMatch) {
    return <span className="text-slate-400">{line}</span>;
  }

  const [, indent, , key, colon, value] = keyMatch;

  let valueElement: React.ReactNode = value;

  if (value.startsWith('"') && value.endsWith('",?')) {
    valueElement = (
      <span className={isRedacted ? 'text-red-300 font-semibold line-through decoration-red-400' : 'text-emerald-300'}>
        {value}
      </span>
    );
  } else if (/^-?\d+(\.\d+)?([eE][+-]?\d+)?,?$/.test(value.trim())) {
    valueElement = <span className="text-amber-300">{value}</span>;
  } else if (/^(true|false),?$/.test(value.trim())) {
    valueElement = <span className="text-purple-400 font-bold">{value}</span>;
  } else if (/^null,?$/.test(value.trim())) {
    valueElement = <span className="text-slate-500 italic">{value}</span>;
  }

  return (
    <>
      <span>{indent}</span>
      <span
        className={cn(
          'font-semibold',
          isRedacted ? 'text-red-400' : isApproved ? 'text-blue-300' : 'text-slate-300'
        )}
      >
        "{key}"
      </span>
      <span className="text-slate-500">{colon}</span>
      {valueElement}
    </>
  );
}
