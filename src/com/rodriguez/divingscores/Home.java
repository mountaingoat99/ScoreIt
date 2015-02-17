package com.rodriguez.divingscores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;


public class Home extends ActionBarActivity {

    private Button btnQuick, btnDetailed;
    private boolean eliteAlert;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnQuick = (Button)findViewById(R.id.buttonQuickScore);
        btnDetailed = (Button)findViewById(R.id.buttonDetailScore);

        createShareDirectory();

        addListenerOnButton();

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!eliteAlert) {
            showAlert();
            savePreferences("eliteAlert", true);
        }
    }

    private void createShareDirectory(){

        File newDir = new File(Environment.getExternalStorageDirectory() + "/ScoreMyDive/");
        if (!newDir.exists()) {
            newDir.mkdirs();
        } else {
            Log.d("Error", "Directory Already Exists");
        }
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        eliteAlert = sp.getBoolean("eliteAlert",false);
    }

    private void savePreferences(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void showAlert(){

        Intent intent = new Intent(context, eliteDialog.class);
        startActivity(intent);
    }

    // do not allow any back presses on the Welcome screen to other activities
    // just exit the app
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void addListenerOnButton(){
        btnQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuickScore.class);
                startActivity(intent);
            }
        });

        btnDetailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Welcome.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
                Intent intent2 = new Intent(context, About.class);
                startActivity(intent2);
                break;
            case R.id.elite:
                Intent intent1 = new Intent(context, eliteDialog.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
