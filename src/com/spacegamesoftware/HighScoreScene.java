package com.spacegamesoftware;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;

import com.spacegamesoftware.SceneManager.SceneType;

public class HighScoreScene extends BaseScene {
	
	private Sprite background;
	private HUD hud;
	private Text totalDistanceText;
	private Text highScoreText;
	private Text totalCoinsText;
	private Text totalScoreText;

	@Override
	public void createScene() {
		createBackground();
		createHud();
	}

	@Override
	public void onBackKeyPressed() {
		disposeScene();
		SceneManager.getInstance().setScene(SceneManager.SceneType.SCENE_MENU);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		totalDistanceText.detachSelf();
		totalDistanceText.dispose();
		highScoreText.detachSelf();
		highScoreText.dispose();
		totalCoinsText.detachSelf();
		totalCoinsText.dispose();
		hud.detachSelf();
		hud.dispose();
		totalScoreText.detachSelf();
		totalScoreText.dispose();
		this.detachSelf();
		this.dispose();
	}
	
	private void createBackground() {
		background = new Sprite(0, 0, resourceManager.menuBackgroundRegion, vbom) {
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
			
		};
		
		attachChild(background);
	}
	
	private void createHud() {
		int x = MainActivity.CAMERA_WIDTH / 3;
		int y = MainActivity.CAMERA_HEIGHT / 2;
		float scale = 2.0f;
		int offset = 150;
		hud = new HUD();
		highScoreText = new Text(x, y, resourceManager.font, "High Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		highScoreText.setSkewCenter(0, 0);
		highScoreText.setScale(scale);
		highScoreText.setText("High Score: " + getHighScore());
		
		totalScoreText = new Text(x, y + offset, resourceManager.font, "Cumulative Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		totalScoreText.setSkewCenter(0, 0);
		totalScoreText.setScale(scale);
		totalScoreText.setText("Cumulative Score: " + getCumulativeHighScore());
		
		totalCoinsText = new Text(x, y + 2 * offset, resourceManager.font, "Total Coins Collected: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		totalCoinsText.setSkewCenter(0, 0);
		totalCoinsText.setScale(scale);
		totalCoinsText.setText("Total Coins Collected: " + getCumulativeCoins());
		
		totalDistanceText = new Text(x, y + 3 * offset, resourceManager.font, "Total Distance: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		totalDistanceText.setSkewCenter(0, 0);
		totalDistanceText.setScale(scale);
		totalDistanceText.setText("Total Distance: " + getTotalDistance());
		
		hud.attachChild(highScoreText);
		hud.attachChild(totalScoreText);
		hud.attachChild(totalDistanceText);
		camera.setHUD(hud);
	}
	
	private int getTotalDistance() {
		PlayerData player = ResourceManager.getInstance().DBHelper.getPlayer();
		return player.getDistance();
	}
	
	private int getHighScore() {
		return ResourceManager.getInstance().DBHelper.getHighScore();
	}
	
	private int getCumulativeHighScore() {
		return ResourceManager.getInstance().DBHelper.getCumulativeScore();
	}

	private int getCumulativeCoins() {
		return ResourceManager.getInstance().DBHelper.getCumulativeCoins();
	}
}
