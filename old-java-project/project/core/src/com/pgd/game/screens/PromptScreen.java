package com.pgd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;

public class PromptScreen extends AbstractScreen {
	
	private Image splashImage;
	private Label status;
	
	public PromptScreen(BookOfKnight game) {
		super(game);
	}

	@Override
	public void draw() {
		status.setText("TOUCH SCREEN TO ENTER GAME");
	}
	
	@Override
	public void show() {
	    
	    BitmapFont font = game.fonts.font36px;
	    LabelStyle labelStyle = new LabelStyle(font, Color.WHITE);
	    status = new Label("", labelStyle);
	    status.setPosition(1680/2, 350);
	    status.setAlignment(Align.center);

	    stage.addActor(splashImage);
	    stage.addActor(status);
	    
		final InputAdapter fullscreen = new InputAdapter() {
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				//game.setFullscreen();
//				game.sounds.play(SoundTrack.THUNDER, false);
				game.setScreen(new MainScreen(game));
				return true;
			}
		};
		    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setInputProcessor(new InputMultiplexer(fullscreen, stage));
	    
	}

	@Override
	public void dispose() {
	    stage.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

}
