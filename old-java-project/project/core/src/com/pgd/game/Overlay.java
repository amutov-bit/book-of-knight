package com.pgd.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.AddFreeSpins;

import com.pgd.game.actors.AdditionalText;
import com.pgd.game.actors.BigWin;
import com.pgd.game.actors.BuyBonusConfirm;
import com.pgd.game.actors.DigitsJackpot;
import com.pgd.game.actors.HoldAndWinText;
import com.pgd.game.actors.FreeGamesTitle;
import com.pgd.game.actors.InsertCredits;
import com.pgd.game.actors.JackpotAnimateWin;
import com.pgd.game.actors.JackpotsTitle;
import com.pgd.game.actors.MaxWinReached;
import com.pgd.game.actors.MenuTurboSpins;
import com.pgd.game.actors.JackpotShowSymbols;
import com.pgd.game.actors.FreeGamesBook;
import com.pgd.game.actors.FreeGamesCongrats;
import com.pgd.game.actors.FreeGamesText;
import com.pgd.game.actors.ShowWin;
import com.pgd.game.actors.JackpotDigitsSymbols;
import com.pgd.game.actors.JackpotShowWin;
import com.pgd.game.screens.HelpScreenPaytable;

public class Overlay extends Group {

	private BookOfKnight game;
	public  BigWin bigWin;

	public AdditionalText addText;

	public AddFreeSpins addFreeGame;
	
	
	
	private Image frame;

	public  InsertCredits insertCredit;
	
	Group groupShowWin;

	public  ShowWin showWin;

	public  MaxWinReached maxWinReached;
	
	Group groupBigWin = new Group();
	Group groupFreeGamesBook = new Group();
	
         public MenuTurboSpins menuTurboSpins;

         public HoldAndWinText holdAndWinText;

         public FreeGamesBook freeGamesBook;

         public FreeGamesText freeGamesText;
    
	public FreeGamesTitle freeGamesTitle;

	public FreeGamesTitle holdAndWinTitle;

	public FreeGamesCongrats freegamesCongrats;

	public JackpotsTitle jackpots;

	public JackpotShowSymbols jackpotSymbols;
	
	public JackpotDigitsSymbols jackpotDigitsSymbols; 

	public JackpotAnimateWin jackpotAnimateWin;
	
	public BuyBonusConfirm bonusConfirm;
	
	public Overlay(BookOfKnight game) {
		this.game = game;
		setPosition(0, 0);
		setOrigin(game.VIRTUAL_WIDTH/2, game.VIRTUAL_HEIGHT/2);
		setVisible(true);
	}
	
	
	public void loadAssets() {
	}	
	
	/**
	 * 
	 */
	public void commitAssests() {

//		game.textures.getInterfaceAtlas().findRegion("podlojka").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		frame = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("podlojka")));
		
		jackpotSymbols = new JackpotShowSymbols(game);
		jackpotSymbols.commitAssets();
		jackpotSymbols.setVisible(true);
//		addActor(jackpotSymbols);
		
//		Group test = new Group();
		jackpotDigitsSymbols = new JackpotDigitsSymbols(game);
		jackpotDigitsSymbols.commitAssets();
		jackpotDigitsSymbols.setScale(1f);
//		addActor(jackpotDigitsSymbols);
		
		Texture texture = game.manager.get("podlojka.png", Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		frame = new Image(texture);
		frame.setPosition(86, 1080 - 20 - 1004);
		frame.setVisible(false);
		addActor(frame);
		
		
		
		groupShowWin = new Group();
		
		showWin  = new ShowWin(game);
		showWin.commitAssets();
		showWin.setVisible(false);
		groupShowWin.addActor(showWin);
		addActor(groupShowWin);

		jackpots  = new JackpotsTitle(game);
		jackpots.commitAssets();
		jackpots.setVisible(true);
		addActor(jackpots);
		
		bigWin = new BigWin(game);
		bigWin.commitAssets();
		bigWin.setFormat(true);
		bigWin.setVisible(false);
		
		insertCredit = new InsertCredits(game);
		insertCredit.commitAssets();
		insertCredit.setVisible(false);
		addActor(insertCredit);

		freeGamesBook = new FreeGamesBook(game);
		freeGamesBook.commitAssets();
		freeGamesBook.setVisible(false);
		groupFreeGamesBook.setPosition(10, 40);
		groupFreeGamesBook.addActor(freeGamesBook);
		addActor(groupFreeGamesBook);
		
		game.textures.getInterfaceAtlas().findRegion("logo").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		game.titleLabel = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("logo")));
		game.titleLabel.setPosition(0 + 20, 1080 - 156 - 35 - 10);
//		game.titleLabel.setScale(0.8f);
		game.titleLabel.setVisible(true);
		
		holdAndWinText = new HoldAndWinText(game);
		holdAndWinText.commitAssets();
		holdAndWinText.setWin(5);
		addActor(holdAndWinText);
		holdAndWinText.setVisible(false);

		freeGamesText = new FreeGamesText(game);
		freeGamesText.commitAssets();
		freeGamesText.setWin(5);
		addActor(freeGamesText);
		freeGamesText.setVisible(false);
		
		freeGamesTitle  = new FreeGamesTitle(game);
		freeGamesTitle.commitAssets("fgleft_digit","fgleft");
		freeGamesTitle.setVisible(false);
		freeGamesTitle.setWinFirst(10);
		addActor(freeGamesTitle);

		holdAndWinTitle  = new FreeGamesTitle(game);
		holdAndWinTitle.commitAssets("respins_digits","respinleft_bg");
		holdAndWinTitle.setVisible(false);
		holdAndWinTitle.setWinFirst(10);
		addActor(holdAndWinTitle);

		jackpotAnimateWin  = new JackpotAnimateWin(game);
		jackpotAnimateWin.commitAssets();
		jackpotAnimateWin.setVisible(true);
//		addActor(jackpotAnimateWin);
		
//		addActor(game.spine);
		groupBigWin.addActor(bigWin);
		addActor(groupBigWin);
//		groupBigWin.setScale(0.8f);
		groupBigWin.setPosition(0, -80);

		maxWinReached = new MaxWinReached(game);
		maxWinReached.commitAssets();
		maxWinReached.setVisible(false);
		addActor(maxWinReached);
		
		menuTurboSpins = new MenuTurboSpins(game);
		menuTurboSpins.commitAssets();
		menuTurboSpins.setVisible(false);

		freegamesCongrats = new FreeGamesCongrats(game);
		freegamesCongrats.commitAssets();
		freegamesCongrats.setWin(123535);
		addActor(freegamesCongrats);
		freegamesCongrats.setVisible(false);

		bonusConfirm = new BuyBonusConfirm(game);
		bonusConfirm.commitAssets();
		bonusConfirm.setVisible(false);
		
		addFreeGame = new AddFreeSpins(game);
		addFreeGame.commitAssets();
		addFreeGame.setVisible(false);
		
//		showAddFreeSpins();
		
//		showHoldAndWinWins();
		
//		showJackpotSymbols();
		
//		freeGamesTitle.setWinFirst(8);
//		freeGamesTitle.setVisible(true);

//		HoldAndWinTitle.setWinFirst(8);
//		HoldAndWinTitle.setVisible(true);

//		showFreeGamesBook(10);
		
//		showWin(594500);
		
//		showFreeGamesWin();

//		showHoldAndWinsText(3);
		
//		freegamesCongrats.setWin(123);
		
//		showFreeGamesText(10);

//		showHoldAndWinCongrats();
		
//		bigWin.setWin(123);
//		showBigWin();
		
//		showInsertCredit();
		
//		showMaxWinReached();				
		
		setVisible(true);
	}
	
	public void showHoldAndWinWins(){
		
		game.menu.showJackpotWin(true);
		game.menu.setJackpotWin(0);
		jackpotAnimateWin.reset();
//		jackpotAnimateWin.animate();
		jackpotAnimateWin.animateJackpotSymbols();
	}
	
	public void showJackpotDigitsSymbolsAll(){
		for(int pos = 0; pos < game.SYMBOLS; pos++){
			for(int reel = 0; reel < game.REELS; reel++){
				jackpotDigitsSymbols.showJackpotDigits(reel, pos);
			}
		}
	}
	
	public void showJackpotDigitsSymbols(int reel, int pos){
		jackpotDigitsSymbols.showJackpotDigits(reel, pos);
	}

	public void hideJackpotDigitsSymbols(){
		jackpotDigitsSymbols.hideJackpotDigits();
	}
	
	public void showJackpotSymbols() {
		
		for(int r = 0; r < game.REELS; r++){
			for(int s = 0; s < game.SYMBOLS; s++ ) {
				if(game.reels.matrixHoldAndWin[s][r] > 0){
					jackpotSymbols.showSymbol(true, r, s, game.reels.matrixHoldAndWin[s][r]);
				}
			}
		}
	}
	public void hideJackpotSymbols(){
		jackpotSymbols.hidejackpotSymbols();
	}
	
//	public void showBookAnim(){
//		bookAnimation.setVisible(true);
//	}
//	
//	public void hideBookAnim(){
//		bookAnimation.setVisible(false);
//	}
	
	public boolean hideIsPressed(){
		return insertCredit.isPressed();
	}

	public boolean menuTurboSpinsIsEnalbled(){
		return game.context.turboSpinIsEnabled;
	}

	public void showWin(int win){
		showWin.setVisible(true);
		showWin.setWin(win);
		showWin.show();
	}

	public void hideWin(){
		showWin.setVisible(true);
		showWin.hide();
	}
	
	public void showMenuTurboSpins(){
		game.reels.showFrame(true);
		menuTurboSpins.setVisible(true);
		game.menu.setStatus("");
		game.menu.disableButtons();
	}
	
	public void hideMenuTurboSpins(){
		game.reels.showFrame(false);
		menuTurboSpins.setVisible(false);
		game.menu.resetButtons();
	}
	
	public void showInsertCredit(){
		
		game.reels.showFrame(true);
		insertCredit.setVisible(true);
	}
	
	public void hideInsertCredit(){
		
		game.reels.showFrame(false);
		insertCredit.setVisible(false);
		insertCredit.setIsPressed();
	}
	
	public void showMultiplier(){
//		game.reels.showFrame(true);
//		multilier.setVisible(true);
	}

	public void showFrame(boolean vis){
//		frame.setVisible(vis);
	}
	
	public void hideMultiplier(){
//		game.reels.showFrame(false);
//		multilier.setVisible(false);
	}
	
	public void setHoldAndWinTitleLabel(int first)
	{
		holdAndWinTitle.setVisible(true);
		game.reels.showTitle(false);
		holdAndWinTitle.setWinFirst(first);
	}
	
	public void showHoldAndWinTitle(boolean vis)
	{
		holdAndWinTitle.setVisible(vis);
	}

	public void setFreeGamesTitleLabelFirst(int first)
	{
		freeGamesTitle.setVisible(true);
		game.reels.showTitle(false);
		freeGamesTitle.setWinFirst(first);
	}
	
	public void showFreeGamesTitle(boolean vis)
	{
		
		freeGamesTitle.setVisible(vis);
	}
	
	
	public void showFreeGamesCrack(){
	}
	
	public void hideCrack(){
	}
	
	/**
	 * Show big win
	 */
	public void showBonus() {
	}
	
	public void showTitle(){	
	}
	/**
	 * Hide big win
	 */
	public void hideBonus() {
	}	
	
	public void showAdditionalSymbols(){
		
	}
	
	/**
	 * Show big win
	 */
	public void showBonusText() {
//		game.reels.showFrame(true);
//		bonusText.setVisible(true);
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideBonusText() {
//		game.reels.showFrame(false);
//		bonusText.setVisible(false);
	}	


	/**
	 * Show big win
	 */
	public void showHoldAndWinsText(int win) {
//		game.reels.showFrame(true);
		frame.setVisible(true);
		holdAndWinText.setWin(win);
		holdAndWinText.setVisible(true);
		
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideHoldAndWinText() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		holdAndWinText.setVisible(false);
	}

	/**
	 * Show big win
	 */
	public void showFreeGamesBook(int win) {
//		game.reels.showFrame(true);
		frame.setVisible(true);
		freeGamesBook.setWin(win);
		freeGamesBook.setVisible(true);
		
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideFreeGamesBook() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		freeGamesBook.setVisible(false);
	}
	
	/**
	 * Show big win
	 */
	public void showFreeGamesText(int win) {
//		game.reels.showFrame(true);
		frame.setVisible(true);
		freeGamesText.setWin(win);
		freeGamesText.setVisible(true);
		
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideFreeGamesText() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		freeGamesText.setVisible(false);
	}
	
	/**
	 * Show big win
	 */
	public void showFreeGamesC(int win) {
	}
	
	
	/**
	 * Show big win
	 */
	public void showBigWin() {
		game.reels.showFrame(true);
		frame.setVisible(true);
		bigWin.setVisible(true);
	}

	
	/**
	 * Hide big win
	 */
	public void hideBigWin() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		bigWin.setVisible(false);
	}	

	/**
	 * Show big win
	 */
	public void showCoctailWin(int win, int coctail) {
//		game.reels.showFrame(false);
//		coctailAnim.setWin(win / game.meters.getDenomination());
//		coctailAnim.setVisible(true);
//		coctailAnim.setVisible(true, coctail);
//		game.reels.showTitle(false);
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideCoctailWin() {
//		game.reels.showFrame(false);
//		coctailAnim.setVisible(false);
	}	
	
	public void setBigWin(int win) {
		bigWin.setWin(win);
	}	

	/**
	 * Show big win
	 */
	public void showFreeGamesWin() {
		game.reels.showFrame(true);
		freegamesCongrats.setWin((int) game.meters.fgwin / game.meters.getDenomination());
		freegamesCongrats.setVisible(true);
	}

	public void showHoldAndWinCongrats() {
		game.reels.showFrame(true);
		freegamesCongrats.setWin((int) game.meters.holdAndWin / game.meters.getDenomination());
		freegamesCongrats.setVisible(true);
	}
	
	
	/**
	 * Hide big win
	 */
	public void hideHoldAndWinCongrats() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		freegamesCongrats.setVisible(false);
	}
	
	/**
	 * Hide big win
	 */
	public void hideFreeGamesWin() {
		game.reels.showFrame(false);
		frame.setVisible(false);
		freegamesCongrats.setVisible(false);
	}	
	
	/**
	 * set big format
	 */
	public void setBigWinFormat(boolean decimal) {
		bigWin.setFormat(decimal);
	}
	
	public boolean bigWinAnimStopped(){
		return bigWin.stopAnimation();
	}
	
	public void showAddFreeSpins(){
		
		if(game.context.showAddFreeSpins || (game.context.hasAddFreeSpins && game.context.addFreeSpinsCnt >= 0)){
			game.menu.disableButtons();
			addFreeGame.setFreeGamesCnt(game.context.addFreeSpinsCnt);
			addFreeGame.setFreeGamesBet(game.meters.getTotalBet());
			
			if(game.context.gameMode != game.context.FREE_GAMES && !game.context.outcome.hasFreeGames){
				addFreeGame.setFreeGamesWin(game.context.addFreeSpinsWin);
			}
			
			addFreeGame.showCloseButton(game.context.showAddFreeSpins);
			addFreeGame.setVisible(true);
		} else {
			game.menu.resetButtonWithDelay();
		}
	}

	public void showAddFreeSpinsCongrats(){
		addFreeGame.setFreeGamesWin(game.context.addFreeSpinsWin);
		addFreeGame.setFreeGamesCongratsWin(game.context.addFreeSpinsWin);
	}

	public void hideAddFreeSpinsCongrats(){
		addFreeGame.setVisible(false);
	}

	public void hideAddFreeSpins(){
		addFreeGame.setVisible(false);
	}
	
	public void showMaxWinReached(){
		
		maxWinReached.setMaxWin();
		game.reels.showFrame(true);
		frame.setVisible(true);
		maxWinReached.setVisible(true);
	}
	
	public void hideMaxWinReached(){
		
		frame.setVisible(false);
		game.reels.showFrame(false);
		maxWinReached.setVisible(false);
	}


	public void changeTitle(boolean b) {
		// TODO Auto-generated method stub
		
	}


	public void deactJackpotValues() {
		for (int s = 0; s < game.SYMBOLS; s++) {
			for (int r = 0; r < game.REELS; r++) {
				game.overlay.jackpotDigitsSymbols.setDeact(s, r, true);
			}
		}
	}
	
}
