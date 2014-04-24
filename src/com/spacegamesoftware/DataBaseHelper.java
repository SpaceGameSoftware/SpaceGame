package com.spacegamesoftware;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
	private static long PLAYER_ID = 1;
	private static int INIT_COINS = 1000;
	private static int INIT_DISTANCE = 0;
	private static int INIT_SCORE = 0;
	private static boolean dbCreate = false;

	//Tables
	private static String TABLE_PLAYER = "Player";
	private static String TABLE_PERKS = "Perks";
	private static String TABLE_ACHIEVEMENTS = "Achievements";
	private static String TABLE_GAMES = "Games";
	private static String TABLE_SETTINGS = "Settings";
	private static String TABLE_PLAYER_PERKS= "PlayerPerks";
	private static String TABLE_PLAYER_ACHIEVEMENTS = "PlayerAchievements";

	//Columns
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
	private static String COLUMN_PERK_PURCHASED = "purchased";
	private static String COLUMN_ACHIEVE_ID = "achieveId";

	private static String PERK_DISTANCE_MULTIPLIER_1_DESCRIP = "2x the distance multiplier.";
	private static String PERK_DISTANCE_MULTIPLIER_2_DESCRIP = "3x the distance multiplier.";
	private static String PERK_COIN_MULTIPLIER_1_DESCRIP = "Coins with value of 2 appear.";
	private static int PERK_DISTANCE_MULTIPLIER_1_ID = 1;
	private static int PERK_DISTANCE_MULTIPLIER_2_ID = 2;
	private static int PERK_COIN_MULTIPLIER_1_ID = 6;
	
	//table column collections
	private static String[] allPlayerColumns = {COLUMN_ID, COLUMN_COINS, COLUMN_DISTANCE, COLUMN_SCORE};
	private static String[] allPerkColumns = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_VALUE, COLUMN_PREREQ_ID};
	private static String[] allAchievementColumns = {COLUMN_ID, COLUMN_DESCRIPTION};
	private static String[] allGameColumns = {COLUMN_ID, COLUMN_COINS, COLUMN_DISTANCE, COLUMN_SCORE, COLUMN_PLAYER_ID};
	private static String[] allSettingsColumns = {COLUMN_ID, COLUMN_MUSIC_ON, COLUMN_EFFECTS_ON, COLUMN_VIBRATE_ON};
	private static String[] allPlayerPerkColumns = {COLUMN_ID, COLUMN_PERK_PURCHASED};

	//Table Creation SQL statements
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
			TABLE_PLAYER_PERKS, COLUMN_ID, COLUMN_PERK_PURCHASED);

	private String PLAYER_ACHIEVEMENTS_TABLE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer );",
			TABLE_PLAYER_ACHIEVEMENTS, COLUMN_ID, COLUMN_ACHIEVE_ID);


	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//calls either, onCreate(), onUpgrade(), or onOpen()
		this.myDataBase = this.getWritableDatabase();

		if(dbCreate) {
			//insert into respective DB tables
			insertPlayerTable(PLAYER_ID, INIT_COINS, INIT_DISTANCE, INIT_SCORE);
			insertPerkTable(PERK_DISTANCE_MULTIPLIER_1_ID, PERK_DISTANCE_MULTIPLIER_1_DESCRIP, 200, 0);
			insertPerkTable(PERK_DISTANCE_MULTIPLIER_2_ID, PERK_DISTANCE_MULTIPLIER_2_DESCRIP, 400, 0);
			insertPerkTable(PERK_COIN_MULTIPLIER_1_ID, PERK_COIN_MULTIPLIER_1_DESCRIP, 200, 0);
			insertAchievementTable(0, "Go a distance of 10k.");
			insertAchievementTable(1, "Collect 50 Space Coins.");
			insertSettingsTable(0, 1, 0, 0);
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create DB tables
		db.execSQL(PLAYER_TABLE_CREATE);
		db.execSQL(PERK_TABLE_CREATE);
		db.execSQL(ACHIEVEMENTS_TABLE_CREATE);
		db.execSQL(GAMES_TABLE_CREATE);
		db.execSQL(SETTINGS_TABLE_CREATE);
		db.execSQL(PLAYER_PERKS_TABLE_CREATE);
		db.execSQL(PLAYER_ACHIEVEMENTS_TABLE_CREATE);
		dbCreate = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

	@Override
	public void onOpen(SQLiteDatabase db) {}

	public void open() throws SQLException {
		myDataBase = getWritableDatabase();
	}

	public void closeDataBase() { 
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

	public long getPlayerId() {
		return PLAYER_ID;
	}

	/*******************************************************
	 * 	PLAYER TABLE
	 *******************************************************/

	public void insertPlayerTable(long id, int coins, int distance, int score) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_COINS, coins);
		values.put(COLUMN_DISTANCE, distance);
		values.put(COLUMN_SCORE, score);
		myDataBase.insert(TABLE_PLAYER, null, values);
	}

	public void updatePlayerTable(long id, int coins, int distance, int score) {
		String whereClause = String.format("%s = %d", COLUMN_ID, PLAYER_ID);
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_COINS, coins);
		values.put(COLUMN_DISTANCE, distance);
		values.put(COLUMN_SCORE, score);
		myDataBase.update(TABLE_PLAYER, values, whereClause, null);
	}

	public PlayerData createPlayer(long id, int coins, int distance, int score) {
		PlayerData player = new PlayerData();
		player.setId(id);
		player.setCoins(coins);
		player.setDistance(distance);
		player.setScore(score);
		return player;
	}

	public void deletePlayer(PlayerData player) {}

	public PlayerData getPlayer() {
		PlayerData player = null;

		Cursor cursor = myDataBase.query(TABLE_PLAYER,
				allPlayerColumns, null, null, null, null, null);

		if(cursor.moveToFirst()) {
			player = cursorToPlayer(cursor);
		}

		cursor.close();
		return player;
	}

	private PlayerData cursorToPlayer(Cursor cursor) {
		//convert from cursor to player
		PlayerData player = new PlayerData();
		player.setId(cursor.getLong(0));
		player.setCoins(cursor.getInt(1));
		player.setDistance(cursor.getInt(2));
		player.setScore(cursor.getInt(3));
		return player;
	}

	/*******************************************************
	 * 	PERK TABLE
	 *******************************************************/

	public void insertPerkTable(long id, String description, int value, int prereqId) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_DESCRIPTION, description);
		values.put(COLUMN_VALUE, value);
		values.put(COLUMN_PREREQ_ID, prereqId);
		myDataBase.insert(TABLE_PERKS, null, values);
	}

	public void updatePerkTable(long id, String description, int value, int prereqId) {
		String whereClause = String.format("%s = %d", COLUMN_ID, id);
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_DESCRIPTION, description);
		values.put(COLUMN_VALUE, value);
		values.put(COLUMN_PREREQ_ID, prereqId);
		myDataBase.update(TABLE_PLAYER, values, whereClause, null);
	}
	
	public PerkData createPerk(long id, int coins, int distance, int score) {return null;}
	public void deletePerk(PlayerData player) {}

	public PerkData getPerk(int perkId) {
		PerkData perk = null;
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", perkId)};
		Cursor cursor = myDataBase.query(TABLE_PERKS,
				allPerkColumns, whereClause, whereArgs, null, null, null);

		if(cursor.moveToFirst()) {
			perk = cursorToPerk(cursor);
		}

		cursor.close();
		return perk;
	}

	private PerkData cursorToPerk(Cursor cursor) {
		//convert from cursor to perk
		PerkData perk = new PerkData();
		perk.setId(cursor.getLong(0));
		perk.setDescription(cursor.getString(1));
		perk.setValue(cursor.getInt(2));
		perk.setPrereqId(cursor.getInt(3));
		return perk;
	}

	/*******************************************************
	 * 	ACHIEVEMENTS TABLE
	 *******************************************************/

	public void insertAchievementTable(long id, String description) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_DESCRIPTION, description);
		myDataBase.insert(TABLE_ACHIEVEMENTS, null, values);
	}

	public AchievementData createAchievement(long id, String description) {return null;}
	public void deleteAchievement(AchievementData achievement) {}

	public AchievementData getAchievement(int achievementId) {
		AchievementData achievement = null;
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", achievementId)};
		Cursor cursor = myDataBase.query(TABLE_ACHIEVEMENTS,
				allAchievementColumns, whereClause, whereArgs, null, null, null);

		if(cursor.moveToFirst()) {
			achievement = cursorToAchievement(cursor);
		}

		cursor.close();
		return achievement;
	}

	private AchievementData cursorToAchievement(Cursor cursor) {
		//convert from cursor to perk
		AchievementData achievement = new AchievementData();
		achievement.setId(cursor.getLong(0));
		achievement.setDescription(cursor.getString(1));
		return achievement;
	}

	/*******************************************************
	 * 	GAMES TABLE
	 *******************************************************/

	public void insertGamesTable(long id, int coins, int distance, int score, long playerId) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_COINS, coins);
		values.put(COLUMN_DISTANCE, distance);
		values.put(COLUMN_SCORE, score);
		values.put(COLUMN_PLAYER_ID, playerId);
		myDataBase.insert(TABLE_GAMES, null, values);
	}
	
	public int getHighScore() {
		GameData gameInstance;
		int highScore = 0;
		Cursor cursor = myDataBase.query(TABLE_GAMES, allGameColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			gameInstance = cursorToGame(cursor);
			if(gameInstance.getScore() > highScore) {
				highScore = gameInstance.getScore();
			}
			cursor.moveToNext();
		}
		return highScore;
	}
	
	public int getCumulativeScore() {
		GameData gameInstance;
		int cumulativeScore = 0;
		Cursor cursor = myDataBase.query(TABLE_GAMES, allGameColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			gameInstance = cursorToGame(cursor);
			cumulativeScore += gameInstance.getScore();
			cursor.moveToNext();
		}
		return cumulativeScore;
	}
	
	public int getCumulativeCoins() {
		GameData gameInstance;
		int cumulativeCoins = 0;
		Cursor cursor = myDataBase.query(TABLE_GAMES, allGameColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			gameInstance = cursorToGame(cursor);
			cumulativeCoins += gameInstance.getCoins();
			cursor.moveToNext();
		}
		return cumulativeCoins;
	}
	
	public GameData createGame(long id, int coins, int distance, int score, long playerId) {return null;}
	public void deleteGame(GameData game) {}

	public GameData getGame(int gameId) {
		GameData game = null;
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", gameId)};
		Cursor cursor = myDataBase.query(TABLE_GAMES,
				allGameColumns, whereClause, whereArgs, null, null, null);

		if(cursor.moveToFirst()) {
			game = cursorToGame(cursor);
		}

		cursor.close();
		return game;
	}

	private GameData cursorToGame(Cursor cursor) {
		//convert from cursor to perk
		GameData game = new GameData();
		game.setId(cursor.getLong(0));
		game.setCoins(cursor.getInt(1));
		game.setDistance(cursor.getInt(2));
		game.setScore(cursor.getInt(3));
		game.setPlayerId(cursor.getLong(4));
		return game;
	}

	/*******************************************************
	 * 	SETTINGS TABLE
	 *******************************************************/

	public void insertSettingsTable(long id, int musicOn, int effectsOn, int vibrateOn) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_MUSIC_ON, musicOn);
		values.put(COLUMN_EFFECTS_ON, effectsOn);
		values.put(COLUMN_VIBRATE_ON, vibrateOn);
		myDataBase.insert(TABLE_SETTINGS, null, values);
	}

	public SettingsData createSettings(long id, int musicOn, int effectsOn, int vibrateOn) {
		SettingsData settings = new SettingsData();
		settings.setId(id);
		settings.setMusicSetting(musicOn);
		settings.setEffectSetting(effectsOn);
		settings.setVibrateSetting(vibrateOn);
		return settings;
	}

	public void deleteSettings(SettingsData settings) {}

	public SettingsData getSettings(long settingsId) {
		SettingsData settings = null;
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", settingsId)};
		Cursor cursor = myDataBase.query(TABLE_SETTINGS,
				allSettingsColumns, whereClause, whereArgs, null, null, null);

		if(cursor.moveToFirst()) {
			settings = cursorToSettings(cursor);
		}

		cursor.close();
		return settings;
	}

	private SettingsData cursorToSettings(Cursor cursor) {
		//convert from cursor to perk
		SettingsData settings = new SettingsData();
		settings.setId(cursor.getLong(0));
		settings.setMusicSetting(cursor.getInt(1));
		settings.setEffectSetting(cursor.getInt(2));
		settings.setVibrateSetting(cursor.getInt(3));
		return settings;
	}
	
	/*******************************************************
	 * 	PLAYER PERK TABLE
	 *******************************************************/

	public void insertPlayerPerkTable(long id, int purchased) {
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_PERK_PURCHASED, purchased);
		myDataBase.insert(TABLE_PLAYER_PERKS, null, values);
	}

	public void updatePlayerPerkTable(long id, int purchased) {
		String whereClause = String.format("%s = %d", COLUMN_ID, purchased);
		ContentValues values = new ContentValues();
		values.put(COLUMN_ID, id);
		values.put(COLUMN_PERK_PURCHASED, purchased);
		myDataBase.update(TABLE_PLAYER_PERKS, values, whereClause, null);
	}

	public PlayerPerkData createPlayerPerk(int id, int purchased) {
		PlayerPerkData playerPerk = new PlayerPerkData();
		playerPerk.setId(id);
		playerPerk.setPurchased(purchased);
		return playerPerk;
	}

	public void deletePlayerPerk(PlayerPerkData playerPerk) {}

	public PlayerPerkData getPlayerPerk(int perkId) {
		PlayerPerkData playerPerk = null;
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", perkId)};
		
		Cursor cursor = myDataBase.query(TABLE_PLAYER_PERKS,
				allPlayerPerkColumns, whereClause, whereArgs, null, null, null);		
		
		if(cursor.moveToFirst()) {
			playerPerk = cursorToPlayerPerk(cursor);
		}

		cursor.close();
		return playerPerk;
	}
	
	public ArrayList<PlayerPerkData> getAllPlayerPerk() {
		ArrayList<PlayerPerkData> playerPerks = new ArrayList<PlayerPerkData>();
		PlayerPerkData perkData;
		Cursor cursor = myDataBase.query(TABLE_PLAYER_PERKS,
				allPlayerPerkColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			perkData = cursorToPlayerPerk(cursor);
			playerPerks.add(perkData);
			cursor.moveToNext();
		}
		cursor.close();
		return playerPerks;
	}

	private PlayerPerkData cursorToPlayerPerk(Cursor cursor) {
		//convert from cursor to player
		PlayerPerkData playerPerk = new PlayerPerkData();
		playerPerk.setId(cursor.getInt(0));
		playerPerk.setPurchased(cursor.getInt(1));
		return playerPerk;
	}
	
	public boolean perkBought(int perkId) {
		String whereClause = String.format("%s = ?", COLUMN_ID);
		String whereArgs[] = {String.format("%d", perkId)};
		
		Cursor cursor = myDataBase.query(TABLE_PLAYER_PERKS,
				allPlayerPerkColumns, whereClause, whereArgs, null, null, null);		
		
		if (cursor.moveToFirst()) {
			return true;
		}
		return false;
	}
}



/*
public List<PlayerData> getAllPlayers() {
	  //update
  List<PlayerData> comments = new ArrayList<PlayerData>();

  Cursor cursor = database.query(DataBaseHelper.TABLE_PLAYER,
      allColumns, null, null, null, null, null);

  cursor.moveToFirst();
  while (!cursor.isAfterLast()) {
  	PlayerData player = cursorToPlayer(cursor);
    comments.add(player);
    cursor.moveToNext();
  }
  // make sure to close the cursor
  cursor.close();
  return comments;
}
*/