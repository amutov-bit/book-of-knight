package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

public class GameOutcome  {
	
	public int matrix[][] = {
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
			{ 0, 0, 0, 0, 0,},
		};
	
	/*
	 * <cod mode bb>
	 */
//	public int stopNextReelTimeout[] = {22, 22, 22, 22, 22};
	public int stopNextReelTimeoutNormal[] = {0, 12, 12, 12, 12};
	public int stopNextReelTimeoutFreeGames[] = {24, 24, 24, 24, 24};
	public int stopNextReelTimeoutHoldAndWin[] = {24, 24, 24, 24, 24};
	
	public int hideReels[] = {0, 0, 0, 0, 0, 0, 0, 0};
	
	public boolean hasSpecialWins = false;
	public boolean hasWin = false;
	public boolean hasFreeGames = false;
	public boolean hasHoldAndWin = false;
	public boolean hasBonusWin = false;
	public boolean hasRespin = false;
	public boolean hasPaytableWins = false;
	public boolean hasWild = false;
	public boolean hasTransformAnim = false;
	public boolean hasCollect = false;	
	public List<Win> wins = new ArrayList<Win>();
	public List<Win> winsSpecial = new ArrayList<Win>();

	public int bonusMultiplier = 0;
	public int bonusWin = 0;
	
	public boolean hasBonusCashWin = false;	
	public int bonusCashWin = 0;
	
//	public boolean changeMultipler = false;
//	public int freeGamesMultiplier = 1;

	public int addFreeGamesCnt = 0;
	
	
	
}
