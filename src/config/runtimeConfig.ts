import type { Variant } from '../types/domain';

/**
 * Resolves the active variant from Vite env first, then the legacy global fallback.
 *
 * Defaults to `desktop` so local development still starts when no variant is injected.
 */
export function getRuntimeVariant(): Variant {
  const fromVite = import.meta.env?.VITE_VARIANT;
  if (fromVite === 'desktop' || fromVite === 'mobile') {
    return fromVite;
  }

  if (typeof __VARIANT__ !== 'undefined' && (__VARIANT__ === 'desktop' || __VARIANT__ === 'mobile')) {
    return __VARIANT__;
  }

  return 'desktop';
}

/**
 * Convenience helper for variant-guarded layout and asset decisions.
 */
export function isMobileVariant(variant: Variant): boolean {
  return variant === 'mobile';
}
