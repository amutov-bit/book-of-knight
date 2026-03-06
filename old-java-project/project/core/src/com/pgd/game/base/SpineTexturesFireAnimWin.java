package com.pgd.game.base;

import java.io.ByteArrayInputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.pgd.game.File;
import com.pgd.game.BookOfKnight;

public class SpineTexturesFireAnimWin extends Group{
	
	private Array<Skeleton> 	    skeleton = new Array<Skeleton>();
    private SkeletonRenderer 		skeletonRenderer;
    private Array<AnimationState>   animationState = new Array<AnimationState>();
    
    int type = 0;
    int reel = 0;

    
    private float symbolSize [][] = {
    		{0, 0},
    		{0, 0},
    		{0, 0},
    };
    
    private boolean loaded = false;
	/**
	 * Reference to game
	 */
	private BookOfKnight game;
	
	private boolean stopAnimatopn = false;
	
	private boolean end = false;
	
    public SpineTexturesFireAnimWin(BookOfKnight game){
		this.game = game;
		 stopAnimatopn = false;
	}
    
	public void loadAssetsSpine(int currentFile) {
		loaded = false;
		
    	switch(currentFile)
    	{
    		case 0:
    			game.ondemandAssetManager.load("spine/1920_fire_explosion.atlas", TextureAtlas.class);
    		break;
    		case 1:
    			game.ondemandAssetManager.load("spine/1920_fire_explosion.json", File.class);
    		break;
    		case 2:
    			break;
    		case 3:
    			break;
    		case 4:
    			break;
    		case 5:
    			break;
    		default:
    		break;
    	}
	}
	
	public void commitAssetsSpine(){
		
		SkeletonJson json = new SkeletonJson(game.manager.get("spine/1920_fire_explosion.atlas", TextureAtlas.class));
		SkeletonData playerSkeletonData = json.readSkeletonData(game.manager.get("spine/1920_fire_explosion.json", File.class).getFile());
		
		Gdx.app.debug("Spine ","playerSkeletonData.getHeight() = " +  playerSkeletonData.getHeight() + " ::: playerSkeletonData.w = " + playerSkeletonData.getWidth());
        AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);

        animationState.add(new AnimationState(playerAnimationData));
        animationState.get(0).setAnimation(0, "animation", false);
        

//        animationState.get(0).addAnimation(0, "animation2", true, 0); // Run after the jump.
        
        skeleton.add(new Skeleton(playerSkeletonData));
//        skeleton.get(0).getRootBone().setScale(0.5714f, 0.5714f);
        
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        
        loaded = true;        
        
    	animationState.get(0).addListener(new AnimationStateListener() {
    		
    		@Override
    		public void start(TrackEntry entry) {
    			// TODO Auto-generated method stub
//    			 Gdx.app.debug("Spine ", "start " );
    			end = false;
    		}
    		
    		@Override
    		public void interrupt(TrackEntry entry) {
    			// TODO Auto-generated method stub
//    			Gdx.app.debug("Spine ", "interrupt " );
    			
    		}
    		
    		@Override
    		public void event(TrackEntry entry, Event event) {
    			// TODO Auto-generated method stub
//    			Gdx.app.debug("Spine ", "event " );
    			
    		}
    		
    		@Override
    		public void end(TrackEntry entry) {
    			// TODO Auto-generated method stub
    			Gdx.app.debug("Spine ", "end " );
    			
    			end = true;
    			
    		}
    		
    		@Override
    		public void dispose(TrackEntry entry) {
    			// TODO Auto-generated method stub
    			Gdx.app.debug("Spine ", "dispose " );
    			
    		}
    		
    		@Override
    		public void complete(TrackEntry entry) {
    			// TODO Auto-generated method stub
    			Gdx.app.debug("Spine ", "complete " );
    			end = true;
//    			game.overlay.bigWin.hideBigWin();
    		}
    	} );
    	
       
        
	}
	
	public boolean getEnd(){
		return end;
	}
	
	public SkeletonRenderer getSpineSkeletonRenderer(){
		return skeletonRenderer;
	}

	@Override
	public void draw(Batch batch, float alpha){
		if(loaded && !stopAnimatopn)
    	{ 
//			Gdx.app.debug("Spine ","draw");
			
			 int getBlendSrc = batch.getBlendSrcFunc();
	
			 int getBlendDst = batch.getBlendDstFunc();
	
		     skeleton.get(type).updateWorldTransform();
		   
		     skeletonRenderer.draw(batch, skeleton.get(type));
			
			 batch.setBlendFunction(getBlendSrc, getBlendDst);
    	}
	}
	
	public void act(float delta, int index) {

//		Gdx.app.debug("Spine ","act");
    	if(loaded && !stopAnimatopn)
    	{    		
    		animationState.get(index).update(delta);
    		animationState.get(index).apply(skeleton.get(index));	      
			skeleton.get(index).update(delta);
    	}
        
    }	
    
    public void setSpineAnimRegionX(int index, float x){
    	if(loaded){
	    	skeleton.get(index).setX(x + symbolSize[index][0] / 2);
	    	type = index;
    	}
    }

    public void setSpineAnimRegionY(int index, float y){
    	if(loaded){
    		skeleton.get(index).setY(y + symbolSize[index][1]);
    	}
    }
    
    
    public boolean isLoaded(){
    	return loaded;
    }
    
    public boolean getAnimStop(){
    	return stopAnimatopn;
    }
    
    public void setAnimStop(boolean stop){
    	stopAnimatopn = stop;
    }
    
    public void clearAnimationState(){
    	if(loaded){
	    	animationState.get(0).clearTracks();
	    	animationState.get(0).setAnimation(0, "animation", false);
    	}
    }
    
    
}