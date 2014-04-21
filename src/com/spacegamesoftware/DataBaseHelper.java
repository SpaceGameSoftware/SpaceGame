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
	private static String TABLE_PLAYER = "Player"; //player table
	public static String COLUMN_ID = "_id";
	public static String COLUMN_COINS = "coins";
	public static String COLUMN_DISTANCE = "distance";
	public static String COLUMN_SCORE = "score";
	
	private static String DATABASE_NAME = "empty.db";
	private static int DATABASE_VERSION = 1;
	private String DATABASE_CREATE = String.format("create table %s " +
			"( %s integer primary key, %s integer, %s integer, %s integer );",
			TABLE_PLAYER, COLUMN_ID,COLUMN_COINS,COLUMN_DISTANCE,COLUMN_SCORE);
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//calls either, onCreate(), onUpgrade(), or onOpen()
		 this.myDataBase = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//TODO: create each table
		
		//create Player table
		db.execSQL(DATABASE_CREATE);
		//populate table with data
		ContentValues values = new ContentValues();
		values.put("_id", 25);
		values.put("coins", 100);
		values.put("distance", 50);
		values.put("score", 205);
		db.insert("Player", null, values);
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
	
	public SQLiteDatabase getDataBase() {
		return myDataBase;
	}
}