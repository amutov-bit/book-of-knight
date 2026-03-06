package com.pgd.game.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class BGAnimation extends Group {
	
	BookOfKnight game;
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	
	private int currentFrame;

	
	private boolean animate;
	private boolean animateButton;
	private boolean stopAnimation;
	

	public BGAnimation(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets(){

	}
	

	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		
		if (animate) {
			game.spineBG.act(delta, 0);
		}

//				if (timeElapsed - lastTimeElapsed > 0.030f) {
//					lastTimeElapsed = timeElapsed;
//					currentFrame++;
//					
//					
//				if (animate) {
//					if(currentFrame > 140)
//					{
////						currentFrame = 240;
//						
////						animate = false;
//						
////						stopAnimation = true;
//					}
//				}
//				
//			}		
//				currentFrame++;

	}
	
    @Override
    public void draw(Batch batch, float alpha){
	    
    	if(animate)
    	{
			
    		
			if(game.spineBG.isLoaded()){
//				game.spineBG.setSpineAnimRegionX(0, 655 + 35 + 240);
//				game.spineBG.setSpineAnimRegionY(0, -10 + 20);	
				game.spineBG.setSpineAnimRegionX(0, 960 + 45);
				game.spineBG.setSpineAnimRegionY(0, 0 + 5);	
				game.spineBG.draw(batch, alpha);
			}

		}
    	    	
    }

	public void setVisible(boolean visible) {
		animate = visible;
		stopAnimation = !visible;
		currentFrame = 0;
		animateButton = true;
//		if(!visible){
//			game.spineBG.clearAnimationState();
//		}
//		
//		game.spineBG.setVisible(visible);
//		
//		if(visible)	game.sounds.play(SoundTrack.FREEGAMES, false);
//		else		game.sounds.stop(SoundTrack.FREEGAMES);
	}
	
	
	public boolean animationStopped(){
		return stopAnimation;
	}
}
