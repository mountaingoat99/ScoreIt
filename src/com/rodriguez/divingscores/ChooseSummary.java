package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.DivesDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.PlatformDivesDatabase;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseSummary extends Activity implements OnItemSelectedListener {

	private TextView name, meetName, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11,
					s1v, s2v, s3v, s4v, s5v, s6v, s7v, s8v, s9v, s10v, s11v, total,
                    totalView, diveTypeText, diveTypeShow, header;
    private Spinner spinner;
    private Button judgeButton, totalbutton;
	private int diverId, meetId, diveTotal, diveType,diverSpinnerPosition, diveNumber;
    private double boardType;
    private String s1String, s2String, s3String, s4String, s5String, s6String, s7String,
            s8String, s9String, s10String, s11String;
    private String typeString, noDive = "There are no scores entered yet.";
    private Boolean failed, dive6 = false, dive11 = false;
    private Double totalScore;

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

		Bundle b = getIntent().getExtras();
		diverId = b.getInt("keyDiver");
		meetId = b.getInt("keyMeet");
        diverSpinnerPosition = b.getInt("keySpin");
        fillType();
        loadSpinnerData();
        getDiveTotals();
        getDiveNumber();
		fillText();
        fillScores();
        addListenerOnButton();
        if(boardType == 1.0 || boardType == 3.0){
            header.setText("SpringBoard Dives");
        } else {
            header.setText("Platform Dives");
        }
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

    public void addListenerOnButton(){
        final Context context = this;

        judgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diveType > 0) {
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveType", diveType);
                    b.putDouble("boardType", boardType);
                    Intent intent = new Intent(context, Dives.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please choose a dive category!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        totalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diveType > 0) {
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveType", diveType);
                    b.putDouble("boardType", boardType);
                    Intent intent = new Intent(context, EnterFinalDiveScore.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please choose a dive category!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        if(boardType == 1 || boardType == 3) {
            switch (position) {
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
        } else {
            switch (position) {
                case 1:
                    diveType = 6;
                    break;
                case 2:
                    diveType = 7;
                    break;
                case 3:
                    diveType = 8;
                    break;
                case 4:
                    diveType = 9;
                    break;
                case 5:
                    diveType = 10;
                    break;
                case 6:
                    diveType = 11;
                    break;
            }
        }
    }

    private void getDiveTotals(){
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        diveTotal = db.searchTotals(meetId, diverId);
    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber = db.getDiveNumber(meetId, diverId);
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
        getScoresFromDB();
        String failDive = "F";
        int numberOfDive;

        DecimalFormat d = new DecimalFormat("0.00");
        Double totalScore2 = Double.parseDouble(d.format(totalScore));

        String totalString = Double.toString(totalScore2);
		if(diveNumber != 0){
			totalView.setVisibility(View.VISIBLE);
			total.setVisibility(View.VISIBLE);
			total.setText(totalString);
            diveTypeText.setVisibility(View.VISIBLE);
            diveTypeShow.setVisibility(View.VISIBLE);
            diveTypeShow.setText(typeString);
        } else {
            totalView.setVisibility(View.GONE);
            diveTypeText.setVisibility(View.GONE);
            diveTypeShow.setVisibility(View.GONE);
            total.setVisibility(View.VISIBLE);
            total.setText(noDive);
        }
        numberOfDive = 1;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s1v.setVisibility(View.VISIBLE);
            s1.setVisibility(View.VISIBLE);
            s1.setText(failDive);
        }else {
            if(diveNumber >= 1){
			    s1v.setVisibility(View.VISIBLE);
			    s1.setVisibility(View.VISIBLE);
                s1.setText(s1String);
            }
		}
        numberOfDive = 2;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s2v.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s2.setText(failDive);
        }else {
            if(diveNumber >= 2){
                s2v.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s2.setText(s2String);
            }
        }
        numberOfDive = 3;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s3v.setVisibility(View.VISIBLE);
            s3.setVisibility(View.VISIBLE);
            s3.setText(failDive);
        }else {
            if(diveNumber >= 3){
                s3v.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s3.setText(s3String);
            }
        }
        numberOfDive = 4;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s4v.setVisibility(View.VISIBLE);
            s4.setVisibility(View.VISIBLE);
            s4.setText(failDive);
        }else {
            if(diveNumber >= 4){
                s4v.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s4.setText(s4String);
            }
        }
        numberOfDive = 5;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s5v.setVisibility(View.VISIBLE);
            s5.setVisibility(View.VISIBLE);
            s5.setText(failDive);
        }else {
            if(diveNumber >= 5){
                s5v.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s5.setText(s5String);
            }
        }
        numberOfDive = 6;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s6v.setVisibility(View.VISIBLE);
            s6.setVisibility(View.VISIBLE);
            s6.setText(failDive);
        }else {
            if(diveNumber >= 6){
                s6v.setVisibility(View.VISIBLE);
                s6.setVisibility(View.VISIBLE);
                s6.setText(s6String);
                //dive6 = true;
            }
        }
        //if(dive6 && diveTotal == 6){    //TODO remove these after test fail on last dive
        if(diveNumber == 6 && diveTotal == 6){
            Toast.makeText(getApplicationContext(),
                    "Congratulations, all six dives are complete," +
                            " total score is " + totalString,
                    Toast.LENGTH_LONG).show();
            judgeButton.setVisibility(View.GONE);
            totalbutton.setVisibility(View.GONE);
            spinner.setEnabled(false);
            return;
        }
        numberOfDive = 7;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s7v.setVisibility(View.VISIBLE);
            s7.setVisibility(View.VISIBLE);
            s7.setText(failDive);
        }else {
            if(diveNumber >= 7){
                s7v.setVisibility(View.VISIBLE);
                s7.setVisibility(View.VISIBLE);
                s7.setText(s7String);
            }
        }
        numberOfDive = 8;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s8v.setVisibility(View.VISIBLE);
            s8.setVisibility(View.VISIBLE);
            s8.setText(failDive);
        }else {
            if(diveNumber >= 8){
                s8v.setVisibility(View.VISIBLE);
                s8.setVisibility(View.VISIBLE);
                s8.setText(s8String);
            }
        }
        numberOfDive = 9;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s9v.setVisibility(View.VISIBLE);
            s9.setVisibility(View.VISIBLE);
            s9.setText(failDive);
        }else {
            if(diveNumber >= 9){
                s9v.setVisibility(View.VISIBLE);
                s9.setVisibility(View.VISIBLE);
                s9.setText(s9String);
            }
        }
        numberOfDive = 10;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s10v.setVisibility(View.VISIBLE);
            s10.setVisibility(View.VISIBLE);
            s10.setText(failDive);
        }else {
            if(diveNumber >= 10){
                s10v.setVisibility(View.VISIBLE);
                s10.setVisibility(View.VISIBLE);
                s10.setText(s10String);
            }
        }
        numberOfDive = 11;
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            s11v.setVisibility(View.VISIBLE);
            s11.setVisibility(View.VISIBLE);
            s11.setText(failDive);
        }else {
            if(diveNumber == 11){
                s11v.setVisibility(View.VISIBLE);
                s11.setVisibility(View.VISIBLE);
                s11.setText(s11String);
                //dive11 = true;
            }
        }
            //if(dive11 && diveTotal == 11){
            if(diveNumber == 11 && diveTotal == 11){
                //spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),
                        "Congratulations, all eleven dives are complete," +
                                " total score is " + totalString,
                        Toast.LENGTH_LONG).show();
                judgeButton.setVisibility(View.GONE);
                totalbutton.setVisibility(View.GONE);
                spinner.setEnabled(false);
            }
		}

    public void getScoresFromDB(){
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        ArrayList<Double> scores;
        scores = db.getResultsList(meetId, diverId);
        s1String = Double.toString(scores.get(0));
        s2String = Double.toString(scores.get(1));
        s3String = Double.toString(scores.get(2));
        s4String = Double.toString(scores.get(3));
        s5String = Double.toString(scores.get(4));
        s6String = Double.toString(scores.get(5));
        s7String = Double.toString(scores.get(6));
        s8String = Double.toString(scores.get(7));
        s9String = Double.toString(scores.get(8));
        s10String = Double.toString(scores.get(9));
        s11String = Double.toString(scores.get(10));

        ResultsDB result = new ResultsDB();
        totalScore = (result.calcScoreTotal(scores.get(0), scores.get(1), scores.get(2),
                scores.get(3), scores.get(4), scores.get(5), scores.get(6),
                scores.get(7), scores.get(8), scores.get(9), scores.get(10)));
    }

    public boolean checkFailedDive(int numberDive){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        return failed = db.checkFailed(meetId, diverId, numberDive);
    }
	
	private void loadSpinnerData(){
        if(boardType == 1 || boardType == 3) {
            DivesDatabase db = new DivesDatabase(getApplicationContext());

            List<String> diveName = db.getDiveNames();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);

            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this)
            );
        } else {
            PlatformDivesDatabase db = new PlatformDivesDatabase(getApplicationContext());
            List<String> diveName = db.getPlatformDiveNames();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);

            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this)
            );
        }
		
	}

    private void fillType(){
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        boardType = db.getType(meetId, diverId);
        int b = (int) boardType;
        typeString = b + " Meters";
    }

    private void setUpView(){
        header = (TextView)findViewById(R.id.TextViewChooseC);
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
        judgeButton = (Button)findViewById(R.id.buttonJudgeScore);
        totalbutton = (Button)findViewById(R.id.buttonTotalScore);
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
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_change_dive_score:
                if(diveNumber == 0){
                    Toast.makeText(getApplicationContext(),
                            "This Diver has no scores yet",
                            Toast.LENGTH_LONG).show();
                    break;
                } else {
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    Intent intent = new Intent(context, ChangeDiveScore.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
            case R.id.menu_rankings:
                Intent intent2 = new Intent(context, Rankings.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

}
