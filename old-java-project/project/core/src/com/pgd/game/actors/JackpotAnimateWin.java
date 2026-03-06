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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;



public class JackpotAnimateWin extends Group {
	
	BookOfKnight game;

	private final int REELS_SPACING = 282;
	private final int REELS_POSITION_X = 257; // left, left-most reel
	private final int REELS_POSITION_Y = 1080 - 264 - 667; // bottom, left-most reel
	private final int pitch = 272;
	
	private List<JackpotFireAnimLoop> fireAnim = new ArrayList<JackpotFireAnimLoop>();
	
	JackpotFireWin jackpotFireWin;
	
	private boolean hasAnimationStopped = false;
	
	private int currPosition = 0;
	private long currentWin = 0;
	
	private int grandCnt = 0;
	
	public int markedMatrix[][] = { 
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
	};
	
	private List<MoveToAction>   move = new ArrayList<MoveToAction>();
	
//	private List<SequenceAction> sequence = new ArrayList<SequenceAction>();

	public JackpotAnimateWin(BookOfKnight game) {
		this.game = game;
		
		jackpotFireWin = new JackpotFireWin(game);
		
		//this.setVisible(true);
	}
	
	public void loadAssets() {
	
	}
	
	public void commitAssets() {
		
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				game.textures.getInterfaceAtlas().findRegion("egg_digit_x").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				fireAnim.add(new JackpotFireAnimLoop(game));
				fireAnim.get(pos * game.REELS + reel).setVisible(false);
				addActor(fireAnim.get(pos * game.REELS + reel));
				
				move.add(new MoveToAction());
			}
		}
		
		addActor(jackpotFireWin);
	}
	
	public boolean hasAnimationStopped(){
		return hasAnimationStopped;
	}
	
	Action moveFinish= new Action(){
		  @Override
		  public boolean act(float delta){
				for(int reel = 0; reel < game.REELS; reel++){
					for(int pos = 0; pos < game.SYMBOLS; pos++){
					  	fireAnim.get(pos * game.REELS + reel).hide();
						fireAnim.get(pos * game.REELS + reel).setVisible(false);
					}
				}
				
				jackpotFireWin.animate(currentWin);
			  	
				return true;
		  }
	};
	
	public void reset(){
		for(int reel = 0; reel < game.REELS; reel++){
			for(int pos = 0; pos < game.SYMBOLS; pos++){
				markedMatrix[pos][reel] = 0;
			}
		}
		hasAnimationStopped = false;
		currentWin = 0;
	}
	private float positionMatrix[][][] = {
		{{35,20} ,{20,10} , {-5,0},  {-20,2}, {-40,8}},
		{{45,30} ,{30,15} , {-5,2},  {-30,2},  {-50,17}},
		{{50,55} ,{45,38} , {-5,5},  {-45,25}, {-60,38}},
	};

	private float positionEndMatrix[][][] = {
			{{35,20} ,{20,10} , {-5,0},  {-20,2}, {-40,8}},
			{{45,30} ,{30,15} , {-5,2},  {-30,2},  {-50,17}},
			{{50,55} ,{45,38} , {-5,5},  {-45,25}, {-60,38}},
	};
	
	public void animate(){
		
		boolean anim = false;
		
			for(int reel = 0; reel < game.REELS; reel++){
				for(int pos = 0; pos < game.SYMBOLS; pos++){
				if((game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_SYMBOL ||
						game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_MINOR ||
								game.reels.matrixHoldAndWin[pos][reel] == game.math.JACKPOT_MAJOR)
						&& markedMatrix[pos][reel] == 0){
					
					anim = true;
					
					currentWin += game.reels.matrixJackpotDigits[pos][reel] * game.meters.getTotalBet();

					currPosition = pos * game.REELS + reel;
					
					markedMatrix[pos][reel] = 1;
					
					// Constants for initial and final positions
					float startX = REELS_POSITION_X + 100 + reel * REELS_SPACING + 45;
					float startY = REELS_POSITION_Y - pos * pitch + 610;

					float endX = 626 + 360;
					float endY = 1080 - 880 - 188 + 30 - 40 + 10;
					

				    // Calculate rotation angle
				    float deltaX = endX - startX;
				    float deltaY = endY - startY;
				    float angleRad = (float) Math.atan2(deltaY, deltaX);
				    float angleDeg = MathUtils.radiansToDegrees * angleRad + 90; // or + 90 if needed

				    
				    float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

				    // Set a constant speed for movement
				    float speedValue = 1400 * 2; // Adjust this value as needed for desired speed

				    // Calculate duration based on distance and speed
				    float durationValue = distance / speedValue;
				 
				    // Set rotation to follow the movement
				    fireAnim.get(currPosition).setRotationAnt(angleDeg);

				    game.overlay.jackpotDigitsSymbols.setDeact(pos, reel, true);
				    fireAnim.get(currPosition).setPosition(startX + positionMatrix[pos][reel][0], startY + positionMatrix[pos][reel][1]);
				    fireAnim.get(currPosition).setVisible(true);
				    fireAnim.get(currPosition).animate();
					
					move.get(currPosition).reset();
					move.get(currPosition).setDuration(durationValue);
//					move.get(currPosition).setInterpolation(Interpolation.pow3);
					move.get(currPosition).setInterpolation(Interpolation.pow2);
//					move.get(currPosition).setInterpolation(Interpolation.smooth);
					move.get(currPosition).setPosition(endX +  + positionMatrix[pos][reel][0], endY + positionMatrix[pos][reel][1]);
					jackpotFireWin.setPosition(626 + 360 - 20, 1080 - 880 - 188 - 10);
					
					SequenceAction sequenceAction = new SequenceAction();
					sequenceAction = Actions.sequence(move.get(currPosition), moveFinish);
					fireAnim.get(currPosition).addAction(sequenceAction);
					
					return;
				}
			}
		}
		
		if(!anim){
			hasAnimationStopped = true;
		}
	}
	
	public void animateJackpotSymbols(){
		
		boolean anim = false;
	
		grandCnt = 0;
		
		for(int reel = 0; reel < game.REELS; reel++){
			for(int pos = 0; pos < game.SYMBOLS; pos++){
				if(game.reels.matrix[pos][reel] == game.math.JACKPOT_GRAND){
					grandCnt++;
				}
			}
		}
		
		if(grandCnt > 0){
			int cnt = 0;
			
			game.sounds.play(SoundTrack.SHAKE_EGG, false);
			
			for(int reel = 0; reel < game.REELS; reel++){
				for(int pos = 0; pos < game.SYMBOLS; pos++){

					if(game.reels.matrix[pos][reel] == game.math.JACKPOT_GRAND){
						
						cnt++;
						Gdx.app.debug("Jackpot Anim ", "grandCnt = " + grandCnt + " ::: cnt = " + cnt);
						
						game.overlay.jackpotSymbols.showSymbol(false, reel, pos, game.reels.matrixHoldAndWin[pos][reel]);
						game.reels.reels.get(reel).animateGrandJackpotSymbol(pos, game.reels.matrixHoldAndWin[pos][reel], (grandCnt > 2 && cnt <= 3) ? false : true);
						
					}
				}
			}
		} else {
			animate();
		}
		
		
	}

	public void animateGrandJackpot(){
		int cnt = 0;
		
		if(grandCnt > 2){
			
			currentWin = 1000 * game.meters.getTotalBet();
			
			for (int reel = 0; reel < game.REELS; reel++) {
				for (int pos = 0; pos < game.SYMBOLS; pos++) {
					
					if(game.reels.matrix[pos][reel] == game.math.JACKPOT_GRAND && cnt <= 2){
						cnt++;
						
						currPosition = pos * game.REELS + reel;
						
						markedMatrix[pos][reel] = 1;
						
						// Constants for initial and final positions
						float startX = REELS_POSITION_X + 100 + reel * REELS_SPACING + 45;
						float startY = REELS_POSITION_Y - pos * pitch + 610;
	
						float endX = 626 + 360;
						float endY = 1080 - 880 - 188 + 30 - 40 + 10;
						
	
					    // Calculate rotation angle
					    float deltaX = endX - startX;
					    float deltaY = endY - startY;
					    float angleRad = (float) Math.atan2(deltaY, deltaX);
					    float angleDeg = MathUtils.radiansToDegrees * angleRad + 90; // or + 90 if needed
	
					    
					    float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	
					    // Set a constant speed for movement
					    float speedValue = 1400; // Adjust this value as needed for desired speed
	
					    // Calculate duration based on distance and speed
					    float durationValue = distance / speedValue;
					 
					    // Set rotation to follow the movement
					    fireAnim.get(currPosition).setRotationAnt(angleDeg);
	
					    game.overlay.jackpotDigitsSymbols.setDeact(pos, reel, true);
					    fireAnim.get(currPosition).setPosition(startX + positionMatrix[pos][reel][0], startY + positionMatrix[pos][reel][1]);
					    fireAnim.get(currPosition).setVisible(true);
					    fireAnim.get(currPosition).animate();
						
						move.get(currPosition).reset();
						move.get(currPosition).setDuration(durationValue);
	//					move.get(currPosition).setInterpolation(Interpolation.pow3);
						move.get(currPosition).setInterpolation(Interpolation.pow2);
	//					move.get(currPosition).setInterpolation(Interpolation.smooth);
						move.get(currPosition).setPosition(endX +  + positionMatrix[pos][reel][0], endY + positionMatrix[pos][reel][1]);
						jackpotFireWin.setPosition(626 + 360 - 20, 1080 - 880 - 188 - 10);
						
						SequenceAction sequenceAction = new SequenceAction();
						sequenceAction = Actions.sequence(move.get(currPosition), moveFinish);
						fireAnim.get(currPosition).addAction(sequenceAction);
					
					}
				}
			}
		} else {
			animate();
		}
		
	}
}
	
