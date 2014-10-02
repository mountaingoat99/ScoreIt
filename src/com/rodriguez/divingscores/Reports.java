package com.rodriguez.divingscores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.Helpers.DiverMeetResults;
import info.Helpers.DiverScoreTotals;
import info.Helpers.PrintedResults;
import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;

public class Reports extends Activity implements OnItemSelectedListener {
    private Spinner spinnerName, spinnerMeet, spinnerReports;
    private Button btnSendReport;
    private int diverId = 0, meetId = 0, reportId = 0;
    private double boardType = 0.0;
    private String stringId;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();

        addListenerOnButton();
        loadSpinnerName();
        loadSpinnerMeet();
        loadSpinnerReports();
        spinnerName.setOnItemSelectedListener(this);
        spinnerMeet.setOnItemSelectedListener(this);
        spinnerReports.setOnItemSelectedListener(this);
    }

    private void addListenerOnButton() {
        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(meetId != 0 && reportId != 0) {
                    if (reportId == 1) {
                        printMeetResults();
                    } else {
                        if (diverId == 0 && (reportId == 2 || reportId == 3)) {
                            Toast.makeText(getApplicationContext(),
                                    "Please Choose a diver",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if (reportId == 2) {
                                printDiverScoreTotalsMeet();
                            }
                            if (reportId == 3) {
                                printJudgeScoreTotalsMeet();
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please choose both a meet and a report",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void printMeetResults(){
        String diver, school, meetname = "", date, judges, divecount, divetype, totalscore, dive1,
                dive2, dive3, dive4, dive5, dive6, dive7, dive8, dive9, dive10,
                dive11, divenumber, divestyle, diveposition, dd, failed, score1, score2, score3, score4,
                score5, score6, score7, combinedString;
        StringBuilder r = new StringBuilder();

        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<PrintedResults> results = db.getPrintedResults(meetId, boardType);

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

    private void printDiverScoreTotalsMeet(){
        String nameString = "", schoolString, meetNameString, meetDateString, totalString,
                score1String, score2String, score3String, score4String, score5String,
                score6String, score7String, score8String, score9String, score10String,
                score11String, combinedString;

        StringBuilder r = new StringBuilder();
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<DiverScoreTotals> results = db.getDiverMeetScoreTotals(meetId, diverId);

        String columnString =   "\"Diver\",\"School\",\"MeetName\",\"Date\",\"TotalScore\"," +
                "\"Dive1\",\"Dive2\",\"Dive3\",\"Dive4\",\"Dive5\",\"Dive6\"," +
                "\"Dive7\",\"Dive8\",\"Dive9\",\"Dive10\",\"Dive11\",";

        for (DiverScoreTotals result : results){
            nameString = result.getName();
            schoolString = result.getSchool();
            meetNameString = result.getMeetName();
            meetDateString = result.getDate();
            totalString = result.getTotal();
            score1String = result.getScore1();
            score2String = result.getScore2();
            score3String = result.getScore3();
            score4String = result.getScore4();
            score5String = result.getScore5();
            score6String = result.getScore6();
            score7String = result.getScore7();
            score8String = result.getScore8();
            score9String = result.getScore9();
            score10String = result.getScore10();
            score11String = result.getScore11();

            String dataString   =   "\"" + nameString + "\",\"" + schoolString + "\",\"" + meetNameString + "\",\"" + meetDateString + "\",\"" + totalString
                    + "\",\"" + score1String + "\",\"" + score2String + "\",\"" + score3String + "\",\"" + score4String
                    + "\",\"" + score5String + "\",\"" + score6String + "\",\"" + score7String + "\",\"" + score8String
                    + "\",\"" + score9String + "\",\"" + score10String + "\",\"" + score11String + "\"";

            r.append("\n").append(dataString);
        }

        combinedString = columnString + "\n" + r;

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

    private void printJudgeScoreTotalsMeet(){
        String diver = "", meetname = "", divenumber, divename, position, dd,
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
            file   =   new File(dir, diver + " " + meetname + " Scores.csv");
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
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,  diver + " " + meetname + " Results" );
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(Intent.createChooser(sendIntent, "Save Dive Results"));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerDiver && position >= 1) {
            String nameSpinner = "name";
            diverId = getDiverId(nameSpinner);
        }

        if(spinner.getId() == R.id.spinnerMeet && position >= 1) {

            String meetSpinner = "meet";
            meetId = getDiverId(meetSpinner);
        }

        if(spinner.getId() == R.id.spinnerReports && position == 1){
            reportId = 1;
        }

        if(spinner.getId() == R.id.spinnerReports && position == 2){
            reportId = 2;
        }

        if(spinner.getId() == R.id.spinnerReports && position == 3){
            reportId = 3;
        }


    }

    private void loadSpinnerReports(){
        ArrayList<String> reportName = new ArrayList<>();

        reportName.add("  Meet Results");
        reportName.add("  Diver Score Totals By Meet");
        reportName.add("  Diver Judge Scores By Meet");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, reportName);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerReports.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.report_name_spinner_row_nothing_selected, this));
    }

    private void loadSpinnerMeet(){
        GetNameForMeetRank rank = new GetNameForMeetRank();
        ArrayList<RankingsMeet> list;
        ArrayList<String> newList = new ArrayList<>();
        list = rank.doInBackground();
        for (RankingsMeet meet : list){
            newList.add("  " + meet.getMeetName() + "-" + meet.getBoardSize() + "  Meter");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, newList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerMeet.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.meet_name_spinner_row_nothing_selected, this));
    }


    private void loadSpinnerName(){
        GetDiverInfo diver = new GetDiverInfo();
        List<String> diverName = diver.doInBackground();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diverName);
        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerName.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.diver_name_spinner_row_nothing_selected, this));

    }

    public int getDiverId(String spinner){
        String name;
        int id;
        if(spinner.equals("name"))
        {
            stringId = spinnerName.getSelectedItem().toString().trim();
            GetDiverId diveid = new GetDiverId();
            id = diveid.doInBackground();
        }
        else
        {
            stringId = spinnerMeet.getSelectedItem().toString().trim();
            int dash = stringId.indexOf("-");
            boardType = Double.parseDouble(stringId.substring((dash + 1), (dash + 4)).trim());
            name = stringId.substring(0, dash);
            MeetDatabase db = new MeetDatabase(getApplicationContext());
            id = db.getId(name);
        }
        return id;
    }

    public void setUpView(){
        spinnerName = (Spinner)findViewById(R.id.spinnerDiver);
        spinnerMeet = (Spinner)findViewById(R.id.spinnerMeet);
        spinnerReports = (Spinner)findViewById(R.id.spinnerReports);
        btnSendReport = (Button)findViewById(R.id.buttonSendReport);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.reports, menu);
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
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetNameForMeetRank extends AsyncTask<ArrayList<RankingsMeet>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<RankingsMeet> name;

        @SafeVarargs
        @Override
        protected final ArrayList<RankingsMeet> doInBackground(ArrayList<RankingsMeet>... params) {
            return name = db.getNameForMeetRank();
        }
    }

    private class GetDiverInfo extends AsyncTask<List<String>, List<String>, List<String>>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        List<String> diverName;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return diverName = db.getDiverNames();
        }
    }

    private class GetDiverId extends AsyncTask<String, Integer, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }

    private class GetMeetId extends AsyncTask<String, Integer, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }
}
