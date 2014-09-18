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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.Helpers.DiveStyleSpinner;
import info.controls.SpinnerDiveStyleCustomBaseAdpater;
import info.sqlite.helper.ArmstandPlatformDatabase;
import info.sqlite.helper.BackDatabase;
import info.sqlite.helper.BackPlatformDatabase;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.ForwardPlatformDatabase;
import info.sqlite.helper.InwardDatabase;
import info.sqlite.helper.InwardPlatformDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.ReverseDatabase;
import info.sqlite.helper.ReversePlatformDatabase;
import info.sqlite.helper.TwistDatabase;
import info.sqlite.helper.TwistPlatformDatabase;

public class EnterFinalDiveScore extends Activity implements OnItemSelectedListener {
    private Spinner spinner;
    private EditText score1;
    private RadioButton radioTuck, radioPike, radioFree, radioStraight;
    private int diverId, meetId, diveType, diveNumber, divePosition = 1;
    private double boardType = 0.0;
    private double  sc1 = 0.0, multiplier = 0.0, total = 0.0;
    private ArrayList<DiveStyleSpinner> searchDives;
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
        boardType = b.getDouble("boardType");

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
    }

    // load the spinner data from db
    private void loadSpinnerData(){

        switch (diveType){
            // Springboard Dives
            case 1:
                if(boardType == 1) {
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    searchDives = fdb.getForwardOneNames();
                    break;
                } else {
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    searchDives = fdb.getForwardThreeNames();
                    break;
                }
            case 2:
                if(boardType == 1) {
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    searchDives = bdb.getBackOneNames();
                    break;
                } else {
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    searchDives = bdb.getBackThreeNames();
                    break;
                }
            case 3:
                if(boardType == 1){
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    searchDives = rdb.getReverseOneNames();
                    break;
                } else {
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    searchDives = rdb.getReverseThreeNames();
                    break;
                }
            case 4:
                if(boardType == 1) {
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    searchDives = idb.getInwardOneNames();
                    break;
                } else {
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    searchDives = idb.getInwardThreeNames();
                    break;
                }
            case 5:
                if(boardType == 1) {
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    searchDives = tdb.getTwistOneNames();
                    break;
                } else {
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    searchDives = tdb.getTwistThreeNames();
                    break;
                }
                //platform dives
            case 6:
                if(boardType == 10) {
                    ForwardPlatformDatabase fpd = new ForwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getFrontPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    ForwardPlatformDatabase fpd = new ForwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getFrontPlatformSevenFiveNames();
                    break;
                } else {
                    ForwardPlatformDatabase fpd = new ForwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getFrontPlatformFiveNames();
                    break;
                }
            case 7:
                if(boardType == 10) {
                    BackPlatformDatabase fpd = new BackPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getBackPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    BackPlatformDatabase fpd = new BackPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getBackPlatformSevenFiveNames();
                    break;
                } else {
                    BackPlatformDatabase fpd = new BackPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getBackPlatformFiveNames();
                    break;
                }
            case 8:
                if(boardType == 10) {
                    ReversePlatformDatabase fpd = new ReversePlatformDatabase(getApplicationContext());
                    searchDives = fpd.getReversePlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    ReversePlatformDatabase fpd = new ReversePlatformDatabase(getApplicationContext());
                    searchDives = fpd.getReversePlatformSevenFiveNames();
                    break;
                } else {
                    ReversePlatformDatabase fpd = new ReversePlatformDatabase(getApplicationContext());
                    searchDives = fpd.getReversePlatformFiveNames();
                    break;
                }
            case 9:
                if(boardType == 10) {
                    InwardPlatformDatabase fpd = new InwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getInwardPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    InwardPlatformDatabase fpd = new InwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getInwardPlatformSevenFiveNames();
                    break;
                } else {
                    InwardPlatformDatabase fpd = new InwardPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getInwardPlatformFiveNames();
                    break;
                }
            case 10:
                if(boardType == 10) {
                    TwistPlatformDatabase fpd = new TwistPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getTwistPlatformTenNames();
                    break;
                } else if (boardType == 7.5) {
                    TwistPlatformDatabase fpd = new TwistPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getTwistPlatformSevenFiveNames();
                    break;
                } else {
                    TwistPlatformDatabase fpd = new TwistPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getTwistPlatformFiveNames();
                    break;
                }
            case 11:
                if(boardType == 10) {
                    ArmstandPlatformDatabase fpd = new ArmstandPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getArmstandTenNames();
                    break;
                } else if (boardType == 7.5) {
                    ArmstandPlatformDatabase fpd = new ArmstandPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getArmstandSevenFiveNames();
                    break;
                } else {
                    ArmstandPlatformDatabase fpd = new ArmstandPlatformDatabase(getApplicationContext());
                    searchDives = fpd.getArmstandFiveNames();
                    break;
                }
        }

        spinner.setAdapter(new SpinnerDiveStyleCustomBaseAdpater(this, searchDives));
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
            case 6:
                setTitle("Front Platform Dives");
                break;
            case 7:
                setTitle("Back Platform Dives");
                break;
            case 8:
                setTitle("Reverse Platform Dives");
                break;
            case 9:
                setTitle("Inward Platform Dives");
                break;
            case 10:
                setTitle("Twist Platform Dives");
                break;
            case 11:
                setTitle("Armstand Platform Dives");
                break;
        }
    }

    public void addListenerOnButton()
    {
        final Context context = this;
        Button btnTotal = (Button) findViewById(R.id.buttonScore);

        btnTotal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
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
            }
        });
    }

    private void getMultiplier(){
        int diveId;
        TextView name = (TextView) findViewById(R.id.diveStyle);
        String stringId = name.getText().toString();
        //stringId = spinner.getSelectedItem().toString();
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
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(getApplicationContext());
                diveId = fpdb.getDiveId(stringId);
                multiplier = fpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase(getApplicationContext());
                diveId = bpdb.getDiveId(stringId);
                multiplier = bpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase(getApplicationContext());
                diveId = rpdb.getDiveId(stringId);
                multiplier = rpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase(getApplicationContext());
                diveId = ipdb.getDiveId(stringId);
                multiplier = ipdb.getDOD(diveId, divePosition, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase(getApplicationContext());
                diveId = tpdb.getDiveId(stringId);
                multiplier = tpdb.getDOD(diveId, divePosition, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(getApplicationContext());
                diveId = apdb.getDiveId(stringId);
                multiplier = apdb.getDOD(diveId, divePosition, boardType);
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
        total = db.getTotalScore(meetId, diverId);
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
            case 6:
                diveCategory = "Front Platform Dives";
                break;
            case 7:
                diveCategory = "Back Platform Dives";
                break;
            case 8:
                diveCategory = "Reverse Platform Dives";
                break;
            case 9:
                diveCategory = "Inward Platform Dives";
                break;
            case 10:
                diveCategory = "Twist Platform Dives";
                break;
            case 11:
                diveCategory = "Armstand Platform Dives";
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

        TextView name = (TextView) findViewById(R.id.diveStyle);
        TextView id = (TextView) findViewById(R.id.diveId);
        String i = id.getText().toString();
        String diveTypeName = i + " - " + name.getText().toString();

        // we are setting the judge scores values to zero since we are just adding in the total
        // we still want to keep track of the other stats
        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveTypeName, DivePosition,
                failedDive, total, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private void getScoreText(){
        double roundedNumber;
        String testNumber = score1.getText().toString();
        if(testNumber.equals("")){
            testNumber = "0.0";
        }
        roundedNumber = Double.parseDouble(testNumber);
        sc1 = .5 * Math.round(roundedNumber * 2);
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
                    b.putDouble("boardType", boardType);
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
