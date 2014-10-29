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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import info.sqlite.helper.MeetDatabase;

public class Choose extends ActionBarActivity implements OnItemSelectedListener {

    private Spinner spinnerMeet;
	private int meetSpinPosition, meetId = 0;
    private String stringId;
	
	@Override
		public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        spinnerMeet = (Spinner)findViewById(R.id.spinnerMeetName);
        loadSpinnerMeet();
        spinnerMeet.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            meetSpinPosition = b.getInt("keyMeetPosition");
            spinnerMeet.setSelection(meetSpinPosition);
        }

		addListenerOnButton();
	}

    @Override
    public void onBackPressed(){

        final Context context = this;
        Intent intent = new Intent(context, Welcome.class);
        startActivity(intent);
    }
   
   private void loadSpinnerMeet(){
       GetMeetInfo meet = new GetMeetInfo();
       List<String> meetName = meet.doInBackground();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, meetName);
		
		dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        dataAdapter.insert("  Choose a Meet", 0);
        spinnerMeet.setAdapter(dataAdapter);
// 		spinnerMeet.setAdapter(
// 				new NothingSelectedSpinnerAdapter(
// 						dataAdapter, R.layout.meet_name_spinner_row_nothing_selected, this));
	}
   
   public void addListenerOnButton()
   {
	   final Context context = this;
       Button btnNext = (Button) findViewById(R.id.buttonChooseJW);
	   
	   btnNext.setOnClickListener(new OnClickListener() {
           public void onClick(View arg0) {
               if (meetId != 0) {
                   Intent intent = new Intent(context, ChooseDiver.class);
                   Bundle b = new Bundle();
                   b.putInt("keyMeet", meetId);
                   b.putInt("keyMeetPosition", meetSpinPosition);
                   intent.putExtras(b);
                   startActivity(intent);
               } else {
                   Toast.makeText(getApplicationContext(),
                           "Please Choose a Meet",
                           Toast.LENGTH_LONG).show();
               }
           }
       });
   } 
   
   public void onItemSelected(AdapterView<?> parent, View view, final int position,
			long id) {
	   Spinner spinner = (Spinner) parent;
       meetSpinPosition = position;
	   if(spinner.getId() == R.id.spinnerMeetName && position >= 1){
		   meetId = getId();
	   }
	}
   
   public int getId(){
       int id;
	   stringId = spinnerMeet.getSelectedItem().toString().trim();
       GetMeetId ID = new GetMeetId();
       id = ID.doInBackground(stringId);
	   return id;
	}
   
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_choose, menu);
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
            case R.id.menu_enter_diver:
                Intent intent = new Intent(context, EnterDiver.class);
                startActivity(intent);
                break;
            case R.id.menu_enter_meet:
                Intent intent1 = new Intent(context, EnterMeet.class);
                startActivity(intent1);
                break;
            case R.id.menu_rankings:
                Intent intent2 = new Intent(context, Rankings.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }	
	
	public void onNothingSelected(AdapterView<?> arg0) {

	}

    private class GetMeetInfo extends AsyncTask<List<String>, List<String>, List<String>> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        List<String> meetName;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return meetName = db.getMeetNames();
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
}
