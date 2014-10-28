package com.rodriguez.divingscores;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;

public class FailedDive extends ActionBarActivity {

    private Button failButton;
    private Button returnButton;
    private int diverId, meetId, diveNumber, diveType = 0, divePosition = 0, diverSpinnerPosition,
            diveNumberFromList;
    private double boardType = 0.0, totalScore, diveScore, multiplier = 0.0;
    private String diveTypeName;
    private String className = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_dive);ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        failButton = (Button)findViewById(R.id.buttonFailDive);
        returnButton = (Button)findViewById(R.id.buttonFailReturn);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            className = b.getString("className");
            meetId = b.getInt("keyMeet");
            diverId = b.getInt("keyDiver");
            diveType = b.getInt("keyDiveType");
            diveTypeName = b.getString("keyDiveTypeName");
            divePosition = b.getInt("keyDivePosition");
            boardType = b.getDouble("boardType");
            diveNumberFromList = b.getInt("diveNumber");
            diverSpinnerPosition = b.getInt("keySpin");
            multiplier = b.getDouble("multiplier");
        }

        getDiveNumber();
        addListenerOnButton();
    }

    private void getDiveNumber(){
        GetDiveNumber number = new GetDiveNumber();
        diveNumber = number.doInBackground();
    }

    public void addListenerOnButton(){
        final Context context = this;

        if(className.equals("nonList")) {
            failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incrementDiveNumber();
                    updateJudgeScore();
                    Intent intent = new Intent(context, ChooseSummary.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }else {
            failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateJudgeScores();
                    getTotalScore();
                    getDiveScore();
                    updateResultTable();
                    incrementDiveNumberFromList();
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, ChooseDivesFromList.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }

        if(className.equals("nonList")) {
            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveType", diveType);
                    b.putDouble("boardType", boardType);
                    Intent intent = new Intent(context, Dives.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }else {

            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, EnterScoreFromList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
    }

    private void getTotalScore(){
        GetTotalScore total = new GetTotalScore();
        totalScore = total.doInBackground();
    }

    private void getDiveScore(){
        GetDiveScore ds = new GetDiveScore();
        diveScore = ds.doInBackground();
    }

    private void updateResultTable(){
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double newTotalScore = totalScore - diveScore;
        int resultIndex;
        resultIndex = diveNumberFromList + 2;
        db.writeDiveScore(meetId, diverId, resultIndex, 0.0, newTotalScore);

    }

    private void incrementDiveNumber(){
        UpdateDiveNumber update = new UpdateDiveNumber();
        diveNumber ++;
        update.doInBackground();
    }

    private void incrementDiveNumberFromList(){
        if (diveNumber == 0) {
            IncrementDiveNumber divenumber = new IncrementDiveNumber();
            divenumber.doInBackground();
        }
    }

    private void updateJudgeScores(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        String failedDive = "F";
        double total = 0.0, sc1 = 0.0, sc2 = 0.0, sc3 = 0.0, sc4 = 0.0, sc5 = 0.0, sc6 = 0.0, sc7 = 0.0;
        db.updateJudgeScoreFailed(meetId, diverId, diveNumberFromList, failedDive, total, sc1, sc2,
                sc3, sc4, sc5, sc6, sc7);
    }

    public void updateJudgeScore(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        String failedDive = "F";
        double total = 0.0, sc1 = 0.0, sc2 = 0.0, sc3 = 0.0, sc4 = 0.0, sc5 = 0.0, sc6 = 0.0, sc7 = 0.0;
        String diveCategory = null;
        switch (diveType){
            case 1:
                diveCategory = "Forward Dive";
                break;
            case 2:
                diveCategory = "Back Dive";
                break;
            case 3:
                diveCategory = "Reverse Dive";
                break;
            case 4:
                diveCategory = "Inward Dive";
                break;
            case 5:
                diveCategory = "Twist Dive";
                break;
        }

        String DivePosition = null;
        switch (divePosition){
            case 1:
                DivePosition = "A - Straight";
                break;
            case 2:
                DivePosition = "B - Pike";
                break;
            case 3:
                DivePosition = "C - Tuck";
                break;
            case 4:
                DivePosition = "D - Free";
                break;
        }

        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveTypeName, DivePosition,
                failedDive, total, sc1, sc2, sc3, sc4, sc5, sc6, sc7, multiplier);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.failed_dive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        int num;

        @Override
        protected Integer doInBackground(Integer... params) {
            return num = db.getDiveNumber(meetId, diverId);
        }
    }

    private class UpdateDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateDiveNumber(meetId, diverId, diveNumber);
            return null;
        }
    }

    private class IncrementDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateDiveNumber(meetId, diverId, diveNumber);
            return null;
        }
    }

    private class GetTotalScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double total;

        @Override
        protected Double doInBackground(Double... params) {
            return total = db.getTotalScore(meetId, diverId);
        }
    }

    private class GetDiveScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double score;

        @Override
        protected Double doInBackground(Double... params) {
            return score = db.getDiveScore(meetId, diverId, diveNumberFromList);
        }
    }
}
