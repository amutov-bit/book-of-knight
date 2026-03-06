package com.pgd.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pgd.game.BookOfKnight;

public class StatsScreen extends AbstractScreen {
	
	private BitmapFont font;
	private Label totalGamesPlayed;
	private Label totalCoinsIn;
	private Label totalCoinsOut;
	private Label payout;
	private Label freeGames;
	private Label bonusGames;
	private Label hitRate;
	private Table table;
	private int freeGamesEnter;
	private int bonusGamesEnter;
	
	public StatsScreen(BookOfKnight game) {
		super(game);
		freeGamesEnter = 0;
		bonusGamesEnter = 0;
	}

	@Override
	public void draw() {
		for(int i = 0; i < 1000; i++) {
			if(game.context.gameMode == /*GameMode.*/game.context.MAIN_GAME)
			{
				game.controller.startSpin();
				if(game.context.outcome.hasFreeGames){
					game.context.gameMode = game.context.FREE_GAMES;
					freeGamesEnter ++;
					Gdx.app.debug("StatsScreen","game.context.freeGamesCounter = " + game.context.freeGamesCounter +  "game.meters.getTotalCoinsIn(): " + game.meters.getTotalCoinsIn()  +  "game.meters.getTotalCoinsOut(): " + game.meters.getTotalCoinsOut());
				}
				else{
					game.context.gameMode = game.context.MAIN_GAME;
				}
			}
			else if(game.context.gameMode == /*GameMode.*/game.context.FREE_GAMES)
			{
					game.controller.startFGSpin();
					if(game.context.freeGamesCounter == 0)
					{
					}
			}
			
		}
		
		if(game.meters.credit < 100)	game.meters.credit += 100000;
		
		System.gc();
		totalGamesPlayed.setText("" + game.meters.getTotalGamesPlayed());
		totalCoinsIn.setText("" + game.meters.getTotalCoinsIn());
		totalCoinsOut.setText("" + game.meters.getTotalCoinsOut());
//		payout.setText(String.format("%.2f", ((float)game.meters.getTotalCoinsOut()/(float)game.meters.getTotalCoinsIn())*100 ) + " %");
//		freeGames.setText(String.format("%.2f", ((float)game.meters.getTotalGamesPlayed()/(float)freeGamesEnter)));
//		bonusGames.setText(String.format("%.2f", ((float)game.meters.getTotalGamesPlayed()/(float)bonusGamesEnter)));
//		hitRate.setText(String.format("%.2f", ((float)game.meters.getTotalGamesPlayed()/(float)game.meters.getTotalHitRate())));
		
	}
	
	@Override
	public void show() {
	    
	    font = game.fonts.font36px;//new BitmapFont(Gdx.files.internal("fonts/arial22.fnt"));
	    LabelStyle style = new LabelStyle(font, Color.WHITE);
	    
	    totalGamesPlayed = new Label("" + game.meters.getTotalGamesPlayed(), style);
	    totalCoinsIn = new Label("" + game.meters.getTotalCoinsIn(), style);
	    totalCoinsOut = new Label("" + game.meters.getTotalCoinsOut(), style);
	    payout = new Label("%", style);
	    freeGames  = new Label("%", style);
	    bonusGames  = new Label("%", style);
	    hitRate  = new Label("%", style);
	    
	    table = new Table();
	    table.setFillParent(true);
	    stage.addActor(table);
	    
	    table.add(new Label("Total Games Played", style)).minWidth(200);
	    table.add(new Label("Total Coins In", style)).minWidth(200);
	    table.add(new Label("Total Coins Out", style)).minWidth(200);
	    table.row();
	    table.add(totalGamesPlayed);
	    table.add(totalCoinsIn);
	    table.add(totalCoinsOut);    
	    table.row();
	    table.add(new Label("Payout %", style)).minWidth(200);
	    table.add(new Label("FreeGames" , style)).minWidth(200);
	    table.add(new Label("BonusGames" , style)).minWidth(200);
	    table.row();
	    table.add(payout);
	    table.add(freeGames);
	    table.add(bonusGames);
	    table.row();
	    table.add(new Label("Hit Rate", style)).minWidth(200);
	    table.row();
	    table.add(hitRate);
	    
//	    table.addListener(new EventListener() {
//			
//			@Override
//			public boolean handle(Event event) {
//				game.setScreen(new MainScreen(game));
//				return true;
//			}
//		});
	    
	    //stage.setKeyboardFocus(table);
	    
	}

	@Override
	public void dispose() {
		font.dispose();
	    stage.dispose();
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		
	}

}
