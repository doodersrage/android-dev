package com.example.mynotelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteDBHelper extends SQLiteOpenHelper { //1
	
	private static final String DATABASE_NAME = "mynotes.db" ; //2
	
	private static final int DATABASE_VERSION = 1; //3
	
	// Database creation sql statement
	private static final String CREATE_TABLE_NOTE = "create table note (_id integer primary key autoincrement, " //4
			+ "subject text not null, note text, "
			+ "created DATETIME DEFAULT CURRENT_TIMESTAMP);" ;
	
	public NoteDBHelper(Context context) { //5
		super (context, DATABASE_NAME , null , DATABASE_VERSION );
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) { //6
		database.execSQL( CREATE_TABLE_NOTE );
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //7
		Log. w (NoteDBHelper. class .getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data" );
		db.execSQL( "DROP TABLE IF EXISTS note" );
		onCreate(db);
	}
}
