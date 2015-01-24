package com.example.mynotelist;

import com.example.mynotelist.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NoteActivity extends FragmentActivity{
	
	private Note currentNote;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initListButton();
        initSettingsButton();
        initToggleButton();
        setForEditing(false);
        Bundle extras = getIntent().getExtras();
        
        if (extras != null ) {
        	initnote(extras.getInt( "noteid" ));
        }
        else {
        	currentNote = new Note();
        }
        
        initTextChangedEvents();
        initSaveButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }

    
    private void initListButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoteActivity.this,
										NoteListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }
    
    private void initSettingsButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonSettings);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoteActivity.this,
										NoteSettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }
    
    private void initToggleButton(){
    	final ToggleButton editToggle = (ToggleButton) findViewById(R.id.toggleButtonEdit);
    	editToggle.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View arg0){
    			setForEditing(editToggle.isChecked());
    		}

    	});
    }
    
    private void setForEditing(boolean enabled){
    	EditText editName = (EditText) findViewById(R.id.editSubject);
    	EditText editNote = (EditText) findViewById(R.id.editNote);

    	Button buttonSave = (Button) findViewById(R.id.buttonSave);
    	
    	editName.setEnabled(enabled);
    	editNote.setEnabled(enabled);

    	buttonSave.setEnabled(enabled);
    	
    	if (enabled) {
    		editName.requestFocus();
    	}
    	else {
    		ScrollView s = (ScrollView) findViewById(R.id.scrollView1 );
    		s.fullScroll(View.FOCUS_UP );
    	}
    }
	
	private void initSaveButton() {
		Button saveButton = (Button) findViewById(R.id. buttonSave );
		saveButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyboard();
				NoteDataSource ds = new NoteDataSource(NoteActivity. this ); //1
				ds.open(); //2
				boolean wasSuccessful = false ; //3
				if ( currentNote.getNoteID()==-1) { //4
					wasSuccessful = ds.insertNote( currentNote );
					int newId = ds.getLastNoteId();
					currentNote.setNoteID(newId);
				}
				else {
					wasSuccessful = ds.updateNote( currentNote );
				}
				ds.close(); //5
				if (wasSuccessful) { //6
					ToggleButton editToggle = (ToggleButton) findViewById(R.id. toggleButtonEdit );
					editToggle.toggle();
					setForEditing(false);
				}
			}
		});
	}
	
	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE );
		
		EditText editName = (EditText) findViewById(R.id.editSubject );
		imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
		EditText editNote = (EditText) findViewById(R.id.editNote );
		imm.hideSoftInputFromWindow(editNote.getWindowToken(), 0);

	}
	
	private void initTextChangedEvents(){
		final EditText noteName = (EditText) findViewById(R.id.editSubject ); //1
		noteName.addTextChangedListener( new TextWatcher() { //2
			@Override
			public void afterTextChanged(Editable s) { //3
				currentNote.setNoteSubject(noteName.getText().toString()); //4
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { //5
				// Auto-generated method stub
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { //6
				// Auto-generated method stub
			}
		});
		final EditText streetAddress = (EditText) findViewById(R.id.editNote ); //7
		streetAddress.addTextChangedListener( new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				currentNote.setNoteNote(streetAddress.getText().toString()); //8
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
	}

	private void initnote( int id) {
		//1
		NoteDataSource ds = new NoteDataSource(NoteActivity. this );
		ds.open();
		currentNote = ds.getSpecificNote(id);
		ds.close();
		//2
		EditText editName = (EditText) findViewById(R.id.editSubject );
		EditText editNote = (EditText) findViewById(R.id.editNote );

		//3
		editName.setText( currentNote .getNoteSubject());
		editNote.setText( currentNote .getNoteNote());

	}
}
