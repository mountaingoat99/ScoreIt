package com.rodriguez.divingscores;

import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiverHistory extends Activity {

    private ListView myList;
	private TextView name, age, grade, school;
    private int diverId, meetId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_history);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        myList = (ListView)findViewById(R.id.list);
        name = (TextView)findViewById(R.id.nameHistory);
        age = (TextView)findViewById(R.id.ageHistory);
        grade = (TextView)findViewById(R.id.gradeHistory);
        school = (TextView)findViewById(R.id.schoolHistory);              
        
        Bundle b = getIntent().getExtras();
        diverId = b.getInt("key");
        fillText();
        
        populateListViewFromDB();

        Toast.makeText(getApplicationContext(),
    			"Click a meet to see the scores",
    			Toast.LENGTH_LONG).show();		
    }	
		
	public void fillText(){
		DiverDatabase db = new DiverDatabase(getApplicationContext());
		ArrayList<String> diverInfo;
		diverInfo = db.getDiverInfo(diverId);
			
		if(!diverInfo.isEmpty()){
            String nameString = diverInfo.get(0);
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
        			"Diver is corrupted, please delete and add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	private void populateListViewFromDB() {				
		MeetDatabase db = new MeetDatabase(getApplicationContext());
		ArrayList<String> meetInfo;
		meetInfo = db.getMeetHistory(diverId);	
		
		if (!meetInfo.isEmpty()){
			ArrayAdapter<String> adapter = new ArrayAdapter<>(
					this, R.layout.list_item, meetInfo);
			myList.setAdapter(adapter);	
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Diver has not been in any meets yet",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
		
		myList.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String stringId = myList.getItemAtPosition(position).toString();				
				
				MeetDatabase db = new MeetDatabase(getApplicationContext());				
				meetId = db.getId(stringId);
				
				Intent intent = new Intent(getBaseContext(), MeetScores.class);
				Bundle b = new Bundle();
				b.putInt("key", diverId);
				b.putInt("key2", meetId);
				intent.putExtras(b);
				startActivity(intent);				
			}
		});	
	}		
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_diver_history, menu);
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
