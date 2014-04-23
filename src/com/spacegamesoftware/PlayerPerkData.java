package com.spacegamesoftware;

public class PlayerPerkData {
	//model of data saved in PlayerPerk table
	private long id;
	private int purchased;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getPurchased() {
		return purchased;
	}
	
	public void setPurchased(int purchase) {
		this.purchased = purchase;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %d", id, purchased);
		return output;
	}

}