package com.example.mycontactlist;
import com.example.mycontactlist.DatePickerDialog.SaveDateListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ContactActivity extends FragmentActivity implements SaveDateListener{

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    	
    	if(enabled){
    		editName.requestFocus();
    	}
    }

	@Override
	public void didFinishDatePickerDialog(Time selectedTime) {
		TextView birthDay = (TextView) findViewById(R.id. textBirthday );
		birthDay.setText(DateFormat.format ( "MM/dd/yyyy" , selectedTime.toMillis( false )).toString());
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
}
