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

public class HelpScreenSplash2 extends Group {
	
	private Image bg;

	private Label title;
	private Label titleWild;
	private Label titleSpecial;
	private Label splashTxt;
	private Label splashTxt2;
	private Label splashTxt3;
	private Image scatter;
	
	private Image book;
	private List<Image> specialSymbol = new ArrayList<Image>();

	
	BookOfKnight game;
	
	public HelpScreenSplash2(BookOfKnight game) {
		this.game = game;
		this.setVisible(false);
	}

	public void loadAssets(int currentFile){
		
	}
	
	public void commitAssets() {
		
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setText(game.gameTxt.splashSecTitle);
		title.setAlignment(Align.top);
		title.setBounds(0,0 - 40,1920, 1080);
		addActor(title);
		
		titleWild = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		titleWild.setFontScale(1f);
		titleWild.setText(game.gameTxt.splashSecTitleWild);
		addActor(titleWild);

		titleSpecial = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		titleSpecial.setFontScale(1f);
		titleSpecial.setText(game.gameTxt.splashSecTitleSpecial);
		addActor(titleSpecial);
		
		game.textures.getSymbolsAtlas().findRegion("book_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		book = new Image(new Sprite(game.textures.getSymbolsAtlas().findRegion("book_blur")));
		book.setVisible(true);
		addActor(book);
		
		game.textures.getFreeGamesAtlas().findRegion("golden_10_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_10_blur"))));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_J_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_J_blur"))));

		game.textures.getFreeGamesAtlas().findRegion("golden_Q_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_Q_blur"))));

		game.textures.getFreeGamesAtlas().findRegion("golden_K_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_K_blur"))));

		game.textures.getFreeGamesAtlas().findRegion("golden_A_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_A_blur"))));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_torch_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_torch_blur"))));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_axe_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_axe_blur"))));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_chalice_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_chalice_blur"))));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_knight_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		specialSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_knight_blur"))));
		
		splashTxt = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt.setFontScale(1f);
		splashTxt.setText(game.gameTxt.splashSecTxt);
		splashTxt.setAlignment(Align.left);
		addActor(splashTxt);

		splashTxt2 = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt2.setFontScale(1f);
		splashTxt2.setText(game.gameTxt.splashSecTxt2);
		splashTxt2.setAlignment(Align.left);
		addActor(splashTxt2);
		
		splashTxt3 = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		splashTxt3.setFontScale(1f);
		splashTxt3.setText(game.gameTxt.splashSecTxt3);
		splashTxt3.setAlignment(Align.left);
		addActor(splashTxt3);
		
		Gdx.app.debug("test ", "jackpotSymbol.get(0).getWidth(); = " + specialSymbol.get(0).getWidth());
		
		
		
		book.setOrigin(book.getWidth()/2, book.getHeight()/2);
		book.setScale(0.7f, 0.7f);
		book.setPosition(300 + 46, 590);
		titleWild.setPosition(345 + 46, 870);

		titleSpecial.setPosition(1000 + 73, 870);
		
		for(int i = 0; i < specialSymbol.size(); i++){
			
			specialSymbol.get(i).setOrigin(specialSymbol.get(i).getWidth()/2, specialSymbol.get(i).getHeight()/2);
			specialSymbol.get(i).setScale(0.3f, 0.3f);
			if(i < 5){
				specialSymbol.get(i).setPosition(900 + i * specialSymbol.get(i).getWidth() * 0.35f, 665);
			} else {
				specialSymbol.get(i).setPosition(900 + 48 + i % 5  * specialSymbol.get(i).getWidth() * 0.35f, 665 - specialSymbol.get(i).getHeight() * 0.35f);
			}
			addActor(specialSymbol.get(i));

		}
		
//		specialSymbol.get(8).setOrigin(specialSymbol.get(8).getWidth()/2, specialSymbol.get(8).getHeight()/2);
//		specialSymbol.get(8).setScale(0.3f, 0.3f);
//		specialSymbol.get(8).setPosition(1000 - 103 + 38 + 8 % 5 * specialSymbol.get(8).getWidth() * 0.35f , 665 - specialSymbol.get(8).getHeight() * 0.35f);
//		
		
		if(game.languageCode == "RUS" || game.languageCode == "PL" || game.languageCode == "FRA" || game.languageCode == "POR" || game.languageCode == "ESP"){
			splashTxt.setFontScale(0.9f);
			splashTxt2.setFontScale(0.9f);
			splashTxt3.setFontScale(0.9f);
		}

//		if(){
//			splashTxt.setFontScale(0.8f);
//			splashTxt2.setFontScale(0.8f);
//			splashTxt3.setFontScale(0.8f);
//		}
		
		splashTxt.setBounds( 210,100 + 468, 1920 - 200 * 2,0);
		splashTxt2.setBounds(210,100 + 384, 1920 - 200 * 2,0);
		splashTxt3.setBounds(210,100 + 300, 1920 - 200 * 2,0);

		splashTxt.setWrap(true);
		splashTxt2.setWrap(true);
		splashTxt3.setWrap(true);
		
	}
	
}
