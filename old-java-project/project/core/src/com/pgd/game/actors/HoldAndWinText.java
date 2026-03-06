package com.pgd.game.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class HoldAndWinText extends Group {
	
	BookOfKnight game;

	
	
	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	private String file;
	private float startX, startY, currentX;
	private float numberDigits;
	private int symW, symH;
	private long currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
//	private boolean showPlus;
	private int offsetW, offsetH;
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private Image freeGames, bg;
	private Digits digitsFrame;
	
	private int currentFrame;

	
	private boolean animate;
	private boolean animateFirst;
	private boolean stopAnimation;
	
	public DigitsSingle digits;

	public HoldAndWinText(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
		loadAssets();		
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets(){

		file = "bigwin_digits";
		numberDigits = 12;
		startX = 440 + 10 - 30 + 300 - 11;
		startY = 772 - 326 - 124 - 60 + 40 + 290 + 20 + 220;
		symW = 150;
		symH =  190;			
		backgroundWidth = 300;
		
		offsetW = +30;
		
		digits = new DigitsSingle(game,"bigwin_digits", false, game.textures.getInterfaceCommon());
		digits.commitAssets();
		
	}
	

	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		
			
		
			
			if (animate) {
				
				if (animateFirst) {
					game.spineHoldAndWinTxt.act(delta, 0);
				}
				
				if(stopAnimation){
					game.spineHoldAndWinTxt.act(delta, 1);
				}
				
				if(game.spineHoldAndWinTxt.getEndFirst() && stopAnimation == false)
				{
					currentFrame = 0;
					animateFirst = false;
					stopAnimation = true;
				}

			}
			

	}
	
    @Override
    public void draw(Batch batch, float alpha){
	    
    	if(animate)
    	{
			
    		
			if(game.spineHoldAndWinTxt.isLoaded()){
				game.spineHoldAndWinTxt.setSpineAnimRegionX(0, 655 + 350 - 11 - 29 + 20);
				game.spineHoldAndWinTxt.setSpineAnimRegionY(0, -400);	
				game.spineHoldAndWinTxt.draw(batch, alpha);
			}
			
			{ 
			
//				if(currentFrame >= 80 && false){
//					
//					long win = currentWin;
//					for(int i = 0; i < lenght; i++)
//					{
//						if(this.decimal)
//						{
//							if(i == lenght - beforeDecimal)
//							{
//								digits.setElement(10, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
//								digits.draw(batch, alpha);
//							}
//							else
//							{
//								long num = win % 10;
//								digits.setElement((int)num, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
//								digits.draw(batch, alpha);
//								
//								win /= 10;
//							}
//						}
//						else{
//							long num = win % 10;
//							
//							digits.setElement((int)num, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
//							digits.draw(batch, alpha);
//							
//							win /= 10;
//						}
//					}
//				}
			}
			
		}
    	    	
    }

	public void setVisible(boolean visible) {
		animateFirst = visible;
		animate = visible;
		stopAnimation = !visible;
		currentFrame = 0;
//		if(!visible){
//			game.spineHoldAndWinTxt.clearAnimationState(1);
//		}
		
		game.spineHoldAndWinTxt.clearAnimationState(0);
		game.spineHoldAndWinTxt.clearAnimationState(1);


		game.spineHoldAndWinTxt.setVisible(visible);
		
		if(visible)	game.sounds.play(SoundTrack.HOLD_AND_WIN, false);
		else		game.sounds.stop(SoundTrack.HOLD_AND_WIN);
	}
	
	public void setWin(int win) {
		digits.hideAll();
		
//		showPlus = (win == 11) ? true : false;
		
		if(this.decimal)
		{
			currentWin = win * game.meters.getDenomination();
			String text = (win * game.meters.getDenomination()/100) + "." + (win * game.meters.getDenomination()/ 10 % 10) + (win * game.meters.getDenomination() % 10);

			beforeDecimal = Long.toString(currentWin / 100).length() + 1;
		
			lenght = (text.length());
			currentX = startX - 60 + lenght*(symW-offsetW) + (backgroundWidth -  lenght*(symW - offsetW))/2;
		}
		else
		{
			currentWin = win;
			lenght = (Integer.toString((int)win).length());
			currentX = startX - 60 + lenght*(symW-offsetW) + (backgroundWidth -  lenght*(symW - offsetW))/2;
			
		}
	}
	
	public void setStopAnim(boolean stop){
		stopAnimation = stop;
	}
	
	public boolean animationStopped(){
		return stopAnimation;
	}
}
