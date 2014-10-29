package info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.sqlite.model.DivesDB;

public class PlatformDivesDatabase extends DatabaseHelper {

    public PlatformDivesDatabase(Context context) {
        super(context);
    }

    // -------------getting all dive names for spinner--------------------------------------//
    public List<String> getPlatformDiveNames(){
        List<String> diveNames = new ArrayList<>();

        String selectQuery = "SELECT " + DIVE_NAME + " FROM " + TABLE_PLATFORM_DIVES;
        Log.e(getLog(), selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                DivesDB d = new DivesDB();
                diveNames.add("  " + d.setDiveName(c.getString(c.getColumnIndex(DIVE_NAME))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return diveNames;
    }
}
