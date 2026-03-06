package com.pgd.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader.TextureAtlasParameter;
import com.badlogic.gdx.graphics.Texture;

//import java.util.ArrayList;
//import java.util.List;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;


public class Textures {

	BookOfKnight game;
	/**
	 * Interface atlas
	 */
	public TextureAtlas interfaceAtlas;
	
	public TextureAtlas interfaceCommon;
	
	private TextureAtlas linesAtlast;

	private TextureAtlas paylinesAtlast;
	
//	private TextureAtlas linesBoostAtlast;

	private TextureAtlas symbolsAtlas;

	private TextureAtlas menuBetAtlas;

	private TextureAtlas freeGamesAtlas;
	
	private TextureAtlas interfaceAddFreeSpins;

//	private TextureAtlas boostMenuAtlas;
	
	/**
	 * Reference to game
	 */
	
	public Textures(BookOfKnight game){
		this.game = game;
			
//		 loadAssets();
	}
	
	public void loadAssetsAddFreeSpins() {
		game.ondemandAssetManager.load("interface/freespinsPopup.atlas", TextureAtlas.class);		
	}	

	public void commitInterfaceAddFreeSpins() {
		interfaceAddFreeSpins = game.manager.get("interface/freespinsPopup.atlas", TextureAtlas.class);
	}
	
	public TextureAtlas getInterfaceAddFreeSpins() {
		return interfaceAddFreeSpins;
	}
	
	
	
	public void loadAssetsInterface() {
		game.ondemandAssetManager.load("interface/interface.atlas", TextureAtlas.class);		
	}
	
	public void loadAssetsCommon() {
		game.ondemandAssetManager.load("interface/common.atlas", TextureAtlas.class);		
	}

	public void loadAssetsSymbols() {
		game.ondemandAssetManager.load("interface/symbols.atlas", TextureAtlas.class);		
	}	

	public void loadAssetsMenuBet() {
		game.ondemandAssetManager.load("interface/menu_bet.atlas", TextureAtlas.class);		
	}	
	
	public void loadAssetsFreeGames() {
		game.ondemandAssetManager.load("interface/freegames.atlas", TextureAtlas.class);		
	}	


//	public void loadAssetsBoostMenu() {
//		game.ondemandAssetManager.load("interface/boost_menu.atlas", TextureAtlas.class);		
//	}	

	public void commitAssetsSymbols() {
		symbolsAtlas = game.manager.get("interface/symbols.atlas", TextureAtlas.class);
	}

	public void commitAssetsMenuBetAtlas() {
		menuBetAtlas = game.manager.get("interface/menu_bet.atlas", TextureAtlas.class);
	}
	
	public void commitAssetsInterface() {
		interfaceAtlas = game.manager.get("interface/interface.atlas", TextureAtlas.class);
	}

	public void commitAssetsFreeGames() {
		freeGamesAtlas = game.manager.get("interface/freegames.atlas", TextureAtlas.class);
	}

//	public void commitBoostMenuAtlas() {
//		boostMenuAtlas = game.manager.get("interface/boost_menu.atlas", TextureAtlas.class);
//	}

	public void commitInterfaceCommon() {
		interfaceCommon = game.manager.get("interface/common.atlas", TextureAtlas.class);
	}
	
	public void loadAssetsLines(){
		game.ondemandAssetManager.load("lines/lines.atlas", TextureAtlas.class);
//		game.ondemandAssetManager.load("lines/lines_boost.atlas", TextureAtlas.class);
	}

	public void loadPayAssetsLines(){
		game.ondemandAssetManager.load("lines/paylines.atlas", TextureAtlas.class);
//		game.ondemandAssetManager.load("lines/lines_boost.atlas", TextureAtlas.class);
	}
	
	
	public void commitAssetsLines() {
		linesAtlast = game.manager.get("lines/lines.atlas", TextureAtlas.class);
//		linesBoostAtlast = game.manager.get("lines/lines_boost.atlas", TextureAtlas.class);
	}

	public void commitAssetsPayLines() {
		paylinesAtlast = game.manager.get("lines/paylines.atlas", TextureAtlas.class);
//		linesBoostAtlast = game.manager.get("lines/lines_boost.atlas", TextureAtlas.class);
	}

	public TextureAtlas getAutoPlayAtlas() {
//		return autoPlayAtlas;
		return menuBetAtlas;
	}

	public TextureAtlas getMenuBetAtlas() {
		return menuBetAtlas;
	}

	public TextureAtlas getSymbolsAtlas() {
		return symbolsAtlas;
	}

	public TextureAtlas getLiensAtlas() {
		return linesAtlast;
	}

	public TextureAtlas getPayLiensAtlas() {
		return paylinesAtlast;
	}

//	public TextureAtlas getLiensBoostAtlas() {
//		return linesBoostAtlast;
//	}
	
	public TextureAtlas getInterfaceAtlas() {
		return interfaceAtlas;
	}

	public TextureAtlas getFreeGamesAtlas() {
		return freeGamesAtlas;
	}

//	public TextureAtlas getBoostMenutlas() {
//		return boostMenuAtlas;
//	}

	public TextureAtlas getInterfaceCommon() {
		return interfaceCommon;
	}
	
	/**
	 * Dispose
	 */
	public void dispose() {
		interfaceAtlas.dispose();
		linesAtlast.dispose();
	}
}
