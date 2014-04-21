package com.spacegamesoftware;

public class GameData {
	//model of data saved in Player table
	private long id;
	private int coins;
	private int distance;
	private int score;
	private long playerId;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public long getPlayerId() {
		return playerId;
	}
	
	public long setPlayerId(long id) {
		return this.playerId = id;
	}
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %d %d %d %d", id, coins, distance, score, playerId);
		return output;
	}

}