package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;

import java.util.ArrayList;
import java.util.List;
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
import android.widget.Spinner;
import android.widget.Toast;

public class Welcome extends Activity implements OnItemSelectedListener
{
    private Spinner spinnerName, spinnerMeet;
    private Button diverHistory, diverEdit, diverDelete, meetResult, meetEdit, meetDelete,
                    btnNext, btnNewDiver, btnNewMeet, btnReports;
    private View layout2, layout3;
	private int diveCount = 0, meetCount = 0, diverId = 0, meetId = 0;
	private boolean diverCheck = false, meetCheck = false;

    final Context context = this;

   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        addListenerOnButton();
        loadSpinnerName();
        loadSpinnerMeet();
        spinnerName.setOnItemSelectedListener(this);
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
		dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
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
		dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
		spinnerMeet.setAdapter(
				new NothingSelectedSpinnerAdapter(
						dataAdapter, R.layout.meet_name_spinner_row_nothing_selected, this));
	}

  	public void addListenerOnButton()
  	{
        DiverDatabase ddb = new DiverDatabase(getApplicationContext());
        diverCheck = ddb.checkDiver();
        MeetDatabase mdb = new MeetDatabase(getApplicationContext());
        meetCheck = mdb.checkMeet();
	   btnNext.setOnClickListener(new OnClickListener() {
           public void onClick(View arg0) {

               if(diverCheck && meetCheck) {
                   Intent intent = new Intent(context, Choose.class);
                   startActivity(intent);
               } else {
                   if(!diverCheck && !meetCheck) {
                       Toast.makeText(getApplicationContext(),
                               "Please add a Diver and a Meet",
                               Toast.LENGTH_LONG).show();
                       return;
                   }
                   if(!diverCheck){
                       Toast.makeText(getApplicationContext(),
                               "Please add a diver before starting a meet",
                               Toast.LENGTH_LONG).show();
                       return;
                   }
                   Toast.makeText(getApplicationContext(),
                           "There have been no meets added yet",
                           Toast.LENGTH_LONG).show();
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

        btnReports.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
                boolean validMeet = db.checkJudgesScores();
                if (validMeet) {
                    Intent intent = new Intent(context, Reports.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "No scores have been entered, so no reports can be generated",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
  	} 	
	
	public void onItemSelected(AdapterView<?> parent, View view, final int position,
			long id) {
		
		Spinner spinner = (Spinner) parent;
		if(spinner.getId() == R.id.spinnerDiverNameW && position >= 1){

			String nameSpinner = "name";
			diverId = getDiverId(nameSpinner);
			if(diveCount == 0){
				// counter used to keep track if the spinner has been hit and only adds the buttons once
				diveCount ++;
                layout2.setVisibility(View.VISIBLE);
				
				diverHistory.setOnClickListener(new OnClickListener(){

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
				
				diverEdit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), DiverEdit.class);
						Bundle b = new Bundle();
						b.putInt("key", diverId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
				
				diverDelete.setOnClickListener(new OnClickListener(){

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

			String meetSpinner = "meet";
			meetId = getDiverId(meetSpinner);
			
			if(meetCount == 0){
				// counter used to keep track if the spinner has been hit and only adds the buttons once
				meetCount ++;
                layout3.setVisibility(View.VISIBLE);
			
				meetResult.setOnClickListener(new OnClickListener(){

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
				
				meetEdit.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getBaseContext(), MeetEdit.class);
						Bundle b = new Bundle();
						b.putInt("key", meetId);
						intent.putExtras(b);
						startActivity(intent);				
					}			
				});	
				
				meetDelete.setOnClickListener(new OnClickListener(){

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
			stringId = spinnerName.getSelectedItem().toString().trim();
			DiverDatabase db = new DiverDatabase(getApplicationContext());
			id = db.getId(stringId);
		}
		else
		{
			stringId = spinnerMeet.getSelectedItem().toString().trim();
			MeetDatabase db = new MeetDatabase(getApplicationContext());
			id = db.getId(stringId);
		}		
		return id;
	}

    private void setUpView(){
        spinnerName = (Spinner)findViewById(R.id.spinnerDiverNameW);
        spinnerMeet = (Spinner)findViewById(R.id.spinnerMeetNameW);
        btnNext = (Button) findViewById(R.id.buttonStartNewMeet);
        btnNewDiver = (Button) findViewById(R.id.buttonNewDiver);
        btnNewMeet = (Button) findViewById(R.id.buttonNewMeet);
        diverHistory = (Button) findViewById(R.id.buttonDiverHistory);
        diverEdit = (Button) findViewById(R.id.buttonDiverEdit);
        diverDelete = (Button) findViewById(R.id.buttonDiverDelete);
        meetResult = (Button) findViewById(R.id.buttonMeetHistory);
        meetEdit = (Button) findViewById(R.id.buttonMeetEdit);
        meetDelete = (Button) findViewById(R.id.buttonMeetDelete);
        btnReports = (Button)findViewById(R.id.buttonReports);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
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
