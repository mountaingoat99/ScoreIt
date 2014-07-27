package com.rodriguez.divingscores;

import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MeetResults extends Activity {
	
	private TextView name, school, city, state, date;
    private ListView myList;
    private int diverId, meetId;

	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_results);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);
        name = (TextView)findViewById(R.id.nameResults);
        school = (TextView)findViewById(R.id.schoolResults);
        city = (TextView)findViewById(R.id.cityResults);
        state = (TextView)findViewById(R.id.StateResults);
        date = (TextView)findViewById(R.id.dateResults);
        
        Bundle b = getIntent().getExtras();
        meetId = b.getInt("key");
        fillText();
        
        populateListViewFromDB();

        Toast.makeText(getApplicationContext(),
    			"Click a diver to see the scores",
    			Toast.LENGTH_LONG).show();
    }	
	
	public void fillText(){
		MeetDatabase db = new MeetDatabase(getApplicationContext());
		ArrayList<String> meetInfo;
		meetInfo = db.getMeetInfo(meetId);
		
		if(!meetInfo.isEmpty()){
            String nameString = meetInfo.get(0);
            String schoolString = meetInfo.get(1);
            String cityString = meetInfo.get(2);
            String stateString = meetInfo.get(3);
            String dateString = meetInfo.get(4);
			
			// formats the date
			SimpleDateFormat indate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
			SimpleDateFormat outdate = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
			try{
				Date DateString = indate.parse(dateString);
				date.setText(outdate.format(DateString));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
			cityString = cityString + ", ";
			
			name.setText(nameString);
			school.setText(schoolString);
			city.setText(cityString);
			state.setText(stateString);
			
		}else{
			Toast.makeText(getApplicationContext(),
        			"Meet is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	public void populateListViewFromDB(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		ArrayList<String> diverInfo;
		diverInfo = db.getDiverHistory(meetId);
		
		if (!diverInfo.isEmpty()){
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
					this, R.layout.list_item, diverInfo);
			myList.setAdapter(adapter);
		}else{
			Toast.makeText(getApplicationContext(),
        			"Meet has no divers associated with it",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
		
		myList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String stringId = myList.getItemAtPosition(position).toString();
				
				DiverDatabase db = new DiverDatabase(getApplicationContext());				
				diverId = db.getId(stringId);
				
				Intent intent = new Intent(getBaseContext(), MeetScores.class);
				Bundle b = new Bundle();
				b.putInt("key", diverId);
				b.putInt("key2", meetId);
				intent.putExtras(b);
				startActivity(intent);				
			}			
		});	
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_meet_results, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                Intent intent3 = new Intent(context, HowTo.class);
                startActivity(intent3);
                break;
            case R.id.menu_about:
                Intent intent4 = new Intent(context, About.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
