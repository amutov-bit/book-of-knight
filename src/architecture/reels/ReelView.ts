import BaseReel from '../../core/BaseReel';
import type { ReelBuildConfig, ReelDisplayLike } from '../../types/reels';
import type BaseGame from '../../core/BaseGame';

export default class ReelView {
  private readonly baseReel: ReelDisplayLike;

  constructor(game: BaseGame, config: ReelBuildConfig) {
    this.baseReel = new BaseReel(game, config.x, config.y, config.width, config.height, config.strip) as unknown as ReelDisplayLike;
  }

  getDisplayObject(): ReelDisplayLike {
    return this.baseReel;
  }

  update(delta: number): void {
    this.baseReel.act(delta);
  }

  onStopped(callback: () => void): void {
    this.baseReel.registerCallback(callback);
  }
}

