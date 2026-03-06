// @ts-nocheck
/**
 * Outcome mapping adapter - currently delegated to GsLink.onSpin.
 */
export default class OutcomeSystem {
    constructor(game) {
        this.game = game;
    }

    applyServerOutcome(outcome) {
        if (this.game && this.game.gsLink && typeof this.game.gsLink.onSpin === 'function') {
            this.game.gsLink.onSpin(outcome);
        }
    }
}
