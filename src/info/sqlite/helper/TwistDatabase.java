package info.sqlite.helper;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import info.Helpers.DiveStyleSpinner;

public class TwistDatabase extends DatabaseHelper {

    public TwistDatabase(Context context) { super(context); }

    public ArrayList<DiveStyleSpinner> getTwistOneNames(){
        ArrayList<DiveStyleSpinner> diveNames = new ArrayList<>();
        DiveStyleSpinner r;
        String selectQuery = "SELECT id, " + DIVE_NAME + " FROM " + TABLE_TWIST
                + " WHERE one_meter= 1";

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
//                TwistDB t = new TwistDB();
//                diveNames.add(t.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
//            }while (c.moveToNext());
//        }
        c.close();
        db.close();
        return diveNames;
    }

    public ArrayList<DiveStyleSpinner> getTwistThreeNames(){
        ArrayList<DiveStyleSpinner> diveNames = new ArrayList<>();
        DiveStyleSpinner r;
        String selectQuery = "SELECT id, " + DIVE_NAME + " FROM " + TABLE_TWIST
                + " WHERE three_meter= 1";

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
//                TwistDB t = new TwistDB();
//                diveNames.add(t.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
//            }while (c.moveToNext());
//        }
        c.close();
        db.close();
        return diveNames;
    }

    //-------------gets the id number for the dive----------------------------------------------//
    public int getDiveId(String stringID){
        int id = 0;
        String selectQuery = "SELECT id FROM twist_dives "
                + "WHERE name= '" + stringID + "'";
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
        if(boardtype == 1) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT oneS FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT oneP FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT oneT FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT oneF FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
            }
        } else {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT threeS FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT threeP FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT threeT FROM twist_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT threeF FROM twist_dives "
                            + "WHERE id= " + diveid;
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
