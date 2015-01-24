package com.example.mynotelist;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class NoteDataSource {
	
	private SQLiteDatabase database ; //1
	
	private NoteDBHelper dbHelper ;
	
	public NoteDataSource(Context context) { //2
		dbHelper = new NoteDBHelper(context);
	}
	
	public void open() throws SQLException { //3
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() { //4
		dbHelper.close();
	}
	
	public boolean insertNote(Note c) {
		boolean didSucceed = false ; //1
		try {
			ContentValues initialValues = new ContentValues(); //2
			initialValues.put( "subject" , c.getNoteSubject()); //3
			initialValues.put( "note" , c.getNoteNote());

			didSucceed = database .insert( "note" , null , initialValues) > 0; //4
		}
		catch (Exception e) {
			//Do nothing -will return false if there is an exception //5
		}
		return didSucceed;
	}
	public boolean updateNote(Note c) {
		boolean didSucceed = false ;
		try {
			Long rowId = Long. valueOf (c.getNoteID()); //6
			ContentValues updateValues = new ContentValues();
			updateValues.put( "subject" , c.getNoteSubject());
			updateValues.put( "note" , c.getNoteNote());

			didSucceed = database .update( "note" , updateValues, "_id=" + rowId,null ) > 0; //7
		}
		catch (Exception e) {
			//Do nothing -will return false if there is an exception
		}
		return didSucceed;
	}
	
	public int getLastNoteId() {
		int lastId = -1;
		try {
			String query = "Select MAX(_id) from note" ; //1
			Cursor cursor = database.rawQuery(query, null ); //2
			cursor.moveToFirst(); //3
			lastId = cursor.getInt(0); //4
			cursor.close(); //5
		}
		catch (Exception e) {
			lastId = -1;
		}
		return lastId;
	}
	
	public ArrayList<String> getNoteSubject() {
		ArrayList<String> NoteNames = new ArrayList<String>(); //1
		try {
			String query = "Select subject from note" ; //2
			Cursor cursor = database.rawQuery(query, null );
			cursor.moveToFirst(); //3
			while (!cursor.isAfterLast()) {
				NoteNames.add(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			NoteNames = new ArrayList<String>(); //4
		}
		return NoteNames;
	}
	
	public ArrayList<Note> getNotes(String sortField, String sortOrder) {
		ArrayList<Note> Notes = new ArrayList<Note>();
		try {
			String query = "SELECT * FROM note ORDER BY " + sortField + " " + sortOrder;
			Cursor cursor = database .rawQuery(query, null );
			Note newNote;
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				newNote = new Note(); //1
				newNote.setNoteID(cursor.getInt(0));
				newNote.setNoteSubject(cursor.getString(1));
				newNote.setNoteNote(cursor.getString(2));
				newNote.setNoteCreated(cursor.getString(3));

				Notes.add(newNote);
				cursor.moveToNext();
			}
			cursor.close();
		}
		catch (Exception e) {
			Notes = new ArrayList<Note>();
		}
		return Notes;
	}
	
	public boolean deleteNote( int NoteId) {
		boolean didDelete = false ;
		try {
			didDelete = database.delete( "note" , "_id=" + NoteId, null ) > 0;
		}
		catch (Exception e) {
			//Do nothing -return value already set to false
		}
		return didDelete;
	}
	
	public Note getSpecificNote( int NoteId) { //1
		Note Note = new Note();
		String query = "SELECT * FROM note WHERE _id =" + NoteId; //2
		Cursor cursor = database .rawQuery(query, null );
		if (cursor.moveToFirst()) { //3
			Note.setNoteID(cursor.getInt(0));
			Note.setNoteSubject(cursor.getString(1));
			Note.setNoteNote(cursor.getString(2));
			Note.setNoteCreated(cursor.getString(3));

			cursor.close();
		}
		return Note;
	}
}
