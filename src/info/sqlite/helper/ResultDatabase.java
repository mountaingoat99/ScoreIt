package info.sqlite.helper;

import info.sqlite.model.ResultsDB;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ResultDatabase extends DatabaseHelper {

	public ResultDatabase(Context context) {
		super(context);
	}
	
	//------------------gets the results info for the Diver History page-----------------------//
    public ArrayList<Double> getResultsList(int meetid, int diverid){
        ArrayList<Double> resultsInfo = new ArrayList<>();
        String selectQuery = "SELECT dive_1, dive_2, "
                + "dive_3, dive_4, dive_5, dive_6, dive_7, dive_8, "
                + "dive_9, dive_10, dive_11, total_score "
                + " FROM " + getTableResults()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                resultsInfo.add(c.getDouble(0));
                resultsInfo.add(c.getDouble(1));
                resultsInfo.add(c.getDouble(2));
                resultsInfo.add(c.getDouble(3));
                resultsInfo.add(c.getDouble(4));
                resultsInfo.add(c.getDouble(5));
                resultsInfo.add(c.getDouble(6));
                resultsInfo.add(c.getDouble(7));
                resultsInfo.add(c.getDouble(8));
                resultsInfo.add(c.getDouble(9));
                resultsInfo.add(c.getDouble(10));
                resultsInfo.add(c.getDouble(11));
            }while(c.moveToNext());
        }
        c.close();
        return resultsInfo;
    }

    //------------------gets the results info for the Dive Calc page-----------------------//
    public ArrayList<Double> checkResults(int meetid, int diverid){
        ArrayList<Double> resultsInfo = new ArrayList<>();
        String selectQuery = "SELECT dive_1, dive_2, "
                + "dive_3, dive_4, dive_5, dive_6, dive_7, dive_8, "
                + "dive_9, dive_10, dive_11, total_score "
                + " FROM " + getTableResults()
                + " WHERE meet_id = " + meetid
                + " AND diver_id = " + diverid;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                resultsInfo.add(c.getDouble(0));
                resultsInfo.add(c.getDouble(1));
                resultsInfo.add(c.getDouble(2));
                resultsInfo.add(c.getDouble(3));
                resultsInfo.add(c.getDouble(4));
                resultsInfo.add(c.getDouble(5));
                resultsInfo.add(c.getDouble(6));
                resultsInfo.add(c.getDouble(7));
                resultsInfo.add(c.getDouble(8));
                resultsInfo.add(c.getDouble(9));
                resultsInfo.add(c.getDouble(10));
                resultsInfo.add(c.getDouble(11));
            }while(c.moveToNext());
        }
        return resultsInfo;
    }

    //-------------------gets the Namse and school info for the MeetScores Page-------------------------------//
	public ArrayList<String> getScores(int meetid, int diverid){
		ArrayList<String> r = new ArrayList<>();
		String selectQuery = "SELECT m.name, m.school, m.city, m.state, m.date "
				+ "FROM meet m INNER JOIN results r "
				+ "on (m.id = r.meet_id) "
				+ "WHERE r.meet_id = " + meetid 
				+ " AND r.diver_id = " + diverid;
		SQLiteDatabase db = this.getReadableDatabase();	
		
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.moveToFirst()){
			do{
				r.add(c.getString(0));
				r.add(c.getString(1));
				r.add(c.getString(2));
				r.add(c.getString(3));
				r.add(c.getString(4));
			}while(c.moveToNext());
		}		
		return r;
	}
	
	//-----------------checks the db to see if a result is attached to a meet and diver yet------//
	public Boolean checkResult(int meetid, int diverid){
		SQLiteDatabase db = this.getReadableDatabase();	
		String selectQuery = "SELECT * FROM results "
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
	
	//-----------------creates and empty result row attached to a meet and Diver-----------------//
	public void createEmptyResult(int meetid, int diverid){
		SQLiteDatabase db = this.getWritableDatabase();
		ResultsDB result = new ResultsDB(meetid, diverid, 0.0, 0.0, 0.0, 0.0,
								0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		
		createResults(result, db);
	}

    public void createResults(ResultsDB results, SQLiteDatabase db){
		ContentValues values = new ContentValues();
		//values.put(KEY_ID, results.getId());
		values.put(MEET_ID, results.getMeetId());
		values.put(DIVER_ID, results.getDiverId());
		values.put(DIVE_1, results.getDive1());
		values.put(DIVE_2, results.getDive2());
		values.put(DIVE_3, results.getDive3());
		values.put(DIVE_4, results.getDive4());
		values.put(DIVE_5, results.getDive5());
		values.put(DIVE_6, results.getDive6());
		values.put(DIVE_7, results.getDive7());
		values.put(DIVE_8, results.getDive8());
		values.put(DIVE_9, results.getDive9());
		values.put(DIVE_10, results.getDive10());
		values.put(DIVE_11, results.getDive11());
		values.put(TOTAL_SCORE, results.getTotalScore());

		db.insert(TABLE_RESULTS, null, values);
	}

    //-----------------updates a record in the database with a score-----------------------------//
    public void writeDiveScore(int meetid, int diverid, int columnindex, double score, double totalscore){
        String column = null;
        switch (columnindex){
            case 3:
                column = "dive_1";
                break;
            case 4:
                column = "dive_2";
                break;
            case 5:
                column = "dive_3";
                break;
            case 6:
                column = "dive_4";
                break;
            case 7:
                column = "dive_5";
                break;
            case 8:
                column = "dive_6";
                break;
            case 9:
                column = "dive_7";
                break;
            case 10:
                column = "dive_8";
                break;
            case 11:
                column = "dive_9";
                break;
            case 12:
                column = "dive_10";
                break;
            case 13:
                column = "dive_11";
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "UPDATE results SET "
                + column + "=" + score + ", total_score = "
                + totalscore + " WHERE meet_id= "
                + meetid + " AND diver_id= " + diverid;

        db.execSQL(selectQuery);
    }

    //--------------gets the Score total from the database------------------------------//
    public double getTotalScore(int meetid, int diverid){
        double totalScore = 0;
        String selectQuery = "SELECT total_score FROM results "
                    + " WHERE meet_id= " + meetid
                    + " AND diver_id= " + diverid;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0 && c.moveToFirst()){
            do{
                totalScore = c.getDouble(0);
            }while (c.moveToNext());
        }
        if (c != null) {
            c.close();
        }
        return totalScore;
    }
}
