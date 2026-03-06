package com.pgd.game.net;

public interface IGameServerLink {
	boolean connect(String url, String uid);
	boolean spin();
	boolean spinAddFreeGames();
	boolean disconnect();
	boolean isConnected();
	boolean spinEnded();
	
	void onConnect();
	void onDisconnect();
	void onMessage();
	void onError(int err);
	
	int getBalance();
	boolean setParams(int betPerLine, int linesSelected, int denom);
	boolean freespin();
	
	void stopLogoAnim();
	void updateProgress(float progress);
	boolean bonus(int bonusWinPosition);
	void setBonusParams(int bonusWinPosition, int betPerLine, int lines, int denomination);
	void console(String str);

	void reload();
	
	void showHowToPlay();
	boolean error();
	void onHomeButton();
	int orientation();
	void fullScreenCustom();
	boolean setSettings(int skipIntro, int skipScreen, int turboGame);
	
	void setParamsBuyFreeGames(int betPerLine, int lines, int denomination, boolean hasBuyBonus, int buyBonusType);

	void onHistoryButton();
	
	void setClaimParams(boolean claimAddFreeSpins);
	
}
