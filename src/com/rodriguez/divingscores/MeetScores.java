package com.rodriguez.divingscores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;
import info.sqlite.model.ResultsDB;

public class MeetScores extends ActionBarActivity {
	
	private TextView meetName, schoolName, schoolCity, schoolState, meetDate, name, age,
                        grade, school, total, Type, score1, score2, score3, score4, score5,
                        score6, score7, score8, score9, score10, score11, s1, s2, s3, s4,
                        s5, s6, s7, s8, s9, s10, s11;
    private int diverId, meetId, diveNumberFromDB, diveNumber = 0;
    private String score1String, score2String, score3String, score4String, score5String, score6String,
                        score7String, score8String, score9String, score10String, score11String, totalString;
    private String meetNameString, meetDateString, nameString, schoolString;
    private Boolean failed;
    private double boardType = 0.0;
    Bitmap myBitmap;
    final Context context = this;
    public boolean firstAlertMeetScores;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meet_scores);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
        loadSavedPreferences();
        if(!firstAlertMeetScores){
            showDialog();
        }
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertMeetScores = sp.getBoolean("firstAlertMeetScores",false);
    }

    private void savePreferences(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void getDiveNumber(){
        GetDiveNumber num = new GetDiveNumber();
        diveNumberFromDB = num.doInBackground();
    }

    private void showDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_see_dive_info);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertMeetScores", true);
                dialog.cancel();
            }
        });

        dialog.show();
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
                startActivity(intent);
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
		ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
		diverInfo = info.doInBackground();

        nameString = diverInfo.get(0);
        String ageString = diverInfo.get(1);
        String gradeString = diverInfo.get(2);
        schoolString = diverInfo.get(3);
		
		name.setText(nameString);		
		age.setText(ageString);		
		grade.setText(gradeString);		
		school.setText(schoolString);			
	}
	
	public void fillMeet() {
		ArrayList<String> meet;
        GetMeetInfo info = new GetMeetInfo();
		meet = info.doInBackground();

        meetNameString = meet.get(0);
        String schoolNameString = meet.get(1);
        String schoolCityString = meet.get(2);
        String schoolStateString = meet.get(3);
        meetDateString = meet.get(4);
		
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
		ArrayList<Double> scores;
        GetResultList rList = new GetResultList();
		scores = rList.doInBackground();
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

        totalString = Double.toString(totalScore2);

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
        FillBoardType fill = new FillBoardType();
        boardType = fill.doInBackground();
        String typeString = boardType + " Meters";
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
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_email:
                emailFile();
                break;
            case R.id.action_share:
                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                myBitmap = v1.getDrawingCache();
                saveBitmap(myBitmap);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void emailFile(){

        String columnString =   "\"Diver\",\"School\",\"MeetName\",\"Date\",\"TotalScore\"," +
                                "\"Dive1\",\"Dive2\",\"Dive3\",\"Dive4\",\"Dive5\",\"Dive6\"," +
                                "\"Dive7\",\"Dive8\",\"Dive9\",\"Dive10\",\"Dive11\",";
        String dataString   =   "\"" + nameString + "\",\"" + schoolString + "\",\"" + meetNameString + "\",\"" + meetDateString + "\",\"" + totalString
                                 + "\",\"" + score1String + "\",\"" + score2String + "\",\"" + score3String + "\",\"" + score4String
                                 + "\",\"" + score5String + "\",\"" + score6String + "\",\"" + score7String + "\",\"" + score8String
                                 + "\",\"" + score9String + "\",\"" + score10String + "\",\"" + score11String + "\"";

        String combinedString = columnString + "\n" + dataString;

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
            dir.mkdirs();
            file   =   new File(dir, nameString + " Scores.csv");
            FileOutputStream out   =   null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.write(combinedString.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    Uri u1;
    u1 = Uri.fromFile(file);

    Intent sendIntent = new Intent(Intent.ACTION_SEND);
    sendIntent.setData(Uri.parse("mailto:"));
    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Diver Results");
    sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
    sendIntent.setType("text/html");
    startActivity(Intent.createChooser(sendIntent, "Save Results"));
    }

    // create a screen shot to share on Facebook
    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "ScoreMyDive/screenshot.png";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            shareStatus(filePath);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void shareStatus(String path) {
        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        String st = buildShareText();
        sendIntent.setType("*/*");

        sendIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);

        //test
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Dive Scores");
        sendIntent.putExtra(Intent.EXTRA_TEXT, st);

        startActivity(Intent.createChooser(sendIntent, "Share your Scores!"));
    }

    public String buildShareText(){
        String boards, dates = null;
        if (boardType == 1.0 || boardType == 3.0){
            int b = (int)boardType;
            boards = b + " Meter Springboard";
        } else {
            int b = (int)boardType;
            boards = b + " Meter Platform";
        }

        // formats the date
        SimpleDateFormat indate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat outdate = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        try{
            Date DateString = indate.parse(meetDateString);
            dates = outdate.format(DateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return nameString + " scored " +  totalString + " points"
                + " on the " +  boards + " at the " + meetNameString
                + " on " + dates + "." + "\n"
                + "Sent from ScoreIt.";
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object> {
        DiveNumberDatabase db =  new DiveNumberDatabase(getApplicationContext());
        int number;

        @Override
        protected Integer doInBackground(Integer... params) {
            return number = db.getDiveNumber(meetId, diverId);
        }
    }

    private class GetDiverInfo extends AsyncTask<ArrayList<String>, Object, Object> {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return diverinfo = db.getDiverInfo(diverId);
        }
    }

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getScores(meetId, diverId);
        }
    }

    private class GetResultList extends AsyncTask<ArrayList<Double>, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        ArrayList<Double> result;

        @SafeVarargs
        @Override
        protected final ArrayList<Double> doInBackground(ArrayList<Double>... params) {
            return result = db.getResultsList(meetId, diverId);
        }
    }

    private class FillBoardType extends AsyncTask<Double, Object, Object>{
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        Double type;

        @Override
        protected Double doInBackground(Double... params) {
            return type = db.getType(meetId, diverId);
        }
    }
}
