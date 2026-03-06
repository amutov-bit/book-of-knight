package com.pgd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.actors.BGAnimation;

public class Background extends Group {

	private BookOfKnight game;
	private Image normal, freegames,holdandwin;
	
	
//	private Image bgTop;
	
	private float actorX = 0, actorY = 0;
	public boolean visible = false;
	
	private boolean changeO = false;
	AlphaAction alphaFadeIn = new AlphaAction();
	AlphaAction alphaFadeOut = new AlphaAction();

	BGAnimation bgAnim;
	
//	Image interfaceMenu;
	
    public Background(BookOfKnight game) {
		super();
		this.game = game;
		
	}

	public void loadAssets(){
		game.ondemandAssetManager.load("bg.jpg", Texture.class);
		game.ondemandAssetManager.load("bg_fg.jpg", Texture.class);
		game.ondemandAssetManager.load("bg_haw.jpg", Texture.class);
		game.ondemandAssetManager.load("podlojka.png", Texture.class);
	}
    
	public void commitAssets() {
		
		Texture texture = game.manager.get("bg.jpg", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		normal = new Image(texture);	

		texture = game.manager.get("bg_fg.jpg", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freegames = new Image(texture);

		texture = game.manager.get("bg_haw.jpg", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		holdandwin = new Image(texture);
		
//		texture = game.manager.get("red_top.png", Texture.class);
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		bgTop = new Image(texture);	


		addActor(normal);
		addActor(freegames);
		addActor(holdandwin);
		
//		freegames.setPosition(0, 0);
//		bonus.getRotation();
		normal.setVisible(true);
		freegames.setVisible(false);
		holdandwin.setVisible(false);
		
		bgAnim = new BGAnimation(game);
		bgAnim.setVisible(true);
		addActor(bgAnim);
		
	}	
	
//	@Override
//    public void act(float delta){
//
//    }
    
//    @Override
//    public void draw(Batch batch, float alpha){
//    	if(visible) {
//    		batch.draw(texture, actorX, actorY);
//    	}
//    }
    
	public void dispose() {
//		normal.dispose();
//		freegames.dispose();
	}
	
	public void showInterface(boolean vis){
//		interfaceMenuFG.setVisible(vis);
//		interfaceMenu.setVisible(vis);
	}
	
	public void setFreeGamesAnim(boolean fg) {
		if(fg)
		{
			freegames.setVisible(true);
			normal.setVisible(true);
			alphaFadeIn.reset();
			freegames.getColor().a = 0f;
			alphaFadeIn.setAlpha(1f);
			alphaFadeIn.setDuration(0.6f); 
			freegames.setVisible(true);
			freegames.addAction(alphaFadeIn);
			
		} else {
			freegames.setVisible(true);
			normal.setVisible(true);

			alphaFadeOut.reset();
			freegames.getColor().a = 1f;
			alphaFadeOut.setAlpha(0f);
			alphaFadeOut.setDuration(0.6f);
			freegames.setVisible(true);
			freegames.addAction(alphaFadeOut);
			
		}
	
//		normal.setVisible(true);
	}
	
	public void setHoldAndWinAnim(boolean fg) {
		if(fg)
		{
			holdandwin.setVisible(true);
			normal.setVisible(true);
			alphaFadeIn.reset();
			holdandwin.getColor().a = 0f;
			alphaFadeIn.setAlpha(1f);
			alphaFadeIn.setDuration(0.6f); 
			holdandwin.setVisible(true);
			holdandwin.addAction(alphaFadeIn);
			
		} else {
			holdandwin.setVisible(true);
			normal.setVisible(true);
			
			alphaFadeOut.reset();
			holdandwin.getColor().a = 1f;
			alphaFadeOut.setAlpha(0f);
			alphaFadeOut.setDuration(0.6f);
			holdandwin.setVisible(true);
			holdandwin.addAction(alphaFadeOut);
			
		}
		
//		normal.setVisible(true);
	}
		
	public boolean showOrientationChange()
	{
		return changeO;
	}

	public void setOrientationChange(boolean change)
	{
		changeO = change;
	}
	
    
}
