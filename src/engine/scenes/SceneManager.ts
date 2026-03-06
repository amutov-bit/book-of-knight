interface ScreenHost {
  setScreen?: (scene: unknown) => void;
}

/**
 * Minimal scene manager adapter over BaseGame.setScreen.
 */
export default class SceneManager {
  private readonly game: ScreenHost | null;

  private current: unknown;

  constructor(game: ScreenHost | null) {
    this.game = game;
    this.current = null;
  }

  set(scene: unknown): void {
    this.current = scene;
    if (this.game && typeof this.game.setScreen === 'function') {
      this.game.setScreen(scene);
    }
  }

  getCurrent<T = unknown>(): T | null {
    return this.current as T | null;
  }
}
