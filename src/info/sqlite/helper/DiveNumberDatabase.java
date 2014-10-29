package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import info.sqlite.model.DiveNumberDB;

public class DiveNumberDatabase extends DatabaseHelper{

    public DiveNumberDatabase(Context context) { super(context); }

    public void createNewDiveNumber(int meetId, int diverId, double type){
        SQLiteDatabase db = this.getWritableDatabase();
        DiveNumberDB diveNumber = new DiveNumberDB(meetId, diverId, 0, type);

        createDiveNumber(diveNumber, db);
        db.close();
    }

    public Boolean checkNumber(int meetid, int diverid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + getTableDiveNumber()
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

    void createDiveNumber(DiveNumberDB numbers, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MEET_ID, numbers.get_meetId());
        values.put(DIVER_ID, numbers.get_diverId());
        values.put(DIVE_NUMBER, numbers.get_diveNumber());
        values.put(DIVE_TYPE, numbers.get_dive_type() );

        db.insert(TABLE_DIVE_NUMBER, null, values);
    }

    public void updateDiveNumber(int meetid, int diverid, int divenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableDiveNumber() + " SET "
                + getDiveNumber() + "=" + divenumber
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;

        db.execSQL(selectQuery);
        db.close();
    }

    public int getDiveNumber(int meetid, int diverid){
        int number = 0;
        String selectQuery = "SELECT dive_number FROM " + getTableDiveNumber()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                number = c.getInt(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return number;
    }
}
