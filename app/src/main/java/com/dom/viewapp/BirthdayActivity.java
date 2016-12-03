package com.dom.viewapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BirthdayActivity extends AppCompatActivity {

    EditText dateEdit, nameEdit;
    Button addButton;

    DBHelper dbHelper;

    public Integer day, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthday);

        dateEdit = (EditText) findViewById(R.id.editBdDate);
        dateEdit.setRawInputType(0x00000000);

        nameEdit = (EditText) findViewById(R.id.editBdName);
        addButton = (Button) findViewById(R.id.add_button);

        dbHelper = new DBHelper(this);

        // Выбор даты
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getApplicationContext(), "Ты нажал на дату!", Toast.LENGTH_LONG).show();
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // Добавить запись
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdit.getText().toString();
                String date = dateEdit.getText().toString();

                String[] dats = date.split("-");

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                contentValues.put(dbHelper.KEY_NAME, name);
                contentValues.put(dbHelper.KEY_DAY, dats[0]);
                contentValues.put(dbHelper.KEY_MONTH, dats[1]);

                database.insert(dbHelper.TABLE_BIRTHDAYS,null, contentValues);
                Toast.makeText(getApplicationContext(), "Добавлена новая запись", Toast.LENGTH_LONG).show();
            }

        });

    }

}
