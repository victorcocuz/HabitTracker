package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by victo on 10/15/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                    + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                    + HabitEntry.COLUMN_HABIT_DESCRIPTION + " TEXT NOT NULL, "
                    + HabitEntry.COLUMN_HABIT_MONDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_TUESDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_WEDNESDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_THURSDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_FRIDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_SATURDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_SUNDAY + " INTEGER NOT NULL DEFAULT 0, "
                    + HabitEntry.COLUMN_HABIT_REMINDER + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
