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
		hud.detachSelf();
		hud.dispose();
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
		hud = new HUD();
		totalDistanceText = new Text(x, y, resourceManager.font, "Total Distance: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		totalDistanceText.setSkewCenter(0, 0);
		totalDistanceText.setScale(scale);
		totalDistanceText.setText("Total Distance: " + getTotalDistance());
		hud.attachChild(totalDistanceText);
		camera.setHUD(hud);
	}
	
	private int getTotalDistance() {
		return ResourceManager.getInstance().player.getDistance();
	}

}
