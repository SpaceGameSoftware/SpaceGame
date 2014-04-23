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

public class EndGameScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene endGameChildScene;
	private Sprite endGameBackground;
	private final int MENU_RETRY = 0;
	private final int MENU_MAIN  = 1;
	private final int MENU_QUIT  = 2;

	@Override
	public void createScene() {
		createBackground();
		createEndGameChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		endGameChildScene.back();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_END_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}

	private void createBackground() {
		//change
		endGameBackground = new Sprite(0, 0, resourceManager.endGameBackgroundRegion, vbom) {

			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}

		};

		endGameBackground.setScale(1.0f);
		attachChild(endGameBackground);
	}

	private void createEndGameChildScene() {
		endGameChildScene = new MenuScene(camera);
		endGameChildScene.setPosition(0, 0);

		final IMenuItem retryMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_RETRY, resourceManager.retryButtonRegion, vbom), 0.7f, 0.75f);
		
		final IMenuItem mainMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_MAIN, resourceManager.menuButtonRegion, vbom), 0.7f, 0.75f);
		
		final IMenuItem quitMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_QUIT, resourceManager.quitButtonRegion, vbom), 0.7f, 0.75f);
		
		endGameChildScene.addMenuItem(retryMenuItem);
		endGameChildScene.addMenuItem(mainMenuItem);
		endGameChildScene.addMenuItem(quitMenuItem);
		
		endGameChildScene.buildAnimations();
		endGameChildScene.setBackgroundEnabled(false);

		float x = 80;
		
		retryMenuItem.setPosition(x, retryMenuItem.getY());
		mainMenuItem.setPosition(x, mainMenuItem.getY());
		quitMenuItem.setPosition(x, quitMenuItem.getY());
		
		endGameChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(endGameChildScene);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		switch (menuItem.getID()) {
		case MENU_RETRY:
			SceneManager.getInstance().createGameScene();
			return true;
		case MENU_MAIN:
			//endGameChildScene.back();
			onBackKeyPressed();
			return true;
		case MENU_QUIT:
			System.exit(0);
			return true;
		default:
			return false;	
		}
	}
}