package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.AbstractController.Event;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.Controller.State;


public class MenuTurboSpins extends Group {
	
	BookOfKnight game;
	
	public Image bg;
//	public Image title;

	private ButtonAutoPlay buttonYes;
	private ButtonAutoPlay buttonNo;

	private Label title;

	public MenuTurboSpins(BookOfKnight game) {
		this.game = game;
		
		this.setPosition(0, 0);
	}
	
	public void loadAssets() {
		
	}
	
	
	/**
	 * 
	 */
	public void commitAssets() {
		
		game.textures.getMenuBetAtlas().findRegion("loading_sound_bg").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("loading_sound_bg")));
		bg.setPosition(611 + 20, 1080 - 539 - 244 + 150);
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		bg.setScale(1.5f);
		addActor(bg);

//		game.textures.getMenuBetAtlas().findRegion("loading_logo").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		title = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("loading_logo")));
//		title.setPosition(711, 1080 - 340 - 244);
		
//		Gdx.app.debug("MenuSOund", "title.getX = " + title.getX() + "title.getY = " + title.getY());
//		title.setScale(1f);
//		addActor(title);
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setAlignment(Align.top);
		title.setBounds(bg.getX(), bg.getY() - 0, bg.getWidth(), bg.getHeight());
		title.setFontScale(1f);
		title.setText(game.gameTxt.turboSpinsTitle);
		addActor(title);
		
		
		float offsetY = 0;

		
		if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "PL" || game.languageCode == "RUS"){
			offsetY = -35;
		} else {
			offsetY = 0;
		}

		if(game.languageCode == "TUR"){
			title.setBounds(bg.getX(), bg.getY() + 35, bg.getWidth(), bg.getHeight());
			offsetY = -40;
		}
		
		buttonYes = new ButtonAutoPlay(game, 0, 0, game.textures.getMenuBetAtlas(), "loading_sound_button");
		buttonYes.setHover(false);
		buttonYes.commitAssets();
		buttonYes.setText(game.gameTxt.soundYes);
//		buttonYes.setScale(0.5f);
		buttonYes.setPosition(665, 1080 - 661 - 78 - 30 + offsetY + 150);
		addActor(buttonYes);

		buttonNo = new ButtonAutoPlay(game, 0, 0, game.textures.getMenuBetAtlas(), "loading_sound_button");
		buttonNo.setHover(false);
		buttonNo.commitAssets();
		buttonNo.setText(game.gameTxt.soundNo);
//		buttonNo.setScale(0.5f);
		buttonNo.setPosition(988, 1080 - 661 - 78 - 30 + offsetY + 150);
		addActor(buttonNo);
		
		buttonYes.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				switch(game.controller.getState())
				{
					case IDLE:
					case SHOW_LAST_WINS:
					case SHOW_LAST_WINS_RESTORE:
						game.context.turboGame = true;
						game.overlay.hideMenuTurboSpins();
						game.controller.event = Event.START;
					break;
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});	
		
		
		
		buttonNo.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.overlay.hideMenuTurboSpins();
				game.context.turboGame = false;
			}
			
			@Override
			public void onPress() {
				game.gsLink.fullScreenCustom();
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

	}
	
	public void hide(){
	}
}
