package com.rodriguez.divingscores;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;
import info.sqlite.model.ResultsDB;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MeetScores extends Activity {
	
	private TextView meetName, schoolName, schoolCity, schoolState, meetDate, name, age,
                        grade, school, total, Type, score1, score2, score3, score4, score5,
                        score6, score7, score8, score9, score10, score11, s1, s2, s3, s4,
                        s5, s6, s7, s8, s9, s10, s11;
    private int diverId, meetId, diveCount, diveNumberFromDB, diveNumber = 0;
    String score1String, score2String, score3String, score4String, score5String, score6String,
                        score7String, score8String, score9String, score10String, score11String;
    Boolean failed, dialogShown = true;
    final Context context = this;
	
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
        if (savedInstanceState != null){
            dialogShown = savedInstanceState.getBoolean("keyBool");
        }

        setupView();
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");
        meetId = b.getInt("key2");

        //getDiveCount();
        getDiveNumber();
        fillText();
        fillMeet();
        fillScores();
        fillType();
        setUpLongPress();
        if(dialogShown){
            showDialog();
        }

    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumberFromDB = db.getDiveNumber(meetId, diverId);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        savedState.putBoolean("keyBool", dialogShown);

    }

    private void showDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_see_dive_info);

        dialog.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                t.cancel();
            }
        }, 2000);
        dialogShown = false;
    }

    private void setUpLongPress(){

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 1;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 1;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 2;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 2;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 3;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 3;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 4;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 4;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 5;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 5;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 6;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 6;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 7;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 7;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 8;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 8;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 9;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 9;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 10;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 10;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 11;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        score11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diveNumber = 11;
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keyDiveNumber", diveNumber);
                Intent intent = new Intent(context, ViewDiveInfo.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
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
        String failDive = "F";

        int numberOfDive = 1;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score1String = failDive;
        } else {
            score1String = Double.toString(scores.get(0));
        }
        numberOfDive = 2;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score2String = failDive;
        } else {
            score2String = Double.toString(scores.get(1));
        }
        numberOfDive = 3;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score3String = failDive;
        } else {
            score3String = Double.toString(scores.get(2));
        }
        numberOfDive = 4;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score4String = failDive;
        } else {
            score4String = Double.toString(scores.get(3));
        }
        numberOfDive = 5;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score5String = failDive;
        } else {
            score5String = Double.toString(scores.get(4));
        }
        numberOfDive = 6;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score6String = failDive;
        } else {
            score6String = Double.toString(scores.get(5));
        }
        numberOfDive = 7;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score7String = failDive;
        } else {
            score7String = Double.toString(scores.get(6));
        }
        numberOfDive = 8;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score8String = failDive;
        } else {
            score8String = Double.toString(scores.get(7));
        }
        numberOfDive = 9;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score9String = failDive;
        } else {
            score9String = Double.toString(scores.get(8));
        }
        numberOfDive = 10;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score10String = failDive;
        } else {
            score10String = Double.toString(scores.get(9));
        }
        numberOfDive = 11;
        failed = checkFailedDive(numberOfDive);
        if(failed){
            score11String = failDive;
        } else {
            score11String = Double.toString(scores.get(10));
        }

        ResultsDB result = new ResultsDB();
        Double totalScore = (result.calcScoreTotal(scores.get(0), scores.get(1), scores.get(2),
                scores.get(3), scores.get(4), scores.get(5), scores.get(6),
                scores.get(7), scores.get(8), scores.get(9), scores.get(10)));
        DecimalFormat d = new DecimalFormat("0.00");
        Double totalScore2 = Double.parseDouble(d.format(totalScore));

        String totalString = Double.toString(totalScore2);

        if(diveNumberFromDB >= 1) {
            total.setText(totalString);
            s1.setVisibility(View.VISIBLE);
            score1.setVisibility(View.VISIBLE);
            score1.setText(score1String);
        } else {
            Toast.makeText(getApplicationContext(),
                    "This diver has no scores in this meet yet.",
                    Toast.LENGTH_LONG).show();
        }
        if(diveNumberFromDB >= 2) {
            s2.setVisibility(View.VISIBLE);
            score2.setVisibility(View.VISIBLE);
            score2.setText(score2String);
        }
        if(diveNumberFromDB >= 3) {
            s3.setVisibility(View.VISIBLE);
            score3.setVisibility(View.VISIBLE);
            score3.setText(score3String);
        }
        if(diveNumberFromDB >= 4) {
            s4.setVisibility(View.VISIBLE);
            score4.setVisibility(View.VISIBLE);
            score4.setText(score4String);
        }
        if(diveNumberFromDB >= 5) {
            s5.setVisibility(View.VISIBLE);
            score5.setVisibility(View.VISIBLE);
            score5.setText(score5String);
        }
        if(diveNumberFromDB >= 6) {
            s6.setVisibility(View.VISIBLE);
            score6.setVisibility(View.VISIBLE);
            score6.setText(score6String);
        }
        if(diveNumberFromDB >= 7) {
            s7.setVisibility(View.VISIBLE);
            score7.setVisibility(View.VISIBLE);
            score7.setText(score7String);
        }
        if(diveNumberFromDB >= 8) {
            s8.setVisibility(View.VISIBLE);
            score8.setVisibility(View.VISIBLE);
            score8.setText(score8String);
        }
        if(diveNumberFromDB >= 9) {
            s9.setVisibility(View.VISIBLE);
            score9.setVisibility(View.VISIBLE);
            score9.setText(score9String);
        }
        if(diveNumberFromDB >= 10) {
            s10.setVisibility(View.VISIBLE);
            score10.setVisibility(View.VISIBLE);
            score10.setText(score10String);
        }
        if(diveNumberFromDB == 11) {
            s11.setVisibility(View.VISIBLE);
            score11.setVisibility(View.VISIBLE);
            score11.setText(score11String);
        }
    }

    public boolean checkFailedDive(int numberDive){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        return failed = db.checkFailed(meetId, diverId, numberDive);
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
        //getMenuInflater().inflate(R.menu.activity_meet_scores, menu);
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
            case R.id.menu_rankings:
                Intent intent2 = new Intent(context, Rankings.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
