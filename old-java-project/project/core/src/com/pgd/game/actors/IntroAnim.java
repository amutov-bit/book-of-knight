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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;



public class IntroAnim extends Group {
	
	private BookOfKnight game;

	private TextureAtlas loadingAtlas;
	private Array<AtlasRegion> loadingRegions;	
	
	private AtlasRegion empty;
//	private Image slider;
	private float sliderPosX = (1920 - 600)/2;
	private float sliderPosY = (1020 - 164)/2;
	private float sliderProgressX = 0;
	private int currentFrame = 0;
	
	private int sliderPrev = 0;
	
	private int step = 580 / 12;
	
	private float timeElapsed;
	private float lastTimeElapsed;
	
	private boolean loaded = false;
	
	private boolean animate = false;
//	public Image logo;
	
	Image normal;
	
	public IntroAnim(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	@Override
	 public void act(float delta){
			
	if(this.isVisible()){
			timeElapsed		 += delta;
			
//			if(animate){
			game.spineIntro.act(delta, 0);
//			}

			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				currentFrame++;
				
				if(currentFrame > 30){
					game.gameAssetsManager.tapTo.setVisible(true);
				}
				
				if(/*currentFrame > 119 ||*/ game.spineIntro.getEnd()){
					this.setVisible(false);
					game.gameAssetsManager.tapTo.setVisible(false);
					animate= false;
					game.overlay.showAddFreeSpins();
				}
				
				
				if(currentFrame > 100){
					game.gameAssetsManager.tapTo.setVisible(false);
				}
				
				Gdx.app.debug("IntroAnim ", "currentFrame = " + currentFrame);
			}
	}


	 }
	
	public void commitAssets() {
		loaded = true;
		Texture texture = game.manager.get("bg.jpg", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		normal = new Image(texture);	
	}
	
	
    @Override
    public void draw(Batch batch, float alpha){

    	if(currentFrame < 120){
    		normal.draw(batch, alpha);
    	}
    	
		if(game.spineIntro.isLoaded()){
			game.spineIntro.setSpineAnimRegionX(0, 1920/2);
			game.spineIntro.setSpineAnimRegionY(0, 0);	
			game.spineIntro.draw(batch, alpha);
		}
		
    	if(loaded && currentFrame > 30){}
    }

}
	
