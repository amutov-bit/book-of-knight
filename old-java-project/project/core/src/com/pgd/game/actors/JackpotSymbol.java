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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;



public class JackpotSymbol extends Group {
	
	BookOfKnight game;

	private int positon = 0;
	
	
	private List<Image> jackpotSymbol = new ArrayList<Image>();
	
	public JackpotSymbol(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
	}
	
	public void loadAssets() {
	
	}
	
	public void commitAssets() {

		
		game.textures.getInterfaceAtlas().findRegion("shield_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("shield_blur"))));
		jackpotSymbol.get(0).setVisible(false);
		addActor(jackpotSymbol.get(0));
		
		game.textures.getInterfaceAtlas().findRegion("mini_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mini_blur"))));
		jackpotSymbol.get(1).setVisible(false);
		addActor(jackpotSymbol.get(1));

		game.textures.getInterfaceAtlas().findRegion("major_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("major_blur"))));
		jackpotSymbol.get(2).setVisible(false);
		addActor(jackpotSymbol.get(2));

		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		jackpotSymbol.add(new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur"))));
		jackpotSymbol.get(3).setVisible(false);
		addActor(jackpotSymbol.get(3));
	}
	
	public void showSymbol(int symbol){
	
		for(int i = 0; i < jackpotSymbol.size(); i ++){
			jackpotSymbol.get(i).setVisible(false);
		}
		

//		int currentSym = symbol - 10;
		
		int currentSym = symbol - 10;
		
		float offsetX = 0;
		float offsetY = 0;
		
		Gdx.app.debug("JAckpotSymbols", "symbol = " + symbol);
//		if(symbol == 10){
//			offsetX = -11f;
//			offsetY = -57f;
//		}
		
		jackpotSymbol.get(currentSym).setPosition(offsetX, offsetY);
		
		jackpotSymbol.get(currentSym).setVisible(true);
	}

}
	
