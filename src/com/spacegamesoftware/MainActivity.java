package com.spacegamesoftware;

import java.io.IOException;

import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;


public class MainActivity extends BaseGameActivity {
	
	private static int CAMERA_WIDTH = 1080;
	private static int CAMERA_HEIGHT = 1920;
	
	private ResourceManager resourceManager;
	private Camera camera;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions options) {
		return new LimitedFPSEngine(options, 60);
	}
	

	@Override
	public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback) throws IOException {
		ResourceManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		resourceManager = ResourceManager.getInstance();
		onCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws IOException {
		SceneManager.getInstance().createSplashScene(onCreateSceneCallback);
	}
	
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback) throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			
			public void onTimePassed(final TimerHandler timerHandler) {
				mEngine.unregisterUpdateHandler(timerHandler);
				SceneManager.getInstance().createMenuScene();
			}
			
		}));
		onPopulateSceneCallback.onPopulateSceneFinished();
	}
	

}
