package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

public class ChooseSummary extends Activity implements OnItemSelectedListener {

	private TextView name, meetName, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11,
					s1v, s2v, s3v, s4v, s5v, s6v, s7v, s8v, s9v, s10v, s11v, total,
                    totalView, diveTypeText, diveTypeShow, header, diveInfo1, diveInfo2,
                    diveInfo3, diveInfo4, diveInfo5, diveInfo6, diveInfo7, diveInfo8,
                    diveInfo9, diveInfo10, diveInfo11;
    private Spinner spinner;
    private Button judgeButton, totalbutton;
	private int diverId, meetId, diveTotal, diveType,diverSpinnerPosition, diveNumber;
    private double boardType;
    private String s1String, s2String, s3String, s4String, s5String, s6String, s7String,
            s8String, s9String, s10String, s11String;
    private String infoString1, infoString2, infoString3, infoString4, infoString5,
                    infoString6, infoString7, infoString8, infoString9, infoString10, infoString11;
    private String typeString, noDive = "There are no scores entered yet.";
    private Boolean failed;
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

        if(boardType == 1.0 || boardType == 3.0) {
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
        SearchDiveTotals total = new SearchDiveTotals();
        diveTotal = total.doInBackground();
    }

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        diveNumber = num.doInBackground();
    }
	
	private void fillText(){
		ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
		diverInfo = info.doInBackground();

        String nameString = diverInfo.get(0);
		name.setText(nameString);
			
		ArrayList<String> meetInfo;
        GetMeetInfo infoMeet = new GetMeetInfo();
		meetInfo = infoMeet.doInBackground();

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
        infoString1 = getDiveInfoFromDB(1);
        diveInfo1.setText(infoString1);
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
        infoString2 = getDiveInfoFromDB(2);
        diveInfo2.setText(infoString2);
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
        infoString3 = getDiveInfoFromDB(3);
        diveInfo3.setText(infoString3);
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
        infoString4 = getDiveInfoFromDB(4);
        diveInfo4.setText(infoString4);
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
        infoString5 = getDiveInfoFromDB(5);
        diveInfo5.setText(infoString5);
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
        infoString6 = getDiveInfoFromDB(6);
        diveInfo6.setText(infoString6);
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
        infoString7 = getDiveInfoFromDB(7);
        diveInfo7.setText(infoString7);
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
        infoString8 = getDiveInfoFromDB(8);
        diveInfo8.setText(infoString8);
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
        infoString9 = getDiveInfoFromDB(9);
        diveInfo9.setText(infoString9);
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
        infoString10 = getDiveInfoFromDB(10);
        diveInfo10.setText(infoString10);
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
        infoString11 = getDiveInfoFromDB(11);
        diveInfo11.setText(infoString11);
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
        ArrayList<Double> scores;
        GetScoresFromDB scoreList = new GetScoresFromDB();
        scores = scoreList.doInBackground();
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

        // this isn't actually a call to the database, it's just using the db helper class
        // to total the results
        ResultsDB result = new ResultsDB();
        totalScore = (result.calcScoreTotal(scores.get(0), scores.get(1), scores.get(2),
                scores.get(3), scores.get(4), scores.get(5), scores.get(6),
                scores.get(7), scores.get(8), scores.get(9), scores.get(10)));
    }

    public String getDiveInfoFromDB(int diveNum){
        int dash;
        String number, position, dd;
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<String> info;
        info = db.getCatAndName(meetId, diverId, diveNum);
        if (info.size() > 0) {
            number = info.get(1);
            dash = number.indexOf("-");
            number = number.substring((0), (dash)).trim();
            position = info.get(2);
            dash = position.indexOf("-");
            position = position.substring((0), (dash)).trim();
            dd = info.get(3);

            return number + position + " - DD: " + dd;
        }

        return "";
    }

    public boolean checkFailedDive(int numberDive){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        return failed = db.checkFailed(meetId, diverId, numberDive);
    }
	
	private void loadSpinnerData(){
        if(boardType == 1.0 || boardType == 3.0) {
            GetSpringboardDiveName dives = new GetSpringboardDiveName();
            List<String> diveName = dives.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);

            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this)
            );
        } else {
            GetPlatformDiveName divesP = new GetPlatformDiveName();
            List<String> diveName = divesP.doInBackground();

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
        String boardString = "";
        GetBoardType type = new GetBoardType();
        boardType = type.doInBackground();

        if (boardType == 1.0) {
            boardString = "1";
        }else if (boardType == 3.0) {
            boardString = "3";
        }else if (boardType == 5.0) {
            boardString = "5";
        }else if (boardType == 7.5) {
            boardString = "7.5";
        }else if (boardType == 10.0) {
            boardString = "10";
        }

        typeString = boardString + " Meters";
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
        diveInfo1 = (TextView)findViewById(R.id.diveInfo1);
        diveInfo2 = (TextView)findViewById(R.id.diveInfo2);
        diveInfo3 = (TextView)findViewById(R.id.diveInfo3);
        diveInfo4 = (TextView)findViewById(R.id.diveInfo4);
        diveInfo5 = (TextView)findViewById(R.id.diveInfo5);
        diveInfo6 = (TextView)findViewById(R.id.diveInfo6);
        diveInfo7 = (TextView)findViewById(R.id.diveInfo7);
        diveInfo8 = (TextView)findViewById(R.id.diveInfo8);
        diveInfo9 = (TextView)findViewById(R.id.diveInfo9);
        diveInfo10 = (TextView)findViewById(R.id.diveInfo10);
        diveInfo11 = (TextView)findViewById(R.id.diveInfo11);
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


    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object> {
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
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

    private class GetDiverInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return diverinfo = db.getDiverInfo(diverId);
        }
    }

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return meetinfo = db.getMeetInfo(meetId);
        }
    }

    private class GetScoresFromDB extends AsyncTask<ArrayList<Double>, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        ArrayList<Double> scoreList;

        @SafeVarargs
        @Override
        protected final ArrayList<Double> doInBackground(ArrayList<Double>... params) {
            return scoreList = db.getResultsList(meetId, diverId);
        }
    }

    private class GetSpringboardDiveName extends AsyncTask<List<String>, Object, Object>{
        DivesDatabase db = new DivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getDiveNames();
        }
    }

    private class GetPlatformDiveName extends AsyncTask<List<String>, Object, Object>{
        PlatformDivesDatabase db = new PlatformDivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getPlatformDiveNames();
        }
    }

    private class GetBoardType extends AsyncTask<Double, Object, Object>{
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        Double type;

        @Override
        protected Double doInBackground(Double... params) {
            return type = db.getType(meetId, diverId);
        }
    }
}
