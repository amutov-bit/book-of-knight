package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.Controller.State;
import com.pgd.game.BookOfKnight;
import com.pgd.game.base.IButtonCallback;

public class ButtonOld extends Actor {
	
	BookOfKnight game;
	
	TextureAtlas textureAtlas;
	private Array<AtlasRegion> regions = new Array<AtlasRegion>();	
	private int currentAnimFrame;
	private int currentFrame;
	private int totalFrames;
	private int toggledFrame;
//	private int hoverFrame;
	private String buttonName;
	
	private enum ButtonState {NORMAL, PRESSED, TRANSITION, TOGGLED, SELECTED};
	ButtonState state;
	boolean signalPress = false;
	boolean signalRelease = false;
	boolean signalToggle = false;
//	private boolean toggled = false;
	private IButtonCallback callback = null;

	private float timeElapsed, lastTimeElapsed;
	
	private String bgName;
	private Image bgButton;
	int bgX = 0, bgY = 0;
	
	float scaleX = 1f;
	float scaleY = 1f;
	
	float x = 0;
	float y = 0;
	
	/**
	 * Constructor
	 */
	public ButtonOld(BookOfKnight game, int x, int y, TextureAtlas textureAtlas, String buttonName) {
		this.game = game;
		this.textureAtlas = textureAtlas;
		this.buttonName = buttonName;
		currentAnimFrame = 0;
		currentFrame = 0;
		toggledFrame = 0;
		totalFrames = 0;
		
		state = ButtonState.NORMAL;
//		toggled = false;
		
		setPosition(x, y);
		
		Gdx.app.debug("Button", "instance created");
	}
	
	public ButtonOld(BookOfKnight game, int x, int y, TextureAtlas textureAtlas, String buttonName, String bgname, int bgx, int bgy) {
		this.game = game;
		this.textureAtlas = textureAtlas;
		this.buttonName = buttonName;
		currentAnimFrame = 0;
		currentFrame = 0;
		toggledFrame = 0;
		totalFrames = 0;
		
		state = ButtonState.NORMAL;
//		toggled = false;
		this.bgName = bgname;
		
		this.bgX = bgx;
		this.bgY = bgy;

		setPosition(x, y);
		
//		loadAssets();
		
		Gdx.app.debug("Button", "instance created");
	}
	
	public void loadAssets() {
//		if(bgName != null)
//			game.manager.load(bgName, Texture.class);
	}
	
	public void setScaleLand(float xx){
		
//		scaleX = xx;
//		scaleY = yy;
		
			setBounds(getX(),
					getY(),
					regions.get(0).getRegionWidth() * xx,
					regions.get(0).getRegionHeight() * xx);
	}
	
	public void setPositionPort(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setScalePort(float xx, float yy){
	
		scaleX = xx;
		scaleY = yy;
		
			setBounds(getX(),
					  getY(),
					  regions.get(0).getRegionWidth(),
					  regions.get(0).getRegionHeight());
	}
	
	public void commitAssets() {
		
		regions = textureAtlas.findRegions(buttonName);
		totalFrames = regions.size - 1;
		
//		regions.add(textureAtlas.findRegion(buttonName + "_stop"));
//		toggledFrame = regions.size - 1;
		
		if(bgName != null)
		{
			bgButton = new Image(new Sprite(textureAtlas.findRegion(bgName)));
			bgButton.setPosition(bgX, bgY);
		}
		
		addListener(new InputListener() {
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	    		signalPress = true;
	    		if(callback!=null)
	    			callback.onPress();
		    	
		    	Gdx.app.debug("Button", "pressed");
		        return true;
		    }
		    
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//		    	state = ButtonState.NORMAL;
		    	signalRelease = true;
		    	if(callback!=null)
		    		callback.onRelease();
		    	Gdx.app.debug("Button", "released");
		 	}

		});
			setBounds(getX(),
					  getY(),
					  regions.get(0).getRegionWidth(),
					  regions.get(0).getRegionHeight());
	}
	
	@Override
    public void act(float delta) {
			
		timeElapsed += delta;
		
		if( timeElapsed - lastTimeElapsed > (float) 1/25) { // @ToDo: control frame rate from stage, not actors

			lastTimeElapsed = timeElapsed;
			
			switch(state) {
				case NORMAL: 
						currentAnimFrame = 0;
						if(signalPress) {
							state = ButtonState.TRANSITION;
							signalPress = false;
						}
						else if(signalToggle) {
							signalToggle = false;
							signalPress = false;
							state = ButtonState.TOGGLED;
						}
						currentFrame = currentAnimFrame;
					break;
					
				case TRANSITION: 
					if(currentAnimFrame < totalFrames) currentAnimFrame++; 
					else state = ButtonState.PRESSED; 
					currentFrame = currentAnimFrame;
					break;
					
				case PRESSED: 
					if(signalToggle) {
						signalToggle = false;
						signalPress = false;
						state = ButtonState.TOGGLED;
					}
					else if(signalRelease) {
						state = ButtonState.NORMAL;
						signalPress = false;
						if(buttonName == "button_start" && game.controller.getState() == State.START_SPIN){
							setVisible(false);
//							game.menu.stopButton.setVisible(true);
						}
						if(buttonName == "button_stop"){
							setVisible(false);
							game.menu.startButton.setVisible(true);
						}
						
						
					}
					currentFrame = currentAnimFrame;
					break;
					
				case TOGGLED:
					if(signalPress) {
						state = ButtonState.NORMAL;
						currentAnimFrame = 0;
						signalPress = false;
					}
					currentFrame = toggledFrame;
				case SELECTED:
					currentFrame = totalFrames;
				break;
				default: 
					currentAnimFrame = 0;
					currentFrame = toggledFrame;
					break;
			}
		}
		
	}
	
    @Override
    public void draw(Batch batch, float alpha){
    	if(bgName != null)
    		bgButton.draw(batch, alpha);

	    	batch.draw(regions.get(currentFrame).getTexture(),
					this.getX(),
					this.getY(), 
	    			regions.get(currentFrame).getRegionWidth()/2, 
	    			regions.get(currentFrame).getRegionHeight()/2,
					regions.get(currentFrame).getRegionWidth(), 
					regions.get(currentFrame).getRegionHeight(),
					this.getScaleX(),
					this.getScaleY(),
					0f,
	    			regions.get(currentFrame).getRegionX(), 
	    			regions.get(currentFrame).getRegionY(), 
	    			regions.get(currentFrame).getRegionWidth(), 
	    			regions.get(currentFrame).getRegionHeight(),
	    			false,
	    			false);
    }	
    
    public void registerCallback(IButtonCallback callback) {
    	this.callback = callback;
    }
	
    public void unregisterCallback() {
    	this.callback = null;
    }    
    
    public void reset() {
    	state = ButtonState.NORMAL;
    	signalPress = false;
//    	toggled = false;
    	currentAnimFrame = 0;
    }
    
    public void activate() {
    	signalPress = true;
    	signalRelease = true;
    }
    
    public void toggled() {
//    	toggled = true;
//    	signalToggle = true;
    }
    
    public void enable() {
//    	toggled = false;
    }
    
	public void setSelected(boolean selected) {
		if (selected) {
			state = ButtonState.SELECTED;
		} else {
			state = ButtonState.NORMAL;
			signalPress = false;
			// toggled = false;
			currentAnimFrame = 0;
		}
	}

	// only in new games
	public boolean isEnable() {
		return true;
	}
}
