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
        String selectQuery = "SELECT score_1, score_2, score_3, score_4,"
                + " score_5, score_6, score_7 FROM judge_scores"
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
        db.close();
        return scoreInfo;
    }

    public double getMultiplier(int meetid, int diverid, int divenumber){
        double multiplier = 0;
        String selectQuery = "SELECT " + MULTIPLIER + " FROM " + TABLE_JUDGE_SCORES
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                multiplier = c.getDouble(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        db.close();
        return multiplier;
    }

    //------------------checks to see if a dive is failed----------------------------------------------//
    public Boolean checkFailed(int meetid, int diverid, int divenumber){
        String failed = null;
        String selectQuery = "SELECT failed FROM " + TABLE_JUDGE_SCORES
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                failed = c.getString(0);
            }while (c.moveToNext());
        }
        if(failed == null){
            db.close();
            return false;
        }
        if (failed.equals("F")) {
            c.close();
            db.close();
            return true;
        }else{
            c.close();
            db.close();
            return false;
        }
    }

    //------------------checks to see if a dive result exists ----------------------------------------------//
    public Boolean checkJudgeScoreResult(int meetid, int diverid){
        String selectQuery = "SELECT * FROM " + TABLE_JUDGE_SCORES
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
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

    //-----------------get the diveNumber from the spinner---------------------------------------------//
    public List<Integer> getDiveNumbers(int meetid, int diverid){
        List<Integer> diveNumbers = new ArrayList<>();
        String selectQuery = "SELECT " + getDiveNumber() + " FROM " + getTableJudgeScores()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()){
            do{
                diveNumbers.add(c.getInt(c.getColumnIndex(DIVE_NUMBER)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return diveNumbers;
    }

    //------------------gets the score DiveCategory and DiveTypeName-----------------------------------//
    public ArrayList<String> getCatAndName(int meetid, int diverid, int divenumber){
        ArrayList<String> diveNames = new ArrayList<>();
        String selectQuery = "SELECT " + DIVE_CATEGORY + ", "
                + DIVE_TYPE_NAME + ", " + DIVE_POSITION + ", " + MULTIPLIER
                + " FROM " + TABLE_JUDGE_SCORES
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;
        Log.e(getLog(), selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            do{
                JudgeScoresDB b = new JudgeScoresDB();
                diveNames.add(b.setDiveCategory(c.getString(c.getColumnIndex(DIVE_CATEGORY))));
                diveNames.add(b.setDiveTypeName(c.getString(c.getColumnIndex(DIVE_TYPE_NAME))));
                diveNames.add(b.setDiveTypeName(c.getString(c.getColumnIndex(DIVE_POSITION))));
                diveNames.add(c.getString(c.getColumnIndex(MULTIPLIER)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return diveNames;
    }

    //----------------checks the db to see if a judgeScore is attached to a meet and a diver yet----------//
    public Boolean checkJudgesScores(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + getTableJudgeScores();
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

    //------------------create an empty score row attached to a meet and diver----------------------------//
    public void createEmptyJudgeScore(int meetid, int diverid){
        SQLiteDatabase db = this.getWritableDatabase();
        JudgeScoresDB scores = new JudgeScoresDB(meetid, diverid, 1, "", "", "", "", 0.0, 0.0, 0.0,
                                                0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        createJudgeScores(scores, db);
    }

    //------------------fills a new record----------------------------------------------------------------//
    public void fillNewJudgeScores(int meetid, int diverid, int divenumber, String diveCategory, String diveTypeName,
                                   String divePosition, String failed, double totalScore, double score1, double score2,
                                   double score3, double score4, double score5, double score6, double score7, double multiplier){

        SQLiteDatabase db = this.getWritableDatabase();
        JudgeScoresDB scores = new JudgeScoresDB(meetid, diverid, divenumber, diveCategory, diveTypeName, divePosition,
                failed, totalScore, score1, score2, score3, score4, score5, score6, score7, multiplier);

        createJudgeScores(scores, db);
        db.close();
    }

    //----------create a new row--------------------------------------------------------------------------//
    public void createJudgeScores(JudgeScoresDB scores, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(MEET_ID, scores.getMeetId());
        values.put(DIVER_ID, scores.getDiverId());
        values.put(DIVE_NUMBER, scores.getDiveNumber());
        values.put(DIVE_CATEGORY, scores.getDiveCategory());
        values.put(DIVE_TYPE_NAME, scores.getDiveTypeName());
        values.put(DIVE_POSITION, scores.getDivePosition());
        values.put(FAILED_DIVE, scores.getFailed());
        values.put(TOTAL_SCORE, scores.getTotalScore());
        values.put(SCORE_1, scores.getScore1());
        values.put(SCORE_2, scores.getScore2());
        values.put(SCORE_3, scores.getScore3());
        values.put(SCORE_4, scores.getScore4());
        values.put(SCORE_5, scores.getScore5());
        values.put(SCORE_6, scores.getScore6());
        values.put(SCORE_7, scores.getScore7());
        values.put(MULTIPLIER, scores.getMultiplier());

        db.insert(TABLE_JUDGE_SCORES, null, values);
    }

    //-------------------updates a record in the database with a score------------------------------------//
    public void writeJudgeScore(int meetid, int diverid, int divenumber, String diveCategory, String diveTypeName,
                                String divePosition, String failed, double score1, double score2,
                                double score3, double score4, double score5, double score6, double score7, double multipler){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getDiveNumber() + "=" + divenumber + ", "
                + getDiveCategory() + "='" + diveCategory + "', "
                + getDiveTypeName() + "='" + diveTypeName + "', "
                + getDivePosition() + "='" + divePosition + "', "
                + getFailedDive() + "='" + failed + "', "
                + getScore1() + "=" + score1 + ", " + getScore2() + "=" + score2 + ", "
                + getScore3() + "=" + score3 + ", " + getScore4() + "=" + score4 + ", "
                + getScore5() + "=" + score5 + ", " + getScore6() + "=" + score6 + ", "
                + getScore7() + "=" + score7 + ", " + getMultiplier() + "=" + multipler
                + " WHERE meet_id= " + meetid + " AND diver_id= " + diverid;

        db.execSQL(selectQuery);
        db.close();
    }

    public void updateJudgeScoreTypes(int meetid, int diverid, String diveCategory, String diveTypeName, String divePosition,
                                      double multiplier, int divenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getDiveCategory() + "='" + diveCategory + "', "
                + getDiveTypeName() + "='" + diveTypeName + "', "
                + getDivePosition() + "='" + divePosition + "', "
                + getMultiplier() + "=" + multiplier
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        db.execSQL(selectQuery);
        db.close();
    }

    //-------------------updates a score in the database with a score------------------------------------//
    public void updateJudgeScoreFailed(int meetid, int diverid, int divenumber, String failed, double totalscore, double score1, double score2,
                                double score3, double score4, double score5, double score6, double score7){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getFailedDive() + "='" + failed + "', "
                + getTotalScore() + "=" + totalscore + ", "
                + getScore1() + "=" + score1 + ", " + getScore2() + "=" + score2 + ", "
                + getScore3() + "=" + score3 + ", " + getScore4() + "=" + score4 + ", "
                + getScore5() + "=" + score5 + ", " + getScore6() + "=" + score6 + ", "
                + getScore7() + "=" + score7
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        db.execSQL(selectQuery);
        db.close();
    }

    public void updateJudgeFailed(int meetid, int diverid, int divenumber, String failed, double totalscore){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getFailedDive() + "='" + failed + "', "
                + getTotalScore() + "=" + totalscore
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        db.execSQL(selectQuery);
        db.close();
    }

    public void updateJudgeScore(int meetid, int diverid, int divenumber, double totalscore, double score1, double score2,
                                       double score3, double score4, double score5, double score6, double score7){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE " + getTableJudgeScores() + " SET "
                + getTotalScore() + "=" + totalscore + ", "
                + getScore1() + "=" + score1 + ", " + getScore2() + "=" + score2 + ", "
                + getScore3() + "=" + score3 + ", " + getScore4() + "=" + score4 + ", "
                + getScore5() + "=" + score5 + ", " + getScore6() + "=" + score6 + ", "
                + getScore7() + "=" + score7
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;

        db.execSQL(selectQuery);
        db.close();
    }

    //------------------deletes a record-------------------------------------------------------------------//
    public void deleteJudgeScore(int meetid, int diverid, int divenumber){
        String selectQuery = "DELETE FROM " + TABLE_JUDGE_SCORES + " WHERE "
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid
                + " AND dive_number = " + divenumber;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
        db.close();
    }
}
