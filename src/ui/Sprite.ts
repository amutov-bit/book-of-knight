import { Sprite as PixiSprite, type Texture } from 'pixi.js';

export default class Sprite extends PixiSprite {
  constructor(texture: Texture) {
    super(texture);
  }

  act(_delta: number): void {
    // sprite hook for screen update loop
  }
}

