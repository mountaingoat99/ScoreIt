package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.model.DiverTotalDB;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends Activity implements OnItemSelectedListener
{
    private Spinner spinnerName, spinnerMeet;
	private int diveCount = 0, meetCount = 0, diverId = 0, meetId = 0;
	private boolean diverCheck = false, meetCheck = false;

    final Context context = this;

   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        addListenerOnButton();

        spinnerName = (Spinner)findViewById(R.id.spinnerDiverNameW);
        loadSpinnerName();
        spinnerName.setOnItemSelectedListener(this);

        spinnerMeet = (Spinner)findViewById(R.id.spinnerMeetNameW);
        loadSpinnerMeet();
        spinnerMeet.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        // do not allow any back presses on the Welcome screen to other activities
        // just exit the app
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

   	private void loadSpinnerName(){
		//DatabaseHelper db = new DatabaseHelper(getApplicationContext());
	   DiverDatabase db = new DiverDatabase(getApplicationContext());
		
		List<String> diverName = db.getDiverNames();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
				R.layout.spinner_item, diverName);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		spinnerName.setPrompt("Choose Diver");
 		spinnerName.setAdapter(
 				new NothingSelectedSpinnerAdapter(
 						dataAdapter, R.layout.diver_name_spinner_row_nothing_selected, this));
		
	}
  
   private void loadSpinnerMeet(){
		//DatabaseHelper db = new DatabaseHelper(getApplicationContext());
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
        Button btnNext = (Button) findViewById(R.id.buttonStartNewMeet);
        Button btnNewDiver = (Button) findViewById(R.id.buttonNewDiver);
        Button btnNewMeet = (Button) findViewById(R.id.buttonNewMeet);
	   btnNext.setOnClickListener(new OnClickListener() {
           public void onClick(View arg0) {
               DiverDatabase ddb = new DiverDatabase(getApplicationContext());
               diverCheck = ddb.checkDiver();
               MeetDatabase mdb = new MeetDatabase(getApplicationContext());
               meetCheck = mdb.checkMeet();
               if(diverCheck && meetCheck) {
                   Intent intent = new Intent(context, Choose.class);
                   startActivity(intent);
               } else {
                   if(!diverCheck && !meetCheck) {
                       Toast.makeText(getApplicationContext(),
                               "Please add a Diver and a Meet before starting a meet",
                               Toast.LENGTH_LONG).show();
                       return;
                   }
                   if(!diverCheck){
                       Toast.makeText(getApplicationContext(),
                               "Please add a diver before starting a meet",
                               Toast.LENGTH_LONG).show();
                       return;
                   }
                   if(!meetCheck) {
                       Toast.makeText(getApplicationContext(),
                               "Please add a Meet before starting a meet",
                               Toast.LENGTH_LONG).show();
                   }
               }
           }
       });
	   
	   btnNewDiver.setOnClickListener(new OnClickListener() {
           public void onClick(View arg0) {
               Intent intent = new Intent(context, EnterDiver.class);
               startActivity(intent);
           }
       });
	   
	   btnNewMeet.setOnClickListener(new OnClickListener() {
           public void onClick(View arg0) {
               Intent intent = new Intent(context, EnterMeet.class);
               startActivity(intent);
           }
       });
  	} 	
	
	public void onItemSelected(AdapterView<?> parent, View view, final int position,
			long id) {

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        boolean largeTablet = getResources().getBoolean(R.bool.isLargeTablet);
		
		Spinner spinner = (Spinner) parent;
		if(spinner.getId() == R.id.spinnerDiverNameW && position >= 1){
			
			// gets the string from the spinner to run query's against the db
			String nameSpinner = "name";
			diverId = getDiverId(nameSpinner);
			if(diveCount == 0){
				// counter used to keep track if the spinner has been hit and only adds the buttons once
				diveCount ++;
				// history button
				Button history = new Button(this);
                if(tabletSize){
                    history.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    history.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				history.setText("History");
				
				// edit button
				Button edit = new Button(this);
                if(tabletSize){
                    edit.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    edit.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				edit.setText("Edit");
				
				// delete button
				Button delete = new Button(this);
                if(tabletSize){
                    delete.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    delete.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				delete.setText("Delete");			
			
				// history button
				LinearLayout diverLL = (LinearLayout)findViewById(R.id.diverBtnLayout);
				LinearLayout.LayoutParams hlp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				hlp.weight = 1;
				hlp.width = LayoutParams.WRAP_CONTENT;
				hlp.height = LayoutParams.WRAP_CONTENT;
				diverLL.addView(history, hlp);
				// edit button
				LinearLayout editLL = (LinearLayout)findViewById(R.id.diverBtnLayout);
				LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				elp.weight = 1;
				elp.width = LayoutParams.WRAP_CONTENT;
				elp.height = LayoutParams.WRAP_CONTENT;
				editLL.addView(edit, elp);
				// delete button
				LinearLayout deleteLL = (LinearLayout)findViewById(R.id.diverBtnLayout);
				LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				dlp.weight = 1;
				dlp.width = LayoutParams.WRAP_CONTENT;
				dlp.height = LayoutParams.WRAP_CONTENT;
				deleteLL.addView(delete, dlp);
				
				history.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
                        MeetDatabase db = new MeetDatabase(getApplicationContext());
                        ArrayList<String> meetInfo;
                        meetInfo = db.getMeetHistory(diverId);
                        if(!meetInfo.isEmpty()) {
                            Intent intent = new Intent(getBaseContext(), DiverHistory.class);
                            Bundle b = new Bundle();
                            b.putInt("key", diverId);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Diver has not been in any meets yet",
                                    Toast.LENGTH_LONG).show();
                        }
					}			
				});	
				
				edit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), DiverEdit.class);
						Bundle b = new Bundle();
						b.putInt("key", diverId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
				
				delete.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), DiverDelete.class);
						Bundle b = new Bundle();
						b.putInt("key", diverId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
			}	
		}	
		
		if(spinner.getId() == R.id.spinnerMeetNameW && position >= 1){
			
			// gets the string from the spinner to run query's against the db
			String meetSpinner = "meet";
			meetId = getDiverId(meetSpinner);
			
			if(meetCount == 0){
				// counter used to keep track if the spinner has been hit and only adds the buttons once
				meetCount ++;
				// history button
				Button history = new Button(this);
                //history.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                if(tabletSize){
                    history.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    history.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				history.setText("Results");
				
				// edit button
				Button edit = new Button(this);
                if(tabletSize){
                    edit.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    edit.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				edit.setText("Edit");
				
				// delete button
				Button delete = new Button(this);
                if(tabletSize){
                    delete.setTextSize(getResources().getDimension(R.dimen.phone_button_text));
                } else if(largeTablet){
                    delete.setTextSize(getResources().getDimension(R.dimen.tablet_button_text));
                }
				delete.setText("Delete");			
			
				// history button
				LinearLayout diverLL = (LinearLayout)findViewById(R.id.meetBtnLayout);
				LinearLayout.LayoutParams hlp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				hlp.weight = 1;
				hlp.width = LayoutParams.WRAP_CONTENT;
				hlp.height = LayoutParams.WRAP_CONTENT;
				diverLL.addView(history, hlp);
				// edit button
				LinearLayout editLL = (LinearLayout)findViewById(R.id.meetBtnLayout);
				LinearLayout.LayoutParams elp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				elp.weight = 1;
				elp.width = LayoutParams.WRAP_CONTENT;
				elp.height = LayoutParams.WRAP_CONTENT;
				editLL.addView(edit, elp);
				// delete button
				LinearLayout deleteLL = (LinearLayout)findViewById(R.id.meetBtnLayout);
				LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				dlp.weight = 1;
				dlp.width = LayoutParams.WRAP_CONTENT;
				dlp.height = LayoutParams.WRAP_CONTENT;
				deleteLL.addView(delete, dlp);
			
				history.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
                        DiverDatabase db = new DiverDatabase(getApplicationContext());
                        ArrayList<String> diverInfo;
                        diverInfo = db.getDiverHistory(meetId);
                        if(!diverInfo.isEmpty()) {
                            Intent intent = new Intent(getBaseContext(), MeetResults.class);
                            Bundle b = new Bundle();
                            b.putInt("key", meetId);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Meet has no divers associated with it",
                                    Toast.LENGTH_LONG).show();
                        }
					}			
				});	
				
				edit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), MeetEdit.class);
						Bundle b = new Bundle();
						b.putInt("key", meetId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
				
				delete.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), MeetDelete.class);
						Bundle b = new Bundle();
						b.putInt("key", meetId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
			}			
		}			
	}
	
	public int getDiverId(String spinner){
		String stringId;
        int id;
        if(spinner.equals("name"))
		{
			stringId = spinnerName.getSelectedItem().toString();
			DiverDatabase db = new DiverDatabase(getApplicationContext());
			id = db.getId(stringId);
		}
		else
		{
			stringId = spinnerMeet.getSelectedItem().toString();
			MeetDatabase db = new MeetDatabase(getApplicationContext());
			id = db.getId(stringId);
		}		
		return id;
	}
	
	public void onNothingSelected(AdapterView<?> arg0) 
	{

	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
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
                Intent intent2 = new Intent(context, About.class);
                startActivity(intent2);
                //showDialog();
                break;
            case R.id.menu_rankings:
                Intent intent1 = new Intent(context, Rankings.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
