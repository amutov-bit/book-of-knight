package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.base.AbstractController.Event;


public class FreeGamesBook extends Group {
	
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
	private boolean stopAnimation;
	private boolean showBook;
	private boolean animeteSymbols;
	
	private Label title, text, congrats, press, youWon;

	private int index = 1;
	private int currentIndex = 1;
	
	private Random rand;
	
	private Color color;
	
	float scaleX[] = {
			(float)4	,
			(float)36	,
			(float)67	,
			(float)102	,
			(float)131	,
			(float)133	,
			(float)132	,
			(float)131	,
			(float)127	,
			(float)113	,
			(float)99	,
			(float)87	,
			(float)78	,
			(float)79	,
			(float)86	,
			(float)96	,
			(float)106	,
			(float)103	,
			(float)101	,
			(float)98	,
			(float)99.5	,	
			(float)101	,
			(float)100.5,	
			(float)100	,
			(float)100	,
	};

	float scaleY[] = {
			(float)4	,
			(float)3    ,
			(float)2.4  ,
			(float)3.6  ,
			(float)8.9  ,
			(float)31   ,
			(float)54   ,
			(float)78   ,
			(float)127  ,
			(float)113  ,
			(float)99   ,
			(float)87   ,
			(float)78   ,
			(float)79   ,
			(float)86   ,
			(float)96   ,
			(float)106	,
			(float)103  ,
			(float)101  ,
			(float)98	,
		    (float)99.5	,
			(float)101	,
			(float)100.5,
			(float)100	,
			(float)100	,
	};

	public FreeGamesBook(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {

//		digits = new DigitsSingle(game, "bigwin_digits", true, game.textures.getInterfaceCommon());
//		digits.commitAssets();
//		
//		file = "bigwin_digits";
//		numberDigits = 12;
//		startX = 440 - 10 + 320;
//		startY = 772 - 326 - 124 - 60 - 85 - 0 + 300 - 80;
//		symW	=  digits.getW()-20;
//		symH 	=  digits.getH();					
//		backgroundWidth = 300;
		
		rand = new Random();
		
		game.textures.getInterfaceCommon().findRegion("openbook").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("openbook")));
		bg.setPosition(0, 0);
		
		//#903300
		title = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(141/255f, 51/255f, 0/255f, 255f/255f)));
		title.setVisible(true);
		title.setFontScale(1f);
		title.setText(game.gameTxt.expandingSymbol);
		title.setAlignment(Align.center);
		title.setBounds(460 + 10,750,485, 0);
		addActor(title);

		congrats = new Label("ERROR: ", new LabelStyle(game.fonts.font54px, new Color(141/255f, 51/255f, 0/255f, 255f/255f)));
		congrats.setVisible(true);
		congrats.setFontScale(1f);
		congrats.setText(game.gameTxt.congrats);
		congrats.setAlignment(Align.center);
		congrats.setBounds(950 ,750 - 75 - 20 + 100,495, 0);
		addActor(congrats);


		press = new Label("ERROR: ", new LabelStyle(game.fonts.font34px, new Color(141/255f, 51/255f, 0/255f, 255f/255f)));
		press.setVisible(true);
		press.setFontScale(1f);
		press.setText(game.gameTxt.pressAnywhere);
		press.setAlignment(Align.center);
		press.setBounds(950 ,704 - 321 - 145 + 165 - 20,495, 0);
		addActor(press);

		youWon = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(141/255f, 51/255f, 0/255f, 255f/255f)));
		youWon.setVisible(true);
		youWon.setFontScale(1.2f);
		youWon.setText(game.gameTxt.youWon);
//		youWon.setText("YOU WON");
		youWon.setAlignment(Align.center);
		youWon.setBounds(950,534 + 10 + 135,495, 0);
		addActor(youWon);
		
		//#ef6000		
		text = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(239/255f, 96/255f, 0/255f, 255f/255f)));
		text.setVisible(true);
		text.setFontScale(1.2f);
		text.setAlignment(Align.center);
		text.setBounds(950,534 + 10,495, 0);
		addActor(text);
		
		if(game.languageCode == "RUS" || game.languageCode == "PL"){
			title.setFontScale(0.5f);
		}

		if(game.languageCode == "CZE" || game.languageCode == "TUR"){
			title.setFontScale(0.8f);
		}
		
		if(game.languageCode == "FRA" || game.languageCode == "POR" || game.languageCode == "ESP"){
			title.setFontScale(0.8f);
			youWon.setFontScale(1f);
			text.setFontScale(1f);
		}

		if(game.languageCode == "BG"){
			title.setFontScale(0.7f);
			youWon.setFontScale(1f);
			text.setFontScale(1f);
		}
	}
	
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		
		timeElapsed		+= delta;
		
			if (animate && !showBook) {
				game.spineBookAnimation.act(delta, 0);
			}
		
			if (animate) {
				if (timeElapsed - lastTimeElapsed > 0.030f) {
						lastTimeElapsed = timeElapsed;
						currentFrame++;
						
						
						if (!showBook){
							if(/*game.spineBookAnimation.getEnd() ||*/ currentFrame >= 24)
							{
								currentFrame = 24;
								showBook = true;
								animeteSymbols = true;
								
							}
						}
						
						if(animeteSymbols){
							if(currentFrame % 4 == 0 && currentFrame > 8){
								index = rand.nextInt(9) + 1;
							}
						}
						
						if(showBook){
							if(currentFrame > 100){
								currentFrame = 101;
								animeteSymbols = false;
								stopAnimation = true;
								index = game.context.specialSymbol;
								animate = false;
								game.overlay.freeGamesTitle.setSpecialSymbols(index);
							}
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
				
//			if(game.spineBookAnimation.isLoaded()){
//				game.spineBookAnimation.setSpineAnimRegionX(0, 655 + 300 + 10);
//				game.spineBookAnimation.setSpineAnimRegionY(0, 600);	
//				game.spineBookAnimation.draw(batch, alpha);
//				batch.setColor(color.r, color.g, color.b, 1f);
//			}
			if(!showBook){
				bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
				bg.setScale(scaleX[currentFrame]/100f, scaleY[currentFrame]/100f);
				bg.draw(batch, alpha);
			}
    	}
		
    	if(showBook){
    		bg.setScale(1f,1f);
    		bg.draw(batch, alpha);
    		title.draw(batch, alpha);
    		text.draw(batch, alpha);
    		youWon.draw(batch, alpha);
    		congrats.draw(batch, alpha);
    		
    		if(stopAnimation){
    			press.draw(batch, alpha);
    		}
    		
    		int type = this.index + 14;
    		
    		int offsetX = this.index == 9 ? 0 : 0;
    		int offsetY = this.index == 9 ? 0 : 0;
    		
			batch.draw(game.reels.textures.getSymbolsTexture(type, 0),
					560 + 10 + offsetX,
					704 - 321 - 145 + 165 + offsetY,
					game.reels.textures.getSymbolTextureRegionWidth(type, 0)/2,
					game.reels.textures.getSymbolTextureRegionHeight(type, 0)/2 ,
					game.reels.textures.getSymbolTextureRegionWidth(type, 0),
					game.reels.textures.getSymbolTextureRegionHeight(type, 0),
					this.getScaleX(), 
					this.getScaleY(),
					this.getRotation(),
					game.reels.textures.getSymbolTextureRegionX(type, 0),
					game.reels.textures.getSymbolTextureRegionY(type, 0),
					game.reels.textures.getSymbolTextureRegionWidth(type, 0),
					game.reels.textures.getSymbolTextureRegionHeight(type, 0),
					false,
					false);

//			type = 9 + 13;
//					
//			batch.draw(game.reels.textures.getSymbolsTexture(type, 0),
//					560 + 10 + offsetX,
//					704 - 321 - 145 + 165 + offsetY,
//					game.reels.textures.getSymbolTextureRegionWidth(type, 0)/2,
//					game.reels.textures.getSymbolTextureRegionHeight(type, 0)/2 ,
//					game.reels.textures.getSymbolTextureRegionWidth(type, 0),
//					game.reels.textures.getSymbolTextureRegionHeight(type, 0),
//					this.getScaleX(), 
//					this.getScaleY(),
//					this.getRotation(),
//					game.reels.textures.getSymbolTextureRegionX(type, 0),
//					game.reels.textures.getSymbolTextureRegionY(type, 0),
//					game.reels.textures.getSymbolTextureRegionWidth(type, 0),
//					game.reels.textures.getSymbolTextureRegionHeight(type, 0),
//					false,
//					false);
		}
			
			
		
    	    	
    }
    
    
	public void setVisible(boolean visible) {
		
//		this.index = index;
		
		animate = visible;
		
		stopAnimation = false;

		showBook = false;
		
		currentFrame = 0;
		
		stopAnimaionFrame = 0;
	
		if(!visible){
			game.spine.clearAnimationState();
		}
		
		if(visible)	game.sounds.play(SoundTrack.OPEN_BOOK, false);
		else		game.sounds.stop(SoundTrack.OPEN_BOOK);
		
		game.spine.setVisible(visible);
	}
	
	
	public boolean animationStopped(){
		return stopAnimation;
	}
	
	public void hideBigWin(){
		stopAnimaionFrame = 0;
		stopAnimation = false;
		digits.hideAll();
	}

	public void setWin(int win) {
		// TODO Auto-generated method stub
		text.setText(win + "\n" + game.gameTxt.freeGameBook);
		
	}
}
