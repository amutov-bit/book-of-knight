package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class SpineSymbolAnimation extends Group {
	
	BookOfKnight game;

	
	
	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private Image freeGames, bg;
	private Digits digitsFrame;
	
	private int currentFrame;
	private int waitingFrame;
	private boolean animate;
	private boolean stopAnimation;
	
	private Color color;
	
	int firstFindSymbolReel = -1;
	
	int firstFindSymbolPos = -1;
	
	int firstFindWild = -1;
	
	int firstFindAddWild = -1;
	
	public SpineSymbolAnimation(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
		loadAssets();		
//		animate = false;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {

	}
	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;

		if(animate){
			for(int r = 0; r < game.REELS; r++){
				for (int i = 0; i < game.SYMBOLS + 2; i++) {
					
					if(firstFindSymbolReel == r){
						game.reels.reels.get(r).visibleSymbols.get(i).actSpine(delta);
					}

				}
			}
		}
		
	}
	
	public void startAnimLine(int index, int line){
		
		//find reel with first win
		for(int r = 0; r < game.REELS; r++){
			for (int s = 0; s < game.SYMBOLS; s++) {
				if(game.reels.matrix[s][r] == index && !(index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
					
//					game.gsLink.console("startAnimLine : game.reels.matrix[" + s + "][" + r + "] =  " + game.reels.matrix[s][r] + " ::: line = " + line);
					
					if(game.reels.matrix[game.math.lines[line][r]][r] == index){
						firstFindSymbolReel = r;
						firstFindSymbolPos  = s;
						r = game.REELS;
						s = game.SYMBOLS;
					}
				}
			}
		}
		
//		game.gsLink.console("startAnimLine : firstFindSymbolReel =  " + firstFindSymbolReel);
		animate = true;
	}

	public void startAnimScatter(int index){
		
		//find reel with first win
		for(int r = 0; r < game.REELS; r++){
			for (int s = 0; s < game.SYMBOLS; s++) {
				if(game.reels.matrix[s][r] == index && !(index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
					firstFindSymbolReel = r;
					firstFindSymbolPos  = s;
					r = game.REELS;
					s = game.SYMBOLS;
				}
			}
		}
		
		animate = true;
	}
	
	public void stopAnim(){
		animate = false;
		firstFindAddWild	= -1;
		firstFindWild  		= -1;
		firstFindSymbolReel = -1;
		firstFindSymbolPos  = -1;

	}
}
