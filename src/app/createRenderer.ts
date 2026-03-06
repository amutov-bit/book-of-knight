import type BaseGame from '../core/BaseGame';

/**
 * Creates Pixi renderer via BaseGame abstraction.
 */
export async function createRenderer(game: BaseGame): Promise<unknown> {
  await game.initRenderer();
  return (game as any).renderer;
}

export default createRenderer;

