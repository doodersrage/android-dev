package com.example.mynotelist;
import java.util.ArrayList;

import com.example.mynotelist.R;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class NoteListActivity extends ListActivity{

	boolean isDeleting = false;
	NoteAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		initListButton();
        initSettingsButton();
        initDeleteButton();
        initAddNoteButton();
        
        // init activity
        String sortBy = getSharedPreferences("MyNoteListPreferences",Context. MODE_PRIVATE ).getString( "sortfield" , "created" );
        String sortOrder = getSharedPreferences("MyNoteListPreferences",Context. MODE_PRIVATE ).getString( "sortorder" , "ASC" );
        NoteDataSource ds = new NoteDataSource( this );
        ds.open();
        final ArrayList<Note> Notes = ds.getNotes(sortBy, sortOrder);
        ds.close();
        adapter = new NoteAdapter( this , Notes);
        setListAdapter( adapter );
        
        // respond to item click
        ListView listView = getListView();
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
        		Note selectedNote = Notes.get(position);
        		if ( isDeleting ) {
        			adapter .showDelete(position, itemClicked, NoteListActivity. this ,selectedNote);
        		}
        		else {
        			Intent intent = new Intent(NoteListActivity. this ,NoteActivity. class );
        			intent.putExtra( "noteid" , selectedNote.getNoteID());
        			startActivity(intent);
        		}
        	}
        });
	}
	
	public void onResume() {
		super .onResume();
		String sortBy = getSharedPreferences( "MyNoteListPreferences" ,Context. MODE_PRIVATE ).getString( "sortfield" , "created" );
		String sortOrder = getSharedPreferences( "MyNoteListPreferences" ,Context. MODE_PRIVATE ).getString( "sortorder" , "ASC" );
		NoteDataSource ds = new NoteDataSource( this );
		ds.open();
		final ArrayList<Note> Notes = ds.getNotes(sortBy, sortOrder);
		ds.close();
		adapter = new NoteAdapter( this , Notes);
		setListAdapter( adapter );
		
		if (Notes.size() > 0) { //1
			adapter = new NoteAdapter( this , Notes);
			setListAdapter( adapter );
			ListView listView = getListView();
			listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
					Note selectedNote = Notes.get(position);
					if ( isDeleting ) {
						adapter.showDelete(position, itemClicked, NoteListActivity.this , selectedNote);
					}
					else {
						Intent intent = new Intent(NoteListActivity.this , NoteActivity.class );
						intent.putExtra( "noteid" , selectedNote.getNoteID());
						startActivity(intent);
					}
				}
			});
		} //2
		else {
			Intent intent = new Intent(NoteListActivity. this , NoteActivity. class );
			startActivity(intent);
		}
	}

	private void initListButton(){
		ImageButton list = (ImageButton) findViewById(R.id.imageButtonList );
    	list.setEnabled(false);
    }
    
    private void initSettingsButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoteListActivity.this,
										NoteSettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }

    private void initDeleteButton() {
    	final Button deleteButton = (Button) findViewById(R.id. buttonDelete );
    	deleteButton.setOnClickListener( new OnClickListener() {
    		@Override
			public void onClick(View v) {
    			if ( isDeleting ) {
    				deleteButton.setText( "Delete" );
    				isDeleting = false ;
    				//1
    				adapter.notifyDataSetChanged();
    			}
    			else {
    				deleteButton.setText( "Done Deleting" );
    				isDeleting = true ;
    			}
    		}
    	});
    }
    
    private void initAddNoteButton() {
    	Button newNote = (Button) findViewById(R.id. buttonAdd );
    	newNote.setOnClickListener( new OnClickListener() {
    		@Override
			public void onClick(View v) {
    			Intent intent = new Intent(NoteListActivity. this , NoteActivity. class );
    			startActivity(intent);
    		}
    	});
    }
}

