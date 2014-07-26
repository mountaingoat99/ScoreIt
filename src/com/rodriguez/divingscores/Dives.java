package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.controls.EditTextLocker;
import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.BackDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.InwardDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.ReverseDatabase;
import info.sqlite.helper.TwistDatabase;

public class Dives extends Activity implements OnItemSelectedListener
{
	private Spinner spinner;
    private RadioButton radioTuck, radioPike, radioFree, radioStraight;
    private TextView view4, view5, view6, view7;
    private EditText score1, score2, score3, score4, score5, score6, score7;
    private String s1, s2, s3, s4, s5, s6, s7;
    private int judges;
    private int diverId;
    private int meetId;
    private int diveType;
    private int divePosition = 1;
    private int diveTotal = 0;
    private int boardType = 0;
    private double sc1 = 0.0, sc2 = 0.0, sc3 = 0.0, sc4 = 0.0, sc5 = 0.0,
                    sc6 = 0.0, sc7 = 0.0, diveScoreTotal = 0.0, multiplier = 0.0;
    private double dive1 = 0.0, dive2 = 0.0, dive3 = 0.0, dive4 = 0.0,
                    dive5 = 0.0, dive6 = 0.0, dive7 = 0.0, dive8 = 0.0,
                    dive9 = 0.0, dive10 = 0.0, dive11 = 0.0, total = 0.0;

    private List<String> diveName;
    ArrayList<Double> Scores = new ArrayList<>();
    boolean missed = false;
    boolean hidebtn;
    String stringId = null;

    private EditText editText;

    private EditText decimalEditText;

	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dives);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if(savedInstanceState != null){
            s1 = savedInstanceState.getString("score1");
            s2 = savedInstanceState.getString("score2");
            s3 = savedInstanceState.getString("score3");
            s4 = savedInstanceState.getString("score4");
            s5 = savedInstanceState.getString("score5");
            s6 = savedInstanceState.getString("score6");
            s7 = savedInstanceState.getString("score7");
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        setDownFocus();
        setEditText();

        if(s1 != null)
            score1.setText(s1);
        if(s2 != null)
            score1.setText(s2);
        if(s3 != null)
            score1.setText(s3);
        if(s4 != null)
            score1.setText(s4);
        if(s5 != null)
            score1.setText(s5);
        if(s6 != null)
            score1.setText(s6);
        if(s7 != null)
            score1.setText(s7);

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diveType = b.getInt("diveType");
        boardType = b.getInt("boardType");

        setTitle();
        getDiveTotals();
        showScores();
        addListenerOnButton();
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
        checkRadios();
    }

    private void setEditText() {

        EditTextLocker editTextLocker1 = new EditTextLocker(score1);
        editTextLocker1.limitCharacters(3);
        editTextLocker1.limitFractionDigitsinDecimal(1);
        //editTextLocker1.maxTextInput(11);

        EditTextLocker editTextLocker2 = new EditTextLocker(score2);
        editTextLocker2.limitCharacters(3);
        editTextLocker2.limitFractionDigitsinDecimal(3);
        //editTextLocker2.maxTextInput(11);

        EditTextLocker editTextLocker3 = new EditTextLocker(score3);
        editTextLocker3.limitCharacters(3);
        editTextLocker3.limitFractionDigitsinDecimal(3);
        //editTextLocker3.maxTextInput(11);

        EditTextLocker editTextLocker4 = new EditTextLocker(score4);
        editTextLocker4.limitCharacters(3);
        editTextLocker4.limitFractionDigitsinDecimal(3);
        //editTextLocker4.maxTextInput(11);

        EditTextLocker editTextLocker5 = new EditTextLocker(score5);
        editTextLocker5.limitCharacters(3);
        editTextLocker5.limitFractionDigitsinDecimal(3);
        //editTextLocker5.maxTextInput(11);

        EditTextLocker editTextLocker6 = new EditTextLocker(score6);
        editTextLocker6.limitCharacters(3);
        editTextLocker6.limitFractionDigitsinDecimal(3);
        //editTextLocker6.maxTextInput(11);

        EditTextLocker editTextLocker7 = new EditTextLocker(score7);
        editTextLocker7.limitCharacters(3);
        editTextLocker7.limitFractionDigitsinDecimal(3);
        //editTextLocker7.maxTextInput(11);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        outState.putString("score1", score1.getText().toString());
        outState.putString("score2", score2.getText().toString());
        outState.putString("score3", score3.getText().toString());
        outState.putString("score4", score4.getText().toString());
        outState.putString("score5", score5.getText().toString());
        outState.putString("score6", score6.getText().toString());
        outState.putString("score7", score7.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if(id < 0)
            hidebtn = false;
        else
            hidebtn = true;

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
    
    public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnTotal = (Button) findViewById(R.id.buttonScore);

    	btnTotal.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (hidebtn) {
                    getScoreText();
                    if (missed)
                        return;
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
        total = db.getTotalScore(meetId, diverId);                  // gets total score
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

        dive1 = scores.get(0);
        dive2 = scores.get(1);
        dive3 = scores.get(2);
        dive4 = scores.get(3);
        dive5 = scores.get(4);
        dive6 = scores.get(5);
        dive7 = scores.get(6);
        dive8 = scores.get(7);
        dive9 = scores.get(8);
        dive10 = scores.get(9);
        dive11 = scores.get(10);

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
        missed = false;
        if(!TextUtils.isEmpty(score1.getText())) {
            sc1 = Double.parseDouble(score1.getText().toString());
            Scores.add(sc1);
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "You missed score one!",
                    Toast.LENGTH_LONG
            ).show();
            missed = true;
            return;
        }
        if(!TextUtils.isEmpty(score2.getText())) {
            sc2 = Double.parseDouble(score2.getText().toString());
            Scores.add(sc2);
        }else{
            Toast.makeText(getApplicationContext(),
                    "You missed score two!",
                    Toast.LENGTH_LONG
            ).show();
            missed = true;
            return;
        }
        if(!TextUtils.isEmpty(score3.getText())) {
            sc3 = Double.parseDouble(score3.getText().toString());
            Scores.add(sc3);
        }else{
            Toast.makeText(getApplicationContext(),
                    "You missed score three!",
                    Toast.LENGTH_LONG
            ).show();
            missed = true;
            return;
        }

        if(judges == 5){
            if(!TextUtils.isEmpty(score4.getText())) {
                sc4 = Double.parseDouble(score4.getText().toString());
                Scores.add(sc4);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score four!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
                return;
            }
            if(!TextUtils.isEmpty(score5.getText())) {
                sc5 = Double.parseDouble(score5.getText().toString());
                Scores.add(sc5);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score five!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
                return;
            }
        }
        if(judges == 7){
            if(!TextUtils.isEmpty(score4.getText())) {
                sc4 = Double.parseDouble(score4.getText().toString());
                Scores.add(sc4);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score four!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
                return;
            }
            if(!TextUtils.isEmpty(score5.getText())) {
                sc5 = Double.parseDouble(score5.getText().toString());
                Scores.add(sc5);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score five!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
                return;
            }
            if(!TextUtils.isEmpty(score6.getText())) {
                sc6 = Double.parseDouble(score6.getText().toString());
                Scores.add(sc6);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score six!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
                return;
            }
            if(!TextUtils.isEmpty(score7.getText())) {
                sc7 = Double.parseDouble(score7.getText().toString());
                Scores.add(sc7);
            }else{
                Toast.makeText(getApplicationContext(),
                        "You missed score seven!",
                        Toast.LENGTH_LONG
                ).show();
                missed = true;
            }
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
        diveTotal = db.searchTotals(meetId, diverId);
        // TODO add in a way to not let user input anything else if diveTotal is 11
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

    private void setUpView(){radioFree = (RadioButton)findViewById(R.id.radioFree);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        score1 = (EditText)findViewById(R.id.editScore1);
        score2 = (EditText)findViewById(R.id.editScore2);
        score3 = (EditText)findViewById(R.id.editScore3);
        score4 = (EditText)findViewById(R.id.editScore4);
        score5 = (EditText)findViewById(R.id.editScore5);
        score6 = (EditText)findViewById(R.id.editScore6);
        score7 = (EditText)findViewById(R.id.editScore7);
        view4 =  (TextView)findViewById(R.id.score4);
        view5 =  (TextView)findViewById(R.id.score5);
        view6 =  (TextView)findViewById(R.id.score6);
        view7 =  (TextView)findViewById(R.id.score7);
        spinner = (Spinner)findViewById(R.id.listDives);
    }
    private void setDownFocus(){
        score1.setNextFocusDownId(R.id.editScore2);
        score2.setNextFocusDownId(R.id.editScore3);
        score3.setNextFocusDownId(R.id.editScore4);
        score4.setNextFocusDownId(R.id.editScore5);
        score5.setNextFocusDownId(R.id.editScore6);
        score6.setNextFocusDownId(R.id.editScore7);
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
