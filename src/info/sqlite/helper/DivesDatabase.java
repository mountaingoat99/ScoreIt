package info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.sqlite.model.DivesDB;

public class DivesDatabase extends DatabaseHelper {

    public DivesDatabase(Context context) {
        super(context);
    }

    // -------------getting all dive names for spinner--------------------------------------//
    public List<String> getDiveNames(){
        List<String> diveNames = new ArrayList<>();

        String selectQuery = "SELECT " + DIVE_NAME + " FROM " + TABLE_DIVES;
        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                DivesDB d = new DivesDB();
                diveNames.add(d.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
            }while (c.moveToNext());
        }
        c.close();
        return diveNames;
    }

    // insert dives in the spinners
    /*public void insertToTables(String label, String tableName){
        String dataBaseName = null;
        switch(tableName) {
            case "forward": dataBaseName = TABLE_FORWARD;
                break;
            case "back": dataBaseName = TABLE_BACK;
                break;
            case "inward": dataBaseName = TABLE_INWARD;
                break;
            case "reverse": dataBaseName = TABLE_REVERSE;
                break;
            case "twist": dataBaseName = TABLE_TWIST;
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, label);

        // Inserting a row
        db.insert(dataBaseName, null, values);
        db.close();
    }*/
}
