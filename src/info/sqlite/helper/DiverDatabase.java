package info.sqlite.helper;

import info.sqlite.model.DiverNameDB;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rodriguez.divingscores.RankingResults;

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
				diverNames.add(t.setName(c.getString(c.getColumnIndex(getDiverName()))));				
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
    public ArrayList<RankingResults> getRankings(int meetid){
        ArrayList<RankingResults> diverInfo = new ArrayList<>();
        RankingResults r;
        String selectQuery = "SELECT DISTINCT d.name, r.total_score, n.dive_number FROM diver d"
                + " INNER JOIN results r on r.diver_id = d.id"
                + " INNER JOIN dive_number n on n.diver_id = d.id"
                + " WHERE r.meet_id= " + meetid
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
    public boolean checkDiverForRankings(int meetid){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT d.name, r.total_score FROM diver d"
                + " INNER JOIN results r on r.diver_id = d.id"
                + " WHERE r.meet_id= " + meetid;

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
