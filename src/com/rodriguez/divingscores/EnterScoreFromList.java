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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.ScoresDatabase;


public class EnterScoreFromList extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private Spinner score1, score2, score3, score4, score5, score6, score7;
    private TextView header, view4, view5, view6, view7;
    private Button btnTotal;
    private int judges, diverId, meetId, diveNumber, dbDiveNumber, diverSpinnerPosition, totalDives;
    private double sc1, sc2, sc3, sc4, sc5, sc6, sc7, diveScoreTotal = 0.0,
            multiplier = 0.0, diveTotal = 0.0, oldTotalScore, newTotalScore, total;
    private ArrayList<Double> Scores = new ArrayList<>();
    private boolean ifZeroTotal = true;
    private String stringId, className = "EnterScoreFromList";
    private static final String KEY_TEXT_VALUE = "textValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_score_from_list);

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
        diveNumber = b.getInt("diveNumber");
        diverSpinnerPosition = b.getInt("keySpin");

        score1.setOnItemSelectedListener(this);
        score2.setOnItemSelectedListener(this);
        score3.setOnItemSelectedListener(this);
        score4.setOnItemSelectedListener(this);
        score5.setOnItemSelectedListener(this);
        score6.setOnItemSelectedListener(this);
        score7.setOnItemSelectedListener(this);
        loadScoreSpinners();
        checkMultiplier();
        setTitle();
        showScores();
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
                calcScores();
                if(ifZeroTotal) {
                    updateJudgeScores();
                    incrementDiveNumber();
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
                    Intent intent = new Intent(context, ChooseDivesFromList.class);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Scores entered are not valid. Please enter an accurate score," +
                                    "or use the menu button to fail the dive " +
                                    "or score a 2 Judge Meet.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkDiveTotal(){
        SearchDiveTotals search = new SearchDiveTotals();
        totalDives = search.doInBackground();
    }

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        dbDiveNumber = num.doInBackground();
    }

    private void updateJudgeScores(){
        UpdateJudgeScores judgescore = new UpdateJudgeScores();
        judgescore.doInBackground();
    }

    private void incrementDiveNumber(){
        if (dbDiveNumber == 0) {
            IncrementDiveNumber divenumber = new IncrementDiveNumber();
            divenumber.doInBackground();
        }
    }

    // this is used when a meet only has two judges
    private void getTwoJudgeScoreText(){
        double score;
        sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
        sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
        score = (sc1 + sc2) / 2;
        //roundedScore = .5 * Math.round(((sc1 + sc2) / 2) * 2);
        sc3 = score;
        sc4 = 0.0;
        sc5 = 0.0;
        sc6 = 0.0;
        sc7 = 0.0;
    }

    private void getScoreText(){
        Scores.clear();
        sc1 = Double.parseDouble(score1.getSelectedItem().toString().trim());
        Scores.add(sc1);
        sc2 = Double.parseDouble(score2.getSelectedItem().toString().trim());
        Scores.add(sc2);
        sc3 = Double.parseDouble(score3.getSelectedItem().toString().trim());
        Scores.add(sc3);
        sc4 = 0.0;
        sc5 = 0.0;
        sc6 = 0.0;
        sc7 = 0.0;

        if(judges == 5){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString().trim());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString().trim());
            Scores.add(sc5);
            sc6 = 0.0;
            sc7 = 0.0;

        }
        if(judges == 7){
            sc4 = Double.parseDouble(score4.getSelectedItem().toString().trim());
            Scores.add(sc4);
            sc5 = Double.parseDouble(score5.getSelectedItem().toString().trim());
            Scores.add(sc5);
            sc6 = Double.parseDouble(score6.getSelectedItem().toString().trim());
            Scores.add(sc6);
            sc7 = Double.parseDouble(score7.getSelectedItem().toString().trim());
            Scores.add(sc7);
        }
    }

    private void calcScores() {
        ifZeroTotal = true;
        // Converts and sorts the ArrayList for processing
        Double[] theScores = new Double[ Scores.size()];
        Scores.toArray(theScores);
        Arrays.sort(theScores);

        if(judges == 3){
            if(sc1 == 0.0 || sc2 == 0.0 || sc3 == 0.0){
            ifZeroTotal = false;
            return;
        }else {
            diveScoreTotal = sc1 + sc2 + sc3;
        }
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

        //roundedDiveTotal = .5 * Math.round(diveTotal * 2);



        if(diveTotal < .5){
            ifZeroTotal = false;
            return;
        }


        // Get the old score, even if it is the first entry
        GetDiveScore oldS = new GetDiveScore();
        oldTotalScore = oldS.doInBackground();


        // grabbing the old total score here
        GetTotalScore tScore = new GetTotalScore();
        total = tScore.doInBackground();

        // subtract the old score, and then add the new score
        // had to do it this way incase they change a dive score. Old way was incrementing edited totals
        double scoreTemp = total - oldTotalScore;
        newTotalScore = scoreTemp + diveTotal;


        ResultDatabase db = new ResultDatabase(getApplicationContext());
        int resultIndex;
        if(diveNumber == 1){
            resultIndex = 3;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 2){
            resultIndex = 4;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 3){
            resultIndex = 5;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 4){
            resultIndex = 6;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 5){
            resultIndex = 7;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 6){
            resultIndex = 8;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 7){
            resultIndex = 9;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 8){
            resultIndex = 10;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 9){
            resultIndex = 11;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 10){
            resultIndex = 12;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
            return;
        }
        if(diveNumber == 11){
            resultIndex = 13;
            db.writeDiveScore(meetId, diverId, resultIndex, diveTotal, newTotalScore);
        }
    }

    private void setTitle() {
        setTitle("Enter Scores");
        String info = getDiveInfoFromDB(diveNumber);
        header.setText(info);
    }

    private void checkMultiplier(){
        CheckMultiplier cm = new CheckMultiplier();
        multiplier = cm.doInBackground();
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

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TEXT_VALUE, stringId);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    //loads the spinners for the scores
    private void loadScoreSpinners(){
        int spinnerPosition = 10;
        GetScoreNames snames = new GetScoreNames();
        List<String> scoreNames = snames.doInBackground();

        ArrayAdapter<String> da = new ArrayAdapter<>(this,
                R.layout.spinner_item, scoreNames);
        da.setDropDownViewResource(R.layout.spinner_layout);
        score1.setAdapter(da);
        score1.setSelection(spinnerPosition);
        score2.setAdapter(da);
        score2.setSelection(spinnerPosition);
        score3.setAdapter(da);
        score3.setSelection(spinnerPosition);
        score4.setAdapter(da);
        score4.setSelection(spinnerPosition);
        score5.setAdapter(da);
        score5.setSelection(spinnerPosition);
        score6.setAdapter(da);
        score6.setSelection(spinnerPosition);
        score7.setAdapter(da);
        score7.setSelection(spinnerPosition);
    }

    private void showScores(){
        GetJudgeTotal jt = new GetJudgeTotal();
        judges = jt.doInBackground();

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
        score1 = (Spinner)findViewById(R.id.editScore1);
        score2 = (Spinner)findViewById(R.id.editScore2);
        score3 = (Spinner)findViewById(R.id.editScore3);
        score4 = (Spinner)findViewById(R.id.editScore4);
        score5 = (Spinner)findViewById(R.id.editScore5);
        score6 = (Spinner)findViewById(R.id.editScore6);
        score7 = (Spinner)findViewById(R.id.editScore7);
        header = (TextView)findViewById(R.id.textView);
        btnTotal = (Button)findViewById(R.id.buttonScore);
        view4 =  (TextView)findViewById(R.id.score4);
        view5 =  (TextView)findViewById(R.id.score5);
        view6 =  (TextView)findViewById(R.id.score6);
        view7 =  (TextView)findViewById(R.id.score7);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_score_from_list, menu);
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
            case R.id.menu_two_judge_option:
                if (judges == 3) {
                    checkMultiplier();
                    if (multiplier != 0.0) {
                        Double test3Spin;
                        test3Spin = Double.parseDouble(score3.getSelectedItem().toString().trim());
                        if (test3Spin == 0.0){
                            getTwoJudgeScoreText();
                            calcScores();
                            if(ifZeroTotal) {
                                updateJudgeScores();
                                Bundle bundle = new Bundle();
                                bundle.putInt("keyDiver", diverId);
                                bundle.putInt("keyMeet", meetId);
                                bundle.putInt("keySpin", diverSpinnerPosition);
                                Intent intents = new Intent(context, ChooseDivesFromList.class);
                                intents.putExtras(bundle);
                                startActivity(intents);
                                break;
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Scores entered will be 0. Please enter accurate score" +
                                                " or fail the dive using the menu button.",
                                        Toast.LENGTH_LONG).show();
                                break;
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Please set the 3rd score to 0.0",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Dive and Position is not valid, " +
                                        "Please Choose a Valid Combination.",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "This can only be used on meets with 3 judges.",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetScoreNames extends AsyncTask<List<String>, Object, Object> {
        ScoresDatabase db = new ScoresDatabase(getApplicationContext());
        List<String> score;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return score = db.getScores();
        }
    }

    private class CheckMultiplier extends AsyncTask<Double, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        double multi;

        @Override
        protected Double doInBackground(Double... params) {
            return multi = db.getMultiplier(meetId, diverId, diveNumber);
        }
    }

    private class GetTotalScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double Total;

        @Override
        protected Double doInBackground(Double... params) {
            return Total = db.getTotalScore(meetId, diverId);
        }
    }

    private class UpdateJudgeScores extends AsyncTask<Object, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.updateJudgeScoreFailed(meetId, diverId, diveNumber, "P", diveTotal, sc1, sc2,
                    sc3, sc4, sc5, sc6, sc7);
            return null;
        }
    }

    private class GetJudgeTotal extends AsyncTask<Integer, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        int num;

        @Override
        protected Integer doInBackground(Integer... params) {
            return num = db.getJudges(meetId);
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

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        DiveNumberDatabase db =  new DiveNumberDatabase(getApplicationContext());
        int number;

        @Override
        protected Integer doInBackground(Integer... params) {
            return number = db.getDiveNumber(meetId, diverId);
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

    private class GetDiveScore extends AsyncTask<Double, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        double theOldtotal;

        @Override
        protected Double doInBackground(Double... params){
            return theOldtotal = db.getDiveScore(meetId, diverId, diveNumber);
        }
    }
}
