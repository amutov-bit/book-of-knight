package com.pgd.game.base;

import com.pgd.game.Sounds.SoundTrack;

/**
 * Win class file.
 * 
 *  Simple object that represents result from a spin. It can a line win or near-miss win (no credits win)
 *  that is simply used to highlight a combination on the screen.  
 *  
 * @author Dimitar
 *
 */
public class Win {

	public enum Type {LINE, SCATTER, NEAR_MISS, WILD, BONUS, SYMBOL, SPECIAL_LINE};
	public Type type = Type.LINE;
	
	/**
	 * A matrix representation of the screen symbols that will be highlighted 
	 */
	public int highlight[][] = {
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0},
	};
	
	/**
	 * A matrix representation of the screen magican that will be highlighted 
	 */
//	public int highlightMagician[] = {
//		 0, 0, 0, 0, 0 
//	};
	
	/**
	 * Winning line number, bet (credits wagered), symbol index, the number of symbols
	 * counted on a line, and multiplier  
	 */
	public int winningLine = 0;
	public int betPerLine = 0;
	public int symbol = -1;
	public int cnt = 0;
	public int mult = 0;
	public int multCnt = 1;
    public int multiplier = 1;
	
	/**
	 * Flag set when a combination includes a wild symbol.
	 */
	public boolean hasWild = false;
	public boolean wildCount = false;
	
	/**
	 * Timeout variable used in Controller to determine duration of the highlighting animation. 
	 */
	public int highlightTimeout = 0;
	
	/**
	 * Sound 
	 */
	public SoundTrack sound = SoundTrack.LOW_WIN;
}
