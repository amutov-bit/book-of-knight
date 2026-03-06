package com.pgd.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.actors.Button;

public class ControlField extends Group {

	private BookOfKnight game;
	private Label text;
	private Image image;
	private String imagePath;
	private Button plusButton, minusButton;
	
	boolean buttons;
//	private Array<AtlasRegion> regions = new Array<AtlasRegion>();	
	RegionsMenu regions;
	
	String leftButton;
	String rightButton;
	
//	int coordintes[] = new int[2];
	
	public ControlField(BookOfKnight game, String imagePath) {
		this.game = game;
		if(imagePath != null) {
			this.imagePath = imagePath;
			game.manager.load(imagePath, Texture.class);
		}
		text = new Label(" ", new LabelStyle(game.fonts.font36px, null));
		plusButton = new Button(game, 0, 0, game.textures.getInterfaceAtlas(), "button_plus");
		plusButton.setHover(true);
		minusButton = new Button(game, 0, 0, game.textures.getInterfaceAtlas(), "button_minus");
		minusButton.setHover(true);
	}
	
	public ControlField(BookOfKnight game, String imagePath, String leftButton, String rightButton) {
		this.game = game;
		if(imagePath != null) {
			this.imagePath = imagePath;
//			game.manager.load(imagePath, Texture.class);
			regions = new RegionsMenu(this.game, imagePath);
			
		}
		
		this.leftButton = leftButton;
		this.rightButton = rightButton;
		
		buttons = false;
		
		if(leftButton != null && rightButton != null)
		{
			buttons = true;
		}
		
		
	}	
	
	public void commitAssets() {
		
		minusButton = new Button(game, 0, 0, game.textures.getInterfaceAtlas(), leftButton);
		minusButton.setHover(true);
		plusButton = new Button(game, 0, 0, game.textures.getInterfaceAtlas(), rightButton);
		plusButton.setHover(true);
		
		text = new Label(" ", new LabelStyle(game.fonts.font36px, null));
		
		if(this.imagePath != null) {
//			this.image = new Image(game.manager.get(imagePath, Texture.class));	 
//			regions = game.textures.interfaceAtlas.findRegions(imagePath);
			regions.commitAssets();
			
//			Gdx.app.debug("ControlField", "loaded imagePath:" + imagePath + " : size = ");
			
//			this.image = new Image(regions.get(0).getTexture());
		}
		
		if(buttons)
		{
			plusButton.commitAssets();
			minusButton.commitAssets();
		}
		
		if(this.imagePath != null) {
			addActor(regions);
		}
		
		if(buttons)
		{
			addActor(plusButton);
			addActor(minusButton);
		}
		addActor(text);
		text.setVisible(false);
	}
	
	public void setPosition(int x, int y, int textOffsetX, int textOffsetY)	{
		if(this.imagePath != null) {
//			image.setPosition(x, y);
			regions.setPosition(x,y);
			text.setWidth(regions.getWidth() - textOffsetX*2);
			text.setHeight(regions.getHeight());
		}
		text.setPosition(x + textOffsetX, y + textOffsetY);
		text.setAlignment(Align.center);
		minusButton.setPosition(x, y + 16);
		plusButton.setPosition(x + 160, y + 16);
	}

	public void setPosition(int imgX, int imgY, int leftBtnX, int leftBtnY, int rightBtnX, int rightBtnY, int textX, int textY, int textW)	{
//		image.setPosition(imgX, imgY);
		if(this.imagePath != null) {
			regions.setPosition(imgX,imgY);
		}
		text.setPosition(textX, textY);
		text.setAlignment(Align.center);
		text.setWidth(textW);
		minusButton.setPosition(leftBtnX, leftBtnY);
		plusButton.setPosition(rightBtnX, rightBtnY);
	}	
	
	public void setText(String string) 	{
		if(text != null)
			text.setText(string);
	}
	
	/**
	 * Register callbacks for both buttons
	 * @param plus
	 * @param minus
	 */
	public void registerCallbacks(IButtonCallback plus, IButtonCallback minus) {
		plusButton.registerCallback(plus);
		minusButton.registerCallback(minus);
	}
	
	/**
	 * Enable buttons
	 */
	public void enableButtons() {
		plusButton.reset();
		minusButton.reset();
	}
	
	/**
	 * Disable buttons
	 */
	public void disableButtons() {
		minusButton.disable();
		plusButton.disable();
	}
	
//	@Override
//	public void draw(Batch batch, float alpha) {
////		if(isVisible()) {
//			minusButton.draw(batch, alpha);
//			plusButton.draw(batch, alpha);
//			text.draw(batch, alpha);
//			
//			batch.draw(regions.get(0).getTexture(), 
//					coordintes[0], coordintes[1], 
//					regions.get(0).getRegionX(),regions.get(0).getRegionY(),
//					regions.get(0).getRegionWidth(), regions.get(0).getRegionHeight());
//			
////		}
//	}
	
}
