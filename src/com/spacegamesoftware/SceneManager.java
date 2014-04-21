package com.spacegamesoftware;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;


public class SceneManager {
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;
	private BaseScene perkScene;
	private BaseScene achieveScene;
	
	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType currentSceneType = SceneType.SCENE_SPLASH;
	private BaseScene currentScene;
	private Engine engine = ResourceManager.getInstance().engine;
	
	public enum SceneType {
		SCENE_SPLASH,
		SCENE_MENU,
		SCENE_GAME,
		SCENE_LOADING,
		SCENE_PERK,
		SCENE_ACHIEVE,
		SCENE_END_GAME
	}
	
	public void createSplashScene(OnCreateSceneCallback onCreateSceneCallback) {
		ResourceManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		onCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void createMenuScene() {
		ResourceManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		setScene(menuScene);
		disposeSplashScene();
	}
	
	public void createPerkScene() {
		ResourceManager.getInstance().loadPerkResources();
		perkScene = new PerkScene();
		setScene(perkScene);
	}
	
	public void createAchieveScene() {
		ResourceManager.getInstance().loadAchieveResources();
		achieveScene = new AchievementScene();
		setScene(achieveScene);
	}
	
	public void createGameScene() {
		ResourceManager.getInstance().loadGameResources();
		gameScene = new GameScene();
		setScene(gameScene);
	}
	
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_MENU:
			setScene(menuScene);
			break;
		case SCENE_PERK:
			setScene(perkScene);
			break;
		case SCENE_ACHIEVE:
			setScene(achieveScene);
			break;
		case SCENE_GAME:
			setScene(gameScene);
			break;
		case SCENE_SPLASH:
			setScene(splashScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		//case SCENE_END_GAME:
		//	setScene(endGameScene);
		//	break;
		default:
			break;
		}
	}
	
	public static SceneManager getInstance() {
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene() {
		return currentScene;
	}
	
	public BaseScene getMenuScene() {
		return menuScene;
	}
	
	private void disposeSplashScene()
	{
		ResourceManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
}
