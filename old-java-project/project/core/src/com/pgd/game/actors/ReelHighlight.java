package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pgd.game.Sounds;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.ReelTextures;

public class ReelHighlight extends Actor {

	private ReelTextures textures;
	private int frame;
	private boolean looping;
	private boolean animate;
	private boolean isStopped;
	private boolean hideWhenIsFinished;
	private float iterations;
	private BookOfKnight game;
	
	public ReelHighlight(ReelTextures textures, BookOfKnight game) {
		this.textures = textures;
		looping = false;
		animate = false;
		setVisible(false);
		frame = 0;
		iterations = 0;
		this.game = game;
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		// @Note: ReelHighlight will not control animation timings.
		// Parent object must ensure act is invoked at proper FPS.
		if (animate) {
			frame++;
			if(frame > 24) {
				frame = 0;
				
				game.gsLink.console("iterations = " + iterations);
				
				isStopped = ++iterations <= 2;
				
				if(iterations == 1){
					game.reels.setReelStep(63);
				}
				
				if(iterations == 2){
					game.reels.setReelStep(43);
				}
				
				if(iterations == 3){
					game.reels.setReelStep(23);
				}
				
//				if(hideWhenIsFinished){
//					isStopped = false;
//					this.iterations = 0;
//					animate = false;
//					looping = false;
//					frame = 0;
//					setVisible(false);	
//				}
			}
		}
			
	}

	@Override
	public void draw(Batch batch, float alpha) {
		if(animate) {
		 	float offsetX = (textures.getRotatedPackedWidth(frame) - textures.getReelHighlightRegionWidth(frame))  / 2+20;
		 	float offsetY = (textures.getRotatedPackedHeight(frame) - textures.getReelHighlightRegionHeight(frame))  / 2;

		 	batch.draw(textures.getReelHighlightTexture(frame),
    				getX() + offsetX +  textures.getReelHighlightOffsetX(frame),
        			getY() + offsetY +  textures.getReelHighlightOffsetY(frame),
        			textures.getReelHighlightRegionWidth(frame)/2,
        			textures.getReelHighlightRegionHeight(frame)/2,
        			textures.getReelHighlightRegionWidth(frame),
        			textures.getReelHighlightRegionHeight(frame),
        			1f,
        			1f,
        			(textures.getReelHighlightRotate(frame)) ? -90f : 0f,
        			textures.getReelHighlightRegionX(frame), 
        			textures.getReelHighlightRegionY(frame), 
        			textures.getReelHighlightRegionWidth(frame), 
        			textures.getReelHighlightRegionHeight(frame),
        			false,
        			false);
		}
		
	}
	
	/**
	 * Show highlights 
	 * @param int - the number of iterations to loop for, 0 will trigger a short blink
	 */
	public void show() {

		if(!textures.getAnimRegionLoadedH()) return;	
		
		looping   = true;
		animate   = true;
		isStopped = true;
		
		hideWhenIsFinished = false;
		
		frame      = 0;	
		iterations = 0;	
		
		game.sounds.play(Sounds.SoundTrack.ANTICIPATION, false);
	}
	
	public boolean isStopped(){
		return !isStopped;
	}
	/**
	 * Hide reel highlights 
	 * @param int - iterations to loop while stopping, immediate stop if -1
	 */
	public void hide(int iterations) {
//		if(iterations == -1) { // immediate stop
		if(!textures.getAnimRegionLoadedH()) return;	
		
//		hideWhenIsFinished = true;
		
		isStopped = false;
		this.iterations = 0;
		animate = false;
		looping = false;
		frame = 0;
		setVisible(false);	
		
		game.sounds.stop(Sounds.SoundTrack.ANTICIPATION);
	}
}
