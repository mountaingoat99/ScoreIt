package com.rodriguez.divingscores;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import info.sqlite.helper.DiverDatabase;

public class RemoveDiverFromMeet extends Activity {

    private Button cancel, yes;
    private int meetId, diverId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remove_diver_from_meet);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cancel = (Button)findViewById(R.id.buttonCancel);
        yes = (Button)findViewById(R.id.buttonYes);

        Bundle b = getIntent().getExtras();
        meetId = b.getInt("keyMeet");
        diverId = b.getInt("keyDiver");

        addListenerOnButton();
    }

    public void addListenerOnButton(){
        final Context context = this;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                RemoveDiver remove = new RemoveDiver();
                remove.doInBackground();
                Intent intent1 = new Intent(context, Welcome.class);
                startActivity(intent1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class RemoveDiver extends AsyncTask<Object, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.removeDiverFromMeet(meetId, diverId);
            return null;
        }
    }
}
