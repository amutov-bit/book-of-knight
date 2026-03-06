import * as PIXIType from 'pixi.js';

declare global {
interface Window {
    PIXI: typeof PIXIType;
    Container: typeof PIXIType.Container;
    Sprite: typeof PIXIType.Sprite;
    Application: typeof PIXIType.Application;
    Texture: typeof PIXIType.Texture;
    Graphics: typeof PIXIType.Graphics;
    Text: typeof PIXIType.Text;
    TextureCache: Record<string, PIXIType.Texture>;
    assetsManifest: any;
    log: (text: any, debugLevel?: 'debug' | 'warning' | 'info' | 'error') => void;
  }

  const PIXI: typeof PIXIType;
  const Container: typeof PIXIType.Container;
  const Sprite: typeof PIXIType.Sprite;
  const Application: typeof PIXIType.Application;
  const Texture: typeof PIXIType.Texture;
  const Graphics: typeof PIXIType.Graphics;
  const Text: typeof PIXIType.Text;
  const TextureCache: Record<string, PIXIType.Texture>;
  function log(text: any, debugLevel?: 'debug' | 'warning' | 'info' | 'error'): void;
}

export {};
