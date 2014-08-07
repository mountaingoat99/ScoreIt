package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rodriguez.divingscores.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;

public class ViewDiveInfo extends Activity {

    private TextView diveType;
    private TextView diveStyle;
    private TextView divePosition;
    private TextView diveNumberView;
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
    String s1String = "0.00";
    String s2String = "0.00";
    String s3String = "0.00";
    String s4String = "0.00";
    String s5String = "0.00";
    String s6String = "0.00";
    String s7String = "0.00";
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
        ResultDatabase rdb = new ResultDatabase(getApplicationContext());
        String totalScore = Double.toString(rdb.getDiveScore(meetId, diverId, diveNumber));
        total.setText(totalScore);

        JudgeScoreDatabase jdb = new JudgeScoreDatabase(getApplicationContext());
        Boolean failed = jdb.checkFailed(meetId, diverId, diveNumber);
        String failedString;
        if(failed){
            failedString = "F";
        } else {
            failedString = "P";
        }
        failedText.setText(failedString);


        ArrayList<Double> scores = jdb.getScoresList(meetId, diverId, diveNumber);
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
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverInfo;
        diverInfo = db.getDiverInfo(diverId);

        String nameString = diverInfo.get(0);
        name.setText(nameString);

        MeetDatabase mdb = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetInfo;
        meetInfo = mdb.getMeetInfo(meetId);

        String meetNameString = meetInfo.get(0);
        meetName.setText(meetNameString);

        JudgeScoreDatabase jdb = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<String> diveInfo;
        diveInfo = jdb.getCatAndName(meetId, diverId, diveNumber);
        String diveTypeString = diveInfo.get(0);     //TODO breaks here
        String diveStyleString = diveInfo.get(1);
        String divePositionString = diveInfo.get(2);
        diveType.setText(diveTypeString);
        diveStyle.setText(diveStyleString);
        divePosition.setText(divePositionString);
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
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        judgeTotal = db.getJudges(meetId);
    }

    private void showDiveNumber(){
        diveNumberView.setText("Dive Number " + diveNumber);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.view_dive_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpView(){
        diveNumberView = (TextView)findViewById(R.id.TextView);
        name = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        diveType = (TextView) findViewById(R.id.diveTypeValue);
        diveStyle = (TextView) findViewById(R.id.diveNameValue);
        divePosition = (TextView) findViewById(R.id.divePositionValue);
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
}
