import type { Texture } from 'pixi.js';
import type AssetManager from './assets/AssetManager';
import type AtlasRegistry from './assets/AtlasRegistry';

type RuntimeShape = (Window & typeof globalThis) & {
  assetsManifest?: unknown;
  TextureCache?: Record<string, Texture>;
  assetManager?: AssetManager;
};

function getGlobalRuntime(): RuntimeShape {
  return (typeof window !== 'undefined' ? window : globalThis) as RuntimeShape;
}

/**
 * Returns the manifest loaded during boot. Config readers should prefer this over direct globals.
 */
export function getAssetsManifest<T = unknown>(): T | null {
  return (getGlobalRuntime().assetsManifest as T | undefined) ?? null;
}

/**
 * Persists the resolved assets manifest on the browser runtime object.
 */
export function setAssetsManifest(manifest: unknown): void {
  getGlobalRuntime().assetsManifest = manifest;
}

/**
 * Shared mutable cache for frame-name to texture lookups.
 */
export function getTextureCache(): Record<string, Texture> {
  const runtime = getGlobalRuntime();
  if (!runtime.TextureCache) {
    runtime.TextureCache = {};
  }
  return runtime.TextureCache;
}

/**
 * Used by layout readers that need to choose portrait vs landscape branches.
 */
export function getIsLandscape(): boolean {
  const runtime = getGlobalRuntime();
  return runtime.innerWidth >= runtime.innerHeight;
}

/**
 * Returns the active asset manager when one has already been attached during boot.
 */
export function getAssetManager(): AssetManager | null {
  return getGlobalRuntime().assetManager ?? null;
}

/**
 * Stores the active asset manager for runtime-wide access.
 */
export function setAssetManager(assetManager: AssetManager): void {
  getGlobalRuntime().assetManager = assetManager;
}

/**
 * Convenience accessor for systems that only need atlas lookups.
 */
export function getAtlasRegistry(): AtlasRegistry | null {
  const manager = getAssetManager();
  return manager && typeof manager.getAtlasRegistry === 'function'
    ? manager.getAtlasRegistry()
    : null;
}
