package com.pgd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Fonts {

	public BitmapFont font60px;
	public BitmapFont font54px;
	public BitmapFont font46px;
	public BitmapFont font36px;
	public BitmapFont font36pxWin;
	public BitmapFont font34px;
//	public BitmapFont font24px;
//	public BitmapFont fontAuto;
//	public BitmapFont fontHelp;
	
	public BitmapFont font36fs;
	public BitmapFont font27fs;
	public BitmapFont font22fs;
	public BitmapFont font62fs;

	public BitmapFont font32fs;
	public BitmapFont font23fs;
	public BitmapFont font20fs;

	private BookOfKnight game;
	
	public Fonts(BookOfKnight game) {

		this.game = game;
//		imagine20px = new BitmapFont(Gdx.files.internal("fonts/imagine20px.fnt"));
//		imagine30px = new BitmapFont(Gdx.files.internal("fonts/30px.fnt"));
//		imagine36px = new BitmapFont(Gdx.files.internal("fonts/imagine36px.fnt"));
//		loadAssets();
	}
	public void loadAssets(){
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/60px.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/54px.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/46px.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/36px.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/36px-win.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/34px.fnt", BitmapFont.class);
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/34px.fnt", BitmapFont.class);	
		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs40.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs30.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs25.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs68.fnt", BitmapFont.class);		

		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs32.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs23.fnt", BitmapFont.class);		
		game.ondemandAssetManager.load("fonts/" + game.gameTxt.path + "/fs20.fnt", BitmapFont.class);
		
//		game.ondemandAssetManager.load("fonts/24px.fnt", BitmapFont.class);		
//		game.ondemandAssetManager.load("autoplay/autoFnt.fnt", BitmapFont.class);
//		game.ondemandAssetManager.load("fonts/paytable.fnt", BitmapFont.class);
	}
	
	public void commitAssets(){
//		font26px = game.manager.get("fonts/22px.fnt", BitmapFont.class);
		
		font60px 	= game.manager.get("fonts/" + game.gameTxt.path + "/60px.fnt", BitmapFont.class);
		font54px 	= game.manager.get("fonts/" + game.gameTxt.path + "/54px.fnt", BitmapFont.class);
		font46px 	= game.manager.get("fonts/" + game.gameTxt.path + "/46px.fnt", BitmapFont.class);
		font36px 	= game.manager.get("fonts/" + game.gameTxt.path + "/36px.fnt", BitmapFont.class);
		font36pxWin = game.manager.get("fonts/" + game.gameTxt.path + "/36px-win.fnt", BitmapFont.class);
		font34px 	= game.manager.get("fonts/" + game.gameTxt.path + "/34px.fnt", BitmapFont.class);

		
		font36fs 	= game.manager.get("fonts/" + game.gameTxt.path + "/fs40.fnt", BitmapFont.class);
		font27fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs30.fnt", BitmapFont.class);
		font22fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs25.fnt", BitmapFont.class);
		font62fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs68.fnt", BitmapFont.class);
		font32fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs32.fnt", BitmapFont.class);
		font23fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs23.fnt", BitmapFont.class);
		font20fs    = game.manager.get("fonts/" + game.gameTxt.path + "/fs20.fnt", BitmapFont.class);
		
		
		font60px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font54px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font46px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font36px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font36pxWin.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font34px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		font36fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font27fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font22fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font62fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font32fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font23fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		font20fs.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		
//		font24px = game.manager.get("fonts/24px.fnt", BitmapFont.class);
//		font24px.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

//		fontAuto = game.manager.get("autoplay/autoFnt.fnt", BitmapFont.class);
//		fontAuto.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

//		fontHelp = game.manager.get("fonts/paytable.fnt", BitmapFont.class);
//		fontHelp.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	public void dispose() {
		font34px.dispose();
		font54px.dispose();
//		fontAuto.dispose();
//		fontHelp.dispose();
	}
}
