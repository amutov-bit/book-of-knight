// @ts-nocheck
/**
 * Aggregates mutable game model data.
 */
export default class GameModel {
    constructor(game) {
        this.game = game;
    }

    get meters() {
        return this.game.meters;
    }

    get context() {
        return this.game.context;
    }

    get outcome() {
        return this.game.context && this.game.context.outcome;
    }
}
