package com.spacegamesoftware;

public class PerkData {
	//model of data saved in Perk table
	private long id;
	private String description;
	private int value;
	private long prereqId;
	
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
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public long getPrereqId() {
		return prereqId;
	}
	
	public void setPrereqId(long id) {
		this.prereqId = id;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %s %d %d", id, description, value, prereqId);
		return output;
	}

}