package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import info.sqlite.model.DiverTotalDB;

public class DiveTotalDatabase extends DatabaseHelper {

    public DiveTotalDatabase(Context context){
        super(context);
    }

    //----------------fill dive total------------------------------------------//
    public void fillDiveTotal(int meetId, int diverId, int diveTotal){
        SQLiteDatabase db = this.getWritableDatabase();

        DiverTotalDB divetotal = new DiverTotalDB(diveTotal);
        createDiveTotal(meetId, diverId, divetotal, db);
        db.close();
    }

    //-----------------insert a row into the diver total table-----------------//
    public void createDiveTotal(int meetId, int diverId,
                                DiverTotalDB diveTotal, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(getDiveCount(), diveTotal.get_diveTotal());
        values.put(getMeetId(), meetId);
        values.put(getDiverId(), diverId);

        db.insert(getTableDiveTotals(), null, values);
    }

    //-----------------check table to see if a diveTotal exists yet-------------//
    public boolean checkTotal(int meetid, int diverid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * from dive_total "
                            + " WHERE meet_id = " + meetid
                            + " AND diver_id = " + diverid;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            c.close();
            db.close();
            return false;
        }
        c.close();
        db.close();
        return true;
    }

    //----------------looks for a diver and meet associated with total-----------//
    public int searchTotals(int meetid, int diverid){
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT dive_count from dive_total "
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                id = c.getInt(0);   //getKeyId();
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return id;
    }
}
