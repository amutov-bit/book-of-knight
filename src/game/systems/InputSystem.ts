// @ts-nocheck
/**
 * Input system facade. Menu button input remains source of truth.
 */
export default class InputSystem {
    constructor(game) {
        this.game = game;
    }

    requestSpin() {
        if (!this.game || !this.game.controller) return;
        this.game.controller.event = 1;
    }
}
