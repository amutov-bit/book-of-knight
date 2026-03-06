package com.pgd.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.Digits;
import com.pgd.game.base.IButtonCallback;

public class HelpScreenPaytable2 extends Group {
	
//	private BitmapFont font40Y, font40G, font28G;
//	private Image pay;
//	private Label textWild;
	private Button backButton;
	private Button nextButton;
	private Button prevButton;
	
	private BookOfKnight game;
	
	private int id;
	
	private Label title;

	private Label wildLabel;
	private Label scatterLabel;
	
	private static List<Image> stackedSymbol = new ArrayList<Image>();

	
	private Array<Digits> digitsFrame = new Array<Digits>();
	
	public enum TextsLabels {
		//id = 0
		SCATTER_X5(0,5,-167, 20 + 32 * 2, 1000),
		SCATTER_X4(0,4,-167, 20 + 32 * 1, 40),
		SCATTER_X3(0,3,-167, 20 + 32 * 0, 2), 

		NINE_X5(9,5, 190, 40 + 32 * 2, 5000),
		NINE_X4(9,4, 190, 40 + 32 * 1, 1000),
		NINE_X3(9,3, 190, 40 + 32 * 0, 100), 
		NINE_X2(9,2, 190, 40 - 32 * 1, 5), 

		EIGHT_X5(8,5,170, 10 + 32 * 2,   2000),
		EIGHT_X4(8,4,170, 10 + 32 * 1,   400),
		EIGHT_X3(8,3,170, 10 + 32 * 0,   30),
		
		SEVEN_X5(7,5,160, 10 + 32 * 2,   750),
		SEVEN_X4(7,4,160, 10 + 32 * 1,   100),
		SEVEN_X3(7,3,160, 10 + 32 * 0,   20),
		
		SIX_X5(6,5,160, 10 + 32 * 2,   750),
		SIX_X4(6,4,160, 10 + 32 * 1,   100),
		SIX_X3(6,3,160, 10 + 32 * 0,   20),
		
		// one line
		FIVE_X5(5,5,150, 10 + 32 * 2,   150),
		FIVE_X4(5,4,150, 10 + 32 * 1,   30),
		FIVE_X3(5,3,150, 10 + 32 * 0,   5),
		
		FOUR_X5(4,5,150, 10 + 32 * 2,   150),
		FOUR_X4(4,4,150, 10 + 32 * 1,   30),
		FOUR_X3(4,3,150, 10 + 32 * 0,   5), 
		
		THREE_X5(3,5,150, 10 + 32 * 2,   100),
		THREE_X4(3,4,150, 10 + 32 * 1,   20), 
		THREE_X3(3,3,150, 10 + 32 * 0,   5),   
		
		TWO_X5(2,5,150, 10 + 32 * 2,  100),
		TWO_X4(2,4,150, 10 + 32 * 1,  20), 
		TWO_X3(2,3,150, 10 + 32 * 0,  5),  
		
		ONE_X5(1,5,150, 10 + 32 * 2,   100),
		ONE_X4(1,4,150, 10 + 32 * 1,   20),
		ONE_X3(1,3,150, 10 + 32 * 0,   5), 
		
		;

		private int index; 
		private int cnt; 
		private int mult;
		private int x;
		private int y;
		LabelStyle style;
		Label label;
		Label labelNumber;

		private TextsLabels(int index, int cnt, int x, int y, int mult) {
			this.index = index;
			this.mult = mult;
			this.cnt = cnt;
			this.x = x;
			this.y = y;
		}
		
		private void setPosition(int x, int y, int offsetX, int offsetY){
			this.x += offsetX;
			this.y += offsetY;
		}
		
		public void setStyle(LabelStyle style,  LabelStyle styleNumber, List<Image> stackedSymbol, int index) {
			this.style = style;
			label = new Label("" + this.mult, style);
			label.setWidth(120);
			if(this.index == 0){
				label.setAlignment(Align.left);
				label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50, stackedSymbol.get(this.index).getY() + this.y);
			} else {
				label.setPosition(stackedSymbol.get(this.index).getX() + this.x + 35, stackedSymbol.get(this.index).getY() + this.y);
				label.setAlignment(Align.left);
			}

			labelNumber = new Label("" + this.mult, styleNumber);
			labelNumber.setWidth(120);
			if(this.index == 0){
				labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80, stackedSymbol.get(this.index).getY() + this.y);
				labelNumber.setAlignment(Align.left);
			} else {
				labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x, stackedSymbol.get(this.index).getY() + this.y);
				labelNumber.setAlignment(Align.left);
			}
		}
		
		public void update(long bet, boolean decimal, BookOfKnight game) {
			
			label.setText(game.meters.formatNumber(this.mult * bet, game.hasBigNumberK == 1));
			labelNumber.setText("" + this.cnt);
			
			Gdx.app.debug("Paytable", "bet = " + bet);
			if(this.index == 0  && bet >= 1000000){
				label.setFontScale(0.9f);
				labelNumber.setFontScale(0.9f);
			} else {
				label.setFontScale(1f);
			}
			
			if(this.index == 0){
				
				if(bet >= 1000000){
					label.setAlignment(Align.left);
					label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setAlignment(Align.left);
				} else if(bet >= 100000){
					label.setAlignment(Align.left);
					labelNumber.setAlignment(Align.left);
					label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50 + 100 - 20 * 4, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80 + 100 - 20 * 4  , stackedSymbol.get(this.index).getY() + this.y);
				} else if(bet >= 10000){
					label.setAlignment(Align.left);
					labelNumber.setAlignment(Align.left);
					label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50 + 100 - 20 * 3, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80 + 100 - 20 * 3  , stackedSymbol.get(this.index).getY() + this.y);
				} else if(bet >= 1000){
					label.setAlignment(Align.left);
					labelNumber.setAlignment(Align.left);
						  label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50 + 100 - 20 * 2, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80 + 100 - 20 * 2  , stackedSymbol.get(this.index).getY() + this.y);
				} else if(bet >= 100){
					label.setAlignment(Align.left);
					labelNumber.setAlignment(Align.left);
					label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50 + 100 - 20, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80 + 100 - 20  , stackedSymbol.get(this.index).getY() + this.y);
				} else {
					label.setAlignment(Align.left);
					labelNumber.setAlignment(Align.left);
     					  label.setPosition(stackedSymbol.get(this.index).getX() + this.x - 50 + 100, stackedSymbol.get(this.index).getY() + this.y);
					labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 80 + 100, stackedSymbol.get(this.index).getY() + this.y);
				}
			} else {
				Gdx.app.debug("HelpScreenPaytable", "stackedSymbol.get("+this.index+").getX() = " + stackedSymbol.get(this.index).getX());
				
				label.setPosition(stackedSymbol.get(this.index).getX() + this.x + 35 - 15, stackedSymbol.get(this.index).getY() + this.y);
				labelNumber.setPosition(stackedSymbol.get(this.index).getX() + this.x - 15, stackedSymbol.get(this.index).getY() + this.y);
			}
			
//			labelNumber.setText("" + this.cnt + ".");
		}
		
		
		Label getLabel() {
			return this.label;
		}

		Label getLabelNumber() {
			return this.labelNumber;
		}
		
	}
	
	public HelpScreenPaytable2(BookOfKnight game, int i) {
		id = i;
		this.game = game;	
	}

	public void commitAssets() {
		
		game.textures.getFreeGamesAtlas().findRegion("golden_10_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_10_blur"))));
		stackedSymbol.get(0).setVisible(false);
//		addActor(stackedSymbol.get(0));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_10_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_10_blur"))));
		stackedSymbol.get(1).setVisible(true);		
		addActor(stackedSymbol.get(1));

		game.textures.getFreeGamesAtlas().findRegion("golden_J_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_J_blur"))));
		stackedSymbol.get(2).setVisible(true);
		addActor(stackedSymbol.get(2));

		game.textures.getFreeGamesAtlas().findRegion("golden_Q_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_Q_blur"))));
		stackedSymbol.get(3).setVisible(true);
		addActor(stackedSymbol.get(3));

		game.textures.getFreeGamesAtlas().findRegion("golden_K_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_K_blur"))));
		stackedSymbol.get(4).setVisible(true);
		addActor(stackedSymbol.get(4));

		game.textures.getFreeGamesAtlas().findRegion("golden_A_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_A_blur"))));
		stackedSymbol.get(5).setVisible(true);
		addActor(stackedSymbol.get(5));

		game.textures.getFreeGamesAtlas().findRegion("golden_torch_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_torch_blur"))));
		stackedSymbol.get(6).setVisible(true);
		addActor(stackedSymbol.get(6));
		
		game.textures.getFreeGamesAtlas().findRegion("golden_axe_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_axe_blur"))));
		stackedSymbol.get(7).setVisible(true);
		addActor(stackedSymbol.get(7));

		game.textures.getFreeGamesAtlas().findRegion("golden_chalice_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_chalice_blur"))));
		stackedSymbol.get(8).setVisible(true);
		addActor(stackedSymbol.get(8));

		game.textures.getFreeGamesAtlas().findRegion("golden_knight_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		stackedSymbol.add(new Image(new Sprite(game.textures.getFreeGamesAtlas().findRegion("golden_knight_blur"))));
		stackedSymbol.get(9).setVisible(true);
		addActor(stackedSymbol.get(9));
		
		
		
		
		Gdx.app.debug("Help Screen", "stackedSymbol.get(1).getW = " + stackedSymbol.get(1).getWidth() + "stackedSymbol.get(1).getH = " + stackedSymbol.get(1).getHeight());
	
		stackedSymbol.get(0).setPosition(660, 800);
		
		stackedSymbol.get(1).setPosition(110 + 340 * 4, 400 + 88);
		stackedSymbol.get(2).setPosition(110 + 340 * 3, 400 + 88);
		stackedSymbol.get(3).setPosition(110 + 340 * 2, 400 + 88);
		stackedSymbol.get(4).setPosition(110 + 340 * 1, 400 + 88);
		stackedSymbol.get(5).setPosition(110 + 340 * 0, 400 + 88);
		
		stackedSymbol.get(6).setPosition(200 - 35 + 420 * 3 - 30, 600 + 88);
		stackedSymbol.get(7).setPosition(200 - 35 + 420 * 2 - 15, 600 + 88);
		stackedSymbol.get(8).setPosition(200 - 35 + 420 * 1, 600 + 88);
		stackedSymbol.get(9).setPosition(200 - 35 + 420 * 0, 583 + 88);
		

		stackedSymbol.get(0).setScale(0.45f);
		stackedSymbol.get(1).setScale(0.45f);
		stackedSymbol.get(2).setScale(0.45f);
		stackedSymbol.get(3).setScale(0.45f);
		stackedSymbol.get(4).setScale(0.45f);
		stackedSymbol.get(5).setScale(0.45f);
		stackedSymbol.get(6).setScale(0.45f);
		stackedSymbol.get(7).setScale(0.45f);
		stackedSymbol.get(8).setScale(0.45f);
		stackedSymbol.get(9).setScale(0.45f);
		

		
		Gdx.app.debug("Help Screen", "stackedSymbol.get(1).getW = " + stackedSymbol.get(1).getWidth() + "stackedSymbol.get(1).getH = " + stackedSymbol.get(1).getHeight());
		
		wildLabel = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		wildLabel.setFontScale(1f);
		wildLabel.setText(game.gameTxt.paytableWild);
		wildLabel.setAlignment(Align.left);
//		addActor(wildLabel);

		scatterLabel = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		scatterLabel.setFontScale(1f);
		scatterLabel.setText(game.gameTxt.paytableScatter);
		scatterLabel.setAlignment(Align.left);
//		addActor(scatterLabel);
//		wildLabel.setPosition(stackedSymbol.get(8).getX() + 160, stackedSymbol.get(8).getY() + 55);
		scatterLabel.setPosition(stackedSymbol.get(0).getX() + 145, stackedSymbol.get(0).getY() + 55);
		
		switch(id){
		case 0:
//			game.ondemandAssetManager.getAssetManager().get("paytable.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			pay = new Image(game.manager.get("paytable.png", Texture.class));
//			pay.setPosition(0, 40);
		break;
		case 1:
			game.ondemandAssetManager.getAssetManager().get("paytable2.jpg", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

//			pay = new Image(game.manager.get("paytable2.jpg", Texture.class));
			break;
		case 2:
			game.ondemandAssetManager.getAssetManager().get("paytable3.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);

//			pay = new Image(game.manager.get("paytable3.png", Texture.class));
			break;
		}
//		addActor(pay);
		
		LabelStyle style40Y;
		LabelStyle styleNumber;
		
		if(game.languageCode == "ENG" || game.languageCode == "BG"){
			style40Y = new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f));
			styleNumber = new LabelStyle(game.fonts.font46px, new Color(255f/255f, 174/255f, 0/255f, 255f/255f));
		} else {
			style40Y = new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f));
			styleNumber = new LabelStyle(game.fonts.font36px, new Color(255f/255f, 174/255f, 0/255f, 255f/255f));
		}
	    
		int i = 0;
	    // Set text labels style
		for(TextsLabels label : TextsLabels.values()) {
			
			label.setStyle(style40Y, styleNumber, stackedSymbol, i);
			
			label.getLabel().setFontScale(1f);
			
			if(i > 2){
				addActor(label.getLabelNumber());		
				addActor(label.getLabel());
			}
			
			i++;
		}	

//		for(TextsLabels labelNumber : TextsLabels.values()) {
//			
//			labelNumber.setStyle(style40Y, styleNumber, stackedSymbol, i);
//			
//			labelNumber.getLabelNumber().setFontScale(1f);
//			
//			addActor(labelNumber.getLabelNumber());		
//			i++;
//		}	
	
//		update();

	}
	
	public void update() {
		// TODO Auto-generated method stub
			for(TextsLabels label : TextsLabels.values()) {
				if(game.menu.creditField.getFormat())		label.update(game.meters.getBetPerLine() * game.meters.getDenomination(), game.menu.creditField.getFormat(), game);
				else										label.update(game.meters.getBetPerLine(), game.menu.creditField.getFormat(), game);
			}
			
				stackedSymbol.get(1).setPosition(110 + 340 * 4, 400 + 88);
				stackedSymbol.get(2).setPosition(110 + 340 * 3, 400 + 88);
				stackedSymbol.get(3).setPosition(110 + 340 * 2, 400 + 88);
				stackedSymbol.get(4).setPosition(110 + 340 * 1, 400 + 88);
				stackedSymbol.get(5).setPosition(110 + 340 * 0, 400 + 88);
				
				stackedSymbol.get(6).setPosition(200 - 35 + 420 * 3 - 30, 600 + 88);
				stackedSymbol.get(7).setPosition(200 - 35 + 420 * 2 - 15, 600 + 88);
				stackedSymbol.get(8).setPosition(200 - 35 + 420 * 1, 600 + 88);
				stackedSymbol.get(9).setPosition(200 - 35 + 420 * 0, 583 + 88);
				
				stackedSymbol.get(0).setPosition(660, 800);
				
//				wildLabel.setPosition(stackedSymbol.get(0).getX() + 160, stackedSymbol.get(0).getY() + 55);
				scatterLabel.setPosition(stackedSymbol.get(0).getX() + 190, stackedSymbol.get(0).getY() + 60);				
			
//			if(game.menu.creditField.getFormat())

			{

					int totalBet = game.meters.getTotalBet();

					Gdx.app.debug("Help Screen", "totalBet = " + totalBet);
					
					TextsLabels.SCATTER_X5.update(totalBet, game.menu.creditField.getFormat(), game);
					TextsLabels.SCATTER_X4.update(totalBet, game.menu.creditField.getFormat(), game);
					TextsLabels.SCATTER_X3.update(totalBet, game.menu.creditField.getFormat(), game);
				
			}

		}
		

}
