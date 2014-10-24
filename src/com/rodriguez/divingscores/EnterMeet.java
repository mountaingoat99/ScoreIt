package com.rodriguez.divingscores;

import info.sqlite.helper.MeetDatabase;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EnterMeet extends Activity implements
OnClickListener {

    private RadioGroup radioJudgesGroup;
    private TextView name, school, city, state;
	private EditText txtDate;
    private int judges, judgeChecked;
	private String nameString, schoolString, cityString, stateString, dateString;
    public boolean firstAlert2Judges;
    private final Context context = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_meet);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        txtDate = (EditText)findViewById(R.id.editTextDate);
        txtDate.setOnClickListener(this);

        addListenerOnButton();

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlert2Judges) {
            showAlert();
        }
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlert2Judges = sp.getBoolean("firstAlertTwoJudges",false);
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
        dialog.setContentView(R.layout.dialog_judge_2_info);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertTwoJudges", true);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showAlertForMenu(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_judge_2_info);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
	
	@Override
    public void onClick(View v) {
 
        if (v == txtDate) {
 
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
 
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
 
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year); 
                        }
                    }, mYear, mMonth, mDay
            );
            dpd.show();
        }
    }
	
	public void addListenerOnButton()
    {
    	final Context context = this;
    	radioJudgesGroup = (RadioGroup)findViewById(R.id.radioGroupMeet);
        Button btnEnterMeet = (Button) findViewById(R.id.buttonMeet);
    	btnEnterMeet.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                name = (TextView) findViewById(R.id.editTextName);
                nameString = name.getText().toString().trim();
                school = (TextView) findViewById(R.id.editTextSchool);
                schoolString = school.getText().toString().trim();
                city = (TextView) findViewById(R.id.editTextCity);
                cityString = city.getText().toString().trim();
                state = (TextView) findViewById(R.id.editTextState);
                stateString = state.getText().toString().trim();
                txtDate = (EditText) findViewById(R.id.editTextDate);
                dateString = txtDate.getText().toString().trim();
                judgeChecked = radioJudgesGroup.getCheckedRadioButtonId();
                if (judgeChecked == R.id.radio3J)
                    judges = 3;
                if (judgeChecked == R.id.radio5J)
                    judges = 5;
                if (judgeChecked == R.id.radio7J)
                    judges = 7;

                if (nameString.isEmpty() || schoolString.isEmpty()
                        || stateString.isEmpty() || dateString.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please make an entry in all fields", Toast.LENGTH_LONG).show();
                } else {
                    WriteNewMeet newmeet = new WriteNewMeet();
                    newmeet.doInBackground();
                    Toast.makeText(getApplicationContext(),
                            "Meet has been saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, Welcome.class);
                    startActivity(intent);
                }
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_enter_meet, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                showAlertForMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Async database tasks
    private class WriteNewMeet extends AsyncTask<String, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.fillMeet(nameString, schoolString, cityString, stateString, dateString, judges);
            return null;
        }
    }

}
