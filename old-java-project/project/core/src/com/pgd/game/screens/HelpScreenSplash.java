package com.pgd.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.ButtonDot;
import com.pgd.game.actors.ButtonMenuBet;
import com.pgd.game.base.IButtonCallback;

public class HelpScreenSplash extends Group {
	
	private Image bg;

	private Label title;
	private Label splashTxt;
	private Label splashTxt2;
	private Label splashTxt3;
	private Image scatter;
	
	private List<Image> jackpotSymbol = new ArrayList<Image>();

	
	BookOfKnight game;
	
	public HelpScreenSplash(BookOfKnight game) {
		this.game = game;
		this.setVisible(false);
	}

	public void loadAssets(int currentFile){
		
	}
	
	public void commitAssets() {
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setText(game.gameTxt.splashTitle);
		title.setAlignment(Align.top);
		title.setBounds(0,0 - 40,1920, 1080);
		addActor(title);
		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur"))));
		jackpotSymbol.get(0).setVisible(true);
		addActor(jackpotSymbol.get(0));
		
		game.textures.getInterfaceAtlas().findRegion("major_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("major_blur"))));
		jackpotSymbol.get(1).setVisible(true);
		addActor(jackpotSymbol.get(1));
		
		game.textures.getInterfaceAtlas().findRegion("mini_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mini_blur"))));
		jackpotSymbol.get(2).setVisible(true);
		addActor(jackpotSymbol.get(2));

		game.textures.getInterfaceAtlas().findRegion("shield_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("shield_blur"))));
		jackpotSymbol.get(3).setVisible(true);
		addActor(jackpotSymbol.get(3));
		
		splashTxt = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt.setFontScale(1f);
		splashTxt.setText(game.gameTxt.splashTxt);
		splashTxt.setAlignment(Align.left);
		addActor(splashTxt);

		splashTxt2 = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt2.setFontScale(1f);
		splashTxt2.setText(game.gameTxt.splashTxt2);
		splashTxt2.setAlignment(Align.left);
		addActor(splashTxt2);
		
		splashTxt3 = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt3.setFontScale(1f);
		splashTxt3.setText(game.gameTxt.splashTxt3);
		splashTxt3.setAlignment(Align.left);
		addActor(splashTxt3);
		
		Gdx.app.debug("test ", "jackpotSymbol.get(0).getWidth(); = " + jackpotSymbol.get(0).getWidth());
		
		for(int i = 0; i < jackpotSymbol.size(); i++){
			
			jackpotSymbol.get(i).setOrigin(jackpotSymbol.get(i).getWidth()/2, jackpotSymbol.get(0).getHeight()/2);
			jackpotSymbol.get(i).setScale(0.7f, 0.7f);
			if(game.languageCode == "RUS" || game.languageCode == "PL" || game.languageCode == "FRA" || game.languageCode == "POR" || game.languageCode == "ESP"){
				jackpotSymbol.get(i).setPosition(500 + i * jackpotSymbol.get(i).getWidth() * 0.7f, 710);
			} else {
				jackpotSymbol.get(i).setPosition(500 + i * jackpotSymbol.get(i).getWidth() * 0.7f, 690);
			}
		}
		
		
		if(game.languageCode == "RUS" || game.languageCode == "CZE" || game.languageCode == "PL" || game.languageCode == "TUR" ||
				game.languageCode == "FRA" || game.languageCode == "POR" || game.languageCode == "ESP"){
			splashTxt.setFontScale(0.9f);
			splashTxt2.setFontScale(0.9f);
			splashTxt3.setFontScale(0.9f);
		}
		
		
		splashTxt.setBounds(200,690, 1920 - 170 * 2,0);
		splashTxt2.setBounds(200,600, 1920 - 170 * 2,0);
		splashTxt3.setBounds(200,450, 1920 - 170 * 2,0);

		if(game.languageCode == "TUR"){
			splashTxt.setBounds(200,690, 1920 - 170 * 2,0);
			splashTxt2.setBounds(200,600, 1920 - 170 * 2,0);
			splashTxt3.setBounds(200,450 - 10, 1920 - 170 * 2,0);
		}
		
		if(game.languageCode == "PL" || game.languageCode == "RUS"){
			splashTxt.setBounds(200,690 + 20, 1920 - 170 * 2,0);
			splashTxt2.setBounds(200,600 + 5, 1920 - 170 * 2,0);
			splashTxt3.setBounds(200,450 - 20, 1920 - 170 * 2,0);
		}
		
		if(game.languageCode == "ESP"){
			splashTxt.setBounds(200,690 + 25, 1920 - 170 * 2,0);
			splashTxt2.setBounds(200,600 + 0, 1920 - 170 * 2,0);
			splashTxt3.setBounds(200,450 - 20, 1920 - 170 * 2,0);
		}
		
		if(game.languageCode == "FRA" || game.languageCode == "POR"){
			splashTxt.setBounds(200,690 + 20, 1920 - 170 * 2,0);
			splashTxt2.setBounds(200,600 + 5, 1920 - 170 * 2,0);
			splashTxt3.setBounds(200,450 - 20, 1920 - 170 * 2,0);
		}
		
		if(game.languageCode == "CZE" || game.languageCode == "BG"){
			splashTxt.setBounds(200,690 + 5, 1920 - 170 * 2,0);
			splashTxt2.setBounds(200,600 - 5, 1920 - 170 * 2,0);
			splashTxt3.setBounds(200,450 - 20, 1920 - 170 * 2,0);
		}

		splashTxt.setWrap(true);
		splashTxt2.setWrap(true);
		splashTxt3.setWrap(true);
		
	}
	
}
