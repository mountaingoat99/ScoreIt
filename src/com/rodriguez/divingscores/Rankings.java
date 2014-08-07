package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rodriguez.divingscores.R;

import java.util.ArrayList;

import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class Rankings extends Activity {

    private ListView myList;
    private int meetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);

        populateListViewFromDB();

        Toast.makeText(getApplicationContext(),
                "Click a meet to see the rankings",
                Toast.LENGTH_LONG).show();
    }

    private void populateListViewFromDB() {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetInfo;
        meetInfo = db.getMeetNameForRank();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.list_item, meetInfo);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String stringId;
                MeetDatabase db = new MeetDatabase(getApplicationContext());
                stringId = myList.getItemAtPosition(position).toString();
                meetId = db.getId(stringId);
                DiverDatabase ddb = new DiverDatabase(getApplicationContext());
                Boolean noScores = ddb.checkDiverForRankings(meetId);
                if(noScores) {
                    Intent intent = new Intent(getBaseContext(), RankingsByMeet.class);
                    Bundle b = new Bundle();
                    b.putInt("keyMeet", meetId);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "There are no scores in this meet yet",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.rankings, menu);
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
