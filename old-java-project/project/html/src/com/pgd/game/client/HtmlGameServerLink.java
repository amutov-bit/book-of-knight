package com.pgd.game.client;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Controller.State;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.GameOutcome;
import com.pgd.game.base.Win;
import com.pgd.game.base.Win.Type;
import com.pgd.game.net.IGameServerLink;
import com.pgd.game.screens.StatsScreen;

/**
 * GSParams is an overlay type for JSON encoded server side win 
 * output. Initially used in code/Math.java Win is an item in 
 * a List<Params> which is processed by Controller as part of 
 * game outcome. See also GSOutcome bellow.
 */
class GSParams extends JavaScriptObject {
	protected GSParams() {}
	
	public final native int getMinBetPerLine() /*-{
		return this.MIN_BET_PER_LINE;
	}-*/;
	
	public final native int getMinLines() /*-{
		return this.MIN_LINES;
	}-*/;
	
	public final native int getMinDenom() /*-{
		return this.MIN_DENOM;
	}-*/;

	public final native int getMaxBetPerLine() /*-{
		return this.MAX_BET_PER_LINE;
	}-*/;
	
	public final native int getMaxLines() /*-{
		return this.MAX_LINES;
	}-*/;
	
	public final native int getMaxDenom() /*-{
		return this.MAX_DENOM;
	}-*/;
	
	public final native int getLinesSelected() /*-{
		return this.linesSelected;
	}-*/;
	
	public final native int getBetPerLine() /*-{
		return this.betPerLine;
	}-*/;
	
	public final native int freeGamesCnt() /*-{
		return this.freeGamesCnt;
	}-*/;
	
	public final native int getDenom() /*-{
		return this.denom;
	}-*/;
	


	public final native boolean getLocked() /*-{
		return this.locked;
	}-*/;
	
	public final native boolean hasAddFreeGames() /*-{
		return this.hasAddFreeSpins;
	}-*/;
	
	
	public final native int getAddFreeGamesCnt() /*-{
	return this.addFreeSpinsCnt;
}-*/;

public final native boolean showAddFreeSpins() /*-{
	return this.showAddFreeSpins;
}-*/;
	
	public final native String getCurrency() /*-{
		return this.currency;
	}-*/;
	
	public final native JsArrayInteger getSelectedBoostLines() /*-{
		return this.selectedBoostLines;
	}-*/;
	
	public final native int getHasK() /*-{
		return this.hasK;
	}-*/;
	
	public final native String getPattern() /*-{
		return this.pattern;
	}-*/;
	
	public final native int hasTurboSpins() /*-{
		return this.hasTurboSpins;
	}-*/;

	public final native int hasBuyFeature() /*-{
		return this.hasBuyFeature;
	}-*/;

	public final native int hasReloadButton() /*-{
		return this.hasReloadButton;
	}-*/;

	public final native int hasHomeButton() /*-{
		return this.hasHomeButton;
	}-*/;
	
	public final native int hasHistoryButton() /*-{
		return this.hasHistory;
	}-*/;
	
	public final native int hasLobbyButton() /*-{
		return this.hasLobbyButton;
	}-*/;
	
	public final native String getGamePercent() /*-{
		return this.gamePercent;
	}-*/;
	
	public final native String getGamePercentBuyFree() /*-{
		return this.gamePercentBuyFree;
	}-*/;

	public final native String getGamePercentBuyHold() /*-{
		return this.gamePercentBuyHold;
	}-*/;
	
	public final native int skipIntro() /*-{
		return this.skipIntro;
	}-*/;

	public final native int skipScreen() /*-{
		return this.skipScreen;
	}-*/;

	public final native int turboGame() /*-{
		return this.turboGame;
	}-*/;
	
	public final native int getBuyFreeGamesMult() /*-{
		return this.buyFreeGamesMult;
	}-*/;

	public final native int getBuyHoldAndWinMult() /*-{
		return this.buyHoldAndWinMult;
	}-*/;
	
}


/**
 * GSWin is an overlay type for JSON encoded server side win 
 * output. Initially used in code/Math.java Win is an item in 
 * a List<Win> which is processed by Controller as part of 
 * game outcome. See also GSOutcome bellow.
 */
class GSWin extends JavaScriptObject {

	protected GSWin() {}

	public final native int getSymbol() /*-{
		return this.symbol;
	}-*/;
	
	public final native int getCnt() /*-{
		return this.cnt;
	}-*/;	
	
	public final native int getMult() /*-{
		return this.mult;
	}-*/;	
	
	public final native int getType() /*-{
		return this.type;
	}-*/;		
	
	public final native boolean getHasWild() /*-{
		return this.hasWild;
	}-*/;		
	
	public final native int getWinningLine() /*-{
		return this.winningLine;
	}-*/;	

	public final native int betPerLine() /*-{
		return this.betPerLine;
	}-*/;	
	
	public final native int denom() /*-{
		return this.denom;
	}-*/;	
	
	public final native int getBet() /*-{
		return this.bet;
	}-*/;		
	
	public final native int getWin() /*-{
		return this.win;
	}-*/;		
	
	public final native JsArray<JsArrayInteger> getHighlight() /*-{
		return this.highlight;
	}-*/;
	
	public final native int getMultCnt() /*-{
		return this.multCnt;
	}-*/;

}

class GSOutcome extends JavaScriptObject {

	protected GSOutcome() {}

	public final native int idJackpot() /*-{
		return this.idJackpot;
	}-*/;
	
	public final native int getJackpotValue() /*-{
		return this.value;
	}-*/;
		
	public final native boolean getJackpotHit() /*-{
		return this.hit;
	}-*/;

	public final native int getWin() /*-{
		return this.win;
	}-*/;
	
	public final native int getMode() /*-{
		return this.mode;
	}-*/;
	
	public final native int getWildCount() /*-{
		return this.wildCount;
	}-*/;

	public final native int getRespinCnt() /*-{
		return this.respinCnt;
	}-*/;

	public final native JsArrayInteger getHighlightMagician() /*-{
		return this.highlightsMagician;
	}-*/;

	public final native JsArrayInteger getReelMultiplier() /*-{
		return this.reelMultiplier;
	}-*/;

	
	public final native int getFreeGames() /*-{
		return this.freeGames;
	}-*/;

	public final native int getFreeGamesCnt() /*-{
		return this.freeGamesCnt;
	}-*/;

	public final native int getHoldAndWinCnt() /*-{
		return this.holdAndWinCnt;
	}-*/;

	public final native int getBuyFreeGamesMult() /*-{
		return this.buyFreeGamesMult;
	}-*/;

	public final native int getBuyHoldAndWinMult() /*-{
		return this.buyHoldAndWinMult;
	}-*/;

	public final native int maxFreeGamesCnt() /*-{
		return this.allFreeGamesCnt;
	}-*/;

	public final native int addFreeGamesCnt() /*-{
		return this.addFreeSpinsCnt;
	}-*/;
	
	public final native int getAddFreeSpinsWinWin() /*-{
	return this.addFreeSpinsWin;
}-*/;


public final native int getAddFreeSpinsTotalCnt() /*-{
	return this.addFreeSpinsTotalCnt;
}-*/;

public final native boolean hasAddFreeSpins() /*-{
	return this.hasAddFreeSpins;
}-*/;

	public final native int fgMultiplier() /*-{
		return this.fgMultiplier;
	}-*/;

	public final native int multiplier() /*-{
		return this.multiplier;
	}-*/;
	
	public final native int getFGWin() /*-{
		return this.fgWin;
	}-*/;

	public final native boolean getHasWild() /*-{
		return this.hasWild;
	}-*/;

	public final native int magicianReel() /*-{
		return this.magicianReel;
	}-*/;

	public final native int mysterySymbol() /*-{
		return this.mysterySymbol;
	}-*/;
	
	public final native boolean hasFreeGames() /*-{
		return this.hasFreeGames;
	}-*/;

	public final native boolean hasHoldAndWin() /*-{
		return this.hasHoldAndWin;
	}-*/;

	public final native boolean hasRespin() /*-{
		return this.hasRespin;
	}-*/;

	public final native boolean hasSuperGames() /*-{
		return this.hasSuperGames;
	}-*/;

	public final native int bonusCashMultiplier() /*-{
		return this.bonusCashMultiplier;
	}-*/;

	public final native int bonusCashWin() /*-{
		return this.bonusCashWin;
	}-*/;
	
	
	public final native JsArrayInteger coctailsWins() /*-{
		return this.coctailsWins;
	}-*/;
	
	public final native boolean hasBonusCashWin() /*-{
		return this.hasBonusCashWin;
	}-*/;

	public final native boolean hasTransformAnim() /*-{
		return this.hasTransformAnim;
	}-*/;
	
	public final native int bonusCashSpins() /*-{
		return this.bonusCashSpins;
	}-*/;

	public final native boolean hasCoctail() /*-{
		return this.hasCoctail;
	}-*/;

	public final native int coctail() /*-{
		return this.coctail;
	}-*/;

	public final native boolean hasBonusWin() /*-{
		return this.hasBonusWin;
	}-*/;
	
	public final native int getBalance() /*-{
		return this.balance;
	}-*/;

	public final native JsArray<JsArrayInteger> getMatrix() /*-{
		return this.matrix;
	}-*/;

	public final native JsArray<JsArrayInteger> getMatrixHoldAndWin() /*-{
		return this.matrixHoldAndWin;
	}-*/;

	public final native JsArray<JsArrayInteger> getMatrixHoldAndWinValues() /*-{
		return this.matrixHoldAndWinValues;
	}-*/;

	public final native int roundId() /*-{
		return this.roundId;
	}-*/;
	
	public final native JsArray<GSWin> getWins() /*-{
		return this.wins;
	}-*/;	
	
	public final native JsArrayInteger getSelectedBoostLines() /*-{
		return this.selectedBoostLines;
	}-*/;

	public final native JsArrayInteger getSelectedBoostSymbol() /*-{
		return this.selectedBoostSymbol;
	}-*/;
	
	public final native boolean maxWinReached() /*-{
		return this.maxWinReached;
	}-*/;
	
	public final native int maxWin() /*-{
		return this.maxWin;
	}-*/;

	public final native int specialSymbol() /*-{
		return this.specialSymbol;
	}-*/;
}

class GSJackpot extends JavaScriptObject {

	protected GSJackpot() {
		
	}
	
	public final native JsArrayInteger getJackpots() /*-{
		
		var jp = [];
		//		var jp = [ 1, 1234
//		 		   2, 567
//		 		   3, 8910
//		 		   4, 123 ];

		for (var k in this.jps) {
			jp.push(Number(k));
			jp.push(this.jps[k]);
		}
		
		
//		console.dir(" Update : " + jp);
		
		return jp;
	}-*/;
	
	public final native JsArrayInteger getJackpotsStarts() /*-{
	
//		var jp = [ 1, 1234
//		 		   2, 567
//		 		   3, 8910
//		 		   4, 123 ];

		var jp = [];
	
		var index = 0;
		for (var k in this.jps) {
			jp.push(Number(k));
			jp.push(this.jps[k]);
		}
//		console.log("getJackpotsStarts" +  jp);
		return jp;
	}-*/;
	
	public final native JsArrayInteger getMinHits() /*-{
	
		var jp = [];
	
		for (var k in this.minHits) {
			jp.push(Number(k));
			jp.push(this.minHits[k]);
		}
	
		return jp;
	}-*/;

	public final native JsArrayInteger getMaxHits() /*-{
	
		var jp = [];
	
		for (var k in this.maxHits) {
			jp.push(Number(k));
			jp.push(this.maxHits[k]);
		}
	
		return jp;
	}-*/;
	
	public final native JsArrayInteger getResetHits() /*-{
	
		var jp = [];
	
		for (var k in this.resetHits) {
			jp.push(Number(k));
			jp.push(this.resetHits[k]);
		}
	
		return jp;
	}-*/;


	
	public final native JsArrayInteger getMinHitsStarts() /*-{
		var jp = [];
	
		var index = 0;
		for (var k in this.minHits) {
			jp.push(Number(k));
			jp.push(this.minHits[k]);
		}

		return jp;
	}-*/;
	
	public final native JsArrayInteger getMaxHitsStarts() /*-{
		var jp = [];
	
		var index = 0;
		for (var k in this.maxHits) {
			jp.push(Number(k));
			jp.push(this.maxHits[k]);
		}
	
		return jp;
	}-*/;
	
	public final native JsArrayInteger getResetHitsStarts() /*-{
		var jp = [];
	
		var index = 0;
		for (var k in this.resetHits) {
			jp.push(Number(k));
			jp.push(this.resetHits[k]);
		}
	
		return jp;
	}-*/;

}
/**
 * Websocket IGameServerLink implementation
 * @author dimitar
 *
 */
public class HtmlGameServerLink implements IGameServerLink {
	
	BookOfKnight game;
	
	HtmlGameServerLink(BookOfKnight game) {
		this.game = game;
		consoleLog("HtmlGameServer instance created");
	}

	@Override
	public native boolean connect(String url, String uid) /*-{

		$wnd.client.connect();
		
		var onConnectCallback = this.@com.pgd.game.client.HtmlGameServerLink::onConnect();
		$wnd.client.onConnectCallback = onConnectCallback;

		var onCloseCallback = this.@com.pgd.game.client.HtmlGameServerLink::onDisconnect();
		$wnd.client.onCloseCallback = onCloseCallback;

		var that = this;
		$wnd.client.onLoginCallback = function(balance, params, demo, jackpots, lastgame){
    		that.@com.pgd.game.client.HtmlGameServerLink::onLogin(ILcom/google/gwt/core/client/JavaScriptObject;ILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(balance, params, demo, jackpots, lastgame);
		} 

		$wnd.client.onLangCallback = function(lang){
    		that.@com.pgd.game.client.HtmlGameServerLink::onSetLang(Ljava/lang/String;)(lang);
		} 
		
		$wnd.client.onSpinCallback = function(outcome){
			that.@com.pgd.game.client.HtmlGameServerLink::onSpin(Lcom/google/gwt/core/client/JavaScriptObject;)(outcome);
		} 
		
		$wnd.client.onJackpotCallback = function(jps){
			that.@com.pgd.game.client.HtmlGameServerLink::onJackpotUpdate(Lcom/google/gwt/core/client/JavaScriptObject;)(jps);
		} 
		
		$wnd.client.onBonusCallback = function(outcome){
			that.@com.pgd.game.client.HtmlGameServerLink::onBonus(Lcom/google/gwt/core/client/JavaScriptObject;)(outcome);
		} 
		
		$wnd.client.onErrorCallback = function(err){
			that.@com.pgd.game.client.HtmlGameServerLink::onError(I)(err);
		}
		
//		$wnd.client.closeHowToPlayCallback = function(){
//			that.@com.pgd.game.client.HtmlGameServerLink::hideHowToPlay();
//		} 
		
		
		var closeHowToPlayCallback = this.@com.pgd.game.client.HtmlGameServerLink::hideHowToPlay();
		$wnd.client.closeHowToPlayCallback = closeHowToPlayCallback;
		

		return true;
	}-*/;	

	native public void login() /*-{
		$wnd.client.login();
	}-*/;
	
	
	@Override
	native public boolean spin() /*-{
		$wnd.client.spin();
		return true;
	}-*/;	

	@Override
	native public boolean spinAddFreeGames() /*-{
		$wnd.client.freespins();
		return true;
	}-*/;	
	
	@Override
	native public boolean bonus(int bonusWinPosition) /*-{
		$wnd.client.bonus();
		return true;
	}-*/;
	
	@Override
	native public void setBonusParams(int bonusWinPosition, int betPerLine, int linesSelected, int denom) /*-{
		$wnd.client.gameBonusParams.bonusChoose = bonusWinPosition;
		$wnd.client.gameBonusParams.betPerLine = betPerLine;
		$wnd.client.gameBonusParams.linesSelected = linesSelected;
		$wnd.client.gameBonusParams.denom = denom;
		return true;
	}-*/;
	
	@Override
	native public boolean freespin() /*-{
		$wnd.client.freespin();
		return true;
	}-*/;	

	@Override
	native public boolean setSettings(int skipIntro, int skipScreen, int turboGame) /*-{
		$wnd.client.gameParams.skipIntro  = skipIntro;
		$wnd.client.gameParams.skipScreen = skipScreen;
		$wnd.client.gameParams.turboGame  = turboGame;
		return true;
	}-*/;

	@Override
	native public void setParamsBuyFreeGames(int betPerLine, int linesSelected, int denom, boolean hasBuyBonus, int buyBonusType) /*-{
		$wnd.client.gameParams.betPerLine = betPerLine;
		$wnd.client.gameParams.linesSelected = linesSelected;
		$wnd.client.gameParams.denom = denom;
		$wnd.client.gameParams.hasBuyBonus  = hasBuyBonus;
		$wnd.client.gameParams.buyBonusType = buyBonusType;
	}-*/;
	
	@Override
	native public boolean setParams(int betPerLine, int linesSelected, int denom) /*-{
		$wnd.client.gameParams.betPerLine = betPerLine;
		$wnd.client.gameParams.linesSelected = linesSelected;
		$wnd.client.gameParams.denom = denom;
		$wnd.client.gameParams.hasBuyBonus  = false;
		$wnd.client.gameParams.buyBonusType = -1;
		return true;
	}-*/;
	
	@Override
	native public boolean disconnect() /*-{
		$wnd.client.disconnect();
		return false;
	}-*/;
	
	@Override
	native public void onConnect() /*-{
		$wnd.client.login();
	}-*/;
	
	@Override
	public void onDisconnect() {
		consoleLog("onDisconnect()");
	}	
	
	public void hideHowToPlay() {
//		game.helpScreen.hideHowToPlay();
	}
	
	@Override
	public void onMessage() {
		consoleLog("Got message from GameServer! ");
	}
	
	int once = 0;
	
	int currentAnimFrame[] = {0, 0, 0 ,0};
	int realAnimFrame[] = {0, 0, 0 ,0};
	
	int currentMult = 0;
	
	boolean fisrtTime = false;
	/**
	 * This function will receive game result from server
	 * and parse it fill game.context.outcome 
	 * @param outcome - JNSI overlayed object
	 */
	public void onJackpotUpdate(JavaScriptObject jps) {}
	
	/**
	 * This function will receive game result from server
	 * and parse it fill game.context.outcome 
	 * @param outcome - JNSI overlayed object
	 */
	public void onSpin(JavaScriptObject outcome) {
		consoleLog("onSpin()");
		
		try{
			GameOutcome gameOutcome = new GameOutcome();
			game.context.outcome = null;
			game.context.outcome = gameOutcome;
			
			
			int balance = ((GSOutcome)outcome).getBalance();
			game.meters.credit = (long) balance;
			
			console(" htmlserver game.meters.credit = " + game.meters.credit);
			JsArray<JsArrayInteger> m = ((GSOutcome)outcome).getMatrix();
			JsArray<JsArrayInteger> mHoldAndWin = ((GSOutcome)outcome).getMatrixHoldAndWin();
			JsArray<JsArrayInteger> mHoldAndWinValues = ((GSOutcome)outcome).getMatrixHoldAndWinValues();
			
			
	//		consoleLog("" + m);
			// Get reel symbols matrix and fill reels and game outcome
			// matrixes respectively
			
			for(int r = 0; r < game.REELS; r++ ) {
				for(int s = 0; s < game.SYMBOLS; s++ ) {
					game.reels.matrix[s][r] = m.get(s).get(r);
					game.reels.matrixHoldAndWin[s][r] = mHoldAndWin.get(s).get(r);
					game.reels.matrixJackpotDigits[s][r] = mHoldAndWinValues.get(s).get(r);
					gameOutcome.matrix[s][r] = m.get(s).get(r);					
				}
			}		
			
			game.context.roundId = ((GSOutcome)outcome).roundId();
			game.menu.setRoundId(game.context.roundId);
			
			game.context.hasWild = false;
			
//			game.reels.matrix[5][5] = 123;
				
			// Extract game server wins and populate game outcome 
			// for Controller to process
			JsArray<GSWin> gswins = ((GSOutcome)outcome).getWins();
			
			boolean stopNearMiss = false;
			
			Win win;
			gameOutcome.wins = new ArrayList<Win>();
			gameOutcome.winsSpecial = new ArrayList<Win>();
			
			game.context.outcome.hasBonusWin = ((GSOutcome)outcome).hasBonusWin();
			
			game.context.outcome.hasFreeGames = ((GSOutcome)outcome).hasFreeGames();

			game.context.outcome.hasHoldAndWin = ((GSOutcome)outcome).hasHoldAndWin();
	
			game.context.gameMode = ((GSOutcome)outcome).getMode();
			
			game.context.freeGamesWon = ((GSOutcome)outcome).maxFreeGamesCnt();
			
			game.context.freeGamesCounter = ((GSOutcome)outcome).getFreeGamesCnt();

			game.context.holdAndWinCounter = ((GSOutcome)outcome).getHoldAndWinCnt();

			if(game.context.outcome.hasHoldAndWin){
				game.context.holdAndWinGamesWon = ((GSOutcome)outcome).getHoldAndWinCnt();
			}
	
			
			game.context.buyFreeGamesMult   = ((GSOutcome)outcome).getBuyFreeGamesMult();
			game.context.buyHoldAndWinMult  = ((GSOutcome)outcome).getBuyHoldAndWinMult();
			
	//		consoleLog("game.context.freeGamesCounter = " + game.context.freeGamesCounter);
	//		consoleLog("game.context.freeGamesWon = " + game.context.freeGamesWon);
//			consoleLog("game.context.freeGamesCounterOnScatter = " + game.context.freeGamesCounterOnScatter);
			
			game.meters.currentWin = ((GSOutcome)outcome).getWin();
			
	//		game.context.outcome.freeGamesMultiplier  = ((GSOutcome)outcome).fgMultiplier();
			
			game.meters.holdAndWin = ((GSOutcome)outcome).getWin();

			if((game.context.gameMode == game.context.FREE_GAMES) || game.context.outcome.hasFreeGames)
			{
				game.meters.fgwin = ((GSOutcome)outcome).getFGWin();
			} else {
				currentMult = 0;
			}
			
			
			
			
			game.context.hasAddFreeSpins = ((GSOutcome)outcome).hasAddFreeSpins();
			
			if(game.context.hasAddFreeSpins){
				game.context.addFreeSpinsCnt = ((GSOutcome)outcome).addFreeGamesCnt();
				game.context.addFreeSpinsWin = ((GSOutcome)outcome).getAddFreeSpinsWinWin();
				game.context.addFreeSpinsTotalCnt = ((GSOutcome)outcome).getAddFreeSpinsTotalCnt();
			}
			
			game.context.maxWinReached = ((GSOutcome)outcome).maxWinReached();
			game.context.maxWin = ((GSOutcome)outcome).maxWin();
			
			if(game.context.maxWinReached){
				game.meters.fgwin = game.context.maxWin;
				game.meters.holdAndWin = game.context.maxWin;
			}
	
			game.context.specialSymbol = ((GSOutcome)outcome).specialSymbol();
			
	//		game.overlay.freegamesText.setWin(game.context.addWild);
			
	//		game.overlay.addWild.setWin(game.context.addWild);
			
			for(int i = 0; i < gswins.length(); i++ ) {
	
				win = new Win();
				
				win.winningLine = gswins.get(i).getWinningLine();
				win.betPerLine = gswins.get(i).betPerLine() * gswins.get(i).denom();
				win.symbol = gswins.get(i).getSymbol();
				win.cnt = gswins.get(i).getCnt();
				win.mult = gswins.get(i).getMult();
				win.multCnt = gswins.get(i).getMultCnt();
				win.hasWild = gswins.get(i).getHasWild();
				
				
				win.highlightTimeout = 10; // default line highlighting duration
	
				switch(gswins.get(i).getType()){
					case 0: // line wins
						win.type = Type.LINE;
						win.highlightTimeout = 70;
						gameOutcome.hasPaytableWins = true;
						gameOutcome.hasWin = true;
						stopNearMiss = true;
						switch (win.symbol) {
							default: 
								if(win.symbol == 9){
									win.sound = SoundTrack.HIGH_WIN; 
								} else {
									win.sound = (win.hasWild) ?  SoundTrack.LOW_WIN : SoundTrack.LOW_WIN; 
								}
								win.highlightTimeout = 70; 
							break;
						}				
					break;
					case 1: // wilds only
						win.type = Type.WILD;
						gameOutcome.hasPaytableWins = true;
						gameOutcome.hasWin = false;
						gameOutcome.hasWild = true;
//     					win.sound = SoundTrack.WILD;
						win.highlightTimeout = 70; 
					break;
						
					case 2: // scatter
					case 3: // near miss scatter
						win.type = gswins.get(i).getType()==2? Type.SCATTER : Type.NEAR_MISS;
//						gameOutcome.hasWin = false;//(gswins.get(i).getType()==2);
//						gameOutcome.hasPaytableWins = false;
						win.hasWild = gswins.get(i).getHasWild();

						win.highlightTimeout = 70;
						
						win.sound = SoundTrack.SCATTER; 
						break;
					case 4:
						win.type = Type.BONUS;
						gameOutcome.hasWin = true;
						gameOutcome.hasPaytableWins = true;
						win.highlightTimeout = 15;
						win.sound = SoundTrack.HIGH_WIN; 
					break;
					case 5:
						win.type = Type.SPECIAL_LINE;
						win.highlightTimeout = 40;
						gameOutcome.hasSpecialWins = true;
						stopNearMiss = true;
						
						game.context.specialSymbol = win.symbol;
						
						switch (win.symbol) {
						default: 
//							win.sound = win.hasWild ? SoundTrack.WILD : SoundTrack.WIN;
							win.highlightTimeout = 60; 
							break;
						}						
						
						gameOutcome.winsSpecial.add(win);
						break;
					default: 
						win.type = Type.NEAR_MISS;
						win.highlightTimeout = 140;
						break;
				}
				
				JsArray<JsArrayInteger> highlights = gswins.get(i).getHighlight();
				
				JsArrayInteger highlightsMagician = ((GSOutcome)outcome).getHighlightMagician();
				
				int respinCnt = ((GSOutcome)outcome).getRespinCnt();
				
				for(int r = 0; r < game.REELS; r++ ) {
					for(int s = 0; s < game.SYMBOLS; s++ ) {
						win.highlight[s][r] = highlights.get(s).get(r);
					}								
				}
				
				
				if(win.type != Type.NEAR_MISS && win.type != Type.SPECIAL_LINE)	 gameOutcome.wins.add(win);
				
			}	
	
			int countScatter 	   = 0;
			int countJackpotSymbol = 0;
			
			game.context.symbolReelHighlight = -1;
			game.context.firstReelHighlight = -1;
			game.context.hasReelHighlight = false;
			
			if(game.context.gameMode == game.context.MAIN_GAME){
				for(int r = 0; r < game.REELS; r++ ) {
					for(int s = 0; s < game.SYMBOLS; s++ ) {
						if(game.reels.matrix[s][r] == game.math.SCATTER){
							countScatter++;
						}
	
						if(game.reels.matrix[s][r] >= game.math.JACKPOT_SYMBOL){
							countJackpotSymbol++;
						}
					}
					
					if(countScatter >= 2){
						game.context.symbolReelHighlight = game.math.SCATTER;
						game.context.firstReelHighlight = r+1;
						game.context.hasReelHighlight = true;
						r =  game.REELS;
					}
	
					if(countJackpotSymbol >= 3){
						game.context.symbolReelHighlight = game.math.JACKPOT_SYMBOL;
						game.context.firstReelHighlight = r+1;
						game.context.hasReelHighlight = true;
						r =  game.REELS;
					}
				}
			}
			
			if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES){
				game.reels.updateStopSymbolsHoldAndWin();
			} else {
				game.reels.updateStopSymbols();
			}
			
			game.controller.processGSOutcome();
	
			game.spinIsEnded = true;
		} catch (Exception e) {
				// TODO: handle exception
			consoleLog("errror = " + e);
			error();
			
		}
	}
	
	
	/**
	 * This function will receive game result from server
	 * and parse it fill game.context.outcome 
	 * @param outcome - JNSI overlayed object
	 */
	public void onBonus(JavaScriptObject outcome) {
	}

	public void onSetLang(String lang) {
		game.gsLink.console("lang = " + lang);
		game.languageCode = lang;
	}

	public void onLogin(int balance, JavaScriptObject params, int demo, JavaScriptObject jps, JavaScriptObject lastgame) {
		game.meters.credit = (long) balance;
		game.DEMO_MODE = (demo==0) ? false : true; 
		
		game.context.addFreeSpinsCnt = ((GSParams)params).getAddFreeGamesCnt();
		game.context.showAddFreeSpins= ((GSParams)params).showAddFreeSpins();
		game.context.hasAddFreeSpins = ((GSParams)params).hasAddFreeGames();
		
		game.meters.setMinDenom(((GSParams)(params)).getMinDenom());
		game.meters.setMinBetPerLine(((GSParams)(params)).getMinBetPerLine());
		game.meters.setMinLines(((GSParams)(params)).getMinLines());

		game.meters.setDenom(((GSParams)(params)).getDenom());
		game.meters.setBetPerLine(((GSParams)(params)).getBetPerLine());
		game.meters.setLines(((GSParams)(params)).getLinesSelected());
		
		game.meters.setMaxDenom(((GSParams)(params)).getMaxDenom());
		game.meters.setMaxBetPerLine(((GSParams)(params)).getMaxBetPerLine());
		game.meters.setMaxLines(((GSParams)(params)).getMaxLines());

		game.meters.setCurrency(((GSParams)(params)).getCurrency());
		
		game.locked = ((GSParams)(params)).getLocked();		
		
		game.context.turboSpinIsEnabled = ((GSParams)(params)).hasTurboSpins() == 0 ? false : true;		
		game.context.hasBuyFeature 		= ((GSParams)(params)).hasBuyFeature() == 0 ? false : true;		
		game.context.hasReloadButton	= ((GSParams)(params)).hasReloadButton() == 0 ? false : true;		
		game.context.hasHomeButton		= ((GSParams)(params)).hasHomeButton() == 0 ? false : true;
		game.context.hasHistoryButton	= ((GSParams)(params)).hasHistoryButton() == 0 ? false : true;
		game.context.hasLobbyButton		= ((GSParams)(params)).hasLobbyButton() == 0 ? false : true;

		game.context.gamePercent		= ((GSParams)(params)).getGamePercent();
		game.context.gamePercentBuyFree	= ((GSParams)(params)).getGamePercentBuyFree();
		game.context.gamePercentBuyHold	= ((GSParams)(params)).getGamePercentBuyHold();
		
		game.context.buyFreeGamesMult   = ((GSParams)(params)).getBuyFreeGamesMult();
		game.context.buyHoldAndWinMult  = ((GSParams)(params)).getBuyHoldAndWinMult();
				
		if(((GSParams)(params)).getHasK() >= 0){
			game.hasBigNumberK =  ((GSParams)(params)).getHasK();
		}

		if(((GSParams)(params)).getPattern() != null){
			game.pattern = ((GSParams)(params)).getPattern();
		}
		
		if(!game.context.showAddFreeSpins){
			if(!isEmpty(lastgame)){
				
				game.context.skipIntro  = ((GSParams)(params)).skipIntro() == 0 ? false : true;		
				game.context.skipScreen = ((GSParams)(params)).skipScreen() == 0 ? false : true;		
				game.context.turboGame	= ((GSParams)(params)).turboGame() == 0 ? false : true;		
				game.restore = true;
				restoreLastGame(lastgame);
			}
		}
		
		
		game.login 		= true;
		
		if(game.showButtons){
			
			game.menu.updateMeters();
			
			game.menu.setRoundId(game.context.roundId);

			
			if(game.gameAssetsManager.isSoundChoosed == false){
				game.gameAssetsManager.menuSound.setVisible(true);
				
				game.gameAssetsManager.bgSound.setVisible(true);
			}
			
			if(game.restore){
				game.controller.setNextState(State.RESTORE_STATE);
			}
		}		
	}
	
	
	public void restoreLastGame(JavaScriptObject outcome){
		GameOutcome gameOutcomeRestore = new GameOutcome();
		game.context.outcome = null;
		game.context.outcome = gameOutcomeRestore;
		
//		JsArray<JsArrayInteger> m = ((GSOutcome)outcome).getMatrix();
//		
//		for(int r = 0; r < game.REELS; r++ ) {
//			for(int s = 0; s < game.SYMBOLS; s++ ) {
//				game.reels.matrix[s][r] = m.get(s).get(r);
//				gameOutcomeRestore.matrix[s][r] = m.get(s).get(r);
//			}
//		}		
		
		console(" htmlserver game.meters.credit = " + game.meters.credit);
		JsArray<JsArrayInteger> m = ((GSOutcome)outcome).getMatrix();
		JsArray<JsArrayInteger> mHoldAndWin = ((GSOutcome)outcome).getMatrixHoldAndWin();
		JsArray<JsArrayInteger> mHoldAndWinValues = ((GSOutcome)outcome).getMatrixHoldAndWinValues();
		
		
//		consoleLog("" + m);
		// Get reel symbols matrix and fill reels and game outcome
		// matrixes respectively
		
		for(int r = 0; r < game.REELS; r++ ) {
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				game.reels.matrix[s][r] = m.get(s).get(r);
				game.reels.matrixHoldAndWin[s][r] = mHoldAndWin.get(s).get(r);
				game.reels.matrixJackpotDigits[s][r] = mHoldAndWinValues.get(s).get(r);
				gameOutcomeRestore.matrix[s][r] = m.get(s).get(r);					
			}
		}
		
		game.context.roundId = (long) ((GSOutcome)outcome).roundId();

		game.context.hasWild = false;

		JsArrayInteger selectedLines = ((GSOutcome)outcome).getSelectedBoostLines();
		JsArrayInteger selectedSymbols = ((GSOutcome)outcome).getSelectedBoostSymbol();
		
		// Extract game server wins and populate game outcome 
		// for Controller to process
		JsArray<GSWin> gswins = ((GSOutcome)outcome).getWins();
		
		Win win;
		gameOutcomeRestore.wins = new ArrayList<Win>();
		
		game.context.gameMode = ((GSOutcome)outcome).getMode();
		
		game.context.outcome.hasBonusWin = ((GSOutcome)outcome).hasBonusWin();
		
		game.context.outcome.hasFreeGames = ((GSOutcome)outcome).hasFreeGames();

		game.context.outcome.hasHoldAndWin = ((GSOutcome)outcome).hasHoldAndWin();

		game.context.gameMode = ((GSOutcome)outcome).getMode();
		
		game.context.freeGamesWon = ((GSOutcome)outcome).maxFreeGamesCnt();
		
		game.context.freeGamesCounter = ((GSOutcome)outcome).getFreeGamesCnt();

		game.context.holdAndWinCounter = ((GSOutcome)outcome).getHoldAndWinCnt();

		if(game.context.outcome.hasHoldAndWin){
			game.context.holdAndWinGamesWon = ((GSOutcome)outcome).getHoldAndWinCnt();
		}

		
		game.context.buyFreeGamesMult   = ((GSOutcome)outcome).getBuyFreeGamesMult();
		game.context.buyHoldAndWinMult  = ((GSOutcome)outcome).getBuyHoldAndWinMult();
		
		if((game.context.gameMode == game.context.FREE_GAMES) || game.context.outcome.hasFreeGames)
		{
			game.meters.fgwin = ((GSOutcome)outcome).getFGWin();
		} else {
			currentMult = 0;
		}
		
		game.meters.currentWin = ((GSOutcome)outcome).getWin();
		
		game.meters.holdAndWin = ((GSOutcome)outcome).getWin();

		game.context.hasWild = ((GSOutcome)outcome).getHasWild();
				
		if(game.context.hasAddFreeSpins){
			game.context.addFreeSpinsCnt = ((GSOutcome)outcome).addFreeGamesCnt();
			game.context.addFreeSpinsWin = ((GSOutcome)outcome).getAddFreeSpinsWinWin();
		}
		
		game.context.maxWinReached = ((GSOutcome)outcome).maxWinReached();
		game.context.maxWin = ((GSOutcome)outcome).maxWin();
		
		if(game.context.maxWinReached){
			game.meters.fgwin = game.context.maxWin;
//			game.meters.respinwin = game.context.maxWin;
		}
		
		game.context.specialSymbol = ((GSOutcome)outcome).specialSymbol();
		
		for(int i = 0; i < gswins.length(); i++ ) {

			win = new Win();
			win.winningLine = gswins.get(i).getWinningLine();
			win.betPerLine = gswins.get(i).betPerLine();
			win.symbol = gswins.get(i).getSymbol();
			win.cnt = gswins.get(i).getCnt();
			win.mult = gswins.get(i).getMult();
			win.multCnt = gswins.get(i).getMultCnt();

			win.hasWild = gswins.get(i).getHasWild();
			win.highlightTimeout = 10; // default line highlighting duration


			
			switch(gswins.get(i).getType()){
				case 0: // line wins
					win.type = Type.LINE;
					win.highlightTimeout = 40;
					gameOutcomeRestore.hasPaytableWins = true;
					gameOutcomeRestore.hasWin = true;
					switch (win.symbol) {
						default: 
							if(win.symbol == 9){
								win.sound = SoundTrack.HIGH_WIN; 
							} else {
								win.sound = (win.hasWild) ?  SoundTrack.LOW_WIN : SoundTrack.LOW_WIN; 
							}
							win.highlightTimeout = 90; 
							break;
						}				
					break;
				case 1: // wilds only
					win.type = Type.WILD;
					gameOutcomeRestore.hasPaytableWins = true;
					gameOutcomeRestore.hasWin = false;
					gameOutcomeRestore.hasWild = true;
// 					win.sound = SoundTrack.WILD;
					win.highlightTimeout = 70; 
				break;
					
				case 2: // scatter
				case 3: // near miss scatter
					win.type = gswins.get(i).getType()==2? Type.SCATTER : Type.NEAR_MISS;
//					gameOutcome.hasWin = false;//(gswins.get(i).getType()==2);
//					gameOutcome.hasPaytableWins = false;
					win.hasWild = gswins.get(i).getHasWild();

					win.highlightTimeout = 70;
					
					win.sound = SoundTrack.SCATTER; 
					break;
				case 4:
					win.type = Type.BONUS;
					gameOutcomeRestore.hasWin = true;
					gameOutcomeRestore.hasPaytableWins = true;
					win.highlightTimeout = 15;
					win.sound = SoundTrack.HIGH_WIN; 
				break;
				case 5:
					win.type = Type.SPECIAL_LINE;
					win.highlightTimeout = 40;
					gameOutcomeRestore.hasSpecialWins = true;

					game.context.specialSymbol = win.symbol;
					
					switch (win.symbol) {
					default: 
//						win.sound = win.hasWild ? SoundTrack.WILD : SoundTrack.WIN;
						win.highlightTimeout = 60; 
						break;
					}						
					
					gameOutcomeRestore.winsSpecial.add(win);
					break;
					
				default: 
					win.type = Type.NEAR_MISS;
					win.highlightTimeout = 140;
					break;
			}
			
			JsArray<JsArrayInteger> highlights = gswins.get(i).getHighlight();
			
			JsArrayInteger highlightsMagician = ((GSOutcome)outcome).getHighlightMagician();
			
			int respinCnt = ((GSOutcome)outcome).getRespinCnt();
			
//			for(int r = 0; r < game.REELS; r++ ) {
//				for(int s = 0; s < game.SYMBOLS; s++ ) {
//					win.highlight[s][r] = highlights.get(s).get(r);
//				}								
//			}
			
			
			for(int r = 0; r < game.REELS; r++ ) {
				for(int s = 0; s < game.SYMBOLS; s++ ) {
					win.highlight[s][r] = highlights.get(s).get(r);
				}
			}								
			

			
			if(win.type != Type.NEAR_MISS && win.type != Type.SPECIAL_LINE)	 gameOutcomeRestore.wins.add(win);
		}
		
		game.context.hasReelHighlight = false;
		
//		game.reels.updateStopSymbols();
//		game.controller.restoreGSOutcome();
	}
	
//	private native boolean isEmpty(JavaScriptObject lastgame) /*-{
//		    for(var key in lastgame) {
//		        if(this.hasOwnProperty(key))
//		            return false;
//		    }
//		    return true;
//	}-*/;
	
	private native boolean isEmpty(JavaScriptObject obj) /*-{
		  for(var prop in obj) {
		    if(obj.hasOwnProperty(prop)) {
		      return false;
		    }
		  }

		  return JSON.stringify(obj) === JSON.stringify({});
	}-*/;
	
	
	public void setFirstTime(JavaScriptObject jps){
		JsArrayInteger minhits = ((GSJackpot)jps).getMinHitsStarts();
		
		for(int i = 0; i < minhits.length(); i++){			
			game.context.hashMinHitsJackpot.put(minhits.get(i * 2), i);			
		}
		
		JsArrayInteger maxhits = ((GSJackpot)jps).getMaxHitsStarts();
		
		for(int i = 0; i < maxhits.length(); i++){			
			game.context.hashMaxHitsJackpot.put(maxhits.get(i * 2), i);			
		}
		
		JsArrayInteger resethits = ((GSJackpot)jps).getResetHitsStarts();
		
		for(int i = 0; i < minhits.length(); i++){			
			game.context.hashResetHitsJackpot.put(resethits.get(i * 2), i);			
		}

		JsArrayInteger jackpot = ((GSJackpot)jps).getJackpotsStarts();
		
		for(int i = 0; i < jackpot.length(); i++){			
			game.context.hashJackpot.put(jackpot.get(i * 2), i);			
		}
		
		for(int index = 0; index < jackpot.length(); index += 2){/*
			
			int i = game.context.hashJackpot.get(jackpot.get(index));
			int j = index + 1;
			
			game.jackpot.updateJackpot(i, (int)jackpot.get(j));
			game.context.jackpotResetValue[i] = (int)jackpot.get(j);			
		*/}
		
//		if(jackpot.length() < 8){
//			game.jackpot.setVisible(false);
//		}
	}
	@Override
	public void onError(int err) {
//		Gdx.app.debug("GSLink", "error: " + err);
	}

	@Override
	public native boolean error() /*-{
		return $wnd.client.onClose();
	}-*/;
	
	@Override
	public native boolean isConnected() /*-{
		return $wnd.client.isConnected && $wnd.client.isLoggedIn;
	}-*/;

	@Override
	public native boolean spinEnded() /*-{
		return $wnd.client.spinEnded;
	}-*/;	
	
	@Override
	public void console(String str){
//		consoleLog(str);
	}
	
	private native void consoleLog(String str) /*-{
//		console.dir(str);
	}-*/;
	

	@Override
	public native int getBalance() /*-{
		$wnd.client.getBalance();
		return 0;
	}-*/;

	@Override
	public native void updateProgress(float progress)/*-{
		// TODO Auto-generated method stub
		$wnd.client.loadProgress = progress;
		$wnd.client.updateProgress();
	}-*/;

	@Override
	public void stopLogoAnim() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public native void showHowToPlay()/*-{
		// TODO Auto-generated method stub
		$wnd.client.showHowToPlay();
	}-*/;
	
	@Override
	public native void onHomeButton() /*-{
		$wnd.client.homeButton();
//		return 0;
	}-*/;



	@Override
	public native void fullScreenCustom()/*-{    	

	}-*/;

	@Override
	public native void reload() /*-{
//		$wnd.client.reload();
	}-*/;

	@Override
	public int orientation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public native void onHistoryButton() /*-{
		$wnd.client.historyButton();
	}-*/;
	
	@Override
	native public void setClaimParams(boolean claimAddFreeSpins) /*-{
		$wnd.client.gameParams.claimAddFreeSpins = claimAddFreeSpins;
	}-*/;

}
