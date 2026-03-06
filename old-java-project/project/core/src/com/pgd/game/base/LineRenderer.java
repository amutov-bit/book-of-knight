package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.Controller.State;
import com.pgd.game.BookOfKnight;

public class LineRenderer extends Actor {
	
	private BookOfKnight game;
	public String path;
	public int totalLines;
	public boolean isOnTop;
//	private TextureAtlas atlas;
	protected Array<AtlasRegion> lines;	
	int[] currentLines;
	
	public LineRenderer(BookOfKnight game, int x, int y, String imgs, TextureAtlas atlas) {
		this.game = game;
		this.path = imgs;
		
		this.setX(x);
		this.setY(y);
		
		totalLines = 0;
		isOnTop = false;
		
		lines = atlas.findRegions("line");
		
		currentLines = new int[lines.size];

		for(int i = 0; i < lines.size; i++) {
			currentLines[i] = 0;
		}
		
	}

	public void loadAssets(){
		Gdx.app.debug("LineRenderer", "loadAssets()");
	}
    
	public void commitAssets() {

	}	
	
	public void draw(Batch batch, float alpha) {
		 for(int i = 0; i < lines.size; i++) {
			 
			 if(currentLines[i] > 0){
				 	float offsetX = (lines.get(i).getRotatedPackedWidth() - lines.get(i).getRegionWidth())  / 2;
				 	float offsetY = (lines.get(i).getRotatedPackedHeight() - lines.get(i).getRegionHeight())  / 2;
				 	lines.get(i).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				 	
				 	float offY = 0;
				 	
//				 	if( i == 1) offY -= 30;
//				 	if( i == 2) offY += 30;
				 	
				 	batch.draw(lines.get(i).getTexture(),
		    				getX() + offsetX +  lines.get(i).offsetX,
		        			getY() + offsetY +  lines.get(i).offsetY + offY,
		        			lines.get(i).getRegionWidth()/2,
		        			lines.get(i).getRegionHeight()/2,
		        			lines.get(i).getRegionWidth(),
		        			lines.get(i).getRegionHeight(),
		        			1f,
		        			1f,
		        			(lines.get(i).rotate) ? -90f : 0f,
		        			lines.get(i).getRegionX(), 
		        			lines.get(i).getRegionY(), 
		        			lines.get(i).getRegionWidth(), 
		        			lines.get(i).getRegionHeight(),
		        			false,
		        			false);
			 }
			 
		 }
	}
	
	public void addLine(int line) {
		currentLines[line] = 1;
	}

//	public void addBoostLine(int line) {
//		currentLinesBoost[line] = 1;
//	}
	
	public void clear() {
		for(int i = 0; i < lines.size; i++) {
			currentLines[i] = 0;
		}
	}
}
