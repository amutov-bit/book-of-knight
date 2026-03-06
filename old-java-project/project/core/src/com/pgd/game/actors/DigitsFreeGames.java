package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;
import com.pgd.game.base.AbstractController.Event;


public class DigitsFreeGames extends Group {
	
	BookOfKnight game;
	
	public List<Image> digits = new ArrayList<Image>();

	public DigitsFreeGames(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
		

		
		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg0"))));
		addActor(digits.get(0));
		digits.get(0).setVisible(false);

		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg1"))));
		addActor(digits.get(1));
		digits.get(1).setVisible(false);
		
		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg2"))));
		addActor(digits.get(2));
		digits.get(2).setVisible(false);
		
		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg3"))));
		addActor(digits.get(3));
		digits.get(3).setVisible(false);

		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg4"))));
		addActor(digits.get(4));
		digits.get(4).setVisible(false);
		
		digits.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("digit_fg5"))));
		addActor(digits.get(5));
		digits.get(5).setVisible(false);
//		
//		digits.add(new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("big_win_digit6"))));
//		addActor(digits.get(6));
//		digits.get(6).setVisible(false);
//		
//		digits.add(new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("big_win_digit7"))));
//		addActor(digits.get(7));
//		digits.get(7).setVisible(false);
//		
//		digits.add(new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("big_win_digit8"))));
//		addActor(digits.get(8));
//		digits.get(8).setVisible(false);
//		
//		digits.add(new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("big_win_digit9"))));
//		addActor(digits.get(9));
//		digits.get(9).setVisible(false);
//		
//		digits.add(new Image(new Sprite(game.textures.getInterfaceCommon().findRegion("big_win_digit_d"))));
//		addActor(digits.get(10));
//		digits.get(10).setVisible(false);
		
	}
	
	public void setElement(int index, float x, float y){
//		Gdx.app.debug("Digits Single", index + " :: x = " + x + " y = " + y);
		digits.get(index).setPosition(x, y);
		digits.get(index).setVisible(true);
	}
	
	public void hideAll(){
		for (Image image : digits) {
			image.setVisible(false);
		}
	}
	
}
