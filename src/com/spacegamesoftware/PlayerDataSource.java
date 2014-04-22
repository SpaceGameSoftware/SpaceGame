package com.spacegamesoftware;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PlayerDataSource {

  // Database fields
  private Context context;
  private SQLiteDatabase database;
  private DataBaseHelper dbHelper;
  private String[] allColumns = { DataBaseHelper.getColumnId(),
		  DataBaseHelper.getColumnCoins(), DataBaseHelper.getColumnDistance(), DataBaseHelper.getColumnScore() };

  public PlayerDataSource(Context MainActivity) {
	context= MainActivity;
    dbHelper = new DataBaseHelper(context);
    database = dbHelper.getDataBase();
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public PlayerData createPlayer(int comment) {
	  //update
    ContentValues values = new ContentValues();
    values.put(DataBaseHelper.getColumnCoins(), comment);
    long insertId = database.insert(DataBaseHelper.getPlayerTable(), null,
        values);
    Cursor cursor = database.query(DataBaseHelper.getPlayerTable(),
        allColumns, DataBaseHelper.getColumnId() + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    PlayerData newComment = cursorToPlayer(cursor);
    cursor.close();
    return newComment;
  }

  public void deletePlayer(PlayerData player) {
	  //update
    long id = player.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(DataBaseHelper.getPlayerTable(), DataBaseHelper.getColumnId()
        + " = " + id, null);
  }

  public PlayerData getPlayer() {
	  PlayerData player = null;
	  
	  Cursor cursor = database.query(DataBaseHelper.getPlayerTable(),
		        allColumns, null, null, null, null, null);
	  
	  //create data
	  if(cursor.moveToFirst()) {
		  player = cursorToPlayer(cursor);
	  }
	  
	  cursor.close();
	  return player;
  }
  
  public void insert(long id, int coins, int distance, int score) {
	  ContentValues values = new ContentValues();
	  values.put("_id", id);
	  values.put("coins", coins);
	  values.put("distance", distance);
	  values.put("score", score);
	  database.insert("Player", null, values);
	  //database.close();
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
  
  private PlayerData cursorToPlayer(Cursor cursor) {
	  //convert from cursor to player
	  PlayerData player = new PlayerData();
	  player.setId(cursor.getLong(0));
	  player.setCoins(cursor.getInt(1));
	  player.setDistance(cursor.getInt(2));
	  player.setScore(cursor.getInt(3));
    return player;
  }
} 