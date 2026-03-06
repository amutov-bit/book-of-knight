package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class BigAnimation extends Group {
	
	BookOfKnight game;

	
	
	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	
	private int currentFrame;
	private int currentFrameWild;
	private boolean animateBlue;
	private boolean animateOrange;
	private boolean stopAnimation;
	
	private boolean load;
	private boolean playWildAnim;
	
	private Array<AtlasRegion> blueAnim;
	private Array<AtlasRegion> orangeAnim;
	
	public BigAnimation(BookOfKnight game) {
		this.game = game;
		load = false;
		playWildAnim = false;
	}
	
	public void loadAssets(int currentFile){
		load = false;
		
		switch(currentFile)
		{
			case 0:
				game.ondemandAssetManager.load("animations/blue_anim-1.png", Texture.class);
			break;
			case 1:
				game.ondemandAssetManager.load("animations/blue_anim-2.png", Texture.class);
				break;
			case 2:
				game.ondemandAssetManager.load("animations/orange_anim-1.png", Texture.class);
				break;
			case 3:
				game.ondemandAssetManager.load("animations/orange_anim-2.png", Texture.class);
				break;
			case 4:
				game.ondemandAssetManager.load("animations/orange_anim-3.png", Texture.class);
				break;
			case 5:
				game.ondemandAssetManager.load("animations/blue_anim.atlas", TextureAtlas.class);
			break;
			case 6:
				game.ondemandAssetManager.load("animations/orange_anim.atlas", TextureAtlas.class);
			break;
			default:
				break;
		}
	}
	
	public void commitAssets() {
		blueAnim = game.manager.get("animations/blue_anim.atlas", TextureAtlas.class).findRegions("blue_fx");
		orangeAnim = game.manager.get("animations/orange_anim.atlas", TextureAtlas.class).findRegions("orange_fx");
		
		load = true;

	}
	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		
		if (timeElapsed - lastTimeElapsed > 0.040f) {
				lastTimeElapsed = timeElapsed;
				
				if (animateBlue || animateOrange) {
					if(++currentFrame > 14)
					{  
						currentFrameWild = -1;
						animateBlue = false;
						animateOrange = false;
						playWildAnim = true;
						game.reels.setTransformAnim();
					}
				}
				
				if(playWildAnim){
					if(++currentFrameWild > 8){
						stopAnimation = true;
						playWildAnim = false;
						currentFrame = 0;
						currentFrameWild = 0;
					}
				}
			}
			
		
	}
	
    @Override
    public void draw(Batch batch, float alpha){
	    
    	if(animateBlue && load && currentFrame < 15){
				 	float offsetX = (blueAnim.get(currentFrame).getRotatedPackedWidth()  - blueAnim.get(currentFrame).getRegionWidth())  / 2;
				 	float offsetY = (blueAnim.get(currentFrame).getRotatedPackedHeight() - blueAnim.get(currentFrame).getRegionHeight())  / 2;
				 	
				 	blueAnim.get(currentFrame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
					batch.draw(blueAnim.get(currentFrame).getTexture(),
		    				0 + offsetX +  blueAnim.get(currentFrame).offsetX,
		        			0 + offsetY +  blueAnim.get(currentFrame).offsetY,
		        			blueAnim.get(currentFrame).getRegionWidth()/2,
		        			blueAnim.get(currentFrame).getRegionHeight()/2,
		        			blueAnim.get(currentFrame).getRegionWidth(),
		        			blueAnim.get(currentFrame).getRegionHeight(),
		        			1f,
		        			1f,
		        			(blueAnim.get(currentFrame).rotate) ? -90f : 0f,
		        			blueAnim.get(currentFrame).getRegionX(), 
		        			blueAnim.get(currentFrame).getRegionY(), 
		        			blueAnim.get(currentFrame).getRegionWidth(), 
		        			blueAnim.get(currentFrame).getRegionHeight(),
		        			false,
		        			false);
    	}
    	
    	if(animateOrange && load && currentFrame < 15){
				 	float offsetX = (orangeAnim.get(currentFrame).getRotatedPackedWidth() - orangeAnim.get(currentFrame).getRegionWidth())  / 2;
				 	float offsetY = (orangeAnim.get(currentFrame).getRotatedPackedHeight() - orangeAnim.get(currentFrame).getRegionHeight())  / 2;
				 	orangeAnim.get(currentFrame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
					batch.draw(orangeAnim.get(currentFrame).getTexture(),
		    				0 + offsetX +  orangeAnim.get(currentFrame).offsetX,
		        			0 + offsetY +  orangeAnim.get(currentFrame).offsetY,
		        			orangeAnim.get(currentFrame).getRegionWidth()/2,
		        			orangeAnim.get(currentFrame).getRegionHeight()/2,
		        			orangeAnim.get(currentFrame).getRegionWidth(),
		        			orangeAnim.get(currentFrame).getRegionHeight(),
		        			1f,
		        			1f,
		        			(orangeAnim.get(currentFrame).rotate) ? -90f : 0f,
		        			orangeAnim.get(currentFrame).getRegionX(), 
		        			orangeAnim.get(currentFrame).getRegionY(), 
		        			orangeAnim.get(currentFrame).getRegionWidth(), 
		        			orangeAnim.get(currentFrame).getRegionHeight(),
		        			false,
		        			false);
    	}
					
    	    	
    }

	public void animateBlue(){
		animateBlue = true;
		stopAnimation = false;
//		game.sounds.play(SoundTrack.BLUE, false);
	}
	
	public void animateOrange(){
		animateOrange = true;
		stopAnimation = false;
//		game.sounds.play(SoundTrack.ORANGE, false);
	}
	
	public void reset(){
		stopAnimation = true;
		animateBlue = false;
		animateOrange = false;
		currentFrame = 0;
	}
	
	public boolean animationStopped(){
		return stopAnimation;
	}
	
	public boolean loaded(){
		return load;
	}
}
