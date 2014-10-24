package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;
import info.sqlite.model.ResultsDB;


public class ChooseDivesFromList extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner diveNumberSpinner;
    private TextView name, meetName, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, total,
            s1v, s2v, s3v, s4v, s5v, s6v, s7v, s8v, s9v, s10v, s11v,
            diveTypeName, diveInfo1, diveInfo2, diveInfo3, diveInfo4, diveInfo5, diveInfo6,
            diveInfo7, diveInfo8, diveInfo9, diveInfo10, diveInfo11, totalView, diveTypeText;
    private TableRow t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11;
    private Button btnJudgeScore, btnTotalScore;
    private String typeString, score1, score2, score3,
            score4, score5, score6, score7, score8, score9, score10, score11, info1, info2,
            info3, info4, info5, info6, info7, info8, info9, info10, info11,
            noDive = "There are no scores entered yet.";
    private int diverId, meetId, showDiveNumber,
            diveTotal, diverSpinnerPosition;
    //private int diveNumber;
    private double  totalScore, boardType = 0.0;
    private double sc1, sc2, sc3, sc4, sc5, sc6, sc7, sc8, sc9, sc10, sc11;
    private boolean failed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dives_from_list);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diverSpinnerPosition = b.getInt("keySpin");

        diveNumberSpinner.setOnItemSelectedListener(this);
        fillType();
        fillText();
        checkDiveTotal();
        //getDiveNumber();
        loadSpinnerData();
        fillScores();
        addListenerOnButton();

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

        btnJudgeScore.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (showDiveNumber != 0){
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveNumber", showDiveNumber);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, EnterScoreFromList.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please choose a dive!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTotalScore.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (showDiveNumber != 0){
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("diveNumber", showDiveNumber);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, EnterTotalScoreFromDiveList.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please choose a dive!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showDiveNumber = 0;
        switch (position){
            case 1:
                showDiveNumber = 1;
                break;
            case 2:
                showDiveNumber = 2;
                break;
            case 3:
                showDiveNumber = 3;
                break;
            case 4:
                showDiveNumber = 4;
                break;
            case 5:
                showDiveNumber = 5;
                break;
            case 6:
                showDiveNumber = 6;
                break;
            case 7:
                showDiveNumber = 7;
                break;
            case 8:
                showDiveNumber = 8;
                break;
            case 9:
                showDiveNumber = 9;
                break;
            case 10:
                showDiveNumber = 10;
                break;
            case 11:
                showDiveNumber = 11;
                break;
        }
    }

    private void loadSpinnerData(){
        List<String> diveNum = new ArrayList<>();
        for (int i = 0; i < diveTotal; ++i){
            diveNum.add(" Dive " + (i + 1));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diveNum);

        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        diveNumberSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.diver_number_spinner_row_nothing_selected, this));
    }

    private void fillScores(){
        getScoresFromDB();
        String failDive = "F";
        int numberOfDive;

        DecimalFormat d = new DecimalFormat("0.00");
        Double totalScore2 = Double.parseDouble(d.format(totalScore));

        String totalString = Double.toString(totalScore2);
        failed = checkFailedDive(1);
        if(sc1 != 0.0 || failed){
            totalView.setVisibility(View.VISIBLE);
            total.setVisibility(View.VISIBLE);
            total.setText(totalString);
            diveTypeText.setVisibility(View.VISIBLE);
            diveTypeName.setVisibility(View.VISIBLE);
            diveTypeName.setText(typeString);
        } else {
            totalView.setVisibility(View.GONE);
            diveTypeText.setVisibility(View.GONE);
            diveTypeName.setVisibility(View.GONE);
            total.setVisibility(View.VISIBLE);
            total.setText(noDive);
        }
        numberOfDive = 1;
        info1 = getDiveInfoFromDB(1);
        diveInfo1.setText(info1);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t1.setVisibility(View.VISIBLE);
            s1v.setVisibility(View.VISIBLE);
            s1.setVisibility(View.VISIBLE);
            s1.setText(failDive);
        }else {
            if(sc1 != 0.0){
                t1.setVisibility(View.VISIBLE);
                s1v.setVisibility(View.VISIBLE);
                s1.setVisibility(View.VISIBLE);
                s1.setText(score1);
            }
        }
        numberOfDive = 2;
        info2 = getDiveInfoFromDB(2);
        diveInfo2.setText(info2);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t2.setVisibility(View.VISIBLE);
            s2v.setVisibility(View.VISIBLE);
            s2.setVisibility(View.VISIBLE);
            s2.setText(failDive);
        }else {
            if(sc2 != 0.0){
                t2.setVisibility(View.VISIBLE);
                s2v.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s2.setText(score2);
            }
        }
        numberOfDive = 3;
        info3 = getDiveInfoFromDB(3);
        diveInfo3.setText(info3);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t3.setVisibility(View.VISIBLE);
            s3v.setVisibility(View.VISIBLE);
            s3.setVisibility(View.VISIBLE);
            s3.setText(failDive);
        }else {
            if(sc3 != 0.0){
                t3.setVisibility(View.VISIBLE);
                s3v.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s3.setText(score3);
            }
        }
        numberOfDive = 4;
        info4 = getDiveInfoFromDB(4);
        diveInfo4.setText(info4);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t4.setVisibility(View.VISIBLE);
            s4v.setVisibility(View.VISIBLE);
            s4.setVisibility(View.VISIBLE);
            s4.setText(failDive);
        }else {
            if(sc4 != 0.0){
                t4.setVisibility(View.VISIBLE);
                s4v.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s4.setText(score4);
            }
        }
        numberOfDive = 5;
        info5 = getDiveInfoFromDB(5);
        diveInfo5.setText(info5);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t5.setVisibility(View.VISIBLE);
            s5v.setVisibility(View.VISIBLE);
            s5.setVisibility(View.VISIBLE);
            s5.setText(failDive);
        }else {
            if(sc5 != 0.0){
                t5.setVisibility(View.VISIBLE);
                s5v.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s5.setText(score5);
            }
        }
        numberOfDive = 6;
        info6 = getDiveInfoFromDB(6);
        diveInfo6.setText(info6);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t6.setVisibility(View.VISIBLE);
            s6v.setVisibility(View.VISIBLE);
            s6.setVisibility(View.VISIBLE);
            s6.setText(failDive);
        }else {
            if(sc6 != 0.0){
                t6.setVisibility(View.VISIBLE);
                s6v.setVisibility(View.VISIBLE);
                s6.setVisibility(View.VISIBLE);
                s6.setText(score6);
            }
        }
        if((sc6 != 0.0 || failed) && diveTotal == 6){
            Toast.makeText(getApplicationContext(),
                    "Congratulations, all six dives are complete," +
                            " total score is " + totalString + " ." +
                            "You can still choose a dive to edit it if needed.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        numberOfDive = 7;
        info7 = getDiveInfoFromDB(7);
        diveInfo7.setText(info7);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t7.setVisibility(View.VISIBLE);
            s7v.setVisibility(View.VISIBLE);
            s7.setVisibility(View.VISIBLE);
            s7.setText(failDive);
        }else {
            if(sc7 != 0.0){
                t7.setVisibility(View.VISIBLE);
                s7v.setVisibility(View.VISIBLE);
                s7.setVisibility(View.VISIBLE);
                s7.setText(score7);
            }
        }
        numberOfDive = 8;
        info8 = getDiveInfoFromDB(8);
        diveInfo8.setText(info8);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t8.setVisibility(View.VISIBLE);
            s8v.setVisibility(View.VISIBLE);
            s8.setVisibility(View.VISIBLE);
            s8.setText(failDive);
        }else {
            if(sc8 != 0.0){
                t8.setVisibility(View.VISIBLE);
                s8v.setVisibility(View.VISIBLE);
                s8.setVisibility(View.VISIBLE);
                s8.setText(score8);
            }
        }
        numberOfDive = 9;
        info9 = getDiveInfoFromDB(9);
        diveInfo9.setText(info9);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t9.setVisibility(View.VISIBLE);
            s9v.setVisibility(View.VISIBLE);
            s9.setVisibility(View.VISIBLE);
            s9.setText(failDive);
        }else {
            if(sc9 != 0.0){
                t9.setVisibility(View.VISIBLE);
                s9v.setVisibility(View.VISIBLE);
                s9.setVisibility(View.VISIBLE);
                s9.setText(score9);
            }
        }
        numberOfDive = 10;
        info10 = getDiveInfoFromDB(10);
        diveInfo10.setText(info10);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t10.setVisibility(View.VISIBLE);
            s10v.setVisibility(View.VISIBLE);
            s10.setVisibility(View.VISIBLE);
            s10.setText(failDive);
        }else {
            if(sc10 != 0.0){
                t10.setVisibility(View.VISIBLE);
                s10v.setVisibility(View.VISIBLE);
                s10.setVisibility(View.VISIBLE);
                s10.setText(score10);
            }
        }
        numberOfDive = 11;
        info11 = getDiveInfoFromDB(11);
        diveInfo11.setText(info11);
        failed = checkFailedDive(numberOfDive);
        if(failed) {
            t11.setVisibility(View.VISIBLE);
            s11v.setVisibility(View.VISIBLE);
            s11.setVisibility(View.VISIBLE);
            s11.setText(failDive);
        }else {
            if(sc11 != 0.0){
                t11.setVisibility(View.VISIBLE);
                s11v.setVisibility(View.VISIBLE);
                s11.setVisibility(View.VISIBLE);
                s11.setText(score11);
            }
        }
        if((sc11 != 0.0 || failed) && diveTotal == 11){
            Toast.makeText(getApplicationContext(),
                    "Congratulations, all eleven dives are complete," +
                    " total score is " + totalString + " ." +
                    "You can still choose a dive to edit it if needed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void getScoresFromDB(){
        ArrayList<Double> scores;
        GetScoresFromDB scoreList = new GetScoresFromDB();
        scores = scoreList.doInBackground();
        sc1 = scores.get(0);
        sc2 = scores.get(1);
        sc3 = scores.get(2);
        sc4 = scores.get(3);
        sc5 = scores.get(4);
        sc6 = scores.get(5);
        sc7 = scores.get(6);
        sc8 = scores.get(7);
        sc9 = scores.get(8);
        sc10 = scores.get(9);
        sc11 = scores.get(10);
        score1 = Double.toString(scores.get(0));
        score2 = Double.toString(scores.get(1));
        score3 = Double.toString(scores.get(2));
        score4 = Double.toString(scores.get(3));
        score5 = Double.toString(scores.get(4));
        score6 = Double.toString(scores.get(5));
        score7 = Double.toString(scores.get(6));
        score8 = Double.toString(scores.get(7));
        score9 = Double.toString(scores.get(8));
        score10 = Double.toString(scores.get(9));
        score11 = Double.toString(scores.get(10));

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

    private void checkDiveTotal(){
        SearchDiveTotals search = new SearchDiveTotals();
        diveTotal = search.doInBackground();
    }

//    private void getDiveNumber(){
//        GetDiveNumber num = new GetDiveNumber();
//        diveNumber = num.doInBackground();
//    }

    private void setUpView(){
        diveNumberSpinner = (Spinner)findViewById(R.id.spinnerDiveNumber);
        btnJudgeScore = (Button)findViewById(R.id.buttonJudgeScore);
        btnTotalScore = (Button)findViewById(R.id.buttonTotalScore);
        name = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        total = (TextView)findViewById(R.id.scoreTotal);
        totalView = (TextView)findViewById(R.id.total2view);
        diveTypeText = (TextView)findViewById(R.id.diveType);
        diveTypeName = (TextView)findViewById(R.id.theType);
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
        t1 = (TableRow)findViewById(R.id.tableRow1);
        t2 = (TableRow)findViewById(R.id.tableRow2);
        t3 = (TableRow)findViewById(R.id.tableRow3);
        t4 = (TableRow)findViewById(R.id.tableRow4);
        t5 = (TableRow)findViewById(R.id.tableRow5);
        t6 = (TableRow)findViewById(R.id.tableRow6);
        t7 = (TableRow)findViewById(R.id.tableRow7);
        t8 = (TableRow)findViewById(R.id.tableRow8);
        t9 = (TableRow)findViewById(R.id.tableRow9);
        t10 = (TableRow)findViewById(R.id.tableRow10);
        t11 = (TableRow)findViewById(R.id.tableRow11);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_dives_from_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object>{
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }

    private class CheckMultiplier extends AsyncTask<Double, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        double multi;

        @Override
        protected Double doInBackground(Double... params) {
            return multi = db.getMultiplier(meetId, diverId, showDiveNumber);
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

    private class GetBoardType extends AsyncTask<Double, Object, Object>{
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        Double type;

        @Override
        protected Double doInBackground(Double... params) {
            return type = db.getType(meetId, diverId);
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

//    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
//        DiveNumberDatabase db =  new DiveNumberDatabase(getApplicationContext());
//        int number;
//
//        @Override
//        protected Integer doInBackground(Integer... params) {
//            return number = db.getDiveNumber(meetId, diverId);
//        }
//    }
}
