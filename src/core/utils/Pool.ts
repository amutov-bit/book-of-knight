/**
 * Lightweight generic object pool to reduce transient allocations.
 */
export default class Pool<T> {
  private readonly createFn: () => T;
  private readonly resetFn: ((item: T) => void) | null;
  private readonly maxSize: number;
  private free: T[] = [];

  constructor(options: { create: () => T; reset?: (item: T) => void; maxSize?: number }) {
    this.createFn = typeof options.create === 'function' ? options.create : (() => ({} as T));
    this.resetFn = typeof options.reset === 'function' ? options.reset : null;
    this.maxSize = Number.isFinite(options.maxSize) ? Math.max(0, options.maxSize as number) : 256;
  }

  acquire(): T {
    return this.free.length > 0 ? (this.free.pop() as T) : this.createFn();
  }

  release(item: T | null | undefined): void {
    if (item == null) return;
    if (this.resetFn) {
      this.resetFn(item);
    }
    if (this.free.length < this.maxSize) {
      this.free.push(item);
    }
  }

  releaseMany(items: Array<T | null | undefined>): void {
    if (!Array.isArray(items)) return;
    for (let i = 0; i < items.length; i++) {
      this.release(items[i]);
    }
  }

  size(): number {
    return this.free.length;
  }

  clear(): void {
    this.free.length = 0;
  }
}
