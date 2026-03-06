package com.pgd.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.actors.Button;
import com.pgd.game.base.IButtonCallback;

public class InsertCredits extends Group {

	@SuppressWarnings("unused")
	private BookOfKnight game;
//	private Label demo;
	private Label error, title;

	private Image bg;
	private Button reload;
	
	private boolean isPressed = false;
	
	public InsertCredits (BookOfKnight game) {
		
		this.game = game;
		
//		demo = new Label("Demo", new LabelStyle(game.fonts.amita40, Color.WHITE));
//		demo.setPosition(game.VIRTUAL_WIDTH - 92 - 20, 0);
//		demo.setVisible(game.DEMO_MODE);
		

		
//		addActor(demo);
	
		
	}
	
	public void setIsPressed(){
		isPressed = false;
	}

	public boolean isPressed(){
		return isPressed;
	}
	
	public void commitAssets(){

		Texture texture = game.ondemandAssetManager.getAssetManager().get("bg_menu.png", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(texture);
		bg.setPosition(0, -600);
		bg.setScale(2f, 5f);
		addActor(bg);

		title = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, Color.YELLOW));
		title.setVisible(true);
		title.setFontScale(1f);
		title.setText(game.gameTxt.insertCreditTitle);
		title.setAlignment(Align.center);
		title.setBounds(0,0 + 180,1920, 1080);
		addActor(title);
		
		
		error = new Label("ERROR: ", new LabelStyle(game.fonts.font46px, Color.WHITE));
		error.setVisible(true);
		error.setFontScale(1f);
		error.setText(game.gameTxt.insertCreditTxt);
		error.setAlignment(Align.center);
		error.setBounds(0,0 + 180 - 120,1920, 1080);
		addActor(error);

		// Add input listener to the image
		bg.addListener(new InputListener() {
		    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		    	isPressed = true;
		        return true; // return true to indicate that the event was handled
		    }
		});
		// Add input listener to the image
		title.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				isPressed = true;
				return true; // return true to indicate that the event was handled
			}
		});
		// Add input listener to the image
		error.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				isPressed = true;
				return true; // return true to indicate that the event was handled
			}
		});
		
		
	}
	
}
