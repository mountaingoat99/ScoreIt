package com.rodriguez.divingscores;

import info.sqlite.helper.MeetDatabase;

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
import android.widget.TextView;
import android.widget.Toast;

public class MeetDelete extends Activity {

    private TextView name, school, city, state, date;
	private String nameString;
    private int meetId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_delete);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        name = (TextView)findViewById(R.id.deleteMName);
        school = (TextView)findViewById(R.id.deleteSchool);
        city = (TextView)findViewById(R.id.deleteCity);
        state = (TextView)findViewById(R.id.deleteState);
        date = (TextView)findViewById(R.id.deleteDate);
        
        Bundle b = getIntent().getExtras();
        meetId = b.getInt("key");

        fillEditText();
        addListenerOnButton();
    }
	
	public void fillEditText(){
		ArrayList<String> meetInfo;
        GetMeetInfo info = new GetMeetInfo();
		meetInfo = info.doInBackground();
		
		if(!meetInfo.isEmpty()){
			nameString = meetInfo.get(0);
            String schoolString = meetInfo.get(1);
            String cityString = meetInfo.get(2);
            String stateString = meetInfo.get(3);
            String dateString = meetInfo.get(4);
		
			name.setText(nameString);
			school.setText(schoolString);
			city.setText(cityString);
			state.setText(stateString);
			date.setText(dateString);		
		}
		else
		{
			Toast.makeText(getApplicationContext(),
        			"Meet if is corrupted, please edit or add again",
        			Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), Welcome.class);
			startActivity(intent);
		}
	}
	
	public void addListenerOnButton()
    {
    	final Context context = this;
        Button btnDelete = (Button) findViewById(R.id.buttonMeetDelete);
    	btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                DeleteMeetinDB delete = new DeleteMeetinDB();
                delete.doInBackground();

                Toast.makeText(getApplicationContext(),
                        "Meet " + nameString + " has been deleted",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Welcome.class);
                startActivity(intent);
            }
        });
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_meet_delete, menu);
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

    private class DeleteMeetinDB extends AsyncTask<Integer, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Integer... params) {
            db.deleteMeet(meetId);
            return null;
        }
    }

    private class GetMeetInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return meetinfo = db.getMeetInfo(meetId);
        }
    }
}
