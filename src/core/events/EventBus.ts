import type { CoreEventMap } from '../../types/events';

type EventMapBase = object;
type Handler<T> = (payload: T) => void;

/**
 * Typed lightweight event bus for decoupled runtime systems.
 */
export default class EventBus<Events extends EventMapBase = CoreEventMap> {
  private listeners: Map<keyof Events, Set<Handler<Events[keyof Events]>>> = new Map();

  on<K extends keyof Events>(event: K, handler: Handler<Events[K]>): () => void {
    const set = (this.listeners.get(event) as Set<Handler<Events[K]>> | undefined) ?? new Set();
    set.add(handler);
    this.listeners.set(event, set as Set<Handler<Events[keyof Events]>>);
    return () => this.off(event, handler);
  }

  off<K extends keyof Events>(event: K, handler: Handler<Events[K]>): void {
    const set = this.listeners.get(event);
    if (!set) return;
    (set as Set<Handler<Events[K]>>).delete(handler);
    if (set.size === 0) {
      this.listeners.delete(event);
    }
  }

  emit<K extends keyof Events>(event: K, payload: Events[K]): void {
    const set = this.listeners.get(event);
    if (!set) return;
    for (const handler of set as Set<Handler<Events[K]>>) {
      handler(payload);
    }
  }

  clear(): void {
    this.listeners.clear();
  }
}

