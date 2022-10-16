package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DB_SCHEDULE";

    //region SCHEDULE
    public static final String TABLE_SCHEDULE_NAME = "SCHEDULE";
    public static final String KEY_SCHEDULE_ID = "ID";
    public static final String KEY_SCHEDULE_SUBJECT = "SUBJECT";
    public static final String KEY_SCHEDULE_TEACHER = "TEACHER";
    public static final String KEY_SCHEDULE_DAY = "DAY";
    public static final String KEY_SCHEDULE_CABINET = "CABINET";
    public static final String KEY_SCHEDULE_TIME = "TIME";
    public static final String KEY_SCHEDULE_NOTE = "NOTE";
    public static final String KEY_SCHEDULE_TYPE_CLASS = "TYPE_CLASS";
    public static final String KEY_SCHEDULE_EVEN = "EVEN";
    //endregion

    //region CABINETS
    public static final String TABLE_CABINETS_NAME = "CABINETS";
    public static final String KEY_CABINETS_ID = "ID";
    public static final String KEY_CABINETS_NAME = "NAME";
    //endregion

    //region DAYS
    public static final String TABLE_DAYS_NAME = "DAYS";
    public static final String KEY_DAYS_ID = "ID";
    public static final String KEY_DAYS_NAME = "NAME";
    //endregion

    //region SUBJECTS
    public static final String TABLE_SUBJECTS_NAME = "SUBJECTS";
    public static final String KEY_SUBJECTS_ID = "ID";
    public static final String KEY_SUBJECTS_NAME = "NAME";
    //endregion

    //region TEACHERS
    public static final String TABLE_TEACHERS_NAME = "TEACHERS";
    public static final String KEY_TEACHERS_ID = "ID";
    public static final String KEY_TEACHERS_NAME = "NAME";
    //endregion

    //region TYPES_CLASS
    public static final String TABLE_TYPES_CLASS_NAME = "TYPES_CLASS";
    public static final String KEY_TYPES_CLASS_ID = "ID";
    public static final String KEY_TYPES_CLASS_NAME = "NAME";
    //endregion

    @Override
    public void onCreate(SQLiteDatabase db)//Вызывается при первом создании бд
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CABINETS_NAME + "\n" +
                "(" + KEY_CABINETS_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                 KEY_CABINETS_NAME + " VARCHAR2(10) NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DAYS_NAME + "\n" +
                "(" + KEY_DAYS_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                KEY_DAYS_NAME + " VARCHAR2(22) NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SUBJECTS_NAME + "\n" +
                "(" + KEY_SUBJECTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                KEY_SUBJECTS_NAME + " VARCHAR(50) NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TEACHERS_NAME + "\n" +
                "(" + KEY_TEACHERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                KEY_TEACHERS_NAME + " VARCHAR(50) NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TYPES_CLASS_NAME + "\n" +
                "(" + KEY_TYPES_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                KEY_TYPES_CLASS_NAME + "  VARCHAR(30) NOT NULL UNIQUE)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_SCHEDULE_NAME +
                        " (" + KEY_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        KEY_SCHEDULE_SUBJECT + " INTEGER NOT NULL," +
                        KEY_SCHEDULE_TEACHER + " INTEGER NOT NULL," +
                        KEY_SCHEDULE_DAY + " INTEGER NOT NULL," +
                        KEY_SCHEDULE_CABINET + " INTEGER NOT NULL," +
                        KEY_SCHEDULE_TYPE_CLASS + " INTEGER NOT NULL," +
                        KEY_SCHEDULE_TIME + " VARCHAR(12)," +
                        KEY_SCHEDULE_NOTE + " TEXT," +
                        KEY_SCHEDULE_EVEN + " BOOLEAN DEFAULT 1 CHECK (" + KEY_SCHEDULE_EVEN + " IN (0, 1))," +
                        "FOREIGN KEY (" + KEY_SCHEDULE_DAY +") REFERENCES " + TABLE_DAYS_NAME + " (" + KEY_DAYS_ID + ")," +
                        "FOREIGN KEY (" + KEY_SCHEDULE_CABINET +") REFERENCES " + TABLE_CABINETS_NAME + " (" + KEY_CABINETS_ID + ") ON DELETE SET NULL," +
                        "FOREIGN KEY (" + KEY_SCHEDULE_TYPE_CLASS +") REFERENCES " + TABLE_TYPES_CLASS_NAME + " (" + KEY_TYPES_CLASS_ID + ") ON DELETE SET NULL," +
                        "FOREIGN KEY (" + KEY_SCHEDULE_SUBJECT +") REFERENCES " + TABLE_SUBJECTS_NAME + " (" + KEY_SUBJECTS_ID + ") ON DELETE CASCADE," +
                        "FOREIGN KEY (" + KEY_SCHEDULE_TEACHER +") REFERENCES " + TABLE_TEACHERS_NAME + " (" + KEY_TEACHERS_ID + ") ON DELETE SET NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)//Вызывается при изменении бд
    {
        db.execSQL("drop table if exists " + TABLE_CABINETS_NAME);
        db.execSQL("drop table if exists " + TABLE_DAYS_NAME);
        db.execSQL("drop table if exists " + TABLE_SUBJECTS_NAME);
        db.execSQL("drop table if exists " + TABLE_TEACHERS_NAME);
        db.execSQL("drop table if exists " + TABLE_TYPES_CLASS_NAME);
        db.execSQL("drop table if exists " + TABLE_SCHEDULE_NAME);
        onCreate(db);
    }
}
