package com.pgd.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.OndemandAssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.pgd.game.actors.AutoPlay;
//import com.esotericsoftware.spine.Skeleton;
import com.pgd.game.actors.BigAnimation;
import com.pgd.game.actors.BuyBonusMenu;
import com.pgd.game.base.IFullScreen;
import com.pgd.game.base.MenuBet;
import com.pgd.game.base.SpineTextures;
import com.pgd.game.base.SpineTexturesSymbols;
import com.pgd.game.base.SpineTexturesBG;
import com.pgd.game.base.SpineTexturesButton;
import com.pgd.game.base.SpineTexturesFireAnimLoop;
import com.pgd.game.base.SpineTexturesFireAnimWin;
import com.pgd.game.base.SpineTexturesFreeGamesCongrats;
import com.pgd.game.base.SpineTexturesFreeGamesTxt;
import com.pgd.game.base.SpineTexturesHoldAndWinTxt;
import com.pgd.game.base.SpineTexturesIntro;
import com.pgd.game.base.SpineTexturesLoading;
import com.pgd.game.base.SpineTexturesLoop;
import com.pgd.game.base.SpineTexturesBookAnimation;
import com.pgd.game.base.Textures;
import com.pgd.game.net.DefaultGameServerLink;
import com.pgd.game.net.IGameServerLink;
import com.pgd.game.screens.HelpScreen;
import com.pgd.game.screens.LoadingScreen;
import com.pgd.game.screens.LockedScreen;
import com.pgd.game.screens.MainScreen;

/**
 * FruityMania class file
 * 
 * @author Dimitar
 *
 */
public class BookOfKnight extends Game {
	
	/**
	 * Version string
	 */
	public String versionString = "v0.0.1";
	
	/**
	 * Client unique ID
	 */
	public String uid = "12345678";
	
	/**
	 * Game virtual width and height. Used by stage viewport 
	 * when fitting the game on the actual screen. 
	 */
	public int VIRTUAL_WIDTH  = 1920;//1650;
	public int VIRTUAL_HEIGHT = 1080;//1050;
	
	public int REELS   = 5;
	public int SYMBOLS = 3;
	
	public int CHROME  = 1;
	public int FIREFOX = 2;
	public int EDGE    = 3;
	public int SAFARI  = 4;
	
	public boolean changeLine = true;
	/**
	 * Set to true to run math test
	 */
	public final boolean MATH_TEST = false; 
	
	/**
	 * Demo mode
	 */
	public boolean DEMO_MODE = true;
	
	
	public boolean locked = false;
	/**
	 * Messages
	 */
	public Error messages;
	
	/**
	 * Holds all the assets
	 */
	public AssetManager manager;
	
	
	
	/**
	 * Set all demand Asset Mangers
	 */
	public OndemandAssetManager ondemandAssetManager;
	
	/**
	 * Holds all reel textures
	 */
	public Textures textures;

	/**
	 * Menu
	 */
	public Menu menu;
	
	/**
	 * Holds all fonts
	 */
	public Fonts fonts;
	
	/**
	 * Reels
	 */
	public Reels reels;
	
	/**
	 * Overlay
	 */
	public Overlay overlay;
	
	/**
	 * Background
	 */
	public Background background;
	
	/**
	 * Controller
	 */
	public Controller controller;
	
	/**
	 * Context
	 */
	public Context context;
	
	/**
	 * Math
	 */
	public Math math;
	
	/**
	 * Sounds
	 */
	public Sounds sounds;
	
	/**
	 * Meters
	 */
	public Meters meters;
	
	
	public MainScreen mainScreen;

	public LockedScreen lockedScreen;
	
	/**
	 * IFullScreen cross-platform implementation
	 */
	public IFullScreen fullscreen = null;
	
	/**
	 * GS interface
	 */
	public IGameServerLink gsLink;
	
	public int browser = 1;
	
	
	public GameAssetsManager gameAssetsManager;
	
	public HelpScreen helpScreen;
	
	
    public int fps = 0;
    
//    public Jackpot jackpot;
    
//    public BigAnimation bigAnim;

//    public Stars stars;
    
	public boolean login = false;
	
	public boolean restore = false;
	
	public boolean showButtons = false;
	
    public int JACKPOT_MIN_HIT_FRAME = 60;
    public int JACKPOT_ANIM_LENGHT= 80;
    
    public int JACKPOT_DIGITS_DELAY= 60; // int credits (0.60)
    
	public SpineTextures spine;
	public SpineTexturesBG spineBG;
	public SpineTexturesButton spineButton;
	

	public AutoPlay autoPlayMenu;
	public MenuBet betMenu;
	public BuyBonusMenu buyBonusMenu;
	
	public boolean spinIsEnded= false;
	
	public GameTexts gameTxt;
	
	public String languageCode = "ENG"; // 0 - en
//	public String languageCode = "BG";  // 1 - bg
//	public String languageCode = "ESP"; // 2 - esp
//	public String languageCode = "POR"; // 3 - por
//	public String languageCode = "FRA"; // 4 - fra
//	public String languageCode = "TUR"; // 5 - TUR
//	public String languageCode = "PL";  // 6 - PL
//	public String languageCode = "CZE";  // 7 - CZE
//	public String languageCode = "RUS";  // 8 - RUS
//	public String languageCode = "ENG_SC";  // 8 - RUS
//	public String languageCode = "HR"; // 10 - HR
//	public String languageCode = "GR"; // 11 - gr
//	public String languageCode = "DE"; // 12 - de
	
	public Image titleLabel;
	
	public int hasBigNumberK = 0;
	public String pattern 	 = "#,###.00";
	
	public SpineTexturesLoading spineLoading;
	public SpineTexturesIntro 	spineIntro;
	
	public Label roundId;
	
	public SpineTexturesFireAnimWin   	  spineFireAnimWin;
	public SpineTexturesFireAnimLoop      spineFireAnimLoop;
	public SpineTexturesLoop     		  spineHoldAndWinTxt;
	public SpineTexturesLoop  	  		  spineFreeGamesTxt;
	public SpineTexturesBookAnimation 	  spineBookAnimation;
	public SpineTexturesFreeGamesCongrats spineFreeGamesCongrats;

	public BookOfKnight(OndemandAssetManager ondemandAssetManager, String lang) {
		super();
		
		this.ondemandAssetManager = ondemandAssetManager;
	    this.manager			  = ondemandAssetManager.getAssetManager();
	    
	    manager.setLoader(File.class, new MyAssetLoader(new InternalFileHandleResolver()));
	    
	    gameAssetsManager = new GameAssetsManager(this);
	    
		meters = new Meters(this);
		
		reels = new Reels(this);
		
		gsLink = new DefaultGameServerLink(this);
		
		languageCode = lang;
		
	}
	
	@Override
	public void create () {
		
		/********************************************
		 * LOG LEVEL SET TO DEBUG
		 * NOTE: Set to NONE for production
		 *******************************************/
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
//		Gdx.app.setLogLevel(Application.LOG_INFO);
//		Gdx.app.setLogLevel(Application.LOG_NONE);
		
		if (Gdx.app.getType() == ApplicationType.WebGL) {
			Gdx.app.setLogLevel(Application.LOG_NONE);
		}
		
//		Gdx.app.debug("FruityMania", "demo mode enabled, inserting " + meters.credit + " credits");
//		manager = new AssetManager();
		math = new Math();
		
		gameTxt = new GameTexts(this);
//		meters = new Meters();
		context = Context.getInstance();		
		textures = new Textures(this);
		fonts = new Fonts(this);
		background = new Background(this);
		menu = new Menu(this);
//		reels = new Reels(this);
		overlay = new Overlay(this);
		messages = new Error(this);
		controller = new Controller(this);
		sounds = new Sounds(this);
		
//		stars = new Stars(this);
		
		helpScreen = new HelpScreen(this);
		
	    reels.addListener(controller);
	    controller.addListener(menu);
	    
	    mainScreen = new MainScreen(this);

	    lockedScreen = new LockedScreen(this);
	    
	    autoPlayMenu = new AutoPlay(this);
	    betMenu 	 = new MenuBet(this);

	    buyBonusMenu = new BuyBonusMenu(this);
	    buyBonusMenu.setVisible(true);
	    
//	    jackpot = new Jackpot(this);
	    spine = new SpineTextures(this);

	    spineFireAnimWin	   = new SpineTexturesFireAnimWin(this);
	    
	    spineFireAnimLoop	   = new SpineTexturesFireAnimLoop(this);
	    
	    spineHoldAndWinTxt     = new SpineTexturesLoop(this, "spine/1920_hold_and_win_v1", false);

	    spineFreeGamesTxt  	   = new SpineTexturesLoop(this, "spine/1920_free_games_v1", false);

	    spineBookAnimation 	   = new SpineTexturesBookAnimation(this);

	    spineFreeGamesCongrats = new SpineTexturesFreeGamesCongrats(this);
	    
	    spineBG = new SpineTexturesBG(this);

        spineLoading = new SpineTexturesLoading(this);

        spineIntro = new SpineTexturesIntro(this);

	    spineButton = new SpineTexturesButton(this);
	    
	    
	    setScreen(new LoadingScreen(this));
    	
	    
	    meters.update();
	    meters.updatePrev();

    	if(DEMO_MODE && (Gdx.app.getType() == ApplicationType.Desktop ||  Gdx.app.getType() == ApplicationType.Android)) {
    		meters.credit = 100000;
//    		meters.credit = 0;
    	} else {
    		meters.credit = 0;
    	}
	}
	
	public void setFullscreen() {
		if(fullscreen!=null) {
			fullscreen.setFullscreen();
			Gdx.app.debug("TropicSmiley", "setFullscreen()");
		}
	}

}
