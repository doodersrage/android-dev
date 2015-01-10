package com.example.mycontactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactDataSource {
	
	private SQLiteDatabase database ; //1
	
	private ContactDBHelper dbHelper ;
	
	public ContactDataSource(Context context) { //2
		dbHelper = new ContactDBHelper(context);
	}
	
	public void open() throws SQLException { //3
		database = dbHelper .getWritableDatabase();
	}
	
	public void close() { //4
		dbHelper .close();
	}
	
	public boolean insertContact(Contact c) {
		boolean didSucceed = false ; //1
		try {
			ContentValues initialValues = new ContentValues(); //2
			initialValues.put( "contactname" , c.getContactName()); //3
			initialValues.put( "streetaddress" , c.getStreetAddress());
			initialValues.put( "city" , c.getCity());
			initialValues.put( "state" , c.getState());
			initialValues.put( "zipcode" , c.getZipCode());
			initialValues.put( "phonenumber" , c.getPhoneNumber());
			initialValues.put( "cellnumber" , c.getCellNumber());
			initialValues.put( "email" , c.getEMail());
			initialValues.put( "birthday" ,String. valueOf (c.getBirthday().toMillis( false )));
			didSucceed = database .insert( "contact" , null , initialValues) > 0; //4
		}
		catch (Exception e) {
			//Do nothing -will return false if there is an exception //5
		}
		return didSucceed;
	}
	public boolean updateContact(Contact c) {
		boolean didSucceed = false ;
		try {
			Long rowId = Long. valueOf (c.getContactID()); //6
			ContentValues updateValues = new ContentValues();
			updateValues.put( "contactname" , c.getContactName());
			updateValues.put( "streetaddress" , c.getStreetAddress());
			updateValues.put( "city" , c.getCity());
			updateValues.put( "state" , c.getState());
			updateValues.put( "zipcode" , c.getZipCode());
			updateValues.put( "phonenumber" , c.getPhoneNumber());
			updateValues.put( "cellnumber" , c.getCellNumber());
			updateValues.put( "email" , c.getEMail());
			updateValues.put( "birthday" ,String. valueOf (c.getBirthday().toMillis( false )));
			didSucceed = database .update( "contact" , updateValues, "_id=" + rowId,null ) > 0; //7
		}
		catch (Exception e) {
			//Do nothing -will return false if there is an exception
		}
		return didSucceed;
	}
	
	public int getLastContactId() {
		int lastId = -1;
		try {
			String query = "Select MAX(_id) from contact" ; //1
			Cursor cursor = database .rawQuery(query, null ); //2
			cursor.moveToFirst(); //3
			lastId = cursor.getInt(0); //4
			cursor.close(); //5
		}
		catch (Exception e) {
			lastId = -1;
		}
		return lastId;
	}
}
