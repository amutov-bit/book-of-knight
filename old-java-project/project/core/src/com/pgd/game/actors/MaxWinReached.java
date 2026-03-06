package com.pgd.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.actors.Button;
import com.pgd.game.base.IButtonCallback;

public class MaxWinReached extends Group {

	@SuppressWarnings("unused")
	private BookOfKnight game;
//	private Label demo;
	private Label error, title, congrats;

	private Image bg;
	private Button reload;
	
	public MaxWinReached (BookOfKnight game) {
		
		this.game = game;
		
//		demo = new Label("Demo", new LabelStyle(game.fonts.amita40, Color.WHITE));
//		demo.setPosition(game.VIRTUAL_WIDTH - 92 - 20, 0);
//		demo.setVisible(game.DEMO_MODE);
		

		
//		addActor(demo);
	
		
	}
	
	public void commitAssets(){
		title = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, Color.YELLOW));
		title.setVisible(true);
		title.setText(game.gameTxt.maxWinReachedTitle);
		title.setAlignment(Align.center);
		title.setBounds(0,0 + 180,1920, 1080);
		addActor(title);
		
		
		congrats = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, Color.WHITE));
		congrats.setFontScale(1f);
		congrats.setVisible(true);
		congrats.setText(game.gameTxt.maxWinReachedCongrats);
		congrats.setAlignment(Align.center);
		congrats.setBounds(0,0 + 180 - 80,1920, 1080);
		addActor(congrats);

		error = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, Color.WHITE));
		error.setFontScale(1f);
		error.setVisible(true);
		error.setText(game.gameTxt.maxWinReachedYouHave + " " + game.context.maxWin + game.gameTxt.maxWinReachedEnd);
		error.setAlignment(Align.center);
		error.setBounds(0,0 + 180 - 90 * 2,1920, 1080);
		addActor(error);
		
	}
	
	public void setMaxWin(){
		
		error.setText(game.gameTxt.maxWinReachedYouHave + " " + game.context.maxWin / game.meters.getTotalBet() + game.gameTxt.maxWinReachedEnd);
	}
	
}
