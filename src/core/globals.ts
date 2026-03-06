import * as PIXI from 'pixi.js';
import { log as importedLog } from '../../log.js';

declare global {
  interface Window {
    PIXI?: typeof PIXI;
    Container?: typeof PIXI.Container;
    Sprite?: typeof PIXI.Sprite;
    Application?: typeof PIXI.Application;
    Texture?: typeof PIXI.Texture;
    Graphics?: typeof PIXI.Graphics;
    Text?: typeof PIXI.Text;
    TextureCache?: Record<string, PIXI.Texture>;
    log?: (message: unknown, level?: string) => void;
  }
}

const runtime = window;

runtime.PIXI = runtime.PIXI || PIXI;
runtime.Container = runtime.PIXI.Container;
runtime.Sprite = runtime.PIXI.Sprite;
runtime.Application = runtime.PIXI.Application;
runtime.Texture = runtime.PIXI.Texture;
runtime.Graphics = runtime.PIXI.Graphics;
(runtime as any).Text = runtime.PIXI.Text;
runtime.TextureCache = runtime.TextureCache || {};
runtime.log = importedLog;

export {};

