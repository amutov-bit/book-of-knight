package com.pgd.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.pgd.game.actors.Button;
import com.pgd.game.base.IButtonCallback;

public class Error extends Group {

	@SuppressWarnings("unused")
	private BookOfKnight game;
//	private Label demo;
	private Label error, fps;

	private Image bg;
	private Button reload;
	
	public Error (BookOfKnight game) {
		
		this.game = game;
		
//		demo = new Label("Demo", new LabelStyle(game.fonts.amita40, Color.WHITE));
//		demo.setPosition(game.VIRTUAL_WIDTH - 92 - 20, 0);
//		demo.setVisible(game.DEMO_MODE);
		

		
//		addActor(demo);
	
		
	}
	
	public void commitAssets(){
		error = new Label("ERROR: ", new LabelStyle(game.fonts.font36px, Color.RED));
		error.setPosition(30, game.VIRTUAL_HEIGHT - 36 - 10);
		error.setVisible(false);
		addActor(error);
		
		fps = new Label("ERROR: ", new LabelStyle(game.fonts.font36px, Color.RED));
		fps.setPosition(30, game.VIRTUAL_HEIGHT - 36 - 10);
		
		
		game.textures.getAutoPlayAtlas().findRegion("error_message").getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg = new Image(new Sprite(game.textures.getAutoPlayAtlas().findRegion("error_message")));
		bg.setPosition((game.VIRTUAL_WIDTH - 392)/2, (game.VIRTUAL_HEIGHT - 292)/2 );
		addActor(bg);

		reload = new Button(game, (int)bg.getX() +  (int)(bg.getWidth() - 176) /2,  (int)bg.getY() + 50, game.textures.getAutoPlayAtlas(), "b_reload");
		reload.setHover(true);
		reload.commitAssets();
		addActor(reload);
		
		reload.registerCallback(new IButtonCallback() {
			
			@Override
			public void onRelease() {
			}
			
			@Override
			public void onPress() {
				game.gsLink.reload();
			}
		});	
		
		bg.setVisible(false);
		reload.setVisible(false);
		
//		addActor(fps);
	}
	
	public void setError(String str) {
		error.setText(str);
		error.setVisible(true);
		bg.setVisible(true);
		reload.setVisible(true);
	}
	
	public void setFps(String str) {
//		fps.setText(str);
//		fps.setVisible(true);
	}
	
	public void clearErrors() {
		error.setText("");
//		error.setVisible(false);
//		bg.setVisible(false);
//		reload.setVisible(false);
	}
}
