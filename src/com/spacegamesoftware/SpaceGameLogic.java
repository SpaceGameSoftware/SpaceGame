package com.spacegamesoftware;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class SpaceGameLogic implements IUpdateHandler {
	
	private int score;
	private int distance;
	private int coins;
	private Text scoreText;
	private Text coinsText;
	private Text distanceText;
	private Sprite spaceship;
	private List<Asteroid> asteroids;
	private List<Coin> coinList;
	private BaseScene scene;
	private Random rand;
	
	public SpaceGameLogic(BaseScene scene) {
		this.scene = scene;
		score = 0;
		distance = 0;
		coins = 0;
		asteroids = new ArrayList<Asteroid>();
		coinList = new ArrayList<Coin>();
		rand = new Random();
	}
	
	private float timeSinceDistance = 0;

	@Override
	public void onUpdate(float secondsElapsed) {
		timeSinceDistance += secondsElapsed;
		updateScore();
		updateDistance();
		updateAsteroids();
		updateCoins();
	}

	@Override
	public void reset() {
		
	}
	
	
	public void setScoreText(Text text) {
		scoreText = text;
	}
	
	public void setCoinsText(Text text) {
		coinsText = text;
	}
	
	public void setDistanceText(Text text) {
		distanceText = text;
	}
	
	public void setSpaceship(Sprite spaceship) {
		this.spaceship = spaceship;
	}
	
	
	private void updateDistance() {
		if (timeSinceDistance > 1.0 / 6.0) {
			distance += 1;
			distanceText.setText("Distance: " + distance);
			timeSinceDistance = 0;
		}
	}
	
	private void updateScore() {
		score = distance + 5 * coins;
		scoreText.setText("Score: " + score);
	}
	
	private void updateAsteroids() {
		if (rand.nextInt(100) < 4) {
			asteroids.add(createAsteroid());
		}
		
		Iterator<Asteroid> iter = asteroids.iterator();
		while (iter.hasNext()) {
			Asteroid asteroid = iter.next();
			
			if (asteroid.getY() > MainActivity.CAMERA_HEIGHT){
				scene.detachChild(asteroid); // remove asteroids that are no longer visible
				iter.remove();
			} else {
				if (spaceship.collidesWith(asteroid)) {
					onSpaceshipHit();
				}
				asteroid.update();
			}
		}
	}
	
	private Asteroid createAsteroid() {
		int asteroidId = rand.nextInt(4);
		float x = (float) rand.nextInt(MainActivity.CAMERA_WIDTH);
		Asteroid asteroid = new Asteroid(x, 0.0f, ResourceManager.getInstance().asteroidRegion[asteroidId],
				ResourceManager.getInstance().vbom);
		float dy = (rand.nextFloat() * 6) + 2.0f;
		asteroid.setVelocity(new Vector(0.0f, dy));
		scene.attachChild(asteroid);
		return asteroid;
	}
	
	private void updateCoins() {
		if (rand.nextInt(100) < 3) {
			coinList.add(createCoin());
		}
		
		Iterator<Coin> iter = coinList.iterator();
		while (iter.hasNext()) {
			Coin coin = iter.next();
			
			if (coin.getY() > MainActivity.CAMERA_HEIGHT) {
				scene.detachChild(coin);
				iter.remove();
			} else {
				if (spaceship.collidesWith(coin)) {
					coins += coin.getValue();
					coinsText.setText("Coins: " + coins);
					scene.detachChild(coin);
					iter.remove();
				} else {
					coin.update();
				}
			}
		}
	}
	
	private Coin createCoin() {
		float x = (float) rand.nextInt(MainActivity.CAMERA_WIDTH);
		Coin coin = new Coin(x, 0.0f, ResourceManager.getInstance().coinRegion,ResourceManager.getInstance().vbom);
		coin.setVelocity(new Vector(0.0f, 6.5f));
		coin.setValue(1);
		scene.attachChild(coin);
		return coin;
	}
	
	private void onSpaceshipHit() {
		onGameOver();
	}
	
	private void onGameOver() {
		PlayerData player = ResourceManager.getInstance().DBHelper.getPlayer();
		int coinBalance = player.getCoins();
		int totalDistance = player.getDistance();
		int randomInt = rand.nextInt(100000);
		//save values into Games Table
		coinBalance += coins;
		totalDistance += distance;
		ResourceManager.getInstance().DBHelper.insertGamesTable(randomInt, coins, distance, score, 
				ResourceManager.getInstance().DBHelper.getPlayerId());
		//also update #coins and total distance in player table
		ResourceManager.getInstance().DBHelper.updatePlayerTable(ResourceManager.getInstance().DBHelper.getPlayerId(), 
				coinBalance, totalDistance, score);
		
		scene.disposeScene();
		//change to END_GAME
		//SceneManager.getInstance().createEndGameScene();
		SceneManager.getInstance().setScene(SceneManager.SceneType.SCENE_MENU);
	}

}
