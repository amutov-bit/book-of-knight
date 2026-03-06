package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

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


public class FreeGamesTitle extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
//	private float timeElapsed;
//	private float lastTimeElapsed;
//	private Image freeGamesTitle;
	private Image freeGamesBG;
//	private Image freeGamesTop;
	private DigitsSingle digitsFrameFirst;
//	private Digits digitsFrameSecond;
	
//	private Array<Image> symbols = new Array<Image>();
//	private Array<Image> symbolsDeact = new Array<Image>();
	
	private int currentNumber = 0;
	
	
	private boolean showFreeGames = false;
	
	private float startX, startY, currentX;
	private float numberDigits;
	private int symW, symH;
	private long currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	private boolean visible = false;

	private int		index 	   = 1;
	private boolean showSymbol = false;
	private boolean animate    = false;
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private int currentFrame;
	private Color color;
	
//	private static List<Image> goldSymbol = new ArrayList<Image>();

//	private int currentFrame;
//	private boolean animate;
	public FreeGamesTitle(BookOfKnight game) {
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
	
	public void commitAssets(String digits, String bg) {
		
		digitsFrameFirst = new DigitsSingle(game, digits, false, game.textures.getInterfaceAtlas());
		digitsFrameFirst.commitAssets();
		
		startX = -12 - 15;
		startY = 1080 - 185 - 160 - 340 + 20 - 10 + 15;
		symW = 124;
		symH =  0;			
		backgroundWidth = 248;
		backgroundHight = 0;
		offsetW = 50;
		
		game.textures.getInterfaceAtlas().findRegion(bg).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		freeGamesBG = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion(bg)));
		freeGamesBG.setPosition(8 - 15, 1080 - 220 - 148 - 340 -10);

		

		setVisible(false);

		addActor(freeGamesBG);
		
		addActor(digitsFrameFirst);
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;

		if(!visible){
			showSymbol   = false;
		}
//		freeGamesTitle.setVisible(visible); 
//		digitsFrameFirst.setVisible(visible);
	}
	
	@Override
    public void act(float delta){
		
		super.act(delta);
		timeElapsed		+= delta;
		
		if (animate) {

			Gdx.app.debug("Title ", " assaasassasa = " + currentFrame);

			if (timeElapsed - lastTimeElapsed > 0.030f) {
					lastTimeElapsed = timeElapsed;
					currentFrame++;
					
					Gdx.app.debug("Title ", " currentFrame = " + currentFrame);

					if(currentFrame > 9){
							currentFrame = 10;
							animate = false;
					}
				}
		}		
	}
	
    @Override
    public void draw(Batch batch, float alpha)
	{
    	if(visible /*&& currentWin > 0*/){
	    	
    		color = getColor();
    		
    		

	    	freeGamesBG.draw(batch, alpha);
	    	batch.setColor(color.r, color.g, color.b, 1.0f);
	    	
			long win = currentWin;
			for(int i = 0; i < lenght; i++)
			{
				if(this.decimal)
				{
					if(i == lenght - beforeDecimal)
					{
						digitsFrameFirst.setElement(10, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
						digitsFrameFirst.draw(batch, alpha);
					}
					else
					{
						long num = win % 10;
						digitsFrameFirst.setElement((int)num, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
						digitsFrameFirst.draw(batch, alpha);
						
						win /= 10;
					}
				}
				else{
					long num = win % 10;
					
					digitsFrameFirst.setElement((int)num, currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2);
					digitsFrameFirst.draw(batch, alpha);
					
					win /= 10;
				}
			}
			
			if(showSymbol){
				
				int type = this.index + 14;
				
				if(animate){
//					Gdx.app.debug("Title ", " currentFrame = " + currentFrame);
//					Gdx.app.debug("Title ", " alo = " + 0.1f * currentFrame);
					
					batch.setColor(color.r, color.g, color.b, 0.1f * currentFrame);
				}
				
				float offsetX = (this.index == 9) ? 20 : 20;
				float offsetY = (this.index == 9) ? 10 : 10;
				
				batch.draw(game.reels.textures.getSymbolsTexture(type, 0),
						getX() + 0 - 23 + offsetX - 15,
						getY() + 1080 - 12 - 168 - 340 + 110 + offsetY - 10,
						game.reels.textures.getSymbolTextureRegionWidth(type, 0)/2,
						game.reels.textures.getSymbolTextureRegionHeight(type, 0)/2 ,
						game.reels.textures.getSymbolTextureRegionWidth(type, 0),
						game.reels.textures.getSymbolTextureRegionHeight(type, 0),
						this.getScaleX() * 0.4f, 
						this.getScaleY() * 0.4f,
						this.getRotation(),
						game.reels.textures.getSymbolTextureRegionX(type, 0),
						game.reels.textures.getSymbolTextureRegionY(type, 0),
						game.reels.textures.getSymbolTextureRegionWidth(type, 0),
						game.reels.textures.getSymbolTextureRegionHeight(type, 0),
						false,
						false);
				batch.setColor(color.r, color.g, color.b, 1.0f);
			}
    	}
	}
	
	public boolean isFreeGamesTitleShow(){
		return showFreeGames;
	}
	
	public void setWinFirst(int num) {
		
		this.decimal = false;
		
		digitsFrameFirst.hideAll();
		
		currentWin = num;
		
		lenght = (Integer.toString((int)num).length());
		
		if(currentWin <= 10){
			offsetW = 50;
		} else {
			offsetW = 40;
		}
		currentX = startX - 60 + lenght*(symW-offsetW) + (backgroundWidth -  lenght*(symW - offsetW))/2 - 10;
		

		
	}

	public void setSpecialSymbols(int index) {
		showSymbol   = true;
		animate      = true;
		currentFrame = 0;
		this.index   = index;
	}
	
	public void resetSpecialSymbols(){
		showSymbol   = false;
	}
	
}
