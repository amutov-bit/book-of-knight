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
import com.pgd.game.actors.ButtonPaytable;
import com.pgd.game.base.IButtonCallback;

public class HelpScreen extends Group {
	
	private Image bg;
	private Image bgReels;
//	private Image splash2;
//	private Image splash3;
//	private Image bg4;
//	private Image bg5;
	private HelpScreenPaylines lines;
	private HelpScreenPaytable paytable;
	private HelpScreenPaytable2 paytable2;
	public  HelpScreenSettings settings;
	public  HelpScreenRules    gameRules;
	public  HelpScreenSplash   splash;
	public  HelpScreenSplash2   splash2;
	Label		       pagesLabel;
//	private HelpScreenPaytable3 paytable3;
	
//	private Button helpButton;
	
	private Button backToGame;
	private Button nextButton;
	private Button prevButton;
	private ButtonPaytable closeButton;
	private ButtonPaytable buttonHistory;

	private ButtonPaytable buttonPay;
	private ButtonPaytable buttonSetting;
	private ButtonPaytable buttonRules;
	
	private int currentPage;
	
	public boolean howToPlay = false;
	
	private List<ButtonDot> pages;
	
	Label title;
	
	BookOfKnight game;
	
	public HelpScreen(BookOfKnight game) {
		this.game = game;
		pages = new ArrayList<ButtonDot>();
		this.setVisible(false);
	}

	public void loadAssets(){
		game.ondemandAssetManager.load("bg_menu.png", Texture.class);		
	}
	
	public void commitAssets() {
	    
		Gdx.app.debug("HelpScreen ", "show()");
		currentPage = 0;
		
		
//		#93a7bf 147, 167, 191
		bg = new Image(game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class));
		bg.setPosition(0, 0);
				
		paytable = new HelpScreenPaytable(game, 0);
		paytable.commitAssets();

		paytable2 = new HelpScreenPaytable2(game, 0);
		paytable2.commitAssets();
		
		paytable.setPosition(0, 0);
		paytable2.setPosition(0, 0);
		
		pages.add(new ButtonDot(game, 575       , 103 + 5, game.textures.getInterfaceCommon(), "dot"));
		pages.add(new ButtonDot(game, 575 + 35*1, 103 + 5, game.textures.getInterfaceCommon(), "dot"));
		pages.add(new ButtonDot(game, 575 + 35*2, 103 + 5, game.textures.getInterfaceCommon(), "dot"));
		pages.add(new ButtonDot(game, 575 + 35*3, 103 + 5, game.textures.getInterfaceCommon(), "dot"));
		pages.add(new ButtonDot(game, 538 + 35*4, 93 + 5, game.textures.getInterfaceCommon(), "dot"));
		
		
		addActor(bg);
		addActor(paytable);
		addActor(paytable2);
//		addActor(splash3);
		
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setText("");
		title.setAlignment(Align.top);
		title.setBounds(0,0 - 40,1920, 1080);
		addActor(title);
		
		lines = new HelpScreenPaylines(game);
		lines.commitAssets();
		lines.setVisible(false);
		lines.setPosition(0, 0);
		addActor(lines);
		
		settings = new HelpScreenSettings(game);
		settings.commitAssets();
		settings.setVisible(false);
		settings.setPosition(0, 0);
		addActor(settings);

		gameRules = new HelpScreenRules(game);
		gameRules.commitAssets();
		gameRules.setVisible(false);
		gameRules.setPosition(0, 0);
		addActor(gameRules);

		splash = new HelpScreenSplash(game);
		splash.commitAssets();
		splash.setVisible(false);
		splash.setPosition(0, 0);
		addActor(splash);

		splash2 = new HelpScreenSplash2(game);
		splash2.commitAssets();
		splash2.setVisible(false);
		splash2.setPosition(0, 0);
		addActor(splash2);
		
		pagesLabel = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		pagesLabel.setFontScale(0.8f);
		pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
		pagesLabel.setAlignment(Align.center);
		pagesLabel.setPosition(1750, 93);
		addActor(pagesLabel);
		
//		title = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("logo")));
//		title.setPosition(394, 800 - 76);
//		title.setVisible(true);
//		addActor(title);
		
		for(int i = 0; i < pages.size(); i++)
		{
			pages.get(i).commitAssets();
//			addActor(pages.get(i));
		}
		
		pages.get(0).toggled();
		
		
		registerPages();
		
//		bg.setVisible(true);
		lines.setVisible(false);
		paytable.setVisible(false);
		paytable2.setVisible(false);
//		paytable2.setVisible(false);
		
		// Next button
//		helpButton = new Button(game, 1280 - 134,  720 - 50, game.textures.getAutoPlayAtlas(), "b_rules");
//		helpButton.setHover(true);
//		helpButton.commitAssets();
//		addActor(helpButton);
		
//		helpButton.registerCallback(new IButtonCallback() {
//			
//			@Override
//			public void onRelease() {
//				game.gsLink.showHowToPlay();
////				howToPlay = true;
//			}
//			
//			@Override
//			public void onPress() {
//			}
//		});	
		
//			private Button buttonPay;
//		private Button buttonSetting;
//		private Button buttonRules;
		
		buttonHistory = new ButtonPaytable(game, 1394  , 1080 - 768 - 172, game.textures.getMenuBetAtlas(), "button_history");
		buttonHistory.setHover(false);
		buttonHistory.commitAssets();
		buttonHistory.setText(game.gameTxt.helpHistory);
		addActor(buttonHistory);
		
		buttonHistory.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.gsLink.onHistoryButton();
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		buttonPay = new ButtonPaytable(game, 354 , 1080 - 768 - 172, game.textures.getMenuBetAtlas(), "button_pay");
		buttonPay.setHover(false);
		buttonPay.commitAssets();
		buttonPay.setText(game.gameTxt.helpPay);
		buttonPay.toggled();
		addActor(buttonPay);
		buttonPay.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
//				game.helpScreen.title.setText(game.gameTxt.paytableTitle);
				
				game.helpScreen.title.setText(game.gameTxt.splashTitle);
				
				buttonPay.toggled();
				buttonSetting.reset();
				buttonRules.reset();
				
				currentPage = 0;
				
				paytable.setVisible(false);
				paytable2.setVisible(false);
				splash.setVisible(true);
				splash2.setVisible(false);
				lines.setVisible(false);
				prevButton.setVisible(true);
				nextButton.setVisible(true);
				
				gameRules.setVisible(false);
				
				settings.setVisible(false);
				
				pagesLabel.setVisible(true);
				pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());

			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		
		buttonSetting = new ButtonPaytable(game, 874 , 1080 - 768 - 172, game.textures.getMenuBetAtlas(), "button_sett");
		buttonSetting.setHover(false);
		buttonSetting.commitAssets();
		buttonSetting.setText(game.gameTxt.helpSettings);
		addActor(buttonSetting);
		buttonSetting.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				buttonPay.reset();
				buttonSetting.toggled();
				buttonRules.reset();
				
				paytable.setVisible(false);
				paytable2.setVisible(false);
				splash.setVisible(false);
				splash2.setVisible(false);
				lines.setVisible(false);
				prevButton.setVisible(false);
				nextButton.setVisible(false);
				
				gameRules.setVisible(false);
				
				settings.show();
				settings.setVisible(true);
				
				pagesLabel.setVisible(false);
				pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		
		
		buttonRules = new ButtonPaytable(game, 1394  , 1080 - 768 - 172, game.textures.getMenuBetAtlas(), "button_rules");
		buttonRules.setHover(false);
		buttonRules.commitAssets();
		buttonRules.setText(game.gameTxt.helpRules);
		addActor(buttonRules);
		
		buttonRules.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				buttonPay.reset();
				buttonSetting.reset();
				buttonRules.toggled();
				
				paytable.setVisible(false);
				paytable2.setVisible(false);
				splash.setVisible(false);
				splash2.setVisible(false);
				lines.setVisible(false);
				prevButton.setVisible(false);
				nextButton.setVisible(false);
				
				gameRules.showPage(0);
				gameRules.setVisible(true);
				
				settings.setVisible(false);
				
				pagesLabel.setVisible(true);
//				pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
				
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		backToGame = new Button(game, 30 , 1080 - 162 - 820, game.textures.getInterfaceAtlas(), "button_settings");
		backToGame.setHover(true);
		backToGame.commitAssets();
		addActor(backToGame);
		
		backToGame.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
//				if(prevScreen.prevScreen!= null)
				if(!howToPlay){
					game.overlay.changeTitle(false);
					setVisible(false);
					game.helpScreen.setVisible(false);
					game.helpScreen.updatePaytable();
					game.reels.hideReels(false);
					game.menu.resetButtons();
					game.menu.showPaytable = false;
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		closeButton = new ButtonPaytable(game, 1711, 1080 - 21 - 131, game.textures.getMenuBetAtlas(), "button_close");
		closeButton.setHover(false);
		closeButton.commitAssets();
		addActor(closeButton);
		
		closeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
//				if(prevScreen.prevScreen!= null)
				if(!howToPlay){
					setVisible(false);
					game.overlay.changeTitle(false);
					game.helpScreen.setVisible(false);
					game.helpScreen.updatePaytable();
					game.reels.hideReels(false);
					game.menu.resetButtons();
					game.menu.showPaytable = false;
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		
		// Next button
//		nextButton = new Button(game, 1800,  500, game.textures.getInterfaceCommon(), "right_arrow");
		nextButton = new Button(game, 1760,  150, game.textures.getMenuBetAtlas(), "button_arrow_r");
		nextButton.setHover(false);
		nextButton.commitAssets();
		addActor(nextButton);
		
		nextButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				if(!howToPlay){
					currentPage++;
					if(currentPage > pages.size() - 1) currentPage = 0;
					showPage(currentPage);
				}
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
				if(!howToPlay){
					currentPage--;
					if(currentPage < 0) currentPage = pages.size() - 1;
					showPage(currentPage);
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

	}

	public void showPage(int page)
	{
		
		pagesLabel.setVisible(true);
		pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());
		
		if(page >= 0){
			for(int i = 0; i < pages.size();i++)
				if(i == page)	pages.get(i).toggled();
				else			pages.get(i).reset();
		}	
		
//		bg.setVisible(true);
		
		switch(page){
			case 2:
				
				game.helpScreen.title.setText(game.gameTxt.paytableTitle);
				
				paytable.setVisible(true);
				paytable.update();
				paytable2.setVisible(false);
				paytable2.update();
				
				splash.setVisible(false);
				splash2.setVisible(false);
				
				lines.setVisible(false);
			break;
			case 0:
				game.helpScreen.title.setText(game.gameTxt.splashTitle);
				paytable.setVisible(false);
				paytable.update();
				paytable2.setVisible(false);
				paytable2.update();
				
				splash.setVisible(true);
				splash2.setVisible(false);
				
				lines.setVisible(false);
			break;
			case 1:
				game.helpScreen.title.setText(game.gameTxt.splashSecTitle);
				paytable.setVisible(false);
				paytable.update();
				paytable2.setVisible(false);
				paytable2.update();
				
				splash.setVisible(false);
				splash2.setVisible(true);
				
				lines.setVisible(false);
				break;
			case 3:
				
				game.helpScreen.title.setText(game.gameTxt.paytableTitle);
				
				paytable.setVisible(false);
				paytable.update();
				paytable2.setVisible(true);
				paytable2.update();
				
				splash.setVisible(false);
				splash2.setVisible(false);
				
				lines.setVisible(false);
				break;
			case 4:
				
				game.helpScreen.title.setText(game.gameTxt.paylinesTitle);
				
				paytable.setVisible(false);
				paytable.update();
				paytable2.setVisible(false);
				paytable2.update();

				splash.setVisible(false);
				splash2.setVisible(false);

				lines.setVisible(true);
				break;
				default:
				break;
		}
		
		paytable.update();
		paytable2.update();
//		paytable3.update();
	}
	
	
	public void registerPages(){
		pages.get(0).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				currentPage = 0;
				showPage(currentPage);
			}
			
			@Override
			public void onPress() {
			}
		});
		
		pages.get(1).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				currentPage = 1;
				showPage(currentPage);
			}
			
			@Override
			public void onPress() {
			}
		});
		
//		pages.get(2).registerCallback(new IButtonCallback() {
//			
//			@Override
//			public void onRelease() {
//				currentPage = 2;
//				showPage(currentPage);
//			}
//			
//			@Override
//			public void onPress() {
//			}
//		});	
		
//		pages.get(3).registerCallback(new IButtonCallback() {
//			
//			@Override
//			public void onRelease() {
//				currentPage = 3;
//				showPage(currentPage);
//			}
//			
//			@Override
//			public void onPress() {
//			}
//		});	
//		
//		pages.get(4).registerCallback(new IButtonCallback() {
//			
//			@Override
//			public void onRelease() {
//				currentPage = 4;
//				showPage(currentPage);
//			}
//			
//			@Override
//			public void onPress() {
//			}
//		});	
//		
//		pages.get(5).registerCallback(new IButtonCallback() {
//			
//			@Override
//			public void onRelease() {
//				currentPage = 5;
//				showPage(currentPage);
//			}
//			
//			@Override
//			public void onPress() {
//			}
//		});	
		
}

	
	
	public void setPage(int page){
		currentPage = page;
		showPage(page);
	}
	
	public void show(){
		buttonPay.toggled();
		buttonSetting.reset();
		buttonRules.reset();
		
		currentPage = 0;
		
		
		float offsetW = (game.context.hasHistoryButton) ? 320 : 520;
		
		game.helpScreen.title.setText(game.gameTxt.splashTitle);
		
		pagesLabel.setText(game.gameTxt.helpPages + " " + (currentPage + 1) + "/" + pages.size());

		buttonPay.setPosition(354 + 0 * offsetW, 1080 - 768 - 172);
		buttonSetting.setPosition(354 + 1 * offsetW, 1080 - 768 - 172);
		  buttonRules.setPosition(354 + 2 * offsetW, 1080 - 768 - 172);
		buttonHistory.setPosition(354 + 3 * offsetW, 1080 - 768 - 172);
		
			buttonHistory.setVisible(game.context.hasHistoryButton);
		
		pagesLabel.setVisible(true);
		
		paytable.setVisible(false);
		paytable2.setVisible(false);
		splash.setVisible(true);
		splash2.setVisible(false);
		lines.setVisible(false);
		prevButton.setVisible(true);
		nextButton.setVisible(true);
		
		gameRules.setVisible(false);
		
		settings.setVisible(false);
	}
	
	public void updatePaytable(){
		buttonPay.toggled();
		buttonSetting.reset();
		buttonRules.reset();
		paytable.update();
		paytable2.update();
//		paytable3.update();
	}
	
	public void hideHowToPlay(){
		howToPlay = false;
	}
	
}
