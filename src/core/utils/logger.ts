import { log as legacyLog } from '../../../log.js';

export type LogLevel = 'debug' | 'warning' | 'info' | 'error';

function normalizeLevel(level: 'debug' | 'warn' | 'info' | 'error'): LogLevel {
  if (level === 'warn') return 'warning';
  return level;
}

function write(level: 'debug' | 'warn' | 'info' | 'error', message: unknown): void {
  const mapped = normalizeLevel(level);
  legacyLog(message, mapped);
}

export function debug(message: unknown): void {
  write('debug', message);
}

export function info(message: unknown): void {
  write('info', message);
}

export function warn(message: unknown): void {
  write('warn', message);
}

export function error(message: unknown): void {
  write('error', message);
}

export default {
  debug,
  info,
  warn,
  error
};

