// @ts-nocheck
/**
 * Aggregates main Pixi view modules.
 */
export default class GameView {
    constructor(game) {
        this.game = game;
    }

    get reels() {
        return this.game.reels;
    }

    get menu() {
        return this.game.menu;
    }

    get messages() {
        return this.game.messages;
    }
}
