package com.pgd.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.GameOutcome;
import com.pgd.game.base.Win;
import com.pgd.game.base.Win.Type;


public class Math {
	
	public final static int MAGICIAN = 133;
	public final static int WILD = 0;
	public final static int SCATTER = 0;
	public final static int BONUS = 129;
	
	public final static int EMPTY_SYMBOL = 14;
	
	public final static int JACKPOT_SYMBOL = 10;
	public final static int JACKPOT_MINOR  = 11;
	public final static int JACKPOT_MAJOR  = 12;
	public final static int JACKPOT_GRAND  = 13;
//	public final int FREE_GAMES_AWARDED = 10;
	
	public int linesSelected = 10;
	public int betPerLine = 0;
	
	public int FREE_GAMES_AWARDED = 5;
	
	public int[] bonusWins = {5, 10, 20, 30, 50};		// 0 is for collect , 1 is for win	
	
	private final int ROWS = 5;
	private final int COLUMNS = 3;
	
	/**
	 * Winning lines
	 */
	public int[][] lines = {
//			   {1, 1, 1, 1, 1}, // 1
//			   {0, 0, 0, 0, 0}, // 2
//			   {2, 2, 2, 2, 2}, // 3
//			   {0, 1, 2, 1, 0}, // 4
//			   {2, 1, 0, 1, 2}, // 5
//			   {1, 0, 1, 2, 1}, // 6  
//			   {1, 2, 1, 0, 1}, // 7
//			   {0, 0, 1, 2, 2}, // 8
//			   {2, 2, 1, 0, 0}, // 9
//			   {0, 1, 0, 1, 0}, // 10
//			   {2, 1, 2, 1, 2}, // 11   
//			   {1, 0, 0, 0, 1}, // 12 
//			   {1, 2, 2, 2, 1}, // 13
//			   {0, 1, 1, 1, 0}, // 14  
//			   {2, 1, 1, 1, 2}, // 15
//			   {1, 1, 0, 1, 1}, // 16
//			   {1, 1, 2, 1, 1}, // 17
//			   {0, 2, 0, 2, 0}, // 18
//			   {2, 0, 2, 0, 2}, // 19
//			   {2, 0, 1, 0, 2}, // 20	

			
			   {1, 1, 1, 1, 1}, //1			
			   {0, 0, 0, 0, 0}, //2			
			   {2, 2, 2, 2, 2},	//3	
			   {0, 1, 2, 1, 0},	//4		
			   {2, 1, 0, 1, 2},	//5		
			   {1, 2, 2, 2, 1},	//6		
			   {1, 0, 0, 0, 1},	//7		
			   {2, 2, 1, 0, 0},	//8		
			   {0, 0, 1, 2, 2},	//9		
			   {2, 1, 1, 1, 0},	//10	
			   
	};
	
	
	

	/**
	 * Reel strips
	 */
	public int[][] stripsNormalGames = {
			// 96%
			{
				5,1,1,2,5,3,2,4,1,2,2,0,2,1,2,10,10,10,1,6,3,8,1,6,7,3,1,5,2,9,2,3,4,1,3,4,
				5,1,1,2,5,3,2,4,1,2,2,0,2,1,2,1,6,3,8,1,6,7,3,1,5,2,9,2,0,3,4,1,3,4,
				5,1,1,2,5,3,2,4,1,2,2,0,2,1,2,1,6,3,8,1,6,7,3,1,5,2,9,2,3,4,1,3,4,
				5,1,1,2,5,3,2,4,1,2,2,2,1,2,1,6,3,8,1,6,7,3,1,5,2,9,2,0,3,4,1,3,4,

				5,1,1,2,5,11,3,2,4,1,2,2,0,2,1,11,2,1,6,3,8,1,6,7,3,11,1,5,2,9,2,3,4,1,3,4,
				5,1,1,2,5,12,3,2,4,1,2,2,0,2,1,11,2,1,6,3,8,1,6,7,3,12,1,5,2,9,2,3,4,1,3,4,
				5,1,1,2,5,13,3,2,4,1,2,2,0,2,1,11,2,1,6,3,8,1,6,7,3,11,1,5,2,9,2,3,4,1,3,4,
			},
		    {
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,0,3,5,4,4,2,4,5,
				4,8,5,5,1,5,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
		  
				4,8,5,5,1,5,11,5,5,4,3,9,8,4,2,7,1,11,6,4,4,7,0,2,5,1,11,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
				4,8,5,5,1,5,12,5,5,4,3,9,8,4,2,7,1,12,6,4,4,7,0,2,5,1,11,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
				4,8,5,5,1,5,13,5,5,4,3,9,8,4,2,7,1,11,6,4,4,7,0,2,5,1,11,3,5,4,4,2,4,5,
				4,8,5,5,1,5,10,10,10,5,5,4,3,9,8,4,2,7,1,6,4,4,7,0,2,5,1,3,5,4,4,2,4,5,
		    },
			{ 
				5,3,2,7,6,3,4,6,1,9,3,7,0,2,3,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,2,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,1,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,1,2,4,1,1,7,5,3,8,5,6,8,3,3,4,3,
		  
				5,3,2,7,6,3,11,4,6,1,9,3,7,0,2,3,2,4,1,11,1,7,5,3,8,5,6,11,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,2,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,1,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,12,4,6,1,9,3,7,0,2,3,2,4,1,11,1,7,5,3,8,5,6,11,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,2,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,1,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,13,4,6,1,9,3,7,0,2,3,2,4,1,11,1,7,5,3,8,5,6,11,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,2,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
				5,3,2,7,6,3,4,6,1,5,3,7,0,2,1,2,4,1,10,10,10,1,7,5,3,8,5,6,8,3,3,4,3,
			},
			{
				3,6,5,5,3,5,10,10,10,2,1,9,2,8,4,7,6,2,7,2,6,8,7,4,0,3,3,4,2,5,4,4,1,1,5,1,1,2,
				3,6,5,5,3,2,10,10,10,3,1,4,2,3,4,7,6,2,7,1,6,8,7,4,0,3,3,4,2,2,4,4,1,1,5,1,1,2,
				3,6,5,5,3,2,3,1,4,2,3,4,7,6,2,7,1,6,8,7,4,0,3,3,4,2,2,4,4,1,1,5,1,1,2,
		  
				3,6,5,5,3,5,11,2,1,9,2,8,4,7,6,2,11,7,2,6,8,7,4,0,3,3,4,2,5,4,11,4,1,1,5,1,1,2,
				3,6,5,5,3,2,10,10,10,3,1,4,2,3,4,7,6,2,7,1,6,8,7,4,0,3,3,4,2,2,4,4,1,1,5,1,1,2,
				3,6,5,5,3,5,12,2,1,9,2,8,4,7,6,2,11,7,2,6,8,7,4,0,3,3,4,2,5,4,12,4,1,1,5,1,1,2,
				3,6,5,5,3,2,10,10,10,3,1,4,2,3,4,7,6,2,7,1,6,8,7,4,0,3,3,4,2,2,4,4,1,1,5,1,1,2,
				3,6,5,5,3,5,13,2,1,9,2,8,4,7,6,2,11,7,2,6,8,7,4,0,3,3,4,2,5,4,11,4,1,1,5,1,1,2,
				3,6,5,5,3,2,10,10,10,3,1,4,2,3,4,7,6,2,7,1,6,8,7,4,0,3,3,4,2,2,4,4,1,1,5,1,1,2,
			},
			{
				7,4,5,3,1,0,4,5,3,5,7,7,5,1,9,1,3,6,3,3,10,10,10,2,6,1,3,5,8,6,2,2,4,4,4,8,1,3,
				7,2,5,3,1,0,2,2,3,2,7,7,5,1,1,1,3,6,1,1,10,10,10,2,6,1,3,5,2,6,2,2,4,2,2,8,1,3,
				7,2,5,3,1,0,2,2,3,2,7,7,5,1,1,1,3,6,1,1,2,6,1,3,5,2,6,2,2,4,2,2,8,1,3,
		  
				7,4,5,3,1,0,4,5,3,5,7,11,7,5,1,9,1,3,6,3,3,11,2,6,1,3,5,8,6,2,11,2,4,4,4,8,1,3,
				7,2,5,3,1,0,2,2,3,2,7,7,5,1,1,1,3,6,1,1,10,10,10,2,6,1,3,5,2,6,2,2,4,2,2,8,1,3,
				7,4,5,3,1,0,4,5,3,5,7,12,7,5,1,9,1,3,6,3,3,11,2,6,1,3,5,8,6,2,11,2,4,4,4,8,1,3,
				7,2,5,3,1,0,2,2,3,2,7,7,5,1,1,1,3,6,1,1,10,10,10,2,6,1,3,5,2,6,2,2,4,2,2,8,1,3,
				7,4,5,3,1,0,4,5,3,5,7,13,7,5,1,9,1,3,6,3,3,11,2,6,1,3,5,8,6,2,11,2,4,4,4,8,1,3,
				7,2,5,3,1,0,2,2,3,2,7,7,5,1,1,1,3,6,1,1,10,10,10,2,6,1,3,5,2,6,2,2,4,2,2,8,1,3,
			}
	};	
	

	
	public int[][] stripsFreeGames = {
			{
				6,7,2,5,1,2,6,1,4,3,5,1,2,3,0,9,1,2,4,1,5,2,1,5,2,1,3,2,1,4,2,3,1,2,4,1,0,2,3,1,7,2,6,5,8,1,3,8,1,3,0,2,1,6,4,3,2,9,3,2,5,4,1,2,3,1,2,
			},				 
			{
				5,4,3,8,4,5,1,4,5,1,2,5,4,2,1,4,5,7,4,8,5,4,3,5,4,3,5,4,1,5,9,7,5,8,4,5,2,1,4,7,5,9,3,5,1,6,4,7,1,5,4,2,0,4,5,2,4,6,2,8,1,2,
				5,4,3,8,4,5,1,4,5,1,2,5,4,2,1,4,5,7,4,8,5,4,3,5,4,3,5,4,1,5,9,7,5,8,4,5,2,1,4,2,5,9,3,5,2,6,4,7,1,5,4,2,1,4,5,2,4,6,2,8,1,2,
				5,4,3,8,4,5,1,4,5,1,2,5,4,2,1,4,5,7,4,8,5,4,3,5,4,3,5,4,1,5,9,7,5,8,4,5,2,1,4,2,5,9,3,5,2,6,4,7,1,5,4,2,1,4,5,2,4,6,2,8,1,2,
				5,4,3,8,4,5,1,4,5,1,2,5,4,2,1,4,5,7,4,8,5,4,3,5,4,3,5,4,1,5,9,7,5,8,4,5,2,1,4,2,5,9,3,5,2,6,4,7,1,5,4,2,1,4,5,2,4,6,2,8,1,2
			},			 
			{
				3,5,0,3,6,5,7,1,3,6,8,7,2,1,0,3,4,6,5,4,1,3,8,4,3,7,5,3,7,2,4,5,8,3,1,2,3,1,4,6,5,3,8,2,6,3,4,5,0,3,5,2,3,1,5,3,6,8,3,1,2,9,3,7,2,3,4,6,3,8,1,3,2,6,3,4,7,3,5,4,3,7,5,2,4,3,7,1,6,4,
			},						  
			{
				4,1,2,3,7,5,3,7,9,3,8,4,3,1,2,7,3,6,8,3,4,0,1,3,5,2,4,3,5,4,6,5,1,2,5,0,3,6,7,2,3,1,7,4,3,7,5,4,0,3,6,1,2,4,6,3,2,1,0,4,1,5,3,6,4,5,1,8,4,1,3,2
			},						  
			{
				1,5,3,4,2,8,3,2,7,3,5,1,2,3,1,2,5,4,3,0,7,1,2,3,4,1,5,6,4,5,7,3,2,1,7,3,1,2,8,3,2,1,6,8,9,2,6,4,2,3,1,7,3,5,1,6,3,7,2,5,1,6,0,4,2,6,3,5,2,3
			},
	};

	public int[][] stripsHoldAndWin = {
			// 96%
			{
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,12,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,4,3,2,4,10,2,2,10,10,4,2,1,10,2,10,10,6,3,8,10,2,5,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,3,3,2,4,10,2,2,10,10,3,2,1,10,2,10,3,6,3,8,10,1,4,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,2,3,2,4,10,2,2,10,10,2,2,1,10,2,10,2,6,3,8,10,3,3,6,7,3,10,1,5,2,9,10,1,3,4,13,3,4,
			},
		    {
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,12,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,4,3,2,4,10,2,2,10,10,4,2,1,10,2,10,10,6,3,8,10,2,5,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,3,3,2,4,10,2,2,10,10,3,2,1,10,2,10,3,6,3,8,10,1,4,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,2,3,2,4,10,2,2,10,10,2,2,1,10,2,10,2,6,3,8,10,3,3,6,7,3,10,1,5,2,9,10,1,3,4,13,3,4,
		    },
			{ 
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,12,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,4,3,2,4,10,2,2,10,10,4,2,1,10,2,10,10,6,3,8,10,2,5,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,3,3,2,4,10,2,2,10,10,3,2,1,10,2,10,3,6,3,8,10,1,4,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,2,3,2,4,10,2,2,10,10,2,2,1,10,2,10,2,6,3,8,10,3,3,6,7,3,10,1,5,2,9,10,1,3,4,13,3,4,
			},
			{
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,12,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,4,3,2,4,10,2,2,10,10,4,2,1,10,2,10,10,6,3,8,10,2,5,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,3,3,2,4,10,2,2,10,10,3,2,1,10,2,10,3,6,3,8,10,1,4,6,7,3,10,1,5,2,9,10,2,3,4,13,3,4,
				5,10,1,2,5,10,2,3,2,4,10,2,2,10,10,2,2,1,10,2,10,2,6,3,8,10,3,3,6,7,3,10,1,5,2,9,10,1,3,4,13,3,4,
			},
			{
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,12,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,11,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
				5,10,1,2,5,10,10,3,2,4,10,2,2,10,10,10,2,1,10,2,10,10,6,3,8,10,10,10,6,7,3,10,1,5,2,9,10,10,3,4,13,3,4,
			}
	};
	
	
	private String[] symbolsToText = { "BOOK", "10", "J", "Q", "K", "A", "TORCH", "AXE", "CHALICE", "KNIGHT"};
	
	/**
	 * Paytable
	 * 
	 * @todo: order by multiplier asc (top to bottom) 
	 * {symbol, count, multiplier}
	 */
	public int[][] paytable = {
			/* 0 is scatter  */
			/* 1 */
			{  1,  3,    5}, // 
			{  1,  4,   20}, // 
			{  1,  5,  100}, // 
			/* 2 */
			{  2,  3,    5}, // 
			{  2,  4,   20}, // 
			{  2,  5,  100}, // 
			/* 3 */
			{  3,  3,    5}, // 
			{  3,  4,   20}, // 
			{  3,  5,  100}, // 
			/* 4 */
			{  4,  3,    5}, // 
			{  4,  4,   30}, // 
			{  4,  5,  150}, // 
			/* 5 */
			{  5,  3,    5}, // 
			{  5,  4,   30}, // 
			{  5,  5,  150}, // 
			/* 6 */
			// {  6,  2,    5}, // 
			{  6,  3,   20}, // 
			{  6,  4,  100}, // 
			{  6,  5,  750}, // 
			/* 7 */
			// {  7,  2,    5}, // 
			{  7,  3,   20}, // 
			{  7,  4,  100}, // 
			{  7,  5,  750}, // 
			/* 8 */
			// {  8,  2,     5}, // 
			{  8,  3,    30}, // 
			{  8,  4,   400}, // 
			{  8,  5,  2000}, // 
			/* 9 */
			{  9,  2,    5}, // 
			{  9,  3,   100}, // 
			{  9,  4,  1000}, // 
			{  9,  5,  5000}, // 

	};
	
	public int[][] scatterPaytable = {
			{  0,  3,   2},  
			{  0,  4,   40},  
			{  0,  5,   1000},  
	};
	
	public int[][] bonusPaytable = {
	};
	
	/**
	 * Set lines selected
	 * @param lines
	 */
	public void selectLines(int lines) {
		this.linesSelected = lines;
	}
	
	/**
	 * Returns game wins
	 * @param matrix
	 * @return GameOutcome - a list of wins to process in Controller
	 */
	public GameOutcome getWins(int[][] matrix) {
		
		GameOutcome outcome = new GameOutcome();
		
		// Keep reel matrix copy in outcome
		for( int s = 0; s < 3; s++) {
			for( int r = 0; r < 5; r++) {
				outcome.matrix[s][r] = matrix[s][r];
			}
		}
		


		// Check winning lines and wilds
		List<Win> lineWins = checkWinningLines(matrix);
//		Win wilds = checkWilds(matrix, lineWins);
//		if(wilds != null) {
//			outcome.wins.add(wilds);
//			outcome.hasPaytableWins = false;
//			outcome.hasWin = false;
//			outcome.hasWild = true;
//		}		
		
		// Check scatters wins first
		Win win = null;
//			
//			int n = 0;
//			for(int s = 0; s < 5; s++) {
//				if(outcome.matrix[s][1] == 0) {
//					outcome.stopNextReelTimeout[2] = 120;
//					outcome.highlightReel[2] = 1;
//					n = 1;
//				}
//			}
//				
//			for(int s = 0; s < 5; s++) {
//				if(outcome.matrix[s][1] == 0 && n == 1) {
//					outcome.stopNextReelTimeout[3] = 120;
//					outcome.highlightReel[3] = 1;
//					
//				}
//			}
//			
//		}
		
		for(int i = 0; i < lineWins.size(); i++) {
			outcome.hasPaytableWins = true;
			outcome.hasWin = true;
			outcome.wins.add(lineWins.get(i));
		}

		win =checkScatterWin(matrix);
		
		if(win != null) {
			outcome.wins.add(win);
			outcome.hasPaytableWins = true;
			outcome.hasWin = (win.type==Type.SCATTER);
		}
		
		return outcome;
	}
	
	
	/**
	 * Checks 3x5 matrix against lines and returns a list of wins.
	 * @param int[][] - reel matrix
	 * @return List<Win> - list of wins
	 */
	public List<Win> checkWinningLines(int[][] matrix) {
		
		List<Win> wins = new ArrayList<Win>();
		Win win;
		int hasWild;
		int count = 0, symbol = -1, result;
		int lineHits[][] = {
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, // 20
				
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0},
				{0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}, {0x80, 0}
		};
		
		printMatrix(matrix);
		
		// traverse paytable
		for( int ptIndex = paytable.length - 1; ptIndex >= 0; ptIndex--) {
			count = paytable[ptIndex][1];
			symbol = paytable[ptIndex][0];
			
			// traverse lines selected
			for (int line = 0; line < linesSelected; line++) {
				result = 0;
				hasWild = 0;
				// The code expects that a line goes from left to right through all columns.
				for (int countOnCurrentLine = 0, reel = 0; reel < 5; reel++) {

//					Gdx.app.debug("Math ", "symbol = " + matrix[lines[line][reel]][reel] + " ::: lines[line][reel] = " + lines[line][reel]);
					int matrixSymbol = matrix[lines[line][reel]][reel];

					if(WILD == matrixSymbol) {
						hasWild = 1;
					}
					
					if ((symbol == matrixSymbol) || (WILD == matrixSymbol)) {
						countOnCurrentLine++;
						result = countOnCurrentLine;
					} else {
						break;
					}
				}

				if (result == count) {
					if (lineHits[line][0] == 0x80  || (paytable[lineHits[line][0]][2] < paytable[ptIndex][2])) {
						lineHits[line][0] = ptIndex;
						lineHits[line][1] = hasWild;
					}
				}
			}
		}
		
		for( int i = 0; i < 20; i++) {
			if(lineHits[i][0] != 0x80) {
				win = new Win();
				win.type = Type.LINE;
				
				win.winningLine = i;
				win.symbol = paytable[lineHits[i][0]][0];
				win.cnt = paytable[lineHits[i][0]][1];
				win.mult = paytable[lineHits[i][0]][2];

				// set variable highlight duration for 
				// short/long winning lines
//				win.highlightTimeout = (win.cnt > 3)? 100 : 50;
				
				// Has WILD?
				win.hasWild = (lineHits[i][1]==1);
				wins.add(win);
				
				int[] line = getLine(i);
				for(int r = 0; r < win.cnt; r++){
					win.highlight[line[r]][r] = 1;
				}	
				
				// Set sound and timeout
				switch (win.symbol) {
					case 0:
//						win.sound = win.sound = SoundTrack.SYMBOLS;
						win.highlightTimeout = 190; 
					default: 
//						win.sound = win.sound = SoundTrack.WILD;
						win.highlightTimeout = 90; 
						break;
				}				
			}
		}
		
		
		return wins;
	}
	
	/**
	 * Check free games win
	 */
	public Win checkFreeGamesWin(int[][] matrix) {
		Win win = new Win();
		
		win.type = Type.NEAR_MISS;
		win.mult = 0;
		win.cnt = 0;
		win.symbol = SCATTER;
		
		for( int s = 0; s < 4; s++) {
			for( int r = 0; r < 4; r++) {
				if(matrix[s][r] == SCATTER) {
					win.cnt++;
					win.highlight[s][r] = 1;
				}
			}
		}
			
		if(win.cnt > 0 ) { // free games win
			win.type = Type.SCATTER;
			Gdx.app.debug("Lines", "free games win");	
		}
		else /*if (win.cnt == 0)*/ {
			win = null;
		}
		
		return win;
	}
	
	/**
	 * Check scatter win
	 */
	public Win checkScatterWin(int[][] matrix) {
		Win win = new Win();

		win.type = Type.NEAR_MISS;
		win.mult = 0;
		win.cnt = 0;
		win.symbol = SCATTER;
		win.highlightTimeout = 90;
		
		for( int s = 0; s < 3; s++) {
			for( int r = 0; r < 5; r++) {
				if(matrix[s][r] == SCATTER) {
					win.cnt++;
					win.highlight[s][r] = 1;
				}
			}
		}
		
		if(win.cnt > 2) { // TODO: drop the if statement and get it right
			
//			win.sound = SoundTrack.WIN3;
			
			// traverse scatterPaytable
			for( int ptIndex = scatterPaytable.length - 1; ptIndex >= 0; ptIndex--) {
				if(scatterPaytable[ptIndex][1] == win.cnt) {
					win.mult = scatterPaytable[ptIndex][2];
					win.type = Type.SCATTER;
					break;
				}
			}
			
			Gdx.app.debug("Math", "scatter win : " + win.mult);	
		}
		else if(win.cnt == 0) {
			win = null;
		}
		
		return win;
	}	
	
	public Win checkBonusWin(int[][] matrix) {
		Win win = new Win();
		
//		win.sound = SoundTrack.WIN1;
		win.type = Type.NEAR_MISS;
		win.mult = 0;
		win.cnt = 0;
		win.symbol = BONUS;
		win.highlightTimeout = 15;
		
		for( int s = 0; s < 5; s++) {
			for( int r = 0; r < 5; r++) {
				if(matrix[s][r] == BONUS) {
					win.cnt++;
					win.highlight[s][r] = 1;
				}
			}
		}
		
		if(win.cnt > 0) { // TODO: drop the if statement and get it right
			
//			win.sound = SoundTrack.WIN3;
			
			// traverse scatterPaytable
			for( int ptIndex = bonusPaytable.length - 1; ptIndex >= 0; ptIndex--) {
				if(bonusPaytable[ptIndex][1] == win.cnt) {
					win.mult = scatterPaytable[ptIndex][2];
					win.type = Type.BONUS;
					break;
				}
			}
			
			Gdx.app.debug("Math", "bonus win : " + win.mult);	
		}
		else if(win.cnt == 0) {
			win = null;
		}
		
		return win;
	}	
	

	
	/**
	 * Check scatter win
	 */
	public Win checkWilds(int[][] matrix, List<Win> wins) {
		Win win = new Win();
		
		win.type = Type.WILD;
		win.mult = 0;
		win.cnt = 0;
		win.symbol = WILD;
		win.highlightTimeout = 140;
		win.sound = SoundTrack.SCATTER; 
		
		for( int i = 0; i < wins.size(); i++) {
			if(wins.get(i).hasWild) {
				for (int s = 0; s < 5; s++) {
					for (int r = 0; r < 5; r++) {
						if (wins.get(i).highlight[s][r] == 1 && matrix[s][r] == WILD) {
							win.cnt++;
							win.highlight[s][r] = 1;
						}
					}
				}
			}
		}
		
		if(win.cnt == 0) {
			win = null;
		}
		
		return win;
	}	
	
	/**
	 * Apply stacked symbols
	 */
	public void applyStackedSymbols(int[][] matrix, int[][] stackedMatrix) {
		
		Gdx.app.debug("Math", "applyStackedSymbols()");
		printMatrix(matrix);
		
		for( int s = 0; s < 5; s++) {
			for( int r = 0; r < 5; r++) {
				if(stackedMatrix[s][r] != -1) {
					matrix[s][r] = stackedMatrix[s][r];
				}
			}
		}	
		
		printMatrix(matrix);
	}
	
	/**
	 * Prints out game matrix. Use for debug.
	 * @param matrix
	 */
	public void printMatrix(int[][] matrix) {
//		String string = "";
//		
//		for( int s = 0; s < 3; s++) {
//			string = "";
//			for( int r = 0; r < 5; r++) {
//				string += "[" + matrix[s][r] + "]";
//			}
//			Gdx.app.debug("Lines", string);
//		}
	}
	
	/**
	 * Returns a winning line
	 * @param line
	 * @return
	 */
	public int[] getLine(int line) {
		return lines[line];
	}
	
	public String winningLineToText(Win win, BookOfKnight game) {
		String string = "";
		
		if(win.type == Type.SCATTER) {
				int totalBet = game.meters.getTotalBet();
				int value = win.mult * totalBet;

				string += win.cnt + " X " + symbolsToText[win.symbol];
				string += " : "  + game.meters.formatNumber(value, false);
		} else {
			int value = 0 ;
				value = win.mult * win.betPerLine ;
			
				string = game.gameTxt.line + " " + (win.winningLine + 1) + " : ";
				string += win.cnt + " X " + symbolsToText[win.symbol]; 
				string += " : " + game.meters.formatNumber(value, false);
		}

		return string;
	}
	
}
