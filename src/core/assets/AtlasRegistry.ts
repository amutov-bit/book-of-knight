import type { Texture } from 'pixi.js';

/**
 * Registry that maps atlas/frame names to textures.
 */
export default class AtlasRegistry {
  private frameToTexture: Map<string, Texture> = new Map();
  private atlasFrames: Map<string, string[]> = new Map();

  registerAtlas(atlasKey: string, textures: Record<string, Texture>): void {
    if (!textures || typeof textures !== 'object') return;
    const frames = Object.keys(textures);
    this.atlasFrames.set(atlasKey, frames);
    for (let i = 0; i < frames.length; i++) {
      const frame = frames[i];
      this.frameToTexture.set(frame, textures[frame]);
    }
  }

  get(frame: string): Texture | null {
    return this.frameToTexture.get(frame) || null;
  }

  getFrames(atlasKey: string): string[] {
    return this.atlasFrames.get(atlasKey) || [];
  }

  clear(): void {
    this.frameToTexture.clear();
    this.atlasFrames.clear();
  }

  getFrameCount(): number {
    return this.frameToTexture.size;
  }
}
