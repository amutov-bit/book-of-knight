package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.pgd.game.BookOfKnight;


public class DigitsCorner extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private String file;
	private Texture digits;
	private float startX, startY, currentX;
	private float numberDigits;
	private float rotation;
	private int symW, symH;
	private int currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	private AtlasRegion regions;
	
	public DigitsCorner(BookOfKnight game, String name, TextureAtlas atals, float num, float w, float h, int offsetW, int offsetH) {
		this.game = game;
		
		file = name;
		 
		numberDigits = num;
		
		loadAssets();		
		
		currentWin = 0;
		
		backgroundWidth = w;
		backgroundHight = h;
		
		this.offsetW = offsetW;
		
		this.offsetH = offsetH;
		
		decimal = true;
		
		regions = atals.findRegion(file);
		
		rotation = 0f;
	}
	
	@Override
	public void setPosition(float x, float y)
	{
		startX = x;
		startY = y;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
		regions.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits = regions.getTexture();
		symW = regions.getRegionWidth();
		symH =  (int)(regions.getRegionHeight() / numberDigits);			
	}
	
	@Override
	public void draw(Batch batch, float alpha){
		
		int win = currentWin;

		for(int i = 0; i < lenght; i++)
		{
			if(win > 0){
				if(this.decimal)
				{
					if(i == lenght - beforeDecimal)
						batch.draw(digits,
								  currentX - i*(symW - offsetW), startY + (backgroundHight - symH)/2,
									symW/2,
									symH/2,
									symW,
									symH,
									this.getScaleX(),
									this.getScaleY(),
									rotation,
								     regions.getRegionX() + offsetH,
									 regions.getRegionY() + symH*(10) + offsetH,
								  symW,
								  symH,
								  false,
								  false);
					else
					{
						int num = win % 10;
						batch.draw( digits,
									currentX - i*(symW - offsetW),
									startY + (backgroundHight - symH)/2,
									symW/2,
									symH/2,
									symW,
									symH,
									this.getScaleX(),
									this.getScaleY(),
									rotation,
									 regions.getRegionX() + 0,
									 regions.getRegionY() + symH*(num),
									symW,
									symH,
									false,
									false);
						win /= 10;
					}
				}
				else
				{
					int num = win % 10;
					
					float offsetX = 0;
					
					if(win < 10){
						offsetX = -10;
					}
					
					batch.draw( digits,
								currentX - i*(symW - offsetW),
								startY + (backgroundHight - symH)/2,
								symW/2,
								symH/2,
								symW,
								symH,
								this.getScaleX(),
								this.getScaleY(),
								rotation,
								regions.getRegionX() + offsetH,
								regions.getRegionY() + symH*(num) + offsetH,
								symW,
								symH,
								false,
								false);
					win /= 10;
				}
			}
		}
	}
	
	public void setWin(int win)
	{
		
		if(this.decimal)
		{
			currentWin = win * game.meters.getDenomination();
			String text = (win * game.meters.getDenomination()/100) + "." + (win * game.meters.getDenomination()/ 10 % 10) + (win * game.meters.getDenomination() % 10);

			beforeDecimal = Integer.toString(currentWin / 100).length() + 1;
		
			lenght = (text.length());
			currentX = startX - 60 + lenght*(symW-offsetW) + (backgroundWidth -  lenght*(symW - offsetW))/2;
		}
		else
		{
			currentWin = win;
			
			float offsetX = 0;
//			if(win / 10 <= 0){
//				offsetX = 20;
//			}
			lenght = (Integer.toString(win).length());
			currentX = offsetX + startX - 60 + lenght*(symW-offsetW) + (backgroundWidth -  lenght*(symW - offsetW))/2;
//			Gdx.app.debug("digits", "currentWin = " + currentWin);
		}
	}
	
	public void setFormat(boolean decimal)
	{
		this.decimal = decimal;
	}
	
	public void setRotation(float r)
	{
		rotation = r;
	}
}
