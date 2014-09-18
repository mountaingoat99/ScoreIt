package info.sqlite.helper;

import info.sqlite.model.MeetDB;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rodriguez.divingscores.RankingsMeet;

public class MeetDatabase extends DatabaseHelper {

	public MeetDatabase(Context context) {
		super(context);
	}
	
	//--------------fill meet----------------------------------------------------------------//
	public void fillMeet(String name, String school, String city, String state, String date, int judges){
		SQLiteDatabase db = this.getWritableDatabase();
		
		MeetDB meet = new MeetDB(name, school, city, state, date, judges);
		createMeet(meet, db);
        db.close();
	}
	
	//--------------insert a row into the diver and meet tables------------------------------//		
	public void createMeet(MeetDB meet, SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put(getMeetName(), meet.getMeetName());
		values.put(getMeetSchool(), meet.getSchool());
		values.put(getMeetCity(), meet.getCity());
		values.put(getMeetState(), meet.getState());
		values.put(getMeetDate(), meet.getMeetDate());
		values.put(getMeetJudges(), meet.getJudges());
			
		db.insert(getTableMeetName(), null, values);
	}	
	
	//----------gets the meet name for the autofill lists----------------------------//
	public List<String> getMeetNames(){
		List<String> meetNames = new ArrayList<>();
		
		String selectQuery = "SELECT " + getMeetName() + " FROM " + getTableMeetName();
		
		Log.e(getLog(), selectQuery);
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if (c.moveToFirst()){
			do{
				MeetDB t = new MeetDB();
				meetNames.add("  " +  t.setMeetName(c.getString(c.getColumnIndex(getMeetName()))));
			}while (c.moveToNext());
		}
		c.close();
        db.close();
		return meetNames;		
	}
	
	//--------------get the meet id from the spinner in the Choose Class-----------------//
		public int getId(String stringId){
			int id = 0;
			String selectQuery = "SELECT id FROM " + getTableMeetName()
					+ " WHERE name= '" + stringId + "'";
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

    //--------------get the judges total for the dive score pages----------------------------//
    public int getJudges(int meetID){
        int id = 0;
        String selectQuery = "SELECT judges FROM " + getTableMeetName()
                + " WHERE id= " + meetID;

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
		
	//--------------get all meet info for the edit/delete pages------------------------------//
	public ArrayList<String> getMeetInfo(int id){
		ArrayList<String> meetInfo = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + getTableMeetName()
				+ " WHERE " + getKeyId() + " = " + id;
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor c = db.rawQuery(selectQuery, null);
		if(c.moveToFirst()){
			do{
				meetInfo.add(c.getString(1));
				meetInfo.add(c.getString(2));
				meetInfo.add(c.getString(3));
				meetInfo.add(c.getString(4));
				meetInfo.add(c.getString(5));
				meetInfo.add(c.getString(6));
			}while(c.moveToNext());
		}
        c.close();
        db.close();
		return meetInfo;
	}	
	
	//---------------gets the meet info for the history pages-------------------------------//	
		public ArrayList<String> getMeetHistory(int id){
			ArrayList<String> meetInfo = new ArrayList<>();
			String selectQuery = "SELECT m.name FROM meet m "
					+ "INNER JOIN results r " 
					+ " on (m.id = r.meet_id) "
					+ "WHERE r.diver_id = " + id;
			SQLiteDatabase db = this.getReadableDatabase();	
			
			Cursor c = db.rawQuery(selectQuery, null);
			if(c.moveToFirst()){
				do{
					meetInfo.add(c.getString(0));
					
				}while(c.moveToNext());
			}
            c.close();
            db.close();
			return meetInfo;
		}

    //---------------gets the meet info for the history pages-------------------------------//
    public ArrayList<String> getMeetNameForRank(){
        ArrayList<String> meetInfo = new ArrayList<>();
        String selectQuery = "SELECT name FROM meet";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                meetInfo.add(c.getString(0));

            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return meetInfo;
    }

    //---------------gets the meet info for the history pages-------------------------------//
    public ArrayList<RankingsMeet> getNameForMeetRank(){
        ArrayList<RankingsMeet> meetInfo = new ArrayList<>();
        RankingsMeet r;
        String selectQuery = "SELECT DISTINCT m.name, d.type  FROM meet m " +
                "INNER JOIN dive_type d on d.meet_id = m.id " +
                "WHERE d.type > 0 " +
                "ORDER BY m.name asc, d.type asc";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        while(c.moveToNext()){
            r = new RankingsMeet();
            r.setMeetName(c.getString(0));
            r.setBoardSize(c.getString(1));
            //r.setDiveTotal(c.getString(2));
            meetInfo.add(r);
        }

//        if(c.moveToFirst()){
//            do{
//                meetInfo.add(c.getString(0));
//
//            }while(c.moveToNext());
//        }
        c.close();
        db.close();
        return meetInfo;
    }

    //--------------get meet name for the rankings pages-------------------------------------//
    public String getMeetName(int meetid){
        String name = "";
        String selectQuery = "SELECT name FROM meet"
                + " WHERE id= " + meetid;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do{
                name = c.getString(0);
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return name;
    }

    //--------------checks to see if there is a diver in the database------------------------//
    public boolean checkMeet(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM meet";
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
	
	//--------------delete meet----------------------------------------------------------//
	public void deleteMeet(int id){
		String selectQuery = "DELETE FROM " + getTableMeetName()
				+ " WHERE id = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
	
	// -------------edit rows in the meet tables ----------------------------------------//
	public void editMeetName(int id, String name){
		String selectQuery = "UPDATE " + getTableMeetName()
				+ " SET name='" + name + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
					
	public void editMeetSchool(int id, String school){
		String selectQuery = "UPDATE " + getTableMeetName()
				+ " SET school='" + school + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
					
	public void editMeetCity(int id, String city){
		String selectQuery = "UPDATE " + getTableMeetName()
				+ " SET city='" + city + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
					
	public void editMeetState(int id, String state){
		String selectQuery = "UPDATE meet SET state='" 
				+ state + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
					
	public void editMeetDate(int id, String date){
		String selectQuery = "UPDATE " + getTableMeetName()
				+ " SET date='" + date + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}	
	
	public void editMeetJudges(int id, int judges){
		String selectQuery = "UPDATE " + getTableMeetName()
				+ " SET judges='" + judges + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
        db.close();
	}
}
