package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import info.sqlite.model.JudgeScoresDB;

public class JudgeScoreDatabase extends DatabaseHelper {

    public JudgeScoreDatabase(Context context) { super(context); }

    //--------------------gets the scores info for the change diver score page----------------------//
    public ArrayList<Double> getScoresList(int meetid, int diverid, int divenumber){
        ArrayList<Double> scoreInfo = new ArrayList<>();
        String selectQuery = "SELECT score_1, score_2, score_3, score_4, "
                + "score_5, score_6, score_7 FROM " + getTableJudgeScores()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                scoreInfo.add(c.getDouble(0));
                scoreInfo.add(c.getDouble(1));
                scoreInfo.add(c.getDouble(2));
                scoreInfo.add(c.getDouble(3));
                scoreInfo.add(c.getDouble(4));
                scoreInfo.add(c.getDouble(5));
                scoreInfo.add(c.getDouble(6));
            }while(c.moveToNext());
        }
        c.close();
        return scoreInfo;
    }

    //----------------checks the db to see if a judgeScore is attached to a meet and a diver yet----------//
    public Boolean checkJudgesScores(int meetid, int diverid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + getTableJudgeScores()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    //------------------create an empty score row attached to a meet and diver----------------------------//
    public void createEmptyJudgeScore(int meetid, int diverid){
        SQLiteDatabase db = this.getWritableDatabase();
        JudgeScoresDB scores = new JudgeScoresDB(meetid, diverid, 1, 0.0, 0.0,
                                                0.0, 0.0, 0.0, 0.0, 0.0);
        createJudgeScores(scores, db);
    }

    //----------create a new row--------------------------------------------------------------------------//
    public void createJudgeScores(JudgeScoresDB scores, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MEET_ID, scores.getMeetId());
        values.put(DIVER_ID, scores.getDiverId());
        values.put(DIVE_NUMBER, scores.getDiveNumber());
        values.put(SCORE_1, scores.getScore1());
        values.put(SCORE_2, scores.getScore2());
        values.put(SCORE_3, scores.getScore3());
        values.put(SCORE_4, scores.getScore4());
        values.put(SCORE_5, scores.getScore5());
        values.put(SCORE_6, scores.getScore6());
        values.put(SCORE_7, scores.getScore7());

        db.insert(TABLE_JUDGE_SCORES, null, values);
    }

    //-------------------updates a record in the database with a score------------------------------------//
    public void writeJudgeScore(int meetid, int diverid, int divenumber, double score1, double score2,
                                double score3, double score4, double score5, double score6, double score7){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getDiveNumber() + "=" + divenumber + ", " + getScore1() + "="
                + score1 + "' " + getScore2() + "=" + score2 + ", "
                + getScore3() + "=" + score3 + ", " + getScore4() + "=" + score4 + ", "
                + getScore5() + "=" + score5 + "' " + getScore6() + "=" + score6 + ", "
                + getScore7() + "=" + score7 + " WHERE meet_id= "
                + meetid + " AND diver_id= " + diverid;

        db.execSQL(selectQuery);
    }
}
