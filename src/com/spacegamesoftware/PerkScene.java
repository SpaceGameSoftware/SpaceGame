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
	
	private MenuScene perkChildScene;
	private Sprite perkBackground;
	private HUD perkHUD;
	private int coins;
	private Text coinsText;
	private final int BUY_BUTTON = 0;
	private final int PERK_COIN = 1;
	private final int PERK_DISTANCE = 2;

	private PlayerData player;
	private int perkId;
	
	@Override
	public void createScene() {
		player = ResourceManager.getInstance().DBHelper.getPlayer();
		coins = player.getCoins();
		createBackground();
		createPerkChildScene();
		createHUD();
	}

	@Override
	public void onBackKeyPressed() {
		disposeScene();
		SceneManager.getInstance().setScene(SceneManager.SceneType.SCENE_MENU);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_PERK;
	}

	@Override
	public void disposeScene() {
		perkHUD.detachSelf();
		perkHUD.dispose();
		coinsText.detachSelf();
		coinsText.dispose();
		perkBackground.detachSelf();
		perkBackground.dispose();

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
		final IMenuItem speedPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(PERK_DISTANCE, resourceManager.speedPerkRegion, vbom), 0.7f, 0.75f);
		final IMenuItem buyPerkMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(BUY_BUTTON, resourceManager.buyPerkRegion, vbom), 0.7f, 0.75f);
		
		perkChildScene.addMenuItem(coinPerkMenuItem);
		perkChildScene.addMenuItem(speedPerkMenuItem);
		perkChildScene.addMenuItem(buyPerkMenuItem);
		
		perkChildScene.buildAnimations();
		perkChildScene.setBackgroundEnabled(false);

		float x = 40;
		coinPerkMenuItem.setPosition(x, coinPerkMenuItem.getY());
		speedPerkMenuItem.setPosition(x, speedPerkMenuItem.getY());
		buyPerkMenuItem.setPosition(x, buyPerkMenuItem.getY());

		perkChildScene.setOnMenuItemClickListener(this);
		setChildScene(perkChildScene);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		switch (menuItem.getID()) {
		//update perk id
		case BUY_BUTTON:
			buyPerk(perkId);
			return true;
		case PERK_COIN:
			perkId = 6;
			return true;
		case PERK_DISTANCE:
			perkId = 1;
			return true;
		default:
			return false;
		}
	}
	
	private void createHUD() {
		perkHUD = new HUD();
		coinsText = new Text(20, 80, resourceManager.font, "Coins: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		coinsText.setSkewCenter(0, 0);
		coinsText.setText( String.format("Coins: %d", coins) );
		perkHUD.attachChild(coinsText);
		camera.setHUD(perkHUD);
	}
	
	private void buyPerk(int perkId) {
		PlayerData player = ResourceManager.getInstance().DBHelper.getPlayer();

		if(!ResourceManager.getInstance().DBHelper.perkBought(perkId)) {
			PerkData perk = ResourceManager.getInstance().DBHelper.getPerk(perkId);
			if ( player.getCoins() >= perk.getValue() ) {
				int coinBalance = player.getCoins() - perk.getValue();
				//update coin total in player table
				ResourceManager.getInstance().DBHelper.updatePlayerTable(player.getId(), 
						coinBalance, player.getDistance(), player.getScore() );
				//update perk owned in PlayerPerkData
				//ResourceManager.getInstance().DBHelper.updatePlayerPerkTable(perkId, 1);
				ResourceManager.getInstance().DBHelper.insertPlayerPerkTable(perkId, 1);
				//implement perk in SpaceGameLogic/ResourceManager
				coinsText.setText( String.format("Coins: %d", coinBalance) );
			}
		}
	}
}