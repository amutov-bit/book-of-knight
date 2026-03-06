package com.pgd.game.base;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;

public class Field extends Group {

	private BookOfKnight game;
	private Label textDigits;
	private Label textTitle;
	private Label textFull;
	private BitmapFont font;
//	private Image image;
	private boolean decimals;
	private Array<AtlasRegion> regions = new Array<AtlasRegion>();
	int coordintes[] = new int[2];
	
	String text = "";
	String textFullstr = "";
	
	LabelStyle labelStyleDigits;
	LabelStyle labelStyleTitle;
	
	GlyphLayout glyphLayout = new GlyphLayout();
	 
	int align = 0;
	
	public Field(BookOfKnight game, BitmapFont font, LabelStyle labelStyle, LabelStyle labelStyleTitle) {
		this.game = game;
		this.font = font;
		this.labelStyleDigits = labelStyle;
		this.labelStyleTitle = labelStyleTitle;

		decimals = true;
	}	
	
	public void commitAssets() {
		
		textDigits = new Label(" ", labelStyleDigits);
		textTitle  = new Label(" ", labelStyleTitle);
		textFull  = new Label(" ", labelStyleTitle);
		
		addActor(textDigits);
		addActor(textTitle);
	}
	
	public void setPosition(int x, int y, int textOffsetX, int textOffsetY)	{
		coordintes[0] = x; coordintes[1] = y;
//		textDigits.setPosition(x + textOffsetX, y + textOffsetY);
//		textTitle.setPosition(x + textOffsetX, y + textOffsetY);
		
		textFull.setPosition(x + textOffsetX, y + textOffsetY);
	}
	
	public void setTextAlignment(int align) {
//		textTitle.setAlignment(align);
//		textDigits.setAlignment(align);
		this.align = align;
		textFull.setAlignment(align);
	}
	
	public void setTextWidth(int width) {
		textDigits.setWidth(width);
		textTitle.setWidth(width);
		textFull.setWidth(width);
	}
	
	public void setText(String string) 	{
		textDigits.setText(string);
		textTitle.setText(string);
	}

	public void setStatusText(String string) 	{
		textDigits.setText(string);
		textFullstr = string;
		textFull.setText(string);

	}
	
	public void setValueBet(long value, boolean hasBigNumberK) 	{
//		text.setText(game.gameTxt.bet + " " + Long.toString(value/100) + "." + (value / 10 % 10) + (value % 10) + " " + game.meters.getCurrency());
		
		
		textFull.setText(game.gameTxt.bet + " " + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency());
		text = game.gameTxt.bet;
		
		textFullstr = game.gameTxt.bet + " " + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency();
		
		textTitle.setText(game.gameTxt.bet);
		textDigits.setText(" " + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency());

	}	

	public void setValueWin(long value, boolean hasBigNumberK) 	{
//		text.setText(game.gameTxt.win + " " + Long.toString(value/100) + "." + (value / 10 % 10) + (value % 10) + " " + game.meters.getCurrency());
		
		textFull.setText(game.gameTxt.win + "  " + game.meters.formatNumber(value, hasBigNumberK));
		
		textFullstr = game.gameTxt.win + "  " + game.meters.formatNumber(value, hasBigNumberK);
		
		text = game.gameTxt.win;
		textTitle.setText(game.gameTxt.win);
		textDigits.setText("  " + game.meters.formatNumber(value, hasBigNumberK));
		
		
        
//        Gdx.app.debug("Field", "glyphLayout.width = " + glyphLayout.width);

	}	
	
//	public void setValueBetBig(long value) 	{
//		if(value % 100000 > 0){
//			text.setText(Long.toString(value / 100000) + "." + (value % 100000)/10000 + "K");
//		} else {
//			text.setText(Long.toString(value / 100000) + "K");
//		}
//	}
	
	public void setValueBalance(long value, boolean hasBigNumberK){
		String tmp = (game.meters.getCurrency() == "FUN") ? game.gameTxt.balanceDemo : game.gameTxt.balanceReal; 
//		text.setText(tmp + " "  + (Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10)) + " " + game.meters.getCurrency());
		
		textFull.setText(tmp + " "  + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency());
		
		textFullstr = tmp + " "  + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency();
		
		text = tmp;
		textTitle.setText(tmp);
		textDigits.setText(" "  + game.meters.formatNumber(value, hasBigNumberK) + " " + game.meters.getCurrency());

		
	}
	
	public void setFontScale(float scaleW, float scaleH){
		textDigits.setFontScale(scaleW, scaleH);
		textTitle.setFontScale(scaleW, scaleH);
		textFull.setFontScale(scaleW, scaleH);
	}
	
	public void setValueBonus(int v) 	{
		int value = (v * 10)/10;
		
//		if(decimals) {
//			String val = Integer.toString(value/100) + "." + (Integer.toString(value / 10 % 10)) + (Integer.toString((int)value % 10));
//			if(game.controller.game.context.bonusCashSpins <= 100){
//				text.setText("Smooth 6\n" + val);
//			} else if(game.controller.game.context.bonusCashSpins <= 200){
//				text.setText("Fortune 8\n" + val);
//			} else {
//				text.setText("Eternality 9\n" + val);
//			}
//		}
//		else {
//			if(game.controller.game.context.bonusCashSpins <= 100){
//				text.setText("Smooth 6\n" + Integer.toString(value));
//			} else if(game.controller.game.context.bonusCashSpins <= 200){
//				text.setText("Fortune 8\n" + Integer.toString(value));
//			} else {
//				text.setText("Eternality 9\n" + Integer.toString(value));
//			}
////			text.setText("+" + Integer.toString(value));
//		}
	}
	
	/**
	 * Set field format
	 * @param decimals - format as decimal if true
	 */
	public void setFormat(boolean decimals) {
		this.decimals = decimals;
	}
	
	public boolean getFormat() {
		return decimals;
	}
	
	
	@Override
	public void draw(Batch batch, float alpha) {
		
//		textFull.draw(batch, alpha);
		
		if(this.align == Align.center){
			glyphLayout.setText(this.font, textFullstr);
			
			float offsetX = 0;
			offsetX = glyphLayout.width;
	        
			textTitle.setPosition(textFull.getX(this.align) - offsetX /2, coordintes[1]);
		} else if(this.align == Align.right){
			glyphLayout.setText(this.font, textFullstr);
			
			float offsetX = 0;
			offsetX = glyphLayout.width;
			textTitle.setPosition(textFull.getX(this.align) - offsetX, coordintes[1]);
		} else {
			textTitle.setPosition(textFull.getX(this.align), coordintes[1]);
		}
		
		textTitle.draw(batch, alpha);
		
		
        glyphLayout.setText(this.font, text);
        float offsetX = 0;//glyphLayout.width * 960/540f;
        
        offsetX = glyphLayout.width;
        
        float label2X = textTitle.getX() /*+ glyphLayout.width*/ + offsetX + 0f; // 20f is the spacing between labels
        
//        if(this.align == Align.center){
//        	Gdx.app.debug("Field", "textFull.getX(this.align) = " + textFull.getX(this.align));
//        	Gdx.app.debug("Field", "textTitle.getX() = " + textTitle.getX());
//        	Gdx.app.debug("Field", "glyphLayout.width = " + glyphLayout.width);
//        }
        textDigits.getStyle().font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textDigits.setPosition(label2X, coordintes[1]);
		textDigits.draw(batch, alpha);
	}
}
