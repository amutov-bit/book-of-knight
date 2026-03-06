package com.pgd.game.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.ButtonMenuBet;
import com.pgd.game.Controller.State;


public class MenuBet extends Group {
	
	BookOfKnight game;
	
	public Image bg;
	public Image bet_bg;

	private Label title;
	private Label totalBetTxt;
	private Label totalBetField;
	private Label linesTxt;
	
	private List<ButtonMenuBet>  bets;
	private ButtonMenuBet		 buttonMaxBet;
	private Button 				 plusButton;
	private Button 				 minusButton;
	private Button 				 closeButton;
	
	private int betArray[] = {1, 10, 30, 60, 80, 150};
	
	public MenuBet(BookOfKnight game) {
		this.game = game;
		
		bets = new ArrayList<ButtonMenuBet>();
	}
	
	public void loadAssets() {
		
	}
	
	
	public void commitAssets() {
		
		
		game.textures.getMenuBetAtlas().findRegion("bg_bet").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("bg_bet")));
		bg.setPosition(846, 1080 - 946 - 48);
		addActor(bg);

		game.textures.getMenuBetAtlas().findRegion("bet_value").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bet_bg = new Image(new Sprite(game.textures.getMenuBetAtlas().findRegion("bet_value")));
		bet_bg.setPosition(1036, 1080 - 438 - 106);
		addActor(bet_bg);
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setAlignment(Align.top);
		title.setBounds(bg.getX(),bg.getY() - 100, bg.getWidth(), bg.getHeight());
		title.setFontScale(1f);
		addActor(title);

		//#bee1f5 190, 225, 245
		totalBetTxt = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		totalBetTxt.setPosition((int)bg.getX() + 0, (int)bg.getY() + 555);
		totalBetTxt.setAlignment(Align.center);
		totalBetTxt.setWidth((int)bg.getWidth());
		totalBetTxt.setFontScale(1f);
		addActor(totalBetTxt);

		linesTxt = new Label(" ", new LabelStyle(game.fonts.font36px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		linesTxt.setPosition((int)bg.getX() + 0, (int)bg.getY() + 80);
		linesTxt.setAlignment(Align.center);
		linesTxt.setWidth((int)bg.getWidth());
		linesTxt.setFontScale(1f);
		addActor(linesTxt);

//		#bee1f5  190, 225, 245
		totalBetField = new Label(" ", new LabelStyle(game.fonts.font46px, new Color(190/255f, 225/255f, 245/255f, 255f/255f)));
		totalBetField.setPosition(1036, 1080 - 438 - 106);
		totalBetField.setAlignment(Align.center);
		totalBetField.setBounds(bet_bg.getX(),bet_bg.getY(), bet_bg.getWidth(), bet_bg.getHeight());
		addActor(totalBetField);
		
		Gdx.app.debug("Menu Bet", "game.gameTxt.menuBetTitle = " + game.gameTxt.menuBetTitle);
		
		buttonMaxBet = new ButtonMenuBet(game, 1136, 1080 - 106 - 234, game.textures.getMenuBetAtlas(), "button_maxbet");
		buttonMaxBet.setHover(false);
		buttonMaxBet.commitAssets();
		addActor(buttonMaxBet);

		totalBetTxt.setText(game.gameTxt.menuBetTotalBet);
		title.setText(game.gameTxt.menuBetTitle);
		buttonMaxBet.setText(game.gameTxt.menuBetMaxBet);
		linesTxt.setText(game.gameTxt.menuBetLines + " " +game.meters.MAX_LINES);
		
		plusButton = new Button(game, 1442, 1080 - 462 - 60, game.textures.getMenuBetAtlas(), "plus");
		plusButton.setHover(false);
		plusButton.commitAssets();
		addActor(plusButton);

		minusButton = new Button(game, 1062, 1080 - 462 - 60, game.textures.getMenuBetAtlas(), "minus");
		minusButton.setHover(false);
		minusButton.commitAssets();
		addActor(minusButton);

		closeButton = new Button(game, (int)bg.getX() + (int)bg.getWidth() - 175, (int)bg.getY() + (int)bg.getWidth() - 100 , game.textures.getMenuBetAtlas(), "button_closesmall");
		closeButton.setHover(false);
		closeButton.commitAssets();
		addActor(closeButton);
		
		closeButton.setPosition((int)bg.getX() + (int)bg.getWidth() - 155 - 35 - 45, (int)bg.getY() + (int)bg.getWidth() - 100 - 20 - 45);
		
		plusButton.setPosition(1442 +  - 33 - 8, 1080 - 462 - 60 + - 30);
		minusButton.setPosition(1062 + - 20, 1080 - 462 - 60 + - 30);
		
		closeButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				hide();
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		minusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.decrementBetPerLine();
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});
		
		plusButton.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.incrementBetPerLine();
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}				
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});			
			
		for(int i = 0; i < 6; i++){
			bets.add(new ButtonMenuBet(game, 0, 0, game.textures.getMenuBetAtlas(), "button_digit"));
		}
		
		for(int i = 0; i < bets.size(); i++)
		{
			bets.get(i).setHover(false);
			bets.get(i).commitAssets();
			addActor(bets.get(i));
		}

		bets.get(0).setPosition(937   , 1080 - 606 - 110);
		bets.get(1).setPosition(1182  , 1080 - 606 - 110);
		bets.get(2).setPosition(1427  , 1080 - 606 - 110);

		bets.get(3).setPosition(937   , 1080 - 750 - 110);
		bets.get(4).setPosition(1182  , 1080 - 750 - 110);
		bets.get(5).setPosition(1427  , 1080 - 750 - 110);


				

		bets.get(0).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[0]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
		bets.get(1).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[1]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		bets.get(2).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[2]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		bets.get(3).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[3]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		bets.get(4).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[4]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		bets.get(5).registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				game.meters.setBetPerLine((betArray[0] > game.meters.MAX_BET_PER_LINE) ? game.meters.MAX_BET_PER_LINE : betArray[5]);
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	

		
		buttonMaxBet.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
				
				game.meters.selectMaxbet();
				game.math.linesSelected = game.meters.getLines();
				game.math.betPerLine = game.meters.getBetPerLine();
				
				game.menu.updateMeters();
				
				game.meters.win = 0;
//				game.controller.setNextState(State.IDLE);
				
			}
			
			@Override
			public void onPress() {
				game.sounds.play(SoundTrack.KNOCK, false);
			}
		});	
		
	}
	
	public void hide(){
		game.menu.paytableButton.reset();
		game.menu.betButton.reset();
		game.menu.autoButton.reset();
		game.menu.startButton.reset();
		game.menu.setStatus("");
		game.menu.updateMeters();
		game.helpScreen.updatePaytable();
		game.meters.win = 0;
		game.controller.setNextState(State.IDLE);
		game.menu.isBetPress = !game.menu.isBetPress;

		this.setVisible(false);

	}
	
	
	public void show(){
		
//		game.menu.setStatus(game.gameTxt.autoStatusTxt);
		
//		int size = 0;
//		int end  = 0;
//		for(int i = 0; i < game.meters.betArray.length; i++){
//			size++;
//			if(game.meters.betArray[i] == game.meters.MAX_BET_PER_LINE){
//				end = i;
//				break;
//			}
//		}
//		
//		int step = size/10;
		
		int count = 0;
		for(int i = 0; i < game.meters.betArray.length; i++){
			if(game.meters.betArray[i] <= game.meters.MAX_BET_PER_LINE){
				count++;
			}
		}

		for(int i = 0; i < bets.size() - 1; i++)
		{
			bets.get(i).reset();
		}
		
		Gdx.app.debug("Menu BEt", " size = " + count);
		Gdx.app.debug("Menu BEt", " step = " + count/5);
		
		int step =  count/5;
		
		betArray[0] = game.meters.betArray[0]; 
		betArray[1] = game.meters.betArray[1 * step];
		betArray[2] = game.meters.betArray[2 * step];
		betArray[3] = game.meters.betArray[3 * step];
		betArray[4] = game.meters.betArray[4 * step];
		betArray[5] = game.meters.betArray[5 * step];
		
		for(int i = 0; i < betArray.length; i++){
			if(betArray[i] >= game.meters.MAX_BET_PER_LINE)	betArray[i] = game.meters.MAX_BET_PER_LINE;
		}
		
		game.menu.isBetPress = !game.menu.isBetPress;

		setTotalBet(game.meters.getTotalBet());
	}
	
	public void setCredit(int value){
//		balance.setText((Integer.toString(value/100) + "." + (value / 10 % 10) + (value % 10)) + " " + game.meters.getCurrency());
	}

	public void setTotalBet(long value){
		
//		value = (menuLines.isBoostActive() ? game.meters.getTotalBetAddLines()
//		if(menuLines.isBoostActive()game.meters.getTotalBetAddLines()){
//			
//		}
		
		totalBetField.setText((game.meters.formatNumber(value, game.hasBigNumberK == 1)) + " " + game.meters.getCurrency());
		
//		Object asd = game.menu.menuLines.isBoostActive() ? game.meters.getTotalBetAddLines() / game.meters.getDenomination() : game.meters.getTotalBetInCredits();
				
		int tmp = 0; 
		tmp = (int) ((betArray[0] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[0]));
		bets.get(0).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
		tmp = (int) ((betArray[1] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[1]));
		bets.get(1).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
		tmp = (int) ((betArray[2] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[2]));
		bets.get(2).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
		tmp = (int) ((betArray[3] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[3]));
		bets.get(3).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
		tmp = (int) ((betArray[4] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[4]));
		bets.get(4).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
		tmp = (int) ((betArray[5] > game.meters.MAX_BET_PER_LINE) ? value / game.meters.getBetPerLine() * game.meters.MAX_BET_PER_LINE : (int) (value / game.meters.getBetPerLine() * betArray[5]));
		bets.get(5).setText(game.meters.formatNumber(tmp, game.hasBigNumberK == 1));
		
//		Gdx.app.debug("Menu BEt", "game.meters.getBetPerLine() = " + game.meters.getBetPerLine());
//		Gdx.app.debug("Menu BEt", "game.meters.MAX_BET_PER_LINE = " + game.meters.MAX_BET_PER_LINE);
	}
	
}
