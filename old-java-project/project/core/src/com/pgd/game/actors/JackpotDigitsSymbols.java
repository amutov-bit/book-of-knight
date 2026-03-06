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
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;



public class JackpotDigitsSymbols extends Group {
	
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
	
	private List<DigitsJackpot> jakcpotDigits = new ArrayList<DigitsJackpot>();
	
	private List<Group> overlay = new ArrayList<Group>();

	private List<Image> jackpotLevelsDeact = new ArrayList<Image>();
	
	private SpriteDrawable drawable;
	
	public JackpotDigitsSymbols(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		

	}
	
//	@Override
//    public void act(float delta){
//		
//		super.act(delta);
//		if (animate) {
//			timeElapsed		+= delta;
//		
//			if (timeElapsed - lastTimeElapsed > 0.030f) {
//				lastTimeElapsed = timeElapsed;
//			
//				if(changeReel){
//					animate = false;
//					hasAnimationStop = true;
//					reel = 0;
//				}
//			}
//		}
//	}
	
	public void commitAssets() {
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				game.textures.getInterfaceAtlas().findRegion("shield_deact").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				jackpotLevelsDeact.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("shield_deact"))));
				addActor(jackpotLevelsDeact.get(pos * game.REELS + reel));
				jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(false);
			}
		}
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				overlay.add(new Group());
				jakcpotDigits.add(new DigitsJackpot(game));
				jakcpotDigits.get(pos * game.REELS + reel).setVisible(false);
				jakcpotDigits.get(pos * game.REELS + reel).commitAssets();
//				jakcpotDigits.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X + reel * REELS_SPACING, REELS_POSITION_Y - pos * pitch);
				jakcpotDigits.get(pos * game.REELS + reel).setScale(1f);
//				addActor(jakcpotDigits.get(pos * game.REELS + reel));
				overlay.get(pos * game.REELS + reel).addActor(jakcpotDigits.get(pos * game.REELS + reel));
				addActor(overlay.get(pos * game.REELS + reel));
			}
		}
		
		
//		game.textures.getInterfaceAtlas().findRegion("mega_deact").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		jackpotLevels.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_deact"))));
//		jackpotLevels.get(1).setPosition(8, 1080 - 220 - 148 - 340);
//		addActor(jackpotLevels.get(1));
//
//		game.textures.getInterfaceAtlas().findRegion("mega_deact").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		jackpotLevels.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_deact"))));
//		jackpotLevels.get(2).setPosition(8, 1080 - 220 - 148 - 340);
//		addActor(jackpotLevels.get(2));
		
	}

	public void showJackpotDigits(int reel, int pos){
		
//		if(symbol == 10){
//			offsetX = -11f;
//			offsetY = -57f;
//		}
		
//		for(int pos = 0; pos < game.SYMBOLS; pos++){
//			for(int reel = 0; reel < game.REELS; reel++){
				
//				Gdx.app.debug("JackpotDigits", "game.reels.matrix["+pos+"]["+reel+"]= " + game.reels.matrix[pos][reel]);
						
		
				if(game.reels.matrix[pos][reel] == game.math.JACKPOT_SYMBOL){
					jakcpotDigits.get(pos * game.REELS + reel).setVisible(true);
					
					int offsetX = 45;
					int offsetY = 0;
					overlay.get(pos * game.REELS + reel).setScale(1f);
					
					if(game.reels.matrixJackpotDigits[pos][reel] * game.meters.getTotalBet() >= 100000){
						overlay.get(pos * game.REELS + reel).setScale(0.80f);
						offsetX = 75;
						offsetY = 5;
					}
					
					if(game.reels.matrixJackpotDigits[pos][reel] * game.meters.getTotalBet() >= 1000000){
						overlay.get(pos * game.REELS + reel).setScale(0.70f);
						offsetX = 85;
						offsetY = 10;
					}

					if(game.reels.matrixJackpotDigits[pos][reel] * game.meters.getTotalBet() >= 10000000){
						overlay.get(pos * game.REELS + reel).setScale(0.60f);
						offsetX = 90;
						offsetY = 10;
					}

					overlay.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING + offsetX, REELS_POSITION_Y - pos * pitch + 640 + offsetY);

					jakcpotDigits.get(pos * game.REELS + reel).setWin(
												game.reels.matrixJackpotDigits[pos][reel] * game.meters.getTotalBet(),
												0,
												0
												);
					
//					Gdx.app.debug("JackpotDigits", "vlizaaa");
				}
//			}
//		}
		hasAnimationStop = false;

	}
	
    public void setDeact(int pos, int reel, boolean deac){
    	
    	if(game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_SYMBOL){
			drawable = new SpriteDrawable(new Sprite(game.textures.getInterfaceAtlas().findRegion("shield_deact")));
			jackpotLevelsDeact.get(pos * game.REELS + reel).setDrawable(drawable);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setSize(drawable.getMinWidth(), drawable.getMinHeight());
			jackpotLevelsDeact.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING - 11, REELS_POSITION_Y - pos * pitch + 618-99);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setScale(1f);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(true);
    		jakcpotDigits.get(pos * game.REELS + reel).setDeact(deac);
    	}
    	
		if(game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_MINOR){
			drawable = new SpriteDrawable(new Sprite(game.textures.getInterfaceAtlas().findRegion("mini_deact")));
			jackpotLevelsDeact.get(pos * game.REELS + reel).setDrawable(drawable);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setSize(drawable.getMinWidth(), drawable.getMinHeight());
			jackpotLevelsDeact.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING - 11, REELS_POSITION_Y - pos * pitch + 618-99);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setScale(1f);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(true);
		}

		if(game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_MAJOR){
			drawable = new SpriteDrawable(new Sprite(game.textures.getInterfaceAtlas().findRegion("major_deact")));
			jackpotLevelsDeact.get(pos * game.REELS + reel).setDrawable(drawable);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setSize(drawable.getMinWidth(), drawable.getMinHeight());
			jackpotLevelsDeact.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING - 11, REELS_POSITION_Y - pos * pitch + 618-99);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setScale(1f);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(true);
		}

		if(game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_GRAND){
			drawable = new SpriteDrawable(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_deact")));
			jackpotLevelsDeact.get(pos * game.REELS + reel).setDrawable(drawable);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setSize(drawable.getMinWidth(), drawable.getMinHeight());
			jackpotLevelsDeact.get(pos * game.REELS + reel).setPosition(REELS_POSITION_X  + reel * REELS_SPACING - 11, REELS_POSITION_Y - pos * pitch + 618-99);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setScale(1f);
			jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(true);
		}
		
    }

	public boolean hasAnimationStopped(){
		return hasAnimationStop;
	}
	
	public void resetJackpotDigits(){
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				game.reels.matrixHoldAndWin[pos][reel] = 0;
			}
		}
	}
	
	public void hideJackpotDigits(){
		
		animate = false;
		changeReel = false;
		reel = 0;
		
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				if(game.reels.matrixHoldAndWin[pos][reel] == 0){
					jakcpotDigits.get(pos * game.REELS + reel).setVisible(false);
					jakcpotDigits.get(pos * game.REELS + reel).setDeact(false);
					jackpotLevelsDeact.get(pos * game.REELS + reel).setVisible(false);
				}
			}
		}
	}
	
	
}
	
