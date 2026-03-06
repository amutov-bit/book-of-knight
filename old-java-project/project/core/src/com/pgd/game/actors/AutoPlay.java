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


public class AutoPlay extends Group {
	
	BookOfKnight game;
	
	public Image bg;


	private Label title;
	
	private Button 				 closeButton;

	private List<ButtonAutoPlay> spins;

	ButtonAutoPlay startAutoPlay;

	private int spinsValue [] = {10, 20, 50, 100, 1000};
	
	private int autoPlayCount = 1000;	//unlimited
	
	Group group = new Group();
	
	private ButtonSettings turboGame;
	private ButtonSettings skipScreen;
	
	private Label turboTxtLabel;
	
	private Label autoNumbersTxtLabel;
	
	private Color color;
	
	public AutoPlay(BookOfKnight game) {
		this.game = game;
		
		spins = new ArrayList<ButtonAutoPlay>();
		
		this.setPosition(0, 0);
	}
	
	public void loadAssets() {
		
	}
	
	
	public void commitAssets() {
		
		game.textures.getAutoPlayAtlas().findRegion("bg_auto").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getAutoPlayAtlas().findRegion("bg_auto")));
		group.addActor(bg);
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setAlignment(Align.center | Align.top);
		title.setText(game.gameTxt.autoPlayTitle);
		group.addActor(title);

		autoNumbersTxtLabel = new Label(" ", new LabelStyle(game.fonts.font54px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		autoNumbersTxtLabel.setFontScale(1f);
		autoNumbersTxtLabel.setAlignment(Align.center);
		autoNumbersTxtLabel.setText(game.gameTxt.autoNumbersTxtLabel);
		group.addActor(autoNumbersTxtLabel);


		closeButton = new Button(game, (int)bg.getX() + (int)bg.getWidth() - 125, (int)bg.getY() + (int)bg.getHeight() - 110 , game.textures.getMenuBetAtlas(), "button_closesmall");
		closeButton.setHover(false);
		closeButton.commitAssets();
		group.addActor(closeButton);
		closeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				hide();
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		spins.add(new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "button_digit"));
		spins.add(new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "button_digit"));
		spins.add(new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "button_digit"));
		spins.add(new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "button_digit"));
		spins.add(new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "button_digit"));
		
		
		startAutoPlay = new ButtonAutoPlay(game, 0, 0, game.textures.getAutoPlayAtlas(), "start_autospins");
		startAutoPlay.setHover(false);
		startAutoPlay.commitAssets();
		startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");
		group.addActor(startAutoPlay);
		startAutoPlay.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				hideStart();
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		
		
		
		for(int i = 0; i < spins.size(); i++)
		{
			spins.get(i).setHover(false);
			spins.get(i).commitAssets();
			spins.get(i).setText(""+spinsValue[i]);
			
			group.addActor(spins.get(i));
		}

		spins.get(0).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				autoPlayCount = spinsValue[0];
				
				startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");

				spins.get(0).toggled();
				spins.get(1).reset();
				spins.get(2).reset();
				spins.get(3).reset();
				spins.get(4).reset();
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	
		
		spins.get(1).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				autoPlayCount = spinsValue[1];
				
				startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");
				
				spins.get(0).reset();
				spins.get(1).toggled();
				spins.get(2).reset();
				spins.get(3).reset();
				spins.get(4).reset();

				
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	

		spins.get(2).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				autoPlayCount = spinsValue[2];
				
				startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");
				
				spins.get(0).reset();
				spins.get(1).reset();
				spins.get(2).toggled();
				spins.get(3).reset();
				spins.get(4).reset();

			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	

		spins.get(3).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				autoPlayCount = spinsValue[3];
				
				startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");
				
				spins.get(0).reset();
				spins.get(1).reset();
				spins.get(2).reset();
				spins.get(3).toggled();
				spins.get(4).reset();

			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	

		spins.get(4).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				autoPlayCount = spinsValue[4];
				
				spins.get(0).reset();
				spins.get(1).reset();
				spins.get(2).reset();
				spins.get(3).reset();
				spins.get(4).toggled();

				
				startAutoPlay.setText(game.gameTxt.autoPlayStart + " (" + autoPlayCount + ")");
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);

			}
		});	

		spins.get(4).toggled();
		
		turboTxtLabel = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(185/255f, 197/255f, 211/255f, 255f/255f)));
		turboTxtLabel.setFontScale(1f);
		turboTxtLabel.setText(game.gameTxt.settingsTurboSpinTxt);
		turboTxtLabel.setAlignment(Align.center);
		group.addActor(turboTxtLabel);
		
		turboGame = new ButtonSettings(game, 357, 1080 - 510 - 106, game.textures.getMenuBetAtlas(), "turbo_bg");
		turboGame.setHover(false);
		turboGame.commitAssets();
		turboGame.setText(game.gameTxt.settingsTurboSpin);
		group.addActor(turboGame);
		
		turboGame.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.context.turboGame = !game.context.turboGame;
				if(game.context.turboGame){
					turboGame.toggled();
				} else{
					turboGame.reset();
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		skipScreen= new ButtonSettings(game, 357, 1080 - 510 - 106, game.textures.getMenuBetAtlas(), "skipscreen_bg");
		skipScreen.setHover(false);
		skipScreen.commitAssets();
		skipScreen.setText(game.gameTxt.settingsSkipScreen);
		group.addActor(skipScreen);
		
		skipScreen.reset();
		
		skipScreen.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				
				game.context.skipScreen = !game.context.skipScreen;
				
				game.gsLink.console("game.context.skipScreen = " + game.context.skipScreen);
				
				if(game.context.skipScreen){
					skipScreen.toggled();
				} else{
					skipScreen.reset();
				}
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		addActor(group);

			float offsetX = -125 - 300;
			float offsetY = -70 - 20;
			
			bg.setPosition(1278 + offsetX, 1080 - 896 - 0 + offsetY);
			bg.setRotation(0f);
			bg.setScale(1f, 1f);
			
			startAutoPlay.setPosition(bg.getX() + 48, bg.getY() + 45);
			
			title.setBounds(bg.getX(), bg.getY() - 20 - 4, bg.getWidth(), bg.getHeight() - 70);
			
			
			turboGame.setPosition(bg.getX() + (bg.getWidth() - turboGame.getWidth())/2 - 4, bg.getY() + bg.getHeight() - turboGame.getHeight() * 2 - 80 -20);
			turboTxtLabel.setBounds(turboGame.getX() - 0,turboGame.getY() - 70,turboGame.getWidth(), turboGame.getHeight());
			skipScreen.setPosition(turboGame.getX(), turboGame.getY() - 159);
			closeButton.setPosition((int)bg.getX() + (int)bg.getWidth() - 125 - 40 - 70, (int)bg.getY() + (int)bg.getHeight() - 110 - 35 - 70);
			
			autoNumbersTxtLabel.setBounds(bg.getX(), bg.getY() - 20 -20, bg.getWidth(), bg.getHeight());
			spins.get(0).setPosition(1388 + offsetX, 1080 - 240 - 180 - 127 + offsetY -20 -20);
			spins.get(1).setPosition(spins.get(0).getX() + spins.get(0).getWidth() + 17, 1080 - 240 - 180 - 127 + offsetY -20-20);
			spins.get(2).setPosition(spins.get(1).getX() + spins.get(1).getWidth() + 17, 1080 - 240 - 180 - 127 + offsetY -20-20);

			spins.get(3).setPosition(1388 + offsetX + spins.get(0).getWidth() / 2, 1080 - 240 - 180 - 127 * 2 + offsetY -20-20);
			spins.get(4).setPosition(spins.get(3).getX() + spins.get(0).getWidth() + 17, 1080 - 240 - 180 - 127 * 2 + offsetY -20-20);

	}
	
	public void hideStart(){
		game.context.autoplay = true;
		
		game.menu.autoButton.toggled();
		
		if(autoPlayCount == 1000){
			game.context.autoplayUnlimited = true;
		} else {
			game.context.autoplayUnlimited = false;
		}
		

		game.context.autoplayWinUnlimited = true;
		
		game.context.autoplayLostUnlimited = true;
		
		game.context.autoplayCounter = autoPlayCount;
		
		game.menu.autoplayStopButton.setText("" + (autoPlayCount - 1), (autoPlayCount - 1));;
		
		game.menu.startButton.reset();
		
		if(game.menu.autoButton.isEnable())
		{
			switch(game.controller.getState())
			{
				case IDLE:
				case REELS_SPINNING:
				case REELS_STOPPING:
				case FREE_GAMES_END:
				case SHOW_LAST_WINS:
					game.controller.event = Event.START;
				break;
				case  WIN_TO_CREDIT:
					game.controller.event = Event.TAKEWIN;
				break;
			default:
				break;
			}
		}
		
		game.menu.setStatus("");
		
		game.autoPlayMenu.setVisible(false);
		
		game.menu.autoplayStopButton.setVisible(true);
		game.menu.autoButton.setVisible(false);
		
//		game.menu.startAutoPlayButton.setVisible(true);
		game.menu.startButton.setVisible(true);
		game.menu.stopButton.setVisible(false);
	}
	
	public void hide(){
		game.menu.paytableButton.reset();
		game.menu.betButton.reset();
		game.menu.autoButton.reset();
		game.menu.startButton.reset();
		
		game.autoPlayMenu.setVisible(false);
		game.menu.setStatus("");

	}
	
	
	public void show(){
		
		
		game.menu.setStatus(game.gameTxt.autoStatusTxt);
		autoPlayCount = 1000;
		
		game.context.autoplayCurrentLost = 0;
		
		for(int i = 0; i < spins.size() - 1; i++)
		{
			spins.get(i).reset();
		}
		
		if(game.context.turboGame){
			turboGame.toggled();
		} else {
			turboGame.reset();
		}

		if(game.context.skipScreen){
			skipScreen.toggled();
		} else{
			skipScreen.reset();
		}

		color = getColor();
		
		turboTxtLabel.setColor(color.r, color.g, color.b, 1.0f);
		
		if(!game.context.turboSpinIsEnabled){
			turboGame.disable();
			turboTxtLabel.setColor(color.r, color.g, color.b, 0.2f);
		}
	
		
		spins.get(4).toggled();
	}
	
	
}
