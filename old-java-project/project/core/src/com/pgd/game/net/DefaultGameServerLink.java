package com.pgd.game.net;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.pgd.game.BookOfKnight;
//import com.pgd.game.Context.GameMode;
import com.pgd.game.base.Win.Type;

public class DefaultGameServerLink implements IGameServerLink {
	
	private String url, uid;
	private HttpRequest httpRequest;
	private BookOfKnight game;
	private Random rand;
	public DefaultGameServerLink(BookOfKnight game) {
		this.game = game;
		rand = new Random();
	}

	@Override
	public boolean connect(String url, String uid) {

		this.url = url;
		this.uid = uid;
		
		httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setUrl(url + "connect.php");
		httpRequest.setContent("uid=" + uid);
		Gdx.net.sendHttpRequest(httpRequest, null);

//		game.messages.clearErrors();

		Gdx.app.debug("DefaultGameServerLink", "connect(" + url + ", " + uid + " )");
		
		return false;
	}

	int spin = 0;
	/**
	 * Stand-alone spin using Math class
	 */
	@Override
	public boolean spin() {
		
		game.meters.win = 0;
		game.meters.credit -= game.meters.getTotalBet();
		
	    game.context.onscreenWinMeter = 0;
	    game.context.onscreenCreditMeter = (int)game.meters.credit;
		
		// get outcome
		game.context.outcome = null;
		game.context.outcome = game.math.getWins(game.reels.matrix);	
		
		
		// traverse wins if any and calculate total game win
		for( int i = 0; i < game.context.outcome.wins.size(); i++) {
			
//			Gdx.app.debug("Def", "game.context.outcome.wins.get(i).type = " + game.context.outcome.wins.get(i).type);
			
			if(game.context.outcome.wins.get(i).type == Type.LINE) {
				game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getBetPerLine() * game.meters.getDenomination();
				game.context.outcome.wins.get(i).betPerLine = game.meters.getBetPerLine() * game.meters.getDenomination(); // set bet per line for this line win
			}
			else if(game.context.outcome.wins.get(i).type == Type.SCATTER) {
				
				game.context.outcome.hasFreeGames = true;
				
				game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getBetPerLine() * game.meters.getDenomination();
				game.context.outcome.wins.get(i).betPerLine = game.meters.getBetPerLine() * game.meters.getDenomination(); // set bet per line for this line win
			}
			else if(game.context.outcome.wins.get(i).type == Type.BONUS) {
				game.context.outcome.hasBonusWin = true;
				game.context.outcome.bonusMultiplier = 50;
			}
		}		
		
//		game.context.outcome.hasRespin = false;
//		game.bonus.setBonusWinPos(game.context.outcome.bonusWin);
		
//		game.meters.fgwin = game.meters.win;
//		game.context.freeGamesCounter 	  = 10;
//		game.context.freeGamesWon 	  	  = 10;
//		
//		game.context.outcome.hasHoldAndWin = true;
//		game.context.holdAndWinCounter = 3;
		
//		game.context.outcome.hasFreeGames = true;
		
		game.meters.updatePrev();
	
		game.meters.credit += game.meters.win;
		game.meters.updateTotals();

		game.menu.setCredit(game.context.onscreenCreditMeter);
		
		if(game.context.gameMode == game.context.FREE_GAMES){
			
		} else {
			game.menu.setWin(0);
		}
        
//        if(spin == 0){
//        	game.context.outcome.hasFreeGames = true;
//        	spin++;
//        } else {
//        	game.context.outcome.hasFreeGames = false;
//        }
        
        game.menu.setLeftStatus("");
        
        if(game.context.gameMode != game.context.MAIN_GAME){
        	game.context.freeGamesCounter--;
        	game.meters.fgwin += game.meters.win;
        }
//        Gdx.app.debug("Link", "game.context.gameMode = " + game.context.gameMode);
//        Gdx.app.debug("Link", "game.context.freeGamesWon = " + game.context.freeGamesWon);
//        Gdx.app.debug("Link", "game.context.freeGamesCounter = " + game.context.freeGamesCounter);
        
        
		return true;		
	}
	
	/**
	 * Stand-alone spinBonusGames using Math class
	 */
	@Override
	public boolean bonus(int bonusWinPosition) {
						
			
		game.meters.win = 0;
		
	    game.context.onscreenWinMeter = 0;
	    game.context.onscreenCreditMeter = (int)game.meters.credit;
		
		game.context.onscreenWinMeter = 0;
		
		game.meters.credit += game.meters.win;
	
		game.context.onscreenCreditMeter = (int)game.meters.credit;
		
		game.meters.updateTotals();

		game.menu.setCredit(game.context.onscreenCreditMeter);
        game.menu.setWin(0);
        
        game.menu.setLeftStatus("");
        
//        game.controller.processBonusGSOutcome();
        
		return false;
	}

	
	/**
	 * Stand-alone spinFreeGames using Math class
	 */
	@Override
	public boolean spinAddFreeGames() {
		return false;
	}
	
	/**
	 * Stand-alone spinBonusGames using Math class
	 */
//	@Override
//	public boolean spinBonusGames() {
//		
////		lineCounter = 0;
////		reelCounter = 0;
////		lastReelStopped = -1;
//		
//		// spin the reels
////		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {			
////				game.reels.spinReelBonus(i, rand.nextInt(game.math.stripsNormalGames[i].length));
////		}
//		
//		// get outcome
//		game.context.outcome = game.math.getWins(game.reels.matrix);	
//		game.meters.win = 0;
//		for( int i = 0; i < game.context.outcome.wins.size(); i++) {
//			if(game.context.outcome.wins.get(i).type == Type.LINE) {
//				if(game.context.outcome.wins.get(i).type == Type.LINE) {
//					game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getBetPerLine();
//					game.context.outcome.wins.get(i).bet = game.meters.getBetPerLine(); // set bet per line for this line win
//				}
//				if(game.context.outcome.wins.get(i).type == Type.SCATTER) {
//					game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getTotalBet();
//					game.context.outcome.wins.get(i).bet = game.meters.getTotalBet(); 
//					//add free games after scatter win <aim>
////					game.context.outcome.hasFreeGames = (game.context.gameMode == /*GameMode.*/game.context.MAIN_GAME);
////					game.context.freeGamesWon = game.math.FREE_GAMES_AWARDED;
//					
//				}
//			}
//		}
//		
//		game.meters.credit += game.meters.win;
//		game.meters.updateTotals();
//		
////		Gdx.app.debug("AbstractController", "startBonusSpin()");
//		game.math.printMatrix(game.reels.matrix);
//		
//		game.menu.setCredit(game.context.onscreenCreditMeter);
//        game.menu.disableControls();
//	//	game.menu.updateMeters();
//		
//        game.menu.setLeftStatus("Bonus Game ");
//        game.sounds.playBackground(true);
//		
//		return true;
//	}
	@Override
	public boolean disconnect() {
		httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setUrl(url + "disconnect.php");
		httpRequest.setContent("uid=" + uid);
		Gdx.net.sendHttpRequest(httpRequest, null);

		Gdx.app.debug("DefaultGameServerLink", "disconnect(" + url + ", " + uid + " )");		
		return false;
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public void onConnect() {

	}

	@Override
	public void onDisconnect() {

	}

	@Override
	public void onMessage() {

	}

	@Override
	public void onError(int err) {

	}

	@Override
	public boolean spinEnded() {
		return true;
	}

	@Override
	public int getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setParams(int betPerLine, int linesSelected, int denom) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean freespin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateProgress(float progress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLogoAnim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBonusParams(int bonusWinPosition, int betPerLine, int lines, int denomination) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void console(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showHowToPlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean error() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onHomeButton() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int orientation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void fullScreenCustom() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean setSettings(int skipIntro, int skipScreen, int turboGame) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setParamsBuyFreeGames(int betPerLine, int lines, int denomination, boolean hasBuyBonus,
			int buyBonusType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHistoryButton() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setClaimParams(boolean claimAddFreeSpins) {
		// TODO Auto-generated method stub
		
	}


}
