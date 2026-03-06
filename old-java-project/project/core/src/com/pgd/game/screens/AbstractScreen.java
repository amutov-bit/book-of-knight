package com.pgd.game.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pgd.game.BookOfKnight;

public abstract class AbstractScreen implements Screen {
	
    protected final BookOfKnight game;
    private long start = System.currentTimeMillis();
    public AbstractScreen prevScreen = null;
    public Stage stage;
    private static final float FIXED_TIMESTEP = 1f / 60f; // 60 FPS
	private float accumulator = 0f;

    OrthographicCamera camera;
    private PolygonSpriteBatch batchSpine;
	
	public AbstractScreen(BookOfKnight game) {
		this.game = game;
		
		batchSpine = new PolygonSpriteBatch();
		
		camera = new OrthographicCamera(game.VIRTUAL_WIDTH, game.VIRTUAL_HEIGHT);

		if (Gdx.app.getType() == ApplicationType.Android) {
			stage = new Stage(new StretchViewport(game.VIRTUAL_WIDTH, game.VIRTUAL_HEIGHT));
		}
		else {
			stage = new Stage(new FitViewport(game.VIRTUAL_WIDTH, game.VIRTUAL_HEIGHT, camera), batchSpine);
		}
			
	}

    @Override
    public void render(float delta) {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
	    
	    game.sounds.update(delta); // hook-up sounds actions /

	    // Limit delta time to prevent large jumps (useful for browser tab switching, lag, etc.)
	    if (delta > 0.25f) {
	        delta = 0.25f;
	    }
	    
	    accumulator += delta;

	    while (accumulator >= FIXED_TIMESTEP) {
	        update(FIXED_TIMESTEP); // Use fixed step for consistent behavior
	        accumulator -= FIXED_TIMESTEP;
	    }

	    stage.act(delta);  // Pass real delta for smooth animations
	    draw();
	    stage.draw();
	}

    public void update(float deltaTime) {
        // Add your update logic here
    }
    
	public void draw() {
		
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	
	public void shake(float intensity, float duration) {
//	    this.elapsed = 0;
//	    this.duration = duration / 1000f;
//	    this.intensity = intensity;
//	    this.startAnim = true;
	}
	
    /**
     * Sleep to limit frame rate
     * @param fps
     */
    public void sleep(int fps) {
        if (fps > 0) {
            long targetDelay = 1000 / fps;
            long diff = System.currentTimeMillis() - start;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            start = System.currentTimeMillis();
        }
    }
	
	public void setPrevScreen()
	{
		
	}
}
