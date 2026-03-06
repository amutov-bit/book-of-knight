package com.pgd.game.base;

//import java.util.ArrayList;
//import java.util.List;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;


public class RegionsMenu extends Group {

	BookOfKnight game;
	/**
	 * Interface atlas
	 */
	private Array<AtlasRegion> regions = new Array<AtlasRegion>();
	String imagePath;
	int coordintes[] = new int[2];
	
	public RegionsMenu(BookOfKnight game, String imagePath){
		this.game = game;
		this.imagePath = imagePath;
		// load 
	}
	
	public void loadAssets() {
	
	}
	
	public void commitAssets() {
		
		regions = game.textures.interfaceAtlas.findRegions(imagePath);

	}
	

	/**
	 * Dispose
	 */
	public void dispose() {
	}
	
//	public void setPositions(int x, int y)
//	{
//		coordintes[0] = x; coordintes[1] = y;
//	}
	
	public void draw(Batch batch, float alpha) {
			batch.draw(regions.get(0).getTexture(), 
					getX(), getY(), 
					regions.get(0).getRegionX(),regions.get(0).getRegionY(),
					regions.get(0).getRegionWidth(), regions.get(0).getRegionHeight());
			
	}
}
