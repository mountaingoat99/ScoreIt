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
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.InwardDatabase;
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
    private int judges, diverId, meetId, diveType, divePosition = 1, boardType = 0;
    private double sc1, sc2, sc3, diveScoreTotal = 0.0, multiplier = 0.0;
    private List<String> diveName;
    private ArrayList<Double> Scores = new ArrayList<>();
    boolean hidebutton;
    String stringId = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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
        getDiveTotals();
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
                ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                diveName = fdb.getForwardNames();
                break;
            case 2:
                BackDatabase bdb = new BackDatabase(getApplicationContext());
                diveName = bdb.getBackNames();
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                diveName = rdb.getReverseNames();
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase(getApplicationContext());
                diveName = idb.getInwardNames();
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                diveName = tdb.getTwistNames();
                break;
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
                    getScoreText();
                    //if (missed)
                        //return;
                    calcScores();
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    Intent intent = new Intent(context, ChooseSummary.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Dive Category",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void calcScores() {
        ResultDatabase db = new ResultDatabase(getApplicationContext());
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
        getMultiplier();
        double diveTotal = diveScoreTotal * multiplier;
        total = total + diveTotal;                             // gets total incremented meet score

        ArrayList<Double> scores;
        scores = db.checkResults(meetId, diverId);                  // checks the previous scores

        double dive1 = scores.get(0);
        double dive2 = scores.get(1);
        double dive3 = scores.get(2);
        double dive4 = scores.get(3);
        double dive5 = scores.get(4);
        double dive6 = scores.get(5);
        double dive7 = scores.get(6);
        double dive8 = scores.get(7);
        double dive9 = scores.get(8);
        double dive10 = scores.get(9);
        double dive11 = scores.get(10);

        int resultIndex;
        if(dive1 == 0.0){                                           // if previous is empty fills the next one
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive2 == 0.0){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive3 == 0.0){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive4 == 0.0){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive5 == 0.0){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive6 == 0.0){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive7 == 0.0){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive8 == 0.0){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive9 == 0.0){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive10 == 0.0){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
            return;
        }
        if(dive11 == 0.0){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, total);
        }
    }

    private void getScoreText(){
        sc1 = Double.parseDouble(score1.getSelectedItem().toString());
        Scores.add(sc1);
        sc2 = Double.parseDouble(score2.getSelectedItem().toString());
        Scores.add(sc2);
        sc3 = Double.parseDouble(score3.getSelectedItem().toString());
        Scores.add(sc3);

        double sc5;
        double sc4;
        if(judges == 5){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString());
            Scores.add(sc5);
        }
        if(judges == 7){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString());
            Scores.add(sc5);
            double sc6 = Double.parseDouble(score6.getSelectedItem().toString());
            Scores.add(sc6);
            double sc7 = Double.parseDouble(score7.getSelectedItem().toString());
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

    private void getDiveTotals(){
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        int diveTotal = db.searchTotals(meetId, diverId);
        // TODO add in a way to not let user input anything else if diveTotal is 11, might not need this
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
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_dives, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
