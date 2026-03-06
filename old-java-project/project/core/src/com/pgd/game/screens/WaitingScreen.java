package com.pgd.game.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.pgd.game.BookOfKnight;

public class WaitingScreen extends AbstractScreen {
	


	
	public WaitingScreen(BookOfKnight game) {
		super(game);

		game.gsLink.stopLogoAnim();
		
	}

   @Override
    public void render(float delta) {
	        
	   		Gdx.gl.glClearColor(0, 0, 0, 1);
   			Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    		draw();
	        stage.act();
	        stage.draw();
	        game.gameAssetsManager.action(delta);
			game.ondemandAssetManager.update();
			
			if(game.showButtons){
		        if (Gdx.input.isTouched()) {
		            float touchX = Gdx.input.getX();
		            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
		            
		            if(game.login && !game.gameAssetsManager.menuSound.isVisible()){
		            	game.gameAssetsManager.hideWatingScreen();
		            }
		        }
			}
    }

	@Override
	public void draw() {
//        if ((game.gameAssetsManager.downloadingAssets() && game.gameAssetsManager.timeoutExpired()) || !game.gameAssetsManager.downloadingAssets()) {
		
		game.gameAssetsManager.downloadingAssets();
		
        if (game.gameAssetsManager.playButtonsPressed()){
			game.menu.updateMeters();
			game.helpScreen.gameRules.setText();		

			game.setScreen(game.MATH_TEST ? new StatsScreen(game) : game.mainScreen);
			
			
		}
	}

	
	@Override
	public void show() {
		game.gameAssetsManager.loadAssets(stage);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {

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
