package com.pgd.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.OnDemandAssetLoader;
import com.badlogic.gdx.backends.gwt.preloader.PreloadedAssetManager;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderCallback;
import com.badlogic.gdx.backends.gwt.preloader.Preloader.PreloaderState;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.pgd.game.File;
import com.pgd.game.MyAssetLoader;
import com.pgd.game.BookOfKnight;
import com.pgd.game.base.IFullScreen;

import java_cup.assoc;

public class HtmlLauncher extends GwtApplication {
    static final int WIDTH = 1920;
    static final int HEIGHT = 1080;
    static HtmlLauncher instance;
    static BookOfKnight game;

    private OnDemandAssetLoader onDemandAssetLoader;
    
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(WIDTH, HEIGHT);

        Element element = Document.get().getElementById("embed-html");
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setHeight("100%");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        element.appendChild(panel.getElement());
        
       // config.rootPanel = panel;
        config.audioContext = getAuidoContext();
        config.autoMuteOnPause = true;
        
        return config;
    }
    
    native public JavaScriptObject getAuidoContext() /*-{    	
		console.log("JavaScriptObject getAuidoContext()");
		var audioCntx = $wnd.client.getAuidoContext(); 
		console.dir(audioCntx)
		return audioCntx;
	}-*/;
    
    
    native public int getBrowser() /*-{    	
	
		
		var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
		
		var isFirefox = typeof InstallTrigger !== 'undefined';

		var isSafari = /constructor/i.test(window.HTMLElement) || (function (p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

		var isIE = false || !!document.documentMode;
		
		var isEdge = !isIE && !!window.StyleMedia;
		
		var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);

		var isEdgeChromium = isChrome && (navigator.userAgent.indexOf("Edg") != -1);

		var isBlink = (isChrome || isOpera);

//var output = 'Detecting browsers by ducktyping:<hr>';
//output += 'isFirefox: ' + isFirefox + '\n';
//output += 'isChrome: ' + isChrome + '\n';
//output += 'isSafari: ' + isSafari + '\n';
//output += 'isOpera: ' + isOpera + '\n';
//output += 'isIE: ' + isIE + '\n';
//output += 'isEdge: ' + isEdge + '\n';
//output += 'isEdgeChromium: ' + isEdgeChromium + '\n';
//output += 'isBlink: ' + isBlink + '\n';
//	console.log("output = " + output);
	
		 if (isFirefox) {
			return 2;
		} else if(isEdge)  {
			return 3;
		} else {
			return 1;
		}
		
	}-*/;
    
    @Override
    public Preloader createPreloader() {
    	        	
            return onDemandAssetLoader;
    }

    @Override
    public PreloadedAssetManager getPreloadedAssetManager() {
            return onDemandAssetLoader.getPreloadedAssetManager();
    }
    
    @Override
    public ApplicationListener createApplicationListener() {
        instance = this;
        setLogLevel(LOG_NONE);
        
//        HtmlLauncher.game = new PlanetRocks();
//        consoleLog("PlanetRocks() Constructor");
//        game.fullscreen = new IFullScreen() {
//			
//			@Override
//			public void setFullscreen() {
//				//instance.requestFullscreen();
//			}
//		};
		
        AssetManager manager = new AssetManager();
        manager.setLoader(TextureAtlas.class, new TextureAtlasLoader(new InternalFileHandleResolver()));
        manager.setLoader(BitmapFont.class, new BitmapFontLoader(new InternalFileHandleResolver()));
        
        manager.setLoader(File.class, new MyAssetLoader(new InternalFileHandleResolver()));
        
        String lang = getLangParam();
        
        HtmlLauncher.game = new BookOfKnight(
                new OndemandAssetManager(
                		manager,
                        (fileName, type, listener) -> onDemandAssetLoader.load(new AssetDescriptor(fileName, type), listener)
                ),
                lang
                );
        
        game.browser = getBrowser();
        
        consoleLog("game.browser() = " + game.browser);
        //toni hack -- default - false
        onDemandAssetLoader = new OnDemandAssetLoader(getPreloaderBaseURL(), true);
        
		game.gsLink = new HtmlGameServerLink(game);
		game.gsLink.connect("http://dev.gamingdevices.co.uk:1000/fruitymania/", game.uid);
		
		
		
        return (ApplicationListener) game;
    }

    // JSNI method to get lang parameter
    private native String getLangParam() /*-{
        return $wnd.client.getLangParam();
    }-*/;
    
//	public PreloaderCallback getPreloaderCallback() {
//
//		final Panel preloaderPanel = new VerticalPanel();
//		preloaderPanel.setStyleName("gdx-preloader");
////		final Image logo = new Image(GWT.getModuleBaseURL() + "../assets/splash.png");
////		preloaderPanel.add(logo);
//		
//		//getRootPanel().add(preloaderPanel);
//		
//		return new PreloaderCallback() {
//
//			@Override
//			public void error (String file) {
//				System.out.println("error: " + file);
//			}
//			
//			@Override
//			public void update (PreloaderState state) {
////				state.();
////				consoleLog("state.getDownloadedSize() = " + state.getDownloadedSize());
//				consoleLog("state.getProgress() = " + state.getProgress());
////				consoleLog("state.getTotalSize() = " + state.getTotalSize());
//				game.gsLink.updateProgress(state.getProgress());
//			}			
//			
//		};
//	} 
	
	
	@Override
	public ApplicationLogger getApplicationLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApplicationLogger(ApplicationLogger arg0) {
		// TODO Auto-generated method stub
		
	}
	
}