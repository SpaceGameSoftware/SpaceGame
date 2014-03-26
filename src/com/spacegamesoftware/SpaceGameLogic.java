package com.spacegamesoftware;

import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

public class SpaceGameLogic implements IUpdateHandler {
	
	private int score;
	private int distance;
	private int coins;
	private Text scoreText;
	private List<Sprite> asteroids;
	
	public SpaceGameLogic() {
		score = 0;
		distance = 0;
		coins = 0;
	}

	@Override
	public void onUpdate(float secondsElapsed) {
		updateScore();
		updateDistance();
	}

	@Override
	public void reset() {
		
	}
	
	
	public void setScoreText(Text text) {
		scoreText = text;
	}
	
	private void updateDistance() {
		distance += 1;
	}
	
	private void updateScore() {
		score = distance + coins;
		scoreText.setText("Score: " + score);
	}

}
