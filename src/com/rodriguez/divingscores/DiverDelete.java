package com.rodriguez.divingscores;

import info.sqlite.helper.DiverDatabase;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DiverDelete extends Activity {

    private TextView name, age, grade, school;
	private String nameString;
    private int diverId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_delete);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (TextView)findViewById(R.id.deleteName);
        age = (TextView)findViewById(R.id.deleteAge);
        grade = (TextView)findViewById(R.id.deleteGrade);
        school = (TextView)findViewById(R.id.deleteSchool);
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }
	
	public void fillEditText(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		ArrayList<String> diverInfo;
		diverInfo = db.getDiverInfo(diverId);
						
		if(!diverInfo.isEmpty()){
			nameString = diverInfo.get(0);
            String ageString = diverInfo.get(1);
            String gradeString = diverInfo.get(2);
            String schoolString = diverInfo.get(3);
		
			name.setText(nameString);		
			age.setText(ageString);		
			grade.setText(gradeString);		
			school.setText(schoolString);	
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Diver if is corrupted, please edit or add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	public void deleteDiverinDB(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		db.deleteDiver(diverId);
	}
	
	public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnDelete = (Button) findViewById(R.id.buttonDiverDeleteE);
    	btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                deleteDiverinDB();

                Toast.makeText(getApplicationContext(),
                        "Diver " + nameString + " has been deleted",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Welcome.class);
                startActivity(intent);
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_diver_delete, menu);
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
            case R.id.menu_about:
                Intent intent4 = new Intent(context, About.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
