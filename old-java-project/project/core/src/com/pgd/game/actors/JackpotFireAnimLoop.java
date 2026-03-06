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


public class JackpotFireAnimLoop extends Group {
	
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
	private String currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	Color color;
	
	private float rotate = 0;
	

	public JackpotFireAnimLoop(BookOfKnight game) {
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
			game.spineFireAnimLoop.act(delta, 0);
			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				
//				Gdx.app.debug("JackpotFireAnimLoop", "currentFrame = " + currentFrame);
				if(++currentFrame > 15){
					currentFrame = 15;
				}
			}
		}
	}
	
    @Override
    public void draw(Batch batch, float alpha)
	{
    	
		color = getColor();
		
		if (animate) {
			if(game.spineFireAnimLoop.isLoaded()){
				game.spineFireAnimLoop.setSpineAnimRegionX(0, this.getX());
				game.spineFireAnimLoop.setSpineAnimRegionY(0, this.getY());	
				
				game.spineFireAnimLoop.setSpineAnimRegionSetRotation(0, rotate);
				
				game.spineFireAnimLoop.draw(batch, alpha);
			}
		}
		
		batch.setColor(color.r, color.g, color.b, 1f);
	
	}
	
    public void animate(){
//    	Gdx.app.debug("JackpotFireAnimLoop", "animate");
    	animate = true;
    	currentFrame = 0;
    	
    	game.sounds.play(SoundTrack.FIRE_LOOP, true, 0.2f);
    }


    public void hide(){
    	if(game.spineFireAnimLoop.isLoaded()){
    		game.spineFireAnimLoop.clearAnimationState();
    	}
    	animate = false;
    	currentFrame = 0;
    	
    	game.sounds.stop(SoundTrack.FIRE_LOOP);
    }

	public void setRotationAnt(float f) {
		rotate = f;
	}
    
	
}
