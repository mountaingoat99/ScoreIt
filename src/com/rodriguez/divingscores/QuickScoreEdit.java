package com.rodriguez.divingscores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.sqlite.helper.QuickScoreDatabase;


public class QuickScoreEdit extends ActionBarActivity {

    private TextView meetName, score1, score2, score3, score4, score5, score6, score7,
                    score8, score9, score10, score11, total;
    private double sumTotal = 0.0;
    private String string1 = "", string2 = "", string3 = "", string4 = "", string5 = "", string6 = "", string7 = "", string8 = "",
                    string9 = "", string10 = "", string11 = "", stringTotal = "", nameMeetString = "";
    private int sheetId, scoreNumber;
    private final Context context = this;
    Bitmap myBitmap;
    public boolean firstAlertQuickEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_score_edit);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();


        Bundle b = getIntent().getExtras();
        if(b != null){
            sheetId = b.getInt("keySheet");
            fillText();
        }
        setUpLongPress();

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlertQuickEdit) {
            showAlert();
        }
    }
    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertQuickEdit = sp.getBoolean("firstAlertWelcome",false);
    }

    private void savePreferences(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void showAlert(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quick_score_edit);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertWelcome", true);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showAlertForHowTo(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quick_score_edit);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void fillText(){
        List<Double> scoreList = new ArrayList<>();

        GetSheetInfo sheets = new GetSheetInfo();
        ArrayList<String> sheetInfo = sheets.doInBackground();
        if(!sheetInfo.isEmpty()) {
            nameMeetString = sheetInfo.get(0);
            meetName.setText(nameMeetString);
            string1 = sheetInfo.get(1);
            score1.setText(string1);
            string2 = sheetInfo.get(2);
            score2.setText(string2);
            string3 = sheetInfo.get(3);
            score3.setText(string3);
            string4 = sheetInfo.get(4);
            score4.setText(string4);
            string5 = sheetInfo.get(5);
            score5.setText(string5);
            string6 = sheetInfo.get(6);
            score6.setText(string6);
            string7 = sheetInfo.get(7);
            score7.setText(string7);
            string8 = sheetInfo.get(8);
            score8.setText(string8);
            string9 = sheetInfo.get(9);
            score9.setText(string9);
            string10 = sheetInfo.get(10);
            score10.setText(string10);
            string11 = sheetInfo.get(11);
            score11.setText(string11);

            //removes the title String and then the total string
            //to add the scores to a list of doubles
            //then we will sum the list
            sheetInfo.remove(0);
            sheetInfo.remove(11);
            for (String sheet : sheetInfo){
                    scoreList.add(Double.parseDouble(sheet));
            }
        }

        for (Double i : scoreList){
            sumTotal = sumTotal + i;
        }
        total.setText(stringTotal= String.valueOf(sumTotal));
    }

        @Override
        public void onBackPressed(){
            final Context context = this;
            Intent intent = new Intent(context, QuickScore.class);
            Bundle b = new Bundle();
            b.putInt("keyPosition", 0);
            intent.putExtras(b);
            startActivity(intent);
    }

    private void setUpLongPress(){
        meetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 0;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", nameMeetString);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 1;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string1);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 2;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string2);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 3;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string3);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 4;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string4);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 5;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string5);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 6;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string6);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 7;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string7);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 8;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string8);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 9;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string9);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 10;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string10);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        score11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreNumber = 11;
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                b.putInt("diveNumber", scoreNumber);
                b.putString("sheetName", string11);
                Intent intent = new Intent(context, EditQuickScoreValue.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void setUpView(){
        meetName = (TextView)findViewById(R.id.editNameMeet);
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
        total = (TextView)findViewById(R.id.total);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quick_score_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_delete_quick_list) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_delete_quick_score);
            Button cancelButton = (Button) dialog.findViewById(R.id.buttonCancel);
            Button yesButton = (Button) dialog.findViewById(R.id.buttonYes);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
                    db.deleteSheet(sheetId);
                    dialog.cancel();
                    Intent intent = new Intent(context, QuickScore.class);
                    Bundle b = new Bundle();
                    b.putInt("keyPosition", 0);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });

            dialog.show();
            return true;
        }

        if(id == R.id.menu_how_to){
            showAlertForHowTo();
        }

        if(id == R.id.action_share){
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            myBitmap = v1.getDrawingCache();
            saveBitmap(myBitmap);
        }

        if(id == R.id.action_email){
            emailFile();
        }

        return super.onOptionsItemSelected(item);
    }

    private void emailFile(){

    String columnString =   "\"Diver\"," +
            "\"Dive1\",\"Dive2\",\"Dive3\",\"Dive4\",\"Dive5\",\"Dive6\"," +
            "\"Dive7\",\"Dive8\",\"Dive9\",\"Dive10\",\"Dive11\",\"Total\",";
    String dataString   =   "\"" + nameMeetString + "\",\"" + string1 + "\",\"" + string2 + "\",\"" + string3 + "\",\"" + string4
            + "\",\"" + string5 + "\",\"" + string6 + "\",\"" + string7 + "\",\"" + string8
            + "\",\"" + string9 + "\",\"" + string10+ "\",\"" + string11 + "\",\"" + stringTotal + "\"";

    String combinedString = columnString + "\n" + dataString;

    File file   = null;
    File root   = Environment.getExternalStorageDirectory();
    if (root.canWrite()){
        File dir    =   new File (root.getAbsolutePath() + "/PersonData");
        dir.mkdirs();
        file   =   new File(dir, nameMeetString + " Scores.csv");
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
        String dates;

        // formats the date
        SimpleDateFormat outdate = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date DateString = new Date();
        dates = outdate.format(DateString);

        return "Meet scores from " + nameMeetString
            + " on " + dates + "." + "\n"
                + "Sent from ScoreIt.";
    }

    private class GetSheetInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
        ArrayList<String> sheets;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return sheets = db.getQuickScoreMemo(sheetId);
        }
    }


}
