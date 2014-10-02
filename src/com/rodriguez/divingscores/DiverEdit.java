package com.rodriguez.divingscores;

import info.sqlite.helper.DiverDatabase;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiverEdit extends Activity {

    private EditText name, age, grade, school;
	private String nameString, ageString, gradeString, schoolString, nameEdit,
                    ageEdit, gradeEdit, schoolEdit;
	private int diverId;	
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_edit);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (EditText)findViewById(R.id.editTextNameED);
        age = (EditText)findViewById(R.id.editTextAgeED);
        grade = (EditText)findViewById(R.id.editTextGradeED);
        school = (EditText)findViewById(R.id.editTextSchoolED);
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }
	
	public void fillEditText(){
        // calls a separate thread
        ArrayList<String> diverInfo;
        GetInfoForDiver diveinfo = new GetInfoForDiver();
        diverInfo = diveinfo.doInBackground();
			
		if(!diverInfo.isEmpty()){
			nameString = diverInfo.get(0);
			ageString = diverInfo.get(1);
			gradeString = diverInfo.get(2);
			schoolString = diverInfo.get(3);		
		
			name.setText(nameString);		
			age.setText(ageString);		
			grade.setText(gradeString);		
			school.setText(schoolString);	
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Diver is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	private class EditNameinDB extends AsyncTask<String, Object, Object>{
		DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editDiverName(diverId, nameEdit);
            return null;
        }
	}

    private class EditAgeinDB extends AsyncTask<String, Object, Object>{
		DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editDiverAge(diverId, ageEdit);
            return null;
        }
	}

    private class EditGradeinDB extends AsyncTask<String, Object, Object>{
		DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editDiverGrade(diverId, gradeEdit);
            return null;
        }
	}

    private class EditSchoolinDB extends AsyncTask<String, Object, Object>{
		DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(String... params) {
            db.editDiverSchool(diverId, schoolEdit);
            return null;
        }
	}
	
	public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnEditDiver = (Button) findViewById(R.id.buttonDiverE);
    	btnEditDiver.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nameEdit = name.getText().toString().trim();
                ageEdit = age.getText().toString().trim();
                gradeEdit = grade.getText().toString().trim();
                schoolEdit = school.getText().toString().trim();
                if (nameEdit.isEmpty() || ageEdit.isEmpty()
                        || gradeEdit.isEmpty() || schoolEdit.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "Please make an entry in all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!nameEdit.equals(nameString)) {
                    EditNameinDB eName = new EditNameinDB();
                    eName.doInBackground();
                }
                if (!ageEdit.equals(ageString)) {
                    EditAgeinDB eAge = new EditAgeinDB();
                    eAge.doInBackground();
                }
                if (!gradeEdit.equals(gradeString)) {
                    EditGradeinDB eGrade = new EditGradeinDB();
                    eGrade.doInBackground();
                }
                if (!schoolEdit.equals(schoolString)) {
                    EditSchoolinDB eSchool = new EditSchoolinDB();
                    eSchool.doInBackground();
                }
                Toast.makeText(getApplicationContext(),
                        "Diver has been edited to " + nameEdit + ", "
                                + ageEdit + ", " + gradeEdit + ", "
                                + schoolEdit, Toast.LENGTH_LONG
                ).show();
                Intent intent = new Intent(context, Welcome.class);
                startActivity(intent);
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_diver_edit, menu);
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

    private class GetInfoForDiver extends AsyncTask<ArrayList<String>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getDiverInfo(diverId);
        }
    }
}
