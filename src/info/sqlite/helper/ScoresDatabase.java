package info.sqlite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import info.sqlite.model.ScoresDB;

public class ScoresDatabase extends DatabaseHelper {

    public ScoresDatabase(Context context) { super(context); }

    //----------------------get the scores for the spinners in the dives page----------------------------//
    public List<String> getScores(){
        List<String> scores = new ArrayList<>();
        String selectQuery = " SELECT " + DIGITS + " FROM " + TABLE_SCORES;

        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                ScoresDB s = new ScoresDB();
                scores.add(s.setDigits(c.getString(c.getColumnIndex(DIGITS))));
            }while (c.moveToNext());
        }
        c.close();
        return scores;
    }

    //--------------------gets the id number for the scores for the dives page---------------------------//
    public int getScoreID(String stringID){
        int id = 0;
        String selectQuery = "SELECT id FROM scores "
                + "WHERE digits= '" + stringID + "'";
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
        return id;
    }
}
