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


public class DigitsJackpot extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private boolean animateShow;
	private boolean animateHide;
	private int currentFrame = 0;
	
	private DigitsSingle digits;
	private DigitsSingle digitsDeact;
	private DigitsSingle digitsActive;
	
	
	private float startX, startY, currentX;
	private int symW, symH;
	private String currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	private boolean deact = false;
	
	Color color;
	

	public DigitsJackpot(BookOfKnight game) {
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
		
		this.decimal = true;
		
		digitsActive = new DigitsSingle(game, "egg_digit", true, game.textures.getInterfaceAtlas());
		digitsActive.commitAssets();

		digitsDeact = new DigitsSingle(game, "egg_digit_deact", true, game.textures.getInterfaceAtlas());
		digitsDeact.commitAssets();
		
		
		backgroundWidth = 205 - 2;
		offsetW = 0;

		symW	=  digitsActive.getW()-3;
		symH 	=  digitsActive.getH();
		
		this.setOrigin(205/2f, 66/2f);

//		startX  = bg.getX();
//		startY  = bg.getY() + (bg.getHeight() - symH)/2;
		
		addActor(digitsActive);
		addActor(digitsDeact);
	}
	
    @Override
    public void draw(Batch batch, float alpha)
	{
    	
    	
    	if(currentWin != "0" && currentWin != null){

    		digits = (deact) ? digitsDeact : digitsActive;
    		
    		float offsetPoint = 10;
    		
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
						digits.setElement(10, currentX + -offsetPoint*3 + i*symW, startY);
					} else {
						afterPoint = true;
						digits.setElement(10, currentX + -offsetPoint + i*symW, startY);
					}
				} else if (currentChar == ',') {
					if(afterPoint){
						afterPointSec = true;
						digits.setElement(11, currentX + -offsetPoint*3 + i*symW, startY);
					} else {
						digits.setElement(11, currentX + -offsetPoint + i*symW, startY);
						afterPoint = true;
					}
				} else {
					if(afterPointSec){
						digits.setElement(charToInt(currentChar), currentX + -offsetPoint*4 + i*symW, startY);
					} else if(afterPoint){
						digits.setElement(charToInt(currentChar), currentX + -offsetPoint*2 + i*symW, startY);
					} else {
						digits.setElement(charToInt(currentChar), currentX + i*symW, startY);
					}
				}
				
				digits.draw(batch, alpha);
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
    
    public void setDeact(boolean deac){
    	deact = deac;
    }

    public void hide(){
    	animateShow = false;
    	animateHide = true;
    	deact = false;
    	currentFrame = 0;
    }
    
	public void setWin(long win, float f , float g) {
		    
			startX = f;
			startY = g;
		
			digitsActive.hideAll();
			digitsDeact.hideAll();
			
			String text = game.meters.formatNumber(win, false);

			currentWin = text;

			lenght = (text.length());

//			if(win >= 100000){
//				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 50;
//			} else {
				currentX = startX + (backgroundWidth -  lenght*symW)/2 /*+ 40*/;
//			}
	}
	
}
