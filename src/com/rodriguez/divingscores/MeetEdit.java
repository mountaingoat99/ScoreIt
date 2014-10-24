package com.rodriguez.divingscores;

import info.sqlite.helper.MeetDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MeetEdit extends Activity implements
        OnClickListener{

    private RadioGroup radioJudgesGroup;
    private RadioButton judge3, judge5, judge7;
	private EditText name, school, city, state, date;
	private String nameString, schoolString, cityString, stateString, dateString, nameEdit,
            schoolEdit, cityEdit, stateEdit, dateEdit, judgeString;
	private int meetId, judges, judgeChecked;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_edit);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setupView();
        date.setOnClickListener(this);

        Bundle b = getIntent().getExtras();
        meetId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }

    private void setupView() {
        name = (EditText)findViewById(R.id.editTextNameEM);
        school = (EditText)findViewById(R.id.editTextSchoolEM);
        city = (EditText)findViewById(R.id.editTextCityEM);
        state = (EditText)findViewById(R.id.editTextStateEM);
        date = (EditText)findViewById(R.id.editTextDateEM);
        radioJudgesGroup = (RadioGroup)findViewById(R.id.radioGroupMeet);
        judge3 = (RadioButton)findViewById(R.id.radio3J);
        judge5 = (RadioButton)findViewById(R.id.radio5J);
        judge7 = (RadioButton)findViewById(R.id.radio7J);
    }

    public void fillEditText(){
		ArrayList<String> meetInfo;
        GetInfoForMeet meetinfo = new GetInfoForMeet();
        meetInfo = meetinfo.doInBackground();

		
		if(!meetInfo.isEmpty()){
			nameString = meetInfo.get(0);
			schoolString = meetInfo.get(1);
			cityString = meetInfo.get(2);
			stateString = meetInfo.get(3);
			dateString = meetInfo.get(4);
            judgeString = meetInfo.get(5);
		
			name.setText(nameString);
			school.setText(schoolString);
			city.setText(cityString);
			state.setText(stateString);
			date.setText(dateString);

            if(judgeString.equals("3")){
                judge3.setChecked(true);
                judge5.setChecked(false);
                judge7.setChecked(false);
            }
            if(judgeString.equals("5")){
                judge5.setChecked(true);
                judge3.setChecked(false);
                judge7.setChecked(false);
            }
            if (judgeString.equals("7")) {
                judge7.setChecked(true);
                judge3.setChecked(false);
                judge5.setChecked(false);
            }
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Meet is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}

    private class EditNameinDB extends AsyncTask<String, Object, Object>{
		MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetName(meetId, nameEdit);
            return null;
        }
	}

    private class EditSchoolinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetSchool(meetId, schoolEdit);
            return null;
        }
	}

    private class EditCityinDB extends AsyncTask<String, Object, Object>{
		MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetCity(meetId, cityEdit);
            return null;
        }
	}

    private class EditStateinDB extends AsyncTask<String, Object, Object>{
		MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetState(meetId, stateEdit);
            return null;
        }
	}

    private class EditDateinDB extends AsyncTask<String, Object, Object>{
		MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetDate(meetId, dateEdit);
            return null;
        }
	}

    private class EditJudgeinDB extends AsyncTask<String, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editMeetJudges(meetId, judges);
            return null;
        }
    }
	
	public void addListenerOnButton()
    {
		final Context context = this;

        Button btnEditMeet = (Button) findViewById(R.id.buttonMeetE);
    	btnEditMeet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nameEdit = name.getText().toString().trim();
                schoolEdit = school.getText().toString().trim();
                cityEdit = city.getText().toString().trim();
                stateEdit = state.getText().toString().trim();
                dateEdit = date.getText().toString().trim();
                judgeChecked = radioJudgesGroup.getCheckedRadioButtonId();
                if (judgeChecked == R.id.radio3J)
                    judges = 3;
                if (judgeChecked == R.id.radio5J)
                    judges = 5;
                if (judgeChecked == R.id.radio7J)
                    judges = 7;

                if (nameEdit.isEmpty() || schoolEdit.isEmpty()
                        || cityEdit.isEmpty() || stateEdit.isEmpty()
                        || dateEdit.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please make an entry in all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!nameEdit.equals(nameString)) {
                    EditNameinDB eName = new EditNameinDB();
                    eName.doInBackground();
                }
                if (!schoolEdit.equals(schoolString)) {
                    EditSchoolinDB eSchool = new EditSchoolinDB();
                    eSchool.doInBackground();
                }
                if (!cityEdit.equals(cityString)) {
                    EditCityinDB eCity = new EditCityinDB();
                    eCity.doInBackground();
                }
                if (!stateEdit.equals(stateString)) {
                    EditStateinDB eState = new EditStateinDB();
                    eState.doInBackground();
                }
                if (!dateEdit.equals(dateString)) {
                    EditDateinDB eDate = new EditDateinDB();
                    eDate.doInBackground();
                }
                EditJudgeinDB eJudge = new EditJudgeinDB();
                eJudge.doInBackground();
                Toast.makeText(getApplicationContext(),
                        "Diver has been edited to " + nameEdit + ", "
                                + schoolEdit + ", " + cityEdit + ", "
                                + stateEdit + ", " + dateEdit + ", Judges: "
                                + judges, Toast.LENGTH_LONG
                ).show();
                Intent intent = new Intent(context, Welcome.class);
                startActivity(intent);
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_meet_edit, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        final Context context = this;
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_how_to:
                Intent intent3 = new Intent(context, HowTo.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v == date) {

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
                            date.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay
            );
            dpd.show();
        }
    }

    private class GetInfoForMeet extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getMeetInfo(meetId);
        }
    }
}
