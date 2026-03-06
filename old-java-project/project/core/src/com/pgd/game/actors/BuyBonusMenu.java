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


public class BuyBonusMenu extends Group {
	
	BookOfKnight game;
	
	public Image bg;
	public Image bg_frame_left_disable;
	public Image bg_frame_right_disable;
	public Image bg_frame_left_active;
	public Image bg_frame_right_active;


	private Label title;
	
	private Label titleFreeSpins;
	private Label titleHoldAndWin;

	private Label freeSpinsPrice;
	private Label holdAndWinPrice;
	
	private Button 				 closeButton;

	private List<ButtonBuyBonus> buyButton;

	private Image freeSpinsImage;
	private Image holdAndWinImage;
	
	private Image  bet_bg;
	private Label  totalBetTxt;
	private Label  totalBetField;
	private Button plusButton;
	private Button minusButton;
	
//	private BuyBonusConfirm bonusConfirm;
	private long currentValue = 0;
	
	private Color color;
	
	public BuyBonusMenu(BookOfKnight game) {
		this.game = game;
		
		buyButton = new ArrayList<ButtonBuyBonus>();
		
	}
	
	public void loadAssets() {
		
	}
	
	
	public void commitAssets() {
				
		
		bg = new Image(game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class));
		bg.setPosition(0, 0);
		addActor(bg);

		game.textures.getInterfaceCommon().findRegion("b_bg_disable").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg_frame_left_disable = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("b_bg_disable")));
		addActor(bg_frame_left_disable);

		game.textures.getInterfaceCommon().findRegion("b_bg_disable").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg_frame_right_disable = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("b_bg_disable")));
		addActor(bg_frame_right_disable);

		game.textures.getInterfaceCommon().findRegion("b_bg_active").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg_frame_left_active = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("b_bg_active")));
		addActor(bg_frame_left_active);
		
		game.textures.getInterfaceCommon().findRegion("b_bg_active").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg_frame_right_active = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("b_bg_active")));
		addActor(bg_frame_right_active);
		
		bg_frame_left_active.setVisible(false);
		bg_frame_right_active.setVisible(false);
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setAlignment(Align.center | Align.top);
		title.setText(game.gameTxt.buyBonusTxt);
		title.setBounds(0,-40,1920, 1080);
		addActor(title);

//		#ff9d2d 255, 157, 45
		titleFreeSpins = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(255/255f, 157/255f, 45/255f, 255f/255f)));
		titleFreeSpins.setFontScale(1f);
		titleFreeSpins.setAlignment(Align.center);
		titleFreeSpins.setText(game.gameTxt.freeGameTxt);
		addActor(titleFreeSpins);
//		#ff9d2d
		titleHoldAndWin = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(255/255f, 157/255f, 45/255f, 255f/255f)));
		titleHoldAndWin.setFontScale(1f);
		titleHoldAndWin.setAlignment(Align.center);
		titleHoldAndWin.setText(game.gameTxt.holdAndWinTxt);
		addActor(titleHoldAndWin);

//		#93a7bf
		freeSpinsPrice = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		freeSpinsPrice.setFontScale(1f);
		freeSpinsPrice.setAlignment(Align.center);
		freeSpinsPrice.setText("0.00" + "\n" + game.meters.getCurrency());
		addActor(freeSpinsPrice);
//		#93a7bf
		holdAndWinPrice = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		holdAndWinPrice.setFontScale(1f);
		holdAndWinPrice.setAlignment(Align.center);
		holdAndWinPrice.setText("0.00" + "\n" + game.meters.getCurrency());
		addActor(holdAndWinPrice);


		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		holdAndWinImage= new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur")));
		addActor(holdAndWinImage);

		game.textures.getSymbolsAtlas().findRegion("book_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freeSpinsImage = new Image(new Sprite(game.textures.getSymbolsAtlas().findRegion("book_blur")));
		addActor(freeSpinsImage);
		
		
		game.textures.getMenuBetAtlas().findRegion("bet_value").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bet_bg = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("bet_value")));
		bet_bg.setPosition(1036 - 333, 1080 - 438 - 106 - 450);
		addActor(bet_bg);
		
		//#bee1f5 190, 225, 245
		totalBetTxt = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		totalBetTxt.setPosition((int)bet_bg.getX() - 10 + 9, (int)bet_bg.getY() + 120);
		totalBetTxt.setAlignment(Align.center);
		totalBetTxt.setWidth((int)bet_bg.getWidth());
		totalBetTxt.setFontScale(1f);
		addActor(totalBetTxt);
		
//		#bee1f5  190, 225, 245
		totalBetField = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		totalBetField.setPosition(1036 - 333, 1080 - 438 - 106 - 450);
		totalBetField.setAlignment(Align.center);
		totalBetField.setBounds(bet_bg.getX(),bet_bg.getY(), bet_bg.getWidth(), bet_bg.getHeight());
		addActor(totalBetField);

		totalBetTxt.setText(game.gameTxt.menuBetTotalBet);
		
		plusButton = new Button(game, 1442 - 333, 1080 - 462 - 60 - 450, game.textures.getMenuBetAtlas(), "plus");
		plusButton.setHover(false);
		plusButton.commitAssets();
		addActor(plusButton);

		minusButton = new Button(game, 1062 - 333, 1080 - 462 - 60 - 450, game.textures.getMenuBetAtlas(), "minus");
		minusButton.setHover(false);
		minusButton.commitAssets();
		addActor(minusButton);
		
		plusButton.setPosition(1442 +  - 33 - 8 - 333, 1080 - 462 - 60 + - 30 - 450);
		minusButton.setPosition(1062 + - 20 - 333, 1080 - 462 - 60 + - 30 - 450);
		
		minusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.decrementBetPerLine();
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		plusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.incrementBetPerLine();
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		closeButton = new Button(game, 1711, 1080 - 21 - 131, game.textures.getMenuBetAtlas(), "button_close");
		closeButton.setHover(false);
		closeButton.commitAssets();
		addActor(closeButton);
		closeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				close();
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		buyButton.add(new ButtonBuyBonus(game, 0, 0, game.textures.getInterfaceCommon(), "bbuy"));
		buyButton.add(new ButtonBuyBonus(game, 0, 0, game.textures.getInterfaceCommon(), "bbuy"));
		
		
		for(int i = 0; i < buyButton.size(); i++)
		{
			buyButton.get(i).setHover(false);
			buyButton.get(i).commitAssets();
			buyButton.get(i).setText(game.gameTxt.buyTxt);
			
			addActor(buyButton.get(i));
		}

		buyButton.get(0).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.overlay.bonusConfirm.setBonusType(0, game.context.buyFreeGamesMult * currentValue);
				game.overlay.bonusConfirm.setVisible(true);
//				bg_frame_left_active.setVisible(true);

				hide();
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	
		
		buyButton.get(1).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.overlay.bonusConfirm.setBonusType(1, game.context.buyHoldAndWinMult * currentValue);
				game.overlay.bonusConfirm.setVisible(true);
//				bg_frame_right_active.setVisible(true);

				hide();
			}
			
			@Override
			public void onPress() {
				
				
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	
		
		

		bg_frame_left_active.setPosition(420, 1080 - 800 - 90);
		bg_frame_left_disable.setPosition(420, 1080 - 800 - 90);
		
		buyButton.get(0).setPosition(500 + 52, 330 - 20 - 10 - 15);
		
		freeSpinsImage.setPosition(430 + 63, 640 - 20 - 15);

		titleFreeSpins.setPosition(bg_frame_left_active.getX(), 640 - 20 - 45 - 15);
		titleFreeSpins.setWidth(504);
		
		freeSpinsPrice.setPosition(bg_frame_left_active.getX(), 500 - 20 - 15);
		freeSpinsPrice.setWidth(504);
		
		bg_frame_right_active.setPosition(990 - 1, 1080 - 800 - 90);
		bg_frame_right_disable.setPosition(990 - 1, 1080 - 800 - 90);

		buyButton.get(1).setPosition(1000 + 150 - 29, 330 - 20 - 10 - 15);
		
		holdAndWinImage.setPosition(950 + 150, 640 - 20 - 30);

		
		
		titleHoldAndWin.setPosition(bg_frame_right_active.getX(), 640 - 20 - 45 - 15);
		titleHoldAndWin.setWidth(504);
		
		holdAndWinPrice.setPosition(bg_frame_right_active.getX(), 500 - 20 - 15);
		holdAndWinPrice.setWidth(504);

		if(game.languageCode == "RUS"){
			titleHoldAndWin.setFontScale(0.55f);
			titleFreeSpins.setFontScale(0.8f);
		}

		if(game.languageCode == "PL" || game.languageCode == "FRA" || game.languageCode == "POR" || game.languageCode == "ESP" /*|| game.languageCode == "BG"*/){
			titleHoldAndWin.setFontScale(0.8f);
//			titleFreeSpins.setFontScale(0.9f);
		}

		if(game.languageCode == "TUR"){
//			titleHoldAndWin.setFontScale(0.8f);
			titleFreeSpins.setFontScale(0.8f);
		}
//		.setPosition(1100 + 150, 600 - 20);
//		.setPosition(1100 + 150, 500 - 20);

	}
	
	public void close(){
		game.menu.paytableButton.reset();
		game.menu.betButton.reset();
		game.menu.autoButton.reset();
		game.menu.startButton.reset();
		
		this.setVisible(false);
	}
	
	public void hide(){
		this.setVisible(false);
	}

	public void setTotalBet(long value) {
		
		
		currentValue = value;
		
		long buyFreeGamesValue = value * game.context.buyFreeGamesMult;
		long buyHoldAndWinValue = value * game.context.buyHoldAndWinMult;
		
		freeSpinsPrice.setText(game.meters.formatNumber(buyFreeGamesValue, false) + "\n" + game.meters.getCurrency());
		holdAndWinPrice.setText(game.meters.formatNumber(buyHoldAndWinValue, false) + "\n" + game.meters.getCurrency());
		totalBetField.setText((game.meters.formatNumber(value, false)) + " " + game.meters.getCurrency());

		if(buyFreeGamesValue > game.meters.credit){
			buyButton.get(0).disable();
			bg_frame_left_active.setVisible(false);
		} else {
			buyButton.get(0).reset();
			bg_frame_left_active.setVisible(true);
		}

		if(buyHoldAndWinValue > game.meters.credit){
			buyButton.get(1).disable();
			bg_frame_right_active.setVisible(false);
		} else {
			buyButton.get(1).reset();
			bg_frame_right_active.setVisible(true);
		}
		
	}
	
	
}
