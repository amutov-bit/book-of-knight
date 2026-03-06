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

public class HelpScreenRules extends Group {
	
	private Image bg;

	private Label title;
	private int currentPage;
	private Button nextButton;
	private Button prevButton;
	
	//screen 1
	private Label maxBetLabel;
	private Label minBetLabel;
	private Label rulesInterface;
	private Label rulesAutoplay;
	private Image settingsButton;
	private Image menuButton;
	private Image startButton;
	private Image autoButton;
	private Image autoStopButton;
	
	//screen 2
	private Label rulesBetMenu;
	private Label rulesSettings;
	private Label rulesLines;
	private Label rulesLines2;
	private Label rulesUnfinished;
	private Label rulesMaxWin;
	private Label rulesBuyBonus;
	private Label rulesBuyBonus2;
	private Label rulesGamePercent;
	private Label rulesGamePercentBuyFree;
	private Label rulesGamePercentBuyHold;
	
	private Label rulesAddFg;
	
	private Image betPlus;
	private Image betMinus;
	private ButtonMenuBet buttonMaxBet;
	private ButtonMenuBet buttonBet;
	
//	private Label		       pagesLabel;

	public List<Group> pages= new ArrayList<Group>();

	BookOfKnight game;
	
	public HelpScreenRules(BookOfKnight game) {
		this.game = game;
		this.setVisible(false);
	}

	public void loadAssets(int currentFile){
		
	}
	
	public void commitAssets() {
	    
		Gdx.app.debug("HelpScreen ", "show()");
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setText(game.gameTxt.rulesTitle);
		title.setAlignment(Align.top);
		title.setBounds(0,0 - 40,1920, 1080);
		addActor(title);

//		pagesLabel = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
//		pagesLabel.setFontScale(0.8f);
//		pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
//		pagesLabel.setAlignment(Align.center);
//		pagesLabel.setPosition(1750, 93);
//		addActor(pagesLabel);
		
		pages.add(new Group());
		
		maxBetLabel = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		maxBetLabel.setFontScale(1f);
		int value  = game.meters.MAX_BET_PER_LINE * game.meters.MAX_DENOM * game.meters.MAX_LINES;
		String tmp =  (Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10));
		maxBetLabel.setText(game.gameTxt.rulesMAXbet + " " + tmp + " " + game.meters.getCurrency());
		maxBetLabel.setAlignment(Align.top);
		maxBetLabel.setBounds(0,0 - 130,1920, 1080);
		pages.get(0).addActor(maxBetLabel);

		value  = game.meters.getMinBetPerLine() * game.meters.getMinDenom() * game.meters.getMinLines();
		tmp =  (Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10));

		minBetLabel = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		minBetLabel.setFontScale(1f);
		minBetLabel.setText(game.gameTxt.rulesMINbet + " " + tmp + " " + game.meters.getCurrency());
		minBetLabel.setAlignment(Align.top);
		minBetLabel.setBounds(0,0 - 170,1920, 1080);
		pages.get(0).addActor(minBetLabel);

		rulesInterface = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesInterface.setFontScale(1f);
		rulesInterface.setText(game.gameTxt.rulesInterface);
		rulesInterface.setAlignment(Align.left);
		rulesInterface.setBounds(220,700 + 20,1265,0);
		pages.get(0).addActor(rulesInterface);

		game.textures.getInterfaceAtlas().findRegion("button_settings").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		settingsButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_settings")));	//0
		settingsButton.setVisible(true);
		settingsButton.setScale(0.4f);;
		settingsButton.setPosition(210, 730 - 65 * 0 + 20);
		pages.get(0).addActor(settingsButton);

		game.textures.getInterfaceAtlas().findRegion("button_bet").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		menuButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_bet")));	//0
		menuButton.setVisible(true);
		menuButton.setScale(0.4f);;
		menuButton.setPosition(210, 730 - 65 * 1 + 20);
		pages.get(0).addActor(menuButton);

		game.textures.getInterfaceAtlas().findRegion("button_start").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		startButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_start")));	//0
		startButton.setVisible(true);
		startButton.setScale(0.2f);;
		startButton.setPosition(210, 730 - 65 * 2 + 20);
		pages.get(0).addActor(startButton);

		game.textures.getInterfaceAtlas().findRegion("button_autoplay").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		autoButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_autoplay")));	//0
		autoButton.setVisible(true);
		autoButton.setScale(0.4f);;
		autoButton.setPosition(210, 730 - 65 * 3 + 20);
		pages.get(0).addActor(autoButton);
		
		rulesAutoplay = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesAutoplay.setFontScale(1f);
		rulesAutoplay.setText(game.gameTxt.rulesAutoplay);
		rulesAutoplay.setAlignment(Align.left);
		rulesAutoplay.setBounds(220,420 + 20,1265,0);
		pages.get(0).addActor(rulesAutoplay);
		
//		game.textures.getInterfaceAtlas().findRegion("button_start").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		startButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_start")));	//0
//		startButton.setVisible(true);
//		startButton.setScale(0.2f);;
//		startButton.setPosition(210, 370 - 65 * 0 + 20);
//		pages.get(0).addActor(startButton);

		game.textures.getInterfaceAtlas().findRegion("button_autoX").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		autoStopButton = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("button_autoX")));	//0
		autoStopButton.setVisible(true);
		autoStopButton.setScale(0.4f);;
		autoStopButton.setPosition(200, 370 - 65 * 0 + 10 - 60);
		pages.get(0).addActor(autoStopButton);
		
		addActor(pages.get(0));
		
		pages.add(new Group());

		rulesBetMenu = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesBetMenu.setFontScale(1f);
		rulesBetMenu.setText(game.gameTxt.rulesBetMenu);
		rulesBetMenu.setAlignment(Align.left);
		rulesBetMenu.setBounds(220,700 + 40,1265,0);
		pages.get(1).addActor(rulesBetMenu);
		
		game.textures.getMenuBetAtlas().findRegion("minus").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		betMinus = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("minus")));	//0
		betMinus.setVisible(true);
		betMinus.setScale(0.8f);;
		betMinus.setPosition(210 - 20, 730 - 20 - 65 * 0 + 0);
		pages.get(1).addActor(betMinus);
		
		game.textures.getMenuBetAtlas().findRegion("plus").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		betPlus = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("plus")));	//0
		betPlus.setVisible(true);
		betPlus.setScale(0.8f);;
		betPlus.setPosition(210 + 70 - 20, 730 - 20 - 65 * 0 + 0);
		pages.get(1).addActor(betPlus);
		
		
		Group groupMaxBet = new Group();
		
		buttonMaxBet = new ButtonMenuBet(game, 210 + 120, 730 - 65 * 1 + 0, game.textures.getMenuBetAtlas(), "button_maxbet");
		buttonMaxBet.setHover(false);
		buttonMaxBet.commitAssets();
		buttonMaxBet.setText(game.gameTxt.menuBetMaxBet);
//		pages.get(1).addActor(buttonMaxBet);
		groupMaxBet.addActor(buttonMaxBet);
		groupMaxBet.setScale(0.5f);
		pages.get(1).addActor(groupMaxBet);
		groupMaxBet.setPosition(50, 330);

		Group groupBet = new Group();
		buttonBet = new ButtonMenuBet(game, 210 + 120, 730 - 65 * 1 + 0, game.textures.getMenuBetAtlas(), "button_digit");
		buttonBet.setHover(false);
		buttonBet.commitAssets();
		buttonBet.setText("" + tmp);
//		pages.get(1).addActor(buttonMaxBet);
		groupBet.addActor(buttonBet);
		groupBet.setScale(0.5f);
		pages.get(1).addActor(groupBet);
		groupBet.setPosition(50, 330 - 65);
		
		
		rulesSettings = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesSettings.setFontScale(1f);
		rulesSettings.setText(game.gameTxt.rulesSettings);
		rulesSettings.setAlignment(Align.left);
		rulesSettings.setBounds(220,500 - 30,1265,0);
		pages.get(1).addActor(rulesSettings);
		
		addActor(pages.get(1));
		

		pages.add(new Group());
		
		rulesLines = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesLines.setFontScale(1f);
		rulesLines.setAlignment(Align.left);
		rulesLines.setText(game.gameTxt.rulesLines);
		rulesLines.setWrap(false);
		pages.get(2).addActor(rulesLines);
		
		addActor(pages.get(2));

		if(game.languageCode == "BG" || game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "TUR" || game.languageCode == "PL" || game.languageCode == "CZE" || game.languageCode == "RUS"){
			rulesLines2 = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
			rulesLines2.setFontScale(1f);
			rulesLines2.setAlignment(Align.left);
			rulesLines2.setText(game.gameTxt.rulesLines2);
			pages.get(2).addActor(rulesLines2);
			
			addActor(pages.get(2));
		}

		rulesUnfinished = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesUnfinished.setFontScale(1f);
		rulesUnfinished.setAlignment(Align.left);
		rulesUnfinished.setText(game.gameTxt.rulesUnfinished);
		rulesUnfinished.setWrap(false);
		pages.get(2).addActor(rulesUnfinished);
		
		addActor(pages.get(2));
		
		rulesGamePercent = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesGamePercent.setFontScale(1f);
		rulesGamePercent.setAlignment(Align.left);
		
		if(game.gameTxt.rulesGamePercent != null && game.gameTxt.rulesGamePercent.length() > 0 && game.context.gamePercent != "0%" && game.context.gamePercent != "0" && game.context.gamePercent != null){
			rulesGamePercent.setText(game.gameTxt.rulesGamePercent + " " + game.context.gamePercent);
			rulesGamePercent.setWrap(false);
			pages.get(2).addActor(rulesGamePercent);
		}
		
		if(game.languageCode == "ENG"){
			rulesLines.setBounds(220,700 + 50,1265,0);
			rulesUnfinished.setBounds(220,420 + 20,1265,0);
		} else {
			rulesLines.setBounds(220,800,1265,0);
			rulesLines2.setBounds(220,640,1265,0);
			rulesUnfinished.setBounds(220,440,1265,0);
		}
		
		
		if(game.languageCode == "ESP" || game.languageCode == "CZE" || game.languageCode == "RUS" || game.languageCode == "PL"){
			
			maxBetLabel.setBounds(0,0 - 130 + 40,1920, 1080);
			minBetLabel.setBounds(0,0 - 170 + 40,1920, 1080);
			
			settingsButton.setPosition(200, 730 - 65 * 0 + 20 + 30 + 10 + 20);
			menuButton.setPosition(200, 730 - 65 * 1 + 20 + 30 + 10 + 20);
			
			startButton.setPosition(200, 730 - 65 * 2 + 20 + 20 + 10 + 10 + 20);
			
			autoButton.setPosition(200, 730 - 65 * 3 + 10 + 10 + 10 + 10 + 20);
			autoStopButton.setPosition(200, 370 - 65 * 0 + 10 - 50 - 35 + 20);
			
			rulesInterface.setBounds(220,700 + 50+ 20,1265,0);
			rulesAutoplay.setBounds(220,420 + 20+ 20,1265,0);

			
			betMinus.setPosition(210 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);
			betPlus.setPosition(210 + 70 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);

			groupMaxBet.setPosition(50, 330 + 40 + 20);
			groupBet.setPosition(50, 330 - 65 + 30 + 20);
			
			rulesBetMenu.setBounds(220,700 + 40 + 40 + 20,1265,0);
			rulesSettings.setBounds(220,500 - 30 + 10 + 20,1265,0);
			
		}
		
		if(game.languageCode == "POR" || game.languageCode == "TUR"){
			
			maxBetLabel.setBounds(0,0 - 130 + 30,1920, 1080);
			minBetLabel.setBounds(0,0 - 170 + 30,1920, 1080);
			
			settingsButton.setPosition(200, 730 - 65 * 0 + 20 + 30 + 10 + 0);
			menuButton.setPosition(200, 730 - 65 * 1 + 20 + 30 + 10 + 0);
			
			startButton.setPosition(200, 730 - 65 * 2 + 20 + 20 + 10 + 10 + 0);
			
			autoButton.setPosition(200, 730 - 65 * 3 + 10 + 10 + 10 + 10 + 0);
			autoStopButton.setPosition(200, 370 - 65 * 0 + 10 - 50 - 35 + 20 + 0);
			
			rulesInterface.setBounds(220,700 + 50+ 0,1265,0);
			rulesAutoplay.setBounds(220,420 + 20+ 0,1265,0);

			
			betMinus.setPosition(210 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);
			betPlus.setPosition(210 + 70 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);

			groupMaxBet.setPosition(50, 330 + 40 + 20);
			groupBet.setPosition(50, 330 - 65 + 30 + 20);
			
			rulesBetMenu.setBounds(220,700 + 40 + 40 + 20,1265,0);
			rulesSettings.setBounds(220,500 - 30 + 10 + 20,1265,0);
			
		}
		
		if(game.languageCode == "FRA"){
			
			maxBetLabel.setBounds(0,0 - 130 + 35,1920, 1080);
			minBetLabel.setBounds(0,0 - 170 + 35,1920, 1080);
			
			rulesInterface.setBounds(220,700 + 20 + 40,1265,0);
			autoStopButton.setPosition(200, 370 - 65 * 0 + 10 - 80- 02);

			settingsButton.setPosition(200, 730 - 65 * 0 + 20 + 30 + 30);
			menuButton.setPosition(200, 730 - 65 * 1 + 20 + 30 + 30);
			
			startButton.setPosition(200, 730 - 65 * 2 + 20 + 20 + 25);
			autoButton.setPosition(200, 730 - 65 * 3 + 10 + 10 + 25);
			
			rulesBetMenu.setBounds(220,700 + 40 + 40 + 20,1265,0);
			rulesSettings.setBounds(220,500 - 30 + 10 + 20,1265,0);
			
			betMinus.setPosition(210 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);
			betPlus.setPosition(210 + 70 - 10, 730 - 65 * 0 + 0 - 20 + 50 + 20);

			groupMaxBet.setPosition(50, 330 + 40 + 20);
			groupBet.setPosition(50, 330 - 65 + 30 + 20);
		}
		
		if(game.languageCode == "CZE" ){
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 85 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 30,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 30,1265,0);
		} else if(game.languageCode == "PL" ){
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 75 + 50 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 40 + 50,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 20,1265,0);
		} else if(game.languageCode == "RUS" ){
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 75 + 50,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 75 - 5,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 10,1265,0);
		} else if(game.languageCode == "BG" ){
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 75 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 75,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20,1265,0);
		} else if(game.languageCode == "ESP"){
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 95 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 60,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 20,1265,0);
		} else if(game.languageCode == "POR") {
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 95 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 60,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 20,1265,0);
		} else if(game.languageCode == "FRA") {
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 75 + 50,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 40 + 50,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 10,1265,0);
		} else if(game.languageCode == "TUR") {
			rulesLines.setBounds(220,500 - 30 + 200 + 30	    + 85 ,1265,0);
			rulesLines2.setBounds(220,500 - 30 + 200 + 30 - 160 + 30,1265,0);
			rulesUnfinished.setBounds(220,500 - 70 + 20 - 30,1265,0);
		} else {
			rulesLines.setBounds(220,500 - 30 + 200 + 30,1265,0);
			rulesUnfinished.setBounds(220,500 - 30 + 0,1265,0);
		}
		
		rulesGamePercent.setPosition(rulesUnfinished.getX(), rulesUnfinished.getY() - 110);

		if(game.languageCode == "FRA" || game.languageCode == "RUS"){
			rulesGamePercent.setPosition(rulesUnfinished.getX(), rulesUnfinished.getY() - 130);
		}
		
		
		pages.add(new Group());
		
		rulesMaxWin = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesMaxWin.setFontScale(1f);
		rulesMaxWin.setAlignment(Align.left);
		rulesMaxWin.setText(game.gameTxt.rulesMaxWin);
		rulesMaxWin.setBounds(220,690 + 100, 1920 - 170 * 2,0);
		rulesMaxWin.setWrap(true);
		pages.get(3).addActor(rulesMaxWin);

		rulesBuyBonus = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesBuyBonus.setFontScale(1f);
		rulesBuyBonus.setAlignment(Align.left);
		rulesBuyBonus.setText(game.gameTxt.rulesBuyBonus);
		rulesBuyBonus.setBounds(220, 490 + 50 + 50, 1920 - 170 * 2,0);
		rulesBuyBonus.setWrap(true);
		pages.get(3).addActor(rulesBuyBonus);

		if(game.languageCode == "BG"){
			rulesBuyBonus2 = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
			rulesBuyBonus2.setFontScale(1f);
			rulesBuyBonus2.setAlignment(Align.left);
			rulesBuyBonus2.setText(game.gameTxt.rulesBuyBonus2);
			
			rulesMaxWin.setBounds(220,690 + 100 + 50, 1920 - 170 * 2,0);
			rulesBuyBonus.setBounds(220, 490 + 50 + 50 + 50, 1920 - 170 * 2,0);
			rulesBuyBonus2.setBounds(220, 490 + 50 - 70 + 50, 1920 - 170 * 2,0);
			
			rulesBuyBonus2.setWrap(true);
			pages.get(3).addActor(rulesBuyBonus2);
		}
		
		addActor(pages.get(3));
		
		rulesGamePercentBuyFree = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesGamePercentBuyFree.setFontScale(1f);
		rulesGamePercentBuyFree.setAlignment(Align.left);
		
		if(game.gameTxt.rulesGamePercentBuyFree != null && game.gameTxt.rulesGamePercentBuyFree.length() > 0 && game.context.gamePercentBuyFree != "0%" && game.context.gamePercentBuyFree != "0" && game.context.gamePercentBuyFree != null){
			rulesGamePercentBuyFree.setText(game.gameTxt.rulesGamePercentBuyFree + " " + game.context.gamePercentBuyFree);
			rulesGamePercentBuyFree.setWrap(false);
			
			if(game.languageCode == "BG"){
				rulesGamePercentBuyFree.setBounds(220,490 + 50 - 160, 1920 - 170 * 2,0);
			} else {
				rulesGamePercentBuyFree.setBounds(220,490 + 50 - 180, 1920 - 170 * 2,0);
			}
			
			pages.get(3).addActor(rulesGamePercentBuyFree);
		}

		rulesGamePercentBuyHold = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesGamePercentBuyHold.setFontScale(1f);
		rulesGamePercentBuyHold.setAlignment(Align.left);
		
		if(game.gameTxt.rulesGamePercentBuyHold != null && game.gameTxt.rulesGamePercentBuyHold.length() > 0 && game.context.gamePercentBuyHold != "0%" && game.context.gamePercentBuyHold != "0" && game.context.gamePercentBuyHold != null){
			rulesGamePercentBuyHold.setText(game.gameTxt.rulesGamePercentBuyHold + " " + game.context.gamePercentBuyHold);
			rulesGamePercentBuyHold.setWrap(false);
			
			if(game.languageCode == "BG"){
				rulesGamePercentBuyHold.setBounds(220,490 + 50 - 200, 1920 - 170 * 2,0);
			} else {
				rulesGamePercentBuyHold.setBounds(220,490 + 50 - 140, 1920 - 170 * 2,0);
			}
			
			pages.get(3).addActor(rulesGamePercentBuyHold);
		}
		
	pages.add(new Group());
		
		rulesAddFg = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		rulesAddFg.setFontScale(1f);
		rulesAddFg.setAlignment(Align.left);
		rulesAddFg.setText(game.gameTxt.rulesAddFg);
		rulesAddFg.setWrap(true);
		pages.get(4).addActor(rulesAddFg);
		
		addActor(pages.get(4));
		
		
		rulesAddFg.setFontScale(1.3f);
		rulesAddFg.setBounds(220,750, 1700,0);
		rulesAddFg.setWrap(true);
		
		nextButton = new Button(game, 1760,  150, game.textures.getMenuBetAtlas(), "button_arrow_r");
		nextButton.setHover(false);
		nextButton.commitAssets();
		addActor(nextButton);
		
		nextButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
					currentPage++;
					if(currentPage > pages.size() - 1) currentPage = 0;
					showPage(currentPage);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});		
		
		// Next button
//		prevButton = new Button(game, 0,  500, game.textures.getInterfaceCommon(), "left_arrow");
		prevButton = new Button(game, 1660,  150, game.textures.getMenuBetAtlas(), "button_arrow_l");
		prevButton.setHover(false);
		prevButton.commitAssets();
		addActor(prevButton);
		
		prevButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
					currentPage--;
					if(currentPage < 0) currentPage = pages.size() - 1;
					showPage(currentPage);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		
		
		
		
		
	}
	
	public void showPage(int page){

		game.helpScreen.title.setText(game.gameTxt.rulesTitle);

		currentPage = page;
		
		game.helpScreen.pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
		
		for(int i=0; i < pages.size(); i++){
			pages.get(i).setVisible(false);
		}
		pages.get(page).setVisible(true);
		
	}
	
	public void setText(){
		int value  = game.meters.getMinBetPerLine() * game.meters.getMinDenom() * game.meters.getMinLines();
//		String tmp =  (Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10));
		String tmp =  game.meters.formatNumber(value, game.hasBigNumberK == 1);
		minBetLabel.setText(game.gameTxt.rulesMINbet + " " + tmp + " " + game.meters.getCurrency());

		value  = game.meters.MAX_BET_PER_LINE * game.meters.MAX_DENOM * game.meters.MAX_LINES;
//		tmp =  (Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10));
    	tmp =  game.meters.formatNumber(value, game.hasBigNumberK == 1);
		maxBetLabel.setText(game.gameTxt.rulesMAXbet + " " + tmp + " " + game.meters.getCurrency());
	}
	
}
