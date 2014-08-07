package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DatabaseHelper;
import info.sqlite.helper.DiveNumberDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.ResultDatabase;
import info.sqlite.helper.TypeDatabase;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ChooseDiver extends Activity implements OnItemSelectedListener {

    private Spinner spinnerName;
    private RadioButton rbd6, rbd11, rbd1, rbd3;
    private RadioGroup radioGroupTotal, radioGroupType;
    private TextView MeetName, DiveTotal, DiveType;
    private int  meetSpinPosition, diverSpinnerPosition, diverId = 0,
                meetId = 0, diveTotal = 6, diveType = 1;
    private boolean checkResult, checkTotals, checkType, checkDiveNumber;
    String showDiveTotal;
    final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_diver);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        loadSpinnerName();
        spinnerName.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        meetId = b.getInt("keyMeet");
        diverId = b.getInt("keyDiver");
        diverSpinnerPosition = b.getInt("keySpin");
        meetSpinPosition = b.getInt("keyMeetPosition");
        spinnerName.setSelection(diverSpinnerPosition);

        loadMeetName();
        addListenerOnButton();
    }

    private void setUpView() {
        MeetName = (TextView)findViewById(R.id.EnterMeetM);
        DiveTotal = (TextView)findViewById(R.id.TextViewDiveTotals);
        DiveType = (TextView)findViewById(R.id.TextViewDiveType);
        radioGroupTotal = (RadioGroup)findViewById(R.id.radioGroupDives);
        radioGroupType = (RadioGroup)findViewById(R.id.radioGroupDiveTypes);
        rbd6 = (RadioButton)findViewById(R.id.radioDives6);
        rbd11 = (RadioButton)findViewById(R.id.radioDives11);
        rbd1 = (RadioButton)findViewById(R.id.radioType1);
        rbd3 = (RadioButton)findViewById(R.id.radioType3);
        spinnerName = (Spinner)findViewById(R.id.spinnerDiverName);
    }

    @Override
    public void onBackPressed(){

        final Context context = this;
        Intent intent = new Intent(context, Choose.class);
        Bundle b = new Bundle();
        b.putInt("keyMeetPosition", meetSpinPosition);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void loadMeetName(){
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> meetInfo;
        meetInfo = db.getMeetInfo(meetId);

        if(!meetInfo.isEmpty()){
            String meetName = meetInfo.get(0);
            MeetName.setText(meetName);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Meet is corrupted, please delete and add again",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), Welcome.class);
            startActivity(intent);
        }
    }

    private void loadSpinnerName(){
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        List<String> diverName = db.getDiverNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diverName);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerName.setPrompt("Choose Diver");
        spinnerName.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.diver_name_spinner_row_nothing_selected, this));

    }

    public void addListenerOnButton()
    {
        final Context context = this;
        Button btnNext = (Button) findViewById(R.id.buttonChooseJW);

        btnNext.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (diverId != 0 && meetId != 0) {
                    // checks to see if a diver and meet are attached to results yet
                    ResultDatabase db = new ResultDatabase(getApplicationContext());
                    checkResult = db.checkResult(meetId, diverId);
                    if (!checkResult) {
                        // enters 0 values in the results database
                        db.createEmptyResult(meetId, diverId);
                    }
                    // checks to see if a diver and meet are attached to a diveNumber yet
                    DiveNumberDatabase dbn = new DiveNumberDatabase(getApplicationContext());
                    checkDiveNumber = dbn.checkNumber(meetId, diverId);
                    if(!checkDiveNumber){
                        dbn.createNewDiveNumber(meetId, diverId);
                    }

                    enterDiveTotal();
                    enterDiveType();
                    Intent intent = new Intent(context, ChooseSummary.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    b.putInt("keySpin", diverSpinnerPosition);
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Diver",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, final int position,
                               long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerDiverName && position >= 1){
            diverId = getId();
        }
        diverSpinnerPosition = position;
        checkDiveTotal();
        checkDiveType();
    }

    private void checkDiveTotal(){
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        checkTotals = db.checkTotal(meetId, diverId);
        if(checkTotals){
            showDiveTotal = " Dives";
            radioGroupTotal.setVisibility(View.GONE);
            int howManyDives = db.searchTotals(meetId, diverId);
            showDiveTotal = howManyDives + showDiveTotal;
            DiveTotal.setText(showDiveTotal);
        }else {
            DiveTotal.setText("How Many Dives?");
            radioGroupTotal.setVisibility(View.VISIBLE);
        }
    }

    private void checkDiveType(){
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        checkType = db.checkType(meetId, diverId);
        if(checkType){
            radioGroupType.setVisibility(View.GONE);
            int typeOfDive = db.searchTypes(meetId, diverId);
            showDiveTotal = showDiveTotal + " - " + typeOfDive + " Meter" ;
            DiveTotal.setText(showDiveTotal);
            DiveType.setVisibility(View.GONE);
        }else {
            radioGroupType.setVisibility(View.VISIBLE);
            DiveType.setVisibility(View.VISIBLE);
        }
    }

    private void enterDiveTotal(){
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        checkTotals = db.checkTotal(meetId, diverId);
        if(!checkTotals){
            db.fillDiveTotal(meetId, diverId, diveTotal);
        }
    }

    private void enterDiveType(){
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        checkType = db.checkType(meetId, diverId);
        if(!checkType){
            db.createType(meetId, diverId, diveType);
        }
    }

    public void onrbd6Click(View v) {
        rbd6.setChecked(true);
        rbd11.setChecked(false);
        diveTotal = 6;
    }

    public void onrbd11Click(View v) {
        rbd6.setChecked(false);
        rbd11.setChecked(true);
        diveTotal = 11;
    }

    public void onrbd1Click(View v){
        rbd1.setChecked(true);
        rbd3.setChecked(false);
        diveType = 1;
    }

    public void onrbd3Click(View v){
        rbd1.setChecked(false);
        rbd3.setChecked(true);
        diveType = 3;
    }

    public int getId(){
        String stringId;
        int id;
        stringId = spinnerName.getSelectedItem().toString();
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        id = db.getId(stringId);
        return id;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choose_diver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_enter_diver:
                Intent intent = new Intent(context, EnterDiver.class);
                startActivity(intent);
                break;
            case R.id.menu_enter_meet:
                Intent intent1 = new Intent(context, EnterMeet.class);
                startActivity(intent1);
                break;
            case R.id.menu_remove_diver_from_meet:
                if(diverId != 0) {
                    Intent intent2 = new Intent(context, RemoveDiverFromMeet.class);
                    Bundle b = new Bundle();
                    b.putInt("keyDiver", diverId);
                    b.putInt("keyMeet", meetId);
                    intent2.putExtras(b);
                    startActivity(intent2);
                    //showAlert();
                    break;
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please Choose a Diver",
                            Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.menu_rankings:
                Intent intent2 = new Intent(context, Rankings.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNothingSelected(AdapterView<?> arg0)    {

    }
}
