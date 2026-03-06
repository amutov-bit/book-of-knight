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


public class BuyBonusConfirm extends Group {
	
	BookOfKnight game;
	
	public Image bg, bg_menu;
	public Image holdAndWinImage, freeSpinsImage;

	private ButtonAutoPlay buttonYes;
	private ButtonAutoPlay buttonNo;

	private Label title;
	
	private int type = 0;

	public BuyBonusConfirm(BookOfKnight game) {
		this.game = game;
		
		this.setPosition(0, 0);
	}
	
	public void loadAssets() {
		
	}
	
	
	/**
	 * 
	 */
	public void commitAssets() {
		
		bg_menu = new Image(game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class));
		bg_menu.setPosition(0, 0);
		addActor(bg_menu);

		bg = new Image(game.ondemandAssetManager.getAssetManager().get("confirmation_bg.png", Texture.class));
		bg.setPosition((1920 - 990)/2, (1080 - 700)/2 + 100 - 50);
		addActor(bg);
		
//		game.textures.getMenuBetAtlas().findRegion("loading_sound_bg").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		bg = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("loading_sound_bg")));
//		bg.setPosition(611 + 20, 1080 - 539 - 244 + 150);
//		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
//		bg.setScale(1.5f);
//		addActor(bg);

		game.textures.getSymbolsAtlas().findRegion("book_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freeSpinsImage = new Image(new Sprite(game.textures.getSymbolsAtlas().findRegion("book_blur")));
		freeSpinsImage.setPosition(782, 1080 - 340 - 244 + 135 - 50);
		addActor(freeSpinsImage);
		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		holdAndWinImage= new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur")));
		holdAndWinImage.setPosition(782 + 29, 1080 - 340 - 244 + 120 - 50);
		addActor(holdAndWinImage);
		
//		game.textures.getMenuBetAtlas().findRegion("loading_logo").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		titleImage = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("loading_logo")));
		
//		Gdx.app.debug("MenuSOund", "title.getX = " + title.getX() + "title.getY = " + title.getY());
//		title.setScale(1f);
//		addActor(titleImage);
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		title.setAlignment(Align.top);
		title.setBounds(bg.getX(), bg.getY() - 355, bg.getWidth(), bg.getHeight());
		title.setFontScale(1f);
		title.setText(game.gameTxt.turboSpinsTitle);
		addActor(title);
		
		
		float offsetY = 0;

		
//		if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "PL" || game.languageCode == "RUS"){
//			offsetY = -35;
//		} else {
//			offsetY = 0;
//		}
//
//		if(game.languageCode == "TUR"){
//			title.setBounds(bg.getX(), bg.getY() + 35, bg.getWidth(), bg.getHeight());
//			offsetY = -40;
//		}
		
		buttonYes = new ButtonAutoPlay(game, 0, 0, game.textures.getInterfaceCommon(), "b_yes");
		buttonYes.setHover(false);
		buttonYes.commitAssets();
		buttonYes.setText(game.gameTxt.soundYes);
//		buttonYes.setScale(0.5f);
		buttonYes.setPosition(665, 1080 - 661 - 78 - 30 + offsetY + 70 - 50);
		addActor(buttonYes);

		buttonNo = new ButtonAutoPlay(game, 0, 0, game.textures.getInterfaceCommon(), "b_no");
		buttonNo.setHover(false);
		buttonNo.commitAssets();
		buttonNo.setText(game.gameTxt.soundNo);
//		buttonNo.setScale(0.5f);
		buttonNo.setPosition(988, 1080 - 661 - 78 - 30 + offsetY + 70 - 50);
		addActor(buttonNo);
		
		buttonYes.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.context.buyBonusType = type;
				game.context.hasBuyBonus  = true;
				hide();
				game.controller.setNextState(State.BUY_FREE_GAMES);
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
				hide();
				game.context.hasBuyBonus = false;
				game.buyBonusMenu.setVisible(true);
			}
			
			@Override
			public void onPress() {
				game.gsLink.fullScreenCustom();
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

	}
	
	public void hide(){
		this.setVisible(false);
	}

	public void setBonusType(int type, long value) {
		
		this.type = type;
		
		game.context.hasBuyBonus = false;
		
		if(type == 0){
			freeSpinsImage.setVisible(true);
			holdAndWinImage.setVisible(false);
			title.setText(game.gameTxt.buyTxt + "\n" + game.gameTxt.freeGameTxt +  "\n" + game.meters.formatNumber(value, false) + " " + game.meters.getCurrency());
		} else {
			freeSpinsImage.setVisible(false);
			holdAndWinImage.setVisible(true);
			title.setText(game.gameTxt.buyTxt + "\n" + game.gameTxt.holdAndWinTxt +  "\n" + game.meters.formatNumber(value, false) + " " + game.meters.getCurrency());
		}
		
	}
}
