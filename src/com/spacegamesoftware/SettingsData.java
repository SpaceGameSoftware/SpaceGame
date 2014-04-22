package com.spacegamesoftware;

public class SettingsData {
	//model of data saved in Player table
	private long id;
	private int musicOn;
	private int effectsOn;
	private int vibrateOn;
	
	/*
	 *  GETTERS AND SETTERS
	 * */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getMusicSetting() {
		return musicOn;
	}
	
	public void setMusicSetting(int state) {
		this.musicOn = state;
	}
	
	public int getEffectSetting() {
		return effectsOn;
	}
	
	public void setEffectSetting(int state) {
		this.effectsOn = state;
	}
	
	public int getVibrateSetting() {
		return vibrateOn;
	}
	
	public void setVibrateSetting(int state) {
		this.vibrateOn = state;
	}
	
	/*
	 * 	toString() 
	 * */
	
	public String toString() {
		//used by ArrayAdapter
		String output = String.format("%d %d %d %d", id, musicOn, effectsOn, vibrateOn);
		return output;
	}

}