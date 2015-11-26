package com.xlm.dao;

import java.util.ArrayList;
import java.util.List;

import com.xlm.model.People;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseImp {
	DBHelper helper=null;
	private Context context;
	public DataBaseImp(Context context){
		this.context=context;
		helper=new DBHelper(context);
		}

	public boolean addSmsNum(String s){
		SQLiteDatabase db=helper.getWritableDatabase();
		if(s!=null){
			//向数据表SmsNum中插入联系人数据
			String sql="insert into SmsNum (phone) values(?)";
			Object[]params=new Object[]{s};
			db.execSQL(sql, params);
			db.close();
			return true;
		}else{
		return false;
		}
	}
	
	public boolean addPhoneNum(String s){
		SQLiteDatabase db=helper.getWritableDatabase();
		if(s!=null){
			//向数据表PhoneNum中插入联系人数据
			String sql="insert into PhoneNum (phone) values(?)";
			Object[]params=new Object[]{s};
			db.execSQL(sql, params);
			db.close();
			return true;
		}else{
		return false;
		}
	}
	
	public void changePhoneNum(String num,int selItem){
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="update PhoneNum set phone=? where _id=?";
		Object[] params=new Object[]{num,selItem};
		db.execSQL(sql, params);
		db.close();
	}
	
	public void addSensiWords(String word){
		SQLiteDatabase db=helper.getWritableDatabase();
		
			//向数据表SensiWords中插入联系人数据
			String sql="insert into SensiWords (word) values(?)";
			String[]params=new String[]{word};
			db.execSQL(sql, params);
			db.close();
		}
	
	public void changeSeensi(String word,int selItem){
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="update SensiWords set word=? where _id=?";
		Object[] params=new Object[]{word,selItem};
		db.execSQL(sql, params);
		db.close();
	}

	
	public void changeSmsNum(String s,int selItem){
		SQLiteDatabase db=helper.getWritableDatabase();
		String sql="update SmsNum set phone=? where _id=?";
		Object[] params=new Object[]{s,selItem};
		db.execSQL(sql, params);
		db.close();
	}
	
	
	public void deleteSmsNum(int id) {
		if(id>0){
			SQLiteDatabase db=helper.getWritableDatabase();
			String sql="delete from SmsNum where _id =?";
			String[]params=new String[]{String.valueOf(id)};
			db.execSQL(sql, params);
			db.close();
		}
		
	}
	
	
	public ArrayList<String> getSensiWords(){
		SQLiteDatabase db=helper.getWritableDatabase();
		 ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.rawQuery( "SELECT word FROM SensiWords",null); 
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	public ArrayList<String> getSmsNum(){
		SQLiteDatabase db=helper.getWritableDatabase();
		 ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.rawQuery( "SELECT phone FROM SmsNum",null); 
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	public ArrayList<String> getPhoneNum(){
		SQLiteDatabase db=helper.getWritableDatabase();
		 ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.rawQuery( "SELECT phone FROM PhoneNum",null); 
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return list;
		
	}
	
	public void deletePhoneNum(int id) {
		if(id>0){
			SQLiteDatabase db=helper.getWritableDatabase();
			String sql="delete from PhoneNum where _id =?";
			String[]params=new String[]{String.valueOf(id)};
			db.execSQL(sql, params);
			db.close();
		}
		
	}
	
	
	public void deleteSensiWords(int id){
		if(id>0){
			SQLiteDatabase db=helper.getWritableDatabase();
			String sql="delete from SensiWords where _id =?";
			String[]params=new String[]{String.valueOf(id)};
			db.execSQL(sql, params);
			db.close();
		}
	}
	
	
	
}
