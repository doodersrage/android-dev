package com.example.mycontactlist;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContactSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_settings);
		initListButton();
        initMapButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_settings, menu);
		return true;
	}

	private void initListButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonList);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContactSettingsActivity.this,
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
				Intent intent = new Intent(ContactSettingsActivity.this,
										ContactMapActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }
    
    private void initSettingsButton(){
    	ImageButton settings = (ImageButton) findViewById(R.id.imageButtonSettings );
    	settings.setEnabled(false);
    }
    
    private void initSettings() {
    	String sortBy = getSharedPreferences("MyContactListPreferences",Context.MODE_PRIVATE ).getString( "sortfield" , "contactname" ); //1
    	String sortOrder = getSharedPreferences("MyContactListPreferences",Context.MODE_PRIVATE ).getString( "sortorder" , "ASC" );
    	RadioButton rbName = (RadioButton) findViewById(R.id. radioName ); //2
    	RadioButton rbCity = (RadioButton) findViewById(R.id. radioCity );
    	RadioButton rbBirthDay = (RadioButton) findViewById(R.id. radioBirthday );
    	
    	if (sortBy.equalsIgnoreCase( "contactname" )) { //3
    		rbName.setChecked( true );
    	}
    	else if (sortBy.equalsIgnoreCase( "city" )) {
    		rbCity.setChecked( true );
    	}
    	else {
    		rbBirthDay.setChecked( true );
    	}
    	
    	RadioButton rbAscending = (RadioButton) findViewById(R.id. radioAscending ); //4
    	RadioButton rbDescending = (RadioButton) findViewById(R.id. radioDescending );
    	
    	if (sortOrder.equalsIgnoreCase( "ASC" )) {
    		rbAscending.setChecked( true );
    	}
    	else {
    		rbDescending.setChecked( true );
    	}
    }
    
    private void initSortByClick() {
    	RadioGroup rgSortBy = (RadioGroup) findViewById(R.id. radioGroup1 );
    	rgSortBy.setOnCheckedChangeListener( new OnCheckedChangeListener() {
    		@Override
    		public void onCheckedChanged(RadioGroup arg0, int arg1) {
    			RadioButton rbName = (RadioButton) findViewById(R.id. radioName );
    			RadioButton rbCity = (RadioButton) findViewById(R.id. radioCity );
    			if (rbName.isChecked()) {
    				getSharedPreferences("MyContactListPreferences", MODE_PRIVATE ).edit().putString( "sortfield" , "contactname" ).commit();
    			}
    			else if (rbCity.isChecked()) {
    				getSharedPreferences("MyContactListPreferences", MODE_PRIVATE ).edit().putString( "sortfield" , "city" ).commit();
    			}
    			else {
    				getSharedPreferences("MyContactListPreferences", MODE_PRIVATE ).edit().putString( "sortfield" , "birthday" ).commit();
    			}
    		}
    	});
    }
    
    private void initSortOrderClick() {
    	RadioGroup rgSortOrder = (RadioGroup) findViewById(R.id.radioGroup2 );
    	rgSortOrder.setOnCheckedChangeListener( new OnCheckedChangeListener() {
    		@Override
    		public void onCheckedChanged(RadioGroup arg0, int arg1) {
    			RadioButton rbAscending = (RadioButton)findViewById(R.id. radioAscending );
    			if (rbAscending.isChecked()) {
    				getSharedPreferences("MyContactListPreferences", MODE_PRIVATE ).edit().putString( "sortorder" , "ASC" ).commit();
    			}
    			else {
    				getSharedPreferences("MyContactListPreferences", MODE_PRIVATE ).edit().putString( "sortorder" , "DESC" ).commit();
    			}
    		}
    	});
    }
}
