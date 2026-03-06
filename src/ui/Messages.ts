// @ts-nocheck
/**
 * Created by Dimitar on 2/21/2017.
 */

/** @typedef {import('../core/BaseGame').default} BaseGame */

export default class Messages extends PIXI.Container {

    /**
     * @param {BaseGame} game
     */
    constructor(game) {
        super();
        /** @type {BaseGame} */
        this.game = game;
        this.demoText = "";
        this.errorText = "";

        //demo = new Label("DEMO", new LabelStyle(game.fonts.impact40y, Color.WHITE));
        //demo.setPosition(game.VIRTUAL_WIDTH - 92, 36);
        //demo.setVisible(/*game.DEMO_MODE*/false);
        //
        //error = new Label("ERROR: ", new LabelStyle(game.fonts.impact40y, Color.RED));
        //error.setPosition(30, game.VIRTUAL_HEIGHT - 36);
        //error.setVisible(true);

        //this.addActor(this.demoText);
        //this.addActor(this.errorText);
    }

    setError(str) {
        //error.setText(str);
        //error.setVisible(true);
    }

    clearErrors() {
        //error.setText("");
        //error.setVisible(false);
    }
}
