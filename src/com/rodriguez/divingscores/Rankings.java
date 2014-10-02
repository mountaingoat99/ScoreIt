package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rodriguez.divingscores.R;

import java.util.ArrayList;

import info.controls.RankingMeetBaseAdapter;
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
        ArrayList<RankingsMeet> meetInfo;
        PopListView name = new PopListView();
        meetInfo = name.doInBackground();

        myList.setAdapter(new RankingMeetBaseAdapter(this, meetInfo));
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView meetName = (TextView) view.findViewById(R.id.meetName);
                TextView board = (TextView) view.findViewById(R.id.board);
                String stringId = meetName.getText().toString();
                Double Board = Double.parseDouble(board.getText().toString());
                MeetDatabase db = new MeetDatabase(getApplicationContext());
                meetId = db.getId(stringId);
                DiverDatabase ddb = new DiverDatabase(getApplicationContext());
                Boolean noScores = ddb.checkDiverForRankings(meetId, Board);
                if(noScores) {
                    Intent intent = new Intent(getBaseContext(), RankingsByMeet.class);
                    //TODO send the board to the RankByMeet
                    Bundle b = new Bundle();
                    b.putInt("keyMeet", meetId);
                    b.putDouble("keyBoard", Board);
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class PopListView extends AsyncTask<ArrayList<RankingsMeet>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<RankingsMeet> info;

        @SafeVarargs
        @Override
        protected final ArrayList<RankingsMeet> doInBackground(ArrayList<RankingsMeet>... params) {
            return info = db.getNameForMeetRank();
        }
    }
}
