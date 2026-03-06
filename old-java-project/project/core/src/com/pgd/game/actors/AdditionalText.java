package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class AdditionalText extends Group {
	
	BookOfKnight game;


	private Image fgText, multText, podlojka, x2, x5, x10, x15, x25;
	
	private Digits fgDigits;
	

	public AdditionalText(BookOfKnight game) {
		this.game = game;
	}
	
	public void loadAssets() {
		
	}
	
	
	public void commitAssets() {
		
		game.textures.getInterfaceAtlas().findRegion("fg_freegames").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fgText = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_freegames")));
		fgText.setPosition(33, 704 - 38 - 70 + 35);
		addActor(fgText);

		game.textures.getInterfaceAtlas().findRegion("fg_multiplier").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		multText = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_multiplier")));
		multText.setPosition(944 - 50, 704 - 38 - 70 + 35);
		addActor(multText);

		game.textures.getInterfaceAtlas().findRegion("podlojka_fg_digits").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		podlojka = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("podlojka_fg_digits")));
		podlojka.setPosition(82 - 60, 704 - 35 - 70 - 35);
		addActor(podlojka);

		game.textures.getInterfaceAtlas().findRegion("fg_mult_x2").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		x2 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_mult_x2")));
		x2.setPosition(1146 - 60, 704 - 35 - 70 - 35);
		addActor(x2);

		game.textures.getInterfaceAtlas().findRegion("fg_mult_x5").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		x5 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_mult_x5")));
		x5.setPosition(1146 - 60, 704 - 35 - 70 - 35);
		addActor(x5);

		game.textures.getInterfaceAtlas().findRegion("fg_mult_x10").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		x10 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_mult_x10")));
		x10.setPosition(1146 - 60, 704 - 35 - 70 - 35);
		addActor(x10);

		game.textures.getInterfaceAtlas().findRegion("fg_mult_x15").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		x15 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_mult_x15")));
		x15.setPosition(1146 - 60, 704 - 35 - 70 - 35);
		addActor(x15);

		game.textures.getInterfaceAtlas().findRegion("fg_mult_x25").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		x25 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("fg_mult_x25")));
		x25.setPosition(1146 - 60, 704 - 35 - 70 - 35);
		addActor(x25);
		
		 x2.setVisible(false);
		 x5.setVisible(false);
		 x10.setVisible(false);
		 x15.setVisible(false);
		 x25.setVisible(false);
	}
	
	public void setMultiplier(int mult){
		switch(mult){
			case 2:
				x2.setVisible(true);
				x5.setVisible(false);
				x10.setVisible(false);
				x15.setVisible(false);
				x25.setVisible(false);
				break;
			case 5:
				x2.setVisible(false);
				x5.setVisible(true);
				x10.setVisible(false);
				x15.setVisible(false);
				x25.setVisible(false);
				break;
			case 10:
				x2.setVisible(false);
				x5.setVisible(false);
				x10.setVisible(true);
				x15.setVisible(false);
				x25.setVisible(false);
				break;
			case 15:
				x2.setVisible(false);
				x5.setVisible(false);
				x10.setVisible(false);
				x15.setVisible(true);
				x25.setVisible(false);
				break;
			case 25:
				x2.setVisible(false);
				x5.setVisible(false);
				x10.setVisible(false);
				x15.setVisible(false);
				x25.setVisible(true);
				break;
			default:
//				multText.setVisible(false);
				 x2.setVisible(false);
				 x5.setVisible(false);
				 x10.setVisible(false);
				 x15.setVisible(false);
				 x25.setVisible(false);
			  break;
		}
	}
	
	public void setFreeGames(int num){
		fgDigits.setWin(num);
	}
	
	public void reset(){
		 x2.setVisible(false);
		 x5.setVisible(false);
		 x10.setVisible(false);
		 x15.setVisible(false);
		 x25.setVisible(false);
	}
}
