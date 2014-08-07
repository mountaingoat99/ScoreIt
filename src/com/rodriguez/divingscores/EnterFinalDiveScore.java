package com.rodriguez.divingscores;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;
import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.BackDatabase;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.InwardDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.ReverseDatabase;
import info.sqlite.helper.TwistDatabase;

public class EnterFinalDiveScore extends Activity implements OnItemSelectedListener {
    private Spinner spinner;
    private EditText score1;
    private RadioButton radioTuck, radioPike, radioFree, radioStraight;
    private int diverId, meetId, diveType, diveNumber, divePosition = 1, boardType = 0;
    private double  sc1 = 0.0, multiplier = 0.0;
    private List<String> diveName;
    boolean hidebutton;
    private String stringId = null, failedDive = "P";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_final_dive_score);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diveType = b.getInt("diveType");
        boardType = b.getInt("boardType");

        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        setTitle();
        getDiveNumber();
        addListenerOnButton();
        checkRadios();
    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber = db.getDiveNumber(meetId, diverId);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if(spinner.getSelectedItemPosition() == 0)
            hidebutton = false;
        else
            hidebutton = true;
    }

    // load the spinner data from db
    private void loadSpinnerData(){

        switch (diveType){
            case 1:
                if(boardType == 1) {
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    diveName = fdb.getForwardOneNames();
                    break;
                } else {
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    diveName = fdb.getForwardThreeNames();
                    break;
                }
            case 2:
                if(boardType == 1) {
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    diveName = bdb.getBackOneNames();
                    break;
                } else {
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    diveName = bdb.getBackThreeNames();
                    break;
                }
            case 3:
                if(boardType == 1){
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    diveName = rdb.getReverseOneNames();
                    break;
                } else {
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    diveName = rdb.getReverseThreeNames();
                    break;
                }
            case 4:
                if(boardType == 1) {
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    diveName = idb.getInwardOneNames();
                    break;
                } else {
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    diveName = idb.getInwardThreeNames();
                    break;
                }
            case 5:
                if(boardType == 1) {
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    diveName = tdb.getTwistOneNames();
                    break;
                } else {
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    diveName = tdb.getTwistThreeNames();
                    break;
                }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diveName);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Choose Dive:");
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.dive_style_spinner_row_nothing_selected, this));

    }

    private void setTitle(){
        switch(diveType){
            case 1:
                setTitle("Forward Dives");
                break;
            case 2:
                setTitle("Back Dives");
                break;
            case 3:
                setTitle("Reverse Dives");
                break;
            case 4:
                setTitle("Inward Dives");
                break;
            case 5:
                setTitle("Twist Dives");
                break;
        }
    }

    public void addListenerOnButton()
    {
        final Context context = this;
        Button btnTotal = (Button) findViewById(R.id.buttonScore);

        btnTotal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (hidebutton) {
                    getMultiplier();
                    if(multiplier != 0.0) {
                        getScoreText();
                        if(sc1 != 0.0) {
                            incrementDiveNumber();
                            calcScores();
                            updateJudgeScores();
                            Bundle b = new Bundle();
                            b.putInt("keyDiver", diverId);
                            b.putInt("keyMeet", meetId);
                            Intent intent = new Intent(context, ChooseSummary.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter a score or use the menu to fail the dive",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Dive and Position is not valid, " +
                                        "Please Choose a Valid Combination.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Dive Category",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getMultiplier(){
        int diveId;
        stringId = spinner.getSelectedItem().toString();
        switch (diveType){
            case 1:
                ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                diveId = fdb.getDiveId(stringId);
                multiplier = fdb.getDOD(diveId, divePosition, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase(getApplicationContext());
                diveId = bdb.getDiveId(stringId);
                multiplier = bdb.getDOD(diveId, divePosition, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                diveId = rdb.getDiveId(stringId);
                multiplier = rdb.getDOD(diveId, divePosition, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase(getApplicationContext());
                diveId = idb.getDiveId(stringId);
                multiplier = idb.getDOD(diveId, divePosition, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                diveId = tdb.getDiveId(stringId);
                multiplier = tdb.getDOD(diveId, divePosition, boardType);
                break;
        }
    }

    private void incrementDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber ++;
        db.updateDiveNumber(meetId, diverId, diveNumber);
    }

    private void calcScores() {
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double total = db.getTotalScore(meetId, diverId);
        total = total + sc1;

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

    private void updateJudgeScores(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
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
                DivePosition = "Straight";
                break;
            case 2:
                DivePosition = "Pike";
                break;
            case 3:
                DivePosition = "Tuck";
                break;
            case 4:
                DivePosition = "Free";
                break;
        }

        String diveTypeName = spinner.getSelectedItem().toString();

        // we are setting the judge scores values to zero since we are just adding in the total
        // we still want to keep track of the other stats
        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveTypeName, DivePosition,
                failedDive, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private void getScoreText(){
        sc1 = Double.parseDouble(score1.getText().toString());
    }

    private void checkRadios() {
        radioStraight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(true);
                radioTuck.setChecked(false);
                divePosition = 1;
            }
        });
        radioPike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(true);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 2;
            }
        });
        radioTuck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(true);
                divePosition = 3;
            }
        });
        radioFree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(true);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 4;
            }
        });
    }

    private void setUpView(){

        radioFree = (RadioButton)findViewById(R.id.radioFree);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        score1 = (EditText)findViewById(R.id.editScore1);
        spinner = (Spinner)findViewById(R.id.listDives);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enter_final_dive_score, menu);
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
                if (hidebutton) {
                    getMultiplier();
                    if (multiplier != 0.0) {
                        String diveTypeName = spinner.getSelectedItem().toString();
                        Intent intent = new Intent(context, FailedDive.class);
                        Bundle b = new Bundle();
                        b.putInt("keyDiver", diverId);
                        b.putInt("keyMeet", meetId);
                        b.putInt("keyDiveType", diveType);
                        b.putString("keyDiveTypeName", diveTypeName);
                        b.putInt("keyDivePosition", divePosition);
                        b.putInt("boardType", boardType);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Dive and Position is not valid, " +
                                        "Please Choose a Valid Combination.",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Dive Category",
                            Toast.LENGTH_LONG).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
