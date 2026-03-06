package com.pgd.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.BookOfKnight;
import com.pgd.game.Sounds.SoundTrack;


public class JackpotsTitle extends Group {
	
	BookOfKnight game;

	/**
	 * multiplier x2 animation atlas and regions <aim>
	 */	
	
	
	private float timeElapsed;
	private float lastTimeElapsed;
	private boolean animateShow;
	private boolean animateHide;
	private int currentFrame = 0;
	
	private Image bg,bg1,bg2;
	private Image digit;
	private Image goldEgg1;
	private Image goldEgg2;
	private Image goldEgg3;
	private Image goldSilver;
	private Image goldBronze;
	private Image digitEgg;
	
	private DigitsSingle digits;
	
	
	private float startX, startY, currentX;
	private int symW, symH;
	private String currentWin;
	private int beforeDecimal;
	private int lenght;
	private float backgroundWidth, backgroundHight;
	private boolean decimal;
	private int offsetW, offsetH;
	
	Color color;
	
	private Label mega, major,  mini; 

	public JackpotsTitle(BookOfKnight game) {
		this.game = game;
		//this.setVisible(true);
		loadAssets();		
//		animate = false;
	}
	
	public void loadAssets() {
	}
	
	/**
	 * @param name
	 * @param digits
	 */
	public void commitAssets() {
		
		game.textures.getInterfaceAtlas().findRegion("jackpot_gold_act").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("jackpot_gold_act")));
		bg.setPosition(getX() + 240, getY() + 1080 - 132 - 0);
		addActor(bg);

		game.textures.getInterfaceAtlas().findRegion("jackpot_silver_act").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg1 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("jackpot_silver_act")));
		bg1.setPosition(getX() + bg.getWidth()+bg.getX(), getY() + 1080 - 132 - 0);
		addActor(bg1);
		
		game.textures.getInterfaceAtlas().findRegion("jackpot_bronze_act").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg2 = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("jackpot_bronze_act")));
		bg2.setPosition(getX() + bg1.getWidth()+bg1.getX(), getY() + 1080 - 132 - 0);
		addActor(bg2);
		
															//		jackpot gold: #ffc000 255 , 192 , 0
		mega = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(255/255f, 192/255f, 0/255f, 255f/255f)));
		mega.setVisible(true);
		mega.setFontScale(1f);
		mega.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 1000, false));
		mega.setAlignment(Align.center);
		mega.setBounds(355,1080 - 39 - 42 + 20 - 10 ,285, 0);
		addActor(mega);
		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		goldEgg1 =new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur")));
		goldEgg1.setPosition(getX() + 254 + 75 + 140 + 120, getY() + 1080 - 132 - 0 + 87);
		goldEgg1.setScale(0.15f);
		addActor(goldEgg1);
		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		goldEgg2 =new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur")));
		goldEgg2.setPosition(getX() + 254 + 75 + 140 + 120 - 40, getY() + 1080 - 132 - 0 + 87);
		goldEgg2.setScale(0.15f);
		addActor(goldEgg2);
		
		game.textures.getInterfaceAtlas().findRegion("mega_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		goldEgg3 =new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mega_blur")));
		goldEgg3.setPosition(getX() + 254 + 75 + 140 + 120 + 40, getY() + 1080 - 132 - 0 + 87);
		goldEgg3.setScale(0.15f);
		addActor(goldEgg3);
		
		major = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(153/255f, 210/255f, 239/255f, 255f/255f)));
		major.setVisible(true);
		major.setFontScale(1f);
		major.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 100, false));
		major.setAlignment(Align.center);
		major.setBounds(835,1080 - 39 - 42 + 20 - 10,285, 0);
		addActor(major);
		
		game.textures.getInterfaceAtlas().findRegion("major_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		goldSilver =new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("major_blur")));
		goldSilver.setPosition(getX() + 254 + 75 + 140 + 53 + 590, getY() + 1080 - 132 - 0 + 87);
		goldSilver.setScale(0.15f);
		addActor(goldSilver);

													//		d4826dot bronze: #d4826d 212 , 130 , 109
		mini = new Label("ERROR: ", new LabelStyle(game.fonts.font60px, new Color(212/255f, 130/255f, 109/255f, 255f/255f)));
		mini.setVisible(true);
		mini.setFontScale(1f);
		mini.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 20, false));
		mini.setAlignment(Align.center);
		mini.setBounds(1322,1080 - 39 - 42 + 20 - 10,285, 0);
		addActor(mini);

		game.textures.getInterfaceAtlas().findRegion("mini_blur").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		goldBronze =new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("mini_blur")));
		goldBronze.setPosition(getX() + 254 + 75 + 140 + 53 + 535 * 2, getY() + 1080 - 132 - 0 + 87);
		goldBronze.setScale(0.15f);
		addActor(goldBronze);

//		game.textures.getInterfaceAtlas().findRegion("egg_digit3").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		digitEgg = new Image(new Sprite(game.textures.getInterfaceAtlas().findRegion("egg_digit3")));
//		digitEgg.setPosition(getX() + 254 + 75, getY() + 1080 - 132 - 0 + 35);
////		digitEgg.setScale(0.4f);
//		addActor(digitEgg);
	}
	
    public void show(){
    	animateShow = true;
    	animateHide = false;
    	currentFrame = 0;
    }

    public void hide(){
    	animateShow = false;
    	animateHide = true;
    	currentFrame = 0;
    }
    
    public void update(){
		mega.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 1000, false));
		major.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 100, false));
		mini.setText("" + game.meters.formatNumber(game.meters.getTotalBet() * 20, false));
    }
	
}
