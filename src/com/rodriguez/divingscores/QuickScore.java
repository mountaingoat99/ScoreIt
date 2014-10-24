package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.QuickScoreDatabase;


public class QuickScore extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private Button btnNewScoreSheet;
    private int spinPosition, sheetId;
    private String stringId;
    ArrayAdapter<String> dataAdapter;
    private int newSheet = 0;
    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_score);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loadSavedPreferences();
        spinner = (Spinner)findViewById(R.id.spinnerScoreSheets);
        loadSpinner();
        spinner.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        if(b != null){
            spinPosition = b.getInt("keyPosition");
            spinner.setSelection(spinPosition);
        }

        addListenerOnButton();
    }

    @Override
    public void onBackPressed(){
        final Context context = this;
        Intent intent = new Intent(context, Home.class);
        startActivity(intent);
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        newSheet = sp.getInt("newSheet",newSheet);
    }

    private void savePreferences(String key, int value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int createNewSheet(){
        newSheet ++;
        savePreferences("newSheet", newSheet);
        CreateNewSheet newSheet = new CreateNewSheet();
        return newSheet.doInBackground();
    }

    private void loadSpinner(){
        GetScoreSheets sheets = new GetScoreSheets();
        List<String> scoreSheets = sheets.doInBackground();

        dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, scoreSheets);

        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.quick_score_spinner_row_nothing_selected, this));
    }

    public void addListenerOnButton(){

        btnNewScoreSheet = (Button)findViewById(R.id.buttonNewScoreSheet);

        btnNewScoreSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetId = createNewSheet();
                Intent intent = new Intent(context, QuickScoreEdit.class);
                Bundle b = new Bundle();
                b.putInt("keySheet", sheetId);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        Spinner spinner = (Spinner) parent;
        spinPosition = position;
        if(spinner.getId() == R.id.spinnerScoreSheets && position >= 1){
            sheetId = getId();
            Intent intent = new Intent(context, QuickScoreEdit.class);
            Bundle b = new Bundle();
            b.putInt("keySheet", sheetId);
            intent.putExtras(b);
            startActivity(intent);
        }
    }

    public int getId(){
        int id;
        stringId = spinner.getSelectedItem().toString().trim();
        GetSheetID ID = new GetSheetID();
        id = ID.doInBackground(stringId);
        return id;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quick_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetScoreSheets extends AsyncTask<List<String>, List<String>, List<String>>{
        QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
        List<String> sheets;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return sheets = db.getQuickScoreMemoName();
        }
    }

    private class GetSheetID extends AsyncTask<String, Integer, Object> {
        QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
        int ids;

        @Override
        protected Integer doInBackground(String... params) {
            return ids = db.getId(stringId);
        }
    }

    private class CreateNewSheet extends AsyncTask<Integer, Object, Object>{
        QuickScoreDatabase db = new QuickScoreDatabase(getApplicationContext());
        int id;

        @Override
        protected Integer doInBackground(Integer... params) {
            return id = db.createEmptyQuickScore(newSheet);
        }
    }
}

