package com.spacegamesoftware;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;

import com.spacegamesoftware.SceneManager.SceneType;

public class AchievementScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene achieveChildScene;
	private Sprite achieveBackground;
	//private Text coinsText;
	//private final int PERK_COIN = 0;
	//private final int PERK_SPEED = 1;

	@Override
	public void createScene() {
		createBackground();
		createAchieveChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		this.disposeScene();
		SceneManager.getInstance().setScene(SceneManager.SceneType.SCENE_MENU);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_ACHIEVE;
	}

	@Override
	public void disposeScene() {
		achieveChildScene.detachChildren();
		achieveChildScene.dispose();
		this.detachSelf();
		this.dispose();
	}

	private void createBackground() {
		//change
		achieveBackground = new Sprite(0, 0, resourceManager.achieveBackgroundRegion, vbom) {

			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}

		};

		achieveBackground.setScale(1.0f);
		attachChild(achieveBackground);
	}

	private void createAchieveChildScene() {
		achieveChildScene = new MenuScene(camera);
		achieveChildScene.setPosition(0, 0);

		//coinsText = new Text(20, 20, resourceManager.font, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		//coinsText.setSkewCenter(0, 0);
		//coinsText.setText("Coins: 0");
		
		//final IMenuItem coinPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PERK_COIN, resourceManager.coinPerkRegion, vbom), 0.7f, 0.75f);
		//final IMenuItem speedPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PERK_SPEED, resourceManager.speedPerkRegion, vbom), 0.7f, 0.75f);

		//perkChildScene.addMenuItem(coinPerkMenuItem);
		//perkChildScene.addMenuItem(speedPerkMenuItem);


		achieveChildScene.buildAnimations();
		achieveChildScene.setBackgroundEnabled(false);

		//coinPerkMenuItem.setPosition(coinPerkMenuItem.getX(), coinPerkMenuItem.getY());
		//speedPerkMenuItem.setPosition(speedPerkMenuItem.getX(), speedPerkMenuItem.getY());

		//perkChildScene.setOnMenuItemClickListener(this);

		setChildScene(achieveChildScene);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		switch (menuItem.getID()) {
		/*
		case PERK_COIN:
			//Do something
			return true;
		case PERK_SPEED:
			//Do something
			return true;
			*/
		default:
			return false;	
		}
	}
}