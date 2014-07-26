package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DatabaseHelper;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.DivesDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;
import info.sqlite.model.ResultsDB;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseSummary extends Activity implements OnItemSelectedListener {

	private TextView name, meetName, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11,
					s1v, s2v, s3v, s4v, s5v, s6v, s7v, s8v, s9v, s10v, s11v, total,
                    totalView, diveTypeText, diveTypeShow;
    private Spinner spinner;
	private int diverId, meetId, diveTotal, diveType, type, diverSpinnerPosition;
    String typeString;

    @Override
		public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_summary);
		ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();

		Bundle b = getIntent().getExtras();
		diverId = b.getInt("keyDiver");
		meetId = b.getInt("keyMeet");
        diverSpinnerPosition = b.getInt("keySpin");

        getDiveTotals();
		fillText();
        fillType();
        fillScores();
	}

    @Override
    public void onBackPressed(){
        final Context context = this;
        Intent intent = new Intent(context, ChooseDiver.class);
        Bundle b = new Bundle();
        b.putInt("keyDiver", diverId);
        b.putInt("keyMeet", meetId);
        b.putInt("keySpin", diverSpinnerPosition);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void getDiveTotals(){
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        diveTotal = db.searchTotals(meetId, diverId);
    }
	
	private void fillText(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		ArrayList<String> diverInfo;
		diverInfo = db.getDiverInfo(diverId);

        String nameString = diverInfo.get(0);
		name.setText(nameString);
			
		MeetDatabase mdb = new MeetDatabase(getApplicationContext());
		ArrayList<String> meetInfo;
		meetInfo = mdb.getMeetInfo(meetId);

        String meetNameString = meetInfo.get(0);
		meetName.setText(meetNameString);
		
	}	
	
	private void fillScores(){
		ResultDatabase db = new ResultDatabase(getApplicationContext());
		ArrayList<Double> scores;
		scores = db.getResultsList(meetId, diverId);

        String s1String = Double.toString(scores.get(0));
        String s2String = Double.toString(scores.get(1));
        String s3String = Double.toString(scores.get(2));
        String s4String = Double.toString(scores.get(3));
        String s5String = Double.toString(scores.get(4));
        String s6String = Double.toString(scores.get(5));
        String s7String = Double.toString(scores.get(6));
        String s8String = Double.toString(scores.get(7));
        String s9String = Double.toString(scores.get(8));
        String s10String = Double.toString(scores.get(9));
        String s11String = Double.toString(scores.get(10));
		
		ResultsDB result = new ResultsDB();
        Double totalScore = (result.calcScoreTotal(scores.get(0), scores.get(1), scores.get(2),
                scores.get(3), scores.get(4), scores.get(5), scores.get(6),
                scores.get(7), scores.get(8), scores.get(9), scores.get(10)));
        DecimalFormat d = new DecimalFormat("0.00");
        Double totalScore2 = Double.parseDouble(d.format(totalScore));

        String totalString = Double.toString(totalScore2);
		if(!totalString.equals("0.0")){
			totalView.setVisibility(View.VISIBLE);
			total.setVisibility(View.VISIBLE);
			total.setText(totalString);
            diveTypeText.setVisibility(View.VISIBLE);
            diveTypeShow.setVisibility(View.VISIBLE);
            diveTypeShow.setText(typeString);
        }
		if(!s1String.equals("0.0")){
			s1v.setVisibility(View.VISIBLE);
			s1.setVisibility(View.VISIBLE);
			s1.setText(s1String);
		}
		if(!s2String.equals("0.0")){
			s2v.setVisibility(View.VISIBLE);
			s2.setVisibility(View.VISIBLE);
			s2.setText(s2String);
		}
		if(!s3String.equals("0.0")){
			s3v.setVisibility(View.VISIBLE);
			s3.setVisibility(View.VISIBLE);
			s3.setText(s3String);			
		}
		if(!s4String.equals("0.0")){
			s4v.setVisibility(View.VISIBLE);
			s4.setVisibility(View.VISIBLE);
			s4.setText(s4String);
		}
		if(!s5String.equals("0.0")){
			s5v.setVisibility(View.VISIBLE);
			s5.setVisibility(View.VISIBLE);
			s5.setText(s5String);
		}
		if(!s6String.equals("0.0")){
			s6v.setVisibility(View.VISIBLE);
			s6.setVisibility(View.VISIBLE);
			s6.setText(s6String);
            if(diveTotal == 6){
                //spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        "Congratulations, all six dives are complete," +
                                " total score is " + totalString,
                        Toast.LENGTH_LONG).show();
                spinner.setEnabled(false);
                return;
            }
		}
		if(!s7String.equals("0.0")){
			s7v.setVisibility(View.VISIBLE);
			s7.setVisibility(View.VISIBLE);
			s7.setText(s7String);
		}
		if(!s8String.equals("0.0")){
			s8v.setVisibility(View.VISIBLE);
			s8.setVisibility(View.VISIBLE);
			s8.setText(s8String);
		}
		if(!s9String.equals("0.0")){
			s9v.setVisibility(View.VISIBLE);
			s9.setVisibility(View.VISIBLE);
			s9.setText(s9String);
		}
		if(!s10String.equals("0.0")){
			s10v.setVisibility(View.VISIBLE);
			s10.setVisibility(View.VISIBLE);
			s10.setText(s10String);
		}
		if(!s11String.equals("0.0")){
			s11v.setVisibility(View.VISIBLE);
			s11.setVisibility(View.VISIBLE);
			s11.setText(s11String);
            if(diveTotal == 11){
                //spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        "Congratulations, all eleven dives are complete," +
                                " total score is " + totalString,
                        Toast.LENGTH_LONG).show();
                spinner.setEnabled(false);
            }
		}
	}
	
	private void loadSpinnerData(){
		DivesDatabase db = new DivesDatabase(getApplicationContext());
		
		List<String> diveName = db.getDiveNames();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diveName);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   		spinner.setPrompt("Choose a Dive Category");
   		spinner.setAdapter(
   				new NothingSelectedSpinnerAdapter(
   						dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this));
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
        final Context context = this;

		if(id == -1){
			return;
		}
		else{
			switch(position){
			case 1:
                diveType = 1;
				break;
			case 2:
                diveType = 2;
				break;
			case 3:
                diveType = 3;
				break;
			case 4:
                diveType = 4;
				break;
			case 5:
                diveType = 5;
				break;
			}
            Bundle b = new Bundle();
            b.putInt("keyDiver", diverId);
            b.putInt("keyMeet", meetId);
            b.putInt("diveType", diveType);
            b.putInt("boardType", type);
            Intent intent = new Intent(context, Dives.class);
            intent.putExtras(b);
            startActivity(intent);
		}		
	}

    private void fillType(){
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        type = db.getType(meetId, diverId);
        typeString = type + " Meters";
    }

    private void setUpView(){
        name = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        s1 = (TextView)findViewById(R.id.score1);
        s2 = (TextView)findViewById(R.id.score2);
        s3 = (TextView)findViewById(R.id.score3);
        s4 = (TextView)findViewById(R.id.score4);
        s5 = (TextView)findViewById(R.id.score5);
        s6 = (TextView)findViewById(R.id.score6);
        s7 = (TextView)findViewById(R.id.score7);
        s8 = (TextView)findViewById(R.id.score8);
        s9 = (TextView)findViewById(R.id.score9);
        s10 = (TextView)findViewById(R.id.score10);
        s11 = (TextView)findViewById(R.id.score11);
        s1v = (TextView)findViewById(R.id.score1view);
        s2v = (TextView)findViewById(R.id.score2view);
        s3v = (TextView)findViewById(R.id.score3view);
        s4v = (TextView)findViewById(R.id.score4view);
        s5v = (TextView)findViewById(R.id.score5view);
        s6v = (TextView)findViewById(R.id.score6view);
        s7v = (TextView)findViewById(R.id.score7view);
        s8v = (TextView)findViewById(R.id.score8view);
        s9v = (TextView)findViewById(R.id.score9view);
        s10v = (TextView)findViewById(R.id.score10view);
        s11v = (TextView)findViewById(R.id.score11view);
        total = (TextView)findViewById(R.id.scoreTotal);
        totalView = (TextView)findViewById(R.id.total2view);
        diveTypeText = (TextView)findViewById(R.id.diveType);
        diveTypeShow = (TextView)findViewById(R.id.theType);
        spinner = (Spinner)findViewById(R.id.spinnerDiveCatC);
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_choose_summary, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
