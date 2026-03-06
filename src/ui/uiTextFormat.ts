import { formatCentsByPattern, getDefaultNumberPattern } from '../utils/numberFormat';

/**
 * Small UI-facing formatter helpers shared by menu overlays.
 * Keeps per-screen classes focused on layout/interaction.
 */
export function getLocalizedText(game: any, key: string, fallback: string): string {
  if (game && game.localization && typeof game.localization.t === 'function') {
    return String(game.localization.t(key, {}, { defaultValue: fallback }));
  }
  return fallback;
}

export function getRuntimeNumberPattern(game: any): string {
  if (!game || !game.gsLink || typeof game.gsLink.getNumberPattern !== 'function') {
    return getDefaultNumberPattern();
  }

  const pattern = game.gsLink.getNumberPattern();
  return typeof pattern === 'string' && pattern.trim().length > 0
    ? pattern
    : getDefaultNumberPattern();
}

export function formatMoneyByGame(value: number, game: any): string {
  return formatCentsByPattern(value, getRuntimeNumberPattern(game));
}

export function getGameCurrency(game: any): string {
  const currency = game && game.gsLink && typeof game.gsLink.currency === 'string'
    ? game.gsLink.currency.trim()
    : '';
  return currency || 'FUN';
}
