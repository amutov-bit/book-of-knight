/**
 * URL-controlled flags that affect app shell behavior without changing gameplay rules.
 */
export interface AppRuntimeConfig {
  /**
   * Enables the overlay toggled with the backquote key.
   *
   * Query param: `?debugOverlay=1`
   */
  debugOverlayEnabled: boolean;
}

/**
 * Reads app-shell flags from the current URL.
 */
export function getAppRuntimeConfig(): AppRuntimeConfig {
  const params = new URLSearchParams(window.location.search || '');
  return {
    debugOverlayEnabled: params.get('debugOverlay') === '1'
  };
}

export default getAppRuntimeConfig;
