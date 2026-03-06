package com.pgd.game.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds;
import com.pgd.game.base.IButtonCallback;

public class ButtonItem extends Actor {
	
	BookOfKnight game;
	
	protected Random rand; // RNG
	
	TextureAtlas textureAtlas;
	private Array<AtlasRegion> regions = new Array<AtlasRegion>();	
	private int currentAnimFrame;
	private int currentFrame;
	private int totalFrames;
	private int toggledFrame;
	private int animFrame;
	private int disabledFrame;
	private int hoverFrame;
	private String buttonName;
	
	private enum ButtonState {NORMAL, PRESSED, TRANSITION, TOGGLED, DISABLED};
	private ButtonState state;
	private boolean signalPress = false;
	private boolean signalRelease = false;
	private boolean hover = false;
	private boolean signalToggle = false;
	private boolean toggled = false;
	private boolean enabled;
	private IButtonCallback callback = null;

	private boolean hoverEnable = false;

	private float timeElapsed, lastTimeElapsed;
	
	private boolean startAnim = false;
		
	private float scaleX = 1f;
	private float scaleY = 1f;
	
	private float x = 0;
	private float y = 0;

	private Color color;
	
	private float opacity [] ={
			0f,
			20f,
			40f,
			60f,
			80f,	
			100f,	
			100f,	
			100f,	
			85f,
			55f,
			35f,
			0f,
	};
	
	private int index;
	
	/**
	 * Constructor
	 */
	public ButtonItem(BookOfKnight game, int x, int y, TextureAtlas textureAtlas, String buttonName, int index) {
		this.game = game;
		this.textureAtlas = textureAtlas;
		this.buttonName = buttonName;
		currentAnimFrame = 0;
		currentFrame = 0;
		toggledFrame = 0;
		totalFrames = 0;
		
		this.index = index;
		state = ButtonState.NORMAL;
//		toggled = false;
		enabled = true;
		
		setPosition(x, y);
		
		rand = new Random();
		
		Gdx.app.debug("Button", "instance created");
		
		
	}
	
	public void loadAssets() {
		
	}
	
	public void commitAssets() {
		
		Gdx.app.debug("ButtonItem ", "buttonName = " + buttonName);
		regions = textureAtlas.findRegions(buttonName + "_act");
		totalFrames = regions.size - 1;
		
		regions.add(textureAtlas.findRegion(buttonName + "_hover"));
		hoverFrame = regions.size - 1;

		regions.add(textureAtlas.findRegion(buttonName + "_hover"));
		animFrame = regions.size - 1;
		
		//regions.add(textureAtlas.findRegion(buttonName + "_stop"));
		toggledFrame = regions.size - 1;
		
		regions.add(textureAtlas.findRegion(buttonName + "_deact"));
		disabledFrame = regions.size - 1;
		
		
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
		    	
		    	Gdx.app.debug("Button", "pressed");
		    //	game.sounds.play(SoundTrack.BUTTONS, false);
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
		    	Gdx.app.debug("Button", "released");
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
		
		if( timeElapsed - lastTimeElapsed > 0.040f) { // @ToDo: control frame rate from stage, not actors

			lastTimeElapsed = timeElapsed;
			
			switch(state) {
				case NORMAL: 
//					currentAnimFrame = 0;
					if(signalPress) {
						state = ButtonState.NORMAL;
						signalPress = false;
						currentFrame = 1;
					} else if(signalToggle) {
						signalToggle = false;
						signalPress = false;
						state = ButtonState.NORMAL;
						currentFrame = animFrame;
					}
					
					if(startAnim)
					{

						currentFrame = animFrame;
						
						if(++currentAnimFrame > 11){
							startAnim = false;
							currentAnimFrame = 0;
							currentFrame = animFrame;
						}


						if(currentAnimFrame == 8){
							int r = 0;
							
							do{
								r = rand.nextInt(game.context.BONUS_ITEMS);
							} while(r == index);
							
//							Gdx.app.debug("ButtonItem", "Index = " + index + " ::: game.overlay.bonus.bonusItems.get("+r+").toggled = " + game.overlay.bonus.bonusItems.get(r).toggled);
							
				    	}
				    	
					}

					
					break;
					
				case TRANSITION: 
//					if(currentAnimFrame < totalFrames) currentAnimFrame++; 
//					else
					currentFrame = animFrame;
					state = ButtonState.NORMAL;
					break;
					
				case PRESSED: 
					state = ButtonState.NORMAL;
					currentFrame = animFrame;
					break;
					
				case TOGGLED:
					state = ButtonState.NORMAL;
					currentFrame = animFrame;

					break;
					
				case DISABLED:			
					currentFrame = disabledFrame;
					break;
					
				default: 
					state = ButtonState.NORMAL;
					currentAnimFrame = 0;
					currentFrame = 0;
					break;
			}
					
		}
		
	}
	
    @Override
    public void draw(Batch batch, float alpha){
    			

    	color = getColor();
    	
		batch.setColor(color.r, color.g, color.b, opacity[currentAnimFrame] / 100f);
    	
		regions.get(currentFrame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	batch.draw(regions.get(currentFrame).getTexture(),
				getX(),
    			getY(), 
    			regions.get(currentFrame).getRegionWidth()/2, 
    			regions.get(currentFrame).getRegionHeight()/2,
				regions.get(currentFrame).getRegionWidth(), 
				regions.get(currentFrame).getRegionHeight(),
				1f,
				1f,
				0f,
    			regions.get(currentFrame).getRegionX(), 
    			regions.get(currentFrame).getRegionY(), 
    			regions.get(currentFrame).getRegionWidth(), 
    			regions.get(currentFrame).getRegionHeight(),
    			false,
    			false);
    	batch.setColor(color.r, color.g, color.b, 1f);
    	
//	 	regions.get(currentFrame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//    	batch.draw(regions.get(currentFrame).getTexture(),
//				getX(),
//    			getY(), 
//			   (float)regions.get(currentFrame).getRegionWidth()/2,
//			   (float)regions.get(currentFrame).getRegionHeight()/2,
//			   (float)regions.get(currentFrame).getRegionWidth(),
//			   (float)regions.get(currentFrame).getRegionHeight(),
//			   1f,
//			   1f,
//			   regions.get(currentFrame).rotate ? -90f : 0f,
//    			regions.get(currentFrame).getRegionX(), 
//    			regions.get(currentFrame).getRegionY(), 
//    			regions.get(currentFrame).getRegionWidth(), 
//    			regions.get(currentFrame).getRegionHeight(),
//			   false,
//			   false);

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
    	toggled = false;
    	enabled = true;
    	currentAnimFrame = 0;
    	currentFrame = 0;
    	startAnim = false;
    }
    
    public void activate() {
    	signalPress = true;
    	signalRelease = true;  
    }
    
    public void toggled() {
    	toggled = true;
    	signalToggle = true;
    	currentFrame = animFrame;
    }

    public boolean isToggled() {
    	return toggled;
    }
    
    public void stopAnim() {
    	state = ButtonState.PRESSED;
    }
    
    public void enable() {
//    	toggled = false;
    	currentFrame = 0;
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
    
    public boolean isAnimStop()
    {
    	return !startAnim;
    }
    
    public void startAnim()
    {
    	startAnim = true;
    	currentAnimFrame = 0;
    }
}
