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
import com.pgd.game.actors.ButtonSettings;
import com.pgd.game.base.IButtonCallback;

public class HelpScreenSettings extends Group {
	

	private ButtonSettings sounds;
	private ButtonSettings soundFx;
	private ButtonSettings music;
	private ButtonSettings skipIntro;
	private ButtonSettings turboGame;
	
	private Image bgVolume;
	
	private boolean isSound   = true;
	private boolean isSoundFx = true;
	private boolean iSMusic   = true;
	
	private int currentVolume = 5; 
	
	private int volume[] = {0, 20, 40, 60, 80, 100}; 
	
	private Button plusButton;
	private Button minusButton;

	private ButtonMenuBet homeButton;
	
	BookOfKnight game;
	
	private Label titleVolume;
	private Label volumeLabel;
	
	private Label turboTxtLabel;
	
	private Color color;
	
	public HelpScreenSettings(BookOfKnight game) {
		this.game = game;
		this.setVisible(false);
	}

	public void loadAssets(int currentFile){
		
	}
	
	public void commitAssets() {
	    
		Gdx.app.debug("HelpScreen ", "show()");
		
		game.textures.getMenuBetAtlas().findRegion("volume_bg").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bgVolume = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("volume_bg")));
		addActor(bgVolume);
		
//		#b9c5d3 185, 197, 211
		titleVolume = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(185/255f, 197/255f, 211/255f, 255f/255f)));
		titleVolume.setFontScale(1f);
		titleVolume.setText(game.gameTxt.settingsVolume);
//		titleVolume.setAlignment(Align.left);
		addActor(titleVolume);

		volumeLabel = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(185/255f, 197/255f, 211/255f, 255f/255f)));
		volumeLabel.setFontScale(1f);
		volumeLabel.setText("100");
		volumeLabel.setAlignment(Align.center);
		addActor(volumeLabel);
		
		turboTxtLabel = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(185/255f, 197/255f, 211/255f, 255f/255f)));
		turboTxtLabel.setFontScale(1f);
		turboTxtLabel.setText(game.gameTxt.settingsTurboSpinTxt);
		turboTxtLabel.setAlignment(Align.center);
		addActor(turboTxtLabel);
		
		homeButton = new ButtonMenuBet(game, (int)bgVolume.getX(), (int)bgVolume.getY() - 220, game.textures.getMenuBetAtlas(), "button_menu_home");
		homeButton.setHover(false);
		homeButton.commitAssets();
		homeButton.setText(game.gameTxt.settingsLobby);
		addActor(homeButton);
		
		plusButton = new Button(game, 1385, 1080 - 274 - 60, game.textures.getMenuBetAtlas(), "plus");
		plusButton.setHover(false);
		plusButton.commitAssets();
		addActor(plusButton);

		minusButton = new Button(game, 1170, 1080 - 274 - 60, game.textures.getMenuBetAtlas(), "minus");
		minusButton.setHover(false);
		minusButton.commitAssets();
		addActor(minusButton);
		
		homeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				Gdx.app.exit();
				game.gsLink.onHomeButton();
				game.sounds.play(SoundTrack.KNOCK, false);
			}				
			
			@Override
			public void onPress() {
			}
		});
		
		
		minusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				if(--currentVolume < 0) currentVolume = volume.length - 1;
				volumeLabel.setText("" + volume[currentVolume]);
				game.sounds.setVolume(volume[currentVolume]);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		plusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				if(++currentVolume >= volume.length) currentVolume = 0;
				volumeLabel.setText("" + volume[currentVolume]);
				game.sounds.setVolume(volume[currentVolume]);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		
				
		sounds = new ButtonSettings(game, 357, 1080 - 197 - 106, game.textures.getMenuBetAtlas(), "sound_bg");
		sounds.setHover(false);
		sounds.commitAssets();
		sounds.setText(game.gameTxt.settingsSoundOn);
		addActor(sounds);
		
		sounds.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				isSound   = !isSound;
				isSoundFx = !isSoundFx;
				iSMusic   = !iSMusic;
				
				if(isSound){
					sounds.toggled();
					soundFx.toggled();
					music.toggled();
				} else {
					sounds.reset();
					soundFx.reset();
					music.reset();
				}
				
				game.sounds.setSound(0, isSound);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		soundFx = new ButtonSettings(game, 357, 1080 - 351 - 106, game.textures.getMenuBetAtlas(), "soundfx_bg");
		soundFx.setHover(false);
		soundFx.commitAssets();
		soundFx.setText(game.gameTxt.settingsGameSounds);
		addActor(soundFx);
		
		soundFx.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				isSoundFx = !isSoundFx;
				if(isSoundFx){
					soundFx.toggled();
				} else {
					soundFx.reset();
				}
				
				game.sounds.setSound(1, isSoundFx);

			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		music = new ButtonSettings(game, 357, 1080 - 510 - 106, game.textures.getMenuBetAtlas(), "music_bg");
		music.setHover(false);
		music.commitAssets();
		music.setText(game.gameTxt.settingsMusic);
		addActor(music);
		
		music.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				iSMusic = !iSMusic;
				if(iSMusic){
					music.toggled();
				} else {
					music.reset();
				}
				
				game.sounds.setSound(2, iSMusic);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		skipIntro = new ButtonSettings(game, 357, 1080 - 510 - 106, game.textures.getMenuBetAtlas(), "skipscreen_bg");
		skipIntro.setHover(false);
		skipIntro.commitAssets();
		skipIntro.setText(game.gameTxt.settingsSkipIntro);
		addActor(skipIntro);
		
		skipIntro.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.context.skipIntro = !game.context.skipIntro;
				if(game.context.skipIntro){
					skipIntro.toggled();
				} else{
					skipIntro.reset();
				}
			}
			
			@Override
			public void onPress() {
			}
		});	

		turboGame = new ButtonSettings(game, 357, 1080 - 510 - 106, game.textures.getMenuBetAtlas(), "turbo_bg");
		turboGame.setHover(false);
		turboGame.commitAssets();
		turboGame.setText(game.gameTxt.settingsTurboSpin);
		addActor(turboGame);
		
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
			}
		});	
		
		
		sounds.toggled();
		soundFx.toggled();
		music.toggled();

		sounds.setPosition(357, 1080 - 197 - 106 + 35);
		soundFx.setPosition(357, 1080 - 351 - 106 + 35);
		music.setPosition(357, 1080 - 510 - 106 + 35);
		bgVolume.setPosition(357, 1080 - 664 - 106 + 35);
			
		plusButton.setPosition((int)bgVolume.getX() + (int)bgVolume.getWidth() - 120, (int)bgVolume.getY() - 5);
		minusButton.setPosition((int)bgVolume.getX() + 240, (int)bgVolume.getY() - 5);
		
		homeButton.setPosition(1063, 1080 - 197 - 106 + 35);
		skipIntro.setPosition(1063, 1080 - 351 - 106 + 35);
		turboGame.setPosition(1063, 1080 - 510 - 106 + 35);
			
		titleVolume.setBounds(bgVolume.getX() + 80,bgVolume.getY() /*- 20*/,bgVolume.getWidth(), bgVolume.getHeight());
		volumeLabel.setBounds(bgVolume.getX() + (int)bgVolume.getWidth() - 380,bgVolume.getY(),bgVolume.getWidth(), bgVolume.getHeight());
			
//			turboTxtLabel.setBounds(bgVolume.getX() + (int)bgVolume.getWidth() - 380,bgVolume.getY(),bgVolume.getWidth(), bgVolume.getHeight());
		turboTxtLabel.setBounds(turboGame.getX() - 0,turboGame.getY() - 70,turboGame.getWidth(), turboGame.getHeight());

	}
	
	public void show(){
	
		game.helpScreen.title.setText(game.gameTxt.settingsTitle);

		if(game.context.skipIntro){
			skipIntro.toggled();
		} else {
			skipIntro.reset();
		}

		if(game.context.turboGame){
			turboGame.toggled();
		} else {
			turboGame.reset();
		}
		
		
		color = getColor();

		turboTxtLabel.setColor(color.r, color.g, color.b, 1.0f);
		
		if(!game.context.turboSpinIsEnabled){
			turboGame.disable();
			turboTxtLabel.setColor(color.r, color.g, color.b, 0.2f);
		}

		homeButton.setVisible(game.context.hasHomeButton);
		
	}
	
	public void setSound(boolean active){
		
		isSound   =active;
		isSoundFx =active;
		iSMusic   =active;
		
		if(active){
			sounds.toggled();
			soundFx.toggled();
			music.toggled();
		} else {
			sounds.reset();
			soundFx.reset();
			music.reset();
		}
	}
	
}
