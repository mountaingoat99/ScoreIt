package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.sqlite.helper.QuickScoreDatabase;


public class QuickScoreEdit extends Activity {

    private TextView meetName, score1, score2, score3, score4, score5, score6, score7,
                    score8, score9, score10, score11, total;
    private double sumTotal = 0.0;
    private String string1 = "", string2 = "", string3 = "", string4 = "", string5 = "", string6 = "", string7 = "", string8 = "",
                    string9 = "", string10 = "", string11 = "", stringTotal = "", nameMeetString = "";
    private int sheetId, scoreNumber;
    private final Context context = this;
    public boolean firstAlertQuickEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_score_edit);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
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

        return super.onOptionsItemSelected(item);
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
