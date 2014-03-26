package com.spacegamesoftware;

import java.util.ArrayList;
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
	private Sprite spaceship;
	private List<Asteroid> asteroids;
	private BaseScene scene;
	private Random rand;
	
	public SpaceGameLogic(BaseScene scene) {
		this.scene = scene;
		score = 0;
		distance = 0;
		coins = 0;
		asteroids = new ArrayList<Asteroid>();
		rand = new Random();
	}
	

	@Override
	public void onUpdate(float secondsElapsed) {
		updateScore();
		updateDistance();
		updateAsteroids();
	}

	@Override
	public void reset() {
		score = 0;
		distance = 0;
		coins = 0;
	}
	
	
	public void setScoreText(Text text) {
		scoreText = text;
	}
	
	public void setSpaceship(Sprite spaceship) {
		this.spaceship = spaceship;
	}
	
	private void updateDistance() {
		distance += 1;
	}
	
	private void updateScore() {
		score = distance + coins;
		scoreText.setText("Score: " + score);
	}
	
	private void updateAsteroids() {
		if (rand.nextInt(100) < 2) {
			asteroids.add(createAsteroid());
		}
		
		for (Asteroid asteroid : asteroids) {
			asteroid.update();
		}
	}
	
	private Asteroid createAsteroid() {
		float x = (float) rand.nextInt(MainActivity.CAMERA_WIDTH);
		Asteroid asteroid = new Asteroid(x, 0.0f, ResourceManager.getInstance().asteroidRegion,
				ResourceManager.getInstance().vbom);
		asteroid.setVelocity(new Vector(0.0f, 1.0f));
		scene.attachChild(asteroid);
		return asteroid;
	}

}
