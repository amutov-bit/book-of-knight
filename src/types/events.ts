export interface CoreEventMap {
  'lifecycle:changed': { from: string; to: string };
  'spin:requested': undefined;
  'spin:resultReceived': undefined;
  'spin:resolved': undefined;
  'debug:toggle': { visible: boolean };
}
