package com.rodriguez.divingscores;

import info.controls.NothingSelectedSpinnerAdapter;
import info.sqlite.helper.DiveListDatabase;
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
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseDiver extends Activity implements OnItemSelectedListener {

    private Spinner spinnerName;
    private RadioButton rbd6, rbd11, rbd1, rbd3, rbd10, rbd75, rbd5;
    private RadioGroup radioGroupTotal;
    private TextView MeetName, DiveTotal, BoardType;
    private View layout1, layout2;
    private int  meetSpinPosition, diverSpinnerPosition, diverId = 0,
                meetId = 0, diveTotal = 6;
    private double boardType = 1;
    private boolean checkResult, checkTotals, checkBoardType, checkDiveNumber;
    private boolean checkListYes, checkList;
    private String showDiveTotal, stringId;
    final Context context = this;
    private Button btnList, btnNext;
    public boolean firstAlertChooseDiver;

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

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlertChooseDiver) {
            showAlert();
        }    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertChooseDiver = sp.getBoolean("firstAlertChooseDiver",false);
    }

    private void savePreferences(String key, boolean value){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void showAlert(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_list_info_warning);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertChooseDiver", true);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showAlertForHowTo(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_list_info_warning);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void setUpView() {
        MeetName = (TextView)findViewById(R.id.EnterMeetM);
        DiveTotal = (TextView)findViewById(R.id.TextViewDiveTotals);
        BoardType = (TextView)findViewById(R.id.TextViewDiveType);
        radioGroupTotal = (RadioGroup)findViewById(R.id.radioGroupDives);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        rbd6 = (RadioButton)findViewById(R.id.radioDives6);
        rbd11 = (RadioButton)findViewById(R.id.radioDives11);
        rbd1 = (RadioButton)findViewById(R.id.radioType1);
        rbd3 = (RadioButton)findViewById(R.id.radioType3);
        rbd10 = (RadioButton)findViewById(R.id.radioType10);
        rbd75 = (RadioButton)findViewById(R.id.radioType75);
        rbd5 = (RadioButton)findViewById(R.id.radioType5);
        btnList = (Button)findViewById(R.id.buttonChooseList);
        btnNext = (Button) findViewById(R.id.buttonChooseJW);
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
        ArrayList<String> meetInfo;
        GetInfoForMeet meetinfo = new GetInfoForMeet();
        meetInfo = meetinfo.doInBackground();

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
        GetDiverInfo diver = new GetDiverInfo();
        List<String> diverName = diver.doInBackground();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, diverName);

        dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
        spinnerName.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter, R.layout.diver_name_spinner_row_nothing_selected, this));

    }

    public void addListenerOnButton()
    {
        final Context context = this;

        btnNext.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                checkDiveList();
                if(!checkList) {
                    // first we need to write a divelist record
                    DiveListDatabase dldb = new DiveListDatabase(getApplicationContext());
                    dldb.createNewDiveList(meetId, diverId, 0, 0);
                }
                if (diverId != 0 && meetId != 0) {
                    // checks to see if a diver and meet are attached to results yet
                    CheckTheResults checks = new CheckTheResults();
                    checkResult = checks.doInBackground();
                    if (!checkResult) {
                        // enters 0 values in the results database
                        CreateEmptyResult empty = new CreateEmptyResult();
                        empty.doInBackground();
                    }
                    // checks to see if a diver and meet are attached to a diveNumber yet
                    CheckDiveNumber check = new CheckDiveNumber();
                    checkDiveNumber = check.doInBackground();
                    if (!checkDiveNumber) {
                        CreateNewDiveNumber newdive = new CreateNewDiveNumber();
                        newdive.doInBackground();
                    }
                    enterDiveTotal();
                    enterBoardDiveType();
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

        btnList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // first we need to see if there is a dive list record
                checkDiveList();
                if(!checkList) {
                    // then we need to write a divelist record
                    DiveListDatabase dldb = new DiveListDatabase(getApplicationContext());
                    dldb.createNewDiveList(meetId, diverId, 0, 0);
                }
                if (diverId != 0 && meetId != 0) {
                    // checks to see if a diver and meet are attached to results yet
                    CheckTheResults checks = new CheckTheResults();
                    checkResult = checks.doInBackground();
                    if (!checkResult) {
                        // enters 0 values in the results database
                        CreateEmptyResult empty = new CreateEmptyResult();
                        empty.doInBackground();
                    }
                    // checks to see if a diver and meet are attached to a diveNumber yet
                    CheckDiveNumber check = new CheckDiveNumber();
                    checkDiveNumber = check.doInBackground();
                    if (!checkDiveNumber) {
                        CreateNewDiveNumber newdive = new CreateNewDiveNumber();
                        newdive.doInBackground();
                    }
                    enterDiveTotal();
                    enterBoardDiveType();
                    Intent intent = new Intent(context, EnterDiveList.class);
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
        checkBoardType();
        checkForYesList();
        checkListFilled();
    }

    // if a list is started the list_filled field will be either set to 1 or 2,
    // if that is the case then we hide the Enter Score Button and user has to score
    // through the list
    private void checkListFilled(){
        CheckDiveListComplete check = new CheckDiveListComplete();
        int isComplete = check.doInBackground();
        if(isComplete == 1 || isComplete == 2) {
            btnNext.setVisibility(View.GONE);
        }else{
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    // if they have started scoreing a meet without a list then then List button will
    // be hidden
    private void checkDiveList(){
        CheckDiveList check = new CheckDiveList();
        checkList = check.doInBackground();
    }

    private void checkForYesList(){
        CheckForYesList noListCheck = new CheckForYesList();
        checkListYes = noListCheck.doInBackground();
        if(checkListYes){
            btnList.setVisibility(View.GONE);
        }else{
            btnList.setVisibility(View.VISIBLE);
        }
    }

    private void checkDiveTotal(){
        CheckDiveTotals check = new CheckDiveTotals();
        checkTotals = check.doInBackground();
        if(checkTotals){
            showDiveTotal = " Dives";
            radioGroupTotal.setVisibility(View.GONE);
            SearchDiveTotals search = new SearchDiveTotals();
            int howManyDives = search.doInBackground();
            showDiveTotal = howManyDives + showDiveTotal;
            DiveTotal.setText(showDiveTotal);
        }else {
            DiveTotal.setText("How Many Dives?");
            radioGroupTotal.setVisibility(View.VISIBLE);
        }
    }

    private void checkBoardType(){
        CheckBoardType checkboard = new CheckBoardType();
        checkBoardType = checkboard.doInBackground();
        if(checkBoardType){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            GetBoardType boardtype = new GetBoardType();
            String typeOfDive = boardtype.doInBackground();
            showDiveTotal = showDiveTotal + " - " + typeOfDive + " Meter" ;
            DiveTotal.setText(showDiveTotal);
            BoardType.setVisibility(View.GONE);
        }else {
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            BoardType.setVisibility(View.VISIBLE);
        }
    }

    private void enterDiveTotal(){
        CheckDiveTotals check = new CheckDiveTotals();
        checkTotals = check.doInBackground();
        if(!checkTotals){
            FillDiveTotal fill = new FillDiveTotal();
            fill.doInBackground();
        }
    }

    private void enterBoardDiveType(){
        CheckBoardType check = new CheckBoardType();
        checkBoardType = check.doInBackground();
        if(!checkBoardType){
            CreateBoardType create = new CreateBoardType();
            create.doInBackground();
        }
    }

    // radio buttons for dive totals
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

    // radio buttons for the board sizes
    public void onrbd1Click(View v){
        rbd1.setChecked(true);
        rbd3.setChecked(false);
        rbd10.setChecked(false);
        rbd75.setChecked(false);
        rbd5.setChecked(false);
        boardType = 1;
    }

    public void onrbd3Click(View v){
        rbd1.setChecked(false);
        rbd3.setChecked(true);
        rbd10.setChecked(false);
        rbd75.setChecked(false);
        rbd5.setChecked(false);
        boardType = 3;
    }

    public void onrbd10Click(View v){
        rbd1.setChecked(false);
        rbd3.setChecked(false);
        rbd10.setChecked(true);
        rbd75.setChecked(false);
        rbd5.setChecked(false);
        boardType = 10;
    }

    public void onrbd75Click(View v){
        rbd1.setChecked(false);
        rbd3.setChecked(false);
        rbd10.setChecked(false);
        rbd75.setChecked(true);
        rbd5.setChecked(false);
        boardType = 7.5;
    }

    public void onrbd5Click(View v){
        rbd1.setChecked(false);
        rbd3.setChecked(false);
        rbd10.setChecked(false);
        rbd75.setChecked(false);
        rbd5.setChecked(true);
        boardType = 5;
    }

    public int getId(){
        int id;
        stringId = spinnerName.getSelectedItem().toString().trim();
        GetDiverID getid = new GetDiverID();
        id = getid.doInBackground();
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
            case R.id.menu_how_to:
                showAlertForHowTo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNothingSelected(AdapterView<?> arg0)    {

    }

    private class GetInfoForMeet extends AsyncTask<ArrayList<String>, Object, Object> {
        MeetDatabase db = new MeetDatabase(getApplicationContext());
        ArrayList<String> info;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return info = db.getMeetInfo(meetId);
        }
    }

    private class GetDiverInfo extends AsyncTask<List<String>, List<String>, List<String>>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        List<String> diverName;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return diverName = db.getDiverNames();
        }
    }

    private class CheckTheResults extends AsyncTask<Boolean, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());
        boolean check;
        @Override
        protected Boolean doInBackground(Boolean... params) {
            return check = db.checkResult(meetId, diverId);
        }
    }

    private class CreateEmptyResult extends AsyncTask <Object, Object, Object>{
        ResultDatabase db = new ResultDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.createEmptyResult(meetId, diverId);
            return null;
        }
    }

    private class CheckDiveNumber extends AsyncTask<Boolean, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());
        boolean check;
        @Override
        protected Boolean doInBackground(Boolean... params) {
            return check = db.checkNumber(meetId, diverId);
        }
    }

    private class CreateNewDiveNumber extends AsyncTask<Object, Object, Object>{
        DiveNumberDatabase db = new DiveNumberDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.createNewDiveNumber(meetId, diverId, boardType);
            return null;
        }
    }

    private class CheckDiveTotals extends AsyncTask<Boolean, Object, Object> {
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());
        boolean check;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return db.checkTotal(meetId, diverId);
        }
    }

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object>{
    DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }

    private class CheckBoardType extends AsyncTask<Boolean, Object, Object>{
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        boolean check;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return check = db.checkType(meetId, diverId);
        }
    }

    private class CheckDiveList extends AsyncTask<Boolean, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        boolean check;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return check = db.checkList(meetId, diverId);
        }
    }

    private class CheckDiveListComplete extends AsyncTask<Integer, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        int check;

        @Override
        protected Integer doInBackground(Integer... params) {
            return check = db.isListFinished(meetId, diverId);
        }
    }

    private class GetBoardType extends AsyncTask<String, Object, Object>{
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        double type;
        String typeString;

        @Override
        protected String doInBackground(String... params) {
            type =  db.searchTypes(meetId, diverId);
            if (type == 1.0) {
                typeString = "1";
            }else if (type == 3.0) {
                typeString = "3";
            }else if (type == 5.0) {
                typeString = "5";
            }else if (type == 7.5) {
                typeString = "7.5";
            }else if (type == 10.0) {
                typeString = "10";
            }
            return typeString;
        }
    }

    private class FillDiveTotal extends AsyncTask<Object, Object, Object>{
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.fillDiveTotal(meetId, diverId, diveTotal);
            return null;
        }
    }

    private class CreateBoardType extends AsyncTask<Object, Object, Object> {
        TypeDatabase db = new TypeDatabase(getApplicationContext());

        @Override
        protected Object doInBackground(Object... params) {
            db.createType(meetId, diverId, boardType);
            return null;
        }
    }

        private class GetDiverID extends AsyncTask<Integer, Object, Object>{
            DiverDatabase db = new DiverDatabase(getApplicationContext());
            int id;

            @Override
            protected Integer doInBackground(Integer... params) {
                return id = db.getId(stringId);
        }
    }

    private class CheckForYesList extends AsyncTask<Boolean, Object, Object>{
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        boolean check;

        @Override
        protected Boolean doInBackground(Boolean... params) {
            return db.checkForNoList(meetId, diverId);
        }
    }
}
