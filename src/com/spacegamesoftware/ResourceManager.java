package com.spacegamesoftware;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
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

public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public ITextureRegion splashRegion;
	public ITextureRegion menuBackgroundRegion;
	public ITextureRegion newGameRegion;
	public ITextureRegion perksButtonRegion;
	public ITextureRegion achievementsButtonRegion;
	public ITextureRegion highscoreButtonRegion;
	public ITextureRegion settingsButtonRegion;
	
	public Music music;
	
	private BitmapTextureAtlas splashTextureAtlas;
	private BuildableBitmapTextureAtlas menuTextureAtlas;
	
	public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
		
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuAudio();
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
		menuTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR);
		menuBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "menu_background.png");
		newGameRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "new_game.png");
		perksButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "perks.png");
		achievementsButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "achievements.png");
		highscoreButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "high_scores.png");
		settingsButtonRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas, activity, "settings.png");
		
		try {
			this.menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			this.menuTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
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
	
	private void loadGameGraphics() {
		
	}
	
	private void loadGameFonts() {
		
	}
	
	private void loadGameAudio() {
		
	}
	
	

}
