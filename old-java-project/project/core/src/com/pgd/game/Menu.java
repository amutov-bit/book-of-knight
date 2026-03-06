package com.pgd.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
//import com.pgd.game.Context.GameMode;
import com.pgd.game.Controller.State;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.ButtonLine;
import com.pgd.game.actors.ButtonMult;
import com.pgd.game.actors.JackpotShowWin;
import com.pgd.game.actors.ShowWin;
//import com.pgd.game.actors.BoostMenu;
import com.pgd.game.base.AbstractController.Event;
import com.pgd.game.base.ControlField;
import com.pgd.game.base.Field;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.screens.HelpScreen;
import com.pgd.game.states.ControllerListener;

/**
 * Menu class file.
 * 
 * Menu creates game user interface using buttons and fields.
 * Touch event listeners are attached and invoked when user interacts with the UI. 

 * @author Dimitar
 *
 */
public class Menu extends Group implements ControllerListener {

	private BookOfKnight game;
	
	private Field winStatusField;
	public  Field creditField;
	private Field winField;
	private Image winImage;
	private Field statusField;
	public Field totalBetField;
	
	public Button buyBonusButton;
	
	public Button startButton;
	public Button stopButton;
//	public Button startAutoPlayButton;
	public Button autoButton;
	public Button autoplayStopButton;

//	public Button autoStopButton;
	public Button betButton;
	public Button paytableButton;

	public Button lobbyButton;
	
//	public Button boosLineButton1;
//	public Button boosLineButton2;
	
	public Image bottomMenu;
	
	Array<TextureAtlas.AtlasRegion> regions;

	public boolean showPaytable = false;
	
	public boolean isBetPress = false;
	
	public  JackpotShowWin showJackpotWin;

	
//	public BoostMenu menuLines;
	
	public Menu(final BookOfKnight game) {
		super();
		this.game = game;
				
		
		setPosition(0, 0);
		setWidth(1280);
		setHeight(100);
		setBounds(getX(), getY(), getWidth(), getHeight());
		
		
	}		


public void addListeners(){
	
		startButton.registerCallback(new IButtonCallback() {
			
			boolean buttonHeldDown = false;
			
			Timer.Task heldDownTask;
			  
			@Override
			public void onRelease() {
//				game.sounds.resume();
				buttonHeldDown = false;
				
				if(game.menu.startButton.isEnable())
				{
					switch(game.controller.getState())
					{
						case SHOW_WINS:
						case HOLD_AND_WIN_START_SPIN:
							game.controller.event = Event.START;
						break;
						case IDLE:
						case REELS_SPINNING:
						case REELS_STOPPING:
						case FREE_GAMES_END:
						case SHOW_LAST_WINS:
						case SHOW_LAST_WINS_RESTORE:
						case SHOW_BIG_WIN:
						case SPIN_END:
						case SHOW_ALL_WINNING_LINES:
						case FREE_GAMES:
							game.controller.event = Event.START;
						break;
						case  BONUS_END:
						case  WIN_TO_CREDIT:
							game.controller.event = Event.TAKEWIN;
						break;
						default:
						break;
					}
//					game.sounds.play(SoundTrack.BUTTON_START, false);
				}
//				game.sounds.playBackground(true);
				
		        // Cancel the timer task if it exists
		        if (heldDownTask != null) {
		            heldDownTask.cancel();
		        }
			}
			
			@Override
			public void onPress() {
				game.gsLink.fullScreenCustom();
				
				buttonHeldDown = true;

				if(game.context.turboGame){
					buttonHeldDown = false;
				}
				
		        // Start a timer to check if the button is held down for a certain duration
				 heldDownTask = Timer.schedule(new Timer.Task() {
		            @Override
		            public void run() {
		                // Check if the button is still being held down after the specified duration
		            	Gdx.app.debug("Start Button", " hold for 1 sec");
		            	if(game.overlay.menuTurboSpinsIsEnalbled()){
			            	if (buttonHeldDown) {
								if(game.menu.startButton.isEnable())
								{
									switch(game.controller.getState())
									{
										case IDLE:
										case SHOW_LAST_WINS:
										case SHOW_LAST_WINS_RESTORE:
											buttonHeldDown = true;
											game.overlay.showMenuTurboSpins();
										break;
									}
								}
			            	}
		            	}
		            }
		        }, 1f); // Adjust the duration as needed (1 second in this example)
			}
		});
		
		stopButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
//				game.sounds.resume();
				
//				game.sounds.playBackground(true);
			}
			
			@Override
			public void onPress() {
				game.gsLink.fullScreenCustom();
				if(game.menu.stopButton.isEnable())
				{
					switch(game.controller.getState())
					{
					case REELS_SPINNING:
					case REELS_STOPPING:
					case REELS_HIGHLIGHT_STOPPING:
						game.controller.event = Event.START;
						break;
					default:
						break;
					}
//					game.sounds.play(SoundTrack.BUTTON_START, false);
				}
			}
		});
		
		
		betButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				if(!isBetPress){
					game.menu.paytableButton.disable();
					game.menu.startButton.disable();
					game.menu.autoButton.disable();
					game.betMenu.setVisible(true);
					game.betMenu.show();
				} else {
					game.menu.paytableButton.reset();
					
					game.menu.startButton.reset();
					game.menu.autoButton.reset();
					game.betMenu.setVisible(false);
					game.betMenu.hide();
				}
				
//				isBetPress = !isBetPress;
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});		
		
		autoButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				Gdx.app.debug("controller", "On pressssssssssssssssssssssssssssssssssss");
				if(game.context.autoplay) {
					game.context.autoplay = false;
					autoButton.reset();
					game.menu.autoButton.disable();
					setLeftStatus("");
					game.menu.setStatus("");
				}
//				else if(game.meters.credit > game.meters.getTotalBet()){
					if(game.autoPlayMenu.isVisible()){
						game.menu.paytableButton.reset();
						game.menu.betButton.reset();
						game.menu.autoButton.reset();
						
						game.menu.startButton.reset();
						game.autoPlayMenu.setVisible(false);
						game.menu.setStatus("");
					} else {
						game.menu.autoButton.reset();
						
						game.menu.paytableButton.disable();
						game.menu.betButton.disable();
						game.menu.startButton.disable();
						game.autoPlayMenu.setVisible(true);
						
						game.autoPlayMenu.show(); 
					}
//				}
			}
			/*
			 * <cod mode bb>
			 */
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});
		
		autoplayStopButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				
					game.context.autoplay = false;
					autoButton.reset();
					game.menu.autoButton.disable();
					setLeftStatus("");
				
				autoplayStopButton.setVisible(false);
				autoButton.setVisible(true);
				
//				startButton.setVisible(true);
				
			}
			/*
			 * <cod mode bb>
			 */
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});
		
		paytableButton.registerCallback(new IButtonCallback() {
			@Override
			public void onPress() {
				
				showPaytable = !showPaytable;
				
				game.gsLink.console("presssssssssssssssssssssssssssssssss showPaytable = " + showPaytable);
				if(showPaytable){
					
					game.helpScreen.show();
					game.overlay.changeTitle(true);
					game.helpScreen.setVisible(true);
					game.helpScreen.updatePaytable();
//					game.reels.hideReels(true);
					startButton.disable();
					autoButton.disable();
					betButton.disable();
				} else {
					game.overlay.changeTitle(false);
					game.helpScreen.setVisible(false);
					game.helpScreen.updatePaytable();
//					game.reels.hideReels(false);
					resetButtons();
				}
				
			}

			@Override
			public void onRelease() {
				// TODO Auto-generated method stub
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
				
			}
		});		
		
		
		buyBonusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.buyBonusMenu.setVisible(true);
			}
			
			/*
			 * <cod mode bb>
			 */
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
				game.gsLink.fullScreenCustom();
			}
		});
		lobbyButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.gsLink.onHomeButton();
			}
			
			/*
			 * <cod mode bb>
			 */
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
	}

	public void loadAssets() {
		Gdx.app.debug("Menu", "loadAssets()");
	}
    
	public void commitAssets() {
		
		/************************************************************
		 * Buttons
		 ***********************************************************/
		startButton = new Button(game,  1616 ,  1080 - 300 - 368, game.textures.getInterfaceAtlas(), "button_start");
		startButton.setHover(true);

		stopButton = new Button(game,  1616 ,  1080 - 300 - 368, game.textures.getInterfaceAtlas(), "button_stop");
		stopButton.setHover(true);
		
//		startAutoPlayButton = new Button(game, 1616 ,  1080 - 300 - 368, game.textures.getInterfaceAtlas(), "button_start_autoplay");
//		startAutoPlayButton.setHover(true);
//		startAutoPlayButton.setVisible(false);
		
		autoButton = new Button(game, 1728 , 1080 - 88 - 162, game.textures.getInterfaceAtlas(), "button_autoplay");
		autoButton.setHover(true);

		autoplayStopButton = new Button(game, 1728 , 1080 - 88 - 162, game.textures.getInterfaceAtlas(), "button_autoX");
		autoplayStopButton.setHover(true);
		autoplayStopButton.setVisible(false);
		
		betButton = new Button(game, 1728 , 1080 - 820 - 162, game.textures.getInterfaceAtlas(), "button_bet");
		betButton.setHover(true);
		
		lobbyButton = new Button(game, 83, 0, game.textures.getInterfaceAtlas(), "button_home");
		lobbyButton.setHover(false);
		
		paytableButton = new Button(game, 30 , 1080 - 162 - 820, game.textures.getInterfaceAtlas(), "button_settings");
		paytableButton.setHover(true);
		
//		x:0, y: 430px, w:194px h:164px
		buyBonusButton = new Button(game,  0 ,  1080 - 164 - 430, game.textures.getInterfaceAtlas(), "bb_yellow");
		buyBonusButton.setHover(false);

		
		startButton.commitAssets();
		stopButton.commitAssets();
		autoButton.commitAssets();
		autoplayStopButton.commitAssets();
		betButton.commitAssets();
		paytableButton.commitAssets();
		lobbyButton.commitAssets();

		buyBonusButton.commitAssets();

		game.menu.stopButton.setVisible(false);
		
		
//		for(int i = 0; i < game.meters.MAX_LINES; i++){
//			lines.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("line"+(i+1)))));
//			lines.get(i).setVisible(true);
//			addActor(lines.get(i));
//		}

		addActor(autoButton);
		addActor(autoplayStopButton);
		addActor(paytableButton);
		addActor(startButton);
		addActor(stopButton);
		addActor(betButton);
		addActor(buyBonusButton);

//		##f3f9ff
		statusField = new Field(game, game.fonts.font36px, new LabelStyle(game.fonts.font36px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)), new LabelStyle(game.fonts.font36px, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));

//		leftStatusField = new Field(game, null, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)));
		winStatusField = new Field(game, game.fonts.font34px, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)), new LabelStyle(game.fonts.font34px, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));

//		#ffcc00
//		winField = new Field(game, null, new LabelStyle(game.fonts.font36pxWin, new Color(255/255f, 204/255f, 0/255f, 255f/255f)));
		winField = new Field(game, game.fonts.font36pxWin, new LabelStyle(game.fonts.font36pxWin, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)), new LabelStyle(game.fonts.font36pxWin, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));

		//#f9efc8 249, 239, 200
//		totalBetField = new Field(game, null, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)));
		totalBetField = new Field(game, game.fonts.font34px, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)), new LabelStyle(game.fonts.font34px, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));

//		##f9efc8  249, 239, 200
//		creditField = new Field(game, null, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)));
		creditField = new Field(game, game.fonts.font34px, new LabelStyle(game.fonts.font34px, new Color(243f/255f, 249f/255f, 249f/255f, 255f/255f)), new LabelStyle(game.fonts.font34px, new Color(255/255f, 198/255f, 0/255f, 255f/255f)));

		winImage = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("win_field")));
		winImage.setPosition(0, 46);
		winImage.setVisible(true);
		addActor(winImage);
		
		game.textures.getInterfaceAtlas().findRegion("bottom_bg").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bottomMenu = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("bottom_bg")));
		bottomMenu.setPosition(0, 0);
		addActor(bottomMenu);
		
		addActor(totalBetField);
		addActor(winField);
		addActor(statusField);
		addActor(winStatusField);
		addActor(creditField);

		addListeners();
		
		
		totalBetField.commitAssets();
		totalBetField.setPosition(480 * 3 + 50, 7, 0 , 0);
		totalBetField.setTextAlignment(Align.left);
		totalBetField.setTextWidth(480);
		
		
		winField.commitAssets(); 
		winField.setPosition(0, (int)winImage.getY() + 45,0 ,0);
		winField.setTextAlignment(Align.center);
		winField.setTextWidth(1920);
		
		statusField.commitAssets();
		statusField.setPosition(0, (int) winImage.getY() + 29, 0 , 0);
		statusField.setFontScale(1f, 1f);
		statusField.setTextWidth(1920);
		statusField.setTextAlignment(Align.center);

		winStatusField.commitAssets();
		winStatusField.setPosition(0, (int) (winImage.getY() + 10), 0 , 0);
		winStatusField.setFontScale(1f, 1f);
		winStatusField.setTextWidth(1920);
		winStatusField.setTextAlignment(Align.center);
		
		creditField.commitAssets(); 
		creditField.setPosition(220, 7, 0 ,0);
		creditField.setTextWidth(480);
		creditField.setTextAlignment(Align.left);
		
		
		
		
		showJackpotWin  = new JackpotShowWin(game);
		showJackpotWin.commitAssets();
		showJackpotWin.setVisible(false);		
		showJackpotWin.setWin(0);
		
		setPosition(0, 0);
		setWidth(1170);
		setHeight(200);
		setBounds(getX(),  getY(), getWidth(), getHeight());
		Gdx.app.debug("Menu", "commitAssets()");
	}

	public void addActors(Stage stage) {
		Gdx.app.debug("Menu", "addActors()");
	}
	
	public void showJackpotWin(boolean show){
		showJackpotWin.setVisible(show);
	}
	public void setJackpotWin(long win){
		showJackpotWin.setWin(win);
	}
    
	public void setStatus(String string) {		
		if(string == ""){
			winField.setVisible(true);
			winStatusField.setVisible(true);
		} else {
			winField.setVisible(false);
			winStatusField.setVisible(false);
		}
		
		statusField.setStatusText(string);
	}	

	public void changeWinStatusPosition(){
		if(game.context.hasAddFreeSpins && game.context.addFreeSpinsCnt > 0){
			winStatusField.setPosition(0, (int) (winImage.getY() + 10) + 59, 0 , 0);
			winField.setPosition(0, (int)winImage.getY() + 45 - 120,0 ,0);
		} else {
			winStatusField.setPosition(0, (int) (winImage.getY() + 10), 0 , 0);
			winField.setPosition(0, (int)winImage.getY() + 45,0 ,0);
		}
	}
	
	
	public void setWinStatus(String string) {		
		winStatusField.setStatusText(string);
	}	

	public void setLeftStatus(String string) {		
	}	

	public void setRoundId(long roundId) {
		if(roundId == 0){
			game.roundId.setText("");
		} else {
			game.roundId.setText("" + roundId);
		}
	}
	
	public void setTotalBet(long totalBet) {
		
//
//		game.gsLink.console("totalBet = " + totalBet);
		Gdx.app.debug("Menu ", "totalBet = " + totalBet);
		if(totalBet == 0) {
			totalBetField.setText("");
		}
		else if(totalBetField.getFormat()) { // money format
				totalBetField.setValueBet(totalBet/**game.meters.getDenomination()*/, false);
		}
		else { // in credits 
//			totalBetField.setValue(totalBet);
			totalBetField.setText("");
		}
		
		game.buyBonusMenu.setTotalBet(totalBet/**game.meters.getDenomination()*/);
		game.betMenu.setTotalBet(totalBet/**game.meters.getDenomination()*/);
		
		game.overlay.jackpots.update();
	}	
	
	public void setWin(long win) {
		//winField.setText(win>0? new Integer(win).toString() : "");

		if(win == 0) {
//			winImage.setVisible(false);
			winField.setText("");
		}
		else if(winField.getFormat()) { // money format
			
			Gdx.app.debug("Menu ", "win = " + win);
//			winImage.setVisible(true);
			winField.setValueWin(win, false);
//			winField.setFontScale(1.0f * 1.00f);
		}
	}	
	
	public void setCredit(long credit) {
		if(!game.context.hasAddFreeSpins){
			creditField.setValueBalance(credit, false);
		}
	}		
	
	public void setBonus(int credit) {
		
	}
	
	public void updateMeters() {
		if(!game.context.hasAddFreeSpins){
			setTotalBet(game.meters.getTotalBet());
			setCredit(game.meters.credit);
			setWin(game.meters.win);
		}
	}

	public void updateMetersStartScreen() {
		setTotalBet(game.meters.getTotalBet());
		creditField.setValueBalance(game.meters.credit, false);
		setWin(game.meters.win);
	}
	
	@Override
	public void onStartSpin() {
		
		creditField.setTouchable(Touchable.disabled);
		winField.setTouchable(Touchable.disabled);
		
		startButton.toggled();
	
	}

	@Override
	public void onReelsStop() {
		setStatus("");
//		startButton.reset();
	}

	@Override
	public void onFreeGamesWin() {

	}

	@Override
	public void onSpinEnd() {
//		creditField.setTouchable(Touchable.enabled);
//		winField.setTouchable(Touchable.enabled);
	}

	@Override
	public void onTakeWin() {

	}

	@Override
	public void onShowWins() {
//		if(game.context.gameMode == GameMode.MAIN_GAME) { 
			//autoButton.setVisible(false);
//		}
	}

	@Override
	public void onWinToCredit() {

	}

	@Override
	public void onFreeGamesTakeWins() {
		setLeftStatus("");
	}


	public void resetButtons(){
		if(!game.context.hasAddFreeSpins){
			startButton.reset();
			autoButton.reset();
			betButton.reset();
			paytableButton.reset();
			buyBonusButton.reset();
		} else {
			startButton.reset();
			paytableButton.reset();
		}
	}
	
	public void resetMenuButtons(){
		if(!game.context.hasAddFreeSpins){
			game.menu.paytableButton.reset();
			game.menu.betButton.reset();
			game.menu.autoButton.reset();
			game.menu.buyBonusButton.reset();
		} else {
			game.menu.paytableButton.reset();
		}
	}

	public void resetButtonWithDelay(){
		
		Timer.schedule(new Timer.Task() {
	        @Override
	        public void run() {
	            startButton.reset();
	            autoButton.reset();
	            betButton.reset();
	            paytableButton.reset();
	            buyBonusButton.reset();
	        }
		 }, 0.4f);
	}
	
	public void disableButtons(){
		startButton.disable();
		autoButton.disable();
		betButton.disable();
		paytableButton.disable();
		buyBonusButton.disable();
	}


}
