import { error } from '../utils/logger';

interface TimerTask {
  remaining: number;
  repeat: boolean;
  interval: number;
  callback: () => void;
}

/**
 * Frame-driven timers to avoid scattered setTimeout / setInterval usage.
 */
export default class Timers {
  private nextId = 1;
  private tasks: Map<number, TimerTask> = new Map();

  after(delayMs: number, callback: () => void): number {
    const id = this.nextId++;
    this.tasks.set(id, {
      remaining: Math.max(0, Number(delayMs) || 0),
      repeat: false,
      interval: 0,
      callback
    });
    return id;
  }

  every(intervalMs: number, callback: () => void): number {
    const interval = Math.max(1, Number(intervalMs) || 1);
    const id = this.nextId++;
    this.tasks.set(id, {
      remaining: interval,
      repeat: true,
      interval,
      callback
    });
    return id;
  }

  cancel(id: number): void {
    this.tasks.delete(id);
  }

  update(dtMs: number): void {
    const delta = Math.max(0, Number(dtMs) || 0);
    if (delta <= 0 || this.tasks.size === 0) return;

    for (const [id, task] of this.tasks) {
      task.remaining -= delta;
      if (task.remaining > 0) continue;

      try {
        task.callback();
      } catch (err) {
        const message = err instanceof Error ? err.message : String(err);
        error(`Timers::callbackError ${message}`);
      }

      if (!task.repeat) {
        this.tasks.delete(id);
        continue;
      }

      while (task.remaining <= 0) {
        task.remaining += task.interval;
      }
    }
  }

  clear(): void {
    this.tasks.clear();
  }
}
