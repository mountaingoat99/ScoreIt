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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.BackDatabase;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.InwardDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.ReverseDatabase;
import info.sqlite.helper.ScoresDatabase;
import info.sqlite.helper.TwistDatabase;

public class Dives extends Activity implements OnItemSelectedListener
{
	private Spinner spinner;
    private RadioButton radioTuck, radioPike, radioFree, radioStraight;
    private TextView view4, view5, view6, view7;
    private Spinner score1, score2, score3, score4, score5, score6, score7;
    private int judges, diverId, meetId, diveType, diveNumber, divePosition = 1, boardType = 0;
    private double sc1, sc2, sc3, sc4, sc5, sc6, sc7, diveScoreTotal = 0.0, multiplier = 0.0;
    private List<String> diveName;
    private ArrayList<Double> Scores = new ArrayList<>();
    boolean hidebutton, ifZeroTotal = true;
    String stringId = null, failedDive = "P";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dives);
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

        score1.setOnItemSelectedListener(this);
        score2.setOnItemSelectedListener(this);
        score3.setOnItemSelectedListener(this);
        score4.setOnItemSelectedListener(this);
        score5.setOnItemSelectedListener(this);
        score6.setOnItemSelectedListener(this);
        score7.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        loadScoreSpinners();
        loadSpinnerData();
        setTitle();
        getDiveNumber();
        showScores();
        addListenerOnButton();
        checkRadios();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if(spinner.getSelectedItemPosition() == 0)
            hidebutton = false;
        else
            hidebutton = true;
    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber = db.getDiveNumber(meetId, diverId);
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

    //loads the spinners for the scores
    private void loadScoreSpinners(){
        ScoresDatabase db = new ScoresDatabase(getApplicationContext());
        List<String> scoreNames = db.getScores();

        ArrayAdapter<String> da = new ArrayAdapter<>(this,
                R.layout.spinner_item, scoreNames);
        da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        score1.setAdapter(da);
        score2.setAdapter(da);
        score3.setAdapter(da);
        score4.setAdapter(da);
        score5.setAdapter(da);
        score6.setAdapter(da);
        score7.setAdapter(da);
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
                        incrementDiveNumber();
                        calcScores();
                        if(ifZeroTotal) {
                            updateJudgeScores();
                            Bundle b = new Bundle();
                            b.putInt("keyDiver", diverId);
                            b.putInt("keyMeet", meetId);
                            Intent intent = new Intent(context, ChooseSummary.class);
                            intent.putExtras(b);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please enter at least one judge score," +
                                            " or fail the dive using the menu button.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else{
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

    private void incrementDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber ++;
        db.updateDiveNumber(meetId, diverId, diveNumber);
    }

    private void calcScores() {
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double diveTotal;
        double total = db.getTotalScore(meetId, diverId);
        // Converts and sorts the ArrayList for processing
        Double[] theScores = new Double[ Scores.size()];
        Scores.toArray(theScores);
        Arrays.sort(theScores);

        if(judges == 3){
            diveScoreTotal = sc1 + sc2 + sc3;
        }else if (judges == 5){
            // converts the sorted array to a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for (int i = 0; i < list.size(); i++){
                diveScoreTotal = diveScoreTotal + list.get(i);
            }
        } else if (judges == 7){
            // converts the sorted array a list and removes the smallest and largest scores
            List<Double> list = new ArrayList<>(Arrays.asList(theScores));
            list.remove(0);
            list.remove(0);
            list.remove(4);
            list.remove(3);
            // iterates through to get the sum of the three scores
            for(int i = 0; i < list.size(); i++){
                diveScoreTotal = diveScoreTotal + list.get(i);
            }
        }
        if(multiplier != 0.0) {
            diveTotal = diveScoreTotal * multiplier;
        }else{
            diveTotal = diveScoreTotal;
        }
        total = total + diveTotal;

        if(diveTotal < .5){
            ifZeroTotal = false;
            return;
        }

        int resultIndex;
        if(diveNumber == 1){
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 2){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 3){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 4){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 5){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 6){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 7){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 8){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 9){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 10){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(diveNumber == 11){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
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

        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveTypeName, DivePosition,
                             failedDive, sc1, sc2, sc3, sc4, sc5, sc6, sc7, multiplier);
    }

    private void getScoreText(){
        Scores.clear();
        sc1 = Double.parseDouble(score1.getSelectedItem().toString());
        Scores.add(sc1);
        sc2 = Double.parseDouble(score2.getSelectedItem().toString());
        Scores.add(sc2);
        sc3 = Double.parseDouble(score3.getSelectedItem().toString());
        Scores.add(sc3);
        sc4 = 0.0;
        sc5 = 0.0;
        sc6 = 0.0;
        sc7 = 0.0;

        if(judges == 5){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString());
            Scores.add(sc5);
            sc6 = 0.0;
            sc7 = 0.0;

        }
        if(judges == 7){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString());
            Scores.add(sc5);
            sc6 = Double.parseDouble(score6.getSelectedItem().toString());
            Scores.add(sc6);
            sc7 = Double.parseDouble(score7.getSelectedItem().toString());
            Scores.add(sc7);
        }
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

    private void showScores(){
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        judges = db.getJudges(meetId);

        if(judges == 3){
            score4.setVisibility(View.INVISIBLE);
            view4.setVisibility(View.INVISIBLE);
            score5.setVisibility(View.INVISIBLE);
            view5.setVisibility(View.INVISIBLE);
            score6.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            view7.setVisibility(View.INVISIBLE);
        } else if(judges == 5){
            score6.setVisibility(View.INVISIBLE);
            view6.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            view7.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpView(){

        radioFree = (RadioButton)findViewById(R.id.radioFree);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        score1 = (Spinner)findViewById(R.id.editScore1);
        score2 = (Spinner)findViewById(R.id.editScore2);
        score3 = (Spinner)findViewById(R.id.editScore3);
        score4 = (Spinner)findViewById(R.id.editScore4);
        score5 = (Spinner)findViewById(R.id.editScore5);
        score6 = (Spinner)findViewById(R.id.editScore6);
        score7 = (Spinner)findViewById(R.id.editScore7);
        view4 =  (TextView)findViewById(R.id.score4);
        view5 =  (TextView)findViewById(R.id.score5);
        view6 =  (TextView)findViewById(R.id.score6);
        view7 =  (TextView)findViewById(R.id.score7);
        spinner = (Spinner)findViewById(R.id.listDives);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dives, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
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
