package info.sqlite.helper;

import info.Helpers.DiverMeetResults;
import info.Helpers.DiverScoreTotals;
import info.sqlite.model.DiverNameDB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import info.Helpers.PrintedResults;
import info.Helpers.RankingResults;

public class DiverDatabase extends DatabaseHelper {

	public DiverDatabase(Context context) {
		super(context);
	}
	
	//--------------fill diver------------------------------------------------------//
	public void fillDiver(String name, String age, String grade, String school){
		SQLiteDatabase db = this.getWritableDatabase();
		
		DiverNameDB diver = new DiverNameDB(name, age, grade, school);
		createDiver(diver, db);
        db.close();
	}	
	
	//--------------insert a row into the diver tables------------------------------//
	public void createDiver(DiverNameDB diver, SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put(getDiverName(), diver.getName());
		values.put(getDiverAge(), diver.getAge());
		values.put(getDiverGrade(), diver.getGrade());
		values.put(getDiverSchool(), diver.getSchool());
			
		db.insert(getTableDiverName(), null, values);		
	}

	//----------gets the diver name for the autofill lists----------------------------//
	public List<String> getDiverNames(){
		List<String> diverNames = new ArrayList<>();
		String selectQuery = "SELECT " + getDiverName() + " FROM " + getTableDiverName();
		Log.e(getLog(), selectQuery);		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()){
			do{
				DiverNameDB t = new DiverNameDB();
				diverNames.add("  " +  t.setName(c.getString(c.getColumnIndex(getDiverName()))));
			}while (c.moveToNext());
		}
		c.close();
        db.close();
		return diverNames;			
	}
	
	//--------------get the driver id from the spinner-----------------------------------------//
	public int getId(String stringId){
		int id = 0;
		String selectQuery = "SELECT id FROM " + getTableDiverName()
				+ " WHERE name= '" + stringId + "'";
		Log.e(getLog(), selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
	
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
	
	//--------------gets all diver info for the edit/delete pages----------------------------//
	public ArrayList<String> getDiverInfo(int id){
		ArrayList<String> diverInfo = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + getTableDiverName() 
				+ " WHERE " + getKeyId() + " = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
			
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.moveToFirst()){
			do{
				diverInfo.add(c.getString(1));
				diverInfo.add(c.getString(2));
				diverInfo.add(c.getString(3));
				diverInfo.add(c.getString(4));	
			}while(c.moveToNext());
		}
        c.close();
        db.close();
		return diverInfo;
	}

    //-------------gets the diver and scores for the rankings by meet page-------------------//
    public ArrayList<RankingResults> getRankings(int meetid, double board){
        ArrayList<RankingResults> diverInfo = new ArrayList<>();
        RankingResults r;
        String selectQuery = "SELECT DISTINCT d.name, r.total_score, n.dive_number FROM diver d"
                + " INNER JOIN results r on r.diver_id = d.id"
                + " INNER JOIN dive_number n on n.meet_id = " + meetid + " AND n.diver_id = d.id"
                + " INNER JOIN dive_type t on t.diver_id = r.diver_id"
                + " WHERE r.meet_id= " + meetid + " AND t.type= " + board
                + " AND n.type= " + board
                + " ORDER by r.total_score desc, n.dive_number desc";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new RankingResults();
            r.setName(c.getString(0));
            r.setScore(c.getString(1));
            r.setDiveNumber(c.getString(2));
            diverInfo.add(r);
        }
        c.close();
        db.close();
        return diverInfo;
    }

    //---------------gets the diver info for for the printed meet results------------------//
    public ArrayList<PrintedResults> getPrintedResults(int meetid, double boardType){
        ArrayList<PrintedResults> results = new ArrayList<>();
        PrintedResults r;
        String selectQuery = "SELECT DISTINCT d.name, d.school, m.name, m.date, m.judges, dt.dive_count, dtt.type, r.total_score, r.dive_1, r.dive_2, r.dive_3, r.dive_4, r.dive_5, "
                + "r.dive_6, r.dive_7, r.dive_8, r.dive_9, r.dive_10, r.dive_11, js.dive_number, js.dive_type_name, js.dive_position, js.multiplier, js.failed, "
                + "js.score_1, js.score_2, js.score_3, js.score_4, js.score_5, js.score_6, js.score_7 "
                + "FROM diver d "
                + "INNER JOIN results r on r.diver_id = d.id "
                + "INNER JOIN meet m on m.id = r.meet_id "
                + "INNER JOIN dive_total dt on dt.diver_id = d.id AND dt.meet_id= " + meetid
                + " INNER JOIN dive_type dtt on dtt.diver_id = d.id AND dtt.meet_id= " + meetid
                + " INNER JOIN judge_scores js on js.diver_id = d.id AND js.meet_id= " + meetid
                + " WHERE m.id= " + meetid + " AND dtt.type= " + boardType;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new PrintedResults();
            r.setName(c.getString(0));
            r.setSchool(c.getString(1));
            r.setMeetName(c.getString(2));
            r.setDate(c.getString(3));
            r.setJudges(c.getString(4));
            r.setDiveCount(c.getString(5));
            r.setDiveType(c.getString(6));
            r.setTotalScore(c.getString(7));
            r.setDive1(c.getString(8));
            r.setDive2(c.getString(9));
            r.setDive3(c.getString(10));
            r.setDive4(c.getString(11));
            r.setDive5(c.getString(12));
            r.setDive6(c.getString(13));
            r.setDive7(c.getString(14));
            r.setDive8(c.getString(15));
            r.setDive9(c.getString(16));
            r.setDive10(c.getString(17));
            r.setDive11(c.getString(18));
            r.setDiveNumber(c.getString(19));
            r.setDiveStyle(c.getString(20));
            r.setDivePostion(c.getString(21));
            r.setDd(c.getString(22));
            r.setFailed(c.getString(23));
            r.setScore1(c.getString(24));
            r.setScore2(c.getString(25));
            r.setScore3(c.getString(26));
            r.setScore4(c.getString(27));
            r.setScore5(c.getString(28));
            r.setScore6(c.getString(29));
            r.setScore7(c.getString(30));
            results.add(r);
        }
        c.close();
        db.close();
        return results;
    }

    //---------------gets the dive scores by meet for individual diver to create spreadsheet--//
    public ArrayList<DiverMeetResults> getDiverMeetResults(int meetid, int diverid){
        ArrayList<DiverMeetResults> results = new ArrayList<>();
        DiverMeetResults r;
        String selectQuery = "SELECT d.name, m.name, js.dive_number, js.dive_type_name, js.dive_position, js.multiplier, " +
                "js.total_score, js.failed, m.judges, js.score_1, js.score_2, js.score_3, js.score_4, js.score_5, js.score_6, js.score_7 " +
                "from results r " +
                "INNER JOIN diver d ON d.id = r.diver_id " +
                "INNER JOIN meet m ON m.id = r.meet_id " +
                "INNER JOIN judge_scores js ON js.diver_id = r.diver_id AND js.meet_id = r.meet_id " +
                "WHERE js.meet_id=" + meetid + " AND js.diver_id= " + diverid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new DiverMeetResults();
            r.setName(c.getString(0));
            r.setMeetName(c.getString(1));
            r.setDiveNumber(c.getString(2));
            r.setDiveName(c.getString(3));
            r.setPosition(c.getString(4));
            r.setDd(c.getString(5));
            r.setTotal(c.getString(6));
            r.setPassFailed(c.getString(7));
            r.setJudges(c.getString(8));
            r.setScore1(c.getString(9));
            r.setScore2(c.getString(10));
            r.setScore3(c.getString(11));
            r.setScore4(c.getString(12));
            r.setScore5(c.getString(13));
            r.setScore6(c.getString(14));
            r.setScore7(c.getString(15));
            results.add(r);
        }
        c.close();
        db.close();
        return results;
    }

    //---------------gets the dive score totals by diver by meet to create spreadsheet--//
    public ArrayList<DiverScoreTotals> getDiverMeetScoreTotals(int meetid, int diverid) {
        ArrayList<DiverScoreTotals> results = new ArrayList<>();
        DiverScoreTotals r;
        String selectQuery = "SELECT d.name, d.school, m.name, m.date, r.total_score, r.dive_1, r.dive_2, " +
                "r.dive_3, r.dive_4, r.dive_5, r.dive_6, r.dive_7, r.dive_8, r.dive_9, r.dive_10, r.dive_11 " +
                " FROM results r " +
                "INNER JOIN diver d ON d.id = r.diver_id " +
                "INNER JOIN meet m ON m.id = r.meet_id " +
                "WHERE r.meet_id= " + meetid + " and r.diver_id= " + diverid;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        while (c.moveToNext()) {
            r = new DiverScoreTotals();
            r.setName(c.getString(0));
            r.setSchool(c.getString(1));
            r.setMeetName(c.getString(2));
            r.setDate(c.getString(3));
            r.setTotal(c.getString(4));
            r.setScore1(c.getString(5));
            r.setScore2(c.getString(6));
            r.setScore3(c.getString(7));
            r.setScore4(c.getString(8));
            r.setScore5(c.getString(9));
            r.setScore6(c.getString(10));
            r.setScore7(c.getString(11));
            r.setScore8(c.getString(12));
            r.setScore9(c.getString(13));
            r.setScore10(c.getString(14));
            r.setScore11(c.getString(15));
            results.add(r);
        }
        c.close();
        db.close();
        return results;
    }
	
	//---------------gets the diver info for the history pages-------------------------------//
		
	public ArrayList<String> getDiverHistory(int id){
		ArrayList<String> diverInfo = new ArrayList<>();
		String selectQuery = "SELECT d.name FROM diver d "
				+ "INNER JOIN results r "
				+ " on (d.id = r.diver_id) "
				+ "WHERE r.meet_id = " + id;
		SQLiteDatabase db = this.getReadableDatabase();	
		
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.moveToFirst()){
			do{
				diverInfo.add(c.getString(0));
			}while(c.moveToNext());
		}
        c.close();
        db.close();
		return diverInfo;
	}

    //--------------checks to see if there is a diver in the database------------------------//
    public boolean checkDiver(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM diver";
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

    //--------------checks to see if a diver is attached for rankings yet--------------------//
    public boolean checkDiverForRankings(int meetid, double board){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT d.name, r.total_score FROM diver d"
                + " INNER JOIN results r on r.diver_id = d.id"
                + " INNER JOIN dive_type t on t.diver_id = r.diver_id"
                + " WHERE r.meet_id= " + meetid
                + " AND r.total_score > 0"
                + " AND t.type= " + board;

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

	//--------------delete diver-------------------------------------------------------------//
	public void deleteDiver(int id){
		String selectQuery = "DELETE FROM " + getTableDiverName()
				+ " WHERE id = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}

    //-------------deletes a diver from a meet----------------------------------------------//
    public void removeDiverFromMeet(int meetid, int diverid){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery1 = "DELETE FROM " + getTableDiveNumber() + " WHERE meet_id =" + meetid + " AND diver_id =" + diverid;
        String selectQuery2 = "DELETE FROM " + getTableJudgeScores() + " WHERE meet_id =" + meetid + " AND diver_id =" + diverid;
        String selectQuery3 = "DELETE FROM " + getTableResults() + " WHERE meet_id =" + meetid + " AND diver_id =" + diverid;
        String selectQuery4 = "DELETE FROM " + getTableDiveTotals() + " WHERE meet_id =" + meetid + " AND diver_id =" + diverid;
        String selectQuery5 = "DELETE FROM " + getTableDiveType() + " WHERE meet_id =" + meetid + " AND diver_id =" + diverid;

        Log.e(getLog(), selectQuery1);
        Log.e(getLog(), selectQuery2);
        Log.e(getLog(), selectQuery3);
        Log.e(getLog(), selectQuery4);
        Log.e(getLog(), selectQuery5);
        db.execSQL(selectQuery1);
        db.execSQL(selectQuery2);
        db.execSQL(selectQuery3);
        db.execSQL(selectQuery4);
        db.execSQL(selectQuery5);

        db.close();
    }
		
	// -------------edit rows in the diver tables ----------------------------------------//
	public void editDiverName(int id, String name){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET name='" + name + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
				
	public void editDiverAge(int id, String age){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET age='" + age + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
				
	public void editDiverGrade(int id, String grade){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET grade='" + grade + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
				
	public void editDiverSchool(int id, String school){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET school='" + school + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
}
