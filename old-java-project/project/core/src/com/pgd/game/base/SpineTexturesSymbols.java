package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.Slot;
import com.esotericsoftware.spine.AnimationState.AnimationStateListener;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.pgd.game.BookOfKnight;
import com.pgd.game.File;

public class SpineTexturesSymbols extends Group{
	
	private Array<Skeleton> 	    skeleton = new Array<Skeleton>();
    private SkeletonRenderer 		skeletonRenderer;
    private Array<AnimationState>   animationState = new Array<AnimationState>();
    
    int type = 0;
    int reel = 0;

    private boolean spineAnimationStopped = false;
    
    private float symbolSize [][] = {
    		{0, 0},
    };
    
    private boolean loaded = false;
	/**
	 * Reference to game
	 */
	private BookOfKnight game;
	
    public SpineTexturesSymbols(BookOfKnight game){
		this.game = game;
	}
    
	public void commitAssetsSpine(SkeletonJson json, SkeletonData playerSkeletonData, AnimationStateData playerAnimationData){

//		SkeletonJson json = new SkeletonJson(game.manager.get("spine/jackinthebox.atlas", TextureAtlas.class));
//		SkeletonData playerSkeletonData = json.readSkeletonData(game.manager.get("spine/jackinthebox.json", File.class).getFile());

		

        animationState.add(new AnimationState(playerAnimationData));
        animationState.get(0).setAnimation(0, "animation", true);
        
//        animationState.get(0).addAnimation(0, "animation2", true, 0); // Run after the jump.
        
        skeleton.add(new Skeleton(playerSkeletonData));

//        json = null;
//        playerSkeletonData = null;
        
//        json = new SkeletonJson(game.manager.get("spine/fan.atlas", TextureAtlas.class));
//        playerSkeletonData = json.readSkeletonData(game.manager.get("spine/fan.json", File.class).getFile());
        
//        playerSkeletonData.setWidth(156);
//        playerSkeletonData.setHeight(144);

//        animationState.add(new AnimationState(new AnimationStateData(playerSkeletonData)));
//        skeleton.add(new Skeleton(playerSkeletonData));
//        animationState.get(1).setAnimation(0, "animation", false);
//        animationState.get(1).addAnimation(0, "animation2", true, 0); // Run after the jump.
        
//       
//        skeleton.get(0).getRootBone().setScale(1.01f, 1.01f);
//        skeleton.get(1).getRootBone().setScale(0.154f, 0.154f);
        
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);

    	animationState.get(0).clearTracks();
    	animationState.get(0).setAnimation(0, "animation", false);
    	
        loaded = true;        
        
    	animationState.get(0).addListener(new AnimationStateListener() {
    		
    		@Override
    		public void start(TrackEntry entry) {
    			// TODO Auto-generated method stub
//    			 Gdx.app.debug("Spine ", "start " );
    			 spineAnimationStopped = false;
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
    			
//    			game.gsLink.console("Spine END ");

    			spineAnimationStopped = true;
    		}
    		
    		@Override
    		public void dispose(TrackEntry entry) {
    			// TODO Auto-generated method stub
//    			Gdx.app.debug("Spine ", "dispose " );
    			
    		}
    		
    		@Override
    		public void complete(TrackEntry entry) {
    			// TODO Auto-generated method stub
    			Gdx.app.debug("Spine ", "complete " );
    			
    			game.gsLink.console("Spine Complete ");

    			
    			spineAnimationStopped = true;
    		}
    	} );
    	
       
        
	}
	
	public SkeletonRenderer getSpineSkeletonRenderer(){
		return skeletonRenderer;
	}

	@Override
	public void draw(Batch batch, float alpha){
		
		 int getBlendSrc = batch.getBlendSrcFunc();

		 int getBlendDst = batch.getBlendDstFunc();

	     skeleton.get(type).updateWorldTransform();
	        
		 skeletonRenderer.draw(batch, skeleton.get(type));
		
		 batch.setBlendFunction(getBlendSrc, getBlendDst);
	}
	
	@Override
	public void act(float delta) {

		super.act(delta);
		
    	if(loaded)
    	{    		
    		animationState.get(0).update(delta);
    		animationState.get(0).apply(skeleton.get(0));	      
			skeleton.get(0).update(delta);
    	}
        
    }	
    
    public void setSpineAnimRegionX(int index, float x){
    	skeleton.get(index).setX(x + symbolSize[index][0] / 2);
    	type = index;
    }

    public void setSpineAnimRegionY(int index, float y){
    	skeleton.get(index).setY(y + symbolSize[index][1]);
    }
    
    
    public boolean isLoaded(){
    	return loaded;
    }
    
    public void clearAnimationState(){
    	if(loaded){
    		spineAnimationStopped = false;
	    	animationState.get(0).clearTracks();
	    	animationState.get(0).setAnimation(0, "animation", false);
//	    	animationState.get(1).clearTracks();
//	    	animationState.get(1).setAnimation(0, "animation", false);
    	}
    }
    
    public boolean spineAnimationStopped(){
    	return spineAnimationStopped;
    }
    
}