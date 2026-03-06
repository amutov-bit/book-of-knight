import { Container } from 'pixi.js';
import { debug } from './utils/logger';
import type BaseGame from './BaseGame';

export default class BaseScreen {
  game: BaseGame;
  stage: Container;

  constructor(game: BaseGame) {
    this.game = game;
    debug('BaseScreen::constructor');
    this.stage = new Container();
  }

  show(): void {
    debug('BaseScreen::show');
  }

  hide(): void {
    debug('BaseScreen::hide');
  }

  act(delta: number): void {
    for (let i = 0; i < this.stage.children.length; i++) {
      const child = this.stage.children[i] as any;
      if (typeof child.act === 'function') {
        child.act(delta);
      }
    }
  }
}
