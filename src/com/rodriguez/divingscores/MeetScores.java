package com.rodriguez.divingscores;

import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;
import info.sqlite.model.ResultsDB;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MeetScores extends Activity {
	
	private TextView meetName, schoolName, schoolCity, schoolState, meetDate, name, age,
                        grade, school, total, Type, score1, score2, score3, score4, score5,
                        score6, score7, score8, score9, score10, score11, s1, s2, s3, s4,
                        s5, s6, s7, s8, s9, s10, s11;
    private int diverId, meetId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_scores);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setupView();
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");
        meetId = b.getInt("key2");

        fillText();
        fillMeet();
        fillScores();
        fillType();
    }

    public void fillText(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		ArrayList<String> diverInfo;
		diverInfo = db.getDiverInfo(diverId);

        String nameString = diverInfo.get(0);
        String ageString = diverInfo.get(1);
        String gradeString = diverInfo.get(2);
        String schoolString = diverInfo.get(3);
		
		name.setText(nameString);		
		age.setText(ageString);		
		grade.setText(gradeString);		
		school.setText(schoolString);			
	}
	
	public void fillMeet() {
		ResultDatabase db = new ResultDatabase(getApplicationContext());
		ArrayList<String> meet;
		meet = db.getScores(meetId, diverId);

        String meetNameString = meet.get(0);
        String schoolNameString = meet.get(1);
        String schoolCityString = meet.get(2);
        String schoolStateString = meet.get(3);
        String meetDateString = meet.get(4);
		
		// formats the date
		SimpleDateFormat indate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		SimpleDateFormat outdate = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
		try{
			Date DateString = indate.parse(meetDateString);
			meetDate.setText(outdate.format(DateString));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}		
		
		//formats the output
		schoolNameString = schoolNameString + "  ";
		schoolCityString = schoolCityString + ", ";
		
		meetName.setText(meetNameString);
		schoolName.setText(schoolNameString);
		schoolCity.setText(schoolCityString);
		schoolState.setText(schoolStateString);	
	}
	
	public void fillScores(){		
		ResultDatabase db = new ResultDatabase(getApplicationContext());
		ArrayList<Double> scores;
		scores = db.getResultsList(meetId, diverId);

        String score1String = Double.toString(scores.get(0));
        String score2String = Double.toString(scores.get(1));
        String score3String = Double.toString(scores.get(2));
        String score4String = Double.toString(scores.get(3));
        String score5String = Double.toString(scores.get(4));
        String score6String = Double.toString(scores.get(5));
        String score7String = Double.toString(scores.get(6));
        String score8String = Double.toString(scores.get(7));
        String score9String = Double.toString(scores.get(8));
        String score10String = Double.toString(scores.get(9));
        String score11String = Double.toString(scores.get(10));
        //String totalString = Double.toString(scores.get(11));

        ResultsDB result = new ResultsDB();
        Double totalScore = (result.calcScoreTotal(scores.get(0), scores.get(1), scores.get(2),
                scores.get(3), scores.get(4), scores.get(5), scores.get(6),
                scores.get(7), scores.get(8), scores.get(9), scores.get(10)));
        DecimalFormat d = new DecimalFormat("0.00");
        Double totalScore2 = Double.parseDouble(d.format(totalScore));

        String totalString = Double.toString(totalScore2);

        if(!totalString.equals("0.0")) {
            total.setText(totalString);
            s1.setVisibility(View.VISIBLE);
            score1.setVisibility(View.VISIBLE);
            score1.setText(score1String);
            s2.setVisibility(View.VISIBLE);
            score2.setVisibility(View.VISIBLE);
            score2.setText(score2String);
            s3.setVisibility(View.VISIBLE);
            score3.setVisibility(View.VISIBLE);
            score3.setText(score3String);
            s4.setVisibility(View.VISIBLE);
            score4.setVisibility(View.VISIBLE);
            score4.setText(score4String);
            s5.setVisibility(View.VISIBLE);
            score5.setVisibility(View.VISIBLE);
            score5.setText(score5String);
            s6.setVisibility(View.VISIBLE);
            score6.setVisibility(View.VISIBLE);
            score6.setText(score6String);
            if (score7String.equals("0.0")) {
                return;
            } else {
                s7.setVisibility(View.VISIBLE);
                score7.setVisibility(View.VISIBLE);
                score7.setText(score7String);
                s8.setVisibility(View.VISIBLE);
                score8.setVisibility(View.VISIBLE);
                score8.setText(score8String);
                s9.setVisibility(View.VISIBLE);
                score9.setVisibility(View.VISIBLE);
                score9.setText(score9String);
                s10.setVisibility(View.VISIBLE);
                score10.setVisibility(View.VISIBLE);
                score10.setText(score10String);
                s11.setVisibility(View.VISIBLE);
                score11.setVisibility(View.VISIBLE);
                score11.setText(score11String);
            }
        } else {
            total.setText(totalString);
            s1.setVisibility(View.INVISIBLE);
            score1.setVisibility(View.INVISIBLE);
            s2.setVisibility(View.INVISIBLE);
            score2.setVisibility(View.INVISIBLE);
            s3.setVisibility(View.INVISIBLE);
            score3.setVisibility(View.INVISIBLE);
            s4.setVisibility(View.INVISIBLE);
            score4.setVisibility(View.INVISIBLE);
            s5.setVisibility(View.INVISIBLE);
            score5.setVisibility(View.INVISIBLE);
            s6.setVisibility(View.INVISIBLE);
            score6.setVisibility(View.INVISIBLE);
            s7.setVisibility(View.INVISIBLE);
            score7.setVisibility(View.INVISIBLE);
            s8.setVisibility(View.INVISIBLE);
            score8.setVisibility(View.INVISIBLE);
            s9.setVisibility(View.INVISIBLE);
            score9.setVisibility(View.INVISIBLE);
            s10.setVisibility(View.INVISIBLE);
            score10.setVisibility(View.INVISIBLE);
            s11.setVisibility(View.INVISIBLE);
            score11.setVisibility(View.INVISIBLE);

            Toast.makeText(getApplicationContext(),
                    "This diver has no scores in this meet yet.",
                    Toast.LENGTH_LONG).show();
        }
	}

    private void fillType(){
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        int type = db.getType(meetId, diverId);
        String typeString = type + " Meters";
        Type.setText(typeString);
    }

    private void setupView() {
        name = (TextView)findViewById(R.id.nameResults);
        age = (TextView)findViewById(R.id.ageResults);
        grade = (TextView)findViewById(R.id.gradeResults);
        school = (TextView)findViewById(R.id.schoolResults);
        meetName = (TextView)findViewById(R.id.meetName);
        schoolName = (TextView)findViewById(R.id.meetSchool);
        schoolCity = (TextView)findViewById(R.id.MeetCity);
        schoolState = (TextView)findViewById(R.id.MeetState);
        meetDate = (TextView)findViewById(R.id.meetDate);
        total = (TextView)findViewById(R.id.theTotal);
        Type = (TextView)findViewById(R.id.theType);
        score1 = (TextView)findViewById(R.id.score1);
        score2 = (TextView)findViewById(R.id.score2);
        score3 = (TextView)findViewById(R.id.score3);
        score4 = (TextView)findViewById(R.id.score4);
        score5 = (TextView)findViewById(R.id.score5);
        score6 = (TextView)findViewById(R.id.score6);
        score7 = (TextView)findViewById(R.id.score7);
        score8 = (TextView)findViewById(R.id.score8);
        score9 = (TextView)findViewById(R.id.score9);
        score10 = (TextView)findViewById(R.id.score10);
        score11 = (TextView)findViewById(R.id.score11);
        s1 = (TextView)findViewById(R.id.score1view);
        s2 = (TextView)findViewById(R.id.score2view);
        s3 = (TextView)findViewById(R.id.score3view);
        s4 = (TextView)findViewById(R.id.score4view);
        s5 = (TextView)findViewById(R.id.score5view);
        s6 = (TextView)findViewById(R.id.score6view);
        s7 = (TextView)findViewById(R.id.score7view);
        s8 = (TextView)findViewById(R.id.score8view);
        s9 = (TextView)findViewById(R.id.score9view);
        s10 = (TextView)findViewById(R.id.score10view);
        s11 = (TextView)findViewById(R.id.score11view);
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_meet_scores, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                Intent intent3 = new Intent(context, HowTo.class);
                startActivity(intent3);
                break;
            case R.id.menu_about:
                Intent intent4 = new Intent(context, About.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
