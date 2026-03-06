package com.pgd.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.pgd.game.Controller.State;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.pgd.game.Context.GameMode;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.ReelFrame;
import com.pgd.game.actors.ReelFrameBackground;
import com.pgd.game.actors.SpineSymbolAnimation;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.base.IReelCallback;
import com.pgd.game.base.LineRenderer;
import com.pgd.game.base.Reel;
import com.pgd.game.base.ReelTextures;
import com.pgd.game.base.Win;
import com.pgd.game.base.AbstractController.Event;



public class Reels extends Group {

	private float elapsed;
	private float duration;
	private float intensity; 
	private boolean startAnimShake;
	private boolean playLogo;
	public Random rand; // RNG
	private float baseX = 0f;
	private float baseY = 0f;
	
	private boolean freeGamesTitle = false;
	
	public boolean spineAnimation = false;

	private int highlightReel = 0;
	/**
	 * Params defining the position of the reels matrix on the screen
	 */
	public int NUMBER_OF_REELS = 5;
	public int NUMBER_OF_REELS_NORMAL = 5;
	
	private final int REELS_SPACING = 282;
	private final int REELS_POSITION_X = 257; // left, left-most reel
	private final int REELS_POSITION_Y = 1080 - 264 - 667; // bottom, left-most reel

	private int magicianCount;
	private int showMagician[] = {0,0,0,0,0};
	
	
	private boolean hideReels = false;

	/**
	 * Reference to game
	 */
	private BookOfKnight game;

	/**
	 * Lines
	 */
	public LineRenderer lineRederer;

	/**
	 * Reels overlay (highlighted and animated symbols)
	 */
//	public ReelsOverlay reelsOverlay;
	
	/**
	 * Reels background textures
	 */
	// private Texture bgTexture; //, bgNormal, bgFreegames;
	public ReelTextures textures;


	/**
	 * List of Reel objects and symbols texture
	 */
	public List<Reel> reels = new ArrayList<Reel>();
	// private Texture reelsTexture;

	/**
	 * Game matrix
	 */
	public int matrix[][] = { 
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
	};

	public int matrixOld[][] = { 
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
	};
	
	public int matrixHoldAndWin[][] = { 
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
	};

	public int prevMatrixHoldAndWin[][] = { 
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
	};

	public int matrixJackpotDigits[][] = { 
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
			{ 0, 0, 0, 0, 0},
	};

	/**
	 * Title
	 */
	private Image frame;
//	private Image title;

	Color color;
	/**
	 * Reels frame
	 */
//	private Image reelsFrame, /*reelsFrameFG,*/ reelsFrameBG/*, reelsFrameBGFG*/;
//	private Image reelsFrameCoverTop, reelsFrameCoverBottom;
//	private Image ways729Left, ways729Right;
	boolean freeGames,holdAndWin, showFrame, showFrameWin, showWinPortal, showTitle, showChooseLine;
	
	private boolean animationTransform = true;
	private Image field;
	
	private boolean showBonusBg;
	
	private boolean drawOverlay;
	
	public boolean showBonus;
	
	private SkeletonJson json;
	private SkeletonData playerSkeletonData;
	private AnimationStateData playerAnimationData;
	
//	private Image frameTop;
	
	private Label titleLabel;
	
	public SpineSymbolAnimation spineSymbolAnim;
	
	private ReelFrameBackground reelsFrameBG;
	private ReelFrame reelsFrame;
	
	
	/**
	 * Reels constructor
	 * @param game - reference to game instance
	 */
	public Reels(BookOfKnight game) {
		super();
		this.game = game;
		textures = new ReelTextures(game);
//		spineTextures = new SpineTextures(game);
		
		showChooseLine = true;
		
		showTitle = true;
		
		playLogo = false;

		freeGames = false;

		showBonus = false;
		
		showFrame = false;

		showFrameWin = false;
		
		drawOverlay = false;
		
		rand = new Random();

		baseX = 0f;
		baseY = 0f;
		
		this.startAnimShake = false;
		
	}

	public void loadAssets() {
		
		game.ondemandAssetManager.load("reels_bg.png", Texture.class);
		game.ondemandAssetManager.load("reels_bg_fg.png", Texture.class);
		game.ondemandAssetManager.load("reels_bg_haw.png", Texture.class);
		game.ondemandAssetManager.load("reels_frame.png", Texture.class);
		game.ondemandAssetManager.load("reels_frame_fg_haw.png", Texture.class);
		


		Gdx.app.debug("Reels", "loadAssets()");
	}


	
	public void commitAssets() {

		lineRederer = new LineRenderer(game, 0, 0, "lines/", game.textures.getLiensAtlas());
		
		textures.commitAssetsSymbols();
		lineRederer.commitAssets();
		
		setPosition(0, 0);
		setWidth(1280);
		setHeight(640);
		setBounds(getX(), getY(), getWidth(), getHeight());
		setOrigin(getWidth() / 2,  getHeight()/2);

		spineSymbolAnim = new SpineSymbolAnimation(game);
		spineSymbolAnim.commitAssets();		

		Reel reel;
		for (int i = 0; i < NUMBER_OF_REELS; i++) {
//			reel = new Reel(textures, spineTextures, REELS_POSITION_X + i * REELS_SPACING, REELS_POSITION_Y, (int[]) game.math.stripsNormalGames[i], game, i);
			reel = new Reel(textures, REELS_POSITION_X + i * REELS_SPACING, REELS_POSITION_Y, (int[]) game.math.stripsNormalGames[i], game, i, json, playerSkeletonData, playerAnimationData);
			reels.add(reel);
			this.addActor(reel);

			// Register individual reel callback
//			if(i < NUMBER_OF_REELS - 1)
			if(i < NUMBER_OF_REELS_NORMAL){
				reel.registerCallback(new IReelCallback() {
					public void atReelStop() {
						game.sounds.play(SoundTrack.REEL_STOP, false);
	//						game.sounds.play(SoundTrack.REEL_STOP_ONE, false);
					}
				});
			}
			
		}
		
//		frameTop = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("frame_line")));
//		frameTop.setPosition(16 + 300, 720 - 54 - 8);
		
//		game.textures.getInterfaceAtlas().findRegion("podlojka").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		frame = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("podlojka")));
//		frame.setPosition(85, 1080 - 22 - 1004);

		Texture texture = game.manager.get("podlojka.png", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		frame = new Image(texture);
		frame.setPosition(86, 1080 - 20 - 1004);
		frame.setVisible(false);
		addActor(frame);
		
//		frameFG = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("podlojka_fg")));
//		frameFG.setPosition(65, 720 - 530 - 64);

//		title = new Logo(game);
//		title = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("logo")));
//		title.setPosition(465 + 300, 720 - 60 - 6);
//		title.setVisible(true);

		reelsFrame = new ReelFrame(game);
		reelsFrame.commitAssets();
		reelsFrame.setPosition(0, 0);
		
		reelsFrameBG = new ReelFrameBackground(game);
		reelsFrameBG.commitAssets();
		reelsFrameBG.setPosition(0,0);
		
		
//		game.ondemandAssetManager.getAssetManager().get("reels_frame.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		reelsFrame = new Image(game.manager.get("reels_frame.png", Texture.class));
//		reelsFrame.setPosition(0, 0);

//		game.ondemandAssetManager.getAssetManager().get("reels_frame_fg.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		reelsFrameFG = new Image(game.manager.get("reels_frame_fg.png", Texture.class));
//		reelsFrameFG.setPosition(0, 0);
		
//		game.ondemandAssetManager.getAssetManager().get("reels_bg.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		reelsFrameBG = new Image(game.manager.get("reels_bg.png", Texture.class));
//		reelsFrameBG.setPosition(0, 0);

//		game.ondemandAssetManager.getAssetManager().get("reel_bg_fg.png", Texture.class).setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		reelsFrameBGFG = new Image(game.manager.get("reel_bg_fg.png", Texture.class));
//		reelsFrameBGFG.setPosition(0, 0);
		

		drawOverlay = false;
		
		addActor(lineRederer);
		addActor(spineSymbolAnim);
		
		addActor(reelsFrameBG);
		addActor(reelsFrame);
		
//		addActor(game.overlay.jackpotDigitsSymbols);
//		addActor(title);
//		addActor(reelsFrame);
//		addActor(frame);
		
		showWinPortal = true;
		
		Gdx.app.debug("Reels", "commitAssets()");
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
	
		applyTransform(batch, computeTransform());
		
		if(startAnimShake){
			update(1);
		}
		
		color = getColor();
		
//		frameTop.draw(batch, alpha);
		
//		Gdx.app.debug("Reels", "hideReels = " + hideReels);

		if(!hideReels){

//			if(freeGames){
////				reelsFrameBGFG.draw(batch, alpha);							
//			} else {
//				reelsFrameBG.draw(batch, alpha);							
//			}
//	
//			if(freeGames){
////				reelsFrameFG.draw(batch, alpha);
//			} else {
//				reelsFrame.draw(batch, alpha);
//			}
	
			batch.setColor(color.r, color.g, color.b, 1.0f);
			reelsFrameBG.draw(batch,alpha);
			batch.setColor(color.r, color.g, color.b, 1.0f);
			
			if(!hideReels){
				if(freeGames){
					for(int i = 0; i < NUMBER_OF_REELS; i++){
						if(game.context.outcome.hideReels[i] == 0){
							reels.get(i).draw(batch, alpha);
						}
					}
				} else {
					for(int i = 0; i < NUMBER_OF_REELS_NORMAL; i++){
						reels.get(i).draw(batch, alpha);
					}
				}
			}
			
			if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES || 
					game.controller.getState() == State.HOLD_AND_WIN ||
					game.controller.getState() == State.HOLD_AND_WIN_START_SPIN){
				
				frame.setColor(color.r, color.g, color.b, 0.8f);
				frame.draw(batch, alpha);
				frame.setColor(color.r, color.g, color.b, 1f);
			}
			
			
			game.overlay.jackpotDigitsSymbols.draw(batch, alpha);

			batch.setColor(color.r, color.g, color.b, 1.0f);
			reelsFrame.draw(batch, alpha);
			batch.setColor(color.r, color.g, color.b, 1.0f);
			
			if(showFrameWin){
				frame.setColor(color.r, color.g, color.b, 0.6f);
				frame.draw(batch, alpha);
				frame.setColor(color.r, color.g, color.b, 1f);
			}
	
			batch.setColor(color.r, color.g, color.b, 1.0f);
			lineRederer.draw(batch, alpha);
			batch.setColor(color.r, color.g, color.b, 1.0f);
			
			for(int i = 0; i < NUMBER_OF_REELS_NORMAL; i++){
				reels.get(i).drawWinning(batch, alpha);
			}
			
			for(int i = 0; i < NUMBER_OF_REELS_NORMAL; i++){
				reels.get(i).drawOnTop(batch, alpha);
			} 
	
			if(game.controller.getState() == State.SHOW_ALL_WINNING_LINES || game.controller.getState() == State.SHOW_SPECIAL_WINS){
				lineRederer.draw(batch, alpha);
			}

			
			if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES || 
			  game.controller.getState() == State.HOLD_AND_WIN ||
			  game.controller.getState() == State.HOLD_AND_WIN_START_SPIN ||
			  game.controller.getState() == State.SHOW_HOLD_AND_WIN_WINS
			  ){
				batch.setColor(color.r, color.g, color.b, 1.0f);
				game.overlay.jackpotSymbols.draw(batch, alpha);
				batch.setColor(color.r, color.g, color.b, 1.0f);
				game.overlay.jackpotDigitsSymbols.draw(batch, alpha);
			}
			
			if(showFrame){
				frame.draw(batch, alpha);
			}
			
//			if(showChooseLine){
//			} else {
//				if(showTitle){
//					title.draw(batch, alpha);
//				}	
//			}

		}
		
		resetTransform(batch);
	}

	/**
	 * Start a new spin.
	 * 
	 * @note: depreciated
	 */
	public void startSpin() {
		for (int i = 0; i < NUMBER_OF_REELS; i++) {
			reels.get(i).startSpin(1, i * 6 + 10);
		}
	}

	/**
	 * Spins a reel
	 * 
	 * @param index
	 *            - reel to spin
	 * @param stop
	 *            - game outcome
	 */
	public void spinReel(int index, int stop) {

		
		
		for( int row = 0; row < game.SYMBOLS; row++) {
//			Gdx.app.debug("REels", "index =  " + index + " ::: stop = " + stop + " ::::: row = " + row);
			matrix[row][index] = (game.context.gameMode == game.context.MAIN_GAME) ? game.math.stripsNormalGames[index][(stop + row) % game.math.stripsNormalGames[index].length] : game.math.stripsFreeGames[index][(stop + row) % game.math.stripsFreeGames[index].length];
		}
		

			matrix[0][0] = 1;
			matrix[0][1] = 2;
			matrix[0][2] = 3;
			matrix[0][3] = 4;
			matrix[0][4] = 5;
	
			matrix[1][0] = 1;
			matrix[1][1] = 2;
			matrix[1][2] = 3;
			matrix[1][3] = 4;
			matrix[1][4] = 5;
			
			matrix[2][0] = 1;
			matrix[2][1] = 2;
			matrix[2][2] = 3;
			matrix[2][3] = 4;
			matrix[2][4] = 5;

		
		for( int row = 0; row < game.SYMBOLS; row++) {
			reels.get(index).stopSymbols[row] = matrix[row][index];
		}

		reels.get(index).startSpin(stop);
	}

	
	public void hideReelFG(int reel){
		game.context.outcome.hideReels[reel] = 1;
	}
	
	public void showAllReels(){
		for(int i = 0; i < NUMBER_OF_REELS; i++){
			game.context.outcome.hideReels[i] = 0;
		}
	}
	
	/**
	 * Update reels stop positions. 
	 */
	public void updateStopSymbols() {
		for( int index = 0; index < NUMBER_OF_REELS; index++) {
			for( int row = 0; row < game.SYMBOLS; row++) {
				reels.get(index).stopSymbols[row] = game.reels.matrix[row][index];
			}
		}
	}

	public void updateStopSymbolsHoldAndWin() {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
//				if(game.reels.matrixHoldAndWin[s][r] > 0 && game.reels.prevMatrixHoldAndWin[s][r] > 0){
//					reels.get(r).stopSymbols[s] = 14;
//				} else {
					reels.get(r).stopSymbols[s] = game.reels.matrix[s][r];
//				}
				
//				game.reels.prevMatrixHoldAndWin[s][r] = game.reels.matrixHoldAndWin[s][r];
			}
		}
	}
	
	
	/**
	 * Update reels stop positions. 
	 */
	public void updateReelSymbols() {
		for( int index = 0; index < NUMBER_OF_REELS; index++) {
			reels.get(index).skillStopHelper();
		}
	}

	public void resetJackpotDigitsSymbols() {
		for( int index = 0; index < NUMBER_OF_REELS; index++) {
			reels.get(index).resetJackpotDigitsSymbols();
		}
	}
	
	/**
	 * Sets flip symbol for reel and stop.
	 * 
	 * @param reel
	 * @param stop
	 * @param symbol
	 */
	public void setFlipSymbolAtReelStop(int reel, int stop, int symbol) {
		reels.get(reel).setFlipSymbolAtStop(stop, symbol);
	}

	// prototype
	public void flipAll() {
		for( int reel = 0; reel < NUMBER_OF_REELS; reel++) {
			for (int stop = 0; stop < 3; stop++)
				reels.get(reel).getReelSymbolAtStop(stop).flip();
		}
	}
	
	/**
	 * Stop a reel
	 * 
	 * @param index
	 *            - reel to stop
	 */
	public void stopReel(int index) {
		if (index >= NUMBER_OF_REELS)
			return;

		// reels.get(reelCounter).stopHelper();
		reels.get(index).stop();
	}

	/**
	 * Stop all reels
	 */
	public void stopAllReels() {
		for (int i = 0; i < NUMBER_OF_REELS; i++) {
			reels.get(i).skillStop();
		}
	}

	public void resetReelSpeed(){
		for(int reel = 0; reel < game.REELS; reel++){
			reels.get(reel).setStep(43);
		}
	}
	
	/**
	 * Highlight reel
	 * 
	 * @param reel
	 */
	public void highlightReel(int reel, int iterations) {
		reels.get(reel).highlight();
		reels.get(reel).setStep(83);
		highlightReel = reel;
	}	
	
	public void setReelStep(int step){
		reels.get(highlightReel).setStep(step);
	}	

	public void unHighlightReel(int reel) {
		reels.get(reel).unhighlight(0);
	}	

	/**
	 * Highlight magican
	 * 
	 * @param reel
	 */
	public void magicianReel(int reel, boolean show) {
		
		reels.get(reel).setMagicianReel(show);
		reels.get(reel).setMagicianReelAnim(show);
	}
	
	public boolean highlightReelIsStopped(int reel) {
		return reels.get(reel).highlightIsStopped();
	}	

	/**
	 * hide magican
	 * 
	 * @param reel
	 */
	public void hideMagicianReels() {
	}
	
	public void hideReels(boolean hide){
//		Gdx.app.debug("Reels", "hideReels = " + hide);
		hideReels = hide;
	}
	/**
	 * Highlight scatter on specific reel
	 * @param reel
	 */
	public void highlightScattersOnReel(int reel) {
//		if(reels.get(reel).highlightScatters())
//			game.sounds.stopAndPlayNext(SoundTrack.SCATTER, SoundTrack.SCATTER);
	}
	
//	public void hideSymbols(int reel, int stop) {
////		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
//		reels.get(reel).hideSymbols(stop);
//	}
	
	/**
	 * Highlight symbol on a reel
	 * 
	 * @param reel
	 * @param stop
	 * @param looping
	 */
	public void highlightSymbol(int reel, int stop, boolean looping, boolean hasWildWin, int mult) {
//		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
		reels.get(reel).highlightSymbolAtStop(stop, looping, hasWildWin, mult);
	}

//	public void boostSymbol(int reel, int stop, boolean boost) {
////		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
//		reels.get(reel).setBoostedSymbolAtStop(stop, boost);
//	}

	public void highlightWinSymbol(int reel, int stop, boolean looping, boolean hasWildWin, int mult) {
//		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
		reels.get(reel).highlightWinSymbolAtStop(stop, looping, hasWildWin, mult);
	}

	public void highlightWinSymbolAllLines(int reel, int stop, boolean looping, boolean hasWildWin, int mult) {
		reels.get(reel).highlightWinSymbolAtStopAllLines(stop, looping, hasWildWin, mult);
	}
	
	public void highlightWildMult(int reel, int stop, boolean looping, boolean hasWildWin) {
			reels.get(reel).highlightWildMult(stop, looping, hasWildWin);
	}

	
	/**
	 * Highlight bonus symbol on a reel
	 * 
	 * @param reel
	 * @param stop
	 * @param looping
	 */
//	public void highlightSymbolBonus(int reel, int stop, boolean looping, boolean hasWildWin) {
////		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
//		reels.get(reel).highlightSymbolAtStopBonus(stop, looping, hasWildWin);
//	}

	public void highlightCurrentScatterSymbol(int reel, int stop, boolean looping, boolean hasWildWin) {
//		reels.get(reel).highlightSymbolAtStop(stop, looping, reel * 0.06f);
		reels.get(reel).highlightCurrentScatterSymbol(stop, looping, hasWildWin);
	}

	/**
	 * Highlight symbol on a reel
	 * 
	 * @param reel
	 * @param stop
	 */
//	public void highlightStackedSymbol(int reel, int stop, boolean looping, boolean isLong) {
//		stackedReels.get(reel).getReelSymbolAtStop(stop).setIndex(reels.get(reel).getReelSymbolAtStop(stop).getIndex());
////		stackedReels.get(reel).highlightSymbolAtStop(stop, looping);
////		stackedReels.get(reel).highlightSymbolAtStop(stop, looping, isLong);
//		stackedReels.get(reel).getReelSymbolAtStop(stop).setVisible(true);
//	}	
	
	/**
	 * Dim symbol on a reel
	 * 
	 * @param reel
	 * @param stop
	 * @param alpha
	 */
	public void dimSymbol(int reel, int stop, float alpha) {
		reels.get(reel).getReelSymbolAtStop(stop).setDimming(alpha);
	}

	/**
	 * Set disable
	 * 
	 * @param reel
	 * @param stop
	 * @param alpha
	 */
	public void disableSymbol(int reel, int stop) {
		reels.get(reel).getReelSymbolAtStop(stop).setDisable();
	}
	
	public void setAnimNearMiss(int reel, int stop) {
		reels.get(reel).getReelSymbolAtStop(stop).setAnimNearMiss();
	}
	
	public boolean getDisableSymbol(int reel, int stop){
		return reels.get(reel).getReelSymbolAtStop(stop).getDisable();
	}

	
//	/**
//	 * Animate symbol on a reel
//	 * 
//	 * @param reel
//	 * @param stop
//	 */
//	public void animateSymbol(int reel, int stop) {
//		reels.get(reel).animateSymbolAtStop(stop, false);
//	}
	
	/**
	 * Highlight win
	 * 
	 * @param Win
	 *            win
	 */
	public void highlightWin(Win win, boolean looping, boolean overlay, int mult) {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
				if (win.highlight[s][r] == 1) {
					Gdx.app.debug(" Reels ", "win.highlight["+s+"]["+r+"] = " + win.highlight[s][r]);
					highlightSymbol(r, s, looping, win.hasWild, mult);										
					drawOverlay = true;
				}
				else if(win.type == Win.Type.LINE || win.type == Win.Type.SCATTER) {
					disableSymbol(r, s);
				}
			}
		}
	}

	public boolean playStar = false;
	public boolean playWild = false;
	public boolean playSun = false;
	
	public void animNearMiss(int reel) {
		

		if(game.context.gameMode == game.context.MAIN_GAME || game.context.gameMode == game.context.HOLD_AND_WIN_GAMES ){
			for (int s = 0; s < game.SYMBOLS; s++) {
//				if (game.reels.matrix[s][reel] == game.math.WILD || game.reels.matrix[s][reel] == game.math.SCATTER || game.reels.matrix[s][reel] == game.math.JACKPOT_SYMBOL) {
				if ((game.reels.matrix[s][reel] == game.math.WILD && !game.context.outcome.hasWin && !game.context.outcome.hasWild)    ||
						 (game.reels.matrix[s][reel] == game.math.SCATTER)
						 || (game.reels.matrix[s][reel] >= game.math.JACKPOT_SYMBOL && game.reels.prevMatrixHoldAndWin[s][reel] == 0)) {
							setAnimNearMiss(reel,s);
				}
			}
			
			
		}
	}
	
public void playSoundNearMiss(int reel) {
	
	
	if(game.context.gameMode == game.context.MAIN_GAME || game.context.gameMode == game.context.HOLD_AND_WIN_GAMES){
		for (int s = 0; s < game.SYMBOLS; s++) {
//			if (game.reels.matrix[s][reel] == game.math.WILD || game.reels.matrix[s][reel] == game.math.SCATTER || game.reels.matrix[s][reel] == game.math.JACKPOT_SYMBOL) {
			if ((game.reels.matrix[s][reel] == game.math.WILD && !game.context.outcome.hasWin && !game.context.outcome.hasWild)    ||
					(game.reels.matrix[s][reel] == game.math.SCATTER/* && !game.context.outcome.hasWin*/) /*|| game.reels.matrix[s][reel] == game.math.JACKPOT_SYMBOL*/) {
				if(game.reels.matrix[s][reel] == game.math.WILD){
//					if(!playWild){
//					game.sounds.play(SoundTrack.BEEP_WILD, false, 1f);
//						playWild = true;
//					}
				}
				
				if(game.reels.matrix[s][reel] == game.math.SCATTER){
//					if(!playStar){
						game.sounds.play(SoundTrack.BEEP_STAR, false, 1f);
//						playStar = true;
//					} 
				}
			}
			
			if(game.reels.matrix[s][reel] >= game.math.JACKPOT_SYMBOL && game.reels.prevMatrixHoldAndWin[s][reel] == 0){
//				if(!playSun){
//				game.sounds.play(SoundTrack.BEEP_SUN, false, 0.3f);
				game.sounds.play(SoundTrack.BEEP_SUN, false, 1f);
//				Gdx.app.debug("REels", "~~~~~~~~~~~~~~~~VLIZA");
//					playSun = true;
//				}
			}
		}
		
		
	}
}
	
	public void highlightHoldAndWinSymbol() {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
		
				if(
					(game.reels.matrix[s][r] == game.math.JACKPOT_SYMBOL || 
					game.reels.matrix[s][r] == game.math.JACKPOT_MINOR || 
					game.reels.matrix[s][r] == game.math.JACKPOT_MAJOR || 
					game.reels.matrix[s][r] == game.math.JACKPOT_GRAND )&& game.reels.prevMatrixHoldAndWin[s][r] == 0
					
				   ){
					prevMatrixHoldAndWin[s][r] = game.reels.matrix[s][r];
					reels.get(r).highlightHoldAndWinSymbol(s);
				}
			}
		}

	}
	
	public void highlightWinScatterSymbol(Win win, boolean looping, boolean overlay, int mult) {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
				if (win.highlight[s][r] == 1) {
					Gdx.app.debug(" Reels ", "win.highlight["+s+"]["+r+"] = " + win.highlight[s][r]);
					highlightCurrentScatterSymbol(r, s, looping, win.hasWild);										
					drawOverlay = true;
				}
				else if(win.type == Win.Type.LINE || win.type == Win.Type.SCATTER) {
					disableSymbol(r, s);
				}
			}
		}
	}

	public void showSpecialSymbolAnim(int reel, int symbol){
		for(int s = 0; s < 3; s++ ) {
			if(game.reels.matrix[s][reel] != symbol /*&& reel == 0*/){
				Gdx.app.debug("Reels" , "r = " + reel + " :: s = " + s);
				reels.get(reel).showSpecialSymbolAnim(s, symbol);
			}
		}
	}
	
	public void clearSpecialReel(){
		for(int reel = 0; reel < 5; reel++){
			reels.get(reel).setSpecialReel(false);
		}
	}
	
	public int setSpecialReels(int symbol){
		int firstReel = -1;
		for(int r = 0; r < 5; r++ ) {
			for(int s = 0; s < 3; s++ ) {
				if(game.reels.matrix[s][r] == symbol){
					
					if(firstReel == -1){
						firstReel = r;
					}
					reels.get(r).setSpecialReel(true);
					reels.get(r).setSpecialReelOnTop(s, true);
				} else {
					reels.get(r).setSpecialReelOnTop(s, false);
				}
			}
		}
		
		return firstReel;
	}
	
	public void resetEggs(){
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {

//				game.reels.prevMatrixHoldAndWin[s][r] = 0;
				
//				reels.get(r).restoreOldSymbols(s, matrix[s][r]);
				
				if(game.reels.matrix[s][r] >= game.math.JACKPOT_SYMBOL && game.reels.matrix[s][r] <= game.math.JACKPOT_GRAND){
					reels.get(r).setSpecialReelOnTop(s, false);
				}
			}
		}
	}
	
	public boolean isSpecialReel(int reel){
		if (reel >= NUMBER_OF_REELS)
			return true;
		
		game.gsLink.console("reels.get(reel).isSpecialReel() = " + reels.get(reel).isSpecialReel());
		return reels.get(reel).isSpecialReel();
	}
	
	public void highlightAll(Win win, int mult) {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
								
				if (win.highlight[s][r] == 1) {
					Gdx.app.debug("Reels" , "r = " + r + " :: s = " + s);
					highlightWinSymbolAllLines(r, s, true, false, mult);
					drawOverlay = true;
				}
			}
		}
	}

	/**
	 * Dim all symbols
	 */
	public void dimAllSymbols(int withoutSymbol) {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
					if(matrix[s][r] != withoutSymbol)	dimSymbol(r, s, 0.65f);
			}
		}
	}
		
//	/**
//	 * Highlight win
//	 * 
//	 * @param Win
//	 *            win
//	 */
//	public void higlightSymbols(Win win) {
//		for (int s = 0; s < game.SYMBOLS; s++) {
//			for (int r = 0; r < NUMBER_OF_REELS; r++) {
//				if (win.highlight[s][r] == 1) {
//					highlightWinSymbol(r, s, true, false, 1);
//				}
//			}
//		}
//	}

	public void unhighligthAll() {
		for(int i = 0; i < NUMBER_OF_REELS; i++){
			reels.get(i).removeHighligth();
		}
		lineRederer.clear();
		drawOverlay= false;
		game.reels.spineSymbolAnim.stopAnim();
//		Gdx.app.debug("Reels", "unhighligthAll()");
	}

	public void setOldMatrix() {
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				matrixOld[s][r] = matrix[s][r];
			}
		}
	}

	// to do not working
	public void restoreOldMatrix() {
//		for(int r = 0; r < game.REELS; r++ ) {
//			for (int s = 0; s < game.SYMBOLS + 2; s++) {
//				reels.get(r).refreshOldSymbols(s, rand.nextInt(10) + 1);
//			}
//		}
		
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				if(matrixHoldAndWin[s][r] == 0){
					matrix[s][r] = matrixOld[s][r];
					reels.get(r).restoreOldSymbols(s, matrix[s][r]);
				}
			}
		}
		
		
		
	}

	public void resetOldMatrixHoldAndWin() {
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				game.reels.prevMatrixHoldAndWin[s][r] = 0;
			}
		}
	}

	public void restoreOldMatrixHoldAndWin() {
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				if(game.reels.matrixHoldAndWin[s][r] == game.math.JACKPOT_SYMBOL
						||game.reels.matrixHoldAndWin[s][r] == game.math.JACKPOT_GRAND
						||game.reels.matrixHoldAndWin[s][r] == game.math.JACKPOT_MINOR
						||game.reels.matrixHoldAndWin[s][r] == game.math.JACKPOT_MAJOR
						){
					game.reels.prevMatrixHoldAndWin[s][r] = game.reels.matrixHoldAndWin[s][r];
				}
			}
		}
	}
	
	
	public void hideSymbols(int expect) {
		for (int s = 0; s < game.SYMBOLS + 2; s++) {
			for (int r = 0; r < game.REELS; r++) {
//				if (matrix[s][r] != expect) {
					reels.get(r).hideSymbols(s);
//				}
			}
		}
	}

	/**
	 * Un-highlight reel symbols keeping wild symbols 
	 * animation running
	 * @param keepWilds
	 */
	public void unhighligthAll(boolean keepWilds) {
		for(int i = 0; i < NUMBER_OF_REELS; i++){
			reels.get(i).removeHighligth(keepWilds);
		}
		lineRederer.clear();
		drawOverlay= false;
		
//		Gdx.app.debug("Reels", "unhighligthAll(" + (keepWilds? "keepWilds" : "~keepWilds") + ")");
	}	
	
	public void unhighligthSpine(){
		for(int i = 0; i < NUMBER_OF_REELS; i++){
			reels.get(i).unhighligthSpine();
		}
		
	}
	
	/**
	 * Set stacked symbols
	 * 
	 * @param Win
	 *            win
	 */
	public void setStackedSymbols() {}

	/**
	 * Checks if specific reel is stopped
	 * @param reel
	 * @return
	 */
	public boolean reelStopped(int reel) {
		return reels.get(reel).isReelStopped();
	}
	
	/**
	 * Checks if specific reel is stopping
	 * @param reel
	 * @return
	 */
	public boolean reelStopping(int reel) {
		return reels.get(reel).isReelStopping();
	}	
	
	/**
	 * Checks if all reels are stopped
	 * 
	 * @return
	 */
	public boolean allStoped() {
		boolean stopped = true;
		for (int i = 0; i < NUMBER_OF_REELS; i++) {
			if (!(reels.get(i).isReelStopped()))
				stopped = false;
		}
		return stopped;
	}

	/**
	 * Update stacked symbols with game outcome. It's adding on top
	 * of previous game outcomes. Call clearStackedMatrix to clear
	 * stackedMatrix.
	 */
	public void updateBonusMatrix() {}

	
	/**
	 * Update stacked symbols with game outcome. It's adding on top
	 * of previous game outcomes. Call clearStackedMatrix to clear
	 * stackedMatrix.
	 */
	public void updateStackedMatrix() {}

	
	/**
	 * Clear stacked symbols matrix and reset freeGamesSpecialSymbol
	 */
	public void clearStackedMatrix() {}

	public void setMagicianCount()
	{}
	
	public int getMagicianCount() {
		return magicianCount;
	}
	
	public void playLogo(boolean play)
	{}
	
	public void setFreeGames(boolean fg) {
		
		freeGames = fg;
		reelsFrameBG.setFreeGamesAnim(fg);
		reelsFrame.setFreeGamesAnim(fg);
	}
	
	public void setHoldAndWin(boolean fg) {
		
		holdAndWin = fg;
		reelsFrameBG.setHoldAndWinAnim(fg);
		reelsFrame.setFreeGamesAnim(fg);
	}

	public void setBonus(boolean show) {
		showBonus = show;
	}
	
	public void shake(float intensity, float duration) {
	    this.elapsed = 0;
	    this.duration = duration / 1000f;
	    this.intensity = intensity;
	    this.startAnimShake = true;
	} 
	
	public void update(float delta) {
		 
	    // Only shake when required.
	    if(elapsed < duration) {
	 
	        // Calculate the amount of shake based on how long it has been shaking already
	        float currentPower = intensity * 1f * ((duration - elapsed) / duration);
	        float x = (rand.nextFloat() - 0.5f) * 2 * currentPower;
	        float y = (rand.nextFloat() - 0.5f) * 2 * currentPower;
	        this.setPosition(-x, -y);
	 
	        
	        // Increase the elapsed time by the delta provided.
	        elapsed += 0.017f;
	    }
	}
	
	public void animateHide(boolean show){
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
					reels.get(r).animateHide(s, show);
			}
		}
	}
	
	public void setMagicanSpin(int index, boolean show){
		showMagician[index] = show ? 1 : 0;
	}

	public void clearMagicanSpin(){
		for(int s = 0; s < 5; s++ ) {
			showMagician[s] = 0;
		}
	}
	
	public void setFreeGamesTitle(boolean fg) {
		freeGamesTitle = fg;
	}
	
	public void animateOnStop(){
		
//		
//		for(int r = 0; r < 5; r++ ) {
//				for(int s = 0; s < 3; s++ ) {
//					if(game.reels.matrix[s][r] == 0 || game.reels.matrix[s][r] == 9){
//							reels.get(r).animateOnStop(s);
//					}
//				}
//		}
	}
	
//	public void setTransform(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.transformMatrix[s][r] == 1){
//						reels.get(r).setTransformSymbol(s);
//				}
//			}
//		}
//	}

	public void setTransformAnim(){
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.transformMatrix[s][r] == 1){
					reels.get(r).setTransformAnim(s);
//				}					
			}
		}
	}
	
	public void hideBonus(){
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.transformMatrix[s][r] == 1){
					reels.get(r).hideBonus(s);
//				}					
			}
		}
	}
	
	public void animateBigAnim(){
//		reels.get(2).animateBigAnim(1);
//		
//		if(game.reels.matrix[1][2] == 5 ||
//		   game.reels.matrix[1][2] == 6 ||
//		   game.reels.matrix[1][2] == 7){
//			game.bigAnim.animateBlue();
//		} else {
//			game.bigAnim.animateOrange();
//		}
//				
//		showWinPortal(false);
	}
	
	public void showFrame(boolean show){
		showFrame = show;
	}

	public void showFrameWin(boolean show){
		showFrameWin = show;
	}

	public void showWinPortal(boolean show){
		showWinPortal = show;
	}
	
	public void showTitle(boolean show)
	{
		showTitle = show;
	}
	
	public void changeTitle(){
		showChooseLine = !showChooseLine;
	}
	
	public void setStackedSymbol(int reel, int stop, boolean stack) {
		reels.get(reel).setStackedSymbolAtStop(stop, stack, reel);
	}
	
	public void setStackedWilds(){
	}

	public boolean animationMysteryStopped(){
		return !animationTransform;
	}
	
	public void setAnimationTransform( boolean anim){
		animationTransform = anim;
	}
	
	public void animateMysterySymbol(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.matrixMysterySymbols[s][r] > 0){
//					reels.get(r).animateMystery(s);
//				}
//			}
//		}
	}
	
	public void setMysterySymbols(boolean firstTime){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.matrixMysterySymbols[s][r] > 0){
//					game.overlay.stackedSymbols.showMystery(true, r, s, firstTime);
//				}
//			}
//		}
	}

//	public void hideMysterySymbols(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
////				if(game.reels.matrixMysterySymbols[s][r] > 0){
//					game.overlay.stackedSymbols.hideMystery(r, s);
////				}
//			}
//		}
//	}

//	public void hideWildSymbols(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
////				if(game.reels.matrixStackedWilds[s][r] > 0){
//					game.overlay.stackedSymbols.hideWild(r, s);
////				}
//			}
//		}
//	}

//	public void hideWildSymbolsTxt(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.matrixStackedWilds[s][r] > 0){
//					game.overlay.stackedSymbols.hideWildTxt(r, s);
//				}
//			}
//		}
//	}

//	public void hideMysterySymbolsImage(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.matrixMysterySymbols[s][r] > 0){
//					game.overlay.stackedSymbols.hideMysteryImage(r, s);
//				}
//			}
//		}
//	}
	

//	public void hidySymbols(){
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				if(game.reels.matrix[s][r] > 0){
//					reels.get(r).hideSymbolAtStop(s);
//				}
//			}
//		}
//	}

	
	public void setBonusBg(boolean show){
		showBonusBg = show;
	}
	
	/**
	 * Stack symbol on a reel
	 * 
	 * @param reel
	 * @param stop
	 * @param looping
	 */
	public void clearStackedSymbols() {
		for (int r = 0; r < 3; r++) {
			reels.get(r).clearStackedSymbolAtStop(false, r);
		}
	}
	
	public void setReelStrip(int[] strip, int i){
		reels.get(i).setReelStrip(strip);
	}
	
	public boolean getSpineAnimationEnd(){
		return spineAnimation;
	}
	
	public void setSpineAnimationEnd(boolean anim){
		spineAnimation = anim;
	}
	
}
