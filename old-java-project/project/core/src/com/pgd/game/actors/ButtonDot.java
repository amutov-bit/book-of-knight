package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;

public class ButtonDot extends Actor {
	
	BookOfKnight game;
	
	TextureAtlas textureAtlas;
	private Array<AtlasRegion> regions = new Array<AtlasRegion>();	
	private int currentAnimFrame;
	private int currentFrame;
	private int totalFrames;
	private int toggledFrame;
	private int disabledFrame;
	private int hoverFrame;
	private String buttonName;
	
	private enum ButtonState {NORMAL, PRESSED, TRANSITION, TOGGLED, DISABLED};
	private ButtonState state;
	private boolean signalPress = false;
	private boolean signalRelease = false;
	private boolean hover = false;
	private boolean signalToggle = false;
//	private boolean toggled = false;
	private boolean enabled;
	private IButtonCallback callback = null;
	
	private boolean hoverEnable = false;
	
	private boolean animate = false;

	private float timeElapsed, lastTimeElapsed;
	
	/**
	 * Constructor
	 */
	public ButtonDot(BookOfKnight game, int x, int y, TextureAtlas textureAtlas, String buttonName) {
		this.game = game;
		this.textureAtlas = textureAtlas;
		this.buttonName = buttonName;
		currentAnimFrame = 0;
		currentFrame = 0;
		toggledFrame = 0;
		totalFrames = 0;
		
		animate = false;
		
		state = ButtonState.NORMAL;
//		toggled = false;
		enabled = true;
		
		setPosition(x, y);
		
		Gdx.app.debug("Button", "instance created");
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
		
		regions = textureAtlas.findRegions(buttonName + "_inactive");
		totalFrames = regions.size - 1;
		
		regions.add(textureAtlas.findRegion(buttonName + "_acitve"));
		toggledFrame = regions.size - 1;		
		
		setWidth(regions.get(0).getRegionWidth());
		setHeight(regions.get(0).getRegionHeight());
		setBounds(getX(),  getY(), getWidth(), getHeight());
		
		addListener(new InputListener() {
		    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		    	if(enabled)
		    	{
		    		signalPress = true;
		    		if(callback!=null)
		    			callback.onPress();
			    	
//			    	Gdx.app.debug("Button", "pressed");
//			    	game.sounds.play(SoundTrack.BUTTONS, false);
			    	return true;
		    	}
		    	return false;
		    }
		    
		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//		    	state = ButtonState.NORMAL;
		    	if(enabled)
		    	{
			    	signalRelease = true;
			    	if(callback!=null)
			    		callback.onRelease();
//			    	Gdx.app.debug("Button", "released");
		    	}
		 	}
		    
		    public  void    enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
                if(hoverEnable)	hover = true;
            }
		    
		    public  void    exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                hover = false;
            }

		});

	}
	
	@Override
    public void act(float delta) {
			
		timeElapsed += delta;
		
		if( timeElapsed - lastTimeElapsed > 0.032f) { // @ToDo: control frame rate from stage, not actors

			lastTimeElapsed = timeElapsed;
			
			switch(state) {
				case NORMAL: 
					if(animate){
						if(++currentAnimFrame > 30) currentAnimFrame = 0;
					} else {
						 currentAnimFrame = 0;
					}
						if(signalPress) {
						state = ButtonState.TRANSITION;
						signalPress = false;
						currentAnimFrame = 0;
					}
						else if(signalToggle) {
							signalToggle = false;
							signalPress = false;
							state = ButtonState.TOGGLED;
							currentAnimFrame= 0;
						}
					currentFrame = currentAnimFrame;
					
					if(animate)
					{
						if(animate && currentFrame > 15)		currentFrame = hoverFrame;
						else									currentFrame = 0;
					}

					if(hover)	currentFrame = hoverFrame;
					
					
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
					break;
					
				case DISABLED:
					if(enabled)
						state = ButtonState.NORMAL;
					
					signalRelease = false;
					signalPress = false;
					currentFrame = disabledFrame;
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
    	regions.get(currentFrame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		batch.draw(regions.get(currentFrame).getTexture(),
				getX(),
    			getY(), 
			   (float)regions.get(currentFrame).getRegionWidth()/2,
			   (float)regions.get(currentFrame).getRegionHeight()/2,
			   (float)regions.get(currentFrame).getRegionWidth(),
			   (float)regions.get(currentFrame).getRegionHeight(),
			   this.getScaleX(),
			   this.getScaleY(),
			   regions.get(currentFrame).rotate ? -90f : 0f,
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
    
    public void animate(boolean anim)
    {
    	animate = anim;
    }
    /*
	* <cod mode bb>
	*/
    public void reset() {
    	state = ButtonState.NORMAL;
    	signalPress = false;
    	signalToggle = false;
    	enabled = true;
    	currentAnimFrame = 0;
    }
    
    public void activate() {
    	signalPress = true;
    	signalRelease = true;  
    }
    
    public void toggled() {
//    	toggled = true;
    	signalToggle = true;
    }
    
    public void enable() {
//    	toggled = false;
    }
    public void disable() {
    	state = ButtonState.DISABLED;
    	enabled = false;
    	currentFrame = disabledFrame;
    }
    public boolean isEnable() {
    	return enabled;
    }
    
    public void setHover(boolean hov)
    {
    	hoverEnable = hov;
    }
}
