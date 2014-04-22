package com.spacegamesoftware;

public class AchievementData {
	//model of data saved in Player table
	private long id;
	private String description;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setCoins(String description) {
		this.description = description;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %s", id, description);
		return output;
	}

}