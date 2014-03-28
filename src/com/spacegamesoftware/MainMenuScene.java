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

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene menuChildScene;
	private Sprite menuBackground;
	private final int MENU_NEWGAME = 0;
	private final int MENU_PERKS = 1;
	private final int MENU_ACHIEVMENTS = 2;
	private final int MENU_HIGHSCORE = 3;
	private final int MENU_SETTINGS = 4;
	
	@Override
	public void createScene() {
		createBackground();
		createMenuChildScene();
		resourceManager.music.play();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground() {
		menuBackground = new Sprite(0, 0, resourceManager.menuBackgroundRegion, vbom) {
			
			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}
			
		};
		
		menuBackground.setScale(14.0f);
		
		attachChild(menuBackground);
	}
	
	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);
		
		final IMenuItem newGameMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_NEWGAME, resourceManager.newGameRegion, vbom), 0.7f, 0.75f);
		final IMenuItem perksMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PERKS, resourceManager.perksButtonRegion, vbom), 0.7f, 0.75f);
		final IMenuItem achievementsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_ACHIEVMENTS, resourceManager.achievementsButtonRegion, vbom),
				0.7f, 0.75f);
		final IMenuItem highscoreMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HIGHSCORE, resourceManager.highscoreButtonRegion, vbom), 0.7f, 0.75f);
		final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SETTINGS, resourceManager.settingsButtonRegion, vbom), 0.7f, 0.75f);
		
		menuChildScene.addMenuItem(newGameMenuItem);
		menuChildScene.addMenuItem(perksMenuItem);
		menuChildScene.addMenuItem(achievementsMenuItem);
		menuChildScene.addMenuItem(highscoreMenuItem);
		menuChildScene.addMenuItem(settingsMenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		float x = 80;
		
		newGameMenuItem.setPosition(x, newGameMenuItem.getY());
		perksMenuItem.setPosition(x, perksMenuItem.getY());
		achievementsMenuItem.setPosition(x, achievementsMenuItem.getY());
		highscoreMenuItem.setPosition(x, highscoreMenuItem.getY());
		settingsMenuItem.setPosition(x, settingsMenuItem.getY());
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		switch (menuItem.getID()) {
		case MENU_NEWGAME:
			SceneManager.getInstance().createGameScene();
			return true;
		case MENU_PERKS:
			return true;
		case MENU_ACHIEVMENTS:
			return true;
		case MENU_HIGHSCORE:
			return true;
		case MENU_SETTINGS:
			return true;
		default:
			return false;
		}
	}

}
