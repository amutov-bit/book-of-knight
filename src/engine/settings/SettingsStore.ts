/**
 * In-memory settings storage.
 */
export default class SettingsStore {
  private data: Record<string, unknown>;

  constructor(initial: Record<string, unknown> = {}) {
    this.data = { ...initial };
  }

  get<T = unknown>(key: string, fallback: T | null = null): T | null {
    return Object.prototype.hasOwnProperty.call(this.data, key) ? (this.data[key] as T) : fallback;
  }

  set(key: string, value: unknown): void {
    this.data[key] = value;
  }

  merge(patch: Record<string, unknown> | null | undefined): void {
    if (!patch || typeof patch !== 'object') return;
    Object.assign(this.data, patch);
  }
}
