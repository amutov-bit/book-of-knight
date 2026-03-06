package com.pgd.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;
import com.pgd.game.actors.Button;
import com.pgd.game.actors.ButtonDot;
import com.pgd.game.base.IButtonCallback;

public class HelpScreenPaylines extends Group {
	
	private Image bg;

	private Label title;
	private Label txt;
	private Label txt2;

	public List<Image> lines= new ArrayList<Image>();
	
	public List<Label> linesTxt= new ArrayList<Label>();

	
//	private Button buttonPay;
//	private Button buttonSetting;
//	private Button buttonRules;
	
	BookOfKnight game;
	
	Group gruopLines = new Group();
	
	public HelpScreenPaylines(BookOfKnight game) {
		this.game = game;
		this.setVisible(false);
	}

	public void loadAssets(int currentFile){
		
	}
	
	public void commitAssets() {
	    
		Gdx.app.debug("HelpScreen ", "show()");
		
		
//		#93a7bf 147, 167, 191
		title = new Label(" ", new LabelStyle(game.fonts.font60px, new Color(147/255f, 167/255f, 191/255f, 255f/255f)));
		title.setFontScale(1f);
		title.setText(game.gameTxt.paylinesTitle);
		title.setAlignment(Align.top);
		title.setBounds(0,0 - 40,1920, 1080);
		addActor(title);

		txt = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
		txt.setFontScale(1f);
		txt.setText(game.gameTxt.paylinesTxt);
		txt.setAlignment(Align.center);
		txt.setBounds(0,100 - 150,1920, 1080);
		addActor(txt);
		
        if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "PL" || game.languageCode == "BG"){
			txt2 = new Label(" ", new LabelStyle(game.fonts.font34px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f)));
			txt2.setFontScale(1f);
			txt2.setText(game.gameTxt.paylinesTxt2);
			txt2.setAlignment(Align.center);
			txt2.setBounds(0,100 - 150,1920, 1080);
			addActor(txt2);
		}
        
//		game.gsLink.console("toni 11");
		for(int i = 0 ; i < 10; i++){
			
			game.textures.getPayLiensAtlas().findRegions("paylines").get(i).getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			lines.add(new Image(new Sprite(game.textures.getPayLiensAtlas().findRegions("paylines").get(i))));
			lines.get(i).setVisible(true);
			if(i > 4){
				lines.get(i).setPosition(500 + i % 5 * (lines.get(i).getWidth() + 20) , 800 - 150);
			} else {
				lines.get(i).setPosition(500 + i * (lines.get(i).getWidth() + 20) , 800 - 150 + 150);
			}
			gruopLines.addActor(lines.get(i));
			
			linesTxt.add(new Label(" ", new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f))));
			linesTxt.get(i).setFontScale(1f);
			linesTxt.get(i).setText(game.gameTxt.paylinesLine + " " + (i+1));
			linesTxt.get(i).setAlignment(Align.top);
			linesTxt.get(i).setBounds(lines.get(i).getX(), lines.get(i).getY() + 40, lines.get(i).getWidth(), lines.get(i).getHeight());
			gruopLines.addActor(linesTxt.get(i));

		}
//
//		for(int i = 5 ; i < 9; i++){
//			
//			game.textures.getPayLiensAtlas().findRegion("paylines_0"+(i+1)+"").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//			lines.add(new Image(new Sprite(game.textures.getPayLiensAtlas().findRegion("paylines_0"+(i+1)+""))));
//			lines.get(i).setVisible(true);
//			lines.get(i).setPosition(500 + i * (lines.get(i).getWidth() + 20) , 800 - 150);
//			gruopLines.addActor(lines.get(i));
//			
//			linesTxt.add(new Label(" ", new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f))));
//			linesTxt.get(i).setFontScale(1f);
//			linesTxt.get(i).setText(game.gameTxt.paylinesLine + " " + (i+1));
//			linesTxt.get(i).setAlignment(Align.top);
//			linesTxt.get(i).setBounds(lines.get(i).getX(), lines.get(i).getY() + 40, lines.get(i).getWidth(), lines.get(i).getHeight());
//			gruopLines.addActor(linesTxt.get(i));
//			
//		}
//		int i = 9;
//		
//		game.textures.getPayLiensAtlas().findRegion("paylines_"+(i+1)+"").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		lines.add(new Image(new Sprite(game.textures.getPayLiensAtlas().findRegion("paylines_"+(i+1)+""))));
//		lines.get(i).setVisible(true);
//		lines.get(i).setPosition(500 + i * (lines.get(i).getWidth() + 20) , 800 - 150);
//		gruopLines.addActor(lines.get(i));
//		
//		linesTxt.add(new Label(" ", new LabelStyle(game.fonts.font46px, new Color(255f/255f, 255f/255f, 255f/255f, 255f/255f))));
//		linesTxt.get(i).setFontScale(1f);
//		linesTxt.get(i).setText(game.gameTxt.paylinesLine + " " + (i+1));
//		linesTxt.get(i).setAlignment(Align.top);
//		linesTxt.get(i).setBounds(lines.get(i).getX(), lines.get(i).getY() + 40, lines.get(i).getWidth(), lines.get(i).getHeight());
//		gruopLines.addActor(linesTxt.get(i));
		
		addActor(gruopLines);
		
//		if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "PL"){
//			gruopLines.setPosition(0, 50);
//			txt.setBounds(0,100 - 150 + 30 + 50,1920, 1080);
//			txt2.setText(game.gameTxt.paylinesTxt2);
//			txt2.setAlignment(Align.center);
//			txt2.setBounds(0,100 - 150 - 120 + 50,1920, 1080);
//		}
		
		if(game.languageCode == "ESP" || game.languageCode == "POR" || game.languageCode == "FRA" || game.languageCode == "PL"){
			gruopLines.setPosition(0, 50 - 80);
			txt.setBounds(0,100 - 150 + 30 + 50 - 50,1920, 1080);
			txt2.setText(game.gameTxt.paylinesTxt2);
			txt2.setAlignment(Align.center);
			txt2.setBounds(0,100 - 150 - 120 + 50 - 50,1920, 1080);
		}
		
		if(game.languageCode == "BG"){
			gruopLines.setPosition(0, 50 - 80);
			txt.setBounds(0,100 - 150 + 30 + 50 - 50,1920, 1080);
			txt2.setText(game.gameTxt.paylinesTxt2);
			txt2.setAlignment(Align.center);
			txt2.setBounds(0,100 - 150 - 120 + 50 - 50 + 15,1920, 1080);
		}
		
		if(game.languageCode=="FRA"){
			gruopLines.setPosition(0, 50 - 55);
			txt.setBounds(0,100 - 150 + 30 + 50 - 50,1920, 1080);
			txt2.setText(game.gameTxt.paylinesTxt2);
			txt2.setAlignment(Align.center);
			txt2.setBounds(0,100 - 150 - 120 + 50 - 50,1920, 1080);
		}
	}
	
}
