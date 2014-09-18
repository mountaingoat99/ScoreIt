package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemSelectedListener;



import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.JudgeScoreDatabase;

public class FailedDive extends Activity implements OnItemSelectedListener {

    private Button failButton;
    private Button returnButton;
    private int diverId, meetId, diveNumber, diveType = 0, divePosition = 0;
    private double boardType = 0.0;
    private String diveTypeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_failed_dive);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        failButton = (Button)findViewById(R.id.buttonFailDive);
        returnButton = (Button)findViewById(R.id.buttonFailReturn);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            meetId = b.getInt("keyMeet");
            diverId = b.getInt("keyDiver");
            diveType = b.getInt("keyDiveType");
            diveTypeName = b.getString("keyDiveTypeName");
            divePosition = b.getInt("keyDivePosition");
            boardType = b.getDouble("boardType");
        }

        getDiveNumber();
        addListenerOnButton();
    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber = db.getDiveNumber(meetId, diverId);
    }

    public void addListenerOnButton(){
        final Context context = this;

        failButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementDiveNumber();
                updateJudgeScore();
                Intent intent = new Intent(context, ChooseSummary.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("diveType", diveType);
                b.putDouble("boardType", boardType);
                Intent intent = new Intent(context, Dives.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void incrementDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber ++;
        db.updateDiveNumber(meetId, diverId, diveNumber);
    }

    public void updateJudgeScore(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        String failedDive = "F";
        double total = 0.0, sc1 = 0.0, sc2 = 0.0, sc3 = 0.0, sc4 = 0.0, sc5 = 0.0, sc6 = 0.0, sc7 = 0.0;
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

        db.fillNewJudgeScores(meetId, diverId, diveNumber, diveCategory, diveTypeName, DivePosition,
                failedDive, total, sc1, sc2, sc3, sc4, sc5, sc6, sc7, 0.0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.failed_dive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
