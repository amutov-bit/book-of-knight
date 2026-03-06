package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
//import com.esotericsoftware.spine.Skeleton;
//import com.esotericsoftware.spine.SkeletonRenderer;
import com.pgd.game.File;
import com.pgd.game.BookOfKnight;

public class ReelTextures {
	
//	private Texture bgTexture, bgNormal, bgFreeGames;
	private Texture reelsTexture, reelsTextureNormal, reelsTextureFG;
	private Texture hightligthTexture, hightligthTexture1, hightligthTexture2, hightligthTexture3;
	private Texture reelSymbolMask;
	
	/**
	 * Symbols
	 */
	private List<Array<AtlasRegion>> symbolRegions = new ArrayList<Array<AtlasRegion>>();
	private TextureAtlas symbolsAtlas, 
//							animAtlas,
							winAtlas
//							,
//							freegamesAtlas,
//							jackAtlas,
//							wildAtlas
							;
	
	/**
	 * Short animations atlas and regions
	 */
//	private TextureAtlas animAtlas;
	private Texture animTexture;
	private List<Array<AtlasRegion>> animRegions = new ArrayList<Array<AtlasRegion>>();
	
	private boolean animLoaded = false;
	private boolean animLoadedMagician = false;
	private boolean animLoadedMagicianGirl = false;
	private boolean animLoadedH = false;
	public  boolean animLoadedJack = false;
	/**
	 * This array holds frame sequence list for individual symbols
	 */
	public int[][] list = {
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 0
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 1
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 2
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 3
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 4
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 5
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 6
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 7
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 8
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 9
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,24,24,24,24,24,24,24,24,24,24,}, // 10
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,24,24,24,24,24,24,24,24,24,24,}, // 11
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,24,24,24,24,24,24,24,24,24,24,}, // 12
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,24,24,24,24,24,24,24,24,24,24,}, // 13
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,53,54,55,56,57,58,59,60,61}, // 9
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29}, // 10
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26}, // 11
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26}, // 12
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,24,24}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49}, // 13
	};		

//	public int[][] list = {
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 0
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 1
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 2
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 3
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 4
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 5
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 6
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 7
//			{1,2,3,4,5,6,7,8,9,10,11,12,11,10,9,8,7,6,5,4,3,2,1,1,1}, // 8
//			{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24}, // 9
//	};		
	

	/**
	 * This array holds what frame index to reset animation to when looping
	 */
	public int[] loop = {
			0, // 0
			0, // 1
			0, // 2
			0, // 3
			0, // 4
			0, // 5
			0, // 6
			0, // 7
			0, // 8
			0, // 9
			0,  // 10
			0,  // 11
			0,  // 12
			0,  // 13
	};		
	
	/**
	 * This array holds what is coordinate of current frame index
	 */
	public int[][][] coordinates = {
			{{0,0}}, // scatter
			{{0,0}}, // scatter
			{{0,0}}, // scatter
			{{0,0}}, // royal_9
			{{0,0}}, // royal_10
			{{0,0}}, // royal_J
			{{0,0}}, // royal_Q
			{{0,0}}, // royal_K
			{{0,0}}, // royal_A
			{{0,0}},// gems
			{{0,0}},// seven
			{{0,0}}, // mag
			{{0,0}}, // wild
			{{0,0}}, // wild
			{{0,0}}, // wild
			{{0,0}}, // wild
//			{{0,0}}, // wild
//			{{35,35}, {35,35}}
//			{{0,0},{0,0},{0,0},{0,0},{0,0},{35,35},{0,-35},{0,-35},{0,-35},{0,-35},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{-49,-160},{0,0},{0,0},{0,0},{0,0},{0,0}},
	};		
	
	/**
	 * This array holds what frame index to reset animation to when reaching the end
	 */
//	public int[] reset = {
//			0, // scatter
//			0, // royal_9
//			0, // royal_10
//			0, // royal_J
//			0, // royal_Q
//			0, // royal_K
//			0, // royal_A
//			0, // gems
//			0, // seven
//			0, // mag
//			35, // potion
//			0
//	};		
	
	/**
	 * Long animations atlas and regions
	 */
//	private TextureAtlas longAnimAtlas;
//	private Texture longAnimTexture;
//	private List<Array<AtlasRegion>> longAnimRegions = new ArrayList<Array<AtlasRegion>>();	
	
	/**
	 * Scatter long animations atlas and regions
	 */
//	private TextureAtlas longAnimAtlasScatter;
//	private Array<AtlasRegion> longAnimScatterRegions;	
	
	
	/**
	 * Reel highlight animation atlas and regions
	 */
	private TextureAtlas reelHighlightAtlas;
	private TextureAtlas reelMagicianAtlas;
	private TextureAtlas reelMagicianGirlAtlas;
	private Array<AtlasRegion> reelHighlightRegions;		
	private Array<AtlasRegion> reelMagicianRegions;		
	private Array<AtlasRegion> reelMagicianGirlRegions;		
    
	List<SpineTexturesSymbols> spineTextures =  new ArrayList<SpineTexturesSymbols>();		

//	private Array<Skeleton> 	    skeleton;
//    private Array<SkeletonRenderer> skeletonRenderer;
    
	/**
	 * Reference to game
	 */
	private BookOfKnight game;
	
	private Array<AtlasRegion> regions;
	
	public ReelTextures(BookOfKnight game){
		this.game = game;
	}
	
	public void loadAssetsSpine(int currentFile) {
    	switch(currentFile)
    	{
    		case 0:
    			game.ondemandAssetManager.load("spine/wild.atlas", TextureAtlas.class);
    		break;
    		case 1:
    			game.ondemandAssetManager.load("spine/wild.json", File.class);
    		break;
    		default:
    		break;
    	}
	}
	
	
	public void loadAssetsMagician(int currentFile) {
		
		animLoadedMagician = false;
		// load short animations
		switch(currentFile)
		{
			case 0:
				game.ondemandAssetManager.load("magician/magician-1.png", Texture.class);
			break;
			case 1:
				game.ondemandAssetManager.load("magician/magician.atlas", TextureAtlas.class);
			break;
			case 2:
			break;
		}
	}

	public void loadAssetsMagicianGirl(int currentFile) {
		
		animLoadedMagicianGirl = false;
		// load short animations
		switch(currentFile)
		{
		case 0:
			game.ondemandAssetManager.load("magician/hera.atlas", TextureAtlas.class);
			break;
		case 1:
			break;
		case 2:
			break;
		}
	}
	
	public void loadAssetsAnimations() {
		
		animLoaded = false;
		// load short animations
//		game.ondemandAssetManager.load("animations/wild.atlas", TextureAtlas.class);
		game.ondemandAssetManager.load("animations/win.atlas", TextureAtlas.class);
		
		game.ondemandAssetManager.load("spine/symbols/1920_book_v1.atlas", TextureAtlas.class);
		game.ondemandAssetManager.load("spine/symbols/1920_book_v1.json", File.class);

		game.ondemandAssetManager.load("spine/symbols/1920_knight_v1.atlas", TextureAtlas.class);
		game.ondemandAssetManager.load("spine/symbols/1920_knight_v1.json", File.class);

		
	}
	
//	public TextureAtlas getJackAnim(){
//		return jackAtlas;
//	}
	
//	public void commitJackAnim(){
//		jackAtlas 	 =  game.manager.get("animations/jack.atlas", TextureAtlas.class);
//		animLoadedJack = true;
//	}
	
	public void loadAssetsHighlights() {
		animLoadedH = false;
		// load reel highlights atlas
		game.ondemandAssetManager.load("animations/anticipation.atlas", TextureAtlas.class);
		
		Gdx.app.debug("ReelTextures", "loadAssetsSaucer()");
	}
	
	public void commitAssetsSymbols() {
		
		
		regions = game.textures.getSymbolsAtlas().findRegions("book_blur"); //0
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("10_blur"); //1
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("J_blur");  //2
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("Q_blur");  //3
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("K_blur");  //4
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("A_blur");  //5
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("torch_blur"); //6
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("axe_blur"); //7
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("chalice_blur");  //8
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("knight_blur");  //9
		if(regions.size > 0) symbolRegions.add(regions);
		
		
		regions = game.textures.getInterfaceAtlas().findRegions("shield_blur");  //10
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("mini_blur");  //11
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("major_blur");  //12
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("mega_blur");  //13
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("empty_blur");  //14
		if(regions.size > 0) symbolRegions.add(regions);

		regions = game.textures.getFreeGamesAtlas().findRegions("golden_10_blur"); //15
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_J_blur");  //16
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_Q_blur");  //17
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_K_blur");  //18
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_A_blur");  //19
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_torch_blur"); //20
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_axe_blur"); //21
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_chalice_blur");  //22
		if(regions.size > 0) symbolRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_knight_blur");  //23
		if(regions.size > 0) symbolRegions.add(regions);
		
		Gdx.app.debug("ReelTextures", "loaded " + symbolRegions.size() + " symbol texture regions");
	}

	public void commitAssetsAnimations() {
		/**
		 * Load short animation atlas, texture and regions
		 */

//		bigWildWin 	 =  game.manager.get("animations/big_wild_win.atlas", TextureAtlas.class);
		
		regions = null;
					
//		animAtlas 	 =  game.manager.get("animations/wild.atlas", TextureAtlas.class);
		winAtlas 	 =  game.manager.get("animations/win.atlas", TextureAtlas.class);
//		wildAtlas 	 =  game.manager.get("animations/wild.atlas", TextureAtlas.class);
		
		regions = game.textures.getSymbolsAtlas().findRegions("book_blur"); //0
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("10_blur"); //1
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("J_blur");  //2
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("Q_blur");  //3
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("K_blur");  //4
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("A_blur");  //5
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("torch_blur"); //6
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("axe_blur"); //7
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("chalice_blur");  //8
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getSymbolsAtlas().findRegions("knight_blur");  //9
		if(regions.size > 0) animRegions.add(regions);
		
		
		regions = game.textures.getInterfaceAtlas().findRegions("shield_blur");  //10
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("mini_blur");  //11
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("major_blur");  //12
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getInterfaceAtlas().findRegions("mega_blur");  	 //13
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("empty_blur");  	 //14
		if(regions.size > 0) animRegions.add(regions);
		
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_10_blur"); //15
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_J_blur");  //16
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_Q_blur");  //17
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_K_blur");  //18
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_A_blur");  //19
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_torch_blur"); //20
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_axe_blur"); //21
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_chalice_blur");  //22
		if(regions.size > 0) animRegions.add(regions);
		regions = game.textures.getFreeGamesAtlas().findRegions("golden_knight_blur");  //23
		if(regions.size > 0) animRegions.add(regions);
		
		regions = winAtlas.findRegions("frame"); //24
		if(regions.size > 0) animRegions.add(regions);


		//symbol 0
		FileHandle file = game.manager.get("spine/symbols/1920_book_v1.json", File.class).getFile();
		TextureAtlas atlas = game.manager.get("spine/symbols/1920_book_v1.atlas", TextureAtlas.class);
		
		SkeletonJson json = new SkeletonJson(atlas);
		SkeletonData playerSkeletonData = json.readSkeletonData(file);
		AnimationStateData playerAnimationData = new AnimationStateData(playerSkeletonData);
		
		spineTextures.add(new SpineTexturesSymbols(game));
		spineTextures.get(0).commitAssetsSpine(json, playerSkeletonData, playerAnimationData);
		
		//symbol 9
		file = game.manager.get("spine/symbols/1920_knight_v1.json", File.class).getFile();
		atlas = game.manager.get("spine/symbols/1920_knight_v1.atlas", TextureAtlas.class);
		
		json = new SkeletonJson(atlas);
		playerSkeletonData = json.readSkeletonData(file);
		playerAnimationData = new AnimationStateData(playerSkeletonData);

		spineTextures.add(new SpineTexturesSymbols(game));
		spineTextures.get(1).commitAssetsSpine(json, playerSkeletonData, playerAnimationData);
		
		animLoaded = true;
		Gdx.app.debug("ReelTextures", "loaded " + animRegions.size() + " animations texture regions");
	}
		
		/**
		 * Load reel hightlights
		 */
	public void commitAssetsHighlights() {
		reelHighlightAtlas = game.manager.get("animations/anticipation.atlas", TextureAtlas.class);
//		reelHighlightAtlas = new TextureAtlas("animations/saucer.atlas");
		reelHighlightRegions = reelHighlightAtlas.findRegions("anticipation");
		
		animLoadedH = true;	
}
		
public void commitAssetsMagician() {	
		/**
		 * Load reel hightlights
		 */
		reelMagicianAtlas = game.manager.get("magician/magician.atlas", TextureAtlas.class);
		reelMagicianRegions = reelMagicianAtlas.findRegions("m_wild");
		
		animLoadedMagician = true;
		
}

public void commitAssetsMagicianGirl() {	
	/**
	 * Load reel hightlights
	 */
	reelMagicianGirlAtlas = game.manager.get("magician/hera.atlas", TextureAtlas.class);
	reelMagicianGirlRegions = reelMagicianGirlAtlas.findRegions("hera_wild");
	
	animLoadedMagicianGirl = true;
	
}
	
/**
 * Short symbol animations spritesheet and regions 
 */
public boolean getAnimRegionLoaded() {
	return animLoaded;
}

/**
 * Short symbol animations spritesheet and regions 
 */
public boolean getAnimRegionLoadedH() {
	return animLoadedH;
	}

/**
 * Short symbol animations spritesheet and regions 
 */
public boolean getAnimRegionLoadedMagician() {
	return animLoadedMagician;
}

/**
 * Short symbol animations spritesheet and regions 
 */
public boolean getAnimRegionLoadedMagicianGirl() {
	return animLoadedMagicianGirl;
}
	
	/**
	 * Sets reels texture
	 * @param fg - free games if true, normal is false
	 */
	public void setFGTextures(boolean fg) {
		reelsTexture = fg ? reelsTextureFG : reelsTextureNormal;
	//	bgTexture = fg? bgFreeGames : bgNormal;
	}
	
	public Texture getReelsTexture() {
		return reelsTexture;
	}
	
	public Texture getReelsHigthlightTexture() {
		return hightligthTexture;
	}
	
	public void setReelsHigthlightTexture(int index) {
		switch(index) {
			case 1: hightligthTexture = hightligthTexture1;  break;
			case 2: hightligthTexture = hightligthTexture2;  break;
			case 3: hightligthTexture = hightligthTexture3;  break;
			default: hightligthTexture = hightligthTexture1; break;
		}
	}		
	
	public Texture getReelsSymbolMask() {
		return reelSymbolMask;
	}	
	
	/**
	 * Return a texture containing the animation sequence for a symbol index
	 * @param index
	 * @return Texture
	 */
	public Texture getSymbolAnimationTexture(int index) {
//		return animations.get(index % animations.size());
		return animTexture;
	}
	
	
	public SpineTexturesSymbols getSpineTexture(int index){
		
		int i = (index == 9) ? 1 : 0;

		return spineTextures.get(i);			
	}
	
	/**
	 * Symbols spritesheet and regions
	 * @return Texture
	 */
	public AtlasRegion getSymbolRegion(int index, int frame) {
		symbolRegions.get(index).get(frame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return symbolRegions.get(index).get(frame);
	}
	
	public Texture getSymbolsTexture(int index, int blurIndex) {
			symbolRegions.get(index).get(blurIndex).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return symbolRegions.get(index).get(blurIndex).getTexture();
	}
	
	public int getSymbolTextureRegionX(int index, int blurIndex) {
			return symbolRegions.get(index).get(blurIndex).getRegionX();
	}
	
	public int getSymbolTextureRegionY(int index, int blurIndex) {
			return symbolRegions.get(index).get(blurIndex).getRegionY();
	}	
	
	public int getSymbolTextureOffsetX(int index, int blurIndex) {
		return (int) symbolRegions.get(index).get(blurIndex).offsetX;		
	}
	
	public int getSymbolTextureOffsetY(int index, int blurIndex) {
		return (int) symbolRegions.get(index).get(blurIndex).offsetY;		
	}	
	
	public int getSymbolTextureRegionWidth(int index, int blurIndex) {
			return symbolRegions.get(index).get(blurIndex).getRegionWidth();
	}
	
	public int getSymbolTextureRegionHeight(int index, int blurIndex) {
		
			return symbolRegions.get(index).get(blurIndex).getRegionHeight();
	}
	
	/**
	 * Get frame coordinates of symbol on the reel 
	 */
	public int getSymFrameX(int index, int frame) {
		return (int) symbolRegions.get(index).get(0).offsetX;		
	}
	
	public int getSymFrameY(int index, int frame) {
		return (int) symbolRegions.get(index).get(0).offsetY;		
	}

	
	/**
	 * Short symbol animations spritesheet and regions 
	 */
	
	public int getAnimFrameX(int index, int frame) {
//		Gdx.app.debug("Symbol", "getAnimFrameX = " + animRegions.get(index).get(frame).offsetX);
		return (int) animRegions.get(index).get(frame).offsetX;		
	}
	
	public int getAnimFrameY(int index, int frame) {
//		Gdx.app.debug("Symbol", "getAnimFrameY = " + animRegions.get(index).get(frame).offsetY);
		return (int) animRegions.get(index).get(frame).offsetY;		
	}
	
	public Texture getAnimTexture(int index, int frame) {
		animRegions.get(index).get(frame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return animRegions.get(index).get(frame).getTexture();
		
//		if(index == game.math.WILD)	  
//			return animRegions.get(0).get(frame).getTexture();
////		else if (index == 5) return animRegions.get(4).get(frame).getTexture();	
////		else if (index == 6) return animRegions.get(5).get(frame).getTexture();	
////		else if (index == 7) return animRegions.get(2).get(frame).getTexture();	
////		else if (index == 8) return animRegions.get(3).get(frame).getTexture();	
//		else if(game.context.gameMode == game.context.SUPER_GAMES)
//			return symbolRegions.get(index+6).get(0).getTexture();
//		else
//			return symbolRegions.get(index).get(0).getTexture();
	}
	
	public int getAnimTextureRegionX(int index, int frame) {
		return animRegions.get(index).get(frame).getRegionX();
//		if(index == game.math.WILD)	  
//			return animRegions.get(0).get(frame).getRegionX();
////		else if (index > 0) return animRegions.get(1).get(frame).getRegionX();	
//		else if(game.context.gameMode == game.context.SUPER_GAMES)					
//			return symbolRegions.get(index+6).get(0).getRegionX();
//		else
//			return symbolRegions.get(index).get(0).getRegionX();
	}
	
	public int getAnimTextureRegionY(int index, int frame) {
		return animRegions.get(index).get(frame).getRegionY();
////		if(index == game.math.WILD)	   
//			return animRegions.get(0).get(frame).getRegionY();
////		else if(index > 0) 					return animRegions.get(1).get(frame).getRegionY();	
//		else if(game.context.gameMode == game.context.SUPER_GAMES)							  
//			return symbolRegions.get(index+6).get(0).getRegionY();
//		else 
//			return symbolRegions.get(index).get(0).getRegionY();
	}	
	
	public int getAnimTextureRegionWidth(int index, int frame) {
		return animRegions.get(index).get(frame).getRegionWidth();
//		if(index == game.math.WILD)	
//			return animRegions.get(0).get(frame).getRegionWidth();
////		else if (index > 0) 			  return animRegions.get(1).get(frame).getRegionWidth();	
//		else if(game.context.gameMode == game.context.SUPER_GAMES)							  
//			return symbolRegions.get(index+6).get(0).getRegionWidth();
//		else
//			return symbolRegions.get(index).get(0).getRegionWidth();
	}
	
	public float getRotatedPackedWidth(int index, int frame) {
		return animRegions.get(index).get(frame).getRotatedPackedWidth();
	}
	
	public float getRotatedPackedHeight(int index, int frame) {
		return animRegions.get(index).get(frame).getRotatedPackedHeight();
	}	
	
	public int getAnimTextureRegionHeight(int index, int frame) {
		return animRegions.get(index).get(frame).getRegionHeight();
		
//		if(index == game.math.WILD) 	  return animRegions.get(0).get(frame).getRegionHeight();
////		else if (index > 0) 			  return animRegions.get(1).get(frame).getRegionHeight();	
//		else if(game.context.gameMode == game.context.SUPER_GAMES)
//			return symbolRegions.get(index+6).get(0).getRegionHeight();
//		else
//			return symbolRegions.get(index).get(0).getRegionHeight();
	}	
	
	/**
	 * Short symbol animations spritesheet and regions 
	 */
	public Texture getAnimOrbitTexture(int index, int frame) {
		    animRegions.get(index).get(frame).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			return animRegions.get(index).get(frame).getTexture();
	}
	
	public int getAnimOrbitTextureRegionX(int index, int frame) {
			return animRegions.get(index).get(frame).getRegionX();
	}
	
	public int getAnimOrbitTextureRegionY(int index, int frame) {
			return animRegions.get(index).get(frame).getRegionY();
	}	
	
	public int getAnimOrbitTextureRegionWidth(int index, int frame) {
			return animRegions.get(index).get(frame).getRegionWidth();
	}
	
	public int getAnimOrbitTextureRegionHeight(int index, int frame) {
			return animRegions.get(index).get(frame).getRegionHeight();	
	}

	
	public int getAnimTotalFrames(int index) {
		return (list[index].length > 0)? list[index].length : animRegions.get(index).size;		
	}
	
	/**
	 * Highlight
	 */
	public Texture getReelHighlightTexture(int frame) {
		return reelHighlightRegions.get(frame).getTexture();
	}

	public int getReelHighlightRegionX(int frame) {
		return reelHighlightRegions.get(frame).getRegionX();
	}

	public int getReelHighlightRegionY(int frame) {
		return reelHighlightRegions.get(frame).getRegionY();
	}
	
	public int getReelHighlightRegionWidth(int frame) {
		return reelHighlightRegions.get(frame).getRegionWidth();
	}
	
	public float getReelHighlightOffsetX(int frame) {
		return reelHighlightRegions.get(frame).offsetX;
	}	
	public float getReelHighlightOffsetY(int frame) {
		return reelHighlightRegions.get(frame).offsetY;
	}	

	public int getReelHighlightRegionHeight(int frame) {
		return reelHighlightRegions.get(frame).getRegionHeight();
	}	
	
	public float getRotatedPackedWidth(int frame) {
		return reelHighlightRegions.get(frame).getRotatedPackedWidth();
	}
	
	public float getRotatedPackedHeight(int frame) {
		return reelHighlightRegions.get(frame).getRotatedPackedHeight();
	}	
	
	public float getRegionWidth(int frame) {
		return reelHighlightRegions.get(frame).getRegionWidth();
	}
	
	public float getRegionHeight(int frame) {
		return reelHighlightRegions.get(frame).getRegionHeight();
	}	
	
	public boolean getReelHighlightRotate(int frame) {
		return reelHighlightRegions.get(frame).rotate;
	}	

	/**
	 * Magician
	 */
	public Texture getReelMagicianTexture(int frame, int magicianFrame, int type) {		
		if(type == 0) return reelMagicianRegions.get(frame).getTexture();
		else		  return reelMagicianGirlRegions.get(frame).getTexture();
//		 else			return  symbolRegions.get(11).get(frame).getTexture();	
	}
	
	public int getReelMagicianRegionX(int frame, int magicianFrame, int type) {
		if(type == 0) return reelMagicianRegions.get(frame).getRegionX();
		else		  return reelMagicianGirlRegions.get(frame).getRegionX();
//		else			return  symbolRegions.get(11).get(frame).getRegionX();	
	}
	
	public int getReelMagicianRegionY(int frame, int magicianFrame, int type) {
		if(type == 0) return reelMagicianRegions.get(frame).getRegionY();
		else		  return reelMagicianGirlRegions.get(frame).getRegionY();
//		else			return  symbolRegions.get(11).get(frame).getRegionY();	
	}

	public float getReelHighlightRegionOffsetX(int frame, int type) {
		if(type == 0)	return reelMagicianRegions.get(frame).offsetX;
		else 			return reelMagicianGirlRegions.get(frame).offsetX;
//		else			return  symbolRegions.get(11).get(frame).getRegionX();	
	}
	
	public float getReelHighlightRegionOffsetY(int frame, int type) {
		if(type == 0) return reelMagicianRegions.get(frame).offsetY;
		else		  return reelMagicianGirlRegions.get(frame).offsetY;
//		else			return  symbolRegions.get(11).get(frame).getRegionY();	
	}
	
	public int getReelMagicianRegionWidth(int frame, int magicianFrame, int type) {
		if(type == 0)	return reelMagicianRegions.get(frame).getRegionWidth();
		else 		 	return reelMagicianGirlRegions.get(frame).getRegionWidth();
//		else			return  symbolRegions.get(11).get(frame).getRegionWidth();	
	}
	
	public int getReelMagicianRegionHeight(int frame, int magicianFrame, int type) {
		if(type == 0)          return reelMagicianRegions.get(frame).getRegionHeight();
		else				  return reelMagicianGirlRegions.get(frame).getRegionHeight();
	}	
	
	public boolean getReelMagicianRegionRotate(int frame, int type) {
		if(type == 0) return reelMagicianRegions.get(frame).rotate;
		else		  return reelMagicianGirlRegions.get(frame).rotate;
//		else			return  symbolRegions.get(11).get(frame).getRegionHeight();	
	}	
	
	public float getMagicianRotatedPackedWidth(int frame, int type) {
		if(type == 0) return reelMagicianRegions.get(frame).getRotatedPackedWidth();
		else 		  return reelMagicianGirlRegions.get(frame).getRotatedPackedWidth();
	}
	
	public float getMagicianRotatedPackedHeight(int frame, int type) {
		if(type == 0)		return reelMagicianRegions.get(frame).getRotatedPackedHeight();
		else				return reelMagicianGirlRegions.get(frame).getRotatedPackedHeight();
	}	
	/**
	 * Dispose
	 */
	public void dispose() {
		symbolsAtlas.dispose();
//		animAtlas.dispose();
		reelHighlightAtlas.dispose();
		reelMagicianAtlas.dispose();
	}
}
