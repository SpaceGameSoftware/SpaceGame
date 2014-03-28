package com.spacegamesoftware;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.spacegamesoftware.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener {
	
	private HUD gameHUD;
	private Text scoreText;
	private Text coinsText;
	private Text distanceText;
	private Spaceship spaceship;
	private SpaceGameLogic logic;

	@Override
	public void createScene() {
		logic = new SpaceGameLogic(this);
		registerUpdateHandler(logic);
		createBackground();
		createHUD();
		logic.setScoreText(scoreText);
		logic.setCoinsText(coinsText);
		logic.setDistanceText(distanceText);
		createSpaceship();
		logic.setSpaceship(spaceship);
		setOnSceneTouchListener(spaceship);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}
	
	private void createSpaceship() {
		int x = MainActivity.CAMERA_WIDTH / 2;
		int y = MainActivity.CAMERA_HEIGHT - MainActivity.CAMERA_HEIGHT / 4;
		spaceship = new Spaceship(x, y, resourceManager.spaceshipRegion, vbom);
		attachChild(spaceship);
	}
	
	private void createHUD() {
		gameHUD = new HUD();
		
		scoreText = new Text(20, 20, resourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		coinsText = new Text(20, 80, resourceManager.font, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		distanceText = new Text(20, 140, resourceManager.font, "Distance: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setSkewCenter(0, 0);
		coinsText.setSkewCenter(0, 0);
		distanceText.setSkewCenter(0, 0);
		scoreText.setText("Score: 0");
		coinsText.setText("Coins: 0");
		distanceText.setText("Distance: 0");
		gameHUD.attachChild(scoreText);
		gameHUD.attachChild(coinsText);
		gameHUD.attachChild(distanceText);
		
		camera.setHUD(gameHUD);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent touchEvent) {
		
		float x = touchEvent.getX();
		float y = touchEvent.getY();
		
		if(touchEvent.isActionDown()) {
			return true;
		}
		else if(touchEvent.isActionUp()) {
			return true;
		}
		else if(touchEvent.isActionMove()) {
			spaceship.setPosition(x,spaceship.getY());
			return true;
		}
		
		return false;
	}

}
