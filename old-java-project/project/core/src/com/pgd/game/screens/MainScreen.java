package com.pgd.game.screens;

import java.io.IOException;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Controller.State;
import com.pgd.game.base.AbstractController.Event;
import com.pgd.game.Sounds;
import com.pgd.game.Sounds.SoundTrack;

public class MainScreen extends AbstractScreen {
	
	
	private float elapsed;
	private float duration;
	private float intensity; 
	private boolean startAnim;
	protected Random rand; // RNG
	private float baseX = 0f;
	private float baseY = 0f;
	private boolean isStartScreen; 

	private long currentTime = System.currentTimeMillis();

	public MainScreen(BookOfKnight game) {
		super(game);
		
		rand = new Random();

		baseX = camera.position.x;
		baseY = camera.position.y;
		
		this.startAnim = false;
		this.isStartScreen = true;

	}

	@Override
	public void show() {
		
		stage.addActor(game.background);
		stage.addActor(game.roundId);
		stage.addActor(game.reels);
		stage.addActor(game.overlay);
		stage.addActor(game.menu);
		stage.addActor(game.autoPlayMenu);	
		stage.addActor(game.betMenu);	
		stage.addActor(game.buyBonusMenu);
		stage.addActor(game.overlay.bonusConfirm);

		stage.addActor(game.helpScreen);	
		stage.addActor(game.titleLabel);
		stage.addActor(game.messages);
		
		stage.addActor(game.overlay.menuTurboSpins);

		game.menu.disableButtons();
		
		if(!game.context.skipIntro){
			game.gameAssetsManager.groupIntro.addActor(game.gameAssetsManager.introAnim);
			stage.addActor(game.gameAssetsManager.groupIntro);
			game.gameAssetsManager.tapTo.setVisible(false);
			stage.addActor(game.gameAssetsManager.tapTo);
		} else {
			game.overlay.showAddFreeSpins();
		}
		game.menu.buyBonusButton.setVisible(game.context.hasBuyFeature);
		game.menu.lobbyButton.setVisible(game.context.hasLobbyButton);
		
		stage.addActor(game.menu.bottomMenu);
		stage.addActor(game.overlay.addFreeGame);
		stage.addActor(game.menu.lobbyButton);
		stage.addActor(game.menu.totalBetField);
		stage.addActor(game.menu.creditField);
		stage.addActor(game.menu.showJackpotWin);
		stage.addActor(game.overlay.jackpotAnimateWin);

//		try {
//			game.gameTxt.setTexts(game.languageCode);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//		}

		currentTime = System.currentTimeMillis();
		
		if(isStartScreen){
			game.menu.updateMetersStartScreen();
		}
		
	    Gdx.input.setInputProcessor(stage);
	    
	    // Setting focus on reels redirects all keyboard input to 
	    // controller as reels add controller as InputListener
	    stage.setKeyboardFocus(game.reels);
	    //game.gsLink.connect("ws://10.103.0.27:8080", 1);
	   // game.gsLink.connect("http://dev.gamingdevices.co.uk:1000/fruitymania/", game.uid);
	}
	
    public void update(float delta) {
		game.controller.update();
		game.sounds.update(delta); /* hook-up sounds actions */
        
		if(startAnim)
        {
	        update(delta, camera);
	        camera.update();
        }
		
        
        if(!game.gameAssetsManager.finishLoading){
	        game.ondemandAssetManager.update();
	        if(!game.gameAssetsManager.downloadingAssets())
	        {
				game.gameAssetsManager.finishLoading = true;
	        }
        }
	}
	
    @Override
    public void draw() {
//    	Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
//        super.render(delta); 

//        if(System.currentTimeMillis() - currentTime > 5000){
//        	currentTime = System.currentTimeMillis();
//        	game.reels.changeTitle();
//        }
        
        
//        stage.act();
//        stage.draw();
        
        // Check for touch input
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            
            if(game.gameAssetsManager.tapTo.isVisible()){
            	game.gameAssetsManager.tapTo.setVisible(false);
            	game.gameAssetsManager.introAnim.setVisible(false);
            	game.sounds.stop(SoundTrack.INTRO);
            	game.overlay.showAddFreeSpins();
            }
            
            if(game.controller.getState() == State.SHOW_MAX_WIN_REACHED){
//            	if(game.overlay.freeGamesText.animationStopped()){
            		game.controller.event = Event.START;
//            	}
            }
            
            if(game.controller.getState() == State.FREE_GAMES){
            	if(game.overlay.freeGamesText.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }

            if(game.controller.getState() == State.FREE_BOOK){
            	if(game.overlay.freeGamesBook.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }

            if(game.controller.getState() == State.FREE_GAMES_END){
            	if(game.overlay.freegamesCongrats.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }
            
            if(game.controller.getState() == State.SHOW_RESTORE_FG_END){
            	if(game.overlay.freegamesCongrats.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }

            if(game.controller.getState() == State.HOLD_AND_WIN){
            	if(game.overlay.holdAndWinText.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }

            if(game.controller.getState() == State.HOLD_AND_WIN_END){
            	if(game.overlay.freegamesCongrats.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }

            if(game.controller.getState() == State.SHOW_RESTORE_HOLD_AND_WIN_END){
            	if(game.overlay.freegamesCongrats.animationStopped()){
            		game.controller.event = Event.START;
            	}
            }
            
//            game.gsLink.console(str);
            if(game.autoPlayMenu.isVisible()){
            	float x = game.autoPlayMenu.bg.getX();
            	float y = game.autoPlayMenu.bg.getY();
            	float w = game.autoPlayMenu.bg.getWidth();
            	float h = game.autoPlayMenu.bg.getHeight();
            	
            	boolean isBg = touchX >= x && touchX <= x + w &&
            					    touchY >= y && touchY <= y + h;
            					    
            	x = game.menu.autoButton.getX();      
            	y = game.menu.autoButton.getY();      
            	w = game.menu.autoButton.getWidth();  
            	h = game.menu.autoButton.getHeight(); 
            					    
            	boolean isButon = touchX >= x && touchX <= x + w &&
                			     touchY >= y && touchY <= y + h;

            	if(!isBg && !isButon){
            		game.autoPlayMenu.hide();
            	}
            }

            if(game.betMenu.isVisible()){
            	float x = game.betMenu.bg.getX();
            	float y = game.betMenu.bg.getY();
            	float w = game.betMenu.bg.getWidth();
            	float h = game.betMenu.bg.getHeight();
            	
            	boolean isBg = touchX >= x && touchX <= x + w &&
            			touchY >= y && touchY <= y + h;
            			
            			x = game.menu.betButton.getX();      
            			y = game.menu.betButton.getY();      
            			w = game.menu.betButton.getWidth();  
            			h = game.menu.betButton.getHeight(); 
            			
            			boolean isButon = touchX >= x && touchX <= x + w &&
            					touchY >= y && touchY <= y + h;
            					
            					if(!isBg && !isButon){
            						game.betMenu.hide();
            					}
            }

//            if(game.gameAssetsManager.menuSound.bg.isVisible()){
//            	float x = game.gameAssetsManager.menuSound.bg.getX();
//            	float y = game.gameAssetsManager.menuSound.bg.getY();
//            	float w = game.gameAssetsManager.menuSound.bg.getWidth();
//            	float h = game.gameAssetsManager.menuSound.bg.getHeight();
//            	
//            	boolean isBg = touchX >= x && touchX <= x + w &&
//            			touchY >= y && touchY <= y + h;
//            			
//            			x = game.gameAssetsManager.menuSound.bg.getX();      
//            			y = game.gameAssetsManager.menuSound.bg.getY();      
//            			w = game.gameAssetsManager.menuSound.bg.getWidth();  
//            			h = game.gameAssetsManager.menuSound.bg.getHeight(); 
//            			
//            			boolean isButon = touchX >= x && touchX <= x + w &&
//            					touchY >= y && touchY <= y + h;
//            					
//            					if(!isBg && !isButon){
//            						game.gameAssetsManager.menuSound.hide();
//            					}
//            }
        }
        
        camera.position.x = baseX;
        camera.position.y = baseY;

    }
    
    public void stop()
    {
    	  this.startAnim = false;
    }
	/**
	 * Start the screen shaking with a given power and duration
	 * @param intensity How much intensity should the shaking use.
	 * @param duration Time in milliseconds the screen should shake.
	 */
	public void shake(float intensity, float duration) {
	    this.elapsed = 0;
	    this.duration = duration / 1000f;
	    this.intensity = intensity;
	    this.startAnim = true;
	}
	 
	/**
	 * Updates the shake and the camera.
	 * This must be called prior to camera.update()
	 */
	public void update(float delta, OrthographicCamera camera) {
	 
	    // Only shake when required.
	    if(elapsed < duration) {
	 
	        // Calculate the amount of shake based on how long it has been shaking already
	        float currentPower = intensity * camera.zoom * ((duration - elapsed) / duration);
	        float x = (rand.nextFloat() - 0.5f) * 2 * currentPower;
	        float y = (rand.nextFloat() - 0.5f) * 2 * currentPower;
	        camera.translate(-x, -y);
	 
	        // Increase the elapsed time by the delta provided.
	        elapsed += delta;
	    }
	}
	
	@Override
	public void pause() {
		//game.gsLink.disconnect();
	}

	@Override
	public void resume() {
		//game.gsLink.connect("http://dev.gamingdevices.co.uk:1000/fruitymania/", game.uid);
//		game.menu.updateMeters();
	}

	@Override
	public void hide() {
		//game.gsLink.disconnect();
	}

	@Override
	public void dispose() {
		stage.dispose();
		game.sounds.dispose();
		game.gsLink.disconnect();
	}
	
	public void setPrevScreen()
	{
		isStartScreen = false;
	}
	
	public void setZorder(boolean normal){
//		if(normal){
//			game.background.setZIndex(1);
//			game.reels.setZIndex(3);
//			game.overlay.setZIndex(4);
//			game.menu.setZIndex(5);
//			game.messages.setZIndex(6);
//			game.helpScreen.setZIndex(7);
//			game.autoPlayMenu.setZIndex(8);
//		} else {
//			game.background.setZIndex(1);
//			game.overlay.setZIndex(4);
//			game.menu.setZIndex(5);
//			game.messages.setZIndex(6);
//			game.helpScreen.setZIndex(7);
//			game.reels.setZIndex(12);
//			game.autoPlayMenu.setZIndex(15);
//		}
	}
}


