package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Choose extends Activity implements OnItemSelectedListener {

    private Spinner spinnerMeet;
	private int meetSpinPosition, meetId = 0;
	
	@Override
		public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose); 
		ActionBar actionBar = getActionBar();
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
	    MeetDatabase db = new MeetDatabase(getApplicationContext());
	    
		List<String> meetName = db.getMeetNames();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, meetName);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		spinnerMeet.setPrompt("Choose Meet");
 		spinnerMeet.setAdapter(
 				new NothingSelectedSpinnerAdapter(
 						dataAdapter, R.layout.meet_name_spinner_row_nothing_selected, this));
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
	   String stringId;
       int id;
	   stringId = spinnerMeet.getSelectedItem().toString();
	   MeetDatabase db = new MeetDatabase(getApplicationContext());
	   id = db.getId(stringId);
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

        }
        return super.onOptionsItemSelected(item);
    }	
	
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		// TODO Auto-generated method stub		
	}		
}
