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


public class FreeGamesText extends Group {
	
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
	
	private int currentFrame;

	private Image freeGamesNum;

	private boolean animateFirst;
	private boolean animate;
	private boolean stopAnimation;
	
	private Color color;
	
	public DigitsSingle digits;

	public FreeGamesText(BookOfKnight game) {
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
		
		game.textures.getPayLiensAtlas().findRegion("12").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freeGamesNum = new Image(new Sprite(game.textures.getPayLiensAtlas().findRegion("12")));
		freeGamesNum.setPosition(793 / 1, (1080 - 284 - 90)/1);
		
	}
	

	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		
			
		
			
			if (animate) {
				
				if (animateFirst) {
					game.spineFreeGamesTxt.act(delta, 0);
				}
				
				if(stopAnimation){
					game.spineFreeGamesTxt.act(delta, 1);
				}
				
//				if (timeElapsed - lastTimeElapsed > 0.030f) {
//					lastTimeElapsed = timeElapsed;
//					currentFrame++;
//				}
				
				if(game.spineFreeGamesTxt.getEndFirst()  && stopAnimation == false)
				{
					currentFrame = 251;
					animateFirst = false;
					stopAnimation = true;
				}
				
			}
			

	}
	
    @Override
    public void draw(Batch batch, float alpha){
	    
    	if(animate)
    	{
			
    		color = getColor();	
    		
			if(game.spineFreeGamesTxt.isLoaded()){
				game.spineFreeGamesTxt.setSpineAnimRegionX(0, 655 + 350 -60);
				game.spineFreeGamesTxt.setSpineAnimRegionY(0, -200 - 80 + 150 + 0+150);	
				game.spineFreeGamesTxt.draw(batch, alpha);
			}
			
			{ 
				
//				if(currentFrame >= 31){
//
//					float a = (currentFrame <= 50) ? currentFrame % 31 * 0.05f : 1f;
//					
//					Gdx.app.debug("FreeGAmes txt", "currentFrame = " + currentFrame + " ::: a = " + a);
//					batch.setColor(color.r, color.g, color.b, a);
//					
//					freeGamesNum.draw(batch, alpha);
//					
//					batch.setColor(color.r, color.g, color.b, 1f);
//				}
			
//				if(currentFrame >= 80){
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
//		}
		game.spineFreeGamesTxt.clearAnimationState(0);
		game.spineFreeGamesTxt.clearAnimationState(1);


		game.spineFreeGamesTxt.setVisible(visible);
		
		if(visible)	game.sounds.play(SoundTrack.FREE_GAMES, false);
		else		game.sounds.stop(SoundTrack.FREE_GAMES);
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
