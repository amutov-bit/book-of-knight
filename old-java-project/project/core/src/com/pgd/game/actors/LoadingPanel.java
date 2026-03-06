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



public class LoadingPanel extends Group {
	
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

	
	public LoadingPanel(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		

	}
	
	@Override
	 public void act(float delta){
			
		if(this.isVisible()){
			timeElapsed		 += delta;
			
//			if(animate){
				game.spineLoading.act(delta, 0);
//			}

			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				currentFrame++;
				if(currentFrame < 83){
					animate= false;
				}
				Gdx.app.debug("LoadingPanel ", "currentFrame = " + currentFrame);
			}
		}


	 }
	
	public void commitAssets() {
		loadingAtlas = game.manager.get("loading/loading.atlas", TextureAtlas.class);
		loadingRegions = loadingAtlas.findRegions("loading_full");
		
		empty = loadingAtlas.findRegion("loading_empty");//game.manager.get("loading_empty.png", Texture.class);

		loaded = true;
	}
	
	
    @Override
    public void draw(Batch batch, float alpha){

		if(game.spineLoading.isLoaded()){
			game.spineLoading.setSpineAnimRegionX(0, 1000 - 20);
			game.spineLoading.setSpineAnimRegionY(0, 0 + 30);	
			game.spineLoading.draw(batch, alpha);
		}
		
    	if(loaded && currentFrame > 30){
//			sliderProgressX = (int) ((game.ondemandAssetManager.progress()) * step + sliderPrev);
    		
    		float scaleFactor = 1f; 
    		float w = loadingRegions.get(0).getRegionWidth();
    		
    		sliderProgressX = (int) (game.ondemandAssetManager.progress() * w / scaleFactor);
			
			if (game.manager.isLoaded("loading/loading.atlas")) {
				batch.draw(empty, sliderPosX + 18, sliderPosY - 20);
			}

			if (game.manager.isLoaded("loading/loading.atlas")) {

				batch.draw(loadingRegions.get(0).getTexture(),
										sliderPosX + 18 , sliderPosY - 20,
										(int) (sliderProgressX) / 2,
										loadingRegions.get(0).getRegionHeight() / 2,
										(int) (sliderProgressX),
										loadingRegions.get(0).getRegionHeight(),
										1f,
										1f,
										0f,
										loadingRegions.get(0).getRegionX(), loadingRegions.get(0).getRegionY(),
										(int) (sliderProgressX),
										loadingRegions.get(0).getRegionHeight(),
										false,
										false);
			}
    	}
    }

	public void setSlider(int sliderPrev2, int step2) {
		sliderPrev = sliderPrev2;
		step = step2;
	}	
	    
}
	
