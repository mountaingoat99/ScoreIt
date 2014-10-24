package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import info.Helpers.DiverMeetResults;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;

public class ViewDiveInfo extends Activity {

    private TextView diveType;
    private TextView diveStyle;
    private TextView divePosition;
    private TextView diveNumberView;
    private TextView diveDD;
    private TextView name;
    private TextView meetName;
    private TextView s4v;
    private TextView s5v;
    private TextView s6v;
    private TextView s7v;
    private TextView s1;
    private TextView s2;
    private TextView s3;
    private TextView s4;
    private TextView s5;
    private TextView s6;
    private TextView s7;
    private TextView total;
    private TextView failedText;
    private Button returnButton;
    private int diverId, meetId, diveNumber, judgeTotal;
    private String s1String = "0.00";
    private String s2String = "0.00";
    private String s3String = "0.00";
    private String s4String = "0.00";
    private String s5String = "0.00";
    private String s6String = "0.00";
    private String s7String = "0.00";
    private String nameString = "";
    private String totalScore = "";
    private String diveStyleString = "";
    private String meetNameString = "";
    private String diveDDString = "";
    private String failedString = "";
    private String diveTypeString = "";
    private String divePositionString = "";
    Bitmap myBitmap;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dive_info);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diveNumber = b.getInt("keyDiveNumber");
        getJudgeTotal();
        showDiveNumber();
        fillText();
        showDiveScores();
        addListenerOnButton();
    }

    private void showDiveScores(){
        GetDiveScore score = new GetDiveScore();
        totalScore = score.doInBackground();
        total.setText(totalScore);

        CheckFailedDive fail = new CheckFailedDive();
        Boolean failed = fail.doInBackground();
        if(failed){
            failedString = "F";
        } else {
            failedString = "P";
        }
        failedText.setText(failedString);

        GetScoreList scorelist = new GetScoreList();
        ArrayList<Double> scores = scorelist.doInBackground();
        s1String = Double.toString(scores.get(0));
        s2String = Double.toString(scores.get(1));
        s3String = Double.toString(scores.get(2));
        s4String = Double.toString(scores.get(3));
        s5String = Double.toString(scores.get(4));
        s6String = Double.toString(scores.get(5));
        s7String = Double.toString(scores.get(6));


            if (judgeTotal >= 3) {
                s1.setText(s1String);
                s2.setText(s2String);
                s3.setText(s3String);
            }
            if (judgeTotal >= 5) {
                s4v.setVisibility(View.VISIBLE);
                s4.setVisibility(View.VISIBLE);
                s4.setText(s4String);

                s5v.setVisibility(View.VISIBLE);
                s5.setVisibility(View.VISIBLE);
                s5.setText(s5String);
            }
            if (judgeTotal == 7) {
                s6v.setVisibility(View.VISIBLE);
                s6.setVisibility(View.VISIBLE);
                s6.setText(s6String);

                s7v.setVisibility(View.VISIBLE);
                s7.setVisibility(View.VISIBLE);
                s7.setText(s7String);
            }

    }

    private void fillText(){
        ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
        diverInfo = info.doInBackground();

        nameString = diverInfo.get(0);
        name.setText(nameString);

        ArrayList<String> meetInfo;
        GetMeetInfo mInfo = new GetMeetInfo();
        meetInfo = mInfo.doInBackground();

        meetNameString = meetInfo.get(0);
        meetName.setText(meetNameString);

        ArrayList<String> diveInfo;
        GetCatAndName catName = new GetCatAndName();
        diveInfo = catName.doInBackground();
        diveTypeString = diveInfo.get(0);
        diveStyleString = diveInfo.get(1);
        divePositionString = diveInfo.get(2);
        diveDDString = diveInfo.get(3);


        diveType.setText(diveTypeString);
        diveStyle.setText(diveStyleString);
        divePosition.setText(divePositionString);
        diveDD.setText(diveDDString);
    }

    public void addListenerOnButton(){

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getJudgeTotal(){
        GetJudgeTotal total = new GetJudgeTotal();
        judgeTotal = total.doInBackground();
    }

    private void showDiveNumber(){
        diveNumberView.setText("Dive Number " + diveNumber);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_dive_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        String diver, meetname = "", divenumber, divename, position, dd,
                total, passfailed, judges, score1, score2, score3,
                score4, score5, score6, score7, combinedString;

        StringBuilder r = new StringBuilder();

        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<DiverMeetResults> results = db.getDiverMeetResults(meetId, diverId);

        String columnString =   "\"Diver\",\"Meet Name\",\"Dive Number\",\"Dive Name\",\"Position\",\"DD\"," +
                "\"Total\",\"Pass/Failed\",\"Judges\",\"Score 1\",\"Score 2\",\"Score 3\"," +
                "\"Score 4\",\"Score 5\",\"Score 6\",\"Score 7\",";

        for (DiverMeetResults result : results) {
            diver = result.getName();
            meetname = result.getMeetName();
            divenumber = result.getDiveNumber();
            divename = result.getDiveName();
            position = result.getPosition();
            dd = result.getDd();
            total = result.getTotal();
            passfailed = result.getPassFailed();
            judges = result.getJudges();
            score1 = result.getScore1();
            score2 = result.getScore2();
            score3 = result.getScore3();
            score4 = result.getScore4();
            score5 = result.getScore5();
            score6 = result.getScore6();
            score7 = result.getScore7();

            String dataString = "\"" + diver + "\",\"" + meetname+ "\",\"" + divenumber + "\",\"" + divename + "\",\"" + position + "\",\"" + dd
                    + "\",\"" + total + "\",\"" + passfailed + "\",\"" + judges + "\",\"" + score1
                    + "\",\"" + score2 + "\",\"" + score3+ "\",\"" + score4 + "\",\"" + score5
                    + "\",\"" + score6 + "\",\"" + score7 + "\"";

            r.append("\n").append(dataString);

        }
        combinedString = columnString + "\n" + r;

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
            dir.mkdirs();
            file   =   new File(dir, nameString + " " + meetname + " Scores.csv");
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
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,  nameString + " " + meetname + " Results" );
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(Intent.createChooser(sendIntent, "Save Dive Results"));
    }

    // create a screen shot to share on Facebook
    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "DCIM/Camera/screenshot.png";
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

        String dive =  diveStyleString.substring(diveStyleString.lastIndexOf("-") + 1);

        return "On dive " + diveNumber + " -" + dive + ", "
                + nameString + " scored "
                + totalScore + " at the  " + meetNameString + "." + "\n"
                + "Sent from ScoreIt.";
    }

    private void setUpView(){
        diveNumberView = (TextView)findViewById(R.id.TextView);
        name = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        diveType = (TextView) findViewById(R.id.diveTypeValue);
        diveStyle = (TextView) findViewById(R.id.diveNameValue);
        divePosition = (TextView) findViewById(R.id.divePositionValue);
        diveDD = (TextView) findViewById(R.id.ddValue);
        s1 = (TextView)findViewById(R.id.score1);
        s2 = (TextView)findViewById(R.id.score2);
        s3 = (TextView)findViewById(R.id.score3);
        s4 = (TextView)findViewById(R.id.score4);
        s5 = (TextView)findViewById(R.id.score5);
        s6 = (TextView)findViewById(R.id.score6);
        s7 = (TextView)findViewById(R.id.score7);
        s4v = (TextView)findViewById(R.id.score4view);
        s5v = (TextView)findViewById(R.id.score5view);
        s6v = (TextView)findViewById(R.id.score6view);
        s7v = (TextView)findViewById(R.id.score7view);
        total = (TextView)findViewById(R.id.scoreTotal);
        failedText = (TextView)findViewById(R.id.failedText);
        returnButton = (Button)findViewById(R.id.buttonReturn);
    }

    private class GetDiveScore extends AsyncTask<String, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        String result;

        @Override
        protected String doInBackground(String... params) {
            return result = Double.toString(db.getDiveScore(meetId, diverId, diveNumber));
        }
    }

    private class CheckFailedDive extends AsyncTask<Boolean, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        boolean fail;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return fail = db.checkFailed(meetId, diverId, diveNumber);
        }
    }

    private class GetScoreList extends AsyncTask<ArrayList<Double>, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<Double> scores;

        @SafeVarargs
        @Override
        protected final ArrayList<Double> doInBackground(ArrayList<Double>... params) {
            return scores = db.getScoresList(meetId, diverId, diveNumber);
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

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return meetinfo = db.getMeetInfo(meetId);
        }
    }

    private class GetCatAndName extends AsyncTask<ArrayList<String>, Object, Object>{
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<String> scores;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return db.getCatAndName(meetId, diverId, diveNumber);
        }
    }

    private class GetJudgeTotal extends AsyncTask<Integer, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        int total;

        @Override
        protected Integer doInBackground(Integer... params) {
            return total = db.getJudges(meetId);
        }
    }

}
