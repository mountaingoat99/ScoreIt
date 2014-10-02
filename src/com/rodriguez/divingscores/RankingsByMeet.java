package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import info.Helpers.PrintedResults;
import info.Helpers.RankingResults;
import info.controls.MyCustomBaseAdapter;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class RankingsByMeet extends Activity {

    private TextView name;
    private ListView myList;
    private int meetId, diverId, diveNumber;
    private double Board;
    private String meetName = "";
    Bitmap myBitmap;

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
        Board = b.getDouble("keyBoard");

        populateListViewFromDB();
        getMeetName();

        Toast.makeText(getApplicationContext(),
                "Click a diver to see their scores",
                Toast.LENGTH_LONG).show();
    }

    private void populateListViewFromDB() {
        ArrayList<RankingResults> searchResults;
        GetRankings rank = new GetRankings();
        searchResults = rank.doInBackground();

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
        GetDiveNumber num = new GetDiveNumber();
        diveNumber = num.doInBackground();
    }

    private void getMeetName(){
        GetMeetName mName = new GetMeetName();
        meetName = mName.doInBackground();
        name.setText(meetName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rankings_by_meet, menu);
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
        String diver, school, meetname = "", date, judges, divecount, divetype, totalscore, dive1,
                dive2, dive3, dive4, dive5, dive6, dive7, dive8, dive9, dive10,
                dive11, divenumber, divestyle, diveposition, dd, failed, score1, score2, score3, score4,
                score5, score6, score7, combinedString;
        StringBuilder r = new StringBuilder();

        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<PrintedResults> results = db.getPrintedResults(meetId, Board);

        String columnString = "\"Diver\",\"School\",\"MeetName\",\"Date\",\"Judges\"," +
                "\"DiveCount\",\"DiveType\",\"TotalScore\",\"Dive1\",\"Dive2\",\"Dive3\"," +
                "\"Dive4\",\"Dive5\",\"Dive6\",\"Dive7\",\"Dive8\",\"Dive9\",\"Dive10\"," +
                "\"Dive11\",\"DiveNumber\",\"DiveStyle\",\"DivePosition\",\"DD\",\"Failed\",\"Score1\"," +
                "\"Score2\",\"Score3\",\"Score4\",\"Score5\",\"Score6\",\"Score7\",";

        for (PrintedResults result : results) {
            diver = result.getName();
            school = result.getSchool();
            meetname = result.getMeetName();
            date = result.getDate();
            judges = result.getJudges();
            divecount = result.getDiveCount();
            divetype = result.getDiveType();
            totalscore = result.getTotalScore();
            dive1 = result.getDive1();
            dive2 = result.getDive2();
            dive3 = result.getDive3();
            dive4 = result.getDive4();
            dive5 = result.getDive5();
            dive6 = result.getDive6();
            dive7 = result.getDive7();
            dive8 = result.getDive8();
            dive9 = result.getDive9();
            dive10 = result.getDive10();
            dive11 = result.getDive11();
            divenumber = result.getDiveNumber();
            divestyle = result.getDiveStyle();
            diveposition = result.getDivePostion();
            dd = result.getDd();
            failed = result.getFailed();
            score1 = result.getScore1();
            score2 = result.getScore2();
            score3 = result.getScore3();
            score4 = result.getScore4();
            score5 = result.getScore5();
            score6 = result.getScore6();
            score7 = result.getScore7();

            String dataString = "\"" + diver + "\",\"" + school + "\",\"" + meetname + "\",\"" + date + "\",\"" + judges
                    + "\",\"" + divecount + "\",\"" + divetype + "\",\"" + totalscore + "\",\"" + dive1
                    + "\",\"" + dive2 + "\",\"" + dive3 + "\",\"" + dive4 + "\",\"" + dive5
                    + "\",\"" + dive6 + "\",\"" + dive7 + "\",\"" + dive8 + "\",\"" + dive9
                    + "\",\"" + dive10 + "\",\"" + dive11 + "\",\"" + divenumber + "\",\"" + divestyle
                    + "\",\"" + diveposition + "\",\"" + dd +  "\",\"" + failed + "\",\"" + score1 + "\",\"" + score2
                    + "\",\"" + score3 + "\",\"" + score4 + "\",\"" + score5 + "\",\"" + score6
                    + "\",\"" + score7 + "\"";



            r.append("\n").append(dataString); // + "\n" + dataString;
        }
        combinedString = columnString + "\n" + r;

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
            dir.mkdirs();
            file   =   new File(dir, meetname + " Results.csv");
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
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Meet Results");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(Intent.createChooser(sendIntent, "Save Results"));
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
        TextView Name = (TextView)findViewById(R.id.name);
        String DiverName = Name.getText().toString();

        return DiverName + " has the top score at the "
                + meetName + "."
                + "\n" + "Sent from ScoreIt.";
    }

    private class GetRankings extends AsyncTask<ArrayList<RankingResults>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<RankingResults> results;

        @SafeVarargs
        @Override
        protected final ArrayList<RankingResults> doInBackground(ArrayList<RankingResults>... params) {
            return db.getRankings(meetId, Board);
        }
    }

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        int num;

        @Override
        protected Integer doInBackground(Integer... params) {
            return num = db.getDiveNumber(meetId, diverId);
        }
    }

    private class GetMeetName extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        String name;

        @Override
        protected String doInBackground(String... params) {
            return name = db.getMeetName(meetId);
        }
    }
}
