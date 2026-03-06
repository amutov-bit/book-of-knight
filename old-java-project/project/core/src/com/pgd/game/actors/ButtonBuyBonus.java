package com.pgd.game.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.IButtonCallback;

public class ButtonBuyBonus extends Actor {
	
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
	
	private List<Label>text = new ArrayList<Label>();

	
	/**
	 * Constructor
	 */
	public ButtonBuyBonus(BookOfKnight game, int x, int y, TextureAtlas textureAtlas, String buttonName) {
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
		
		regions = textureAtlas.findRegions(buttonName);
//		regions.add(textureAtlas.findRegion(buttonName));
		totalFrames = regions.size - 1;
		
//		regions.add(textureAtlas.findRegion(buttonName + "_stop"));
		
		regions.add(textureAtlas.findRegion(buttonName + "_deact"));
		disabledFrame = regions.size - 1;
		
		if(hoverEnable)
		{
			regions.add(textureAtlas.findRegion(buttonName + "_hover"));
			hoverFrame = regions.size - 1;
		}
		
		toggledFrame = disabledFrame;
		
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
		
//		BUY button 001
//		#ffff00 - 255, 255, 0
//
//		BUY button 002
//		#ffffa1 - 255, 255, 161
//
//		BUY button deact
//		#83837f - 131,131,127
		
		text.add(new Label(" ", new LabelStyle(game.fonts.font60px, new Color(255/255f, 255/255f, 0/255f, 255f/255f))));
		text.add(new Label(" ", new LabelStyle(game.fonts.font60px, new Color(255/255f, 255/255f, 161/255f, 255f/255f))));
		text.add(new Label(" ", new LabelStyle(game.fonts.font60px, new Color(131/255f, 131/255f, 127/255f, 255f/255f))));

		if(game.languageCode == "FRA" || game.languageCode == "TUR" || game.languageCode == "POR" || game.languageCode == "ESP"){
			text.get(0).setFontScale(0.8f);	
			text.get(1).setFontScale(0.8f);	
			text.get(2).setFontScale(0.8f);	
		}
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
//    	batch.draw(regions.get(currentFrame).getTexture(),
//				getX(),
//    			getY(), 
//    			regions.get(currentFrame).getRegionX(), 
//    			regions.get(currentFrame).getRegionY(), 
//    			regions.get(currentFrame).getRegionWidth(), 
//    			regions.get(currentFrame).getRegionHeight());
    	
		float offsetX = 0, offsetY = 0;
		
	 	offsetX = (regions.get(currentFrame).getRotatedPackedWidth() - regions.get(currentFrame).getRegionWidth())  / 2;
	 	offsetY = (regions.get(currentFrame).getRotatedPackedHeight() - regions.get(currentFrame).getRegionHeight())  / 2;
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
		
//		if(currentFrame == 3){
//			text.get(2).setAlignment(Align.center);
//			text.get(2).setBounds(getX(), getY(), regions.get(currentFrame).getRegionWidth(), regions.get(currentFrame).getRegionHeight());
//			text.get(2).draw(batch, alpha);
//		}
//		else
		{
			text.get(currentFrame).setAlignment(Align.center);
			text.get(currentFrame).setBounds(getX(),getY(), regions.get(currentFrame).getRegionWidth(), regions.get(currentFrame).getRegionHeight());
			text.get(currentFrame).draw(batch, alpha);
		}
		

		
    }	
    
    public void setText(String str){
    	text.get(0).setText(str);
    	text.get(1).setText(str);
    	text.get(2).setText(str);
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
