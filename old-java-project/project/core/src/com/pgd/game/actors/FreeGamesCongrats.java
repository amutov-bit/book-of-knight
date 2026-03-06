package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

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


public class FreeGamesCongrats extends Group {
	
	BookOfKnight game;

	
	private String file;
	private float startX, startY, currentX;
	private float numberDigits;
	private int symW, symH;
	private String currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	public DigitsSingle digits;
	
	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	
	private float timeElapsedSpine;
	private float lastTimeElapsedSpine;

	private Image freeGames, bg;
	private Digits digitsFrame;
	
	private int currentFrame;
	private int stopAnimaionFrame;
	private boolean animate;
	private boolean hideBigWin;
	private boolean stopAnimation;
	
	private Color color;
	
	public FreeGamesCongrats(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {

		digits = new DigitsSingle(game, "win_digits", true, game.textures.getInterfaceCommon());
		digits.commitAssets();
		
		file = "win_digits";
		numberDigits = 12;
		startX = 470 - 150 -20;
		startY = 772 - 326 - 124 - 60 - 85 - 0 + 300 - 80 + 80 - 10;
		symW	=  digits.getW()+5;
		symH 	=  digits.getH();					
		backgroundWidth = 1300;
		
	}
	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		 += delta;
		timeElapsedSpine += delta;
		
		if (animate) {
			game.spineFreeGamesCongrats.act(delta, 0);
		}
		
		if (animate) {
			if (timeElapsed - lastTimeElapsed > 0.030f) {
				lastTimeElapsed = timeElapsed;
				currentFrame++;
				
				if(game.spineFreeGamesCongrats.getEnd() || currentFrame > 122)
				{
					currentFrame = 251;
					
					stopAnimation = true;
					
				}
				
				if(currentFrame >= 44){
					game.menu.showJackpotWin(false);
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
				
			if(game.spineFreeGamesCongrats.isLoaded()){
				game.spineFreeGamesCongrats.setSpineAnimRegionX(0, 655 + 300 + 10);
				game.spineFreeGamesCongrats.setSpineAnimRegionY(0, -200 - 80 + 300 - 10);	
				game.spineFreeGamesCongrats.draw(batch, alpha);
			}
			
			batch.setColor(color.r, color.g, color.b, 1f);
			
			if(currentFrame >= 44){
						
		    	if(currentWin != "0"){
		    		
		    		float offsetPoint = 20;
		    		
		    		boolean afterPoint = false;
		    		boolean afterPointSec = false;
					for(int i = 0; i < currentWin.length(); i++)
					{
						char currentChar = currentWin.charAt(i);
						if (currentChar == ' ') {
							afterPoint = true;
						} else if (currentChar == '.') {
							if(afterPoint){
								afterPointSec = true;
								digits.setElement(10, getX() + currentX + -offsetPoint*3 + i*symW, startY);
							} else {
								afterPoint = true;
								digits.setElement(10, getX() + currentX + -offsetPoint + i*symW, startY);
							}
						} else if (currentChar == ',') {
							if(afterPoint){
								afterPointSec = true;
								digits.setElement(11, getX() + currentX + -offsetPoint*3 + i*symW, startY);
							} else {
								digits.setElement(11, getX() + currentX + -offsetPoint + i*symW, startY);
								afterPoint = true;
							}
						} else {
							if(afterPointSec){
								digits.setElement(charToInt(currentChar), getX() + currentX + -offsetPoint*4 + i*symW, startY);
							} else if(afterPoint){
								digits.setElement(charToInt(currentChar), getX() + currentX + -offsetPoint*2 + i*symW, startY);
							} else {
								digits.setElement(charToInt(currentChar), getX() +currentX + i*symW, startY);
							}
						}
						
						digits.draw(batch, alpha);
					}
		 
					batch.setColor(color.r, color.g, color.b, 1f);
		    	}
			}
		}
    	    	
    }
    
    private static int charToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0'; // This assumes that the character represents a digit
        } else {
            // Handle the case where the character is not a digit
            throw new IllegalArgumentException("Character is not a digit: " + c);
        }
    }

    
	public void setVisible(boolean visible) {
		animate = visible;
		
		stopAnimation = false;
		
		currentFrame = 0;
		
		hideBigWin = false;
		
		stopAnimaionFrame = 0;
	
		if(!visible){
			game.spineFreeGamesCongrats.clearAnimationState();
		}
		
		if(visible)	game.sounds.play(SoundTrack.CONGRATS, false);
		else		game.sounds.stop(SoundTrack.CONGRATS);
		
		game.spineFreeGamesCongrats.setVisible(visible);
	}
	
	public void setWin(long win)
	{
		digits.hideAll();
		
			String text = game.meters.formatNumber(win * game.meters.getDenomination(), false);
			currentWin = text;
			lenght = (text.length());
			
			if(win * game.meters.getDenomination() >= 100000){
				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 70;
			} else {
				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 50;
			}
	}
	
	public void setFormat(boolean decimal)
	{
		this.decimal = decimal;
	}
	
	public boolean animationStopped(){
		return stopAnimation;
	}
	
	public boolean stopAnimation(){
		return stopAnimation;
	}
	
	public void hideBigWin(){
		stopAnimaionFrame = 0;
		hideBigWin = true;
		stopAnimation = false;
		digits.hideAll();
	}
}
