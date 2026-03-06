package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.base.AbstractController.Event;


public class AddFreeSpins extends Group {
	
	BookOfKnight game;
	
	private Image frame;
	private Image bg, base, baseLeft, baseRight;
	public ButtonAutoPlay claimButton, okButton;
	public Button closeButton;
	
	private Label freespinsTitle;		
	private Label freespinsCongrats;	
	private Label freespinsGet;		
	private Label freespinsNumber;		
	private Label freespinsTxt;		
	
	
	private Label 	freespinsLeft;
	private Label 	freespinsleftNumber;
	private Label 	freespinsTotalWin;
	private Label 	freespinsTotalNumber;
	private Label 	freespinsTotalWinCurrency;
	private Label 	freespinsBet;
	private Label   freespinsBetNumber;
	private Label   freespinsBetCurrency;
	
	private Label freespinsCongratsTitle;
	private Label freespinsCongratsYou;	
	private Label freespinsCongratsWon;	
	private Label freespinsCongratsNumber; 
	private Label freespinsCongratsTxt;	
	
	GlyphLayout glyphLayout = new GlyphLayout();
			
//	private Digits digitsFrameFirst;
	
	Group groupConfirm = new Group();
	Group groupFSwin   = new Group();
	Group groupFScongrats   = new Group();

	
	public AddFreeSpins(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
		loadAssets();		
//		animate = false;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
//		frame = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("podlojka")));
//		frame.setPosition(240 - 170 - 20, 720 - 565 - 50 - 40);
//		addActor(frame);
		
//		#93a7bf 147, 167, 191
		
		bg = new Image(game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class));
		bg.setPosition(0, 0);
		
		bg.getColor().a = 0.7f;
		
		addActor(bg);
		
		game.textures.getInterfaceAddFreeSpins().findRegion("freegames_popup_bg").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		base = new Image(new Sprite(game.textures.getInterfaceAddFreeSpins().findRegion("freegames_popup_bg")));
		base.setPosition((1920 - 874)/2, (1080 - 712)/2);
		addActor(base);

//		141, 171, 236 #8dabec
		freespinsTitle = new Label(" ", new LabelStyle(game.fonts.font36fs, new Color(141/255f, 171/255f, 236/255f, 255f/255f)));
		freespinsTitle.setFontScale(1.1f);
		freespinsTitle.setAlignment(Align.center | Align.top);
		freespinsTitle.setText(game.gameTxt.freespinsTitle);
		
		if(game.languageCode == "BG"){
			freespinsTitle.setBounds(base.getX() + 15, base.getY() - 80, base.getWidth(), base.getHeight());
		} else {
			freespinsTitle.setBounds(base.getX(), base.getY() - 100, base.getWidth(), base.getHeight());
		}
		
		groupConfirm.addActor(freespinsTitle);


		//255, 255, 255
		freespinsCongrats = new Label(" ", new LabelStyle(game.fonts.font22fs, new Color(255/255f, 255/255f, 255/255f, 255f/255f)));
		freespinsCongrats.setFontScale(1.1f);
		freespinsCongrats.setAlignment(Align.center);
		freespinsCongrats.setText(game.gameTxt.freespinsCongrats);
		freespinsCongrats.setBounds(base.getX(), base.getY() - 100 + 215, base.getWidth(), base.getHeight());
		groupConfirm.addActor(freespinsCongrats);

		//255, 255, 255
		freespinsGet = new Label(" ", new LabelStyle(game.fonts.font22fs, new Color(255/255f, 255/255f, 255/255f, 255f/255f)));
		freespinsGet.setFontScale(1.1f);
		freespinsGet.setAlignment(Align.center);
		freespinsGet.setText(game.gameTxt.freespinsGet);
		freespinsGet.setBounds(base.getX(), base.getY() - 100 + 215 - 40, base.getWidth(), base.getHeight());
		groupConfirm.addActor(freespinsGet);

		//132, 168, 255 #84a8ff
		freespinsNumber = new Label(" ", new LabelStyle(game.fonts.font62fs, new Color(132/255f, 168/255f, 255/255f, 255f/255f)));
		freespinsNumber.setFontScale(1.1f);
		freespinsNumber.setAlignment(Align.center);
		freespinsNumber.setText("10");
		freespinsNumber.setBounds(base.getX(), base.getY() - 100 + 215 - 120, base.getWidth(), base.getHeight());
		groupConfirm.addActor(freespinsNumber);

		//132, 168, 255 #84a8ff
		freespinsTxt = new Label(" ", new LabelStyle(game.fonts.font27fs, new Color(132/255f, 168/255f, 255/255f, 255f/255f)));
		freespinsTxt.setFontScale(1.1f);
		freespinsTxt.setAlignment(Align.center);
		freespinsTxt.setText(game.gameTxt.freespinsTxt);
		freespinsTxt.setBounds(base.getX(), base.getY() - 100 + 215 - 120 - 60, base.getWidth(), base.getHeight());
		groupConfirm.addActor(freespinsTxt);

		claimButton = new ButtonAutoPlay(game, 0, 0, game.textures.getInterfaceAddFreeSpins(), "b_confirm");
		claimButton.setHover(false);
		claimButton.commitAssetsWhite();
		claimButton.setText(game.gameTxt.buttonClaim);
		claimButton.setPosition(base.getX() + 283, base.getY() + 133 );
		groupConfirm.addActor(claimButton);
		
		claimButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				if(!game.gameAssetsManager.introAnim.isVisible()){
					game.menu.paytableButton.reset();
					
					game.menu.autoButton.disable();
					
					game.menu.betButton.disable();
					
					game.menu.buyBonusButton.disable();
					
					game.menu.startButton.reset();
					
					game.menu.changeWinStatusPosition();
					
					game.context.claimAddFreeSpins = true;
					
					groupConfirm.setVisible(false);
					base.setVisible(false);
					bg.setVisible(false);
				}
			}
			
			@Override
			public void onPress() {
			}
		});
		
		closeButton = new Button(game, (int)(base.getX() + base.getWidth() - 170), (int)(base.getY() + base.getHeight() - 185), game.textures.getMenuBetAtlas(), "button_close");
		closeButton.setHover(false);
		closeButton.commitAssets();
		closeButton.setScale(0.6f);;
		groupConfirm.addActor(closeButton);
		
		closeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				if(!game.gameAssetsManager.introAnim.isVisible()){
					game.context.claimAddFreeSpins = false;
					game.context.hasAddFreeSpins = false;
	
					game.menu.resetButtons();
					
					groupConfirm.setVisible(false);
					groupFSwin.setVisible(false);
					base.setVisible(false);
					bg.setVisible(false);
				}
			}
			
			@Override
			public void onPress() {
			}
		});
		
		addActor(groupConfirm);

		game.textures.getInterfaceAddFreeSpins().findRegion("field_fg_left").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		baseLeft = new Image(new Sprite(game.textures.getInterfaceAddFreeSpins().findRegion("field_fg_left")));
		baseLeft.setPosition(535, 1080 - 922 - 160 - 20);
		groupFSwin.addActor(baseLeft);

		game.textures.getInterfaceAddFreeSpins().findRegion("field_fg_right").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		baseRight = new Image(new Sprite(game.textures.getInterfaceAddFreeSpins().findRegion("field_fg_right")));
		baseRight.setPosition(765, 1080 - 922 - 160 - 20);
		groupFSwin.addActor(baseRight);
		
		
		//#9ebaff 158, 186, 255
		freespinsLeft = new Label(" ", new LabelStyle(game.fonts.font20fs, new Color(158/255f, 186/255f, 255/255f, 255f/255f)));
		freespinsLeft.setFontScale(1.1f);
		freespinsLeft.setAlignment(Align.left);
		freespinsLeft.setText(game.gameTxt.freespinsLeft);
		freespinsLeft.setBounds(baseRight.getX() + 100, baseRight.getY() - 20 + 40, baseRight.getWidth(), baseRight.getHeight());
		groupFSwin.addActor(freespinsLeft);

		//#cef1ff 206, 241, 255
		freespinsleftNumber = new Label(" ", new LabelStyle(game.fonts.font32fs, new Color(206/255f, 241/255f, 255/255f, 255f/255f)));
		freespinsleftNumber.setFontScale(1.1f);
		freespinsleftNumber.setAlignment(Align.left);
		freespinsleftNumber.setText(game.gameTxt.freespinsleftNumber);
		
        glyphLayout.setText(game.fonts.font20fs, game.gameTxt.freespinsLeft);
        float offsetX = 0;//glyphLayout.width * 960/540f;
        offsetX = glyphLayout.width * 1.1f;
        float label2X = freespinsLeft.getX() /*+ glyphLayout.width*/ + offsetX + 10f;
		freespinsleftNumber.setBounds(label2X, baseRight.getY() - 20 + 40 + 3, baseRight.getWidth(), baseRight.getHeight());
		groupFSwin.addActor(freespinsleftNumber);

		//#9ebaff 158, 186, 255
		freespinsTotalWin = new Label(" ", new LabelStyle(game.fonts.font20fs, new Color(158/255f, 186/255f, 255/255f, 255f/255f)));
		freespinsTotalWin.setFontScale(1.1f);
		freespinsTotalWin.setAlignment(Align.left);
		freespinsTotalWin.setText(game.gameTxt.freespinsTotalWin);
		freespinsTotalWin.setBounds(baseRight.getX()  + 100, baseRight.getY() - 20, baseRight.getWidth(), baseRight.getHeight());
		groupFSwin.addActor(freespinsTotalWin);

		//#b9ff3e 185, 255, 62
		freespinsTotalNumber = new Label(" ", new LabelStyle(game.fonts.font32fs, new Color(185/255f, 255/255f, 62/255f, 255f/255f)));
		freespinsTotalNumber.setFontScale(1.1f);
		freespinsTotalNumber.setAlignment(Align.left);
		freespinsTotalNumber.setText(game.meters.formatNumber(0, false));
        glyphLayout.setText(game.fonts.font20fs, game.gameTxt.freespinsTotalWin);
        offsetX = 0;//glyphLayout.width * 960/540f;
        offsetX = glyphLayout.width * 1.1f;
        label2X = freespinsLeft.getX() /*+ glyphLayout.width*/ + offsetX + 15f;
        freespinsTotalNumber.setBounds(label2X, baseRight.getY() - 20 + 4, baseRight.getWidth(), baseRight.getHeight());
        groupFSwin.addActor(freespinsTotalNumber);

        freespinsTotalWinCurrency = new Label(" ", new LabelStyle(game.fonts.font20fs, new Color(158/255f, 186/255f, 255/255f, 255f/255f)));
        freespinsTotalWinCurrency.setFontScale(1.1f);
        freespinsTotalWinCurrency.setAlignment(Align.left);
        freespinsTotalWinCurrency.setText("EUR");
        glyphLayout.setText(game.fonts.font32fs, game.meters.formatNumber(0, false));
        offsetX = 0;//glyphLayout.width * 960/540f;
        offsetX = glyphLayout.width * 1.1f;
        label2X = freespinsTotalNumber.getX() /*+ glyphLayout.width*/ + offsetX + 10f;
        freespinsTotalWinCurrency.setBounds(label2X, baseRight.getY() - 20 - 1, baseRight.getWidth(), baseRight.getHeight());
        groupFSwin.addActor(freespinsTotalWinCurrency);

		// #ffc600 255, 198, 0
		freespinsBet = new Label(" ", new LabelStyle(game.fonts.font20fs, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));
		freespinsBet.setFontScale(1.2f);
		freespinsBet.setAlignment(Align.center);
		freespinsBet.setText(game.gameTxt.freespinsBet);
		freespinsBet.setBounds(baseLeft.getX() + 10, baseLeft.getY() - 30 + 32 + 32, baseLeft.getWidth(), baseLeft.getHeight());
		groupFSwin.addActor(freespinsBet);

		// 231, 248, 255 #e7f8ff
		freespinsBetNumber = new Label(" ", new LabelStyle(game.fonts.font32fs, new Color(231/255f, 248/255f, 255/255f, 255f/255f)));
		freespinsBetNumber.setFontScale(1.2f);
		freespinsBetNumber.setAlignment(Align.center);
		freespinsBetNumber.setText(game.gameTxt.freespinsBetNumber);
		freespinsBetNumber.setBounds(baseLeft.getX() + 10, baseLeft.getY() - 30 + 32, baseLeft.getWidth(), baseLeft.getHeight());
		groupFSwin.addActor(freespinsBetNumber);
		
		// #ffc600 255, 198, 0
		freespinsBetCurrency = new Label(" ", new LabelStyle(game.fonts.font20fs, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));
		freespinsBetCurrency.setFontScale(1.2f);
		freespinsBetCurrency.setAlignment(Align.center);
		freespinsBetCurrency.setText("EUR");
		freespinsBetCurrency.setBounds(baseLeft.getX() + 10, baseLeft.getY() - 30, baseLeft.getWidth(), baseLeft.getHeight());
		groupFSwin.addActor(freespinsBetCurrency);
		
		addActor(groupFSwin);
		
//		141, 171, 236 #8dabec
		freespinsCongratsTitle = new Label(" ", new LabelStyle(game.fonts.font36fs, new Color(141/255f, 171/255f, 236/255f, 255f/255f)));
		freespinsCongratsTitle.setFontScale(1.1f);
		freespinsCongratsTitle.setAlignment(Align.center | Align.top);
		freespinsCongratsTitle.setText(game.gameTxt.freespinsCongratsTitle);
		if(game.languageCode == "BG"){
			freespinsCongratsTitle.setBounds(base.getX(), base.getY() - 70, base.getWidth(), base.getHeight());
		} else {
			freespinsCongratsTitle.setBounds(base.getX(), base.getY() - 100, base.getWidth(), base.getHeight());
		}
		groupFScongrats.addActor(freespinsCongratsTitle);


		//255, 255, 255
		freespinsCongratsYou = new Label(" ", new LabelStyle(game.fonts.font22fs, new Color(255/255f, 255/255f, 255/255f, 255f/255f)));
		freespinsCongratsYou.setFontScale(1.1f);
		freespinsCongratsYou.setAlignment(Align.center);
		freespinsCongratsYou.setText(game.gameTxt.freespinsCongratsYou);
		freespinsCongratsYou.setBounds(base.getX(), base.getY() - 100 + 215, base.getWidth(), base.getHeight());
		groupFScongrats.addActor(freespinsCongratsYou);

		//255, 255, 255
		freespinsCongratsWon = new Label(" ", new LabelStyle(game.fonts.font22fs, new Color(255/255f, 255/255f, 255/255f, 255f/255f)));
		freespinsCongratsWon.setFontScale(1.1f);
		freespinsCongratsWon.setAlignment(Align.center);
		freespinsCongratsWon.setText(game.gameTxt.freespinsCongratsWon);
		freespinsCongratsWon.setBounds(base.getX(), base.getY() - 100 + 215 - 40, base.getWidth(), base.getHeight());
		groupFScongrats.addActor(freespinsCongratsWon);

		//132, 168, 255 #84a8ff
		freespinsCongratsNumber = new Label(" ", new LabelStyle(game.fonts.font62fs, new Color(132/255f, 168/255f, 255/255f, 255f/255f)));
		freespinsCongratsNumber.setFontScale(1.1f);
		freespinsCongratsNumber.setAlignment(Align.center);
		freespinsCongratsNumber.setText(game.meters.formatNumber(1000, false));
		freespinsCongratsNumber.setBounds(base.getX(), base.getY() - 100 + 215 - 120, base.getWidth(), base.getHeight());
		groupFScongrats.addActor(freespinsCongratsNumber);

		//132, 168, 255 #84a8ff
		freespinsCongratsTxt = new Label(" ", new LabelStyle(game.fonts.font27fs, new Color(132/255f, 168/255f, 255/255f, 255f/255f)));
		freespinsCongratsTxt.setFontScale(1.1f);
		freespinsCongratsTxt.setAlignment(Align.center);
		freespinsCongratsTxt.setText(game.gameTxt.freespinsCongratsTxt);
		freespinsCongratsTxt.setBounds(base.getX(), base.getY() - 100 + 215 - 120 - 60, base.getWidth(), base.getHeight());
		groupFScongrats.addActor(freespinsCongratsTxt);

		okButton = new ButtonAutoPlay(game, 0, 0, game.textures.getInterfaceAddFreeSpins(), "b_confirm");
		okButton.setHover(false);
		okButton.commitAssetsWhite();
		okButton.setText("OK");
		okButton.setPosition(base.getX() + 283, base.getY() + 133 );
		groupFScongrats.addActor(okButton);
		
		okButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
//				if(!game.gameAssetsManager.introAnim.isVisible()){
//					groupConfirm.setVisible(false);
//					groupFScongrats.setVisible(false);
//					groupFSwin.setVisible(false);
//					base.setVisible(false);
//					bg.setVisible(false);
//				}
				game.controller.event = Event.ADD_FREE_SPINS_TAKE_WIN;
			}
			
			@Override
			public void onPress() {
			}
		});
		addActor(groupFScongrats);
		
		groupFScongrats.setVisible(false);
	}
	
	public void setFreeGamesCnt(int cnt){
		freespinsleftNumber.setText("" + cnt);
		freespinsNumber.setText("" + cnt);
		freespinsNumber.setText("" + cnt);
		
		freespinsBetCurrency.setText("" + game.meters.getCurrency());
		freespinsTotalWinCurrency.setText("" + game.meters.getCurrency());
		freespinsCongratsTxt.setText("" + game.meters.getCurrency());
	}

	public void setFreeGamesLeftCnt(int cnt){
		freespinsleftNumber.setText("" + cnt);
	}

	public void setFreeGamesBet(int cnt){
		freespinsBetNumber.setText("" + game.meters.formatNumber(cnt, game.hasBigNumberK == 1));
	}

	public void setFreeGamesWin(int win){
		freespinsTotalNumber.setText("" + game.meters.formatNumber(win, game.hasBigNumberK == 1));
        glyphLayout.setText(game.fonts.font32fs, game.meters.formatNumber(win, false));
        float offsetX = glyphLayout.width * 1.1f;
        float label2X = freespinsTotalNumber.getX() /*+ glyphLayout.width*/ + offsetX + 10f;
        freespinsTotalWinCurrency.setBounds(label2X, baseRight.getY() - 20 - 1, baseRight.getWidth(), baseRight.getHeight());
        groupFSwin.addActor(freespinsTotalWinCurrency);
        
	}

	public void setFreeGamesCongratsWin(int win){
		freespinsCongratsNumber.setText("" + game.meters.formatNumber(win, game.hasBigNumberK == 1));
		bg.setVisible(true);
		base.setVisible(true);
		groupConfirm.setVisible(false);
		groupFScongrats.setVisible(true);
	}

	public void showCloseButton(boolean show){
		closeButton.setVisible(show);
	}
	
	
}
