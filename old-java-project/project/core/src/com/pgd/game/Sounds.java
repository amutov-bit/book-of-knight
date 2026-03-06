package com.pgd.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	
	private final static float BACKGROUND_VOLUME = 1f;
	private final static float SOUND_VOLUME = 1f;
	
	public BookOfKnight game;
	
	private float playTimeout;
	private boolean bgPlaying;
	private float backgroundVolumeFade;
	
	private Music background;
	
	private boolean isSoundOn = true;

	private boolean downloadSpin;
	
	public boolean downloadAllSounds;
	
	private boolean restoreSound;

	private int soundState = 0;

	private float volume = 1f;
	
	public enum SoundTrack {
		COINUP("sounds/coinup.mp3"),
		COINEND("sounds/coinupend.mp3"),
		REEL_STOP("sounds/stop.mp3"),
		KNOCK("sounds/knock.mp3"),

		INTRO("sounds/intro.mp3"),

//		WIN("sounds/frame.mp3"),
//		DRAGON("sounds/dragon.mp3"),
		SCATTER("sounds/scatter.mp3"),
//		WILD("sounds/wild.mp3"),

		LOW_WIN("sounds/lowWin.mp3"),
		HIGH_WIN("sounds/highWin.mp3"),
		
		SHAKE_EGG("sounds/egg.mp3"),
		FIRE_LOOP("sounds/fire_loop.mp3"),
		FIRE_EXPLOSION("sounds/fire_explosion.mp3"),
		
		CONGRATS("sounds/congrats.mp3"),
		FREE_GAMES("sounds/free_games.mp3"),
		HOLD_AND_WIN("sounds/holdandwin.mp3"),
		OPEN_BOOK("sounds/open_book.mp3"),
//		FRAME("sounds/frame.mp3"),

		ANTICIPATION("sounds/anticipation.mp3"),
//		BEEP_WILD("sounds/beep_wild.mp3"),
		BEEP_SUN("sounds/beep_sun.mp3"),
		BEEP_STAR("sounds/beep_star.mp3"),
		
		BIG_WIN("sounds/big_win.mp3");

		private int value;
		private boolean isPlaying;
		private String fileName;

		private SoundTrack(String fileName) {
			this.fileName = fileName;
			this.value = 0;
			this.isPlaying = false;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
		
		public String getFileName() {
			return fileName;
		}		
	}
	
	private List<Sound> playList = new ArrayList<Sound>();
	
	/**
	 * Constructor
	 * @param game
	 */
	public Sounds(BookOfKnight game) {
		this.game = game;
		this.bgPlaying = false;
		this.isSoundOn = true;
		backgroundVolumeFade = 0.0f;
		downloadSpin = false;
		downloadAllSounds = false;
	}
	
	/**
	 * Load music assets
	 */
	public void loadAssets() {
		for(SoundTrack l : SoundTrack.values()) {
			game.ondemandAssetManager.load(l.getFileName(), Sound.class);
		}	
		
//		restoreSound = false;
	}
	public void loadAssetsSpin() {
		game.ondemandAssetManager.load("sounds/spin.mp3", Music.class);
	}
	/**
	 * Commit music assets
	 */
	public void commitAssets() {
		int i = 0;
		Sound sound;
		for(SoundTrack l : SoundTrack.values()) {
			l.setValue(i);
			sound = game.manager.get(l.getFileName(), Sound.class);
			playList.add(sound);
			i++;
		}
		if(Gdx.app.getType() != ApplicationType.Desktop){			
			downloadAllSounds = true;
		}
		
	}
	
	public void commitAssetsSpin() {
		background = game.manager.get("sounds/spin.mp3", Music.class);
		
		if(Gdx.app.getType() != ApplicationType.Desktop){
			downloadSpin = true;
		}
	}
	/**
	 * Play music
	 * @param item
	 * @param looping
	 */
	public void play(SoundTrack item, boolean looping) {
		
		if(isSoundOn && downloadAllSounds)
		{
			if(looping)
				playList.get(item.getValue()).loop(SOUND_VOLUME * volume);
			else
				playList.get(item.getValue()).play(SOUND_VOLUME * volume);					
			
			item.isPlaying = true;
		}
	}

	public void play(SoundTrack item, boolean looping, float sound_volume) {
		
		if(isSoundOn && downloadAllSounds)
		{
			if(looping)
				playList.get(item.getValue()).loop(sound_volume * volume);
			else
				playList.get(item.getValue()).play(sound_volume * volume);					
			
			item.isPlaying = true;
		}
	}
	
//	/**
//	 * Play music
//	 * @param item
//	 * @param looping
//	 */
//	public void play(SoundTrack item, boolean looping, float volume) {
//		
////		SOUND_VOLUME = 0.40f;
////		volume = 0.20f;
//				
//		if(isSoundOn && downloadAllSounds)
//		{
//			if(looping)
//				playList.get(item.getValue()).loop(volume);
//			else
//				playList.get(item.getValue()).play(volume);					
//			
//			item.isPlaying = true;
//		}
//	}
	
	/**
	 * Play music
	 * @param item
	 * @param looping
	 */
	public void playBackground(boolean looping) {

		if(background == null) return;
		
		if(isSoundOn && downloadSpin)
		{
			if(/*background.isPlaying()*/ bgPlaying) {
				// background already playing
			}
			else {
				background.play();
				background.setLooping(looping);			
			}
		
			// Reset timeout and volume
			playTimeout = 55.5f;// 3.35f; // 2.85f last
			bgPlaying = true;
			backgroundVolumeFade = 0.0f;
			background.setVolume(BACKGROUND_VOLUME * volume);
		}
//		playTimeout = 2.85f; 
//		signalBgPlay = true;
	}	
	
	/**
	 * Stop background sound track
	 */
	public void stopBackground() {
		if(background == null) return;
		background.stop();
		bgPlaying = false;
	}
	
	public void setBackgroundPlayTimeout(float timeout) {
		playTimeout = timeout; 
	}
	
	/**
	 * Stop music
	 * @param item
	 */
	public void stop(SoundTrack item) {
		if(downloadAllSounds)
		{
			playList.get(item.getValue()).stop();
			item.isPlaying = false;
		}
	}
	
	/**
	 * This function will stop currently playing sound 
	 * and start playing next
	 * @param item
	 * @param next
	 */
	public void stopAndPlayNext(SoundTrack item, SoundTrack next) {
		if (isSoundOn && downloadAllSounds) {
			playList.get(item.getValue()).stop();			
			item.isPlaying = false;
			playList.get(next.getValue()).play();
			next.isPlaying = true;
		}
	}
	
	/**
	 * 
	 */
	public boolean isPlaying(SoundTrack item) {
		return item.isPlaying;
	}
	
	/**
	 * Update
	 */
	public void update(float delta) {

		
		if(playTimeout > 0) {
			playTimeout -= delta;
		}
		else if(bgPlaying) {}
		
		if(!isSoundOn && downloadSpin){
			background.setVolume(0.0f);
			background.pause();
		}
		
//		if(background == null) return;
//		
//		float volume = background.getVolume();
//		switch(backgroundState) {
//			case FADEIN: 
//				if(volume < BACKGROUND_VOLUME) {
//					background.setVolume(volume + BACKGROUND_VOLUME/5);
//				}
//				else {
//					backgroundState = BackgroundState.PLAYING;
//				}
//				break;
//			case FADEOUT: 
//				if(volume > 0) {
//					background.setVolume(volume - 0.01f);
//				}
//				else {
//					backgroundState = BackgroundState.STOPPED;
//					background.setVolume(0.0f);
//					background.pause();
//				}
//				break;
//			case PLAYING: 
//				if(playTimeout > 0) {
//					playTimeout -= delta;
//				}
//				else {
//					backgroundState = BackgroundState.FADEOUT;
//				}
//				break;
//			case STOPPED: 
//				if(signalBgPlay) {
//					signalBgPlay = false;
//					backgroundState = BackgroundState.FADEIN;
//					background.setVolume(0.0f);
//					background.play();
//					background.setLooping(true);
//				}
//				break;
//			default: break;
//		}
		
	}
	
	public void resume(){
		if(!restoreSound && downloadAllSounds)
		{
			//hack for audioContext for Chrome
			//modify libgdx lib
			playList.get(SoundTrack.KNOCK.getValue()).resume();
			restoreSound = true;
		}
	}
	
	/**
	 * Stop all sounds
	 */
	public void stopAll() {
		if(downloadAllSounds){
			for(SoundTrack l : SoundTrack.values()) {
				playList.get(l.getValue()).stop();
				l.isPlaying = false;
			}		
		}
	}
	
	/**
	 * Free memory
	 */
	public void dispose() {
		for( int i = 0; i < playList.size(); i++) {
			playList.get(i).dispose();
		}
		background.dispose();
	}
	
	/**
	 * Stop all sounds
	 */
	public void setSound(boolean sound) {
		isSoundOn = sound;
		if(!isSoundOn && downloadSpin)	stopBackground();
	}
	
	/**
	 * Stop all sounds
	 */
	public void setSoundIntro(int sound, boolean active) {
		
		soundState = sound;
		
		switch(sound){
		case 0:
			downloadSpin 	  = active;
			downloadAllSounds = active;
			break;
		case 1:
			downloadAllSounds = active;
			break;
		case 2:
			downloadSpin 	  = active;
			if(active){
				playBackground(active);
			} else {
				stopBackground();
			}
			break;
		}
		
	}
	
	/**
	 * Stop all sounds
	 */
	public void setSound(int sound, boolean active) {
		
		soundState = sound;
		
		switch(sound){
			case 0:
				downloadSpin 	  = active;
				downloadAllSounds = active;
				
				if(active){
					playBackground(active);
				} else {
					stopBackground();
				}
			break;
			case 1:
				downloadAllSounds = active;
			break;
			case 2:
				downloadSpin 	  = active;
				if(active){
					playBackground(active);
				} else {
					stopBackground();
				}
			break;
		}
		
	}
	
	public void setVolume(int vol){
		volume = vol / 100f;
		
		Gdx.app.debug("Sound", "volume = " + volume);
		
		background.setVolume(BACKGROUND_VOLUME * volume);
	}
	
	public int getSoundState(){
		return soundState;
	}
	
}
