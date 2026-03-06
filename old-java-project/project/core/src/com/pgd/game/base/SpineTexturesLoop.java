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

public class SpineTexturesLoop extends Group{
	
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
	
	private boolean endFirst = false;
	private boolean endSecond = false;
	
	private String path;
	
	private boolean loop;
	
    public SpineTexturesLoop(BookOfKnight game, String path, boolean loop){
		this.game = game;
		this.path = path;
		
		 stopAnimatopn = false;
	}
    
	public void loadAssetsSpine(int currentFile) {
		loaded = false;
		
		
    	switch(currentFile)
    	{
    		case 0:
    			game.ondemandAssetManager.load(path + ".atlas", TextureAtlas.class);
    		break;
    		case 1:
    			game.ondemandAssetManager.load(path + ".json", File.class);
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
		
		SkeletonJson json = new SkeletonJson(game.manager.get(path + ".atlas", TextureAtlas.class));
		SkeletonData playerSkeletonData = json.readSkeletonData(game.manager.get(path + ".json", File.class).getFile());
		
		Gdx.app.debug("Spine ","playerSkeletonData.getHeight() = " +  playerSkeletonData.getHeight() + " ::: playerSkeletonData.w = " + playerSkeletonData.getWidth());
        AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);

        animationState.add(new AnimationState(playerAnimationData));
        animationState.get(0).setAnimation(0, "animation_start", false);
        
        animationState.add(new AnimationState(playerAnimationData));
        animationState.get(1).setAnimation(0, "animation_loop", false);
        

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
    			endFirst = false;
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
    			
//    			end = true;
    			
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
    			endFirst = true;
//    			game.overlay.bigWin.hideBigWin();
    		}
    	} );
    	
    	
    	animationState.get(1).addListener(new AnimationStateListener() {
    		
    		@Override
    		public void start(TrackEntry entry) {
    			// TODO Auto-generated method stub
//    			 Gdx.app.debug("Spine ", "start " );
    			endSecond = false;
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
    			
//    			if (!entry.getLoop()) {
//    				endSecond = true;
//    			}
    			
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
    			if (!entry.getLoop()) {
    				endSecond = true;
    			}
//    			game.overlay.bigWin.hideBigWin();
    		}
    	} );
    	
       
        
	}
	
	public boolean getEndFirst(){
		return endFirst;
	}

	public boolean getEndSecond(){
		return endSecond;
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
    		animationState.get(index).apply(skeleton.get(0));	      
			skeleton.get(0).update(delta);
    	}
        
    }	
    
    public void setSpineAnimRegionSetRotation(int index, float y){
    	if(loaded){
		     skeleton.get(type).getRootBone().setRotation(y);
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
    
    public void clearAnimationState(int index){
		if (loaded) {
			if (index == 0) {
				endFirst = false;
				AnimationState state = animationState.get(index);  // Or your track index
				
				// 1. Clear the current animation
				state.clearTrack(0); // Clears track 0
				// or
				state.clearTracks(); // Clears all tracks
				
				// 2. Reset skeleton pose (optional but usually needed)
				skeleton.get(0).setToSetupPose(); // Reset to setup pose
				animationState.get(index).setAnimation(0, "animation_start", false);
			}else{
				endSecond = false;
//				animationState.get(index).clearTracks();
				AnimationState state = animationState.get(index);  // Or your track index
				
				// 1. Clear the current animation
				state.clearTrack(index); // Clears track 0
				
				// 2. Reset skeleton pose (optional but usually needed)
				skeleton.get(0).setToSetupPose(); // Reset to setup pose
				
				animationState.get(index).setAnimation(0, "animation_loop", true);
			}
		}
    }
    
    
}