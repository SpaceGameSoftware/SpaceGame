package com.spacegamesoftware;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.spacegamesoftware.SceneManager.SceneType;

public class PerkScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene perkChildScene;
	private Sprite perkBackground;
	private final int PERK_COIN = 0;
	private final int PERK_SPEED = 1;
	//private final int MENU_ACHIEVMENTS = 2;
	//private final int MENU_HIGHSCORE = 3;
	//private final int MENU_SETTINGS = 4;

	@Override
	public void createScene() {
		createBackground();
		createPerkChildScene();
		//resourceManager.music.play();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_PERK;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

	private void createBackground() {
		//change
		perkBackground = new Sprite(0, 0, resourceManager.perkBackgroundRegion, vbom) {

			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}

		};

		perkBackground.setScale(14.0f);

		attachChild(perkBackground);
	}

	private void createPerkChildScene() {
		perkChildScene = new MenuScene(camera);
		perkChildScene.setPosition(0, 0);

		final IMenuItem coinPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PERK_COIN, resourceManager.coinPerkRegion, vbom), 0.7f, 0.75f);
		final IMenuItem speedPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PERK_SPEED, resourceManager.speedPerkRegion, vbom), 0.7f, 0.75f);

		perkChildScene.addMenuItem(coinPerkMenuItem);
		perkChildScene.addMenuItem(speedPerkMenuItem);


		perkChildScene.buildAnimations();
		perkChildScene.setBackgroundEnabled(false);

		coinPerkMenuItem.setPosition(coinPerkMenuItem.getX(), coinPerkMenuItem.getY());
		speedPerkMenuItem.setPosition(speedPerkMenuItem.getX(), speedPerkMenuItem.getY());

		perkChildScene.setOnMenuItemClickListener(this);

		setChildScene(perkChildScene);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		switch (menuItem.getID()) {
		case PERK_COIN:
			//SceneManager.getInstance().createGameScene();
			return true;
		case PERK_SPEED:
			//Do something
			return true;
			/*
		case MENU_ACHIEVMENTS:
			return true;
		case MENU_HIGHSCORE:
			return true;
		case MENU_SETTINGS:
			return true;
			*/
		default:
			return false;
		}
	}

}