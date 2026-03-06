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


public class JackpotShowWin extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private boolean animateShow;
	private boolean animateHide;
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
	

	public JackpotShowWin(BookOfKnight game) {
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
		
		digits = new DigitsSingle(game, "win_digits", true, game.textures.getInterfaceCommon());
		digits.commitAssets();
		
		
		game.textures.getInterfaceAtlas().findRegion("totalwin").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("totalwin")));
		bg.setPosition(626, 1080 - 880 - 188);

		setWidth(game.textures.getInterfaceAtlas().findRegion("totalwin").getRegionWidth());
		setHeight(game.textures.getInterfaceAtlas().findRegion("totalwin").getRegionHeight());

		backgroundWidth = game.textures.getInterfaceAtlas().findRegion("totalwin").getRegionWidth();
		offsetW = 0;

		symW	=  digits.getW()-5;
		symH 	=  digits.getH();			
		startX  = bg.getX();
		startY  = bg.getY() + (bg.getHeight() - symH)/2;
		
		addActor(digits);
		addActor(bg);
	}
	
//	@Override
//    public void act(float delta){
//		
//		super.act(delta);
//		
//		timeElapsed		+= delta;
//		
//		if (animateShow || animateHide) {
//			if (timeElapsed - lastTimeElapsed > 0.030f) {
//				lastTimeElapsed = timeElapsed;
//				
//				if(++currentFrame > 15){
//					currentFrame = 15;
//				}
//			}
//		}
//	}
	
    @Override
    public void draw(Batch batch, float alpha)
	{
    	
    	float currentAlpha = alpha;
    	
//    	if(animateShow){
//    		currentAlpha = 0f + (1f/15) * currentFrame;
//    	}
//
//    	if(animateHide){
//    		currentAlpha = 0f;//1f - (1f/5) * currentFrame;
//    	}
    	
		color = getColor();
		
		batch.setColor(color.r, color.g, color.b, currentAlpha);
	
    	if(currentWin != "0" && currentWin != null){
    		
    		bg.draw(batch, currentAlpha);
	    	
    		float offsetPoint = 20;
    		
    		boolean afterPoint = false;
    		boolean afterPointSec = false;
    		boolean afterPointThird = false;
			for(int i = 0; i < currentWin.length(); i++)
			{
				char currentChar = currentWin.charAt(i);
				if (currentChar == ' ') {
					afterPoint = true;
				} else if (currentChar == '.') {
					if(afterPointSec){
						afterPointThird = true;
						digits.setElement(10, getX() + currentX + -offsetPoint*5 + i*symW + 10, getY() +  startY);
					} else if(afterPoint){
						afterPointSec = true;
						digits.setElement(10, getX() + currentX + -offsetPoint*3 + i*symW, getY() +  startY);
					} else {
						afterPoint = true;
						digits.setElement(10, getX() + currentX + -offsetPoint + i*symW, getY() +  startY);
					}
				} else if (currentChar == ',') {
					if(afterPoint){
						afterPointSec = true;
						digits.setElement(11, getX() + currentX + -offsetPoint*3 + i*symW, getY() +  startY);
					} else {
						digits.setElement(11, getX() + currentX + -offsetPoint + i*symW, getY() +  startY);
						afterPoint = true;
					}
				} else {
					if(afterPointThird){
						digits.setElement(charToInt(currentChar), getX() + currentX + -offsetPoint*5 + i*symW, getY() +  startY);
					}else if(afterPointSec){
						digits.setElement(charToInt(currentChar), getX() + currentX + -offsetPoint*4 + i*symW, getY() +  startY);
					} else if(afterPoint){
						digits.setElement(charToInt(currentChar), getX() + currentX + -offsetPoint*2 + i*symW, getY() +  startY);
					} else {
						digits.setElement(charToInt(currentChar), getX() + currentX + i*symW, getY() +  startY);
					}
				}
				
				digits.draw(batch, currentAlpha);
			}
 
			batch.setColor(color.r, color.g, color.b, 1f);
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
    
    public void show(){
    	animateShow = true;
    	animateHide = false;
    	currentFrame = 0;
    }

    public void hide(){
    	animateShow = false;
    	animateHide = true;
    	currentFrame = 0;
    }
    
	public void setWin(long win) {
		    digits.hideAll();
			
			String text = game.meters.formatNumber(win, false);

			currentWin = text;

			lenght = (text.length());
			
			
			game.gsLink.console("Jackpot ::: win = " + win);
			
			if(win >= 100000){
				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 40;
			} else if(win >= 1000) {
				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 15;
			} else {
				currentX = startX + (backgroundWidth -  lenght*symW)/2 + 15;
			}
	}
	
}
