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


public class MenuSound extends Group {
	
	BookOfKnight game;
	
	public Image bg;
	public Image title;

	private ButtonAutoPlay buttonYes;
	private ButtonAutoPlay buttonNo;

	private Label titleSound;

	public MenuSound(BookOfKnight game) {
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
		bg.setPosition(611, 1080 - 539 - 244);
		addActor(bg);

		game.textures.getMenuBetAtlas().findRegion("loading_logo").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		title = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("loading_logo")));
		title.setPosition(711, 1080 - 340 - 244);
		
		Gdx.app.debug("MenuSOund", "title.getX = " + title.getX() + "title.getY = " + title.getY());
		title.setScale(1f);
		addActor(title);
		
//		#93a7bf 147, 167, 191
		titleSound = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		titleSound.setAlignment(Align.top);
		titleSound.setBounds(bg.getX(), bg.getY() - 50, bg.getWidth(), bg.getHeight());
		titleSound.setFontScale(1f);
		titleSound.setText(game.gameTxt.soundTitle);
		addActor(titleSound);
		
		buttonYes = new ButtonAutoPlay(game, 0, 0, game.textures.getMenuBetAtlas(), "loading_sound_button");
		buttonYes.setHover(false);
		buttonYes.commitAssets();
		buttonYes.setText(game.gameTxt.soundYes);
//		buttonYes.setScale(0.5f);
		buttonYes.setPosition(665, 1080 - 661 - 78);
		addActor(buttonYes);

		buttonNo = new ButtonAutoPlay(game, 0, 0, game.textures.getMenuBetAtlas(), "loading_sound_button");
		buttonNo.setHover(false);
		buttonNo.commitAssets();
		buttonNo.setText(game.gameTxt.soundNo);
//		buttonNo.setScale(0.5f);
		buttonNo.setPosition(988, 1080 - 661 - 78);
		addActor(buttonNo);

		buttonYes.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				game.helpScreen.settings.setSound(true);

				game.gameAssetsManager.menuSound.setVisible(false);
				game.gameAssetsManager.bgSound.setVisible(false);
				game.sounds.playBackground(true);
				
				if(!game.context.skipIntro){
					game.sounds.play(SoundTrack.INTRO, false);
				}
				
				game.gameAssetsManager.hideWatingScreen();
//				game.gameAssetsManager.title.setVisible(true);
				
//				if(game.login){
//					game.gameAssetsManager.tapTo.setVisible(true);
//				} else {
//					game.gameAssetsManager.tapTo.setVisible(false);
//				}
				
			}
			
			@Override
			public void onPress() {
				game.sounds.setSoundIntro(0, true);
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});	
		
		
		
		buttonNo.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				game.helpScreen.settings.setSound(false);

				game.gameAssetsManager.menuSound.setVisible(false);
				game.gameAssetsManager.bgSound.setVisible(false);
				game.sounds.setSound(0, false);
//				game.gameAssetsManager.title.setVisible(true);
				
				game.gameAssetsManager.hideWatingScreen();
				
//				if(game.login){
//					game.gameAssetsManager.tapTo.setVisible(true);
//				} else {
//					game.gameAssetsManager.tapTo.setVisible(false);
//				}
				game.sounds.play(SoundTrack.KNOCK, false);
			}
			
			@Override
			public void onPress() {
				game.gsLink.fullScreenCustom();
			}
		});	

	}
	
	public void hide(){
		
		
		game.gameAssetsManager.hideWatingScreen();
		
//		game.gsLink.console("HIDEEEEEEEEEEEEEEEEEE sound game.login = " + game.login);
		game.helpScreen.settings.setSound(true);

		this.setVisible(false);
		game.sounds.playBackground(true);
		game.sounds.setSoundIntro(0, true);
		game.sounds.play(SoundTrack.INTRO, false);
		game.gameAssetsManager.bgSound.setVisible(false);
//		game.gameAssetsManager.title.setVisible(true);
		
//		if(game.login){
//			game.gameAssetsManager.tapTo.setVisible(true);
//		} else {
//			game.gameAssetsManager.tapTo.setVisible(false);
//		}
		}
	}
