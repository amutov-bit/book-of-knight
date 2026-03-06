/**
 * Small RAF ticker abstraction used by the app shell.
 */

export interface AppTicker {
  start(): void;
  stop(): void;
  isRunning(): boolean;
}

/**
 * Creates a frame loop that clamps long frame gaps to avoid giant simulation steps.
 */
export function createTicker(onTick: (dtMs: number, nowMs: number) => void): AppTicker {
  let running = false;
  let rafId = 0;
  let lastMs = 0;

  const tick = (nowMs: number): void => {
    if (!running) return;
    const dtMs = Math.max(0, Math.min(100, nowMs - lastMs));
    lastMs = nowMs;
    onTick(dtMs, nowMs);
    rafId = requestAnimationFrame(tick);
  };

  return {
    start() {
      if (running) return;
      running = true;
      lastMs = performance.now();
      rafId = requestAnimationFrame(tick);
    },
    stop() {
      running = false;
      if (rafId) {
        cancelAnimationFrame(rafId);
        rafId = 0;
      }
    },
    isRunning() {
      return running;
    }
  };
}

export default createTicker;
