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


public class Bonus extends Group {
	
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
	
	public Bonus(BookOfKnight game) {
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
		
		if (animate) {
			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				currentFrame++;
				if(currentFrame > 56)
				{
					currentFrame = 56;
//					if(++waitingFrame > 15)
					{
						stopAnimation = true; 
					}
				}
			}
		}
	}
    @Override
    public void draw(Batch batch, float alpha){
	    
    	if(animate)
    	{
			

			float scale = 1f;
			float alphaImg = 1f;
			
			color = getColor();
				
			if(currentFrame < 23){
				alphaImg = 1f;
			} else if(currentFrame >= 23 && currentFrame <= 37){
				alphaImg = 1f - (currentFrame - 23) * 0.071f;
			} else {
				alphaImg = 0f;
			}
			
			if(currentFrame <= 10) {
				scale = currentFrame * 0.1f;
			} else if(currentFrame <= 20){
				scale = 1f + (currentFrame - 10) * 0.2f;
			} else if(currentFrame <= 32) {
				scale = 3f + (currentFrame - 20) * 0.017f;
			} else {
				scale = 3.2f;
			}

			

			batch.setColor(color.r, color.g, color.b, alphaImg);
			
			batch.draw(game.textures.getInterfaceCommon().findRegion("bigwin_bg").getTexture(),
					(1280 - 710) / 2 - 120,
	    			0,
				   (float)game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionWidth()/2,
				   (float)game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionHeight()/2,
				   (float)game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionWidth(),
				   (float)game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionHeight(),
				   scale,
				   scale,
				   0f,
				   game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionX(), 
				   game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionY(),  
				   game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionWidth(),
				   game.textures.getInterfaceCommon().findRegion("bigwin_bg").getRegionHeight(),
				   false,
				   false);
	    	
		   batch.setColor(color.r, color.g, color.b, 1f);
				
			if(currentFrame == 0) {
				scale = 0f;
			} else if(currentFrame > 0  && currentFrame <= 18){
				scale = (currentFrame - 1) * 0.064f;
			} else {
				scale = 1.1f;
			}
			
			if(currentFrame >= 40){
				alphaImg = 1f - (currentFrame - 40) * 0.062f;
			} else {
				alphaImg = 1f;
			}
			
			float degrees = 0f;
			
			if(currentFrame >= 10){
				degrees = (currentFrame-10) * 2;
			}

//						
//			batch.setColor(color.r, color.g, color.b, alphaImg);
//			
//			batch.draw(game.textures.getInterfaceCommon().findRegion("cloud_1").getTexture(),
//					260,
//	    			640 - 640 - 0,
//				    game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionWidth()/2,
//				    game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionHeight()/2,
//				    game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionWidth(),
//				    game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionHeight(),
//				    scale,
//				    scale,
//				   -degrees,
//				   game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionX(), 
//				   game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionY(),  
//				   game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionWidth(),
//				   game.textures.getInterfaceCommon().findRegion("cloud_1").getRegionHeight(),
//				   false,
//				   false);
//			
//			batch.setColor(color.r, color.g, color.b, 1f);
			
			if(currentFrame < 4 ){
				scale = 0f;
			} else if(currentFrame <= 14 ){
				scale = (currentFrame - 4) * 0.12f;
			} else if(currentFrame == 15){
				scale = 1f;
			} else if(currentFrame == 16){
				scale = 0.8f;
			} else if(currentFrame <= 19){
				scale = 0.8f + (currentFrame - 16) * 0.033f;
			} else {
				scale = 1f;
			}
			
			if(currentFrame < 8){
				alphaImg = 0f;
			} else if(currentFrame <= 14){
				alphaImg = 0f + (currentFrame - 8) * 0.166f;
			} else {
				alphaImg = 1f;
			}
			
			batch.draw(game.textures.getInterfaceCommon().findRegion("bonus_txt").getTexture(),
					175,
					640 - 192 - 320 + 120,
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionWidth()/2,
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionHeight()/2,
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionWidth(),
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionHeight(),
					scale,
					scale,
					0f,
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionX(), 
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionY(),  
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionWidth(),
					game.textures.getInterfaceCommon().findRegion("bonus_txt").getRegionHeight(),
					false,
					false);
			
			batch.setColor(color.r, color.g, color.b, 1f);
			
		}
    	    	
    }
}
