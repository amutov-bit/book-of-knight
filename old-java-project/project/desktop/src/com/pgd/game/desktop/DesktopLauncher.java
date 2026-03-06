 package com.pgd.game.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pgd.game.BookOfKnight;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		//System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		cfg.title = "Deep Blue";
		cfg.width  = 1920;//1680;
		cfg.height = 1080;//1050;
		
//		cfg.fullscreen = true;
		
        new LwjglApplication(
                new BookOfKnight(
                        new OndemandAssetManager(
                                new AssetManager(),
                                (fileName, type, listener) -> listener.onSuccess(fileName, type)
                                
                        ), "ENG"
                ),
                cfg
        );
	}
}
