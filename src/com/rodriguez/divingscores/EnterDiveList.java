package com.rodriguez.divingscores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.Helpers.DiveStyleSpinner;
import info.controls.SpinnerDiveStyleCustomBaseAdpater;
import info.sqlite.helper.ArmstandPlatformDatabase;
import info.sqlite.helper.BackDatabase;
import info.sqlite.helper.BackPlatformDatabase;
import info.sqlite.helper.DiveListDatabase;
import info.sqlite.helper.DiveTotalDatabase;
import info.sqlite.helper.DiverDatabase;
import info.sqlite.helper.DivesDatabase;
import info.sqlite.helper.ForwardDatabase;
import info.sqlite.helper.ForwardPlatformDatabase;
import info.sqlite.helper.InwardDatabase;
import info.sqlite.helper.InwardPlatformDatabase;
import info.sqlite.helper.JudgeScoreDatabase;
import info.sqlite.helper.MeetDatabase;
import info.sqlite.helper.PlatformDivesDatabase;
import info.sqlite.helper.ReverseDatabase;
import info.sqlite.helper.ReversePlatformDatabase;
import info.sqlite.helper.TwistDatabase;
import info.sqlite.helper.TwistPlatformDatabase;
import info.sqlite.helper.TypeDatabase;

public class EnterDiveList extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private TextView diveNumberView, DODView, diveInfo1, diveInfo2, diveInfo3, diveInfo4, diveInfo5, diveInfo6,
                    diveInfo7, diveInfo8, diveInfo9, diveInfo10, diveInfo11, diverName, meetName, name,
                    textView1;
    private View view1;
    private LinearLayout layout1, layout2;
    private TableRow row1, row2, row3, row4,row5, row6, row7, row8, row9, row10, row11 ;
    private Spinner diveCategory, diveStyle;
    private RadioButton radioStraight, radioPike, radioTuck, radioFree;
    private Button btnEnter, btnScore;
    private int diverId, meetId, diveTotal, divePosition, diveType = 1, newDiveType = 0, diveNumber = 0, diverSpinnerPosition;
    private double boardType, multiplier = 0.0;
    private ArrayList<DiveStyleSpinner> searchDives;
    private String infoString1 = "", infoString2 = "", infoString3 = "", infoString4 = "", infoString5 = "",
            infoString6 = "", infoString7 = "", infoString8 = "", infoString9 = "", infoString10 = "", infoString11 = "",
            stringId, ddString;

    private final Context context = this;
    public boolean firstAlertEditDiveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_dive_list);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setUpView();
        diveCategory.setOnItemSelectedListener(this);
        diveStyle.setOnItemSelectedListener(this);

        Bundle b = getIntent().getExtras();
        diverId = b.getInt("keyDiver");
        meetId = b.getInt("keyMeet");
        diverSpinnerPosition = b.getInt("keySpin");
        getBoardType();
        loadCategorySpinnerData();
        getMultiplier();
        getDiveTotals();
        fillNameText();
        getDiveNumber();
        fillDiveNumber();
        fillDiveInfo();
        checkRadios();
        addListenerOnButton();
        setUpLongPress();

        // shared preference for the alert dialog
        loadSavedPreferences();
        if (!firstAlertEditDiveList) {
            showAlert();
        }
    }

    private void loadSavedPreferences(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        firstAlertEditDiveList = sp.getBoolean("firstAlertEditDiveList",false);
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
        dialog.setContentView(R.layout.dialog_edit_dive_list);
        Button okButton = (Button) dialog.findViewById(R.id.buttonOkay);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences("firstAlertEditDiveList", true);
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void showAlertForHowTo(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_dive_list);
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
    public void onBackPressed(){
        final Context context = this;
        Intent intent = new Intent(context, ChooseDiver.class);
        Bundle b = new Bundle();
        b.putInt("keyDiver", diverId);
        b.putInt("keyMeet", meetId);
        b.putInt("keySpin", diverSpinnerPosition);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void addListenerOnButton(){
        final Context context = this;
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiplier != 0.0) {
                    updateJudgeScores();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Dive and Position is not valid, " +
                                    "Please Choose a Valid Combination.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseDivesFromList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void setUpLongPress(){
        final Context context = this;
        diveInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 1;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 2;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 3;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 4;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 5;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 6;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 7;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 8;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 9;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 10;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        diveInfo11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int divenumber = 11;
                Intent intent = new Intent(context, EditDiveList.class);
                Bundle b = new Bundle();
                b.putInt("keyDiver", diverId);
                b.putInt("keyMeet", meetId);
                b.putInt("keySpin", diverSpinnerPosition);
                b.putInt("keyDiveNumber", divenumber);
                b.putDouble("keyBoardType", boardType);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    private void fillDiveInfo(){
        if(diveNumber >= 1){
            updateListFilled(1);
            row1.setVisibility(View.VISIBLE);
            infoString1 = getDiveInfoFromDB(1);
            diveInfo1.setText(infoString1);
        }

        if(diveNumber >= 2){
            row2.setVisibility(View.VISIBLE);
            infoString2 = getDiveInfoFromDB(2);
            diveInfo2.setText(infoString2);
        }

        if(diveNumber >= 3){
            row3.setVisibility(View.VISIBLE);
            infoString3 = getDiveInfoFromDB(3);
            diveInfo3.setText(infoString3);
        }

        if(diveNumber >= 4){
            row4.setVisibility(View.VISIBLE);
            infoString4 = getDiveInfoFromDB(4);
            diveInfo4.setText(infoString4);
        }

        if(diveNumber >= 5){
            row5.setVisibility(View.VISIBLE);
            infoString5 = getDiveInfoFromDB(5);
            diveInfo5.setText(infoString5);
        }

        if(diveNumber >= 6){
            row6.setVisibility(View.VISIBLE);
            infoString6 = getDiveInfoFromDB(6);
            diveInfo6.setText(infoString6);
        }

        if(diveNumber == 6 && diveTotal == 6){
            updateListFilled(2);
            Toast.makeText(getApplicationContext(),
                    "All six dives have been entered, " +
                            "Click on a dive to edit it",
                    Toast.LENGTH_LONG).show();
            //diveCategory.setEnabled(false);
            //diveStyle.setEnabled(false);
            diveCategory.setVisibility(View.GONE);
            diveStyle.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            DODView.setVisibility(View.GONE);
            btnEnter.setVisibility(View.GONE);
            btnScore.setVisibility(View.VISIBLE);
            return;
        }

        if(diveNumber >= 7){
            row7.setVisibility(View.VISIBLE);
            infoString7 = getDiveInfoFromDB(7);
            diveInfo7.setText(infoString7);
        }

        if(diveNumber >= 8){
            row8.setVisibility(View.VISIBLE);
            infoString8 = getDiveInfoFromDB(8);
            diveInfo8.setText(infoString8);
        }

        if(diveNumber >= 9){
            row9.setVisibility(View.VISIBLE);
            infoString9 = getDiveInfoFromDB(9);
            diveInfo9.setText(infoString9);
        }

        if(diveNumber >= 10){
            row10.setVisibility(View.VISIBLE);
            infoString10 = getDiveInfoFromDB(10);
            diveInfo10.setText(infoString10);
        }

        if(diveNumber >= 11){
            row11.setVisibility(View.VISIBLE);
            infoString11 = getDiveInfoFromDB(11);
            diveInfo11.setText(infoString11);
        }

        if(diveNumber == 11 && diveTotal == 11){
            updateListFilled(2);
            Toast.makeText(getApplicationContext(),
                    "All eleven dives have been entered, " +
                            "Click on a dive to edit it",
                    Toast.LENGTH_LONG).show();
            //diveCategory.setEnabled(false);
            //diveStyle.setEnabled(false);
            diveCategory.setVisibility(View.GONE);
            diveStyle.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            DODView.setVisibility(View.GONE);
            btnEnter.setVisibility(View.GONE);
            btnScore.setVisibility(View.VISIBLE);
        }
    }

    private void updateListFilled(int key){
        DiveListDatabase db = new DiveListDatabase(getApplicationContext());
        db.updateListFilled(meetId, diverId, key);
    }

    private String getDiveInfoFromDB(int divenumber){
        int dash;
        String cat, number, position, dd;
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        ArrayList<String> info;
        info = db.getCatAndName(meetId, diverId, divenumber);
        if(info.size() > 0){
            cat = info.get(0);
            number = info.get(1);
            dash = number.indexOf("-");
            number = number.substring((0), (dash)).trim();
            position = info.get(2);
            dash = position.indexOf("-");
            position = position.substring((0), (dash)).trim();
            dd = info.get(3);

            return cat + " - " + number + position + " - DD: " + dd;
        }
        return null;
    }

    private void updateJudgeScores(){
        JudgeScoreDatabase db = new JudgeScoreDatabase(getApplicationContext());
        String diveCategory = null;
        switch (diveType){
            case 1:
                diveCategory = "Forward Dive";
                break;
            case 2:
                diveCategory = "Back Dive";
                break;
            case 3:
                diveCategory = "Reverse Dive";
                break;
            case 4:
                diveCategory = "Inward Dive";
                break;
            case 5:
                diveCategory = "Twist Dive";
                break;
            case 6:
                diveCategory = "Front Platform Dives";
                break;
            case 7:
                diveCategory = "Back Platform Dives";
                break;
            case 8:
                diveCategory = "Reverse Platform Dives";
                break;
            case 9:
                diveCategory = "Inward Platform Dives";
                break;
            case 10:
                diveCategory = "Twist Platform Dives";
                break;
            case 11:
                diveCategory = "Armstand Platform Dives";
                break;
        }

        String DivePosition = null;
        switch (divePosition){
            case 1:
                DivePosition = "A - Straight";
                break;
            case 2:
                DivePosition = "B - Pike";
                break;
            case 3:
                DivePosition = "C - Tuck";
                break;
            case 4:
                DivePosition = "D - Free";
                break;
        }

        TextView name = (TextView) findViewById(R.id.diveStyle);
        TextView id = (TextView) findViewById(R.id.diveId);
        String i = id.getText().toString();
        String diveTypeName = i + " - " + name.getText().toString();
        int divenum = diveNumber += 1;

        db.fillNewJudgeScores(meetId, diverId, divenum, diveCategory, diveTypeName, DivePosition,
                "", 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, multiplier);
    }

    private void DisableRadioButtons(){
        int diveId;
        double testS = 0.0;
        double testP = 0.0;
        double testT = 0.0;
        double testF = 0.0;


        switch (diveType) {
            case 1:
                ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                diveId = fdb.getDiveId(stringId);
                testS = fdb.getDOD(diveId, 1, boardType);
                testP = fdb.getDOD(diveId, 2, boardType);
                testT = fdb.getDOD(diveId, 3, boardType);
                testF = fdb.getDOD(diveId, 4, boardType);
                break;
            case 2:
                BackDatabase bdb = new BackDatabase(getApplicationContext());
                diveId = bdb.getDiveId(stringId);
                testS = bdb.getDOD(diveId, 1, boardType);
                testP = bdb.getDOD(diveId, 2, boardType);
                testT = bdb.getDOD(diveId, 3, boardType);
                testF = bdb.getDOD(diveId, 4, boardType);
                break;
            case 3:
                ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                diveId = rdb.getDiveId(stringId);
                testS = rdb.getDOD(diveId, 1, boardType);
                testP = rdb.getDOD(diveId, 2, boardType);
                testT = rdb.getDOD(diveId, 3, boardType);
                testF = rdb.getDOD(diveId, 4, boardType);
                break;
            case 4:
                InwardDatabase idb = new InwardDatabase(getApplicationContext());
                diveId = idb.getDiveId(stringId);
                testS = idb.getDOD(diveId, 1, boardType);
                testP = idb.getDOD(diveId, 2, boardType);
                testT = idb.getDOD(diveId, 3, boardType);
                testF = idb.getDOD(diveId, 4, boardType);
                break;
            case 5:
                TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                diveId = tdb.getDiveId(stringId);
                testS = tdb.getDOD(diveId, 1, boardType);
                testP = tdb.getDOD(diveId, 2, boardType);
                testT = tdb.getDOD(diveId, 3, boardType);
                testF = tdb.getDOD(diveId, 4, boardType);
                break;
            case 6:
                ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(getApplicationContext());
                diveId = fpdb.getDiveId(stringId);
                testS = fpdb.getDOD(diveId, 1, boardType);
                testP = fpdb.getDOD(diveId, 2, boardType);
                testT = fpdb.getDOD(diveId, 3, boardType);
                testF = fpdb.getDOD(diveId, 4, boardType);
                break;
            case 7:
                BackPlatformDatabase bpdb = new BackPlatformDatabase(getApplicationContext());
                diveId = bpdb.getDiveId(stringId);
                testS = bpdb.getDOD(diveId, 1, boardType);
                testP = bpdb.getDOD(diveId, 2, boardType);
                testT = bpdb.getDOD(diveId, 3, boardType);
                testF = bpdb.getDOD(diveId, 4, boardType);
                break;
            case 8:
                ReversePlatformDatabase rpdb = new ReversePlatformDatabase(getApplicationContext());
                diveId = rpdb.getDiveId(stringId);
                testS = rpdb.getDOD(diveId, 1, boardType);
                testP = rpdb.getDOD(diveId, 2, boardType);
                testT = rpdb.getDOD(diveId, 3, boardType);
                testF = rpdb.getDOD(diveId, 4, boardType);
                break;
            case 9:
                InwardPlatformDatabase ipdb = new InwardPlatformDatabase(getApplicationContext());
                diveId = ipdb.getDiveId(stringId);
                testS = ipdb.getDOD(diveId, 1, boardType);
                testP = ipdb.getDOD(diveId, 2, boardType);
                testT = ipdb.getDOD(diveId, 3, boardType);
                testF = ipdb.getDOD(diveId, 4, boardType);
                break;
            case 10:
                TwistPlatformDatabase tpdb = new TwistPlatformDatabase(getApplicationContext());
                diveId = tpdb.getDiveId(stringId);
                testS = tpdb.getDOD(diveId, 1, boardType);
                testP = tpdb.getDOD(diveId, 2, boardType);
                testT = tpdb.getDOD(diveId, 3, boardType);
                testF = tpdb.getDOD(diveId, 4, boardType);
                break;
            case 11:
                ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(getApplicationContext());
                diveId = apdb.getDiveId(stringId);
                testS = apdb.getDOD(diveId, 1, boardType);
                testP = apdb.getDOD(diveId, 2, boardType);
                testT = apdb.getDOD(diveId, 3, boardType);
                testF = apdb.getDOD(diveId, 4, boardType);
                break;
        }

        if (testS == 0.0){
            radioStraight.setEnabled(false);
            radioStraight.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioStraight.setEnabled(true);
            radioStraight.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testP == 0.0){
            radioPike.setEnabled(false);
            radioPike.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioPike.setEnabled(true);
            radioPike.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testT == 0.0){
            radioTuck.setEnabled(false);
            radioTuck.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioTuck.setEnabled(true);
            radioTuck.setTextColor(this.getResources().getColor(R.color.random_text));
        }

        if (testF == 0.0){
            radioFree.setEnabled(false);
            radioFree.setTextColor(this.getResources().getColor(R.color.static_text));
        }else {
            radioFree.setEnabled(true);
            radioFree.setTextColor(this.getResources().getColor(R.color.random_text));
        }
    }

    private void checkRadios() {
        radioStraight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(true);
                radioTuck.setChecked(false);
                divePosition = 1;
                getMultiplier();
            }
        });
        radioPike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(true);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 2;
                getMultiplier();
            }
        });
        radioTuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(false);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(true);
                divePosition = 3;
                getMultiplier();
            }
        });
        radioFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioFree.setChecked(true);
                radioPike.setChecked(false);
                radioStraight.setChecked(false);
                radioTuck.setChecked(false);
                divePosition = 4;
                getMultiplier();
            }
        });
    }

    private void getMultiplier(){
        int diveId;
        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null && divePosition != 0) {
            stringId = name.getText().toString();
            switch (diveType) {
                case 1:
                    ForwardDatabase fdb = new ForwardDatabase(getApplicationContext());
                    diveId = fdb.getDiveId(stringId);
                    multiplier = fdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 2:
                    BackDatabase bdb = new BackDatabase(getApplicationContext());
                    diveId = bdb.getDiveId(stringId);
                    multiplier = bdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 3:
                    ReverseDatabase rdb = new ReverseDatabase(getApplicationContext());
                    diveId = rdb.getDiveId(stringId);
                    multiplier = rdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 4:
                    InwardDatabase idb = new InwardDatabase(getApplicationContext());
                    diveId = idb.getDiveId(stringId);
                    multiplier = idb.getDOD(diveId, divePosition, boardType);
                    break;
                case 5:
                    TwistDatabase tdb = new TwistDatabase(getApplicationContext());
                    diveId = tdb.getDiveId(stringId);
                    multiplier = tdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 6:
                    ForwardPlatformDatabase fpdb = new ForwardPlatformDatabase(getApplicationContext());
                    diveId = fpdb.getDiveId(stringId);
                    multiplier = fpdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 7:
                    BackPlatformDatabase bpdb = new BackPlatformDatabase(getApplicationContext());
                    diveId = bpdb.getDiveId(stringId);
                    multiplier = bpdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 8:
                    ReversePlatformDatabase rpdb = new ReversePlatformDatabase(getApplicationContext());
                    diveId = rpdb.getDiveId(stringId);
                    multiplier = rpdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 9:
                    InwardPlatformDatabase ipdb = new InwardPlatformDatabase(getApplicationContext());
                    diveId = ipdb.getDiveId(stringId);
                    multiplier = ipdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 10:
                    TwistPlatformDatabase tpdb = new TwistPlatformDatabase(getApplicationContext());
                    diveId = tpdb.getDiveId(stringId);
                    multiplier = tpdb.getDOD(diveId, divePosition, boardType);
                    break;
                case 11:
                    ArmstandPlatformDatabase apdb = new ArmstandPlatformDatabase(getApplicationContext());
                    diveId = apdb.getDiveId(stringId);
                    multiplier = apdb.getDOD(diveId, divePosition, boardType);
                    break;
            }
            ddString = "Dive DD: " + multiplier;
            DODView.setText(ddString);
        } else {
            ddString = "Dive DD: ";
            DODView.setText(ddString);
        }
    }

    private void getDiveTotals(){
        SearchDiveTotals total = new SearchDiveTotals();
        diveTotal = total.doInBackground();
    }

    private void fillNameText(){
        ArrayList<String> diverInfo;
        GetDiverInfo info = new GetDiverInfo();
        diverInfo = info.doInBackground();

        String nameString = diverInfo.get(0);
        diverName.setText(nameString);

        ArrayList<String> meetInfo;
        GetMeetInfo infoMeet = new GetMeetInfo();
        meetInfo = infoMeet.doInBackground();

        String meetNameString = meetInfo.get(0);
        meetName.setText(meetNameString);
    }

    private void fillDiveNumber(){

        if(diveTotal != diveNumber) {
            diveNumberView.setText("Please Enter Dive " + (diveNumber + 1));
        } else {
            diveNumberView.setText("Dive List is Complete");
        }
    }

    private void getDiveNumber(){
        List<Integer> numbers;
        GetDiveNumber num = new GetDiveNumber();
        numbers = num.doInBackground();
        if(numbers.size() > 0) {
            diveNumber = Collections.max(numbers);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        diveType = diveCategory.getSelectedItemPosition();
        //divestyle = diveStyle.getSelectedItemPosition();

        if(diveType != newDiveType){
            loadStyleSpinnerData();
        }

        name = (TextView) findViewById(R.id.diveStyle);
        if(name != null) {
            stringId = name.getText().toString();
        }
        DisableRadioButtons();
    }

    private void getBoardType(){
        GetBoardType type = new GetBoardType();
        boardType = type.doInBackground();
    }

    private void loadCategorySpinnerData(){
        if(boardType == 1.0 || boardType == 3.0) {
            GetSpringboardDiveName dives = new GetSpringboardDiveName();
            List<String> diveName = dives.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);

            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
            dataAdapter.insert("  Choose a Dive Category", 0);
            diveCategory.setAdapter(dataAdapter);
//            diveCategory.setAdapter(
//                    new NothingSelectedSpinnerAdapter(
//                            dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this)
//            );
        } else {
            GetPlatformDiveName divesP = new GetPlatformDiveName();
            List<String> diveName = divesP.doInBackground();

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_item, diveName);

            dataAdapter.setDropDownViewResource(R.layout.spinner_layout);
            dataAdapter.insert("  Choose a Dive Category", 0);
            diveCategory.setAdapter(dataAdapter);
//            diveCategory.setAdapter(
//                    new NothingSelectedSpinnerAdapter(
//                            dataAdapter, R.layout.dive_type_spinner_row_nothing_selected, this)
//            );
        }
        if (diveCategory.getSelectedItemPosition() == 0){
            diveCategory.setSelection(1);
        }
        loadStyleSpinnerData();
    }

    private void loadStyleSpinnerData(){

        // DiveStyle Spinner
        newDiveType = diveType;
        switch (diveType) {
            // Springboard Dives
            case 1:
                if (boardType == 1) {
                    GetForwardOneNames f1Name = new GetForwardOneNames();
                    searchDives = f1Name.doInBackground();
                    break;
                } else {
                    GetForwardThreeNames f3Name = new GetForwardThreeNames();
                    searchDives = f3Name.doInBackground();
                    break;
                }
            case 2:
                if (boardType == 1) {
                    GetBackOneNames b1Name = new GetBackOneNames();
                    searchDives = b1Name.doInBackground();
                    break;
                } else {
                    GetBackThreeNames b3Name = new GetBackThreeNames();
                    searchDives = b3Name.doInBackground();
                    break;
                }
            case 3:
                if (boardType == 1) {
                    GetReverseOneNames r1Name = new GetReverseOneNames();
                    searchDives = r1Name.doInBackground();
                    break;
                } else {
                    GetReverseThreeNames r3Name = new GetReverseThreeNames();
                    searchDives = r3Name.doInBackground();
                    break;
                }
            case 4:
                if (boardType == 1) {
                    GetInwardOneNames i1Name = new GetInwardOneNames();
                    searchDives = i1Name.doInBackground();
                    break;
                } else {
                    GetInwardThreeNames i3Name = new GetInwardThreeNames();
                    searchDives = i3Name.doInBackground();
                    break;
                }
            case 5:
                if (boardType == 1) {
                    GetTwistOneNames t1Name = new GetTwistOneNames();
                    searchDives = t1Name.doInBackground();
                    break;
                } else {
                    GetTwistThreeNames t3Name = new GetTwistThreeNames();
                    searchDives = t3Name.doInBackground();
                    break;
                }
                //platform dives
            case 6:
                if (boardType == 10) {
                    GetFrontTenNames f10 = new GetFrontTenNames();
                    searchDives = f10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetFrontSevenNames f7 = new GetFrontSevenNames();
                    searchDives = f7.doInBackground();
                    break;
                } else {
                    GetFrontFiveNames f5 = new GetFrontFiveNames();
                    searchDives = f5.doInBackground();
                    break;
                }
            case 7:
                if (boardType == 10) {
                    GetBackTenNames b10 = new GetBackTenNames();
                    searchDives = b10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetBackSevenNames b7 = new GetBackSevenNames();
                    searchDives = b7.doInBackground();
                    break;
                } else {
                    GetBackFiveNames b5 = new GetBackFiveNames();
                    searchDives = b5.doInBackground();
                    break;
                }
            case 8:
                if (boardType == 10) {
                    GetReverseTenNames r10 = new GetReverseTenNames();
                    searchDives = r10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetReverseSevenNames r7 = new GetReverseSevenNames();
                    searchDives = r7.doInBackground();
                    break;
                } else {
                    GetReverseFiveNames r5 = new GetReverseFiveNames();
                    searchDives = r5.doInBackground();
                    break;
                }
            case 9:
                if (boardType == 10) {
                    GetInwardTenNames ip10 = new GetInwardTenNames();
                    searchDives = ip10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetInwardSevenNames ip7 = new GetInwardSevenNames();
                    searchDives = ip7.doInBackground();
                    break;
                } else {
                    GetInwardFiveNames ip5 = new GetInwardFiveNames();
                    searchDives = ip5.doInBackground();
                    break;
                }
            case 10:
                if (boardType == 10) {
                    GetTwistTenNames tp10 = new GetTwistTenNames();
                    searchDives = tp10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetTwistSevenNames tp7 = new GetTwistSevenNames();
                    searchDives = tp7.doInBackground();
                    break;
                } else {
                    GetTwistFiveNames tp5 = new GetTwistFiveNames();
                    searchDives = tp5.doInBackground();
                    break;
                }
            case 11:
                if (boardType == 10) {
                    GetArmstandTenNames a10 = new GetArmstandTenNames();
                    searchDives = a10.doInBackground();
                    break;
                } else if (boardType == 7.5) {
                    GetArmstandSevenNames a7 = new GetArmstandSevenNames();
                    searchDives = a7.doInBackground();
                    break;
                } else {
                    GetArmstandFiveNames a5 = new GetArmstandFiveNames();
                    searchDives = a5.doInBackground();
                    break;
                }
        }

        diveStyle.setAdapter(new SpinnerDiveStyleCustomBaseAdpater(this, searchDives));

    }

    private void setUpView(){
        diveNumberView = (TextView)findViewById(R.id.DiveNumberView);
        diverName = (TextView)findViewById(R.id.divername);
        meetName = (TextView)findViewById(R.id.meetname);
        DODView = (TextView)findViewById(R.id.DODView);
        diveInfo1 = (TextView)findViewById(R.id.diveInfo1);
        diveInfo2 = (TextView)findViewById(R.id.diveInfo2);
        diveInfo3 = (TextView)findViewById(R.id.diveInfo3);
        diveInfo4 = (TextView)findViewById(R.id.diveInfo4);
        diveInfo5 = (TextView)findViewById(R.id.diveInfo5);
        diveInfo6 = (TextView)findViewById(R.id.diveInfo6);
        diveInfo7 = (TextView)findViewById(R.id.diveInfo7);
        diveInfo8 = (TextView)findViewById(R.id.diveInfo8);
        diveInfo9 = (TextView)findViewById(R.id.diveInfo9);
        diveInfo10 = (TextView)findViewById(R.id.diveInfo10);
        diveInfo11 = (TextView)findViewById(R.id.diveInfo11);
        view1 = findViewById(R.id.View);
        textView1 = (TextView)findViewById(R.id.textView);
        layout1 = (LinearLayout)findViewById(R.id.layout1);
        layout2 = (LinearLayout)findViewById(R.id.layout2);
        row1 = (TableRow)findViewById(R.id.tableRow1);
        row2 = (TableRow)findViewById(R.id.tableRow2);
        row3 = (TableRow)findViewById(R.id.tableRow3);
        row4 = (TableRow)findViewById(R.id.tableRow4);
        row5 = (TableRow)findViewById(R.id.tableRow5);
        row6 = (TableRow)findViewById(R.id.tableRow6);
        row7 = (TableRow)findViewById(R.id.tableRow7);
        row8 = (TableRow)findViewById(R.id.tableRow8);
        row9 = (TableRow)findViewById(R.id.tableRow9);
        row10 = (TableRow)findViewById(R.id.tableRow10);
        row11 = (TableRow)findViewById(R.id.tableRow11);
        diveCategory = (Spinner)findViewById(R.id.DiveCategory);
        diveStyle = (Spinner)findViewById(R.id.DiveStyle);
        btnEnter = (Button)findViewById(R.id.buttonEnter);
        btnScore = (Button)findViewById(R.id.buttonScore);
        radioStraight = (RadioButton)findViewById(R.id.radioStraight);
        radioPike = (RadioButton)findViewById(R.id.radioPike);
        radioTuck = (RadioButton)findViewById(R.id.radioTuck);
        radioFree = (RadioButton)findViewById(R.id.radioFree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.enter_dive_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_how_to) {
            showAlertForHowTo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetBoardType extends AsyncTask<Double, Object, Object> {
        TypeDatabase db = new TypeDatabase(getApplicationContext());
        Double type;

        @Override
        protected Double doInBackground(Double... params) {
            return type = db.getType(meetId, diverId);
        }
    }

    private class GetSpringboardDiveName extends AsyncTask<List<String>, Object, Object>{
        DivesDatabase db = new DivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getDiveNames();
        }
    }

    private class GetPlatformDiveName extends AsyncTask<List<String>, Object, Object>{
        PlatformDivesDatabase db = new PlatformDivesDatabase(getApplicationContext());
        List<String> dives;

        @SafeVarargs
        @Override
        protected final List<String> doInBackground(List<String>... params) {
            return dives = db.getPlatformDiveNames();
        }
    }

    // SpringBoard Dives
    private class GetForwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardDatabase db = new ForwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardOneNames();
        }
    }

    private class GetForwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardDatabase db = new ForwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getForwardThreeNames();
        }
    }

    private class GetBackOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackOneNames();
        }
    }

    private class GetBackThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackDatabase db = new BackDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackThreeNames();
        }
    }

    private class GetReverseOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseOneNames();
        }
    }

    private class GetReverseThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReverseDatabase db = new ReverseDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReverseThreeNames();
        }
    }

    private class GetInwardOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardOneNames();
        }
    }

    private class GetInwardThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardDatabase db = new InwardDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardThreeNames();
        }
    }

    private class GetTwistOneNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistOneNames();
        }
    }

    private class GetTwistThreeNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistDatabase db = new TwistDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistThreeNames();
        }
    }

    // Platform dives
    private class GetFrontTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformTenNames();
        }
    }

    private class GetFrontSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformSevenFiveNames();
        }
    }

    private class GetFrontFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ForwardPlatformDatabase db = new ForwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getFrontPlatformFiveNames();
        }
    }

    private class GetBackTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformTenNames();
        }
    }

    private class GetBackSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformSevenFiveNames();
        }
    }
    private class GetBackFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        BackPlatformDatabase db = new BackPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getBackPlatformFiveNames();
        }
    }

    private class GetReverseTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformTenNames();
        }
    }

    private class GetReverseSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformSevenFiveNames();
        }
    }

    private class GetReverseFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ReversePlatformDatabase db = new ReversePlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getReversePlatformFiveNames();
        }
    }

    private class GetInwardTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformTenNames();
        }
    }

    private class GetInwardSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformSevenFiveNames();
        }
    }

    private class GetInwardFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        InwardPlatformDatabase db = new InwardPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getInwardPlatformFiveNames();
        }
    }

    private class GetTwistTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformTenNames();
        }
    }

    private class GetTwistSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformSevenFiveNames();
        }
    }

    private class GetTwistFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        TwistPlatformDatabase db = new TwistPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getTwistPlatformFiveNames();
        }
    }

    private class GetArmstandTenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandTenNames();
        }
    }

    private class GetArmstandSevenNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandSevenFiveNames();
        }
    }

    private class GetArmstandFiveNames extends AsyncTask<ArrayList<DiveStyleSpinner>, Object, Object>{
        ArmstandPlatformDatabase db = new ArmstandPlatformDatabase(getApplicationContext());
        ArrayList<DiveStyleSpinner> names;

        @SafeVarargs
        @Override
        protected final ArrayList<DiveStyleSpinner> doInBackground(ArrayList<DiveStyleSpinner>... params) {
            return names = db.getArmstandFiveNames();
        }
    }

    private class SearchDiveTotals extends AsyncTask<Integer, Object, Object> {
        DiveTotalDatabase db = new DiveTotalDatabase(getApplicationContext());

        @Override
        protected Integer doInBackground(Integer... params) {
            return db.searchTotals(meetId, diverId);
        }
    }

    private class GetDiverInfo extends AsyncTask<ArrayList<String>, Object, Object>{
        DiverDatabase db = new DiverDatabase(getApplicationContext());
        ArrayList<String> diverinfo;

        @SafeVarargs
        @Override
        protected final ArrayList<String> doInBackground(ArrayList<String>... params) {
            return diverinfo = db.getDiverInfo(diverId);
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

    private class GetDiveNumber extends AsyncTask<Integer, Object, Object>{
        JudgeScoreDatabase db =  new JudgeScoreDatabase(getApplicationContext());
        List<Integer> number;

        @Override
        protected List<Integer> doInBackground(Integer... params) {
            return number = db.getDiveNumbers(meetId, diverId);
        }
    }
}
