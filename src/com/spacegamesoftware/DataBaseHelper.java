package com.spacegamesoftware;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	private SQLiteDatabase myDataBase;
	private static String DATABASE_NAME = "SpaceGame.db";
	private static int DATABASE_VERSION = 1;
	
	private static String TABLE_PLAYER = "Player";
	private static String TABLE_PERKS = "Perks";
	private static String TABLE_ACHIEVEMENTS = "Achievements";
	private static String TABLE_GAMES = "Games";
	private static String TABLE_SETTINGS = "Settings";
	private static String TABLE_PLAYER_PERKS= "PlayerPerks";
	private static String TABLE_PLAYER_ACHIEVEMENTS = "PlayerAchievements";
	
	private static String COLUMN_ID = "_id";
	private static String COLUMN_COINS = "coins";
	private static String COLUMN_DISTANCE = "distance";
	private static String COLUMN_SCORE = "score";
	private static String COLUMN_DESCRIPTION = "description";
	private static String COLUMN_VALUE = "value";
	private static String COLUMN_PREREQ_ID = "prereqId";
	private static String COLUMN_PLAYER_ID = "playerId";
	private static String COLUMN_MUSIC_ON = "musicOn";
	private static String COLUMN_EFFECTS_ON = "effectsOn";
	private static String COLUMN_VIBRATE_ON = "vibrateOn";
	private static String COLUMN_PERK_ID = "perkId";
	private static String COLUMN_ACHIEVE_ID = "achieveId";
	
	private String PLAYER_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer, %s integer, %s integer );",
			TABLE_PLAYER, COLUMN_ID,COLUMN_COINS,COLUMN_DISTANCE,COLUMN_SCORE);
	
	private String PERK_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s text, %s integer, %s integer );",
			TABLE_PERKS, COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_VALUE, COLUMN_PREREQ_ID);
	
	private String ACHIEVEMENTS_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s text );",
			TABLE_ACHIEVEMENTS, COLUMN_ID, COLUMN_DESCRIPTION);
	
	private String GAMES_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer, %s integer, %s integer, %s integer);",
			TABLE_GAMES, COLUMN_ID, COLUMN_COINS, COLUMN_DISTANCE, COLUMN_SCORE, COLUMN_PLAYER_ID);
	
	private String SETTINGS_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer, %s integer, %s integer );",
			TABLE_SETTINGS, COLUMN_ID, COLUMN_MUSIC_ON, COLUMN_EFFECTS_ON, COLUMN_VIBRATE_ON);
	
	private String PLAYER_PERKS_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer );",
			TABLE_PLAYER_PERKS, COLUMN_ID, COLUMN_PERK_ID);
	
	private String PLAYER_ACHIEVEMENTS_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer );",
			TABLE_PLAYER_ACHIEVEMENTS, COLUMN_ID, COLUMN_ACHIEVE_ID);
	
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//calls either, onCreate(), onUpgrade(), or onOpen()
		 this.myDataBase = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create Player table and insert values
		db.execSQL(PLAYER_TABLE_CREATE);
		ContentValues playerTableValues = new ContentValues();
		playerTableValues.put(COLUMN_ID, 25);
		playerTableValues.put(COLUMN_COINS, 100);
		playerTableValues.put(COLUMN_DISTANCE, 50);
		playerTableValues.put(COLUMN_SCORE, 205);
		db.insert(TABLE_PLAYER, null, playerTableValues);
		
		db.execSQL(PERK_TABLE_CREATE);
		ContentValues perkTableValues = new ContentValues();
		perkTableValues.put(COLUMN_ID, 0);
		perkTableValues.put(COLUMN_DESCRIPTION, "2x the distance multiplier.");
		perkTableValues.put(COLUMN_VALUE, 200);
		perkTableValues.put(COLUMN_PREREQ_ID, 0);
		db.insert(TABLE_PERKS, null, perkTableValues);
		
		perkTableValues = new ContentValues();
		perkTableValues.put(COLUMN_ID, 1);
		perkTableValues.put(COLUMN_DESCRIPTION, "3x the distance multiplier.");
		perkTableValues.put(COLUMN_VALUE, 400);
		perkTableValues.put(COLUMN_PREREQ_ID, 0);
		db.insert(TABLE_PERKS, null, perkTableValues);
		
		perkTableValues = new ContentValues();
		perkTableValues.put(COLUMN_ID, 2);
		perkTableValues.put(COLUMN_DESCRIPTION, "Coins with value of 2 appear.");
		perkTableValues.put(COLUMN_VALUE, 200);
		perkTableValues.put(COLUMN_PREREQ_ID, 0);
		db.insert(TABLE_PERKS, null, perkTableValues);
		
		db.execSQL(ACHIEVEMENTS_TABLE_CREATE);
		ContentValues achievementTableValues = new ContentValues();
		achievementTableValues.put(COLUMN_ID, 0);
		achievementTableValues.put(COLUMN_DESCRIPTION, "Go a distance of 10k.");
		db.insert(TABLE_ACHIEVEMENTS, null, achievementTableValues);
		
		achievementTableValues = new ContentValues();
		achievementTableValues.put(COLUMN_ID, 1);
		achievementTableValues.put(COLUMN_DESCRIPTION, "Collect 50 Space Coins.");
		db.insert(TABLE_ACHIEVEMENTS, null, achievementTableValues);
		
		
		db.execSQL(GAMES_TABLE_CREATE);
		
		db.execSQL(SETTINGS_TABLE_CREATE);
		ContentValues settingsTableValues = new ContentValues();
		settingsTableValues.put(COLUMN_ID, 0);
		settingsTableValues.put(COLUMN_MUSIC_ON, 1);
		settingsTableValues.put(COLUMN_EFFECTS_ON, 0);
		settingsTableValues.put(COLUMN_VIBRATE_ON, 0);
		db.insert(TABLE_SETTINGS, null, settingsTableValues);

		db.execSQL(PLAYER_PERKS_TABLE_CREATE);
		
		db.execSQL(PLAYER_ACHIEVEMENTS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//Log.w(MySQLiteHelper.class.getName(),
		//        "Upgrading database from version " + oldVersion + " to "
		//            + newVersion + ", which will destroy all old data");
		//    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		//    onCreate(db);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		//if db is created, read values from it.. maybe
	}
	
	public void closeDataBase() {
		//close database 
		myDataBase.close();
	}
	
	public static String getPlayerTable() {
		return TABLE_PLAYER;
	}
	
	public static String getColumnId() {
		return COLUMN_ID;
	}
	
	public static String getColumnCoins() {
		return COLUMN_COINS;
	}
	
	public static String getColumnDistance() {
		return COLUMN_DISTANCE;
	}
	
	public static String getColumnScore() {
		return COLUMN_SCORE;
	}
	
	public SQLiteDatabase getDataBase() {
		return myDataBase;
	}
}