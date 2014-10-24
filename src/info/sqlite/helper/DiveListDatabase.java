package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import info.sqlite.model.DiveListDB;

public class DiveListDatabase extends DatabaseHelper {

    public DiveListDatabase(Context context) {super(context);}

    public void createNewDiveList(int meetid, int diverid, int listfilled, int yeslist){
        SQLiteDatabase db = this.getWritableDatabase();
        DiveListDB diveList = new DiveListDB(meetid, diverid, listfilled, yeslist);

        createDiveList(diveList, db);
        db.close();
    }

    public void createDiveList(DiveListDB list, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MEET_ID, list.getMeetId());
        values.put(DIVER_ID, list.getDiverId());
        values.put(LIST_FILLED, list.getListFilled());
        values.put(NO_LIST, list.getNoList());

        db.insert(TABLE_DIVE_LIST, null, values);
    }

    public Boolean checkList(int meetid, int diverid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + getTableDiveList()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
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

    public Boolean checkForNoList(int meetid, int diverid){
        int number = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT no_list FROM " + getTableDiveList()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);

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

        if(number == 0){
            return false;
        }else {
            return true;
        }
    }

    public void setNoList(int meetid, int diverid){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE dive_list set no_list = 1"
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
        db.execSQL(selectQuery);
    }

    public void updateListFilled(int meetid, int diverid, int key){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE dive_list set list_filled ='" + key + "'"
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
        db.execSQL(selectQuery);
    }

    public int isListFinished(int meetid, int diverid){
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT list_filled from dive_list "
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
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
