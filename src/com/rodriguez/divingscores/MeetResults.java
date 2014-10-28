package com.rodriguez.divingscores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class MeetResults extends ActionBarActivity {
	
	private TextView name, school, city, state, date;
    private ListView myList;
    private int diverId, meetId, diveNumber;
    private String stringId;

	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_results);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
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
        ArrayList<String> meetInfo;
        GetMeetInfo info = new GetMeetInfo();
		meetInfo = info.doInBackground();
		
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
		ArrayList<String> diverInfo;
        GetDiverHistory his = new GetDiverHistory();
		diverInfo = his.doInBackground();

		ArrayAdapter<String> adapter = new ArrayAdapter<>(
			this, R.layout.list_item, diverInfo);
		myList.setAdapter(adapter);
		myList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                stringId = myList.getItemAtPosition(position).toString();
                GetDiverId diveid = new GetDiverId();
                diverId = diveid.doInBackground();
                getDiveNumber();
                if(diveNumber == 0){
                    Toast.makeText(getApplicationContext(),
                            "Diver has no scores at this meet yet",
                            Toast.LENGTH_LONG).show();
                } else {
                    stringId = myList.getItemAtPosition(position).toString();
                    diverId = diveid.doInBackground();
                    Intent intent = new Intent(getBaseContext(), MeetScores.class);
                    Bundle b = new Bundle();
                    b.putInt("key", diverId);
                    b.putInt("key2", meetId);
                    intent.putExtras(b);
                    startActivity(intent);
                }
			}			
		});	
	}

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        diveNumber = num.doInBackground();
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
            case R.id.menu_rankings:
                Intent intent2 = new Intent(context, Rankings.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return meetinfo = db.getMeetInfo(meetId);
        }
    }

    private class GetDiverHistory extends AsyncTask<ArrayList<String>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getDiverHistory(meetId);
        }
    }

    private class GetDiverId extends AsyncTask<String, Integer, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        DiveNumberDatabase db =  new DiveNumberDatabase(getApplicationContext());
        int number;

        @Override
        protected Integer doInBackground(Integer... params) {
            return number = db.getDiveNumber(meetId, diverId);
        }
    }
}
