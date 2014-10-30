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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;


public class EnterTotalScoreFromDiveList extends ActionBarActivity {

    private TextView header;
    private EditText score1;
    private Button btnTotal;
    private String className = "EnterScoreFromList";
    private int diverId, meetId, diveNumber, dbDiveNumber, diverSpinnerPosition, totalDives;
    private double sc1 = 0.0, total = 0.0, scr1 = 0.0, sc2 = 0.0,
            sc3 = 0.0, sc4 = 0.0, sc5 = 0.0, sc6 = 0.0, sc7 = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_total_score_from_dive_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diveNumber = b.getInt("diveNumber");
        diverSpinnerPosition = b.getInt("keySpin");

        //checkMultiplier();
        setTitle();
        checkDiveTotal();
        getDiveNumber();
        addListenerOnButton();
    }

    public void addListenerOnButton(){
        final Context context = this;

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScoreText();
                if(sc1 != 0.0){
                    updateJudgeScores();
                    calcScores();
                    incrementDiveNumber();
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, ChooseDivesFromList.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter a score or use the menu to fail the dive",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void calcScores() {
        GetTotalScore tScore = new GetTotalScore();
        total = tScore.doInBackground();
        total = total + sc1;

        ResultDatabase db = new ResultDatabase(getApplicationContext());
        int resultIndex;
        if(diveNumber == 1){
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 2){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 3){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 4){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 5){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 6){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 7){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 8){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 9){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 10){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
            return;
        }
        if(diveNumber == 11){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, sc1, total);
        }
    }

    private void checkDiveTotal(){
        SearchDiveTotals search = new SearchDiveTotals();
        totalDives = search.doInBackground();
    }

    private void incrementDiveNumber(){
        if (dbDiveNumber == 0) {
            IncrementDiveNumber divenumber = new IncrementDiveNumber();
            divenumber.doInBackground();
        }
    }

    private void updateJudgeScores(){
        UpdateJudgeScores judgescore = new UpdateJudgeScores();
        judgescore.doInBackground();
    }

    private void getScoreText(){
        //double roundedNumber;
        String testNumber = score1.getText().toString();
        if(testNumber.equals("")){
            testNumber = "0.0";
        }
        //roundedNumber = Double.parseDouble(testNumber);
        //sc1 = .5 * Math.round(roundedNumber * 2);
        sc1 = Double.parseDouble(testNumber);
    }

    private void setTitle() {
        setTitle("Enter Scores");
        String info = getDiveInfoFromDB(diveNumber);
        header.setText(info);

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

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        dbDiveNumber = num.doInBackground();
    }

    private void setUpView(){
        header = (TextView)findViewById(R.id.textView);
        score1 = (EditText)findViewById(R.id.editScore1);
        btnTotal = (Button)findViewById(R.id.buttonScore);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_total_score_from_dive_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_failed_dive:
                Intent intent = new Intent(context, FailedDive.class);
                Bundle b = new Bundle();
                b.putString("className", className);
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("diveNumber", diveNumber);
                b.putInt("keySpin", diverSpinnerPosition);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    private class CheckMultiplier extends AsyncTask<Double, Object, Object> {
//        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
//        double multi;
//
//        @Override
//        protected Double doInBackground(Double... params) {
//            return multi = db.getMultiplier(meetId, diverId, diveNumber);
//        }
//    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        DiveNumberDatabase db =  new DiveNumberDatabase(getApplicationContext());
        int number;

        @Override
        protected Integer doInBackground(Integer... params) {
            return number = db.getDiveNumber(meetId, diverId);
        }
    }

    private class UpdateJudgeScores extends AsyncTask<Object, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateJudgeScoreFailed(meetId, diverId, diveNumber, "P", sc1, scr1, sc2,
                    sc3, sc4, sc5, sc6, sc7);
            return null;
        }
    }

    private class IncrementDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateDiveNumber(meetId, diverId, totalDives);
            return null;
        }
    }

    private class GetTotalScore extends AsyncTask<Double, Object, Object> {
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double total;

        @Override
        protected Double doInBackground(Double... params) {
            return total = db.getTotalScore(meetId, diverId);
        }
    }

        private class SearchDiveTotals extends AsyncTask<Integer, Object, Object>{
            DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
            int number;

            @Override
            protected Integer doInBackground(Integer... params) {
                return number = db.searchTotals(meetId, diverId);
            }
        }
}
