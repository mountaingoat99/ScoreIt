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
import info.sqlite.helper.DiveListDatabase;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
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

public class EnterFinalDiveScore extends ActionBarActivity implements OnItemSelectedListener {
    private Spinner spinner;
    private EditText score1;
    private TextView DD, name;
    private RadioButton radioTuck, radioPike, radioFree, radioStraight;
    private int diverId, meetId, diveType, diveNumber, diveTotal, divePosition;
    private double boardType = 0.0;
    private double  sc1 = 0.0, multiplier = 0.0, total = 0.0;
    private ArrayList<DiveStyleSpinner> searchDives;
    private String failedDive = "P", ddString, stringId, className = "nonList";
    private static final String KEY_TEXT_VALUE = "textValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_final_dive_score);
        if (savedInstanceState != null) {
            stringId = savedInstanceState.getString(KEY_TEXT_VALUE);
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
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
        getDiveTotal();
        addListenerOnButton();
        checkRadios();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_VALUE, stringId);
    }

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        diveNumber = num.doInBackground();
    }

    private void getDiveTotal(){
        SearchDiveTotals search = new SearchDiveTotals();
        diveTotal = search.doInBackground();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null) {
            stringId = name.getText().toString();
        }
        DisableRadioButtons();
        getMultiplier();
    }

    private void DisableRadioButtons(){
        int diveId;
        double testS = 0.0;
        double testP = 0.0;
        double testT = 0.0;
        double testF = 0.0;

            switch (diveType) {
                case 1:
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    diveId = fdb.getDiveId(stringId);
                    testS = fdb.getDOD(diveId, 1, boardType);
                    testP = fdb.getDOD(diveId, 2, boardType);
                    testT = fdb.getDOD(diveId, 3, boardType);
                    testF = fdb.getDOD(diveId, 4, boardType);
                    break;
                case 2:
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    diveId = bdb.getDiveId(stringId);
                    testS = bdb.getDOD(diveId, 1, boardType);
                    testP = bdb.getDOD(diveId, 2, boardType);
                    testT = bdb.getDOD(diveId, 3, boardType);
                    testF = bdb.getDOD(diveId, 4, boardType);
                    break;
                case 3:
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    diveId = rdb.getDiveId(stringId);
                    testS = rdb.getDOD(diveId, 1, boardType);
                    testP = rdb.getDOD(diveId, 2, boardType);
                    testT = rdb.getDOD(diveId, 3, boardType);
                    testF = rdb.getDOD(diveId, 4, boardType);
                    break;
                case 4:
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    diveId = idb.getDiveId(stringId);
                    testS = idb.getDOD(diveId, 1, boardType);
                    testP = idb.getDOD(diveId, 2, boardType);
                    testT = idb.getDOD(diveId, 3, boardType);
                    testF = idb.getDOD(diveId, 4, boardType);
                    break;
                case 5:
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    diveId = tdb.getDiveId(stringId);
                    testS = tdb.getDOD(diveId, 1, boardType);
                    testP = tdb.getDOD(diveId, 2, boardType);
                    testT = tdb.getDOD(diveId, 3, boardType);
                    testF = tdb.getDOD(diveId, 4, boardType);
                    break;
                case 6:
                    ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(getApplicationContext());
                    diveId = fpdb.getDiveId(stringId);
                    testS = fpdb.getDOD(diveId, 1, boardType);
                    testP = fpdb.getDOD(diveId, 2, boardType);
                    testT = fpdb.getDOD(diveId, 3, boardType);
                    testF = fpdb.getDOD(diveId, 4, boardType);
                    break;
                case 7:
                    BackPlatformDatabase bpdb = new BackPlatformDatabase(getApplicationContext());
                    diveId = bpdb.getDiveId(stringId);
                    testS = bpdb.getDOD(diveId, 1, boardType);
                    testP = bpdb.getDOD(diveId, 2, boardType);
                    testT = bpdb.getDOD(diveId, 3, boardType);
                    testF = bpdb.getDOD(diveId, 4, boardType);
                    break;
                case 8:
                    ReversePlatformDatabase rpdb = new ReversePlatformDatabase(getApplicationContext());
                    diveId = rpdb.getDiveId(stringId);
                    testS = rpdb.getDOD(diveId, 1, boardType);
                    testP = rpdb.getDOD(diveId, 2, boardType);
                    testT = rpdb.getDOD(diveId, 3, boardType);
                    testF = rpdb.getDOD(diveId, 4, boardType);
                    break;
                case 9:
                    InwardPlatformDatabase ipdb = new InwardPlatformDatabase(getApplicationContext());
                    diveId = ipdb.getDiveId(stringId);
                    testS = ipdb.getDOD(diveId, 1, boardType);
                    testP = ipdb.getDOD(diveId, 2, boardType);
                    testT = ipdb.getDOD(diveId, 3, boardType);
                    testF = ipdb.getDOD(diveId, 4, boardType);
                    break;
                case 10:
                    TwistPlatformDatabase tpdb = new TwistPlatformDatabase(getApplicationContext());
                    diveId = tpdb.getDiveId(stringId);
                    testS = tpdb.getDOD(diveId, 1, boardType);
                    testP = tpdb.getDOD(diveId, 2, boardType);
                    testT = tpdb.getDOD(diveId, 3, boardType);
                    testF = tpdb.getDOD(diveId, 4, boardType);
                    break;
                case 11:
                    ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(getApplicationContext());
                    diveId = apdb.getDiveId(stringId);
                    testS = apdb.getDOD(diveId, 1, boardType);
                    testP = apdb.getDOD(diveId, 2, boardType);
                    testT = apdb.getDOD(diveId, 3, boardType);
                    testF = apdb.getDOD(diveId, 4, boardType);
                    break;
            }

        if (testS == 0.0){
            radioStraight.setEnabled(false);
            radioStraight.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioStraight.setEnabled(true);
            radioStraight.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testP == 0.0){
            radioPike.setEnabled(false);
            radioPike.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioPike.setEnabled(true);
            radioPike.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testT == 0.0){
            radioTuck.setEnabled(false);
            radioTuck.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioTuck.setEnabled(true);
            radioTuck.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testF == 0.0){
            radioFree.setEnabled(false);
            radioFree.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioFree.setEnabled(true);
            radioFree.setTextColor(this.getResources().getColor(R.color.random_text));
        }
    }

    // load the spinner data from db
    private void loadSpinnerData(){

        switch (diveType){
            // Springboard Dives
            case 1:
                if(boardType == 1) {
                    GetForwardOneNames f1Name = new GetForwardOneNames();
                    searchDives = f1Name.doInBackground();
                    break;
                } else {
                    GetForwardThreeNames f3Name = new GetForwardThreeNames();
                    searchDives = f3Name.doInBackground();
                    break;
                }
            case 2:
                if(boardType == 1) {
                    GetBackOneNames b1Name = new GetBackOneNames();
                    searchDives = b1Name.doInBackground();
                    break;
                } else {
                    GetBackThreeNames b3Name = new GetBackThreeNames();
                    searchDives = b3Name.doInBackground();
                    break;
                }
            case 3:
                if(boardType == 1){
                    GetReverseOneNames r1Name = new GetReverseOneNames();
                    searchDives = r1Name.doInBackground();
                    break;
                } else {
                    GetReverseThreeNames r3Name = new GetReverseThreeNames();
                    searchDives = r3Name.doInBackground();
                    break;
                }
            case 4:
                if(boardType == 1) {
                    GetInwardOneNames i1Name = new GetInwardOneNames();
                    searchDives = i1Name.doInBackground();
                    break;
                } else {
                    GetInwardThreeNames i3Name = new GetInwardThreeNames();
                    searchDives = i3Name.doInBackground();
                    break;
                }
            case 5:
                if(boardType == 1) {
                    GetTwistOneNames t1Name = new GetTwistOneNames();
                    searchDives = t1Name.doInBackground();
                    break;
                } else {
                    GetTwistThreeNames t3Name = new GetTwistThreeNames();
                    searchDives = t3Name.doInBackground();
                    break;
                }
                //platform dives
            case 6:
                if(boardType == 10) {
                    GetFrontTenNames f10 = new GetFrontTenNames();
                    searchDives = f10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetFrontSevenNames f7 = new GetFrontSevenNames();
                    searchDives = f7.doInBackground();
                    break;
                } else {
                    GetFrontFiveNames f5 = new GetFrontFiveNames();
                    searchDives = f5.doInBackground();
                    break;
                }
            case 7:
                if(boardType == 10) {
                    GetBackTenNames b10 = new GetBackTenNames();
                    searchDives = b10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetBackSevenNames b7 = new GetBackSevenNames();
                    searchDives = b7.doInBackground();
                    break;
                } else {
                    GetBackFiveNames b5 = new GetBackFiveNames();
                    searchDives = b5.doInBackground();
                    break;
                }
            case 8:
                if(boardType == 10) {
                    GetReverseTenNames r10 = new GetReverseTenNames();
                    searchDives = r10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetReverseSevenNames r7 = new GetReverseSevenNames();
                    searchDives = r7.doInBackground();
                    break;
                } else {
                    GetReverseFiveNames r5 = new GetReverseFiveNames();
                    searchDives = r5.doInBackground();
                    break;
                }
            case 9:
                if(boardType == 10) {
                    GetInwardTenNames ip10 = new GetInwardTenNames();
                    searchDives = ip10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetInwardSevenNames ip7 = new GetInwardSevenNames();
                    searchDives = ip7.doInBackground();
                    break;
                } else {
                    GetInwardFiveNames ip5 = new GetInwardFiveNames();
                    searchDives = ip5.doInBackground();
                    break;
                }
            case 10:
                if(boardType == 10) {
                    GetTwistTenNames tp10 = new GetTwistTenNames();
                    searchDives = tp10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetTwistSevenNames tp7 = new GetTwistSevenNames();
                    searchDives = tp7.doInBackground();
                    break;
                } else {
                    GetTwistFiveNames tp5 = new GetTwistFiveNames();
                    searchDives = tp5.doInBackground();
                    break;
                }
            case 11:
                if(boardType == 10) {
                    GetArmstandTenNames a10 = new GetArmstandTenNames();
                    searchDives = a10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetArmstandSevenNames a7 = new GetArmstandSevenNames();
                    searchDives = a7.doInBackground();
                    break;
                } else {
                    GetArmstandFiveNames a5 = new GetArmstandFiveNames();
                    searchDives = a5.doInBackground();
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
        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null && divePosition != 0) {
            stringId = name.getText().toString();
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
                ddString = "Dive DD: " + multiplier;
                DD.setText(ddString);
        } else {
            ddString = "Dive DD: ";
            DD.setText(ddString);
        }
    }

    private void incrementDiveNumber(){
        IncrementDiveNumber divenumber = new IncrementDiveNumber();
        if (diveNumber != diveTotal) {
            diveNumber++;
            divenumber.doInBackground();
        }
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
            updateDiveListToYes();
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
                failedDive, total, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, multiplier);
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

    private void updateDiveListToYes(){
        UpdateDiveListToYes yes = new UpdateDiveListToYes();
        yes.doInBackground();
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
                getMultiplier();
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
                getMultiplier();
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
                getMultiplier();
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
                getMultiplier();
            }
        });
    }

    private void setUpView(){

        radioFree = (RadioButton)findViewById(R.id.radioFree);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        score1 = (EditText)findViewById(R.id.editScore1);
        DD = (TextView)findViewById(R.id.Divider);
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
                    TextView name = (TextView) findViewById(R.id.diveStyle);
                    TextView id = (TextView) findViewById(R.id.diveId);
                    String i = id.getText().toString();
                    String diveTypeName = i + " - " + name.getText().toString();
                    Intent intent = new Intent(context, FailedDive.class);
                    Bundle b = new Bundle();
                    b.putString("className", className);
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keyDiveType", diveType);
                    b.putString("keyDiveTypeName", diveTypeName);
                    b.putInt("keyDivePosition", divePosition);
                    b.putDouble("boardType", boardType);
                    b.putDouble("multiplier", multiplier);
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

    private class UpdateDiveListToYes extends AsyncTask<Object, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params){
            db.setNoList(meetId, diverId);
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

    private class IncrementDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateDiveNumber(meetId, diverId, diveNumber);
            return null;
        }
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object> {
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        int num;

        @Override
        protected Integer doInBackground(Integer... params) {
            return num = db.getDiveNumber(meetId, diverId);
        }
    }

    // SpringBoard Dives
    private class GetForwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardDatabase db = new ForwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardOneNames();
        }
    }

    private class GetForwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardDatabase db = new ForwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardThreeNames();
        }
    }

    private class GetBackOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackOneNames();
        }
    }

    private class GetBackThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackThreeNames();
        }
    }

    private class GetReverseOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseOneNames();
        }
    }

    private class GetReverseThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseThreeNames();
        }
    }

    private class GetInwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardOneNames();
        }
    }

    private class GetInwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardThreeNames();
        }
    }

    private class GetTwistOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistOneNames();
        }
    }

    private class GetTwistThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistThreeNames();
        }
    }

    // Platform dives
    private class GetFrontTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformTenNames();
        }
    }

    private class GetFrontSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformSevenFiveNames();
        }
    }

    private class GetFrontFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformFiveNames();
        }
    }

    private class GetBackTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformTenNames();
        }
    }

    private class GetBackSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformSevenFiveNames();
        }
    }
    private class GetBackFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformFiveNames();
        }
    }

    private class GetReverseTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformTenNames();
        }
    }

    private class GetReverseSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformSevenFiveNames();
        }
    }

    private class GetReverseFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformFiveNames();
        }
    }

    private class GetInwardTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformTenNames();
        }
    }

    private class GetInwardSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformSevenFiveNames();
        }
    }

    private class GetInwardFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformFiveNames();
        }
    }

    private class GetTwistTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformTenNames();
        }
    }

    private class GetTwistSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformSevenFiveNames();
        }
    }

    private class GetTwistFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformFiveNames();
        }
    }

    private class GetArmstandTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandTenNames();
        }
    }

    private class GetArmstandSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandSevenFiveNames();
        }
    }

    private class GetArmstandFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandFiveNames();
        }
    }

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object>{
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }
}
