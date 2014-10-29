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

import java.util.ArrayList;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class DiverHistory extends ActionBarActivity {

    private ListView myList;
	private TextView name, age, grade, school;
    private int diverId, meetId, diveNumber;
    private String stringId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_history);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);
        name = (TextView)findViewById(R.id.nameHistory);
        age = (TextView)findViewById(R.id.ageHistory);
        grade = (TextView)findViewById(R.id.gradeHistory);
        school = (TextView)findViewById(R.id.schoolHistory);              
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");
        fillText();
        
        populateListViewFromDB();

        Toast.makeText(getApplicationContext(),
    			"Click a meet to see the scores",
    			Toast.LENGTH_LONG).show();		
    }	
		
	public void fillText(){
        ArrayList<String> diverInfo;
		GetDiverInfo info = new GetDiverInfo();
		diverInfo = info.doInBackground();
			
		if(!diverInfo.isEmpty()){
            String nameString = diverInfo.get(0);
            String ageString = diverInfo.get(1);
            String gradeString = diverInfo.get(2);
            String schoolString = diverInfo.get(3);
		
			name.setText(nameString);		
			age.setText(ageString);		
			grade.setText(gradeString);		
			school.setText(schoolString);	
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Diver is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	private void populateListViewFromDB() {
        ArrayList<String> meetInfo;
        GetMeetHistory history = new GetMeetHistory();
		meetInfo = history.doInBackground();

		ArrayAdapter<String> adapter = new ArrayAdapter<>(
			this, R.layout.list_item, meetInfo);
		myList.setAdapter(adapter);
		myList.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                stringId = myList.getItemAtPosition(position).toString();
                GetMeetId ID = new GetMeetId();
                meetId = ID.doInBackground(stringId);
                getDiveNumber();
                if(diveNumber == 0){
                    Toast.makeText(getApplicationContext(),
                            "Diver has no scores at this meet yet",
                            Toast.LENGTH_LONG).show();
                } else {
                    stringId = myList.getItemAtPosition(position).toString();
                    meetId = ID.doInBackground(stringId);
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
        getMenuInflater().inflate(R.menu.activity_diver_history, menu);
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

    private class GetDiverInfo extends AsyncTask<ArrayList<String>, Object, Object> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return diverinfo = db.getDiverInfo(diverId);
        }
    }

    private class GetMeetHistory extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getMeetHistory(diverId);
        }
    }

    private class GetMeetId extends AsyncTask<String, Integer, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
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
