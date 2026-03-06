package com.pgd.game;

import com.badlogic.gdx.Gdx;

public class Meters {
	
	public  int MAX_BET_PER_LINE = 1000;
	public  int MAX_LINES = 10;
	public  int MAX_DENOM = 1;

	private  int MIN_BET_PER_LINE = 1;
	private  int MIN_LINES = 10;
	private  int MIN_DENOM = 1;
	
	public int incDenom = 0;
	public int incBet = 0;
	
	public long credit;
	private int denomination;
	private int bet;
	private int totalBet;
	
	private int totalBetAddLines;
	
	private int totalBetPrev;
	public int win;
	public int fgwin;
	public int holdAndWin;
	public int currentWin;
	private int lines;
	private long totalGamesPlayed;
	private long hitRateCnt;
	private long totalCoinsIn;
	private long totalCoinsOut;
	private long totalIn;
	private long totalOut;
	private BookOfKnight game;
	
	private int denominationPrev;
	private int betPrev;
	private int freeGamesCntPrev;
	private int linesPrev;
	
	
	public int betArray[]   = {1,2,3,4,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800,850,900,950,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,3000,4000,8000};
	
	private int betIndex = 0;
	private int denomIndex = 0;
	
	private String currency = "FUN";
	
	public Meters(BookOfKnight game) {
		denomination 	 = 1;//100000;
		credit 			 = 0;
		bet 			 = 1;
		lines 			 = MAX_LINES;
		win 			 = 0;
		fgwin 			 = 0;
		totalBet 		 = 0;
		totalBetPrev 	 = totalBet;
		totalGamesPlayed = 0;
		totalCoinsIn     = 0;
		totalCoinsOut    = 0;
		totalIn 		 = 0;
		totalOut 		 = 0;		
		hitRateCnt 		 = 0;		
		this.game 		 = game;
		
		denominationPrev = denomination;
		betPrev 		 = bet;
		freeGamesCntPrev = 0;
		linesPrev 		 = lines;
		
	}
	
	public void update() {
		totalBet = bet * lines * denomination;
	
		
		totalBetPrev 		= totalBet;
	}
	
	public void updateStart() {
		totalBet = bet * lines * denomination;
		
		totalBetPrev 		= totalBet;
		
		updatePrevStart();
	}
	
	public void updatePrevStart()
	{
		betPrev 		 = bet;
		linesPrev 		 = lines;
		denominationPrev = denomination;
	}
	
	public void updatePrev()
	{
		betPrev 		 = bet;
		linesPrev 		 = lines;
		denominationPrev = denomination ;
		freeGamesCntPrev = game.context.freeGamesCounter;
	}
	
	public void updateTotals() {
		totalGamesPlayed++;
		if(game.context.gameMode == game.context.MAIN_GAME)
		{
			totalCoinsIn  += totalBet;
			totalCoinsOut += win;
		} 
		else if(game.context.gameMode == game.context.FREE_GAMES)
		{
			totalCoinsOut += win;
			totalCoinsIn  += 0;
		}
		else 
		{
			totalCoinsOut += win;
			totalCoinsIn  += 0;
		}
		
		if(win > 0)
		{
			hitRateCnt++;
		}
	}
	
	public void selectMaxbet() {
		bet = MAX_BET_PER_LINE;
		lines = MAX_LINES;
		denomination = MAX_DENOM;
		
//		for(int i = 0; i < denomArray.length; i++){
//			if(denomArray[i] == MAX_DENOM){
//				denomIndex = i;
//			}
//		}
		
		for(int i = 0; i < betArray.length; i++){
			if(betArray[i] == MAX_BET_PER_LINE){
				betIndex = i;
			}
		}
		
//		betIndex = betArray.length - 1;
//		denomIndex = denomArray.length - 1;
		
		update();
	}
	

	
	public void incrementBetPerLine() {
		
		if(betArray[betIndex] < MAX_BET_PER_LINE){ 
			betIndex++;
		} else {
			if(MIN_BET_PER_LINE > 1){
				for(int i = 0; i < betArray.length; i++){
					if(betArray[i] == MIN_BET_PER_LINE){
						betIndex = i;
					}
				}
			} else {
				betIndex = 0;
			}
		}
		
		bet = betArray[betIndex];
		
		update();
	}
	
	public void decrementBetPerLine() {
		if(betArray[betIndex] > MIN_BET_PER_LINE){
			betIndex--;
		} else {
			for(int i = 0; i < betArray.length; i++){
				if(betArray[i] == MAX_BET_PER_LINE){
					betIndex = i;
				}
			}
		}
		
		bet = betArray[betIndex];
		
		update();
	}	
	
	public void incrementLines() {
		if(lines < MAX_LINES) lines++;
		else				  lines = MIN_LINES;
		update();
	}
	
	public void decrementLines() {
		if(lines > MIN_LINES) lines--;
		else		  lines = MAX_LINES;
		update();
	}
	
	public void incrementDenomination() {
//		if(denomArray[denomIndex] < MAX_DENOM){ 
//			denomIndex++;
//		} else {
//			if(MIN_DENOM > 1){
//				for(int i = 0; i < denomArray.length; i++){
//					if(denomArray[i] == MIN_DENOM){
//						denomIndex = i;
//					}
//				}
//			} else {
//				denomIndex = 0;
//			}
//		}
//		
//		denomination = denomArray[denomIndex];
		
		update();
	}
	
	public void decrementDenomination() {
//		if(denomArray[denomIndex] > MIN_DENOM){
//			denomIndex--;
//		}else{
//			for(int i = 0; i < denomArray.length; i++){
//				if(denomArray[i] == MAX_DENOM){
//					denomIndex = i;
//				}
//			}
//		}
//		
//		denomination = denomArray[denomIndex];
		
		update();
	}		
	
	public void setLines(int x) {
		lines = x;
		
		if(MIN_LINES > lines)	lines = MIN_LINES;
		
		updateStart();
	}
	
	public void setBetPerLine(int x) {
		bet = x;
		
		if(MIN_BET_PER_LINE > bet)	bet = MIN_BET_PER_LINE;
		
		for(int i = 0; i < betArray.length; i++){
			if(bet == betArray[i]){
				betIndex = i;
			}
		}
		
		updateStart();
	}

	public void setDenom(int x) {
		denomination = x;
		
//		if(MIN_DENOM > denomination)	denomination = MIN_DENOM;
//		
//		for(int i = 0; i < denomArray.length; i++){
//			if(denomination == denomArray[i]){
//				denomIndex = i;
//			}
//		}
		
		updateStart();
	}
	
	public void setMaxDenom(int x) {
		MAX_DENOM = x;
	}

	public void setMaxLines(int x) {
		MAX_LINES = x;
	}

	public void setCurrency(String c) {
		currency = c;
	}
	
	public void setMaxBetPerLine(int x) {
		MAX_BET_PER_LINE = x;
	}

	public void setMinDenom(int x) {
		MIN_DENOM = x;
	}
	
	public void setMinLines(int x) {
		MIN_LINES = x;
	}
	
	public void setMinBetPerLine(int x) {
		MIN_BET_PER_LINE = x;
	}

	public String getCurrency() {
		return currency;
	}
	
	public int getMinDenom() {
		return MIN_DENOM;
	}
	
	public int getMinLines() {
		return MIN_LINES;
	}
	
	public int getMinBetPerLine() {
		return MIN_BET_PER_LINE;
	}
	
	public int getDenomination() {
		return denomination;
	}
	
	public int getLines() {
		return lines;
	}
	
	public int getBetPerLine() {
		return bet;
	}
	
	public int getTotalBet() {
		return totalBet;
	}
	
	public void setTotalBetAddLines(int x){
		totalBetAddLines = x;
//		Gdx.app.debug("Meters", "<< 1 totalBetAddLines = " + totalBetAddLines);
	}

	public int getTotalBetAddLines(){
//		Gdx.app.debug("Meters", " << 2 totalBetAddLines = " + totalBetAddLines);
		return totalBetAddLines;
	}
	
	public int getTotalBetInCredits() {
		return (int) totalBet/denomination;
	}

	public long getTotalHitRate() {
		return hitRateCnt;
	}
	
	public long getTotalGamesPlayed() {
		return totalGamesPlayed;
	}
	
	public long getTotalCoinsIn() {
		return totalCoinsIn;
	}
	
	public long getTotalCoinsOut() {
		return totalCoinsOut;
	}	
	
	public long getTotalIn() {
		return totalIn;
	}
	
	public long getTotalOut() {
		return totalOut;
	}		
	
    public String formatNumber(long value, boolean hasBigNumberK) {
    	
    	
    	String pattern = "";
    	

    	if(value >= 1000000 && hasBigNumberK){
    		
    		if(value % 100000 > 0){
    			pattern = (Long.toString(value / 100000) + "." + (value % 100000)/10000 + "K");
    		} else {
    			pattern = (Long.toString(value / 100000) + "K");
    		}
    		
    		return pattern;
    	}
    	
    	pattern = game.pattern;
    	
    	String output =  "";
    	String bigNumSeparate = String.valueOf(pattern.charAt(1));
    	String decimal = String.valueOf(pattern.charAt(5));

    	int beforeDec = (int) (value/100);
    	int size = Long.toString(beforeDec).length();

    	for(int i = 0; i < size; i++){
    		long num = beforeDec % 10;
    		output = Long.toString(num) + output;
    		beforeDec /= 10;
    	}
    	
    	if(!bigNumSeparate.equals("#")){
	    	if(size > 3){
	    		output = output.substring(0, size - 3) + bigNumSeparate + output.substring(size - 3, output.length());
	    		if(size > 6){
	    			output = output.substring(0, size - 6) + bigNumSeparate + output.substring(size - 6, output.length());
	    		}
	    	}
    	}
    	
    	if(!decimal.equals(" ")){
    		output +=  decimal + (value / 10 % 10) + (value % 10);
    	}
    	
    	return output;
    }
    
	/**
	 * Get credit in money/credits
	 * @param credits
	 */
	public long getCredit(boolean inCredits) {
		return inCredits? (credit / denomination) : credit;
	}
}
