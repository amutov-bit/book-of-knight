package com.pgd.game;

import com.badlogic.gdx.utils.ObjectMap;

//import java.util.Hashtable;

import com.pgd.game.base.GameOutcome;


public class Context {

	private static Context instance = null;

	protected Context() { }

	public static Context getInstance() {
		if (instance == null) {
			instance = new Context();
		}
		return instance;
	}
	
	public String gamePercent = "0";
	public String gamePercentBuyFree = "0";
	public String gamePercentBuyHold = "0";
	
	public int specialSymbol = -1;
	
	public int prevMode = 0;
	
	public long roundId = 0;

	/**
	 * Game modes
	 */
	public final int  MAIN_GAME  = 0;
	public final int  FREE_GAMES = 1;
	public final int  HOLD_AND_WIN_GAMES = 2;
//	public final int  ADD_FREE_GAMES= 2;
//	public final int  SUPER_GAMES = 2222;

	public int addFreeGamesCnt = 21;
	
	public boolean hasWild 	  = false;
	
	public boolean  hasSpineAnimation = false;
	
	public int WIN_FG_MULTIPLIER = 1;
	
	//change
	public int BIG_WIN_MULT = 10;
	
	public int BONUS_ITEMS = 123101;
	
	public boolean jackpotHit 	  = false;
	public int 	   jackpotValue	  = 0;
	public int	   jackpotId  	  = 0;
	
	public int	   jackpotResetValue[] = {0,0,0,0};
	public int	   jackpotAnimValue[] = {0,0,0,0};

	public int	 	 buyFreeGamesMult  = 150;
	public int	 	 buyHoldAndWinMult = 170;
	public int 		 buyBonusType = 0;
	public boolean	 hasBuyBonus  = false;
	
	

	public int	 	 maxWin = 5000;
	public boolean 	 maxWinReached = false;
	
	public ObjectMap<Integer, Integer> hashJackpot			 =  new ObjectMap<Integer, Integer>();
	public ObjectMap<Integer, Integer> hashMinHitsJackpot    =  new ObjectMap<Integer, Integer>();
	public ObjectMap<Integer, Integer> hashMaxHitsJackpot    =  new ObjectMap<Integer, Integer>();
	public ObjectMap<Integer, Integer> hashResetHitsJackpot  =  new ObjectMap<Integer, Integer>();

	public boolean hasReelHighlight = false;
	
	public int firstReelHighlight  = 0;
	public int symbolReelHighlight = 0;
	
	public int gameMode;
	public GameOutcome outcome = new GameOutcome();    // last outcome
//	public GameOutcome restoreOutcome = new GameOutcome();    // last outcome
	public GameOutcome pfgOutcome; // prior free games outcome
	
	public GameOutcome outcomeLastGame = new GameOutcome();    // last outcome

	public boolean hasHomeButton  = true;
	public boolean hasHistoryButton  = false;

	public boolean hasLobbyButton  = true;
	
	public boolean hasReloadButton  = true;
	
	public boolean hasBuyFeature    = true;

	public boolean turboGame		   = false;
	public boolean turboSpinIsEnabled  = true;
	
	public boolean skipIntro  = false;
	public boolean skipScreen = false;
	
	public boolean autoplay;
	public int autoplayCounter;
	
	public int autoplayCurrentWin;
	public int autoplayMaxWin;
	
	public boolean     showAddFreeSpins 	= false;
	public boolean 	   hasAddFreeSpins 		= false;
	public boolean 	   claimAddFreeSpins	= false;

	public int 		   addFreeSpinsCnt      = 10;
	public int 		   addFreeSpinsWin      = 0;
	public int 		   addFreeSpinsTotalCnt = 0;
	
	public int autoplayCurrentLost;
	public int autoplayMaxLost;
//	public int autoplayLimit = 19;
	public boolean autoplayUnlimited;
	public boolean autoplayWinUnlimited;
	public boolean autoplayLostUnlimited;
	
	public int holdAndWinCounter = 0;
	public int visibleHoldAndWinCounter = 0;
	public int holdAndWinGamesWon = 0;

	public int visibleFreeGamesCounter = 0;
	public int freeGamesCounter = 0;
	public int freeGamesCounterOnScatter = 0;
	public int freeGamesChangeMultiplier = 0;
	public int freeGamesWon = 0;

	public int respinGamesWon = 0;
	public int respinCounter = 0;

	public int freeGamesMultiplier = 0;
	
//	public int freeGamesWonFake = 0;
	public int freeGamesSpecialSymbol;
	
//	public int maxFreeGamesCounter = 0;
	
	public int timerCounter;
	public int idleTimerCounter;
	
	public int reelCounter;
	public int lineCounter;
	public int onscreenSpecialWinMeter = 0;
	public int onscreenWinMeter = 0;
	public int onscreenBigWinMeter = 0;
	public int onscreenCreditMeter = 0;
	
	
	
	public int     magicianReel = -1;
	public boolean freeGamesRespin  = false;
	
	public int addWild = -1;

	public int mysterySymbol = 4;
	public int restoreFGMultiplier = 1;
	public int restoreDigitsMystery = 1;
	public int restoreDigitsWilds = 1;
//	public boolean skipCounter  = false;
	
	/****************************************************************
	 * Free Games Acc
	 ***************************************************************/
//	public int freeGamesCnt= 0;
	
	/****************************************************************
	 * Double-up critical variables
	 ***************************************************************/
	public int currentCard;
	public int cardIndex;
	public int playerCardSelected;
	public int[] deck = new int[52];
	public int[] doubleUpHistory = new int[10];

//	public int[] selectedBoostLines  = {0,0,0,0,0};
//	public int[] selectedBoostSymbol = {0,0};
	
}
