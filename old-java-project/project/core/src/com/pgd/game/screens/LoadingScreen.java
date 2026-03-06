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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pgd.game.BookOfKnight;

public class LoadingScreen extends AbstractScreen {
	
	private Texture splashImage;
	
	private float sliderPosX = (1170 - 612)/2;
	private float sliderPosY = 0;
	private float sliderProgressX = 0;
	
	private int currentFile;
	public LoadingScreen(BookOfKnight game) {
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
        			game.spineLoading.loadAssetsSpine(0);
        			currentFile++;
        		break;
        		case 1:
        			game.spineLoading.loadAssetsSpine(1);
        			currentFile++;
        			break;
        		case 2:
        			game.spineLoading.commitAssetsSpine();
        			currentFile++;
        		break;
        		case 3:
        			game.setScreen(new WaitingScreen(game));
        		break;
        		default:
        			break;
        	}
        }
        
//        if(currentFile > 0)
//        {
//        	stage.getBatch().begin();
//        	stage.getBatch().draw(splashImage, 0, 0);
//        	stage.getBatch().end();
//        }
	}
	
	@Override
	public void show() {
		Gdx.app.debug("Draw()", "game.ondemandAssetManager.update()" + game.ondemandAssetManager.update());
//		game.ondemandAssetManager.load("splash.jpg", Texture.class);
//		game.ondemandAssetManager.load("logo.png", Texture.class);

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
