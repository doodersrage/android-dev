package com.example.mycontactlist;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

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
	
	public ArrayList<String> getContactName() {
		ArrayList<String> contactNames = new ArrayList<String>(); //1
		try {
			String query = "Select contactname from contact" ; //2
			Cursor cursor = database .rawQuery(query, null );
			cursor.moveToFirst(); //3
			while (!cursor.isAfterLast()) {
				contactNames.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			contactNames = new ArrayList<String>(); //4
		}
		return contactNames;
	}
	
	public ArrayList<Contact> getContacts(String sortField, String sortOrder) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try {
			String query = "SELECT * FROM contact ORDER BY " + sortField + " " + sortOrder;
			Cursor cursor = database .rawQuery(query, null );
			Contact newContact;
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				newContact = new Contact(); //1
				newContact.setContactID(cursor.getInt(0));
				newContact.setContactName(cursor.getString(1));
				newContact.setStreetAddress(cursor.getString(2));
				newContact.setCity(cursor.getString(3));
				newContact.setState(cursor.getString(4));
				newContact.setZipCode(cursor.getString(5));
				newContact.setPhoneNumber(cursor.getString(6));
				newContact.setCellNumber(cursor.getString(7));
				newContact.setEMail(cursor.getString(8));
				Time t = new Time(); //2
				t.set(Long. valueOf (cursor.getString(9)));
				newContact.setBirthday(t);
				contacts.add(newContact);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			contacts = new ArrayList<Contact>();
		}
		return contacts;
	}
	
	public boolean deleteContact( int contactId) {
		boolean didDelete = false ;
		try {
			didDelete = database .delete( "contact" , "_id=" + contactId, null ) > 0;
		}
		catch (Exception e) {
			//Do nothing -return value already set to false
		}
		return didDelete;
	}
	
	public Contact getSpecificContact( int contactId) { //1
		Contact contact = new Contact();
		String query = "SELECT * FROM contact WHERE _id =" + contactId; //2
		Cursor cursor = database .rawQuery(query, null );
		if (cursor.moveToFirst()) { //3
			contact.setContactID(cursor.getInt(0));
			contact.setContactName(cursor.getString(1));
			contact.setStreetAddress(cursor.getString(2));
			contact.setCity(cursor.getString(3));
			contact.setState(cursor.getString(4));
			contact.setZipCode(cursor.getString(5));
			contact.setPhoneNumber(cursor.getString(6));
			contact.setCellNumber(cursor.getString(7));
			contact.setEMail(cursor.getString(8));
			Time t = new Time();
			t.set(Long. valueOf (cursor.getString(9)));
			contact.setBirthday(t);
			cursor.close();
		}
		return contact;
	}
}
