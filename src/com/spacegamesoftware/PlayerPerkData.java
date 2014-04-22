package com.spacegamesoftware;

public class PlayerPerkData {
	//model of data saved in PlayerPerk table
	private long id;
	private int perkId;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getPerkId() {
		return perkId;
	}
	
	public void setPerkId(int id) {
		this.perkId = id;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %d", id, perkId);
		return output;
	}

}