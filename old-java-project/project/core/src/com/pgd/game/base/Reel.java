package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.pgd.game.BookOfKnight;
import com.pgd.game.actors.ReelHighlight;

/**
 * Reel groups symbols in a scene2d Group. Each symbol is an Actor.
 * Two extra symbols are inserted - one on top and one on the bottom 
 * 
 * @author Dimitar
 *
 */
public class Reel extends Group {
	public final int SYMBOLS = 3;	// visible symbols 
	public int STEP = 43;//38;//33;			// Spinning step (speed) Aristocrat 38
	
	public float x, y; 				// reel position on screen
	public int pitch = 272;			// vertical spacing between symbols
	
	public int symbolWidth = 278;	// symbol width
	public int symbolHeight = 264; 	// symbol height
	
	public int trimTopY = 1080 - 123 - symbolHeight / 2 - 13; 
	public int trimBottomY = -5 + 5;
	
	private int currentStop;		// current top stop
	@SuppressWarnings("unused")
	private int stopAtIndex;		// 
	private int spriteOffset;
	private int travel;
	
	private boolean reelClick;		// flag to indicate one stop move
	
	private boolean startSpin = false;
	private boolean stopSpin = false;
	private boolean skillStopSpin = false;
	private boolean skillStopped = false; // flag to indicate reel was skill stopped 
//	private boolean reversed = false;
	private int clicksToStop = 0;
	
	
	private int frameCnt = 0;
	
	private boolean specialReel = false;
	 
	/****************************************************************
	 * dampedSinTable holds the physic for reel elastic bounce
	 ***************************************************************/
	public int dampedIndex = 0;		
	public float dampedSinTable[] = {
			21, 12,	-5,-15,-17,-13, 
			-6,	 1,  5,  7,  6,  4,  
			 1,	-1, -2, -2, -1,	 0,  
			 1,  1,  1,  1,  1, 0};
	
	/****************************************************************
	 * Reel states 
	 ***************************************************************/	
	private enum State {			
		REEL_IDLE,
		REEL_SPIN,
		REEL_STOP_AT_INDEX,
		REEL_STOP,
		REEL_SKILL_STOP,
		REEL_BOUNCE,
	};
	private State reelState;
	
	/****************************************************************
	 * visibleSymbols holds the symbols drawn the screen and 
	 * reelStrip is a reference to int array defining the math
	 ***************************************************************/
	public List<ReelSymbol> visibleSymbols = new ArrayList<ReelSymbol>();
	private int[] reelStrip;
	
	public int[] stopSymbols = {0, 0, 0, 0, 0};				// game outcome
	
	private IReelCallback callback = null;
	
	private ReelHighlight highlight;
	private float timeElapsedSec = 0;
	private float timeElapsed = 0;
	private float lastTimeElapsed = 0;
	private float lastTimeElapsedSec = 0;
	private BookOfKnight game;
 
	boolean isMagicianReel;
	boolean isMagicianReelAnim;
	
    public int index = 0;
    
    private ReelSymbol symbol;
    
	/**
	 * Reel constructor
	 * @param textures - reference to ReelTextures
	 * @param x - x position on the screen
	 * @param y - y position on the screen
	 * @param strip - int array (math)
	 */
//	public Reel(ReelTextures textures, SpineTextures spineTextures,float x, float y, int[] strip, DeepBlue game, int num) {
    public Reel(ReelTextures textures,float x, float y, int[] strip, BookOfKnight game, int num, SkeletonJson json, SkeletonData playerSkeletonData, AnimationStateData playerAnimationData) {
    	super();
		
		this.index = num;
		
		this.game = game;
		
		travel = 0;
		spriteOffset = 1; // sprite index at stop 0
		this.reelState = State.REEL_IDLE;
		this.reelStrip = strip;
		setPosition(x, y);
		setWidth(symbolWidth);
		setHeight(symbolHeight);

		//
		// Add reel highlight
		//
		highlight = new ReelHighlight(textures, game);
		
		highlight.setPosition(-50, -60);		
		
		
		isMagicianReel = false;
		isMagicianReelAnim = false;

		ReelSymbol symbol;
		
		for (int i = 0; i < (SYMBOLS + 2); i++ ) {
			currentStop++;
			currentStop %= reelStrip.length;	
//			symbol = new ReelSymbol(textures, new SpineTexturesSymbols(game), this, reelStrip[currentStop], game,  json, playerSkeletonData, playerAnimationData);
			symbol =  new ReelSymbol(textures, this, reelStrip[currentStop], game);
			symbol.setPosition(0, game.SYMBOLS*pitch - pitch*i);
			symbol.setHeight2(symbol.textures.getSymbolTextureRegionHeight(i, 0));	
			symbol.setWidth(symbol.textures.getSymbolTextureRegionWidth(i, 0));
			symbol.setBounds(symbol.getX(), symbol.getY(), symbol.getWidth(), symbol.getHeight());
			visibleSymbols.add(symbol);
			this.addActor(symbol); 
			trim(symbol);
		}
		
	}
    

	public int getReelNum(){
		return this.index; 
	}
	
	@Override
    public void act(float delta){

		//
		// Update symbols at 25fps
		//
		timeElapsed += delta;
		timeElapsedSec += delta;
		
		if( timeElapsed - lastTimeElapsed > 0.030f ) {
//		if( timeElapsed - lastTimeElapsed > 0.040f ) {
			
			lastTimeElapsed = timeElapsed;
			
			for (int i = 0; i < SYMBOLS + 2; i++) {
				visibleSymbols.get(i).act(delta);
				
//				visibleSymbols.get(i).actSpine(delta);
			}

			highlight.act(delta);	
			
		}
		
		if( timeElapsedSec - lastTimeElapsedSec > 0.010f ) {
			
			lastTimeElapsedSec = timeElapsedSec;
		
//		if(index == 0){
//		game.jackpot.acting(delta);
//		}
		
		reelClick = false;
		
		// FSM
		switch (reelState) {
				
			case REEL_IDLE:
				if (startSpin) {
					startSpin		= false;
					stopSpin		= false;
					skillStopSpin	= false;
					clicksToStop 	= 0;
					dampedIndex     = 0;
					frameCnt		= 0;
					reelState		= State.REEL_SPIN;
					
					for (int i = 0; i < SYMBOLS + 2; i++) {		
						visibleSymbols.get(i).stopAnim();
					}
				}
				
				// Adjust drawing position if symbol animations 
				// go outside their respective placeholders 
				for (int i = 0; i < SYMBOLS + 2; i++) {		
					visibleSymbols.get(i).adjustDrawingPosition();
				}
				
				break;
			
			case REEL_SPIN:
				
				spin(delta);
				if(frameCnt < 6) {
					switch(frameCnt) {
						case 0: debounce(1); break;
						case 2: debounce(2); break;
						case 4: debounce(3); break;
						default: break;
					}
					frameCnt++;
				}
				
				if(skillStopSpin) {
					//Gdx.app.debug("Reel: " + getX(), "reel skill stopped, travel: " + travel);
					clicksToStop = 0;
					reelState    = State.REEL_SKILL_STOP;	
				}
				else if(stopSpin) {
					//Gdx.app.debug("Reel: " + getX(), "reel stopped, travel: " + travel);
					clicksToStop = 0;
					reelState    = State.REEL_STOP;
				}
				
			break;
				
			case REEL_STOP:
				
				spin(delta);
				
				if(skillStopSpin) {
//					Gdx.app.debug("Reel: " + getX(), "reel skill stopped, travel: " + travel);
					clicksToStop = 0;
					reelState    = State.REEL_SKILL_STOP;	
					break;
				}				
				
				if(reelClick) {
//					if(callback != null && clicksToStop == 0)
//						callback.atReelStop();	
					clicksToStop++;
				}

				switch(clicksToStop) {
				case 1: 
				case 2: 
				case 3: stopHelper(clicksToStop); break;
				case 5: 
					
					for(int i = 0; i < 3; i++){
						game.overlay.showJackpotDigitsSymbols(index, i);
					}
					
					game.reels.playSoundNearMiss(index);
					stopSpin = false;
					reelState = State.REEL_BOUNCE;
					dampedIndex	= 0;
					if(callback != null && !skillStopSpin)
						callback.atReelStop();	
					break;
				default: break;
			}

				break;	
				
			case REEL_SKILL_STOP:
	
				//anton hack
//				spin(delta);
				spinSkillStop(delta);
	
				if (reelClick) {
					
					highlight.hide(-1);
					game.reels.playSoundNearMiss(index);
					skillStopSpin = false;
					skillStopHelper();
					reelState = State.REEL_BOUNCE;
					dampedIndex = 0;
					if (callback != null && index == 4)
						callback.atReelStop();
					for(int i = 0; i < 3; i++){
						game.overlay.showJackpotDigitsSymbols(index, i);
					}
				}
				
				break;
				
			case REEL_BOUNCE:		  
				highlight.hide(-1);
				
				if(frameCnt > 0) {
					switch(frameCnt) {

						case 5: debounce(2); break;
						case 3: debounce(1); break;
						case 1: debounce(0); break;
					
						default: break;
					}
					
					frameCnt--;
				}
				else {
					reelState = State.REEL_IDLE;
				}
				
				// Adjust drawing position if symbol animations 
				// go outside their respective placeholders 
				for (int i = 0; i < SYMBOLS + 2; i++) {	
					visibleSymbols.get(i).adjustDrawingPosition();
				}
				
				game.reels.animNearMiss(index);
				highlight.hide(-1);
				
//				if(frameCnt == 0){
//					game.overlay.showJackpotDigitsSymbols();
//				}

				break;

			default:
				reelState = State.REEL_IDLE;
				
				break;
		}		
		}
    }	
	
	public void setReelStrip(int[] strip){
		this.reelStrip = strip;
	}
	/**
	 * Spins the reel
	 */
	public void spin(float delta) {
		
		ReelSymbol symbol;
		float nextY = 0;
		reelClick = false;
		int step = 0;
		
		if((stopSpin || skillStopSpin) && pitch - travel < STEP) { // mind last step when stopping
			//Gdx.app.debug("Reel: "+getX(), "stopSpin or skillStopSpin set, compansating last step: " + (pitch - travel) + ", travel: " + travel);
			step = pitch - travel;
		}
		else {
//			step = (int) ((int) STEP * 60 / (1/delta));
			step = STEP;
		}
		
		travel += step;
		
		//Gdx.app.debug("Reel: " + getX(), "spinning step: " + step + ", travel after stepping: " + travel);
		
		if(travel >= pitch) {
			
			travel %= pitch;

			spriteOffset += SYMBOLS + 1;
			spriteOffset %= (SYMBOLS + 2);	
			
			// click
			reelClick = true;
//			Gdx.app.debug("Reel: "+getX(), "reelClick at: " + travel);
		}
		
		
		// walk through all reel sprites and update y position
		for (int i = 0; i < SYMBOLS + 2; i++) {
			
			symbol = visibleSymbols.get(i);
							
			nextY = symbol.getY() - step;

			// wrap around
			if (nextY < y - (pitch /*+ pitch/2*/)) {
				
				//Gdx.app.debug("Reel: "+getX(), "wrap: " + nextY);
				
				nextY += (SYMBOLS + 2) * pitch;
				currentStop ++;
								
				currentStop %= reelStrip.length;
				
				symbol.setIndex(reelStrip[currentStop]);
				
				//Gdx.app.debug("Reel: "+getX(), "getIndex(): " + symbol.getIndex() + "setIndex(): " + reelStrip[currentStop]);
			}
//			Gdx.app.debug("Reel: " + index, i + " : symbolIndex = " + symbol.getIndex());
			// update symbol position
			symbol.setPosition(symbol.getX(), nextY);
			

//			symbol.updateSpeed(step);
			
			// trim top/bottom symbols
			trimSpin(symbol); 
//			trim(symbol); 
			
		}
		
			
	}	
	
	/**
	 * Spins the reel
	 */
	public void spinSkillStop(float delta) {
		
		ReelSymbol symbol;
		float nextY = 0;
		reelClick = false;
		int step = 0;
		
		if((stopSpin || skillStopSpin) && pitch - travel < STEP) { // mind last step when stopping
			//Gdx.app.debug("Reel: "+getX(), "stopSpin or skillStopSpin set, compansating last step: " + (pitch - travel) + ", travel: " + travel);
			step = pitch - travel;
		}
		else {
//			step = (int) ((int) STEP * 60 / (1/delta));
			step = STEP;
		}
		
		travel += step;
		
//		Gdx.app.debug("Reel: " + getX(), "spinning step: " + step + ", travel after stepping: " + travel);
		
		if(travel >= pitch) {
			
			travel %= pitch;
			
			spriteOffset += SYMBOLS + 1;
			spriteOffset %= (SYMBOLS + 2);	
			
			// click
			reelClick = true;
			//Gdx.app.debug("Reel: "+getX(), "reelClick at: " + travel);
		}
		
		
		// walk through all reel sprites and update y position
		for (int i = 0; i < SYMBOLS + 2; i++) {
			
			symbol = visibleSymbols.get(i);
			
			nextY = symbol.getY() - step;
			
			// wrap around
			if (nextY < y - (pitch /*+ pitch/2*/)) {
				
				//Gdx.app.debug("Reel: "+getX(), "wrap: " + nextY);
				
				nextY += (SYMBOLS + 2) * pitch;
				currentStop ++;
				currentStop %= reelStrip.length;
				
				symbol.setIndex(reelStrip[currentStop]);
				
				//Gdx.app.debug("Reel: "+getX(), "getIndex(): " + symbol.getIndex() + "setIndex(): " + reelStrip[currentStop]);
			}
			
			// update symbol position
			symbol.setPosition(symbol.getX(), nextY);
//			symbol.updateSpeed(step);
			// trim top/bottom symbols
			trimSpinSkill(symbol); 
			
		}
		
		
	}	
	
	/**
	 * Trim symbols outside reel margins
	 * @param symbol
	 */
	public void trimSpinSkill(ReelSymbol symbol) {
		
		float top = trimTopY; //y + SYMBOLS * pitch;
		
		float bottom = trimBottomY;
		float height = 0;
		int extraHeight = (symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0) - pitch)/2;
		
		if (symbol.getY() + pitch + extraHeight >= top) {
			
			
			height = (symbol.getY() - extraHeight >= top) ? 0 : (top - symbol.getY() + extraHeight);
			
			symbol.trimTop((int) (height));
			
//			Gdx.app.debug("Reel", "trimTop(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
			
		} else if (symbol.getY() - extraHeight < bottom) {
			
			height = (symbol.getY() + pitch + extraHeight <= bottom) ? 0 : (symbol.getY() + pitch + extraHeight - bottom);
			
			symbol.trimBottom((int) (height));
			
//			Gdx.app.debug("Reel", "trimBottom(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		} else {
			symbol.setHeight2(symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0));
			
//			Gdx.app.debug("Reel", "setHeight2(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		}
		
	}
	
	/**
	 * Trim symbols outside reel margins
	 * @param symbol
	 */
	public void trimSpin(ReelSymbol symbol) {
		
		float top = trimTopY; 
		
		float bottom = trimBottomY;
		float height = 0;
		int extraHeight = (symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0) - pitch)/2;

		if (symbol.getY() + pitch + extraHeight >= top) {

			
			height = (symbol.getY() - extraHeight >= top) ? 0 : (top - symbol.getY() + extraHeight);
			
			symbol.trimTop((int) (height));
			
//			Gdx.app.debug("Reel", "trimTop(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);

		} else if (symbol.getY() - extraHeight < bottom) {

			height = (symbol.getY() + pitch + extraHeight <= bottom) ? 0 : (symbol.getY() + pitch + extraHeight - bottom);
			
			symbol.trimBottom((int) (height));
			
//			Gdx.app.debug("Reel", "trimBottom(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		} else {
			symbol.setHeight2(symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0));
			
//			Gdx.app.debug("Reel", "setHeight2(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		}
		
//		symbol.setBounds(symbol.getX(), symbol.getY(), symbol.getWidth(),
//				symbol.getHeight());
		
//		Gdx.app.debug("REelSymbol", "(symbol.getY() = " + symbol.getY()); 
//		if(symbol.getY() < -60){
//			symbol.setHeight(symbol.getHeight());
//		}
	}
	
	/**
	 * Trim symbols outside reel margins
	 * @param symbol
	 */
	public void trim(ReelSymbol symbol) {
		
		float top = trimTopY; //y + SYMBOLS * pitch;
		float bottom = trimBottomY;
		float height = 0;
		int extraHeight = (symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0) - pitch)/2;

		if (symbol.getY() + pitch + extraHeight >= top) {

			height = (symbol.getY() - extraHeight >= top) ? 0 : (top - symbol.getY() + extraHeight);
			symbol.trimTop((int) (height));
			
//			Gdx.app.debug("Reel", "trimTop(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);

		} else if (symbol.getY() - extraHeight < bottom) {

			height = (symbol.getY() + pitch + extraHeight <= bottom) ? 0 : (symbol.getY() + pitch + extraHeight - bottom);
			symbol.trimBottom((int) (height));
			
//			Gdx.app.debug("Reel", "trimBottom(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		} else {
			symbol.setHeight2(symbol.textures.getSymbolTextureRegionHeight(symbol.getIndex(), 0));
			
//			Gdx.app.debug("Reel", "setHeight2(), height:  " + height + ", getY(): " + symbol.getY() + ", extraHeight: " + extraHeight);
		}
		
//		symbol.setBounds(symbol.getX(), symbol.getY(), symbol.getWidth(),
//				symbol.getHeight());
		
//		if(symbol.getY() < -60){
//			symbol.setHeight(0);
//		}
		
	}
	
	/**
	 * This function will bounce the reel to mimic physical reel behaviour. 
	 * Use when stopping the reel with dampedSinTable. 
	 * @param step
	 */
	public void bounce(float step) {
		
		ReelSymbol symbol;
		
		for (int i = 0; i < SYMBOLS + 2; i++) {		
			symbol = visibleSymbols.get(i);
			symbol.setPosition(symbol.getX(), symbol.getY() + step);
			symbol.updateSpeed((step < 0)? -1 * (int)step : (int)step);
			trim(symbol);
		}
	}
	
	public void debounce(int index) {
		
		ReelSymbol symbol;
		
		for (int i = 0; i < SYMBOLS + 2; i++) {		
			symbol = visibleSymbols.get(i);
			symbol.setBlurFrame(index);
			trim(symbol);
		}
	}	
	
	@Override
	public void draw(Batch batch, float alpha) {
		applyTransform(batch, computeTransform());

		highlight.draw(batch, alpha);
		
		// Draw background symbols
		for (Actor actor : getChildren()) {
			symbol = (ReelSymbol) actor;
			
			if(isMagicianReel)	symbol.setVisible(true);
			else				symbol.setVisible(!(symbol.ontop || symbol.isWinning));
		}
			
		drawChildren(batch, alpha);		
		
		resetTransform(batch);
	}	
	
	public void drawOnTop(Batch batch, float alpha) {
		applyTransform(batch, computeTransform());

		// Draw ontop symbols
		for (Actor actor : getChildren()) {
			symbol = (ReelSymbol) actor;
			symbol.setVisible(symbol.ontop);
		}
		drawChildren(batch, alpha);

		resetTransform(batch);
	}

	public void drawWinning(Batch batch, float alpha) {
		applyTransform(batch, computeTransform());

		for (Actor actor : getChildren()) {
			symbol = (ReelSymbol) actor;
			if(!symbol.showBonusSymbols){
				if(!isMagicianReel)	symbol.setVisible(symbol.isWinning && !symbol.ontop);
			}
		}
		
		drawChildren(batch, alpha);		
		
		resetTransform(batch);
	}		

	public void drawWilds(Batch batch, float alpha) {
		applyTransform(batch, computeTransform());
		
		for (Actor actor : getChildren()) {
			symbol = (ReelSymbol) actor;
			symbol.setVisible(symbol.getIndex() == game.math.WILD );
		}
		
		drawChildren(batch, alpha);		
		
		resetTransform(batch);
	}		
	
	/**
	 * This function will start a reel spin for 
	 * @param stopAtIndex - stop in game outcome
	 */
	public void startSpin(int stopAtIndex) {
		startSpin	= true;
		skillStopped = false;
		this.stopAtIndex = stopAtIndex;
	}
	
	/**
	 * This function will start a reel spin for a predefined
	 * number of stops
	 * @param stopAtIndex - stop in game outcome
	 * @param stopsToSpin - number of stops to spin
	 */
	public void startSpin(int stopAtIndex, int stopsToSpin) {
		startSpin	= true;
		this.stopAtIndex = stopAtIndex;
	}
	
	/**
	 * Signal reel stop
	 */
	public void stop() {
		stopSpin = true;
		//Gdx.app.debug("Reel: "+getX(), "stop(), current distance travelled: " + travel);
	}
	
	/**
	 * Signal skill stop reel
	 */
	public void skillStop() {
		skillStopSpin = true;
		skillStopped = true;
	}
	
	public void skillStopHelper() {
		
		visibleSymbols.get(spriteOffset).setIndex(stopSymbols[0]);
		visibleSymbols.get((spriteOffset + 1) % (SYMBOLS + 2)).setIndex(stopSymbols[1]);
		visibleSymbols.get((spriteOffset + 2) % (SYMBOLS + 2)).setIndex(stopSymbols[2]);
//		visibleSymbols.get((spriteOffset + 3) % (SYMBOLS + 2)).setIndex(stopSymbols[3]);
//		visibleSymbols.get((spriteOffset + 4) % (SYMBOLS + 2)).setIndex(stopSymbols[4]);
		
//		Gdx.app.debug("skillStopHelper Reel: " + getX(), "stopHelper(), stopSymbols: " + stopSymbols[0] + ", " + stopSymbols[1] + ", " + stopSymbols[2] + ", " + stopSymbols[3] );
//		game.gsLink.console("skillStopHelper Reel: " + getX() +  "stopHelper(), stopSymbols: " + stopSymbols[0] + ", " + stopSymbols[1] + ", " + stopSymbols[2] + ", " + stopSymbols[3] );
	}
	
	public void stopHelper(int index) {
		
//		Gdx.app.debug("stopHelper", "spriteOffset = " + spriteOffset + "  index = " + index );
		
//		visibleSymbols.get((spriteOffset + 3) % (SYMBOLS + 2)).setIndex(stopSymbols[3]);
		visibleSymbols.get((spriteOffset + SYMBOLS) % (SYMBOLS + 2)).setIndex(stopSymbols[SYMBOLS - index]);
	}
	public ReelSymbol getReelSymbolAtStop(int stop) {
		return visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2));
	}
	
	/**
	 * set reel stop symbols
	 */
	public void setStopSymbols(Integer[] symbols){
	    for (int i = 0; i < SYMBOLS; i++) {
	        stopSymbols[i] = symbols[i];
	    }
	}
	
	public boolean isReelStopped() {
		return (reelState == State.REEL_IDLE);
	}

	public boolean isReelStopping() {
		return stopSpin;
	}	
	
	public boolean isSkillStopped() {
		return skillStopped;
	}
	
	public void setSpecialReel(boolean special){
		specialReel = special;
	}
	
	public boolean isSpecialReel(){
		return specialReel;
	}
	
	public void setSpecialReelOnTop(int stop, boolean top){
		visibleSymbols.get((spriteOffset + stop) % 5).setOnTop(top);
	}
	
	/**
	 * Highlight reel 
	 * @param int - iterations
	 */
	public void highlight() {
		highlight.show();
	}
	
	public void setStep(int step){
		STEP = step;
	}
	
	public boolean highlightIsStopped()
	{
		return highlight.isStopped();
	}
	
	public void unhighlight(int iterations) {
		highlight.hide(iterations);
	}	
	
	public void unhighligthSpine(){
		for( int i = 0; i < (SYMBOLS + 2); i++ ) {
			visibleSymbols.get(i).unhighlightSpine(false);
			visibleSymbols.get(i).setDimmingAnim(0);
			visibleSymbols.get(i).isWinning = false;
			visibleSymbols.get(i).disble = false;
		}
	}
	
	/**
	 * Highlight scatters if any
	 */
	public boolean highlightScatters() {
		boolean result = false;
		for(int i=0; i < 3; i++) {
			if(visibleSymbols.get((spriteOffset + i) % (SYMBOLS + 2)).getIndex() == 0) {
				visibleSymbols.get((spriteOffset + i) % (SYMBOLS + 2)).animate(true, false, 0);
				result = true;
			}
		}
		return result;
	}
	
	public void highlightSymbolAtStop(int stop, boolean looping, boolean hasWildWin, int mult) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).animate(looping, hasWildWin, mult);
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).isWinning = true;
		
	}	

	public void restoreOldSymbols(int stop, int index) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).restoreOldSymbols(index);
//		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).refreshOldSymbols(index);
	}

	public void hideSymbols(int stop) {
		Gdx.app.debug("REEL", "pos" + (spriteOffset + stop) % (SYMBOLS + 2));
		
		Gdx.app.debug("REEL", "index = " + (visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).getIndex()));
		
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).hideSymbols();
	}	

	public void highlightWinSymbolAtStop(int stop, boolean looping, boolean hasWildWin, int mult) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).winSymbol(looping, hasWildWin, mult);
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).isWinning = true;
	}	

	public void highlightWinSymbolAtStopAllLines(int stop, boolean looping, boolean hasWildWin, int mult) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).winSymbolAllLines(looping, hasWildWin, mult);
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).isWinning = true;
		
	}	
	
	public void highlightWildMult(int stop, boolean looping, boolean hasWildWin) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).wildMult(looping, hasWildWin);
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).isWinning = true;
		
	}	

	public void highlightHoldAndWinSymbol(int stop) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).showHoldAndWinSymbolAnim();
	}
	
	public void showSpecialSymbolAnim(int stop, int symbol) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).showSpecialSymbolAnim(symbol);
	}	

	public void highlightCurrentScatterSymbol(int stop, boolean looping, boolean hasWildWin) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).isWinning = true;
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).index = game.math.SCATTER;
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).animateScatterSymbol(looping, hasWildWin);
		
	}	
	
//	public void animateSymbolAtStop(int stop, boolean looping) {
//		visibleSymbols.get((spriteOffset + stop) % 5).animate(looping);
//	}	
	
	public void setFlipSymbolAtStop(int stop, int symbol) {
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).setFlipSimbol(symbol);
	}
	
	
	public void removeHighligth(){
		for( int i = 0; i < (SYMBOLS + 2); i++ ) {
			visibleSymbols.get(i).highlight(false);
			visibleSymbols.get(i).loop(false);
			visibleSymbols.get(i).setDimmingAnim(0);
			visibleSymbols.get(i).isWinning = false;
			visibleSymbols.get(i).disble = false;
//			visibleSymbols.get(i).stopAnim();
		}
		highlight.hide(-1);
	}

	public void setOldMatrix(){
		for( int i = 0; i < (SYMBOLS + 2); i++ ) {
			visibleSymbols.get(i).highlight(false);
			visibleSymbols.get(i).loop(false);
			visibleSymbols.get(i).setDimmingAnim(0);
			visibleSymbols.get(i).isWinning = false;
			visibleSymbols.get(i).disble = false;
//			visibleSymbols.get(i).stopAnim();
		}
		highlight.hide(-1);
	}

	/**
	 * Remove highlight keeping special symbols animation running
	 * @param keepSpecialSymbols boolean
	 */
	public void removeHighligth(boolean keepSpecialSymbols){
		for( int i = 0; i < (SYMBOLS + 2); i++ ) {
			visibleSymbols.get(i).highlight(false);
			
			// Skip special symbols
			if(!((visibleSymbols.get(i).getIndex() == 10 || visibleSymbols.get(i).getIndex() == 0) && keepSpecialSymbols))
				visibleSymbols.get(i).loop(false);
			
			visibleSymbols.get(i).setDimmingAnim(0);
			visibleSymbols.get(i).isWinning = false;
		}
		highlight.hide(-1);
	}	
	
	public void registerCallback(IReelCallback callback) {
		this.callback = callback;
	}
	
	public void untegisterCallback() {
		this.callback = null;
	}
	
	public void setMagicianReel(boolean show) {
		isMagicianReel = show;
		if(!show)
		{
			for (int i = 0; i < SYMBOLS + 2; i++) {
				visibleSymbols.get(i).setShowMagician(false);
			}
		}
	}

	public boolean getMagicianReel() {
		return isMagicianReel;
	}

	public void setMagicianReelAnim(boolean show) {
		isMagicianReelAnim = show;
	}

	public boolean getMagicianReelAnim() {
		return isMagicianReelAnim;
	}
	
	public void animateGrandJackpotSymbol(int stop, int index, boolean transform){
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).animateGrandJackpotSymbol(index, transform);
	}

	public void setTransformAnim(int stop){
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).setTransformAnim();
	}
	
	public void hideBonus(int stop){
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).hideBonus();
	}

//	public void setTransformSymbol(int stop){
//		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).setTransformSymbol();
//	}
	
	public void animateHide(int stop, boolean show){
		visibleSymbols.get((spriteOffset + stop) % (SYMBOLS + 2)).animateHide(show);
	}

	public void setStackedSymbolAtStop(int stop, boolean stack, int reel) {
		visibleSymbols.get((spriteOffset + stop) % 5).setStacked(stack, reel, (spriteOffset + stop) % 5);
	}
	
	
	public void resetJackpotDigitsSymbols() {
		for(int i = 0; i < game.SYMBOLS; i++){
			game.overlay.showJackpotDigitsSymbols(index, i);
		}
	}
	
	public void clearStackedSymbolAtStop(boolean stack, int reel) {
		for( int i = 0; i < 3; i++ ) {
			visibleSymbols.get(i).setStacked(stack, reel, i);
		}
	}
}
