package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;
import com.facebook.stetho.Stetho;

public class CatalogActivity extends AppCompatActivity {

    private static final String LOG_TAG = CatalogActivity.class.getSimpleName();
    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_catalog);


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new HabitDbHelper(this);
        displayDatabaseInfo(readDatabaseInfo());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo(readDatabaseInfo());
    }

    public Cursor readDatabaseInfo() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_DESCRIPTION,
                HabitEntry.COLUMN_HABIT_MONDAY,
                HabitEntry.COLUMN_HABIT_TUESDAY,
                HabitEntry.COLUMN_HABIT_WEDNESDAY,
                HabitEntry.COLUMN_HABIT_THURSDAY,
                HabitEntry.COLUMN_HABIT_FRIDAY,
                HabitEntry.COLUMN_HABIT_SATURDAY,
                HabitEntry.COLUMN_HABIT_SUNDAY,
                HabitEntry.COLUMN_HABIT_REMINDER};

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    public void displayDatabaseInfo(Cursor cursor) {

        try {
            TextView displayView = (TextView) findViewById(R.id.habit_text_view);
            displayView.setText("This table contains " + cursor.getCount() + " entries.\n\n");
            displayView.append(HabitEntry._ID + "\t"
                    + HabitEntry.COLUMN_HABIT_NAME + "\t"
                    + HabitEntry.COLUMN_HABIT_DESCRIPTION + "\t"
                    + HabitEntry.COLUMN_HABIT_MONDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_TUESDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_WEDNESDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_THURSDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_FRIDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_SATURDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_SUNDAY + "\t"
                    + HabitEntry.COLUMN_HABIT_REMINDER + "\n");


            while (cursor.moveToNext()) {
                displayView.append(cursor.getString(cursor.getColumnIndex(HabitEntry._ID)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DESCRIPTION)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_MONDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TUESDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_WEDNESDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_THURSDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_FRIDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_SATURDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_SUNDAY)) + "\t"
                        + cursor.getString(cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_REMINDER)) + "\n");
            }

        } finally {
            cursor.close();
        }

    }

    private void insertHabit() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "running");
        values.put(HabitEntry.COLUMN_HABIT_DESCRIPTION, "just running around");
        values.put(HabitEntry.COLUMN_HABIT_MONDAY, HabitEntry.DAY_NO);
        values.put(HabitEntry.COLUMN_HABIT_TUESDAY, HabitEntry.DAY_NO);
        values.put(HabitEntry.COLUMN_HABIT_WEDNESDAY, HabitEntry.DAY_YES);
        values.put(HabitEntry.COLUMN_HABIT_THURSDAY, HabitEntry.DAY_NO);
        values.put(HabitEntry.COLUMN_HABIT_FRIDAY, HabitEntry.DAY_YES);
        values.put(HabitEntry.COLUMN_HABIT_SATURDAY, HabitEntry.DAY_NO);
        values.put(HabitEntry.COLUMN_HABIT_SUNDAY, HabitEntry.DAY_YES);
        values.put(HabitEntry.COLUMN_HABIT_REMINDER, HabitEntry.REMINDER_YES);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.e(LOG_TAG, "New row id: " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_catalog_dummy:
                insertHabit();
                displayDatabaseInfo(readDatabaseInfo());
                return true;
            case R.id.menu_catalog_delete:
                //delete all;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
