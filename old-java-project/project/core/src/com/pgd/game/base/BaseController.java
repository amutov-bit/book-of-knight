package com.pgd.game.base;

import java.io.Console;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds;
//import com.pgd.game.Context.GameMode;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.Win.Type;

public abstract class BaseController extends AbstractController {

	protected final static int SERVER_RESPONSE_TIMEOUT = 2000;
	/*
	 * <cod mode bb>
	 */
	protected final static int SPIN_TIMEOUT = 11;
	protected final static int REEL_STOP_INTERVAL = 11;
	protected final static int REEL_EXTENDED_STOP_INTERVAL = 111;
	
	protected long currentTime;
	
	protected int timerCounter;
	protected int buttonCounter;
	protected int idleTimerCounter;
	protected int highlightTimeout;
	protected int reelsStoppedTimeout;
	protected int reelCounter;
	protected int lastReelStopped;
	protected int lineCounter;
	protected int lineCounterTMP;
	protected int w2cSpeed; // win to credit speed
	
//	protected boolean signalPotion; //
	
	protected Random rand; // RNG
	
	protected int bonusReel;
	
	public BaseController(BookOfKnight game) {
		super(game);
		timerCounter = 0;
		buttonCounter = 0;
		reelCounter = 0;
		idleTimerCounter = 0;
		lineCounter = 0;
		game.context.autoplayCounter = 0;
		game.context.gameMode = /*GameMode.*/game.context.MAIN_GAME;

//		signalPotion = false;
		
		bonusReel = 10;
		
		rand = new Random();
		
		highlightTimeout = 20;
		
		currentTime = TimeUtils.millis();
		/*****************************************************************
		 * DEMO MODE 
		 * TODO: Remove in production code !!!
		 ****************************************************************/
		//if(game.meters.credit == 0) {
		//	game.meters.credit = 10000;
		//}
		}
	
	public boolean beforeSpin(){
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		if(game.meters.credit < game.meters.getTotalBet()) {
			game.menu.setStatus("INSERT CREDITS TO PLAY");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Start new spin. Results are stored in a GameOutcome instance.
	 * Reference of game outcome is kept in game context.
	 */
	public boolean startSASpin() {
		
		return true;
	}
	
	public boolean gameCanStart() {
		
		return true;
	}
	
	int spins = 0;
	
	public boolean /*startGSSpin*/startSpin() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		
		if(game.meters.credit < game.meters.getTotalBet()) {
			game.menu.setStatus("INSERT CREDITS TO PLAY");
			return false;
		}

		game.spinIsEnded = false;
		
		
		game.context.onscreenCreditMeter = (int) (game.meters.credit - game.meters.getTotalBet());
        
		
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
			if(game.context.gameMode == /*GameMode.*/game.context.MAIN_GAME){
				game.reels.setReelStrip(game.math.stripsNormalGames[i], i);
				game.reels.spinReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
			}else if(game.context.gameMode == /*GameMode.*/game.context.FREE_GAMES){
				game.reels.setReelStrip(game.math.stripsFreeGames[i], i);
				game.reels.spinReel(i, rand.nextInt(game.math.stripsFreeGames[i].length));
			}
				
		}
		
		int skipIntro  = (game.context.skipIntro) ? 1 : 0;
		int skipScreen = (game.context.skipScreen) ? 1 : 0;
		int turboGame  = (game.context.turboGame) ? 1 : 0;
		
		game.gsLink.setSettings(skipIntro, skipScreen, turboGame);

		game.gsLink.setParams(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
		game.gsLink.spin();
		game.menu.setCredit(game.context.onscreenCreditMeter);
        game.menu.setWin(0);
                
		return true;
	}
	public boolean /*startGSSpin*/startAddFreeSpins() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		
//		if(game.meters.credit < game.meters.getTotalBet()) {
//			game.menu.setStatus("INSERT CREDITS TO PLAY");
//			return false;
//		}
//		
		
		game.overlay.addFreeGame.setFreeGamesLeftCnt(game.context.addFreeSpinsCnt - 1);
		
		game.spinIsEnded = false;
		
		
		game.context.onscreenCreditMeter = (int) (game.meters.credit - game.meters.getTotalBet());
		
		
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
			if(game.context.gameMode == /*GameMode.*/game.context.MAIN_GAME){
				
				game.reels.setReelStrip(game.math.stripsNormalGames[i], i);
				game.reels.spinReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
				
//				game.reels.setFreeGamesReelStrip(game.math.stripsFree[0], 0);
//				game.reels.setMultiplierReelStrip(game.math.stripsMult[0], 0);
//				
//				game.reels.spinFreeGamesReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
//				game.reels.spinMultiplierReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
				
			}else if(game.context.gameMode == /*GameMode.*/game.context.FREE_GAMES){
				game.reels.setReelStrip(game.math.stripsFreeGames[i], i);
				game.reels.spinReel(i, rand.nextInt(game.math.stripsFreeGames[i].length));
			}
			
		}
		
//		game.reels.spinReelMult(0, rand.nextInt(game.math.stripsFree[0].length));
//		game.reels.spinReelMult(1, rand.nextInt(game.math.stripsMult[0].length));
		
		int skipIntro  = (game.context.skipIntro) ? 1 : 0;
		int skipScreen = (game.context.skipScreen) ? 1 : 0;
		int turboGame  = (game.context.turboGame) ? 1 : 0;
		
		game.gsLink.setSettings(skipIntro, skipScreen, turboGame);
		
		if(game.context.showAddFreeSpins){
			game.gsLink.setClaimParams(game.context.claimAddFreeSpins);
		}
		
		game.gsLink.spin();
		
//		game.gsLink.setParams(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
//		game.gsLink.spin();
//		game.menu.setCredit(game.context.onscreenCreditMeter);
//		game.menu.setWin(0);
		
		
		return true;
	}
	/**
	 * Start new spin
	 */
	public boolean startBuyFreeGamesSpin() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;


		game.spinIsEnded = false;
		
		long buyBonusValue = (game.context.buyBonusType == 0) ? game.context.buyFreeGamesMult : game.context.buyHoldAndWinMult;

		
		game.gsLink.console("buyBonusValue = " + buyBonusValue);
		game.gsLink.console("game.context.hasBuyBonus = " + game.context.hasBuyBonus);
		game.gsLink.console("game.meters.credit = " + game.meters.credit);
		game.gsLink.console(" game.meters.getTotalBet() * buyBonusValue = " +  game.meters.getTotalBet() * buyBonusValue);
		
		if(game.meters.credit < game.meters.getTotalBet() * buyBonusValue) {
			game.menu.setStatus("INSERT CREDITS TO PLAY");
			return false;
		}
		
		if(!game.context.hasBuyBonus){
			return false;
		}
		
		// spin the reels
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
			game.reels.setReelStrip(game.math.stripsNormalGames[i], i);
			game.reels.spinReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
		}

		int skipIntro  = (game.context.skipIntro) ? 1 : 0;
		int skipScreen = (game.context.skipScreen) ? 1 : 0;
		int turboGame  = (game.context.turboGame) ? 1 : 0;
		
		game.gsLink.setSettings(skipIntro, skipScreen, turboGame);
		
		game.context.onscreenCreditMeter = (int) (game.meters.credit - (game.meters.getTotalBet() * buyBonusValue));
		game.menu.setCredit(game.context.onscreenCreditMeter);
		game.gsLink.setParamsBuyFreeGames(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination(), game.context.hasBuyBonus, game.context.buyBonusType);
		
		
		game.gsLink.spin();
		
		return true;
	}

	/**
	 * Start new spin
	 */
	public boolean startFGSpin() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
//		game.gsLink.console("---------------------------------------------game.context.gameMode = " + game.context.gameMode);

		// spin the reels
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
				 game.reels.setReelStrip(game.math.stripsFreeGames[i], i);
				 game.reels.spinReel(i, rand.nextInt(game.math.stripsFreeGames[i].length));
		}
		
//		game.gsLink.setParams(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
		game.gsLink.spin();
		
		return true;
	}	

	/**
	 * Start new spin
	 */
	public boolean startHoldAndWinSpin() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
//		game.gsLink.console("---------------------------------------------game.context.gameMode = " + game.context.gameMode);
		
		// spin the reels
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
			game.reels.setReelStrip(game.math.stripsHoldAndWin[i], i);
			game.reels.spinReel(i, rand.nextInt(game.math.stripsHoldAndWin[i].length));
		}
		
//		game.gsLink.setParams(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
		game.gsLink.spin();
		
		return true;
	}	
	
	/**
	 * Start new spin
	 */
	public boolean startAdditionalFreeSpins() {
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		// spin the reels
		for (int i = 0; i < game.reels.NUMBER_OF_REELS; i++) {
				 game.reels.spinReel(i, rand.nextInt(game.math.stripsNormalGames[i].length));
		}
		
//		game.gsLink.setParams(game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
		game.gsLink.spinAddFreeGames();
		
		return true;
	}	
	
	/**
	 * Get GameServer outcome
	 */
	public void processGSOutcome() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		game.meters.update();
		
		game.meters.updatePrev();
		
		game.meters.win = 0;
		
		if(game.context.gameMode == game.context.MAIN_GAME){
			game.meters.win = 0;
			game.context.onscreenWinMeter = 0;
		} else {
			game.meters.win = game.meters.fgwin - game.meters.currentWin; 
			game.context.onscreenWinMeter = game.meters.fgwin - game.meters.currentWin;
		}
		
//	    game.context.onscreenCreditMeter = (int)game.meters.credit;
		
		// get outcome
		//game.context.outcome = null;
		//game.context.outcome = game.math.getWins(game.reels.matrix);	
		
//		if(game.context.gameMode != game.context.FREE_GAMES){
//			game.context.freeGamesCounterFake = 0;
//		} else {
//			game.context.freeGamesCounterFake--; 
//		}
//		
//		game.context.freeGamesWonFake = 0;
		
		// traverse wins if any and calculate total game win
		for( int i = 0; i < game.context.outcome.wins.size(); i++) {
			if(game.context.outcome.wins.get(i).type == Type.LINE) {
				game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getBetPerLine()*game.meters.getDenomination() ;
			}
			else if(game.context.outcome.wins.get(i).type == Type.SCATTER) {
				
					int totalBet = game.meters.getTotalBet();
					
					game.meters.win += game.context.outcome.wins.get(i).mult * totalBet;
			}
		}
				
		for( int i = 0; i < game.context.outcome.winsSpecial.size(); i++) {
			if(game.context.outcome.winsSpecial.get(i).type == Type.SPECIAL_LINE) {
				game.meters.win += game.context.outcome.winsSpecial.get(i).mult * game.meters.getBetPerLine()*game.meters.getDenomination();
			}
		}
		
		game.meters.updateTotals();

		
        if(game.context.gameMode == game.context.FREE_GAMES)
        {
        	game.context.visibleFreeGamesCounter--;
        	
        	if(game.context.outcome.hasFreeGames){
        		game.overlay.setFreeGamesTitleLabelFirst(game.context.visibleFreeGamesCounter);
        	} else {
        		game.overlay.setFreeGamesTitleLabelFirst(game.context.visibleFreeGamesCounter);
        	}
        }

        if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES)
        {
        	game.context.visibleHoldAndWinCounter--;
        	
        	if(game.context.outcome.hasHoldAndWin){
        		game.overlay.setHoldAndWinTitleLabel(game.context.visibleHoldAndWinCounter);
        	} else {
        		game.overlay.setHoldAndWinTitleLabel(game.context.visibleHoldAndWinCounter);
        	}
        }
        
        if(game.context.hasAddFreeSpins){
        	game.overlay.addFreeGame.setFreeGamesLeftCnt(game.context.addFreeSpinsCnt);
        }
        
        game.context.visibleFreeGamesCounter  = game.context.freeGamesCounter;
        
        game.context.visibleHoldAndWinCounter = game.context.holdAndWinCounter;

		 if(!game.context.autoplay)		game.menu.setLeftStatus(" ");
		 
	}
	
	public void restoreGSOutcome() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		game.meters.update();
		
		game.meters.updatePrev();
		
		if((game.context.gameMode == game.context.FREE_GAMES)
				&& (game.context.freeGamesCounter == 0 && !game.context.outcome.hasFreeGames)
				
				){
			
			
		} else {
			if(game.context.outcome.hasFreeGames || game.context.gameMode == game.context.FREE_GAMES){
//				game.meters.credit -=  game.meters.fgwin;
				game.meters.win = game.meters.fgwin - game.meters.currentWin;
			} else {
				if(game.context.freeGamesCounter == 0 ){
//					game.meters.credit -=  game.meters.respinwin;
				}
			}
		}
		
		game.context.onscreenWinMeter = 0;
		
		// traverse wins if any and calculate total game win
		for( int i = 0; i < game.context.outcome.wins.size(); i++) {
			if(game.context.outcome.wins.get(i).type == Type.LINE) {
				game.meters.win += game.context.outcome.wins.get(i).mult * game.meters.getBetPerLine()*game.meters.getDenomination() ;
			}
			else if(game.context.outcome.wins.get(i).type == Type.SCATTER) {
				
					int totalBet = game.meters.getTotalBet();
					
					game.meters.win += game.context.outcome.wins.get(i).mult * totalBet;
			}
		}
		
		for( int i = 0; i < game.context.outcome.winsSpecial.size(); i++) {
			if(game.context.outcome.winsSpecial.get(i).type == Type.SPECIAL_LINE) {
				game.meters.win += game.context.outcome.winsSpecial.get(i).mult * game.meters.getBetPerLine()*game.meters.getDenomination();
			}
		}
		
		game.meters.updateTotals();
        
		game.gsLink.console("game.context.freeGamesCounter = " + game.context.freeGamesCounter);
		game.gsLink.console("ame.context.gameMode = " +game.context.gameMode);
		
		game.context.visibleFreeGamesCounter--;
		
		if(game.context.freeGamesCounter == 0){
			game.context.visibleFreeGamesCounter = 0;
		}

		game.context.visibleHoldAndWinCounter--;
		
		if(game.context.holdAndWinCounter == 0){
			game.context.visibleHoldAndWinCounter = 0;
		}
		
        if(game.context.gameMode == game.context.FREE_GAMES)
        {
        	game.context.visibleFreeGamesCounter--;
        	
//        	if(game.context.outcome.hasFreeGames){
//        		game.overlay.setFreeGamesTitleLabelFirst(game.context.visibleFreeGamesCounter);
//        	} else {
//        		game.overlay.setFreeGamesTitleLabelFirst(game.context.visibleFreeGamesCounter);
//        	}
        }

        if(game.context.gameMode == game.context.HOLD_AND_WIN_GAMES)
        {
        	game.context.visibleHoldAndWinCounter--;
        	
//        	if(game.context.outcome.hasHoldAndWin){
//        		game.overlay.setHoldAndWinTitleLabel(game.context.visibleHoldAndWinCounter);
//        	} else {
//        		game.overlay.setHoldAndWinTitleLabel(game.context.visibleHoldAndWinCounter);
//        	}
        }
        
        game.context.visibleFreeGamesCounter  = game.context.freeGamesCounter;
        
        game.context.visibleHoldAndWinCounter = game.context.holdAndWinCounter;

		
		if(!game.context.autoplay)		game.menu.setLeftStatus(" ");
		
	}
	
	public void processBonusGSOutcome() {
		
		lineCounter = 0;
		reelCounter = 0;
		lastReelStopped = -1;
		
		game.meters.update();
		
	}

	/**
	 * Process win
	 */
	protected void processWin(int lineCounter) {
		
		Win win = game.context.outcome.wins.get(lineCounter);
		highlightTimeout = win.highlightTimeout;
		game.reels.unhighligthAll();
		
		game.overlay.hideWin();
		
//		game.gsLink.console("lineCounter = " + lineCounter);
//		game.reels.setMagicianCount();
		
		if(!game.reels.textures.getAnimRegionLoaded())	highlightTimeout = 30;
		
		switch(win.type){
			case LINE:
				game.reels.spineSymbolAnim.startAnimLine(win.symbol, win.winningLine);
				
				game.reels.highlightWin(win, false, true, 1);
				game.reels.lineRederer.addLine(win.winningLine);
				game.menu.setWinStatus(game.math.winningLineToText(win, game));
				
				game.context.onscreenWinMeter += win.mult * win.betPerLine;
				
				game.overlay.showWin(win.mult * win.betPerLine);
				
				if(game.context.hasAddFreeSpins && game.context.gameMode != game.context.FREE_GAMES && !game.context.outcome.hasFreeGames){
					game.overlay.addFreeGame.setFreeGamesWin(game.context.addFreeSpinsWin + game.context.onscreenWinMeter - game.meters.currentWin);
				}
				
				game.menu.setWin(game.context.onscreenWinMeter);
				
				game.sounds.play(win.sound, false);
				break;
				
			case SCATTER:
				game.changeLine = true;
				break;			
			case SPECIAL_LINE:
				game.changeLine = true;
				break;
			default:
				break;
		}

	}
	
	protected void processWinSpecial(int lineCounter) {
		
		Win win = game.context.outcome.winsSpecial.get(lineCounter);
		highlightTimeout = win.highlightTimeout;
//		game.reels.unhighligthAll(true);
		
		highlightTimeout = 20;
		
		switch(win.type){
			case LINE:
			break;
			case SPECIAL_LINE:
				game.reels.lineRederer.addLine(win.winningLine);
				game.menu.setWinStatus(game.math.winningLineToText(win, game));
				
				game.context.onscreenWinMeter += win.mult * win.betPerLine;
				
//				game.context.onscreenSpecialWinMeter += win.mult * win.betPerLine;
//				game.overlay.showWin(game.context.onscreenSpecialWinMeter);
				
//				game.overlay.showWin(win.mult * win.betPerLine);
				if(game.context.hasAddFreeSpins && game.context.gameMode != game.context.FREE_GAMES && !game.context.outcome.hasFreeGames){
					game.overlay.addFreeGame.setFreeGamesWin(game.context.addFreeSpinsWin + game.context.onscreenWinMeter - game.meters.currentWin);
				}
				game.menu.setWin(game.context.onscreenWinMeter);
				
				game.sounds.play(SoundTrack.KNOCK, false);
				
			break;
			default:
			break;
		}
		
	}
	
	protected void processHoldAndWinWin() {
		game.sounds.play(SoundTrack.HIGH_WIN, false);
		game.reels.highlightHoldAndWinSymbol();
//		game.reels.lineRederer.clear();
		game.reels.unhighligthAll();
	}
	
	protected void processScatterWin(int lineCounter) {
		
		
		Win win = game.context.outcome.wins.get(lineCounter);
		highlightTimeout = win.highlightTimeout;
		game.reels.unhighligthAll();
		
		game.overlay.hideWin();
		
//		game.reels.setMagicianCount();
		
		Gdx.app.debug("BaseController" , "processScatterWin lineCounter = " + lineCounter);
		Gdx.app.debug("BaseController" , "win.type = " + win.type);
		
		if(!game.reels.textures.getAnimRegionLoaded())	highlightTimeout = 30;
		
		switch(win.type){
		case LINE:
			game.changeLine = true;
			break;
			
		case SCATTER:
			
			game.reels.spineSymbolAnim.startAnimScatter(win.symbol);
			game.reels.showFrameWin(true);
			game.reels.highlightWinScatterSymbol(win, true, true, 0);
			game.menu.setWinStatus(game.math.winningLineToText(win,game));
			
			int totalBet = game.meters.getTotalBet();
				
			game.context.onscreenWinMeter += win.mult * totalBet;
			game.overlay.showWin(win.mult * totalBet);

			if(game.context.hasAddFreeSpins && game.context.gameMode != game.context.FREE_GAMES && !game.context.outcome.hasFreeGames){
				game.overlay.addFreeGame.setFreeGamesWin(game.context.addFreeSpinsWin + game.context.onscreenWinMeter - game.meters.currentWin);
			}

			game.menu.setWin(game.context.onscreenWinMeter);
			game.sounds.play(win.sound, false);
			break;			
		case WILD:
			break;
		default:
			break;
		}
		
	}
	
	protected void processAllLines(int lineCounter){
		Win win = game.context.outcome.wins.get(lineCounter);
		game.reels.highlightWin(win, false, true, 1);
	}
	
	protected void showWin() {
		
		Win win = game.context.outcome.wins.get(lineCounter);
		highlightTimeout = /*win.hasWild ? 140 : */win.highlightTimeout; // extend timeout if wild present
		game.reels.unhighligthAll();
		
		game.menu.setWinStatus("");
		
//		game.reels.setMagicianCount();
		
		if(!game.reels.textures.getAnimRegionLoaded())	highlightTimeout = 30;
		
		switch(win.type){
			case LINE:
				game.reels.spineSymbolAnim.startAnimLine(win.symbol, win.winningLine);
				game.reels.highlightWin(win, false, true, 1);
//				game.reels.lineRederer.addLine(win.winningLine);
				game.menu.setWinStatus(game.math.winningLineToText(win, game));
				break;				
			case SCATTER:
			case BONUS:
				game.reels.highlightWin(win, false, true, 0);
				game.menu.setWinStatus(game.math.winningLineToText(win,game));
				break;
			case NEAR_MISS:
//			case POTION:
//				game.reels.highlightWin(win, false, true);
//				game.menu.setStatus("");
				break;
			default:
				break;
		}

	}	
	
	/**
	 * Skip near-miss if game pays
	 * TODO: to fix it's quick&dirty
	 */
	public void skipNearMissIfPays() {
		if(game.context.outcome.hasWin && game.context.outcome.wins.get(lineCounter).type==Type.NEAR_MISS) {
			lineCounter++;
		}
	}	

	/**
	 * Show all winning lines
    */
   /*
	* <cod mode bb>
	*/
	public boolean showAllLines(int line, Win win) {
		boolean stop = false;
		
		
		if (game.controller.timerCounter > 2 && (line < game.controller.game.context.outcome.wins.size() || line == 0))
		{
			win = game.controller.game.context.outcome.wins.get(line);
			if(win.type == Type.LINE)
			{
				if(line % 2 == 0){
					game.controller.game.sounds.play(SoundTrack.KNOCK, false);
				}
				game.controller.showAllWinningLinesOne(win);
				game.reels.highlightAll(win, 1);
//				game.context.onscreenWinMeter += win.mult * win.bet;
			}
			game.controller.timerCounter = 0;
		}else{
			stop = true;
		}
		
//		game.menu.setWin(game.context.onscreenWinMeter);
		
		return stop;
	}

	public boolean higlightAllLines(int line, Win win) {
		boolean stop = false;
		
		Gdx.app.debug(" base Controller ", " line = " + line + " :: game.controller.game.context.outcome.wins.size() = " + game.controller.game.context.outcome.wins.size());
		if (line < game.controller.game.context.outcome.wins.size())
		{
			win = game.controller.game.context.outcome.wins.get(line);
			if(win.type == Type.LINE)
			{
				if(game.controller.game.context.outcome.wins.size() > 3){
//					game.controller.game.sounds.play(SoundTrack.KNOCK, false);
				}
//				game.controller.showAllWinningLinesOne(win);
				game.reels.highlightAll(win, 1);
				
				game.context.onscreenWinMeter += win.mult * win.betPerLine;
				
			}
			game.controller.timerCounter = 0;
		}else{
			stop = true;
		}
		
//		game.menu.setWin(game.context.onscreenWinMeter);
		
		return stop;
	}
	
	public void showAllWinningLinesOne(Win win) {		
		game.reels.lineRederer.addLine(win.winningLine);
	}
	/**
	 * Clear all lines
	 */
	public void clearAllLines() {
		game.reels.lineRederer.clear();
	}
	
	/**
	 * Set pick-me symbol for the free games select screen
	 */
//	public void setPickMeSymbols() {
//		
//		for(int r = 1; r < 4; r++){
//			for(int s = 0; s < 3; s++) {
//				game.reels.setFlipSymbolAtReelStop(r, s, 
//						game.math.specialSymbols[rand.nextInt(game.math.specialSymbols.length)]);
//			}
//		}
//	}
	
	/**
	 * Near win
	 * 
	 * @return boolean - true if a scatter win is near
	 */
	public boolean nearWin() {
		int cnt = 0;
		int lastReel = -1;
		
		for(int r = 0; r < game.reels.NUMBER_OF_REELS; r++) {
			if(game.reels.reelStopped(r)) {
				lastReel = r;
			}
		}

		if(lastReelStopped != lastReel) {
			lastReelStopped = lastReel;
			for(int r = 0; r <= lastReelStopped; r++) {
				for(int s = 0; s < 3; s++) {
					if(game.reels.matrix[s][r]==/*SCATTER*/0) {
						cnt++;
					}
				}
			}
		}
		
		// TODO cnt and paytable !!!
		return (cnt >= 1 && lastReelStopped < game.reels.NUMBER_OF_REELS - 1);
	}
	
	/**
	 * Near win
	 * 
	 * @return boolean - true if a scatter win is near
	 */
	public boolean reelStopped() {
		int lastReel = -1;
		
		for(int r = 0; r < game.reels.NUMBER_OF_REELS; r++) {
			if(game.reels.reelStopped(r)) {
				lastReel = r;
			}
		}

		if(lastReelStopped != lastReel) {
			lastReelStopped = lastReel;
			return true;
		}
		
		return false;
	}	
	
	/**
	 * This function will check if all reels has stopped
	 * 
	 * @return boolean - true if all reels has stopped
	 */
	public boolean reelsStopped() {
		
		for(int r = 0; r < game.reels.NUMBER_OF_REELS; r++) {
			if( !game.reels.reelStopped(r) ) 
				return false;
		}

		return true;
	}
	
	public boolean addBonusWin(int bonusWinPosition)
	{
		game.gsLink.setBonusParams(bonusWinPosition, game.meters.getBetPerLine(), game.meters.getLines(), game.meters.getDenomination());
		game.gsLink.bonus(bonusWinPosition);
		
		return true;
	}
	/**
	 * Highlight winning WILDs
	 */
//	public void highlightWinningWilds() {
//		
//		for( int i = 0; i < game.context.outcome.wins.size(); i++) {
//			if(game.context.outcome.wins.get(i).hasWild) {
//				for (int s = 0; s < 3; s++) {
//					for (int r = 0; r < 5; r++) {
//						if (game.context.outcome.wins.get(i).highlight[s][r] == 1 && game.context.outcome.matrix[s][r] == 11) {
//							game.reels.highlightSymbol(r, s, true);
//						}
//					}
//				}
//			}
//		}
//		
//	}
}
