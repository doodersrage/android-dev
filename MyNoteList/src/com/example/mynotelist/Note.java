package com.example.mynotelist;


public class Note {
	private int noteID ;
	private String noteSubject ;
	private String noteNote ;
	private String noteCreated;

	
	public Note() {
		noteID = -1;
	}
	
	public int getNoteID() {
		return noteID ;
	}
	
	public void setNoteID( int i) {
		noteID = i;
	}
	
	public String getNoteSubject() {
		return noteSubject ;
	}
	
	public void setNoteSubject(String s) {
		noteSubject = s;
	}
	
	public String getNoteNote() {
		return noteNote ;
	}
	
	public void setNoteNote(String s) {
		noteNote = s;
	}

	public String getNoteCreated() {
		return noteCreated;
	}
	
	public void setNoteCreated(String s) {
		noteCreated = s;
	}
}
