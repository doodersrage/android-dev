package com.example.mycontactlist;
import com.example.mycontactlist.DatePickerDialog.SaveDateListener;

import android.app.Activity;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ContactActivity extends FragmentActivity implements SaveDateListener{
	
	private Contact currentContact;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initListButton();
        initMapButton();
        initSettingsButton();
        initToggleButton();
        setForEditing(false);
        initChangeDateButton();
        currentContact = new Contact();
        initTextChangedEvents();
        initSaveButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    
    private void initListButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContactActivity.this,
										ContactListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }
    
    private void initMapButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContactActivity.this,
										ContactMapActivity.class);
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
				Intent intent = new Intent(ContactActivity.this,
										ContactSettingsActivity.class);
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
    	EditText editName = (EditText) findViewById(R.id.editName);
    	EditText editAddress = (EditText) findViewById(R.id.editAddress);
    	EditText editCity = (EditText) findViewById(R.id.editCity);
    	EditText editState = (EditText) findViewById(R.id.editState);
    	EditText editZipCode = (EditText) findViewById(R.id.editZipCode);
    	EditText editPhone = (EditText) findViewById(R.id.editHome);
    	EditText editCell = (EditText) findViewById(R.id.editCell);
    	EditText editEmail = (EditText) findViewById(R.id.editEMail);
    	Button buttonChange = (Button) findViewById(R.id.buttonBirthday);
    	Button buttonSave = (Button) findViewById(R.id.buttonSave);
    	
    	editName.setEnabled(enabled);
    	editAddress.setEnabled(enabled);
    	editCity.setEnabled(enabled);
    	editState.setEnabled(enabled);
    	editZipCode.setEnabled(enabled);
    	editPhone.setEnabled(enabled);
    	editCell.setEnabled(enabled);
    	editEmail.setEnabled(enabled);
    	buttonChange.setEnabled(enabled);
    	buttonSave.setEnabled(enabled);
    	
    	if (enabled) {
    		editName.requestFocus();
    	}
    	else {
    		ScrollView s = (ScrollView) findViewById(R.id. scrollView1 );
    		s.fullScroll(ScrollView. FOCUS_UP );
    	}
    }

	@Override
	public void didFinishDatePickerDialog(Time selectedTime) {
		TextView birthDay = (TextView) findViewById(R.id. textBirthday );
		birthDay.setText(DateFormat.format ( "MM/dd/yyyy" , selectedTime.toMillis( false )).toString());
		currentContact .setBirthday(selectedTime);
	}
	
	private void initChangeDateButton() {
		Button changeDate = (Button) findViewById(R.id.buttonBirthday );
		changeDate.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager(); //1
				DatePickerDialog datePickerDialog = new DatePickerDialog(); //2
				datePickerDialog.show(fm, "DatePick" ); //3
			}
		});
	}
	
	private void initSaveButton() {
		Button saveButton = (Button) findViewById(R.id. buttonSave );
		saveButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyboard();
				ContactDataSource ds = new ContactDataSource(ContactActivity. this ); //1
				ds.open(); //2
				boolean wasSuccessful = false ; //3
				if ( currentContact.getContactID()==-1) { //4
					wasSuccessful = ds.insertContact( currentContact );
					int newId = ds.getLastContactId();
					currentContact.setContactID(newId);
				}
				else {
					wasSuccessful = ds.updateContact( currentContact );
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
		
		EditText editName = (EditText) findViewById(R.id.editName );
		imm.hideSoftInputFromWindow(editName.getWindowToken(), 0);
		EditText editAddress = (EditText) findViewById(R.id.editAddress );
		imm.hideSoftInputFromWindow(editAddress.getWindowToken(), 0);
		EditText editCity = (EditText) findViewById(R.id.editCity );
		imm.hideSoftInputFromWindow(editCity.getWindowToken(), 0);
		EditText editState = (EditText) findViewById(R.id.editState );
		imm.hideSoftInputFromWindow(editState.getWindowToken(), 0);
		EditText editZipCode = (EditText) findViewById(R.id.editZipCode );
		imm.hideSoftInputFromWindow(editZipCode.getWindowToken(), 0);
		EditText editHome = (EditText) findViewById(R.id.editHome );
		imm.hideSoftInputFromWindow(editHome.getWindowToken(), 0);
		EditText editCell = (EditText) findViewById(R.id.editCell );
		imm.hideSoftInputFromWindow(editCell.getWindowToken(), 0);
		EditText editEMail = (EditText) findViewById(R.id.editEMail );
		imm.hideSoftInputFromWindow(editEMail.getWindowToken(), 0);
	}
	
	private void initTextChangedEvents(){
		final EditText contactName = (EditText) findViewById(R.id.editName ); //1
		contactName.addTextChangedListener( new TextWatcher() { //2
			public void afterTextChanged(Editable s) { //3
				currentContact.setContactName(contactName.getText().toString()); //4
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { //5
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) { //6
				// Auto-generated method stub
			}
		});
		final EditText streetAddress = (EditText) findViewById(R.id.editAddress ); //7
		streetAddress.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setStreetAddress(streetAddress.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText city = (EditText) findViewById(R.id.editCity ); //7
		city.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setCity(city.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText state = (EditText) findViewById(R.id.editState ); //7
		state.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setState(state.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText zipCode = (EditText) findViewById(R.id.editZipCode ); //7
		zipCode.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setZipCode(zipCode.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText home = (EditText) findViewById(R.id.editHome ); //7
		home.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setPhoneNumber(home.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText cell = (EditText) findViewById(R.id.editCell ); //7
		cell.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setCellNumber(cell.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		final EditText eMail = (EditText) findViewById(R.id.editEMail ); //7
		eMail.addTextChangedListener( new TextWatcher() {
			public void afterTextChanged(Editable s) {
				currentContact.setEMail(eMail.getText().toString()); //8
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// Auto-generated method stub
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Auto-generated method stub
			}
		});
		home.addTextChangedListener( new PhoneNumberFormattingTextWatcher());
		cell.addTextChangedListener( new PhoneNumberFormattingTextWatcher());
	}

}
