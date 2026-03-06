'use strict';

import type { Container as PixiContainer, Texture } from 'pixi.js';
import Sprite from '../ui/Sprite';

interface SymbolOffset {
  x: number;
  y: number;
}

interface TexturesLike {
  regions: Array<Array<Texture>>;
  symbolOffsets?: SymbolOffset[];
}

interface ReelGameLike {
  textures: TexturesLike;
}

export default class ReelSymbol extends Sprite {
  private readonly game: ReelGameLike;
  private readonly reel: unknown;
  private anim: boolean;
  isWinning: boolean;
  private highlight: boolean;
  private index: number;
  private blurIndex: number;
  private logicalX: number;
  private logicalY: number;
  private offsetX: number;
  private offsetY: number;
  looping: boolean;
  isLong: boolean;
  animationFrameCnt: number;
  delayAnimation: number;
  totalFrames: number;

  constructor(game: ReelGameLike, reel: unknown, index: number) {
    super(game.textures.regions[index][0]);
    this.game = game;
    this.reel = reel;
    this.anim = false;
    this.isWinning = false;
    this.highlight = false;
    this.index = index;
    this.blurIndex = 0;
    this.logicalX = 0;
    this.logicalY = 0;
    this.offsetX = 0;
    this.offsetY = 0;
    this.looping = false;
    this.isLong = false;
    this.animationFrameCnt = 0;
    this.delayAnimation = 0;
    this.totalFrames = 0;

    this.anchor.set(0.5);
    this.blendMode = 'normal' as never;
    this.applySymbolOffset(this.index);
  }

  setIndex(index: number): void {
    if (this.index === index) {
      return;
    }
    const prevLogicalX = this.logicalX;
    const prevLogicalY = this.logicalY;
    this.index = index;
    this.texture = this.game.textures.regions[this.index][this.blurIndex];
    this.applySymbolOffset(this.index);
    this.logicalX = prevLogicalX;
    this.logicalY = prevLogicalY;
    this.applyVisualPosition();
  }

  setPosition(x: number, y: number): void {
    this.logicalX = x;
    this.logicalY = y;
    this.applyVisualPosition();
  }

  setLogicalY(y: number): void {
    this.logicalY = y;
    this.applyVisualPosition();
  }

  getLogicalY(): number {
    return this.logicalY;
  }

  private applySymbolOffset(index: number): void {
    const offsets = this.game?.textures?.symbolOffsets;
    const offset = Array.isArray(offsets) ? offsets[index] : null;
    this.offsetX = offset && Number.isFinite(offset.x) ? offset.x : 0;
    this.offsetY = offset && Number.isFinite(offset.y) ? offset.y : 0;
  }

  private applyVisualPosition(): void {
    this.x = this.logicalX + this.offsetX;
    this.y = this.logicalY + this.offsetY;
  }

  animate(animate: boolean, looping: boolean, isLong: boolean): void {
    this.anim = animate;
    this.looping = looping;
    this.isLong = isLong;
    this.animationFrameCnt = 0;
    this.delayAnimation = 0;
    this.totalFrames = 0;
  }

  setBlurFrame(index: number): void {
    const frames = this.game.textures.regions[this.index] || [];
    if (frames.length === 0) return;
    const safeIndex = Math.max(0, Math.min(index, frames.length - 1));
    if (this.blurIndex === safeIndex) return;
    this.blurIndex = safeIndex;
    this.texture = frames[this.blurIndex];
  }

  getIndex(): number {
    return this.index;
  }

  bringToFront(): void {
    if (!this.parent) return;
    const parent = this.parent as PixiContainer;
    const idx = parent.children.indexOf(this);
    if (idx < 0) return;
    parent.children.splice(idx, 1);
    parent.children.push(this);
  }
}
