package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.sqlite.model.QuickScoreDB;

public class QuickScoreDatabase extends DatabaseHelper {
    public String emptyScoreName;

    public QuickScoreDatabase(Context context) { super(context); }

    public int createEmptyQuickScore(int sheetNum){
        emptyScoreName = "Enter Sheet " + sheetNum;
        int id;
        SQLiteDatabase db = this.getWritableDatabase();
        QuickScoreDB quickscore = new QuickScoreDB(emptyScoreName, 0.0, 0.0, 0.0, 0.0, 0.0,
                                        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        id = createQuickScore(quickscore, db);
        db.close();

        return id;
    }

    public int createQuickScore(QuickScoreDB quickscore, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(NAME_MEET, quickscore.getNameMeet());
        values.put(DIVE_1, quickscore.getDive1());
        values.put(DIVE_2, quickscore.getDive2());
        values.put(DIVE_3, quickscore.getDive3());
        values.put(DIVE_4, quickscore.getDive4());
        values.put(DIVE_5, quickscore.getDive5());
        values.put(DIVE_6, quickscore.getDive6());
        values.put(DIVE_7, quickscore.getDive7());
        values.put(DIVE_8, quickscore.getDive8());
        values.put(DIVE_9, quickscore.getDive9());
        values.put(DIVE_10, quickscore.getDive10());
        values.put(DIVE_11, quickscore.getDive11());
        values.put(TOTAL_SCORE, quickscore.getTotalScore());

        db.insert(TABLE_QUICK_SCORE, null, values);

        return getId(emptyScoreName);
    }

    // gets all the fields from each record, is called with the parameter from the autofill list
    // make sure to trim the string on the spinner value
    public ArrayList<String> getQuickScoreMemo(int id){
        ArrayList<String> memo = new ArrayList<>();
        String selectQuery = "SELECT name_meet ,dive_1, dive_2, "
                + "dive_3, dive_4, dive_5, dive_6, dive_7, dive_8, "
                + "dive_9, dive_10, dive_11, total_score "
                + " FROM " + getTableQuickScore()
                + " WHERE id = " + id;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                memo.add(c.getString(0));
                memo.add(c.getString(1));
                memo.add(c.getString(2));
                memo.add(c.getString(3));
                memo.add(c.getString(4));
                memo.add(c.getString(5));
                memo.add(c.getString(6));
                memo.add(c.getString(7));
                memo.add(c.getString(8));
                memo.add(c.getString(9));
                memo.add(c.getString(10));
                memo.add(c.getString(11));
                memo.add(c.getString(12));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return memo;
    }

    // gets the memo names for the autofill lists
    public List<String> getQuickScoreMemoName(){
        List<String> memo = new ArrayList<>();
        String selectQuery = "SELECT " + getNameMeet() + " FROM "
                + getTableQuickScore();
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{
                QuickScoreDB q = new QuickScoreDB();
                memo.add("  " + q.setNameMeet(c.getString(c.getColumnIndex(getNameMeet()))));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return memo;
    }

    // updates the QuickScore
    public void updateQuickScore(String memoName, String score1, String score2, String score3, String score4,
                                 String score5, String score6, String score7, String score8,
                                 String score9, String score10, String score11, String totalScore){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery  = "UPDATE " + getTableQuickScore() + " SET "
                + getDive1() + "=" + score1 + ", " + getDive2() + "=" + score2 + ", "
                + getDive3() + "=" + score3 + ", " + getDive4() + "=" + score4 + ", "
                + getDive5() + "=" + score5 + ", " + getDive6() + "=" + score6 + ", "
                + getDive7() + "=" + score7 + ", " + getDive8() + "=" + score8 + ", "
                + getDive9() + "=" + score9 + ", " + getDive10() + "=" + score10 + ", "
                + getDive11() + "=" + score11 + ", " + getTotalScore() + "=" + totalScore
                + " WHERE " + getNameMeet() + "=" + memoName;

        db.execSQL(selectQuery);
        db.close();
    }

    public void UpdateAQuickScore(int id, int columnIndex, String value){
        String column  = null;
        switch (columnIndex){
            case 0:
                column = "name_meet";
                break;
            case 1:
                column = "dive_1";
                break;
            case 2:
                column = "dive_2";
                break;
            case 3:
                column = "dive_3";
                break;
            case 4:
                column = "dive_4";
                break;
            case 5:
                column = "dive_5";
                break;
            case 6:
                column = "dive_6";
                break;
            case 7:
                column = "dive_7";
                break;
            case 8:
                column = "dive_8";
                break;
            case 9:
                column = "dive_9";
                break;
            case 10:
                column = "dive_10";
                break;
            case 11:
                column = "dive_11";
                break;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = " UPDATE " + getTableQuickScore() + " SET "
                + column + "='" + value + "'"
                + " WHERE id= " + id;
        Log.e(getLog(), selectQuery);
        db.execSQL(selectQuery);
        db.close();
    }

    //--------------get the sheet id from the spinner in the QuickScore Class-----------------//
    public int getId(String stringId){
        int id = 0;
        String selectQuery = "SELECT id FROM " + getTableQuickScore()
                + " WHERE name_meet= '" + stringId + "'";
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

    public void deleteSheet(int id){
        String selectQuery = "DELETE FROM " + getTableQuickScore()
                + " WHERE id = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }
}
