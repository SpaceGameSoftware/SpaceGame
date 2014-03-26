package com.spacegamesoftware;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.spacegamesoftware.SceneManager.SceneType;

public class GameScene extends BaseScene {
	
	private HUD gameHUD;
	private Text scoreText;
	private Sprite spaceship;
	private SpaceGameLogic logic;

	@Override
	public void createScene() {
		logic = new SpaceGameLogic();
		registerUpdateHandler(logic);
		createBackground();
		createHUD();
		logic.setScoreText(scoreText);
		createSpaceship();
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
		spaceship = new Sprite(x, y, resourceManager.spaceshipRegion, vbom);
		attachChild(spaceship);
	}
	
	private void createHUD() {
		gameHUD = new HUD();
		
		scoreText = new Text(20, 20, resourceManager.font, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setSkewCenter(0, 0);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		
		camera.setHUD(gameHUD);
	}

}