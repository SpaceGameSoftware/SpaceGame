package com.spacegamesoftware;

public class PlayerAchievementData {
	//model of data saved in Player table
	private long id;
	private int achieveId;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getAchieveId() {
		return achieveId;
	}
	
	public void setAchieveId(int id) {
		this.achieveId = id;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %d", id, achieveId);
		return output;
	}

}