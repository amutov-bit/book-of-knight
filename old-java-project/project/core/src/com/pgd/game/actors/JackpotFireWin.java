package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class JackpotFireWin extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private boolean animate;
	private int currentFrame = 0;
	
	private Image bg;
	private DigitsSingle digits;
	
	
	private float startX, startY, currentX;
	private int symW, symH;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	Color color;
	
	private long currentWin = 0;

	public JackpotFireWin(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
		loadAssets();		
//		animate = false;
	}
	
	public void loadAssets() {
	}
	
	/**
	 * @param name
	 * @param digits
	 */
	public void commitAssets() {
		
	}
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		

		
		if (animate) {
			game.spineFireAnimWin.act(delta, 0);
			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				
				Gdx.app.debug("JackpotFireAnimWin", "currentFrame = " + currentFrame);
				
				if(currentFrame == 14){
					game.menu.setJackpotWin(currentWin);
				}
				
				if(++currentFrame > 22 || game.spineBookAnimation.getEnd()){
					game.overlay.jackpotAnimateWin.animate();
					animate = false;
					game.menu.setJackpotWin(currentWin);
				}
			}
		}
	}
	
    @Override
    public void draw(Batch batch, float alpha)
	{
    	
		color = getColor();
		
		//626 + 360, 1080 - 880 - 188 + 30
		if (animate) {
			if(game.spineFireAnimWin.isLoaded()){
				game.spineFireAnimWin.setSpineAnimRegionX(0, this.getX());
				game.spineFireAnimWin.setSpineAnimRegionY(0, this.getY());	
				game.spineFireAnimWin.draw(batch, alpha);
			}
		}
		
		batch.setColor(color.r, color.g, color.b, 1f);
	
	}
	
    public void animate(long win){
    	Gdx.app.debug("JackpotFireAnimLoop", "animate");
    	
    	currentWin = win;
    	
    	if(game.spineFireAnimLoop.isLoaded()){
    		game.spineFireAnimWin.clearAnimationState();
    	}
    	
    	game.sounds.play(SoundTrack.FIRE_EXPLOSION, false, 0.2f);
    	
    	animate = true;
    	currentFrame = 0;
    }


    public void hide(){
    	if(game.spineFireAnimLoop.isLoaded()){
    		game.spineFireAnimWin.clearAnimationState();
    	}
    	animate = false;
    	currentFrame = 0;
    }
    
	
}
