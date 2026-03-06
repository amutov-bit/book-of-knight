package com.pgd.game.states;

public interface ControllerListener {

	public void onStartSpin();
	public void onReelsStop();
	public void onFreeGamesWin();
	public void onFreeGamesTakeWins();
	public void onSpinEnd();
	public void onTakeWin();
	public void onWinToCredit();
	public void onShowWins();
}
