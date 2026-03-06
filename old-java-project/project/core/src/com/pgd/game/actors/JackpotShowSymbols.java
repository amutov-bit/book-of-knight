package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;



public class JackpotShowSymbols extends Group {
	
//	private final int REELS_SPACING = 200 + 5;
//	private final int REELS_POSITION_X = 140 - 5; // left, left-most reel
//	private final int REELS_POSITION_Y = 720 - 401 - 173 + 347; // bottom, left-most reel

	private final int REELS_SPACING = 282;
	private final int REELS_POSITION_X = 257; // left, left-most reel
	private final int REELS_POSITION_Y = 1080 - 264 - 667; // bottom, left-most reel
	private final int pitch = 272;
			
	private BookOfKnight game;

	private int reel = 0;
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private boolean animate;
	private int currentFrame = 0;
	
	public boolean changeReel = false;
	
	
	public boolean hasAnimationStop;
	
	private List<JackpotSymbol> jackpotSymbols = new ArrayList<JackpotSymbol>();
	
	public JackpotShowSymbols(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		

	}
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		if (animate) {
			timeElapsed		+= delta;
		
			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
			
				if(changeReel){
					animate = false;
					hasAnimationStop = true;
					reel = 0;
				}
			}
		}
	}
	
	public void commitAssets() {
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				jackpotSymbols.add(new JackpotSymbol(game));
				jackpotSymbols.get(pos * game.REELS + reel).setVisible(false);
				jackpotSymbols.get(pos * game.REELS + reel).commitAssets();
				jackpotSymbols.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X + reel * REELS_SPACING, REELS_POSITION_Y - pos * pitch);
				jackpotSymbols.get(pos * game.REELS + reel).setScale(1f);
				addActor(jackpotSymbols.get(pos * game.REELS + reel));
			}
		}
		
	}

	public void showSymbol(boolean show, int reel, int pos, int symbol){
		
//		game.gsLink.console("pos = " + pos + "  ::::: reel = " + reel + " :::: symbol = " + symbol + "  :::::: show = " + show);
    	
//		float offsetX = 0;
//		float offsetY = 0;
//		
//		if(symbol == 10){
//			offsetX = -11f;
//			offsetY = -57f;
//		}
		
		jackpotSymbols.get(pos * game.REELS + reel).setVisible(show);
		jackpotSymbols.get(pos * game.REELS + reel).setScale(1f);
		jackpotSymbols.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING - 12, REELS_POSITION_Y - pos * pitch + 640 - 138 + 3+15);
		jackpotSymbols.get(pos * game.REELS + reel).showSymbol(symbol);
		
		hasAnimationStop = false;

	}

	public boolean hasAnimationStopped(){
		return hasAnimationStop;
	}
	
	public void hidejackpotSymbols(){
		
		animate = false;
		changeReel = false;
		reel = 0;
		
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				jackpotSymbols.get(pos * game.REELS + reel).setVisible(false);
			}
		}
	}
	
	
}
	
