package info.sqlite.helper;

import info.sqlite.model.DiverNameDB;
import info.sqlite.model.ForwardDB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ForwardDatabase extends DatabaseHelper{

    public ForwardDatabase(Context context) { super(context); }

    public List<String> getForwardOneNames(){
        List<String> diveNames = new ArrayList<>();

        String selectQuery = "SELECT " + DIVE_NAME + " FROM " + TABLE_FORWARD
                + " WHERE one_meter= 1";

        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                ForwardDB f = new ForwardDB();
                diveNames.add(f.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return diveNames;
    }

    public List<String> getForwardThreeNames(){
        List<String> diveNames = new ArrayList<>();

        String selectQuery = "SELECT " + DIVE_NAME + " FROM " + TABLE_FORWARD
                + " WHERE three_meter= 1";

        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                ForwardDB f = new ForwardDB();
                diveNames.add(f.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return diveNames;
    }

    //-------------gets the id number for the dive----------------------------------------------//
    public int getDiveId(String stringID){
        int id = 0;
        String selectQuery = "SELECT id FROM forward_dives "
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
    public double getDOD(int diveid, int diveposition, int boardtype){
        double dod = 0.0;
        String selectQuery = null;
        if(boardtype == 1) {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT oneS FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT oneP FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT oneT FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT oneF FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
            }
        } else {
            switch (diveposition) {
                case 1:
                    selectQuery = "SELECT threeS FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 2:
                    selectQuery = "SELECT threeP FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 3:
                    selectQuery = "SELECT threeT FROM forward_dives "
                            + "WHERE id= " + diveid;
                    break;
                case 4:
                    selectQuery = "SELECT threeF FROM forward_dives "
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
