package info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TypeDatabase extends DatabaseHelper {

    public TypeDatabase(Context context) { super(context);}

    //--------------------------write a new record to the table------------------------------------------//
    public void createType(int meetid, int diverid, double divetype){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "INSERT INTO dive_type "
                + "(meet_id, diver_id, type) VALUES ("
                + meetid + ", " + diverid + ", " + divetype + " )";
        db.execSQL(selectQuery);
        db.close();
    }

    //-----------------check table to see if a diveType exists yet-------------//
    public boolean checkType(int meetid, int diverid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * from dive_type "
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

    //----------------looks for a dive type associated with total-----------//
    public int searchTypes(int meetid, int diverid){
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT type from dive_type "
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
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

    //--------------get the type---------------------------------------------------------------------//
    public double getType(int meetid, int diverid){
        double type = 0.0;
        String selectQuery = "SELECT type FROM dive_type"
                + " WHERE meet_id= " + meetid + " AND "
                + " diver_id= " + diverid;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                type = c.getDouble(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return type;
    }


}
