package com.wiredave.uvite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Referral_Database {

	private final String db_name = "Referral.db";
	private final int db_version = 1;
	private Context con;
	private DatabaseHelper dbhelp;
	private SQLiteDatabase db;
	
	private final String Login = "Login";

	String db_creat1 = "create table Login(user_id TEXT,username TEXT,email TEXT,password TEXT,phonenumber TEXT,usertype TEXT,usertoken TEXT)";
	
	public Referral_Database(Context context){
		this.con = context;
		dbhelp = new DatabaseHelper(con, db_name, null, db_version);
		db = dbhelp.getWritableDatabase();
	}
	
	public class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context, String name,CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			 db.execSQL(db_creat1);
			 
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
	
	public void Closedatabase(){
		db.close();
	}
	
	//insert Login Data
	public void Insert_Login_Detail(String id,String username ,String email ,String password,String phonenumber,String usertype,String usertoken){
		
		ContentValues initialVal = new ContentValues();
		initialVal.put("user_id", id);		
		initialVal.put("username", username);
		initialVal.put("email", email);
		initialVal.put("password", password);
		initialVal.put("phonenumber",phonenumber);
		initialVal.put("usertype", usertype);
		initialVal.put("usertoken", usertoken);
		
		db.insert(Login, null, initialVal);
	}
	
	
	public Boolean checkIfExist(){
		Cursor c = db.rawQuery("SELECT * FROM "+Login, null);
		if(c.moveToFirst()){
		    return true ;
		}else{
		    return false ;
		}
		
	}
	
	public void Delete_Login_Detail(){
		Cursor c = db.rawQuery("SELECT * FROM "+Login, null);
		if(c.getCount() > 0)
			db.execSQL("DELETE FROM "+Login);
	}
	
	public String getUserID(){
		Cursor c = db.rawQuery("SELECT user_id FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("user_id"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	public String getUserType(){
		Cursor c = db.rawQuery("SELECT usertype FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("usertype"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	
	public String getUserName(){
		Cursor c = db.rawQuery("SELECT username FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("username"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	public String getEmail(){
		
		Cursor c = db.rawQuery("SELECT email FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("email"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	
	public String getPassword(){
		Cursor c = db.rawQuery("SELECT password FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("password"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	public String getPhoneNumber(){
		Cursor c = db.rawQuery("SELECT phonenumber FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("phonenumber"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	public String getUserToken(){
		Cursor c = db.rawQuery("SELECT usertoken FROM "+Login, null);
		if(c.moveToFirst()){
			String u = c.getString(c.getColumnIndex("usertoken"));
			c.close();
			return u;
		}
		else{
			c.close();
			return "";
		}
	}
	
	
}