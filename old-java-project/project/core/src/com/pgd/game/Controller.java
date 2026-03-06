package com.pgd.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.TimeUtils;
import com.pgd.game.BookOfKnight;
//import com.pgd.game.Context.GameMode;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.base.BaseController;
import com.pgd.game.base.Reel;
import com.pgd.game.base.ReelSymbol;
import com.pgd.game.base.Win;
import com.pgd.game.base.AbstractController.Event;

/**
 * Controller implements FSM to control the game through it's various states.
 * Transitions are emitted to registered listeners.
 * 
 * @author Dimitar
 *
 */
public class Controller extends BaseController {

	private State state;

	private int fpsCounter = 0; 
	
	private static int spins = 0;
	
	private static boolean stateShowAll = false;
	
	protected long blinkStartTime = 0;
	
	protected boolean maxWinHasBeenShown = false;
	
	public Controller(BookOfKnight game) {
		super(game);
		state = State.IDLE;

//		game.messages.clearErrors();
//		game.gsLink.getBalance();
//		game.menu.updateMeters();
	}

	public State getState()
	{
		return state;
	}
	/**
	 * Handle touch inputs
	 */
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {

		// Quick&dirty fix for touch reels/symbol bounds
		// if(y < 165) return true;

		switch (state) {

		// case IDLE:
		// this.event = Event.START;
		// break;

		case REELS_SPINNING:
		case REELS_STOPPING:
//			if (event.getTarget() instanceof ReelSymbol) {
//				ReelSymbol symbol = (ReelSymbol) event.getTarget();
//				symbol.getReel().skillStop();
//				// symbol.getReel().highlight(0);
//				symbol.getReel().unhighlight(1);
//				// Gdx.app.debug("Reels", "Symbol: " + symbol.getCurrentStop());
//			} else if (event.getTarget() instanceof Reel) {
//				Reel reel = (Reel) event.getTarget();
//				reel.skillStop();
//				// reel.highlight(0);
//				reel.unhighlight(1);
//			}
			break;
		default:
			// this.event = Event.START;
			break;
		}

		// Gdx.app.debug("InputEvent", "target: " + event.getTarget());

		return true;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		// Gdx.app.debug("Reels", "Released");
	}

	/**
	 * Keyboard events
	 */
	@Override
	public boolean keyDown(InputEvent event, int keycode) {

		boolean press = false;
		
		if(buttonCounter > 20)
		{
			buttonCounter = 0;
			switch (keycode) {
	
			case 62: /* SPACE */
//				if(game.menu.startButton.isEnable())
				{
					this.event = Event.START;
					game.menu.startButton.activate();
					
					press = true;
				}
				break;
	
			case 29: /* A */
//				if(game.menu.autoButton.isEnable())
//				{
//					if (game.context.autoplay) {
//						game.context.autoplay = false;
//						game.menu.autoButton.reset();
//						game.menu.setLeftStatus("");
//					} else {	
//						game.context.autoplay = true;
//						game.menu.autoButton.toggled();
////						game.context.autoplayCounter = game.context.autoplayLimit;
//						game.controller.event = Event.START;
//					}
//					press = true;
//				}
				break;
	
			case 41: /* M */
//				if(game.menu.betButton.isEnable())
//				{
//					game.meters.selectMaxbet();
//					game.menu.betButton.activate();
////					game.menu.updateMeters();
//					game.controller.event = Event.START;
//					press = true;
//				}
				break;
	
			case 47: /* S */
				game.controller.event = Event.ENTER_STATS;
				break;
	
				case Input.Keys.H: /* H */
					if(game.menu.paytableButton.isEnable())
					{
						game.menu.paytableButton.activate();
						press = true;
					}
					break;				
					
			default:
				break;
			}
		}
		if(press) Gdx.app.debug("Controller", "keyDown: " + keycode);

		return press;
	}

	/**
	 * Set next state
	 * 
	 * @param nextState
	 */
	public void setNextState(State nextState) {

		Gdx.app.debug("Controller", "state: " + state + " > " + nextState
				+ ", timerCounter: " + timerCounter);

		game.gsLink.console("state: " + state + " > " + nextState
				+ ", timerCounter: " + timerCounter);

		state.exit(this);
		state = nextState;
		state.entry(this);

		timerCounter = 0;
		idleTimerCounter = 0;

		// Notify listeners
		for (int i = 0; i < listeners.size(); i++) {
			switch (nextState) {
			case START_SPIN:
				listeners.get(i).onStartSpin();
				break;
			case SHOW_WINS:
				listeners.get(i).onShowWins();
				break;
			case TAKE_WINS:
				listeners.get(i).onTakeWin();
				break;
			case WIN_TO_CREDIT:
				listeners.get(i).onWinToCredit();
				break;
			// case FREE_GAMES_WIN: listeners.get(i).onFreeGamesWin(); break;
			case FREE_GAMES_TAKE_WINS:
				listeners.get(i).onFreeGamesTakeWins();
				break;
			case REELS_STOPPED:
				listeners.get(i).onReelsStop();
				break;
			case SPIN_END:
				listeners.get(i).onSpinEnd();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * States
	 */
	public enum State {
		IDLE() {
			void entry(Controller controller) { // autoplay
				
				controller.game.menu.setLeftStatus("");
				controller.game.menu.setWinStatus("");
				controller.game.menu.setStatus("");
				controller.game.meters.win = 0;
				controller.game.menu.setWin(0);
				
				controller.game.overlay.hideJackpotSymbols();
				controller.game.overlay.jackpotDigitsSymbols.resetJackpotDigits();
				
				if(!controller.game.context.autoplay){
					controller.game.menu.resetMenuButtons();
				}

				if(controller.game.context.autoplayCurrentLost >= controller.game.context.autoplayMaxLost && !controller.game.context.autoplayLostUnlimited){
					controller.game.context.autoplay = false;
					if(controller.game.context.hasAddFreeSpins){
						controller.game.menu.autoButton.reset();
					}
					if(controller.game.meters.credit < controller.game.meters.getTotalBet())	controller.game.menu.autoButton.disable();
					controller.game.menu.setLeftStatus("");
				}
				
				controller.game.gsLink.console("<<1");

				Gdx.app.debug("Controller ", "controller.game.context.autoplayCounter = " + controller.game.context.autoplayCounter);
				Gdx.app.debug("Controller ", "controller.game.context.autoplay = " + controller.game.context.autoplay);
				
				if (controller.game.context.autoplay) {
					if (controller.game.context.autoplayCounter > 0 && controller.game.meters.credit >= controller.game.meters.getTotalBet()) {
						controller.event = Event.START;
					} else {
						controller.game.context.autoplay = false;
						if(controller.game.context.hasAddFreeSpins){
							controller.game.menu.autoButton.reset();
						}
						if(controller.game.meters.credit < controller.game.meters.getTotalBet())	controller.game.menu.autoButton.disable();
						controller.game.menu.setLeftStatus("");
						
						controller.game.menu.resetMenuButtons();
						
						controller.game.menu.autoButton.setVisible(true);
						controller.game.menu.autoplayStopButton.setVisible(false);
						
						controller.game.menu.autoplayStopButton.setVisible(false);
						controller.game.menu.startButton.setVisible(true);
						controller.game.menu.startButton.reset();
					}
				} else {
					
					controller.game.menu.resetMenuButtons();
					
					controller.game.menu.autoButton.setVisible(true);
					controller.game.menu.autoplayStopButton.setVisible(false);
					
					controller.game.menu.autoplayStopButton.setVisible(false);
					controller.game.menu.startButton.setVisible(true);
				}
				
				controller.game.gsLink.console("<<2");

//				controller.game.reels.showFrameWin(false);
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
//				controller.game.meters.fgwin = 0; // reset free games win
//				controller.game.context.freeGamesCounter = 0;
//				controller.game.context.freeGamesWon = 0;
				
				controller.game.mainScreen.setZorder(true);
				
//				controller.game.overlay.hideBonus();
				
			}

			void process(Controller controller) {
				
				String currentTxt = "";
				
				if(!(controller.game.overlay.menuTurboSpins.isVisible())){
					if(!controller.game.context.autoplay){
						if(controller.game.context.turboGame || !controller.game.overlay.menuTurboSpinsIsEnalbled()){
							currentTxt = controller.game.gameTxt.placeBets;
						} else {
							currentTxt = controller.game.gameTxt.holdSpin;
						}
					}
				}
				
				long blinkInterval = 2000; // Time in ms for each blink step

                if (controller.blinkStartTime == 0) {
                    controller.blinkStartTime = System.currentTimeMillis();
                }

                long elapsed = System.currentTimeMillis() - controller.blinkStartTime;

                if ((elapsed / blinkInterval) % 2 == 0) {
                    controller.game.menu.setStatus(currentTxt);
                } else {
                    controller.game.menu.setStatus(controller.game.gameTxt.startWinUp);
                }
				
				if (controller.event == Event.START) {
					controller.event = Event.NONE;
					controller.setNextState(START_SPIN);
					controller.game.reels.playLogo(true);
					
				}

				if (controller.event == Event.ADD_FREE_SPINS) {
					controller.event = Event.NONE;
					controller.setNextState(ADD_FREE_GAMES);
					controller.game.reels.playLogo(true);
					
				}

				if (controller.idleTimerCounter < 1000)
					controller.idleTimerCounter++;

				if (controller.idleTimerCounter == 400) {
//					controller.game.sounds.stopBackground();
				}

			}
			
			void exit(Controller controller){
//				controller.game.background.setFreeGames(true);

				if(controller.game.context.autoplay){
					controller.game.context.autoplayCounter--;
				}

			}
		},
		RESTORE_STATE() {
			void entry(Controller controller) {
				controller.game.reels.updateStopSymbols();
				controller.game.reels.updateReelSymbols();
				controller.restoreGSOutcome();
				controller.currentTime = System.currentTimeMillis();
				controller.game.reels.resetJackpotDigitsSymbols();
				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.setVisible(true);
				}
				
			}
			
			void process(Controller controller) {
				if((controller.game.context.maxWinReached && controller.game.meters.win >= controller.game.context.maxWin) && !controller.maxWinHasBeenShown){
					controller.game.gsLink.console("<<9");
					controller.setNextState(SHOW_MAX_WIN_REACHED_RESTORE);
//					controller.setNextState(IDLE);
					controller.game.gsLink.console("<<11");
				} else {
					
					if(controller.game.context.gameMode == controller.game.context.HOLD_AND_WIN_GAMES || controller.game.context.outcome.hasHoldAndWin){
						
						controller.game.menu.paytableButton.disable();
						controller.game.menu.betButton.disable();
						controller.game.menu.autoButton.disable();
						controller.game.menu.buyBonusButton.setVisible(false);
						controller.game.reels.restoreOldMatrixHoldAndWin();
						
						//hack 
						controller.game.context.gameMode = controller.game.context.HOLD_AND_WIN_GAMES;
						
						controller.game.overlay.setHoldAndWinTitleLabel(controller.game.context.holdAndWinCounter);

						if(controller.game.context.outcome.hasHoldAndWin){
							controller.game.gsLink.console("<<7");
							controller.setNextState(State.HOLD_AND_WIN);
							controller.game.menu.paytableButton.disable();
							controller.game.menu.betButton.disable();
							controller.game.menu.autoButton.disable();
							controller.game.menu.buyBonusButton.disable();
						} else if(controller.game.context.holdAndWinCounter == 0){
							controller.setNextState(State.SHOW_RESTORE_HOLD_AND_WIN_END);
						} else {
							if((System.currentTimeMillis() - controller.currentTime > 1300)){
								controller.setNextState(State.HOLD_AND_WIN_START_SPIN);
							}
						}
						
					} else if(controller.game.context.gameMode == controller.game.context.FREE_GAMES || controller.game.context.outcome.hasFreeGames){
						
						controller.game.menu.paytableButton.disable();
						controller.game.menu.betButton.disable();
						controller.game.menu.autoButton.disable();
						controller.game.menu.buyBonusButton.setVisible(false);
						
						controller.game.overlay.addFreeGame.setFreeGamesWin(controller.game.context.addFreeSpinsWin - controller.game.meters.fgwin);
						controller.game.overlay.setFreeGamesTitleLabelFirst(controller.game.context.visibleFreeGamesCounter);
						
						if(controller.game.context.outcome.hasFreeGames){
							controller.game.gsLink.console("<<7");
							controller.setNextState(State.FREE_GAMES);
							controller.game.menu.paytableButton.disable();
							controller.game.menu.betButton.disable();
							controller.game.menu.autoButton.disable();
							controller.game.menu.buyBonusButton.disable();
						} else if(controller.game.context.freeGamesCounter == 0){
							controller.setNextState(State.SHOW_RESTORE_FG_END);
							controller.game.overlay.addFreeGame.setFreeGamesWin(controller.game.context.addFreeSpinsWin);
						} else {
							if((System.currentTimeMillis() - controller.currentTime > 1300)){
								if(controller.game.context.outcome.hasWin){
									controller.setNextState(State.SHOW_FG_LAST_WINS);
								} else {
									controller.setNextState(State.FREE_GAMES_START_SPIN);
								}
							}
						}
						
					} else {
						controller.setNextState(State.SHOW_LAST_WINS);
						controller.game.gsLink.console("<<6");
					}
				}
			}
			
			void exit(Controller controller) {
			}
		},
		START_SPIN() {
			void entry(Controller controller) {
				controller.game.reels.unhighligthAll();
				
				
				controller.game.context.prevMode = 0;
				
				controller.maxWinHasBeenShown = false;
				
				boolean startSpin = false;

				if(controller.game.context.hasAddFreeSpins || controller.game.context.claimAddFreeSpins){
					startSpin = controller.startAddFreeSpins();
				} else {
					startSpin = controller.startSpin();
				}
				
				if (startSpin) {
//				if (controller.startSpin()) {
					
					controller.game.menu.paytableButton.disable();
					controller.game.menu.betButton.disable();
					controller.game.menu.autoButton.disable();
					controller.game.menu.buyBonusButton.disable();
					
					controller.game.menu.setWin(0);
					
					controller.game.menu.setWinStatus("");
					controller.game.menu.setStatus(controller.game.gameTxt.goodLuck);
					
					
					controller.game.context.autoplayCurrentLost += controller.game.meters.getTotalBet();
					
					controller.setNextState(REELS_SPINNING);
					
					controller.game.menu.paytableButton.disable();
					controller.game.menu.betButton.disable();

					if(controller.game.context.autoplay)	controller.game.menu.autoButton.toggled();
					else									controller.game.menu.autoButton.disable();
										
				} else {
					controller.setNextState(INSERT_CREDITS);

				}
			}
		},
		
		SHOW_MAX_WIN_REACHED(){
			void entry(Controller controller) {
				controller.game.reels.showFrame(true);
				controller.game.menu.setStatus("MAX WIN REACHED");
				controller.game.overlay.showMaxWinReached();
				controller.game.menu.disableButtons();
				controller.currentTime = System.currentTimeMillis();
				
				controller.game.menu.autoButton.disable();
				controller.game.menu.paytableButton.disable();
				controller.game.menu.betButton.disable();
				controller.game.menu.buyBonusButton.disable();

				controller.game.reels.unhighligthAll();
				
				controller.game.context.onscreenWinMeter = controller.game.context.maxWin;
				controller.game.meters.win = controller.game.context.maxWin;
				controller.game.menu.setWin(controller.game.context.maxWin);
				
				controller.maxWinHasBeenShown = true;
				
				controller.game.meters.fgwin =  controller.game.context.maxWin;
				controller.game.meters.holdAndWin =  controller.game.context.maxWin;
						
				controller.event = Event.NONE;
				
				controller.game.menu.setWin(0);
				controller.game.menu.setWinStatus("");
				controller.game.overlay.hideWin();
			}
			
			void process(Controller controller) {
				
				if((System.currentTimeMillis() - controller.currentTime > 2000)){
					spins++;
					if(spins < 25){
						controller.game.menu.setStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setStatus("");
					} else {
						spins = 0;
					}
					switch (controller.event) {
						case START:
							controller.event = Event.NONE;
							if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
								controller.setNextState(State.HOLD_AND_WIN_END);
								controller.game.overlay.setHoldAndWinTitleLabel(0);
							} if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
								controller.setNextState(State.FREE_GAMES_END);
								controller.game.overlay.setFreeGamesTitleLabelFirst(0);
							} else {
								controller.setNextState(State.IDLE);
							}
						break;
						default:
						break;
				   }
								
				} else {
					controller.event = Event.NONE;
				}
			}
			
			void exit(Controller controller) {
				controller.game.overlay.hideMaxWinReached();
				controller.game.menu.setStatus("");
				controller.game.menu.startButton.reset();
				
			}
		},
		SHOW_MAX_WIN_REACHED_RESTORE(){
			void entry(Controller controller) {
				controller.game.reels.showFrame(true);
				controller.game.menu.setStatus("MAX WIN REACHED");
				controller.game.overlay.showMaxWinReached();
				controller.game.menu.disableButtons();
				controller.currentTime = System.currentTimeMillis();
				
				controller.game.menu.autoButton.disable();
				controller.game.menu.paytableButton.disable();
				controller.game.menu.betButton.disable();
				controller.game.menu.buyBonusButton.disable();
				
				controller.game.reels.unhighligthAll();
				
				controller.game.context.onscreenWinMeter = controller.game.context.maxWin;
				controller.game.meters.win = controller.game.context.maxWin;
				controller.game.menu.setWin(controller.game.context.maxWin);
				
				controller.maxWinHasBeenShown = true;
				
				controller.event = Event.NONE;
			}
			
			void process(Controller controller) {
				if((System.currentTimeMillis() - controller.currentTime > 5000)){
						controller.setNextState(State.IDLE);
				}
			}
			
			void exit(Controller controller) {
				controller.game.overlay.hideMaxWinReached();
				controller.game.menu.setStatus("");
				controller.game.menu.startButton.reset();
				
			}
		},
		INSERT_CREDITS{
			void entry(Controller controller) {
//				controller.game.reels.showFrame(true);
				controller.game.menu.setStatus(controller.game.gameTxt.insertCredits);
				controller.game.overlay.showInsertCredit();
				controller.game.menu.disableButtons();
				controller.currentTime = System.currentTimeMillis();
				controller.game.context.autoplay = false;
			}

			void process(Controller controller) {
				if((System.currentTimeMillis() - controller.currentTime > 3000) || controller.game.overlay.hideIsPressed()){
					controller.setNextState(IDLE);
				}
			}

			void exit(Controller controller) {
				controller.game.overlay.hideInsertCredit();
				controller.game.menu.setStatus("");
				controller.game.menu.startButton.reset();
			}
		},
		REELS_SPINNING() {
			void entry(Controller controller) {
				// ..
				
				controller.game.reels.resetReelSpeed();
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
				
				controller.game.overlay.hideJackpotDigitsSymbols();
				
				Gdx.app.debug("Controller ", "controller.game.context.autoplayCounter = " + controller.game.context.autoplayCounter);
				if(controller.game.context.autoplay) {
					controller.game.menu.autoplayStopButton.setText("" + (controller.game.context.autoplayCounter), controller.game.context.autoplayCounter);;	
				}
			}

			void process(Controller controller) {

				if (!controller.game.gsLink.spinEnded())
					return;

				if (controller.timerCounter > SPIN_TIMEOUT)
						if(controller.game.context.turboGame){
							controller.event = Event.NONE;
							controller.setNextState(REELS_STOPPED);
						} else {
						controller.setNextState(REELS_STOPPING);
						}
				if (controller.event == Event.START) {
						controller.event = Event.NONE;
						controller.setNextState(REELS_STOPPED);
				}
			}

			void exit(Controller controller) {
				
				if(!controller.game.context.turboGame){
					controller.game.menu.stopButton.setVisible(true);
					controller.game.menu.startButton.setVisible(false);
				}
				// controller.getGameServerOutcome();
			}
		},
		REELS_SPINNING_HOLD_AND_WIN() {
			void entry(Controller controller) {
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
				controller.game.menu.startButton.disable();
				controller.game.overlay.hideJackpotDigitsSymbols();
				controller.game.reels.resetReelSpeed();
				controller.timerCounter = 0;
			}
			
			void process(Controller controller) {
				
				if (!controller.game.gsLink.spinEnded())
					return;
				
				if (controller.timerCounter > SPIN_TIMEOUT * 3){
						controller.event = Event.NONE;
						controller.setNextState(REELS_STOPPING);
				}
			}
			
			void exit(Controller controller) {
				controller.game.menu.stopButton.setVisible(true);
				controller.game.menu.startButton.setVisible(false);
			}
		},
		REELS_HIGHLIGHT_STOPPING() {
		    void entry(Controller controller) {
		        controller.animationStarted = false;
		        controller.animationFinished = false;
//		        controller.event = Event.NONE;
		    }

		    void process(Controller controller) {
		        int current = controller.reelCounter;
		        int prev = current - 1;

				if(controller.event == Event.START) {
					controller.event = Event.NONE;
					controller.setNextState(REELS_STOPPED);
					return;
				}
				
		        // Prevent index out of bounds
		        if (current >= controller.game.REELS) {
		        	
					if(controller.reelsStopped()) {
						controller.setNextState(REELS_STOPPED);
					}
					return;
		        }

		        // Step 1: Wait for previous reel to stop
		        if (prev >= 0 && !controller.game.reels.reelStopped(prev)) return;

		        // Step 2: Start animation if not started
		        if (!controller.animationStarted) {
		            controller.animationStarted = true;
		            controller.game.reels.highlightReel(current, 0);
		        }

		        // Step 3: Wait for highlight animation to complete
		        if (controller.animationStarted && !controller.animationFinished) {
		            if (controller.game.reels.highlightReelIsStopped(current)) {
		                controller.animationFinished = true;
		            }
		        }

		        // Step 4: Stop the reel after animation is finished
		        if (controller.animationFinished && !controller.game.reels.reelStopped(current)) {
		            controller.game.reels.stopReel(current);
		        }

		        // Step 5: Move to next reel if current is fully stopped
		        if (controller.animationFinished && controller.game.reels.reelStopped(current)) {
		            controller.reelCounter++;
		            if (controller.reelsStopped()) {
		            	controller.setNextState(REELS_STOPPED); // replace with actual next state
		            } else {
		            	controller.setNextState(REELS_HIGHLIGHT_STOPPING); // loop for next reel
		            }
		        }
		        
				if(controller.reelsStopped()) {
					controller.setNextState(REELS_STOPPED);
				}
		    }

		    void exit(Controller controller) {
		    	controller.game.reels.resetReelSpeed();
		    }
		},
		
		REELS_STOPPING() {
			void entry(Controller controller) {
				controller.reelCounter = 0;
			}

			void process(Controller controller) {
				
				// Handle input
				
				if(!controller.game.context.hasReelHighlight){
					if(controller.event == Event.START) {
						controller.event = Event.NONE;
						// inhibit reels stop if highlighting 
						/*
						 * <cod mode bb>
						 */
						controller.setNextState(REELS_STOPPED);
					}
				}
				
				// Stop one at a time
				
				int time = (controller.game.context.gameMode == controller.game.context.FREE_GAMES) ?
						controller.game.context.outcome.stopNextReelTimeoutFreeGames[controller.reelCounter] :
							controller.game.context.outcome.stopNextReelTimeoutNormal[controller.reelCounter] ;
				
					if(controller.game.context.gameMode == controller.game.context.HOLD_AND_WIN_GAMES){
						time = controller.game.context.outcome.stopNextReelTimeoutHoldAndWin[controller.reelCounter];
					}
				
				if (controller.timerCounter > time) {

					controller.timerCounter = 0;
					controller.game.reels.stopReel(controller.reelCounter);

					if (controller.reelCounter < controller.game.reels.NUMBER_OF_REELS - 1) {
						controller.reelCounter++;
					}
				}
				
				if(controller.reelCounter == controller.game.context.firstReelHighlight && controller.game.context.hasReelHighlight){
					controller.setNextState(REELS_HIGHLIGHT_STOPPING);
				}

				// Wait for all reels to stop and go to next state
				if(controller.reelsStopped()) {
					controller.setNextState(REELS_STOPPED);
				}
			}

			void exit(Controller controller) {
			}
		},
		REELS_STOPPED() {
			void entry(Controller controller) {
				controller.event = Event.NONE;
				controller.game.reels.stopAllReels(); // make sure reels stopped
				controller.timerCounter = 0;
			}

			void process(Controller controller) {

				if (controller.game.reels.allStoped()) {
					controller.setNextState(REELS_STOPPED_WAIT);
				}
			}

			void exit(Controller controller) {
				controller.game.reels.unhighligthAll();
				
				if(!controller.game.context.turboGame || controller.game.context.gameMode == controller.game.context.HOLD_AND_WIN_GAMES){
					controller.game.menu.stopButton.setVisible(false);
					controller.game.menu.startButton.setVisible(true);
				}
								
			}
		},
		REELS_STOPPED_WAIT() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				controller.game.reels.stopAllReels(); // make sure reels stopped
				controller.timerCounter = 0;
				controller.currentTime = System.currentTimeMillis();
			}

			void process(Controller controller) {

					if (controller.game.reels.allStoped()) {
							if (controller.game.context.outcome.hasWin){
								if(controller.game.context.turboGame){
									controller.setNextState(State.SHOW_ALL_WINNING_LINES);
								} else {
									controller.setNextState(SHOW_WINS);
								}
							}
							else{
								controller.setNextState(SPIN_END);
							}
					}
			}

			void exit(Controller controller) {
				
				controller.game.reels.unhighligthAll();
				
				if(controller.game.context.turboGame && controller.game.context.autoplay){
					controller.game.menu.startButton.disable();
				} else{
					controller.game.menu.startButton.reset();
				}
//				controller.game.sounds.stop(SoundTrack.HIGHLIGHTS_LOOP);
			}
		},
		ANIMATE_SPECIAL_SYMBOL(){
			void entry(Controller controller) {
				
				controller.game.overlay.hideWin();
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(true);
				controller.game.reels.clearSpecialReel();
				controller.reelCounter = controller.game.reels.setSpecialReels(controller.game.context.specialSymbol);
				controller.game.changeLine = true;
				controller.game.menu.setWinStatus("");
				
			}
			
			void process(Controller controller) {
				
				if (controller.game.changeLine) {
					controller.timerCounter = 0;
					controller.game.changeLine = false;
					if (controller.reelCounter < controller.game.reels.NUMBER_OF_REELS){

						if(controller.game.reels.isSpecialReel(controller.reelCounter)){
							controller.game.reels.showSpecialSymbolAnim(controller.reelCounter, controller.game.context.specialSymbol);
						} else {
							controller.game.changeLine = true;
						}
						
						controller.reelCounter++;
					} else {
						controller.setNextState(SHOW_SPECIAL_WINS);
					}
				}
			}
			
			void exit(Controller controller) {
//				controller.game.reels.clearSpecialReel();
				
//				controller.game.reels.animateOnStopSpecial(controller.symbolsCounter);
				
			}
			
		},
		
		BONUS_WAIT() {
			void entry(Controller controller) {
				controller.event = Event.NONE;
				controller.currentTime = System.currentTimeMillis();

				
				controller.game.reels.setFreeGames(false);
				controller.game.overlay.showFreeGamesTitle(false);
			}
			
			void process(Controller controller) {
				
				if(System.currentTimeMillis() - controller.currentTime > 2000)
				{					
						controller.setNextState(BONUS_END);
				}
			}
			
			void exit(Controller controller) {
				controller.game.reels.setMysterySymbols(true);
//				controller.game.reels.hideMysterySymbolsImage();
				controller.game.reels.setStackedWilds();
				controller.game.overlay.hideBonus();
			}
		},
		BONUS_END() {
		},
		
		
		HOLD_AND_WIN() {
			void entry(Controller controller) {
				
				controller.game.background.setHoldAndWinAnim(true);
				controller.game.reels.setHoldAndWin(true);
//				controller.game.reels.hideSymbols(controller.game.math.JACKPOT_SYMBOL);
				
				controller.game.reels.unhighligthAll();
				
				controller.game.overlay.showJackpotSymbols();
				
				controller.game.overlay.setHoldAndWinTitleLabel(controller.game.context.holdAndWinGamesWon);
				
				controller.game.overlay.showHoldAndWinsText(controller.game.context.holdAndWinGamesWon);
				
				controller.game.menu.setLeftStatus("");
				controller.game.menu.setWinStatus("");
				controller.game.menu.setWin(0);
				controller.game.menu.setStatus(controller.game.gameTxt.holdAndWinTxt);
				
//				controller.game.context.autoplay = false;				
				controller.currentTime = System.currentTimeMillis();
				
				controller.game.menu.buyBonusButton.setVisible(false);
				
				controller.game.menu.startButton.disable();
				
				controller.game.overlay.hideWin();
				
				controller.event = Event.NONE;
				
//				if(controller.game.DEMO_MODE && Gdx.app.getType() == ApplicationType.Desktop){
//				}
				
				//hack for drawing
				controller.game.context.gameMode = controller.game.context.HOLD_AND_WIN_GAMES;
				
				controller.currentTime = System.currentTimeMillis();
				
				controller.event = Event.NONE;
				
				spins = 0;
			}
			
			void process(Controller controller) {
				if((System.currentTimeMillis() - controller.currentTime > 18600) || controller.game.overlay.holdAndWinText.animationStopped())
				{
					spins++;
					if(spins < 25){
						controller.game.menu.setStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setStatus("");
					} else {
						spins = 0;
					}
					switch (controller.event) {
						case START:
							controller.setNextState(HOLD_AND_WIN_START_SPIN);
						break;
						default:
						break;
				   }
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.setNextState(HOLD_AND_WIN_START_SPIN);
					}
				}
			}
			
			void exit(Controller controller) {
//				controller.game.menu.startButton.reset();
				controller.game.reels.unhighligthAll();
				controller.game.menu.setStatus("");
				controller.game.overlay.hideHoldAndWinText();
				controller.game.reels.showFrameWin(false);
			}
		},
		HOLD_AND_WIN_START_SPIN() {
			void entry(Controller controller) {
				controller.game.menu.startButton.reset();
				controller.event = Event.NONE;
				
				if(controller.game.context.outcome.hasHoldAndWin){
					controller.game.overlay.setHoldAndWinTitleLabel(controller.game.context.holdAndWinGamesWon);
				}
				
				controller.game.reels.resetEggs();
				
				spins = 0;
			}
			
			void process(Controller controller) {

				
				spins++;
				if(spins < 25){
					controller.game.menu.setStatus(controller.game.gameTxt.pressStart);
				} else if(spins < 25 * 2){
					controller.game.menu.setStatus("");
				} else {
					spins = 0;
				}
				
				if(controller.game.context.autoplay){
					controller.event = Event.START;
				}
				
				switch (controller.event) {
					case START:
						if (controller.game.context.holdAndWinCounter > 0) {
							
							controller.game.menu.setStatus((controller.game.context.holdAndWinCounter - 1 ) + " " + controller.game.gameTxt.repinsLeft);
							
							controller.startHoldAndWinSpin();
							
							controller.game.overlay.showJackpotSymbols();
							
							controller.setNextState(REELS_SPINNING_HOLD_AND_WIN);
							
						} else {
							controller.setNextState(HOLD_AND_WIN_SHOW_WINS);
						}
					break;
					default:
					break;
				}
				
			}
			
			void exit(Controller controller) {
				controller.event = Event.NONE;
			}
		},
		HOLD_AND_WIN_SHOW_WINS(){
			void entry(Controller controller) {
				controller.event = Event.NONE;
				controller.game.overlay.showHoldAndWinWins();
				controller.game.menu.startButton.disable();
			}
			
			void process(Controller controller) {
				if(controller.game.overlay.jackpotAnimateWin.hasAnimationStopped()){
					controller.setNextState(HOLD_AND_WIN_SHOW_WINS_WAIT);
				}
			}
			
			void exit(Controller controller) {
			}
		},
		HOLD_AND_WIN_SHOW_WINS_WAIT(){
			void entry(Controller controller) {
				controller.event = Event.NONE;
				controller.game.menu.startButton.disable();
				controller.currentTime = System.currentTimeMillis();
			}
			
			void process(Controller controller) {
				if(System.currentTimeMillis() - controller.currentTime > 900){
					controller.setNextState(HOLD_AND_WIN_END);
				}
			}
			
			void exit(Controller controller) {
			}
		},
		HOLD_AND_WIN_END() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				
				controller.currentTime = System.currentTimeMillis(); 
				
				if (controller.game.meters.holdAndWin > 0) {
					controller.game.menu.setWin(controller.game.meters.holdAndWin);
					controller.game.menu.setWinStatus("");
					controller.game.overlay.showHoldAndWinCongrats();
					controller.game.reels.dimAllSymbols(-1);
					controller.game.menu.startButton.disable();
				} else {
					controller.game.menu.setStatus("");
					controller.setNextState(SPIN_END);
					controller.game.context.gameMode = controller.game.context.MAIN_GAME;
				}
				spins = 0;
			}

			void process(Controller controller) {
				if(controller.game.overlay.freegamesCongrats.animationStopped()) {
					spins++;
					if(spins < 25){
						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setWinStatus("");
					} else {
						spins = 0;
					}
					
					switch (controller.event) {
						case START:
							controller.event = Event.NONE;
							controller.setNextState(HOLD_AND_WIN_TAKE_WINS);
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
						break;
						default:
						break;
					}
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.event = Event.NONE;
						controller.setNextState(HOLD_AND_WIN_TAKE_WINS);
						controller.game.context.onscreenWinMeter = 0;
						controller.game.meters.win = 0;
					}
				}
			}

			void exit(Controller controller) {
				controller.game.menu.setLeftStatus("");
				controller.game.overlay.showHoldAndWinTitle(false);
				controller.game.reels.setHoldAndWin(false);
				controller.game.overlay.hideFreeGamesWin();
				controller.game.reels.unhighligthAll();
				controller.game.overlay.hideHoldAndWinCongrats();
				
				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.setVisible(true);
					controller.game.menu.buyBonusButton.disable();
				}
			}
		},
		SHOW_RESTORE_HOLD_AND_WIN_END() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				
				controller.game.overlay.deactJackpotValues();
				if (controller.game.meters.holdAndWin > 0) {
					controller.game.menu.setWin(controller.game.meters.holdAndWin);
					controller.game.menu.setWinStatus("");
					controller.game.overlay.showHoldAndWinCongrats();
					controller.game.reels.dimAllSymbols(-1);
					controller.game.menu.startButton.disable();
				} else {
					controller.game.menu.setStatus("");
					controller.setNextState(IDLE);
					controller.game.context.gameMode = controller.game.context.MAIN_GAME;
				}
				spins = 0;
			}

			void process(Controller controller) {
				if(controller.game.overlay.freegamesCongrats.animationStopped()) {
					spins++;
					if(spins < 25){
						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setWinStatus("");
					} else {
						spins = 0;
					}
					
					switch (controller.event) {
						case START:
							controller.event = Event.NONE;
							controller.setNextState(State.IDLE);
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
							controller.game.meters.fgwin = 0;
							controller.game.meters.holdAndWin = 0;
						break;
						default:
						break;
					}
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.event = Event.NONE;
						controller.setNextState(IDLE);
						controller.game.context.onscreenWinMeter = 0;
						controller.game.meters.win = 0;
						controller.game.context.onscreenWinMeter = 0;
						controller.game.meters.win = 0;
						controller.game.meters.fgwin = 0;
						controller.game.meters.holdAndWin = 0;
					}
				}
			}

			void exit(Controller controller) {
				
				controller.game.context.gameMode = controller.game.context.MAIN_GAME;
//				
				controller.game.menu.setLeftStatus("");
				controller.game.background.setHoldAndWinAnim(false);
				controller.game.reels.resetOldMatrixHoldAndWin();
				controller.game.overlay.showHoldAndWinTitle(false);
				controller.game.reels.setHoldAndWin(false);
				controller.game.overlay.hideFreeGamesWin();
//				controller.game.reels.unhighligthAll();
				controller.game.overlay.hideHoldAndWinCongrats();
				controller.game.menu.startButton.reset();
//				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.setVisible(true);
					controller.game.menu.buyBonusButton.disable();
				}
			}
		},
		HOLD_AND_WIN_TAKE_WINS() {
			void entry(Controller controller) {
				
				controller.game.context.gameMode = controller.game.context.MAIN_GAME;
				
				controller.game.reels.resetEggs();
				
				controller.game.reels.resetOldMatrixHoldAndWin();
//				controller.game.reels.restoreOldMatrix();
				
				controller.game.reels.unhighligthAll();
				
				controller.event = Event.NONE;
				
				controller.game.menu.startButton.disable();
				
				controller.game.menu.setStatus("");
				
				controller.game.context.onscreenWinMeter = 0;
				
				controller.game.menu.setWin(controller.game.context.onscreenWinMeter);

				controller.game.gsLink.console("controller.game.meters.credit = " + controller.game.meters.credit);
				controller.game.gsLink.console("controller.game.meters.holdAndWin = " + controller.game.meters.holdAndWin);
				
				controller.game.context.onscreenCreditMeter = (int) (controller.game.meters.credit - controller.game.meters.holdAndWin);
				
				if(controller.game.meters.holdAndWin > 0){
//					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true, 0.8f);
					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true);
				}
				
				controller.w2cSpeed = 1;
				
			}

			void process(Controller controller) {
				
				controller.w2cSpeed = controller.game.meters.holdAndWin / 20;

				
				if(controller.w2cSpeed < 1)	controller.w2cSpeed = 1;
				
				controller.game.gsLink.console("controller.game.context.onscreenWinMeter = " + controller.game.context.onscreenWinMeter
											 + " :: controller.game.meters.holdAndWin = " + controller.game.meters.holdAndWin);
				
				if (controller.game.context.onscreenWinMeter < controller.game.meters.holdAndWin) {

					int delta = controller.w2cSpeed;

					controller.game.context.onscreenWinMeter += delta;
					controller.game.context.onscreenCreditMeter += delta;
//							* controller.game.meters.getDenomination();
					int currentWin = (controller.game.meters.holdAndWin - controller.game.context.onscreenWinMeter > 0) ? controller.game.meters.holdAndWin - controller.game.context.onscreenWinMeter : 0;
					controller.game.menu.setWin(currentWin);
					controller.game.menu.setCredit(controller.game.context.onscreenCreditMeter);

				} else {
					
					controller.game.menu.setWin(0);
					controller.game.menu.setCredit((int) controller.game.meters.credit);					
					
					{
						if(controller.game.meters.holdAndWin > 0){
							controller.game.sounds.stop(Sounds.SoundTrack.COINUP);
//							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false, 0.8f);
							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false);
						}
						
						if(controller.game.context.hasAddFreeSpins && controller.game.context.addFreeSpinsCnt == 0){
							controller.setNextState(ADD_FREE_GAMES_END);
						} else {
						
							if (controller.game.context.autoplay) {
								controller.setNextState(State.START_SPIN);
							} else {
								controller.setNextState(IDLE);
							}
						}
					}
				}
				
			}

			void exit(Controller controller) {
				controller.event = Event.NONE;
				
				if(controller.game.context.hasAddFreeSpins){
					controller.game.overlay.addFreeGame.setFreeGamesWin(controller.game.context.addFreeSpinsWin);
				}
				
				controller.game.background.setHoldAndWinAnim(false);
				
				controller.game.menu.startButton.reset();
				controller.game.reels.showFrame(false);
				controller.game.menu.setWin(0);
				controller.game.menu.setCredit((int) controller.game.meters.credit);
			}
		},
		FREE_GAMES() {
			void entry(Controller controller) {
				
				controller.game.background.setFreeGamesAnim(true);
				controller.game.reels.setFreeGames(true);
				controller.game.reels.unhighligthAll();
				
//				if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
//					controller.game.overlay.setFreeGamesTitleLabelFirst(10 + controller.game.context.freeGamesCounter);
//				} else {
//				}
				controller.game.overlay.setFreeGamesTitleLabelFirst(controller.game.context.freeGamesCounter);
				
				controller.game.overlay.showFreeGamesText(10);
				
				controller.game.menu.setLeftStatus("");
				controller.game.menu.setStatus("");
//				controller.game.menu.setWinStatus("");
				controller.game.menu.setWinStatus(controller.game.gameTxt.youWon + " " + 12 + " " + controller.game.gameTxt.freeGameTxt + "!");

				
//				controller.game.context.autoplay = false;			
				
				controller.currentTime = System.currentTimeMillis();
				
				controller.game.menu.buyBonusButton.setVisible(false);
				
				controller.game.menu.startButton.disable();
				
				controller.game.overlay.hideWin();
				
				controller.event = Event.NONE;
				
				spins = 0;
			}

			void process(Controller controller) {
				if(/*(System.currentTimeMillis() - controller.currentTime > 18600) || */controller.game.overlay.freeGamesText.animationStopped())
				{
						spins++;
						if(spins < 25){
							controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
						} else if(spins < 25 * 2){
							controller.game.menu.setWinStatus("");
						} else {
							spins = 0;
						}
						switch (controller.event) {
							case START:
								if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
									controller.setNextState(FREE_GAMES_START_SPIN);
								} else {
									controller.setNextState(FREE_BOOK);
								}
							break;
							default:
							break;
					   }
						
						if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
							if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
								controller.setNextState(FREE_GAMES_START_SPIN);
							} else {
								controller.setNextState(FREE_BOOK);
							}
						}
				}
			}

			void exit(Controller controller) {
//				controller.game.menu.startButton.reset();
				controller.game.reels.unhighligthAll();
				controller.game.menu.setStatus("");
				controller.game.overlay.hideFreeGamesText();
				controller.game.reels.showFrameWin(false);
			}
		},
		FREE_BOOK() {
			void entry(Controller controller) {
				
				controller.game.reels.unhighligthAll();
				
				controller.game.overlay.showFreeGamesBook(12);
				
				controller.game.menu.setLeftStatus("");
				controller.game.menu.setStatus("");
				controller.game.menu.setWinStatus("");
				
//				controller.game.context.autoplay = false;	
				
				controller.currentTime = System.currentTimeMillis();
				
				controller.game.menu.startButton.disable();
				
				controller.game.overlay.hideWin();
				
				controller.event = Event.NONE;
				
				spins = 0;
			}

			void process(Controller controller) {
				if((System.currentTimeMillis() - controller.currentTime > 18600) || controller.game.overlay.freeGamesBook.animationStopped())
				{
					spins++;
					if(spins < 25){
						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setWinStatus("");
					} else {
						spins = 0;
					}
					
					switch (controller.event) {
						case START:
							controller.setNextState(FREE_GAMES_WAIT);
						break;
						default:
						break;
					}
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.setNextState(FREE_GAMES_WAIT);
					}
				}
			}

			void exit(Controller controller) {
//				controller.game.menu.startButton.reset();
				controller.game.reels.unhighligthAll();
				controller.game.overlay.hideFreeGamesBook();
				controller.game.reels.showFrameWin(false);
				
				//hack to refresh golden symbol on waiting of free games
				controller.game.context.gameMode = controller.game.context.FREE_GAMES;
				
//				if(controller.game.DEMO_MODE && Gdx.app.getType() == ApplicationType.Desktop){
//					controller.game.context.gameMode = controller.game.context.FREE_GAMES;
//				}
			}
		},
		FREE_GAMES_WAIT() {
			void entry(Controller controller) {
				controller.currentTime = System.currentTimeMillis();
			}
			
			void process(Controller controller) {
				if((System.currentTimeMillis() - controller.currentTime > 600))
				{
					controller.setNextState(FREE_GAMES_START_SPIN);
				}
			}
			
			void exit(Controller controller) {
			}
		},
		FREE_GAMES_START_SPIN() {
			void entry(Controller controller) {
				
				controller.game.overlay.hideWin();
				
				if (controller.game.context.freeGamesCounter > 0) {
					
					controller.game.menu.setWinStatus((controller.game.context.freeGamesCounter -1 ) + " " + controller.game.gameTxt.freeGameLeft);

					controller.startFGSpin();
					
					controller.setNextState(REELS_SPINNING);
					
					controller.game.menu.setWin(controller.game.meters.fgwin);
					
				} else {
					controller.setNextState(FREE_GAMES_END);
				}
			}
			void exit(Controller controller) {
				controller.event = Event.NONE;
			}
		},
		FREE_GAMES_END() {
			void entry(Controller controller) {
				
				controller.game.menu.startButton.disable();
				controller.game.overlay.hideWin();
				
				 controller.currentTime = System.currentTimeMillis();
				
				if (controller.game.meters.fgwin > 0) {
					controller.game.menu.setWinStatus("");
					controller.game.overlay.showFreeGamesWin();
					controller.game.mainScreen.shake(10f, 500f);
					controller.game.reels.dimAllSymbols(-1);
				} else {
					controller.game.menu.setStatus("");
					controller.setNextState(SPIN_END);
					controller.game.context.gameMode = controller.game.context.MAIN_GAME;
				}
				controller.event = Event.NONE;
				spins = 0;
			}

			void process(Controller controller) {
				if(controller.game.overlay.freegamesCongrats.animationStopped()) {
					
					spins++;
					if(spins < 25){
						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setWinStatus("");
					} else {
						spins = 0;
					}
					
					switch (controller.event) {
						case START:
							controller.event = Event.NONE;
							controller.setNextState(FREE_GAMES_TAKE_WINS);
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
						break;
						default:
						break;
					}
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.event = Event.NONE;
						controller.setNextState(FREE_GAMES_TAKE_WINS);
						controller.game.context.onscreenWinMeter = 0;
						controller.game.meters.win = 0;
					}
					
				}
			}

			void exit(Controller controller) {
				controller.game.menu.setLeftStatus("");
				controller.game.overlay.showFreeGamesTitle(false);
				controller.game.reels.setFreeGames(false);
				controller.game.overlay.hideFreeGamesWin();
				controller.game.reels.unhighligthAll();
				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.setVisible(true);
					controller.game.menu.buyBonusButton.disable();
				}
				
//				controller.game.context.gameMode = 0;
			}
		},
		FREE_GAMES_TAKE_WINS() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				
				controller.game.menu.startButton.disable();
				
				controller.game.menu.setStatus("");
				
				controller.game.context.onscreenWinMeter = 0;
				controller.game.menu.setWin(controller.game.context.onscreenWinMeter);

				controller.game.gsLink.console("controller.game.meters.credit = " + controller.game.meters.credit);
				controller.game.gsLink.console("controller.game.meters.fgwin = " + controller.game.meters.fgwin);
				controller.game.context.onscreenCreditMeter = (int) (controller.game.meters.credit - controller.game.meters.fgwin);
				
				if(controller.game.meters.fgwin > 0){
//					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true, 0.8f);
					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true);
				}
				
				controller.w2cSpeed = 1;
				
			}

			void process(Controller controller) {
				
				controller.w2cSpeed = controller.game.meters.fgwin / 20;

				
				if(controller.w2cSpeed < 1)	controller.w2cSpeed = 1;
				
//				controller.game.gsLink.console("controller.game.context.onscreenWinMeter = " + controller.game.context.onscreenWinMeter
//											 + "controller.game.meters.respinwin = " + controller.game.meters.respinwin);
				
				if (controller.game.context.onscreenWinMeter < controller.game.meters.fgwin) {

					int delta = controller.w2cSpeed;

					controller.game.context.onscreenWinMeter += delta;
					controller.game.context.onscreenCreditMeter += delta;
//							* controller.game.meters.getDenomination();
					int currentWin = (controller.game.meters.fgwin - controller.game.context.onscreenWinMeter > 0) ? controller.game.meters.fgwin-controller.game.context.onscreenWinMeter : 0;
					controller.game.menu.setWin(currentWin);
					controller.game.menu.setCredit(controller.game.context.onscreenCreditMeter);

				} else {
					
					controller.game.menu.setWin(0);
					controller.game.menu.setCredit((int) controller.game.meters.credit);					
					
					{
						if(controller.game.meters.fgwin > 0){
							controller.game.sounds.stop(Sounds.SoundTrack.COINUP);
//							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false, 0.8f);
							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false);
						}
						
						if(controller.game.context.hasAddFreeSpins && controller.game.context.addFreeSpinsCnt == 0){
							controller.setNextState(ADD_FREE_GAMES_END);
						} else {
							if (controller.game.context.autoplay) {
								controller.setNextState(State.START_SPIN);
							} else {
								controller.setNextState(IDLE);
							}
						}
					}
				}
				
			}

			void exit(Controller controller) {
				controller.event = Event.NONE;
				
				if(controller.game.context.hasAddFreeSpins){
					controller.game.overlay.addFreeGame.setFreeGamesWin(controller.game.context.addFreeSpinsWin);
				}
				
				controller.game.background.setFreeGamesAnim(false);
				
				controller.game.menu.startButton.reset();
				controller.game.reels.showFrame(false);
				controller.game.menu.setWin(0);
				controller.game.menu.setCredit((int) controller.game.meters.credit);
				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.reset();
				}
			}
		},
		SHOW_RESTORE_FG_END() {
			void entry(Controller controller) {
				
				controller.game.menu.startButton.disable();
				controller.game.overlay.hideWin();
				
				controller.currentTime = System.currentTimeMillis(); 
				
				if (controller.game.meters.fgwin > 0) {
//					controller.game.menu.setStatus("CONGRATS!");
					controller.game.menu.setWinStatus("");
					controller.game.overlay.showFreeGamesWin();
					controller.game.reels.dimAllSymbols(-1);
				} else {
					controller.game.menu.setStatus("");
					controller.setNextState(SHOW_LAST_WINS);
					controller.game.context.gameMode = controller.game.context.MAIN_GAME;
				}
				
				controller.event = Event.NONE;
				spins = 0;
			}

			void process(Controller controller) {
				if(controller.timerCounter > 60) {
					
					
					spins++;
					if(spins < 25){
						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
					} else if(spins < 25 * 2){
						controller.game.menu.setWinStatus("");
					} else {
						spins = 0;
					}
					
					switch (controller.event) {
						case START:
							controller.event = Event.NONE;
							controller.setNextState(SHOW_LAST_WINS);
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
							controller.game.meters.fgwin = 0;
							controller.game.meters.holdAndWin = 0;
						break;
						default:
						break;
					}
					
					
					if(controller.game.context.skipScreen && (System.currentTimeMillis() - controller.currentTime) > 10000){
						controller.event = Event.NONE;
						controller.setNextState(SHOW_LAST_WINS);
						controller.game.context.onscreenWinMeter = 0;
						controller.game.meters.win = 0;
						controller.game.meters.fgwin = 0;
						controller.game.meters.holdAndWin = 0;
					}
				}
			}

			void exit(Controller controller) {
				controller.game.menu.setLeftStatus("");
				controller.game.overlay.showFreeGamesTitle(false);
				controller.game.background.setFreeGamesAnim(false);
				controller.game.reels.setFreeGames(false);
				controller.game.overlay.hideFreeGamesWin();
				controller.game.reels.unhighligthAll();
				
				controller.game.menu.startButton.reset();
				
				if(controller.game.context.hasBuyFeature){
					controller.game.menu.buyBonusButton.setVisible(true);
					controller.game.menu.buyBonusButton.disable();
				}
			}
		},
		/**
		 * @author mutof
		 *
		 */
		SHOW_SPECIAL_WINS() {
			void entry(Controller controller) {
				controller.lineCounterTMP = 0;
				
				controller.timerCounter = 0;
				
				controller.highlightTimeout= 0;
				
				controller.event = Event.NONE;
				
				controller.lineCounter = 0;

				controller.game.overlay.hideWin();
				
//				controller.game.context.onscreenSpecialWinMeter = 0;
				
//				controller.game.overlay.showWin(controller.game.context.outcome.winsSpecial.get(controller.lineCounter).mult * controller.game.context.outcome.winsSpecial.get(controller.lineCounter).betPerLine);
			}
			
			void process(Controller controller) {
				
				// give it half a second
				// if(controller.timerCounter < /*10*/ 1) return;
				
					if (controller.timerCounter > controller.highlightTimeout) {
						controller.timerCounter = 0;
						
						if (controller.lineCounter < controller.game.context.outcome.winsSpecial.size() && controller.lineCounterTMP < 10) {
							controller.processWinSpecial(controller.lineCounter);
//							if(controller.lineCounter % 4 == 0){
//								controller.game.reels.animateOnStopSpecial(controller.symbolsCounter);
//							}
							controller.lineCounter++;
							controller.lineCounterTMP++;
						}
						else {
							
							controller.game.context.outcome.hasSpecialWins = false;
							
							if((controller.game.context.maxWinReached && controller.game.meters.win >= controller.game.context.maxWin) && !controller.maxWinHasBeenShown){
								controller.setNextState(SHOW_MAX_WIN_REACHED);
							} else {
								if(controller.game.meters.currentWin > controller.game.context.BIG_WIN_MULT * controller.game.meters.getTotalBet())
								{
									controller.setNextState(SHOW_BIG_WIN);
									controller.game.overlay.setBigWin(controller.game.meters.win / controller.game.meters.getDenomination());	
								} else {
									controller.setNextState(State.SPIN_END);
									controller.game.reels.clearSpecialReel();
									controller.game.menu.startButton.reset();
									controller.game.reels.unhighligthAll();
									controller.game.reels.clearSpecialReel();
								}
							}
						}
						
					}
				
			}

			void exit(Controller controller) {
				controller.game.overlay.hideWin();
				controller.game.menu.setStatus("");
				controller.event = Event.NONE;
			}
		},
		SHOW_ALL_WINNING_LINES() {
			boolean delay;
			int line = 0;
			Win win = null;
			void entry(Controller controller) {
				
				controller.game.menu.setWinStatus("");
				
				if(controller.game.context.hasAddFreeSpins){
					if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
						
					} else {
						controller.game.overlay.addFreeGame.setFreeGamesWin(controller.game.context.addFreeSpinsWin);
					}
				}
				
				controller.game.context.onscreenWinMeter = 0;
				
				if(controller.game.context.gameMode == controller.game.context.FREE_GAMES){
					controller.game.context.onscreenWinMeter = controller.game.meters.fgwin - controller.game.meters.currentWin;
				} else {
				}
				
				controller.game.reels.showFrameWin(true);
				
				for(int i = 0; i <= controller.game.context.outcome.wins.size(); i++)
				{
					if(controller.game.context.outcome.wins.size() > 0)
					{
						if(!controller.higlightAllLines(i, win))
						{
							line++;
						}
					}
				}
				
				
				if(controller.game.context.outcome.wins.size() > 1 && !controller.game.context.turboGame){
					controller.game.reels.unhighligthAll();
				}
				
				controller.game.overlay.hideWin();
				
				controller.event = Event.NONE;
				
				if(controller.game.context.gameMode != controller.game.context.FREE_GAMES && !controller.game.context.outcome.hasFreeGames){
					controller.game.menu.updateMeters();
				} 
				
				if(controller.game.context.turboGame && controller.game.context.autoplay){
					
				} else{
					controller.game.menu.startButton.reset();
				}

				controller.event = Event.NONE;
				
				delay = false;

				controller.game.menu.setWin(controller.game.context.onscreenWinMeter);

				
				line = 0;
			}

			void process(Controller controller) {
								
//				switch (controller.event) {
//					case START:
//						if(
//						(controller.game.meters.currentWin > controller.game.context.BIG_WIN_MULT * controller.game.meters.getTotalBet()))
//								
//							{
//								controller.setNextState(SHOW_BIG_WIN);
//								controller.game.overlay.setBigWin(controller.game.meters.win / controller.game.meters.getDenomination());
//							}
//							else
//							{
//								controller.game.menu.startButton.reset();
//								controller.setNextState(SPIN_END);
//							}
//
//					break;
//				}
				
				if(controller.game.context.outcome.wins.size() > 0)
				{
					
					if(!controller.showAllLines(line, win))
					{
//						controller.processAllLines(line);
						line++;
					}
					else
					{
						if (controller.timerCounter > 35 || (controller.game.context.outcome.wins.size() == 1 && controller.timerCounter > 20)) {
							controller.clearAllLines();
							
							if((controller.game.context.maxWinReached && controller.game.meters.win >= controller.game.context.maxWin) && !controller.maxWinHasBeenShown){
								controller.setNextState(SHOW_MAX_WIN_REACHED);
							} else {
								if((controller.game.meters.currentWin > controller.game.context.BIG_WIN_MULT * controller.game.meters.getTotalBet())
									&& controller.game.context.outcome.hasSpecialWins == false
									&& controller.game.context.outcome.hasFreeGames == false 
									)
									{
										controller.setNextState(SHOW_BIG_WIN);
										controller.game.overlay.setBigWin(controller.game.meters.win / controller.game.meters.getDenomination());
									}
									else
									{
										if(delay)
										{													
											if(controller.timerCounter > 55){
												delay = false;
												controller.game.menu.startButton.reset();
											}
										}
										else
										{
											controller.game.menu.startButton.reset();
											controller.setNextState(SPIN_END);
										}
									}
								}
						}				  
					}
					
				}
			}

			void exit(Controller controller) {
				controller.event = Event.NONE;
			}
		},
		// add bonus game <aim>
		SHOW_WINS() {
			void entry(Controller controller) {
				controller.skipNearMissIfPays();
				controller.processWin(controller.lineCounter);
				controller.lineCounter++;
				controller.timerCounter = controller.highlightTimeout + 1;
				controller.event = Event.NONE;
				controller.game.changeLine = false;
				
				if (controller.game.context.outcome.hasWin) {
					controller.game.reels.showFrameWin(true);
				}
				
				stateShowAll = false;
			}

			void process(Controller controller) {

				// give it half a second
				switch (controller.event) {
				case START:
										
//					if (!controller.game.context.outcome.hasFreeGames/* && controller.game.context.outcome.wins.size() > 2*/) {
						if (controller.game.context.outcome.hasWin) {
								if(controller.game.reels.getMagicianCount() == 0 || controller.timerCounter > 50)
								{
									controller.event = Event.NONE;
									
									controller.game.reels.unhighligthSpine();
									 
									controller.setNextState(SHOW_ALL_WINNING_LINES);
									 
									stateShowAll = true;
								}
							//always show potion animataion
						}
//					}
//					else {						
//						if (controller.timerCounter > controller.highlightTimeout) {
//							controller.timerCounter = 0;
//
//							if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
//								controller.skipNearMissIfPays();
//								controller.processWin(controller.lineCounter);
//								controller.lineCounter++;
//							}
//							 else  if (controller.game.context.outcome.hasPaytableWins) {
//								 if(controller.game.context.gameMode == controller.game.context.FREE_GAMES /*&& !controller.game.context.outcome.hasRespinGames*/ && !controller.game.context.outcome.hasFreeGames){
//									 controller.setNextState(TAKE_WINS);
//								 } else {
//									 controller.setNextState(SPIN_END);
//								 }
//							} else {
//								controller.setNextState(SPIN_END);
//							}
//						}
//					}
					break;

				default:
//					controller.game.gsLink.console("controller.game.changeLine = " + controller.game.changeLine);
					if (controller.game.changeLine /*|| controller.timerCounter > 200*/) {
						controller.timerCounter = 0;

//						controller.game.gsLink.console("controller.lineCounter = " + controller.lineCounter);
//						controller.game.gsLink.console("controller.game.context.outcome.wins.size() = " + controller.game.context.outcome.wins.size());
						if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
							controller.game.changeLine = false;
							controller.skipNearMissIfPays();
							controller.processWin(controller.lineCounter);
							controller.lineCounter++;
						}
						 else  if (controller.game.context.outcome.hasPaytableWins) {
							 if(controller.game.context.gameMode != controller.game.context.FREE_GAMES && !controller.game.context.outcome.hasFreeGames){
								 controller.setNextState(TAKE_WINS);
							 } else {
								 controller.setNextState(SPIN_END);
							 }
						} else {
							controller.setNextState(SPIN_END);
						}
					}
					break;
				}
				
			}
			void exit(Controller controller) {
				controller.game.menu.setStatus("");
				controller.event = Event.NONE;
				if(controller.game.context.gameMode == controller.game.context.FREE_GAMES && !controller.game.context.outcome.hasSpecialWins){
					controller.game.menu.setWin(controller.game.meters.win);
				} else {
//					controller.game.menu.setWin(controller.game.meters.win);
				}
			}
		},
		
		SHOW_SCATTER_WINS() {
			void entry(Controller controller) {
				controller.lineCounter = 0;
				controller.game.changeLine = false;
				controller.skipNearMissIfPays();
				controller.processScatterWin(controller.lineCounter);
				controller.lineCounter++;
				controller.timerCounter = controller.highlightTimeout + 1;
				controller.event = Event.NONE;
				
				controller.game.reels.showFrameWin(true);

				controller.game.menu.startButton.disable();
				
				stateShowAll = false;
			}
			
			void process(Controller controller) {
				
				// give it half a second
					if (controller.game.changeLine /*|| controller.timerCounter > 200*/) {
						controller.timerCounter = 0;
						
						if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
							controller.game.changeLine = false;
							controller.skipNearMissIfPays();
							controller.processScatterWin(controller.lineCounter);
							controller.lineCounter++;
						}
						else  if (controller.game.context.outcome.hasPaytableWins) {
							if(controller.game.context.gameMode == controller.game.context.FREE_GAMES /*&& !controller.game.context.outcome.hasRespinGames && !controller.game.context.outcome.hasFreeGames*/){
								controller.setNextState(FREE_GAMES);
							} else {
								controller.setNextState(FREE_GAMES);
							}
						} else {
							controller.setNextState(FREE_GAMES);
						}
					}
				
			}
			void exit(Controller controller) {
				controller.game.menu.setStatus("");
				controller.event = Event.NONE;
				controller.game.menu.setWin(controller.game.meters.win);
			}
		},
		SHOW_HOLD_AND_WIN_WINS() {
			void entry(Controller controller) {
				
				controller.game.menu.setWin(0);
				controller.game.menu.setWinStatus("");
				
				controller.event = Event.NONE;
				
				controller.game.reels.showFrameWin(true);
				
				controller.game.menu.startButton.disable();
				
				controller.game.reels.setOldMatrix();
				
				controller.processHoldAndWinWin();
				
				controller.game.changeLine = false;
				
				controller.timerCounter = 0;
			}
			
			void process(Controller controller) {
				
				// give it half a second
				if (controller.game.changeLine || controller.timerCounter > 200) {
					controller.timerCounter = 0;
					if(controller.game.context.gameMode == controller.game.context.HOLD_AND_WIN_GAMES){
						controller.setNextState(HOLD_AND_WIN_START_SPIN);
					} else{
						controller.setNextState(State.HOLD_AND_WIN);
					}
				}
				
			}
			void exit(Controller controller) {
				controller.event = Event.NONE;
			}
		},
		TAKE_WINS() {
			void entry(Controller controller)
			{
				
				controller.game.overlay.hideWin();
				
				controller.event = Event.NONE;
			}
			
			void process(Controller controller) {
				
				
					switch (controller.event) {
					case START:
						controller.setNextState(WIN_TO_CREDIT);
						break;
	
					default:
						if (controller.timerCounter > 10
								&& controller.game.context.autoplay)
							controller.setNextState(WIN_TO_CREDIT);
						//anton stop delay before you take the win 
						else if (controller.timerCounter > 0/*00*/) {
							controller.setNextState(WIN_TO_CREDIT);
						}
						break;
					}

			}

			void exit(Controller controller) {
				controller.event = Event.NONE;
			}
		},
		SHOW_LAST_WINS() {
			void entry(Controller controller) {
				controller.lineCounter = 0;
				
				controller.game.menu.resetMenuButtons();
				
//				if (controller.game.context.outcome.wins.size() == 0){
//					controller.setNextState(IDLE);
//				}
				if(!controller.game.context.outcome.hasBonusWin){
					if(controller.game.context.outcome.hasWin){
						controller.game.changeLine = false;
							controller.game.reels.showFrameWin(true);
							controller.showWin();
							controller.lineCounter++;
					}
				} else {
					controller.game.reels.showFrameWin(true);
				}
				
				controller.timerCounter = controller.highlightTimeout + 1;
				

			}

			void process(Controller controller) {
				
				if (controller.game.context.outcome.wins.size() == 0){
					String currentTxt = "";
					if(!(controller.game.overlay.menuTurboSpins.isVisible())){
						if(!controller.game.context.autoplay){
							if(controller.game.context.turboGame || !controller.game.overlay.menuTurboSpinsIsEnalbled()){
								currentTxt = controller.game.gameTxt.placeBets;
							} else {
								currentTxt = controller.game.gameTxt.holdSpin;
							}
						}
					}
					
				    long blinkInterval = 2000; // Time in ms for each blink step

	                if (controller.blinkStartTime == 0) {
	                    controller.blinkStartTime = System.currentTimeMillis();
	                }

	                long elapsed = System.currentTimeMillis() - controller.blinkStartTime;

	                if ((elapsed / blinkInterval) % 2 == 0) {
	                    controller.game.menu.setStatus(currentTxt);
	                } else {
	                    controller.game.menu.setStatus(controller.game.gameTxt.startWinUp);
	                }
				}
				
				switch (controller.event) {
				case START:
					/*
					 * controller.event = Event.NONE; don't clear START event so
					 * that it propagates to IDLE and new spin begins
					 */
					controller.setNextState(IDLE);
					break;

				default:
					if(!controller.game.context.outcome.hasBonusWin){
//						if (controller.timerCounter > controller.highlightTimeout) {
						if (controller.game.changeLine || controller.timerCounter > 200) {
							controller.timerCounter = 0;
//							controller.game.gsLink.console("Change Line controller.lineCounter = " + controller.lineCounter + " ::::  controller.game.context.outcome.wins.size() = " +  controller.game.context.outcome.wins.size());

							if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
								controller.game.changeLine = false;
								controller.showWin();
								controller.lineCounter++;
							} else {
								controller.lineCounter = 0;
								controller.timerCounter = controller.highlightTimeout;
								controller.game.changeLine = true;
							}
						}
					}
					break;
				}
			}

			void exit(Controller controller) {
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
			}
		},
		SHOW_FG_LAST_WINS() {
			void entry(Controller controller) {
				controller.lineCounter = 0;
				
				controller.game.menu.resetMenuButtons();
				
//				if (controller.game.context.outcome.wins.size() == 0){
//					controller.setNextState(IDLE);
//				}
				if(!controller.game.context.outcome.hasBonusWin){
					if(controller.game.context.outcome.hasWin){
						controller.game.changeLine = false;
						controller.game.reels.showFrameWin(true);
						controller.showWin();
						controller.lineCounter++;
					}
				} else {
					controller.game.reels.showFrameWin(true);
				}
				
				controller.timerCounter = controller.highlightTimeout + 1;
			}
			
			void process(Controller controller) {
				switch (controller.event) {
				case START:
					/*
					 * controller.event = Event.NONE; don't clear START event so
					 * that it propagates to IDLE and new spin begins
					 */
					controller.setNextState(IDLE);
					break;
					
				default:
					if(!controller.game.context.outcome.hasBonusWin){
//						if (controller.timerCounter > controller.highlightTimeout) {
						if (controller.game.changeLine || controller.timerCounter > 200) {
							controller.timerCounter = 0;
//							controller.game.gsLink.console("Change Line controller.lineCounter = " + controller.lineCounter + " ::::  controller.game.context.outcome.wins.size() = " +  controller.game.context.outcome.wins.size());
							
							if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
								controller.game.changeLine = false;
								controller.showWin();
								controller.lineCounter++;
							} else {
								controller.lineCounter = 0;
								controller.timerCounter = controller.highlightTimeout;
								controller.game.changeLine = true;
								
								if (controller.game.context.freeGamesCounter > 0) {
									controller.setNextState(FREE_GAMES_START_SPIN);
								} else {
									controller.setNextState(FREE_GAMES_END);
								}
								
							}
						}
					}
					break;
				}
			}
			
			void exit(Controller controller) {
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
			}
		},
		SHOW_LAST_WINS_RESTORE() {
			void entry(Controller controller) {
				controller.lineCounter = 0;
				
				controller.game.menu.resetMenuButtons();
				
//				if (controller.game.context.outcome.wins.size() == 0){
//					controller.setNextState(IDLE);
//				}
				if(!controller.game.context.outcome.hasBonusWin){
					if(controller.game.context.outcome.hasWin){
						controller.game.changeLine = false;
						controller.game.reels.showFrameWin(true);
						controller.showWin();
						controller.lineCounter++;
					}
				} else {
					controller.game.reels.showFrameWin(true);
				}
				
				controller.timerCounter = controller.highlightTimeout + 1;
			}
			
			void process(Controller controller) {
				switch (controller.event) {
				case START:
					/*
					 * controller.event = Event.NONE; don't clear START event so
					 * that it propagates to IDLE and new spin begins
					 */
					controller.setNextState(IDLE);
					break;
					
				default:
					if(!controller.game.context.outcome.hasBonusWin){
//						if (controller.timerCounter > controller.highlightTimeout) {
						if (controller.game.changeLine) {
							controller.timerCounter = 0;
//							controller.game.gsLink.console("Change Line controller.lineCounter = " + controller.lineCounter + " ::::  controller.game.context.outcome.wins.size() = " +  controller.game.context.outcome.wins.size());
							
							if (controller.lineCounter < controller.game.context.outcome.wins.size()) {
								controller.game.changeLine = false;
								controller.lineCounter++;
							} else {
								controller.lineCounter = 0;
								controller.timerCounter = controller.highlightTimeout;
							}
						}
					}
					break;
				}
			}
			
			void exit(Controller controller) {
				controller.game.reels.unhighligthAll();
				controller.game.reels.showFrameWin(false);
			}
		},

		WIN_TO_CREDIT() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				
//				controller.game.menu.startButton.disable();
				
				controller.game.menu.setStatus("");
				
				
//				controller.game.gsLink.console("controller.game.meters.credit = " + controller.game.meters.credit);
//				controller.game.gsLink.console("controller.game.meters.win = " + controller.game.meters.win);
//				controller.game.gsLink.console("controller.game.context.onscreenCreditMete = " + controller.game.context.onscreenCreditMeter);
				
				controller.game.context.onscreenCreditMeter = (int) (controller.game.meters.credit - controller.game.meters.win);
				
				if(controller.game.meters.win > 0){
					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true);
				}
				
				controller.w2cSpeed = 1;
				
			}

			void process(Controller controller) {
				
				controller.w2cSpeed = controller.game.meters.win / 10;

				
				if(controller.w2cSpeed < 1)	controller.w2cSpeed = 1;
				
//				controller.game.gsLink.console("controller.game.context.onscreenWinMeter = " + controller.game.context.onscreenWinMeter
//											 + "controller.game.meters.respinwin = " + controller.game.meters.respinwin);
				
				if (controller.game.context.onscreenCreditMeter < controller.game.meters.credit) {

					int delta = controller.w2cSpeed;

					controller.game.context.onscreenWinMeter += delta;
					controller.game.context.onscreenCreditMeter += delta;
//							* controller.game.meters.getDenomination();
					int currentWin = (controller.game.meters.win - controller.game.context.onscreenWinMeter > 0) ? controller.game.meters.win-controller.game.context.onscreenWinMeter : 0;
					controller.game.menu.setCredit(controller.game.context.onscreenCreditMeter);

				} else {
					
					controller.game.menu.setCredit((int) controller.game.meters.credit);					
					
				{
						if(controller.game.meters.win > 0){
							controller.game.sounds.stop(Sounds.SoundTrack.COINUP);
							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false);
						}
						
//						if (controller.game.context.autoplay) {
//							controller.setNextState(SHOW_ALL_WINNING_LINES);
//						} else {
						if((controller.game.context.maxWinReached && controller.game.meters.win >= controller.game.context.maxWin) && !controller.maxWinHasBeenShown){
							controller.setNextState(SHOW_MAX_WIN_REACHED);
						} else {
							if((controller.game.meters.currentWin > controller.game.context.BIG_WIN_MULT * controller.game.meters.getTotalBet()))
								{
									controller.setNextState(SHOW_BIG_WIN);
									controller.game.overlay.setBigWin(controller.game.meters.win / controller.game.meters.getDenomination());
								}
								else
								{
									controller.game.menu.startButton.reset();
									controller.setNextState(SPIN_END);
								}
						}
				}
//						}
				}

			}
			
			void exit(Controller controller){
				controller.event = Event.NONE;
				
				controller.game.menu.setCredit((int) controller.game.meters.credit);
			}
		},
		SHOW_BIG_WIN() {
			void entry(Controller controller) {
//				controller.game.overlay.hideBigWin();
				controller.game.menu.setWinStatus("");
				controller.game.overlay.showBigWin();
				controller.game.menu.startButton.disable();
				controller.game.reels.dimAllSymbols(-1);
//				controller.game.reels.unhighligthAll();
				controller.event = Event.NONE;

//				controller.game.menu.setStatus("Press Start To Continue");
				
				controller.w2cSpeed = 3000;
				
				if (controller.game.meters.currentWin * controller.game.meters.getDenomination()  > 1000000000)
					controller.w2cSpeed = 30000000;
				else if (controller.game.meters.currentWin * controller.game.meters.getDenomination()  > 100000000)
					controller.w2cSpeed = 3000000;
				else if (controller.game.meters.currentWin * controller.game.meters.getDenomination()  > 10000000)
					controller.w2cSpeed = 300000;
				else if (controller.game.meters.currentWin * controller.game.meters.getDenomination()  > 1000000)
					controller.w2cSpeed = 30000;
				else if (controller.game.meters.currentWin * controller.game.meters.getDenomination()  > 10000)
					controller.w2cSpeed = 3000;
				
				controller.game.context.onscreenBigWinMeter = 0;
				
				controller.timerCounter = 0;
				
//				controller.game.menu.setWin(controller.game.meters.currentWin /*/ controller.game.meters.getDenomination()*/);
			}

			void process(Controller controller) {

				if (controller.timerCounter > 500) {
					controller.game.overlay.setBigWin(controller.game.meters.currentWin / controller.game.meters.getDenomination());
//					controller.setNextState(SPIN_END);
				}
				
				int delta = 0;

					if (controller.timerCounter % 2 == 0)
					{
						if (controller.game.context.onscreenBigWinMeter < controller.game.meters.currentWin / controller.game.meters.getDenomination())
						{
							if (controller.game.meters.currentWin / controller.game.meters.getDenomination() - controller.game.context.onscreenBigWinMeter >= controller.w2cSpeed)
							{
								delta = controller.w2cSpeed;
								controller.game.context.onscreenBigWinMeter += delta;
								controller.game.overlay.setBigWin(controller.game.context.onscreenBigWinMeter);
							}
							else
							{
								controller.game.overlay.setBigWin(controller.game.meters.currentWin / controller.game.meters.getDenomination());
							}
						}
					}
					
				switch (controller.event) {
					case START:
					{
							controller.game.context.onscreenBigWinMeter = controller.game.meters.currentWin / controller.game.meters.getDenomination();
							controller.event = Event.NONE;
							controller.timerCounter = 0;
							controller.setNextState(SPIN_END);
							controller.game.overlay.setBigWin(controller.game.meters.currentWin / controller.game.meters.getDenomination());

					}
					break;
				default:
					break;
				}
				
				if(controller.game.overlay.bigWinAnimStopped()){
					controller.setNextState(SPIN_END);
				}
			}

			void exit(Controller controller) {
				controller.game.overlay.hideBigWin();
				controller.game.menu.startButton.reset();
				controller.event = Event.NONE;
				controller.game.reels.showFrameWin(true);
				controller.game.menu.setStatus("");
//				controller.game.reels.unhighligthAll();
			}
		},
		SPIN_END(){
			
			void entry(Controller controller) {
				controller.game.gsLink.console("controller.game.meters.win = " + controller.game.meters.win);
				controller.game.gsLink.console("controller.game.context.maxWin = " + controller.game.context.maxWin);
				controller.game.gsLink.console("controller.maxWinHasBeenShown = " + controller.maxWinHasBeenShown);
				
				if((controller.game.context.maxWinReached && controller.game.meters.win >= controller.game.context.maxWin) && !controller.maxWinHasBeenShown){
					controller.setNextState(SHOW_MAX_WIN_REACHED);
				} else {
					if(controller.game.context.outcome.hasSpecialWins){
						controller.setNextState(ANIMATE_SPECIAL_SYMBOL);
						controller.game.menu.startButton.disable();
					} else {
						controller.setNextState(REAL_SPIN_END);
					}
				}
			};



			void process(Controller controller) {};
			
			void exit(Controller controller) {
			};
			
		},
		SHOW_MULTIPLIER(){
			
			void entry(Controller controller) {
			};

			void process(Controller controller) {
				
			};
			
			void exit(Controller controller) {
			};
			
		},		
		
		REAL_SPIN_END() {
			void entry(Controller controller) {
				
				controller.game.gsLink.console("controller.game.context.gameMode = " + controller.game.context.gameMode);
				
//				controller.game.gsLink.console("REAL_SPIN_END");
//				if(controller.game.context.gameMode != controller.game.context.FREE_GAMES && !controller.game.context.outcome.hasFreeGames){
//					controller.game.menu.updateMeters();
//				}
				
//				Gdx.app.debug("Controller", "");
				if( controller.game.meters.win >= controller.game.context.autoplayMaxWin && !controller.game.context.autoplayWinUnlimited){
					controller.game.context.autoplay = false;
				}
				
				controller.game.context.autoplayCurrentLost -= controller.game.meters.win;
			}

			void process(Controller controller) {

				// Hold on for a while at spin end to smooth autoplay/freegames
				// transitions 
//				controller.game.gsLink.console("controller.game.context.gameMode = " + controller.game.context.gameMode);
//				controller.game.gsLink.console("controller.game.context.outcome.hasFreeGames = " + controller.game.context.outcome.hasFreeGames);
				
				if (controller.timerCounter > (controller.game.context.autoplay ? /*16*/4
						: 1)) {
					switch(controller.game.context.gameMode)
					{
						case 0:
//						case 2:
		//					if (controller.game.context.gameMode == /*GameMode.*/controller.game.context.MAIN_GAME ) {
								if (controller.game.context.outcome.hasHoldAndWin) {
									controller.setNextState(SHOW_HOLD_AND_WIN_WINS);
									controller.game.menu.updateMeters();
								} else if (controller.game.context.outcome.hasFreeGames) {
									controller.setNextState(SHOW_SCATTER_WINS);
								} else if (controller.game.context.autoplay) {
									controller.setNextState(IDLE);
									controller.game.menu.updateMeters();
									controller.game.reels.showFrameWin(false);
									controller.game.context.onscreenWinMeter = 0;
								} else {
									if(!(controller.game.context.gameMode == controller.game.context.FREE_GAMES && !controller.game.context.outcome.hasFreeGames))
										if(controller.game.context.hasAddFreeSpins && controller.game.context.addFreeSpinsCnt == 0){
											controller.setNextState(ADD_FREE_GAMES_END);
										} else {
											controller.setNextState(SHOW_LAST_WINS);
											controller.game.context.onscreenWinMeter = 0;
											controller.game.menu.updateMeters();
										}
								}
								
								
//								if(controller.game.context.outcome.hasBonusWin &&
//										(controller.game.context.outcome.bonusWin == 0) || (controller.game.context.outcome.bonusWin == 3)){
//									controller.game.context.onscreenCreditMeter = (int) controller.game.meters.credit - controller.game.overlay.getBonusWin();
//								} else {
									controller.game.context.onscreenCreditMeter = (int) controller.game.meters.credit;
//								}
						break;
						case 2:
							if (controller.game.context.outcome.hasHoldAndWin) {
								controller.setNextState(SHOW_HOLD_AND_WIN_WINS);
							}else{
								if (controller.game.context.holdAndWinCounter> 0) {
									//wait before start new respin
									if (controller.timerCounter > 15) {
										controller.setNextState(HOLD_AND_WIN_START_SPIN);
									}
								} else if (controller.game.context.holdAndWinCounter == 0) {
									controller.setNextState(HOLD_AND_WIN_SHOW_WINS);
								}
							}
						break;
						case 1:
						case 3:
							
							if (controller.game.context.outcome.hasFreeGames) {
									controller.setNextState(SHOW_SCATTER_WINS);
							}else if (controller.game.context.outcome.hasBonusWin) {
								
							} else { 
							
								if (controller.game.context.freeGamesCounter > 0) {
								
								if (controller.timerCounter > 15) {
									controller.game.menu
											.setWin(controller.game.context.onscreenWinMeter);
									controller.setNextState(FREE_GAMES_START_SPIN);
								}
								
								} else if (controller.game.context.freeGamesCounter == 0) {
//										controller.game.context.onscreenWinMeter = 0;// (int)
																						// controller.game.meters.fgwin;
//										controller.game.meters.win = (int) controller.game.meters.fgwin;
										controller.setNextState(FREE_GAMES_END);
								}
							}
						break;
					}
				}
			}

			void exit(Controller controller) {
//				controller.game.reels.unhighligthAll();

				// prototype
				// controller.game.reels.highlightReel(0, 1);
				// controller.game.reels.highlightReel(2, 1);
				// controller.game.reels.highlightReel(4, 1);
			}
		},

		ADD_FREE_GAMES() {},
		ADD_FREE_GAMES_FIRST_SPIN() {},
		ADD_FREE_GAMES_START_SPIN() {},
		ADD_FREE_GAMES_END() {
			void entry(Controller controller) {
				
				controller.game.context.showAddFreeSpins  = false;
				controller.game.context.claimAddFreeSpins = false;
				controller.game.context.hasAddFreeSpins   = false;
				
				controller.event = Event.NONE;
				
				controller.game.overlay.hideWin();
				controller.game.menu.startButton.disable();
					
				controller.game.overlay.showAddFreeSpinsCongrats();
				controller.game.mainScreen.shake(10f, 500f);
				controller.game.reels.dimAllSymbols(-1);

				controller.event = Event.NONE;
				controller.game.menu.startButton.reset();
			}

			void process(Controller controller) {
//				if(/*controller.game.overlay.freegamesCongrats.animationStopped()*/ true) {
//					spins++;
//					if(spins < 25){
//						controller.game.menu.setWinStatus(controller.game.gameTxt.pressAnywhere);
//					} else if(spins < 25 * 2){
//						controller.game.menu.setWinStatus("");
//					} else {
//						spins = 0;
//					}
//					
					switch (controller.event) {
						case ADD_FREE_SPINS_TAKE_WIN:
							controller.event = Event.NONE;
							if(controller.game.context.addFreeSpinsWin > 0){
								controller.setNextState(ADD_FREE_SPINS_TAKE_WINS);
							} else {
								controller.setNextState(SHOW_LAST_WINS);
								controller.game.context.gameMode = controller.game.context.MAIN_GAME;
								controller.event = Event.NONE;
								
								controller.game.menu.startButton.reset();
								controller.game.menu.changeWinStatusPosition();
								controller.game.overlay.hideAddFreeSpinsCongrats();
								
								
								controller.game.reels.showFrame(false);
								controller.game.menu.setWin(0);
								controller.game.menu.setCredit((int) controller.game.meters.credit);
							}
							
							controller.game.context.onscreenWinMeter = 0;
							controller.game.meters.win = 0;
						break;
						default:
						break;
					}
//				}
			}

			void exit(Controller controller) {
				controller.game.menu.setLeftStatus("");
				controller.game.reels.unhighligthAll();
				controller.game.context.gameMode = controller.game.context.MAIN_GAME;

			}
		},
		ADD_FREE_SPINS_TAKE_WINS() {
			void entry(Controller controller) {
				
				controller.event = Event.NONE;
				
				controller.game.menu.startButton.disable();
				
				controller.game.menu.setStatus("");
				
				controller.game.context.onscreenWinMeter = 0;
				controller.game.menu.setWin(controller.game.context.onscreenWinMeter);

				controller.game.context.onscreenCreditMeter = (int) (controller.game.meters.credit - controller.game.context.addFreeSpinsWin);
				
				if(controller.game.context.addFreeSpinsWin > 0){
					controller.game.sounds.play(Sounds.SoundTrack.COINUP, true, 0.8f);
				}
				
				controller.w2cSpeed = 1;
				
			}

			void process(Controller controller) {
				
				controller.w2cSpeed = controller.game.context.addFreeSpinsWin / 20;

				
				if(controller.w2cSpeed < 1)	controller.w2cSpeed = 1;
				
				if (controller.game.context.onscreenWinMeter < controller.game.context.addFreeSpinsWin) {

					int delta = controller.w2cSpeed;

					controller.game.context.onscreenWinMeter += delta;
					controller.game.context.onscreenCreditMeter += delta;
//							* controller.game.meters.getDenomination();
					int currentWin = (controller.game.context.addFreeSpinsWin - controller.game.context.onscreenWinMeter > 0) ? controller.game.context.addFreeSpinsWin-controller.game.context.onscreenWinMeter : 0;
					controller.game.menu.setWin(currentWin);
					controller.game.menu.setCredit(controller.game.context.onscreenCreditMeter);

				} else {
					
					controller.game.menu.setWin(0);
					controller.game.menu.setCredit((int) controller.game.meters.credit);					
					
					{
						if(controller.game.context.addFreeSpinsWin > 0){
							controller.game.sounds.stop(Sounds.SoundTrack.COINUP);
							controller.game.sounds.play(Sounds.SoundTrack.COINEND, false, 0.8f);
						}
						
						if (controller.game.context.autoplay) {
							controller.setNextState(SHOW_LAST_WINS);
						} else {
							controller.setNextState(SHOW_LAST_WINS);
						}
					}
				}
				
			}

			void exit(Controller controller) {
				controller.event = Event.NONE;
				
				controller.game.menu.startButton.reset();
				controller.game.menu.changeWinStatusPosition();
				controller.game.overlay.hideAddFreeSpinsCongrats();
				
				
				controller.game.reels.showFrame(false);
				controller.game.menu.setWin(0);
				controller.game.menu.setCredit((int) controller.game.meters.credit);
			}
		},
		
		BUY_FREE_GAMES() {
			void entry(Controller controller) {
				
				controller.maxWinHasBeenShown = false;
				controller.game.context.autoplay = false;	
				
				controller.game.menu.setWin(0);
				
				controller.currentTime = System.currentTimeMillis();

				controller.game.menu.disableButtons();
				
				controller.currentTime = System.currentTimeMillis();
			}
			
			void process(Controller controller) {
				
				if((System.currentTimeMillis() - controller.currentTime > 500))
				{

					controller.game.reels.unhighligthAll();
					
					
					controller.game.context.prevMode = 0;
					
					controller.maxWinHasBeenShown = false;
					
					if (controller.startBuyFreeGamesSpin()) {
						
						controller.game.menu.paytableButton.disable();
						controller.game.menu.betButton.disable();
						controller.game.menu.autoButton.disable();
						controller.game.menu.buyBonusButton.disable();
						
						controller.game.menu.setWin(0);
						
						controller.game.menu.setWinStatus("");
						controller.game.menu.setStatus(controller.game.gameTxt.goodLuck);
						
						
						controller.game.context.autoplayCurrentLost += controller.game.meters.getTotalBet();
						
						controller.setNextState(REELS_SPINNING);
						
						controller.game.menu.paytableButton.disable();
						controller.game.menu.betButton.disable();

						if(controller.game.context.autoplay)	controller.game.menu.autoButton.toggled();
						else									controller.game.menu.autoButton.disable();
											
					} else {
						controller.setNextState(INSERT_CREDITS);

					}
				}
			}
			
			void exit(Controller controller) {
				
//				controller.game.menu.startButton.reset();
				controller.game.reels.unhighligthAll();
				controller.game.menu.setStatus("");
				controller.game.reels.showFrameWin(false);
				
			}
		},
		
		; /* End of states */

		void entry(Controller controller) {
		};

		void exit(Controller controller) {
		};

		void process(Controller controller) {
		};

	};

	@Override
	public void update() {
		rand.nextInt();
		timerCounter++;
		
		buttonCounter++;
//		state.process(this);
//		time += delta;
		if(game.gsLink.isConnected()) {
			state.process(this);
			game.messages.clearErrors();
		}
		else {
			game.messages.setError("SESSION EXPIRED!");
		}
		
//		game.messages.setFps("" + Gdx.graphics.getFramesPerSecond());
//		game.fps =  Gdx.graphics.getFramesPerSecond();
		
	}

}
