package com.xlm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String NAME="fw.db";
	private static final int VERSION=1;	
		public DBHelper(Context context){
			super(context,NAME,null,VERSION);
		}
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql1="create table SmsNum ("
				+ "_id integer primary key autoincrement,"						
				+ "name text,"+ "phone text) ";
		String sql2="create table PhoneNum ("
				+ "_id integer primary key autoincrement,"						
				+ "name text,"+ "phone text)";
		String sql3="create table SensiWords ("
				+ "_id integer primary key autoincrement,"						
				+ "word text)";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
