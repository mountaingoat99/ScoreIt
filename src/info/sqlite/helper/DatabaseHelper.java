package info.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.sqlite.model.ArmstandPlatformDB;
import info.sqlite.model.BackDB;
import info.sqlite.model.BackPlatformDB;
import info.sqlite.model.DivesDB;
import info.sqlite.model.ForwardDB;
import info.sqlite.model.FrontPlatformDB;
import info.sqlite.model.InwardDB;
import info.sqlite.model.InwardPlatformDB;
import info.sqlite.model.PlatformDivesDB;
import info.sqlite.model.QuickScoreDB;
import info.sqlite.model.ReverseDB;
import info.sqlite.model.ReversePlatformDB;
import info.sqlite.model.ScoresDB;
import info.sqlite.model.TwistDB;
import info.sqlite.model.TwistPlatformDB;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	// logcat tag
	protected static final String LOG = "DatabaseHelper";
	
	// Database Version
	private static final int DATABASE_VERSION = 2;
	
	// Database name
	private static final String DATABASE_NAME = "DIVE_DOD";
	
	// Table Names
	protected static final String TABLE_FORWARD = "forward_dives";
	protected static final String TABLE_BACK = "back_dives";
	protected static final String TABLE_INWARD = "inward_dives";
	protected static final String TABLE_REVERSE = "reverse_dives";
	protected static final String TABLE_TWIST = "twist_dives";

    protected static final String TABLE_PLATFORM_FRONT = "platform_front_dives";
    protected static final String TABLE_PLATFORM_BACK = "platform_back_dives";
    protected static final String TABLE_PLATFORM_INWARD = "platform_inward_dives";
    protected static final String TABLE_PLATFORM_REVERSE = "platform_reverse_dives";
    protected static final String TABLE_PLATFORM_TWIST = "platform_twist_dives";
    protected static final String TABLE_PLATFORM_ARMSTAND = "platform_armstand_dives";

    protected static final String TABLE_PLATFORM_DIVES = "platform_dives";

	protected static final String TABLE_DIVES = "dives";
    protected static final String TABLE_DIVER_NAME = "diver";
    protected static final String TABLE_MEET_NAME = "meet";
    protected static final String TABLE_RESULTS = "results";
    protected static final String TABLE_DIVE_TOTALS = "dive_total";
    protected static final String TABLE_DIVE_TYPE = "dive_type";
    protected static final String TABLE_SCORES = "scores";
    protected static final String TABLE_JUDGE_SCORES = "judge_scores";
    protected static final String TABLE_DIVE_NUMBER = "dive_number";

    // version 2 database
    protected static final String TABLE_DIVE_LIST = "dive_list";
    protected static final String TABLE_QUICK_SCORE = "quick_score";

	// common column names
	protected static final String KEY_ID = "id";
    protected static final String MEET_ID = "meet_id";
    protected static final String DIVER_ID = "diver_id";
	protected static final String DIVE_NAME = "name";
    protected static final String THREE_METER = "three_meter";
    protected static final String ONE_METER = "one_meter";
    protected static final String TEN_METER = "ten_meter";
    protected static final String SEVEN_FIVE_METER = "seven_five_meter";
    protected static final String FIVE_METER = "five_meter";
    protected static final String ONE_S = "oneS";
    protected static final String ONE_P = "oneP";
    protected static final String ONE_T = "oneT";
    protected static final String ONE_F = "oneF";
    protected static final String THREE_S = "threeS";
    protected static final String THREE_P = "threeP";
    protected static final String THREE_T = "threeT";
    protected static final String THREE_F = "threeF";
    protected static final String TEN_S = "tenS";
    protected static final String TEN_P = "tenP";
    protected static final String TEN_T = "tenT";
    protected static final String TEN_F = "tenF";
    protected static final String SEVEN_FIVE_S = "seven_fiveS";
    protected static final String SEVEN_FIVE_P = "seven_fiveP";
    protected static final String SEVEN_FIVE_T = "seven_fiveT";
    protected static final String SEVEN_FIVE_F = "seven_fiveF";
    protected static final String FIVE_S = "fiveS";
    protected static final String FIVE_P = "fiveP";
    protected static final String FIVE_T = "fiveT";
    protected static final String FIVE_F = "fiveF";
	
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
    protected static final String TOTAL_SCORE = "total_score";

    //dive totals columns names
    protected static final String DIVE_COUNT = "dive_count";

    //dive type column names
    protected static final String DIVE_TYPE = "type";

    // judges scores values tables
    protected static final String DIGITS = "digits";

    // judge scores table
    protected static final String DIVE_CATEGORY = "dive_category";
    protected static final String DIVE_TYPE_NAME = "dive_type_name";
    protected static final String DIVE_POSITION = "dive_position";
    protected static final String DIVE_NUMBER = "dive_number";
    protected static final String FAILED_DIVE = "failed";
    protected static final String SCORE_1 = "score_1";
    protected static final String SCORE_2 = "score_2";
    protected static final String SCORE_3 = "score_3";
    protected static final String SCORE_4 = "score_4";
    protected static final String SCORE_5 = "score_5";
    protected static final String SCORE_6 = "score_6";
    protected static final String SCORE_7 = "score_7";
    protected static final String MULTIPLIER = "multiplier";

    // version 2 database changes
    // dive list table
    protected static final String LIST_FILLED = "list_filled";
    protected static final String NO_LIST = "no_list";

    // quick score table
    protected static final String NAME_MEET = "name_meet";

    public static String getTableQuickScore() {
        return TABLE_QUICK_SCORE;
    }
    public static String getNameMeet() {
        return NAME_MEET;
    }
    public static String getListFilled() {
        return LIST_FILLED;
    }
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
    public static String getTablePlatformFront() {
        return TABLE_PLATFORM_FRONT;
    }

    public static String getTablePlatformBack() {
        return TABLE_PLATFORM_BACK;
    }

    public static String getTablePlatformInward() {
        return TABLE_PLATFORM_INWARD;
    }

    public static String getTablePlatformReverse() {
        return TABLE_PLATFORM_REVERSE;
    }

    public static String getTablePlatformTwist() {
        return TABLE_PLATFORM_TWIST;
    }

    public static String getTablePlatformArmstand() {
        return TABLE_PLATFORM_ARMSTAND;
    }

    public static String getTableArmstandDives() {
        return TABLE_PLATFORM_DIVES;
    }

    public static String getTableDiveList() {
        return TABLE_DIVE_LIST;
    }

    public static String getTenMeter() {
        return TEN_METER;
    }

    public static String getSevenFiveMeter() {
        return SEVEN_FIVE_METER;
    }

    public static String getFiveMeter() {
        return FIVE_METER;
    }

    public static String getTenS() {
        return TEN_S;
    }

    public static String getTenP() {
        return TEN_P;
    }

    public static String getTenT() {
        return TEN_T;
    }

    public static String getTenF() {
        return TEN_F;
    }

    public static String getSevenFiveS() {
        return SEVEN_FIVE_S;
    }

    public static String getSevenFiveP() {
        return SEVEN_FIVE_P;
    }

    public static String getSevenFiveT() {
        return SEVEN_FIVE_T;
    }

    public static String getSevenFiveF() {
        return SEVEN_FIVE_F;
    }

    public static String getFiveS() {
        return FIVE_S;
    }

    public static String getFiveP() {
        return FIVE_P;
    }

    public static String getFiveT() {
        return FIVE_T;
    }

    public static String getFiveF() {
        return FIVE_F;
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
    public static String getThreeMeter() { return THREE_METER; }
    public static String getOneMeter() { return ONE_METER; }
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
    public static String getDiveCategory() { return DIVE_CATEGORY; }
    public static String getDiveTypeName() { return DIVE_TYPE_NAME; }
    public static String getDivePosition() { return DIVE_POSITION;  }
    public static String getDiveNumber() { return DIVE_NUMBER; }
    public static String getFailedDive() { return FAILED_DIVE; }
    public static String getScore1() { return SCORE_1; }
    public static String getScore2() { return SCORE_2; }
    public static String getScore3() { return SCORE_3; }
    public static String getScore4() { return SCORE_4; }
    public static String getScore5() { return SCORE_5; }
    public static String getScore6() { return SCORE_6; }
    public static String getScore7() { return SCORE_7; }
    public static String getMultiplier() { return MULTIPLIER; }

    public static String getTotalScore() {
		return TOTAL_SCORE;
	}
    public static String getTableDiveNumber() { return TABLE_DIVE_NUMBER; }
    public static String getLog() {
		return LOG;
	}

	// table create statements
	public static final String CREATE_TABLE_FORWARD = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_FORWARD + "(" + KEY_ID + " INTEGER, "
            + ONE_METER + " TEXT, " + THREE_METER + " TEXT, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";

	public static final String CREATE_TABLE_BACK = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_BACK + "(" + KEY_ID + " INTEGER, "
            + ONE_METER + " TEXT, " + THREE_METER + " TEXT, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_INWARD = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_INWARD + "(" + KEY_ID + " INTEGER, "
            + ONE_METER + " TEXT, " + THREE_METER + " TEXT, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_REVERSE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_REVERSE + "(" + KEY_ID + " INTEGER, "
            + ONE_METER + " TEXT, " + THREE_METER + " TEXT, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";
	
	public static final String CREATE_TABLE_TWIST = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_TWIST + "(" + KEY_ID + " INTEGER, "
            + ONE_METER + " TEXT, " + THREE_METER + " TEXT, "
			+ DIVE_NAME + " TEXT, " + ONE_S + " REAL, " + ONE_P + " REAL, "
			+ ONE_T + " REAL, " + ONE_F + " REAL, " + THREE_S + " REAL, "
			+ THREE_P + " REAL, " + THREE_T + " REAL, " + THREE_F + " REAL "
			+ ")";

    public static final String CREATE_TABLE_PLATFORM_FRONT = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_FRONT + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_PLATFORM_BACK = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_BACK + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_PLATFORM_REVERSE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_REVERSE + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_PLATFORM_INWARD = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_INWARD + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_PLATFORM_TWIST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_TWIST + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_PLATFORM_ARMSTAND = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_ARMSTAND + "(" + KEY_ID + " INTEGER, "
            + TEN_METER + " TEXT, " + SEVEN_FIVE_METER + " TEXT, " + FIVE_METER + " TEXT, "
            + DIVE_NAME + " TEXT, " + TEN_S + " REAL, " + TEN_P + " REAL, "
            + TEN_T + " REAL, " + TEN_F + " REAL, " + SEVEN_FIVE_S + " REAL, "
            + SEVEN_FIVE_P + " REAL, " + SEVEN_FIVE_T + " REAL, " + SEVEN_FIVE_F + " REAL, "
            + FIVE_S + " REAL, " + FIVE_P + " REAL, " + FIVE_T + " REAL, " + FIVE_F + " REAL "
            + ")";

    public static final String CREATE_TABLE_DIVES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_DIVES + "(" + KEY_ID + " INTEGER, " 
			+ DIVE_NAME + " TEXT " + ")";

    public static final String CREATE_TABLE_PLATFORM_DIVES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PLATFORM_DIVES + "(" + KEY_ID + " INTEGER, "
            + DIVE_NAME + " TEXT " + ")";

    public static final String CREATE_TABLE_SCORES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SCORES + "(" + KEY_ID + " INTEGER, "
            + DIGITS + " TEXT " + ")";
	
	public static final String CREATE_TABLE_DIVER_NAME = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_DIVER_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ DIVER_NAME + " TEXT, " + DIVER_AGE + " INTEGER, " + DIVER_GRADE + " TEXT, "
			+ DIVER_SCHOOL + " TEXT " + ")";
	
	public static final String CREATE_TABLE_MEET_NAME = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_MEET_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEET_NAME + " TEXT, " + MEET_SCHOOL + " TEXT, " + MEET_CITY + " TEXT, "
			+ MEET_STATE + " TEXT, " + MEET_DATE + " TEXT, " + MEET_JUDGES + " INT "
			+ ")";
	
	public static final String CREATE_TABLE_RESULTS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_RESULTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ MEET_ID + " INTEGER, " + DIVER_ID + " INTEGER, "
			+ DIVE_1 + " TEXT, " + DIVE_2 + " TEXT, " + DIVE_3 + " TEXT, "
			+ DIVE_4 + " TEXT, " + DIVE_5 + " TEXT, " + DIVE_6 + " TEXT, "
			+ DIVE_7 + " TEXT, " + DIVE_8 + " TEXT, " + DIVE_9 + " TEXT, "
			+ DIVE_10 + " TEXT, " + DIVE_11 + " TEXT, " +  TOTAL_SCORE + " REAL, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";


    public static final String CREATE_TABLE_DIVE_TOTAL = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DIVE_TOTALS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + DIVE_COUNT + " INTEGER, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_DIVE_TYPE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DIVE_TYPE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + DIVE_TYPE + " INTEGER, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_JUDGE_SCORES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_JUDGE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, " + DIVE_NUMBER + " INTEGER, "
            + DIVE_CATEGORY + " TEXT, " + DIVE_TYPE_NAME + " TEXT, "
            + DIVE_POSITION + " TEXT, " + FAILED_DIVE + " TEXT, " + TOTAL_SCORE + " TEXT, "
            + SCORE_1 + " TEXT, " + SCORE_2 + " TEXT, " + SCORE_3 + " TEXT, "
            + SCORE_4 + " TEXT, " + SCORE_5 + " TEXT, " + SCORE_6 + " TEXT, " + SCORE_7 + " TEXT, "
            + MULTIPLIER + " TEXT, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_DIVE_NUMBER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DIVE_NUMBER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + DIVE_NUMBER + " INTEGER, " + DIVE_TYPE + " REAL, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    // Version 2 database changes
    public static final String CREATE_TABLE_DIVE_LIST = "CREATE TABLE IF NOT EXISTS "
            + TABLE_DIVE_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEET_ID + " INTEGER, "  + DIVER_ID + " INTEGER, "
            + LIST_FILLED + " INTEGER, " + NO_LIST + " INTEGER, "
            + "FOREIGN KEY (" + MEET_ID + ") REFERENCES " + TABLE_MEET_NAME + " (id), "
            + "FOREIGN KEY (" + DIVER_ID + ") REFERENCES " + TABLE_DIVER_NAME + " (id))";

    public static final String CREATE_TABLE_QUICK_SCORE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_QUICK_SCORE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_MEET + " TEXT, "
            + DIVE_1 + " TEXT, " + DIVE_2 + " TEXT, " + DIVE_3 + " TEXT, "
            + DIVE_4 + " TEXT, " + DIVE_5 + " TEXT, " + DIVE_6 + " TEXT, "
            + DIVE_7 + " TEXT, " + DIVE_8 + " TEXT, " + DIVE_9 + " TEXT, "
            + DIVE_10 + " TEXT, " + DIVE_11 + " TEXT, " +  TOTAL_SCORE + " REAL " + ")";

    //---------------Triggers---------------------------------------------------------------------//
    public static final String DIVER_DELETE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS diver_delete_trigger "
            + "BEFORE DELETE ON " + TABLE_DIVER_NAME + " FOR EACH ROW BEGIN "
            + "DELETE FROM " + TABLE_DIVE_NUMBER + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_LIST + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_JUDGE_SCORES + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_RESULTS + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TOTALS + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_TYPE + " WHERE " + DIVER_ID + " = old." + KEY_ID + "; END";

    public static final String MEET_DELETE_TRIGGER = "CREATE TRIGGER IF NOT EXISTS meet_delete_trigger "
            + "BEFORE DELETE ON " + TABLE_MEET_NAME + " FOR EACH ROW BEGIN "
            + "DELETE FROM " + TABLE_DIVE_NUMBER + " WHERE " + MEET_ID + " = old." + KEY_ID + "; "
            + "DELETE FROM " + TABLE_DIVE_LIST + " WHERE " + MEET_ID + " = old." + KEY_ID + "; "
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

		db.execSQL(CREATE_TABLE_PLATFORM_BACK);
		db.execSQL(CREATE_TABLE_PLATFORM_FRONT);
		db.execSQL(CREATE_TABLE_PLATFORM_REVERSE);
		db.execSQL(CREATE_TABLE_PLATFORM_INWARD);
		db.execSQL(CREATE_TABLE_PLATFORM_TWIST);
		db.execSQL(CREATE_TABLE_PLATFORM_ARMSTAND);

		db.execSQL(CREATE_TABLE_DIVES);
		db.execSQL(CREATE_TABLE_PLATFORM_DIVES);

        db.execSQL(CREATE_TABLE_SCORES);
		db.execSQL(CREATE_TABLE_DIVER_NAME);
		db.execSQL(CREATE_TABLE_MEET_NAME);
		db.execSQL(CREATE_TABLE_RESULTS);
        db.execSQL(CREATE_TABLE_DIVE_TOTAL);
        db.execSQL(CREATE_TABLE_DIVE_TYPE);
        db.execSQL(CREATE_TABLE_JUDGE_SCORES);
        db.execSQL(CREATE_TABLE_DIVE_NUMBER);
        db.execSQL(CREATE_TABLE_DIVE_LIST);
        db.execSQL(CREATE_TABLE_QUICK_SCORE);
        db.execSQL((DIVER_DELETE_TRIGGER));
        db.execSQL((MEET_DELETE_TRIGGER));

		// call the methods to fill the dive table data
        fillForwardDives(db);
        fillBackDives(db);
        fillInwardDives(db);
        fillReverseDives(db);
        fillTwistDives(db);

        fillBackPlatform(db);
        fillFrontPlatform(db);
        fillReversePlatform(db);
        fillInwardPlatform(db);
        fillTwistPlatform(db);
        fillArmstand(db);

        fillDives(db);
        fillPlatformDives(db);
        fillScores(db);

        fillSampleQuickScore(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion){
            case 2:
                db.execSQL(CREATE_TABLE_DIVE_LIST);
                db.execSQL(CREATE_TABLE_QUICK_SCORE);
                fillSampleQuickScore(db);
                break;
            default:
                throw new IllegalStateException(
                        "onUpgrade() with unknown new Version" + newVersion);
        }

//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BACK);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORWARD);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INWARD);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVERSE);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWIST);
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_BACK);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_FRONT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_INWARD);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_REVERSE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_TWIST);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_ARMSTAND);
//
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVES);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATFORM_DIVES);
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVER_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEET_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVE_TOTALS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVE_TYPE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JUDGE_SCORES);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVE_NUMBER);
//        db.execSQL("DROP TABLE IF EXISTS " + DIVER_DELETE_TRIGGER);
//        db.execSQL("DROP TABLE IF EXISTS " + MEET_DELETE_TRIGGER);
	}

    public void createDives(DivesDB dives, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(KEY_ID, dives.getId());
        values.put(DIVE_NAME, dives.getDiveName());

        db.insert(TABLE_DIVES, null, values);
    }

    public void createPlatformDives(PlatformDivesDB dives, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(KEY_ID, dives.getId());
        values.put(DIVE_NAME, dives.getDiveName());

        db.insert(TABLE_PLATFORM_DIVES, null, values);
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
        values.put(ONE_METER, forward.getOneMeter());
        values.put(THREE_METER, forward.getThreeMeter());
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
        values.put(ONE_METER, twist.getOneMeter());
        values.put(THREE_METER, twist.getThreeMeter());
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
        values.put(ONE_METER, back.getOneMeter());
        values.put(THREE_METER, back.getThreeMeter());
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
        values.put(ONE_METER, inward.getOneMeter());
        values.put(THREE_METER, inward.getThreeMeter());
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
        values.put(ONE_METER, reverse.getOneMeter());
        values.put(THREE_METER, reverse.getThreeMeter());
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

    public void createPlatformForward(FrontPlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_FRONT, null, values);
    }

    public void createPlatformBack(BackPlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_BACK, null, values);
    }

    public void createPlatformReverse(ReversePlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_REVERSE, null, values);
    }

    public void createPlatformInward(InwardPlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_INWARD, null, values);
    }

    public void createPlatformTwist(TwistPlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_TWIST, null, values);
    }

    public void createPlatformArmstand(ArmstandPlatformDB d, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(KEY_ID, d.getId());
        values.put(TEN_METER, d.getTenMeter());
        values.put(SEVEN_FIVE_METER, d.getSevenFiveMeter());
        values.put(FIVE_METER, d.getFiveMeter());
        values.put(DIVE_NAME, d.getDiveName());
        values.put(TEN_S, d.getTenA());
        values.put(TEN_P, d.getTenB());
        values.put(TEN_T, d.getTenC());
        values.put(TEN_F, d.getTenD());
        values.put(SEVEN_FIVE_S, d.getSevenFiveA());
        values.put(SEVEN_FIVE_P, d.getSevenFiveB());
        values.put(SEVEN_FIVE_T, d.getSevenFiveC());
        values.put(SEVEN_FIVE_F, d.getSevenFiveD());
        values.put(FIVE_S, d.getFiveA());
        values.put(FIVE_P, d.getFiveB());
        values.put(FIVE_T, d.getFiveC());
        values.put(FIVE_F, d.getFiveD());

        db.insert(TABLE_PLATFORM_ARMSTAND, null, values);
    }

    private void createQuickScore(QuickScoreDB quickscore, SQLiteDatabase db){
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
    }

    private void fillSampleQuickScore(SQLiteDatabase db){
        QuickScoreDB sheet = new QuickScoreDB("Sample Diver - Sample Meet", 28.0, 35.5, 32.5,
                                     30.0, 39.5, 24.5, 26.0, 41.5, 39.0, 22.0, 27.0, 345.5);

        createQuickScore(sheet, db);
    }

	private void fillDives(SQLiteDatabase db){
			
		DivesDB dive1 = new DivesDB(1, "Forward Group");
		DivesDB dive2 = new DivesDB(2, "Back Group");
		DivesDB dive3 = new DivesDB(3, "Reverse Group");
		DivesDB dive4 = new DivesDB(4, "Inward Group");
		DivesDB dive5 = new DivesDB(5, "Twisting Group");
			
		createDives(dive1, db);
		createDives(dive2, db);
		createDives(dive3, db);
		createDives(dive4, db);
		createDives(dive5, db);
	}

    private void fillPlatformDives(SQLiteDatabase db){

        PlatformDivesDB dive1 = new PlatformDivesDB(1, "Forward Group");
        PlatformDivesDB dive2 = new PlatformDivesDB(2, "Back Group");
        PlatformDivesDB dive3 = new PlatformDivesDB(3, "Reverse Group");
        PlatformDivesDB dive4 = new PlatformDivesDB(4, "Inward Group");
        PlatformDivesDB dive5 = new PlatformDivesDB(5, "Twisting Group");
        PlatformDivesDB dive6 = new PlatformDivesDB(6, "Armstand Group");

        createPlatformDives(dive1, db);
        createPlatformDives(dive2, db);
        createPlatformDives(dive3, db);
        createPlatformDives(dive4, db);
        createPlatformDives(dive5, db);
        createPlatformDives(dive6, db);
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

        ForwardDB forward1 = new ForwardDB(101, 1, 1, "Forward Dive", 1.4, 1.3, 1.2, 0.0, 1.6, 1.5, 1.4, 0.0);
        ForwardDB forward2 = new ForwardDB(102, 1, 1, "Forward 1 Somersault", 1.6, 1.5, 1.4, 0.0, 1.7, 1.6, 1.5, 0.0);
        ForwardDB forward3 = new ForwardDB(103, 1, 1, "Forward 1 1/2 Somersault", 2.0, 1.7, 1.6, 0.0, 1.9, 1.6, 1.5, 0.0);
        ForwardDB forward4 = new ForwardDB(104, 1, 1, "Forward Double Somersault", 2.6, 2.3, 2.2, 0.0, 2.4, 2.1, 2.0, 0.0);
        ForwardDB forward5 = new ForwardDB(105, 1, 1, "Forward 2 1/2 Somersault", 0.0, 2.6, 2.4, 0.0, 2.8, 2.4, 2.2, 0.0);
        ForwardDB forward6 = new ForwardDB(106, 1, 1, "Forward Triple Somersault", 0.0, 3.2, 2.9, 0.0, 0.0, 2.8, 2.5, 0.0);
        ForwardDB forward7 = new ForwardDB(107, 1, 1, "Forward 3 1/2 Somersault", 0.0, 3.3, 3.0, 0.0, 0.0, 3.1, 2.8, 0.0);
        ForwardDB forward12 = new ForwardDB(108, 1, 1, "Forward 4 Somersaults", 0.0, 0.0, 4.0, 0.0, 0.0, 3.8, 3.4, 0.0);
        ForwardDB forward8 = new ForwardDB(109, 1, 1, "Forward 4 1/2 Somersault", 0.0, 0.0, 4.3, 0.0, 0.0, 4.2, 3.8, 0.0);
        ForwardDB forward9 = new ForwardDB(112, 1, 1, "Forward Flying Somersault", 0.0, 1.7, 1.6, 0.0, 0.0, 1.8, 1.7, 0.0);
        ForwardDB forward10 = new ForwardDB(113, 1, 1, "Forward Flying 1 1/2 Somersault", 0.0, 1.9, 1.8, 0.0, 0.0, 1.8, 1.7, 0.0);
        ForwardDB forward11 = new ForwardDB(115, 0, 1, "Forward Flying 2 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 2.7, 2.5, 0.0);

        createForward(forward1, db);
        createForward(forward2, db);
        createForward(forward3, db);
        createForward(forward4, db);
        createForward(forward5, db);
        createForward(forward6, db);
        createForward(forward7, db);
        createForward(forward12, db);
        createForward(forward8, db);
        createForward(forward9, db);
        createForward(forward10, db);
        createForward(forward11, db);
    }

	private void fillBackDives(SQLiteDatabase db) {
		
		BackDB back1 = new BackDB(201, 1, 1, "Back Dive", 1.7, 1.6, 1.5, 0.0, 1.9, 1.8, 1.7, 0.0);
		BackDB back2 = new BackDB(202, 1, 1, "Back Somersault", 1.7, 1.6, 1.5, 0.0, 1.8, 1.7, 1.6, 0.0);
		BackDB back3 = new BackDB(203, 1, 1, "Back 1 1/2 Somersaults", 2.5, 2.3, 2.0, 0.0, 2.4, 2.2, 1.9, 0.0);
		BackDB back4 = new BackDB(204, 1, 1, "Back Double Somersaults", 0.0, 2.5, 2.2, 0.0, 2.5, 2.3, 2.0, 0.0);
		BackDB back5 = new BackDB(205, 1, 1, "Back 2 1/2 Somersaults", 0.0, 3.2, 3.0, 0.0, 0.0, 3.0, 2.8, 0.0);
		BackDB back6 = new BackDB(206, 1, 1, "Back 3 Somersaults", 0.0, 3.2, 2.9, 0.0, 0.0, 2.8, 2.5, 0.0);
		BackDB back7 = new BackDB(207, 0, 1, "Back 3 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 3.9, 3.6, 0.0);
		BackDB back12 = new BackDB(208, 0, 1, "Back 4 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 3.7, 3.4, 0.0);
		BackDB back8 = new BackDB(209, 0, 1, "Back 4 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 4.8, 4.5, 0.0);
		BackDB back9 = new BackDB(212, 1, 1, "Back Flying Somersault", 0.0, 1.7, 1.6, 0.0, 0.0, 1.8, 1.7, 0.0);
		BackDB back10 = new BackDB(213, 0, 1, "Back Flying 1 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.1, 0.0);
		BackDB back11 = new BackDB(215, 0, 1, "Back Flying 2 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 3.3, 3.1, 0.0);

		createBack(back1, db);
		createBack(back2, db);
		createBack(back3, db);
		createBack(back4, db);
		createBack(back5, db);
		createBack(back6, db);
		createBack(back7, db);
		createBack(back12, db);
		createBack(back8, db);
		createBack(back9, db);
		createBack(back10, db);
		createBack(back11, db);
	}

		private void fillReverseDives(SQLiteDatabase db) {
			
		ReverseDB reverse1 = new ReverseDB(301, 1, 1, "Reverse Dive", 1.8, 1.7, 1.6, 0.0, 2.0, 1.9, 1.8, 0.0);
		ReverseDB reverse2 = new ReverseDB(302, 1, 1, "Reverse Somersault", 1.8, 1.7, 1.6, 0.0, 1.9, 1.8, 1.7, 0.0);
		ReverseDB reverse3 = new ReverseDB(303, 1, 1, "Reverse 1 1/2 Somersaults", 2.7, 2.4, 2.1, 0.0, 2.6, 2.3, 2.0, 0.0);
		ReverseDB reverse4 = new ReverseDB(304, 1, 1, "Reverse Double Somersaults", 2.9, 2.6, 2.3, 0.0, 2.7, 2.4, 2.1, 0.0);
		ReverseDB reverse5 = new ReverseDB(305, 1, 1, "Reverse 2 1/2 Somersaults", 0.0, 3.2, 3.0, 0.0, 3.4, 3.0, 2.8, 0.0);
		ReverseDB reverse6 = new ReverseDB(306, 1, 1, "Reverse 3 Somersaults", 0.0, 3.2, 3.0, 0.0, 0.0, 2.9, 2.6, 0.0);
		ReverseDB reverse7 = new ReverseDB(307, 0, 1, "Reverse 3 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 3.8, 3.5, 0.0);
		ReverseDB reverse11 = new ReverseDB(308, 0, 1, "Reverse 4 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 3.7, 3.4, 0.0);
		ReverseDB reverse8 = new ReverseDB(309, 0, 1, "Reverse 4 1/2 Somersaults", 0.0, 0.0, 0.0, 0.0, 0.0, 4.7, 4.4, 0.0);
		ReverseDB reverse9 = new ReverseDB(312, 1, 1,"Reverse Flying Somersault", 0.0, 1.8, 1.7, 0.0, 0.0, 1.9, 1.8, 0.0);
		ReverseDB reverse10 = new ReverseDB(313, 1, 1, "Reverse Flying 1 1/2 Somersaults", 0.0, 2.6, 2.3, 0.0, 0.0, 2.5, 2.2, 0.0);
		
		createReverse(reverse1, db);
		createReverse(reverse2, db);
		createReverse(reverse3, db);
		createReverse(reverse4, db);
		createReverse(reverse5, db);
		createReverse(reverse6, db);
		createReverse(reverse7, db);
		createReverse(reverse11, db);
		createReverse(reverse8, db);
		createReverse(reverse9, db);
		createReverse(reverse10, db);
	}
		
		private void fillInwardDives(SQLiteDatabase db) {
			
		InwardDB inward1 = new InwardDB(401, 1, 1, "Inward Dive", 1.8, 1.5, 1.4, 0.0, 1.7, 1.4, 1.3, 0.0);
		InwardDB inward2 = new InwardDB(402, 1, 1, "Inward Somersault", 2.0, 1.7, 1.6, 0.0, 1.8, 1.5, 1.4, 0.0);
		InwardDB inward3 = new InwardDB(403, 1, 1, "Inward 1 1/2 Somersault", 0.0, 2.4, 2.2, 0.0, 0.0, 2.1, 1.9, 0.0);
		InwardDB inward4 = new InwardDB(404, 1, 1, "Inward Double Somersault", 0.0, 3.0, 2.8, 0.0, 0.0, 2.6, 2.4, 0.0);
		InwardDB inward5 = new InwardDB(405, 1, 1, "Inward 2 1/2 Somersault", 0.0, 3.4, 3.1, 0.0, 0.0, 3.0, 2.7, 0.0);
		InwardDB inward6 = new InwardDB(407, 0, 1, "Inward 3 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 3.7, 3.4, 0.0);
		InwardDB inward7 = new InwardDB(409, 0, 1, "Inward 4 1/2 Somersault", 0.0, 0.0, 0.0, 0.0, 0.0, 4.6, 4.2, 0.0);
		InwardDB inward8 = new InwardDB(412, 1, 1, "Inward Flying Somersault", 0.0, 2.1, 2.0, 0.0, 0.0, 1.9, 1.8, 0.0);
		InwardDB inward9 = new InwardDB(413, 1, 1, "Inward Flying 1 1/2 Somersault", 0.0, 2.9, 2.7, 0.0, 0.0, 2.6, 2.4, 0.0);
		
		createInward(inward1, db);
		createInward(inward2, db);
		createInward(inward3, db);
		createInward(inward4, db);
		createInward(inward5, db);
		createInward(inward6, db);
		createInward(inward7, db);
		createInward(inward8, db);
		createInward(inward9, db);
	}

		private void fillTwistDives(SQLiteDatabase db) {
			
		TwistDB twist1 = new TwistDB(5111, 1, 1, "Forward Dive  1/2 Twist", 1.8, 1.7, 1.6, 0.0, 2.0, 1.9, 1.8, 0.0);
		TwistDB twist2 = new TwistDB(5112, 1, 1, "Forward Dive 1 Twist", 2.0, 1.9, 0.0, 0.0, 2.2, 2.1, 0.0, 0.0);
		TwistDB twist3 = new TwistDB(5121, 1, 1, "Forward Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.7, 0.0, 0.0, 0.0, 1.8);
		TwistDB twist4 = new TwistDB(5122, 1, 1, "Forward Somersault 1 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 2.0);
		TwistDB twist5 = new TwistDB(5124, 1, 1, "Forward Somersault 2 Twists", 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist6 = new TwistDB(5126, 1, 1, "Forward Somersault 3 Twists", 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.9);
		TwistDB twist7 = new TwistDB(5131, 1, 1, "Forward 1 1/2 Somersaults  1/2 Twist", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 1.9);
		TwistDB twist8 = new TwistDB(5132, 1, 1, "Forward 1 1/2 Somersaults 1 Twist", 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.1);
		TwistDB twist9 = new TwistDB(5134, 1, 1, "Forward 1 1/2 Somersaults 2 Twists", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5);
		TwistDB twist10 = new TwistDB(5136, 1, 1, "Forward 1 1/2 Somersaults 3 Twists", 0.0, 0.0, 0.0, 3.1, 0.0, 0.0, 0.0, 3.0);
		TwistDB twist11 = new TwistDB(5138, 1, 1, "Forward 1 1/2 Somersaults 4 Twists", 0.0, 0.0, 0.0, 3.5, 0.0, 0.0, 0.0, 3.4);
		TwistDB twist12 = new TwistDB(5151, 1, 1, "Forward 2 1/2 Somersaults  1/2 Twists", 0.0, 3.0, 2.8, 0.0, 0.0, 2.8, 2.6, 0.0);
		TwistDB twist13 = new TwistDB(5152, 1, 1, "Forward 2 1/2 Somersaults 1 Twists", 0.0, 3.2, 3.0, 0.0, 0.0, 3.0, 2.8, 0.0);
		TwistDB twist14 = new TwistDB(5154, 1, 1, "Forward 2 1/2 Somersaults 2 Twists", 0.0, 3.6, 3.4, 0.0, 0.0, 3.4, 3.2, 0.0);
		TwistDB twist15 = new TwistDB(5156, 0, 1, "Forward 2 1/2 Somersaults 3 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.9, 3.7, 0.0);
		TwistDB twist16 = new TwistDB(5172, 0, 1, "Forward 3 1/2 Somersaults 1 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.7, 3.4, 0.0);

		TwistDB twist17 = new TwistDB(5211, 1, 1, "Back Dive  1/2 Twist", 1.8, 1.7, 1.6, 0.0, 2.0, 1.9, 1.8, 0.0);
		TwistDB twist18 = new TwistDB(5212, 1, 1, "Back Dive 1 Twist", 2.0, 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0);
		TwistDB twist19 = new TwistDB(5221, 1, 1, "Back Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.7, 0.0, 0.0, 0.0, 1.8);
		TwistDB twist20 = new TwistDB(5222, 1, 1, "Back Somersault 1 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 2.0);
		TwistDB twist21 = new TwistDB(5223, 1, 1, "Back Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist22 = new TwistDB(5225, 1, 1, "Back Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 2.8);
		TwistDB twist23 = new TwistDB(5227, 1, 1, "Back Somersault 3 1/2 Twists", 0.0, 0.0, 0.0, 3.2, 0.0, 0.0, 0.0, 3.3);
		TwistDB twist24 = new TwistDB(5231, 1, 1, "Back 1 1/2 Somersaults  1/2 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.0);
		TwistDB twist25 = new TwistDB(5233, 1, 1, "Back 1 1/2 Somersaults 1 1/2 Twists", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist26 = new TwistDB(5235, 1, 1, "Back 1 1/2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.8);
		TwistDB twist27 = new TwistDB(5237, 0, 1, "Back 1 1/2 Somersaults 3 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.3);
		TwistDB twist28 = new TwistDB(5239, 0, 1, "Back 1 1/2 Somersaults 4 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.7);
		TwistDB twist29 = new TwistDB(5251, 1, 1, "Back 2 1/2 Somersaults  1/2 Twist", 0.0, 2.9, 2.7, 0.0, 0.0, 2.7, 2.5, 0.0);
		TwistDB twist30 = new TwistDB(5253, 0, 1, "Back 2 1/2 Somersaults 1 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.4, 3.2, 0.0);
		TwistDB twist31 = new TwistDB(5255, 0, 1, "Back 2 1/2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 3.8, 3.6, 0.0);

		TwistDB twist32 = new TwistDB(5311, 1, 1, "Reverse Dive  1/2 Twist", 1.9, 1.8, 1.7, 0.0, 2.1, 2.0, 1.9, 0.0);
		TwistDB twist33 = new TwistDB(5312, 1, 1, "Reverse Dive 1 Twist", 2.1, 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0);
		TwistDB twist34 = new TwistDB(5321, 1, 1, "Reverse Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 1.9);
		TwistDB twist35 = new TwistDB(5322, 1, 1, "Reverse Somersault 1 Twist", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.1);
		TwistDB twist36 = new TwistDB(5323, 1, 1, "Reverse Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.5);
		TwistDB twist37 = new TwistDB(5325, 1, 1, "Reverse Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.9);
		TwistDB twist38 = new TwistDB(5331, 1, 1, "Reverse 1 1/2 Somersaults  1/2 Twist", 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.1);
		TwistDB twist39 = new TwistDB(5333, 1, 1, "Reverse 1 1/2 Somersaults 1 1/2 Twists", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5);
		TwistDB twist40 = new TwistDB(5335, 1, 1, "Reverse 1 1/2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 2.9);
		TwistDB twist41 = new TwistDB(5337, 1, 1, "Reverse 1 1/2 Somersaults 3 1/2 Twists", 0.0, 0.0, 0.0, 3.5, 0.0, 0.0, 0.0, 3.4);
		TwistDB twist42 = new TwistDB(5339, 0, 1, "Reverse 1 1/2 Somersaults 4 1/2 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.8);
		TwistDB twist43 = new TwistDB(5351, 1, 1, "Reverse 2 1/2 Somersaults  1/2 Twist", 0.0, 2.9, 2.7, 0.0, 0.0, 2.7, 2.5, 0.0);
		TwistDB twist44 = new TwistDB(5353, 1, 1, "Reverse 2 1/2 Somersaults 1 1/2 Twists", 0.0, 3.5, 3.3, 0.0, 0.0, 3.5, 3.1, 0.0);
		TwistDB twist45 = new TwistDB(5355, 1, 1, "Reverse 2 1/2 Somersaults 2 1/2 Twists", 0.0, 3.9, 3.7, 0.0, 0.0, 3.7, 3.5, 0.0);
		TwistDB twist46 = new TwistDB(5371, 0, 1, "Reverse 3 1/2 Somersaults  1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 3.4, 3.1, 0.0);
		TwistDB twist54 = new TwistDB(5373, 0, 1, "Reverse 3 1/2 Somersaults 1  1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.7, 0.0);
		TwistDB twist55 = new TwistDB(5375, 0, 1, "Reverse 3 1/2 Somersaults 2  1/2 Twist", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 4.1, 0.0);

		TwistDB twist47 = new TwistDB(5411, 1, 1, "Inward Dive  1/2 Twist", 2.0, 1.7, 1.6, 0.0, 1.9, 1.6, 1.5, 0.0);
		TwistDB twist48 = new TwistDB(5412, 1, 1, "Inward Dive 1 Twist", 2.2, 1.9, 1.8, 0.0, 2.1, 1.8, 1.7, 0.0);
		TwistDB twist49 = new TwistDB(5421, 1, 1, "Inward Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 1.7);
		TwistDB twist50 = new TwistDB(5422, 1, 1, "Inward Somersault 1 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 1.9);
		TwistDB twist51 = new TwistDB(5432, 1, 1, "Inward 1 1/2 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 2.4);
		TwistDB twist52 = new TwistDB(5434, 1, 1, "Inward 1 1/2 Somersault 2 Twists", 0.0, 0.0, 0.0, 3.1, 0.0, 0.0, 0.0, 2.8);
		TwistDB twist53 = new TwistDB(5436, 0, 1, "Inward 1 1/2 Somersault 3 Twists", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 3.5);

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
		createTwist(twist54, db);
		createTwist(twist55, db);
        createTwist(twist47, db);
        createTwist(twist48, db);
        createTwist(twist49, db);
        createTwist(twist50, db);
        createTwist(twist51, db);
        createTwist(twist52, db);
        createTwist(twist53, db);


	}

    private void fillFrontPlatform(SQLiteDatabase db){
        FrontPlatformDB d1 = new FrontPlatformDB(101, 1, 1, 1, "Forward Dive", 1.6, 1.5, 1.4, 0.0, 1.6, 1.5, 1.4, 0.0, 1.4, 1.3, 1.2, 0.0);
        FrontPlatformDB d2 = new FrontPlatformDB(102, 1, 1, 1, "Forward 1 Somersault", 1.8, 1.7, 1.6, 0.0, 1.7, 1.6, 1.5, 0.0, 1.6, 1.5, 1.4, 0.0);
        FrontPlatformDB d3 = new FrontPlatformDB(103, 1, 1, 1, "Forward 1 1/2 Somersaults", 1.9, 1.6, 1.5, 0.0, 1.9, 1.6, 1.5, 0.0, 2.0, 1.7, 1.6, 0.0);
        FrontPlatformDB d4 = new FrontPlatformDB(104, 1, 1, 1, "Forward 2 Somersaults", 2.5, 2.2, 2.1, 0.0, 2.4, 2.1, 2.0, 0.0, 2.6, 2.3, 2.2, 0.0);
        FrontPlatformDB d5 = new FrontPlatformDB(105, 1, 1, 1, "Forward 2 1/2 Somersaults", 2.7, 2.3, 2.1, 0.0, 0.0, 2.4, 2.2, 0.0, 0.0, 2.6, 2.4, 0.0);
        FrontPlatformDB d12 = new FrontPlatformDB(106, 1, 1, 1, "Forward 3 Somersaults", 0.0, 3.0, 2.7, 0.0, 0.0, 2.8, 2.5, 0.0, 0.0, 3.2, 2.9, 0.0);
        FrontPlatformDB d6 = new FrontPlatformDB(107, 1, 1, 1, "Forward 3 1/2 Somersaults", 0.0, 3.0, 2.7, 0.0, 0.0, 3.1, 2.8, 0.0, 0.0, 0.0, 3.0, 0.0);
        FrontPlatformDB d13 = new FrontPlatformDB(108, 1, 0, 0, "Forward 4 Somersaults", 0.0, 4.2, 3.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        FrontPlatformDB d7 = new FrontPlatformDB(109, 1, 0, 0, "Forward 4 1/2 Somersaults", 0.0, 4.1, 3.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        FrontPlatformDB d14 = new FrontPlatformDB(1011, 1, 0, 0, "Forward 5 1/2 Somersaults", 0.0, 0.0, 4.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        FrontPlatformDB d8 = new FrontPlatformDB(112, 1, 1, 1, "Forward Flying Somersault", 0.0, 1.9, 1.8, 0.0, 0.0, 1.8, 1.7, 0.0, 0.0, 1.7, 1.6, 0.0);
        FrontPlatformDB d9 = new FrontPlatformDB(113, 1, 1, 1, "Forward Flying 1 1/2 Somersaults", 0.0, 1.8, 1.7, 0.0, 0.0, 1.8, 1.7, 0.0, 0.0, 1.9, 1.8, 0.0);
        FrontPlatformDB d10 = new FrontPlatformDB(114, 1, 1, 1, "Forward Flying 2 Somersaults", 0.0, 2.4, 2.3, 0.0, 0.0, 2.3, 2.2, 0.0, 0.0, 2.5, 2.4, 0.0);
        FrontPlatformDB d11 = new FrontPlatformDB(115, 1, 1, 0, "Forward Flying 2 1/2 Somersaults", 0.0, 2.6, 2.4, 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 0.0, 0.0);

        createPlatformForward(d1, db);
        createPlatformForward(d2, db);
        createPlatformForward(d3, db);
        createPlatformForward(d4, db);
        createPlatformForward(d5, db);
        createPlatformForward(d12, db);
        createPlatformForward(d6, db);
        createPlatformForward(d13, db);
        createPlatformForward(d7, db);
        createPlatformForward(d14, db);
        createPlatformForward(d8, db);
        createPlatformForward(d9, db);
        createPlatformForward(d10, db);
        createPlatformForward(d11, db);
    }

    private void fillBackPlatform(SQLiteDatabase db){
        BackPlatformDB d1 = new BackPlatformDB(201, 1, 1, 1, "Back Dive", 1.9, 1.8, 1.7, 0.0, 1.9, 1.8, 1.7, 0.0, 1.7, 1.6, 1.5, 0.0);
        BackPlatformDB d2 = new BackPlatformDB(202, 1, 1, 1, "Back 1 Somersault", 1.9, 1.8, 1.7, 0.0, 1.8, 1.7, 1.6, 0.0, 1.7, 1.6, 1.5, 0.0);
        BackPlatformDB d3 = new BackPlatformDB(203, 1, 1, 1, "Back 1 1/2 Somersaults", 2.4, 2.2, 1.9, 0.0, 2.4, 2.2, 1.9, 0.0, 2.5, 2.3, 2.0, 0.0);
        BackPlatformDB d4 = new BackPlatformDB(204, 1, 1, 1, "Back 2 Somersaults", 2.6, 2.4, 2.1, 0.0, 2.5, 2.3, 2.0, 0.0, 0.0, 2.5, 2.2, 0.0);
        BackPlatformDB d5 = new BackPlatformDB(205, 1, 1, 1, "Back 2 1/2 Somersaults", 3.3, 2.9, 2.7, 0.0, 0.0, 3.0, 2.8, 0.0, 0.0, 0.0, 3.0, 0.0);
        BackPlatformDB d6 = new BackPlatformDB(206, 1, 1, 1, "Back 3 Somersaults", 0.0, 3.0, 2.7, 0.0, 0.0, 2.8, 2.5, 0.0, 0.0, 3.2, 2.9, 0.0);
        BackPlatformDB d7 = new BackPlatformDB(207, 1, 1, 0, "Back 3 1/2 Somersaults", 0.0, 3.6, 3.3, 0.0, 0.0, 0.0, 3.5, 0.0, 0.0, 0.0, 0.0, 0.0);
        BackPlatformDB d11 = new BackPlatformDB(208, 1, 1, 1, "Back 4 Somersaults", 0.0, 4.1, 3.8, 0.0, 0.0, 4.2, 3.9, 0.0, 0.0, 4.1, 4.1, 0.0);
        BackPlatformDB d8 = new BackPlatformDB(209, 1, 0, 0, "Back 4 1/2 Somersaults", 0.0, 4.5, 4.2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        BackPlatformDB d9 = new BackPlatformDB(212, 1, 1, 1, "Back Flying Somersault", 0.0, 1.9, 1.8, 0.0, 0.0, 1.8, 1.7, 0.0, 0.0, 1.7, 1.6, 0.0);
        BackPlatformDB d10 = new BackPlatformDB(213, 1, 1, 1, "Back Flying 1 1/2 Somersaults", 0.0, 2.4, 2.1, 0.0, 0.0, 2.4, 2.1, 0.0, 0.0, 2.5, 2.2, 0.0 );

        createPlatformBack(d1, db);
        createPlatformBack(d2, db);
        createPlatformBack(d3, db);
        createPlatformBack(d4, db);
        createPlatformBack(d5, db);
        createPlatformBack(d6, db);
        createPlatformBack(d7, db);
        createPlatformBack(d11, db);
        createPlatformBack(d8, db);
        createPlatformBack(d9, db);
        createPlatformBack(d10, db);
    }

    private void fillReversePlatform(SQLiteDatabase db){
        ReversePlatformDB d1 = new ReversePlatformDB(301, 1, 1, 1, "Reverse Dive", 2.0, 1.9, 1.8, 0.0, 2.0, 1.9, 1.8, 0.0, 1.8, 1.7, 1.6, 0.0);
        ReversePlatformDB d2 = new ReversePlatformDB(302, 1, 1, 1, "Reverse 1 Somersault", 2.0, 1.9, 1.8, 0.0, 1.9, 1.8, 1.7, 0.0, 1.8, 1.7, 1.6, 0.0);
        ReversePlatformDB d3 = new ReversePlatformDB(303, 1, 1, 1, "Reverse 1 1/2 Somersaults", 2.6, 2.3, 2.0, 0.0, 2.6, 2.3, 2.0, 0.0, 2.7, 2.4, 2.1, 0.0);
        ReversePlatformDB d4 = new ReversePlatformDB(304, 1, 1, 1, "Reverse 2 Somersaults", 2.8, 2.5, 2.2, 0.0, 2.7, 2.4, 2.1, 0.0, 2.9, 2.6, 2.3, 0.0);
        ReversePlatformDB d5 = new ReversePlatformDB(305, 1, 1, 1, "Reverse 2 1/2 Somersaults", 3.4, 3.0, 2.8, 0.0, 3.5, 3.1, 2.9, 0.0, 0.0, 3.3, 3.1, 0.0);
        ReversePlatformDB d6 = new ReversePlatformDB(306, 1, 1, 1, "Reverse 3 Somersaults", 0.0, 3.2, 2.9, 0.0, 0.0, 3.0, 2.7, 0.0, 0.0, 3.4, 3.1, 0.0);
        ReversePlatformDB d7 = new ReversePlatformDB(307, 1, 0, 0, "Reverse 3 1/2 Somersaults", 0.0, 3.7, 3.4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ReversePlatformDB d11 = new ReversePlatformDB(308, 1, 1, 0, "Reverse 4 Somersaults", 0.0, 4.4, 4.1, 0.0, 0.0, 4.5, 4.2, 0.0, 0.0, 0.0, 0.0, 0.0);
        ReversePlatformDB d8 = new ReversePlatformDB(309, 1, 0, 0, "Reverse 4 1/2 Somersaults", 0.0, 4.8, 4.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ReversePlatformDB d9 = new ReversePlatformDB(312, 1, 1, 1, "Reverse Flying Somersault", 0.0, 2.0, 1.9, 0.0, 0.0, 1.9, 1.8, 0.0, 0.0, 1.8, 1.7, 0.0);
        ReversePlatformDB d10 = new ReversePlatformDB(313, 1, 1, 1, "Reverse Flying 1 1/2 Somersaults", 0.0, 2.5, 2.2, 0.0, 0.0, 2.5, 2.2, 0.0, 0.0, 2.6, 2.3, 0.0);

        createPlatformReverse(d1, db);
        createPlatformReverse(d2, db);
        createPlatformReverse(d3, db);
        createPlatformReverse(d4, db);
        createPlatformReverse(d5, db);
        createPlatformReverse(d6, db);
        createPlatformReverse(d7, db);
        createPlatformReverse(d11, db);
        createPlatformReverse(d8, db);
        createPlatformReverse(d9, db);
        createPlatformReverse(d10, db);
    }

    private void fillInwardPlatform(SQLiteDatabase db){
        InwardPlatformDB d1 = new InwardPlatformDB(401, 1, 1, 1, "Inward Dive", 1.7, 1.4, 1.3, 0.0, 1.7, 1.4, 1.3, 0.0, 1.8, 1.5, 1.4, 0.0);
        InwardPlatformDB d2 = new InwardPlatformDB(402, 1, 1, 1, "Inward 1 Somersault", 1.9, 1.6, 1.5, 0.0, 1.8, 1.5, 1.4, 0.0, 2.0, 1.7, 1.6, 0.0);
        InwardPlatformDB d3 = new InwardPlatformDB(403, 1, 1, 1, "Inward 1 1/2 Somersaults", 0.0, 2.0, 1.8, 0.0, 0.0, 2.1, 1.9, 0.0, 0.0, 2.4, 2.2, 0.0);
        InwardPlatformDB d4 = new InwardPlatformDB(404, 1, 1, 1, "Inward 2 Somersaults", 0.0, 2.6, 2.4, 0.0, 0.0, 2.6, 2.4, 0.0, 0.0, 3.0, 2.8, 0.0);
        InwardPlatformDB d5 = new InwardPlatformDB(405, 1, 1, 1, "Inward 2 1/2 Somersaults", 0.0, 2.8, 2.5, 0.0, 0.0, 3.0, 2.7, 0.0, 0.0, 3.4, 3.1, 0.0);
        InwardPlatformDB d10 = new InwardPlatformDB(406, 1, 1, 1, "Inward 3 Somersaults", 0.0, 3.5, 3.2, 0.0, 0.0, 3.4, 3.1, 0.0, 0.0, 4.0, 3.7, 0.0);
        InwardPlatformDB d6 = new InwardPlatformDB(407, 1, 1, 0, "Inward 3 1/2 Somersaults", 0.0, 3.5, 3.2, 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 0.0, 0.0);
        InwardPlatformDB d11 = new InwardPlatformDB(408, 1, 0, 0, "Inward 4 Somersaults", 0.0, 4.4, 4.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        InwardPlatformDB d7 = new InwardPlatformDB(409, 1, 0, 0, "Inward 4 1/2 Somersaults", 0.0, 4.4, 4.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        InwardPlatformDB d8 = new InwardPlatformDB(412, 1, 1, 1, "Inward Flying Somersault", 0.0, 2.0, 1.9, 0.0, 0.0, 1.9, 1.8, 0.0, 0.0, 2.1, 2.0, 0.0);
        InwardPlatformDB d9 = new InwardPlatformDB(413, 1, 1, 1, "Inward Flying 1 1/2 Somersaults", 0.0, 2.5, 2.3, 0.0, 0.0, 2.6, 2.4, 0.0, 0.0, 2.9, 2.7, 0.0);

        createPlatformInward(d1, db);
        createPlatformInward(d2, db);
        createPlatformInward(d3, db);
        createPlatformInward(d4, db);
        createPlatformInward(d5, db);
        createPlatformInward(d10, db);
        createPlatformInward(d6, db);
        createPlatformInward(d11, db);
        createPlatformInward(d7, db);
        createPlatformInward(d8, db);
        createPlatformInward(d9, db);
    }

    private void fillTwistPlatform(SQLiteDatabase db){
        TwistPlatformDB d1 = new TwistPlatformDB(5111, 1, 1, 1, "Forward Dive  1/2 Twist", 2.0, 1.9, 1.8, 0.0, 2.0, 1.9, 1.8, 0.0, 1.8, 1.7, 1.6, 0.0);
        TwistPlatformDB d2 = new TwistPlatformDB(5112, 1, 1, 1, "Forward Dive 1 Twist", 2.2, 2.1, 0.0, 0.0, 2.2, 2.1, 0.0, 0.0, 2.0, 1.9, 0.0, 0.0);
        TwistPlatformDB d3 = new TwistPlatformDB(5121, 1, 1, 1, "Forward 1 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 1.7);
        TwistPlatformDB d4 = new TwistPlatformDB(5122, 1, 1, 1, "Forward 1 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 1.9);
        TwistPlatformDB d5 = new TwistPlatformDB(5124, 1, 1, 1, "Forward 1 Somersault 2 Twist", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.3);
        TwistPlatformDB d6 = new TwistPlatformDB(5131, 1, 1, 1, "Forward 1 1/2 Somersaults  1/2 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 2.0);
        TwistPlatformDB d7 = new TwistPlatformDB(5132, 1, 1, 1, "Forward 1 1/2 Somersaults 1 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.2);
        TwistPlatformDB d8 = new TwistPlatformDB(5134, 1, 1, 1, "Forward 1 1/2 Somersaults 2 Twists", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.6);
        TwistPlatformDB d9 = new TwistPlatformDB(5136, 1, 1, 1, "Forward 1 1/2 Somersaults 3 Twists", 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 3.1);
        TwistPlatformDB d10 = new TwistPlatformDB(5138, 1, 1, 1, "Forward 1 1/2 Somersaults 4 Twists", 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 3.5);
        TwistPlatformDB d11 = new TwistPlatformDB(5152, 1, 1, 1, "Forward 2 1/2 Somersaults 1 Twist", 0.0, 2.9, 2.7, 0.0, 0.0, 3.0, 2.8, 0.0, 0.0, 3.2, 3.0, 0.0);
        TwistPlatformDB d12 = new TwistPlatformDB(5254, 1, 1, 1, "Forward 2 1/2 Somersaults 2 Twists", 0.0, 3.3, 3.1, 0.0, 0.0, 3.4, 3.2, 0.0, 0.0, 3.6, 3.4, 0.0);
        TwistPlatformDB d13 = new TwistPlatformDB(5156, 1, 0, 0, "Forward 2 1/2 Somersaults 3 Twists", 0.0, 3.8, 3.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d14 = new TwistPlatformDB(5172, 1, 1, 0, "Forward 3 1/2 Somersaults 1 Twist", 0.0, 3.6, 3.3, 0.0, 0.0, 3.7, 3.4, 0.0, 0.0, 0.0, 0.0, 0.0);

        TwistPlatformDB d15 = new TwistPlatformDB(5211, 1, 1, 1, "Back Dive  1/2 Twist", 2.0, 1.9, 1.8, 0.0, 2.0, 1.9, 1.8, 0.0, 1.8, 1.7, 1.6, 0.0);
        TwistPlatformDB d16 = new TwistPlatformDB(5212, 1, 1, 1, "Back Dive 1 Twist", 2.2, 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d17 = new TwistPlatformDB(5221, 1, 1, 1, "Back 1 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 1.7);
        TwistPlatformDB d18 = new TwistPlatformDB(5222, 1, 1, 1, "Back 1 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 1.9);
        TwistPlatformDB d19 = new TwistPlatformDB(5223, 1, 1, 1, "Back 1 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.3);
        TwistPlatformDB d20 = new TwistPlatformDB(5225, 1, 1, 1, "Back 1 Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.7);
        TwistPlatformDB d21 = new TwistPlatformDB(5231, 1, 1, 1, "Back 1 1/2 Somersaults  1/2 Twists", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 2.1);
        TwistPlatformDB d22 = new TwistPlatformDB(5233, 1, 1, 1, "Back 1 1/2 Somersaults 1 1/2 Twist", 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.5);
        TwistPlatformDB d23 = new TwistPlatformDB(5235, 1, 1, 1, "Back 1 1/2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.9);
        TwistPlatformDB d24 = new TwistPlatformDB(5237, 1, 1, 1, "Back 1 1/2 Somersaults 3 1/2 Twists", 0.0, 0.0, 0.0, 3.3, 0.0, 0.0, 0.0, 3.3, 0.0, 0.0, 0.0, 3.4);
        TwistPlatformDB d25 = new TwistPlatformDB(5239, 1, 1, 1, "Back 1 1/2 Somersaults 4 1/2 Twists", 0.0, 0.0, 0.0, 3.7, 0.0, 0.0, 0.0, 3.7, 0.0, 0.0, 0.0, 3.8);
        TwistPlatformDB d26 = new TwistPlatformDB(5251, 1, 1, 1, "Back 2 1/2 Somersaults  1/2 Twist", 0.0, 2.6, 2.4, 0.0, 0.0, 2.7, 2.5, 0.0, 0.0, 2.9, 2.7, 0.0);
        TwistPlatformDB d27 = new TwistPlatformDB(5253, 1, 1, 0, "Back 2 1/2 Somersaults 1 1/2 Twists", 0.0, 3.2, 3.0, 0.0, 0.0, 3.3, 3.1, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d28 = new TwistPlatformDB(5255, 1, 0, 0, "Back 2 1/2 Somersaults 2 1/2 Twists", 0.0, 3.6, 3.4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d52 = new TwistPlatformDB(5257, 1, 0, 0, "Back 2 1/2 Somersaults 3 1/2 Twists", 0.0, 4.3, 4.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d29 = new TwistPlatformDB(5271, 1, 0, 0, "Back 3 1/2 Somersaults  1/2 Twist", 0.0, 3.2, 2.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d53 = new TwistPlatformDB(5273, 1, 0, 0, "Back 3 1/2 Somersaults 1 1/2 Twist", 0.0, 3.8, 3.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d54 = new TwistPlatformDB(5275, 1, 0, 0, "Back 3 1/2 Somersaults 2 1/2 Twist", 0.0, 4.2, 3.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        TwistPlatformDB d30 = new TwistPlatformDB(5311, 1, 1, 1, "Reverse Dive  1/2 Twist", 2.1, 2.0, 1.9, 0.0, 2.1, 2.0, 1.9, 0.0, 1.9, 1.8, 1.7, 0.0);
        TwistPlatformDB d31 = new TwistPlatformDB(5312, 1, 1, 1, "Reverse Dive 1 Twist", 2.3, 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0);
        TwistPlatformDB d32 = new TwistPlatformDB(5321, 1, 1, 1, "Reverse 1 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 1.8);
        TwistPlatformDB d33 = new TwistPlatformDB(5322, 1, 1, 1, "Reverse 1 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.2, 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.0);
        TwistPlatformDB d34 = new TwistPlatformDB(5323, 1, 1, 1, "Reverse 1 Somersault 1 1/2 Twists", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4);
        TwistPlatformDB d35 = new TwistPlatformDB(5325, 1, 1, 1, "Reverse 1 Somersault 2 1/2 Twists", 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.8);
        TwistPlatformDB d36 = new TwistPlatformDB(5331, 1, 1, 1, "Reverse 1 1/2 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.1, 0.0, 0.0, 0.0, 2.2);
        TwistPlatformDB d37 = new TwistPlatformDB(5333, 1, 1, 1, "Reverse 1 1/2 Somersaults 1 1/2 Twists", 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.6);
        TwistPlatformDB d38 = new TwistPlatformDB(5335, 1, 1, 1, "Reverse 1 1/2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 3.0);
        TwistPlatformDB d39 = new TwistPlatformDB(5337, 1, 1, 1, "Reverse 1 1/2 Somersaults 3 1/2 Twists", 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 3.5);
        TwistPlatformDB d40 = new TwistPlatformDB(5339, 1, 1, 0, "Reverse 1 1/2 Somersaults 4 1/2 Twists", 0.0, 0.0, 0.0, 3.8, 0.0, 0.0, 0.0, 3.8, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d41 = new TwistPlatformDB(5351, 1, 1, 1, "Reverse 2 1/2 Somersaults  1/2 Twist", 0.0, 2.6, 2.4, 0.0, 0.0, 2.7, 2.5, 0.0, 0.0, 2.9, 2.7, 0.0);
        TwistPlatformDB d42 = new TwistPlatformDB(5353, 1, 1, 1, "Reverse 2 1/2 Somersaults 1 1/2 Twists", 0.0, 3.2, 3.1, 0.0, 0.0, 3.4, 3.2, 0.0, 0.0, 0.0, 3.4, 0.0);
        TwistPlatformDB d43 = new TwistPlatformDB(5355, 1, 1, 1, "Reverse 2 1/2 Somersaults 2 1/2 Twists", 0.0, 3.7, 3.5, 0.0, 0.0, 3.8, 3.6, 0.0, 0.0, 0.0, 3.8, 0.0);
        TwistPlatformDB d44 = new TwistPlatformDB(5371, 1, 0, 0, "Reverse 3 1/2 Somersaults  1/2 Twist", 0.0, 3.3, 3.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d55 = new TwistPlatformDB(5373, 1, 0, 0, "Reverse 3 1/2 Somersaults 1 1/2 Twist", 0.0, 0.0, 3.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        TwistPlatformDB d56 = new TwistPlatformDB(5375, 1, 0, 0, "Reverse 3 1/2 Somersaults 2 1/2 Twist", 0.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        TwistPlatformDB d45 = new TwistPlatformDB(5411, 1, 1, 1, "Inward Dive  1/2 Twist", 1.9, 1.6, 1.5, 0.0, 1.9, 1.6, 1.5, 0.0, 2.0, 1.7, 1.6, 0.0);
        TwistPlatformDB d46 = new TwistPlatformDB(5412, 1, 1, 1, "Inward Dive 1 Twist", 2.1, 1.8, 1.7, 0.0, 2.1, 1.8, 1.7, 0.0, 2.2, 1.9, 1.8, 0.0);
        TwistPlatformDB d47 = new TwistPlatformDB(5421, 1, 1, 1, "Inward 1 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 1.7, 0.0, 0.0, 0.0, 1.9);
        TwistPlatformDB d48 = new TwistPlatformDB(5422, 1, 1, 1, "Inward 1 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.0, 0.0, 0.0, 0.0, 1.9, 0.0, 0.0, 0.0, 2.1);
        TwistPlatformDB d49 = new TwistPlatformDB(5432, 1, 1, 1, "Inward 1 1/2 Somersaults 1 Twists", 0.0, 0.0, 0.0, 2.3, 0.0, 0.0, 0.0, 2.4, 0.0, 0.0, 0.0, 2.7);
        TwistPlatformDB d50 = new TwistPlatformDB(5434, 1, 1, 1, "Inward 1 1/2 Somersaults 2 Twists", 0.0, 0.0, 0.0, 2.7, 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 3.1);
        TwistPlatformDB d51 = new TwistPlatformDB(5436, 1, 0, 0, "Inward 1 1/2 Somersaults 3 Twists", 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        createPlatformTwist(d1, db);
        createPlatformTwist(d2, db);
        createPlatformTwist(d3, db);
        createPlatformTwist(d4, db);
        createPlatformTwist(d5, db);
        createPlatformTwist(d6, db);
        createPlatformTwist(d7, db);
        createPlatformTwist(d8, db);
        createPlatformTwist(d9, db);
        createPlatformTwist(d10, db);
        createPlatformTwist(d11, db);
        createPlatformTwist(d12, db);
        createPlatformTwist(d13, db);
        createPlatformTwist(d14, db);
        createPlatformTwist(d15, db);
        createPlatformTwist(d16, db);
        createPlatformTwist(d17, db);
        createPlatformTwist(d18, db);
        createPlatformTwist(d19, db);
        createPlatformTwist(d20, db);
        createPlatformTwist(d21, db);
        createPlatformTwist(d22, db);
        createPlatformTwist(d23, db);
        createPlatformTwist(d24, db);
        createPlatformTwist(d25, db);
        createPlatformTwist(d26, db);
        createPlatformTwist(d27, db);
        createPlatformTwist(d28, db);
        createPlatformTwist(d52, db);
        createPlatformTwist(d29, db);
        createPlatformTwist(d53, db);
        createPlatformTwist(d54, db);
        createPlatformTwist(d30, db);
        createPlatformTwist(d31, db);
        createPlatformTwist(d32, db);
        createPlatformTwist(d33, db);
        createPlatformTwist(d34, db);
        createPlatformTwist(d35, db);
        createPlatformTwist(d36, db);
        createPlatformTwist(d37, db);
        createPlatformTwist(d38, db);
        createPlatformTwist(d39, db);
        createPlatformTwist(d40, db);
        createPlatformTwist(d41, db);
        createPlatformTwist(d42, db);
        createPlatformTwist(d43, db);
        createPlatformTwist(d44, db);
        createPlatformTwist(d55, db);
        createPlatformTwist(d56, db);
        createPlatformTwist(d45, db);
        createPlatformTwist(d46, db);
        createPlatformTwist(d47, db);
        createPlatformTwist(d48, db);
        createPlatformTwist(d49, db);
        createPlatformTwist(d50, db);
        createPlatformTwist(d51, db);
    }

    private void fillArmstand(SQLiteDatabase db){
        ArmstandPlatformDB d1 = new ArmstandPlatformDB(600, 1, 1, 1, "Armstand Dive", 1.6, 0.0, 0.0, 0.0, 1.6, 0.0, 0.0, 0.0, 1.5, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d2 = new ArmstandPlatformDB(611, 1, 1, 1, "Armstand Forward 1/2 Somersault", 2.0, 1.9, 1.7, 0.0, 2.0, 1.9, 1.7, 0.0, 1.8, 1.7, 1.5, 0.0);
        ArmstandPlatformDB d3 = new ArmstandPlatformDB(612, 1, 1, 1, "Armstand Forward 1 Somersault", 2.0, 1.9, 1.7, 0.0, 1.9, 1.8, 1.6, 0.0, 1.8, 1.7, 1.5, 0.0);
        ArmstandPlatformDB d4 = new ArmstandPlatformDB(614, 1, 1, 1, "Armstand Forward 2 Somersaults", 0.0, 2.4, 2.1, 0.0, 0.0, 2.3, 2.0, 0.0, 0.0, 2.5, 2.2, 0.0);
        ArmstandPlatformDB d5 = new ArmstandPlatformDB(616, 1, 0, 0, "Armstand Forward 3 Somersaults", 0.0, 3.3, 3.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d6 = new ArmstandPlatformDB(621, 1, 1, 1,  "Armstand Back  1/2 Somersault", 1.9, 1.8, 1.6, 0.0, 1.9, 1.8, 1.6, 0.0, 1.7, 1.6, 1.4, 0.0);
        ArmstandPlatformDB d7 = new ArmstandPlatformDB(622, 1, 1, 1, "Armstand Back 1 Somersault", 2.3, 2.2, 2.0, 0.0, 2.2, 2.1, 1.9, 0.0, 2.1, 2.0, 1.8, 0.0);
        ArmstandPlatformDB d8 = new ArmstandPlatformDB(623, 1, 1, 1, "Armstand Back 1 1/2 Somersaults", 0.0, 2.2, 1.9, 0.0, 0.0, 2.2, 1.9, 0.0, 0.0, 2.3, 2.0, 0.0);
        ArmstandPlatformDB d9 = new ArmstandPlatformDB(624, 1, 1, 1, "Armstand Back 2 Somersaults", 3.0, 2.8, 2.5, 0.0, 2.9, 2.7, 2.4, 0.0, 3.1, 2.9, 2.6, 0.0);
        ArmstandPlatformDB d10 = new ArmstandPlatformDB(626, 1, 1, 1, "Armstand Back 3 Somersaults", 0.0, 3.5, 3.3, 0.0, 0.0, 3.3, 3.1, 0.0, 0.0, 0.0, 3.5, 0.0);
        ArmstandPlatformDB d26 = new ArmstandPlatformDB(628, 1, 1, 1, "Armstand Back 4 Somersaults", 0.0, 4.7, 4.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d11 = new ArmstandPlatformDB(631, 1, 1, 1, "Armstand Reverse  1/2 Somersault", 2.0, 1.9, 1.7, 0.0, 2.0, 1.9, 1.7, 0.0, 1.8, 1.7, 1.5, 0.0);
        ArmstandPlatformDB d12 = new ArmstandPlatformDB(632, 1, 1, 1, "Armstand Reverse 1 Somersault", 0.0, 2.3, 2.1, 0.0, 0.0, 2.2, 2.0, 0.0, 0.0, 2.1, 1.9, 0.0);
        ArmstandPlatformDB d13 = new ArmstandPlatformDB(633, 1, 1, 1, "Armstand Reverse 1 1/2 Somersaults", 0.0, 2.3, 2.0, 0.0, 0.0, 2.3, 2.0, 0.0, 0.0, 2.4, 2.1, 0.0);
        ArmstandPlatformDB d14 = new ArmstandPlatformDB(634, 1, 1, 1, "Armstand Reverse 2 Somersaults", 0.0, 2.9, 2.6, 0.0, 0.0, 2.8, 2.5, 0.0, 0.0, 3.0, 2.7, 0.0);
        ArmstandPlatformDB d15 = new ArmstandPlatformDB(636, 1, 1, 0, "Armstand Reverse 3 Somersaults", 0.0, 3.6, 3.4, 0.0, 0.0, 0.0, 3.2, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d27 = new ArmstandPlatformDB(638, 1, 0, 0, "Armstand Reverse 4 Somersaults", 0.0, 4.8, 4.6, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d16 = new ArmstandPlatformDB(6122, 1, 1, 1, "Armstand Forward 1 Somersault 1 Twist", 0.0, 0.0, 0.0, 2.6, 0.0, 0.0, 0.0, 2.5, 0.0, 0.0, 0.0, 2.4);
        ArmstandPlatformDB d17 = new ArmstandPlatformDB(6124, 1, 1, 1, "Armstand Forward 1 Somersault 2 Twists", 0.0, 0.0, 0.0, 2.9, 0.0, 0.0, 0.0, 2.8, 0.0, 0.0, 0.0, 2.7);
        ArmstandPlatformDB d18 = new ArmstandPlatformDB(6142, 1, 1, 1, "Armstand Forward 2 Somersaults 1 Twist", 0.0, 0.0, 0.0, 3.1, 0.0, 0.0, 0.0, 3.0, 0.0, 0.0, 0.0, 3.2);
        ArmstandPlatformDB d19 = new ArmstandPlatformDB(6144, 1, 1, 1, "Armstand Forward 2 Somersaults 2 Twists", 0.0, 0.0, 0.0, 3.4, 0.0, 0.0, 0.0, 3.3, 0.0, 0.0, 0.0, 3.5);
        ArmstandPlatformDB d20 = new ArmstandPlatformDB(6162, 1, 0, 0, "Armstand Forward 3 Somersaults 1 Twists", 0.0, 0.0, 3.9, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d21 = new ArmstandPlatformDB(6221, 1, 1, 1, "Armstand Back 1 Somersault  1/2 Twist", 0.0, 0.0, 0.0, 1.8, 0.0, 0.0, 0.0, 1.7, 0.0, 0.0, 0.0, 1.6);
        ArmstandPlatformDB d22 = new ArmstandPlatformDB(6241, 1, 1, 1, "Armstand Back 2 Somersaults  1/2 Twist", 0.0, 2.7, 2.4, 0.0, 0.0, 2.6, 2.3, 0.0, 0.0, 2.8, 2.5, 0.0);
        ArmstandPlatformDB d23 = new ArmstandPlatformDB(6243, 1, 1, 1, "Armstand Back 2 Somersaults 1 1/2 Twists", 0.0, 0.0, 0.0, 3.2, 0.0, 0.0, 0.0, 3.1, 0.0, 0.0, 0.0, 3.3);
        ArmstandPlatformDB d24 = new ArmstandPlatformDB(6245, 1, 1, 1, "Armstand Back 2 Somersaults 2 1/2 Twists", 0.0, 0.0, 0.0, 3.6, 0.0, 0.0, 0.0, 3.5, 0.0, 0.0, 0.0, 3.7);
        ArmstandPlatformDB d28 = new ArmstandPlatformDB(6247, 1, 0, 0, "Armstand Back 2 Somersaults 3 1/2 Twists", 0.0, 0.0, 0.0, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d25 = new ArmstandPlatformDB(6261, 1, 1, 1, "Armstand Back 3 Somersaults  1/2 Twist", 0.0, 3.5, 3.3, 0.0, 0.0, 3.3, 3.1, 0.0, 0.0, 3.7, 3.5, 0.0);
        ArmstandPlatformDB d29 = new ArmstandPlatformDB(6263, 1, 0, 0, "Armstand Back 3 Somersaults 1 1/2 Twists", 0.0, 4.3, 4.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        ArmstandPlatformDB d30 = new ArmstandPlatformDB(6265, 1, 0, 0, "Armstand Back 3 Somersaults 2 1/2 Twists", 0.0, 4.7, 4.4, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

        createPlatformArmstand(d1, db);
        createPlatformArmstand(d2, db);
        createPlatformArmstand(d3, db);
        createPlatformArmstand(d4, db);
        createPlatformArmstand(d5, db);
        createPlatformArmstand(d6, db);
        createPlatformArmstand(d7, db);
        createPlatformArmstand(d8, db);
        createPlatformArmstand(d9, db);
        createPlatformArmstand(d10, db);
        createPlatformArmstand(d26, db);
        createPlatformArmstand(d11, db);
        createPlatformArmstand(d12, db);
        createPlatformArmstand(d13, db);
        createPlatformArmstand(d14, db);
        createPlatformArmstand(d15, db);
        createPlatformArmstand(d27, db);
        createPlatformArmstand(d16, db);
        createPlatformArmstand(d17, db);
        createPlatformArmstand(d18, db);
        createPlatformArmstand(d19, db);
        createPlatformArmstand(d20, db);
        createPlatformArmstand(d21, db);
        createPlatformArmstand(d22, db);
        createPlatformArmstand(d23, db);
        createPlatformArmstand(d24, db);
        createPlatformArmstand(d28, db);
        createPlatformArmstand(d25, db);
        createPlatformArmstand(d29, db);
        createPlatformArmstand(d30, db);
    }
}
