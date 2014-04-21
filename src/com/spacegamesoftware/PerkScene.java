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

public class PerkScene extends BaseScene implements IOnMenuItemClickListener {
	private BaseScene mainMenuScene;
	private MenuScene perkChildScene;
	private Sprite perkBackground;
	private HUD perkHUD;
	private Text coinsText;
	private final int PERK_COIN = 0;
	private final int PERK_SPEED = 1;

	@Override
	public void createScene() {
		createBackground();
		createPerkChildScene();
		createHUD();
	}

	@Override
	public void onBackKeyPressed() {
		disposeScene();
		//doesn't work
		mainMenuScene = SceneManager.getInstance().getMenuScene();
		SceneManager.getInstance().setScene(mainMenuScene);	
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
		perkBackground = new Sprite(0, 0, resourceManager.perkBackgroundRegion, vbom) {

			@Override
			protected void preDraw(GLState glState, Camera camera) {
				super.preDraw(glState, camera);
				glState.enableDither();
			}

		};

		perkBackground.setScale(1.0f);

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
			//Buy logic
			//coinsText.setText("Coins: 10");
			return true;
		case PERK_SPEED:
			//Buy logic
			//coinsText.setText("Coins: 20");
			return true;
		default:
			return false;
		}
	}
	
	private void createHUD() {
		String initInput;
		perkHUD = new HUD();
		coinsText = new Text(20, 80, resourceManager.font, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		coinsText.setSkewCenter(0, 0);
		initInput = String.format("Coins: %d", ResourceManager.getInstance().getCoins());
		coinsText.setText(initInput);
		perkHUD.attachChild(coinsText);
		camera.setHUD(perkHUD);
	}
	

}