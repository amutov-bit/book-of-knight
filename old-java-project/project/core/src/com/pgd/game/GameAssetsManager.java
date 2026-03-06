package com.pgd.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.Controller.State;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.ButtonDot;
import com.pgd.game.actors.InsertCredits;
import com.pgd.game.actors.IntroAnim;
import com.pgd.game.actors.LoadingPanel;
import com.pgd.game.actors.MenuSound;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.screens.HelpScreenPaytable;
import com.pgd.game.screens.HelpScreenPaytable.TextsLabels;

public class GameAssetsManager {

	private BookOfKnight game;

	public boolean finishLoading;
	public int currentFile;

	private int MAX_SCENES = 2;

	private int currentScreen;
	private int currentFrame;

	boolean timeout;

	public Image bgSound;

//	public Image splash;
//	public Image logo;
	public Image title;

	private TextureAtlas loadingAtlas;
	private Array<AtlasRegion> loadingRegions;

	private AtlasRegion empty;
//	private Texture loaded;
//	private Texture slider;
	private float sliderPosX = (1920 - 600)/2;
	private float sliderPosY = (1080 - 164)/2;
	private float sliderProgressX = 0;

	private long currentTime;

	private long waitingTime;

//	public Button playButton;

	private boolean download = true;

	// private boolean showButtons;

	private boolean playButtonPressed;

	private Stage stage;

	private int sliderPrev = 0;

	private int step = 580 / 12;

	private float timeElapsed;
	private float lastTimeElapsed;
	
	public MenuSound menuSound;
	
	public boolean isSoundChoosed = false;
//	private Label txtWinUp;
//	private Label txtAdd;
//	public Label tapTo;

	LoadingPanel loadingPanel;
	public IntroAnim 	 introAnim;

	Group groupLoading = new Group();
	public Group groupIntro = new Group();

	Texture texture;

	public Label tapTo;
	
	public GameAssetsManager(BookOfKnight game) {

		download = true;

		this.game = game;

		currentTime = System.currentTimeMillis();
		waitingTime = System.currentTimeMillis();

		timeout = false;

		game.showButtons = false;

		playButtonPressed = false;

		loadingPanel = new LoadingPanel(game);
		
		introAnim 	 = new IntroAnim(game);

	}

	public void loadAssets(Stage stage) {

//		splash = new Image(game.ondemandAssetManager.getAssetManager().get("splash.jpg", Texture.class));

		currentTime = System.currentTimeMillis();

		currentScreen = 0;

//		stage.addActor(splash);

//		splash.setVisible(false);

		this.stage = stage;
		
	}

	public boolean getDownload() {
		return download;
	}

	
	/**
	 * @return
	 */
	public boolean downloadingAssets() {
		// boolean download = true;


		if (game.ondemandAssetManager.isComplete()) {
			
			game.gsLink.console("currentFile = " + currentFile);
			
			
			switch (currentFile) {

			case 0:
				game.ondemandAssetManager.load("loading/loading.atlas", TextureAtlas.class);
				break;
			case 1:
				loadingPanel.commitAssets();
				
			break;
			case 2:
				groupLoading.addActor(loadingPanel);
				stage.addActor(groupLoading);
			break;
			case 3:
			break;
			case 4:
				game.gameTxt.loadAssets();

				break;
			case 5:
				
				game.gameTxt.commitAssets();
				
				try {
					game.gameTxt.setTexts(game.languageCode);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(game.languageCode == "ENG_SC"){
					game.languageCode = "ENG";
				}
				
				if(game.languageCode == "HR" || game.languageCode == "GR" || game.languageCode == "DE"){
					game.languageCode = "FRA";
				}
				
				
				game.ondemandAssetManager.load("lines/lines-1.png", Texture.class);
				game.spineFireAnimWin.loadAssetsSpine(0);
				game.spineIntro.loadAssetsSpine(0);
				game.spineFireAnimLoop.loadAssetsSpine(0);

				game.ondemandAssetManager.load("confirmation_bg.png", Texture.class);		
				

				game.ondemandAssetManager.load("spine/1920_bigwin_v12.png", Texture.class);
				
				game.ondemandAssetManager.load("spine/1920_free_games_v12.png", Texture.class);
				game.ondemandAssetManager.load("spine/1920_free_games_v13.png", Texture.class);
				game.ondemandAssetManager.load("spine/1920_hold_and_win_v12.png", Texture.class);
			
				
				game.ondemandAssetManager.load("animations/anticipation-2.png", Texture.class);
				game.ondemandAssetManager.load("animations/anticipation-1.png", Texture.class);
				
				
				currentTime = System.currentTimeMillis();
				break;
			case 6:					
				game.spineIntro.loadAssetsSpine(1);
				break;
			case 7:
				game.spineIntro.loadAssetsSpine(2);
				break;
			case 8:
			break;
			case 9:
			case 10:
			case 11:
			case 12:
			break;
			case 13:
			break;
			case 14:
			break;
			case 15:
			break;
			case 16:
			break;
			case 17:
			case 18:
			break;
			case 19:
				
				game.reels.textures.loadAssetsHighlights();
				game.helpScreen.loadAssets();
				game.fonts.loadAssets();
				game.textures.loadAssetsInterface();
				game.background.loadAssets();
				game.textures.loadAssetsLines();
				game.textures.loadPayAssetsLines();
				game.helpScreen.loadAssets();
				game.textures.loadAssetsCommon();
				game.textures.loadAssetsSymbols();
				game.textures.loadAssetsFreeGames();
				game.reels.textures.loadAssetsAnimations();
				game.textures.loadAssetsMenuBet();
				game.reels.loadAssets();
				game.sounds.loadAssets();
				game.sounds.loadAssetsSpin();
				
				game.spineIntro.loadAssetsSpine(1);
				game.spineFireAnimLoop.loadAssetsSpine(1);
				
				game.spineFireAnimWin.loadAssetsSpine(1);
				game.textures.loadAssetsAddFreeSpins();
			break;
			case 20:
				
				
				game.textures.commitInterfaceAddFreeSpins();
				game.reels.textures.commitAssetsHighlights();
				game.fonts.commitAssets();
				game.textures.commitAssetsInterface();
				game.textures.commitAssetsLines();
				game.textures.commitAssetsPayLines();
				game.background.commitAssets();
				game.textures.commitInterfaceCommon();
				game.textures.commitAssetsMenuBetAtlas();
				game.textures.commitAssetsFreeGames();
				game.betMenu.commitAssets();
				game.betMenu.setVisible(false);				
//				game.textures.commitBoostMenuAtlas();
				game.textures.commitAssetsSymbols();
				game.autoPlayMenu.commitAssets();
				game.autoPlayMenu.setVisible(false);
				game.reels.textures.commitAssetsAnimations();
				game.menu.commitAssets();
				game.messages.commitAssets();
				
				game.spineIntro.commitAssetsSpine();
				introAnim.commitAssets();
				game.spineFireAnimLoop.commitAssetsSpine();	
				game.spineFireAnimWin.commitAssetsSpine();
				
				game.buyBonusMenu.commitAssets();
				game.buyBonusMenu.setVisible(false);

			break;
			case 21:
			break;
			case 22:
				
			case 23:
			break;
			case 24:
			break;
			case 25:
			break;
			case 26:
			break;
			case 27:
			
				game.textures.getInterfaceAtlas().findRegion("logo").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				title = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("logo")));
				title.setPosition(860, 1080 - 156 - 40);
				title.setScale(1f);
				title.setVisible(false);
				stage.addActor(title);
				
				game.sounds.commitAssets();
				game.sounds.commitAssetsSpin();

				commitButtons();
				game.reels.commitAssets();

				game.roundId = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
				game.roundId.setFontScale(1f);
				game.roundId.setAlignment(Align.topRight);
				game.roundId.setBounds(-10,0,1920, 1080); 
				game.menu.setRoundId(0);
				

				bgSound = new Image(game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class));
				bgSound.setPosition(0, 0);

				Color color = bgSound.getColor();
				color.a = 0.8f; // 'a' represents alpha
				bgSound.setColor(color);
				stage.addActor(bgSound);
				
		        // Create a ClickListener and attach it to the button
				bgSound.addListener(new ClickListener() {
		            @Override
		            public void clicked(InputEvent event, float x, float y) {
		                // Handle the button click event here
		            	menuSound.hide();
		            }
		        });

				game.showButtons = true;
				
				menuSound = new MenuSound(game);
				menuSound.commitAssets();
				menuSound.setVisible(true);
				stage.addActor(menuSound);
				
				game.helpScreen.commitAssets();

				game.overlay.commitAssests();
				
				
				stage.addActor(game.menu.bottomMenu);
				stage.addActor(game.menu.totalBetField);
				stage.addActor(game.menu.creditField);
				
				if(game.restore){
					game.controller.setNextState(State.RESTORE_STATE);
				}

				if (Gdx.app.getType() == ApplicationType.Desktop) {
					game.login = true;
//					tapTo.setVisible(true);
				}
				
				if(game.login){
					isSoundChoosed = true;
					game.menu.updateMeters();
					loadingPanel.setVisible(false);
					menuSound.setVisible(true);
					bgSound.setVisible(true);
					
					game.menu.setRoundId(game.context.roundId);
					
					game.overlay.showJackpotDigitsSymbolsAll();
				}

				tapTo = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
				tapTo.setFontScale(1f);
				tapTo.setText(game.gameTxt.startTap);
				tapTo.setAlignment(Align.center);
				tapTo.setBounds(0, -460,1920, 1080);
				tapTo.setVisible(false);
				
//				Actions.addActionLoop(tapTo, 1.0f, 0.0f, 1.0f, 1.0f, 2.0f);
//				
                SequenceAction action = Actions.sequence(
                        Actions.alpha(1.0f, 0.5f, Interpolation.fade), // From 1.0 to 0.0 in 1 second
                        Actions.delay(0.5f), // Wait for 1 second
                        Actions.alpha(0.15f, 0.5f, Interpolation.fade) // From 0.0 to 1.0 in 1 second
                    );

                    RepeatAction continuousLoop = Actions.forever(action);

                    tapTo.addAction(continuousLoop);
                    tapTo.setVisible(false);
                    
				break;
			// can start the game
			case 28:
			case 29:
				 game.spine.loadAssetsSpine(currentFile % 28);
			break;
			case 30:
				game.spine.commitAssetsSpine();
			break;
			case 31:
			case 32:
				game.spineFreeGamesTxt.loadAssetsSpine(currentFile % 31);
			break;
			case 33:
				game.spineFreeGamesTxt.commitAssetsSpine();	
			break;
			case 34:
			case 35:
				game.spineBG.loadAssetsSpine(currentFile % 34);
			break;
			case 36:
				game.spineBG.commitAssetsSpine();
			break;
			case 37:
			case 38:
				game.spineFreeGamesCongrats.loadAssetsSpine(currentFile % 37);
				break;
			case 39:
				game.spineFreeGamesCongrats.commitAssetsSpine();
				break;
			case 40:
			case 41:
				game.spineHoldAndWinTxt.loadAssetsSpine(currentFile % 40);
				break;
			case 42:
				game.spineHoldAndWinTxt.commitAssetsSpine();	
				break;
			case 43:
				// game.bigAnim.commitAssets();
//				game.overlay.showFreeGamesText(5);
				download = false;
				timeout = true;
				break;
			default:
				break;
			}
			sliderPrev += step;

			if(!timeout){
				currentFile++;
			}

			if (currentFile == 5 || currentFile == 6)
				step = (580 / 40) * 3;
			else
				step = (580 / 40);
		}

//		game.gsLink.console("game.ondemandAssetManager.progress() = " + game.ondemandAssetManager.progress());

		loadingPanel.setSlider(sliderPrev, step);

		return download;
	}

	public void hideWatingScreen(){

		playButtonPressed = true;

	
	}
	
	public void commitButtons() {
			}

	public void action(float delta) {

		timeElapsed += delta;

		if (timeElapsed - lastTimeElapsed > 0.030f) {
			lastTimeElapsed = timeElapsed;
			currentFrame++;
			if (currentFrame > 19) {
				currentFrame = 0;
			}
		}
	}

//	public void drawLoadingPanel(Stage stage) {
//
//		if (!game.showButtons) {
//			sliderProgressX = (int) ((game.ondemandAssetManager.progress()) * step + sliderPrev);
//
////			game.ondemandAssetManager.getAssetManager().get("splash.jpg", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
////			stage.getBatch().draw(game.ondemandAssetManager.getAssetManager().get("splash.jpg", Texture.class), 0, 0);
//
//			game.ondemandAssetManager.getAssetManager().get("logo.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			stage.getBatch().draw(game.ondemandAssetManager.getAssetManager().get("logo.png", Texture.class), sliderPosX, sliderPosY);
//			
//			if (game.manager.isLoaded("loading/loading.atlas")) {
//				stage.getBatch().draw(empty, sliderPosX + 36, sliderPosY - 20);
//			}
//
//			if (game.manager.isLoaded("loading/loading.atlas")) {
//
//				stage.getBatch().draw(loadingRegions.get(0).getTexture(),
//						sliderPosX, 16 + 20,
//						(int) (sliderProgressX) / 2,
//						loadingRegions.get(0).getRegionHeight() / 2,
//						(int) (sliderProgressX),
//						loadingRegions.get(0).getRegionHeight(),
//						1f,
//						1f,
//						0f,
//						loadingRegions.get(0).getRegionX(), loadingRegions.get(0).getRegionY(),
//						(int) (sliderProgressX),
//						loadingRegions.get(0).getRegionHeight(),
//						false,
//						false);
//			}
//		}
//	}

	public boolean showButtons() {
		return game.showButtons;
	}

	public boolean playButtonsPressed() {
		return playButtonPressed;
	}

	public boolean timeoutExpired() {
		return timeout;
	}

	public void dispose() {
	}
}
