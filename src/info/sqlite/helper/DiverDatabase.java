package info.sqlite.helper;

import info.sqlite.model.DiverNameDB;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DiverDatabase extends DatabaseHelper {

	public DiverDatabase(Context context) {
		super(context);
	}
	
	//--------------fill diver------------------------------------------------------//
	public void fillDiver(String name, String age, String grade, String school){
		SQLiteDatabase db = this.getWritableDatabase();
		
		DiverNameDB diver = new DiverNameDB(name, age, grade, school);
		createDiver(diver, db);		
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
		return diverInfo;
	}

    //--------------checks to see if there is a diver in the database------------------------//
    public boolean checkDiver(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM diver";
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.getCount() <= 0){
            return false;
        }
        return true;
    }
	//--------------delete diver-------------------------------------------------------------//
	public void deleteDiver(int id){
		String selectQuery = "DELETE FROM " + getTableDiverName()
				+ " WHERE id = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
	}
		
	// -------------edit rows in the diver tables ----------------------------------------//
	public void editDiverName(int id, String name){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET name='" + name + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
	}
				
	public void editDiverAge(int id, String age){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET age='" + age + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
	}
				
	public void editDiverGrade(int id, String grade){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET grade='" + grade + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
	}
				
	public void editDiverSchool(int id, String school){
		String selectQuery = "UPDATE " + getTableDiverName()
				+ " SET school='" + school + "' WHERE id = "
				+ id;
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(selectQuery);
	}
}
