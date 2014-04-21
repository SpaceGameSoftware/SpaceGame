package com.spacegamesoftware;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.database.SQLException;
import android.graphics.Color;
import android.widget.Toast;

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public ITextureRegion splashRegion;
	//main menu
	public ITextureRegion menuBackgroundRegion;
	public ITextureRegion newGameRegion;
	public ITextureRegion perksButtonRegion;
	public ITextureRegion achievementsButtonRegion;
	public ITextureRegion highscoreButtonRegion;
	public ITextureRegion settingsButtonRegion;
	//perk menu
	public ITextureRegion perkBackgroundRegion;
	public ITextureRegion coinPerkRegion;
	public ITextureRegion speedPerkRegion;
	//achievement menu
	public ITextureRegion achieveBackgroundRegion;
	//end game menu
	
	//other
	public ITextureRegion spaceshipRegion;
	public ITextureRegion[] asteroidRegion;
	public ITextureRegion asteroidRegion2;
	public ITextureRegion asteroidRegion3;
	public ITextureRegion asteroidRegion4;
	public ITextureRegion coinRegion;
	
	public Music music;
	
	public Font font;
	
	private BitmapTextureAtlas splashTextureAtlas;
	private BitmapTextureAtlas menuBackgroundTextureAtlas;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	private BuildableBitmapTextureAtlas perkTextureAtlas;
	private BuildableBitmapTextureAtlas achieveTextureAtlas;
	private BuildableBitmapTextureAtlas gameTextureAtlas;
	
	//database variables
	//public DataBaseHelper DBHelper;
	public PlayerDataSource PlayerDataSource;
	public PlayerData player;
	//player info
	int coins;
	int distance;
	int score;
	
	public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		getInstance().PlayerDataSource = new PlayerDataSource(activity);
		
		try {
			//getInstance().PlayerDataSource.open();
			//get info from DB
			getInstance().player = getInstance().PlayerDataSource.getPlayer();
			getInstance().coins = getInstance().player.getCoins();
			getInstance().distance = getInstance().player.getDistance();
			getInstance().score = getInstance().player.getScore();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuFonts();
		loadMenuAudio();
	}
	
	public void loadPerkResources() {
		loadPerkGraphics();
		loadPerkFonts();
		loadPerkAudio();
	}
	
	public void loadAchieveResources() {
		loadAchieveGraphics();
		loadAchieveFonts();
		loadAchieveAudio();
	}
	
	public void loadGameResources() {
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}
	
	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity, "splash_screen.png", 0, 0);
		splashTextureAtlas.load();
	}
	
	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splashRegion = null;
	}
	
	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		menuBackgroundTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 2048, TextureOptions.BILINEAR);
		menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundTextureAtlas, 
				activity, "menu_background.png", 0, 0);
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		//menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		newGameRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "new_game.png");
		perksButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "perks.png");
		achievementsButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "achievements.png");
		highscoreButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "high_scores.png");
		settingsButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "settings.png");
		
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
			this.menuBackgroundTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	
	private void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "font.ttf", 50.0f, true, Color.WHITE, 2.0f, Color.BLACK);
		font.load();
	}
	
	private void loadMenuAudio() {
		MusicFactory.setAssetBasePath("mfx/");
		
		try {
			music = MusicFactory.createMusicFromAsset(engine.getMusicManager(), activity, "MaxNRG-Prometheus.ogg");
			music.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}
	
	private void loadPerkGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/perk/");
		perkTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		perkBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perkTextureAtlas, activity, "perk_background.png");
		coinPerkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perkTextureAtlas, activity, "coin.png");
		speedPerkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perkTextureAtlas, activity, "speed.png");

		try {
			this.perkTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.perkTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadPerkFonts() {

	}

	private void loadPerkAudio() {

	}
	
	private void loadAchieveGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/achieve/");
		achieveTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		achieveBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(achieveTextureAtlas, activity, "achieve_background.png");
		//coinPerkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perkTextureAtlas, activity, "coin.png");
		//speedPerkRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(perkTextureAtlas, activity, "speed.png");

		try {
			this.achieveTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.achieveTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadAchieveFonts() {

	}

	private void loadAchieveAudio() {

	}
	
	private void loadGameGraphics() {
		asteroidRegion = new ITextureRegion[4];
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		spaceshipRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "spaceship.png");
		asteroidRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "asteroid.png");
		asteroidRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "asteroid2.png");
		asteroidRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "asteroid3.png");
		asteroidRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "asteroid4.png");
		coinRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity, "coin.png");
		
		try {
			this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	
	private void loadGameFonts() {
	}
	
	private void loadGameAudio() {
		
	}
	
	public int getCoins() {
		return coins;
	}
	
}
