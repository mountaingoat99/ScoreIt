package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeDiveScore extends Activity implements OnItemSelectedListener{

    private TextView name, meetName, s1v, s2v, s3v, s4v, s5v, s6v, s7v,
            totalView, failedV, failedText;
    private View layouts, View3, tableRow4;
    private EditText s1, s2, s3, s4, s5, s6, s7, total;
    private Spinner spinner;
    private TableLayout table;
    private Button updateScoreButton, returnButton;
    private int diverId, meetId, showDiveNumber,  diveTotal, type, diveNumber, judgeTotal;
    private String  totalScore, s1String, s2String, s3String, s4String, s5String, s6String, s7String, failedString, testNumber;
    private String editTotal = "0.0", edit1 = "0.0", edit2 = "0.0", edit3 = "0.0", edit4 = "0.0",
            edit5 = "0.0", edit6 = "0.0", edit7 = "0.0", editFailed;
    private Double e1 = 0.0, e2 = 0.0, e3 = 0.0, e4 = 0.0, e5 = 0.0, e6 = 0.0, e7 = 0.0, newOverAllScore, allScoreTotal, multiplier = 0.0;
    private Boolean noSpinChoice, alert = true;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_dive_score);
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
        getJudgeTotal();
        getDiveTotals();
        getDiveNumber();
        loadSpinnerData();
        fillText();
        addListenerOnButton();
    }

    private void loadSpinnerData(){
        List<String> diveNum = new ArrayList<>();
        for(int i = 0; i < diveNumber; ++i){
            diveNum.add("  Dive " + (i + 1));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diveNum);

        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.diver_number_spinner_row_nothing_selected, this));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showDiveNumber = 0;
        noSpinChoice = true;
        if(id == -1){
            noSpinChoice = false;
        } else {
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
            checkMultiplier();
            showDiveScores();
            layouts.setVisibility(View.VISIBLE);
            View3.setVisibility(View.VISIBLE);
            updateScoreButton.setVisibility(View.VISIBLE);
            showAlert();
        }
    }

    private void showDiveScores(){
        ResultDatabase rdb = new ResultDatabase(getApplicationContext());
        totalScore = Double.toString(rdb.getDiveScore(meetId, diverId, showDiveNumber));
        totalView.setVisibility(View.VISIBLE);
        total.setVisibility(View.VISIBLE);
        total.setText(totalScore);

        JudgeScoreDatabase jdb = new JudgeScoreDatabase(getApplicationContext());
        Boolean failed = jdb.checkFailed(meetId, diverId, showDiveNumber);
        if(failed){
            failedString = "F";
        } else {
            failedString = "P";
        }
        failedV.setVisibility(View.VISIBLE);
        failedText.setVisibility(View.VISIBLE);
        failedText.setText(failedString);

        if(multiplier != 0.0) {
            table.setVisibility(View.VISIBLE);
            ArrayList<Double> scores = jdb.getScoresList(meetId, diverId, showDiveNumber);
            s1String = Double.toString(e1 = scores.get(0));
            s2String = Double.toString(e2 = scores.get(1));
            s3String = Double.toString(e3 = scores.get(2));
            s4String = Double.toString(e4 = scores.get(3));
            s5String = Double.toString(e5 = scores.get(4));
            s6String = Double.toString(e6 = scores.get(5));
            s7String = Double.toString(e7 = scores.get(6));

            if (judgeTotal >= 3) {
                s1v.setVisibility(View.VISIBLE);
                s1.setVisibility(View.VISIBLE);
                s1.setText(s1String);

                s2v.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                s2.setText(s2String);

                s3v.setVisibility(View.VISIBLE);
                s3.setVisibility(View.VISIBLE);
                s3.setText(s3String);
            }
            if (judgeTotal >= 5) {
                tableRow4.setVisibility(View.VISIBLE);
                s4v.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s4.setText(s4String);

                s5v.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s5.setText(s5String);
            }
            if (judgeTotal == 7) {
                tableRow4.setVisibility(View.VISIBLE);
                s6v.setVisibility(View.VISIBLE);
                s6.setVisibility(View.VISIBLE);
                s6.setText(s6String);

                s7v.setVisibility(View.VISIBLE);
                s7.setVisibility(View.VISIBLE);
                s7.setText(s7String);
            }
        }
    }

    public void addListenerOnButton(){
        final Context context = this;

        updateScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noSpinChoice) {
                    getNewScores();
                    if (!testNumber.equals("")) {
                        updateScores();
                        Bundle b = new Bundle();
                        b.putInt("keyDiver", diverId);
                        b.putInt("keyMeet", meetId);
                        Intent intent = new Intent(context, ChooseSummary.class);
                        intent.putExtras(b);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter a number",
                                Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Dive Number",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                Intent intent = new Intent(context, ChooseSummary.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void getNewScores(){
        testNumber = total.getText().toString();
        if(testNumber.equals("")){
            return;
        }

        editTotal = testNumber;

        if(multiplier != 0.0) {
            if (judgeTotal >= 3) {
                edit1 = s1.getText().toString().trim();
                e1 = Double.parseDouble(edit1);
                edit2 = s2.getText().toString().trim();
                e2 = Double.parseDouble(edit2);
                edit3 = s3.getText().toString().trim();
                e3 = Double.parseDouble(edit3);
            }
            if (judgeTotal >= 5) {
                edit4 = s4.getText().toString().trim();
                e4 = Double.parseDouble(edit4);
                edit5 = s5.getText().toString().trim();
                e5 = Double.parseDouble(edit5);
            }
            if (judgeTotal == 7) {
                edit6 = s6.getText().toString().trim();
                e6 = Double.parseDouble(edit6);
                edit7 = s7.getText().toString().trim();
                e7 = Double.parseDouble(edit7);
            }
        }
    }

    private Double calcNewOverall(double newTotal){
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double oldTotal = Double.parseDouble(totalScore);
        double Overall = db.getTotalScore(meetId, diverId);
        newOverAllScore = newTotal + (Overall - oldTotal);

        return newOverAllScore;
    }

    public void updateScores() {
        ResultDatabase rdb = new ResultDatabase(getApplicationContext());
        JudgeScoreDatabase jdb = new JudgeScoreDatabase(getApplicationContext());
        double newTotal, roundedNumber;
        int index = 0;
        switch (showDiveNumber) {
            case 1:
                index = 3;
                break;
            case 2:
                index = 4;
                break;
            case 3:
                index = 5;
                break;
            case 4:
                index = 6;
                break;
            case 5:
                index = 7;
                break;
            case 6:
                index = 8;
                break;
            case 7:
                index = 9;
                break;
            case 8:
                index = 10;
                break;
            case 9:
                index = 11;
                break;
            case 10:
                index = 12;
                break;
            case 11:
                index = 13;
                break;
        }
        if(!totalScore.equals(editTotal)){
            if (editTotal.equals("0") || editTotal.equals("0.0") || editTotal.equals("00.0")
                    || editTotal.equals("00.00") || editTotal.equals("0.00") || editTotal.equals("000.00")) {
                editFailed = "F";
                jdb.updateJudgeScoreFailed(meetId, diverId, showDiveNumber, editFailed, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
                newTotal = 0.0;
            } else {
                editFailed = "P";
                roundedNumber = Double.parseDouble(editTotal);
                newTotal = .5 * Math.round(roundedNumber * 2);
                jdb.updateJudgeScoreFailed(meetId, diverId, showDiveNumber, editFailed, newTotal, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

            }
            allScoreTotal = calcNewOverall(newTotal);
            rdb.writeDiveScore(meetId, diverId, index, newTotal, allScoreTotal);
            return;
        } else {
            roundedNumber = getMultiplerScore();
            newTotal = .5 * Math.round(roundedNumber * 2);
            jdb.updateJudgeScoreFailed(meetId, diverId, showDiveNumber, editFailed, newTotal, e1, e2, e3, e4, e5, e6, e7);
        }
        allScoreTotal = calcNewOverall(newTotal);
        rdb.writeDiveScore(meetId, diverId, index, newTotal, allScoreTotal);
    }

    private void showAlert(){
        if (!alert)
            return;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_score_warning);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void checkMultiplier(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        multiplier = db.getMultiplier(meetId, diverId, showDiveNumber);
    }

    private Double getMultiplerScore(){
        double score = 0.0;
        ArrayList<Double> Scores = new ArrayList<>();
        Scores.add(e1);
        Scores.add(e2);
        Scores.add(e3);
        Scores.add(e4);
        Scores.add(e5);
        Scores.add(e6);
        Scores.add(e7);

        Double[] theScores = new Double[ Scores.size()];
        Scores.toArray(theScores);
        Arrays.sort(theScores);

        if(judgeTotal == 3){
            score = e1 + e2 + e3;
        }else if (judgeTotal == 5){
            // converts the sorted array to a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for (int i = 0; i < list.size(); i++){
                score = score + list.get(i);
            }
        } else if (judgeTotal == 7){
            // converts the sorted array a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(0);
            list.remove(4);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for(int i = 0; i < list.size(); i++){
                score = score + list.get(i);
            }
        }
        if(multiplier != 0.0) {
            score = score * multiplier;
        }
        return  score;
    }

    private void getJudgeTotal(){
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        judgeTotal = db.getJudges(meetId);
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

    private void setUpView(){
        name = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        s1 = (EditText)findViewById(R.id.score1);
        s2 = (EditText)findViewById(R.id.score2);
        s3 = (EditText)findViewById(R.id.score3);
        s4 = (EditText)findViewById(R.id.score4);
        s5 = (EditText)findViewById(R.id.score5);
        s6 = (EditText)findViewById(R.id.score6);
        s7 = (EditText)findViewById(R.id.score7);
        s1v = (TextView)findViewById(R.id.score1view);
        s2v = (TextView)findViewById(R.id.score2view);
        s3v = (TextView)findViewById(R.id.score3view);
        s4v = (TextView)findViewById(R.id.score4view);
        s5v = (TextView)findViewById(R.id.score5view);
        s6v = (TextView)findViewById(R.id.score6view);
        s7v = (TextView)findViewById(R.id.score7view);
        total = (EditText)findViewById(R.id.scoreTotal);
        totalView = (TextView)findViewById(R.id.total2view);
        failedV = (TextView)findViewById(R.id.failedView);
        failedText = (TextView)findViewById(R.id.failedText);
        spinner = (Spinner)findViewById(R.id.spinnerDiveNumber);
        updateScoreButton = (Button)findViewById(R.id.buttonChangeScore);
        returnButton = (Button)findViewById(R.id.buttonReturn);
        table = (TableLayout)findViewById(R.id.tableLayout1);
        layouts = findViewById(R.id.layouts);
        View3 = findViewById(R.id.View3);
        tableRow4 = findViewById(R.id.tableRow4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.change_dive_score, menu);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
