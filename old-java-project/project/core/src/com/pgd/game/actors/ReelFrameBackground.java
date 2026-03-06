package com.pgd.game.actors;

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
import com.pgd.game.BookOfKnight;
import com.pgd.game.actors.BGAnimation;

public class ReelFrameBackground extends Group {

	private BookOfKnight game;
	private Image normal, freegames,holdAndWin;
	
	
//	private Image bgTop;
	
	private float actorX = 0, actorY = 0;
	public boolean visible = false;
	
	private boolean changeO = false;
	AlphaAction alphaFadeIn = new AlphaAction();
	AlphaAction alphaFadeOut = new AlphaAction();

	BGAnimation bgAnim;
	
//	Image interfaceMenu;
	
    public ReelFrameBackground(BookOfKnight game) {
		super();
		this.game = game;
		
	}

	public void loadAssets(){
		
	}
    
	public void commitAssets() {
		
		Texture texture = game.manager.get("reels_bg.png", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		normal = new Image(texture);	
		
		Texture texture1 = game.manager.get("reels_bg_fg.png", Texture.class);
		texture1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freegames = new Image(texture1);
		
		texture = game.manager.get("reels_bg_haw.png", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		holdAndWin = new Image(texture);




		addActor(normal);
		addActor(freegames);
		addActor(holdAndWin);
//		addActor(bgTop);
		
//		freegames.setPosition(0, 0);
//		bonus.getRotation();
		normal.setVisible(true);
		freegames.setVisible(false);
		holdAndWin.setVisible(false);

		
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
			alphaFadeIn.setDuration(0.4f); 
			freegames.setVisible(true);
			freegames.addAction(alphaFadeIn);
			
		} else {
			freegames.setVisible(true);
			normal.setVisible(true);

			alphaFadeOut.reset();
			freegames.getColor().a = 1f;
			alphaFadeOut.setAlpha(0f);
			alphaFadeOut.setDuration(0.4f);
			freegames.setVisible(true);
			freegames.addAction(alphaFadeOut);
			
		}
	
//		normal.setVisible(true);
	}
	
	public void setHoldAndWinAnim(boolean fg) {
		if(fg)
		{
			holdAndWin.setVisible(true);
			normal.setVisible(true);
			alphaFadeIn.reset();
			holdAndWin.getColor().a = 0f;
			alphaFadeIn.setAlpha(1f);
			alphaFadeIn.setDuration(0.6f); 
			holdAndWin.setVisible(true);
			holdAndWin.addAction(alphaFadeIn);
			
		} else {
			holdAndWin.setVisible(true);
			normal.setVisible(true);
			
			alphaFadeOut.reset();
			holdAndWin.getColor().a = 1f;
			alphaFadeOut.setAlpha(0f);
			alphaFadeOut.setDuration(0.6f);
			holdAndWin.setVisible(true);
			holdAndWin.addAction(alphaFadeOut);
			
//			normal.setVisible(true);
		}
		
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
