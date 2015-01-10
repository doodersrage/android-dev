package com.example.mycontactlist;
import java.util.ArrayList;

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

public class ContactListActivity extends ListActivity{

	boolean isDeleting = false;
	ContactAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		initListButton();
        initMapButton();
        initSettingsButton();
        initDeleteButton();
        initAddContactButton();
        
        // init activity
        String sortBy = getSharedPreferences("MyContactListPreferences",Context. MODE_PRIVATE ).getString( "sortfield" , "contactname" );
        String sortOrder = getSharedPreferences("MyContactListPreferences",Context. MODE_PRIVATE ).getString( "sortorder" , "ASC" );
        ContactDataSource ds = new ContactDataSource( this );
        ds.open();
        final ArrayList<Contact> contacts = ds.getContacts(sortBy, sortOrder);
        ds.close();
        adapter = new ContactAdapter( this , contacts);
        setListAdapter( adapter );
        
        // respond to item click
        ListView listView = getListView();
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
        		Contact selectedContact = contacts.get(position);
        		if ( isDeleting ) {
        			adapter .showDelete(position, itemClicked, ContactListActivity. this ,selectedContact);
        		}
        		else {
        			Intent intent = new Intent(ContactListActivity. this ,ContactActivity. class );
        			intent.putExtra( "contactid" , selectedContact.getContactID());
        			startActivity(intent);
        		}
        	}
        });
	}
	
	public void onResume() {
		super .onResume();
		String sortBy = getSharedPreferences( "MyContactListPreferences" ,Context. MODE_PRIVATE ).getString( "sortfield" , "contactname" );
		String sortOrder = getSharedPreferences( "MyContactListPreferences" ,Context. MODE_PRIVATE ).getString( "sortorder" , "ASC" );
		ContactDataSource ds = new ContactDataSource( this );
		ds.open();
		final ArrayList<Contact> contacts = ds.getContacts(sortBy, sortOrder);
		ds.close();
		adapter = new ContactAdapter( this , contacts);
		setListAdapter( adapter );
		
		if (contacts.size() > 0) { //1
			adapter = new ContactAdapter( this , contacts);
			setListAdapter( adapter );
			ListView listView = getListView();
			listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
					Contact selectedContact = contacts.get(position);
					if ( isDeleting ) {
						adapter .showDelete(position, itemClicked, ContactListActivity. this , selectedContact);
					}
					else {
						Intent intent = new Intent(ContactListActivity.this , ContactActivity. class );
						intent.putExtra( "contactid" , selectedContact.getContactID());
						startActivity(intent);
					}
				}
			});
		} //2
		else {
			Intent intent = new Intent(ContactListActivity. this , ContactActivity. class );
			startActivity(intent);
		}
	}

	private void initListButton(){
		ImageButton list = (ImageButton) findViewById(R.id.imageButtonList );
    	list.setEnabled(false);
    }
    
    private void initMapButton(){
    	ImageButton list = (ImageButton) findViewById(R.id.imageButtonMap);
    	list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ContactListActivity.this,
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
				Intent intent = new Intent(ContactListActivity.this,
										ContactSettingsActivity.class);
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
    
    private void initAddContactButton() {
    	Button newContact = (Button) findViewById(R.id. buttonAdd );
    	newContact.setOnClickListener( new OnClickListener() {
    		@Override
			public void onClick(View v) {
    			Intent intent = new Intent(ContactListActivity. this , ContactActivity. class );
    			startActivity(intent);
    		}
    	});
    }
}

