package com.pgd.game.base;

import javax.swing.GroupLayout.SequentialGroup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Math;
import com.pgd.game.Controller.State;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Digits;
import com.pgd.game.actors.DigitsJackpot;

public class ReelSymbol extends Actor {
	
	/**
	 * Reference to owner
	 */
	private Reel reel;

//	private JackpotDigits eggDigits;
	
	private int indexNewEgg;
	
	int index;
	private int blurIndex = 0;	
	private int flipSymbolIndex = 10; // default flip symbol is wild 
	private int lastY = 0;
	
	public int drawOffsetY = 0, textureOffsetY = 0, drawPositionX = 0;
	public boolean visible = true;
	public ReelTextures textures;
//	public SpineTextures spineTextures;''
	private boolean highlight = false;
	public boolean animate = false;
	public boolean animateSpine = false;
	public boolean wildMultAnim = false;
	
	public boolean wildLine = false;

	
	public boolean disble = false;
	
	public boolean animateNearMiss = false;
	public boolean animateSymbol = false;
	public boolean showDigits = false;
//	public boolean changeLine = false;
	public boolean looping = false;
//	private boolean isLong = false;
	public boolean ontop = false;
	public boolean stacked = false;
	public boolean isWinning = false;
	public boolean hasWildWin = false;
	private int highlightFrameCnt = 0;
	public int animationFrameCnt = 0;
	public int waitngFrameCnt = 0;
	public int nearMissFrame	= 0;
	public int bonusFrameCnt = 0;
	public int currentFrame = 0;
	private float delayAnimation = 0;
	private int shortAnimationTotalFrames = 0;
	private int longAnimationTotalFrames = 0;
	private int highlightTotalFrames = 6;
	private int totalFrames = 0;
	private boolean canFlip = false;
	private float dimmingAlpha = 0.0f;
	private float dimmingAlphaStep = 0.0f;
	
	private int multiplier = 0;
	
	private int currentBonusFrame = 0;
	private boolean dimmingAnim = false;
	private boolean startAnim = false;
	private Color color;
	private BookOfKnight game;
	
	boolean transformAnim = false; 
	public int transformFrame = 0;

	public  int	    showBonusCurrentFrame = 0;
	boolean showBonusSymbols 		 = false; 

//	boolean boost 		 = false; 
	
	public boolean animateEggTransform  = false;
	public boolean animateGrandJackpot  = false;
	public int currentFrameGrandJackpot = 0;
	
	public boolean animateHoldAndWin = false;
	
	public boolean animateHide = false;
	public boolean animateShow = false;
	public int animateHideFrame = 0;
	public int animateShowFrame = 0;

	int specialSymbol = -1;
	
	public boolean animateMultiplier = false;
	public int animateMultiplierFrame = 0;

	public int wildCount = 0;
	
	public boolean animateOnStopScatter;
	public int animateOnStopFrameScatter = 0;
	public int animateOnStopCntFrameScatter = 0;
	public int animateOnStopLenghtScatter = 0;

	private int currentReel = 0;
	private int currentSymbol = 0;
	
	private float opacityEggSymbolOld[] = {
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)100,
			(float)89,
			(float)78,
			(float)67,
			(float)33 ,
			(float)0,
			(float)0,
			
	};

	private float opacityEggSymbolNew[] = {
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			(float)0,
			
			(float)11,
			(float)22,
			(float)33,
			(float)89,
			(float)100,
			(float)100,
			
	};
	
	private float scaleEggSymbolX[] = {
			(float)100	 ,
			(float)105  ,
			(float)110  ,
			(float)112	 ,
			(float)97	 ,
			(float)79	 ,
			(float)65	 ,
			(float)83   ,
			(float)105	 ,
			(float)105  ,
			(float)104	 ,
			(float)102	 ,
			(float)100	 ,
			(float)99.8	,
			(float)99.7	,
			(float)99.8	,
			(float)99.8	,
			(float)99.9	,
			(float)100	 ,
			(float)100	 ,
			(float)100	 ,
			(float)100	 ,
			(float)100	 ,
			(float)100	 ,
			(float)100	 ,
			
	};

	private float scaleEggSymbolY[] = {
			(float)	100		,
			(float) 	80	,
			(float) 	60 	,
			(float)	47		,
			(float)66	    ,
			(float)91	    ,
			(float)111	    ,
			(float)     86  ,
			(float)	59      ,
			(float)     78  ,
			(float)	100     ,
			(float)	101	    ,
			(float)	100     ,
			(float)	100		,
			(float)	100		,
			(float)	100		,
			(float)	100		,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			(float)	100	    ,
			
	};

	private float rotateEggSymbol[] = {
			(float)-4	,
			(float)-4	,
			(float)-4	,
			(float)-4	,
			(float)-4	,
			(float)3.7  ,
			(float)3.5  ,
			(float)7	,
			(float)-6.7 ,
			(float)	-31	,
			(float)	-44	,
			(float)	-16 ,
			(float)	13	,
			(float)	-2	,
			(float)	-20	,
			(float)	-7.6,
			(float)	5	,
			(float)	-4.6,
			(float)	-16	,
			(float)	-8	,
			(float)	-4	,
			(float)	0	,
			(float)	0	,
			(float)	0	,
			(float)	0	,
			
	};
	
	private float scaleNormalSymbol[] = {
			(float) 100 ,
			(float) 94.4,
			(float) 88.8,
			(float) 83.2,
			(float) 77.6,
			(float) 72  ,
			(float) 82.8,
			(float) 93.5,
			(float) 104 ,
			(float) 115 ,
			(float) 110 ,
			(float) 105 ,
			(float) 100 ,
			(float) 95  ,
			(float) 90  ,
			(float) 91.4,
			(float) 92.9,
			(float) 94  ,
			(float) 95.7,
			(float) 97  ,
			(float) 98.6,
			(float) 100	,	
			(float) 100	,	
			(float) 100	,	
			(float) 100	,	

	};

	private float scaleHighSymbol[] = {
			(float)100  ,
			(float)94.4 ,
			(float)88.8 ,
			(float)83.2 ,
			(float)77.6 ,
			(float)72   ,
			(float)82.8 ,
			(float)93.5 ,
			(float)104.2,
			(float)115  ,
			(float)110.8,
			(float)106.6,
			(float)102.4,
			(float)98.2 ,
			(float)94	,
			(float)96.8 ,
			(float)99.5 ,
			(float)102.2,
			(float)105  ,
			(float)103.8,
			(float)102.5,
			(float)101.2,
			(float)100  ,
			(float)100  ,
			(float)100  ,
			
	};
	private float rotateHighSymbol[] = {
			(float)0	,
			(float)-2.3 ,
			(float)-4.5	,
			(float)-6.8 ,
			(float)-9	,
			(float)-6.5	,
			(float)-4	,
			(float)-1.5	,
			(float)1	,
			(float)3.5	,
			(float)6	,
			(float)5.1  ,
			(float)4.3  ,
			(float)3.4  ,
			(float)2.6  ,
			(float)1.7  ,
			(float)0.8  ,
			(float)0	,
			(float)0	,
			(float)0	,
			(float)0	,
			(float)0	,
			(float)0	,
			(float)0	,
			(float)0	,
			
	};
//	private final float[] nearMissScaleCurve = new float[] {
//    1.0f, 1.1f, 1.2f, 1.1f, 1.05f, 1.0f
//};

private final float[] nearMissScaleCurve = new float[] {
    1.0f, 1.08f, 1.15f, 1.08f, 1.03f, 1.0f
};

//private final float[] nearMissScaleCurve = new float[] {
//    1.0f, 1.12f, 1.05f, 1.0f
//};

//private final float[] nearMissScaleCurve = new float[] {
//    1.0f, 1.1f, 1.15f, 1.08f, 1.0f
//};


	
	private boolean showMagician;
	
	public boolean stopAnim;
	
	public SpineTexturesSymbols spineTextures;
	
	/**
	 * Creates a reel symbol from texture (atlas)
	 * 
	 * @param texture ReelTextures (from loaded png file)
	 * @param reel Reel - reference to reel 
	 * @param index int - symbol index on the reel strip
	 */
	
//	public ReelSymbol(ReelTextures textures, SpineTexturesSymbols spineTextures, Reel reel, int index, BookOfDragon game, SkeletonJson json, SkeletonData playerSkeletonData, AnimationStateData playerAnimationData) {
//		super();
//		
//		this.reel = reel;
//		this.visible = true;
//		this.textures = textures;
//		
//		this.spineTextures = spineTextures;
//		this.spineTextures.commitAssetsSpine(json, playerSkeletonData, playerAnimationData);
//		
//		this.animate = false;
//		this.highlight = false;
//		
//		this.game = game;
//		//setOrigin(reel.symbolWidth / 2, /*reel.symbolHeight / 2*/ textures.getSymbolTextureRegionHeight(index, blurIndex) / 2 );
//		setOrigin(reel.symbolWidth / 2, getRegionHeight() / 2 );
//
//		this.showMagician = false;
//		this.stopAnim = false;
//		
//		setIndex(index);
//	}

	public ReelSymbol(ReelTextures textures, Reel reel, int index, BookOfKnight game) {
		super();
		this.reel = reel;
		this.visible = true;
		this.textures = textures;
//		this.spineTextures = spineTextures;
		
		this.animate = false;
		this.highlight = false;
		
		this.game = game;
		//setOrigin(reel.symbolWidth / 2, /*reel.symbolHeight / 2*/ textures.getSymbolTextureRegionHeight(index, blurIndex) / 2 );
		setOrigin(reel.symbolWidth / 2, getRegionHeight() / 2 );

		this.showMagician = false;
		this.stopAnim = false;
		
		setIndex(index);
	}

	public void trimBottom(int f) {
		setHeight(f);
		drawOffsetY = textures.getSymbolTextureRegionHeight(index, blurIndex) - f + (reel.pitch - textures.getSymbolTextureRegionHeight(index, blurIndex))/2;
		textureOffsetY = 0; //(reel.pitch - textures.getSymbolTextureRegionHeight(index, blurIndex))/2;  
	}
	
	public void trimTop(int f) {
		setHeight(f);
		drawOffsetY = (reel.pitch - textures.getSymbolTextureRegionHeight(index, blurIndex))/2; 
		//drawOffsetY = (reel.pitch - getRegionHeight())/2;
		textureOffsetY = getRegionHeight() - f; 
	}	
	
    public void setHeight2(float height){
		setHeight(height);
		drawOffsetY = (reel.pitch - textures.getSymbolTextureRegionHeight(index, blurIndex))/2;
		textureOffsetY = 0;
    }
    
    public void calculateDrawOffset() {
    	drawOffsetY = (reel.pitch - textures.getSymbolTextureRegionHeight(index, blurIndex))/2;
    	//drawOffsetY = (reel.pitch - getRegionHeight())/2;
    }
	
    
    public void actSpine(float delta){
    	
    	super.act(delta);
    	
		if(((index == 0 && animateSymbol) || index == 9) && animate && isWinning){
			
			textures.getSpineTexture(index).act(delta);
			
			if(textures.getSpineTexture(index).spineAnimationStopped() || animationFrameCnt > 100){
					animate = false;
					animationFrameCnt = 0;
					game.changeLine = true;
					game.context.hasSpineAnimation = false;
				}
			}
    }
    
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		if(showMagician) animate = false;
		
//		if(animateMultiplier){
//			animateMultiplierFrame++;
//			if(animateMultiplierFrame >= scaleAnim.length){
//				animateMultiplierFrame = scaleAnim.length;
//			}
//		}
		
		if(animateGrandJackpot){
			
			if(++currentFrameGrandJackpot >= 24 + 30) {
				currentFrameGrandJackpot = 24 + 30;
				animateGrandJackpot = false;
				game.overlay.jackpotAnimateWin.animateGrandJackpot();
				index = indexNewEgg; 
			}
		}
		
		if(animateHoldAndWin){
			if(++animationFrameCnt >= textures.list[index].length) {
				animationFrameCnt = 0;
				animateHoldAndWin = false;
				game.changeLine = true;
			}
			
			currentFrame = textures.list[index][animationFrameCnt];
		}
		
		if (animate) {
			
			animationFrameCnt++;
			
			if((index > 0 && index < 9) || (index == 0 && !animateSymbol) || (index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
				if(animationFrameCnt >= textures.list[index].length) {
					
						animationFrameCnt = 0;
						animate = false;
						if(!game.context.hasSpineAnimation){
							game.changeLine = true;
						}
					 
				}
				
				currentFrame = textures.list[index][animationFrameCnt];
			}
			
			if(((index == 0 && animateSymbol) || index == 9) && !(index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
				if(animationFrameCnt >=  537) {
					animationFrameCnt = 0;
				}
				currentFrame = 0;//textures.list[index][animationFrameCnt];
			}
			
		}
		
		if (animateOnStopScatter) {
			
			animateOnStopFrameScatter++;
			if(animateOnStopFrameScatter >= animateOnStopLenghtScatter) {
				animateOnStopFrameScatter = 0;
				animateOnStopScatter = false;
			}
		}
		
		if(animateHide){
			if(++animateHideFrame > 4) {
				animateHideFrame = 4;
				animateShow = true;
				animateShowFrame = 0;
				animateHide = false;
				index = specialSymbol;
				game.sounds.play(SoundTrack.KNOCK, false);
		    	ontop 			  = true;
			}
		}

		if(animateShow){
			if(++animateShowFrame > 4) {
				animateShowFrame = 4;
				animateShow = false;
				game.changeLine = true;
			}
		}
		
//		if (animateNearMiss) {
//	    nearMissFrame++;
//	    if (nearMissFrame > 10) { 
//	        animateNearMiss = false;
//	        nearMissFrame = 0;
//	    }
//	}
	
	if (animateNearMiss) {
	    nearMissFrame++;
	    if (nearMissFrame >= nearMissScaleCurve.length) {
	        animateNearMiss = false;
	        nearMissFrame = 0;
	    }
	}
    }
    
	/**
	 * Make ReelSymbol aware of Reel speed
	 * @param step
	 */
	public void updateSpeed(int step) {   	
		if(step >= 10) blurIndex = 3;
    	if(step >= 5) blurIndex = 2;
    	else if(step > 2) blurIndex = 1;
    	else blurIndex = 0;
	}
	
	/**
	 * Adjust drawing position
	 */
	public void adjustDrawingPosition() {
		
		drawOffsetY = (reel.pitch - getRegionHeight())/2;
		drawPositionX = (int) (this.getOriginX() + textures.getSymFrameX(index, currentFrame) - getRegionWidth()/2);
		setWidth(getRegionWidth());
		setHeight(getRegionHeight());
		
		float top = reel.trimTopY; //y + SYMBOLS * pitch;
		
		float bottom = reel.trimBottomY - 20;
		float height = 0;
		float extraHeight = animate ? 0 : (getRegionHeight() - reel.pitch)/2 - textures.getSymFrameY(index, currentFrame);
		
		
		
		if (getY() + reel.pitch + extraHeight >= top) {
			height = (getY() - extraHeight >= top) ? 0 : (top - getY() + extraHeight);
			setHeight(height);
			drawOffsetY = (reel.pitch - getRegionHeight())/2; 
			textureOffsetY = (int) (getRegionHeight() - height); 
		}
		else if (getY() - extraHeight < bottom) {

			height = (getY() + reel.pitch + extraHeight <= bottom) ? 0 : (getY() + reel.pitch + extraHeight - bottom);
			trimBottom((int) (height));
		
		} 
		
		
//		Gdx.app.debug("REEL SYMBOLS","index = " + index + " :::: getY = " + getY());
		//hack
		if(getY() == -272){
			setHeight(0);
		}
		
		if(getY() >= 816 && index == 0){
			setHeight(0);
		}
		
	}
	
	/**
	 * 
	 */
	public void setBlurFrame(int blurIndex) {   	
    	this.blurIndex = blurIndex;
   	}	
	
    @Override
    public void draw(Batch batch, float alpha){
	        

//    	if(dimmingAnim && startAnim)
//    	{
//    		color = getColor();
//    		batch.setColor(color.r, color.g, color.b, 1.0f - dimmingAlphaStep);
//    	}
    	
    	
    	color = getColor();
    	
		if(dimmingAlpha > 0f) { // get current color before dimming

				{
					dimmingAlphaStep += 0.05f;
					if(dimmingAlphaStep >= dimmingAlpha){
						dimmingAlphaStep = dimmingAlpha;
					}
				}
//			}
				
				//stop hide symbols with animation
//			batch.setColor(color.r, color.g, color.b, 1.0f - dimmingAlphaStep);
//			batch.setColor(color.r, color.g, color.b, 0.35f);
			
			batch.setColor(color.r, color.g, color.b, 1.0f);
		}

		if(dimmingAnim && !animate && !isWinning)
		{
			color = getColor();
			dimmingAlphaStep -= 0.05f;
			if(dimmingAlphaStep <= 0){
				dimmingAlphaStep = 0f;
				dimmingAlpha = 0f;
				dimmingAnim = false;
			}

//			batch.setColor(color.r, color.g, color.b, 1.0f - dimmingAlphaStep);

			batch.setColor(color.r, color.g, color.b, 1.0f);
			
		}
		
		if(animateHide){
			float a =  1.0f - animateHideFrame*0.25f;
//			batch.setColor(color.r, color.g, color.b, a);
		}

		if(animateShow){
			batch.setColor(color.r, color.g, color.b, (0.0f + animateShowFrame*0.25f));
		}
		
	     if(animateHoldAndWin){

	    	 	int type = 24;
	    	 	int animFrame = currentFrame;
	    	 	
				float offsetX = -60;
				float offsetY = (getY() == 0) ? -60 : -65;

//				Gdx.app.debug("Reels Symbol", "getY() = " + getY());
				
				batch.draw(textures.getAnimOrbitTexture(type, animFrame),
						drawPositionX + textures.getSymFrameX(index, 0) + offsetX,
						getY() + drawOffsetY + textures.getSymFrameY(index, 0) + offsetY,
						(float)textures.getAnimOrbitTextureRegionWidth(type, animFrame)/2, 
						(float)textures.getAnimOrbitTextureRegionHeight(type, animFrame)/2,
						(float)textures.getAnimOrbitTextureRegionWidth(type, animFrame), 
						(float)textures.getAnimOrbitTextureRegionHeight(type, animFrame),
						1f,
						1f,
						0f,
						textures.getAnimOrbitTextureRegionX(type, animFrame), 
						textures.getAnimOrbitTextureRegionY(type, animFrame),   			
						textures.getAnimOrbitTextureRegionWidth(type, animFrame), 
						textures.getAnimOrbitTextureRegionHeight(type, animFrame),
						false,
						false);	
				
				batch.setColor(color.r, color.g, color.b, 1f);
				
	     } 
	     
	    if(animateGrandJackpot){
	    	if(index == 13){
	    		int type = index;
	    		float offsetX = 0;
	    		float offsetY = 0;
	    		
	    		batch.setColor(color.r, color.g, color.b, animateEggTransform ? opacityEggSymbolOld[currentFrameGrandJackpot] / 100f : 1f);
	    		
//	    		Gdx.app.debug("ReelSymbols", "opacityEggSymbolOld["+currentFrameGrandJackpot+"] = " + opacityEggSymbolOld[currentFrameGrandJackpot] / 100f);
	    		
				batch.draw(textures.getSymbolsTexture(type, 0),
	    				drawPositionX + offsetX,
		    			getY() + drawOffsetY + offsetY,
						(float)textures.getAnimTextureRegionWidth(type, 0)/2,
						(float)textures.getAnimTextureRegionHeight(type, 0)/2,
						(float)textures.getAnimTextureRegionWidth(type, 0),
						(float)textures.getAnimTextureRegionHeight(type, 0),
						currentFrameGrandJackpot > 24 ? 1f : scaleEggSymbolX[currentFrameGrandJackpot] / 100f,
						currentFrameGrandJackpot > 24 ? 1f : scaleEggSymbolY[currentFrameGrandJackpot] / 100f,
						currentFrameGrandJackpot > 24 ? 0f : rotateEggSymbol[currentFrameGrandJackpot],
						textures.getAnimTextureRegionX(type, 0), 
						textures.getAnimTextureRegionY(type, 0),  
						textures.getAnimTextureRegionWidth(type, 0),
						textures.getAnimTextureRegionHeight(type, 0),
						false,
						false);
				
				batch.setColor(color.r, color.g, color.b, 1f);

				type = indexNewEgg;
				
//				Gdx.app.debug("ReelSymbols","type = " + type + "  :::: opacityEggSymbolNew["+currentFrameGrandJackpot+"] = " + opacityEggSymbolNew[currentFrameGrandJackpot] / 100f);
				
				if(currentFrameGrandJackpot < 22){
					batch.setColor(color.r, color.g, color.b, animateEggTransform ? opacityEggSymbolNew[currentFrameGrandJackpot] / 100f : 0f);
				} else {
					batch.setColor(color.r, color.g, color.b, 1f);
				}
				
				batch.draw(textures.getSymbolsTexture(type, 0),
						drawPositionX + offsetX,
						getY() + drawOffsetY + offsetY,
						(float)textures.getAnimTextureRegionWidth(type, 0)/2,
						(float)textures.getAnimTextureRegionHeight(type, 0)/2,
						(float)textures.getAnimTextureRegionWidth(type, 0),
						(float)textures.getAnimTextureRegionHeight(type, 0),
						1f,
						1f,
						0f,
						textures.getAnimTextureRegionX(type, 0), 
						textures.getAnimTextureRegionY(type, 0),  
						textures.getAnimTextureRegionWidth(type, 0),
						textures.getAnimTextureRegionHeight(type, 0),
						false,
						false);
				
				batch.setColor(color.r, color.g, color.b, 1f);
	    	}
    	} else if(animate) {
				if((index > 0 && index < 9) || (index == 0 && !animateSymbol) || (index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
					int frame = 0;
	
						int type = 24;
						int animFrame = currentFrame;
						
						float offsetX = (index == 0) ? -30 : -65;
						float offsetY = (index == 0) ? -37 : -65;
						
						if(!(index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
							batch.draw(textures.getAnimOrbitTexture(type, animFrame),
									drawPositionX + textures.getSymFrameX(index, 0) + offsetX,
									getY() + drawOffsetY + textures.getSymFrameY(index, 0) + offsetY,
									(float)textures.getAnimOrbitTextureRegionWidth(type, animFrame)/2, 
									(float)textures.getAnimOrbitTextureRegionHeight(type, animFrame)/2,
									(float)textures.getAnimOrbitTextureRegionWidth(type, animFrame), 
									(float)textures.getAnimOrbitTextureRegionHeight(type, animFrame),
									1f,
									1f,
									0f,
									textures.getAnimOrbitTextureRegionX(type, animFrame), 
									textures.getAnimOrbitTextureRegionY(type, animFrame),   			
									textures.getAnimOrbitTextureRegionWidth(type, animFrame), 
									textures.getAnimOrbitTextureRegionHeight(type, animFrame),
									false,
									false);	
						}
						
						batch.setColor(color.r, color.g, color.b, 1f);
						
						float scale = scaleNormalSymbol[currentFrame] / 100f;
						
						offsetX = (index == 0) ? 0 : 0;
						offsetY = (index == 0) ? -10 : 0;
						
					    type = (game.context.specialSymbol == index && game.context.gameMode == game.context.FREE_GAMES) ? index + 14 : index;
						
						batch.draw(textures.getSymbolsTexture(type, 0),
			    				drawPositionX + offsetX,
				    			getY() + drawOffsetY + offsetY,
								(float)textures.getAnimTextureRegionWidth(type, 0)/2,
								(float)textures.getAnimTextureRegionHeight(type, 0)/2,
								(float)textures.getAnimTextureRegionWidth(type, 0),
								(float)textures.getAnimTextureRegionHeight(type, 0),
				                (index < 6) ? scaleNormalSymbol[currentFrame] / 100f : scaleHighSymbol[currentFrame] / 100f,
		                		(index < 6) ? scaleNormalSymbol[currentFrame] / 100f : scaleHighSymbol[currentFrame] / 100f,
		                		(index < 6) ? 0 : rotateHighSymbol[currentFrame],
								textures.getAnimTextureRegionX(type, 0), 
								textures.getAnimTextureRegionY(type, 0),  
								textures.getAnimTextureRegionWidth(type, 0),
								textures.getAnimTextureRegionHeight(type, 0),
								false,
								false);
						
						batch.setColor(color.r, color.g, color.b, 1f);
				 } else {
					 
					 
						int offsetY = 0;
						int offsetX = 0;
						
						if(index == 9){
							offsetX = 82 - 45 + 4;
							offsetY = 0 - 1;
						} 

						if(index == 0){
							offsetX = 82 - 5;
							offsetY = -400;
						} 
						
						textures.getSpineTexture(index).setSpineAnimRegionX(0, drawPositionX + 98 + offsetX);
						textures.getSpineTexture(index).setSpineAnimRegionY(0, (getY() + drawOffsetY + offsetY));	
						textures.getSpineTexture(index).draw(batch, alpha);
					
//						batch.draw(textures.getSymbolsTexture(9, blurIndex),
//				        		drawPositionX  + textures.getSymFrameX(9, currentFrame) + offsetX,
//				        		getY() + drawOffsetY + textures.getSymFrameY(9, currentFrame) + offsetY,
//				        		this.getOriginX(),
//				        		this.getOriginY(),
//				        		this.getWidth(),
//				                this.getHeight(),
//				                this.getScaleX(), 
//				                this.getScaleY(),
//				                this.getRotation(),
//				                textures.getSymbolTextureRegionX(9, blurIndex),
//				                textures.getSymbolTextureRegionY(9, blurIndex) + textureOffsetY,
//				                (int)getWidth(),
//				                (int)getHeight(),
//				                false,
//				                false);
						
				 }
			} else  {
			
				
				if(isWinning){
					batch.setColor(color.r, color.g, color.b, 1f);
				}
				
				float offsetX = (index == 0 && blurIndex < 3) ? 0 : 0;
				float offsetY = (index == 0 && blurIndex < 3) ? -10 : 0;
				
//				if(game.context.specialSymbol == index && game.context.gameMode == game.context.FREE_GAMES){
//					blurIndex = 0;
//				}
				int type = (game.context.specialSymbol == index && game.context.gameMode == game.context.FREE_GAMES) ? index + 14 : index;

				//hack
				if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES &&
						   (type == game.math.JACKPOT_SYMBOL ||
						    type == game.math.JACKPOT_MINOR ||
						    type == game.math.JACKPOT_MAJOR || 
						    type == game.math.JACKPOT_GRAND)
						   ){
							ontop = true;	
						}
				
				if(animateNearMiss && blurIndex == 0 && (index == game.math.SCATTER || index == game.math.WILD || index >= game.math.JACKPOT_SYMBOL)){
					
					int index = (nearMissFrame >= nearMissScaleCurve.length) ? nearMissScaleCurve.length - 1 : nearMissFrame;
					float scaleFactor = nearMissScaleCurve[index];
					
					float scale = this.getScaleX() * scaleFactor; // or getScaleY()
					
					batch.draw(textures.getSymbolsTexture(type, blurIndex),
			        		drawPositionX  + textures.getSymFrameX(type, currentFrame) + offsetX,
			        		getY() + drawOffsetY + textures.getSymFrameY(type, currentFrame) + offsetY,
			        		this.getOriginX(),
			        		this.getOriginY(),
			        		this.getWidth(),
			                this.getHeight(),
			                scale, 
			                scale,
			                this.getRotation(),
			                textures.getSymbolTextureRegionX(type, blurIndex),
			                textures.getSymbolTextureRegionY(type, blurIndex) + textureOffsetY,
			                (int)getWidth(),
			                (int)getHeight(),
			                false,
			                false);
				} else {
					batch.draw(textures.getSymbolsTexture(type, blurIndex),
					        		drawPositionX  + textures.getSymFrameX(type, currentFrame) + offsetX,
					        		getY() + drawOffsetY + textures.getSymFrameY(type, currentFrame) + offsetY,
					        		this.getOriginX(),
					        		this.getOriginY(),
					        		this.getWidth(),
					                this.getHeight(),
					                this.getScaleX(), 
					                this.getScaleY(),
					                this.getRotation(),
					                textures.getSymbolTextureRegionX(type, blurIndex),
					                textures.getSymbolTextureRegionY(type, blurIndex) + textureOffsetY,
					                (int)getWidth(),
					                (int)getHeight(),
					                false,
					                false);
				}
									
			}
		 
		 
		if(dimmingAlpha > 0f || dimmingAnim) { // restore color
			batch.setColor(color.r, color.g, color.b, 1f);
		}		
		
		batch.setColor(color.r, color.g, color.b, 1f);
    }
    
    /**
     * Set animation loop property
     * @param looping
     */
    public void loop(boolean looping) {
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	this.looping =  false;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    }
    
    public void animate(boolean looping, boolean hasWildWin, int mult) {
    	
    	// Don't interrupt animation if running 
    	if(this.animate && this.looping) {
//    		Gdx.app.debug("ReelSymbol", "animation already running");
    		return;
    	}
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	
    	if(index == 9 && !(index == 9 && game.context.specialSymbol == 9 && game.context.gameMode == game.context.FREE_GAMES)){
    		game.context.hasSpineAnimation = true;
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
//    	if(index == 11){
//    		spineTextures.clearAnimationState();
//    	}
    	multiplier = mult;
    	
//    	wildLine = hasWildWin;
    	
    	disble = false;
    	animateSymbol = false;
    	
    	this.animate = true; //animate;
    	this.looping = false;//(index == com.pgd.game.Math.BONUS  || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = textures.getAnimTotalFrames(index);
    	delayAnimation = 0;
    	
    	ontop = false;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    }

    public void winSymbol(boolean looping, boolean hasWildWin, int mult) {
    	
    	// Don't interrupt animation if running 
    	if(this.animate && this.looping) {
//    		Gdx.app.debug("ReelSymbol", "animation already running");
    		return;
    	}
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	
    	if(index == 0 || index == 9){
    		game.context.hasSpineAnimation = true;
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
//    	if(index == 11){
//    		spineTextures.clearAnimationState();
//    	}
    	multiplier = mult;
    	
//    	wildLine = hasWildWin;
    	
    	disble = false;
    	animateSymbol = false;
    	
    	this.animate = true; //animate;
    	this.looping = false;//(index == com.pgd.game.Math.BONUS  || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = textures.getAnimTotalFrames(index);
    	delayAnimation = 0;
    	
//    	game.overlay.addWild.animate(animate);
    	
    	ontop = false;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    }

    public void winSymbolAllLines(boolean looping, boolean hasWildWin, int mult) {
    	
    	// Don't interrupt animation if running 
    	if(this.animate && this.looping) {
//    		Gdx.app.debug("ReelSymbol", "animation already running");
    		return;
    	}
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	disble = false;
    	animateSymbol = false;
    	
//    	multiplier = mult;
    	
    	this.animate = false; //animate;
    	this.looping = false;//(index == com.pgd.game.Math.BONUS  || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	animationFrameCnt = 0;
    	currentFrame = textures.getAnimTotalFrames(index) - 1;
    	totalFrames = textures.getAnimTotalFrames(index);
    	delayAnimation = 0;
    	
//    	game.overlay.addWild.animate(animate);
    	
    	ontop = false;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    }

    public void wildMult(boolean looping, boolean hasWildWin) {
    	
    	// Don't interrupt animation if running 
    	if(this.animate && this.looping) {
//    		Gdx.app.debug("ReelSymbol", "animation already running");
    		return;
    	}
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	disble = false;
    	animateSymbol = false;
    	
    	this.animate = false; //animate;
    	this.looping = false;//(index == com.pgd.game.Math.BONUS  || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = textures.getAnimTotalFrames(index);
    	delayAnimation = 0;
    	
//    	game.overlay.addWild.animate(animate);
    	
    	ontop = true;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    	wildMultAnim = true;
    	
    }
    
    public void highlight(boolean highlight){
    	this.highlight = highlight;
    	highlightFrameCnt = 0;
    }
    
    public void setOnTop(boolean top){
    	ontop = top;
    }
    
    public void setSymbolAtStopSpecial(int symbol, int cur) {
    	

    	game.context.hasSpineAnimation = false;
    	
    	animateSpine = false;
    	
    	multiplier = 0;
    	
    	showBonusSymbols = false;
    	showBonusCurrentFrame = 0;
    	
    	animateMultiplier       = false;
    	animateMultiplierFrame  = 0;
    	wildCount = 0;
    	
    	if(index == 0 || index == 9){
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
    	if(index == -1){
    		this.index = 1;
    	} else {
    		this.index = index;
    	}
    	
    	disble = false;
    	animateSymbol = false;
    	
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = (textures.getAnimRegionLoaded()) ? textures.getAnimTotalFrames(this.index) : 0;
    	animate = false;
    	looping = false;
    	
    	calculateDrawOffset();
    	setWidth(getRegionWidth());
    	
    	drawPositionX = (int) (this.getOriginX() - getRegionWidth()/2);

    	ontop = true;
    }
    
    public void setIndex(int index) {
    	

    	game.context.hasSpineAnimation = false;
    	
    	animateSpine = false;
    	
    	multiplier = 0;
    	
    	showBonusSymbols = false;
    	showBonusCurrentFrame = 0;
    	
    	animateNearMiss = false;
    	animateMultiplier       = false;
    	animateMultiplierFrame  = 0;
    	wildCount = 0;
    	
    	if(index == 0 || index == 9){
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
    	if(index == -1){
    		this.index = 1;
    	} else {
    		this.index = index;
    	}
    	
    	disble = false;
    	animateSymbol = false;
    	
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = (textures.getAnimRegionLoaded()) ? textures.getAnimTotalFrames(this.index) : 0;
    	animate = false;
    	looping = false;
    	
    	calculateDrawOffset();
    	setWidth(getRegionWidth());
    	
    	drawPositionX = (int) (this.getOriginX() - getRegionWidth()/2);

		//hack
//		if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES &&
//				   (index == game.math.JACKPOT_SYMBOL ||
//				   index == game.math.JACKPOT_MINOR ||
//				   index == game.math.JACKPOT_MAJOR || 
//				   index== game.math.JACKPOT_GRAND)
//				   ){
//					ontop = true;	
//				}
		
		ontop = false;	
	}
    
	public void setStacked(boolean show, int reel, int pos) {
		stacked = show;
//		game.overlay.stackedSymbols.show(show, reel, pos);
	}
	
    public void unhighlightSpine(boolean highlight){
    	if(index == 0 || index == 9){
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
    	animate = false;
    	
    	game.context.hasSpineAnimation = false;
    	
    	animateNearMiss = false;
    	
    }
    
    public void stopAnim() {
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	animateMultiplier       = false;
    	animateMultiplierFrame  = 0;
    	wildCount = 0;
    	
    	multiplier = 0;
    	
    	if(index == 0 || index == 9){
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
    	disble = false;
    	animateSymbol = false;
    	
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = textures.getAnimTotalFrames(index);
    	animate =  false;
    	looping = false;
    	disble = false;

    	showBonusSymbols = false;
    	showBonusCurrentFrame = 0;
    	
    	ontop = false;
//    	ontop = (index == com.pgd.game.Math.SCATTER) ? true : false;
    }
	public int getIndex() {
		return index;
	}
    
    public void setFlipSimbol(int flipSymbolIndex) {
    	this.flipSymbolIndex = flipSymbolIndex;
    	canFlip = true;
    //	Gdx.app.debug("setFlipSimbol", "flipSymbolIndex = " + flipSymbolIndex + "index = " + index);
    }
    
    public Reel getReel() {
    	return reel;
    }

    public int getReelNum() {
    	return reel.index;
    }
    	
    public int getCurrentStop() {
    	// TODO: implement and test
    	return (2 -  (int) getY() / reel.pitch);
    }
    
    /**
     * Loads a sequence of scale actions to animate card flip. 
     * Flag prevents it from flipping twice. 
     * 
     * @return int current flip symbol index
     */
    public int flip() {
    	
    	if(canFlip) {
    	}
    	
    	return flipSymbolIndex;
    	
    }

    private Texture getTexture() {
    	return textures.getAnimTexture(index, currentFrame);
    }
    	
    private int getRegionX() {
    	return textures.getAnimTextureRegionX(index, currentFrame);
    }
    
    private int getRegionY() {
    	return textures.getAnimTextureRegionY(index, currentFrame);
    }
    
    private int getRegionWidth() {
    	if(getReel().getMagicianReelAnim())
    	{
    		return textures.getAnimTextureRegionWidth(index, currentFrame);
    	}
    	else if(animate) {
    		return textures.getAnimTextureRegionWidth(index, 0);
    	}
    	else {
    		return textures.getSymbolTextureRegionWidth(index, blurIndex);
    	}
    }
    
    private int getRegionHeight() {
    	if(showMagician)
    	{
    		return textures.getAnimTextureRegionHeight(index, currentFrame);
    	}
    	else if(animate) {
    		return textures.getAnimTextureRegionHeight(index, 0);
    	}
    	else {
    		return textures.getSymbolTextureRegionHeight(index, blurIndex);
    	}
    }
    
    /**
     * Set symbol dimming. 
     * @param float alpha 0 - 1, set 0 for no dimming  
     */
    public void setDimming(float alpha) {
    	this.dimmingAlpha = alpha;
    }
    
    /**
     * Remove diming highlight 
     * @param float alpha 0 - 1, set 0 for no dimming  
     */
    public void setDimmingAnim(float alpha) {
    	dimmingAnim = false;
    	this.dimmingAlphaStep = 0f;
    	
    	if(dimmingAlpha > 0 && dimmingAlpha < 1f)
    	{
	    	this.dimmingAlpha = alpha;
	    	this.dimmingAlphaStep = 0.75f;
	    	dimmingAnim = true;
    	}
    	
    	multiplier = 0;
    }
    
    public void resetDim()
    {
    	this.dimmingAlphaStep = 0f;
    }

    
    /**
     * shoe magician animation
     */
    public void setShowMagician(boolean show) {
    	showMagician  = show;
    }

    public void animateGrandJackpotSymbol(int index, boolean transform){
    	
    	Gdx.app.debug("ReelSymbol", "animateEggTransform = " + animateEggTransform);
    	
    	animateEggTransform = transform;
    	this.indexNewEgg = index;
    	animateGrandJackpot = true;
    	currentFrameGrandJackpot = 0;
    }

    public void setTransformAnim(){
//    	Gdx.app.debug("showWILD", "showWILD");
    	showBonusSymbols = true;
    	showBonusCurrentFrame = 0;
    	ontop = false;
    	isWinning = false;
    }

    public void hideBonus(){
    	showBonusSymbols = false;
    	showBonusCurrentFrame = 0;
    	ontop = false;
    	isWinning = false;
    }
    
    public void hideSymbols(){
    	index = 14;
    }

    public void restoreOldSymbols(int index){
    	this.index = index;
    }

    public void setAnimNearMiss(){
//    	ontop = true;
    	animateNearMiss = true;
    	nearMissFrame = 0;
    }
    
    public void setDisable(){
    	disble = true;
    	isWinning = false;
    	animate = false;
    }

    public boolean getDisable(){
    	return disble;
    }
    

    public void animateHide(boolean win){    	
    		animateHide       = win;
    		animateHideFrame  = 0;
    }

    public void animateScatterSymbol(boolean looping, boolean hasWildWin) {
    	
//    	game.gsLink.console("index = " + index);
//    	game.gsLink.console("this.animate = " + this.animate);
//    	game.gsLink.console("this.looping = " + this.looping);
//    	game.gsLink.console("textures.getAnimRegionLoaded() = " + textures.getAnimRegionLoaded());
//    	
    	// Don't interrupt animation if running 
    	if(this.animate && this.looping) {
//    		Gdx.app.debug("ReelSymbol", "animation already running");
    		return;
    	}
    	
    	if(!textures.getAnimRegionLoaded()) return;	
    	
    	
    	if(index == game.math.SCATTER){
    		game.context.hasSpineAnimation = true;
    		textures.getSpineTexture(index).clearAnimationState();
    	}
    	
    	disble = false;
    	animateSymbol = true;
    	
    	this.animate = true; //animate;
    	this.looping = (index == com.pgd.game.Math.BONUS  || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	animationFrameCnt = 0;
    	currentFrame = 0;
    	totalFrames = textures.getAnimTotalFrames(index);
    	delayAnimation = 0;
    	
    	ontop = false;//(index == com.pgd.game.Math.BONUS || index == com.pgd.game.Math.SCATTER) ? true : false;;
    	
    }
    
    public void showSpecialSymbolAnim(int symbol){
    	specialSymbol	  = symbol + 14;
		animateHide       = true;
		animateHideFrame  = 0;
		animateShow		  = false;
		animateShowFrame  = 0;
    }

    public void showHoldAndWinSymbolAnim(){
    	
    	ontop 			  = true;
    	animateHoldAndWin = true;
    	animationFrameCnt = 0;
    }
    
    public boolean spineAnimationStopped(){
    	return spineTextures.spineAnimationStopped();
    }
    
//    public void setBoostedSymbol(boolean boosted) {
//    	boost = boosted;
//    }
    
}
