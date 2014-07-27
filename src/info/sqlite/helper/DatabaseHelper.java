package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.sqlite.model.BackDB;
import info.sqlite.model.DivesDB;
import info.sqlite.model.ForwardDB;
import info.sqlite.model.InwardDB;
import info.sqlite.model.ReverseDB;
import info.sqlite.model.ScoresDB;
import info.sqlite.model.TwistDB;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// logcat tag
	protected static final String LOG = "DatabaseHelper";
	
	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database name
	private static final String DATABASE_NAME = "DIVE_DOD";
	
	// Table Names
	protected static final String TABLE_FORWARD = "forward_dives";
	protected static final String TABLE_BACK = "back_dives";
	protected static final String TABLE_INWARD = "inward_dives";
	protected static final String TABLE_REVERSE = "reverse_dives";
	protected static final String TABLE_TWIST = "twist_dives";
	protected static final String TABLE_DIVES = "dives";
    protected static final String TABLE_DIVER_NAME = "diver";
    protected static final String TABLE_MEET_NAME = "meet";
    protected static final String TABLE_RESULTS = "results";
    protected static final String TABLE_DIVE_TOTALS = "dive_total";
    protected static final String TABLE_DIVE_TYPE = "dive_type";
    protected static final String TABLE_SCORES = "scores";
    protected static final String TABLE_JUDGE_SCORES = "judge_scores";

	// common column names
	protected static final String KEY_ID = "id";
    protected static final String MEET_ID = "meet_id";
    protected static final String DIVER_ID = "diver_id";
	protected static final String DIVE_NAME = "name";
    protected static final String ONE_S = "oneS";
    protected static final String ONE_P = "oneP";
    protected static final String ONE_T = "oneT";
    protected static final String ONE_F = "oneF";
    protected static final String THREE_S = "threeS";
    protected static final String THREE_P = "threeP";
    protected static final String THREE_T = "threeT";
    protected static final String THREE_F = "threeF";
	
	// diver name columns
    protected static final String DIVER_NAME = "name";
    protected static final String DIVER_AGE = "age";
    protected static final String DIVER_GRADE = "grade";
    protected static final String DIVER_SCHOOL = "school";
	
	// diver array for history class
    protected static String[] ALL_KEYS = new String[] {DIVER_NAME, DIVER_AGE,
		DIVER_GRADE,DIVER_SCHOOL};
	
	// meet column names
    protected static final String MEET_NAME = "name";
    protected static final String MEET_SCHOOL = "school";
    protected static final String MEET_CITY = "city";
    protected static final String MEET_STATE = "state";
    protected static final String MEET_DATE = "date";
    protected static final String MEET_JUDGES = "judges";
	
	//results column names
    protected static final String DIVE_1 = "dive_1";
    protected static final String DIVE_2 = "dive_2";
    protected static final String DIVE_3 = "dive_3";
    protected static final String DIVE_4 = "dive_4";
    protected static final String DIVE_5 = "dive_5";
    protected static final String DIVE_6 = "dive_6";
    protected static final String DIVE_7 = "dive_7";
    protected static final String DIVE_8 = "dive_8";
    protected static final String DIVE_9 = "dive_9";
    protected static final String DIVE_10 = "dive_10";
    protected static final String DIVE_11 = "dive_11";
    protected static String TOTAL_SCORE = "total_score";

    //dive totals columns names
    protected static final String DIVE_COUNT = "dive_count";

    //dive type column names
    private static final String DIVE_TYPE = "type";

    // judges scores values tables
    protected static final String DIGITS = "digits";

    // judge scores table
    protected  static final String DIVE_NUMBER = "dive_number";
    protected static final String SCORE_1 = "score_1";
    protected static final String SCORE_2 = "score_2";
    protected static final String SCORE_3 = "score_3";
    protected static final String SCORE_4 = "score_4";
    protected static final String SCORE_5 = "score_5";
    protected static final String SCORE_6 = "score_6";
    protected static final String SCORE_7 = "score_7";

    public static String getTableJudgeScores() { return TABLE_JUDGE_SCORES; }
    public static String getTableScores() { return TABLE_SCORES; }
    public static String getTableDiveType() { return TABLE_DIVE_TYPE; }
    public static String getDiveCount() { return DIVE_COUNT; }
	public static String[] getAllKeys() {
		return ALL_KEYS;
	}
	public static void setAllKeys(String[] allKeys) {
		ALL_KEYS = allKeys;
	}
	public static String getTableForward() {
		return TABLE_FORWARD;
	}
	public static String getTableBack() {
		return TABLE_BACK;
	}
	public static String getTableInward() {
		return TABLE_INWARD;
	}
	public static String getTableReverse() {
		return TABLE_REVERSE;
	}
	public static String getTableTwist() {
		return TABLE_TWIST;
	}
	public static String getTableDives() {
		return TABLE_DIVES;
	}
	public static String getTableDiverName() {
		return TABLE_DIVER_NAME;
	}
	public static String getTableMeetName() {
		return TABLE_MEET_NAME;
	}
	public static String getTableResults() {
		return TABLE_RESULTS;
	}
    public static String getTableDiveTotals() { return TABLE_DIVE_TOTALS; }
	public static String getKeyId() {
		return KEY_ID;
	}
	public static String getDiveName() {
		return DIVE_NAME;
	}
	public static String getOneS() {
		return ONE_S;
	}
	public static String getOneP() {
		return ONE_P;
	}
	public static String getOneT() {
		return ONE_T;
	}
	public static String getOneF() {
		return ONE_F;
	}
	public static String getThreeS() {
		return THREE_S;
	}
	public static String getThreeP() {
		return THREE_P;
	}
	public static String getThreeT() {
		return THREE_T;
	}
	public static String getThreeF() {
		return THREE_F;
	}
	public static String getDiverName() {
		return DIVER_NAME;
	}
	public static String getDiverAge() {
		return DIVER_AGE;
	}
	public static String getDiverGrade() {
		return DIVER_GRADE;
	}
	public static String getDiverSchool() {
		return DIVER_SCHOOL;
	}
	public static String getMeetName() {
		return MEET_NAME;
	}
	public static String getMeetSchool() {
		return MEET_SCHOOL;
	}
	public static String getMeetCity() {
		return MEET_CITY;
	}
	public static String getMeetState() {
		return MEET_STATE;
	}
	public static String getMeetDate() {
		return MEET_DATE;
	}
	public static String getMeetJudges(){
		return MEET_JUDGES;
	}
	public static String getMeetId() {
		return MEET_ID;
	}
	public static String getDiverId() {
		return DIVER_ID;
	}
	public static String getDive1() {
		return DIVE_1;
	}
	public static String getDive2() {
		return DIVE_2;
	}
	public static String getDive3() {
		return DIVE_3;
	}
	public static String getDive4() {
		return DIVE_4;
	}
	public static String getDive5() {
		return DIVE_5;
	}
	public static String getDive6() {
		return DIVE_6;
	}
	public static String getDive7() {
		return DIVE_7;
	}
	public static String getDive8() {
		return DIVE_8;
	}
	public static String getDive9() {
		return DIVE_9;
	}
	public static String getDive10() {
		return DIVE_10;
	}
	public static String getDive11() {
		return DIVE_11;
	}
    public static String getDiveNumber() { return DIVE_NUMBER; }
    public static String getScore1() { return SCORE_1; }
    public static String getScore2() { return SCORE_2; }
    public static String getScore3() { return SCORE_3; }
    public static String getScore4() { return SCORE_4; }
    public static String getScore5() { return SCORE_5; }
    public static String getScore6() { return SCORE_6; }
    public static String getScore7() { return SCORE_7; }
    public static String getTotalScore() {
		return TOTAL_SCORE;
	}
	public static void setTotalScore(String totalScore) {
		TOTAL_SCORE = totalScore;
	}
	public static String getLog() {
		return LOG;
	}

	// table create statements
	public static final String CREATE_TABLE_FORWARD = "CREATE TABLE "
			+ TABLE_FORWARD + "(" + KEY_ID + " INTEGER, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_BACK = "CREATE TABLE "
			+ TABLE_BACK + "(" + KEY_ID + " INTEGER, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_INWARD = "CREATE TABLE "
			+ TABLE_INWARD + "(" + KEY_ID + " INTEGER, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_REVERSE = "CREATE TABLE "
			+ TABLE_REVERSE + "(" + KEY_ID + " INTEGER, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_TWIST = "CREATE TABLE "
			+ TABLE_TWIST + "(" + KEY_ID + " INTEGER, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_DIVES = "CREATE TABLE "
			+ TABLE_DIVES + "(" + KEY_ID + " INTEGER, " 
			+ DIVE_NAME + " TEXT " + ")";

    public static final String CREATE_TABLE_SCORES = "CREATE TABLE "
            + TABLE_SCORES + "(" + KEY_ID + " INTEGER, "
            + DIGITS + " TEXT " + ")";
	
	public static final String CREATE_TABLE_DIVER_NAME = "CREATE TABLE "
			+ TABLE_DIVER_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DIVER_NAME + " TEXT, " + DIVER_AGE + " INTEGER, " + DIVER_GRADE + " TEXT, "
			+ DIVER_SCHOOL + " TEXT " + ")";
	
	public static final String CREATE_TABLE_MEET_NAME = "CREATE TABLE "
			+ TABLE_MEET_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEET_NAME + " TEXT, " + MEET_SCHOOL + " TEXT, " + MEET_CITY + " TEXT, "
			+ MEET_STATE + " TEXT, " + MEET_DATE + " TEXT, " + MEET_JUDGES + " INT "
			+ ")";
	
	public static final String CREATE_TABLE_RESULTS = "CREATE TABLE "
			+ TABLE_RESULTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEET_ID + " INTEGER, " + DIVER_ID + " INTEGER, "
			+ DIVE_1 + " TEXT, " + DIVE_2 + " TEXT, " + DIVE_3 + " TEXT, "
			+ DIVE_4 + " TEXT, " + DIVE_5 + " TEXT, " + DIVE_6 + " TEXT, "
			+ DIVE_7 + " TEXT, " + DIVE_8 + " TEXT, " + DIVE_9 + " TEXT, "
			+ DIVE_10 + " TEXT, " + DIVE_11 + " TEXT, " +  TOTAL_SCORE + " TEXT, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";


    public static final String CREATE_TABLE_DIVE_TOTAL = "CREATE TABLE "
            + TABLE_DIVE_TOTALS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + DIVE_COUNT + " INTEGER, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_DIVE_TYPE = "CREATE TABLE "
            + TABLE_DIVE_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + DIVE_TYPE + " INTEGER, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_JUDGE_SCORES = "CREATE TABLE "
            + TABLE_JUDGE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, " + DIVE_NUMBER + " INTEGER, "
            + SCORE_1 + " TEXT, " + SCORE_2 + " TEXT, " + SCORE_3 + " TEXT, "
            + SCORE_4 + " TEXT, " + SCORE_5 + " TEXT, " + SCORE_6 + " TEXT, " + SCORE_7 + " TEXT, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    //---------------Triggers---------------------------------------------------------------------//
    public static final String DIVER_DELETE_TRIGGER = "CREATE TRIGGER diver_delete_trigger "
            + "BEFORE DELETE ON " + TABLE_DIVER_NAME + " FOR EACH ROW BEGIN "
            + "DELETE FROM " + TABLE_JUDGE_SCORES + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_RESULTS + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TOTALS + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TYPE + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; END";

    public static final String MEET_DELETE_TRIGGER = "CREATE TRIGGER meet_delete_trigger "
            + "BEFORE DELETE ON " + TABLE_MEET_NAME + " FOR EACH ROW BEGIN "
            + "DELETE FROM " + TABLE_JUDGE_SCORES + " WHERE " + MEET_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_RESULTS + " WHERE " + MEET_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TOTALS + " WHERE " + MEET_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TYPE + " WHERE " + MEET_ID + " = old." + KEY_ID + "; END";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_BACK);
		db.execSQL(CREATE_TABLE_FORWARD);
		db.execSQL(CREATE_TABLE_INWARD);
		db.execSQL(CREATE_TABLE_REVERSE);
		db.execSQL(CREATE_TABLE_TWIST);
		db.execSQL(CREATE_TABLE_DIVES);
        db.execSQL(CREATE_TABLE_SCORES);
		db.execSQL(CREATE_TABLE_DIVER_NAME);
		db.execSQL(CREATE_TABLE_MEET_NAME);
		db.execSQL(CREATE_TABLE_RESULTS);
        db.execSQL(CREATE_TABLE_DIVE_TOTAL);
        db.execSQL(CREATE_TABLE_DIVE_TYPE);
        db.execSQL(CREATE_TABLE_JUDGE_SCORES);
        db.execSQL((DIVER_DELETE_TRIGGER));
        db.execSQL((MEET_DELETE_TRIGGER));

		// call the methods to fill the dive table data
        fillForwardDives(db);
        fillBackDives(db);
        fillInwardDives(db);
        fillReverseDives(db);
        fillTwistDives(db);
        fillDives(db);
        fillScores(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BACK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORWARD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INWARD);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVERSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVER_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEET_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVE_TOTALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUDGE_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + DIVER_DELETE_TRIGGER);
        db.execSQL("DROP TABLE IF EXISTS " + MEET_DELETE_TRIGGER);

		onCreate(db);		
	}
    public void createDives(DivesDB dives, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(KEY_ID, dives.getId());
        values.put(DIVE_NAME, dives.getDiveName());

        db.insert(TABLE_DIVES, null, values);
    }

    public void createScores(ScoresDB score, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, score.getId());
        values.put(DIGITS, score.getDigits());

        db.insert(TABLE_SCORES, null, values);
    }

    public void createForward(ForwardDB forward, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_ID, forward.getId());
        values.put(DIVE_NAME, forward.getDiveName());
        values.put(ONE_S, forward.getOneA());
        values.put(ONE_P, forward.getOneB());
        values.put(ONE_T, forward.getOneC());
        values.put(ONE_F, forward.getOneD());
        values.put(THREE_S, forward.getThreeA());
        values.put(THREE_P, forward.getThreeB());
        values.put(THREE_T, forward.getThreeC());
        values.put(THREE_F, forward.getThreeD());

        db.insert(TABLE_FORWARD, null, values);
    }

	public void createTwist(TwistDB twist, SQLiteDatabase db) {
			
		ContentValues values = new ContentValues();
		values.put(KEY_ID, twist.getId());
		values.put(DIVE_NAME, twist.getDiveName());
		values.put(ONE_S, twist.getOneA());
		values.put(ONE_P, twist.getOneB());
		values.put(ONE_T, twist.getOneC());
		values.put(ONE_F, twist.getOneD());
		values.put(THREE_S, twist.getThreeA());
		values.put(THREE_P, twist.getThreeB());
		values.put(THREE_T, twist.getThreeC());
		values.put(THREE_F, twist.getThreeD());
			
		db.insert(TABLE_TWIST, null, values);
	}
		
	public void createBack(BackDB back, SQLiteDatabase db) {
			
		ContentValues values = new ContentValues();
		values.put(KEY_ID, back.getId());
		values.put(DIVE_NAME, back.getDiveName());
		values.put(ONE_S, back.getOneA());
		values.put(ONE_P, back.getOneB());
		values.put(ONE_T, back.getOneC());
		values.put(ONE_F, back.getOneD());
		values.put(THREE_S, back.getThreeA());
		values.put(THREE_P, back.getThreeB());
		values.put(THREE_T, back.getThreeC());
		values.put(THREE_F, back.getThreeD());
			
		db.insert(TABLE_BACK, null, values);
	}
		
	public void createInward(InwardDB inward, SQLiteDatabase db) {
			
		ContentValues values = new ContentValues();
		values.put(KEY_ID, inward.getId());
		values.put(DIVE_NAME, inward.getDiveName());
		values.put(ONE_S, inward.getOneA());
		values.put(ONE_P, inward.getOneB());
		values.put(ONE_T, inward.getOneC());
		values.put(ONE_F, inward.getOneD());
		values.put(THREE_S, inward.getThreeA());
		values.put(THREE_P, inward.getThreeB());
		values.put(THREE_T, inward.getThreeC());
		values.put(THREE_F, inward.getThreeD());
			
		db.insert(TABLE_INWARD, null, values);
	}
		
	public void createReverse(ReverseDB reverse, SQLiteDatabase db) {
			
		ContentValues values = new ContentValues();
		values.put(KEY_ID, reverse.getId());
		values.put(DIVE_NAME, reverse.getDiveName());
		values.put(ONE_S, reverse.getOneA());
		values.put(ONE_P, reverse.getOneB());
		values.put(ONE_T, reverse.getOneC());
		values.put(ONE_F, reverse.getOneD());
		values.put(THREE_S, reverse.getThreeA());
		values.put(THREE_P, reverse.getThreeB());
		values.put(THREE_T, reverse.getThreeC());
		values.put(THREE_F, reverse.getThreeD());
			
		db.insert(TABLE_REVERSE, null, values);
	}

	private void fillDives(SQLiteDatabase db){
			
		DivesDB dive1 = new DivesDB(1, "Forward Dives");
		DivesDB dive2 = new DivesDB(2, "Back Dives");
		DivesDB dive3 = new DivesDB(3, "Reverse Dives");
		DivesDB dive4 = new DivesDB(4, "Inward Dives");
		DivesDB dive5 = new DivesDB(5, "Twist Dives");
			
		createDives(dive1, db);
		createDives(dive2, db);
		createDives(dive3, db);
		createDives(dive4, db);
		createDives(dive5, db);
	}

    private void fillScores(SQLiteDatabase db){
        ScoresDB score1 = new ScoresDB(1, "0.0");
        ScoresDB score2 = new ScoresDB(2, "0.5");
        ScoresDB score3 = new ScoresDB(3, "1.0");
        ScoresDB score4 = new ScoresDB(4, "1.5");
        ScoresDB score5 = new ScoresDB(5, "2.0");
        ScoresDB score6 = new ScoresDB(6, "2.5");
        ScoresDB score7 = new ScoresDB(7, "3.0");
        ScoresDB score8 = new ScoresDB(8, "3.5");
        ScoresDB score9 = new ScoresDB(9, "4.0");
        ScoresDB score10 = new ScoresDB(10, "4.5");
        ScoresDB score11 = new ScoresDB(11, "5.0");
        ScoresDB score12 = new ScoresDB(12,"5.5");
        ScoresDB score13 = new ScoresDB(13, "6.0");
        ScoresDB score14 = new ScoresDB(14, "6.5");
        ScoresDB score15 = new ScoresDB(15, "7.0");
        ScoresDB score16 = new ScoresDB(16, "7.5");
        ScoresDB score17 = new ScoresDB(17, "8.0");
        ScoresDB score18 = new ScoresDB(18, "8.5");
        ScoresDB score19 = new ScoresDB(19, "9.0");
        ScoresDB score20 = new ScoresDB(20, "9.5");
        ScoresDB score21 = new ScoresDB(21, "10");

        createScores(score1, db);
        createScores(score2, db);
        createScores(score3, db);
        createScores(score4, db);
        createScores(score5, db);
        createScores(score6, db);
        createScores(score7, db);
        createScores(score8, db);
        createScores(score9, db);
        createScores(score10, db);
        createScores(score11, db);
        createScores(score12, db);
        createScores(score13, db);
        createScores(score14, db);
        createScores(score15, db);
        createScores(score16, db);
        createScores(score17, db);
        createScores(score18, db);
        createScores(score19, db);
        createScores(score20, db);
        createScores(score21, db);
    }

    // fill dive tables with data
    private void fillForwardDives(SQLiteDatabase db) {

        ForwardDB forward1 = new ForwardDB(101, "Forward Dive", 1.4, 1.3, 1.2, 0.0, 1.6, 1.5, 1.4, 0.0);
        ForwardDB forward2 = new ForwardDB(102, "Forward Somersault", 1.6, 1.5, 1.4, 0.0, 1.7, 1.6, 1.5, 0.0);
        ForwardDB forward3 = new ForwardDB(103, "Forward 1 1/2 Somersault", 0.0, 1.7, 1.6, 0.0, 1.9, 1.6, 1.5, 0.0);
        ForwardDB forward4 = new ForwardDB(104, "Forward Double Somersault", 0.0, 2.3, 2.2, 0.0, 0.0, 2.1, 2.0, 0.0);
        ForwardDB forward5 = new ForwardDB(105, "Forward 2 1/2 Somersault", 0.0, 2.6, 2.4, 0.0, 0.0, 2.4, 2.2, 0.0);
        ForwardDB forward6 = new ForwardDB(106, "Forward Triple Somersault", 0.0, 0.0, 2.9, 0.0, 0.0, 2.8, 2.5, 0.0);
        ForwardDB forward7 = new ForwardDB(107, "Forward 3 1/2 Somersault", 0.0, 0.0, 3.0, 0.0, 0.0, 3.1, 2.8, 0.0);
        ForwardDB forward8 = new ForwardDB(109, "Forward 4 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.5, 0.0);
        ForwardDB forward9 = new ForwardDB(112, "Forward Flying Somersault", 0.0, 1.7, 1.6, 0.0, 0.0, 1.8, 1.7, 0.0);
        ForwardDB forward10 = new ForwardDB(113, "Forward Flying 1 1/2 Somersault", 0.0, 1.9, 1.8, 0.0, 0.0, 1.8, 1.7, 0.0);
        ForwardDB forward11 = new ForwardDB(115, "Forward Flying 2 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.5, 0.0);

        createForward(forward1, db);
        createForward(forward2, db);
        createForward(forward3, db);
        createForward(forward4, db);
        createForward(forward5, db);
        createForward(forward6, db);
        createForward(forward7, db);
        createForward(forward8, db);
        createForward(forward9, db);
        createForward(forward10, db);
        createForward(forward11, db);
    }

	private void fillBackDives(SQLiteDatabase db) {
		
		BackDB back1 = new BackDB(201, "Back Dive", 1.7, 1.6, 1.5, 0.0, 1.9, 1.8, 1.7, 0.0);
		BackDB back2 = new BackDB(202, "Back Somersault", 1.7, 1.6, 1.5, 0.0, 1.8, 1.7, 1.6, 0.0);
		BackDB back3 = new BackDB(203, "Back 1 1/2 Somersault", 2.5, 2.3, 2.0, 0.0, 2.4, 2.2, 1.9, 0.0);
		BackDB back4 = new BackDB(204, "Back Double Somersault", 0.0, 2.5, 2.2, 0.0, 2.5, 2.3, 2.0, 0.0);
		BackDB back5 = new BackDB(205, "Back 2 1/2 Somersault", 0.0, 3.2, 3.0, 0.0, 0.0, 3.0, 2.8, 0.0);
		BackDB back6 = new BackDB(207, "Back 3 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.4, 0.0);
		BackDB back7 = new BackDB(212, "Back Flying Somersault", 0.0, 1.7, 1.6, 0.0, 0.0, 1.8, 1.7, 0.0);
		BackDB back8 = new BackDB(213, "Back Flying 1 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.1, 0.0);
			
		createBack(back1, db);
		createBack(back2, db);
		createBack(back3, db);
		createBack(back4, db);
		createBack(back5, db);
		createBack(back6, db);
		createBack(back7, db);
		createBack(back8, db);
	}

		private void fillReverseDives(SQLiteDatabase db) {
			
		ReverseDB reverse1 = new ReverseDB(301, "Reverse Dive", 1.8, 1.7, 1.6, 0.0, 2.0, 1.9, 1.8, 0.0);
		ReverseDB reverse2 = new ReverseDB(302, "Reverse Somersault", 1.8, 1.7, 1.6, 0.0, 1.9, 1.8, 1.7, 0.0);
		ReverseDB reverse3 = new ReverseDB(303, "Reverse 1 1/2 Somersault", 2.7, 2.4, 2.1, 0.0, 2.6, 2.3, 2.0, 0.0);
		ReverseDB reverse4 = new ReverseDB(304, "Reverse Double Somersault", 0.0, 2.6, 2.3, 0.0, 0.0, 3.0, 2.8, 0.0);
		ReverseDB reverse5 = new ReverseDB(305, "Reverse 2 1/2 Somersault", 0.0, 3.2, 3.0, 0.0, 0.0, 3.0, 2.8, 0.0);
		ReverseDB reverse6 = new ReverseDB(307, "Reverse 3 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.5, 0.0);
		ReverseDB reverse7 = new ReverseDB(312, "Reverse Flying Somersault", 0.0, 1.8, 1.7, 0.0, 0.0, 0.0, 1.8, 0.0);
		ReverseDB reverse8 = new ReverseDB(313, "Reverse Flying 1 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.2, 0.0);
		
		createReverse(reverse1, db);
		createReverse(reverse2, db);
		createReverse(reverse3, db);
		createReverse(reverse4, db);
		createReverse(reverse5, db);
		createReverse(reverse6, db);
		createReverse(reverse7, db);
		createReverse(reverse8, db);
	}
		
		private void fillInwardDives(SQLiteDatabase db) {
			
		InwardDB inward1 = new InwardDB(401, "Inward Dive", 1.8, 1.5, 1.4, 0.0, 1.7, 1.4, 1.3, 0.0);
		InwardDB inward2 = new InwardDB(402, "Inward Somersault", 0.0, 1.7, 1.6, 0.0, 0.0, 1.5, 1.4, 0.0);
		InwardDB inward3 = new InwardDB(403, "Inward 1 1/2 Somersault", 0.0, 2.4, 2.2, 0.0, 0.0, 2.1, 1.9, 0.0);
		InwardDB inward4 = new InwardDB(404, "Inward Double Somersault", 0.0, 0.0, 2.8, 0.0, 0.0, 2.6, 2.4, 0.0);
		InwardDB inward5 = new InwardDB(405, "Inward 2 1/2 Somersault", 0.0, 3.4, 3.1, 0.0, 0.0, 3.0, 2.7, 0.0);
		InwardDB inward6 = new InwardDB(407, "Inward 3 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.4, 0.0);
		InwardDB inward7 = new InwardDB(412, "Inward Flying Somersault", 0.0, 2.1, 2.0, 0.0, 0.0, 1.9, 1.8, 0.0);
		InwardDB inward8 = new InwardDB(413, "Inward Flying 1 1/2 Somersault", 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 2.4, 0.0);
		
		createInward(inward1, db);
		createInward(inward2, db);
		createInward(inward3, db);
		createInward(inward4, db);
		createInward(inward5, db);
		createInward(inward6, db);
		createInward(inward7, db);
		createInward(inward8, db);
	}

		private void fillTwistDives(SQLiteDatabase db) {
			
		TwistDB twist1 = new TwistDB(5111, "Forward Dive 1/2 Twist", 1.8, 1.7, 0.0, 0.0, 2.0, 1.9, 0.0, 0.0);
		TwistDB twist2 = new TwistDB(5112, "Forward Dive 1 Twist", 2.0, 1.9, 0.0, 0.0, 2.2, 2.1, 0.0, 0.0);
		TwistDB twist3 = new TwistDB(5121, "Forward Somersault 1/2 Twist", 1.9, 1.8, 0.0, 1.7, 2.0, 1.9, 0.0, 0.0);
		TwistDB twist4 = new TwistDB(5122, "Forward Somersault 1 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 2.0);
		TwistDB twist5 = new TwistDB(5124, "Forward Somersault 2 Twists", 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist6 = new TwistDB(5126, "Forward Somersault 3 Twists", 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist7 = new TwistDB(5131, "Forward 1 1/2 Somersault 1/2 Twist", 0.0, 2.1, 2.0, 0.0, 0.0, 2.0, 1.9, 0.0);
		TwistDB twist8 = new TwistDB(5132, "Forward 1 1/2 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.1);
		TwistDB twist9 = new TwistDB(5134, "Forward 1 1/2 Somersault 2 Twists", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5);
		TwistDB twist10 = new TwistDB(5136, "Forward 1 1/2 Somersault 3 Twists", 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 2.9);
		TwistDB twist11 = new TwistDB(5138, "Forward 1 1/2 Somersault 4 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.3);
		TwistDB twist12 = new TwistDB(5152, "Forward 2 1/2 Somersault 1 Twists", 0.0, 3.2, 3.0, 0.0, 0.0, 3.0, 2.8, 2.8);
		TwistDB twist13 = new TwistDB(5154, "Forward 2 1/2 Somersault 2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.4, 3.2, 3.2);
		TwistDB twist14 = new TwistDB(5211, "Back Dive 1/2 Twist", 1.8, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0);
		TwistDB twist15 = new TwistDB(5212, "Back Dive 1 Twist", 2.0, 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0);
		TwistDB twist16 = new TwistDB(5221, "Back Somersault 1/2 Twist", 0.0, 0.0, 0.0, 0.0, 1.7, 0.0, 0.0, 0.0);
		TwistDB twist17 = new TwistDB(5222, "Back Somersault 1 Twist", 0.0, 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0);
		TwistDB twist18 = new TwistDB(5223, "Back Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0);
		TwistDB twist19 = new TwistDB(5225, "Back Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0);
		TwistDB twist20 = new TwistDB(5227, "Back Somersault 3 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.2);
		TwistDB twist21 = new TwistDB(5231, "Back 1 1/2 Somersault 1/2 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.0);
		TwistDB twist22 = new TwistDB(5233, "Back 1 1/2 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist23 = new TwistDB(5235, "Back 1 1/2 Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.8);
		TwistDB twist24 = new TwistDB(5237, "Back 1 1/2 Somersault 3 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.2);
		TwistDB twist25 = new TwistDB(5239, "Back 1 1/2 Somersault 4 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.6);
		TwistDB twist26 = new TwistDB(5251, "Back 2 1/2 Somersault 1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 3.1, 2.9, 2.7);
		TwistDB twist27 = new TwistDB(5253, "Back 2 1/2 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.5, 3.3, 3.1);
		TwistDB twist28 = new TwistDB(5311, "Reverse Dive 1/2 Twist", 1.9, 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0);
		TwistDB twist29 = new TwistDB(5312, "Reverse Dive 1 Twist", 2.1, 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0);
		TwistDB twist30 = new TwistDB(5321, "Reverse Somersault 1/2 Twist", 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist31 = new TwistDB(5322, "Reverse Somersault 1 Twist", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist32 = new TwistDB(5323, "Reverse Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist33 = new TwistDB(5325, "Reverse Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist34 = new TwistDB(5331, "Reverse 1 1/2 Somersault 1/2 Twist", 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.1);
		TwistDB twist35 = new TwistDB(5333, "Reverse 1 1/2 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5);
		TwistDB twist36 = new TwistDB(5335, "Reverse 1 1/2 Somersault 2 1/2 Twist", 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 2.9);
		TwistDB twist37 = new TwistDB(5337, "Reverse 1 1/2 Somersault 3 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.3);
		TwistDB twist38 = new TwistDB(5351, "Reverse 2 1/2 Somersault 1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 3.1, 2.9, 2.7);
		TwistDB twist39 = new TwistDB(5353, "Reverse 2 1/2 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.5, 3.3, 3.1);
		TwistDB twist40 = new TwistDB(5371, "Reverse 3 1/2 Somersault 1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.5, 3.6);
		TwistDB twist41 = new TwistDB(5411, "Inward Dive 1/2 Twist", 2.0, 1.7, 0.0, 0.0, 1.9, 1.6, 0.0, 0.0);
		TwistDB twist42 = new TwistDB(5412, "Inward Dive 1 Twist", 2.2, 1.9, 0.0, 0.0, 2.1, 1.8, 0.0, 0.0);
		TwistDB twist43 = new TwistDB(5421, "Inward Somersault 1/2 Twist", 0.0, 1.8, 1.7, 0.0, 0.0, 1.6, 1.5, 0.0);
		TwistDB twist44 = new TwistDB(5422, "Inward Somersault 1 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 0.0);
		TwistDB twist45 = new TwistDB(5432, "Inward 1 1/2 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist46 = new TwistDB(5434, "Inward 1 1/2 Somersault 2 Twists", 0.0, 0.0, 0.0, 3.1, 0.0, 0.0, 0.0, 2.8);
		
		createTwist(twist1, db);
		createTwist(twist2, db);
		createTwist(twist3, db);
		createTwist(twist4, db);
		createTwist(twist5, db);
		createTwist(twist6, db);
		createTwist(twist7, db);
		createTwist(twist8, db);
		createTwist(twist9, db);
		createTwist(twist10, db);
		createTwist(twist11, db);
		createTwist(twist12, db);
		createTwist(twist13, db);
		createTwist(twist14, db);
		createTwist(twist15, db);
		createTwist(twist16, db);
		createTwist(twist17, db);
		createTwist(twist18, db);
		createTwist(twist19, db);
		createTwist(twist20, db);
		createTwist(twist21, db);
		createTwist(twist22, db);
		createTwist(twist23, db);
		createTwist(twist24, db);
		createTwist(twist25, db);
		createTwist(twist26, db);
		createTwist(twist27, db);
		createTwist(twist28, db);
		createTwist(twist29, db);
		createTwist(twist30, db);
		createTwist(twist31, db);
		createTwist(twist32, db);
		createTwist(twist33, db);
		createTwist(twist34, db);
		createTwist(twist35, db);
		createTwist(twist36, db);
		createTwist(twist37, db);
		createTwist(twist38, db);
		createTwist(twist39, db);
		createTwist(twist40, db);
		createTwist(twist41, db);
		createTwist(twist42, db);
		createTwist(twist43, db);
		createTwist(twist44, db);
		createTwist(twist45, db);
		createTwist(twist46, db);
	}
}
