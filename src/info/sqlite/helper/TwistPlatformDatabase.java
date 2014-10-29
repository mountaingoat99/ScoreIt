package info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import info.Helpers.DiveStyleSpinner;


public class TwistPlatformDatabase extends DatabaseHelper {

    public TwistPlatformDatabase(Context context) {
        super(context); }

    public ArrayList<DiveStyleSpinner> getTwistPlatformTenNames(){
        ArrayList<DiveStyleSpinner> diveNames = new ArrayList<>();
        DiveStyleSpinner r;
        String selectQuery = "SELECT id, " + DIVE_NAME + " FROM " + TABLE_PLATFORM_TWIST
                + " WHERE ten_meter= 1";

        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new DiveStyleSpinner();
            r.setId(c.getString(0));
            r.setDiveStyle(c.getString(1));
            diveNames.add(r);
        }
//        if (c.moveToFirst()){
//            do{
//                BackDB b = new BackDB();
//                diveNames.add(b.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
//            }while (c.moveToNext());
//        }
        c.close();
        db.close();
        return diveNames;
    }

    public ArrayList<DiveStyleSpinner> getTwistPlatformSevenFiveNames(){
        ArrayList<DiveStyleSpinner> diveNames = new ArrayList<>();
        DiveStyleSpinner r;
        String selectQuery = "SELECT id, " + DIVE_NAME + " FROM " + TABLE_PLATFORM_TWIST
                + " WHERE seven_five_meter= 1";

        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new DiveStyleSpinner();
            r.setId(c.getString(0));
            r.setDiveStyle(c.getString(1));
            diveNames.add(r);
        }
//        if (c.moveToFirst()){
//            do{
//                BackDB b = new BackDB();
//                diveNames.add(b.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
//            }while (c.moveToNext());
//        }
        c.close();
        db.close();
        return diveNames;
    }

    public ArrayList<DiveStyleSpinner> getTwistPlatformFiveNames(){
        ArrayList<DiveStyleSpinner> diveNames = new ArrayList<>();
        DiveStyleSpinner r;
        String selectQuery = "SELECT id," + DIVE_NAME + " FROM " + TABLE_PLATFORM_TWIST
                + " WHERE five_meter= 1";

        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new DiveStyleSpinner();
            r.setId(c.getString(0));
            r.setDiveStyle(c.getString(1));
            diveNames.add(r);
        }
//        if (c.moveToFirst()){
//            do{
//                BackDB b = new BackDB();
//                diveNames.add(b.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
//            }while (c.moveToNext());
//        }
        c.close();
        db.close();
        return diveNames;
    }

    //-------------gets the id number for the dive----------------------------------------------//
    public int getDiveId(String stringID){
        int id = 0;
        String selectQuery = "SELECT id FROM " + TABLE_PLATFORM_TWIST
                + " WHERE name= '" + stringID + "'";
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                id = c.getInt(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return id;
    }

    //----------------gets the DOD for the dive type-------------------------------------------//
    public double getDOD(int diveid, int diveposition, double boardtype){
        double dod = 0.0;
        String selectQuery = null;
        if(boardtype == 10.0) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT tenS FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT tenP FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT tenT FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT tenF FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        if (boardtype == 7.5) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT seven_fiveS FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT seven_fiveP FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT seven_fiveT FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT seven_fiveF FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        if (boardtype == 5.0){
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT fiveS FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT fiveP FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT fiveT FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT fiveF FROM " + TABLE_PLATFORM_TWIST
                            + " WHERE id= " + diveid;
                    break;
            }
        }
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                dod = c.getDouble(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return  dod;
    }
}
