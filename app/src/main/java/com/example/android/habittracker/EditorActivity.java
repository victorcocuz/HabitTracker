package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.habittracker.data.HabitContract;
import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static android.R.attr.id;

public class EditorActivity extends AppCompatActivity {

    private static final String LOG_TAG = EditorActivity.class.getSimpleName();
    private EditText habitName, habitDescription;
    private CheckBox habitMonday, habitTuesday, habitWednesday, habitThursday, habitFriday, habitSaturday, habitSunday;
    private Spinner habitReminder;
    private int reminder = 0;
    private int monday = 0, tuesday = 0, wednesday = 0, thursday = 0, friday = 0, saturday = 0, sunday = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        habitName = (EditText) findViewById(R.id.editor_name);
        habitDescription = (EditText) findViewById(R.id.editor_description);
        habitMonday = (CheckBox) findViewById(R.id.editor_monday);
        habitTuesday = (CheckBox) findViewById(R.id.editor_tuesday);
        habitWednesday = (CheckBox) findViewById(R.id.editor_wednesday);
        habitThursday = (CheckBox) findViewById(R.id.editor_thursday);
        habitFriday = (CheckBox) findViewById(R.id.editor_friday);
        habitSaturday = (CheckBox) findViewById(R.id.editor_saturday);
        habitSunday = (CheckBox) findViewById(R.id.editor_sunday);
        habitReminder = (Spinner) findViewById(R.id.editor_reminder);

        spinnerSetup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void spinnerSetup() {
        ArrayAdapter reminderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.reminder_array, android.R.layout.simple_spinner_item);
        reminderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        habitReminder.setAdapter(reminderSpinnerAdapter);

        habitReminder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.yes))) {
                        reminder = HabitEntry.REMINDER_YES;
                    } else {
                        reminder = HabitEntry.REMINDER_NO;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                reminder = 1;
            }
        });
    }

    private void checkboxSetup() {
        if(habitMonday.isChecked()) {monday = 1;}
        if(habitTuesday.isChecked()) {tuesday = 1;}
        if(habitWednesday.isChecked()) {wednesday = 1;}
        if(habitThursday.isChecked()) {thursday = 1;}
        if(habitFriday.isChecked()) {friday = 1;}
        if(habitSaturday.isChecked()) {saturday = 1;}
        if(habitSunday.isChecked()) {sunday = 1;}
    }

    private void insertHabit() {
        HabitDbHelper dbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitName.getText().toString().trim());
        values.put(HabitEntry.COLUMN_HABIT_DESCRIPTION, habitDescription.getText().toString());
        values.put(HabitEntry.COLUMN_HABIT_MONDAY, monday);
        values.put(HabitEntry.COLUMN_HABIT_TUESDAY, tuesday);
        values.put(HabitEntry.COLUMN_HABIT_WEDNESDAY, wednesday);
        values.put(HabitEntry.COLUMN_HABIT_THURSDAY, thursday);
        values.put(HabitEntry.COLUMN_HABIT_FRIDAY, friday);
        values.put(HabitEntry.COLUMN_HABIT_SATURDAY, saturday);
        values.put(HabitEntry.COLUMN_HABIT_SUNDAY, sunday);
        values.put(HabitEntry.COLUMN_HABIT_REMINDER, reminder);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.e(LOG_TAG, "New row id: " + newRowId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_editor_save:
                checkboxSetup();
                insertHabit();
                finish();
                return true;

            case R.id.menu_editor_delete:
                //Do nothing for now;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
