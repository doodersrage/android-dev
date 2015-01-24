package com.example.mynotelist;

import java.util.ArrayList;

import com.example.mynotelist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {

    private ArrayList<Note> items;
    private Context adapterContext;

    public NoteAdapter(Context context, ArrayList<Note> items) {
            super(context, R.layout.list_item, items);
            adapterContext = context;
            this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
    	try {
            Note Note = items.get(position);
            
            if (v == null) {
            		LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            		v = vi.inflate(R.layout.list_item, null);
            }

            TextView noteSubject = (TextView) v.findViewById(R.id.textSubject);
            TextView noteBody = (TextView) v.findViewById(R.id.textNote);
            ((TextView) v.findViewById(R.id.textCreatedDate)).setText(Note.getNoteCreated());
        	Button b = (Button) v.findViewById(R.id.buttonDelete);
            	
        	noteSubject.setText(Note.getNoteSubject());
        	noteBody.setText(Note.getNoteNote());
            b.setVisibility(View.INVISIBLE);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		e.getCause();
    	}
            return v;
    }
    
    public void showDelete(final int position, final View convertView, final Context context, final Note Note) {
    	View v = convertView;
    	final Button b = (Button) v.findViewById(R.id.buttonDelete);

    	if (b.getVisibility()==View.INVISIBLE) {
    		b.setVisibility(View.VISIBLE);
    		b.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				hideDelete(position, convertView, context);
    				items.remove(Note);
    				deleteOption(Note.getNoteID(), context);
    			}

    		});
    	}
    	else {
			hideDelete(position, convertView, context);
    	}
    }

	private void deleteOption(int NoteToDelete, Context context) {
		NoteDataSource db = new NoteDataSource(context);
		db.open();
		db.deleteNote(NoteToDelete);
		db.close();	
		this.notifyDataSetChanged();
	}
 
	private void hideDelete(int position, View convertView, Context context) {
      View v = convertView;
      final Button b = (Button) v.findViewById(R.id.buttonDelete);
	  b.setVisibility(View.INVISIBLE);
	  b.setOnClickListener(null);
 }

}
