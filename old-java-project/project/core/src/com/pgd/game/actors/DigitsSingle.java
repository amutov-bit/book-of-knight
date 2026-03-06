package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

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
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.base.AbstractController.Event;


public class DigitsSingle extends Group {
	
	BookOfKnight game;
	
	private String name = "";
	public List<Image> digits = new ArrayList<Image>();

	private TextureAtlas texture;
	private boolean hasDot = false;
	private boolean hasComma = false;
	
	private int index = -1;

	public DigitsSingle(BookOfKnight game, String name, boolean hasDot, TextureAtlas texture) {
		this.game = game;
		this.name = name;
		this.hasDot = hasDot;
		this.hasComma = hasDot;
		
		this.texture = texture;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
		

		this.texture.findRegion(name+"0").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"0"))));
		addActor(digits.get(0));
		digits.get(0).setVisible(false);

		this.texture.findRegion(name+"1").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"1"))));
		addActor(digits.get(1));
		digits.get(1).setVisible(false);
		
		this.texture.findRegion(name+"2").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"2"))));
		addActor(digits.get(2));
		digits.get(2).setVisible(false);
		
		this.texture.findRegion(name+"3").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"3"))));
		addActor(digits.get(3));
		digits.get(3).setVisible(false);

		this.texture.findRegion(name+"4").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"4"))));
		addActor(digits.get(4));
		digits.get(4).setVisible(false);
		
		this.texture.findRegion(name+"5").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"5"))));
		addActor(digits.get(5));
		digits.get(5).setVisible(false);
		
		this.texture.findRegion(name+"6").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"6"))));
		addActor(digits.get(6));
		digits.get(6).setVisible(false);
		
		this.texture.findRegion(name+"7").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"7"))));
		addActor(digits.get(7));
		digits.get(7).setVisible(false);
		
		this.texture.findRegion(name+"8").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"8"))));
		addActor(digits.get(8));
		digits.get(8).setVisible(false);
		
		this.texture.findRegion(name+"9").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		digits.add(new Image(new Sprite(texture.findRegion(name+"9"))));
		addActor(digits.get(9));
		digits.get(9).setVisible(false);
		
		if(hasDot){
			this.texture.findRegion(name+"_dot").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			digits.add(new Image(new Sprite(texture.findRegion(name+"_dot"))));
			addActor(digits.get(10));
			digits.get(10).setVisible(false);
		}
		if(hasComma){
			this.texture.findRegion(name+"_comma").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			digits.add(new Image(new Sprite(texture.findRegion(name+"_comma"))));
			addActor(digits.get(11));
			digits.get(11).setVisible(false);
		}
		
		Gdx.app.debug("DigitsSigle", "digits.get(9).getWidth() = " + digits.get(9).getWidth());
	}
	
	public void setElement(int index, float x, float y){
//		Gdx.app.debug("Digits Single", index + " :: x = " + x + " y = " + y);
		digits.get(index).setPosition(x, y);
		digits.get(index).setVisible(true);
		
		this.index = index;
	}
	
    @Override
    public void draw(Batch batch, float alpha){
    	for(int i = 0; i < digits.size();i++){
    		if(this.index == i){
    			digits.get(index).draw(batch, alpha);
    		}
    	}
    }
    
	public int getW(){
		return (int) digits.get(9).getWidth();
	}

	public int getH(){
		return (int) digits.get(9).getHeight();
	}
	
	public void hideAll(){
		for (Image image : digits) {
			image.setVisible(false);
			index = -1;
		}
	}
	
}
