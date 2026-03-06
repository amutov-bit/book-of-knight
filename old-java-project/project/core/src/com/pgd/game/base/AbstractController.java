package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pgd.game.BookOfKnight;
import com.pgd.game.states.ControllerListener;

/**
 * BaseController
 * 
 * Extend BaseController to implement individual game features.
 *  
 * @author Dimitar
 *
 */
public abstract class AbstractController extends InputListener {

	public enum Event {NONE, START, TAKEWIN, DOUBLEUP, MENU, AUTO, FGSTART, CARDSEL, ENTER_STATS, PLAY_FREE_GAMES, JACKPOT, ADD_FREE_SPINS, ADD_FREE_SPINS_TAKE_WIN};
	public Event event;
	
	protected BookOfKnight game;
	protected List<ControllerListener> listeners = new ArrayList<ControllerListener>(); 

	public boolean animationStarted  = false;
    public boolean animationFinished = false;
	
	public AbstractController(BookOfKnight game) {
		this.game = game;
		event = Event.NONE;
	}
	
	/**
	 * Add listener
	 * @param listener
	 */
	public void addListener(ControllerListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Overwrite this function 
	 */
	public void update() {
		
	}

}
