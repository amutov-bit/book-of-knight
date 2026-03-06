package com.pgd.game.screens;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pgd.game.BookOfKnight;
import com.pgd.game.base.Field;

public class LockedScreen extends AbstractScreen {
	
	private Texture splashImage;
	
	private Image frame;
	
	private Label text;
	
	private float sliderPosX = (1170 - 612)/2;
	private float sliderPosY = 0;
	private float sliderProgressX = 0;
	
	private int currentFile;
	public LockedScreen(BookOfKnight game) {
		super(game);
		currentFile = 0;
	}

	@Override
	public void draw() {
		
		game.ondemandAssetManager.update();
		Gdx.app.debug("Draw()", "game.ondemandAssetManager.update()" + game.ondemandAssetManager.update());
        if (game.ondemandAssetManager.isComplete()) {
        	
			switch(currentFile){
        		case 0:
        			splashImage = game.ondemandAssetManager.getAssetManager().get("bg.jpg", Texture.class);
        			
        			frame = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("podlojka")));
        			frame.setPosition(239, 720 - 18 - 644);
        			
        			text = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(255f/255f, 253f/255f, 196f/255f, 255f/255f)));
        			text.setPosition(285, 720 - 530 - 64 + 240);
        			
        			text.setText("Unfortunately your account has been locked.\n\n" +
        						 "Try to close all sessions on this game!\n\n" + 
        						 "If your account is still locked.\n\n Please contact customer support.");
        			currentFile++;
        		break;
        		case 1:
        		break;
        		default:
        			break;
        	}
        }
        
        if(currentFile > 0)
        {
        	stage.getBatch().begin();
        	stage.getBatch().draw(splashImage, 0, 0);
        	frame.draw(stage.getBatch(), 1f);
        	text.draw(stage.getBatch(), 1f);
//        	statusField.draw(stage.getBatch(), 1f);
        	stage.getBatch().end();
        }
	}
	
	@Override
	public void show() {
		Gdx.app.debug("Draw()", "game.ondemandAssetManager.update()" + game.ondemandAssetManager.update());

	    game.gsLink.getBalance();
	    
	    Gdx.input.setInputProcessor(stage);
	}


	@Override
	public void dispose() {
	    stage.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

}
