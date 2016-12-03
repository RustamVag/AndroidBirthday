package com.dom.viewapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Рустам on 05.11.2016.
 */
public class DBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "birthdayDb2";
    public static final String TABLE_BIRTHDAYS = "birthdays";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_BIRTHDAYS + "(" + KEY_ID
                + " integer primary key autoincrement not null," + KEY_NAME + " text," + KEY_DAY + " integer," + KEY_MONTH + " integer" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_BIRTHDAYS);

        onCreate(db);

    }
}
