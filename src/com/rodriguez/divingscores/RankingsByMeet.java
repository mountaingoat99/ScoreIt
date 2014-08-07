package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.controls.MyCustomBaseAdapter;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class RankingsByMeet extends Activity {

    private TextView name;
    private ListView myList;
    private int meetId, diverId, diveNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings_by_meet);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);
        name = (TextView)findViewById(R.id.MeetNameHeader);

        Bundle b = getIntent().getExtras();
        meetId = b.getInt("keyMeet");

        populateListViewFromDB();
        getMeetName();

        Toast.makeText(getApplicationContext(),
                "Click a diver to see their scores",
                Toast.LENGTH_LONG).show();
    }

    private void populateListViewFromDB() {
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<RankingResults> searchResults;
        searchResults = db.getRankings(meetId);

        myList.setAdapter(new MyCustomBaseAdapter(this, searchResults));

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView name = (TextView) view.findViewById(R.id.name);
                String diverName = name.getText().toString();
                DiverDatabase db = new DiverDatabase(getApplicationContext());
                diverId = db.getId(diverName);
                getDiveNumber();
                if(diveNumber == 0){
                    Toast.makeText(getApplicationContext(),
                            "Diver has no scores at this meet yet",
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), MeetScores.class);
                    Bundle b = new Bundle();
                    b.putInt("key", diverId);
                    b.putInt("key2", meetId);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }

    private void getDiveNumber(){
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        diveNumber = db.getDiveNumber(meetId, diverId);
    }

    private void getMeetName(){
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        String meetName = db.getMeetName(meetId);
        name.setText(meetName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.rankings_by_meet, menu);
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
}
